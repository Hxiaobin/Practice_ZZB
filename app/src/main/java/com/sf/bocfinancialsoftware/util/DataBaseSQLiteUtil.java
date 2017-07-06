package com.sf.bocfinancialsoftware.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;

import com.sf.bocfinancialsoftware.base.BaseApplication;
import com.sf.bocfinancialsoftware.bean.BocAnalyseBean;
import com.sf.bocfinancialsoftware.bean.BusinessBean;
import com.sf.bocfinancialsoftware.bean.BusinessTypeBean;
import com.sf.bocfinancialsoftware.bean.ContractBean;
import com.sf.bocfinancialsoftware.bean.MessageReminderBean;
import com.sf.bocfinancialsoftware.bean.PleasantMessageBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.sf.bocfinancialsoftware.constant.ConstantConfig.CREDIT_BALANCE;
import static com.sf.bocfinancialsoftware.constant.ConstantConfig.OPENING_AMOUNT;
import static com.sf.bocfinancialsoftware.constant.SQLiteConfig.COLUMN_BOC_ANALYSE_DETAIL_NEWS_IMAGE_URL;
import static com.sf.bocfinancialsoftware.constant.SQLiteConfig.COLUMN_BOC_ANALYSE_NEWS_CONTENT;
import static com.sf.bocfinancialsoftware.constant.SQLiteConfig.COLUMN_BOC_ANALYSE_NEWS_DATE;
import static com.sf.bocfinancialsoftware.constant.SQLiteConfig.COLUMN_BOC_ANALYSE_NEWS_DESC;
import static com.sf.bocfinancialsoftware.constant.SQLiteConfig.COLUMN_BOC_ANALYSE_NEWS_ID;
import static com.sf.bocfinancialsoftware.constant.SQLiteConfig.COLUMN_BOC_ANALYSE_NEWS_IMAGE_URL;
import static com.sf.bocfinancialsoftware.constant.SQLiteConfig.COLUMN_BOC_ANALYSE_NEWS_TITLE;
import static com.sf.bocfinancialsoftware.constant.SQLiteConfig.COLUMN_BUSINESS_DATE_NAME;
import static com.sf.bocfinancialsoftware.constant.SQLiteConfig.COLUMN_BUSINESS_ID_NAME;
import static com.sf.bocfinancialsoftware.constant.SQLiteConfig.COLUMN_BUSINESS_NAME;
import static com.sf.bocfinancialsoftware.constant.SQLiteConfig.COLUMN_BUSINESS_TYPE_NAME;
import static com.sf.bocfinancialsoftware.constant.SQLiteConfig.COLUMN_CONTRACT_AUTO_ID;
import static com.sf.bocfinancialsoftware.constant.SQLiteConfig.COLUMN_CONTRACT_CREDIT_BALANCE;
import static com.sf.bocfinancialsoftware.constant.SQLiteConfig.COLUMN_CONTRACT_ID;
import static com.sf.bocfinancialsoftware.constant.SQLiteConfig.COLUMN_CONTRACT_OPENING_AMOUNT;
import static com.sf.bocfinancialsoftware.constant.SQLiteConfig.COLUMN_CONTRACT_SIGN_DATE;
import static com.sf.bocfinancialsoftware.constant.SQLiteConfig.COLUMN_MESSAGE_REMINDER_MSG_CONTENT;
import static com.sf.bocfinancialsoftware.constant.SQLiteConfig.COLUMN_MESSAGE_REMINDER_MSG_DATE;
import static com.sf.bocfinancialsoftware.constant.SQLiteConfig.COLUMN_MESSAGE_REMINDER_MSG_ID;
import static com.sf.bocfinancialsoftware.constant.SQLiteConfig.COLUMN_MESSAGE_REMINDER_MSG_IS_READ;
import static com.sf.bocfinancialsoftware.constant.SQLiteConfig.COLUMN_MESSAGE_REMINDER_MSG_TITLE;
import static com.sf.bocfinancialsoftware.constant.SQLiteConfig.COLUMN_MESSAGE_REMINDER_MSG_UN_READ_NUMBER;
import static com.sf.bocfinancialsoftware.constant.SQLiteConfig.COLUMN_MESSAGE_REMINDER_TYPE_ID;
import static com.sf.bocfinancialsoftware.constant.SQLiteConfig.COLUMN_PLEASANT_MESSAGE_MSG_CONTENT;
import static com.sf.bocfinancialsoftware.constant.SQLiteConfig.COLUMN_PLEASANT_MESSAGE_MSG_DATE_AND_TIME;
import static com.sf.bocfinancialsoftware.constant.SQLiteConfig.COLUMN_PLEASANT_MESSAGE_MSG_ID;
import static com.sf.bocfinancialsoftware.constant.SQLiteConfig.COLUMN_PLEASANT_MESSAGE_MSG_IS_READ;
import static com.sf.bocfinancialsoftware.constant.SQLiteConfig.COLUMN_PLEASANT_MESSAGE_MSG_TITLE;
import static com.sf.bocfinancialsoftware.constant.SQLiteConfig.DB_NAME;
import static com.sf.bocfinancialsoftware.constant.SQLiteConfig.DB_VERSION;
import static com.sf.bocfinancialsoftware.constant.SQLiteConfig.TABLE_BOC_ANALYSE_COLUMNS;
import static com.sf.bocfinancialsoftware.constant.SQLiteConfig.TABLE_BOC_ANALYSE_DETAIL_COLUMNS;
import static com.sf.bocfinancialsoftware.constant.SQLiteConfig.TABLE_BUSINESS_COLUMNS;
import static com.sf.bocfinancialsoftware.constant.SQLiteConfig.TABLE_BUSINESS_TYPE_COLUMNS;
import static com.sf.bocfinancialsoftware.constant.SQLiteConfig.TABLE_CONTRACT_COLUMNS;
import static com.sf.bocfinancialsoftware.constant.SQLiteConfig.TABLE_MESSAGE_REMINDER_COLUMNS;
import static com.sf.bocfinancialsoftware.constant.SQLiteConfig.TABLE_NAME_BOC_ANALYSE;
import static com.sf.bocfinancialsoftware.constant.SQLiteConfig.TABLE_NAME_BOC_ANALYSE_DETAIL;
import static com.sf.bocfinancialsoftware.constant.SQLiteConfig.TABLE_NAME_BUSINESS;
import static com.sf.bocfinancialsoftware.constant.SQLiteConfig.TABLE_NAME_BUSINESS_TYPE;
import static com.sf.bocfinancialsoftware.constant.SQLiteConfig.TABLE_NAME_CONTRACT;
import static com.sf.bocfinancialsoftware.constant.SQLiteConfig.TABLE_NAME_MESSAGE_REMINDER;
import static com.sf.bocfinancialsoftware.constant.SQLiteConfig.TABLE_NAME_PLEASANT_MESSAGE;
import static com.sf.bocfinancialsoftware.constant.SQLiteConfig.TABLE_PLEASANT_MESSAGE_COLUMNS;

