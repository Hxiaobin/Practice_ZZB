package com.sf.bocfinancialsoftware.activity.tool.calculator;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.sf.bocfinancialsoftware.R;
import com.sf.bocfinancialsoftware.widget.MyTextWatcher;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ForwardQuotationCalculateActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView ivBack;
    private Button btnForwardQuotationSettlement;
    private Button btnForwardQuotationSurrendering;
    private TextView tvForwardQuotationData; //选择日期
    private EditText etForwardQuotationRate;
    private EditText etForwardQuotationCurrency;
    private TextView tvForwardQuotationExchange;
    private TextView tvSpotPrice;
    private TextView tvForwardQuotationAmount;
    private TextView tvForwardQuotationPurchasePrice;
    private TextView tvRate; //币种选择
    private List<String> mList;//币种数据
    private ArrayAdapter<String> mAdapter;//币种适配器
    private int mYear, mMonth, mDay; //日期显示
    private final int DATE_DIALOG = 1;
    private String[] strCurrency = new String[]{"美元", "欧元", "日元"};
    private int positionCurrency = 0; //币种的位置
    private String[] strRate = new String[]{"结汇", "售汇"};
    private int mPositionBtn = 0; //结汇 售汇的位置
    private String strRateResult; //获取签单汇率的结果
    private String strMoney; //获取外币金额的结果
    private PopupWindow mPopupWindow;
    private ListView lvPopup;
    private Context mContext;

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
        btnForwardQuotationSurrendering = (Button) findViewById(R.id.btnForwardQuotationSurrendering);
        tvForwardQuotationData = (TextView) findViewById(R.id.tvForwardQuotationData);
        etForwardQuotationRate = (EditText) findViewById(R.id.etForwardQuotationRate);
        btnForwardQuotationSettlement = (Button) findViewById(R.id.btnForwardQuotationSettlement);
        etForwardQuotationCurrency = (EditText) findViewById(R.id.etForwardQuotationCurrency);
        tvForwardQuotationExchange = (TextView) findViewById(R.id.tvForwardQuotationExchange);
        tvForwardQuotationAmount = (TextView) findViewById(R.id.tvForwardQuotationAmount);
        tvForwardQuotationPurchasePrice = (TextView) findViewById(R.id.tvForwardQuotationPurchasePrice);
        tvSpotPrice = (TextView) findViewById(R.id.tvSpotPrice);
        tvRate = (TextView) findViewById(R.id.tvRate);
    }

    private void initData() {
        mContext = this;
        //获取当前日期
        getDate();
        tvForwardQuotationData.setText(mYear + "-" + (mMonth + 1) + "-" + mDay);
    }

    private void initListener() {
        ivBack.setOnClickListener(this);
        btnForwardQuotationSettlement.setOnClickListener(this);
        btnForwardQuotationSurrendering.setOnClickListener(this);
        tvForwardQuotationData.setOnClickListener(this);
        tvRate.setOnClickListener(this);
        etForwardQuotationRate.addTextChangedListener(new MyTextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                //计算操作
//                String strRateResult = "0";
                strRateResult = s.toString();
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
        etForwardQuotationCurrency.addTextChangedListener(new MyTextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                //计算操作
                strRateResult = etForwardQuotationRate.getText().toString();
                strMoney = s.toString();
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
            case R.id.tvRate:
                // 点击控件后显示popup窗口
                initSelectPopup();
                // 使用isShowing()检查popup窗口是否在显示状态
                if (mPopupWindow != null && !mPopupWindow.isShowing()) {
                    mPopupWindow.showAsDropDown(tvRate);
                }
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

    /**
     * 点击popup以及计算
     */
    private void initSelectPopup() {
        //添加一个下拉列表项的list菜单项
        mList = new ArrayList<>();
        String[] currency = getResources().getStringArray(R.array.calculate_currency);
        for (int i = 0; i < currency.length; i++) {
            mList.add(currency[i]);
        }
        View contentView = View.inflate(mContext, R.layout.popup_caculater, null);
        lvPopup = (ListView) contentView.findViewById(R.id.lvPopup);
        mPopupWindow = new PopupWindow(contentView, tvRate.getWidth(), ActionBar.LayoutParams.WRAP_CONTENT, true);
        //设置背景
        Drawable drawable = ContextCompat.getDrawable(mContext, R.drawable.shape_popup);
        mPopupWindow.setBackgroundDrawable(drawable);
        //为下拉列表定义一个适配器
        mAdapter = new ArrayAdapter<>(this, R.layout.spinner_item, mList);
        lvPopup.setAdapter(mAdapter);
        //设置点击窗口外边可以消失
        mPopupWindow.setOutsideTouchable(true);
        lvPopup.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //获取item数据
                String value = mList.get(position);
                //把选择的数据展示对应的TextView上
                tvRate.setText(value);
                tvForwardQuotationAmount.setText(value);
                tvForwardQuotationPurchasePrice.setText(value);
                //选择完后关闭popup窗口
                mPopupWindow.dismiss();
                //计算操作
                String strRateResult = etForwardQuotationRate.getText().toString();
                String strMoney = etForwardQuotationCurrency.getText().toString();
                if (TextUtils.isEmpty(strRateResult) || TextUtils.isEmpty(strMoney)) {
                    tvForwardQuotationExchange.setText("");
                    return;
                }
                positionCurrency = position;
                String calculate = calculate(strCurrency[positionCurrency], strRate[mPositionBtn],
                        strRateResult, strMoney);
                tvForwardQuotationExchange.setText(calculate);
            }
        });
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

    public String calculate(String strCurrency, String strRate, String etStrRate, String etStrMoney) {
        if (TextUtils.isEmpty(strCurrency) || TextUtils.isEmpty(strRate) || TextUtils.isEmpty(etStrRate) || TextUtils.isEmpty(etStrMoney)) {
            return "";
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
