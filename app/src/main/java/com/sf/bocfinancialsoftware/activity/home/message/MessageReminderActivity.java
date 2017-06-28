package com.sf.bocfinancialsoftware.activity.home.message;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sf.bocfinancialsoftware.R;
import com.sf.bocfinancialsoftware.activity.base.BaseActivity;
import com.sf.bocfinancialsoftware.bean.MessageReminderBean;
import com.sf.bocfinancialsoftware.constant.ConstantConfig;
import com.sf.bocfinancialsoftware.util.DataBaseSQLiteUtil;

import java.util.List;

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
import static com.sf.bocfinancialsoftware.constant.ConstantConfig.MSG_READ_SUM;
import static com.sf.bocfinancialsoftware.constant.ConstantConfig.MSG_TYPE_ID_EXPORT;
import static com.sf.bocfinancialsoftware.constant.ConstantConfig.MSG_TYPE_ID_FACTORING;
import static com.sf.bocfinancialsoftware.constant.ConstantConfig.MSG_TYPE_ID_FORWARD;
import static com.sf.bocfinancialsoftware.constant.ConstantConfig.MSG_TYPE_ID_GUARANTEE;
import static com.sf.bocfinancialsoftware.constant.ConstantConfig.MSG_TYPE_ID_IMPORT;
import static com.sf.bocfinancialsoftware.constant.ConstantConfig.MSG_UN_READ;

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
    private int importUnReadSum; //进口通知总未读数量
    private int exportUnReadSum;  //出口通知总未读数量
    private int guaranteeUnReadSum;  //保函通知总未读数量
    private int factoringUnReadSum;  //保理通知总未读数量
    private int forwardUnReadSum;   //远期通知总未读数量

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
        //进口通知未读数量
        importUnReadSum = getUnReadMessageNumber(MSG_TYPE_ID_IMPORT);
        showMsgUnReadSum(importUnReadSum, tvImportUnReadSum);
        //出口通知未读数量
        exportUnReadSum = getUnReadMessageNumber(MSG_TYPE_ID_EXPORT);
        showMsgUnReadSum(exportUnReadSum, tvExportUnReadSum);
        //保函通知未读数量
        guaranteeUnReadSum = getUnReadMessageNumber(MSG_TYPE_ID_GUARANTEE);
        showMsgUnReadSum(guaranteeUnReadSum, tvGuaranteeUnReadSum);
        //保理通知未读数量
        factoringUnReadSum = getUnReadMessageNumber(MSG_TYPE_ID_FACTORING);
        showMsgUnReadSum(factoringUnReadSum, tvFactoringUnReadSum);
        //远期通知未读数量
        forwardUnReadSum = getUnReadMessageNumber(MSG_TYPE_ID_FORWARD);
        showMsgUnReadSum(forwardUnReadSum, tvForwardUnReadSum);
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
            case R.id.ivTitleBarBack:
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
        if (requestCode == IMPORT_REQUEST && resultCode == IMPORT_RESPONSE) { //进口通知未读数量
            int unReadSum = importUnReadSum - data.getIntExtra(MSG_READ_SUM, 0); //剩余未读数量 = 总未读数量-已读数量
            showMsgUnReadSum(unReadSum, tvImportUnReadSum);
        } else if (requestCode == EXPORT_REQUEST && resultCode == EXPORT_RESPONSE) { //出口通知未读数量
            int unReadSum = exportUnReadSum - data.getIntExtra(MSG_READ_SUM, 0); //剩余未读数量 = 总未读数量-已读数量
            showMsgUnReadSum(unReadSum, tvExportUnReadSum);
        } else if (requestCode == GUARANTEE_REQUEST && resultCode == GUARANTEE_RESPONSE) { //保函通知未读数量
            int unReadSum = guaranteeUnReadSum - data.getIntExtra(MSG_READ_SUM, 0); //剩余未读数量 = 总未读数量-已读数量
            showMsgUnReadSum(unReadSum, tvGuaranteeUnReadSum);
        } else if (requestCode == FACTORING_REQUEST && resultCode == FACTORING_RESPONSE) { //保理通知未读数量
            int unReadSum = factoringUnReadSum - data.getIntExtra(MSG_READ_SUM, 0); //剩余未读数量 = 总未读数量-已读数量
            showMsgUnReadSum(unReadSum, tvFactoringUnReadSum);
        } else if (requestCode == FORWARD_REQUEST && resultCode == FORWARD_RESPONSE) { //远期通知未读数量
            int unReadSum = forwardUnReadSum - data.getIntExtra(MSG_READ_SUM, 0); //剩余未读数量 = 总未读数量-已读数量
            showMsgUnReadSum(unReadSum, tvForwardUnReadSum);
        }
    }

    /**
     * 打开Activity的时候，遍历消息列表获取总未读消息数量
     */
    private int getUnReadMessageNumber(String typeId) {
        List<MessageReminderBean> msgList = DataBaseSQLiteUtil.queryAllMessageByTypeAndTitle(typeId, "");//进口通知列表
        int unReadSum = 0;
        for (MessageReminderBean bean : msgList) {
            if (bean.getMsgIsRead().equals(MSG_UN_READ)) { //如果消息状态为未读状态
                unReadSum++;
            }
        }
        return unReadSum;
    }

    /**
     * 处理未读消息的显示问题
     *
     * @param unReadSum
     * @param tv
     */
    private void showMsgUnReadSum(int unReadSum, TextView tv) {
        if (unReadSum <= 0) {
            tv.setVisibility(View.GONE);
        } else {
            tv.setText(String.valueOf(unReadSum));
        }
    }

}
