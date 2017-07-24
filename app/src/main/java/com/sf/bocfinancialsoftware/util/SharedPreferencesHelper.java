package com.sf.bocfinancialsoftware.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.sf.bocfinancialsoftware.bean.UserBean;
import com.sf.bocfinancialsoftware.constant.ConstantConfig;

/**
 * 个人中心注册 登录信息
 * Created by Author: wangyongzhu on 2017/6/16.
 */

public class SharedPreferencesHelper {
    //保存用户数据表
    public void save(Context context, String userName, String passwd, boolean isLogin) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(ConstantConfig.SP_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.putString(ConstantConfig.USER_NAME, userName);
        editor.putString(ConstantConfig.PASSWORD, passwd);
        editor.putBoolean(ConstantConfig.IS_LOGIN, isLogin);
        editor.commit();
    }

    //读取用户数据表
    public UserBean read(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(ConstantConfig.SP_NAME, Context.MODE_PRIVATE);
        UserBean userBean = new UserBean(sharedPreferences.getString(ConstantConfig.USER_NAME, ""), sharedPreferences.getString(ConstantConfig.PASSWORD, ""));
        userBean.setLogin(sharedPreferences.getBoolean(ConstantConfig.IS_LOGIN, false));
        return userBean;
    }

}
