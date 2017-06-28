package com.sf.bocfinancialsoftware.activity.home.analyse;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jude.rollviewpager.RollPagerView;
import com.sf.bocfinancialsoftware.R;
import com.sf.bocfinancialsoftware.activity.base.BaseActivity;
import com.sf.bocfinancialsoftware.adapter.ImageAdapter;
import com.sf.bocfinancialsoftware.bean.BocAnalyseBean;
import com.sf.bocfinancialsoftware.util.DataBaseSQLiteUtil;

import java.util.List;

import static com.sf.bocfinancialsoftware.constant.ConstantConfig.NEWS_ID;

/**
 * 中银分析详情
 * Created by sn on 2017/6/9.
 */
public class BocAnalyseDetailActivity extends BaseActivity {

    private ImageView ivTitleBarBack; //返回按钮
    private TextView tvTitleBarTitle; //标题
    private TextView tvBocAnalyseDetailNewsTitle; //文本标题
    private TextView tvBocAnalyseDetailHtmlContent; //文本内容
    private TextView tvBocAnalyseDetailNewsDate; //文本时间
    private RollPagerView rollPagerViewBoc; //图片轮播
    private ImageAdapter adapter;  //图片轮播适配器
    private Intent intent;
    private String newsId; //新闻id
    private BocAnalyseBean bocAnalyseBean; //新闻对象

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
        tvBocAnalyseDetailNewsTitle = (TextView) findViewById(R.id.tvBocAnalyseDetailNewsTitle);
        tvBocAnalyseDetailHtmlContent = (TextView) findViewById(R.id.tvBocAnalyseDetailHtmlContent);
        tvBocAnalyseDetailNewsDate = (TextView) findViewById(R.id.tvBocAnalyseDetailNewsDate);
        rollPagerViewBoc = (RollPagerView) findViewById(R.id.rollPagerViewBoc);
    }

    @Override
    protected void initData() {
        tvTitleBarTitle.setText(getString(R.string.common_boc_analyse));
        ivTitleBarBack.setVisibility(View.VISIBLE);
        //获取上一页面传递的数据
        intent = getIntent();
        newsId = intent.getStringExtra(NEWS_ID);
        bocAnalyseBean = DataBaseSQLiteUtil.getBocAnalyseById(newsId);
        tvBocAnalyseDetailNewsTitle.setText(bocAnalyseBean.getNewsTitle());
        tvBocAnalyseDetailHtmlContent.setText("        " + bocAnalyseBean.getHtmlContent());
        tvBocAnalyseDetailNewsDate.setText(bocAnalyseBean.getNewsData());
        List<String> imageList = bocAnalyseBean.getImageList(); //轮播图片源
        adapter = new ImageAdapter(BocAnalyseDetailActivity.this, imageList);
        rollPagerViewBoc.setAdapter(adapter);
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

}
