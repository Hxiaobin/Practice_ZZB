package com.sf.bocfinancialsoftware.activity.tool.calculator;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
    private TextView tvForwardQuotationAmount;
    private TextView tvForwardQuotationPurchasePrice;
    private List<String> mList;
    private ArrayAdapter<String> mAdapter;
    private boolean isLeftCheck = true; //是否点击结汇按钮
    private boolean isRightCheck = true; //是否点击售汇按钮
    private int mYear, mMonth, mDay; //日期显示
    private final int DATE_DIALOG = 1;

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
    }

    private void initData() {
        //添加一个下拉列表项的list菜单项
        mList = new ArrayList<>();
        String[] currency = getResources().getStringArray(R.array.calculate_currency);
        for (int i = 0; i < currency.length; i++) {
            mList.add(currency[i]);
        }
        //为下拉列表定义一个适配器，这里就用到里前面定义的list 为适配器设置下拉列表下拉时的菜单样式
        mAdapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, mList);
        //将适配器添加到下拉列表上
        spinnerForwardQuotation.setAdapter(mAdapter);
        //获取当前日期
        getDate();
        tvForwardQuotationData.setText(mYear+"-"+(mMonth+1)+"-"+mDay);
    }

    private void initListener() {
        ivBack.setOnClickListener(this);
        btnForwardQuotationSettlement.setOnClickListener(this);
        btnForwardQuotationSurrendering.setOnClickListener(this);
        spinnerForwardQuotation.setOnItemSelectedListener(this);
        tvForwardQuotationData.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivBack:
                finish();
                break;
            case R.id.btnForwardQuotationSettlement: //结汇按钮
                if (isLeftCheck) {
                    isRightCheck = true;
                    isLeftCheck = false;
                    btnForwardQuotationSettlement.setBackgroundResource(R.mipmap.btn_left_pre);
                    btnForwardQuotationSurrendering.setBackgroundResource(R.mipmap.btn_right);
                    btnForwardQuotationSettlement.setTextColor(getResources().getColor(R.color.white));
                    btnForwardQuotationSurrendering.setTextColor(getResources().getColor(R.color.tv_subtitle));
                }
                break;
            case R.id.btnForwardQuotationSurrendering: //售汇按钮
                if (isRightCheck) {
                    isLeftCheck = true;
                    isRightCheck = false;
                    btnForwardQuotationSurrendering.setBackgroundResource(R.mipmap.btn_right_pre);
                    btnForwardQuotationSettlement.setBackgroundResource(R.mipmap.btn_left);
                    btnForwardQuotationSurrendering.setTextColor(getResources().getColor(R.color.white));
                    btnForwardQuotationSettlement.setTextColor(getResources().getColor(R.color.tv_subtitle));
                }
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
        StringBuffer strBuffer = new StringBuffer().append(mYear).append("-").append(mMonth+1)
                .append("-").append(mDay);
        tvForwardQuotationData.setText(strBuffer);
    }

    //Spinner选中操作
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        tvForwardQuotationAmount.setText(mAdapter.getItem(position));
        tvForwardQuotationPurchasePrice.setText(mAdapter.getItem(position));
    }

    //Spinner未选中操作
    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