/**
 * 本地数据库操作util
 * Created by sn on 2017/6/9.
 */
public class DataBaseSQLiteUtil {

    private static SQLiteDatabase mDatabase;
    private static Context mContext = BaseApplication.getInstance();
    private static ContactDBOpenHelper mDbOpenHelper; // 数据库帮助类

    /**
     * 数据表打开帮助类
     */
    private static class ContactDBOpenHelper extends SQLiteOpenHelper {

        public ContactDBOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            String sql1 = createTable(TABLE_NAME_BOC_ANALYSE, TABLE_BOC_ANALYSE_COLUMNS);
            db.execSQL(sql1); // 创建中银分析表
            String sql2 = createTable(TABLE_NAME_BOC_ANALYSE_DETAIL, TABLE_BOC_ANALYSE_DETAIL_COLUMNS);
            db.execSQL(sql2); // 创建中银分析详情表
            String sql3 = createTable(TABLE_NAME_MESSAGE_REMINDER, TABLE_MESSAGE_REMINDER_COLUMNS);
            db.execSQL(sql3); // 创建通知提醒表
            String sql4 = createTable(TABLE_NAME_BUSINESS_TYPE, TABLE_BUSINESS_TYPE_COLUMNS);
            db.execSQL(sql4); // 创建业务类型表表
            String sql5 = createTable(TABLE_NAME_BUSINESS, TABLE_BUSINESS_COLUMNS);
            db.execSQL(sql5); // 创建业务表
            String sql6 = createTable(TABLE_NAME_CONTRACT, TABLE_CONTRACT_COLUMNS);
            db.execSQL(sql6); // 创建合同表
            String sql7 = createTable(TABLE_NAME_PLEASANT_MESSAGE, TABLE_PLEASANT_MESSAGE_COLUMNS);
            db.execSQL(sql7); // 创建合同表
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        }
    }

    /**
     * 实例化数据库对象
     */
    public static void openDataBase() {
        mDbOpenHelper = new ContactDBOpenHelper(mContext, DB_NAME, null, DB_VERSION);
        try {
            mDatabase = mDbOpenHelper.getWritableDatabase(); // 获取可写数据库
        } catch (SQLException e) {
            mDatabase = mDbOpenHelper.getReadableDatabase(); // 获取只读数据库
        }
    }

    /**
     * 记得关闭数据库
     */
    public static void closeDataBase() {
        if (mDatabase != null) {
            mDatabase.close();
        }
    }

    /**
     * 创建表的方法
     *
     * @param tableName 表名
     * @param columns   列名数组：new String[]{"id", "name varchar(20),", "phone varchar(15)"}
     * @return 创建表的数据库语句
     */
    public static String createTable(String tableName, String... columns) {
        StringBuffer sb = new StringBuffer();
        sb.append("create table if not exists " + tableName + " ("
                + columns[0] + " integer primary key autoincrement, ");
        for (int i = 1; i < columns.length; i++) {
            sb.append(" " + columns[i] + " ");
        }
        sb.append(");");
        return sb.toString();
    }

    /**
     * 向中银分析列表插入数据
     *
     * @param bean 中银分析Bean对象
     * @return
     */
    public static long insertToBocAnalyse(BocAnalyseBean bean) {
        openDataBase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_BOC_ANALYSE_NEWS_TITLE, bean.getNewsTitle()); // 新闻名称
        values.put(COLUMN_BOC_ANALYSE_NEWS_DESC, bean.getNewsDesc()); // 新闻描述
        values.put(COLUMN_BOC_ANALYSE_NEWS_DATE, bean.getNewsData()); // 新闻时间
        values.put(COLUMN_BOC_ANALYSE_NEWS_IMAGE_URL, bean.getNewsImageUrl()); // 新闻图片url
        values.put(COLUMN_BOC_ANALYSE_NEWS_CONTENT, bean.getHtmlContent()); // 新闻正文文本
        long insert = mDatabase.insert(TABLE_NAME_BOC_ANALYSE, null, values);
        bean.setNewsId(insert + "");
        List<String> imageList = bean.getImageList();
        for (String CarouselImageUrl : imageList) {
            bean.setCarouseImageUrl(CarouselImageUrl);
            insertToBocAnalyseDetail(bean);  //插入轮播图片
        }
        closeDataBase();
        return insert; // 插入中银分析表
    }

    /**
     * 插入中银分析详情图片
     *
     * @param bean 中银分析Bean对象
     * @return
     */
    public static long insertToBocAnalyseDetail(BocAnalyseBean bean) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_BOC_ANALYSE_NEWS_ID, bean.getNewsId()); // 新闻id
        values.put(COLUMN_BOC_ANALYSE_DETAIL_NEWS_IMAGE_URL, bean.getCarouseImageUrl()); // 图片id
        long insert = mDatabase.insert(TABLE_NAME_BOC_ANALYSE_DETAIL, null, values);
        return insert; // 插入中银分析表
    }

    /**
     * 获取中银分析列表
     *
     * @return
     */
    public static List<BocAnalyseBean> queryBocAnalyseList() {
        openDataBase();
        String[] projection = {COLUMN_BOC_ANALYSE_NEWS_ID, COLUMN_BOC_ANALYSE_NEWS_TITLE, COLUMN_BOC_ANALYSE_NEWS_DESC, COLUMN_BOC_ANALYSE_NEWS_DATE
                , COLUMN_BOC_ANALYSE_NEWS_IMAGE_URL}; //
        Cursor cursor = mDatabase.query(TABLE_NAME_BOC_ANALYSE, projection, null,
                null, null, null, null);
        List<BocAnalyseBean> list = new ArrayList<>();
        while (cursor.moveToNext()) {
            BocAnalyseBean bean = new BocAnalyseBean();
            bean.setNewsId(String.valueOf(cursor.getInt(cursor.getColumnIndex(COLUMN_BOC_ANALYSE_NEWS_ID)))); //新闻id
            bean.setNewsTitle(cursor.getString(cursor.getColumnIndex(COLUMN_BOC_ANALYSE_NEWS_TITLE)));  //新闻名称
            bean.setNewsDesc(cursor.getString(cursor.getColumnIndex(COLUMN_BOC_ANALYSE_NEWS_DESC)));  //新闻描述
            bean.setNewsData(cursor.getString(cursor.getColumnIndex(COLUMN_BOC_ANALYSE_NEWS_DATE)));  //新闻时间
            bean.setNewsImageUrl(cursor.getString(cursor.getColumnIndex(COLUMN_BOC_ANALYSE_NEWS_IMAGE_URL)));  //新闻列表的图片
            list.add(bean);
        }
        cursor.close();
        closeDataBase();
        return list;
    }

    /**
     * 分页查询中银分析列表
     *
     * @param page  页码
     * @param count 每页查询数据条数
     * @return
     */
    public static List<BocAnalyseBean> queryBocAnalyseList(int page, int count) {
        openDataBase();
        String sql = "select * from " + TABLE_NAME_BOC_ANALYSE + " limit " + count + " offset " + page * count + ";";
        Cursor cursor = mDatabase.rawQuery(sql, null);
        List<BocAnalyseBean> list = new ArrayList<>();
        while (cursor.moveToNext()) {
            BocAnalyseBean bean = new BocAnalyseBean();
            bean.setNewsId(String.valueOf(cursor.getInt(cursor.getColumnIndex(COLUMN_BOC_ANALYSE_NEWS_ID)))); //新闻Id
            bean.setNewsTitle(cursor.getString(cursor.getColumnIndex(COLUMN_BOC_ANALYSE_NEWS_TITLE)));  //新闻名称
            bean.setNewsDesc(cursor.getString(cursor.getColumnIndex(COLUMN_BOC_ANALYSE_NEWS_DESC)));  //新闻描述
            bean.setNewsData(cursor.getString(cursor.getColumnIndex(COLUMN_BOC_ANALYSE_NEWS_DATE)));  //新闻时间
            bean.setNewsImageUrl(cursor.getString(cursor.getColumnIndex(COLUMN_BOC_ANALYSE_NEWS_IMAGE_URL)));  //新闻列表的图片
            list.add(bean);
        }
        cursor.close();
        closeDataBase();
        return list;
    }

    /**
     * 通过新闻id查询中银分析详情
     *
     * @param id 新闻id
     * @return
     */
    public static BocAnalyseBean getBocAnalyseById(String id) {
        openDataBase();
        String[] projection = {COLUMN_BOC_ANALYSE_NEWS_ID, COLUMN_BOC_ANALYSE_NEWS_TITLE, COLUMN_BOC_ANALYSE_NEWS_DATE, COLUMN_BOC_ANALYSE_NEWS_CONTENT}; //
        Cursor cursor = mDatabase.query(TABLE_NAME_BOC_ANALYSE, projection, COLUMN_BOC_ANALYSE_NEWS_ID + "= ?",
                new String[]{id}, null, null, null);
        BocAnalyseBean bocAnalyseBean = new BocAnalyseBean();
        while (cursor.moveToNext()) {
            bocAnalyseBean.setNewsId(String.valueOf(cursor.getInt(cursor.getColumnIndex(COLUMN_BOC_ANALYSE_NEWS_ID))));  //新闻id
            bocAnalyseBean.setNewsTitle(cursor.getString(cursor.getColumnIndex(COLUMN_BOC_ANALYSE_NEWS_TITLE)));  // 新闻名称
            bocAnalyseBean.setNewsData(cursor.getString(cursor.getColumnIndex(COLUMN_BOC_ANALYSE_NEWS_DATE)));  // 新闻时间
            bocAnalyseBean.setHtmlContent(cursor.getString(cursor.getColumnIndex(COLUMN_BOC_ANALYSE_NEWS_CONTENT)));  // 新闻正文文本
            bocAnalyseBean.setImageList(getBocAnalyseImageListById(id));
        }
        cursor.close();
        closeDataBase();
        return bocAnalyseBean;
    }

    /**
     * 通过新闻id查询中银分析详情的图片轮播
     *
     * @param id 新闻id
     * @return
     */
    public static List<String> getBocAnalyseImageListById(String id) {
        openDataBase();
        String[] projection = {COLUMN_BOC_ANALYSE_DETAIL_NEWS_IMAGE_URL};
        Cursor cursor = mDatabase.query(TABLE_NAME_BOC_ANALYSE_DETAIL, projection, COLUMN_BOC_ANALYSE_NEWS_ID + "= ?",
                new String[]{id}, null, null, null);
        List<String> imageList = new ArrayList<>();
        while (cursor.moveToNext()) {
            imageList.add(cursor.getString(cursor.getColumnIndex(COLUMN_BOC_ANALYSE_DETAIL_NEWS_IMAGE_URL)));  //新闻详情图片集合
        }
        cursor.close();
        closeDataBase();
        return imageList;
    }

    /**
     * 向通知提醒列表插入数据
     *
     * @param bean 通知Bean对象
     * @return
     */
    public static long insertToMessageReminder(MessageReminderBean bean) {
        openDataBase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_MESSAGE_REMINDER_TYPE_ID, bean.getTypeId());  // 消息类型
        values.put(COLUMN_MESSAGE_REMINDER_MSG_TITLE, bean.getMsgTitle());  // 消息标题
        values.put(COLUMN_MESSAGE_REMINDER_MSG_DATE, bean.getMsgDate());  // 消息时间
        values.put(COLUMN_MESSAGE_REMINDER_MSG_CONTENT, bean.getMsgContent());  // 消息内容
        values.put(COLUMN_MESSAGE_REMINDER_MSG_IS_READ, bean.getMsgIsRead());  // 消息是否已读
        long insert = mDatabase.insert(TABLE_NAME_MESSAGE_REMINDER, null, values);
        closeDataBase();
        return insert; // 插入中银分析表
    }

    /**
     * 更新消息是否已读的状态
     *
     * @param bean 通知Bean对象
     * @return
     */
    public static int updateMessageReminder(MessageReminderBean bean) {
        openDataBase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_MESSAGE_REMINDER_MSG_IS_READ, bean.getMsgIsRead());  // 消息是否已读
        int update = mDatabase.update(TABLE_NAME_MESSAGE_REMINDER, values, COLUMN_MESSAGE_REMINDER_MSG_ID + "= ?", new String[]{bean.getMsgId()});
        closeDataBase();
        return update;
    }

    /**
     * 获取通知提醒列表中所有的数据
     *
     * @return
     */
    public static List<MessageReminderBean> queryAllMessageReminderList() {
        openDataBase();
        String[] projection = {COLUMN_MESSAGE_REMINDER_MSG_ID, COLUMN_MESSAGE_REMINDER_TYPE_ID, COLUMN_MESSAGE_REMINDER_MSG_TITLE,
                COLUMN_MESSAGE_REMINDER_MSG_DATE, COLUMN_MESSAGE_REMINDER_MSG_CONTENT, COLUMN_MESSAGE_REMINDER_MSG_IS_READ,
                COLUMN_MESSAGE_REMINDER_MSG_UN_READ_NUMBER};
        Cursor cursor = mDatabase.query(TABLE_NAME_MESSAGE_REMINDER, projection, null,
                null, null, null, null);
        List<MessageReminderBean> list = new ArrayList<>();
        while (cursor.moveToNext()) {
            MessageReminderBean bean = new MessageReminderBean();
            bean.setMsgId(String.valueOf(cursor.getInt(cursor.getColumnIndex(COLUMN_MESSAGE_REMINDER_MSG_ID))));  //消息id
            bean.setTypeId(cursor.getString(cursor.getColumnIndex(COLUMN_MESSAGE_REMINDER_TYPE_ID)));  //消息类型id
            bean.setMsgTitle(cursor.getString(cursor.getColumnIndex(COLUMN_MESSAGE_REMINDER_MSG_TITLE)));  //消息标题
            bean.setMsgDate(cursor.getString(cursor.getColumnIndex(COLUMN_MESSAGE_REMINDER_MSG_DATE)));  //消息时间
            bean.setMsgContent(cursor.getString(cursor.getColumnIndex(COLUMN_MESSAGE_REMINDER_MSG_CONTENT)));  //消息内容
            bean.setMsgIsRead(cursor.getString(cursor.getColumnIndex(COLUMN_MESSAGE_REMINDER_MSG_IS_READ)));  //消息是否已读
            bean.setUnReadSum(cursor.getString(cursor.getColumnIndex(COLUMN_MESSAGE_REMINDER_MSG_UN_READ_NUMBER))); //未读消息数量
            list.add(bean);
        }
        cursor.close();
        closeDataBase();
        return list;
    }

    /**
     * PopupWindow筛选通知，获取全部该类通知
     *
     * @param typeId 通知类型id
     * @param title  筛选条件
     * @return
     */
    public static List<MessageReminderBean> queryAllMessageByTypeAndTitle(String typeId, String title) {
        openDataBase();
        String[] projection;
        Cursor cursor;
        if (TextUtils.isEmpty(title)) { //筛选条件为空
            projection = new String[]{COLUMN_MESSAGE_REMINDER_MSG_ID, COLUMN_MESSAGE_REMINDER_MSG_TITLE,
                    COLUMN_MESSAGE_REMINDER_MSG_DATE, COLUMN_MESSAGE_REMINDER_MSG_CONTENT, COLUMN_MESSAGE_REMINDER_MSG_IS_READ};
            cursor = mDatabase.query(TABLE_NAME_MESSAGE_REMINDER, projection, COLUMN_MESSAGE_REMINDER_TYPE_ID + "= ?",
                    new String[]{typeId}, null, null, null, null);
        } else { //筛选条件不为空
            projection = new String[]{COLUMN_MESSAGE_REMINDER_MSG_ID, COLUMN_MESSAGE_REMINDER_MSG_TITLE,
                    COLUMN_MESSAGE_REMINDER_MSG_DATE, COLUMN_MESSAGE_REMINDER_MSG_CONTENT, COLUMN_MESSAGE_REMINDER_MSG_IS_READ};
            cursor = mDatabase.query(TABLE_NAME_MESSAGE_REMINDER, projection, COLUMN_MESSAGE_REMINDER_TYPE_ID + "= ? and " + COLUMN_MESSAGE_REMINDER_MSG_TITLE + " like ?",
                    new String[]{typeId, "%" + title + "%"}, null, null, null);
        }
        List<MessageReminderBean> list = new ArrayList<>();
        while (cursor.moveToNext()) {
            MessageReminderBean bean = new MessageReminderBean();
            bean.setMsgId(String.valueOf(cursor.getInt(cursor.getColumnIndex(COLUMN_MESSAGE_REMINDER_MSG_ID))));  //消息id
            bean.setMsgTitle(cursor.getString(cursor.getColumnIndex(COLUMN_MESSAGE_REMINDER_MSG_TITLE)));  //消息标题
            bean.setMsgDate(cursor.getString(cursor.getColumnIndex(COLUMN_MESSAGE_REMINDER_MSG_DATE)));  //消息时间
            bean.setMsgContent(cursor.getString(cursor.getColumnIndex(COLUMN_MESSAGE_REMINDER_MSG_CONTENT))); //消息内容
            bean.setMsgIsRead(cursor.getString(cursor.getColumnIndex(COLUMN_MESSAGE_REMINDER_MSG_IS_READ)));  //消息是否已读
            list.add(bean);
        }
        cursor.close();
        closeDataBase();
        return list;
    }

    /**
     * 通过消息typeId和筛选条件，分页查询通知提醒列表
     *
     * @param typeId 通知类型Id
     * @param title  筛选条件
     * @param page   页码
     * @param count  每页查询数据条数
     * @return
     */
    public static List<MessageReminderBean> queryMessageByTypeAndTitle(String typeId, String title, int page, int count) {
        openDataBase();
        String sql;
        if (TextUtils.isEmpty(title)) {
            sql = "select * from " + TABLE_NAME_MESSAGE_REMINDER + " where " + COLUMN_MESSAGE_REMINDER_TYPE_ID + " = " + typeId + " limit " + count + " offset " + page * count + ";";
        } else {
            sql = "select * from " + TABLE_NAME_MESSAGE_REMINDER + " where " + COLUMN_MESSAGE_REMINDER_TYPE_ID + " = " + typeId +
                    " and " + COLUMN_MESSAGE_REMINDER_MSG_TITLE + " like '%" + title + "%'" +
                    " limit " + count + " offset " + page * count + ";";
        }
        Cursor cursor = mDatabase.rawQuery(sql, null);
        List<MessageReminderBean> list = new ArrayList<>();
        while (cursor.moveToNext()) {
            MessageReminderBean bean = new MessageReminderBean();
            bean.setMsgId(String.valueOf(cursor.getInt(cursor.getColumnIndex(COLUMN_MESSAGE_REMINDER_MSG_ID))));  //消息id
            bean.setMsgTitle(cursor.getString(cursor.getColumnIndex(COLUMN_MESSAGE_REMINDER_MSG_TITLE)));  //消息标题
            bean.setMsgDate(cursor.getString(cursor.getColumnIndex(COLUMN_MESSAGE_REMINDER_MSG_DATE)));  //消息时间
            bean.setMsgContent(cursor.getString(cursor.getColumnIndex(COLUMN_MESSAGE_REMINDER_MSG_CONTENT)));  //消息内容
            bean.setMsgIsRead(cursor.getString(cursor.getColumnIndex(COLUMN_MESSAGE_REMINDER_MSG_IS_READ)));  //消息是否已读
            list.add(bean);
        }
        cursor.close();
        closeDataBase();
        return list;
    }

    /**
     * 向业务类别表中插入数据
     *
     * @param bean 业务类型Bean对象
     * @return
     */
    public static long insertToBusinessType(BusinessTypeBean bean) {
        openDataBase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_BUSINESS_TYPE_NAME, bean.getTypeName());  // 业务类别名称
        long insert = mDatabase.insert(TABLE_NAME_BUSINESS_TYPE, null, values);
        closeDataBase();
        return insert; // 插入中银分析表
    }

    /**
     * 向业务表中插入数据
     *
     * @param bean 通业务Bean对象
     * @return
     */
    public static long insertToBusiness(BusinessBean bean) {
        openDataBase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_BUSINESS_TYPE_NAME, bean.getTypeName());  // 业务类型
        values.put(COLUMN_BUSINESS_NAME, bean.getBusinessName());  // 业务名称
        values.put(COLUMN_BUSINESS_DATE_NAME, bean.getDateName());  // 时间名称
        values.put(COLUMN_BUSINESS_ID_NAME, bean.getIdName());  // 编号名称
        long insert = mDatabase.insert(TABLE_NAME_BUSINESS, null, values);
        closeDataBase();
        return insert; // 插入中银分析表
    }

    /**
     * 向合同表中插入数据
     *
     * @param bean 合同bean对象
     * @return
     */
    public static long insertToContract(ContractBean bean) {
        openDataBase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_CONTRACT_ID, bean.getContractId());  // 业务编号
        values.put(COLUMN_BUSINESS_NAME, bean.getBusinessName());  // 业务名称
        values.put(COLUMN_CONTRACT_SIGN_DATE, bean.getSignDate());  // 签订时间
        values.put(COLUMN_CONTRACT_OPENING_AMOUNT, (String) bean.getMapObject().get(OPENING_AMOUNT));  // 开证金额
        values.put(COLUMN_CONTRACT_CREDIT_BALANCE, (String) bean.getMapObject().get(CREDIT_BALANCE));  // 信用证余额
        long insert = mDatabase.insert(TABLE_NAME_CONTRACT, null, values);
        closeDataBase();
        return insert; // 插入中银分析表
    }

    /**
     * 获取合同列表中所有的数据
     *
     * @return
     */
    public static List<ContractBean> queryAllContractList() {
        openDataBase();
        String[] projection = {COLUMN_CONTRACT_AUTO_ID, COLUMN_BUSINESS_NAME, COLUMN_CONTRACT_ID, COLUMN_CONTRACT_SIGN_DATE,
                COLUMN_CONTRACT_OPENING_AMOUNT, COLUMN_CONTRACT_CREDIT_BALANCE};
        Cursor cursor = mDatabase.query(TABLE_NAME_CONTRACT, projection, null,
                null, null, null, null);
        List<ContractBean> list = new ArrayList<>();
        while (cursor.moveToNext()) {
            ContractBean bean = new ContractBean();
            bean.setAutoId(String.valueOf(cursor.getInt(cursor.getColumnIndex(COLUMN_CONTRACT_AUTO_ID)))); //业务id
            bean.setBusinessName(cursor.getString(cursor.getColumnIndex(COLUMN_BUSINESS_NAME)));  //业务名称
            bean.setContractId(cursor.getString(cursor.getColumnIndex(COLUMN_CONTRACT_ID)));   //业务编号
            bean.setSignDate(cursor.getString(cursor.getColumnIndex(COLUMN_CONTRACT_SIGN_DATE)));  //合同签订时间
            HashMap map = new HashMap();
            map.put(OPENING_AMOUNT, cursor.getString(cursor.getColumnIndex(COLUMN_CONTRACT_OPENING_AMOUNT))); //开证金额
            map.put(CREDIT_BALANCE, cursor.getString(cursor.getColumnIndex(COLUMN_CONTRACT_CREDIT_BALANCE)));  //信用证余额
            bean.setMapObject(map);
            list.add(bean);
        }
        cursor.close();
        closeDataBase();
        return list;
    }

    /**
     * 根据不同的条件，分页查询业务
     *
     * @param businessName 业务名称
     * @param startDate    开始时间
     * @param endDate      结束时间
     * @param contractId   业务编号
     * @param page         页码
     * @param count        每页查询数据条数
     * @return
     */
    public static List<ContractBean> queryContractList(String businessName, String startDate, String endDate, String contractId, int page, int count) {
        openDataBase();
        String[] projection;
        Cursor cursor = null;
        if (!TextUtils.isEmpty(startDate) && !TextUtils.isEmpty(endDate) && TextUtils.isEmpty(contractId)) { //通过开始时间+结束时间查询
            projection = new String[]{COLUMN_CONTRACT_AUTO_ID, COLUMN_BUSINESS_NAME, COLUMN_CONTRACT_ID, COLUMN_CONTRACT_OPENING_AMOUNT, COLUMN_CONTRACT_CREDIT_BALANCE};
            cursor = mDatabase.query(TABLE_NAME_CONTRACT, projection, COLUMN_BUSINESS_NAME + " = ? and " + COLUMN_CONTRACT_SIGN_DATE + " >= ? and " + COLUMN_CONTRACT_SIGN_DATE + " <= ?",
                    new String[]{businessName, startDate, endDate}, null, null, null, page + "," + count);
        } else if (!TextUtils.isEmpty(contractId) && (TextUtils.isEmpty(startDate) || TextUtils.isEmpty(endDate))) {//通过业务编号查询
            projection = new String[]{COLUMN_CONTRACT_AUTO_ID, COLUMN_BUSINESS_NAME, COLUMN_CONTRACT_ID, COLUMN_CONTRACT_OPENING_AMOUNT, COLUMN_CONTRACT_CREDIT_BALANCE};
            cursor = mDatabase.query(TABLE_NAME_CONTRACT, projection, COLUMN_BUSINESS_NAME + " = ? and " + COLUMN_CONTRACT_ID + " = ?",
                    new String[]{businessName, contractId}, null, null, null, page + "," + count);
        } else if (!TextUtils.isEmpty(startDate) && !TextUtils.isEmpty(endDate) && !TextUtils.isEmpty(contractId)) { //通过三者查询
            projection = new String[]{COLUMN_CONTRACT_AUTO_ID, COLUMN_BUSINESS_NAME, COLUMN_CONTRACT_ID, COLUMN_CONTRACT_OPENING_AMOUNT, COLUMN_CONTRACT_CREDIT_BALANCE};
            cursor = mDatabase.query(TABLE_NAME_CONTRACT, projection, COLUMN_BUSINESS_NAME + " = ? and " + COLUMN_CONTRACT_SIGN_DATE + " >= ? and " + COLUMN_CONTRACT_SIGN_DATE + " <= ? and " + COLUMN_CONTRACT_ID + " = ?",
                    new String[]{businessName, startDate, endDate, contractId}, null, null, null, page + "," + count);
        }
        List<ContractBean> list = new ArrayList<>();
        while (cursor.moveToNext()) {
            ContractBean bean = new ContractBean();
            bean.setAutoId(String.valueOf(cursor.getInt(cursor.getColumnIndex(COLUMN_CONTRACT_AUTO_ID)))); //业务id
            bean.setBusinessName(cursor.getString(cursor.getColumnIndex(COLUMN_BUSINESS_NAME)));  //业务名称
            bean.setContractId(cursor.getString(cursor.getColumnIndex(COLUMN_CONTRACT_ID)));  //业务编号
            HashMap map = new HashMap();
            map.put(OPENING_AMOUNT, cursor.getString(cursor.getColumnIndex(COLUMN_CONTRACT_OPENING_AMOUNT)));  //开证金额
            map.put(CREDIT_BALANCE, cursor.getString(cursor.getColumnIndex(COLUMN_CONTRACT_CREDIT_BALANCE)));  //信用证余额
            bean.setMapObject(map);
            list.add(bean);
        }
        cursor.close();
        closeDataBase();
        return list;
    }

    /**
     * 通过业务编号分页查询合同列表
     *
     * @param contractId 业务编号
     * @param page       页码
     * @param count      每页查询数据条数
     * @return
     */
    public static List<ContractBean> queryContractByContractId(String contractId, int page, int count) {
        openDataBase();
        String[] projection = {COLUMN_CONTRACT_AUTO_ID, COLUMN_CONTRACT_ID, COLUMN_CONTRACT_OPENING_AMOUNT, COLUMN_CONTRACT_CREDIT_BALANCE};
        Cursor cursor = mDatabase.query(TABLE_NAME_CONTRACT, projection, COLUMN_CONTRACT_ID + " = ?",
                new String[]{contractId}, null, null, null, page + "," + count);
        List<ContractBean> list = new ArrayList<>();
        while (cursor.moveToNext()) {
            ContractBean bean = new ContractBean();
            bean.setAutoId(String.valueOf(cursor.getInt(cursor.getColumnIndex(COLUMN_CONTRACT_AUTO_ID))));  //业务id
            bean.setContractId(cursor.getString(cursor.getColumnIndex(COLUMN_CONTRACT_ID)));  //业务编号
            HashMap map = new HashMap();
            map.put(OPENING_AMOUNT, cursor.getString(cursor.getColumnIndex(COLUMN_CONTRACT_OPENING_AMOUNT)));  //开证金额
            map.put(CREDIT_BALANCE, cursor.getString(cursor.getColumnIndex(COLUMN_CONTRACT_CREDIT_BALANCE)));  //信用证余额
            bean.setMapObject(map);
            list.add(bean);
        }
        cursor.close();
        closeDataBase();
        return list;
    }

    /**
     * 向温馨提示列表插入数据
     *
     * @param bean 温馨提示Bean对象
     * @return
     */
    public static long insertToPleasantMessage(PleasantMessageBean bean) {
        openDataBase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_PLEASANT_MESSAGE_MSG_TITLE, bean.getMsgTitle());  // 消息标题
        values.put(COLUMN_PLEASANT_MESSAGE_MSG_CONTENT, bean.getMsgContent());  // 消息内容
        values.put(COLUMN_PLEASANT_MESSAGE_MSG_IS_READ, bean.getMsgIsRead());  // 消息时间
        values.put(COLUMN_PLEASANT_MESSAGE_MSG_DATE_AND_TIME, bean.getMsgDateAndTime());  // 消息是否已读
        long insert = mDatabase.insert(TABLE_NAME_PLEASANT_MESSAGE, null, values);
        closeDataBase();
        return insert;
    }

    /**
     * 获取温馨提示列表中所有的数据
     *
     * @return
     */
    public static List<PleasantMessageBean> queryAllPleasantMessage() {
        openDataBase();
        String[] projection = {COLUMN_PLEASANT_MESSAGE_MSG_ID, COLUMN_PLEASANT_MESSAGE_MSG_TITLE,
                COLUMN_PLEASANT_MESSAGE_MSG_CONTENT, COLUMN_PLEASANT_MESSAGE_MSG_IS_READ,
                COLUMN_PLEASANT_MESSAGE_MSG_DATE_AND_TIME};
        Cursor cursor = mDatabase.query(TABLE_NAME_PLEASANT_MESSAGE, projection, null,
                null, null, null, null);
        List<PleasantMessageBean> list = new ArrayList<>();
        while (cursor.moveToNext()) {
            PleasantMessageBean bean = new PleasantMessageBean();
            bean.setMsgId(String.valueOf(cursor.getInt(cursor.getColumnIndex(COLUMN_PLEASANT_MESSAGE_MSG_ID))));  //提示信息id
            bean.setMsgTitle( cursor.getString(cursor.getColumnIndex(COLUMN_PLEASANT_MESSAGE_MSG_TITLE)));  //提示信息标题
            bean.setMsgContent(cursor.getString(cursor.getColumnIndex(COLUMN_PLEASANT_MESSAGE_MSG_CONTENT)));  //提示信息内容
            bean.setMsgIsRead(cursor.getString(cursor.getColumnIndex(COLUMN_PLEASANT_MESSAGE_MSG_IS_READ)));  //信息是否已读
            bean.setMsgDateAndTime(cursor.getString(cursor.getColumnIndex(COLUMN_PLEASANT_MESSAGE_MSG_DATE_AND_TIME))); //提示信息时间
            list.add(bean);
        }
        cursor.close();
        closeDataBase();
        return list;
    }

    /**
     * 分页查询温馨提示消息列表
     *
     * @param page  页码
     * @param count 每页查询数据条数
     * @return
     */
    public static List<PleasantMessageBean> queryPleasantMessage(int page, int count) {
        openDataBase();
        String sql = "select * from " + TABLE_NAME_PLEASANT_MESSAGE + " limit " + count + " offset " + page * count + ";";
        Cursor cursor = mDatabase.rawQuery(sql, null);
        List<PleasantMessageBean> list = new ArrayList<>();
        while (cursor.moveToNext()) {
            PleasantMessageBean bean = new PleasantMessageBean();
            bean.setMsgId(String.valueOf(cursor.getInt(cursor.getColumnIndex(COLUMN_PLEASANT_MESSAGE_MSG_ID))));  //提示信息id
            bean.setMsgTitle(cursor.getString(cursor.getColumnIndex(COLUMN_PLEASANT_MESSAGE_MSG_TITLE)));  //提示信息标题
            bean.setMsgContent(cursor.getString(cursor.getColumnIndex(COLUMN_PLEASANT_MESSAGE_MSG_CONTENT)));  //提示信息内容
            bean.setMsgIsRead(cursor.getString(cursor.getColumnIndex(COLUMN_PLEASANT_MESSAGE_MSG_IS_READ)));  //提示信息是否已读
            bean.setMsgDateAndTime(cursor.getString(cursor.getColumnIndex(COLUMN_PLEASANT_MESSAGE_MSG_DATE_AND_TIME)));  //提示信息时间
            list.add(bean);
        }
        cursor.close();
        closeDataBase();
        return list;
    }

}
