package com.scott.testproject.eventbus;

import android.util.Log;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by zouzhiyi on 10/07/17.
 */

public class WorkThread {
    private final String TAG = WorkThread.class.getSimpleName();
    public void start(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                working();
            }
        }).start();
    }

    private void working() {
        try {
            Log.d(TAG,"working");
            Thread.sleep(3000);
            Log.d(TAG,"work finish");
            EventBus.getDefault().post(new TestEvent(3,"work finish after 3s"));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
