package com.sf.bocfinancialsoftware.fragment;

import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.sf.bocfinancialsoftware.R;
import com.sf.bocfinancialsoftware.activity.home.analyse.BocAnalyseListActivity;
import com.sf.bocfinancialsoftware.activity.tool.calculator.CalculatorActivity;
import com.sf.bocfinancialsoftware.activity.tool.finance.FinanceProductListActivity;
import com.sf.bocfinancialsoftware.activity.tool.product.IntelProductListActivity;
import com.sf.bocfinancialsoftware.activity.tool.rate.RateActivity;
import com.sf.bocfinancialsoftware.adapter.PictureAdapter;
import com.sf.bocfinancialsoftware.bean.Picture;

import java.util.ArrayList;
import java.util.List;

public class ToolFragment extends Fragment implements AdapterView.OnItemClickListener {

    private GridView gvProduct, gvToolkit;
    private PictureAdapter iconAdapter;
    private PictureAdapter textAdapter;
    private String[] productName;
    private String[] calculatorName;
    private TypedArray productIv;
    private TypedArray calculatorIv;
    private List<Picture> productPictures;
    private List<Picture> calculatorPictures;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tool, container, false);
        initView(view);
        initDate();
        initLister();
        return view;
    }

    private void initDate() {
        productPictures = new ArrayList<>();
        calculatorPictures = new ArrayList<>();
        if (isAdded()) { //当该Fragment对象被添加到了它的Activity中时
            //产品市场图片的文字标题
            productName = getResources().getStringArray(R.array.tool_product_name);
            //产品市场图片
            productIv = getResources().obtainTypedArray(R.array.tool_product_icon);
            //工具箱图片的文字标题
            calculatorName = getResources().getStringArray(R.array.tool_calculator_name);
            //工具箱图片的文字标题
            calculatorIv = getResources().obtainTypedArray(R.array.tool_calculator_icon);
        }
        for (int i = 0; i < productName.length; i++) {
            productPictures.add(new Picture(productIv.getResourceId(i, 0), productName[i]));
        }
        for (int i = 0; i < calculatorName.length; i++) {
            calculatorPictures.add(new Picture(calculatorIv.getResourceId(i, 0), calculatorName[i]));
        }
        iconAdapter = new PictureAdapter(getActivity(), productPictures);
        textAdapter = new PictureAdapter(getActivity(), calculatorPictures);
        gvProduct.setAdapter(iconAdapter);
        gvToolkit.setAdapter(textAdapter);
    }

    private void initView(View view) {
        gvProduct = (GridView) view.findViewById(R.id.gvProduct);
        gvToolkit = (GridView) view.findViewById(R.id.gvToolkit);
    }

    private void initLister() {
        gvProduct.setOnItemClickListener(this);
        gvToolkit.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(getActivity(), CalculatorActivity.class));
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 0:
                startActivity(new Intent(getActivity(), BocAnalyseListActivity.class));
                break;
            case 1:
                startActivity(new Intent(getActivity(), RateActivity.class));
                break;
            case 2:
                startActivity(new Intent(getActivity(), IntelProductListActivity.class));
                break;
            case 3:
                startActivity(new Intent(getActivity(), FinanceProductListActivity.class));
                break;
        }
    }
}
