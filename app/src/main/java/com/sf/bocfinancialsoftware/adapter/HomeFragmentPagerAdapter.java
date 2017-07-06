package com.sf.bocfinancialsoftware.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * 首页TabLayout适配器
 * Created by sn on 2017/6/8.
 */

public class HomeFragmentPagerAdapter extends FragmentPagerAdapter{

    private List<String> titleList;  //标题集合
    private List<Fragment> fragmentList; //页面集合

    /**
     * 构造函数
     * @param fm
     * @param titleList
     * @param fragmentList
     */
    public HomeFragmentPagerAdapter(FragmentManager fm, List<String> titleList, List<Fragment> fragmentList) {
        super(fm);
        this.titleList = titleList;
        this.fragmentList = fragmentList;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titleList.get(position);
    }

}
