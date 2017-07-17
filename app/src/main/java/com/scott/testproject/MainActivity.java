package com.scott.testproject;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RemoteViews;

import com.scott.testproject.brokenkeyderivation.BrokenKeyDerivationActivity;
import com.scott.testproject.emoji.EmojiActivity;
import com.scott.testproject.encryptionAlgorithm.EncrypetionTestActivity;
import com.scott.testproject.eventbus.EventBusActivity;
import com.scott.testproject.jobscheduler.JobSchedulerActivity;
import com.scott.testproject.keystore.KeystoreActivity;
import com.scott.testproject.oom.OOMTestActivity;
import com.scott.testproject.webviewLoophole.WebViewActivity;

import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.Args;
import org.apache.http.util.ByteArrayBuffer;
import org.apache.http.util.EntityUtils;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;

public class MainActivity extends Activity implements View.OnClickListener{
    private static final String TAG = "testHttps";
    private NotificationManager mNM;
    private int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNM = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        findViewById(R.id.test_jobscheduler).setOnClickListener(this);
        findViewById(R.id.test_emoji).setOnClickListener(this);
    }

    public void startKeystoreActivity(View view){
        startActivity(new Intent(this, KeystoreActivity.class));
    }

    public void startWebViewActivity(View view){
        startActivity(new Intent(this, WebViewActivity.class));
    }

    public void startEevntBusActivity(View view){
        startActivity(new Intent(this, EventBusActivity.class));
    }

    public void startOOMActivity(View view){
        startActivity(new Intent(this, OOMTestActivity.class));
    }

    public void startEncryptActivity(View view){
        startActivity(new Intent(this, BrokenKeyDerivationActivity.class));
    }

    private void showNotification() {
//        Bitmap bigBitmap = BitmapFactory.decodeResource(getResources(),R.mipmap.icon);
//        PendingIntent pendingIntent = PendingIntent.getActivity(this,
//                0,new Intent(this,SecondActivity.class),0);
        RemoteViews remoteViews = new RemoteViews(getPackageName(),
                R.layout.notification_layout_base);
        remoteViews.setImageViewResource(R.id.icon, R.mipmap.push);
        remoteViews.setTextViewText(R.id.app_name_text,
                getResources().getString(R.string.app_name));
        remoteViews.setTextViewText(R.id.title, "标题标题标题标题标题标题标题标题标题标题标题标题标题标题");
        remoteViews.setTextViewText(R.id.content, "内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容");

        Notification notification = new Notification.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setCustomContentView(remoteViews)
                .build();
        mNM.notify(index++, notification);
    }

    private void testHttps() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String urlStr = "https://t-push.gionee.com/nds/config.do";
                    //URL url = new URL(urlStr);
                    //HttpsURLConnection urlConnection = (HttpsURLConnection)url.openConnection();
                    //InputStream inputStream = getHttpsConnection(urlStr).getInputStream();
                    //InputStream inputStream = urlConnection.getInputStream();
                    InputStream inputStream = getHttpsConnection(urlStr).getInputStream();
                    String result = readStream(inputStream, 500);
                    Log.d(TAG, "result:" + result);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.test_jobscheduler:
                startActivity(new Intent(this, JobSchedulerActivity.class));
                break;
            case R.id.test_emoji:
                startActivity(new Intent(this, EmojiActivity.class));
                break;
        }
    }


    public class MyHostnameVerifier implements HostnameVerifier {

        @Override
        public boolean verify(String hostname, SSLSession session) {
            if ("t-id.gionee.com".equals(hostname)) {
                return true;
            }
            if ("id.gionee.com".equals(hostname)) {
                return true;
            }
            if ("t-push.gionee.com".equals(hostname)) {
                return true;
            }
            if ("id.amigo.cn".equals(hostname)) {
                return true;
            }
            if ("t-id.amigo.cn".equals(hostname)) {
                return true;
            }
            if ("t-stat.gionee.com".equals(hostname)) {
                return true;
            }
            if ("stat.gionee.com".equals(hostname)) {
                return true;
            }
            if ("t-music.gionee.com".equals(hostname)) {
                return true;
            }
            return false;
        }
    }

    private String readStream(InputStream stream, int maxLength) throws IOException {
        String result = null;
        // Read InputStream using the UTF-8 charset.
        InputStreamReader reader = new InputStreamReader(stream, "UTF-8");
        // Create temporary buffer to hold Stream data with specified max length.
        char[] buffer = new char[maxLength];
        // Populate temporary buffer with Stream data.
        int numChars = 0;
        int readSize = 0;
        while (numChars < maxLength && readSize != -1) {
            numChars += readSize;
            int pct = (100 * numChars) / maxLength;
            //publishProgress(DownloadCallback.Progress.PROCESS_INPUT_STREAM_IN_PROGRESS, pct);
            readSize = reader.read(buffer, numChars, buffer.length - numChars);
        }
        if (numChars != -1) {
            // The stream was not empty.
            // Create String that is actual length of response body if actual length was less than
            // max length.
            numChars = Math.min(numChars, maxLength);
            result = new String(buffer, 0, numChars);
        }
        return result;
    }

    private void toActivity() {
        Intent intent = new Intent();
        intent.setPackage("com.scott.testproject");
        intent.setClassName(getPackageName(), "com.scott.testproject.SecondActivity");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }


    public static float getImageFactor(Resources r) {
        DisplayMetrics metrics = r.getDisplayMetrics();
        float multiplier = metrics.density / 3f;
        return multiplier;
    }

