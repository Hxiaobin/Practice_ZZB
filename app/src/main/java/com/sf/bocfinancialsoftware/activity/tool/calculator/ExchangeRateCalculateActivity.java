package com.sf.bocfinancialsoftware.activity.tool.calculator;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
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
import java.util.List;

public class ExchangeRateCalculateActivity extends BaseActivity implements View.OnClickListener, AdapterView.OnItemClickListener, PopupWindow.OnDismissListener {

    private ImageView ivBack;//返回按钮
    private Button btnExchangeRateSettlement;//结汇
    private Button btnExchangeRateSurrendering;//售汇
    private TextView tvSpotPrice;//现汇买入价
    private EditText etExchangeRateCurrency;//外币金额输入框
    private TextView tvExchangeRateExchange;//兑换人民币
    private TextView tvExchangeRateAmount;
    private TextView tvRate; //选择币种
    private TextView tvExchangeRatePurchasePrice;
    private List<ExchangeRateCalculateBean.ContentBean> mCurrencys = new ArrayList<>(); //币种数据
    private PopupWindow mPopupWindow;
    private CalculateAdapter mAdapter;//币种适配器
    private String[] strRate = new String[]{"结汇", "售汇"};
    private int mPositionBtn = 0; //结汇 售汇的位置
    private ListView popListView;//下拉框的listview
    private ExchangeRateCalculateBean.ContentBean mCurrencyContentBean;
    private NumberFormat numberFormat;//科学计数法

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currency_calculator);
        initView();
        initData();
        initListener();
    }

    @Override
    protected void initView() {
        TextView tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvTitle.setText(R.string.tv_currency_calculator);
        ivBack = (ImageView) findViewById(R.id.ivBack);
        btnExchangeRateSettlement = (Button) findViewById(R.id.btnExchangeRateSettlement);
        btnExchangeRateSurrendering = (Button) findViewById(R.id.btnExchangeRateSurrendering);
        tvSpotPrice = (TextView) findViewById(R.id.tvSpotPrice);
        etExchangeRateCurrency = (EditText) findViewById(R.id.etExchangeRateCurrency);
        tvExchangeRateExchange = (TextView) findViewById(R.id.tvExchangeRateExchange);
        tvExchangeRateAmount = (TextView) findViewById(R.id.tvExchangeRateAmount);
        tvExchangeRatePurchasePrice = (TextView) findViewById(R.id.tvExchangeRatePurchasePrice);
        tvRate = (TextView) findViewById(R.id.tvRate);
    }

    @Override
    protected void initData() {
        //为下拉列表定义一个适配器
        mAdapter = new CalculateAdapter(mContext);
        //获取币种名 结汇 售汇
        getRateCalculateData();
        //数据不显示成科学计数法
        numberFormat = NumberFormat.getInstance();
//        numberFormat.setGroupingUsed(false);
    }

    @Override
    protected void initListener() {
        ivBack.setOnClickListener(this);
        btnExchangeRateSettlement.setOnClickListener(this);
        btnExchangeRateSurrendering.setOnClickListener(this);
        tvRate.setOnClickListener(this);
        etExchangeRateCurrency.addTextChangedListener(new MyTextWatcher() {
            String mString;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                mString = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {
                //计算操作
                String string = s.toString().trim();
                if (!TextUtils.isEmpty(string) && !string.matches("(^[1-9]\\d{0,7}(\\.\\d{0,2})?)|(^0(\\.\\d{0,2})?)")) {
                    etExchangeRateCurrency.setText(mString);
                    etExchangeRateCurrency.setSelection(etExchangeRateCurrency.getText().toString().length());
                }
                //结果不显示成科学计数法
                double calculate = Double.parseDouble(calculate(string));
                String format = numberFormat.format(calculate);
                tvExchangeRateExchange.setText(format);
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
                tvSpotPrice.setText(mCurrencyContentBean.getExchangeSettlement());
                btnExchangeRateSettlement.setBackgroundResource(R.mipmap.btn_left_pre);
                btnExchangeRateSurrendering.setBackgroundResource(R.mipmap.btn_right);
                btnExchangeRateSettlement.setTextColor(getResources().getColor(R.color.white));
                btnExchangeRateSurrendering.setTextColor(getResources().getColor(R.color.tv_subtitle));
                //计算操作
                getRate(strRate[mPositionBtn]);
                double calculate = Double.parseDouble(calculate(etExchangeRateCurrency.getText().toString().trim()));
                if (calculate == 0) {
                    tvExchangeRateExchange.setText("");
                } else {
                    tvExchangeRateExchange.setText(numberFormat.format(calculate));
                }
                break;
            case R.id.btnExchangeRateSurrendering:
                mPositionBtn = 1;
                tvSpotPrice.setText(mCurrencyContentBean.getExchangeSurrendering());
                btnExchangeRateSurrendering.setBackgroundResource(R.mipmap.btn_right_pre);
                btnExchangeRateSettlement.setBackgroundResource(R.mipmap.btn_left);
                btnExchangeRateSurrendering.setTextColor(getResources().getColor(R.color.white));
                btnExchangeRateSettlement.setTextColor(getResources().getColor(R.color.tv_subtitle));
                //计算操作
                getRate(strRate[mPositionBtn]);
                double calculate2 = Double.parseDouble(calculate(etExchangeRateCurrency.getText().toString().trim()));
                if (calculate2 == 0) {
                    tvExchangeRateExchange.setText("");
                } else {
                    tvExchangeRateExchange.setText(numberFormat.format(calculate2));
                }
                break;
            case R.id.tvRate:
                // 点击控件后显示popup窗口
                initSelectPopup();
                // 使用isShowing()检查popup窗口是否在显示状态
                if (mPopupWindow != null && !mPopupWindow.isShowing()) {
                    mPopupWindow.showAsDropDown(tvRate);
                }
                break;
        }
    }

    /**
     * 初始化popup以及计算
     */
    private void initSelectPopup() {
        //添加一个下拉列表项的list菜单项
        View contentView = View.inflate(mContext, R.layout.popup_caculater, null);
        popListView = (ListView) contentView.findViewById(R.id.lvPopup);
        mPopupWindow = new PopupWindow(contentView, tvRate.getWidth(), ActionBar.LayoutParams.WRAP_CONTENT, true);
        popListView.setOnItemClickListener(this);
        mPopupWindow.setOnDismissListener(this);
        //设置背景
        Drawable drawable = ContextCompat.getDrawable(this, R.drawable.shape_popup);
        mPopupWindow.setBackgroundDrawable(drawable);
        //设置点击窗口外边可以消失
        mPopupWindow.setOutsideTouchable(true);
        //将适配器添加到下拉列表上
        popListView.setAdapter(mAdapter);
    }

    //计算操作
    public String calculate(String etStr) {
        if (TextUtils.isEmpty(etStr)) {
            return "0";
        }
        float money = 0f;
        float rate = getRate(strRate[mPositionBtn]);
        tvSpotPrice.setText(rate + "");
        float a = Float.parseFloat(etStr);
        switch (mCurrencyContentBean.getCurrencyName()) {
            case "美元":
                money = rate * a;
                break;
            case "欧元":
                money = rate * a;
                break;
            case "日元":
                money = rate * a;
                break;
        }
        return money + "";
    }

    public float getRate(String strRate) {
        float rate = 0;
        switch (strRate) {
            case "结汇":
                rate = Float.parseFloat(mCurrencyContentBean.getExchangeSettlement());
                break;
            case "售汇":
                rate = Float.parseFloat(mCurrencyContentBean.getExchangeSurrendering());
                break;
        }
        return rate;
    }

    /**
     * 获取币种 结汇 售汇
     */
    public void getRateCalculateData() {
        NetWorkUtils.doPost(mContext, URLConfig.EXCHANGE_RATE_CALCULATE, new NetWorkUtils.RequestCallBack() {
            @Override
            public void onSuccess(String json) {
                ExchangeRateCalculateBean exchangeRateCalculateBean = new Gson().fromJson(json, ExchangeRateCalculateBean.class);
                if (exchangeRateCalculateBean != null) {
                    List<ExchangeRateCalculateBean.ContentBean> content = exchangeRateCalculateBean.getContent();
                    mCurrencys.clear();
                    mCurrencys.addAll(content);
                    mAdapter.setCurrency(content);
                    //初始数据
                    mCurrencyContentBean = mCurrencys.get(0);
                    tvSpotPrice.setText(mCurrencyContentBean.getExchangeSettlement());
                    tvRate.setText(mCurrencyContentBean.getCurrencyName());
                }
            }

            @Override
            public void onError() {
                ToastUtil.showToast(mContext, getString(R.string.common_refresh_failed));
            }
        });
    }

    /**
     * popListView点击事件
     *
     * @param parent
     * @param view
     * @param position
     * @param id
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //获取item数据
        mCurrencyContentBean = mCurrencys.get(position);
        if (mPositionBtn == 0) {
            tvSpotPrice.setText(mCurrencyContentBean.getExchangeSettlement());
        } else {
            tvSpotPrice.setText(mCurrencyContentBean.getExchangeSurrendering());
        }
        String value = mCurrencyContentBean.getCurrencyName();
        //把选择的数据展示对应的TextView上
        tvRate.setText(value);
        tvExchangeRatePurchasePrice.setText(value);
        tvExchangeRateAmount.setText(value);
        //选择完后关闭popup窗口
        mPopupWindow.dismiss();
        //计算操作
        String string = etExchangeRateCurrency.getText().toString().trim();
        if (TextUtils.isEmpty(string)) {
            return;
        }
        double calculate = Double.parseDouble(calculate(string));
        if (calculate == 0) {
            tvExchangeRateExchange.setText("");
        } else {
            tvExchangeRateExchange.setText(numberFormat.format(calculate));
        }
    }

    /**
     * PopupWindow点击消失
     */
    @Override
    public void onDismiss() {
        mPopupWindow.dismiss();
    }

}
