package com.sf.bocfinancialsoftware.activity.tool.calculator;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sf.bocfinancialsoftware.R;
import com.sf.bocfinancialsoftware.base.BaseActivity;

public class CalculatorActivity extends BaseActivity implements View.OnClickListener {
    private ImageView ivBack;
    private LinearLayout llCurrencyCalculator, llPriceCalculator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);
        initView();
        initData();
        initListener();
    }

    @Override
    protected void initView() {
        TextView tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvTitle.setText(R.string.tv_calculator);
        ivBack = (ImageView) findViewById(R.id.ivBack);
        llCurrencyCalculator = (LinearLayout) findViewById(R.id.llCurrencyCalculator);
        llPriceCalculator = (LinearLayout) findViewById(R.id.llPriceCalculator);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
        ivBack.setOnClickListener(this);
        llCurrencyCalculator.setOnClickListener(this);
        llPriceCalculator.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivBack:
                finish();
                break;
            case R.id.llCurrencyCalculator:
                startActivity(new Intent(CalculatorActivity.this,ExchangeRateCalculateActivity.class));
                break;
            case R.id.llPriceCalculator:
                startActivity(new Intent(CalculatorActivity.this,ForwardQuotationCalculateActivity.class));
                break;
        }
    }
}
