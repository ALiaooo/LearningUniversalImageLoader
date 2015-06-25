package com.aliao.learninguil.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.aliao.learninguil.R;
import com.aliao.learninguil.utils.L;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by 丽双 on 2015/6/25.
 */
public class ExecutorTestActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_executor);
        findViewById(R.id.btn_execut).setOnClickListener(this);
        L.d("MainThreadName:" + Thread.currentThread().getName() + "   MainThreadId:" + Thread.currentThread().getId());

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_execut:
                executorTest();
                break;
        }
    }

    private void executorTest() {

        /**
         * newFixedThreadPool返回一个包含指定数目线程的线程池，如果任务数量多于线程数目，那么没有没有执行的任务必须等待，直到有任务完成为止
         */
        Executor executor = Executors.newFixedThreadPool(2);
        /**
         * newCachedThreadPool根据用户的任务数创建相应的线程来处理，该线程池不会对线程数目加以限制，完全依赖于JVM能创建线程的数量，可能引起内存不足。
         */
//        Executor executor = Executors.newCachedThreadPool();
        executor.execute(new MyRunnable());
        executor.execute(new MyRunnable());
        executor.execute(new MyRunnable());
    }

    class MyRunnable implements Runnable{

        @Override
        public void run() {
            for (int i = 0; i<10; i++){
                L.d(i+" - ThreadName:"+Thread.currentThread().getName()+"   ThreadId:"+Thread.currentThread().getId());
            }
        }
    }
}
