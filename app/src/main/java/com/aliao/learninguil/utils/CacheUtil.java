package com.aliao.learninguil.utils;

/**
 * Created by 丽双 on 2015/6/26.
 */
public class CacheUtil {
    /**
     * 返回可用内存的最大值，单位为kb
     * @return
     */
    public static int getAvailableMaxMemeryCache(){
        return (int) (Runtime.getRuntime().maxMemory() / 1024);
    }
}
