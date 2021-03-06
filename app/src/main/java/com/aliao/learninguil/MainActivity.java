package com.aliao.learninguil;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.aliao.learninguil.activity.ExecutorTestActivity;
import com.aliao.learninguil.activity.LoadImageByDiskCacheActivity;
import com.aliao.learninguil.activity.LoadImgByMemoryCacheActivity;
import com.aliao.learninguil.activity.PhotoListActivity;
import com.aliao.learninguil.activity.PhotoWallActivity;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn_executor).setOnClickListener(this);
        findViewById(R.id.btn_memorycache).setOnClickListener(this);
        findViewById(R.id.btn_disklrucache).setOnClickListener(this);
        findViewById(R.id.btn_photowall_memorycache).setOnClickListener(this);
        findViewById(R.id.btn_photolist_memorycache).setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_executor:
                startActivity(new Intent(MainActivity.this, ExecutorTestActivity.class));
                break;
            case R.id.btn_memorycache:
                startActivity(new Intent(MainActivity.this, LoadImgByMemoryCacheActivity.class));
                break;
            case R.id.btn_disklrucache:
                startActivity(new Intent(MainActivity.this, LoadImageByDiskCacheActivity.class));
                break;
            case R.id.btn_photowall_memorycache:
                startActivity(new Intent(MainActivity.this, PhotoWallActivity.class));
                break;
            case R.id.btn_photolist_memorycache:
                startActivity(new Intent(MainActivity.this, PhotoListActivity.class));
                break;
        }
    }
}
