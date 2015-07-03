package com.aliao.learninguil.activity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by 丽双 on 2015/7/3.
 * 存储在sd卡上
 * 监测是否有sd卡，如果有，则存储在sd卡上，如果没有则存储到内存上
 * sd卡 data/Android/package_name/cache
 * 内存 data/data/package_name/cache
 * 在上面的地址上进行读写操作
 */
public class LoadImageByDiskCacheActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);

    }
}
