package com.scott.testproject.eventbus;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.scott.testproject.R;
import com.scott.testproject.eventbus.event.Event;
import com.scott.testproject.eventbus.event.Event2;
import com.scott.testproject.eventbus.event.Event3;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class Fragment2 extends Fragment implements View.OnClickListener{
    private Button mButton;

    private TextView mTextView;


    public Fragment2() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onReceiveFragmentTwoMessage(Event3 event){
        mTextView.setText(event.getMessage());
    }

    @Subscribe
    public void onEvent(Event event){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment2, container, false);

        mButton = (Button)view.findViewById(R.id.btn1);
        mTextView = (TextView)view.findViewById(R.id.tv1);

        mButton.setOnClickListener(this);

        return view;
    }

    private void sendData2PlusTwo(){
        EventBus.getDefault().post(new Event2(1,"来自Fragment2的消息"));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn1:
                sendData2PlusTwo();
                break;
        }
    }
}
