package com.sf.bocfinancialsoftware.activity.home.message;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.google.gson.Gson;
import com.sf.bocfinancialsoftware.R;
import com.sf.bocfinancialsoftware.adapter.home.message.MessageAdapter;
import com.sf.bocfinancialsoftware.base.BaseActivity;
import com.sf.bocfinancialsoftware.bean.message.MessageBean;
import com.sf.bocfinancialsoftware.http.HttpCallBackListener;
import com.sf.bocfinancialsoftware.http.HttpUtil;
import com.sf.bocfinancialsoftware.util.SwipeRefreshUtil;
import com.sf.bocfinancialsoftware.util.ToastUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.sf.bocfinancialsoftware.constant.ConstantConfig.HAS_NEXT;
import static com.sf.bocfinancialsoftware.constant.ConstantConfig.HAS_NOT_NEXT;
import static com.sf.bocfinancialsoftware.constant.ConstantConfig.IMPORT_RESPONSE;
import static com.sf.bocfinancialsoftware.constant.ConstantConfig.MSG_TYPE_ID;
import static com.sf.bocfinancialsoftware.constant.ConstantConfig.QUERY_ALL;
import static com.sf.bocfinancialsoftware.constant.ConstantConfig.QUERY_FILTER;
import static com.sf.bocfinancialsoftware.constant.ConstantConfig.QUERY_IMPORT_FINANCIAL_BUSINESS;
import static com.sf.bocfinancialsoftware.constant.ConstantConfig.QUERY_IMPORT_INWARD_COLLECTION;
import static com.sf.bocfinancialsoftware.constant.ConstantConfig.QUERY_IMPORT_OPEN;
import static com.sf.bocfinancialsoftware.constant.ConstantConfig.QUERY_IMPORT_ORDER;
import static com.sf.bocfinancialsoftware.constant.ConstantConfig.QUERY_IMPORT_PAY_AT_MATURITY;
import static com.sf.bocfinancialsoftware.constant.ConstantConfig.QUERY_IMPORT_SIGHT_PAYMENT;
import static com.sf.bocfinancialsoftware.constant.ConstantConfig.QUERY_PAGE;
import static com.sf.bocfinancialsoftware.constant.ConstantConfig.REQUEST_FOR_THE_FIRST_TIME;
import static com.sf.bocfinancialsoftware.constant.ConstantConfig.REQUEST_FROM_FILTER;
import static com.sf.bocfinancialsoftware.constant.ConstantConfig.REQUEST_FROM_LOAD_MORE;
import static com.sf.bocfinancialsoftware.constant.ConstantConfig.REQUEST_FROM_REFRESH;
import static com.sf.bocfinancialsoftware.constant.URLConfig.IMPORT_MESSAGE_LIST_URL;
import static com.sf.bocfinancialsoftware.constant.URLConfig.MESSAGE_LIST_URL;

/**
 * 进口通知列表
 * Created by sn on 2017/6/12.
 */

public class ImportMessageListActivity extends BaseActivity implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener, AbsListView.OnScrollListener {

