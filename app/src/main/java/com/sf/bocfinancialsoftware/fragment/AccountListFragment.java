package com.sf.bocfinancialsoftware.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.sf.bocfinancialsoftware.R;
import com.sf.bocfinancialsoftware.activity.financialAssistant.AccountDetailActivity;
import com.sf.bocfinancialsoftware.adapter.AccountListAdapter;
import com.sf.bocfinancialsoftware.bean.AccountListBean;

import java.util.ArrayList;
import java.util.List;

public class AccountListFragment extends Fragment {

    private ListView lvFinancialAssistant;
    private List<AccountListBean> mDates;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_financial_assistant, container, false);
        initView(view);
        initData();
        return view;
    }

    private void initView(View view) {
        lvFinancialAssistant = (ListView) view.findViewById(R.id.lvFinancialAssistant);
    }

    private void initData() {
        lvFinancialAssistant.setDividerHeight(0);
        mDates = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            mDates.add(new AccountListBean("2869****787"+i,"134,231,233.00","28,000,000.00"));
        }
        lvFinancialAssistant.setAdapter(new AccountListAdapter(getActivity(),mDates));
        lvFinancialAssistant.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //改变账号
                Bundle bundle = new Bundle();
                bundle.putString("account",mDates.get(position).getTvAccount());
                Intent intent = new Intent(getActivity(), AccountDetailActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

}
