package com.sf.bocfinancialsoftware.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.sf.bocfinancialsoftware.R;
import com.sf.bocfinancialsoftware.activity.home.analyse.BocAnalyseDetailActivity;
import com.sf.bocfinancialsoftware.activity.home.analyse.BocAnalyseListActivity;
import com.sf.bocfinancialsoftware.activity.home.business.BusinessQueryActivity;
import com.sf.bocfinancialsoftware.activity.home.message.MessageReminderActivity;
import com.sf.bocfinancialsoftware.adapter.ImageCarouselAdapter;
import com.sf.bocfinancialsoftware.adapter.HomeFragmentBocAnalyseAdapter;
import com.sf.bocfinancialsoftware.bean.BocAnalyseBean;
import com.sf.bocfinancialsoftware.util.DataBaseSQLiteUtil;
import com.sf.bocfinancialsoftware.util.SwipeRefreshUtil;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import static com.sf.bocfinancialsoftware.constant.ConstantConfig.NEWS_ID;

/**
 * 首页
 * Created by sn on 2017/6/7.
 */

public class HomeFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemClickListener, ViewPager.OnPageChangeListener, AbsListView.OnScrollListener, SwipeRefreshLayout.OnRefreshListener {

    private View headView; //列表头部
    private View footView; //列表尾部
    private LinearLayout lltLoadMore; //列表尾部加载更多
    private ViewPager vpAdvertisingCarousel; //广告轮播
    private Button btnMessageReminder;  //通知提醒
    private Button btnBusinessQuery;  //业务查询
    private Button btnBOCAnalyse;  //中银分析
    private SwipeRefreshLayout swipeRefreshLayoutHomeFragmentBocAnalyse;
    private ListView lvHomeFragmentBocAnalyse; //中银分析新闻列表
    private LinearLayout lltEmptyViewBocAnalyseHome; //处理空数据
    private AdvertisingCarouselHandler handler = new AdvertisingCarouselHandler(new WeakReference<Fragment>(this)); //处理广告轮播的线程
    private List<BocAnalyseBean> bocAnalyseBeanList;  //中银分析Bean类集合
    private List<BocAnalyseBean> allBocAnalyseBeanList; //所有的数据列表
    private HomeFragmentBocAnalyseAdapter bocAnalyseAdapter; //中银分析列表适配器
    private boolean isLastLine = false;  //列表是否滚动到最后一行
    private int page = 0;
    private Handler mHandler = new Handler() {  //主线程中的Handler对象
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 8) {
                //下拉刷新，三秒睡眠之后   重新加载
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
        initData(inflater);      // 初始化数据
        initListener();  //初始化监听事件
        handler.sendEmptyMessageDelayed(AdvertisingCarouselHandler.MSG_UPDATE_IMAGE, AdvertisingCarouselHandler.MSG_DELAY);//开始轮播效果
        return view;
    }

