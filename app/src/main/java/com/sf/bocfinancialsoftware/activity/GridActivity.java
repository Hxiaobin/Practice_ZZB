package com.sf.bocfinancialsoftware.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.sf.bocfinancialsoftware.R;

import java.util.ArrayList;
import java.util.List;

public class GridActivity extends AppCompatActivity {

    private GridView mGridView;
    private Context mContext;
    private List<String> mList;
    private MyGridAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid);
        mContext = this;
        mGridView = (GridView) findViewById(R.id.gl);

        mList = new ArrayList<>();
        mAdapter = new MyGridAdapter();

        mGridView.setAdapter(mAdapter);


        for (int i = 0; i < 20; i++) {
            mList.add("kbkobrop"+i);
        }

        mAdapter.notifyDataSetChanged();
    }

    class MyGridAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public String getItem(int position) {
            return mList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Holder holder;
            if (convertView == null) {
                convertView = View.inflate(mContext, android.R.layout.simple_list_item_1, null);
                holder = new Holder();
                holder.mTextView = (TextView) convertView.findViewById(android.R.id.text1);
                convertView.setTag(holder);
            } else {
                holder = (Holder) convertView.getTag();
            }
            holder.mTextView.setText(getItem(position));
            return convertView;
        }

        class Holder {
            TextView mTextView;
        }
    }
}
