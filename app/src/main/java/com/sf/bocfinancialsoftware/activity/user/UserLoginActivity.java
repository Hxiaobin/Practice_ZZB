package com.sf.bocfinancialsoftware.activity.user;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.sf.bocfinancialsoftware.constant.ConstantConfig;
import com.sf.bocfinancialsoftware.widget.SetHintTextSize;
import com.sf.bocfinancialsoftware.util.SharedPreferencesHelper;
import com.sf.bocfinancialsoftware.R;
import com.sf.bocfinancialsoftware.bean.UserBean;

import org.greenrobot.eventbus.EventBus;

import java.util.Map;

public class UserLoginActivity extends AppCompatActivity implements View.OnClickListener {

    private Context mContext;
    private ImageView ivRemove;
    private Button btnRegistered, btnLogin;
    private EditText etLoginUser, etLoginKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);
        mContext = this;
        initViews();
        initLister();
    }

    private void initLister() {
        ivRemove.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
        btnRegistered.setOnClickListener(this);
        etLoginUser.setOnClickListener(this);
        etLoginKey.setOnClickListener(this);
    }

    private void initViews() {
        ivRemove = (ImageView) findViewById(R.id.ivRemove);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnRegistered = (Button) findViewById(R.id.btnRegistered);
        etLoginUser = (EditText) findViewById(R.id.etLoginUser);
        etLoginKey = (EditText) findViewById(R.id.etLoginKey);
        //设置隐藏字体大小
        SetHintTextSize.setHintTextSize(etLoginUser,getString(R.string.et_login_user),12);
        SetHintTextSize.setHintTextSize(etLoginKey,getString(R.string.et_login_key),12);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivRemove:
                finish();
                break;
            case R.id.btnLogin:
                String loginName = etLoginUser.getText().toString().trim();
                String loginPasswd = etLoginKey.getText().toString().trim();
                SharedPreferencesHelper sharedPreferencesHelper2 = new SharedPreferencesHelper();
                Map<String, String> data = sharedPreferencesHelper2.read2(this);
                String userName = data.get(ConstantConfig.USER_NAME);
                String userPasswd = data.get(ConstantConfig.PASSWORD);
                if (loginName.equals("") || loginPasswd.equals("")) {
                    btnLogin.setEnabled(false);
                    break;
                }
                if (loginName.equals(userName) && loginPasswd.equals(userPasswd)) {
                    SharedPreferencesHelper sharedPreferencesHelper1 = new SharedPreferencesHelper();
                    sharedPreferencesHelper1.save1(this,userName,userPasswd);
                    EventBus.getDefault().post(new UserBean(loginName, loginPasswd));
                    finish();
                } else {
                    Toast.makeText(mContext, R.string.toast_user_error, Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.btnRegistered:
                startActivityForResult(new Intent(UserLoginActivity.this, RegisteredActivity.class), 1);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                finish();
            }
        }
    }
}
