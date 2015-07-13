package com.aliao.learninguil.adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v4.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.aliao.learninguil.R;
import com.aliao.learninguil.utils.CacheUtil;
import com.aliao.learninguil.utils.L;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by 丽双 on 2015/7/9.
 *
 * 使用内存缓存实现图片墙 http://blog.csdn.net/guolin_blog/article/details/9526203
 *
 * 从源码分析Universal-Image-Loader的缓存机制
 * http://www.cnblogs.com/kissazi2/p/3931400.html
 */
public class PhotoWallAdapter extends BaseAdapter {

    private String[] mImageUrls;
    private LruCache<String, Bitmap> mLruCache;
    private GridView mGridView;

    public PhotoWallAdapter(String[] imageUrls, GridView gridView) {
        mGridView = gridView;
        mImageUrls = imageUrls;
        int maxSize = CacheUtil.getAvailableMaxMemeryCache() / 8;//取手机最大可用内存的1/8
        mLruCache = new LruCache<>(maxSize);
    }

    @Override
    public int getCount() {
        return mImageUrls.length;
    }

    @Override
    public Object getItem(int position) {
        return mImageUrls[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String imageUrl = mImageUrls[position];
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_photo, parent, false);
            holder.ivPhoto = (ImageView) convertView.findViewById(R.id.iv_photo);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.ivPhoto.setTag(imageUrl);

        //判断缓存中是否有图片
//        setPhotoView(imageUrl, holder.ivPhoto);

        L.d("position = "+position+", imageUrl = "+imageUrl);
        loadBitmap(imageUrl);

        return convertView;
    }

    private void setPhotoView(String imageUrl, ImageView ivPhoto) {
        Bitmap bitmap = getBitmapFromMemoryCache(imageUrl);
        if (bitmap != null){
            ivPhoto.setImageBitmap(mLruCache.get(imageUrl));
        }else {
            ivPhoto.setImageResource(R.mipmap.ic_launcher);
        }
    }

    private Bitmap getBitmapFromMemoryCache(String imageUrl) {
        if (mLruCache.get(imageUrl) != null){
            return mLruCache.get(imageUrl);
        }else {
            return null;//loadBitmap(imageUrl);
        }
    }

    class ViewHolder{
        private ImageView ivPhoto;
    }

    private void loadBitmap(final String imageUrl) {

        new LoadImageAsyncTask().execute(imageUrl);
    }

    class LoadImageAsyncTask extends AsyncTask<Object, Void, Bitmap>{

        private String mImageUrl;

        @Override
        protected Bitmap doInBackground(Object... params) {
            mImageUrl = (String) params[0];
            Bitmap bitmap = doadloadImage(mImageUrl);
            mLruCache.put(mImageUrl, bitmap);
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            L.d("onPostExecute");
            ImageView imageView = (ImageView) mGridView.findViewWithTag(mImageUrl);
            if (imageView != null){
                L.d("onPostExecute setImage = " + mImageUrl);
                imageView.setImageBitmap(bitmap);}
        }
    }

    /**
     * 通过Http请求下载图片
     * @param imageUrl
     * @return
     */
    private Bitmap doadloadImage(String imageUrl) {
        Bitmap bitmap = null;
        HttpURLConnection connection = null;
        try {
            URL url = new URL(imageUrl);
            connection = (HttpURLConnection) url.openConnection();
            bitmap = BitmapFactory.decodeStream(connection.getInputStream());
            return bitmap;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (connection != null){
                connection.disconnect();
            }
        }

        return bitmap;
    }

}
