package com.sf.bocfinancialsoftware.activity.tool.product;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sf.bocfinancialsoftware.constant.ConstantConfig;
import com.sf.bocfinancialsoftware.R;

import java.util.ArrayList;
import java.util.List;

import me.gujun.android.taggroup.TagGroup;

public class IntelProductListActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView ivBack;
    private TagGroup tclToolProductSupply, tclToolProductTrade;
    private List<String> mToolProductSupplyList, mToolProductTradeList;
    private EditText etSearch;
    private ImageView ivSearch;
    private String searchStr; //输入的内容
    private String[] productSupplyName;
    private String[] productTradeName;
    private LinearLayout llTrade,llSupply;

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
        ivBack = (ImageView) findViewById(R.id.ivBack);
        tclToolProductSupply = (TagGroup) findViewById(R.id.tclToolProductSupply);
        tclToolProductTrade = (TagGroup) findViewById(R.id.tclToolProductTrade);
        ivSearch = (ImageView) findViewById(R.id.ivSearch);
        etSearch = (EditText) findViewById(R.id.etSearch);
        llSupply = (LinearLayout) findViewById(R.id.llSupply);
        llTrade = (LinearLayout) findViewById(R.id.llTrade);
    }

    private void initData() {
        //供应链融资数据
        mToolProductSupplyList = new ArrayList<>();
        productSupplyName = getResources().getStringArray(R.array.tool_product_name_supply);
        for (int i = 0; i < productSupplyName.length; i++) {
            mToolProductSupplyList.add(productSupplyName[i]);
        }
        tclToolProductSupply.setTags(mToolProductSupplyList);
        //国际贸易结算数据
        mToolProductTradeList = new ArrayList<>();
        productTradeName = getResources().getStringArray(R.array.tool_product_name_trade);
        for (int i = 0; i < productTradeName.length; i++) {
            mToolProductTradeList.add(productTradeName[i]);
        }
        tclToolProductTrade.setTags(mToolProductTradeList);
    }

    private void initListener() {
        ivBack.setOnClickListener(this);
        ivSearch.setOnClickListener(this);
        //供应链融资跳转
        tclToolProductSupply.setOnTagClickListener(new TagGroup.OnTagClickListener() {
            @Override
            public void onTagClick(String tag) {
                Intent intent = new Intent(IntelProductListActivity.this, IntelProductDetailActivity.class);
                intent.putExtra(ConstantConfig.TITLE, tag);
                startActivity(intent);
            }
        });
        //国际贸易结算跳转
        tclToolProductTrade.setOnTagClickListener(new TagGroup.OnTagClickListener() {
            @Override
            public void onTagClick(String tag) {
                Intent intent = new Intent(IntelProductListActivity.this, IntelProductDetailActivity.class);
                intent.putExtra(ConstantConfig.TITLE, tag);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivBack:
                finish();
                break;
            case R.id.ivSearch: //查询
                supplyShow();
                break;
        }
    }

    private void supplyShow() {
        List<String> list = new ArrayList<>();
        List<String> list2 = new ArrayList<>();
        searchStr = etSearch.getText().toString();
        if (TextUtils.isEmpty(searchStr)) {
            //供应链融资
            list.clear();
            mToolProductSupplyList.clear();
            for (int i = 0; i < productSupplyName.length; i++) {
                list.add(productSupplyName[i]);
            }
            tclToolProductSupply.setTags(list);
            //国际贸易结算
            list2.clear();
            mToolProductTradeList.clear();
            for (int i = 0; i < productTradeName.length; i++) {
                list2.add(productTradeName[i]);
            }
            tclToolProductTrade.setTags(list2);
        } else {
            boolean flag = true;
            //供应链融资
            int supplyLen = productSupplyName.length;
            for (int i = 0; i < supplyLen; i++) {
                int len = supplyLen - searchStr.length() + 1;
                for (int j = 0; j < len; j++) {
                    boolean contains = productSupplyName[i].contains(searchStr);
                    if (contains) {
                        list.add(productSupplyName[i]);
                        flag = false;
                        break;
                    }
                }
            }
            //国际贸易结算
            int tradeLen = productTradeName.length;
            for (int i = 0; i < supplyLen; i++) {
                int len = tradeLen - searchStr.length() + 1;
                for (int j = 0; j < len; j++) {
                    boolean contains = productTradeName[i].contains(searchStr);
                    if (contains) {
                        list2.add(productTradeName[i]);
                        flag = false;
                        break;
                    }
                }
            }
            if (flag) {
                Toast.makeText(this, "没有找到相匹配的产品", Toast.LENGTH_LONG).show();
            }
            Log.e("IntelProductList", "show: " + list.size());
            tclToolProductSupply.setTags(list);
            tclToolProductTrade.setTags(list2);
        }

    }
}
