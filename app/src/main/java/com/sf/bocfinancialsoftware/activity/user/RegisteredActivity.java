package com.sf.bocfinancialsoftware.activity.user;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.sf.bocfinancialsoftware.R;
import com.sf.bocfinancialsoftware.base.BaseActivity;
import com.sf.bocfinancialsoftware.util.SharedPreferencesHelper;
import com.sf.bocfinancialsoftware.util.ToastUtil;
import com.sf.bocfinancialsoftware.widget.SetHintTextSize;

public class RegisteredActivity extends BaseActivity implements View.OnClickListener {

    private EditText etRegisteredUserName, etRegisteredKey;
    private Button btnRegistered;
    private ImageView ivBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registered);
        initView();
        initData();
        initListener();
    }

    @Override
    protected void initView() {
        TextView tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvTitle.setText(R.string.btn_registered);
        btnRegistered = (Button) findViewById(R.id.btnRegistered);
        etRegisteredUserName = (EditText) findViewById(R.id.etRegisteredUserName);
        etRegisteredKey = (EditText) findViewById(R.id.etRegisteredKey);
        ivBack = (ImageView) findViewById(R.id.ivBack);
    }

    @Override
    protected void initData() {
        //设置隐藏字体大小
        SetHintTextSize.setHintTextSize(etRegisteredUserName, getString(R.string.et_registered_user_name), 12);
        SetHintTextSize.setHintTextSize(etRegisteredKey, getString(R.string.et_key), 12);

    }

    @Override
    protected void initListener() {
        ivBack.setOnClickListener(this);
        btnRegistered.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivBack:
                finish();
                break;
            case R.id.btnRegistered:
                registered();
                break;
        }
    }

    /**
     * 注册
     */
    private void registered() {
        String userName = etRegisteredUserName.getText().toString();
        String password = etRegisteredKey.getText().toString();
        int len = password.length();
        if (TextUtils.isEmpty(userName)) {
            ToastUtil.showToast(mContext, getString(R.string.et_registered_user_name));
            return;
        }
        if (len < 6 || len > 15) {
            ToastUtil.showToast(mContext, getString(R.string.et_key));
            return;
        }
        SharedPreferencesHelper sharedPreferencesHelper = new SharedPreferencesHelper();
        sharedPreferencesHelper.save(mContext, userName, password, false);
        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        finish();
    }
}
