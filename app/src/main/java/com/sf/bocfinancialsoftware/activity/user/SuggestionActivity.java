package com.sf.bocfinancialsoftware.activity.user;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sf.bocfinancialsoftware.R;
import com.sf.bocfinancialsoftware.base.BaseActivity;
import com.sf.bocfinancialsoftware.constant.URLConfig;
import com.sf.bocfinancialsoftware.util.NetWorkUtils;
import com.sf.bocfinancialsoftware.util.ToastUtil;
import com.sf.bocfinancialsoftware.widget.SetHintTextSize;

import java.util.HashMap;

public class SuggestionActivity extends BaseActivity implements View.OnClickListener {

    private EditText etSuggestion, etContact;
    private Button btnSend;
    private ImageView ivBack;
    private String mSuggestion;//反馈内容
    private String mContact;//联系方式

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggestion);
        initView();
        initData();
        initListener();
    }

    @Override
    protected void initView() {
        TextView textView = (TextView) findViewById(R.id.tvTitle);
        textView.setText(R.string.tv_user_suggestion);
        etSuggestion = (EditText) findViewById(R.id.etSuggestion);
        etContact = (EditText) findViewById(R.id.etContact);
        btnSend = (Button) findViewById(R.id.btnSend);
        ivBack = (ImageView) findViewById(R.id.ivBack);
        //设置隐藏字体大小
        SetHintTextSize.setHintTextSize(etSuggestion, getString(R.string.et_suggestion), 12);
        SetHintTextSize.setHintTextSize(etContact, getString(R.string.et_contact), 12);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
        btnSend.setOnClickListener(this);
        ivBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivBack:
                finish();
                break;
            case R.id.btnSend:
                send();
                break;
        }
    }

    private void send() {
        mSuggestion = etSuggestion.getText().toString();
        mContact = etContact.getText().toString();
        if (TextUtils.isEmpty(mSuggestion)) {
            Toast.makeText(mContext, R.string.et_suggestion, Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(mContact)) {
            Toast.makeText(mContext, R.string.et_contact, Toast.LENGTH_LONG).show();
            return;
        }
        //提交意见数据
        uPSuggestionData();
        finish();
    }

    private void uPSuggestionData() {
        HashMap<String, String> suggestionMap = new HashMap<>();
        suggestionMap.put("content", mSuggestion);
        suggestionMap.put("content", mContact);
        NetWorkUtils.doPost(mContext, URLConfig.UP_SUGGESTION, suggestionMap, new NetWorkUtils.RequestCallBack() {
            @Override
            public void onSuccess(String json) {
                ToastUtil.showToast(mContext,getString( R.string.toast_get_suggestion));

            }

            @Override
            public void onError() {
                ToastUtil.showToast(mContext,getString(R.string.toast_suggestion_error));
            }
        });
    }

}
