package com.aliao.learninguil.adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
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
 * Created by 丽双 on 2015/7/13.
 *
 * 一、最原始的下载图片的方法
 * 没有设置item的高，图片的高，默认显示的是textview的高度，所以刚进入时一屏能够显示27个列表项，也就是说getView一开始调用了27次，那么线程也就创建了27个，下载了27张图片
 * 前几张图下载好后显示在屏幕上，根据图片的高度一屏只够显示5个，那么只保留显示在当前屏幕的convertView，之前创建的其他的convertView被回收了，通过findViewWithTag也就找不到imageurl对应的imageView。
 * （不止是请求过得要重新请求这种）由此可以看到不是屏幕显示多少张就去对应地进行多少次网络请求，事实上做了很多多余的网络请求。除了该情况外，当快速滑动列表时，那些被快速划过的item也要进行网络请求，这种一种资源浪费，既然不是用户要看的，为什么要去请求呢。
 * 所以接下来的主要目的就是避免多余的网络请求！
 * 1.图片高度设置的影响
 * 如果listview的item的高度是自动扩展的，比如没有设置item的高度或者imageView的高度，那进入列表页的时候，默认显示的高度就是textview的高度，当图片下载完毕，
 * 再显示在imageview中后，高度又扩展到他自己的大小。在这个过程中，一屏显示的item数是在变化的，因为textview的高度很小，一屏可以显示27个（举例），当图片现在完成显示完全后，一屏最终显示了6个item。
 * 如果提前已知item的显示高度，例如设置ImageView的高度为100dp，那么一屏能够显示的item数量是确定的，就可以做到一屏显示多少个item，进行相应数量的网络请求。
 *
 * 2.不想查看而被快速滑过去的图片
 * 对于我们不想查看而快速滑过的图片是没有必要浪费资源去加载的。但是由于把加载图片的操作放在了getView()中，只要滑动屏幕都会调用getView()，也就没法控制图片加载的时机。最好的做法就是，当listview滑动停止时再去加载。
 *
 */
public class PhotoListAdapter extends BaseAdapter implements AbsListView.OnScrollListener{

    private List<ImageInfo> imageInfos;
    private ListView mListView;
    private int mFirstVisibleItem;
    private int mVisibleItemCount;
    private boolean mFirstEnter;
    private Set<LoadImageAsyncTask> taskCollection;

    public PhotoListAdapter(List<ImageInfo> imageInfos, ListView listView) {
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
        L.d("-------》onScrollStateChanged scrollState = "+scrollState);
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
        L.d("onScroll firstVisibleItem = "+firstVisibleItem+", visibleItemCount = "+visibleItemCount+", totalItemCount = "+totalItemCount);
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
            L.d("position = "+i+", url = "+imageInfo.getUrl());
            LoadImageAsyncTask task = new LoadImageAsyncTask();
            task.execute(imageInfo.getUrl());
            taskCollection.add(task);
        }
    }

    class LoadImageAsyncTask extends AsyncTask<String, Void, Bitmap>{

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
            L.d("------------------------------------loadBitmap ");
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
