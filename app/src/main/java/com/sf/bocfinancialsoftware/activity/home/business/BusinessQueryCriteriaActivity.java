package com.sf.bocfinancialsoftware.activity.home.business;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.fourmob.datetimepicker.date.DatePickerDialog;
import com.sf.bocfinancialsoftware.R;
import com.sf.bocfinancialsoftware.base.BaseActivity;
import com.sf.bocfinancialsoftware.util.ToastUtil;
import com.sf.bocfinancialsoftware.widget.ClearEditTextTextWatcher;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static com.sf.bocfinancialsoftware.constant.ConstantConfig.BUSINESS_ID;
import static com.sf.bocfinancialsoftware.constant.ConstantConfig.CONTRACT_ID;
import static com.sf.bocfinancialsoftware.constant.ConstantConfig.DATE_PICKER_TAG;
import static com.sf.bocfinancialsoftware.constant.ConstantConfig.END_DATE;
import static com.sf.bocfinancialsoftware.constant.ConstantConfig.START_DATE;

/**
 * 业务查询条件
 * Created by sn on 2017/6/14.
 */

public class BusinessQueryCriteriaActivity extends BaseActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener {

    private ImageView ivTitleBarBack;  //返回
    private ImageView ivContractIdClear;  //清除文本
    private TextView tvTitleBarTitle;  //标题
    private TextView tvBusinessQueryStartDate; //开始时间
    private TextView tvBusinessQueryEndDate; //结束时间
    private EditText etBusinessQueryContractId; //业务编号
    private Button btnBusinessQueryCancel; //取消
    private Button btnBusinessQueryOK; //确定
    private Intent intent;
    private String businessId; //业务名称
    private DatePickerDialog startDatePickerDialog;  //开始日期选择器对话框
    private DatePickerDialog endDatePickerDialog;  //结束日期选择器对话框
    private Calendar calendar; //开始时间选择器日历对象
    private Date date;  //日期对象
    private DateFormat format;  //日期格式化对象，年
    private DateFormat format2; //日期格式化对象，年月日
    private String currentYear;  //当前年份

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_query_criteria);
        initView();
        initData();
        initListener();
        if (savedInstanceState != null) {
            DatePickerDialog dpd = (DatePickerDialog) getSupportFragmentManager().findFragmentByTag(DATE_PICKER_TAG);
            if (dpd != null) {
                dpd.setOnDateSetListener(this);
            }
        }
    }

    protected void initView() {
        ivTitleBarBack = (ImageView) findViewById(R.id.ivTitleBarBack);
        ivContractIdClear = (ImageView) findViewById(R.id.ivContractIdClear);
        tvTitleBarTitle = (TextView) findViewById(R.id.tvTitleBarTitle);
        tvBusinessQueryStartDate = (TextView) findViewById(R.id.tvBusinessQueryStartDate);
        tvBusinessQueryEndDate = (TextView) findViewById(R.id.tvBusinessQueryEndDate);
        etBusinessQueryContractId = (EditText) findViewById(R.id.etBusinessQueryContractId);
        btnBusinessQueryCancel = (Button) findViewById(R.id.btnBusinessQueryCancel);
        btnBusinessQueryOK = (Button) findViewById(R.id.btnBusinessQueryOK);
    }

    protected void initData() {
        intent = getIntent();
        businessId = intent.getStringExtra(BUSINESS_ID);//获取业务类别名称
        tvTitleBarTitle.setText(getString(R.string.common_business_query));
        ivTitleBarBack.setVisibility(View.VISIBLE);
        calendar = Calendar.getInstance();
        startDatePickerDialog = DatePickerDialog.newInstance(this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), false);
        endDatePickerDialog = DatePickerDialog.newInstance(this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), false);
        date = new Date();
        format = new SimpleDateFormat("yyyy");
        format2 = new SimpleDateFormat("yyyy-MM-dd");
        currentYear = format.format(date);   //获取当前年份
        ClearEditTextTextWatcher textWatcher = new ClearEditTextTextWatcher(BusinessQueryCriteriaActivity.this, etBusinessQueryContractId, ivContractIdClear);
        etBusinessQueryContractId.addTextChangedListener(textWatcher);
    }

    protected void initListener() {
        ivTitleBarBack.setOnClickListener(this);
        ivContractIdClear.setOnClickListener(this);
        btnBusinessQueryCancel.setOnClickListener(this);
        btnBusinessQueryOK.setOnClickListener(this);
        tvBusinessQueryStartDate.setOnClickListener(this);
        tvBusinessQueryEndDate.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivTitleBarBack:  //返回
                finish();
                break;
            case R.id.ivContractIdClear: //清除文本
                etBusinessQueryContractId.setText("");
                break;
            case R.id.tvBusinessQueryStartDate: //开始时间
                startDatePickerDialog.setVibrate(false);
                startDatePickerDialog.setYearRange(1985, Integer.parseInt(currentYear));
                startDatePickerDialog.setCloseOnSingleTapDay(false);
                startDatePickerDialog.show(getSupportFragmentManager(), DATE_PICKER_TAG);
                break;
            case R.id.tvBusinessQueryEndDate:  //结束时间
                endDatePickerDialog.setVibrate(true);
                endDatePickerDialog.setYearRange(1985, Integer.parseInt(currentYear));
                endDatePickerDialog.setCloseOnSingleTapDay(true);
                endDatePickerDialog.show(getSupportFragmentManager(), DATE_PICKER_TAG);
                break;
            case R.id.btnBusinessQueryCancel: // 取消
                finish();
                break;
            case R.id.btnBusinessQueryOK: //确定
                String startTime = tvBusinessQueryStartDate.getText().toString();
                String endTime = tvBusinessQueryEndDate.getText().toString();
                String contractId = etBusinessQueryContractId.getText().toString();
                Intent intent;
                if (!TextUtils.isEmpty(startTime) && !TextUtils.isEmpty(endTime) && TextUtils.isEmpty(contractId)) { //开始时间、结束时间不为空，业务编号为空，可查询
                    intent = new Intent(BusinessQueryCriteriaActivity.this, BusinessQueryResultActivity.class);
                    intent.putExtra(BUSINESS_ID, businessId);
                    intent.putExtra(START_DATE, startTime);
                    intent.putExtra(END_DATE, endTime);
                    startActivity(intent);
                } else if (!TextUtils.isEmpty(contractId) && (TextUtils.isEmpty(startTime) || TextUtils.isEmpty(endTime))) {//输入了业务编号 和其他
                    intent = new Intent(BusinessQueryCriteriaActivity.this, BusinessQueryResultActivity.class);
                    intent.putExtra(BUSINESS_ID, businessId);
                    intent.putExtra(CONTRACT_ID, contractId);
                    startActivity(intent);
                } else if (!TextUtils.isEmpty(startTime) && !TextUtils.isEmpty(endTime) && !TextUtils.isEmpty(contractId)) { //开始时间、结束时间、业务编号都输入
                    intent = new Intent(BusinessQueryCriteriaActivity.this, BusinessQueryResultActivity.class);
                    intent.putExtra(BUSINESS_ID, businessId);
                    intent.putExtra(START_DATE, startTime);
                    intent.putExtra(END_DATE, endTime);
                    intent.putExtra(CONTRACT_ID, contractId);
                    startActivity(intent);
                } else {
                    ToastUtil.showToast(mContext, getString(R.string.common_please_enter_the_useful_criteria));
                }
                break;
            default:
                break;
        }
    }

    /**
     * 处理日期选择回传值
     *
     * @param datePickerDialog 时间选择器
     * @param year             年
     * @param month            月
     * @param day              日
     */
    @Override
    public void onDateSet(DatePickerDialog datePickerDialog, int year, int month, int day) {
        int m = month + 1;
        Date date1; //开始时间
        Date date2; //结束时间
        if (datePickerDialog == startDatePickerDialog) { //如果当前选择器是开始时间选择器
            if ((m / 10) == 1) { //月份是2位数
                if ((day / 10) >= 1) { //日期是2位数
                    tvBusinessQueryStartDate.setText(year + "-" + m + "-" + day);
                } else {
                    tvBusinessQueryStartDate.setText(year + "-" + m + "-0" + day);
                }
            } else {//月份是1位数
                if ((day / 10) >= 1) { //日期2位数
                    tvBusinessQueryStartDate.setText(year + "-0" + m + "-" + day);
                } else {
                    tvBusinessQueryStartDate.setText(year + "-0" + m + "-0" + day);
                }
            }
            String startDate = tvBusinessQueryStartDate.getText().toString();
            try {
                date1 = format2.parse(startDate);
                if (date1.getTime() >= System.currentTimeMillis()) { //开始时间大于当前时间
                    tvBusinessQueryStartDate.setText("");
                    ToastUtil.showToast(mContext, getString(R.string.activity_business_query_end_time_wrong3));
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else if (datePickerDialog == endDatePickerDialog) { //如果当前选择器是结束时间选择器
            if ((m / 10) == 1) { //月份是2位数
                if ((day / 10) >= 1) { //日期是2位数
                    tvBusinessQueryEndDate.setText(year + "-" + m + "-" + day);
                } else {
                    tvBusinessQueryEndDate.setText(year + "-" + m + "-0" + day);
                }
            } else {//月份是1位数
                if ((day / 10) >= 1) { //日期是2位数
                    tvBusinessQueryEndDate.setText(year + "-0" + m + "-" + day);
                } else {
                    tvBusinessQueryEndDate.setText(year + "-0" + m + "-0" + day);
                }
            }
            String endDate = tvBusinessQueryEndDate.getText().toString();
            try {
                date2 = format2.parse(endDate);
                if (date2.getTime() > System.currentTimeMillis()) { //结束时间大于当前时间
                    tvBusinessQueryEndDate.setText("");
                    ToastUtil.showToast(mContext, getString(R.string.activity_business_query_end_time_wrong2));
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        //对开始时间和结束时间进行判断
        String startDate = tvBusinessQueryStartDate.getText().toString();
        String endDate = tvBusinessQueryEndDate.getText().toString();
        try {
            date1 = format2.parse(startDate);
            date2 = format2.parse(endDate);
            if (date2.getTime() < date1.getTime()) { //结束时间小于开始时间
                tvBusinessQueryEndDate.setText("");
                ToastUtil.showToast(mContext, getString(R.string.activity_business_query_end_time_wrong));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

}
