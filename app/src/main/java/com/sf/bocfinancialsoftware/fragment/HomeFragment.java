package com.sf.bocfinancialsoftware.fragment;

import android.content.Intent;
import android.os.Bundle;
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

import com.google.gson.Gson;
import com.jude.rollviewpager.RollPagerView;
import com.sf.bocfinancialsoftware.R;
import com.sf.bocfinancialsoftware.activity.home.analyse.BocAnalyseDetailActivity;
import com.sf.bocfinancialsoftware.activity.home.analyse.BocAnalyseListActivity;
import com.sf.bocfinancialsoftware.activity.home.business.BusinessQueryActivity;
import com.sf.bocfinancialsoftware.activity.home.message.MessageReminderActivity;
import com.sf.bocfinancialsoftware.adapter.home.analysis.HomeFragmentBocAnalyseAdapter;
import com.sf.bocfinancialsoftware.adapter.home.ImageAdapter;
import com.sf.bocfinancialsoftware.bean.AdvertLoopImageBean;
import com.sf.bocfinancialsoftware.bean.BocAnalyseBean;
import com.sf.bocfinancialsoftware.bean.UnReadMsgBean;
import com.sf.bocfinancialsoftware.http.HttpCallBackListener;
import com.sf.bocfinancialsoftware.http.HttpUtil;
import com.sf.bocfinancialsoftware.util.SwipeRefreshUtil;
import com.sf.bocfinancialsoftware.widget.SwipeRefreshLayoutHome;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.sf.bocfinancialsoftware.constant.ConstantConfig.ADVERT_LOOP_IMAGE_TYPE;
import static com.sf.bocfinancialsoftware.constant.ConstantConfig.ADVERT_LOOP_IMAGE_TYPE_HOME;
import static com.sf.bocfinancialsoftware.constant.ConstantConfig.HAS_NEXT;
import static com.sf.bocfinancialsoftware.constant.ConstantConfig.HAS_NOT_NEXT;
import static com.sf.bocfinancialsoftware.constant.ConstantConfig.MSG_TOTAL_READ_SUM;
import static com.sf.bocfinancialsoftware.constant.ConstantConfig.MSG_TOTAL_UN_REN_SUM_REQUEST;
import static com.sf.bocfinancialsoftware.constant.ConstantConfig.MSG_TOTAL_UN_REN_SUM_RESPONSE;
import static com.sf.bocfinancialsoftware.constant.ConstantConfig.NEWS_ID;
import static com.sf.bocfinancialsoftware.constant.ConstantConfig.QUERY_PAGE;
import static com.sf.bocfinancialsoftware.constant.ConstantConfig.REQUEST_FOR_THE_FIRST_TIME;
import static com.sf.bocfinancialsoftware.constant.ConstantConfig.REQUEST_FROM_FILTER;
import static com.sf.bocfinancialsoftware.constant.ConstantConfig.REQUEST_FROM_LOAD_MORE;
import static com.sf.bocfinancialsoftware.constant.ConstantConfig.REQUEST_FROM_REFRESH;
import static com.sf.bocfinancialsoftware.constant.URLConfig.ADVERT_LOOP_IMAGE_URL;
import static com.sf.bocfinancialsoftware.constant.URLConfig.BOC_ANALYSE_LIST_URL;
import static com.sf.bocfinancialsoftware.constant.URLConfig.MESSAGE_UN_READ_URL;

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
    private TextView tvPromptMessage;// 空数据提示消息
    private SwipeRefreshLayoutHome swipeRefreshLayoutHomeFragmentBocAnalyse;  //下拉刷新控件
    private ListView lvHomeFragmentBocAnalyse; //中银分析新闻列表
    private LinearLayout lltEmptyView; //处理空数据
    private List<BocAnalyseBean.Content.NewsBean> newsArray; //中银分析列表
    private HomeFragmentBocAnalyseAdapter bocAnalyseAdapter; //中银分析列表适配器
    private ImageAdapter imageAdapter;  //图片轮播适配器
    private List<String> imageUrlList; //图片Url集合
    private List<AdvertLoopImageBean.AdvertImage> content; //轮播图片对象集合
    private boolean isLastLine = false;  //列表是否滚动到最后一行
    private String hasNext = "0"; //是否含有下一页，默认为没有有下一页，0：没有，1：有
    private int page = 0; //查询页码
    private HashMap<String, String> map; //保存请求参数
    private UnReadMsgBean.Content unReadMsgContent;

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
        tvPromptMessage = (TextView) view.findViewById(R.id.tvPromptMessage);
        swipeRefreshLayoutHomeFragmentBocAnalyse = (SwipeRefreshLayoutHome) view.findViewById(R.id.swipeRefreshLayoutHomeFragmentBocAnalyse);
        lvHomeFragmentBocAnalyse = (ListView) view.findViewById(R.id.lvHomeFragmentBocAnalyse);
        lltEmptyView = (LinearLayout) view.findViewById(R.id.lltEmptyView);
    }

    private void initData() {
        HashMap<String, String> map = new HashMap<>();
        getNetworkData(MESSAGE_UN_READ_URL, map);  // 请求网络获取各类通知未读消息数量
        lvHomeFragmentBocAnalyse.addHeaderView(headView);
        lvHomeFragmentBocAnalyse.addFooterView(footView);
        lltEmptyView.setVisibility(View.VISIBLE);
        tvPromptMessage.setText(getString(R.string.common_sorry_is_loading_now));  //正在加载
        lvHomeFragmentBocAnalyse.setEmptyView(lltEmptyView); //处理空ListView
        getAdvertLoopImage(); //获取轮播
        firstRequest();  //打开页面首次请求列表数据
        SwipeRefreshUtil.setRefreshCircle(swipeRefreshLayoutHomeFragmentBocAnalyse); //设置刷新样式
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
        BocAnalyseBean.Content.NewsBean bean = newsArray.get(position - 1); //当前点击项
        String newsId = bean.getNewsId(); //获取新闻id,传递给详情页
        Intent intent = new Intent(getActivity(), BocAnalyseDetailActivity.class);
        intent.putExtra(NEWS_ID, newsId);
        startActivity(intent);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == MSG_TOTAL_UN_REN_SUM_REQUEST && resultCode == MSG_TOTAL_UN_REN_SUM_RESPONSE) { //通知总未读数量
            int totalUnReadSum = data.getIntExtra(MSG_TOTAL_READ_SUM, 0); //剩余总未读数量
            showMsgUnReadSum(totalUnReadSum, tvMsgTotalReadSum);
        }
    }

    /**
     * 获取轮播图
     */
    private void getAdvertLoopImage() {
        content = new ArrayList<>();
        imageUrlList = new ArrayList<>();
        imageAdapter = new ImageAdapter(getActivity(), imageUrlList);
        rollPagerViewHome.setAdapter(imageAdapter);
        HashMap<String, String> imageMap = new HashMap<>();
        imageMap.put(ADVERT_LOOP_IMAGE_TYPE, ADVERT_LOOP_IMAGE_TYPE_HOME); //轮播图类型：首页
        getNetworkImage(imageMap);
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
        bocAnalyseAdapter = new HomeFragmentBocAnalyseAdapter(getActivity(), newsArray);
        lvHomeFragmentBocAnalyse.setAdapter(bocAnalyseAdapter);
        getNetworkListData(map, strSuccess, strError, REQUEST_FOR_THE_FIRST_TIME);  // 请求网络
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
        getNetworkListData(map, strSuccess, strError, REQUEST_FROM_REFRESH);  // 请求网络
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
                Toast.makeText(getActivity(), getString(R.string.common_not_date), Toast.LENGTH_SHORT).show();
            } else if (hasNext.equals(HAS_NEXT)) {  //还有下一页
                lltLoadMore.setVisibility(View.VISIBLE);
                page++;
                map.clear();
                map.put(QUERY_PAGE, String.valueOf(page)); //查询页码
                String strSuccess = getString(R.string.common_load_success);  //加载成功提示
                String strError = getString(R.string.common_load_failed);  //加载失败提示
                getNetworkListData(map, strSuccess, strError, REQUEST_FROM_LOAD_MORE);  // 请求网络
            }
        }
    }

    /**
     * 监听ListView状态
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
     * 获取网络轮播图
     *
     * @param mapObject 存放请求参数
     */
    public void getNetworkImage(final HashMap<String, String> mapObject) {
        HttpUtil.getNetworksJSonResponse(getActivity(), ADVERT_LOOP_IMAGE_URL, mapObject, new HttpCallBackListener() {
            @Override
            public void onSuccess(String response) {
                Gson gson = new Gson();
                AdvertLoopImageBean bean = gson.fromJson(response, AdvertLoopImageBean.class);
                content.addAll(bean.getContent());
                for (int i = 0; i < content.size(); i++) {
                    imageUrlList.add(content.get(i).getImgUrl());  //图片url集合
                }
                imageAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailed(String msg) {
            }

            @Override
            public void onError(Exception e) {

            }
        });
    }

    /**
     * 获取中银分析列表数据
     *
     * @param mapObject 存放请求参数
     * @param success   请求成功提示语
     * @param error     请求失败提示语
     * @param flag      请求类型
     */
    public void getNetworkListData(final HashMap<String, String> mapObject, final String success, final String error, final String flag) {
        HttpUtil.getNetworksJSonResponse(getActivity(), BOC_ANALYSE_LIST_URL, mapObject, new HttpCallBackListener() {
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
                newsArray.addAll(bean.getContent().getNewsArray());
                bocAnalyseAdapter.notifyDataSetChanged();
                if (flag.equals(REQUEST_FROM_REFRESH)) {  //如果是刷新请求
                    swipeRefreshLayoutHomeFragmentBocAnalyse.setRefreshing(false);  //设置刷新圈圈消失
                }
                lltLoadMore.setVisibility(View.GONE); //隐藏正在加载
                Toast.makeText(getActivity(), success, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailed(String msg) {
                tvPromptMessage.setText(getString(R.string.common_sorry_not_date));  //暂无数据
                if (flag.equals(REQUEST_FROM_REFRESH)) {  //如果是刷新请求
                    swipeRefreshLayoutHomeFragmentBocAnalyse.setRefreshing(false);  //设置刷新圈圈消失
                }
                lltLoadMore.setVisibility(View.GONE); //隐藏正在加载
                Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onError(Exception e) {
                tvPromptMessage.setText(getString(R.string.common_sorry_not_date));  //暂无数据
                if (flag.equals(REQUEST_FROM_REFRESH)) {  //如果是刷新请求
                    swipeRefreshLayoutHomeFragmentBocAnalyse.setRefreshing(false);  //设置刷新圈圈消失
                }
                lltLoadMore.setVisibility(View.GONE); //隐藏正在加载
                Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 获取未读消息数量
     *
     * @return
     */
    public void getNetworkData(String url, final HashMap<String, String> mapObject) {
        HttpUtil.getNetworksJSonResponse(getActivity(), url, mapObject, new HttpCallBackListener() {

            @Override
            public void onSuccess(String response) {
                Gson gson = new Gson();
                UnReadMsgBean bean = gson.fromJson(response, UnReadMsgBean.class);
                unReadMsgContent = bean.getContent();
                int totalUnReadSum = Integer.valueOf(unReadMsgContent.getSum());
                showMsgUnReadSum(totalUnReadSum, tvMsgTotalReadSum);
            }

            @Override
            public void onFailed(String msg) {
            }

            @Override
            public void onError(Exception e) {

            }

        });
    }

    /**
     * 处理未读消息的显示问题
     *
     * @param unReadSum 未读消息数量
     * @param tv        显示控件TextView
     */
    private void showMsgUnReadSum(int unReadSum, TextView tv) {
        if (unReadSum <= 0) {
            tv.setVisibility(View.GONE);
        } else {
            tv.setText(String.valueOf(unReadSum));
        }
    }
}
