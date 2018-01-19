package com.william_zhang.mystudy.videoplayer;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.william_zhang.mystudy.R;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.jzvd.JZVideoPlayer;
import cn.jzvd.JZVideoPlayerStandard;

/**
 * Created by william_zhang on 2018/1/18.
 */

public class ListActivity extends AppCompatActivity {
    @BindView(R.id.list)
    ListView list;
    private List<String> linkedList;
    private ListAdapter adapter;
    private ListAdapter1 adapter1;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_list);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        linkedList = new LinkedList<>();
        linkedList.add(VideoplayerActivity.url);
        linkedList.add(VideoplayerActivity.url2);
        linkedList.add(VideoplayerActivity.url);
        linkedList.add(VideoplayerActivity.url2);
        linkedList.add(VideoplayerActivity.url);
        linkedList.add(VideoplayerActivity.url);
        linkedList.add(VideoplayerActivity.url2);
        adapter = new ListAdapter(this, R.layout.item_video, linkedList);
        adapter1 = new ListAdapter1(this);
        list.setAdapter(adapter);
    }

    class ListAdapter extends CommonAdapter<String> {
        public ListAdapter(Context context, int layoutId, List<String> datas) {
            super(context, layoutId, datas);
        }

        @Override
        protected void convert(ViewHolder viewHolder, String item, int position) {
            JZVideoPlayerStandard player = viewHolder.getView(R.id.item);
            if (player != null && player.dataSourceObjects != null) {
                player.release();
            }
            player.setUp(item, JZVideoPlayer.SCREEN_WINDOW_LIST, "");
        }
    }

    class ListAdapter1 extends BaseAdapter {
        Context context;

        public ListAdapter1(Context context) {
            this.context = context;
        }

        @Override
        public int getCount() {
            return linkedList.size();
        }

        @Override
        public Object getItem(int i) {
            return linkedList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View convertView, ViewGroup viewGroup) {
            ViewHolder1 viewHolder;
            if (null == convertView) {
                viewHolder = new ViewHolder1();
                LayoutInflater mInflater = LayoutInflater.from(context);
                convertView = mInflater.inflate(R.layout.item_video, null);
                viewHolder.player = (JZVideoPlayerStandard) convertView.findViewById(R.id.item);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder1) convertView.getTag();
            }
            if (viewHolder.player != null && viewHolder.player.dataSourceObjects != null) {
                viewHolder.player.release();
            }
            viewHolder.player.setUp(
                    linkedList.get(i), JZVideoPlayer.SCREEN_WINDOW_LIST,
                    "");
            return convertView;
        }
    }

    class ViewHolder1 {
        JZVideoPlayerStandard player;
    }

    @Override
    protected void onPause() {
        super.onPause();
        JZVideoPlayer.releaseAllVideos();
    }
}
