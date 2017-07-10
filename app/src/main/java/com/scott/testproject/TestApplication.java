package com.scott.testproject;

import android.app.Application;

import com.scott.testproject.eventbus.MyEventBusIndex;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by zouzhiyi on 10/07/17.
 */

public class TestApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        EventBus.builder().addIndex(new MyEventBusIndex()).installDefaultEventBus();
    }
}

