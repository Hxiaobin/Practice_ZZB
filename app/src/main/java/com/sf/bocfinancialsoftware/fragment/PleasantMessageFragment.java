package com.sf.bocfinancialsoftware.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.sf.bocfinancialsoftware.R;
import com.sf.bocfinancialsoftware.adapter.pleasant.PleasantMessageAdapter;
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
import static com.sf.bocfinancialsoftware.constant.ConstantConfig.MSG_TYPE_ID;
import static com.sf.bocfinancialsoftware.constant.ConstantConfig.MSG_TYPE_ID_PLEASANT;
import static com.sf.bocfinancialsoftware.constant.ConstantConfig.QUERY_PAGE;
import static com.sf.bocfinancialsoftware.constant.ConstantConfig.REQUEST_FOR_THE_FIRST_TIME;
import static com.sf.bocfinancialsoftware.constant.ConstantConfig.REQUEST_FROM_FILTER;
import static com.sf.bocfinancialsoftware.constant.ConstantConfig.REQUEST_FROM_LOAD_MORE;
import static com.sf.bocfinancialsoftware.constant.ConstantConfig.REQUEST_FROM_REFRESH;
import static com.sf.bocfinancialsoftware.constant.URLConfig.MESSAGE_LIST_URL;

/**
 * 温馨提示
 * Created by sn on 2017/6/7.
 */

public class PleasantMessageFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, AbsListView.OnScrollListener {

    private View headView; //列表头部
    private View footView; //列表尾部
    private LinearLayout lltLoadMore; //列表尾部加载更多
    private ListView lvPleasantMessage; //温馨提示列表
    private LinearLayout lltEmptyView; // 处理空数据
    private TextView tvPromptMessage;// 空数据提示消息
    private SwipeRefreshLayout swipeRefreshLayoutPleasantMessage;
    private PleasantMessageAdapter adapter; //列表适配器
    private List<MessageBean.Content.MessageObject> msgArray; //已加载的数据列表
    private String typeId;  //消息类型id
    private boolean isLastLine = false;  //列表是否滚动到最后一行
    private String hasNext = HAS_NOT_NEXT; //是否含有下一页，默认为没有有下一页，0：没有，1：有
    private int page = 0;  //查询页码
    private HashMap<String, String> map; // 保存请求参数
    private String strSuccess;  //请求成功提示语
    private String strError;  //请求失败提示语

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
        lltEmptyView = (LinearLayout) view.findViewById(R.id.lltEmptyView);
        tvPromptMessage = (TextView) view.findViewById(R.id.tvPromptMessage);
        swipeRefreshLayoutPleasantMessage = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayoutPleasantMessage);
    }

    private void initData() {
        lvPleasantMessage.addHeaderView(headView);
        lvPleasantMessage.addFooterView(footView);
        lvPleasantMessage.setAdapter(adapter);
        tvPromptMessage.setText(getString(R.string.common_sorry_is_loading_now));  //正在加载
        lvPleasantMessage.setEmptyView(lltEmptyView);
        typeId = MSG_TYPE_ID_PLEASANT;  //消息类型
        firstRequest();  //打开页面首次请求网络
        SwipeRefreshUtil.setRefreshCircle(swipeRefreshLayoutPleasantMessage); //设置刷新样式
    }

    private void initListener() {
        swipeRefreshLayoutPleasantMessage.setOnRefreshListener(this);
        lvPleasantMessage.setOnScrollListener(this);
    }

    /**
     * 打开页面首次获取列表数据
     */
    private void firstRequest() {
        msgArray = new ArrayList<>();
        adapter = new PleasantMessageAdapter(getActivity(), msgArray);
        lvPleasantMessage.setAdapter(adapter);
        page = 0;
        map = new HashMap<>();
        map.put(MSG_TYPE_ID, typeId);  //通知类型
        map.put(QUERY_PAGE, String.valueOf(page)); //查询页码
        strSuccess = getString(R.string.common_request_success); //请求成功提示
        strError = getString(R.string.common_request_failed); //请求失败提示
        getNetworkData(MESSAGE_LIST_URL, map, strSuccess, strError, REQUEST_FOR_THE_FIRST_TIME);  // 请求网络
    }

    /**
     * 下拉刷新
     */
    @Override
    public void onRefresh() {
        strSuccess = getString(R.string.common_refresh_success); //请求成功提示
        strError = getString(R.string.common_refresh_success); //请求失败提示
        if (msgArray != null && msgArray.size() > 0) {
            msgArray.clear(); //清空列表
        }
        page = 0;
        map.clear();
        map.put(MSG_TYPE_ID, typeId);  //通知类型
        map.put(QUERY_PAGE, String.valueOf(page)); //查询页码
        getNetworkData(MESSAGE_LIST_URL, map, strSuccess, strError, REQUEST_FROM_REFRESH);
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
                ToastUtil.showToast(getActivity(), getString(R.string.common_not_date));
            } else if (hasNext.equals(HAS_NEXT)) {  //还有下一页
                lltLoadMore.setVisibility(View.VISIBLE);
                strSuccess = getString(R.string.common_load_success);
                strError = getString(R.string.common_load_failed);
                page++;
                map.clear();
                map.put(MSG_TYPE_ID, typeId);  //通知类型
                map.put(QUERY_PAGE, String.valueOf(page));
                getNetworkData(MESSAGE_LIST_URL, map, strSuccess, strError, REQUEST_FROM_LOAD_MORE);
            }
        }
    }

    /**
     * 监听ListView滚动状态
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
     * 网络请求，获取温馨提示列表数据
     *
     * @param url       请求url
     * @param mapObject 请求参数
     * @param success   请求成功提示语
     * @param error     请求失败提示语
     * @param flag      请求类型
     */
    public void getNetworkData(String url, final HashMap<String, String> mapObject, final String success, final String error, final String flag) {
        HttpUtil.getNetworksJSonResponse(getActivity(), url, mapObject, new HttpCallBackListener() {
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
                msgArray.addAll(bean.getContent().getMsgArray());  //温馨提示数据列表
                adapter.notifyDataSetChanged();
                if (flag.equals(REQUEST_FROM_REFRESH)) {  //如果是刷新请求
                    swipeRefreshLayoutPleasantMessage.setRefreshing(false);  //设置刷新圈圈消失
                }
                lltLoadMore.setVisibility(View.GONE); //隐藏正在加载
                ToastUtil.showToast(getActivity(), success);
            }

            @Override
            public void onFailed(String msg) {
                tvPromptMessage.setText(getString(R.string.common_sorry_not_date));  //暂无数据
                if (flag.equals(REQUEST_FROM_REFRESH)) {  //如果是刷新请求
                    swipeRefreshLayoutPleasantMessage.setRefreshing(false);  //设置刷新圈圈消失
                }
                lltLoadMore.setVisibility(View.GONE); //隐藏正在加载
                ToastUtil.showToast(getActivity(), error);
            }

            @Override
            public void onError(Exception e) {
                tvPromptMessage.setText(getString(R.string.common_sorry_not_date));  //暂无数据
                if (flag.equals(REQUEST_FROM_REFRESH)) {  //如果是刷新请求
                    swipeRefreshLayoutPleasantMessage.setRefreshing(false);  //设置刷新圈圈消失
                }
                lltLoadMore.setVisibility(View.GONE); //隐藏正在加载
                ToastUtil.showToast(getActivity(), e.getMessage());
            }
        });
    }

}
