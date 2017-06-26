package com.sf.bocfinancialsoftware.constant;

/**
 * 数据库常量类
 * 说明：定义有关数据库使用到的各种常量
 *
 * @author qq724418408
 */
public class SQLiteConfig {

    //数据库建库建表
    public static final String DB_NAME = "boc_financial_software.db"; // 数据库名称
    public static final int DB_VERSION = 1; // 数据库版本
    public static final String TABLE_NAME_BOC_ANALYSE = "tb_boc_analyse"; // 中银分析列表
    public static final String TABLE_NAME_BOC_ANALYSE_DETAIL = "tb_boc_analyse_detail"; // 中银分析详情
    public static final String TABLE_NAME_MESSAGE_REMINDER = "tb_msg_reminder"; // 通知提醒列表
    public static final String TABLE_NAME_BUSINESS_TYPE = "tb_business_type"; // 业务类别列表
    public static final String TABLE_NAME_BUSINESS = "tb_business"; // 业务列表
    public static final String TABLE_NAME_CONTRACT = "tb_contract"; // 合同列表
    public static final String TABLE_NAME_PLEASANT_MESSAGE = "tb_pleasant_message"; // 温馨提示列表

    //中银分析表字段
    public static final String COLUMN_BOC_ANALYSE_NEWS_ID = "newsId"; // 新闻id
    public static final String COLUMN_BOC_ANALYSE_NEWS_TITLE = "newsTitle"; // 新闻名称
    public static final String COLUMN_BOC_ANALYSE_NEWS_DESC = "newsDesc"; // 新闻描述
    public static final String COLUMN_BOC_ANALYSE_NEWS_DATE = "newsDate"; // 新闻时间
    public static final String COLUMN_BOC_ANALYSE_NEWS_IMAGE_URL = "newsImageUrl"; // 新闻图片URL
    public static final String COLUMN_BOC_ANALYSE_NEWS_CONTENT = "newsContent"; // 新闻正文文本

    //中银分析详情
    public static final String COLUMN_BOC_ANALYSE_DETAIL_NEWS_IMAGE_ID = "imageId"; // 新闻图片id
    public static final String COLUMN_BOC_ANALYSE_DETAIL_NEWS_IMAGE_URL = "imageUrl"; // 新闻图片URL

    //通知提醒列表字段
    public static final String COLUMN_MESSAGE_REMINDER_MSG_ID = "msgId"; // 消息id
    public static final String COLUMN_MESSAGE_REMINDER_TYPE_ID = "typeId"; // 消息类型id
    public static final String COLUMN_MESSAGE_REMINDER_MSG_TITLE = "msgTitle"; // 消息标题
    public static final String COLUMN_MESSAGE_REMINDER_MSG_DATE = "msgDate"; // 消息时间
    public static final String COLUMN_MESSAGE_REMINDER_MSG_CONTENT = "msgContent"; // 消息内容
    public static final String COLUMN_MESSAGE_REMINDER_MSG_IS_READ = "msgIsRead"; // 消息是否已读
    public static final String COLUMN_MESSAGE_REMINDER_MSG_UN_READ_NUMBER = "unReadSum"; // 消息未读数量

    //业务类别列表字段
    public static final String COLUMN_BUSINESS_TYPE_ID = "typeId"; // 业务类别id
    public static final String COLUMN_BUSINESS_TYPE_NAME = "typeName"; // 业务类别名称

    //业务列表字段
    public static final String COLUMN_BUSINESS_ID = "businessId"; // 业务id
    public static final String COLUMN_BUSINESS_NAME = "businessName"; // 业务名称
    public static final String COLUMN_BUSINESS_DATE_NAME = "dateName"; // 时间名称
    public static final String COLUMN_BUSINESS_ID_NAME = "idName"; // 编号名称

    //合同列表字段
    public static final String COLUMN_CONTRACT_AUTO_ID = "contractAutoId"; // 合同id
    public static final String COLUMN_CONTRACT_ID = "contractId"; // 业务编号
    public static final String COLUMN_CONTRACT_SIGN_DATE = "signDate"; // 签订时间
    public static final String COLUMN_CONTRACT_OPENING_AMOUNT = "openingAmount"; // 开证金额
    public static final String COLUMN_CONTRACT_CREDIT_BALANCE = "creditBalance"; // 信用证余额


