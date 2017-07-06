package com.sf.bocfinancialsoftware.activity.tool.calculator;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.sf.bocfinancialsoftware.R;

import java.util.ArrayList;
import java.util.List;

public class ExchangeRateCalculateActivity extends AppCompatActivity implements View.OnClickListener {

    private Context mContext;
    private ImageView ivBack;
    private Button btnExchangeRateSettlement;
    private Button btnExchangeRateSurrendering;
    private TextView tvSpotPrice;
    private EditText etExchangeRateCurrency;
    private TextView tvExchangeRateExchange;
    private TextView tvExchangeRateAmount;
    private TextView tvRate; //选择币种
    private TextView tvExchangeRatePurchasePrice;
    private List<String> mList; //币种数据
    private PopupWindow mPopupWindow;
    private ArrayAdapter<String> mAdapter;//币种适配器
    private String[] strCurrency = new String[]{"美元", "欧元", "日元"};
    private int positionCurrency = 0; //币种的位置
    private String[] strRate = new String[]{"结汇", "售汇"};
    private int mPositionBtn = 0; //结汇 售汇的位置
    private ListView popListView;//下拉框的listview

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
        btnExchangeRateSettlement = (Button) findViewById(R.id.btnExchangeRateSettlement);
        btnExchangeRateSurrendering = (Button) findViewById(R.id.btnExchangeRateSurrendering);
        tvSpotPrice = (TextView) findViewById(R.id.tvSpotPrice);
        etExchangeRateCurrency = (EditText) findViewById(R.id.etExchangeRateCurrency);
        tvExchangeRateExchange = (TextView) findViewById(R.id.tvExchangeRateExchange);
        tvExchangeRateAmount = (TextView) findViewById(R.id.tvExchangeRateAmount);
        tvExchangeRatePurchasePrice = (TextView) findViewById(R.id.tvExchangeRatePurchasePrice);
        tvRate = (TextView) findViewById(R.id.tvRate);
    }

    private void initData() {
        mContext = this;
    }

    private void initListener() {
        ivBack.setOnClickListener(this);
        btnExchangeRateSettlement.setOnClickListener(this);
        btnExchangeRateSurrendering.setOnClickListener(this);
        tvRate.setOnClickListener(this);
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
                }else if (string.equals(".")){
                    string = "0"+string;
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
     *初始化popup以及计算
     */
    private void initSelectPopup() {
        //添加一个下拉列表项的list菜单项
        mList = new ArrayList<>();
        View contentView = View.inflate(mContext, R.layout.popup_caculater, null);
        popListView = (ListView) contentView.findViewById(R.id.lvPopup);
        mPopupWindow = new PopupWindow(contentView, tvRate.getWidth(), ActionBar.LayoutParams.WRAP_CONTENT, true);
        //设置背景
        Drawable drawable = ContextCompat.getDrawable(this, R.drawable.shape_popup);
        mPopupWindow.setBackgroundDrawable(drawable);
        //设置点击窗口外边可以消失
        mPopupWindow.setOutsideTouchable(true);
        String[] currency = getResources().getStringArray(R.array.calculate_currency);
        for (int i = 0; i < currency.length; i++) {
            mList.add(currency[i]);
        }
        //为下拉列表定义一个适配器
        mAdapter = new ArrayAdapter<>(this, R.layout.spinner_item, mList);
        //将适配器添加到下拉列表上
        popListView.setAdapter(mAdapter);
        popListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //获取item数据
                String value = mList.get(position);
                //把选择的数据展示对应的TextView上
                tvRate.setText(value);
                tvExchangeRatePurchasePrice.setText(value);
                tvExchangeRateAmount.setText(value);
                //选择完后关闭popup窗口
                mPopupWindow.dismiss();
                //计算操作
                String string = etExchangeRateCurrency.getText().toString();
                if (TextUtils.isEmpty(string)) {
                    return;
                }
                positionCurrency = position;
                String calculate = calculate(strCurrency[positionCurrency], strRate[mPositionBtn], string);
                tvExchangeRateExchange.setText(calculate);
            }
        });
        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                mPopupWindow.dismiss();
            }
        });
    }

    //计算操作
    public String calculate(String strCurrency, String strRate, String etStr) {
        if (TextUtils.isEmpty(strCurrency) || TextUtils.isEmpty(strRate) || TextUtils.isEmpty(etStr)) {
            return "";
        }
        float money = 0f;
        float rate = getRate(strRate);
        float a = Float.parseFloat(etStr);
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

    public float getRate(String strRate) {
        float rate = 1000f;
        switch (strRate) {
            case "结汇":
                rate = 1000f;
                tvSpotPrice.setText("1000.00");
                break;
            case "售汇":
                rate = 2000f;
                tvSpotPrice.setText("2000.00");
                break;
        }
        return rate;
    }

}
