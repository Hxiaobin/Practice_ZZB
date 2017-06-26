package com.sf.bocfinancialsoftware.activity.tool.analysis;

import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.sf.bocfinancialsoftware.adapter.AnalysisAdapter;
import com.sf.bocfinancialsoftware.bean.AnalysisBean;
import com.sf.bocfinancialsoftware.util.SpacesItemDecoration;
import com.sf.bocfinancialsoftware.R;

import java.util.ArrayList;
import java.util.List;

public class BocAnalyseListActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView ivBack;
    private RecyclerView recyclerView;
    private AnalysisAdapter mRecyclerViewAdapter;
    private List<AnalysisBean> mDatas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analysis);
        initView();
        initData();
        initLister();
    }

    private void initData() {
        mDatas = new ArrayList<>();
        TypedArray image = getResources().obtainTypedArray(R.array.tool_analysis_icon);
        for (int i = 0; i < 10; i++) {
            mDatas.add(new AnalysisBean(getString(R.string.tv_tool_analysis_title),
                    getString(R.string.tv_tool_analysis_content), getString(R.string.tv_tool_analysis_time),
                    image.getResourceId(i%3, 0)));
        }
        mRecyclerViewAdapter = new AnalysisAdapter(mDatas, BocAnalyseListActivity.this);
        //设置布局管理器
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        //设置为垂直布局，这也是默认的
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new SpacesItemDecoration(30));
        recyclerView.setAdapter(mRecyclerViewAdapter);
        mRecyclerViewAdapter.setOnItemClickListener(new AnalysisAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                startActivity(new Intent(BocAnalyseListActivity.this, BocAnalyseDetailActivity.class));
            }
        });
    }

    private void initLister() {
        ivBack.setOnClickListener(this);
    }

    private void initView() {
        TextView tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvTitle.setText(R.string.text_analysis);
        ivBack = (ImageView) findViewById(R.id.ivBack);
        recyclerView = (RecyclerView) findViewById(R.id.rvAnalysis);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivBack:
                finish();
                break;

        }
    }
}
