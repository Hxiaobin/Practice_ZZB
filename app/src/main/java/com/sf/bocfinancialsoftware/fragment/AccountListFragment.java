package com.sf.bocfinancialsoftware.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.google.gson.Gson;
import com.sf.bocfinancialsoftware.R;
import com.sf.bocfinancialsoftware.activity.financialAssistant.AccountDetailActivity;
import com.sf.bocfinancialsoftware.adapter.account.AccountListAdapter;
import com.sf.bocfinancialsoftware.bean.account.AccountBean;
import com.sf.bocfinancialsoftware.constant.ConstantConfig;
import com.sf.bocfinancialsoftware.constant.URLConfig;
import com.sf.bocfinancialsoftware.util.NetWorkUtils;
import com.sf.bocfinancialsoftware.util.ToastUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AccountListFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, AdapterView.OnItemClickListener, AbsListView.OnScrollListener {

    private Context mContext;
    private SwipeRefreshLayout srlRefresh; //刷新
    private AccountListAdapter adapter; //
    private ListView lvFinancialAssistant; //list显示数据
    private View footView;//listView底部显示加载
    private LinearLayout lltLoad;//加载
    private List<AccountBean.ContentBean.AccountListBean> mDates = new ArrayList<>(); //数据源
    private int currentPage = 1;//分页查询
    private boolean isRefresh;//是否刷新
    private boolean isLoadMore = true;//是否加载
    private String mHasNext;//是否有下一页

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_financial_assistant, container, false);
        mContext = getContext();
        initView(view);
        initData();
        initListener();
        return view;
    }

    private void initView(View view) {
        lvFinancialAssistant = (ListView) view.findViewById(R.id.lvFinancialAssistant);
        srlRefresh = (SwipeRefreshLayout) view.findViewById(R.id.srlRefresh);
        footView = View.inflate(mContext, R.layout.layout_lv_loading_foot, null);
        lltLoad = (LinearLayout) footView.findViewById(R.id.lltLoad);
    }

    private void initData() {
        lltLoad.setVisibility(View.GONE);
        //设置进度动画的颜色
        srlRefresh.setColorSchemeColors(getResources().getColor(R.color.redLight));
        //设置分隔线高度为0
        lvFinancialAssistant.setDividerHeight(0);
        adapter = new AccountListAdapter(getActivity());
        lvFinancialAssistant.addFooterView(footView);
        lvFinancialAssistant.setAdapter(adapter);
        //获取网络请求的数据
        getNetWorkData();
    }

    /**
     * 获取网络请求的数据
     */
    private void getNetWorkData() {
        isLoadMore = false;
        HashMap<String, String> accountMap = new HashMap<>();
        accountMap.put("currentPage", currentPage + "");
        NetWorkUtils.doPost(mContext, URLConfig.ACCOUNT_LIST, accountMap, new NetWorkUtils.RequestCallBack() {
            @Override
            public void onSuccess(String json) {
//                Type type = new TypeToken<List<AccountList.ContentBean.AccountListBean>>(){}.getType();
                srlRefresh.setRefreshing(false);
                isLoadMore = true;
                AccountBean jsonBean = new Gson().fromJson(json, AccountBean.class);
                if (jsonBean != null) {
                    lltLoad.setVisibility(View.VISIBLE);
                    mHasNext = jsonBean.getContent().getHasNext();
                    List<AccountBean.ContentBean.AccountListBean> accountList = jsonBean.getContent().getAccountList();
                    if (currentPage == 1) {
                        if (isRefresh) {
                            ToastUtil.showToast(mContext, getString(R.string.common_refresh_success));
                        }
                        mDates.clear();
                        mDates.addAll(accountList);
                        adapter.setDates(accountList);
                    } else {
                        mDates.addAll(accountList);
                        adapter.addPage(accountList);
                    }
                }
            }

            @Override
            public void onError() {
                if (currentPage > 1) {
                    currentPage--;
                }
                isLoadMore = true;
                lltLoad.setVisibility(View.GONE);
                srlRefresh.setRefreshing(false);
                ToastUtil.showToast(mContext, "onError");
            }
        });
    }

    private void initListener() {
        lvFinancialAssistant.setOnItemClickListener(this);
        lvFinancialAssistant.setOnScrollListener(this);
        srlRefresh.setOnRefreshListener(this);
    }

    /**
     * 加载 刷新
     */
    @Override
    public void onRefresh() {
        currentPage = 1;
        isRefresh = true;
        getNetWorkData();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //改变账号
        Intent intent = new Intent(getActivity(), AccountDetailActivity.class);
        intent.putExtra(ConstantConfig.INTENT_KEY_SER, adapter.getDates().get(position));
        startActivity(intent);
    }

    //当滑动状态发生改变的时候
    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        switch (scrollState) {
            //当不滚动的时候
            case SCROLL_STATE_IDLE:
                if (isLoadMore && view.getLastVisiblePosition() == (view.getCount()) - 1) {
                    if (TextUtils.equals(mHasNext, "1")) {
                        //加载更多
                        currentPage++;
                        getNetWorkData();
                    } else {
                        lltLoad.setVisibility(View.GONE);
                        ToastUtil.showToast(mContext, getString(R.string.common_not_date));
                    }
                }
                break;
        }

    }

    //正在滑动的时候
    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

    }

}
