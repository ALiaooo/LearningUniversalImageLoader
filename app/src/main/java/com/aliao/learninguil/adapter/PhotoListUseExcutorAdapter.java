package com.aliao.learninguil.adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
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
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by 丽双 on 2015/8/4.
 * 使用线程池进行异步请求
 */
public class PhotoListUseExcutorAdapter  extends BaseAdapter implements AbsListView.OnScrollListener{

    private List<ImageInfo> imageInfos;
    private ListView mListView;
    private int mFirstVisibleItem;
    private int mVisibleItemCount;
    private boolean mFirstEnter;
    private Set<LoadImageAsyncTask> taskCollection;

    private Executor mExecutor;

    public PhotoListUseExcutorAdapter(List<ImageInfo> imageInfos, ListView listView) {
        this.imageInfos = imageInfos;
        mListView = listView;
        mListView.setOnScrollListener(this);
        mFirstEnter = true;
        taskCollection = new HashSet<>();
        mExecutor = Executors.newFixedThreadPool(5);
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
//        L.d("position = "+position+", url = "+imageInfo.getUrl());
//        loadAndSetImage(imageInfo.getUrl());

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
            L.d("position = " + i + ", url = " + imageInfo.getUrl());
            //使用线程池
            mExecutor.execute(new LoadImageRunnable(imageInfo.getUrl(), handler));
            //使用AsyncTask
//            LoadImageAsyncTask task = new LoadImageAsyncTask();
//            task.execute(imageInfo.getUrl());
//            taskCollection.add(task);
        }
    }

    private Handler handler = new Handler();
    class LoadImageRunnable implements Runnable{
        private Handler handler;
        private String mImageUrl;
        public LoadImageRunnable(String url, Handler handler) {
            mImageUrl = url;
            this.handler = handler;
        }

        @Override
        public void run() {
            final Bitmap bitmap = loadBitmap(mImageUrl);

            handler.post(new Runnable() {
                @Override
                public void run() {
                    ImageView imageView = (ImageView) mListView.findViewWithTag(mImageUrl);
                    if (imageView != null){
                        imageView.setImageBitmap(bitmap);
                    }
                }
            });

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