//    private Notification.BigPictureStyle makeBigPictureStyle() {
//        NotificationRichMedia rich = data.getRichMedia();
//        Bitmap bitmap = getMediaBitmap(rich.getImages()[0].getPath(), mBigPictureWidth,
//                mBigPictureHeight);
//        Notification.BigPictureStyle style = new Notification.BigPictureStyle();
//        style.bigPicture(bitmap);
//        style.setBigContentTitle(getText(rich, 0));
//        style.setSummaryText(getText(rich, 1));
//        return style;
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void Test2() throws Exception {
        String urlStr = "https://t-push.gionee.com/nds/config.do";
        HttpGet httpget = null;
        CloseableHttpResponse response = null;
        CloseableHttpClient httpClient = null;
        try {
            //
            SSLContextBuilder builder = new SSLContextBuilder();
            builder.loadTrustMaterial(null, new TrustSelfSignedStrategy());
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(builder.build(),
                    new MyHostnameVerifier());
            httpClient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
            RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(
                    1 * 1000).setConnectTimeout(1 * 1000).build();//
            httpget = new HttpGet(urlStr);
            httpget.setConfig(requestConfig);
            response = httpClient.execute(httpget);
            HttpEntity entity = response.getEntity();
            //
            if (entity != null && response.getStatusLine().getStatusCode() == 200) {
                String res = EntityUtils.toString(entity);
                Log.d(TAG, "res:" + res);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            httpClient.close();
            response.close();
        }
    }


    public void getNetworkNds() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String urlStr = "https://t-push.gionee.com/nds/config.do";
                    Log.d(TAG, "pushNdsApi: " + urlStr);
                    URLConnection urlConnection = getHttpsConnection(urlStr);
                    byte[] data = toByteArray(urlConnection);
                    String result = new String(data);
                    Log.d(TAG, "result: " + result);
                } catch (Exception e) {
                    e.printStackTrace();
                }finally {

                }
            }
        }).start();
    }

    public static byte[] toByteArray(URLConnection urlConnection) throws Exception {
        if(urlConnection == null || urlConnection.getInputStream() == null) {
            return null;
        }
        InputStream instream = null;
        try {
            instream = urlConnection.getInputStream();
            int length = urlConnection.getContentLength();
            Log.d(TAG,"length:"+length);
            Args.check(length <= 2147483647L, "HTTP entity too large to be buffered in memory");
            BufferedInputStream bis = new BufferedInputStream(instream);
            ByteArrayBuffer baf = new ByteArrayBuffer(length);
            int read;
            int bufSize = 204;
            byte[] buffer = new byte[bufSize];
            while(true){
                read = bis.read(buffer);
                if(read==-1){
                    break;
                }
                baf.append(buffer, 0, read);
            }
            return baf.toByteArray();
        }catch (Exception e){
            e.printStackTrace();
        }finally{
            if (instream != null){
                instream.close();
            }
        }
        return null;
    }

    private URLConnection getHttpsConnection(String url){
        try {
            URLConnection connection;
            SSLContextBuilder builder = new SSLContextBuilder();
            builder.loadTrustMaterial(null, new TrustSelfSignedStrategy());
            SSLContext sc = builder.build();
            SSLSocketFactory ssf = sc.getSocketFactory();
            URL myUrl = new URL(url);
            connection = new URL(myUrl.getProtocol(), myUrl.getHost(), 443, myUrl.getFile())
                    .openConnection();
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            ((HttpsURLConnection) connection).setSSLSocketFactory(ssf);
            HttpsURLConnection.setDefaultHostnameVerifier(new MyHostnameVerifier());
            return connection;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
