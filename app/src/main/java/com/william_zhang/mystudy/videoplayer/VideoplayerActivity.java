package com.william_zhang.mystudy.videoplayer;

import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.Adapter;
import android.widget.ImageView;

import com.william_zhang.mystudy.R;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.jzvd.JZUtils;
import cn.jzvd.JZVideoPlayer;
import cn.jzvd.JZVideoPlayerStandard;

/**
 * https://github.com/lipangit/JiaoZiVideoPlayer/wiki/API的使用
 * Created by william_zhang on 2018/1/18.
 */

public class VideoplayerActivity extends AppCompatActivity {
    @BindView(R.id.image)
    ImageView image;
    //例子 网络视频
    public static final String url = "http://jzvd.nathen.cn/c6e3dc12a1154626b3476d9bf3bd7266/6b56c5f0dc31428083757a45764763b0-5287d2089db37e62345123a1be272f8b.mp4";
    public static final String url2 = "http://ips.ifeng.com/video19.ifeng.com/video09/2014/06/16/1989823-102-086-0009.mp4";
    private String imageUrl = "http://p.qpic.cn/videoyun/0/2449_43b6f696980311e59ed467f22794e792_1/640";
    @BindView(R.id.palyer)
    JZVideoPlayerStandard palyer;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videoplayer);
        ButterKnife.bind(this);
        setVideo();
    }

    private void setVideo() {
        palyer.setUp(url, JZVideoPlayer.SCREEN_WINDOW_NORMAL, "德玛西亚");
        long seek = JZUtils.getSavedProgress(this, url);
        try {
            Bitmap bitmap = getFristImageFromVideo(url, seek);//获得当前帧哦的图片
            image.setImageBitmap(bitmap);
            palyer.thumbImageView.setImageBitmap(bitmap);
        } catch (IllegalArgumentException e) {
            palyer.thumbImageView.setImageURI(Uri.parse(imageUrl));
        }
    }

    /**
     * 获得url第一帧
     *
     * @param url
     * @return
     */
    public static Bitmap getFristImageFromVideo(String url, long seek) {
        long time = 0;
        MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();//实例化MediaMetadataRetriever对象
        mediaMetadataRetriever.setDataSource(url, new HashMap<String, String>());
        String duration = mediaMetadataRetriever.extractMetadata(android.media.MediaMetadataRetriever.METADATA_KEY_DURATION);//获取时长
        if (!TextUtils.isEmpty(duration)) {
            time = Long.parseLong(duration);//计算总时长  并没是没用！
        }
        return mediaMetadataRetriever.getFrameAtTime(seek * 1000, MediaMetadataRetriever.OPTION_CLOSEST);//穿入微妙
    }

    @Override
    protected void onPause() {
        super.onPause();
        palyer.releaseAllVideos();
    }
}
