package com.sf.bocfinancialsoftware.constant;

/**
 * 网路请求URL地址
 * Created by sn on 2017/7/12.
 */

public class URLConfig {

    //中银分析列表
    public static final String BOC_ANALYSE_LIST_URL = "http://192.168.2.123/zzb/home/bocAnalyse/bocAnalyseList.resp";  //case
    //中银分析详情
    public static final String BOC_ANALYSE_NEWS_DETAIL_URL = "http://192.168.2.123/zzb/home/bocAnalyse/bocAnalyseDetail.resp";  //case
    //图片轮播
    public static final String ADVERT_LOOP_IMAGE_URL = "http://192.168.2.123/zzb/home/advertLoop/advertLoopByType.resp";  //case
    //PopupWindow筛选进口通知列表
    public static final String IMPORT_MESSAGE_LIST_URL = "http://192.168.2.123/zzb/home/message/importMessage/importMessageList.resp";  //case
    //PopupWindow筛选出口通知列表
    public static final String EXPORT_MESSAGE_LIST_URL = "http://192.168.2.123/zzb/home/message/exportMessage/exportMessageList.resp";  //case
    //PopupWindow筛选保函通知列表
    public static final String GUARANTEE_MESSAGE_LIST_URL = "http://192.168.2.123/zzb/home/message/guaranteeMessage/guaranteeMessageList.resp";  //case
    //PopupWindow筛选保理通知列表
    public static final String FACTORING_MESSAGE_LIST_URL = "http://192.168.2.123/zzb/home/message/factoringMessage/factoringMessageList.resp";  //case
    //PopupWindow筛选远期通知列表
    public static final String FORWARD_MESSAGE_LIST_URL = "http://192.168.2.123/zzb/home/message/forwardMessage/forwardMessageList.resp";  //case
    //未读消息数量
    public static final String MESSAGE_UN_READ_URL = "http://192.168.2.123/zzb/home/message/unReadMessage.resp";  //case
    //通知提醒列表
    public static final String MESSAGE_LIST_URL = "http://192.168.2.123/zzb/home/message/messageList.resp";  //case
    //业务类别 获取业务列表
    public static final String BUSINESS_TYPE_LIST_URL = "http://192.168.2.123/zzb/home/business/businessList.resp";  //case
    //根据合同编号获取合同列表
    public static final String CONTRACT_LIST_BY_CONTRACT_ID_URL = "http://192.168.2.123/zzb/home/business/businessListByContractIdWithoutBusinessId.resp";  //case
    //根据业务id+合同编号获取合同列表
    public static final String CONTRACT_LIST_BY_BUSINESS_ID_AND_CONTRACT_ID_URL = "http://192.168.2.123/zzb/home/business/businessListByContractId.resp";  //case
    //根据业务id+起止时间获取合同列表
    public static final String CONTRACT_LIST_BY_BUSINESS_ID_AND_DATE_URL = "http://192.168.2.123/zzb/home/business/businessListByData.resp";  //case
    //根据业务id+起止时间+合同编号
    public static final String CONTRACT_LIST_BY_ALL_URL = "http://192.168.2.123/zzb/home/business/businessListByAll.resp";  //case

}
