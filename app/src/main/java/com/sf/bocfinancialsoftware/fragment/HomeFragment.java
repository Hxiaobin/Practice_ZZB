package com.sf.bocfinancialsoftware.fragment;

import android.content.Intent;
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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.jude.rollviewpager.RollPagerView;
import com.sf.bocfinancialsoftware.R;
import com.sf.bocfinancialsoftware.activity.home.analyse.BocAnalyseDetailActivity;
import com.sf.bocfinancialsoftware.activity.home.analyse.BocAnalyseListActivity;
import com.sf.bocfinancialsoftware.activity.home.business.BusinessQueryActivity;
import com.sf.bocfinancialsoftware.activity.home.message.MessageReminderActivity;
import com.sf.bocfinancialsoftware.adapter.HomeFragmentBocAnalyseAdapter;
import com.sf.bocfinancialsoftware.adapter.ImageAdapter;
import com.sf.bocfinancialsoftware.bean.BocAnalyseBean;
import com.sf.bocfinancialsoftware.bean.MessageReminderBean;
import com.sf.bocfinancialsoftware.util.DataBaseSQLiteUtil;
import com.sf.bocfinancialsoftware.util.SwipeRefreshUtil;
import com.sf.bocfinancialsoftware.widget.SwipeRefreshLayoutHome;

import java.util.List;

import static com.sf.bocfinancialsoftware.constant.ConstantConfig.IMAGE_LIST;
import static com.sf.bocfinancialsoftware.constant.ConstantConfig.MSG_TOTAL_READ_SUM;
import static com.sf.bocfinancialsoftware.constant.ConstantConfig.MSG_TOTAL_UN_REN_SUM_REQUEST;
import static com.sf.bocfinancialsoftware.constant.ConstantConfig.MSG_TOTAL_UN_REN_SUM_RESPONSE;
import static com.sf.bocfinancialsoftware.constant.ConstantConfig.MSG_UN_READ;
import static com.sf.bocfinancialsoftware.constant.ConstantConfig.NEWS_ID;

/**
 * 首页
 * Created by sn on 2017/6/7.
 */

public class HomeFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemClickListener, AbsListView.OnScrollListener, SwipeRefreshLayout.OnRefreshListener {

    private View headView; //列表头部
    private View footView; //列表尾部
    private LinearLayout lltLoadMore; //列表尾部加载更多
    private RollPagerView rollPagerViewHome; //图片轮播
    private Button btnMessageReminder;  //通知提醒
    private Button btnBusinessQuery;  //业务查询
    private Button btnBOCAnalyse;  //中银分析
    private TextView tvMsgTotalReadSum;  //通知总未读数量
    private SwipeRefreshLayoutHome swipeRefreshLayoutHomeFragmentBocAnalyse;  //下拉刷新控件
    private ListView lvHomeFragmentBocAnalyse; //中银分析新闻列表
    private LinearLayout lltEmptyViewBocAnalyseHome; //处理空数据
    private List<BocAnalyseBean> bocAnalyseBeanList;  //中银分析Bean类集合
    private List<BocAnalyseBean> allBocAnalyseBeanList; //所有的数据列表
    private HomeFragmentBocAnalyseAdapter bocAnalyseAdapter; //中银分析列表适配器
    private ImageAdapter imageAdapter;  //图片轮播适配器
    private boolean isLastLine = false;  //列表是否滚动到最后一行
    private int page = 0; //查询页码
    private Handler mHandler = new Handler() {  //主线程中的Handler对象
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 8) {
                //下拉刷新，1秒睡眠之后   重新加载
                page = 0;
                List<BocAnalyseBean> list = DataBaseSQLiteUtil.queryBocAnalyseList(page, 8); //获取中银分析列表
                if (list == null || list.size() <= 0) {
                    Toast.makeText(getActivity(), getString(R.string.common_refresh_failed), Toast.LENGTH_SHORT).show();
                } else {
                    bocAnalyseBeanList.clear();
                    bocAnalyseBeanList.addAll(list);
                    bocAnalyseAdapter.notifyDataSetChanged();
                    Toast.makeText(getActivity(), getString(R.string.common_refresh_success), Toast.LENGTH_SHORT).show();
                }
                swipeRefreshLayoutHomeFragmentBocAnalyse.setRefreshing(false);//加载完毕，设置不刷新
            } else if (msg.what == 18) { //上拉加载更多
                page++; //页数自增
                List<BocAnalyseBean> loadMoreList = DataBaseSQLiteUtil.queryBocAnalyseList(page, 8);
                bocAnalyseBeanList.addAll(loadMoreList);
                isLastLine = false;
                bocAnalyseAdapter.notifyDataSetChanged();
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        initView(view, inflater);      //初始化控件
        initData();      // 初始化数据
        initListener();  //初始化监听事件
        return view;
    }

