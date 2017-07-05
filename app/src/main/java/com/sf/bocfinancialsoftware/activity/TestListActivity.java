package com.sf.bocfinancialsoftware.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.sf.bocfinancialsoftware.R;
import com.sf.bocfinancialsoftware.adapter.Test;

import java.util.ArrayList;
import java.util.List;

public class TestListActivity extends AppCompatActivity {

    private ListView lv;
    private List<String> mList;
    private Context mContext;
    private Test mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_list);
        mContext = this;
        lv = (ListView) findViewById(R.id.lv);

        mList = new ArrayList<>();
        mAdapter = new Test(mContext,mList);
        lv.setAdapter(mAdapter);

        initData();


        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(mContext, "5555", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void initData() {
        for (int i = 0; i < 20; i++) {
            mList.add("bobjirbhi"+i);
        }
        mAdapter.notifyDataSetChanged();
    }

}
