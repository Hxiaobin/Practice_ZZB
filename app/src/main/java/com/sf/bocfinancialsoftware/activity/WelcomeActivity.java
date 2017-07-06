package com.sf.bocfinancialsoftware.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.WindowManager;

import com.sf.bocfinancialsoftware.R;
import com.sf.bocfinancialsoftware.base.BaseActivity;

import static com.sf.bocfinancialsoftware.constant.ConstantConfig.FILE_FIRST_IN;
import static com.sf.bocfinancialsoftware.constant.ConstantConfig.IS_FIRST_IN;


/**
 * 欢迎页
 * Created by sn on 2017/6/28.
 */
public class WelcomeActivity extends BaseActivity {

    private boolean isFirst = false;  //是否第一次进入app
    private SharedPreferences sharedPreferences;  //保存进入状态到本地

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0) {
                isFirst = sharedPreferences.getBoolean(IS_FIRST_IN, true);
                if (isFirst) {  //如果是第一次进入app，跳转到引导页
                    if (!WelcomeActivity.this.isFinishing()) {
                        overridePendingTransition(R.anim.zoomin, R.anim.zoomout);
                        startActivity(new Intent(WelcomeActivity.this, GuideActivity.class));
                        finish();
                    }
                } else { //不是第一次进入App,直接进入主页
                    if (!WelcomeActivity.this.isFinishing()) {
                        overridePendingTransition(R.anim.zoomin, R.anim.zoomout);
                        startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
                        finish();
                    }
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        initView();
        initData();
        initListener();
    }

    @Override
    protected void initView() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }

    @Override
    protected void initData() {
        sharedPreferences = getSharedPreferences(FILE_FIRST_IN, Context.MODE_PRIVATE);
        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    sleep(1000 * 2); //睡眠1秒
                    Message msg = handler.obtainMessage();
                    msg.what = 0;
                    handler.sendMessage(msg);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    @Override
    protected void initListener() {

    }

    /**
     * 手指点击屏幕监听事件
     *
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            isFirst = sharedPreferences.getBoolean(IS_FIRST_IN, true);
            if (isFirst) {  //如果是第一次进入app，跳转到引导页
                if (!WelcomeActivity.this.isFinishing()) {
                    overridePendingTransition(R.anim.zoomin, R.anim.zoomout);
                    startActivity(new Intent(WelcomeActivity.this, GuideActivity.class));
                    finish();
                }
            } else {//不是第一次进入app，跳转到引导页
                if (!WelcomeActivity.this.isFinishing()) {
                    overridePendingTransition(R.anim.zoomin, R.anim.zoomout);
                    startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
                    finish();
                }
            }
            return true;
        }
        return super.onTouchEvent(event);
    }
}
