package com.scott.testproject.oom;

import android.app.ActivityManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.scott.testproject.R;

public class OOMTestActivity extends AppCompatActivity {
    private TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oomtest);

        ActivityManager activityManager =
                (ActivityManager)getSystemService(ACTIVITY_SERVICE);
        int size = activityManager.getMemoryClass();
        textView = (TextView) findViewById(R.id.memory_limit);
        textView.setText("每个进程可用的最大内存:"+size+"m");
    }

}
