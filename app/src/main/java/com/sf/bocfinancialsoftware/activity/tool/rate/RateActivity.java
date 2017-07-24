package com.sf.bocfinancialsoftware.activity.tool.rate;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.sf.bocfinancialsoftware.R;
import com.sf.bocfinancialsoftware.adapter.rate.RateAdapter;
import com.sf.bocfinancialsoftware.base.BaseActivity;
import com.sf.bocfinancialsoftware.bean.rate.ExchangeRateBean;
import com.sf.bocfinancialsoftware.constant.URLConfig;
import com.sf.bocfinancialsoftware.util.NetWorkUtils;
import com.sf.bocfinancialsoftware.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

public class RateActivity extends BaseActivity {

    private List<ExchangeRateBean.ContentBean> mDatas = new ArrayList<>();
    private ListView listview;
    private LinearLayout headView, footView;
    private RateAdapter rateAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate);
        initView();
        initData();
        initListener();
    }

    @Override
    protected void initView() {
        TextView tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvTitle.setText(R.string.tv_rate);
        ImageView ivBack = (ImageView) findViewById(R.id.ivBack);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        listview = (ListView) findViewById(R.id.listview);
        headView = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.layout_rate_head, null);
        footView = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.layout_bottom_content, null);
    }

    @Override
    protected void initData() {
        listview.setVisibility(View.INVISIBLE);
        listview.addHeaderView(headView, null, false);
        listview.addFooterView(footView, null, false);
        rateAdapter = new RateAdapter(mContext);
        listview.setAdapter(rateAdapter);
        NetWorkUtils.doPost(mContext, URLConfig.RATE_RESULT, new NetWorkUtils.RequestCallBack() {
            @Override
            public void onSuccess(String json) {
                ExchangeRateBean jsonBean = new Gson().fromJson(json, ExchangeRateBean.class);
                if (jsonBean != null) {
                    mDatas.clear();
                    mDatas.addAll(jsonBean.getContent());
                    rateAdapter.setDatas(jsonBean.getContent());
                    listview.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onError() {
                listview.setVisibility(View.INVISIBLE);
                ToastUtil.showToast(mContext, "onError");
            }
        });
    }

    @Override
    protected void initListener() {

    }

}
