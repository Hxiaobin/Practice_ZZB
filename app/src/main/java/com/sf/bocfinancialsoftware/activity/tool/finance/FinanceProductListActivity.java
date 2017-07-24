package com.sf.bocfinancialsoftware.activity.tool.finance;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jude.rollviewpager.RollPagerView;
import com.sf.bocfinancialsoftware.R;
import com.sf.bocfinancialsoftware.adapter.RollViewPagerAdapter;
import com.sf.bocfinancialsoftware.adapter.finance.FinancialAdapter;
import com.sf.bocfinancialsoftware.base.BaseActivity;
import com.sf.bocfinancialsoftware.bean.AdvertLoopBean;
import com.sf.bocfinancialsoftware.bean.finance.FinanceBean;
import com.sf.bocfinancialsoftware.constant.ConstantConfig;
import com.sf.bocfinancialsoftware.constant.URLConfig;
import com.sf.bocfinancialsoftware.util.NetWorkUtils;
import com.sf.bocfinancialsoftware.util.ToastUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FinanceProductListActivity extends BaseActivity implements View.OnClickListener, AdapterView.OnItemClickListener, AbsListView.OnScrollListener, SwipeRefreshLayout.OnRefreshListener {

    private ImageView ivBack;
    private ImageView ivSearch;
    private ImageView ivClear;
    private EditText etSearch;
    private LinearLayout lltLoad;
    private LinearLayout linearLayout;//搜索的布局
    private ListView lvFinancial;
    private View footerView;//底部加载更多布局
    private List<FinanceBean.ContentBean.ProductListBean> mDatas = new ArrayList<>(); //原来的数据源
    private List<FinanceBean.ContentBean.ProductListBean> mList = new ArrayList<>(); //查询后的数据源
    private FinancialAdapter myAdapter;
    private RollPagerView rollPagerView; //图片轮播
    private RollViewPagerAdapter mRollViewPagerAdapter; //图片轮播适配器
    private SwipeRefreshLayout srlRefresh; //加载刷新控件
    private int mCurrentPage = 1;
    private boolean isLoadMore = true;//是否加载
    private boolean isRefresh;//是否刷新
    private String mHasNext;//是否有下一页

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_financial_product);
        initView();
        initData();
        initListener();
    }

    @Override
    protected void initView() {
        TextView tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvTitle.setText(R.string.text_financial);
        ivBack = (ImageView) findViewById(R.id.ivBack);
        ivSearch = (ImageView) findViewById(R.id.ivSearch);
        ivClear = (ImageView) findViewById(R.id.ivClear);
        etSearch = (EditText) findViewById(R.id.etSearch);
        lvFinancial = (ListView) findViewById(R.id.lvFinancial);
        rollPagerView = (RollPagerView) findViewById(R.id.rollPagerView);
        srlRefresh = (SwipeRefreshLayout) findViewById(R.id.srlRefresh);
        footerView = View.inflate(mContext, R.layout.layout_lv_loading_foot, null);
        lltLoad = (LinearLayout) footerView.findViewById(R.id.lltLoad);
        etSearch.setFocusable(true);
        linearLayout = (LinearLayout) findViewById(R.id.linearLayout);
    }

    @Override
    protected void initData() {
        lltLoad.setVisibility(View.GONE);
        //设置进度动画的颜色
        srlRefresh.setColorSchemeColors(getResources().getColor(R.color.redLight));
        //获取网络数据
        myAdapter = new FinancialAdapter(mContext, mDatas);
        lvFinancial.addFooterView(footerView);
        lvFinancial.setAdapter(myAdapter);
        //图片轮播
        mRollViewPagerAdapter = new RollViewPagerAdapter(mContext);
        rollPagerView.setAdapter(mRollViewPagerAdapter);
        //获取轮播图的额图片
        getNetImageData();
        //获取网络上数据
        getNetWorkData();
    }

    /**
     * 获取网络请求的数据
     */
    private void getNetWorkData() {
        isLoadMore = false;
        HashMap<String, String> map = new HashMap<>();
        map.put("currentPage", mCurrentPage + "");
        NetWorkUtils.doPost(mContext, URLConfig.FINANCE_PRODUCT_LIST, map, new NetWorkUtils.RequestCallBack() {

            @Override
            public void onSuccess(String json) {
                isLoadMore = true;
                srlRefresh.setRefreshing(false);
                FinanceBean jsonBean = new Gson().fromJson(json, FinanceBean.class);
                if (jsonBean != null) {
                    lltLoad.setVisibility(View.VISIBLE);
                    mHasNext = jsonBean.getContent().getHasNext();
                    List<FinanceBean.ContentBean.ProductListBean> productList = jsonBean.getContent().getProductList();
                    if (mCurrentPage == 1) {
                        if (isRefresh) {
                            ToastUtil.showToast(mContext, getString(R.string.common_refresh_success));
                        }
                        mDatas.clear();
                        mDatas.addAll(productList);
                        myAdapter.setProductListBeen(productList);
                    } else {
                        mDatas.addAll(productList);
                        myAdapter.addPage(productList);
                    }
                    linearLayout.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onError() {
                if (mCurrentPage > 1) {
                    mCurrentPage--;
                }
                isLoadMore = true;
                srlRefresh.setRefreshing(false);
                lltLoad.setVisibility(View.GONE);
                linearLayout.setVisibility(View.INVISIBLE);
                ToastUtil.showToast(mContext, "onError");
            }
        });
    }

    @Override
    protected void initListener() {
        ivBack.setOnClickListener(this);
        ivSearch.setOnClickListener(this);
        ivClear.setOnClickListener(this);
        lvFinancial.setOnItemClickListener(this);
        lvFinancial.setOnScrollListener(this);
        srlRefresh.setOnRefreshListener(this);
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
        isLoadMore = false;
        //编辑框获取字符串
        String searchContent = etSearch.getText().toString().trim();
        if (!TextUtils.isEmpty(searchContent)) {
            getResultData(searchContent);
            myAdapter.setProductListBeen(mList);
        }
    }

    /**
     * 获取搜索结果data和adapter
     */
    private void getResultData(String text) {
        mList.clear();
        for (int i = 0; i < mDatas.size(); i++) {
            FinanceBean.ContentBean.ProductListBean productListBean = mDatas.get(i);
            String productName = productListBean.getProductName();
            if (productName.contains(text.trim())) {
                mList.add(productListBean);
            }
            lltLoad.setVisibility(View.GONE);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(FinanceProductListActivity.this, FinanceProductDetailActivity.class);
        intent.putExtra(ConstantConfig.FINANCE_PRODUCT_KEY_SER, myAdapter.getProductListBeen().get(position));
        startActivity(intent);
    }

    @Override
    public void onRefresh() {
        mCurrentPage = 1;
        isRefresh = true;
        getNetWorkData();
    }

    /**
     * 当滑动状态发生改变的时候
     *
     * @param view
     * @param scrollState 1 SCROLL_STATE_TOUCH_SCROLL是拖动  2 SCROLL_STATE_FLING是惯性滑动 3 SCROLL_STATE_IDLE是停止 , 只有当在不同状态间切换的时候才会执行
     */
    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        switch (scrollState) {
            case SCROLL_STATE_IDLE: //当不滚动的时候
                if (isLoadMore && view.getLastVisiblePosition() == (view.getCount()) - 1) {
                    if (TextUtils.equals(mHasNext, "1"))
                        loadMore();
                    else {
                        lltLoad.setVisibility(View.GONE);
                        ToastUtil.showToast(mContext, getString(R.string.common_not_date));
                    }
                }
                break;
        }
    }

    /**
     * 加载更多
     */
    private void loadMore() {
        mCurrentPage++;
        getNetWorkData();
    }

    /**
     * 监听可见界面的情况
     *
     * @param view             ListView
     * @param firstVisibleItem 第一个可见的item的索引
     * @param visibleItemCount 可显示的item的条数
     * @param totalItemCount   总共有多少个item
     */
    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

    }

    /**
     * 获取轮播图片
     */
    public void getNetImageData() {
        NetWorkUtils.doPost(mContext, URLConfig.IMAGE_RESULT, new NetWorkUtils.RequestCallBack() {
            @Override
            public void onSuccess(String json) {
                AdvertLoopBean jsonBean = new Gson().fromJson(json, AdvertLoopBean.class);
                if (jsonBean != null) {
                    List<AdvertLoopBean.ContentBean> content = jsonBean.getContent();
                    List<String> imageUrl = new ArrayList<>();
                    for (AdvertLoopBean.ContentBean contentBean : content) {
                        imageUrl.add(contentBean.getImageUrl());
                    }
                    mRollViewPagerAdapter.setLoop(imageUrl);
                }
            }

            @Override
            public void onError() {

            }
        });
    }

}
