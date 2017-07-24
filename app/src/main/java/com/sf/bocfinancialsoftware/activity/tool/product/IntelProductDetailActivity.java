package com.sf.bocfinancialsoftware.activity.tool.product;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jude.rollviewpager.RollPagerView;
import com.sf.bocfinancialsoftware.R;
import com.sf.bocfinancialsoftware.adapter.RollViewPagerAdapter;
import com.sf.bocfinancialsoftware.base.BaseActivity;
import com.sf.bocfinancialsoftware.bean.product.IntelProductDetailBean;
import com.sf.bocfinancialsoftware.bean.product.IntelProductListBean;
import com.sf.bocfinancialsoftware.constant.ConstantConfig;
import com.sf.bocfinancialsoftware.constant.URLConfig;
import com.sf.bocfinancialsoftware.util.NetWorkUtils;
import com.sf.bocfinancialsoftware.util.ToastUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class IntelProductDetailActivity extends BaseActivity {
    private RollPagerView rollPagerView; //图片轮播
    private WebView webViewFinancial;//webView
    private TextView tvTitle;//标题
    private ImageView ivBack;//返回按钮
    private RollViewPagerAdapter mRollViewPagerAdapter;

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
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        ivBack = (ImageView) findViewById(R.id.ivBack);
        rollPagerView = (RollPagerView) findViewById(R.id.rollPagerView);
    }

    @Override
    protected void initData() {
        //获取标题
        Intent intent = getIntent();
        IntelProductListBean.ContentBean.TypeListBean.ProductArrayBean productArrayBean = (IntelProductListBean.ContentBean.TypeListBean.ProductArrayBean) intent.getSerializableExtra(ConstantConfig.INTEL_PRODUCT_KEY_SER);
        tvTitle.setText(productArrayBean.getProductName());
        //图片轮播
        mRollViewPagerAdapter = new RollViewPagerAdapter(mContext);
        rollPagerView.setAdapter(mRollViewPagerAdapter);
        //获取图片轮播
        getImageData(productArrayBean.getProductId());
        //获取网络数据
        getNetWorkData(productArrayBean.getProductId());
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

    public void getNetWorkData(String productId) {
        HashMap<String, String> detailMap = new HashMap<>();
        detailMap.put("productId", productId);
        NetWorkUtils.doPost(mContext, URLConfig.PRODUCT_DETAIL_CASE, detailMap, new NetWorkUtils.RequestCallBack() {
            @Override
            public void onSuccess(String json) {
                IntelProductDetailBean jsonBean = new Gson().fromJson(json, IntelProductDetailBean.class);
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

    public void getImageData(String productId) {
        HashMap<String, String> imageMap = new HashMap<>();
        imageMap.put("productId", productId);
        NetWorkUtils.doPost(mContext, URLConfig.PRODUCT_DETAIL_CASE, imageMap, new NetWorkUtils.RequestCallBack() {
            @Override
            public void onSuccess(String json) {
                IntelProductDetailBean intelProductDetailBean = new Gson().fromJson(json, IntelProductDetailBean.class);
                if (intelProductDetailBean != null) {
                    List<IntelProductDetailBean.ContentBean.ImageListBean> imageList = intelProductDetailBean.getContent().getImageList();
                    List<String> images = new ArrayList<>();
                    for (IntelProductDetailBean.ContentBean.ImageListBean imageListBean : imageList) {
                        images.add(imageListBean.getImageUrl());
                    }
                    mRollViewPagerAdapter.setLoop(images);
                }
            }

            @Override
            public void onError() {
                ToastUtil.showToast(mContext, getString(R.string.common_refresh_failed));
            }
        });
    }
}
