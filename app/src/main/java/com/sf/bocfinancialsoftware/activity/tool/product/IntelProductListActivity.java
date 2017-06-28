package com.sf.bocfinancialsoftware.activity.tool.product;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.sf.bocfinancialsoftware.R;
import com.sf.bocfinancialsoftware.adapter.ProductAdapter;
import com.sf.bocfinancialsoftware.bean.ProductBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 产品介绍
 */
public class IntelProductListActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView ivBack; //返回按钮
    private EditText etSearch; //搜索框
    private ImageView ivSearch; //搜索按钮
    private ListView lvProduct1; //listview1显示数据源的数据
    private List<String> mToolProductSupplyList, mToolProductTradeList;
    private List<ProductBean> mDatas; //数据源的数据
    private List<ProductBean> mDatas2; //查找后的数据
    private ProductAdapter mAdapter;
    private String[] productSupplyName;
    private String[] productTradeName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
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
        ivBack = (ImageView) findViewById(R.id.ivBack);
    }

    private void initData() {
        //供应链融资数据
        mDatas = new ArrayList<>();
        mToolProductSupplyList = new ArrayList<>();
        productSupplyName = getResources().getStringArray(R.array.tool_product_name_supply);
        for (int i = 0; i < productSupplyName.length; i++) {
            mToolProductSupplyList.add(productSupplyName[i]);
        }
        mDatas.add(new ProductBean(getString(R.string.text_product_supply), mToolProductSupplyList));
        //国际贸易结算数据
        mToolProductTradeList = new ArrayList<>();
        productTradeName = getResources().getStringArray(R.array.tool_product_name_trade);
        for (int i = 0; i < productTradeName.length; i++) {
            mToolProductTradeList.add(productTradeName[i]);
        }
        mDatas.add(new ProductBean(getString(R.string.text_product_trade), mToolProductTradeList));
        mAdapter = new ProductAdapter(this, mDatas);
        lvProduct1.setAdapter(mAdapter);
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
                search();
                break;
        }
    }

    private void search() {
        List<ProductBean> beanList = new ArrayList<>();
        //输入的关键字
        String searchStr = etSearch.getText().toString();
        if (TextUtils.isEmpty(searchStr)) {
            mDatas.clear();
            for (int i = 0; i < productSupplyName.length; i++) {
                mToolProductSupplyList.add(productSupplyName[i]);
            }
            mDatas.add(new ProductBean(getString(R.string.text_product_supply), mToolProductSupplyList));
            //国际贸易结算数据
            for (int i = 0; i < productTradeName.length; i++) {
                mToolProductTradeList.add(productTradeName[i]);
            }
            mDatas.add(new ProductBean(getString(R.string.text_product_trade), mToolProductTradeList));
            lvProduct1.setAdapter(mAdapter);
        } else {
            boolean flag = true;
            List<String> list = new ArrayList();
            for (int i = 0; i < mDatas.size(); i++) {
                ProductBean productBean = mDatas.get(i);
                List<String> content = productBean.getContent();
                for (int j = 0; j < mDatas.get(i).getContent().size(); j++) {
                    String s = content.get(j);
                    boolean contains = s.contains(searchStr);
                    if (contains) {
                        list.add(s);
                        flag = false;
                    }
                }
                content.clear();
                content.addAll(list);
                beanList.add(new ProductBean(mDatas.get(i).getTitle(),content));
            }
            if (flag) {
                mDatas.clear();
                Toast.makeText(this, R.string.text_not_search, Toast.LENGTH_LONG).show();
            }
            mDatas.clear();
            mDatas.addAll(beanList);
            //mAdapter = new ProductAdapter(this, mDatas2);
            mAdapter.notifyDataSetChanged();
        }
    }

}
