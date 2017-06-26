package com.sf.bocfinancialsoftware.activity.user;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sf.bocfinancialsoftware.util.SharedPreferencesHelper;
import com.sf.bocfinancialsoftware.R;
import com.sf.bocfinancialsoftware.bean.UserBean;
import com.sf.bocfinancialsoftware.constant.ConstantConfig;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Map;

public class UserActivity extends AppCompatActivity implements View.OnClickListener {

    private Context mContext;
    private ImageView ivBack;
    private ImageView ivUserImage; //用户头像
    private Button btnExit;
    private TextView tvUserName; //用户名
    private RelativeLayout rlSuggestion, rlUpdate, rlAbout;
    private SharedPreferencesHelper sharedPreferencesHelper;
    private String isLogin; //判断用户是否登录
    private String userName;
    private String userPasswd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        mContext = this;
        initView();
        initDate();
        initLister();
    }

    @Override
    protected void onStart() {
        super.onStart();
        sharedPreferencesHelper = new SharedPreferencesHelper();
        Map<String, String> data = sharedPreferencesHelper.read1(this);
        userName = data.get(ConstantConfig.USER_NAME);
        userPasswd = data.get(ConstantConfig.PASSWORD);
        tvUserName.setText(userName);
        if (tvUserName.getText().toString().equals("")) {
            btnExit.setVisibility(View.INVISIBLE);
        } else {
            btnExit.setVisibility(View.VISIBLE);
        }
    }

    private void initView() {
        TextView tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvTitle.setText(R.string.tv_user_center);
        tvUserName = (TextView) findViewById(R.id.tvUserName);
        ivBack = (ImageView) findViewById(R.id.ivBack);
        ivUserImage = (ImageView) findViewById(R.id.ivUserImage);
        btnExit = (Button) findViewById(R.id.btnExit);
        rlSuggestion = (RelativeLayout) findViewById(R.id.rlSuggestion);
        rlUpdate = (RelativeLayout) findViewById(R.id.rlUpdate);
        rlAbout = (RelativeLayout) findViewById(R.id.rlAbout);
    }

    private void initDate() {
        EventBus.getDefault().register(this);
    }

    private void initLister() {
        ivBack.setOnClickListener(this);
        ivUserImage.setOnClickListener(this);
        btnExit.setOnClickListener(this);
        rlSuggestion.setOnClickListener(this);
        rlUpdate.setOnClickListener(this);
        rlAbout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivBack: //返回
                finish();
                break;
            case R.id.ivUserImage: //点击登录
                login();
                break;
            case R.id.rlSuggestion: //意见反馈
                //EventBus.getDefault().post(new UserBean(userName, userPasswd));
                startActivity(new Intent(UserActivity.this, SuggestionActivity.class));
                break;
            case R.id.rlUpdate: //检查更新版本
                Toast.makeText(mContext, R.string.toast_check_update, Toast.LENGTH_LONG).show();
                break;
            case R.id.rlAbout: //关于
                startActivity(new Intent(UserActivity.this, AboutActivity.class));
                break;
            case R.id.btnExit: //退出登录
                exit();
                break;
        }
    }

    //登录
    private void login() {
        if (tvUserName.getText().toString().equals("")) {
            startActivity(new Intent(UserActivity.this, UserLoginActivity.class));
        } else {
            ivUserImage.setEnabled(false);
        }
    }

    private void exit() {
        sharedPreferencesHelper.clear(this);
        tvUserName.setText("");
        Toast.makeText(mContext, R.string.toast_exit, Toast.LENGTH_LONG).show();
        btnExit.setVisibility(View.INVISIBLE);
        ivUserImage.setEnabled(true);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getUserInfo(UserBean user) {
        userName = user.getUserName();
        userPasswd = user.getLoginPassword();
        tvUserName.setText(userName);
        btnExit.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
