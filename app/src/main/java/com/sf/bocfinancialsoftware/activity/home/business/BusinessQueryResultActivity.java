package com.sf.bocfinancialsoftware.activity.home.business;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.sf.bocfinancialsoftware.R;
import com.sf.bocfinancialsoftware.base.BaseActivity;
import com.sf.bocfinancialsoftware.adapter.BusinessAdapter;
import com.sf.bocfinancialsoftware.bean.ContractBean;
import com.sf.bocfinancialsoftware.util.DataBaseSQLiteUtil;
import com.sf.bocfinancialsoftware.util.SwipeRefreshUtil;

import java.util.List;

import static com.sf.bocfinancialsoftware.constant.ConstantConfig.BUSINESS_NAME;
import static com.sf.bocfinancialsoftware.constant.ConstantConfig.CONTRACT_ID;
import static com.sf.bocfinancialsoftware.constant.ConstantConfig.END_DATE;
import static com.sf.bocfinancialsoftware.constant.ConstantConfig.START_DATE;

/**
 * 业务查询结果
 * Created by sn on 2017/6/15.
 */
public class BusinessQueryResultActivity extends BaseActivity implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener, AbsListView.OnScrollListener {

    private ImageView ivTitleBarBack;  //返回
    private ImageView ivTitleBarScan;  //扫一扫
    private TextView tvTitleBarTitle;  //标题
    private SwipeRefreshLayout swipeRefreshLayoutBusinessQueryResult; //下拉刷新上拉加载
    private ListView lvBusinessQueryResult; // 合同列表
    private View headView; //列表头部
    private View footView; //列表尾部
    private LinearLayout lltLoadMore; //列表尾部加载更多
    private LinearLayout lltEmptyViewBusinessQuery; //无数据是显示的视图
    private List<ContractBean> contractBeanList; // 合同数据
    private List<ContractBean> allContractBeanList; // 合同数据
    private BusinessAdapter adapter; //适配器
    private Intent intent;
    private String businessName; //业务类型id
    private String startDate; //开始时间
    private String endDate; // 结束时间
    private String contractId; //业务编号
    private boolean isLastLine = false;  //列表是否滚动到最后一行
    private int page = 0;  //查询页码
    private Handler mHandler = new Handler() {  //主线程中的Handler对象
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 7) {
                //下拉刷新，1秒睡眠之后   重新加载
                page = 0;
                List<ContractBean> list = getQueryResult(businessName, startDate, endDate, contractId, page, 5);
                if (list == null || list.size() <= 0) {
                    Toast.makeText(BusinessQueryResultActivity.this, getString(R.string.common_refresh_failed), Toast.LENGTH_SHORT).show();
                } else {
                    contractBeanList.clear();  //清空原数据
                    contractBeanList.addAll(list); //从新插入新数据
                    adapter.notifyDataSetChanged();
                    Toast.makeText(BusinessQueryResultActivity.this, getString(R.string.common_refresh_success), Toast.LENGTH_SHORT).show();
                }
                swipeRefreshLayoutBusinessQueryResult.setRefreshing(false);//加载完毕，设置不刷新
            } else if (msg.what == 17) { //上拉加载更多
                page = page + 5; //页数自增
                List<ContractBean> loadMoreList = getQueryResult(businessName, startDate, endDate, contractId, page, 5);
                contractBeanList.addAll(loadMoreList);
                isLastLine = false;
                adapter.notifyDataSetChanged();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_query_result);
        initView();
        initData();
        initListener();
    }

    @Override
    protected void initView() {
        ivTitleBarBack = (ImageView) findViewById(R.id.ivTitleBarBack);
        ivTitleBarScan = (ImageView) findViewById(R.id.ivTitleBarScan);
        tvTitleBarTitle = (TextView) findViewById(R.id.tvTitleBarTitle);
        swipeRefreshLayoutBusinessQueryResult = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayoutBusinessQueryResult);
        lvBusinessQueryResult = (ListView) findViewById(R.id.lvBusinessQueryResult);
        headView = LayoutInflater.from(this).inflate(R.layout.layout_list_head, null);
        footView = LayoutInflater.from(this).inflate(R.layout.layout_list_foot, null);
        lltLoadMore = (LinearLayout) footView.findViewById(R.id.lltLoadMore);
        lltEmptyViewBusinessQuery = (LinearLayout) findViewById(R.id.lltEmptyViewBusinessQuery);
    }

    @Override
    protected void initData() {
        tvTitleBarTitle.setText(getString(R.string.activity_business_query_result_title));
        ivTitleBarBack.setVisibility(View.VISIBLE);
        ivTitleBarScan.setVisibility(View.GONE);
        intent = getIntent();
        businessName = intent.getStringExtra(BUSINESS_NAME);
        startDate = intent.getStringExtra(START_DATE);
        endDate = intent.getStringExtra(END_DATE);
        contractId = intent.getStringExtra(CONTRACT_ID);
        page = 0;
        contractBeanList = getQueryResult(businessName, startDate, endDate, contractId, 0, 5); //根据输入条件查询
        adapter = new BusinessAdapter(BusinessQueryResultActivity.this, contractBeanList);
        lvBusinessQueryResult.addHeaderView(headView);
        lvBusinessQueryResult.addFooterView(footView);
        lvBusinessQueryResult.setAdapter(adapter);
        lvBusinessQueryResult.setEmptyView(lltEmptyViewBusinessQuery); //处理空ListView
        allContractBeanList = getQueryResult(businessName, startDate, endDate, contractId, 0, 5); //根据输入条件查询
        if (contractBeanList.size() >= allContractBeanList.size()) {
            lltLoadMore.setVisibility(View.GONE);// 如果加载完毕，隐藏掉正在加载图标
        }
        adapter.notifyDataSetChanged();
        SwipeRefreshUtil.setRefreshCircle(swipeRefreshLayoutBusinessQueryResult); //刷新样式
    }

    @Override
    protected void initListener() {
        ivTitleBarBack.setOnClickListener(this);
        swipeRefreshLayoutBusinessQueryResult.setOnRefreshListener(this);
        lvBusinessQueryResult.setOnScrollListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivTitleBarBack: //返回
                finish();
                break;
            default:
                break;
        }
    }

    /**
     * 根据条件查询合同
     *
     * @param start
     * @param end
     * @param contractId
     */
    private List<ContractBean> getQueryResult(String businessName, String start, String end, String contractId, int page, int count) {
        List<ContractBean> list = null;
        if (!TextUtils.isEmpty(businessName)) { //业务类型名称不为空，
            list = DataBaseSQLiteUtil.queryContractList(businessName, start, end, contractId, page, count);
            if (list == null || list.size() <= 0) {
                if (!TextUtils.isEmpty(startDate) && !TextUtils.isEmpty(endDate) && TextUtils.isEmpty(contractId)) { //通过开始时间+结束时间查询
                    Toast.makeText(BusinessQueryResultActivity.this, businessName + getString(R.string.activity_business_query_no_date_for_this_time), Toast.LENGTH_LONG).show();
                } else if (!TextUtils.isEmpty(contractId) && (TextUtils.isEmpty(startDate) || TextUtils.isEmpty(endDate))) {//通过业务编号查询
                    Toast.makeText(BusinessQueryResultActivity.this, businessName + getString(R.string.activity_business_query_no_date_for_this_contractId), Toast.LENGTH_SHORT).show();
                } else if (!TextUtils.isEmpty(startDate) && !TextUtils.isEmpty(endDate) && !TextUtils.isEmpty(contractId)) { //通过三者查询
                    Toast.makeText(BusinessQueryResultActivity.this, businessName + getString(R.string.activity_business_query_no_date_for_this_time_and_contract_id), Toast.LENGTH_SHORT).show();
                }
            }
        } else { //业务类型名称为空,只根据业务id查询
            if (!TextUtils.isEmpty(contractId)) {
                list = DataBaseSQLiteUtil.queryContractByContractId(contractId, page, count);
            }
        }
        return list;
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
                    sleep(1000); //睡眠3秒
                    Message msg = new Message();
                    msg.what = 7;
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
            if (contractBeanList.size() >= allContractBeanList.size()) { // 如果加载完毕，隐藏掉正在加载图标
                lltLoadMore.setVisibility(View.GONE);
                Toast.makeText(BusinessQueryResultActivity.this, getString(R.string.common_not_date), Toast.LENGTH_SHORT).show();
            } else {
                new Thread() {
                    @Override
                    public void run() {
                        super.run();
                        try {
                            sleep(1000); //睡眠1秒
                            Message msg = new Message();
                            msg.what = 17;
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
