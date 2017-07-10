package com.scott.testproject.eventbus;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.scott.testproject.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class EventBusActivity extends AppCompatActivity {
    private TextView mTextView;
    private TextView mTextView2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_bus);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //EventBus.builder().addIndex(new MyEventBusIndex()).installDefaultEventBus();
        mTextView = (TextView)findViewById(R.id.work_result);

        mTextView2 = (TextView)findViewById(R.id.work_result2);

        EventBus.getDefault().register(this);

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    public void startWorkThread(View view) {
        WorkThread workThread = new WorkThread();
        workThread.start();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onWorkFinish(TestEvent messageEvent){
        mTextView.setText(messageEvent.getMessage());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onWorkFinish2(TestEvent messageEvent){
        mTextView2.setText(messageEvent.getMessage());
    }

}
