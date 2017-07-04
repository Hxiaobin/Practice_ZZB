package com.sf.bocfinancialsoftware.activity.tool.product;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.sf.bocfinancialsoftware.R;
import com.sf.bocfinancialsoftware.adapter.ProductAdapter;
import com.sf.bocfinancialsoftware.adapter.SearchAdapter;
import com.sf.bocfinancialsoftware.bean.ProductBean;
import com.sf.bocfinancialsoftware.bean.ProductSearchBean;

import java.util.ArrayList;
import java.util.List;

public class IntelProductDetailActivity2 extends AppCompatActivity implements SearchView.SearchViewListener {

    private ListView lvResults;//搜索结果列表
    private SearchView mSearchView;//搜索view
    private ArrayAdapter<String> autoCompleteAdapter;//自动补全列表adapter
    private SearchAdapter resultAdapter;//搜索结果列表adapter
    private List<ProductBean> dbData;//总数据
    private List<String> autoCompleteData;//搜索过程中自动补全数据
    private List<ProductSearchBean> resultData;//搜索结果的数据
    private List<String> mToolProductSupplyList;
    private static int DEFAULT_HINT_SIZE = 4;// 默认提示框显示项的个数
    private static int hintSize = DEFAULT_HINT_SIZE;//提示框显示项的个数

    /**
     * 设置提示框显示项的个数
     *
     * @param hintSize 提示框显示个数
     */
    public static void setHintSize(int hintSize) {
        IntelProductDetailActivity2.hintSize = hintSize;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intel_product_detail2);
        initViews();
        initData();
    }

    private void initViews() {
        lvResults = (ListView) findViewById(R.id.lvResults);
        mSearchView = (SearchView) findViewById(R.id.SearchView);
        mSearchView.setSearchViewListener(this);
        mSearchView.setAutoCompleteAdapter(autoCompleteAdapter);
    }

    private void initData() {
        //从数据库获取数据  
        getDbData();
        //初始化自动补全数据  
        getAutoCompleteData(null);
        //初始化搜索结果数据  
        getResultData(null);
    }

    /**
     * 获取db 数据
     */
    public void getDbData() {
        dbData = new ArrayList<>();
        String[] productSupplyName = getResources().getStringArray(R.array.tool_product_name_supply);
        for (int i = 0; i < productSupplyName.length; i++) {
            mToolProductSupplyList.add(productSupplyName[i]);
        }
        dbData.add(new ProductBean(getString(R.string.text_product_supply), mToolProductSupplyList));
    }

    /**
     * 获取自动补全data 和adapter
     */
    private void getAutoCompleteData(String text) {
        if (autoCompleteData == null) {

        } else {
            // 根据text 获取auto data
            autoCompleteData.clear();

        }
    }

    /**
     * 获取搜索结果data和adapter
     */
    private void getResultData(String text) {
        if (resultData == null) {
            //初始化
            resultData = new ArrayList<>();
        } else {
            resultData.clear();
            for (int i = 0; i < dbData.size(); i++) {
                List<String> contents = dbData.get(i).getContent();
                for (int j = 0;j<dbData.get(i).getContent().size();j++) {
                    String s = contents.get(j);
                    if (s.contains(text.trim())) {
                        resultData.add(new ProductSearchBean(s));
                    }
                }
            }
        }
        if (resultAdapter == null) {
            resultAdapter = new SearchAdapter(this,resultData);
        } else {
            resultAdapter.notifyDataSetChanged();
        }
    }

    /**
     * 当搜索框 文本改变时 触发的回调 ,更新自动补全数据
     * @param text 传入补全后的文本
     */
    @Override
    public void onRefreshAutoComplete(String text) {
        getAutoCompleteData(text);
    }

    /**
     * 点击搜索键时edit text触发的回调
     * @param text 传入输入框的文本
     */
    @Override
    public void onSearch(String text) {
        //更新result数据
        getResultData(text);
        lvResults.setVisibility(View.VISIBLE);
        //第一次获取结果 还未配置适配器
        if (lvResults.getAdapter() == null) {
            //获取搜索数据 设置适配器
            lvResults.setAdapter(resultAdapter);
        } else {
            //更新数据
            resultAdapter.notifyDataSetChanged();
        }
    }
}
