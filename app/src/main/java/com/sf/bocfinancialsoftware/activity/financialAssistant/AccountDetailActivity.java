package com.sf.bocfinancialsoftware.activity.financialAssistant;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.sf.bocfinancialsoftware.R;
import com.sf.bocfinancialsoftware.adapter.account.AccountDetailAdapter;
import com.sf.bocfinancialsoftware.base.BaseActivity;
import com.sf.bocfinancialsoftware.bean.account.AccountBean;
import com.sf.bocfinancialsoftware.bean.account.AccountDetailBean;
import com.sf.bocfinancialsoftware.constant.ConstantConfig;
import com.sf.bocfinancialsoftware.constant.URLConfig;
import com.sf.bocfinancialsoftware.util.NetWorkUtils;
import com.sf.bocfinancialsoftware.util.ToastUtil;

import java.util.HashMap;

public class AccountDetailActivity extends BaseActivity {

    private  ImageView ivBack;//返回按钮
    private TextView tvAccountDetail1;
    private TextView tvBalance;
    private TextView tvPay;
    private ListView lvAccountDetail;//
    private AccountDetailAdapter mDetailAdapter;
    private View headView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_detail);
        initView();
        initData();
        initListener();
    }

    @Override
    protected void initView() {
        headView = View.inflate(mContext, R.layout.layout_account_detail_head, null);
        tvAccountDetail1 = (TextView) headView.findViewById(R.id.tvAccountDetail1);
        tvBalance = (TextView) headView.findViewById(R.id.tvBalance);
        tvPay = (TextView) headView.findViewById(R.id.tvPay);
        lvAccountDetail = (ListView) findViewById(R.id.lvAccountDetail);
        TextView ivTitle = (TextView) findViewById(R.id.tvTitle);
        ivTitle.setText(R.string.text_financial_assistant);
        ivBack = (ImageView) findViewById(R.id.ivBack);
    }

    @Override
    protected void initData() {
        //改变账号
        Intent intent = getIntent();
        AccountBean.ContentBean.AccountListBean account = (AccountBean.ContentBean.AccountListBean) intent.getSerializableExtra(ConstantConfig.INTENT_KEY_SER);
        tvAccountDetail1.setText(account.getAccountName());
        tvBalance.setText(account.getBalance());
        tvPay.setText(account.getPayable());
        mDetailAdapter = new AccountDetailAdapter(mContext);
        lvAccountDetail.addHeaderView(headView);
        lvAccountDetail.setAdapter(mDetailAdapter);
        getAccountDetail(account.getAccountId());
    }

    @Override
    protected void initListener() {
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    private void getAccountDetail(String accountId) {
        HashMap<String, String> accountMap = new HashMap<>();
        accountMap.put("accountId", accountId);
        NetWorkUtils.doPost(mContext, URLConfig.ACCOUNT_DETAIL_CASE, accountMap, new NetWorkUtils.RequestCallBack() {
            @Override
            public void onSuccess(String json) {
                AccountDetailBean jsonBean = new Gson().fromJson(json, AccountDetailBean.class);
                Log.e("", "onSuccess() called with: json = [" + json + "]");
                if (jsonBean != null) {
                    mDetailAdapter.setContractListDatas(jsonBean.getContent().getContractList());
                }
            }

            @Override
            public void onError() {
                ToastUtil.showToast(mContext, "onError");
                Log.d("", "onError() called");
            }
        });
    }

}
