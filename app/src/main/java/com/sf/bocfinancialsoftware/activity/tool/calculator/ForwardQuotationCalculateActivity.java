package com.sf.bocfinancialsoftware.activity.tool.calculator;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.google.gson.Gson;
import com.sf.bocfinancialsoftware.R;
import com.sf.bocfinancialsoftware.adapter.calculate.CalculateAdapter;
import com.sf.bocfinancialsoftware.base.BaseActivity;
import com.sf.bocfinancialsoftware.bean.calculate.ExchangeRateCalculateBean;
import com.sf.bocfinancialsoftware.constant.URLConfig;
import com.sf.bocfinancialsoftware.util.NetWorkUtils;
import com.sf.bocfinancialsoftware.util.ToastUtil;
import com.sf.bocfinancialsoftware.widget.MyTextWatcher;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ForwardQuotationCalculateActivity extends BaseActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

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
    private CalculateAdapter mAdapter;//币种适配器
    private int mYear, mMonth, mDay; //日期显示
    private final int DATE_DIALOG = 1;
    private String[] strRate = new String[]{"结汇", "售汇"};
    private int mPositionBtn = 0; //结汇 售汇的位置
    private String strRateResult; //获取签单汇率的结果
    private String strMoney; //获取外币金额的结果
    private PopupWindow mPopupWindow;
    private ListView lvPopup;
    private List<ExchangeRateCalculateBean.ContentBean> mCurrency = new ArrayList<>();//币种数据
    private ExchangeRateCalculateBean.ContentBean mCurrContentBean;
    private NumberFormat numberFormat;//科学计数法

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forward_quotation_calculate);
        initView();
        initData();
        initListener();
    }

    @Override
    protected void initView() {
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

    @Override
    protected void initData() {
        //为下拉列表定义一个适配器
        mAdapter = new CalculateAdapter(mContext);
        //获取币种名 结汇 售汇
        getForwardCalculateData();
        //数据不显示成科学计数法
        numberFormat = NumberFormat.getInstance();
        //获取当前日期
        getDate();
        tvForwardQuotationData.setText(mYear + "-" + (mMonth + 1) + "-" + mDay);
    }

    @Override
    protected void initListener() {
        ivBack.setOnClickListener(this);
        btnForwardQuotationSettlement.setOnClickListener(this);
        btnForwardQuotationSurrendering.setOnClickListener(this);
        tvForwardQuotationData.setOnClickListener(this);
        tvRate.setOnClickListener(this);
        etForwardQuotationRate.addTextChangedListener(new MyTextWatcher() {
            String mString;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                mString = s.toString().trim();
            }

            @Override
            public void afterTextChanged(Editable s) {
                //计算操作
                strRateResult = s.toString().trim();
                strMoney = etForwardQuotationCurrency.getText().toString().trim();
                if (!TextUtils.isEmpty(strRateResult) &&
                        !strRateResult.matches("(^[1-9]\\d{0,7}(\\.\\d{0,2})?)|(^0(\\.\\d{0,2})?)")) {
                    etForwardQuotationRate.setText(mString);
                    etForwardQuotationRate.setSelection(etForwardQuotationRate.getText().toString().length());
                }
                //数据不显示成科学计数法
                double calculate = Double.parseDouble(calculate(strRateResult, strMoney));
                if (calculate == 0) {
                    tvForwardQuotationExchange.setText("");
                } else {
                tvForwardQuotationExchange.setText(numberFormat.format(calculate));
                }
            }
        });
        etForwardQuotationCurrency.addTextChangedListener(new MyTextWatcher() {
            String mString;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                mString = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {
                //计算操作
                strRateResult = etForwardQuotationRate.getText().toString().trim();
                strMoney = s.toString().trim();
                if (!TextUtils.isEmpty(strMoney) &&
                        !strMoney.matches("(^[1-9]\\d{0,7}(\\.\\d{0,2})?)|(^0(\\.\\d{0,2})?)")) {
                    etForwardQuotationCurrency.setText(mString);
                    etForwardQuotationCurrency.setSelection(etForwardQuotationCurrency.getText().toString().length());
                }
                //数据不显示成科学计数法
                double calculate = Double.parseDouble(calculate(strRateResult, strMoney));
                if (calculate == 0) {
                    tvForwardQuotationExchange.setText("");
                } else {
                    tvForwardQuotationExchange.setText(numberFormat.format(calculate));
                }
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
                tvSpotPrice.setText(mCurrContentBean.getExchangeSettlement());
                btnForwardQuotationSettlement.setBackgroundResource(R.mipmap.btn_left_pre);
                btnForwardQuotationSurrendering.setBackgroundResource(R.mipmap.btn_right);
                btnForwardQuotationSettlement.setTextColor(getResources().getColor(R.color.white));
                btnForwardQuotationSurrendering.setTextColor(getResources().getColor(R.color.tv_subtitle));
                //计算操作
                getRate(strRate[mPositionBtn]);
                //数据不显示成科学计数法
                String calculate = calculate(etForwardQuotationRate.getText().toString().trim(), etForwardQuotationCurrency.getText().toString().trim());
                double calculateResult = Double.parseDouble(calculate);
                if (calculateResult == 0) {
                    tvForwardQuotationExchange.setText("");
                } else {
                    tvForwardQuotationExchange.setText(numberFormat.format(calculateResult));
                }
                break;
            case R.id.btnForwardQuotationSurrendering: //售汇按钮
                mPositionBtn = 1;
                tvSpotPrice.setText(mCurrContentBean.getExchangeSurrendering());
                btnForwardQuotationSurrendering.setBackgroundResource(R.mipmap.btn_right_pre);
                btnForwardQuotationSettlement.setBackgroundResource(R.mipmap.btn_left);
                btnForwardQuotationSurrendering.setTextColor(getResources().getColor(R.color.white));
                btnForwardQuotationSettlement.setTextColor(getResources().getColor(R.color.tv_subtitle));
                //计算操作
                getRate(strRate[mPositionBtn]);
                //数据不显示成科学计数法
                String calculate2 = calculate(etForwardQuotationRate.getText().toString().trim(), etForwardQuotationCurrency.getText().toString().trim());
                double calculateResult2 = Double.parseDouble(calculate2);
                if (calculateResult2 == 0) {
                    tvForwardQuotationExchange.setText("");
                } else {
                    tvForwardQuotationExchange.setText(numberFormat.format(calculateResult2));
                }
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
        View contentView = View.inflate(mContext, R.layout.popup_caculater, null);
        lvPopup = (ListView) contentView.findViewById(R.id.lvPopup);
        mPopupWindow = new PopupWindow(contentView, tvRate.getWidth(), ActionBar.LayoutParams.WRAP_CONTENT, true);
        //设置背景
        Drawable drawable = ContextCompat.getDrawable(mContext, R.drawable.shape_popup);
        mPopupWindow.setBackgroundDrawable(drawable);
        lvPopup.setAdapter(mAdapter);
        //设置点击窗口外边可以消失
        mPopupWindow.setOutsideTouchable(true);
        lvPopup.setOnItemClickListener(this);
    }

    /**
     * 获取日期
     */
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

    /**
     * 汇率计算
     */
    public String calculate(String etStrRate, String etStrMoney) {
        if (TextUtils.isEmpty(etStrRate) || TextUtils.isEmpty(etStrMoney)) {
            return "0";
        }
        float money = 0f;
        float rate = getRate(strRate[mPositionBtn]);
        tvSpotPrice.setText(rate+"");
        float a = Float.parseFloat(etStrRate); //获取签单汇率
        float b = Float.parseFloat(etStrMoney); //获取外币金额
        switch (mCurrContentBean.getCurrencyName()) {
            case "美元":
                money = rate * a * b;
                break;
            case "欧元":
                money = rate * a * b;
                break;
            case "日元":
                money = rate * a * b;
                break;
        }
        return money + "";
    }

    public float getRate(String strRate) {
        float rate = 0;
        switch (strRate) {
            case "结汇":
                rate = Float.parseFloat(mCurrContentBean.getExchangeSettlement());
                break;
            case "售汇":
                rate = Float.parseFloat(mCurrContentBean.getExchangeSurrendering());
                break;
        }
        return rate;
    }

    /**
     * 获取币种名，结汇 售汇
     */
    public void getForwardCalculateData() {
        NetWorkUtils.doPost(mContext, URLConfig.EXCHANGE_RATE_CALCULATE, new NetWorkUtils.RequestCallBack() {
            @Override
            public void onSuccess(String json) {
                ExchangeRateCalculateBean calculateBean = new Gson().fromJson(json, ExchangeRateCalculateBean.class);
                if (calculateBean != null) {
                    mCurrency.clear();
                    mCurrency.addAll(calculateBean.getContent());
                    mAdapter.setCurrency(calculateBean.getContent());
                    //初始数据
                    mCurrContentBean = mCurrency.get(0);
                    tvSpotPrice.setText(mCurrContentBean.getExchangeSettlement());
                    tvRate.setText(mCurrContentBean.getCurrencyName());
                }

            }

            @Override
            public void onError() {
                ToastUtil.showToast(mContext,getString(R.string.common_refresh_failed));
            }
        });
    }

    /**
     * popListView点击事件
     * @param parent
     * @param view
     * @param position
     * @param id
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //获取item数据
        mCurrContentBean = mCurrency.get(position);
        if (mPositionBtn == 0) {
            tvSpotPrice.setText(mCurrContentBean.getExchangeSettlement());
        } else {
            tvSpotPrice.setText(mCurrContentBean.getExchangeSurrendering());
        }
        String value = mCurrContentBean.getCurrencyName();
        //把选择的数据展示对应的TextView上
        tvRate.setText(value);
        tvForwardQuotationAmount.setText(value);
        tvForwardQuotationPurchasePrice.setText(value);
        //选择完后关闭popup窗口
        mPopupWindow.dismiss();
        //计算操作
        String strRateResult = etForwardQuotationRate.getText().toString().trim();
        String strMoney = etForwardQuotationCurrency.getText().toString().trim();
        if (TextUtils.isEmpty(strRateResult) || TextUtils.isEmpty(strMoney)) {
            tvForwardQuotationExchange.setText("");
            return;
        }
        double calculate = Double.parseDouble(calculate(strRateResult, strMoney));
        if (calculate == 0) {
            tvForwardQuotationExchange.setText("");
        } else {
            tvForwardQuotationExchange.setText(numberFormat.format(calculate));
        }
    }
}
