package com.sf.bocfinancialsoftware.activity.home.business;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.sf.bocfinancialsoftware.R;
import com.sf.bocfinancialsoftware.adapter.home.business.BusinessQueryAdapter;
import com.sf.bocfinancialsoftware.base.BaseActivity;
import com.sf.bocfinancialsoftware.bean.BusinessBean;
import com.sf.bocfinancialsoftware.http.HttpCallBackListener;
import com.sf.bocfinancialsoftware.http.HttpUtil;
import com.sf.bocfinancialsoftware.widget.ClearEditTextTextWatcher;
import com.xys.libzxing.zxing.activity.CaptureActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.sf.bocfinancialsoftware.constant.ConstantConfig.BUSINESS_ID;
import static com.sf.bocfinancialsoftware.constant.ConstantConfig.CONTRACT_ID;
import static com.sf.bocfinancialsoftware.constant.ConstantConfig.SCAN_CODE_REQUEST_BUSINESS_QUERY;
import static com.sf.bocfinancialsoftware.constant.ConstantConfig.SCAN_CODE_RESULT;
import static com.sf.bocfinancialsoftware.constant.URLConfig.BUSINESS_TYPE_LIST_URL;

/**
 * 业务查询
 * Created by sn on 2017/6/14.
 */

public class BusinessQueryActivity extends BaseActivity implements View.OnClickListener, ExpandableListView.OnChildClickListener {

    private ImageView ivTitleBarBack;  //返回
    private ImageView ivBusinessQueryScan;  //扫一扫
    private ImageView ivBusinessQueryClear;  //清除文本
    private TextView tvTitleBarTitle;  //标题
    private EditText etBusinessQuery; //输入框
    private Button btnBusinessQuery; //查询按钮
    private ExpandableListView elvBusiness; //可扩展列表
    private List<BusinessBean.BusinessTypeBean> groups;  //父类集合
    private List<List<BusinessBean.BusinessTypeBean.Business>> children; //所有子类集合
    private BusinessQueryAdapter adapter;  //列表适配器
    private String contractId;  //业务编号
    private HashMap<String, String> map; // 保存请求参数

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_query);
        initView();
        initData();
        initListener();
    }

    @Override
    protected void initView() {
        ivTitleBarBack = (ImageView) findViewById(R.id.ivTitleBarBack);
        ivBusinessQueryScan = (ImageView) findViewById(R.id.ivBusinessQueryScan);
        ivBusinessQueryClear = (ImageView) findViewById(R.id.ivBusinessQueryClear);
        tvTitleBarTitle = (TextView) findViewById(R.id.tvTitleBarTitle);
        etBusinessQuery = (EditText) findViewById(R.id.etBusinessQuery);
        btnBusinessQuery = (Button) findViewById(R.id.btnBusinessQuery);
        elvBusiness = (ExpandableListView) findViewById(R.id.elvBusiness);
    }

    @Override
    protected void initData() {
        tvTitleBarTitle.setText(getString(R.string.common_business_query));
        ivTitleBarBack.setVisibility(View.VISIBLE);
        getBusinessType();  //获取业务类别 一级目录
        ClearEditTextTextWatcher textWatcher = new ClearEditTextTextWatcher(BusinessQueryActivity.this, etBusinessQuery, ivBusinessQueryClear);
        etBusinessQuery.addTextChangedListener(textWatcher);
    }

    /**
     * 获取业务类别
     */
    public void getBusinessType() {
        map = new HashMap<>();
        groups = new ArrayList<>();
        children = new ArrayList<>();
        adapter = new BusinessQueryAdapter(BusinessQueryActivity.this, groups, children);
        elvBusiness.setAdapter(adapter);
        getBusinessList(BUSINESS_TYPE_LIST_URL, map);  // 请求网络获取业务列表
    }

    /**
     * 获取业务列表
     *
     * @param url       请求链接
     * @param mapObject 请求参数
     */
    public void getBusinessList(String url, HashMap<String, String> mapObject) {
        HttpUtil.getNetworksJSonResponse(BusinessQueryActivity.this, url, mapObject, new HttpCallBackListener() {
            @Override
            public void onSuccess(String response) {
                Gson gson = new Gson();
                BusinessBean bean = gson.fromJson(response, BusinessBean.class);
                groups.addAll(bean.getContent()); //父目录
                for (int i = 0; i < groups.size(); i++) {
                    List<BusinessBean.BusinessTypeBean.Business> businessArray = groups.get(i).getBusinessArray();
                    children.add(businessArray);  //子目录
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailed(String msg) {

            }

            @Override
            public void onError(Exception e) {

            }
        });
    }

    @Override
    protected void initListener() {
        ivTitleBarBack.setOnClickListener(this);
        ivBusinessQueryScan.setOnClickListener(this);
        ivBusinessQueryClear.setOnClickListener(this);
        elvBusiness.setOnChildClickListener(this);
        btnBusinessQuery.setOnClickListener(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SCAN_CODE_REQUEST_BUSINESS_QUERY && resultCode == RESULT_OK) {  //扫描二维码值回调
            Bundle extras = data.getExtras();
            if (null != extras) {
                String result = extras.getString(SCAN_CODE_RESULT);  //获取传回的业务编码
                Intent intent = new Intent(BusinessQueryActivity.this, BusinessQueryResultActivity.class);
                intent.putExtra(CONTRACT_ID, result);
                startActivity(intent);
            }
        }
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.ivTitleBarBack:  //返回
                finish();
                break;
            case R.id.ivBusinessQueryScan:  //扫一扫
                intent = new Intent(BusinessQueryActivity.this, CaptureActivity.class);
                startActivityForResult(intent, SCAN_CODE_REQUEST_BUSINESS_QUERY);
                break;
            case R.id.ivBusinessQueryClear: //清除文本
                etBusinessQuery.setText("");
                break;
            case R.id.btnBusinessQuery:  //查询
                contractId = etBusinessQuery.getText().toString();
                if (!TextUtils.isEmpty(contractId)) {
                    intent = new Intent(BusinessQueryActivity.this, BusinessQueryResultActivity.class);
                    intent.putExtra(CONTRACT_ID, contractId);
                    startActivity(intent);
                } else {
                    Toast.makeText(BusinessQueryActivity.this, getString(R.string.common_please_enter_the_contract_id), Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
        Intent intent = new Intent(BusinessQueryActivity.this, BusinessQueryCriteriaActivity.class);
        intent.putExtra(BUSINESS_ID, children.get(groupPosition).get(childPosition).getBusinessId()); //业务id
        startActivity(intent);
        return true;
    }

}
