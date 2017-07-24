package com.sf.bocfinancialsoftware.activity.tool.finance;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jude.rollviewpager.RollPagerView;
import com.sf.bocfinancialsoftware.R;
import com.sf.bocfinancialsoftware.adapter.RollViewPagerAdapter;
import com.sf.bocfinancialsoftware.base.BaseActivity;
import com.sf.bocfinancialsoftware.bean.finance.FinanceBean;
import com.sf.bocfinancialsoftware.bean.finance.FinanceDetailBean;
import com.sf.bocfinancialsoftware.constant.ConstantConfig;
import com.sf.bocfinancialsoftware.constant.URLConfig;
import com.sf.bocfinancialsoftware.util.NetWorkUtils;
import com.sf.bocfinancialsoftware.util.ToastUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.sf.bocfinancialsoftware.R.id.tvTitle;


public class FinanceProductDetailActivity extends BaseActivity {

    private ImageView ivBack;//返回按钮
    private TextView mTvTitle;//标题
    private WebView webViewFinancial;//
    private RollPagerView rollPagerView; //图片轮播
    private RollViewPagerAdapter mRollViewPagerAdapter;
//    private ScrollView scrollView;
//    private LinearLayout dataEmpty;//数据为空时

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finalysis_detail);
        initView();
        initData();
        initListener();
    }

    @Override
    protected void initView() {
        webViewFinancial = (WebView) findViewById(R.id.webViewFinancial);
        mTvTitle = (TextView) findViewById(tvTitle);
        ivBack = (ImageView) findViewById(R.id.ivBack);
        rollPagerView = (RollPagerView) findViewById(R.id.rollPagerView);
//        dataEmpty = (LinearLayout) findViewById(R.id.dataEmpty);
//        scrollView = (ScrollView) findViewById(R.id.scrollView);
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        FinanceBean.ContentBean.ProductListBean productListBean = (FinanceBean.ContentBean.ProductListBean) intent.getSerializableExtra(ConstantConfig.FINANCE_PRODUCT_KEY_SER);
        mTvTitle.setText(productListBean.getProductName());
        //图片轮播
        mRollViewPagerAdapter = new RollViewPagerAdapter(mContext);
        rollPagerView.setAdapter(mRollViewPagerAdapter);
        //获取网络请求
        getProductDetail(productListBean.getProductId());
        //获取图片轮播
        getProductImage(productListBean.getProductId());
    }

    /**
     * 获取轮播图片
     *
     * @param productId
     */
    private void getProductImage(String productId) {
        HashMap<String, String> imageMap = new HashMap<>();
        imageMap.put("productId", productId);
        NetWorkUtils.doPost(mContext, URLConfig.FINANCE_PRODUCT_DETAIL, imageMap, new NetWorkUtils.RequestCallBack() {
            @Override
            public void onSuccess(String json) {
                FinanceDetailBean jsonBean = new Gson().fromJson(json, FinanceDetailBean.class);
                if (jsonBean != null) {
                    List<FinanceDetailBean.ContentBean.ImageListBean> imageList = jsonBean.getContent().getImageList();
                    List<String> imageUrl = new ArrayList<>();
                    for (FinanceDetailBean.ContentBean.ImageListBean imageListBean : imageList) {
                        imageUrl.add(imageListBean.getImageUrl());
                    }
                    mRollViewPagerAdapter.setLoop(imageUrl);
                }
            }

            @Override
            public void onError() {
                ToastUtil.showToast(mContext, getString(R.string.common_refresh_failed));
            }
        });
    }

    private void getProductDetail(String productId) {
        HashMap<String, String> map = new HashMap<>();
        map.put("productId", productId);
        NetWorkUtils.doPost(mContext, URLConfig.FINANCE_PRODUCT_DETAIL, map, new NetWorkUtils.RequestCallBack() {
            @Override
            public void onSuccess(String json) {
                FinanceDetailBean jsonBean = new Gson().fromJson(json, FinanceDetailBean.class);
                Log.e("", "onSuccess() called with: json = [" + json + "]");
                if (jsonBean != null) {
                    String htmlContent = jsonBean.getContent().getHtmlContent();
                    webViewFinancial.loadUrl(htmlContent);
                }
            }

            @Override
            public void onError() {
                ToastUtil.showToast(mContext, getString(R.string.common_refresh_failed));
            }
        });
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

}
