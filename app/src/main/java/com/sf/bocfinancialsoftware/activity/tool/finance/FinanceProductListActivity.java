package com.sf.bocfinancialsoftware.activity.tool.finance;

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

import com.jude.rollviewpager.RollPagerView;
import com.sf.bocfinancialsoftware.R;
import com.sf.bocfinancialsoftware.adapter.FinancialAdapter;
import com.sf.bocfinancialsoftware.adapter.RollViewPagerAdapter;
import com.sf.bocfinancialsoftware.bean.FinanceBean;

import java.util.ArrayList;
import java.util.List;

public class FinanceProductListActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    private ImageView ivBack;
    private ImageView ivSearch;
    private ImageView ivClear;
    private EditText etSearch;
    private LinearLayout llNoData;//显示无数据
    private ListView lvFinancial;
    private ListView lvFinancial2; //查询后显示数据的view
    private List<FinanceBean> mDatas = new ArrayList<>(); //原来的数据源
    private List<FinanceBean> mList = new ArrayList<>(); //查询后的数据源
    private FinancialAdapter myAdapter;
    private FinancialAdapter resultAdapter;//查询后的
    private RollPagerView rollPagerView; //图片轮播
    private RollViewPagerAdapter mRollViewPagerAdapter; //图片轮播适配器
    private String searchContent; //输入的内容
    private String[] tvFinancialTitle; //名字
    private String[] tvFinancialTime; //时间

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_financial_product);
        initView();
        initData();
        initListener();
    }

    private void initView() {
        TextView tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvTitle.setText(R.string.text_financial);
        ivBack = (ImageView) findViewById(R.id.ivBack);
        ivSearch = (ImageView) findViewById(R.id.ivSearch);
        ivClear = (ImageView) findViewById(R.id.ivClear);
        etSearch = (EditText) findViewById(R.id.etSearch);
        lvFinancial = (ListView) findViewById(R.id.lvFinancial);
        lvFinancial2 = (ListView) findViewById(R.id.lvFinancial2);
        rollPagerView = (RollPagerView) findViewById(R.id.rollPagerView);
        llNoData = (LinearLayout) findViewById(R.id.llNoData);
        etSearch.setFocusable(true);
    }

    private void initData() {
        tvFinancialTitle = getResources().getStringArray(R.array.tool_financial_title);
        tvFinancialTime = getResources().getStringArray(R.array.tool_financial_time);
        for (int i = 0; i < tvFinancialTitle.length; i++) {
            mDatas.add(new FinanceBean(tvFinancialTitle[i], tvFinancialTime[i]));
        }
        myAdapter = new FinancialAdapter(this, mDatas);
        lvFinancial.setAdapter(myAdapter);
        resultAdapter = new FinancialAdapter(this, mList);
        lvFinancial2.setAdapter(resultAdapter);
        //图片轮播
        mRollViewPagerAdapter = new RollViewPagerAdapter();
        rollPagerView.setAdapter(mRollViewPagerAdapter);
    }

    private void initListener() {
        ivBack.setOnClickListener(this);
        ivSearch.setOnClickListener(this);
        ivClear.setOnClickListener(this);
        lvFinancial.setOnItemClickListener(this);
        lvFinancial2.setOnItemClickListener(this);
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TextUtils.isEmpty(s.toString())) {
                    ivClear.setVisibility(View.GONE);
                } else {
                    ivClear.setVisibility(View.VISIBLE);
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
            case R.id.ivBack:
                finish();
                break;
            case R.id.ivClear:
                etSearch.setText("");
                ivClear.setVisibility(View.GONE);
                break;
            case R.id.ivSearch:
                onSearch();
                //隐藏软键盘
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                break;
        }
    }

    /**
     * 点击搜索键进行搜索
     */
    public void onSearch() {
        //编辑框获取字符串
        searchContent = etSearch.getText().toString().trim();
        if (TextUtils.isEmpty(searchContent)) {
            lvFinancial.setVisibility(View.VISIBLE);
            lvFinancial2.setVisibility(View.GONE);
            llNoData.setVisibility(View.GONE);
            myAdapter.notifyDataSetChanged();
        } else if (getResultData(searchContent)) {
            //更新数据
            lvFinancial.setVisibility(View.GONE);
            llNoData.setVisibility(View.GONE);
            lvFinancial2.setVisibility(View.VISIBLE);
            resultAdapter.notifyDataSetChanged();
        } else {
            lvFinancial.setVisibility(View.GONE);
            lvFinancial2.setVisibility(View.GONE);
            llNoData.setVisibility(View.VISIBLE);
            Toast.makeText(this, R.string.text_not_search, Toast.LENGTH_LONG).show();
        }
    }

    /**
     * 获取搜索结果data和adapter
     */
    private boolean getResultData(String text) {
        mList.clear();
        for (int i = 0; i < mDatas.size(); i++) {
            String str = mDatas.get(i).getProductName();
            if (str.contains(text.trim())) {
                mList.add(new FinanceBean(str, ""));
            }
        }
        if (mList.size() == 0) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(FinanceProductListActivity.this, FinanceProductDetailActivity.class);
        intent.putExtra("product", mDatas.get(position).getProductName());
        startActivity(intent);
    }

}