    private void initView(View view, LayoutInflater inflater) {
        headView = inflater.inflate(R.layout.layout_home_head_view, null);
        footView = inflater.inflate(R.layout.layout_list_foot, null);
        rollPagerViewHome = (RollPagerView) headView.findViewById(R.id.rollPagerViewHome);
        lltLoadMore = (LinearLayout) footView.findViewById(R.id.lltLoadMore);
        btnMessageReminder = (Button) headView.findViewById(R.id.btnMessageReminder);
        btnBusinessQuery = (Button) headView.findViewById(R.id.btnBusinessQuery);
        btnBOCAnalyse = (Button) headView.findViewById(R.id.btnBOCAnalyse);
        tvMsgTotalReadSum = (TextView) headView.findViewById(R.id.tvMsgTotalReadSum);
        swipeRefreshLayoutHomeFragmentBocAnalyse = (SwipeRefreshLayoutHome) view.findViewById(R.id.swipeRefreshLayoutHomeFragmentBocAnalyse);
        lvHomeFragmentBocAnalyse = (ListView) view.findViewById(R.id.lvHomeFragmentBocAnalyse);
        lltEmptyViewBocAnalyseHome = (LinearLayout) view.findViewById(R.id.lltEmptyViewBocAnalyseHome);
    }

    private void initData() {
        getBocAnalyseList(); //获取中银分析列表数据
        imageAdapter = new ImageAdapter(getActivity(), IMAGE_LIST);
        rollPagerViewHome.setAdapter(imageAdapter);
        lvHomeFragmentBocAnalyse.setEmptyView(lltEmptyViewBocAnalyseHome); //处理空ListView
        SwipeRefreshUtil.setRefreshCircle(swipeRefreshLayoutHomeFragmentBocAnalyse); //设置刷新样式
        getMsgTotalUnReadSum(); //初始化的时候，获取通知的总未读数量
    }

    private void getMsgTotalUnReadSum() {
        List<MessageReminderBean> msgList = DataBaseSQLiteUtil.queryAllMessageReminderList(); //获取全部类型的所有通知
        int totalUnReadSum = 0;
        for (MessageReminderBean bean : msgList) { //遍历通知消息列表
            if (bean.getMsgIsRead().equals(MSG_UN_READ)) { //如果消息状态为未读状态
                totalUnReadSum++;
            }
        }
        if (totalUnReadSum <= 0) {
            tvMsgTotalReadSum.setVisibility(View.GONE);
        } else {
            tvMsgTotalReadSum.setText(String.valueOf(totalUnReadSum));
        }
    }

    private void initListener() {
        btnMessageReminder.setOnClickListener(this);
        btnBusinessQuery.setOnClickListener(this);
        btnBOCAnalyse.setOnClickListener(this);
        lvHomeFragmentBocAnalyse.setOnItemClickListener(this);
        lvHomeFragmentBocAnalyse.setOnScrollListener(this);
        swipeRefreshLayoutHomeFragmentBocAnalyse.setOnRefreshListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.btnMessageReminder: //通知提醒
                intent = new Intent(getActivity(), MessageReminderActivity.class);
                startActivityForResult(intent, MSG_TOTAL_UN_REN_SUM_REQUEST);
                break;
            case R.id.btnBusinessQuery:  //业务查询
                intent = new Intent(getActivity(), BusinessQueryActivity.class);
                startActivity(intent);
                break;
            case R.id.btnBOCAnalyse:  //中银分析
                intent = new Intent(getActivity(), BocAnalyseListActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        BocAnalyseBean bocAnalyseBean = bocAnalyseBeanList.get(position - 1); //当前点击项
        String newsId = bocAnalyseBean.getNewsId(); //获取新闻id,传递给详情页
        Intent intent = new Intent(getActivity(), BocAnalyseDetailActivity.class);
        intent.putExtra(NEWS_ID, newsId);
        startActivity(intent);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == MSG_TOTAL_UN_REN_SUM_REQUEST && resultCode == MSG_TOTAL_UN_REN_SUM_RESPONSE) { //通知总未读数量
            int unReadSum = data.getIntExtra(MSG_TOTAL_READ_SUM, 0); //剩余总未读数量
            if (unReadSum <= 0) {
                tvMsgTotalReadSum.setVisibility(View.GONE);
            } else {
                tvMsgTotalReadSum.setText(String.valueOf(unReadSum));
            }
        }

    }

    /**
     * 获取中银分析列表
     */
    private void getBocAnalyseList() {
        lvHomeFragmentBocAnalyse.addHeaderView(headView);
        lvHomeFragmentBocAnalyse.addFooterView(footView);
        bocAnalyseBeanList = DataBaseSQLiteUtil.queryBocAnalyseList(page, 10); //获取中银分析列表
        bocAnalyseAdapter = new HomeFragmentBocAnalyseAdapter(getActivity(), bocAnalyseBeanList);
        lvHomeFragmentBocAnalyse.setAdapter(bocAnalyseAdapter);
        allBocAnalyseBeanList = DataBaseSQLiteUtil.queryBocAnalyseList();
        if (bocAnalyseBeanList.size() >= allBocAnalyseBeanList.size()) {
            lltLoadMore.setVisibility(View.GONE);// 如果加载完毕，隐藏掉正在加载图标
        }
        bocAnalyseAdapter.notifyDataSetChanged();
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
                    sleep(1000); //睡眠3秒
                    Message msg = new Message();
                    msg.what = 8;
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
            if (bocAnalyseBeanList.size() >= allBocAnalyseBeanList.size()) { // 如果加载完毕，隐藏掉正在加载图标
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
                            msg.what = 18;
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
        } else {
            isLastLine = false;
        }
    }

}
