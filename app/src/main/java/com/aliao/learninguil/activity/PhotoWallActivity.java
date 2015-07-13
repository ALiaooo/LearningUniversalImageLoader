package com.aliao.learninguil.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.GridView;

import com.aliao.learninguil.Constants;
import com.aliao.learninguil.R;
import com.aliao.learninguil.adapter.PhotoWallAdapter;

/**
 * Created by 丽双 on 2015/7/9.
 * 结合内存缓存和磁盘缓存实现照片墙
 */
public class PhotoWallActivity extends AppCompatActivity{

    private GridView mGridView;
    private PhotoWallAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photowall);
        mGridView = (GridView) findViewById(R.id.photoWall);
        mAdapter = new PhotoWallAdapter(Constants.IMAGES, mGridView);
        mGridView.setAdapter(mAdapter);
    }

}
