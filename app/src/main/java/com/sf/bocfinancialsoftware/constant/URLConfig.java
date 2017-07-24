package com.sf.bocfinancialsoftware.constant;

/**
 * url地址
 * Created by Author: wangyongzhu on 2017/7/13.
 */

public class URLConfig {
    //
    private static final String BASE_URL="http://192.168.2.123/zzb/";
    //财务助手列表
    public static final String ACCOUNT_LIST = BASE_URL+"account/AccountPage_Case.resp";
    //财务助手详情
    public static final String ACCOUNT_DETAIL_CASE = BASE_URL+"account/AccountDetail_Case.resp";
    //图片地址
    public static final String IMAGE_RESULT = BASE_URL+"AdvertLoop.resp";
    //热销理财产品列表
    public static final String FINANCE_PRODUCT_LIST = BASE_URL+"tool/finance/FinanceProductPage_Case.resp";
    //热销理财产品详情
    public static final String FINANCE_PRODUCT_DETAIL = BASE_URL+"tool/finance/FinanceProductDetail_Case.resp";
    //汇率
    public static final String RATE_RESULT = BASE_URL+"tool/rate/ExchangeRateAct.resp";
    //产品介绍列表
    public static final String PRODUCT_LIST_CASE = BASE_URL+"tool/product/IntelProductPage_Case.resp";
    //产品介绍详情
    public static final String PRODUCT_DETAIL_CASE = BASE_URL+"tool/product/IntelProductDetail_Case.resp";
    //汇率计算器
    public static final String EXCHANGE_RATE_CALCULATE = BASE_URL+"tool/calculate/ExchangeRateCalculateAct.resp";
    //币种
    public static final String FORWARD_QUOTATION_CURRENCY_LIST = BASE_URL+"tool/calculate/ForwardQuotationCalculateListAct.resp";
    //远期报价计算器
    public static final String FORWARD_QUOTATION_CURRENCY = BASE_URL+"tool/calculate/ForwardQuotationCalculateAct.resp";
    //意见反馈
    public static final String UP_SUGGESTION = BASE_URL+"user/UpSuggestion.resp";
    //版本检测
    public static final String CHECK_VERSION = BASE_URL+"user/CheckVersion.resp";

}
