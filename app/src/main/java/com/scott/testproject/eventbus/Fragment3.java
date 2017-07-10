package com.scott.testproject.eventbus;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.scott.testproject.R;
import com.scott.testproject.eventbus.event.Event2;
import com.scott.testproject.eventbus.event.Event3;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * A fragment with a Google +1 button.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * create an instance of this fragment.
 */
public class Fragment3 extends Fragment implements View.OnClickListener{
    private Button mButton;
    private TextView mTextView;

    public Fragment3() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment3, container, false);
        mButton = (Button)view.findViewById(R.id.btn2);
        mTextView = (TextView)view.findViewById(R.id.tv2);
        mButton.setOnClickListener(this);

        return view;
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

    private void sendData2PlusOne(){
        EventBus.getDefault().post(new Event3(2,"来自Fragment3的消息"));
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onReceiverFragmentOneMessage(Event2 fragmentEvent){
        mTextView.setText(fragmentEvent.getMessage());
    }

    @Override
    public void onResume() {
        super.onResume();
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn2:
                sendData2PlusOne();
                break;
        }
    }
}
