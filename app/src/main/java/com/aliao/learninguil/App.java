package com.aliao.learninguil;

import android.app.Application;
import android.content.Context;
import android.support.v4.util.LruCache;

import com.aliao.learninguil.imageloader.DefaultConfigurationFactory;

/**
 * Created by 丽双 on 2015/6/26.
 */
public class App extends Application {

    private static Context mContext;
    private static LruCache mLruCache;

    public App() {
        mContext = this;
    }

    public static Context getContext(){
        return mContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }


    public static LruCache getLruCache(){
        if (mLruCache == null){
            mLruCache = DefaultConfigurationFactory.createMemoryCche();
        }
        return mLruCache;
    }
}
