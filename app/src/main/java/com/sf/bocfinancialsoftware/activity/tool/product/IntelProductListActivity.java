package com.sf.bocfinancialsoftware.activity.tool.product;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.sf.bocfinancialsoftware.R;
import com.sf.bocfinancialsoftware.adapter.product.IntelProductListAdapter;
import com.sf.bocfinancialsoftware.base.BaseActivity;
import com.sf.bocfinancialsoftware.bean.product.IntelProductListBean;
import com.sf.bocfinancialsoftware.constant.URLConfig;
import com.sf.bocfinancialsoftware.util.NetWorkUtils;
import com.sf.bocfinancialsoftware.util.ToastUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.view.View.VISIBLE;

/**
 * 产品介绍
 */
public class IntelProductListActivity extends BaseActivity implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener, AbsListView.OnScrollListener {

    private ImageView ivBack; //返回按钮
    private EditText etSearch; //搜索框 输入框
    private ImageView ivSearch; //搜索按钮
    private ImageView ivDelete;//删除键
    private SwipeRefreshLayout srlRefresh;//刷新
    private View footView;//ListView底部
    private LinearLayout lltLoad;//显示加载
    private ListView lvProduct1; //listview1显示数据源的数据
    private LinearLayout linearLayout;//搜索的布局
    private List<IntelProductListBean.ContentBean.TypeListBean> mDatas = new ArrayList<>(); //数据源的数据
    private List<IntelProductListBean.ContentBean.TypeListBean> mResultDatas = new ArrayList<>(); //搜索结果返回的数据
    private IntelProductListAdapter mAdapter;
    private int currentPage = 1;//分页查询
    private boolean isRefresh;//是否刷新
    private boolean isLoadMore = true;//是否加载
    private String mHasNext;//是否有下一页

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        initView();
        initData();
        initListener();
    }

    @Override
    protected void initView() {
        TextView tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvTitle.setText(R.string.tv_product_introduce);
        etSearch = (EditText) findViewById(R.id.etSearch);
        ivSearch = (ImageView) findViewById(R.id.ivSearch);
        lvProduct1 = (ListView) findViewById(R.id.lvProduct1);
        ivBack = (ImageView) findViewById(R.id.ivBack);
        ivDelete = (ImageView) findViewById(R.id.ivDelete);
        srlRefresh = (SwipeRefreshLayout) findViewById(R.id.srlRefresh);
        footView = View.inflate(mContext, R.layout.layout_lv_loading_foot, null);
        lltLoad = (LinearLayout) footView.findViewById(R.id.lltLoad);
        linearLayout = (LinearLayout) findViewById(R.id.linearLayout);
    }

    @Override
    protected void initData() {
        lltLoad.setVisibility(View.GONE);
        //设置刷新进度动画的颜色
        srlRefresh.setColorSchemeColors(getResources().getColor(R.color.redLight));
        mAdapter = new IntelProductListAdapter(mContext);
        lvProduct1.addFooterView(footView);
        lvProduct1.setAdapter(mAdapter);
        //获取网络数据
        getIntelProductData();
    }

    @Override
    protected void initListener() {
        ivBack.setOnClickListener(this);
        ivSearch.setOnClickListener(this);
        ivDelete.setOnClickListener(this);
        srlRefresh.setOnRefreshListener(this);
        lvProduct1.setOnScrollListener(this);
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
     */
    public void onSearch(String text) {
        isLoadMore = false;
        if (!TextUtils.isEmpty(text)) {
            getResultData(text);
            //更新适配器
            mAdapter.setTypeListBeen(mResultDatas);
        }
    }

    /**
     * 获取搜索结果data和adapter
     */
    private void getResultData(String text) {
        mResultDatas.clear();
        for (int i = 0; i < mDatas.size(); i++) {
            IntelProductListBean.ContentBean.TypeListBean typeListBeanResult = new IntelProductListBean.ContentBean.TypeListBean();
            List<IntelProductListBean.ContentBean.TypeListBean.ProductArrayBean> productArrayResult = new ArrayList<>();

            IntelProductListBean.ContentBean.TypeListBean typeListBean = mDatas.get(i);
            List<IntelProductListBean.ContentBean.TypeListBean.ProductArrayBean> productArray = typeListBean.getProductArray();
            for (int j = 0; j < productArray.size(); j++) {
                IntelProductListBean.ContentBean.TypeListBean.ProductArrayBean productArrayBean = productArray.get(j);
                String productName = productArrayBean.getProductName();
                if (productName.contains(text.trim())) {
                    productArrayResult.add(productArrayBean);
                }
            }
            if (productArrayResult.size() != 0) {
                typeListBeanResult.setTypeId(typeListBean.getTypeId());
                typeListBeanResult.setTypeName(typeListBean.getTypeName());
                typeListBeanResult.setProductArray(productArrayResult);
                mResultDatas.add(typeListBeanResult);
            }
        }
        if (mResultDatas.size() == 0) {
            ToastUtil.showToast(mContext, getString(R.string.text_not_search));
        }
        lltLoad.setVisibility(View.GONE);
    }

    public void getIntelProductData() {
        isLoadMore = false;
        HashMap<String, String> productMap = new HashMap<>();
        productMap.put("currentPage", currentPage + "");
        NetWorkUtils.doPost(mContext, URLConfig.PRODUCT_LIST_CASE, productMap, new NetWorkUtils.RequestCallBack() {
            @Override
            public void onSuccess(String json) {
                isLoadMore = true;
                srlRefresh.setRefreshing(false);
                IntelProductListBean jsonBean = new Gson().fromJson(json, IntelProductListBean.class);
                if (jsonBean != null) {
                    lltLoad.setVisibility(VISIBLE);
                    List<IntelProductListBean.ContentBean.TypeListBean> typeList = jsonBean.getContent().getTypeList();
                    mHasNext = jsonBean.getContent().getHasNext();
                    if (currentPage == 1) {
                        if (isRefresh) {
                            ToastUtil.showToast(mContext, getString(R.string.common_refresh_success));
                        }
                        mDatas.clear();
                        mDatas.addAll(typeList);
                        mAdapter.setTypeListBeen(typeList);
                    } else {
                        mDatas.addAll(typeList);
                        mAdapter.addPage(typeList);
                    }
                    linearLayout.setVisibility(VISIBLE);
                }
            }

            @Override
            public void onError() {
                if (currentPage > 1) {
                    currentPage--;
                }
                isLoadMore = true;
                srlRefresh.setRefreshing(false);
                lltLoad.setVisibility(View.GONE);
                linearLayout.setVisibility(View.INVISIBLE);
                ToastUtil.showToast(mContext, "onError");
            }
        });
    }

    /**
     * 下拉刷新
     */
    @Override
    public void onRefresh() {
        currentPage = 1;
        isRefresh = true;
        getIntelProductData();
    }

    /**
     * 当滑动状态发生改变的时候
     *
     * @param view
     * @param scrollState
     */
    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        switch (scrollState) {
            case SCROLL_STATE_IDLE: //当不滚动的时候
                if (isLoadMore && view.getLastVisiblePosition() == (view.getCount()) - 1) {
                    if (TextUtils.equals(mHasNext, "1")) {
                        //加载更多
                        currentPage++;
                        getIntelProductData();
                    } else {
                        lltLoad.setVisibility(View.GONE);
                        ToastUtil.showToast(mContext, getString(R.string.common_not_date));
                    }
                }
                break;
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

    }
}
