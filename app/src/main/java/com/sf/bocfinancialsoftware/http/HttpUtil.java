package com.sf.bocfinancialsoftware.http;

import android.content.Context;
import android.os.Handler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * 网络请求
 * Created by sn on 2017/7/11.
 */

public class HttpUtil {

    /**
     * 请求网络 获取JSON报文
     *
     * @param context  上下文
     * @param strUrl   请求url
     * @param map      存放请求参数
     * @param listener 回调接口
     */
    public static void getNetworksJSonResponse(final Context context, final String strUrl, final HashMap<String, String> map, final HttpCallBackListener listener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                InputStream is = null;
                InputStreamReader isReader = null;
                BufferedReader bf = null;
                try {
                    URL url = new URL(strUrl);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("POST");
                    connection.setConnectTimeout(5000);
                    connection.setDoOutput(true);
                    connection.setDoInput(true);
                    connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                    connection.setUseCaches(false); //Post方式不能缓存,需手动设置为false
                    String param = ""; //请求参数
                    boolean isFirst = true;
                    if (map != null || map.size() > 0) {
                        for (Map.Entry<String, String> entry : map.entrySet()) {  //遍历map,拼接参数
                            if (entry.getKey() != null & entry.getValue() != null) {
                                if (isFirst) {
                                    param = entry.getKey() + "=" + URLEncoder.encode(entry.getValue(), "UTF-8");
                                    isFirst = false;
                                } else {
                                    param += "&" + entry.getKey() + "=" + URLEncoder.encode(entry.getValue(), "UTF-8");
                                }
                            }
                        }
                        OutputStream outputStream = connection.getOutputStream(); //获取输出流
                        outputStream.write(param.getBytes());
                    }
                    if (connection.getResponseCode() == connection.HTTP_OK) {
                        is = connection.getInputStream();
                        isReader = new InputStreamReader(is);
                        bf = new BufferedReader(isReader);
                        final StringBuilder sb = new StringBuilder();
                        String line = "";
                        while ((line = bf.readLine()) != null) {
                            sb.append(line + "\r\n");
                        }
                        if (listener != null) {
                            new Handler(context.getMainLooper()).post(new Runnable() {  //切换到主线程
                                @Override
                                public void run() {
                                    listener.onSuccess(sb.toString());   //回调onSuccess方法
                                }
                            });
                        }
                    } else {
                        if (listener != null) {
                            new Handler(context.getMainLooper()).post(new Runnable() {  //切换到主线程
                                @Override
                                public void run() {
                                    listener.onFailed("连接不成功"); //回调onError方法
                                }
                            });
                        }
                    }
                } catch (final MalformedURLException e) {
                    if (listener != null) {
                        new Handler(context.getMainLooper()).post(new Runnable() {  //切换到主线程
                            @Override
                            public void run() {
                                listener.onError(e); //回调onError方法
                            }
                        });
                    }
                } catch (IOException e) {
                    if (listener != null) {
                        new Handler(context.getMainLooper()).post(new Runnable() {  //切换到主线程
                            @Override
                            public void run() {
                                listener.onFailed("IO异常"); //回调onError方法
                            }
                        });
                    }
                } finally {
                    if (bf != null) {
                        try {
                            bf.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (isReader != null) {
                        try {
                            isReader.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (is != null) {
                        try {
                            is.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }).start();
    }

}
