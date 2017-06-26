package com.sf.bocfinancialsoftware.activity.tool.calculator;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sf.bocfinancialsoftware.R;

public class CalculatorActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView ivBack;
    private LinearLayout llCurrencyCalculator, llPriceCalculator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);
        initView();
        initListener();
    }

    private void initListener() {
        ivBack.setOnClickListener(this);
        llCurrencyCalculator.setOnClickListener(this);
        llPriceCalculator.setOnClickListener(this);
    }

    private void initView() {
        TextView tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvTitle.setText(R.string.tv_calculator);
        ivBack = (ImageView) findViewById(R.id.ivBack);
        llCurrencyCalculator = (LinearLayout) findViewById(R.id.llCurrencyCalculator);
        llPriceCalculator = (LinearLayout) findViewById(R.id.llPriceCalculator);
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
