package com.sf.bocfinancialsoftware.activity.home.analyse;

import android.content.Intent;
import android.os.Bundle;
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

import com.google.gson.Gson;
import com.sf.bocfinancialsoftware.R;
import com.sf.bocfinancialsoftware.adapter.home.analysis.BocAnalyseAdapter;
import com.sf.bocfinancialsoftware.base.BaseActivity;
import com.sf.bocfinancialsoftware.bean.BocAnalyseBean;
import com.sf.bocfinancialsoftware.http.HttpCallBackListener;
import com.sf.bocfinancialsoftware.http.HttpUtil;
import com.sf.bocfinancialsoftware.util.SwipeRefreshUtil;
import com.sf.bocfinancialsoftware.util.ToastUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.sf.bocfinancialsoftware.constant.ConstantConfig.HAS_NEXT;
import static com.sf.bocfinancialsoftware.constant.ConstantConfig.HAS_NOT_NEXT;
import static com.sf.bocfinancialsoftware.constant.ConstantConfig.NEWS_ID;
import static com.sf.bocfinancialsoftware.constant.ConstantConfig.QUERY_PAGE;
import static com.sf.bocfinancialsoftware.constant.ConstantConfig.REQUEST_FOR_THE_FIRST_TIME;
import static com.sf.bocfinancialsoftware.constant.ConstantConfig.REQUEST_FROM_FILTER;
import static com.sf.bocfinancialsoftware.constant.ConstantConfig.REQUEST_FROM_LOAD_MORE;
import static com.sf.bocfinancialsoftware.constant.ConstantConfig.REQUEST_FROM_REFRESH;
import static com.sf.bocfinancialsoftware.constant.URLConfig.BOC_ANALYSE_LIST_URL;

/**
 * 中银分析列表
 * Created by sn on 2017/6/8.
 */

public class BocAnalyseListActivity extends BaseActivity implements AdapterView.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener, AbsListView.OnScrollListener {

