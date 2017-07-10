package com.scott.testproject;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

/**
 * Created by zouzhiyi on 19/04/17.
 */

public class MyTextView extends android.support.v7.widget.AppCompatTextView {
    public MyTextView(Context context) {
        super(context);
    }

    public MyTextView(Context context,
            @Nullable AttributeSet attrs) {
        super(context, attrs);
    }
}
