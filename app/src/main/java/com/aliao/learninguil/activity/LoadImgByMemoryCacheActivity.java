package com.aliao.learninguil.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.aliao.learninguil.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by ��˫ on 2015/6/25.
 */
public class LoadImgByMemoryCacheActivity extends AppCompatActivity implements View.OnClickListener {

    private String mImageUrl = "http://car0.autoimg.cn/upload/spec/5742/w_20101014155156565264.jpg";
    private ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memorycache);
        findViewById(R.id.btn_loadimage).setOnClickListener(this);
        mImageView = (ImageView) findViewById(R.id.iv_showimg);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_loadimage:
                showImage();
                break;
        }
    }

    private void showImage() {
        //���ж��ڴ�����û��ͼƬ�����û��ȥ��������
         Bitmap bitmap = getBitmapFromMemoryCache("");
        if (bitmap != null){
            mImageView.setImageBitmap(bitmap);
        }else {
            //�ڴ���û��ͼƬ��ȥ��������ͼƬ
            new LoadImageTask().execute();
//            requestImage();

            /*Bitmap bitmap1 = requestImage();
            if (bitmap != null){
                mImageView.setImageBitmap(bitmap1);
            }*/
        }

    }

    class LoadImageTask extends AsyncTask<Void, Void, Bitmap>{

        @Override
        protected Bitmap doInBackground(Void... params) {
            InputStream is = loadImage();
            return BitmapFactory.decodeStream(is);//getNetWorkBitmap(mImageUrl);//BitmapFactory.decodeStream(is);
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            if (null != bitmap){
                mImageView.setImageBitmap(bitmap);
            }
        }
    }

    public static Bitmap getNetWorkBitmap(String urlString) {
        URL imgUrl = null;
        Bitmap bitmap = null;
        try {
            imgUrl = new URL(urlString);
            // ʹ��HttpURLConnection������
            HttpURLConnection urlConn = (HttpURLConnection) imgUrl
                    .openConnection();
            urlConn.setDoInput(true);
            urlConn.connect();
            // ���õ�������ת����InputStream
            InputStream is = urlConn.getInputStream();
            // ��InputStreamת����Bitmap
            bitmap = BitmapFactory.decodeStream(is);
            is.close();
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            System.out.println("[getNetWorkBitmap->]MalformedURLException");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("[getNetWorkBitmap->]IOException");
            e.printStackTrace();
        }
        return bitmap;
    }


    private InputStream loadImage() {

        HttpURLConnection urlConnection = null;
        InputStream is = null;
        try {
            URL url = new URL(mImageUrl);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(15 * 1000);
            urlConnection.setDoInput(true);
            urlConnection.connect();
            is = urlConnection.getInputStream();
            /*if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                is = urlConnection.getInputStream();
            }*/

            //д������

            return is;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    private Bitmap getBitmapFromMemoryCache(String cacheKey) {

        return null;
    }

    private Handler handler = new Handler();

    private void requestImage() {
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(new loadImageRunnabel(handler));
    }

    class loadImageRunnabel implements Runnable{

        private Handler handler;

        public loadImageRunnabel(Handler handler) {
            this.handler = handler;
        }

        @Override
        public void run() {
            final InputStream inputStream = loadImage();
            handler.post(new Runnable() {
                @Override
                public void run() {
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                    mImageView.setImageBitmap(bitmap);
                }
            });
        }
    }

}
