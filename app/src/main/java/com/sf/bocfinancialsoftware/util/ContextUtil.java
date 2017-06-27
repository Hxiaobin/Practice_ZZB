package com.sf.bocfinancialsoftware.util;

import android.app.Application;

/***
 * 全局上下文
 *  sn
 */
public class ContextUtil extends Application {

    private static ContextUtil instance;

    public  boolean isFinishGoodsActivity;

    public static void setInstance(ContextUtil instance) {
        ContextUtil.instance = instance;
    }

    public boolean isFinishGoodsActivity() {
        return isFinishGoodsActivity;
    }

    public void setFinishGoodsActivity(boolean finishGoodsActivity) {
        isFinishGoodsActivity = finishGoodsActivity;
    }

    public static ContextUtil getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        isFinishGoodsActivity=false;
    }

}
