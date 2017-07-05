package com.sf.bocfinancialsoftware.base;

import android.app.Application;

/**
 * 全局上下文
 * Created by sn on 2017/7/4.
 */

public class BaseApplication extends Application {

    private static BaseApplication instance;

    public boolean isFinishGoodsActivity;

    public static void setInstance(BaseApplication instance) {
        BaseApplication.instance = instance;
    }

    public boolean isFinishGoodsActivity() {
        return isFinishGoodsActivity;
    }

    public void setFinishGoodsActivity(boolean finishGoodsActivity) {
        isFinishGoodsActivity = finishGoodsActivity;
    }

    public static BaseApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        isFinishGoodsActivity = false;
    }

}
