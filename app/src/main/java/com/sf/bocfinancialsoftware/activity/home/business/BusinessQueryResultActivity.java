package com.sf.bocfinancialsoftware.activity.home.business;

import android.content.Intent;
import android.os.Bundle;
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

import com.google.gson.Gson;
import com.sf.bocfinancialsoftware.R;
import com.sf.bocfinancialsoftware.adapter.BusinessAdapter;
import com.sf.bocfinancialsoftware.base.BaseActivity;
import com.sf.bocfinancialsoftware.bean.ContractBean;
import com.sf.bocfinancialsoftware.http.HttpCallBackListener;
import com.sf.bocfinancialsoftware.http.HttpUtil;
import com.sf.bocfinancialsoftware.util.SwipeRefreshUtil;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import static com.sf.bocfinancialsoftware.constant.ConstantConfig.BUSINESS_ID;
import static com.sf.bocfinancialsoftware.constant.ConstantConfig.CONTRACT_ID;
import static com.sf.bocfinancialsoftware.constant.ConstantConfig.END_DATE;
import static com.sf.bocfinancialsoftware.constant.ConstantConfig.HAS_NEXT;
import static com.sf.bocfinancialsoftware.constant.ConstantConfig.HAS_NOT_NEXT;
import static com.sf.bocfinancialsoftware.constant.ConstantConfig.QUERY_PAGE;
import static com.sf.bocfinancialsoftware.constant.ConstantConfig.REQUEST_FOR_THE_FIRST_TIME;
import static com.sf.bocfinancialsoftware.constant.ConstantConfig.REQUEST_FROM_FILTER;
import static com.sf.bocfinancialsoftware.constant.ConstantConfig.REQUEST_FROM_LOAD_MORE;
import static com.sf.bocfinancialsoftware.constant.ConstantConfig.REQUEST_FROM_REFRESH;
import static com.sf.bocfinancialsoftware.constant.ConstantConfig.START_DATE;
import static com.sf.bocfinancialsoftware.constant.URLConfig.CONTRACT_LIST_BY_ALL_URL;
import static com.sf.bocfinancialsoftware.constant.URLConfig.CONTRACT_LIST_BY_BUSINESS_ID_AND_CONTRACT_ID_URL;
import static com.sf.bocfinancialsoftware.constant.URLConfig.CONTRACT_LIST_BY_BUSINESS_ID_AND_DATE_URL;
import static com.sf.bocfinancialsoftware.constant.URLConfig.CONTRACT_LIST_BY_CONTRACT_ID_URL;

/**
 * 业务查询结果
 * Created by sn on 2017/6/15.
 */

public class BusinessQueryResultActivity extends BaseActivity implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener, AbsListView.OnScrollListener {

