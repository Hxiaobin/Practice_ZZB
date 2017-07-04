package com.sf.bocfinancialsoftware.activity.tool.finance;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
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
    private EditText etSearch;
    private ListView lvFinancial;
    private ListView lvFinancial2; //查询后显示数据的view
    private View mHeaderView; //ListView头部视图
    private List<FinanceBean> mDatas; //原来的数据源
    private List<FinanceBean> mList; //查询后的数据源
    private FinancialAdapter myAdapter;
    private RollPagerView rollPagerView; //图片轮播
    private RollViewPagerAdapter mRollViewPagerAdapter; //图片轮播适配器
    private String searchContent; //输入的内容
    private String[] tvFinancialTitle; //名字
    private String[] tvFinancialTime; //时间
    private int i, j;
    private final String TAG = "FinanceProduct";

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
        mHeaderView = LayoutInflater.from(this).inflate(R.layout.layout_financial_list_header, null);
        ivSearch = (ImageView) mHeaderView.findViewById(R.id.ivSearch);
        etSearch = (EditText) mHeaderView.findViewById(R.id.etSearch);
        lvFinancial = (ListView) findViewById(R.id.lvFinancial);
        lvFinancial2 = (ListView) findViewById(R.id.lvFinancial2);
        rollPagerView = (RollPagerView) mHeaderView.findViewById(R.id.rollPagerView);
        etSearch.setFocusable(true);
    }

    private void initData() {
        mDatas = new ArrayList<>();
        tvFinancialTitle = getResources().getStringArray(R.array.tool_financial_title);
        tvFinancialTime = getResources().getStringArray(R.array.tool_financial_time);
        for (int i = 0; i < tvFinancialTitle.length; i++) {
            mDatas.add(new FinanceBean(tvFinancialTitle[i], tvFinancialTime[i]));
        }
        myAdapter = new FinancialAdapter(this, mDatas);
        lvFinancial.addHeaderView(mHeaderView, null, false);
        lvFinancial.setAdapter(myAdapter);
        //图片轮播
        mRollViewPagerAdapter = new RollViewPagerAdapter();
        rollPagerView.setAdapter(mRollViewPagerAdapter);
    }

    private void initListener() {
        ivBack.setOnClickListener(this);
        ivSearch.setOnClickListener(this);
        lvFinancial.setOnItemClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivBack:
                finish();
                break;
            case R.id.ivSearch:
                show();
                break;
        }
    }

    private void show() {
        mList = new ArrayList<>();
        //编辑框获取字符串
        searchContent = etSearch.getText().toString().trim();
        if (TextUtils.isEmpty(searchContent)) {
            mList.clear();
            mDatas.clear();
            for (int i = 0; i < tvFinancialTitle.length; i++) {
                mList.add(new FinanceBean(tvFinancialTitle[i], tvFinancialTime[i]));
            }
            myAdapter.setFinancialBeen(mList);
        } else {
            boolean flag = true;
            int pLen = tvFinancialTitle.length;
            for (i = 0; i < pLen; i++) {
                //获取查询的字符串
                int len = pLen - searchContent.length() + 1;
                for (j = 0; j < len; j++) {
                    //String string = tvFinancialTitle[i].substring(j, j + searchContent.length());
                    boolean contains = tvFinancialTitle[i].contains(searchContent);
                    if (contains) {
                        mList.add(new FinanceBean(tvFinancialTitle[i], ""));
                        flag = false;
                        break;
                    }
                }
            }
            if (flag) {
                mList.clear();
                mDatas.clear();
                Toast.makeText(this, R.string.text_not_search, Toast.LENGTH_LONG).show();
            }
            Log.e(TAG, "show: " + mList.size());
            myAdapter.setFinancialBeen(mList);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(FinanceProductListActivity.this, FinanceProductDetailActivity.class);
        intent.putExtra("product",mDatas.get(position-1).getProductName());
        startActivity(intent);
    }
}
