package com.sf.bocfinancialsoftware.activity.tool.calculator;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.sf.bocfinancialsoftware.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ForwardQuotationCalculateActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    private ImageView ivBack;
    private Spinner spinnerForwardQuotation;
    private Button btnForwardQuotationSettlement;
    private Button btnForwardQuotationSurrendering;
    private TextView tvForwardQuotationData; //选择日期
    private EditText etForwardQuotationRate;
    private EditText etForwardQuotationCurrency;
    private TextView tvForwardQuotationExchange;
    private TextView tvSpotPrice;
    private TextView tvForwardQuotationAmount;
    private TextView tvForwardQuotationPurchasePrice;
    private List<String> mList;
    private ArrayAdapter<String> mAdapter;
    private int mYear, mMonth, mDay; //日期显示
    private final int DATE_DIALOG = 1;
    private String[] strCurrency = new String[]{"美元", "欧元", "日元"};
    private int positionCurrency = 0; //币种的位置
    private String[] strRate = new String[]{"结汇", "售汇"};
    private int mPositionBtn = 0; //结汇 售汇的位置

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forward_quotation_calculate);
        initView();
        initData();
        initListener();
    }

    private void initView() {
        ivBack = (ImageView) findViewById(R.id.ivBack);
        TextView tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvTitle.setText(R.string.tv_price_calculator);
        spinnerForwardQuotation = (Spinner) findViewById(R.id.spinnerForwardQuotation);
        btnForwardQuotationSurrendering = (Button) findViewById(R.id.btnForwardQuotationSurrendering);
        tvForwardQuotationData = (TextView) findViewById(R.id.tvForwardQuotationData);
        etForwardQuotationRate = (EditText) findViewById(R.id.etForwardQuotationRate);
        btnForwardQuotationSettlement = (Button) findViewById(R.id.btnForwardQuotationSettlement);
        etForwardQuotationCurrency = (EditText) findViewById(R.id.etForwardQuotationCurrency);
        tvForwardQuotationExchange = (TextView) findViewById(R.id.tvForwardQuotationExchange);
        tvForwardQuotationAmount = (TextView) findViewById(R.id.tvForwardQuotationAmount);
        tvForwardQuotationPurchasePrice = (TextView) findViewById(R.id.tvForwardQuotationPurchasePrice);
        tvSpotPrice = (TextView) findViewById(R.id.tvSpotPrice);
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
        spinnerForwardQuotation.setAdapter(mAdapter);
        //获取当前日期
        getDate();
        tvForwardQuotationData.setText(mYear + "-" + (mMonth + 1) + "-" + mDay);
    }

    private void initListener() {
        ivBack.setOnClickListener(this);
        btnForwardQuotationSettlement.setOnClickListener(this);
        btnForwardQuotationSurrendering.setOnClickListener(this);
        spinnerForwardQuotation.setOnItemSelectedListener(this);
        tvForwardQuotationData.setOnClickListener(this);
        etForwardQuotationRate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                //计算操作
                String strRateResult = s.toString();
                String strMoney = etForwardQuotationCurrency.getText().toString();
                if (TextUtils.isEmpty(strRateResult) || TextUtils.isEmpty(strMoney)) {
                    tvForwardQuotationExchange.setText("");
                    return;
                }
                String calculate = calculate(strCurrency[positionCurrency], strRate[mPositionBtn],
                        strRateResult, strMoney);
                tvForwardQuotationExchange.setText(calculate);
            }
        });
        etForwardQuotationCurrency.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                //计算操作
                String strRateResult = etForwardQuotationRate.getText().toString();
                String strMoney = s.toString();
                if (TextUtils.isEmpty(strRateResult) || TextUtils.isEmpty(strMoney)) {
                    tvForwardQuotationExchange.setText("");
                    return;
                }
                String calculate = calculate(strCurrency[positionCurrency], strRate[mPositionBtn],
                        strRateResult, strMoney);
                tvForwardQuotationExchange.setText(calculate);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivBack:
                finish();
                break;
            case R.id.btnForwardQuotationSettlement: //结汇按钮
                mPositionBtn = 0;
                btnForwardQuotationSettlement.setBackgroundResource(R.mipmap.btn_left_pre);
                btnForwardQuotationSurrendering.setBackgroundResource(R.mipmap.btn_right);
                btnForwardQuotationSettlement.setTextColor(getResources().getColor(R.color.white));
                btnForwardQuotationSurrendering.setTextColor(getResources().getColor(R.color.tv_subtitle));
                //计算操作
                getRate(strRate[mPositionBtn]);
                String calculate = calculate(strCurrency[positionCurrency], strRate[mPositionBtn],
                        etForwardQuotationRate.getText().toString(), etForwardQuotationCurrency.getText().toString());
                tvForwardQuotationExchange.setText(calculate);
                break;
            case R.id.btnForwardQuotationSurrendering: //售汇按钮
                mPositionBtn = 1;
                btnForwardQuotationSurrendering.setBackgroundResource(R.mipmap.btn_right_pre);
                btnForwardQuotationSettlement.setBackgroundResource(R.mipmap.btn_left);
                btnForwardQuotationSurrendering.setTextColor(getResources().getColor(R.color.white));
                btnForwardQuotationSettlement.setTextColor(getResources().getColor(R.color.tv_subtitle));
                //计算操作
                getRate(strRate[mPositionBtn]);
                String calculate2 = calculate(strCurrency[positionCurrency], strRate[mPositionBtn],
                        etForwardQuotationRate.getText().toString(), etForwardQuotationCurrency.getText().toString());
                tvForwardQuotationExchange.setText(calculate2);
                break;
            case R.id.tvForwardQuotationData: //日期选择
                showDialog(DATE_DIALOG);
                break;
        }
    }

    public void getDate() {
        final Calendar calendar = Calendar.getInstance();
        mYear = calendar.get(Calendar.YEAR);
        mMonth = calendar.get(Calendar.MONTH);
        mDay = calendar.get(Calendar.DAY_OF_MONTH);
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG:
                return new DatePickerDialog(this, mDateSetListener, mYear, mMonth, mDay);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            mYear = year;
            mMonth = month;
            mDay = dayOfMonth;
            disPlay();
        }
    };

    private void disPlay() {
        StringBuffer strBuffer = new StringBuffer().append(mYear).append("-").append(mMonth + 1)
                .append("-").append(mDay);
        tvForwardQuotationData.setText(strBuffer);
    }

    //Spinner选中操作
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        tvForwardQuotationAmount.setText(mAdapter.getItem(position));
        tvForwardQuotationPurchasePrice.setText(mAdapter.getItem(position));
        //计算操作
        String strRateResult = etForwardQuotationRate.getText().toString();
        String strMoney = etForwardQuotationCurrency.getText().toString();
        if (TextUtils.isEmpty(strRateResult) || TextUtils.isEmpty(strMoney)) {
            tvForwardQuotationExchange.setText("");
            return;
        }
        this.positionCurrency = position;
        String calculate = calculate(strCurrency[positionCurrency], strRate[mPositionBtn],
                strRateResult, strMoney);
        tvForwardQuotationExchange.setText(calculate);
    }

    //Spinner未选中操作
    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public String calculate(String strCurrency, String strRate, String etStrRate, String etStrMoney) {
        if (TextUtils.isEmpty(strCurrency) || TextUtils.isEmpty(strRate) || TextUtils.isEmpty(etStrRate)||TextUtils.isEmpty(etStrMoney)) {
            return "" ;
        }
        float money = 0;
        int rate = getRate(strRate);
        float a = Float.parseFloat(etStrRate); //获取签单汇率
        float b = Float.parseFloat(etStrMoney); //获取外币金额
        switch (strCurrency) {
            case "美元":
                money = 3 * rate * a * b;
                break;
            case "欧元":
                money = 4 * rate * a * b;
                break;
            case "日元":
                money = 5 * rate * a * b;
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
