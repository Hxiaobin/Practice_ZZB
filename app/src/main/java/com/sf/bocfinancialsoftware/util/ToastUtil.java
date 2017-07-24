package com.sf.bocfinancialsoftware.util;

import android.content.Context;
import android.widget.Toast;

/**
 * toast Util
 * Created by Author: wangyongzhu on 2017/7/10.
 */

public class ToastUtil {
    private static Toast toast;

    public static void showToast(Context context, String msg) {
        if (toast == null) {
            toast = Toast.makeText(context, msg, Toast.LENGTH_LONG);
        } else {
            toast.setText(msg);
        }
        toast.show();
    }
}
