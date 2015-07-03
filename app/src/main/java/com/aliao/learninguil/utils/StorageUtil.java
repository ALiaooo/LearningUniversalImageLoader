package com.aliao.learninguil.utils;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Environment;

import java.io.File;
/**
 * Created by 丽双 on 2015/7/3.
 */
public class StorageUtil {

    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    public static File getDiskCache(Context context, String uniqueName){

        String cachePath;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) && !Environment.isExternalStorageRemovable()){
            cachePath =  context.getExternalCacheDir().getPath();
        }else {
            cachePath = context.getCacheDir().getPath();
        }

        return new File(uniqueName + File.separator + cachePath);
    }
}
