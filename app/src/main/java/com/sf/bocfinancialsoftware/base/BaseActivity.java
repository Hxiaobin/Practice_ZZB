package com.sf.bocfinancialsoftware.base;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Activity基类
 * Created by sn on 2017/6/7
 */

public abstract class BaseActivity extends AppCompatActivity {

    protected Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        //设置支持屏幕的方向
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    protected abstract void initView();         //初始化View

    protected abstract void initData();        //初始化数据

    protected abstract void initListener();    //初始化事件

}
