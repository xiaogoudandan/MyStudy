package com.william_zhang.mystudy;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.william_zhang.mystudy.videoplayer.ListActivity;
import com.william_zhang.mystudy.videoplayer.VideoListActivity;
import com.william_zhang.mystudy.videoplayer.VideoplayerActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @OnClick(R.id.videoplayer)
    public void onclick() {
        startActivity(new Intent(this, VideoplayerActivity.class));
    }

    @OnClick(R.id.videoplayerlist)
    public void onclick2() {
        //startActivity(new Intent(this, ListActivity.class));
        startActivity(new Intent(this, VideoListActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }
}
