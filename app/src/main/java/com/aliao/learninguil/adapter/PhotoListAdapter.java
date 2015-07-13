package com.aliao.learninguil.adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import java.util.List;

/**
 * Created by 丽双 on 2015/7/13.
 *
 * 一、最原始的下载图片的方法
 * 没有设置item的高，图片的高，默认显示的是textview的高度，所以刚进入时一屏能够显示27个列表项，也就是说getView一开始调用了27次，那么线程也就创建了27个，下载了27张图片
 * 前几张图下载好后显示在屏幕上，根据图片的高度一屏只够显示5个，那么只保留显示在当前屏幕的convertView，之前创建的其他的convertView被回收了，通过findViewWithTag也就找不到imageurl对应的imageView。
 * （不止是请求过得要重新请求这种）由此可以看到不是屏幕显示多少张就去对应地进行多少次网络请求，事实上做了很多多余的网络请求。除了该情况外，当快速滑动列表时，那些被快速划过的item也要进行网络请求，这种一种资源浪费，既然不是用户要看的，为什么要去请求呢。
 * 所以接下来的主要目的就是避免多余的网络请求！
 *
 */
public class PhotoListAdapter extends BaseAdapter {

    private List<ImageInfo> imageInfos;
    private ListView mListView;

    public PhotoListAdapter(List<ImageInfo> imageInfos, ListView listView) {
        this.imageInfos = imageInfos;
        mListView = listView;
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
        L.d("position = "+position+", url = "+imageInfo.getUrl());
        loadAndSetImage(imageInfo.getUrl());

        return convertView;
    }

    class ViewHolder{
        ImageView imgView;
        TextView imgName;
    }

    private void loadAndSetImage(String url) {
        new LoadImageAsyncTask().execute(url);
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
            L.d("onPostExecute ");
            ImageView imageView = (ImageView) mListView.findViewWithTag(mImageUrl);
            if (imageView != null){
                L.d("onPostExecute setImage - url = "+mImageUrl);
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
