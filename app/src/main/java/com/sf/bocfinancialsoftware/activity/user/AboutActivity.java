package com.sf.bocfinancialsoftware.activity.user;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.sf.bocfinancialsoftware.R;
import com.sf.bocfinancialsoftware.base.BaseActivity;

public class AboutActivity extends BaseActivity {

    private ImageView mIvBack;//返回按钮

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        initView();
        initData();
        initListener();
    }

    @Override
    protected void initView() {
        TextView textView = (TextView) findViewById(R.id.tvTitle);
        textView.setText(R.string.tv_user_about);
        mIvBack = (ImageView) findViewById(R.id.ivBack);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
        mIvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
