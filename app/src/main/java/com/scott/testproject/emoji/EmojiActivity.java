package com.scott.testproject.emoji;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.scott.testproject.R;

public class EmojiActivity extends Activity {
    private TextView mTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emoji);
        mTextView = (TextView)findViewById(R.id.emoji_tv);
        setEmojiToTextView();
    }

    private void setEmojiToTextView(){
        int unicodeJoy = 0x1F684;//0x1F602 0x1F684 😂 🚄
        String emojiString = getEmojiStringByUnicode(unicodeJoy);
        Log.d("","emojiString:"+emojiString);
        mTextView.setText(emojiString);
    }

    private String getEmojiStringByUnicode(int unicode){
        return new String(Character.toChars(unicode));
    }

}
