package com.sf.bocfinancialsoftware.activity.tool.calculator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.sf.bocfinancialsoftware.R;

import java.util.ArrayList;
import java.util.List;

public class ExchangeRateCalculateActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    private ImageView ivBack;
    private Spinner spinnerExchangeRate;
    private Button btnExchangeRateSettlement;
    private Button btnExchangeRateSurrendering;
    private TextView tvSpotPrice;
    private EditText etExchangeRateCurrency;
    private TextView tvExchangeRateExchange;
    private TextView tvExchangeRateAmount;
    private TextView tvExchangeRatePurchasePrice;
    private List<String> mList;
    private ArrayAdapter<String> mAdapter;
    private String[] strCurrency = new String[]{"美元", "欧元", "日元"};
    private int positionCurrency = 0; //币种的位置
    private String[] strRate = new String[]{"结汇", "售汇"};
    private int mPositionBtn = 0; //结汇 售汇的位置

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currency_calculator);
        initView();
        initData();
        initListener();
    }

    private void initView() {
        TextView tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvTitle.setText(R.string.tv_currency_calculator);
        ivBack = (ImageView) findViewById(R.id.ivBack);
        spinnerExchangeRate = (Spinner) findViewById(R.id.spinnerExchangeRate);
        btnExchangeRateSettlement = (Button) findViewById(R.id.btnExchangeRateSettlement);
        btnExchangeRateSurrendering = (Button) findViewById(R.id.btnExchangeRateSurrendering);
        tvSpotPrice = (TextView) findViewById(R.id.tvSpotPrice);
        etExchangeRateCurrency = (EditText) findViewById(R.id.etExchangeRateCurrency);
        tvExchangeRateExchange = (TextView) findViewById(R.id.tvExchangeRateExchange);
        tvExchangeRateAmount = (TextView) findViewById(R.id.tvExchangeRateAmount);
        tvExchangeRatePurchasePrice = (TextView) findViewById(R.id.tvExchangeRatePurchasePrice);
    }

    private void initData() {
        //添加一个下拉列表项的list菜单项
        mList = new ArrayList<>();
        String[] currency = getResources().getStringArray(R.array.calculate_currency);
        for (int i = 0; i < currency.length; i++) {
            mList.add(currency[i]);
        }
        //为下拉列表定义一个适配器，这里就用到里前面定义的list 为适配器设置下拉列表下拉时的菜单样式
        mAdapter = new ArrayAdapter<>(this, R.layout.spinner_item, mList);
        //将适配器添加到下拉列表上
        spinnerExchangeRate.setAdapter(mAdapter);
    }

    private void initListener() {
        ivBack.setOnClickListener(this);
        btnExchangeRateSettlement.setOnClickListener(this);
        btnExchangeRateSurrendering.setOnClickListener(this);
        spinnerExchangeRate.setOnItemSelectedListener(this);
        etExchangeRateCurrency.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                //计算操作
                String string = s.toString();
                if (TextUtils.isEmpty(string)) {
                    tvExchangeRateExchange.setText("");
                    return;
                }
                String calculate = calculate(strCurrency[positionCurrency], strRate[mPositionBtn], string);
                tvExchangeRateExchange.setText(calculate);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivBack:
                finish();
                break;
            case R.id.btnExchangeRateSettlement:
                mPositionBtn = 0;
                btnExchangeRateSettlement.setBackgroundResource(R.mipmap.btn_left_pre);
                btnExchangeRateSurrendering.setBackgroundResource(R.mipmap.btn_right);
                btnExchangeRateSettlement.setTextColor(getResources().getColor(R.color.white));
                btnExchangeRateSurrendering.setTextColor(getResources().getColor(R.color.tv_subtitle));
                //计算操作
                getRate(strRate[mPositionBtn]);
                String calculate = calculate(strCurrency[positionCurrency], strRate[mPositionBtn],
                        etExchangeRateCurrency.getText().toString());
                tvExchangeRateExchange.setText(calculate);
                break;
            case R.id.btnExchangeRateSurrendering:
                mPositionBtn = 1;
                btnExchangeRateSurrendering.setBackgroundResource(R.mipmap.btn_right_pre);
                btnExchangeRateSettlement.setBackgroundResource(R.mipmap.btn_left);
                btnExchangeRateSurrendering.setTextColor(getResources().getColor(R.color.white));
                btnExchangeRateSettlement.setTextColor(getResources().getColor(R.color.tv_subtitle));
                //计算操作
                getRate(strRate[mPositionBtn]);
                String calculate2 = calculate(strCurrency[positionCurrency], strRate[mPositionBtn],
                        etExchangeRateCurrency.getText().toString());
                tvExchangeRateExchange.setText(calculate2);
                break;
        }
    }

    //Spinner选中操作
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        tvExchangeRateAmount.setText(mAdapter.getItem(position));
        tvExchangeRatePurchasePrice.setText(mAdapter.getItem(position));
        //计算操作
        String string = etExchangeRateCurrency.getText().toString();
        if (TextUtils.isEmpty(string)) {
            return;
        }
        this.positionCurrency = position;
        String calculate = calculate(strCurrency[positionCurrency], strRate[mPositionBtn], string);
        tvExchangeRateExchange.setText(calculate);
    }

    //Spinner未选中操作
    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    //计算操作
    public String calculate(String strCurrency, String strRate, String etStr) {
        if (TextUtils.isEmpty(strCurrency) || TextUtils.isEmpty(strRate) || TextUtils.isEmpty(etStr)) {
            return "" ;
        }
        long money = 0;
        int rate = getRate(strRate);
        long a = Long.parseLong(etStr);
        switch (strCurrency) {
            case "美元":
                money = 3 * rate * a;
                break;
            case "欧元":
                money = 4 * rate * a;
                break;
            case "日元":
                money = 5 * rate * a;
                break;
        }
        return money + "";
    }

    public int getRate(String strRate) {
        int rate = 1000;
        switch (strRate) {
            case "结汇":
                rate = 1000;
                tvSpotPrice.setText("1000.00");
                break;
            case "售汇":
                rate = 2000;
                tvSpotPrice.setText("2000.00");
                break;
        }
        return rate;
    }
}
