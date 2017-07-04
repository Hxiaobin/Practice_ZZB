package com.sf.bocfinancialsoftware.activity.user;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sf.bocfinancialsoftware.util.SetHintTextSize;
import com.sf.bocfinancialsoftware.util.SharedPreferencesHelper;
import com.sf.bocfinancialsoftware.R;
import com.sf.bocfinancialsoftware.bean.UserBean;

import org.greenrobot.eventbus.EventBus;

public class RegisteredActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText etRegisteredUserName, etRegisteredKey;
    private Button btnRegistered;
    private ImageView ivBack;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registered);
        mContext = getApplicationContext();
        initView();
        initListener();
    }

    private void initListener() {
        ivBack.setOnClickListener(this);
        btnRegistered.setOnClickListener(this);
    }

    private void initView() {
        TextView tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvTitle.setText(R.string.btn_registered);
        btnRegistered = (Button) findViewById(R.id.btnRegistered);
        etRegisteredUserName = (EditText) findViewById(R.id.etRegisteredUserName);
        etRegisteredKey = (EditText) findViewById(R.id.etRegisteredKey);
        ivBack = (ImageView) findViewById(R.id.ivBack);
        //设置隐藏字体大小
        SetHintTextSize.setHintTextSize(etRegisteredUserName,getString(R.string.et_registered_user_name),12);
        SetHintTextSize.setHintTextSize(etRegisteredKey,getString(R.string.et_registered_key),12);
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
            Toast.makeText(mContext, R.string.et_registered_user_name, Toast.LENGTH_LONG).show();
            return;
        }
        if (len < 6 || len > 15) {
            Toast.makeText(mContext, R.string.et_registered_key, Toast.LENGTH_LONG).show();
            return;
        }
        UserBean userBean = new UserBean(userName,password);
        SharedPreferencesHelper sharedPreferencesHelper1 = new SharedPreferencesHelper();
        sharedPreferencesHelper1.save1(mContext,userName,password);
        SharedPreferencesHelper sharedPreferencesHelper2 = new SharedPreferencesHelper();
        sharedPreferencesHelper2.save2(mContext,userName,password);
        EventBus.getDefault().post(userBean);
        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        finish();
    }
}
