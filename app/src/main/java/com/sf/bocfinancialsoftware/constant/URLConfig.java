package com.sf.bocfinancialsoftware.constant;

/**
 * 网路请求URL地址
 * Created by sn on 2017/7/12.
 */

public class URLConfig {

    //URL公共部分
    private static final String BASE_URL = "http://192.168.2.123/zzb/";
    //中银分析列表
    public static final String BOC_ANALYSE_LIST_URL = BASE_URL + "home/bocAnalyse/bocAnalyseList.resp";
    //中银分析详情
    public static final String BOC_ANALYSE_NEWS_DETAIL_URL = BASE_URL + "home/bocAnalyse/bocAnalyseDetail.resp";
    //图片轮播
    public static final String ADVERT_LOOP_IMAGE_URL = BASE_URL + "home/advertLoop/advertLoopByType.resp";
    //PopupWindow筛选进口通知列表
    public static final String IMPORT_MESSAGE_LIST_URL = BASE_URL + "home/message/importMessage/importMessageList.resp";
    //PopupWindow筛选出口通知列表
    public static final String EXPORT_MESSAGE_LIST_URL = BASE_URL + "home/message/exportMessage/exportMessageList.resp";
    //PopupWindow筛选保函通知列表
    public static final String GUARANTEE_MESSAGE_LIST_URL = BASE_URL + "home/message/guaranteeMessage/guaranteeMessageList.resp";
    //PopupWindow筛选保理通知列表
    public static final String FACTORING_MESSAGE_LIST_URL = BASE_URL + "home/message/factoringMessage/factoringMessageList.resp";
    //PopupWindow筛选远期通知列表
    public static final String FORWARD_MESSAGE_LIST_URL = BASE_URL + "home/message/forwardMessage/forwardMessageList.resp";
    //未读消息数量
    public static final String MESSAGE_UN_READ_URL = BASE_URL + "home/message/unReadMessage.resp";
    //通知提醒列表
    public static final String MESSAGE_LIST_URL = BASE_URL + "home/message/messageList.resp";
    //业务类别 获取业务列表
    public static final String BUSINESS_TYPE_LIST_URL = BASE_URL + "home/business/businessList.resp";
    //根据合同编号获取合同列表
    public static final String CONTRACT_LIST_BY_CONTRACT_ID_URL = BASE_URL + "home/business/businessListByContractIdWithoutBusinessId.resp";
    //根据业务id+合同编号获取合同列表
    public static final String CONTRACT_LIST_BY_BUSINESS_ID_AND_CONTRACT_ID_URL = BASE_URL + "home/business/businessListByContractId.resp";
    //根据业务id+起止时间获取合同列表
    public static final String CONTRACT_LIST_BY_BUSINESS_ID_AND_DATE_URL = BASE_URL + "home/business/businessListByData.resp";
    //根据业务id+起止时间+合同编号
    public static final String CONTRACT_LIST_BY_ALL_URL = BASE_URL + "home/business/businessListByAll.resp";
    // 财务助手列表
    public static final String ACCOUNT_LIST = BASE_URL + "account/AccountPage_Case.resp";
    //财务助手详情
    public static final String ACCOUNT_DETAIL_CASE = BASE_URL + "account/AccountDetail_Case.resp";
    //图片地址
    public static final String IMAGE_RESULT = BASE_URL + "AdvertLoop.resp";
    //热销理财产品列表
    public static final String FINANCE_PRODUCT_LIST = BASE_URL + "tool/finance/FinanceProductPage_Case.resp";
    //热销理财产品详情
    public static final String FINANCE_PRODUCT_DETAIL = BASE_URL + "tool/finance/FinanceProductDetail_Case.resp";
    //汇率
    public static final String RATE_RESULT = BASE_URL + "tool/rate/ExchangeRateAct.resp";
    //产品介绍列表
    public static final String PRODUCT_LIST_CASE = BASE_URL + "tool/product/IntelProductPage_Case.resp";
    //产品介绍详情
    public static final String PRODUCT_DETAIL_CASE = BASE_URL + "tool/product/IntelProductDetail_Case.resp";
    //汇率计算器
    public static final String EXCHANGE_RATE_CALCULATE = BASE_URL + "tool/calculate/ExchangeRateCalculateAct.resp";
    //币种
    public static final String FORWARD_QUOTATION_CURRENCY_LIST = BASE_URL + "tool/calculate/ForwardQuotationCalculateListAct.resp";
    //远期报价计算器
    public static final String FORWARD_QUOTATION_CURRENCY = BASE_URL + "tool/calculate/ForwardQuotationCalculateAct.resp";
    //意见反馈
    public static final String UP_SUGGESTION = BASE_URL + "user/UpSuggestion.resp";
    //版本检测
    public static final String CHECK_VERSION = BASE_URL + "user/CheckVersion.resp";

}
