package com.sf.bocfinancialsoftware.activity.tool.rate;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.sf.bocfinancialsoftware.adapter.RateAdapter;
import com.sf.bocfinancialsoftware.bean.RateBean;
import com.sf.bocfinancialsoftware.R;

import java.util.ArrayList;
import java.util.List;

public class RateActivity extends AppCompatActivity {

    private List<RateBean> mDatas;
    private ListView listview;
    private LinearLayout headView, footView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate);
        initView();
        initData();
    }

    private void initView() {
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

    private void initData() {
        mDatas = new ArrayList<>();
        mDatas.add(new RateBean("美元", "期限", "6.2172", "6.2418"));
        mDatas.add(new RateBean("美元", "1年", "6.2813", "6.3147"));
        mDatas.add(new RateBean("美元", "6个月", "6.3184", "6.3522"));
        mDatas.add(new RateBean("美元", "1年", "6.3795", "6.4164"));
        mDatas.add(new RateBean("欧元", "期限", "7.2172", "7.2418"));
        mDatas.add(new RateBean("欧元", "3个月", "7.2813", "7.3147"));
        mDatas.add(new RateBean("欧元", "6个月", "7.3184", "7.3522"));
        mDatas.add(new RateBean("欧元", "1年", "7.3795", "7.4164"));
        mDatas.add(new RateBean("日元", "3个月", "7.6172", "7.6418"));
        mDatas.add(new RateBean("日元", "6个月", "7.6813", "7.6147"));
        mDatas.add(new RateBean("日元", "1年", "7.6184", "7.6522"));
        mDatas.add(new RateBean("日元", "1年", "7.6795", "7.6164"));
        listview.addHeaderView(headView, null, false);
        listview.addFooterView(footView, null, false);
        listview.setAdapter(new RateAdapter(mDatas, this));
    }

}
