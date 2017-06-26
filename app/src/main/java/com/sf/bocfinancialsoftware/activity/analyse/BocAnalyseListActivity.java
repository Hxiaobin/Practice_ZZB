package com.sf.bocfinancialsoftware.activity.analyse;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.sf.bocfinancialsoftware.R;
import com.sf.bocfinancialsoftware.activity.base.BaseActivity;
import com.sf.bocfinancialsoftware.adapter.BocAnalyseAdapter;
import com.sf.bocfinancialsoftware.bean.BocAnalyseBean;
import com.sf.bocfinancialsoftware.util.DataBaseSQLiteUtil;
import com.sf.bocfinancialsoftware.util.SwipeRefreshUtil;

import java.util.List;

import static com.sf.bocfinancialsoftware.constant.ConstantConfig.NEWS_ID;

/**
 * 中银分析列表
 * Created by sn on 2017/6/8.
 */

public class BocAnalyseListActivity extends BaseActivity implements AdapterView.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener, AbsListView.OnScrollListener {

    private ImageView ivTitleBarBack;  //返回按钮
    private TextView tvTitleBarTitle; //标题
    private SwipeRefreshLayout swipeRefreshLayoutBocAnalyse; //下拉刷新上拉加载
    private ListView lvBocAnalyse; //中银分析列表
    private View headView; //列表头部
    private View footView; //列表尾部
    private LinearLayout lltLoadMore; //列表尾部加载更多
    private LinearLayout lltEmptyViewBocAnalyse; //无数据是显示的视图
    private List<BocAnalyseBean> bocAnalyseBeanList; //已加载的数据
    private List<BocAnalyseBean> allBocAnalyseBeanList; //所有的数据列表
    private BocAnalyseAdapter bocAnalyseAdapter; //列表适配器
    private boolean isLastLine = false;  //列表是否滚动到最后一行
    private int page = 0;
    private Handler mHandler = new Handler() {  //主线程中的Handler对象
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 6) {
                //下拉刷新，1秒睡眠之后   重新加载
                page = 0;
                List<BocAnalyseBean> list = DataBaseSQLiteUtil.queryBocAnalyseList(page,5);
                if (list == null || list.size() <= 0) { //如果列表数据为空，
                    Toast.makeText(BocAnalyseListActivity.this, getString(R.string.common_refresh_failed), Toast.LENGTH_SHORT).show();
                } else { //刷新之后，数据不为空
                    bocAnalyseBeanList.clear(); //清空原列表数据
                    bocAnalyseBeanList.addAll(list);
                    bocAnalyseAdapter.notifyDataSetChanged();
                    Toast.makeText(BocAnalyseListActivity.this, getString(R.string.common_refresh_success), Toast.LENGTH_SHORT).show();
                }
                swipeRefreshLayoutBocAnalyse.setRefreshing(false);//加载完毕，设置不刷新
            } else if (msg.what == 16) { //上拉加载更多
                page++; //页数自增
                List<BocAnalyseBean> loadMoreList = DataBaseSQLiteUtil.queryBocAnalyseList(page, 5);
                bocAnalyseBeanList.addAll(loadMoreList);
                isLastLine = false;
                bocAnalyseAdapter.notifyDataSetChanged();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boc_analyse);
        initView();
        initData();
        initListener();
    }

    @Override
    protected void initView() {
        ivTitleBarBack = (ImageView) findViewById(R.id.ivTitleBarBack);
        tvTitleBarTitle = (TextView) findViewById(R.id.tvTitleBarTitle);
        swipeRefreshLayoutBocAnalyse = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayoutBocAnalyse);
        lvBocAnalyse = (ListView) findViewById(R.id.lvBocAnalyse);
        headView = LayoutInflater.from(this).inflate(R.layout.layout_list_head, null);
        footView = LayoutInflater.from(this).inflate(R.layout.layout_list_foot, null);
        lltLoadMore = (LinearLayout) footView.findViewById(R.id.lltLoadMore);
        lltEmptyViewBocAnalyse = (LinearLayout) findViewById(R.id.lltEmptyViewBocAnalyse);
    }

    @Override
    protected void initData() {
        ivTitleBarBack.setVisibility(View.VISIBLE);
        tvTitleBarTitle.setText(getString(R.string.common_boc_analyse));
        bocAnalyseBeanList = DataBaseSQLiteUtil.queryBocAnalyseList(page, 5);
        bocAnalyseAdapter = new BocAnalyseAdapter(BocAnalyseListActivity.this, bocAnalyseBeanList);
        lvBocAnalyse.addHeaderView(headView);
        lvBocAnalyse.addFooterView(footView);
        lvBocAnalyse.setAdapter(bocAnalyseAdapter);
        lvBocAnalyse.setEmptyView(lltEmptyViewBocAnalyse); //处理空ListView
        allBocAnalyseBeanList = DataBaseSQLiteUtil.queryBocAnalyseList();
        if (bocAnalyseBeanList.size() >= allBocAnalyseBeanList.size()) {
            lltLoadMore.setVisibility(View.GONE);// 如果加载完毕，隐藏掉正在加载图标
        }
        bocAnalyseAdapter.notifyDataSetChanged();
        SwipeRefreshUtil.setRefreshCircle(swipeRefreshLayoutBocAnalyse);
    }

    @Override
    protected void initListener() {
        ivTitleBarBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        lvBocAnalyse.setOnItemClickListener(this);
        swipeRefreshLayoutBocAnalyse.setOnRefreshListener(this);
        lvBocAnalyse.setOnScrollListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        BocAnalyseBean bocAnalyseBean = bocAnalyseBeanList.get(position); //当前点击项
        String newsId = bocAnalyseBean.getNewsId();
        Intent intent = new Intent(BocAnalyseListActivity.this, BocAnalyseDetailActivity.class);
        intent.putExtra(NEWS_ID, newsId);
        startActivity(intent);
    }

    /**
     * 下拉加载
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
                    msg.what = 6;
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
                Toast.makeText(BocAnalyseListActivity.this, getString(R.string.common_not_date), Toast.LENGTH_SHORT).show();
            } else {
                new Thread() {
                    @Override
                    public void run() {
                        super.run();
                        try {
                            sleep(1000); //睡眠3秒
                            Message msg = new Message();
                            msg.what = 16;
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
