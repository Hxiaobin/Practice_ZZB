package com.sf.bocfinancialsoftware.activity.tool.product;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.sf.bocfinancialsoftware.R;
import com.sf.bocfinancialsoftware.adapter.ProductAdapter;
import com.sf.bocfinancialsoftware.adapter.SearchAdapter;
import com.sf.bocfinancialsoftware.bean.ProductBean;
import com.sf.bocfinancialsoftware.constant.ConstantConfig;

import java.util.ArrayList;
import java.util.List;

import static android.view.View.VISIBLE;

/**
 * 产品介绍
 */
public class IntelProductListActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    private ImageView ivBack; //返回按钮
    private EditText etSearch; //搜索框 输入框
    private ImageView ivSearch; //搜索按钮
    private ImageView ivDelete;//删除键
    private LinearLayout llNoData;//显示无数据
    private ListView lvResults;//搜索结果列表
    private ListView lvProduct1; //listview1显示数据源的数据
    private List<ProductBean> mDatas = new ArrayList<>(); //数据源的数据
    private List<String> resultData = new ArrayList<>();//搜索结果的数据
    private String[] productSupplyName;
    private String[] productTradeName;
    private ProductAdapter mAdapter;
    private SearchAdapter resultAdapter;//搜索结果列表adapter

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
        lvResults = (ListView) findViewById(R.id.lvResults);
        ivBack = (ImageView) findViewById(R.id.ivBack);
        ivDelete = (ImageView) findViewById(R.id.ivDelete);
        llNoData = (LinearLayout) findViewById(R.id.llNoData);
    }

    private void initData() {
        mAdapter = new ProductAdapter(this, mDatas);
        lvProduct1.setAdapter(mAdapter);
        resultAdapter = new SearchAdapter(this, resultData);
        lvResults.setAdapter(resultAdapter);
        getData();
    }

    private void initListener() {
        ivBack.setOnClickListener(this);
        ivSearch.setOnClickListener(this);
        ivDelete.setOnClickListener(this);
        lvResults.setOnItemClickListener(this);
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!TextUtils.isEmpty(s.toString())) {
                    ivDelete.setVisibility(VISIBLE);
                } else {
                    ivDelete.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivBack: //返回
                finish();
                break;
            case R.id.ivDelete: //清除
                etSearch.setText("");
                ivDelete.setVisibility(View.GONE);
                break;
            case R.id.ivSearch: //查询
                onSearch(etSearch.getText().toString().trim());
                //隐藏软键盘
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                break;
        }
    }

    /**
     * 点击搜索键时edit text触发的回调
     *
     * @param text 传入输入框的文本
     */
    public void onSearch(String text) {
        if (TextUtils.isEmpty(text)) {
            lvProduct1.setVisibility(VISIBLE);
            lvResults.setVisibility(View.GONE);
            llNoData.setVisibility(View.GONE);
            mAdapter.notifyDataSetChanged();
        } else if (!getResultData(text)) {
            lvProduct1.setVisibility(View.GONE);
            lvResults.setVisibility(View.GONE);
            llNoData.setVisibility(View.VISIBLE);
            Toast.makeText(this, R.string.text_not_search, Toast.LENGTH_LONG).show();
        } else {
            lvProduct1.setVisibility(View.GONE);
            llNoData.setVisibility(View.GONE);
            lvResults.setVisibility(VISIBLE);
            //跟新适配器
            resultAdapter.notifyDataSetChanged();
        }
    }

    /**
     * 获取搜索结果data和adapter
     */
    private boolean getResultData(String text) {
        resultData.clear();
        for (int i = 0; i < mDatas.size(); i++) {
            List<String> contents = mDatas.get(i).getContent();
            for (int j = 0; j < contents.size(); j++) {
                String s = contents.get(j);
                if (s.contains(text.trim())) {
                    resultData.add(s);
                }
            }
        }
        if (resultData.size() == 0) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 获取数据
     */
    public void getData() {
        //供应链融资数据
        productSupplyName = getResources().getStringArray(R.array.tool_product_name_supply);
        List<String> mToolProductSupplyList = new ArrayList<>();
        for (int i = 0; i < productSupplyName.length; i++) {
            mToolProductSupplyList.add(productSupplyName[i]);
        }
        mDatas.add(new ProductBean(getString(R.string.text_product_supply), mToolProductSupplyList));

        //国际贸易结算数据
        List<String> mToolProductTradeList = new ArrayList<>();
        productTradeName = getResources().getStringArray(R.array.tool_product_name_trade);
        for (int i = 0; i < productTradeName.length; i++) {
            mToolProductTradeList.add(productTradeName[i]);
        }
        mDatas.add(new ProductBean(getString(R.string.text_product_trade), mToolProductTradeList));

        //国际贸易结算数据
        List<String> mToolProductTradeList2 = new ArrayList<>();
        productTradeName = getResources().getStringArray(R.array.tool_product_name_trade);
        for (int i = 0; i < productTradeName.length; i++) {
            mToolProductTradeList2.add(productTradeName[i]);
        }
        mDatas.add(new ProductBean(getString(R.string.text_product_trade), mToolProductTradeList2));
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(IntelProductListActivity.this, IntelProductDetailActivity.class);
        intent.putExtra(ConstantConfig.TITLE, resultData.get(position));
        startActivity(intent);
    }
}
