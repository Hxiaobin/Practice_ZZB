package com.sf.bocfinancialsoftware.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.sf.bocfinancialsoftware.fragment.AccountListFragment;
import com.sf.bocfinancialsoftware.fragment.MainPageFragment;
import com.sf.bocfinancialsoftware.fragment.PleasantMessageFragment;
import com.sf.bocfinancialsoftware.fragment.ToolFragment;

/**
 * Created by Author: wangyongzhu on 2017/6/8.
 */

public class MyFragmentPagerAdapter extends FragmentPagerAdapter {

    private MainPageFragment mMainPageFragment = null;
    private PleasantMessageFragment mPleasantMessageFragment = null;
    private AccountListFragment mFinancialAssistantFragment = null;
    private ToolFragment mToolFragment = null;

    private String[] mTitles = {"首页","温馨提示","财务助手","工具箱"};

    public String[] getTitles() {
        return mTitles;
    }

    public void setTitles(String[] titles) {
        mTitles = titles;
    }

    public MyFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
        mMainPageFragment = new MainPageFragment();
        mPleasantMessageFragment = new PleasantMessageFragment();
        mFinancialAssistantFragment = new AccountListFragment();
        mToolFragment = new ToolFragment();
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 1){
            return mPleasantMessageFragment;
        }else if (position == 2){
            return mFinancialAssistantFragment;
        } else if (position == 3) {
            return mToolFragment;
        }
        return mMainPageFragment;
    }

    @Override
    public int getCount() {
        return mTitles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }
}
