package com.scott.testproject.runtimepermission;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.CellLocation;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;

import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.scott.testproject.R;

import java.io.IOException;

public class RuntimePermissionActivity extends AppCompatActivity {
    private static final String TAG = RuntimePermissionActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_runtime_permission);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //requestPermissions(new String[]{"android.permission.READ_PHONE_STATE"},100);
        SharedPreferences preferences = getSharedPreferences("test",Context.MODE_PRIVATE);
        preferences.edit().putString("a","100").apply();
    }

    public void getImei(View view) {
        String path = this.getFilesDir().getPath();
        String getCacheDir = this.getCacheDir().getPath();
        Log.d(TAG,"path="+path);
        Log.d(TAG,"getCacheDir="+getCacheDir);
        new Thread(new Runnable() {
            @Override
            public void run() {
                TelephonyManager telephonyManager =
                        (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
                String imei1 = telephonyManager.getDeviceId();
                Log.d(TAG,"imei1="+imei1);

            }
        }).start();

    }

    public void getLocation(View view){
        TelephonyManager telephonyManager =
                (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        CellLocation cellLocation = telephonyManager.getCellLocation();
    }


}