    private ImageView ivTitleBarBack;  //返回
    private ImageView ivTitleBarFilter;  //筛选
    private TextView tvTitleBarTitle;  //标题
    private TextView tvPromptMessage;// 空数据提示消息
    private SwipeRefreshLayout swipeRefreshLayoutMessage; //下拉刷新上拉加载
    private ListView lvMessage;  //进口消息列表
    private View headView; //列表头部
    private View footView; //列表尾部
    private LinearLayout lltLoadMore; //列表尾部加载更多
    private LinearLayout lltEmptyView; //处理空数据
    private MessageAdapter messageAdapter; //列表适配器
    private List<MessageBean.Content.MessageObject> msgArray; //已加载的数据列表
    private Intent intent;
    private String typeId;  //消息类型id
    private PopupWindow mPopWindow;
    private LinearLayout lltFilterCondition0;
    private LinearLayout lltFilterCondition1;
    private LinearLayout lltFilterCondition2;
    private LinearLayout lltFilterCondition3;
    private LinearLayout lltFilterCondition4;
    private LinearLayout lltFilterCondition5;
    private LinearLayout lltFilterCondition6;
    private TextView tvFilterCondition0;
    private TextView tvFilterCondition1;
    private TextView tvFilterCondition2;
    private TextView tvFilterCondition3;
    private TextView tvFilterCondition4;
    private TextView tvFilterCondition5;
    private TextView tvFilterCondition6;
    private boolean isLastLine = false;  //列表是否滚动到最后一行
    private String hasNext = HAS_NOT_NEXT; //是否含有下一页，默认为没有有下一页，0：没有，1：有
    private int page = 0;  //查询页码
    private String filter = ""; //筛选条件
    private HashMap<String, String> map; // 保存请求参数
    private String strSuccess;  //请求成功提示语
    private String strError;  //请求失败提示语

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        initView();
        initData();
        initListener();
    }

    @Override
    protected void initView() {
        ivTitleBarBack = (ImageView) findViewById(R.id.ivTitleBarBack);
        ivTitleBarFilter = (ImageView) findViewById(R.id.ivTitleBarFilter);
        tvTitleBarTitle = (TextView) findViewById(R.id.tvTitleBarTitle);
        tvPromptMessage = (TextView) findViewById(R.id.tvPromptMessage);
        swipeRefreshLayoutMessage = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayoutMessage);
        lvMessage = (ListView) findViewById(R.id.lvMessage);
        headView = LayoutInflater.from(this).inflate(R.layout.layout_list_head, null);
        footView = LayoutInflater.from(this).inflate(R.layout.layout_list_foot, null);
        lltLoadMore = (LinearLayout) footView.findViewById(R.id.lltLoadMore);
        lltEmptyView = (LinearLayout) findViewById(R.id.lltEmptyView);
    }

    @Override
    protected void initData() {
        tvTitleBarTitle.setText(getString(R.string.common_message_reminder_import));
        ivTitleBarBack.setVisibility(View.VISIBLE);
        ivTitleBarFilter.setVisibility(View.VISIBLE);
        intent = getIntent();
        typeId = intent.getStringExtra(MSG_TYPE_ID);  //通知类型
        lvMessage.addHeaderView(headView);  //列表头部
        lvMessage.addFooterView(footView);  //列表尾部
        tvPromptMessage.setText(getString(R.string.common_sorry_is_loading_now));  //正在加载
        lvMessage.setEmptyView(lltEmptyView); //处理空ListView
        firstRequest();  //打开页面首次请求网络
        SwipeRefreshUtil.setRefreshCircle(swipeRefreshLayoutMessage);//刷新圆圈颜色
    }

    @Override
    protected void initListener() {
        ivTitleBarBack.setOnClickListener(this);
        ivTitleBarFilter.setOnClickListener(this);
        swipeRefreshLayoutMessage.setOnRefreshListener(this);
        lvMessage.setOnScrollListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivTitleBarBack:  //返回
                Intent intent = new Intent();
                setResult(IMPORT_RESPONSE, intent);
                finish();
                break;
            case R.id.ivTitleBarFilter: //筛选
                createPopupWindow();
                break;
            case R.id.lltFilterCondition0:  //筛选全部
                filter = QUERY_ALL;
                requestByQueryFilter(filter);
                break;
            case R.id.lltFilterCondition1:  //筛选进口信用证开立通知
                filter = QUERY_IMPORT_OPEN;
                requestByQueryFilter(filter);
                break;
            case R.id.lltFilterCondition2: //筛选进口信用证到单通知
                filter = QUERY_IMPORT_ORDER;
                requestByQueryFilter(filter);
                break;
            case R.id.lltFilterCondition3:  //筛选进口信用证即期付款提示
                filter = QUERY_IMPORT_SIGHT_PAYMENT;
                requestByQueryFilter(filter);
                break;
            case R.id.lltFilterCondition4:  //进口信用证承兑到期付款提示
                filter = QUERY_IMPORT_PAY_AT_MATURITY;
                requestByQueryFilter(filter);
                break;
            case R.id.lltFilterCondition5:  //进口贸易融资业务到期提示
                filter = QUERY_IMPORT_FINANCIAL_BUSINESS;
                requestByQueryFilter(filter);
                break;
            case R.id.lltFilterCondition6:  //进口代收到单通知
                filter = QUERY_IMPORT_INWARD_COLLECTION;
                requestByQueryFilter(filter);
                break;
            default:
                break;
        }
    }

    /**
     * 首次请求网络
     */
    private void firstRequest() {
        msgArray = new ArrayList<>();
        messageAdapter = new MessageAdapter(ImportMessageListActivity.this, msgArray);
        lvMessage.setAdapter(messageAdapter);
        page = 0;
        map = new HashMap<>();
        map.put(MSG_TYPE_ID, typeId);  //通知类型
        map.put(QUERY_PAGE, String.valueOf(page)); //查询页码
        strSuccess = getString(R.string.common_request_success); //请求成功提示
        strError = getString(R.string.common_request_failed); //请求失败提示
        getNetworkData(MESSAGE_LIST_URL, map, strSuccess, strError, REQUEST_FOR_THE_FIRST_TIME);  // 请求网络
    }

    /**
     * PopupWindow根据条件筛选通知
     */
    public void requestByQueryFilter(String strFilter) {
        page = 0;
        map.clear();
        map.put(QUERY_FILTER, strFilter);  //筛选条件
        map.put(QUERY_PAGE, String.valueOf(page)); //查询页码
        strSuccess = getString(R.string.common_request_success);
        strError = getString(R.string.common_request_failed);
        getNetworkData(IMPORT_MESSAGE_LIST_URL, map, strSuccess, strError, REQUEST_FROM_FILTER);
        mPopWindow.dismiss();
        if (hasNext.equals(HAS_NOT_NEXT)) { // 如果没有下一页，隐藏正在加载的提示
            lltLoadMore.setVisibility(View.GONE);
        }
    }

    /**
     * 下拉刷新
     */
    @Override
    public void onRefresh() {
        strSuccess = getString(R.string.common_refresh_success); //请求成功提示
        strError = getString(R.string.common_refresh_success); //请求失败提示
        page = 0;
        if (map.get(QUERY_FILTER) == null) { //没有筛选条件
            map.clear();
            map.put(MSG_TYPE_ID, typeId);  //通知类型
            map.put(QUERY_PAGE, String.valueOf(page)); //查询页码
            getNetworkData(MESSAGE_LIST_URL, map, strSuccess, strError, REQUEST_FROM_REFRESH);
        } else {  //popupWindow过滤筛选
            map.clear();
            map.put(QUERY_FILTER, filter);
            map.put(QUERY_PAGE, String.valueOf(page)); //查询页码
            getNetworkData(IMPORT_MESSAGE_LIST_URL, map, strSuccess, strError, REQUEST_FROM_REFRESH);
        }
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
                ToastUtil.showToast(mContext, getString(R.string.common_not_date));
            } else if (hasNext.equals(HAS_NEXT)) {  //还有下一页
                page++;
                lltLoadMore.setVisibility(View.VISIBLE);
                map.clear();
                strSuccess = getString(R.string.common_load_success);
                strError = getString(R.string.common_load_failed);
                if (map.get(QUERY_FILTER) == null) { //没有筛选条件
                    map.put(MSG_TYPE_ID, typeId);  //通知类型
                    map.put(QUERY_PAGE, String.valueOf(page));
                    getNetworkData(MESSAGE_LIST_URL, map, strSuccess, strError, REQUEST_FROM_LOAD_MORE);
                } else {  //popupWindow过滤筛选
                    map.put(QUERY_FILTER, filter);
                    map.put(QUERY_PAGE, String.valueOf(page));
                    getNetworkData(IMPORT_MESSAGE_LIST_URL, map, strSuccess, strError, REQUEST_FROM_LOAD_MORE);
                }
            }
        }
    }

    /**
     * 监听ListView状态 是否已经是最后一行
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
     * 网络请求，获取通知列表
     *
     * @param url       请求Url
     * @param mapObject 存放请求参数
     * @param success   请求成功提示语
     * @param error     请求失败提示语
     * @param flag      请求类型
     */
    public void getNetworkData(String url, final HashMap<String, String> mapObject, final String success, final String error, final String flag) {
        HttpUtil.getNetworksJSonResponse(ImportMessageListActivity.this, url, mapObject, new HttpCallBackListener() {
            @Override
            public void onSuccess(String response) {
                Gson gson = new Gson();
                MessageBean bean = gson.fromJson(response, MessageBean.class);
                hasNext = bean.getContent().getHasNext();  //是否还有下一页
                if (flag.equals(REQUEST_FROM_REFRESH) || flag.equals(REQUEST_FROM_FILTER)) {  //如果是刷新和筛选请求
                    if (msgArray != null && msgArray.size() > 0) {
                        msgArray.clear(); //则清空原来的列表数据
                    }
                }
                msgArray.addAll(bean.getContent().getMsgArray());  //中银分析数据列表
                messageAdapter.notifyDataSetChanged();
                if (flag.equals(REQUEST_FROM_REFRESH)) {  //如果是刷新请求
                    swipeRefreshLayoutMessage.setRefreshing(false);  //设置刷新圈圈消失
                }
                lltLoadMore.setVisibility(View.GONE); //隐藏正在加载
                ToastUtil.showToast(mContext, success);
            }

            @Override
            public void onFailed(String msg) {
                tvPromptMessage.setText(getString(R.string.common_sorry_not_date));  //暂无数据
                if (flag.equals(REQUEST_FROM_REFRESH)) {  //如果是刷新请求
                    swipeRefreshLayoutMessage.setRefreshing(false);  //设置刷新圈圈消失
                }
                lltLoadMore.setVisibility(View.GONE); //隐藏正在加载
                ToastUtil.showToast(mContext, error);
            }

            @Override
            public void onError(Exception e) {
                tvPromptMessage.setText(getString(R.string.common_sorry_not_date));  //暂无数据
                if (flag.equals(REQUEST_FROM_REFRESH)) {  //如果是刷新请求
                    swipeRefreshLayoutMessage.setRefreshing(false);  //设置刷新圈圈消失
                }
                lltLoadMore.setVisibility(View.GONE); //隐藏正在加载
                ToastUtil.showToast(mContext, e.getMessage());
            }
        });
    }

    /**
     * 创建PopupWindow
     */
    private void createPopupWindow() {
        View contentView = LayoutInflater.from(ImportMessageListActivity.this).inflate(R.layout.layout_message_popupwindow, null);
        mPopWindow = new PopupWindow(contentView);
        // 设置宽和高
        mPopWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        mPopWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        // 3.获取视图里面的控件进行操作
        lltFilterCondition0 = (LinearLayout) contentView.findViewById(R.id.lltFilterCondition0);
        lltFilterCondition1 = (LinearLayout) contentView.findViewById(R.id.lltFilterCondition1);
        lltFilterCondition2 = (LinearLayout) contentView.findViewById(R.id.lltFilterCondition2);
        lltFilterCondition3 = (LinearLayout) contentView.findViewById(R.id.lltFilterCondition3);
        lltFilterCondition4 = (LinearLayout) contentView.findViewById(R.id.lltFilterCondition4);
        lltFilterCondition5 = (LinearLayout) contentView.findViewById(R.id.lltFilterCondition5);
        lltFilterCondition6 = (LinearLayout) contentView.findViewById(R.id.lltFilterCondition6);
        tvFilterCondition0 = (TextView) contentView.findViewById(R.id.tvFilterCondition0);
        tvFilterCondition1 = (TextView) contentView.findViewById(R.id.tvFilterCondition1);
        tvFilterCondition2 = (TextView) contentView.findViewById(R.id.tvFilterCondition2);
        tvFilterCondition3 = (TextView) contentView.findViewById(R.id.tvFilterCondition3);
        tvFilterCondition4 = (TextView) contentView.findViewById(R.id.tvFilterCondition4);
        tvFilterCondition5 = (TextView) contentView.findViewById(R.id.tvFilterCondition5);
        tvFilterCondition6 = (TextView) contentView.findViewById(R.id.tvFilterCondition6);
        //设置筛选条件
        tvFilterCondition0.setText(getString(R.string.activity_message_filter_condition0));
        tvFilterCondition1.setText(getString(R.string.activity_message_import_filter_condition1));
        tvFilterCondition2.setText(getString(R.string.activity_message_import_filter_condition2));
        tvFilterCondition3.setText(getString(R.string.activity_message_import_filter_condition3));
        tvFilterCondition4.setText(getString(R.string.activity_message_import_filter_condition4));
        tvFilterCondition5.setText(getString(R.string.activity_message_import_filter_condition5));
        tvFilterCondition6.setText(getString(R.string.activity_message_import_filter_condition6));
        //监听事件
        lltFilterCondition0.setOnClickListener(this);
        lltFilterCondition1.setOnClickListener(this);
        lltFilterCondition2.setOnClickListener(this);
        lltFilterCondition3.setOnClickListener(this);
        lltFilterCondition4.setOnClickListener(this);
        lltFilterCondition5.setOnClickListener(this);
        lltFilterCondition6.setOnClickListener(this);
        // 设置是否可以触摸PopupWindow外部的区域
        mPopWindow.setOutsideTouchable(true);
        mPopWindow.setTouchable(true);
        //通常来将，PopupWindow里面的控件如果是button，TextView ，ListView，默认是不会获取焦点， 需要设置mPopWindow.setFocusable(true)来提供给控件获取焦点
        mPopWindow.setFocusable(true);
        // 设置背景图片
        mPopWindow.setBackgroundDrawable(new ColorDrawable(0));
        // anchor:是能够触发显示PopupWindow的试图
        mPopWindow.showAsDropDown(ivTitleBarFilter);
    }

}
