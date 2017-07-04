package com.sf.bocfinancialsoftware.activity.tool.product;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.sf.bocfinancialsoftware.R;

/**
 * Created by Author: wangyongzhu on 2017/7/3.
 */

public class SearchView extends LinearLayout implements View.OnClickListener {
    private Context mContext;
    private EditText etInput;//输入框
    private ImageView ivDelete;//删除键
    private ListView lvTips;//弹出列表
    private ArrayAdapter<String> mHintAdapter;//提示adapter
    private ArrayAdapter<String> mAutoCompleteAdapter;//自动补全adapter 只显示名字
    private SearchViewListener mListener;//搜索回调接口

    /**
     * 设置搜索回调接口
     *
     * @param listener 监听者
     */
    public void setSearchViewListener(SearchViewListener listener) {
        mListener = listener;
    }

    public SearchView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        LayoutInflater.from(context).inflate(R.layout.activity_product, null);
        initView();
        initListener();
    }

    private void initView() {
        etInput = (EditText) findViewById(R.id.etSearch);
        ivDelete = (ImageView) findViewById(R.id.ivDelete);
        lvTips = (ListView) findViewById(R.id.lvProduct1);

    }

    private void initListener() {
        ivDelete.setOnClickListener(this);
        etInput.setOnClickListener(this);
        etInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!TextUtils.isEmpty(s.toString())) {
                    ivDelete.setVisibility(VISIBLE);
                    lvTips.setVisibility(VISIBLE);
                    if (mAutoCompleteAdapter != null && lvTips.getAdapter() != mAutoCompleteAdapter) {
                        lvTips.setAdapter(mAutoCompleteAdapter);
                    }
                    //更新autoComplete数据
                    if (mListener != null) {
                        mListener.onRefreshAutoComplete(s + "");
                    }
                } else {
                    ivDelete.setVisibility(GONE);
                    if (mHintAdapter != null) {
                        lvTips.setAdapter(mHintAdapter);
                    }
                    lvTips.setVisibility(GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        lvTips.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String text = lvTips.getAdapter().getItem(position).toString();
                etInput.setText(text);
                etInput.setSelection(text.length());
                lvTips.setVisibility(GONE);
                notifyStartSearching(text);
            }
        });
    }

    /**
     * 通知监听者 进行搜索操作
     *
     * @param text
     */
    private void notifyStartSearching(String text) {
        if (mListener != null) {
            mListener.onSearch(etInput.getText().toString());
        }
        //隐藏软键盘
        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /**
     * 设置自动补全adapter
     *
     * @param adapter
     */
    public void setAutoCompleteAdapter(ArrayAdapter<String> adapter) {
        this.mAutoCompleteAdapter = adapter;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.etSearch:
                lvTips.setVisibility(VISIBLE);
                break;
            case R.id.ivDelete:
                etInput.setText("");
                ivDelete.setVisibility(GONE);
                break;
        }
    }

    /**
     * search view回调方法
     */
    public interface SearchViewListener {
        /**
         * 更新自动补全内容
         *
         * @param text 传入补全后的文本
         */
        void onRefreshAutoComplete(String text);

        /**
         * 开始搜索
         *
         * @param text 传入输入框的文本
         */
        void onSearch(String text);
    }
}
