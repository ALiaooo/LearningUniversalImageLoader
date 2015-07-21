package com.aliao.learninguil.activity;

import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.aliao.learninguil.Constants;
import com.aliao.learninguil.R;
import com.aliao.learninguil.adapter.PhotoListAdapter;
import com.aliao.learninguil.entity.ImageInfo;
import com.aliao.learninguil.utils.L;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ALiao on 2015/7/13.
 *
 *
 * Android 内存管理 &Memory Leak & OOM 分析
 * http://blog.csdn.net/vshuang/article/details/39647167
 *
 * Android最佳性能实践(二)——分析内存的使用情况
 * http://blog.csdn.net/guolin_blog/article/details/42238633
 */
public class PhotoListActivity extends AppCompatActivity {

    private ListView mListView;
    private PhotoListAdapter mAdapter;
    private List<ImageInfo> mImageInfos = new ArrayList<>();
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photolist);

        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        int heapgrowthlimit = manager.getMemoryClass();//可用堆内存，得到的是heapgrowthlimit的值
        int heapSize = manager.getLargeMemoryClass();//进程内存空间分配的最大值
        L.d("heapgrowthlimit = "+heapgrowthlimit+"M"+", heapsize = "+heapSize+"M");

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
