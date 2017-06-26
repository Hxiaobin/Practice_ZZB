package com.sf.bocfinancialsoftware.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.sf.bocfinancialsoftware.constant.ConstantConfig;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Author: wangyongzhu on 2017/6/16.
 */

public class SharedPreferencesHelper {
    //保存数据 表1
    public void save1(Context context, String userName, String passwd) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(ConstantConfig.SP_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(ConstantConfig.USER_NAME, userName);
        editor.putString(ConstantConfig.PASSWORD, passwd);
        editor.commit();
    }

    //保存数据 表2
    public void save2(Context context, String userName, String passwd) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(ConstantConfig.SP_NAME2, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(ConstantConfig.USER_NAME, userName);
        editor.putString(ConstantConfig.PASSWORD, passwd);
        editor.commit();
    }

    //读取数据 表1
    public Map<String, String> read1(Context context) {
        Map<String, String> data = new HashMap<>();
        SharedPreferences sharedPreferences = context.getSharedPreferences(ConstantConfig.SP_NAME, Context.MODE_PRIVATE);
        data.put(ConstantConfig.USER_NAME, sharedPreferences.getString(ConstantConfig.USER_NAME, ""));
        data.put(ConstantConfig.PASSWORD, sharedPreferences.getString(ConstantConfig.PASSWORD, ""));
        return data;
    }

    //读取数据 表2
    public Map<String, String> read2(Context context) {
        Map<String, String> data = new HashMap<>();
        SharedPreferences sharedPreferences = context.getSharedPreferences(ConstantConfig.SP_NAME2, Context.MODE_PRIVATE);
        data.put(ConstantConfig.USER_NAME, sharedPreferences.getString(ConstantConfig.USER_NAME, ""));
        data.put(ConstantConfig.PASSWORD, sharedPreferences.getString(ConstantConfig.PASSWORD, ""));
        return data;
    }

    //清除数据
    public void clear(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(ConstantConfig.SP_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();
    }
}
