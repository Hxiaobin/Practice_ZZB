package com.sf.bocfinancialsoftware.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.sf.bocfinancialsoftware.R;
import com.sf.bocfinancialsoftware.activity.home.business.BusinessQueryResultActivity;
import com.sf.bocfinancialsoftware.activity.user.UserActivity;
import com.sf.bocfinancialsoftware.adapter.home.home.HomeFragmentPagerAdapter;
import com.sf.bocfinancialsoftware.base.BaseActivity;
import com.sf.bocfinancialsoftware.fragment.AccountListFragment;
import com.sf.bocfinancialsoftware.fragment.HomeFragment;
import com.sf.bocfinancialsoftware.fragment.PleasantMessageFragment;
import com.sf.bocfinancialsoftware.fragment.ToolFragment;
import com.xys.libzxing.zxing.activity.CaptureActivity;

import java.util.ArrayList;
import java.util.List;

import static com.sf.bocfinancialsoftware.constant.ConstantConfig.CONTRACT_ID;
import static com.sf.bocfinancialsoftware.constant.ConstantConfig.SCAN_CODE_REQUEST_MAIN;
import static com.sf.bocfinancialsoftware.constant.ConstantConfig.SCAN_CODE_RESULT;

/**
 *
 */
public class MainActivity extends BaseActivity implements TabLayout.OnTabSelectedListener, View.OnClickListener {

    private TextView tvTitleBarTitle; //标题
    private ImageView ivTitleBarPersonalCenter; //个人中心
    private ImageView ivTitleBarScan; //扫一扫
    private TabLayout tabLayout;
    private ViewPager vpMain;
    private HomeFragmentPagerAdapter homeFragmentPagerAdapter;
    private TabLayout.Tab home;  //首页
    private TabLayout.Tab reminder;  //温馨提示
    private TabLayout.Tab financialAssistant;   //财务助手
    private TabLayout.Tab toolbar;  //工具箱
    private List<String> titleList;
    private List<Fragment> fragmentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();      //初始化控件
        initData();      // 初始化数据
        initListener();  //初始化监听事件
    }

    @Override
    protected void initView() {
        tvTitleBarTitle = (TextView) findViewById(R.id.tvTitleBarTitle);
        ivTitleBarPersonalCenter = (ImageView) findViewById(R.id.ivTitleBarPersonalCenter);
        ivTitleBarScan = (ImageView) findViewById(R.id.ivTitleBarScan);
        vpMain = (ViewPager) findViewById(R.id.vpMain);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
    }

    @Override
    protected void initData() {
        createBottomNavigationBar(); //创建底部导航栏
        ivTitleBarPersonalCenter.setVisibility(View.VISIBLE); //显示个人中心图标
        ivTitleBarScan.setVisibility(View.VISIBLE);  //显示扫一扫
    }

    @Override
    protected void initListener() {
        tabLayout.setOnTabSelectedListener(this);
        ivTitleBarScan.setOnClickListener(this);
        ivTitleBarPersonalCenter.setOnClickListener(this);
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
        fragmentList.add(2, new AccountListFragment());
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
            case R.id.ivTitleBarPersonalCenter: //个人中心
                startActivity(new Intent(MainActivity.this, UserActivity.class));
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
                String result = extras.getString(SCAN_CODE_RESULT);  //获取传回的业务编码
                Intent intent = new Intent(MainActivity.this, BusinessQueryResultActivity.class);
                intent.putExtra(CONTRACT_ID, result);
                startActivity(intent);
            }
        }
    }
}
