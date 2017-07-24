package com.sf.bocfinancialsoftware.activity.home.message;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.sf.bocfinancialsoftware.R;
import com.sf.bocfinancialsoftware.base.BaseActivity;
import com.sf.bocfinancialsoftware.bean.UnReadMsgBean;
import com.sf.bocfinancialsoftware.constant.ConstantConfig;
import com.sf.bocfinancialsoftware.http.HttpCallBackListener;
import com.sf.bocfinancialsoftware.http.HttpUtil;

import java.util.HashMap;

import static com.sf.bocfinancialsoftware.constant.ConstantConfig.EXPORT_REQUEST;
import static com.sf.bocfinancialsoftware.constant.ConstantConfig.EXPORT_RESPONSE;
import static com.sf.bocfinancialsoftware.constant.ConstantConfig.FACTORING_REQUEST;
import static com.sf.bocfinancialsoftware.constant.ConstantConfig.FACTORING_RESPONSE;
import static com.sf.bocfinancialsoftware.constant.ConstantConfig.FORWARD_REQUEST;
import static com.sf.bocfinancialsoftware.constant.ConstantConfig.FORWARD_RESPONSE;
import static com.sf.bocfinancialsoftware.constant.ConstantConfig.GUARANTEE_REQUEST;
import static com.sf.bocfinancialsoftware.constant.ConstantConfig.GUARANTEE_RESPONSE;
import static com.sf.bocfinancialsoftware.constant.ConstantConfig.IMPORT_REQUEST;
import static com.sf.bocfinancialsoftware.constant.ConstantConfig.IMPORT_RESPONSE;
import static com.sf.bocfinancialsoftware.constant.ConstantConfig.MSG_TOTAL_READ_SUM;
import static com.sf.bocfinancialsoftware.constant.ConstantConfig.MSG_TOTAL_UN_REN_SUM_RESPONSE;
import static com.sf.bocfinancialsoftware.constant.ConstantConfig.MSG_TYPE_ID;
import static com.sf.bocfinancialsoftware.constant.ConstantConfig.MSG_TYPE_ID_EXPORT;
import static com.sf.bocfinancialsoftware.constant.ConstantConfig.MSG_TYPE_ID_FACTORING;
import static com.sf.bocfinancialsoftware.constant.ConstantConfig.MSG_TYPE_ID_FORWARD;
import static com.sf.bocfinancialsoftware.constant.ConstantConfig.MSG_TYPE_ID_GUARANTEE;
import static com.sf.bocfinancialsoftware.constant.ConstantConfig.MSG_TYPE_ID_IMPORT;
import static com.sf.bocfinancialsoftware.constant.URLConfig.MESSAGE_UN_READ_URL;

/**
 * 通知提醒页面
 * Created by sn on 2017/6/12.
 */

public class MessageReminderActivity extends BaseActivity implements View.OnClickListener {

