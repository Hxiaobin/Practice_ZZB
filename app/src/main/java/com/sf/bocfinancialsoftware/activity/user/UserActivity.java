package com.sf.bocfinancialsoftware.activity.user;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.sf.bocfinancialsoftware.R;
import com.sf.bocfinancialsoftware.base.BaseActivity;
import com.sf.bocfinancialsoftware.bean.UserBean;
import com.sf.bocfinancialsoftware.bean.user.CheckVersion;
import com.sf.bocfinancialsoftware.constant.URLConfig;
import com.sf.bocfinancialsoftware.util.NetWorkUtils;
import com.sf.bocfinancialsoftware.util.SharedPreferencesHelper;
import com.sf.bocfinancialsoftware.util.ToastUtil;
import com.sf.bocfinancialsoftware.widget.SetHintTextSize;

import java.util.HashMap;

public class UserActivity extends BaseActivity implements View.OnClickListener {

    private View dialogLoginView;//登录对话框
    private ImageView ivBack;//返回按钮
    private ImageView ivUserImage; //用户头像
    private ImageView ivRemove;//取消
    private Button btnExit;//退出登录
    private Button btnRegistered;//注册
    private Button btnLogin;//登录
    private EditText etLoginUser;//输入用户名
    private EditText etLoginKey;//输入密码
    private TextView tvUserName; //用户名
    private RelativeLayout llSuggestion, llUpdate, llAbout;
    private SharedPreferencesHelper sharedPreferencesHelper;
    private String userName;//用户名
    private String userPasswd;//密码
    private Dialog dialog;//对话框
    private UserBean mUserBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        initView();
        initData();
        initListener();
    }

    @Override
    protected void initView() {
        TextView tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvTitle.setText(R.string.tv_user_center);
        tvUserName = (TextView) findViewById(R.id.tvUserName);
        llSuggestion = (RelativeLayout) findViewById(R.id.llSuggestion);
        llUpdate = (RelativeLayout) findViewById(R.id.llUpdate);
        llAbout = (RelativeLayout) findViewById(R.id.llAbout);
        ivBack = (ImageView) findViewById(R.id.ivBack);
        ivUserImage = (ImageView) findViewById(R.id.ivUserImage);
        btnExit = (Button) findViewById(R.id.btnExit);
    }

    @Override
    protected void initData() {
        //获取用户
        getUser();
    }

    @Override
    protected void initListener() {
        ivBack.setOnClickListener(this);
        ivUserImage.setOnClickListener(this);
        btnExit.setOnClickListener(this);
        llSuggestion.setOnClickListener(this);
        llUpdate.setOnClickListener(this);
        llAbout.setOnClickListener(this);
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
            case R.id.llSuggestion: //意见反馈
                startActivity(new Intent(UserActivity.this, SuggestionActivity.class));
                break;
            case R.id.llUpdate: //检查更新版本
                getCheckVersionData();
                break;
            case R.id.llAbout: //关于
                startActivity(new Intent(UserActivity.this, AboutActivity.class));
                break;
            case R.id.btnExit: //退出登录
                exit();
                break;
            case R.id.btnRegistered: //注册
                startActivityForResult(new Intent(UserActivity.this, RegisteredActivity.class), 1);
                break;
            case R.id.btnLogin: //登录
                String loginName = etLoginUser.getText().toString().trim();
                String loginPasswd = etLoginKey.getText().toString().trim();
                if (loginName.equals(userName) && loginPasswd.equals(userPasswd)) {
                    sharedPreferencesHelper.save(this, userName, userPasswd, true);
                    dialog.dismiss();
                    tvUserName.setText(userName);
                    btnExit.setVisibility(View.VISIBLE);
                    ivUserImage.setEnabled(false);
                } else {
                    ToastUtil.showToast(mContext, getString(R.string.toast_user_error));
                }
                break;
            case R.id.ivRemove://取消
                dialog.dismiss();
                break;
        }
    }

    /**
     * 登录
     */
    private void login() {
        String name = tvUserName.getText().toString().trim();
        if (TextUtils.isEmpty(name)) {
            ivUserImage.setEnabled(true);
            dialog = new Dialog(mContext);
            dialogLoginView = View.inflate(mContext, R.layout.activity_user_login, null);
            ivRemove = (ImageView) dialogLoginView.findViewById(R.id.ivRemove);
            btnRegistered = (Button) dialogLoginView.findViewById(R.id.btnRegistered);
            btnLogin = (Button) dialogLoginView.findViewById(R.id.btnLogin);
            etLoginUser = (EditText) dialogLoginView.findViewById(R.id.etLoginUser);
            etLoginKey = (EditText) dialogLoginView.findViewById(R.id.etLoginKey);
            //设置隐藏字体大小
            SetHintTextSize.setHintTextSize(etLoginUser, getString(R.string.et_login_user), 12);
            SetHintTextSize.setHintTextSize(etLoginKey, getString(R.string.et_key), 12);
            //设置Dialog没有标题
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(dialogLoginView);
            dialog.show();
            btnRegistered.setOnClickListener(this);
            btnLogin.setOnClickListener(this);
            ivRemove.setOnClickListener(this);
        } else {
            ivUserImage.setEnabled(false);
        }
    }

    /**
     * 获取用户名 密码
     */
    private void getUser() {
        sharedPreferencesHelper = new SharedPreferencesHelper();
        mUserBean = sharedPreferencesHelper.read(mContext);
        userName = mUserBean.getUserName();
        userPasswd = mUserBean.getLoginPassword();
        if (mUserBean.isLogin()) {
            tvUserName.setText(userName);
            btnExit.setVisibility(View.VISIBLE);
        } else {
            btnExit.setVisibility(View.INVISIBLE);
        }
    }

    /**
     * 退出登录
     */
    private void exit() {
        sharedPreferencesHelper.save(mContext, userName, userPasswd, false);
        tvUserName.setText("");
        ToastUtil.showToast(mContext, getString(R.string.toast_exit));
        btnExit.setVisibility(View.INVISIBLE);
        ivUserImage.setEnabled(true);
    }

    /**
     * 版本检测
     */
    public void getCheckVersionData() {
        HashMap<String, String> versionMap = new HashMap<>();
        versionMap.put("version", "1.0.0");
        NetWorkUtils.doPost(mContext, URLConfig.CHECK_VERSION, versionMap, new NetWorkUtils.RequestCallBack() {
            @Override
            public void onSuccess(String json) {
                CheckVersion checkVersion = new Gson().fromJson(json, CheckVersion.class);
                if (checkVersion != null) {
                    String version = checkVersion.getContent().getVersion();
                    ToastUtil.showToast(mContext, version);
                }
            }

            @Override
            public void onError() {
                ToastUtil.showToast(mContext, getString(R.string.common_refresh_failed));
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                getUser();
            }
        }
    }
}
