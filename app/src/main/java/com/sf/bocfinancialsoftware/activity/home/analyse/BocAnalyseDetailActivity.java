package com.sf.bocfinancialsoftware.activity.home.analyse;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jude.rollviewpager.RollPagerView;
import com.sf.bocfinancialsoftware.R;
import com.sf.bocfinancialsoftware.adapter.home.advertisement.ImageAdapter;
import com.sf.bocfinancialsoftware.base.BaseActivity;
import com.sf.bocfinancialsoftware.bean.analysis.BocAnalyseBean;
import com.sf.bocfinancialsoftware.http.HttpCallBackListener;
import com.sf.bocfinancialsoftware.http.HttpUtil;
import com.sf.bocfinancialsoftware.util.ToastUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.sf.bocfinancialsoftware.constant.ConstantConfig.BOC_ANALYSE_NEWS_ID;
import static com.sf.bocfinancialsoftware.constant.ConstantConfig.NEWS_ID;
import static com.sf.bocfinancialsoftware.constant.URLConfig.BOC_ANALYSE_NEWS_DETAIL_URL;

/**
 * 中银分析详情
 * Created by sn on 2017/6/9.
 */

public class BocAnalyseDetailActivity extends BaseActivity {

    private ImageView ivTitleBarBack; //返回按钮
    private TextView tvTitleBarTitle; //标题
    private WebView webViewBocAnalyseDetail; //Html正文内容
    private RollPagerView rollPagerViewBoc; //图片轮播
    private LinearLayout lltBocAnalyseDetailLoading; //正在加载
    private LinearLayout lltBocAnalyseDetailNoData; //没有数据
    private LinearLayout lltBocAnalyseContent; //详情内容
    private ImageAdapter adapter;  //图片轮播适配器
    private List<BocAnalyseBean.Content.NewsImageBean> imageBeans; //轮播图片对象
    private List<String> imageList;  //图片轮播适配器
    private String htmlContent;  //网络正文
    private Intent intent;
    private String newsId; //新闻id
    private HashMap<String, String> map; //保存请求参数

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boc_analyse_detail);
        initView();
        initData();
        initListener();
    }

    @Override
    protected void initView() {
        ivTitleBarBack = (ImageView) findViewById(R.id.ivTitleBarBack);
        tvTitleBarTitle = (TextView) findViewById(R.id.tvTitleBarTitle);
        webViewBocAnalyseDetail = (WebView) findViewById(R.id.webViewBocAnalyseDetail);
        rollPagerViewBoc = (RollPagerView) findViewById(R.id.rollPagerViewBoc);
        lltBocAnalyseDetailLoading = (LinearLayout) findViewById(R.id.lltBocAnalyseDetailLoading);
        lltBocAnalyseDetailNoData = (LinearLayout) findViewById(R.id.lltBocAnalyseDetailNoData);
        lltBocAnalyseContent = (LinearLayout) findViewById(R.id.lltBocAnalyseContent);
    }

    @Override
    protected void initData() {
        tvTitleBarTitle.setText(getString(R.string.common_boc_analyse));
        ivTitleBarBack.setVisibility(View.VISIBLE);
        intent = getIntent();
        newsId = intent.getStringExtra(NEWS_ID); //获取上一页面传递的newsId
        WebSettings webSettings = webViewBocAnalyseDetail.getSettings();
        webSettings.setJavaScriptEnabled(true);
        imageBeans = new ArrayList<>();  //图片对象集合
        imageList = new ArrayList<>();  //图片URL集合
        adapter = new ImageAdapter(BocAnalyseDetailActivity.this, imageList);
        rollPagerViewBoc.setAdapter(adapter);
        requestNetworkData();//请求网络
    }

    @Override
    protected void initListener() {
        ivTitleBarBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    /**
     * 首次请求网络
     */
    private void requestNetworkData() {
        lltBocAnalyseDetailLoading.setVisibility(View.VISIBLE);  //显示正在加载
        lltBocAnalyseContent.setVisibility(View.GONE); //
        map = new HashMap<>();
        map.put(BOC_ANALYSE_NEWS_ID, newsId);
        String strSuccess = getString(R.string.common_request_success);  //请求成功提示
        String strError = getString(R.string.common_request_failed);  //请求失败提示
        getNetworkData(map, strSuccess, strError);  // 请求网络
    }

    /**
     * 获取网络数据
     *
     * @return
     */
    public void getNetworkData(final HashMap<String, String> mapObject, final String success, final String error) {
        HttpUtil.getNetworksJSonResponse(BocAnalyseDetailActivity.this, BOC_ANALYSE_NEWS_DETAIL_URL, mapObject, new HttpCallBackListener() {
            @Override
            public void onSuccess(String response) {
                lltBocAnalyseContent.setVisibility(View.VISIBLE);  //显示内容部分
                Gson gson = new Gson();
                BocAnalyseBean bean = gson.fromJson(response, BocAnalyseBean.class);
                imageBeans.addAll(bean.getContent().getImageList());//详情页轮播图
                for (int i = 0; i < imageBeans.size(); i++) {
                    imageList.add(imageBeans.get(i).getImageUrl());  //图片url集合
                }
                adapter.notifyDataSetChanged();
                htmlContent = bean.getContent().getHtmlContent(); //详情页正文内容
                webViewBocAnalyseDetail.loadUrl(htmlContent);   //加载网络正文
                webViewBocAnalyseDetail.setWebViewClient(new WebViewClient() {
                    @Override
                    public boolean shouldOverrideUrlLoading(WebView view, String url) {
                        view.loadUrl(url);//设置在webView点击打开的新网页在当前界面显示,而不跳转到新的浏览器中
                        return true;
                    }

                    @Override
                    public void onPageFinished(WebView view, String url) {
                    }
                });
                if ((imageList == null || imageList.size() <= 0) && TextUtils.isEmpty(htmlContent)) {
                    lltBocAnalyseDetailNoData.setVisibility(View.GONE);
                } else {
                    lltBocAnalyseDetailNoData.setVisibility(View.VISIBLE);
                }
                lltBocAnalyseDetailLoading.setVisibility(View.GONE);
                ToastUtil.showToast(mContext, success);
            }

            @Override
            public void onFailed(String msg) {
                lltBocAnalyseDetailNoData.setVisibility(View.VISIBLE);
                lltBocAnalyseContent.setVisibility(View.GONE);
                lltBocAnalyseDetailLoading.setVisibility(View.GONE);
                ToastUtil.showToast(mContext, error);
            }

            @Override
            public void onError(Exception e) {
                lltBocAnalyseDetailNoData.setVisibility(View.VISIBLE);
                lltBocAnalyseContent.setVisibility(View.GONE);
                lltBocAnalyseDetailLoading.setVisibility(View.GONE);
                ToastUtil.showToast(mContext, e.getMessage());
            }
        });
    }

}
