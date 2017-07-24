package com.sf.bocfinancialsoftware.util;

import android.content.Context;
import android.os.Handler;

import com.socks.library.KLog;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * 获取网络数据
 * Created by Author: wangyongzhu on 2017/7/12.
 */

public class NetWorkUtils {
    public static void doPost(final Context context, final String url, final RequestCallBack myListener) {
        doPost(context, url, null, myListener);
    }

    public static void doPost(final Context context, final String url, final HashMap<String, String> map, final RequestCallBack myListener) {
        new Thread() {
            @Override
            public void run() {
                InputStream inputStream = null;
                BufferedInputStream in = null;
                ByteArrayOutputStream baos = null;
                Handler handler = new Handler(context.getMainLooper());
                try {
                    HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
                    //表示设置请求体的类型是文本类型
                    connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                    //设置连接超时时间
                    connection.setConnectTimeout(5000);
                    connection.setRequestMethod("POST");
                    connection.setDoOutput(true);
                    //"id=123 &name=555 &mm=888"
                    StringBuffer params = new StringBuffer();
                    if (map != null) {
                        boolean isFirst = true;
                        for (Map.Entry<String, String> entry : map.entrySet()) {
                            if (isFirst) {
                                params.append(entry.getKey()).append("=").append(entry.getValue() == null ? "" : entry.getValue());
                                isFirst = false;
                            } else {
                                params.append("&").append(entry.getKey()).append("=").append(entry.getValue() == null ? "" : entry.getValue());
                            }
                        }
                        connection.setRequestProperty("Content-Length", params.toString().getBytes().length + "");
                        connection.getOutputStream().write(params.toString().getBytes());
                    }

                    //获得结果码
                    int responseCode = connection.getResponseCode();
                    if (responseCode == 200) {
                        KLog.e("200");
                        //请求成功
                        inputStream = connection.getInputStream();
                        in = new BufferedInputStream(inputStream);
                        baos = new ByteArrayOutputStream();
                        byte[] bytes = new byte[1024];
                        int len = -1;
                        while ((len = in.read(bytes)) != -1) {
                            baos.write(bytes, 0, len);
                        }
                        final String result = new String(baos.toByteArray());
                        KLog.e(result);
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                myListener.onSuccess(result);
                            }
                        });
                    } else {
                        KLog.e("not 200");
                        //请求失败
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                myListener.onError();
                            }
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    KLog.e(e.getMessage());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            myListener.onError();
                        }
                    });
                } finally {
                    try {
                        if (inputStream != null) {
                            inputStream.close();
                        }
                        if (in != null) {
                            in.close();
                        }
                        if (baos != null) {
                            baos.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();

    }

    public interface RequestCallBack {
        void onSuccess(String json);

        void onError();
    }
}
