package com.scott.testproject.encryptionAlgorithm;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.scott.testproject.R;

import java.util.ArrayList;
import java.util.List;

public class EncrypetionTestActivity extends AppCompatActivity implements View.OnClickListener{
    private Spinner mSpinner;
    private String mCurrentEncryptType;
    private String[] mArray;

    private Button mEncryptButton;
    private Button mDecryptButton;
    private EditText mContentEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aestest);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mArray = getResources().getStringArray(R.array.encrypt_array);

        mSpinner = (Spinner)findViewById(R.id.spinner_choose);
        mContentEditText = (EditText)findViewById(R.id.encrypt_et);
        mEncryptButton = (Button)findViewById(R.id.encrypt_btn);
        mDecryptButton = (Button)findViewById(R.id.decrypt_btn);
        mDecryptButton.setOnClickListener(this);
        mEncryptButton.setOnClickListener(this);


        mSpinner.setSelection(0);
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mCurrentEncryptType = mArray[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.encrypt_btn:
                encrypt(mContentEditText.getText().toString(),mCurrentEncryptType);
                break;
            case R.id.decrypt_btn:

                break;
        }
    }

    private void encrypt(String content,String type) {
        if ("AES".equals(type)){
            testAES(content);
        }else if ("MD5".equals(type)){

        }else if ("DES".equals(type)){

        }else if ("SHA".equals(type)){

        }else if ("Base64".equals(type)){

        }else if ("RSA".equals(type)){

        }
    }

    private void testAES(String content) {
        List<Person> personList = new ArrayList<>();
        int testMaxCount = 1000;//测试的最大数据条数
        //添加测试数据
        for (int i = 0; i < testMaxCount; i++) {
            Person person = new Person();
            person.setAge(i);
            person.setName(String.valueOf(i));
            personList.add(person);
        }
        //FastJson生成json数据
        Gson gson = new GsonBuilder().create();
        String jsonData = gson.toJson(personList);
        Log.e("MainActivity", "AES加密前json数据 ---->" + jsonData);
        Log.e("MainActivity", "AES加密前json数据长度 ---->" + jsonData.length());

        //生成一个动态key
        String secretKey = AesUtils.generateKey();
        Log.e("MainActivity", "AES动态secretKey ---->" + secretKey);

        //AES加密
        long start = System.currentTimeMillis();
        String encryStr = AesUtils.encrypt(secretKey, jsonData);
        long end = System.currentTimeMillis();
        Log.e("MainActivity", "AES加密耗时 cost time---->" + (end - start));
        Log.e("MainActivity", "AES加密后json数据 ---->" + encryStr);
        Log.e("MainActivity", "AES加密后json数据长度 ---->" + encryStr.length());

        //AES解密
        start = System.currentTimeMillis();
        String decryStr = AesUtils.decrypt(secretKey, encryStr);
        end = System.currentTimeMillis();
        Log.e("MainActivity", "AES解密耗时 cost time---->" + (end - start));
        Log.e("MainActivity", "AES解密后json数据 ---->" + decryStr);
    }
}
