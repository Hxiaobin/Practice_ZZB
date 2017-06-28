package com.sf.bocfinancialsoftware.constant;

import com.sf.bocfinancialsoftware.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 常量类
 * Created by sn on 2017/6/9.
 */

public class ConstantConfig {
    public static final String SP_NAME = "sp_name";//SP的文件名
    public static final String SP_NAME2 = "sp_name2";//SP的文件名
    public static final String USER_NAME = "userName";//用户名
    public static final String PASSWORD = "password";//密码
    public static final String LOGIN = "login";//登录
    public static final String LOGOUT = "logout";//登录
    public static final String USER = "user"; //用户信息
    public static final String TITLE = "title";
    //中银分析
    public static final String NEWS_ID = "newsId";  //新闻id
    public static final String MSG_TYPE_ID = "typeId";  //消息类型id
    public static final List<String> IMAGE_LIST = new ArrayList<String>() {{ //轮播图片集合
        add(0, R.mipmap.image_banner1 + "");
        add(1, R.mipmap.image_banner2 + "");
        add(2, R.mipmap.image_banner3 + "");
    }};
    //消息类型
    public static final String MSG_TYPE_ID_IMPORT = "1";  //进口通知
    public static final String MSG_TYPE_ID_EXPORT = "2";  //出口通知
    public static final String MSG_TYPE_ID_GUARANTEE = "3";  //保函通知
    public static final String MSG_TYPE_ID_FACTORING = "4";  //保理通知
    public static final String MSG_TYPE_ID_FORWARD = "5";  //远期通知
    //消息状态
    public static final String MSG_UN_READ = "0";  //未读
    public static final String MSG_READ = "1";  //已读
    public static final String MSG_READ_SUM = "msgReadSum";  //已读数量
    //业务查询
    public static final String BUSINESS_NAME = "businessName";  //业务类别名称
    public static final String START_DATE = "startTime";  //开始时间
    public static final String END_DATE = "endTime";  //结束时间
    public static final String CONTRACT_ID = "contractId";  //业务编号
    public static final String OPENING_AMOUNT = "open_amount";  //开证金额
    public static final String CREDIT_BALANCE = "credit_balance";  //信用证余额
    //回调
    public static final int IMPORT_REQUEST = 0x0001;  //进口通知请求码
    public static final int IMPORT_RESPONSE = 0x0002;  //进口通知返回码
    public static final int EXPORT_REQUEST = 0x0003;  //出口通知请求码
    public static final int EXPORT_RESPONSE = 0x0004;  //出口通知返回码
    public static final int GUARANTEE_REQUEST = 0x0005;  //保函通知请求码
    public static final int GUARANTEE_RESPONSE = 0x0006;  //保函通知返回码
    public static final int FACTORING_REQUEST = 0x0007;  //保理通知请求码
    public static final int FACTORING_RESPONSE = 0x0008;  //保理通知返回码
    public static final int FORWARD_REQUEST = 0x0009;  //远期通知请求码
    public static final int FORWARD_RESPONSE = 0x1000;  //远期通知返回码
    //扫一扫
    public static final int SCAN_CODE_REQUEST_MAIN = 0x1001;  //Main标题栏扫一扫请求吗
    public static final int SCAN_CODE_REQUEST_BUSINESS_QUERY = 0x1002;  //业务查询页扫一扫请求码
    public static final String DATE_PICKER_TAG = "datePicker";  //时间选择器标记
    //通知模糊查询条件
    //进口
    public static final String QUERY_IMPORT_OPEN = "进口信用证开立";
    public static final String QUERY_IMPORT_ORDER = "进口信用证到单";
    public static final String QUERY_IMPORT_SIGHT_PAYMENT = "进口信用证即期付款";
    public static final String QUERY_IMPORT_PAY_AT_MATURITY = "进口信用证承兑到期付款";
    public static final String QUERY_IMPORT_FINANCIAL_BUSINESS = "进口贸易金融业务";
    public static final String QUERY_IMPORT_INWARD_COLLECTION = "进口代收到单";
    //出口
    public static final String QUERY_EXPORT_CONDITION1 = "出口信用证开立";
    public static final String QUERY_EXPORT_CONDITION2 = "出口信用证结算";
    public static final String QUERY_EXPORT_CONDITION3 = "出口结汇";
    public static final String QUERY_EXPORT_CONDITION4 = "出口信用证承兑到期付款";
    public static final String QUERY_EXPORT_CONDITION5 = "出口贸易业务到期";
    //保函
    public static final String QUERY_GUARANTEE_CONDITION1 = "保函通知";
    public static final String QUERY_GUARANTEE_CONDITION2 = "保函收费";
    public static final String QUERY_GUARANTEE_CONDITION3 = "保函上传";
    public static final String QUERY_GUARANTEE_CONDITION4 = "融资性担保定义";
    //保理
    public static final String QUERY_FACTORING_CONDITION1 = "保理商签发收账";
    public static final String QUERY_FACTORING_CONDITION2 = "保理业务确认函";
    public static final String QUERY_FACTORING_CONDITION3 = "商业保理行业管理";
    public static final String QUERY_FACTORING_CONDITION4 = "国际保理业务";
    //远期
    public static final String QUERY_FORWARD_CONDITION1 = "远期外汇";
    public static final String QUERY_FORWARD_CONDITION2 = "外汇资金业务";
    public static final String QUERY_FORWARD_CONDITION3 = "代理结售汇业务";
    public static final String QUERY_FORWARD_CONDITION4 = "代客外汇理财业务";
    public static final String QUERY_FORWARD_CONDITION5 = "代客外汇风险管理";
    //欢迎页
    public static final String FILE_FIRST_IN = "guideFirst"; //保存是否是第一次进入app
    public static final String IS_FIRST_IN = "isFirstIn"; //是否是第一次进入app
}
