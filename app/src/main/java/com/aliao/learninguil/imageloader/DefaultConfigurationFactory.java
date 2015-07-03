package com.aliao.learninguil.imageloader;


import android.support.v4.util.LruCache;

import com.aliao.learninguil.disk.DiskLruCache;
import com.aliao.learninguil.utils.CacheUtil;

/**
 * Created by 丽双 on 2015/6/26.
 * 如果 1+1 ＝2 。1+22 ＝3。 1+3=44.那么请问：1+4= ？
 */
public class DefaultConfigurationFactory {


    public static LruCache createMemoryCache(){
        int memoryCacheSize = CacheUtil.getAvailableMaxMemeryCache() / 8;
        return new LruCache(memoryCacheSize);
    }


}
