package com.sf.bocfinancialsoftware.activity.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.sf.bocfinancialsoftware.R;
import com.sf.bocfinancialsoftware.activity.business.BusinessQueryResultActivity;
import com.sf.bocfinancialsoftware.activity.user.UserActivity;
import com.sf.bocfinancialsoftware.adapter.HomeFragmentPagerAdapter;
import com.sf.bocfinancialsoftware.bean.BocAnalyseBean;
import com.sf.bocfinancialsoftware.bean.ContractBean;
import com.sf.bocfinancialsoftware.bean.MessageReminderBean;
import com.sf.bocfinancialsoftware.bean.PleasantMessageBean;
import com.sf.bocfinancialsoftware.fragment.FinancialAssistantFragment;
import com.sf.bocfinancialsoftware.fragment.HomeFragment;
import com.sf.bocfinancialsoftware.fragment.PleasantMessageFragment;
import com.sf.bocfinancialsoftware.fragment.ToolFragment;
import com.sf.bocfinancialsoftware.fragment.ToolbarFragment;
import com.sf.bocfinancialsoftware.util.DataBaseSQLiteUtil;
import com.sf.bocfinancialsoftware.util.DataUtil;
import com.xys.libzxing.zxing.activity.CaptureActivity;

import java.util.ArrayList;
import java.util.List;

import static com.sf.bocfinancialsoftware.constant.ConstantConfig.CONTRACT_ID;
import static com.sf.bocfinancialsoftware.constant.ConstantConfig.SCAN_CODE_REQUEST_MAIN;

public class MainActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener, View.OnClickListener {

    private TextView tvTitleBarTitle; //标题
    private ImageView ivTitleBarPersonalCenter; //个人中心
    private ImageView ivTitleBarScan; //扫一扫
    private TabLayout tabLayout;
    private ViewPager vpMain;
    private HomeFragmentPagerAdapter homeFragmentPagerAdapter;

    private TabLayout.Tab home;
    private TabLayout.Tab reminder;
    private TabLayout.Tab financialAssistant;
    private TabLayout.Tab toolbar;

    private List<String> titleList;
    private List<Fragment> fragmentList;
    private ImageView ivUser; //注册  登录个人中心

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();      //初始化控件
        initData();      // 初始化数据
        initListener();  //初始化监听事件
    }

    private void initView() {
        tvTitleBarTitle = (TextView) findViewById(R.id.tvTitleBarTitle);
        ivTitleBarPersonalCenter = (ImageView) findViewById(R.id.ivTitleBarPersonalCenter);
        ivTitleBarScan = (ImageView) findViewById(R.id.ivTitleBarScan);
        vpMain = (ViewPager) findViewById(R.id.vpMain);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        ivUser = (ImageView) findViewById(R.id.ivTitleBarBack);
    }

    private void initData() {
        createBottomNavigationBar(); //创建底部导航栏
        ivTitleBarPersonalCenter.setVisibility(View.VISIBLE); //显示个人中心图标
        ivTitleBarScan.setVisibility(View.VISIBLE);  //显示扫一扫
        List<BocAnalyseBean> bocAnalyseBeanList = DataBaseSQLiteUtil.queryBocAnalyseList();
        if (bocAnalyseBeanList == null || bocAnalyseBeanList.size() <= 0) {
            DataUtil.setBocAnalyseDate(); //设置中银分析数据源
        }
        List<MessageReminderBean> messageReminderBeanList = DataBaseSQLiteUtil.queryAllMessageReminderList();
        if (messageReminderBeanList == null || messageReminderBeanList.size() <= 0) {
            DataUtil.setMessageReminderData(); //设置通知提醒数据源
        }
        List<ContractBean> contractBeanList = DataBaseSQLiteUtil.queryAllContractList();
        if (contractBeanList == null || contractBeanList.size() <= 0) {
            DataUtil.setContractDate(); //设置业务合同数据
        }
        List<PleasantMessageBean> pleasantMessageBeanList = DataBaseSQLiteUtil.queryAllPleasantMessage();
        if (pleasantMessageBeanList == null || pleasantMessageBeanList.size() <= 0) {
            DataUtil.setPleasantMessageData(); //设置温馨提示数据
        }

    }

    /**
     * 创建底部导航栏
     */
    private void createBottomNavigationBar() {
        //每一个Fragment标题和内容的数据源
        titleList = new ArrayList<String>(); //标题列表
        fragmentList = new ArrayList<Fragment>();  //Fragment页面列表
        //使用适配器将ViewPager与Fragment绑定在一起
        FragmentManager fragmentManager = getSupportFragmentManager();
        homeFragmentPagerAdapter = new HomeFragmentPagerAdapter(fragmentManager, titleList, fragmentList);
        //viewPager缓存
        vpMain.setOffscreenPageLimit(3);
        //设置标题和内容
        setFragment();
        //添加适配器
        vpMain.setAdapter(homeFragmentPagerAdapter);
        //将TabLayout与ViewPager绑定在一起
        tabLayout.setupWithViewPager(vpMain);
        //设置TabLayout位置、图标、文字
        setTabPosition();
        tvTitleBarTitle.setText(titleList.get(0));
    }

    private void initListener() {
        tabLayout.setOnTabSelectedListener(this);
        ivTitleBarScan.setOnClickListener(this);
        ivUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, UserActivity.class));
            }
        });
    }

    /**
     * 设置每一个Fragment的标题和内容
     */
    private void setFragment() {
        titleList.add(0, getString(R.string.common_home));
        titleList.add(1, getString(R.string.common_reminder));
        titleList.add(2, getString(R.string.common_financialAssistant));
        titleList.add(3, getString(R.string.common_toolbar));
        fragmentList.add(0, new HomeFragment());
        fragmentList.add(1, new PleasantMessageFragment());
        fragmentList.add(2, new FinancialAssistantFragment());
        fragmentList.add(3, new ToolFragment());
    }

    /**
     * 指定Tab位置和对应的icon
     */
    private void setTabPosition() {
        home = tabLayout.getTabAt(0);
        reminder = tabLayout.getTabAt(1);
        financialAssistant = tabLayout.getTabAt(2);
        toolbar = tabLayout.getTabAt(3);
        home.setIcon(R.drawable.selector_button_home);
        reminder.setIcon(R.drawable.selector_button_reminder);
        financialAssistant.setIcon(R.drawable.selector_button_financial_assistant);
        toolbar.setIcon(R.drawable.selector_button_toobox);
    }

    /**
     * Tab选中，设置页面标题
     *
     * @param tab
     */
    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        tvTitleBarTitle.setText(tab.getText());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivTitleBarScan: //扫一扫
                Intent intent = new Intent(MainActivity.this, CaptureActivity.class);
                startActivityForResult(intent, SCAN_CODE_REQUEST_MAIN);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SCAN_CODE_REQUEST_MAIN && resultCode == RESULT_OK) {  //扫描二维码值回调
            Bundle extras = data.getExtras();
            if (null != extras) {
                String result = extras.getString("result");  //获取传回的业务编码
                Intent intent = new Intent(MainActivity.this, BusinessQueryResultActivity.class);
                intent.putExtra(CONTRACT_ID, result);
                startActivity(intent);
            }
        }
    }
}
