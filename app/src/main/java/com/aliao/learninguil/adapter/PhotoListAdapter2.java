package com.aliao.learninguil.adapter;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.aliao.learninguil.R;
import com.aliao.learninguil.entity.ImageInfo;
import com.aliao.learninguil.utils.L;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by ALiao on 2015/7/27.
 * 减少对象对内存的消耗
 * 由于图片消耗的内存较大，所以这个类主要用来测试图片消耗内存的大小以及降低图片对内存消耗的方法
 * 1.查看Bitmap占用内存的大小
 * 2.如果图片实际尺寸比要显示的尺寸要大，可以通过压缩图片来减少内存的浪费
 */
public class PhotoListAdapter2 extends BaseAdapter implements AbsListView.OnScrollListener{

    private List<ImageInfo> imageInfos;
    private ListView mListView;
    private int mFirstVisibleItem;
    private int mVisibleItemCount;
    private boolean mFirstEnter;
    private Set<LoadImageAsyncTask> taskCollection;

    public PhotoListAdapter2(List<ImageInfo> imageInfos, ListView listView) {
        this.imageInfos = imageInfos;
        mListView = listView;
        mListView.setOnScrollListener(this);
        mFirstEnter = true;
        taskCollection = new HashSet<>();
    }

    @Override
    public int getCount() {
        return imageInfos.size();
    }

    @Override
    public Object getItem(int position) {
        return imageInfos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        if (convertView == null){
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_photolist, parent, false);
            holder = new ViewHolder();
            holder.imgView = (ImageView) convertView.findViewById(R.id.iv_img);
            holder.imgName = (TextView) convertView.findViewById(R.id.tv_imagename);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        ImageInfo imageInfo = imageInfos.get(position);
        holder.imgName.setText(imageInfo.getName());
        holder.imgView.setTag(imageInfo.getUrl());
        return convertView;
    }


    class ViewHolder{
        ImageView imgView;
        TextView imgName;
    }


    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        /**
         * scrollState = SCROLL_STATE_IDLE( = 0 )停止混动
         * scrollState = SCROLL_STATE_TOUCH_SCROLL( = 1 )正在滚动
         * scrollState = SCROLL_STATE_FLING( = 2 ) 手指做了抛的动作
         */
        if (scrollState == SCROLL_STATE_IDLE){
            loadAndSetImage(mFirstVisibleItem, mVisibleItemCount);
        }else {
            //当listview再次滑动时取消所有正在下载的任务
            cancelAllTasks();
        }
    }

    public void cancelAllTasks() {
        if (taskCollection != null){
            for (LoadImageAsyncTask task : taskCollection){
                task.cancel(false);
            }
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        mFirstVisibleItem = firstVisibleItem;//第一张可见图片的下标
        mVisibleItemCount = visibleItemCount;//一屏可见图片的总数

        //首次进入程序时，onScrollStateChanged方法并不会被调用，所以在这里首次进入程序时启动下载任务
        if (mFirstEnter && visibleItemCount > 0){
            loadAndSetImage(mFirstVisibleItem, mVisibleItemCount);
            mFirstEnter = false;
        }

    }

    private void loadAndSetImage(int firstVisibleItem, int visibleItemCount) {
        for (int i = firstVisibleItem; i< firstVisibleItem + visibleItemCount; i++){
            ImageInfo imageInfo = imageInfos.get(i);
            LoadImageAsyncTask task = new LoadImageAsyncTask();
            task.execute(imageInfo.getUrl());
            taskCollection.add(task);
        }
    }

    class LoadImageAsyncTask extends AsyncTask<String, Void, Bitmap> {

        private String mImageUrl;

        @Override
        protected Bitmap doInBackground(String... params) {

            mImageUrl = params[0];

            Bitmap bitmap = loadBitmap(mImageUrl);

            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            ImageView imageView = (ImageView) mListView.findViewWithTag(mImageUrl);
            if (imageView != null){
                imageView.setImageBitmap(bitmap);
            }
        }
    }

    private Bitmap loadBitmap(String imageUrl) {
        HttpURLConnection connection = null;
        Bitmap bitmap = null;
        try {
            URL url = new URL(imageUrl);
            connection = (HttpURLConnection) url.openConnection();

            BitmapFactory.Options options = new BitmapFactory.Options();
            //防止第一次解析时对图片的内存分配
            options.inJustDecodeBounds = true;
            //第一次解析
            BitmapFactory.decodeStream(connection.getInputStream(), null, options);
            //decodeStream不能重复解析同一个网络流的inputStream，需要重新打开一次inputStream
            connection.disconnect();
            connection = (HttpURLConnection) url.openConnection();
            //获取图片的缩放比例
            options.inSampleSize = calculateInSampleSize(options,400, 400);
            //将options.inJustDecodeBounds的值设回false，为了第二次解析能够正常分配内存
            options.inJustDecodeBounds = false;
            //第二次解析，此时图片的内存会按照压缩后的图片宽高去分配
            bitmap = BitmapFactory.decodeStream(connection.getInputStream(), null, options);
            L.d("loadBitmap url = "+imageUrl);
            L.d("该图占用内存 = "+getBitmapSize(bitmap)+"bytes, "+(getBitmapSize(bitmap)/1024)+"KB"+", width = "+bitmap.getWidth()+", height = "+bitmap.getHeight()+", config = "+bitmap.getConfig().name());

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

    private int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        int width = options.outWidth;//原图的宽
        int height = options.outHeight;//原图的高
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth){
            //计算出原图宽高和目标宽高的比例——缩小几倍
            int heightRadio = Math.round((float)height / (float)reqHeight);
            int widthRadio = Math.round((float)width / (float)reqWidth);
            //选择宽和高中最小的比例作为inSampleSize的值，可以保证最终图片的宽和高一定都会大于等于目标的宽和高
            inSampleSize = heightRadio < widthRadio ? heightRadio : widthRadio;
        }
        return inSampleSize;
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public int getBitmapSize(Bitmap bitmap){

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){// API 19  Android 4.4
            return bitmap.getAllocationByteCount();
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1){// API 12  Android 3.1
            return bitmap.getByteCount();
        }

        return bitmap.getRowBytes() * bitmap.getHeight();
    }

}
