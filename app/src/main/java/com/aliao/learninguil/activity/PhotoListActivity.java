package com.aliao.learninguil.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.aliao.learninguil.Constants;
import com.aliao.learninguil.R;
import com.aliao.learninguil.adapter.PhotoListAdapter;
import com.aliao.learninguil.entity.ImageInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 丽双 on 2015/7/13.
 */
public class PhotoListActivity extends AppCompatActivity {

    private ListView mListView;
    private PhotoListAdapter mAdapter;
    private List<ImageInfo> mImageInfos = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photolist);
        for (int i = 0; i< Constants.IMAGES.length; i++){
            ImageInfo imageInfo = new ImageInfo();
            imageInfo.setUrl(Constants.IMAGES[i]);
            imageInfo.setName("item-" + i);
            mImageInfos.add(imageInfo);
        }
        mListView = (ListView) findViewById(R.id.photoList);
        mAdapter = new PhotoListAdapter(mImageInfos, mListView);
        mListView.setAdapter(mAdapter);
    }
}
