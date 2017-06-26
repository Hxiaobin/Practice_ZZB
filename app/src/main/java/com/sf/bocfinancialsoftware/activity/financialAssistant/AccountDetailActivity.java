package com.sf.bocfinancialsoftware.activity.financialAssistant;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.sf.bocfinancialsoftware.R;

public class AccountDetailActivity extends AppCompatActivity {

    private TextView tvAccountDetail1,tvAccountDetail2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_detail);
        initView();
        initDatas();
    }

    private void initView() {
        tvAccountDetail1 = (TextView) findViewById(R.id.tvAccountDetail1);
        tvAccountDetail2 = (TextView) findViewById(R.id.tvAccountDetail2);
        TextView ivTitle = (TextView) findViewById(R.id.tvTitle);
        ivTitle.setText(R.string.text_financial_assistant);
        ImageView ivBack = (ImageView) findViewById(R.id.ivBack);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initDatas() {
        //改变账号
        Bundle bundle = getIntent().getExtras();
        String accountDetail = bundle.getString("account");
        tvAccountDetail1.setText(accountDetail);
        tvAccountDetail2.setText(accountDetail);
    }

}
