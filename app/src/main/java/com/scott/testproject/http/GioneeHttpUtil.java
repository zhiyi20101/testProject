package com.scott.testproject.http;

import android.text.TextUtils;

import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.Args;
import org.apache.http.util.ByteArrayBuffer;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;

/**
 * Created by zouzhiyi on 22/05/17.
 */

public class GioneeHttpUtil {
    private static final String TAG = GioneeHttpUtil.class.getSimpleName();
    private static final int NETWORK_TIMEOUT = 5 * 1000;
    private static final String PUSH_HOSTNAME_PRODUCT = "push.gionee.com";
    private static final String PUSH_HOSTNAME_TEST = "t-push.gionee.com";
    private static final int HTTP_PORT = 80;
    private static final int HTTPS_PORT = 443;

    private GioneeHttpUtil(){}

    private static class SingletonHolder{
        private static final GioneeHttpUtil INSTANCE = new GioneeHttpUtil();
    }

    public static final GioneeHttpUtil getInstance(){
        return SingletonHolder.INSTANCE;
    }

    public String execute(String url, Map<String, String> header) {
        if (TextUtils.isEmpty(url)) {
            return null;
        }
        URLConnection urlConnection = getURLConnection(url, header);
        byte[] data = toByteArray(urlConnection);
        if (data != null && data.length > 0) {
            return new String(data);
        } else {
            return null;
        }
    }

    public byte[] request(String url, Map<String, String> header) {
        if (TextUtils.isEmpty(url)) {
            return null;
        }
        URLConnection urlConnection = getURLConnection(url, header);
        return toByteArray(urlConnection);
    }

    private byte[] toByteArray(URLConnection urlConnection) {
        if (urlConnection == null) return null;
        InputStream instream = null;
        try {
            instream = urlConnection.getInputStream();
            if (instream == null) return null;
            int length = urlConnection.getContentLength();
//            LogUtils.d(TAG, "length:" + length);
            Args.check(length <= Integer.MAX_VALUE,
                    "HTTP entity too large to be buffered in memory");
            BufferedInputStream bis = new BufferedInputStream(instream);
            ByteArrayBuffer baf = new ByteArrayBuffer(length);
            int read;
            int bufSize = 2014;
            byte[] buffer = new byte[bufSize];
            while (true) {
                read = bis.read(buffer);
                if (read == -1) {
                    break;
                }
                baf.append(buffer, 0, read);
            }
            return baf.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (instream != null) {
                try {
                    instream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    private URLConnection getURLConnection(String url, Map<String, String> header) {
        try {
            URL myUrl = new URL(url);
            String protocol = myUrl.getProtocol();
            int port = HTTP_PORT;
            if (isHttp(protocol)) port = HTTP_PORT;
            if (isHttps(protocol)) port = HTTPS_PORT;
            URLConnection connection = new URL(protocol, myUrl.getHost(), port,
                    myUrl.getFile())
                    .openConnection();
            connection.setConnectTimeout(NETWORK_TIMEOUT);
            connection.setReadTimeout(NETWORK_TIMEOUT);
            if (header != null && header.size() > 0) {
                for (Map.Entry<String, String> entry : header.entrySet()) {
                    connection.setRequestProperty(entry.getKey(), entry.getValue());
                }
            }
            if (isHttps(protocol)) {
                SSLContextBuilder builder = new SSLContextBuilder();
                builder.loadTrustMaterial(null, new TrustSelfSignedStrategy());
                SSLContext sc = builder.build();
                SSLSocketFactory ssf = sc.getSocketFactory();
                ((HttpsURLConnection) connection).setSSLSocketFactory(ssf);
            }
            HttpsURLConnection.setDefaultHostnameVerifier(new PushHostnameVerifier());
            return connection;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private boolean isHttps(String protocol) {
        return "https".equals(protocol);
    }

    private boolean isHttp(String protocol) {
        return "http".equals(protocol);
    }

    private class PushHostnameVerifier implements HostnameVerifier {

        @Override
        public boolean verify(String hostname, SSLSession session) {
            if (PUSH_HOSTNAME_TEST.equals(hostname)) {
                return true;
            }
            if (PUSH_HOSTNAME_PRODUCT.equals(hostname)) {
                return true;
            }
            return false;
        }
    }
}