    private ImageView ivTitleBarBack;//返回
    private TextView tvTitleBarTitle;//标题
    private TextView tvImportUnReadSum; //进口消息未读数量
    private TextView tvExportUnReadSum; //出口消息未读数量
    private TextView tvGuaranteeUnReadSum; //保函消息未读数量
    private TextView tvFactoringUnReadSum; //保理消息未读数量
    private TextView tvForwardUnReadSum; //远期消息未读数量
    private LinearLayout lltMessageReminderImport; // 进口提醒
    private LinearLayout lltMessageReminderExport; // 出口提醒
    private LinearLayout lltMessageReminderGuarantee; // 保函提醒
    private LinearLayout lltMessageReminderFactoring; // 保理提醒
    private LinearLayout lltMessageReminderForward; // 远期提醒
    private UnReadMsgBean.Content unReadMsgContent;
    private HashMap<String, String> map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_reminder);
        initView();
        initData();
        initListener();
    }

    @Override
    protected void initView() {
        ivTitleBarBack = (ImageView) findViewById(R.id.ivTitleBarBack);
        tvTitleBarTitle = (TextView) findViewById(R.id.tvTitleBarTitle);
        tvImportUnReadSum = (TextView) findViewById(R.id.tvImportUnReadSum);
        tvExportUnReadSum = (TextView) findViewById(R.id.tvExportUnReadSum);
        tvGuaranteeUnReadSum = (TextView) findViewById(R.id.tvGuaranteeUnReadSum);
        tvFactoringUnReadSum = (TextView) findViewById(R.id.tvFactoringUnReadSum);
        tvForwardUnReadSum = (TextView) findViewById(R.id.tvForwardUnReadSum);
        lltMessageReminderImport = (LinearLayout) findViewById(R.id.lltMessageReminderImport);
        lltMessageReminderExport = (LinearLayout) findViewById(R.id.lltMessageReminderExport);
        lltMessageReminderGuarantee = (LinearLayout) findViewById(R.id.lltMessageReminderGuarantee);
        lltMessageReminderFactoring = (LinearLayout) findViewById(R.id.lltMessageReminderFactoring);
        lltMessageReminderForward = (LinearLayout) findViewById(R.id.lltMessageReminderForward);
    }

    @Override
    protected void initData() {
        tvTitleBarTitle.setText(getString(R.string.common_message_reminder));
        ivTitleBarBack.setVisibility(View.VISIBLE);
        map = new HashMap<>();
        getNetworkData(MESSAGE_UN_READ_URL, map);  // 请求网络获取各类通知未读消息数量
    }

    @Override
    protected void initListener() {
        ivTitleBarBack.setOnClickListener(this);
        lltMessageReminderImport.setOnClickListener(this);
        lltMessageReminderExport.setOnClickListener(this);
        lltMessageReminderGuarantee.setOnClickListener(this);
        lltMessageReminderFactoring.setOnClickListener(this);
        lltMessageReminderForward.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.ivTitleBarBack: //返回
                intent = new Intent();
                //获取当前各类通知的未读消息数量
                int sum1 = Integer.valueOf(tvImportUnReadSum.getText().toString());
                int sum2 = Integer.valueOf(tvExportUnReadSum.getText().toString());
                int sum3 = Integer.valueOf(tvGuaranteeUnReadSum.getText().toString());
                int sum4 = Integer.valueOf(tvFactoringUnReadSum.getText().toString());
                int sum5 = Integer.valueOf(tvForwardUnReadSum.getText().toString());
                int totalUnReadSum = sum1 + sum2 + sum3 + sum4 + sum5;
                intent.putExtra(MSG_TOTAL_READ_SUM, totalUnReadSum);
                setResult(MSG_TOTAL_UN_REN_SUM_RESPONSE, intent);
                finish();
                break;
            case R.id.lltMessageReminderImport: //进口通知
                intent = new Intent(MessageReminderActivity.this, ImportMessageListActivity.class);
                intent.putExtra(ConstantConfig.MSG_TYPE_ID, MSG_TYPE_ID_IMPORT);
                startActivityForResult(intent, IMPORT_REQUEST);
                break;
            case R.id.lltMessageReminderExport: //出口通知
                intent = new Intent(MessageReminderActivity.this, ExportMessageListActivity.class);
                intent.putExtra(ConstantConfig.MSG_TYPE_ID, MSG_TYPE_ID_EXPORT);
                startActivityForResult(intent, EXPORT_REQUEST);
                break;
            case R.id.lltMessageReminderGuarantee: //保函通知
                intent = new Intent(MessageReminderActivity.this, GuaranteeMessageListActivity.class);
                intent.putExtra(ConstantConfig.MSG_TYPE_ID, MSG_TYPE_ID_GUARANTEE);
                startActivityForResult(intent, GUARANTEE_REQUEST);
                break;
            case R.id.lltMessageReminderFactoring: //保理通知
                intent = new Intent(MessageReminderActivity.this, FactoringMessageListActivity.class);
                intent.putExtra(ConstantConfig.MSG_TYPE_ID, MSG_TYPE_ID_FACTORING);
                startActivityForResult(intent, FACTORING_REQUEST);
                break;
            case R.id.lltMessageReminderForward:  //远期通知
                intent = new Intent(MessageReminderActivity.this, ForwardMessageListActivity.class);
                intent.putExtra(ConstantConfig.MSG_TYPE_ID, MSG_TYPE_ID_FORWARD);
                startActivityForResult(intent, FORWARD_REQUEST);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        map.clear();
        if (requestCode == IMPORT_REQUEST && resultCode == IMPORT_RESPONSE) { //进口通知未读数量
            map.put(MSG_TYPE_ID, MSG_TYPE_ID_IMPORT);
        } else if (requestCode == EXPORT_REQUEST && resultCode == EXPORT_RESPONSE) { //出口通知未读数量
            map.put(MSG_TYPE_ID, MSG_TYPE_ID_EXPORT);
        } else if (requestCode == GUARANTEE_REQUEST && resultCode == GUARANTEE_RESPONSE) { //保函通知未读数量
            map.put(MSG_TYPE_ID, MSG_TYPE_ID_GUARANTEE);
        } else if (requestCode == FACTORING_REQUEST && resultCode == FACTORING_RESPONSE) { //保理通知未读数量
            map.put(MSG_TYPE_ID, MSG_TYPE_ID_FACTORING);
        } else if (requestCode == FORWARD_REQUEST && resultCode == FORWARD_RESPONSE) { //远期通知未读数量
            map.put(MSG_TYPE_ID, MSG_TYPE_ID_FORWARD);
        }
        getNetworkData(MESSAGE_UN_READ_URL, map);  // 请求网络获取各类通知未读消息数量
    }

    /**
     * 处理未读消息的显示问题
     *
     * @param unReadSum
     * @param tv
     */
    private void showMsgUnReadSum(int unReadSum, TextView tv) {
        if (unReadSum <= 0) {
            tv.setText("0");
            tv.setVisibility(View.GONE);
        } else {
            tv.setText(String.valueOf(unReadSum));
            tv.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 请求服务器，获取未读消息数量
     *
     * @param url       请求url
     * @param mapObject 存放请求参数
     */
    public void getNetworkData(String url, final HashMap<String, String> mapObject) {
        HttpUtil.getNetworksJSonResponse(MessageReminderActivity.this, url, mapObject, new HttpCallBackListener() {

            @Override
            public void onSuccess(String response) {
                Gson gson = new Gson();
                UnReadMsgBean bean = gson.fromJson(response, UnReadMsgBean.class);
                unReadMsgContent = bean.getContent();
                int importUnReadSum = Integer.valueOf(unReadMsgContent.getType1());
                int exportUnReadSum = Integer.valueOf(unReadMsgContent.getType2());
                int guaranteeUnReadSum = Integer.valueOf(unReadMsgContent.getType3());
                int factoringUnReadSum = Integer.valueOf(unReadMsgContent.getType4());
                int forwardUnReadSum = Integer.valueOf(unReadMsgContent.getType5());
                showMsgUnReadSum(importUnReadSum, tvImportUnReadSum);
                showMsgUnReadSum(exportUnReadSum, tvExportUnReadSum);
                showMsgUnReadSum(guaranteeUnReadSum, tvGuaranteeUnReadSum);
                showMsgUnReadSum(factoringUnReadSum, tvFactoringUnReadSum);
                showMsgUnReadSum(forwardUnReadSum, tvForwardUnReadSum);
            }

            @Override
            public void onFailed(String msg) {
                tvImportUnReadSum.setVisibility(View.GONE);
                tvExportUnReadSum.setVisibility(View.GONE);
                tvGuaranteeUnReadSum.setVisibility(View.GONE);
                tvFactoringUnReadSum.setVisibility(View.GONE);
                tvForwardUnReadSum.setVisibility(View.GONE);
            }

            @Override
            public void onError(Exception e) {
                tvImportUnReadSum.setVisibility(View.GONE);
                tvExportUnReadSum.setVisibility(View.GONE);
                tvGuaranteeUnReadSum.setVisibility(View.GONE);
                tvFactoringUnReadSum.setVisibility(View.GONE);
                tvForwardUnReadSum.setVisibility(View.GONE);
            }
        });
    }

}
