package com.sf.bocfinancialsoftware.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.sf.bocfinancialsoftware.R;
import com.sf.bocfinancialsoftware.activity.user.UserActivity;
import com.sf.bocfinancialsoftware.adapter.MyFragmentPagerAdapter;

public class MainActivity extends AppCompatActivity {

    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private ImageView ivUser; //注册  登录个人中心

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initDate();
    }

    private void initView() {
//        ivUser = (ImageView) findViewById(R.id.ivUser);
//        mTabLayout = (TabLayout) findViewById(R.id.tlTab);
//        mViewPager = (ViewPager) findViewById(R.id.vpContent);
        mViewPager.setOffscreenPageLimit(3); //缓存3个界面
        mViewPager.setAdapter(new MyFragmentPagerAdapter(getSupportFragmentManager()));
        //绑定viewpager
        mTabLayout.setupWithViewPager(mViewPager);

        //获取图片
        mTabLayout.getTabAt(0).setIcon(R.drawable.bottom_nav_main_page);
        mTabLayout.getTabAt(1).setIcon(R.drawable.bottom_nav_pleasant_message);
        mTabLayout.getTabAt(2).setIcon(R.drawable.bottom_nav_financial_assistant);
        mTabLayout.getTabAt(3).setIcon(R.drawable.bottom_nav_tool);

        ivUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, UserActivity.class));
            }
        });
    }

    private void initDate() {
    }

}
