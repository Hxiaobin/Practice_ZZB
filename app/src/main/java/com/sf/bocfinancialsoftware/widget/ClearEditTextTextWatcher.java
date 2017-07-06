package com.sf.bocfinancialsoftware.widget;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

/**
 * 自定义文本编辑框监听器
 * Created by sn on 2017/7/4.
 */

public class ClearEditTextTextWatcher implements TextWatcher {

    private Context context;  // 上下文
    private EditText editText; //文本输入框
    private ImageView ivEditTextClear;  //清除文本图标

    public ClearEditTextTextWatcher(Context context, EditText editText, ImageView ivEditTextClear) {
        this.context = context;
        this.editText = editText;
        this.ivEditTextClear = ivEditTextClear;
    }

    /**
     * 输入文字之前的状态
     *
     * @param s
     * @param start
     * @param count
     * @param after
     */
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        if (editText.getText().toString().length() <= 0) { //文本框无输入
            ivEditTextClear.setVisibility(View.GONE);
        } else {
            ivEditTextClear.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 文本输入中的状态
     *
     * @param s
     * @param start
     * @param before
     * @param count
     */
    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        ivEditTextClear.setVisibility(View.VISIBLE);
    }

    /**
     * 输入文字之后的状态
     *
     * @param s
     */
    @Override
    public void afterTextChanged(Editable s) {
        if (editText.getText().toString().length() <= 0) { //文本框无输入
            ivEditTextClear.setVisibility(View.GONE);
        } else {
            ivEditTextClear.setVisibility(View.VISIBLE);
        }
    }
}
