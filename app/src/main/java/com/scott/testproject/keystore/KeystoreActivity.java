package com.scott.testproject.keystore;

import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.scott.testproject.R;

import java.security.KeyStore;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;

public class KeystoreActivity extends AppCompatActivity {
    private static final String TAG = KeystoreActivity.class.getSimpleName();
    private static final String ALIAS = "spider_man";
    private static final String PROVIDER = "AndroidKeyStore";
    private static final String ENCRYPT_KEY="encrypt_key";
    private static final String IV_KEY="iv_key";
    private Cipher mCipher;
    private byte[] iv;
    private byte[] encryptionData;

    private EditText mEditText;
    private TextView mTextView;

    private SharedPreferences mSharedPreferences;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keystore);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mEditText = (EditText)findViewById(R.id.et);
        mTextView = (TextView)findViewById(R.id.result);

        initShareReference();
    }

    private void initShareReference() {
        mSharedPreferences = getPreferences(MODE_PRIVATE);
    }

    @android.support.annotation.RequiresApi(api = Build.VERSION_CODES.M)
    public void encrypt(View view) throws Exception {
        Log.d(TAG,"encrypt");
        final KeyGenerator keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES,
                PROVIDER);
        final KeyGenParameterSpec spec = new KeyGenParameterSpec.Builder(ALIAS,
                KeyProperties.PURPOSE_DECRYPT|KeyProperties.PURPOSE_ENCRYPT)
                .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
                .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
                .build();
        keyGenerator.init(spec);
        final SecretKey secretKey = keyGenerator.generateKey();
        mCipher = Cipher.getInstance("AES/GCM/NoPadding");
        mCipher.init(Cipher.ENCRYPT_MODE,secretKey);
        iv = mCipher.getIV();
        encryptionData = mCipher.doFinal(mEditText.getText().toString().getBytes("UTF-8"));
        mTextView.setText(Base64.encodeToString(encryptionData,Base64.DEFAULT));
        mSharedPreferences.edit().putString(ENCRYPT_KEY,Base64.encodeToString(encryptionData,Base64.DEFAULT)).apply();
        mSharedPreferences.edit().putString(IV_KEY,Base64.encodeToString(iv,Base64.DEFAULT)).apply();
        Log.d(TAG,"encrypt value:"+Base64.encodeToString(encryptionData,Base64.DEFAULT));
        Log.d(TAG,"iv value:"+Base64.encodeToString(iv,Base64.DEFAULT));
    }


    public void decrypt(View view) throws Exception {
        KeyStore keyStore = KeyStore.getInstance(PROVIDER);
        keyStore.load(null);
        KeyStore.SecretKeyEntry entry = (KeyStore.SecretKeyEntry)keyStore.getEntry(ALIAS,null);
        SecretKey secretKey = entry.getSecretKey();
        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        GCMParameterSpec spec = new GCMParameterSpec(128,
                Base64.decode(mSharedPreferences.getString(IV_KEY,null),Base64.DEFAULT));
        cipher.init(Cipher.DECRYPT_MODE,secretKey,spec);
        byte[] decryptData = cipher.doFinal(
                Base64.decode(mSharedPreferences.getString(ENCRYPT_KEY,null),Base64.DEFAULT));
        String decryptString = new String(decryptData,"UTF-8");
        mTextView.setText(decryptString);
    }
}