    private ImageView ivTitleBarBack;  //返回
    private ImageView ivTitleBarScan;  //扫一扫
    private TextView tvTitleBarTitle;  //标题
    private TextView tvPromptMessage;// 空数据提示消息
    private SwipeRefreshLayout swipeRefreshLayoutBusinessQueryResult; //下拉刷新上拉加载
    private ListView lvBusinessQueryResult; // 合同列表
    private View headView; //列表头部
    private View footView; //列表尾部
    private LinearLayout lltLoadMore; //列表尾部加载更多
    private LinearLayout lltEmptyView; //无数据是显示的视图
    private List<ContractBean.Content.Contract> contractArray; // 合同列表
    private BusinessAdapter adapter; //适配器
    private Intent intent;
    private String businessId; //业务类型id
    private String startDate; //开始时间
    private String endDate; // 结束时间
    private String contractId; //业务编号
    private boolean isLastLine = false;  //列表是否滚动到最后一行
    private String hasNext = "0"; //是否含有下一页，默认为没有有下一页，0：没有，1：有
    private int page = 0; //查询页码,默认从第0页开始查询
    private HashMap<String, String> map; //保存请求参数
    private String strSuccess;
    private String strError;
    private long start;
    private long end;

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
        tvPromptMessage = (TextView) findViewById(R.id.tvPromptMessage);
        swipeRefreshLayoutBusinessQueryResult = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayoutBusinessQueryResult);
        lvBusinessQueryResult = (ListView) findViewById(R.id.lvBusinessQueryResult);
        lltEmptyView = (LinearLayout) findViewById(R.id.lltEmptyView);
        headView = LayoutInflater.from(this).inflate(R.layout.layout_list_head, null);
        footView = LayoutInflater.from(this).inflate(R.layout.layout_list_foot, null);
        lltLoadMore = (LinearLayout) footView.findViewById(R.id.lltLoadMore);
    }

    @Override
    protected void initData() {
        tvTitleBarTitle.setText(getString(R.string.activity_business_query_result_title));
        ivTitleBarBack.setVisibility(View.VISIBLE);
        ivTitleBarScan.setVisibility(View.GONE);
        intent = getIntent();   //接收由上一个页面传递过来的请求参数
        businessId = intent.getStringExtra(BUSINESS_ID);  //业务id
        startDate = intent.getStringExtra(START_DATE);   //开始时间
        endDate = intent.getStringExtra(END_DATE);   //结束时间
        contractId = intent.getStringExtra(CONTRACT_ID);  //合同编号
        start = transformIntoTime(startDate);
        end = transformIntoTime(endDate);
        tvPromptMessage.setText(getString(R.string.common_sorry_is_loading_now));  //正在加载
        lvBusinessQueryResult.setEmptyView(lltEmptyView);
        lvBusinessQueryResult.addHeaderView(headView);
        lvBusinessQueryResult.addFooterView(footView);
        contractArray = new ArrayList<>();
        adapter = new BusinessAdapter(BusinessQueryResultActivity.this, contractArray);
        lvBusinessQueryResult.setAdapter(adapter);
        page = 0;
        map = new HashMap<>();
        strSuccess = getString(R.string.common_request_success); //请求成功提示
        strError = getString(R.string.common_request_failed); //请求失败提示
        RequestByJudgementCondition(businessId, startDate, endDate, contractId, page, strSuccess, strError, REQUEST_FOR_THE_FIRST_TIME);  //根据请求天剑来请求网络
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
     * 下拉刷新
     */
    @Override
    public void onRefresh() {
        page = 0;
        map.clear();
        strSuccess = getString(R.string.common_refresh_success); //刷新成功提示
        strError = getString(R.string.common_refresh_failed); //刷新失败提示
        RequestByJudgementCondition(businessId, startDate, endDate, contractId, page, strSuccess, strError, REQUEST_FROM_REFRESH);
    }

    /**
     * 上拉加载
     *
     * @param view
     * @param scrollState
     */
    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (scrollState == SCROLL_STATE_IDLE && isLastLine) { //停止滚动，且滚动到最后一行
            if (hasNext.equals(HAS_NOT_NEXT)) { // 如果没有下一页
                lltLoadMore.setVisibility(View.GONE);
                Toast.makeText(BusinessQueryResultActivity.this, getString(R.string.common_not_date), Toast.LENGTH_SHORT).show();
            } else if (hasNext.equals(HAS_NEXT)) {  //还有下一页
                lltLoadMore.setVisibility(View.VISIBLE);
                page++;
                map.clear();
                strSuccess = getString(R.string.common_load_success);  //加载成功提示
                strError = getString(R.string.common_load_failed);  //加载失败提示
                RequestByJudgementCondition(businessId, startDate, endDate, contractId, page, strSuccess, strError, REQUEST_FROM_LOAD_MORE);
            }
        }
    }

    /**
     * 记录ListView的滚动过程的状态
     *
     * @param view
     * @param firstVisibleItem
     * @param visibleItemCount
     * @param totalItemCount
     */
    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if (firstVisibleItem + visibleItemCount == totalItemCount && totalItemCount > 0) {  //当滚到最后一行
            isLastLine = true;
        } else {
            isLastLine = false;
        }
    }

    /**
     * 根据请求条件来查询
     *
     * @param businessId 业务ID
     * @param startDate  开始时间
     * @param endDate    结束时间
     * @param contractId 合同编号
     * @param page       页码
     * @param success    请求成功提示
     * @param error      请求失败提示
     * @param flag       请求类型
     */
    private void RequestByJudgementCondition(String businessId, String startDate, String endDate, String contractId, int page, String success, String error, String flag) {
        if (!TextUtils.isEmpty(businessId)) { //业务id不为空，
            map.put(BUSINESS_ID, businessId);
            if (!TextUtils.isEmpty(startDate) && !TextUtils.isEmpty(endDate) && TextUtils.isEmpty(contractId)) { //通过开始时间+结束时间查询
                map.put(START_DATE, String.valueOf(start));
                map.put(END_DATE, String.valueOf(end));
                getNetworkData(CONTRACT_LIST_BY_BUSINESS_ID_AND_DATE_URL, map, success, error, flag);  // 请求网络
            } else if (!TextUtils.isEmpty(contractId) && (TextUtils.isEmpty(startDate) || TextUtils.isEmpty(endDate))) {//通过合同编号查询
                map.put(CONTRACT_ID, contractId);
                getNetworkData(CONTRACT_LIST_BY_BUSINESS_ID_AND_CONTRACT_ID_URL, map, success, error, flag);  // 请求网络
            } else if (!TextUtils.isEmpty(startDate) && !TextUtils.isEmpty(endDate) && !TextUtils.isEmpty(contractId)) { //通过三者查询
                map.put(START_DATE, String.valueOf(start));
                map.put(END_DATE, String.valueOf(end));
                map.put(CONTRACT_ID, contractId);
                getNetworkData(CONTRACT_LIST_BY_ALL_URL, map, success, error, flag);  // 请求网络
            }
            map.put(QUERY_PAGE, String.valueOf(page));
        } else { //业务id为空,只根据合同id查询
            if (!TextUtils.isEmpty(contractId)) {
                map.put(CONTRACT_ID, contractId);
                map.put(QUERY_PAGE, String.valueOf(page));
                getNetworkData(CONTRACT_LIST_BY_CONTRACT_ID_URL, map, success, error, flag);  // 请求网络
            }
        }
    }

    /**
     * 判断请求条件
     *
     * @param businessId 业务ID
     * @param startDate  开始时间
     * @param endDate    结束时间
     * @param contractId 合同编号
     */
    private void judgementRequestCondition(String businessId, String startDate, String endDate, String contractId) {
        if (!TextUtils.isEmpty(businessId)) { //业务id不为空，
            if (!TextUtils.isEmpty(startDate) && !TextUtils.isEmpty(endDate) && TextUtils.isEmpty(contractId)) { //通过开始时间+结束时间查询
                Toast.makeText(BusinessQueryResultActivity.this, getString(R.string.activity_business_query_no_date_for_this_time), Toast.LENGTH_SHORT).show();
            } else if (!TextUtils.isEmpty(contractId) && (TextUtils.isEmpty(startDate) || TextUtils.isEmpty(endDate))) {//通过合同编号查询
                Toast.makeText(BusinessQueryResultActivity.this, getString(R.string.activity_business_query_no_date_for_this_contractId), Toast.LENGTH_SHORT).show();
            } else if (!TextUtils.isEmpty(startDate) && !TextUtils.isEmpty(endDate) && !TextUtils.isEmpty(contractId)) { //通过三者查询
                Toast.makeText(BusinessQueryResultActivity.this, getString(R.string.activity_business_query_no_date_for_this_time_and_contract_id), Toast.LENGTH_SHORT).show();
            }
        } else { //业务id为空,只根据合同id查询
            if (!TextUtils.isEmpty(contractId)) {
                Toast.makeText(BusinessQueryResultActivity.this, getString(R.string.activity_business_query_no_date_for_this_contractId), Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * 获取网络数据
     *
     * @return
     */
    private void getNetworkData(String url, final HashMap<String, String> mapObject, final String success, final String error, final String flag) {
        HttpUtil.getNetworksJSonResponse(BusinessQueryResultActivity.this, url, mapObject, new HttpCallBackListener() {
            @Override
            public void onSuccess(String response) {
                Gson gson = new Gson();
                ContractBean bean = gson.fromJson(response, ContractBean.class);
                hasNext = bean.getContent().getHasNext();  //是否还有下一页
                if (flag.equals(REQUEST_FROM_REFRESH) || flag.equals(REQUEST_FROM_FILTER)) {  //如果是刷新和筛选请求
                    if (contractArray != null && contractArray.size() > 0) {
                        contractArray.clear(); //则清空原来的列表数据
                    }
                }
                contractArray.addAll(bean.getContent().getContractArray());  //合同列表列表
                adapter.notifyDataSetChanged();
                if (flag.equals(REQUEST_FROM_REFRESH)) {  //如果是刷新请求
                    swipeRefreshLayoutBusinessQueryResult.setRefreshing(false);  //设置刷新圈圈消失
                }
                if (contractArray == null || contractArray.size() <= 0) {  //合同列表为空
                    judgementRequestCondition(businessId, startDate, endDate, contractId);
                } else {
                    Toast.makeText(BusinessQueryResultActivity.this, success, Toast.LENGTH_SHORT).show();
                }
                lltLoadMore.setVisibility(View.GONE); //隐藏正在加载
            }

            @Override
            public void onFailed(String msg) {
                tvPromptMessage.setText(getString(R.string.common_sorry_not_date));
                if (flag.equals(REQUEST_FROM_REFRESH)) {  //如果是刷新请求
                    swipeRefreshLayoutBusinessQueryResult.setRefreshing(false);  //设置刷新圈圈消失
                }
                lltLoadMore.setVisibility(View.GONE);
                Toast.makeText(BusinessQueryResultActivity.this, error, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Exception e) {
                tvPromptMessage.setText(getString(R.string.common_sorry_not_date));
                if (flag.equals(REQUEST_FROM_REFRESH)) {  //如果是刷新请求
                    swipeRefreshLayoutBusinessQueryResult.setRefreshing(false);  //设置刷新圈圈消失
                }
                lltLoadMore.setVisibility(View.GONE); //隐藏正在加载
                Toast.makeText(BusinessQueryResultActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 将字符串转化为时间
     *
     * @param strTime
     * @return
     */
    private long transformIntoTime(String strTime) {
        long time = 0;
        if (!TextUtils.isEmpty(strTime)) {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date data = dateFormat.parse(strTime);
                time = data.getTime();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return time;
    }

}
