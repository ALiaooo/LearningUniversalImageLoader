package com.aliao.learninguil.activity;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;

import com.aliao.learninguil.disk.DiskLruCache;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by 丽双 on 2015/7/3.
 * 存储在sd卡上
 * 监测是否有sd卡，如果有，则存储在sd卡上，如果没有则存储到内存上
 * sd卡 data/Android/package_name/cache
 * 内存 data/data/package_name/cache
 * 在上面的缓存路径上进行读写操作
 */
public class LoadImageByDiskCacheActivity extends AppCompatActivity {


    private String mImageUrl = "http://car0.autoimg.cn/upload/spec/5742/w_20101014155156565264.jpg";
    private DiskLruCache mDiskLruCache;

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);

        createDiskLruCache();
    }

    private void createDiskLruCache() {


        /**
         * 第一步：获取DiskLruCache的实例
         * fileDirectory:保存图片的目录
         * appVersion：app版本号
         * valueCount：每个缓存实体的数量值
         * maxSize：最大缓存大小
         */
        try {
            File cacheDir = getCacheDirectory(this,"bitmap");
            if (!cacheDir.exists()){
                cacheDir.mkdirs();
            }
            mDiskLruCache = DiskLruCache.open(cacheDir,getAppVersion(this), 1, 10 * 1024 * 1024);
        } catch (IOException e) {
            e.printStackTrace();
        }

        /**
         * 第二步 将图片写入缓存
         * 1.获取缓存文件的文件名
         * 2.下载图片
         * 3.写入缓存
         */
        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    String key = hashKeyForDisk(mImageUrl);//缓存文件的文件名
                    DiskLruCache.Editor editor = mDiskLruCache.edit(key);
                    if (editor != null){
                        OutputStream outputStream = editor.newOutputStream(0);
                        if (downloadUrlToStream(mImageUrl, outputStream)){
                            editor.commit();
                        }else {
                            editor.abort();
                        }
                    }
                    mDiskLruCache.flush();

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }).start();

        /**
         * 从缓存中读取
         */


    }

    /**
     * 获取图片缓存的目录
     * @param directoryName
     * @return
     */
    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    private File getCacheDirectory(Context context, String directoryName){

        String cachePath;

        //判断有没有sd卡或者不可移除的sd卡（内置sd卡）
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) && !Environment.isExternalStorageRemovable()){
            //如果有，则存到sd卡的目录下
            cachePath = context.getExternalCacheDir().getPath();
        }else {
            //否则存储到内存缓存目录中
            cachePath = context.getCacheDir().getPath();
        }

        return new File(cachePath + File.separator +directoryName);
    }

    /**
     * 获取当前应用程序的版本号
     * @param context
     * @return
     */
    private int getAppVersion(Context context){
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 1;
    }

    /**
     * 将图片的URL进行MD5编码，编码后的字符串肯定是唯一的，并且只会包含0-F这样的字符，完全符合文件的命名规则
     * @param key
     * @return
     */
    public String hashKeyForDisk(String key) {
        String cacheKey;
        try {
            final MessageDigest mDigest = MessageDigest.getInstance("MD5");
            mDigest.update(key.getBytes());
            cacheKey = bytesToHexString(mDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            cacheKey = String.valueOf(key.hashCode());
        }
        return cacheKey;
    }

    private String bytesToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(0xFF & bytes[i]);
            if (hex.length() == 1) {
                sb.append('0');
            }
            sb.append(hex);
        }
        return sb.toString();
    }

    private boolean downloadUrlToStream(String imageUrl, OutputStream outputStream){
        HttpURLConnection connection = null;
        BufferedInputStream bufferedInputStream = null;
        BufferedOutputStream bufferedOutputStream = null;
        try {
            URL url = new URL(imageUrl);

            connection = (HttpURLConnection) url.openConnection();

            bufferedInputStream = new BufferedInputStream(connection.getInputStream());

            bufferedOutputStream = new BufferedOutputStream(outputStream);

            int read;
            while ((read = bufferedInputStream.read()) != -1){
                bufferedOutputStream.write(read);
            }
            return true;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (connection != null){
                connection.disconnect();
            }

            if (bufferedInputStream != null){
                try {
                    bufferedInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (bufferedOutputStream != null){
                try {
                    bufferedOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }
}
