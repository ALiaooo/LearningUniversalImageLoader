package com.aliao.learninguil.imageloader;


import android.support.v4.util.LruCache;

import com.aliao.learninguil.utils.CacheUtil;

/**
 * Created by 丽双 on 2015/6/26.
 */
public class DefaultConfigurationFactory {

    public static LruCache createMemoryCche(){
        int memoryCacheSize = CacheUtil.getAvailableMaxMemeryCache() / 8;
        return new LruCache(memoryCacheSize);
    }
}
