package com.william_zhang.mystudy.videoplayer;

import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.AbsListView;
import android.widget.ListView;

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
 * 列表滚动暂停    自动播放的功能
 * Created by william_zhang on 2018/1/19.
 */

public class VideoListActivity extends AppCompatActivity {
    @BindView(R.id.list)
    ListView list;
    private ListViewAdapter adapter;
    private List<String> urlList;
    private int firstVisible;//当前第一个可见的item
    private int visibleCount;//当前可见的数量

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_list);
        ButterKnife.bind(this);
        urlList = new LinkedList<>();
        urlList.add(VideoplayerActivity.url);
        urlList.add(VideoplayerActivity.url2);
        urlList.add(VideoplayerActivity.url);
        urlList.add(VideoplayerActivity.url2);
        urlList.add(VideoplayerActivity.url);
        urlList.add(VideoplayerActivity.url2);
        adapter = new ListViewAdapter(this, R.layout.item_video_list, urlList);
        list.setAdapter(adapter);
        list.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {
                switch (i) {
                    //刚开始滚动的时候
                    case AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:
                        break;
                    //滚动结束的时候
                    case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:
                        antoPlayVideo(absListView);
                        Log.e("onScrollStateChanged", "自动播放哦");
                        break;
                    //手指离开屏幕靠惯性的时候回调用，不一定每次都有
                    case AbsListView.OnScrollListener.SCROLL_STATE_FLING:
                        break;
                }
            }

            /**
             *
             * @param view
             * @param firstVisibleItem 可视的第一个item
             * @param visibleItemCount  可见的所有数量
             * @param totalItemCount  总数量
             */
            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                //JZVideoPlayer.onScrollReleaseAllVideos(view, firstVisibleItem, visibleItemCount, totalItemCount);//滚动的时候暂停所有的
                if (firstVisible == firstVisibleItem)//获得第一个可见的postion
                    return;
                firstVisible = firstVisibleItem;
                visibleCount = visibleItemCount;
            }
        });
    }

    JZVideoPlayerStandard currentPlayer;

    private void antoPlayVideo(AbsListView absListView) {
        for (int i = 0; i < visibleCount; i++) {//遍历可见得
            if (absListView != null && absListView.getChildAt(i) != null && absListView.getChildAt(i).findViewById(R.id.video_item) != null) {
                currentPlayer = (JZVideoPlayerStandard) absListView.getChildAt(i).findViewById(R.id.video_item);
                //目的是获得view的位置
                Rect rect = new Rect();
                //以目标左上角作参考系
                currentPlayer.getLocalVisibleRect(rect);
                int height = currentPlayer.getHeight();
                if (rect.top == 0 && rect.bottom == height) {
                    if (currentPlayer.currentState == JZVideoPlayer.CURRENT_STATE_NORMAL ||
                            currentPlayer.currentState == JZVideoPlayer.CURRENT_STATE_ERROR) {
                        currentPlayer.startButton.performClick();//模拟点击
                    }
                    return;
                }
            }
        }
    }

    class ListViewAdapter extends CommonAdapter<String> {

        public ListViewAdapter(Context context, int layoutId, List<String> datas) {
            super(context, layoutId, datas);
        }

        @Override
        protected void convert(ViewHolder viewHolder, String item, int position) {
            JZVideoPlayerStandard videoPlayerStandard = viewHolder.getView(R.id.video_item);
//            if (videoPlayerStandard != null && videoPlayerStandard.dataSourceObjects != null) {
//                videoPlayerStandard.release();//释放资源
//            }
            videoPlayerStandard.setUp(item, JZVideoPlayer.SCREEN_WINDOW_LIST, "");
        }
    }

    @Override
    public void onBackPressed() {
        if (JZVideoPlayer.backPress()) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        JZVideoPlayer.releaseAllVideos();
    }
}
