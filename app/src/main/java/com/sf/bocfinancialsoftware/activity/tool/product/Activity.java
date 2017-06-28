package com.sf.bocfinancialsoftware.activity.tool.product;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.sf.bocfinancialsoftware.R;
import com.sf.bocfinancialsoftware.adapter.ProductAdapter;
import com.sf.bocfinancialsoftware.bean.ProductBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 产品介绍
 */
public class Activity extends AppCompatActivity implements View.OnClickListener {

    private ImageView ivBack; //返回按钮
    private EditText etSearch; //搜索框
    private ImageView ivSearch; //搜索按钮
    private ListView lvProduct1; //listview1显示数据源的数据
    private ListView lvProduct2; //listview2显示查找后的数据
    private List<ProductBean> mDatas;
    private ProductAdapter mAdapter;
    private String[] productSupplyName;
    private String[] productTradeName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_);
        initView();
        initData();
        initListener();
    }

    private void initView() {
        TextView tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvTitle.setText(R.string.tv_product_introduce);
        etSearch = (EditText) findViewById(R.id.etSearch);
        ivSearch = (ImageView) findViewById(R.id.ivSearch);
        lvProduct1 = (ListView) findViewById(R.id.lvProduct1);
        lvProduct2 = (ListView) findViewById(R.id.lvProduct2);
        ivBack = (ImageView) findViewById(R.id.ivBack);
    }

    private void initData() {
        //供应链融资数据
        mDatas = new ArrayList<>();
        productSupplyName = getResources().getStringArray(R.array.tool_product_name_supply);
        for (int i = 0; i < productSupplyName.length; i++) {
            mDatas.add(new ProductBean("供应链融资",productSupplyName[i]));
        }

        mAdapter = new ProductAdapter(this, mDatas);
        lvProduct1.setAdapter(mAdapter);
        productTradeName = getResources().getStringArray(R.array.tool_product_name_trade);
    }

    private void initListener() {
        ivBack.setOnClickListener(this);
        ivSearch.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivBack: //返回
                finish();
                break;
            case R.id.ivSearch: //查询
                break;
        }
    }
}
