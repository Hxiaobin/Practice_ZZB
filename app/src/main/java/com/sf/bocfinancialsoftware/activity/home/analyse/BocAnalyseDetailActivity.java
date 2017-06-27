package com.sf.bocfinancialsoftware.activity.home.analyse;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.sf.bocfinancialsoftware.R;
import com.sf.bocfinancialsoftware.activity.base.BaseActivity;
import com.sf.bocfinancialsoftware.adapter.ImageCarouselAdapter;
import com.sf.bocfinancialsoftware.bean.BocAnalyseBean;
import com.sf.bocfinancialsoftware.util.DataBaseSQLiteUtil;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import static com.sf.bocfinancialsoftware.constant.ConstantConfig.NEWS_ID;

/**
 * 中银分析详情
 * Created by sn on 2017/6/9.
 */

public class BocAnalyseDetailActivity extends BaseActivity implements ViewPager.OnPageChangeListener {

    private ImageView ivTitleBarBack; //返回按钮
    private TextView tvTitleBarTitle; //标题
    private TextView tvBocAnalyseDetailNewsTitle; //文本标题
    private TextView tvBocAnalyseDetailHtmlContent; //文本内容
    private TextView tvBocAnalyseDetailNewsDate; //文本时间
    private ViewPager vpBocAnalyseDetail;  //图片轮播
    private Intent intent;
    private String newsId; //新闻id
    private BocAnalyseBean bocAnalyseBean; //新闻对象
    private ImageCarouselHandler handler = new ImageCarouselHandler(new WeakReference<Activity>(this)); //处理图片轮播的线程

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boc_analyse_detail);
        initView();
        initData();
        initListener();
        handler.sendEmptyMessageDelayed(ImageCarouselHandler.MSG_UPDATE_IMAGE, ImageCarouselHandler.MSG_DELAY);//开始轮播效果
    }

    @Override
    protected void initView() {
        ivTitleBarBack = (ImageView) findViewById(R.id.ivTitleBarBack);
        tvTitleBarTitle = (TextView) findViewById(R.id.tvTitleBarTitle);
        tvBocAnalyseDetailNewsTitle = (TextView) findViewById(R.id.tvBocAnalyseDetailNewsTitle);
        tvBocAnalyseDetailHtmlContent = (TextView) findViewById(R.id.tvBocAnalyseDetailHtmlContent);
        tvBocAnalyseDetailNewsDate = (TextView) findViewById(R.id.tvBocAnalyseDetailNewsDate);
        vpBocAnalyseDetail = (ViewPager) findViewById(R.id.vpBocAnalyseDetail);
    }

    @Override
    protected void initData() {
        tvTitleBarTitle.setText(getString(R.string.common_boc_analyse));
        ivTitleBarBack.setVisibility(View.VISIBLE);
        //获取上一页面传递的数据
        intent = getIntent();
        newsId = intent.getStringExtra(NEWS_ID);
        bocAnalyseBean = DataBaseSQLiteUtil.getBocAnalyseById(newsId);
        showNewsDetail(bocAnalyseBean);
    }

    @Override
    protected void initListener() {
        ivTitleBarBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        vpBocAnalyseDetail.setOnPageChangeListener(this);  //广告图片轮播监听
        vpBocAnalyseDetail.setCurrentItem(Integer.MAX_VALUE / 2);//默认广告页在中间，使用户看不到边界
    }

    /**
     * 显示页面数据
     *
     * @param bocAnalyseBean
     */
    private void showNewsDetail(BocAnalyseBean bocAnalyseBean) {
        tvBocAnalyseDetailNewsTitle.setText(bocAnalyseBean.getNewsTitle());
        tvBocAnalyseDetailHtmlContent.setText("        " + bocAnalyseBean.getHtmlContent());
        tvBocAnalyseDetailNewsDate.setText(bocAnalyseBean.getNewsData());
        AdvertisingCarousel(bocAnalyseBean);
    }

    /**
     * 获取中银分析详情页广告轮播图片
     *
     * @param bocAnalyseBean
     */
    private void AdvertisingCarousel(BocAnalyseBean bocAnalyseBean) {
        //初始化iewPager的内容
        List<String> imageList = bocAnalyseBean.getImageList();
        List<ImageView> views = new ArrayList<>();
        for (String imageUrl : imageList) {
            ImageView view = (ImageView) getLayoutInflater().inflate(R.layout.item_advertising_carousel, null);
            view.setImageResource(Integer.parseInt(imageUrl));
            views.add(view);
        }
        vpBocAnalyseDetail.setAdapter(new ImageCarouselAdapter(views));
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        handler.sendMessage(Message.obtain(handler, ImageCarouselHandler.MSG_PAGE_CHANGED, position, 0));
    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {
        switch (state) {
            case ViewPager.SCROLL_STATE_DRAGGING:
                handler.sendEmptyMessage(ImageCarouselHandler.MSG_KEEP_SILENT);
                break;
            case ViewPager.SCROLL_STATE_IDLE:
                handler.sendEmptyMessageDelayed(ImageCarouselHandler.MSG_UPDATE_IMAGE, ImageCarouselHandler.MSG_DELAY);
                break;
            default:
                break;
        }
    }

    /**
     * 处理广告轮播线程
     */
    private static class ImageCarouselHandler extends Handler {
        protected static final int MSG_UPDATE_IMAGE = 1; //请求更新显示图片
        protected static final int MSG_KEEP_SILENT = 2; //请求暂停轮播
        protected static final int MSG_BREAK_SILENT = 3; //请求恢复轮播
        protected static final int MSG_PAGE_CHANGED = 4; //手动滑动时，记录新页号
        protected static final long MSG_DELAY = 3000; //轮播间隔时间
        private WeakReference<Activity> weakReference; //使用弱引用避免Handler泄露
        private int currentItem = 0;

        protected ImageCarouselHandler(WeakReference<Activity> wk) {
            weakReference = wk;
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            BocAnalyseDetailActivity activity = (BocAnalyseDetailActivity) weakReference.get();
            if (activity == null) {
                return;  //无需处理
            }
            //检查消息队列并移除未发送的消息，避免消息出现重复的问题
            if (activity.handler.hasMessages(MSG_UPDATE_IMAGE)) {
                activity.handler.removeMessages(MSG_UPDATE_IMAGE);
            }
            switch (msg.what) {
                case MSG_UPDATE_IMAGE: //请求更新图片
                    currentItem++;
                    activity.vpBocAnalyseDetail.setCurrentItem(currentItem);
                    //准备下次播放
                    activity.handler.sendEmptyMessageDelayed(MSG_UPDATE_IMAGE, MSG_DELAY);
                    break;
                case MSG_KEEP_SILENT:  //请求暂停轮播
                    //只要不发送消息就暂停了
                    break;
                case MSG_BREAK_SILENT: //请求恢复轮播
                    activity.handler.sendEmptyMessageDelayed(MSG_UPDATE_IMAGE, MSG_DELAY);
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