    private ImageView ivTitleBarBack;  //返回按钮
    private TextView tvTitleBarTitle; //标题
    private TextView tvPromptMessage;// 空数据提示消息
    private SwipeRefreshLayout swipeRefreshLayoutBocAnalyse; //下拉刷新上拉加载
    private ListView lvBocAnalyse; //中银分析列表
    private View headView; //列表头部
    private View footView; //列表尾部
    private LinearLayout lltLoadMore; //列表尾部加载更多
    private LinearLayout lltEmptyView; //无数据是显示的视图
    private List<BocAnalyseBean.Content.NewsBean> newsArray; //加载的数据列表
    private BocAnalyseAdapter bocAnalyseAdapter; //列表适配器
    private boolean isLastLine = false;  //列表是否滚动到最后一行
    private String hasNext = "0"; //是否含有下一页，默认为没有有下一页，0：没有，1：有
    private int page = 0; //查询页码,默认从第0页开始查询
    private HashMap<String, String> map; //保存请求参数

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
        tvPromptMessage = (TextView) findViewById(R.id.tvPromptMessage);
        swipeRefreshLayoutBocAnalyse = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayoutBocAnalyse);
        lvBocAnalyse = (ListView) findViewById(R.id.lvBocAnalyse);
        headView = LayoutInflater.from(this).inflate(R.layout.layout_list_head, null);
        footView = LayoutInflater.from(this).inflate(R.layout.layout_list_foot, null);
        lltLoadMore = (LinearLayout) footView.findViewById(R.id.lltLoadMore);
        lltEmptyView = (LinearLayout) findViewById(R.id.lltEmptyView);
    }

    @Override
    protected void initData() {
        ivTitleBarBack.setVisibility(View.VISIBLE);
        tvTitleBarTitle.setText(getString(R.string.common_boc_analyse));
        lvBocAnalyse.addHeaderView(headView);
        lvBocAnalyse.addFooterView(footView);
        tvPromptMessage.setText(getString(R.string.common_sorry_is_loading_now));  //正在加载
        lvBocAnalyse.setEmptyView(lltEmptyView); //处理空ListView
        firstRequest();  //打开页面首次请求网络
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
        BocAnalyseBean.Content.NewsBean bean = newsArray.get(position - 1); //当前点击项
        String newsId = bean.getNewsId(); //获取新闻id,传递给详情页
        Intent intent = new Intent(BocAnalyseListActivity.this, BocAnalyseDetailActivity.class);
        intent.putExtra(NEWS_ID, newsId);
        startActivity(intent);
    }

    /**
     * 首次请求网络
     */
    private void firstRequest() {
        page = 0;
        map = new HashMap<>();
        map.put(QUERY_PAGE, String.valueOf(page)); //查询页码
        String strSuccess = getString(R.string.common_request_success);  //请求成功提示
        String strError = getString(R.string.common_request_failed);  //请求失败提示
        newsArray = new ArrayList<>();
        bocAnalyseAdapter = new BocAnalyseAdapter(BocAnalyseListActivity.this, newsArray);
        lvBocAnalyse.setAdapter(bocAnalyseAdapter);
        getNetworkData(map, strSuccess, strError, REQUEST_FOR_THE_FIRST_TIME);  // 请求网络
    }

    /**
     * 下拉刷新
     */
    @Override
    public void onRefresh() {
        page = 0;
        map.clear();
        map.put(QUERY_PAGE, String.valueOf(page)); //查询页码
        String strSuccess = getString(R.string.common_refresh_success);  //刷新成功提示
        String strError = getString(R.string.common_refresh_failed);  //刷新失败提示
        getNetworkData(map, strSuccess, strError, REQUEST_FROM_REFRESH);  // 请求网络
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
                Toast.makeText(BocAnalyseListActivity.this, getString(R.string.common_not_date), Toast.LENGTH_SHORT).show();
            } else if (hasNext.equals(HAS_NEXT)) {  //还有下一页
                lltLoadMore.setVisibility(View.VISIBLE);
                page++;
                map.clear();
                map.put(QUERY_PAGE, String.valueOf(page)); //查询页码
                String strSuccess = getString(R.string.common_load_success);  //加载成功提示
                String strError = getString(R.string.common_load_failed);  //加载失败提示
                getNetworkData(map, strSuccess, strError, REQUEST_FROM_LOAD_MORE);  // 请求网络
            }
        }
    }

    /**
     * 监听ListView状态，标志是否滚动到最后一行
     *
     * @param view
     * @param firstVisibleItem
     * @param visibleItemCount
     * @param totalItemCount
     */
    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if (firstVisibleItem + visibleItemCount == totalItemCount && totalItemCount > 0) {  //当滚到最后一行
            isLastLine = true; //标志列表状态
        } else {
            isLastLine = false;
        }
    }

    /**
     * 请求网络，获取中银分析列表
     *
     * @param mapObject 存放请求参数
     * @param success   请求成功提示语
     * @param error     请求失败提示语
     * @param flag      请求类型
     */
    public void getNetworkData(final HashMap<String, String> mapObject, final String success, final String error, final String flag) {
        HttpUtil.getNetworksJSonResponse(BocAnalyseListActivity.this, BOC_ANALYSE_LIST_URL, mapObject, new HttpCallBackListener() {
            @Override
            public void onSuccess(String response) {
                Gson gson = new Gson();
                BocAnalyseBean bean = gson.fromJson(response, BocAnalyseBean.class);
                hasNext = bean.getContent().getHasNext();  //是否还有下一页
                if (flag.equals(REQUEST_FROM_REFRESH) || flag.equals(REQUEST_FROM_FILTER)) {  //如果是刷新和筛选请求
                    if (newsArray != null && newsArray.size() > 0) {
                        newsArray.clear(); //则清空原来的列表数据
                    }
                }
                newsArray.addAll(bean.getContent().getNewsArray());  //中银分析数据列表
                bocAnalyseAdapter.notifyDataSetChanged();
                if (flag.equals(REQUEST_FROM_REFRESH)) {  //如果是刷新请求
                    swipeRefreshLayoutBocAnalyse.setRefreshing(false);  //设置刷新圈圈消失
                }
                lltLoadMore.setVisibility(View.GONE); //隐藏正在加载
                ToastUtil.showToast(mContext, success);
            }

            @Override
            public void onFailed(String msg) {
                tvPromptMessage.setText(getString(R.string.common_sorry_not_date));  //暂无数据
                if (flag.equals(REQUEST_FROM_REFRESH)) {  //如果是刷新请求
                    swipeRefreshLayoutBocAnalyse.setRefreshing(false);  //设置刷新圈圈消失
                }
                lltLoadMore.setVisibility(View.GONE); //隐藏正在加载
                ToastUtil.showToast(mContext, error);
            }

            @Override
            public void onError(Exception e) {
                tvPromptMessage.setText(getString(R.string.common_sorry_not_date));  //暂无数据
                if (flag.equals(REQUEST_FROM_REFRESH)) {  //如果是刷新请求
                    swipeRefreshLayoutBocAnalyse.setRefreshing(false);  //设置刷新圈圈消失
                }
                lltLoadMore.setVisibility(View.GONE); //隐藏正在加载
                ToastUtil.showToast(mContext, e.getMessage());
            }
        });
    }

}
