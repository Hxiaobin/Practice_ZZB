package com.sf.bocfinancialsoftware.activity.tool.finance;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.sf.bocfinancialsoftware.adapter.RollViewPagerAdapter;
import com.sf.bocfinancialsoftware.R;
import com.jude.rollviewpager.RollPagerView;

public class FinanceProductDetailActivity extends AppCompatActivity {
    private RollPagerView rollPagerView; //图片轮播
    private RollViewPagerAdapter mRollViewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finalysis_detail);
        initView();
        initDates();
    }

    private void initDates() {
        //图片轮播
        mRollViewPagerAdapter = new RollViewPagerAdapter();
        rollPagerView.setAdapter(mRollViewPagerAdapter);
    }

    private void initView() {
        WebView webViewFinancial = (WebView) findViewById(R.id.webViewFinancial);
        TextView tvTitle = (TextView) findViewById(R.id.tvTitle);
        ImageView ivBack = (ImageView) findViewById(R.id.ivBack);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        rollPagerView = (RollPagerView) findViewById(R.id.rollPagerView);
        WebSettings webSettings = webViewFinancial.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webViewFinancial.loadUrl("file:///android_asset/financialdetail.html");
        Intent intent = getIntent();
        tvTitle.setText(intent.getStringExtra("product"));
    }

}
