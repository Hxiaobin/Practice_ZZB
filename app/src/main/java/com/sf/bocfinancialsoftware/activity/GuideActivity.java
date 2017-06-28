package com.sf.bocfinancialsoftware.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.sf.bocfinancialsoftware.R;
import com.sf.bocfinancialsoftware.activity.base.BaseActivity;
import com.sf.bocfinancialsoftware.adapter.GuideAdapter;

import java.util.ArrayList;
import java.util.List;

import static com.sf.bocfinancialsoftware.constant.ConstantConfig.FILE_FIRST_IN;
import static com.sf.bocfinancialsoftware.constant.ConstantConfig.IS_FIRST_IN;


/**
 * 引导页
 * Created by sn on 2017/6/28.
 */
public class GuideActivity extends BaseActivity {

    private ViewPager vpGuide;
    private Button btnExperience; //立即体验
    private List<View> views; //引导页数据源
    private GuideAdapter adapter; //ViewPager适配器
    private View guide1;  //引导页1
    private View guide2;  //引导页2
    private View guide3;  //引导页3

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        initView();
        initData();
        initListener();
    }

    @Override
    protected void initView() {
        vpGuide = (ViewPager) findViewById(R.id.vpGuide);
        guide1 = LayoutInflater.from(GuideActivity.this).inflate(R.layout.layout_guide_one, null);
        guide2 = LayoutInflater.from(GuideActivity.this).inflate(R.layout.layout_guide_two, null);
        guide3 = LayoutInflater.from(GuideActivity.this).inflate(R.layout.layout_guide_three, null);
        btnExperience = (Button) guide3.findViewById(R.id.btnExperience);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }

    @Override
    protected void initData() {
        views = new ArrayList<>();
        views.add(guide1);
        views.add(guide2);
        views.add(guide3);
        adapter = new GuideAdapter(GuideActivity.this, views);
        vpGuide.setAdapter(adapter);
    }

    @Override
    protected void initListener() {
        btnExperience.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getSharedPreferences(FILE_FIRST_IN, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean(IS_FIRST_IN, false);  //将状态设置为：不是第一次进入app
                editor.commit();
                overridePendingTransition(R.anim.zoomin, R.anim.zoomout);
                startActivity(new Intent(GuideActivity.this, MainActivity.class));
                finish();
            }
        });
    }

}
