package com.scott.testproject.arch.lifecycle;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;

/**
 * Created by zouzhiyi on 08/06/17.
 */

public class TestLifeCycle {
    public class MyObserver implements LifecycleObserver {
        @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
        public void onResume(){

        }

        @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
        public void onPause(){

        }

    }
}