    //温馨提示列表字段
    public static final String COLUMN_PLEASANT_MESSAGE_MSG_ID = "msgId"; // 消息id
    public static final String COLUMN_PLEASANT_MESSAGE_MSG_TITLE = "msgTitle"; // 消息标题
    public static final String COLUMN_PLEASANT_MESSAGE_MSG_CONTENT = "msgContent"; // 消息内容
    public static final String COLUMN_PLEASANT_MESSAGE_MSG_IS_READ = "msgIsRead"; // 消息是否已读
    public static final String COLUMN_PLEASANT_MESSAGE_MSG_DATE_AND_TIME = "msgDateAndTime"; // 消息日期和时间


    // 中银分析表的列名数组
    public static final String[] TABLE_BOC_ANALYSE_COLUMNS = new String[]{COLUMN_BOC_ANALYSE_NEWS_ID, COLUMN_BOC_ANALYSE_NEWS_TITLE
            + " varchar(50),", COLUMN_BOC_ANALYSE_NEWS_DESC + " varchar(100),", COLUMN_BOC_ANALYSE_NEWS_DATE + " varchar(20),",
            COLUMN_BOC_ANALYSE_NEWS_IMAGE_URL + " varchar(50),", COLUMN_BOC_ANALYSE_NEWS_CONTENT + " varchar(255)"};

    // 中银分析详情表的列名数组
    public static final String[] TABLE_BOC_ANALYSE_DETAIL_COLUMNS = new String[]{COLUMN_BOC_ANALYSE_DETAIL_NEWS_IMAGE_ID,
            COLUMN_BOC_ANALYSE_NEWS_ID + " varchar(50),", COLUMN_BOC_ANALYSE_DETAIL_NEWS_IMAGE_URL + " varchar(50)"};

    // 通知提醒表的列名数组
    public static final String[] TABLE_MESSAGE_REMINDER_COLUMNS = new String[]{COLUMN_MESSAGE_REMINDER_MSG_ID, COLUMN_MESSAGE_REMINDER_TYPE_ID
            + " varchar(20),", COLUMN_MESSAGE_REMINDER_MSG_TITLE + " varchar(50),", COLUMN_MESSAGE_REMINDER_MSG_DATE + " varchar(20),",
            COLUMN_MESSAGE_REMINDER_MSG_CONTENT + " varchar(255),", COLUMN_MESSAGE_REMINDER_MSG_IS_READ + " varchar(20),",
            COLUMN_MESSAGE_REMINDER_MSG_UN_READ_NUMBER + " varchar(20)"};

    // 业务类别表的列名数组
    public static final String[] TABLE_BUSINESS_TYPE_COLUMNS = new String[]{COLUMN_BUSINESS_TYPE_ID, COLUMN_BUSINESS_TYPE_NAME + " varchar(50)"};

    // 业务表的列名数组
    public static final String[] TABLE_BUSINESS_COLUMNS = new String[]{COLUMN_BUSINESS_ID, COLUMN_BUSINESS_TYPE_NAME + " varchar(50),",
            COLUMN_BUSINESS_NAME + " varchar(100),", COLUMN_BUSINESS_DATE_NAME + " DATE,", COLUMN_BUSINESS_ID_NAME + " varchar(50)"};

    // 合同表的列名数组
    public static final String[] TABLE_CONTRACT_COLUMNS = new String[]{COLUMN_CONTRACT_AUTO_ID, COLUMN_BUSINESS_NAME + " varchar(100),",
            COLUMN_CONTRACT_ID + " varchar(50),", COLUMN_CONTRACT_SIGN_DATE + " DATE,", COLUMN_CONTRACT_OPENING_AMOUNT + " varchar(50),",
            COLUMN_CONTRACT_CREDIT_BALANCE + " varchar(50)"};

    // 温馨提示表的列名数组
    public static final String[] TABLE_PLEASANT_MESSAGE_COLUMNS = new String[]{COLUMN_PLEASANT_MESSAGE_MSG_ID, COLUMN_PLEASANT_MESSAGE_MSG_TITLE
            + " varchar(50),", COLUMN_PLEASANT_MESSAGE_MSG_CONTENT + " varchar(255),", COLUMN_PLEASANT_MESSAGE_MSG_IS_READ + " varchar(20),",
            COLUMN_PLEASANT_MESSAGE_MSG_DATE_AND_TIME + " varchar(50)"};

}
