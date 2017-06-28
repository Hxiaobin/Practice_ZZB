package com.sf.bocfinancialsoftware.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.sf.bocfinancialsoftware.R;
import com.sf.bocfinancialsoftware.adapter.PleasantMessageAdapter;
import com.sf.bocfinancialsoftware.bean.PleasantMessageBean;
import com.sf.bocfinancialsoftware.util.DataBaseSQLiteUtil;
import com.sf.bocfinancialsoftware.util.SwipeRefreshUtil;

import java.util.List;

/**
 * 温馨提示
 * Created by sn on 2017/6/7.
 */

public class PleasantMessageFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, AbsListView.OnScrollListener {

    private View headView; //列表头部
    private View footView; //列表尾部
    private LinearLayout lltLoadMore; //列表尾部加载更多
    private ListView lvPleasantMessage; //温馨提示列表
    private LinearLayout lltEmptyViewPleasantMessage; // 处理空数据
    private SwipeRefreshLayout swipeRefreshLayoutPleasantMessage;
    private List<PleasantMessageBean> pleasantMessageBeanList; //数据源
    private List<PleasantMessageBean> allPleasantMessageBeanList; //数据源
    private PleasantMessageAdapter adapter; //列表适配器
    private boolean isLastLine = false;  //列表是否滚动到最后一行
    private int page = 0;  //查询页码
    private Handler mHandler = new Handler() {  //主线程中的Handler对象
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 9) {
                //下拉刷新，三秒睡眠之后   重新加载
                page = 0;
                List<PleasantMessageBean> list = DataBaseSQLiteUtil.queryPleasantMessage(page, 4);
                if (list.size() <= 0 || list == null) {
                    Toast.makeText(getActivity(), getString(R.string.common_refresh_failed), Toast.LENGTH_SHORT).show();
                } else {
                    //获取温馨提醒分析列表
                    pleasantMessageBeanList.clear();
                    pleasantMessageBeanList.addAll(list);
                    adapter.notifyDataSetChanged();
                    Toast.makeText(getActivity(), getString(R.string.common_refresh_success), Toast.LENGTH_SHORT).show();
                }
                swipeRefreshLayoutPleasantMessage.setRefreshing(false);//加载完毕，设置不刷新
            } else if (msg.what == 19) { //上拉加载更多
                page++; //页数自增
                List<PleasantMessageBean> loadMoreList = DataBaseSQLiteUtil.queryPleasantMessage(page, 4);
                pleasantMessageBeanList.addAll(loadMoreList);
                isLastLine = false;
                adapter.notifyDataSetChanged();
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pleasant_message, container, false);
        initView(view, inflater);
        initData();
        initListener();
        return view;
    }

    private void initView(View view, LayoutInflater inflater) {
        headView = inflater.inflate(R.layout.layout_list_head, null);
        footView = inflater.inflate(R.layout.layout_list_foot, null);
        lltLoadMore = (LinearLayout) footView.findViewById(R.id.lltLoadMore);
        lvPleasantMessage = (ListView) view.findViewById(R.id.lvPleasantMessage);
        lltEmptyViewPleasantMessage = (LinearLayout) view.findViewById(R.id.lltEmptyViewPleasantMessage);
        swipeRefreshLayoutPleasantMessage = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayoutPleasantMessage);
    }

    private void initData() {
        pleasantMessageBeanList = DataBaseSQLiteUtil.queryPleasantMessage(page, 4);
        adapter = new PleasantMessageAdapter(getActivity(), pleasantMessageBeanList);
        lvPleasantMessage.addHeaderView(headView);
        lvPleasantMessage.addFooterView(footView);
        lvPleasantMessage.setAdapter(adapter);
        lvPleasantMessage.setEmptyView(lltEmptyViewPleasantMessage);
        allPleasantMessageBeanList = DataBaseSQLiteUtil.queryAllPleasantMessage();
        if (pleasantMessageBeanList.size() >= allPleasantMessageBeanList.size()) {
            lltLoadMore.setVisibility(View.GONE);// 如果加载完毕，隐藏掉正在加载图标
        }
        adapter.notifyDataSetChanged();
        SwipeRefreshUtil.setRefreshCircle(swipeRefreshLayoutPleasantMessage); //设置刷新样式
    }

    private void initListener() {
        swipeRefreshLayoutPleasantMessage.setOnRefreshListener(this);
        lvPleasantMessage.setOnScrollListener(this);
    }

    /**
     * 下拉刷新
     */
    @Override
    public void onRefresh() {
        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    sleep(1000); //睡眠1秒
                    Message msg = new Message();
                    msg.what = 9;
                    mHandler.sendMessage(msg);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (scrollState == SCROLL_STATE_IDLE && isLastLine) { //停止滚动，且滚动到最后一行
            if (pleasantMessageBeanList.size() >= allPleasantMessageBeanList.size()) { // 如果加载完毕，隐藏掉正在加载图标
                lltLoadMore.setVisibility(View.GONE);
                Toast.makeText(getActivity(), getString(R.string.common_not_date), Toast.LENGTH_SHORT).show();
            } else {
                new Thread() {
                    @Override
                    public void run() {
                        super.run();
                        try {
                            sleep(1000); //睡眠1秒
                            Message msg = new Message();
                            msg.what = 19;
                            mHandler.sendMessage(msg);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }.start();
            }
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if (firstVisibleItem + visibleItemCount == totalItemCount && totalItemCount > 0) {  //当滚到最后一行
            isLastLine = true;
        }
    }

}