    private void initView(View view, LayoutInflater inflater) {
        headView = inflater.inflate(R.layout.layout_home_head_view, null);
        footView = inflater.inflate(R.layout.layout_list_foot, null);
        lltLoadMore = (LinearLayout) footView.findViewById(R.id.lltLoadMore);
        vpAdvertisingCarousel = (ViewPager) headView.findViewById(R.id.vpAdvertisingCarousel);
        btnMessageReminder = (Button) headView.findViewById(R.id.btnMessageReminder);
        btnBusinessQuery = (Button) headView.findViewById(R.id.btnBusinessQuery);
        btnBOCAnalyse = (Button) headView.findViewById(R.id.btnBOCAnalyse);
        swipeRefreshLayoutHomeFragmentBocAnalyse = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayoutHomeFragmentBocAnalyse);
        lvHomeFragmentBocAnalyse = (ListView) view.findViewById(R.id.lvHomeFragmentBocAnalyse);
        lltEmptyViewBocAnalyseHome = (LinearLayout) view.findViewById(R.id.lltEmptyViewBocAnalyseHome);
    }

    private void initData(LayoutInflater inflater) {
        getBocAnalyseList(); //获取页面列表数据
        AdvertisingCarousel(inflater);// 广告图片轮播
        lvHomeFragmentBocAnalyse.setEmptyView(lltEmptyViewBocAnalyseHome);
        SwipeRefreshUtil.setRefreshCircle(swipeRefreshLayoutHomeFragmentBocAnalyse); //设置刷新样式
    }

    private void initListener() {
        btnMessageReminder.setOnClickListener(this);
        btnBusinessQuery.setOnClickListener(this);
        btnBOCAnalyse.setOnClickListener(this);
        lvHomeFragmentBocAnalyse.setOnItemClickListener(this);
        vpAdvertisingCarousel.setOnPageChangeListener(this);  //广告图片轮播监听
        vpAdvertisingCarousel.setCurrentItem(Integer.MAX_VALUE / 2);//默认广告页在中间，使用户看不到边界
        swipeRefreshLayoutHomeFragmentBocAnalyse.setOnRefreshListener(this);
        lvHomeFragmentBocAnalyse.setOnScrollListener(this);
    }

    /**
     * 广告图片
     *
     * @param inflater
     */
    private void AdvertisingCarousel(LayoutInflater inflater) {
        //初始化iewPager的内容
        ImageView view1 = (ImageView) inflater.inflate(R.layout.item_advertising_carousel, null);
        ImageView view2 = (ImageView) inflater.inflate(R.layout.item_advertising_carousel, null);
        ImageView view3 = (ImageView) inflater.inflate(R.layout.item_advertising_carousel, null);
        view1.setImageResource(R.mipmap.image_banner1);
        view2.setImageResource(R.mipmap.image_banner2);
        view3.setImageResource(R.mipmap.image_banner3);
        List<ImageView> views = new ArrayList<ImageView>();
        views.add(view1);
        views.add(view2);
        views.add(view3);
        vpAdvertisingCarousel.setAdapter(new ImageCarouselAdapter(views));
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        handler.sendMessage(Message.obtain(handler, AdvertisingCarouselHandler.MSG_PAGE_CHANGED, position, 0));
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        switch (state) {
            case ViewPager.SCROLL_STATE_DRAGGING:
                handler.sendEmptyMessage(AdvertisingCarouselHandler.MSG_KEEP_SILENT);
                break;
            case ViewPager.SCROLL_STATE_IDLE:
                handler.sendEmptyMessageDelayed(AdvertisingCarouselHandler.MSG_UPDATE_IMAGE, AdvertisingCarouselHandler.MSG_DELAY);
                break;
            default:
                break;
        }
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.btnMessageReminder: //通知提醒
                intent = new Intent(getActivity(), MessageReminderActivity.class);
                startActivity(intent);
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
        String newsId = bocAnalyseBean.getNewsId();
        Intent intent = new Intent(getActivity(), BocAnalyseDetailActivity.class);
        intent.putExtra(NEWS_ID, newsId);
        startActivity(intent);
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
//            Toast.makeText(getActivity(), getString(R.string.common_not_date), Toast.LENGTH_SHORT).show();
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
        }
    }

    /**
     * 处理广告轮播线程
     */
    private static class AdvertisingCarouselHandler extends Handler {
        protected static final int MSG_UPDATE_IMAGE = 1; //请求更新显示图片
        protected static final int MSG_KEEP_SILENT = 2; //请求暂停轮播
        protected static final int MSG_BREAK_SILENT = 3; //请求恢复轮播
        protected static final int MSG_PAGE_CHANGED = 4; //手动滑动时，记录新页号
        protected static final long MSG_DELAY = 3000; //轮播间隔时间
        private WeakReference<Fragment> weakReference; //使用弱引用避免Handler泄露
        private int currentItem = 0;
        protected AdvertisingCarouselHandler(WeakReference<Fragment> wk) {
            weakReference = wk;
        }
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            HomeFragment fragment = (HomeFragment) weakReference.get();
            if (fragment == null) {
                return;  //无需处理
            }
            //检查消息队列并移除未发送的消息，避免消息出现重复的问题
            if (fragment.handler.hasMessages(MSG_UPDATE_IMAGE)) {
                fragment.handler.removeMessages(MSG_UPDATE_IMAGE);
            }
            switch (msg.what) {
                case MSG_UPDATE_IMAGE: //请求更新图片
                    currentItem++;
                    fragment.vpAdvertisingCarousel.setCurrentItem(currentItem);
                    //准备下次播放
                    fragment.handler.sendEmptyMessageDelayed(MSG_UPDATE_IMAGE, MSG_DELAY);
                    break;
                case MSG_KEEP_SILENT:  //请求暂停轮播
                    //只要不发送消息就暂停了
                    break;
                case MSG_BREAK_SILENT: //请求恢复轮播
                    fragment.handler.sendEmptyMessageDelayed(MSG_UPDATE_IMAGE, MSG_DELAY);
                    break;
                case MSG_PAGE_CHANGED: //手动滑动，记录新页号
                    //记录当前的页号，避免播放的时候页面显示不正确。
                    currentItem = msg.arg1;
                    break;
                default:
                    break;
            }
        }
    }
}
