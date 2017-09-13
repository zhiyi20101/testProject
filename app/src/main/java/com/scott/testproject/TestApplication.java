package com.scott.testproject;

import android.app.Application;
import android.os.Build;
import android.os.StrictMode;

import com.scott.testproject.eventbus.MyEventBusIndex;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by zouzhiyi on 10/07/17.
 */

public class TestApplication extends Application {
    private static final boolean IS_DEBUG = true;
    @Override
    public void onCreate() {
        super.onCreate();
        EventBus.builder().addIndex(new MyEventBusIndex()).installDefaultEventBus();
        if (IS_DEBUG && Build.VERSION.SDK_INT > Build.VERSION_CODES.GINGERBREAD){
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                            .detectAll()
                            .penaltyDialog()
                            .build());
            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                            .detectAll()
                            .penaltyLog()
                            .build());
        }
    }
}

