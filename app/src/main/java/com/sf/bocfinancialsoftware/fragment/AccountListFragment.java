package com.sf.bocfinancialsoftware.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.google.gson.Gson;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
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

public class AccountListFragment extends Fragment implements AdapterView.OnItemClickListener, OnRefreshListener, OnLoadmoreListener {

    private Context mContext;
    private SmartRefreshLayout smartRefresh; //刷新
    private AccountListAdapter adapter; //
    private ListView lvFinancialAssistant; //list显示数据
    private LinearLayout lltEmptyView;
    private List<AccountBean.ContentBean.AccountListBean> mDates = new ArrayList<>(); //数据源
    private int currentPage = 1;//分页查询
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
        smartRefresh = (SmartRefreshLayout) view.findViewById(R.id.smartRefresh);
        lltEmptyView = (LinearLayout) view.findViewById(R.id.dataEmpty);
    }

    private void initData() {
        //设置分隔线高度为0
        lvFinancialAssistant.setDividerHeight(0);
        adapter = new AccountListAdapter(getActivity());
        lvFinancialAssistant.setAdapter(adapter);
        //获取网络请求的数据
        getNetWorkData();
        //设置 Header 为 Material风格
        smartRefresh.setRefreshHeader(new MaterialHeader(mContext).setShowBezierWave(true));
        //设置 Footer 为 球脉冲
        smartRefresh.setRefreshFooter(new BallPulseFooter(mContext).setSpinnerStyle(SpinnerStyle.Scale));
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
                isLoadMore = true;
                AccountBean jsonBean = new Gson().fromJson(json, AccountBean.class);
                if (jsonBean != null) {
                    mHasNext = jsonBean.getContent().getHasNext();
                    List<AccountBean.ContentBean.AccountListBean> accountList = jsonBean.getContent().getAccountList();
                    if (currentPage == 1) {
                        mDates.clear();
                        mDates.addAll(accountList);
                        adapter.setDates(accountList);
                    } else {
                        mDates.addAll(accountList);
                        adapter.addPage(accountList);
                    }
                    lltEmptyView.setVisibility(View.GONE);
                    lvFinancialAssistant.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onError() {
                if (currentPage > 1) {
                    currentPage--;
                }
                isLoadMore = true;
                lltEmptyView.setVisibility(View.VISIBLE);
                lvFinancialAssistant.setVisibility(View.GONE);
                ToastUtil.showToast(mContext, "onError");
            }
        });
    }

    private void initListener() {
        lvFinancialAssistant.setOnItemClickListener(this);
        smartRefresh.setOnRefreshListener(this);
        smartRefresh.setOnLoadmoreListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //改变账号
        Intent intent = new Intent(getActivity(), AccountDetailActivity.class);
        intent.putExtra(ConstantConfig.INTENT_KEY_SER, adapter.getDates().get(position));
        startActivity(intent);
    }

    /**
     * smartRefresh 刷新
     *
     * @param refreshlayout
     */
    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        //停止刷新
        refreshlayout.finishRefresh(2000);
        currentPage = 1;
        getNetWorkData();
    }

    /**
     * @param refreshlayout
     */
    @Override
    public void onLoadmore(RefreshLayout refreshlayout) {
        if (isLoadMore) {
            if (TextUtils.equals(mHasNext, "1")) {
                //加载更多
                currentPage++;
                getNetWorkData();
                //停止加载
                refreshlayout.finishLoadmore();
            } else {
                //停止加载
                refreshlayout.finishLoadmore();
                ToastUtil.showToast(mContext, getString(R.string.common_not_date));
            }
        }
    }
}
