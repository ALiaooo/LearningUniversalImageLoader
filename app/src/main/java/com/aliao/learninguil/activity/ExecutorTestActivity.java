package com.aliao.learninguil.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.aliao.learninguil.R;
import com.aliao.learninguil.utils.L;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by ��˫ on 2015/6/25.
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
         * newFixedThreadPool����һ������ָ����Ŀ�̵߳��̳߳أ�����������������߳���Ŀ����ôû��û��ִ�е��������ȴ���ֱ�����������Ϊֹ
         */
        Executor executor = Executors.newFixedThreadPool(2);
        /**
         * newCachedThreadPool�����û���������������Ӧ���߳����������̳߳ز�����߳���Ŀ�������ƣ���ȫ������JVM�ܴ����̵߳����������������ڴ治�㡣
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
