package com.sf.bocfinancialsoftware.http;

/**
 * 网络请求回调接口
 * Created by sn on 2017/7/17.
 */

public interface HttpCallBackListener {

    void onSuccess(String response); //请求成功

    void onFailed(String msg);  //请求失败

    void onError(Exception e);  //请求异常
}
