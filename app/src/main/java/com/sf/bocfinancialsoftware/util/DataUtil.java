package com.sf.bocfinancialsoftware.util;

import com.sf.bocfinancialsoftware.R;
import com.sf.bocfinancialsoftware.bean.BocAnalyseBean;
import com.sf.bocfinancialsoftware.bean.BusinessBean;
import com.sf.bocfinancialsoftware.bean.BusinessTypeBean;
import com.sf.bocfinancialsoftware.bean.ContractBean;
import com.sf.bocfinancialsoftware.bean.MessageReminderBean;
import com.sf.bocfinancialsoftware.bean.PleasantMessageBean;
import com.sf.bocfinancialsoftware.constant.ConstantConfig;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.sf.bocfinancialsoftware.constant.ConstantConfig.CREDIT_BALANCE;
import static com.sf.bocfinancialsoftware.constant.ConstantConfig.IMAGE_LIST;
import static com.sf.bocfinancialsoftware.constant.ConstantConfig.MSG_UN_READ;
import static com.sf.bocfinancialsoftware.constant.ConstantConfig.OPENING_AMOUNT;
import static com.sf.bocfinancialsoftware.constant.SQLiteConfig.COLUMN_BUSINESS_DATE_NAME;
import static com.sf.bocfinancialsoftware.constant.SQLiteConfig.COLUMN_BUSINESS_ID_NAME;
import static com.sf.bocfinancialsoftware.constant.SQLiteConfig.COLUMN_BUSINESS_NAME;
import static com.sf.bocfinancialsoftware.constant.SQLiteConfig.COLUMN_BUSINESS_TYPE_ID;

/**
 * Created by john on 2017/6/9.
 */

public class DataUtil {

    /**
     * 设置中银分析数据
     *
     * @return
     */
    public static List<BocAnalyseBean> setBocAnalyseDate() {
        List<BocAnalyseBean> bocAnalyseBeanList = new ArrayList<>();
        bocAnalyseBeanList.add(new BocAnalyseBean(
                "汇率下调",
                "欧洲央行行长宣布，从本月1日起，欧元汇率下调至0.1个百分点",
                "2015-01-01",
                R.mipmap.image_boc_analyse_one + "",
                "欧洲央行行长宣布，从本月1日起，欧元汇率下调至0.1个百分点，此举措将会对各国产生一定程度的影响。汇率变动后，出口商品的国内价格也发生变动。如本币汇率下降，则外币购买力提高，国外进口商就会增加对本国出口商品的需求。在出口商品供应数量不能相应增长的情况下，出口商品的国内价格必然上涨。在初级产品的出口贸易中，汇率变化对价格的影响特别明显。",
                IMAGE_LIST));
        bocAnalyseBeanList.add(new BocAnalyseBean(
                "三个因素至外储大幅回升",
                "中国连续第四个月上升，创2014年6月来最长上升周期",
                "2015-01-02",
                R.mipmap.image_boc_analyse_two + "",
                "据悉，中国5月外汇储备继续上升240.4亿美元，为连续第四个月上升，创2014年6月来最长上升周期，九州证券全球首席经济学家邓海清认为，这进一步验证前期外汇大幅下降的趋势已经扭转，预期未来外汇储备将进一步稳步回升。",
                IMAGE_LIST));
        bocAnalyseBeanList.add(new BocAnalyseBean(
                "人民币已经跌至五年新低",
                "人民币持续贬值，每日几乎均在以100点左右的跌幅阶梯下行，连跌五天",
                "2015-01-03",
                R.mipmap.image_boc_analyse_three + "",
                "本周以来，人民币持续贬值，每日几乎均在以100点左右的跌幅阶梯下行，连跌五天，市场上关于“央行默许人民币贬值”的猜测急速升温。周四在岸人民币对美元再跌142点，报6.4378，为五年最低，较前日收盘价下跌98个点；而离岸人民币跳水近300点，午后16点起两个半小时内跌近300点，最低至6.54456，直逼6.55关口，跌幅扩大至0.5%。至此，从IMF宣布将人民币纳入特别提款权（SDR）货币篮子到今天，离岸人民币已贬值超过1.2%。",
                IMAGE_LIST));
        //插入数据库
        for (BocAnalyseBean bean : bocAnalyseBeanList) {
            DataBaseSQLiteUtil.insertToBocAnalyse(bean);
        }
        return bocAnalyseBeanList;
    }

    /**
     * 设置通知提醒数据
     *
     * @return
     */
    public static List<MessageReminderBean> setMessageReminderData() {
        List<MessageReminderBean> messageReminderBeanList = new ArrayList<>();
        //进口通知
        messageReminderBeanList.add(new MessageReminderBean(
                ConstantConfig.MSG_TYPE_ID_IMPORT,
                "进口信用证开立通知书",
                "2015-01-03",
                "博文公司：您好！贵公司在我行开立的进口信用证LXKJHJH,应于2015年01月31日到期付款，应付金额2000000,联系颠换0771-2567890，特此通知!",
                MSG_UN_READ));
        messageReminderBeanList.add(new MessageReminderBean(
                ConstantConfig.MSG_TYPE_ID_IMPORT,
                "进口信用证到单通知书",
                "2015-01-02",
                "凡是向中国银行申请办理进口信用证结算的公司，必须具有进出口经营权。若为首次办理该业务时需向中国银行提供如下文件的副本各一份：营业执照，经贸部（委）批准证书。",
                MSG_UN_READ));
        messageReminderBeanList.add(new MessageReminderBean(
                ConstantConfig.MSG_TYPE_ID_IMPORT,
                "进口信用证即期付款通知书",
                "2015-01-01",
                "开证银行根据信用证的付款条件规定，对外履行付款，同时，对进口公司办理进口结汇，由进口公司填写“购买外汇申请书”和“进口付汇核销单”，然后由银行根据中国银行外汇牌价表的汇率，以人民币兑换信用证上所规定的外币及各项费用。",
                MSG_UN_READ));
        messageReminderBeanList.add(new MessageReminderBean(
                ConstantConfig.MSG_TYPE_ID_IMPORT,
                "进口信用证承兑到期付款通知",
                "2014-12-31",
                "信用证（Letter of Credit，L/C），是指开证银行应申请人（买方）的要求并按其指示向受益人开立的载有一定金额的、在一定的期限内凭符合规定的单据付款的书面保证文件。信用证是国际贸易中最主要、最常用的支付方式。",
                MSG_UN_READ));

        messageReminderBeanList.add(new MessageReminderBean(
                ConstantConfig.MSG_TYPE_ID_IMPORT,
                "进口贸易金融业务到期提示",
                "2014-12-30",
                "信用证（Letter of Credit，L/C），是指开证银行应申请人（买方）的要求并按其指示向受益人开立的载有一定金额的、在一定的期限内凭符合规定的单据付款的书面保证文件。信用证是国际贸易中最主要、最常用的支付方式。",
                MSG_UN_READ));
        messageReminderBeanList.add(new MessageReminderBean(
                ConstantConfig.MSG_TYPE_ID_IMPORT,
                "进口代收到单通知",
                "2014-12-29",
                "信用证（Letter of Credit，L/C），是指开证银行应申请人（买方）的要求并按其指示向受益人开立的载有一定金额的、在一定的期限内凭符合规定的单据付款的书面保证文件。信用证是国际贸易中最主要、最常用的支付方式。",
                MSG_UN_READ));

        //出口通知
        messageReminderBeanList.add(new MessageReminderBean(
                ConstantConfig.MSG_TYPE_ID_EXPORT,
                "出口信用证开立通知书",
                "2015-01-03",
                "博文公司：您好！贵公司在我行开立的进口信用证LXKJHJH,应于2015年01月31日到期付款，应付金额2000000,联系颠换0771-2567890，特此通知!",
                MSG_UN_READ));
        messageReminderBeanList.add(new MessageReminderBean(
                ConstantConfig.MSG_TYPE_ID_EXPORT,
                "出口信用证结算通知书",
                "2015-01-02",
                "凡是向中国银行申请办理进口信用证结算的公司，必须具有进出口经营权。若为首次办理该业务时需向中国银行提供如下文件的副本各一份：营业执照，经贸部（委）批准证书。",
                MSG_UN_READ));
        messageReminderBeanList.add(new MessageReminderBean(
                ConstantConfig.MSG_TYPE_ID_EXPORT,
                "出口结汇通知书",
                "2015-01-01",
                "开证银行根据信用证的付款条件规定，对外履行付款，同时，对进口公司办理进口结汇，由进口公司填写“购买外汇申请书”和“进口付汇核销单”，然后由银行根据中国银行外汇牌价表的汇率，以人民币兑换信用证上所规定的外币及各项费用。",
                MSG_UN_READ));

        //保函通知
        messageReminderBeanList.add(new MessageReminderBean(
                ConstantConfig.MSG_TYPE_ID_GUARANTEE,
                "保函通知",
                "2015-01-03",
                "在对外经贸活动中，保函的受益人在商务谈判中指定中国银行作为保函的通知银行。无论是信开还是电开的保函，中国银行能为客户核验保函的真实性，并及时将保函正本通知客户。",
                MSG_UN_READ));
        messageReminderBeanList.add(new MessageReminderBean(
                ConstantConfig.MSG_TYPE_ID_GUARANTEE,
                "保函收费通知",
                "2015-01-02",
                "保函按收费标准每三个月收取一次（即本费率是三个月费率），也可一次性收取。",
                MSG_UN_READ));
        messageReminderBeanList.add(new MessageReminderBean(
                ConstantConfig.MSG_TYPE_ID_GUARANTEE,
                "关于银行保函上传的通知",
                "2015-01-01",
                "各投标单位在投标时，应将银行保函扫描后上传到交易平台的“缴纳保证金”模块，以免造成开标延误或投标失败。",
                MSG_UN_READ));
        messageReminderBeanList.add(new MessageReminderBean(
                ConstantConfig.MSG_TYPE_ID_GUARANTEE,
                "融资性担保定义通知",
                "2014-12-31",
                "融资性担保，是指担保项下主合同具有融资性质的担保，包括但不限于为借款、债券发行、融资租赁等提供的，及其他为申请人融资行为而承担保证责任的担保",
                MSG_UN_READ));
        messageReminderBeanList.add(new MessageReminderBean(
                ConstantConfig.MSG_TYPE_ID_GUARANTEE,
                "融资性担保定义通知",
                "2014-12-30",
                "融资性担保，是指担保项下主合同具有融资性质的担保，包括但不限于为借款、债券发行、融资租赁等提供的，及其他为申请人融资行为而承担保证责任的担保",
                MSG_UN_READ));

        //保理通知
        messageReminderBeanList.add(new MessageReminderBean(
                ConstantConfig.MSG_TYPE_ID_FACTORING,
                "保理商签发收账通知注意事项",
                "2015-01-03",
                "通知发出人的选择、未来应收账款的描述、签发时间的确定、收件人的表达、“假章”风险的排查。",
                MSG_UN_READ));
        messageReminderBeanList.add(new MessageReminderBean(
                ConstantConfig.MSG_TYPE_ID_FACTORING,
                "保理业务确认函",
                "2015-01-02",
                "保理业务确认函已下发至您的邮箱，请注意查收！保理业务确认函已下发至您的邮箱，请注意查收!",
                MSG_UN_READ));
        messageReminderBeanList.add(new MessageReminderBean(
                ConstantConfig.MSG_TYPE_ID_FACTORING,
                "加强商业保理行业管理",
                "2015-01-01",
                "为促进应收账款流转，支持中小企业发展，商务部办公厅最近印发了《关于做好商业保理行业管理工作的通知》（商办秩函［2013］718号，以下简称《通知》），就进一步加强商业保理行业管理，促进行业健康发展，做出了部署和安排。",
                MSG_UN_READ));
        //远期通知
        messageReminderBeanList.add(new MessageReminderBean(
                ConstantConfig.MSG_TYPE_ID_FORWARD,
                "加强远期售汇宏观审慎管理的通知",
                "2015-01-04",
                "为完善宏观审慎管理框架，防范宏观金融风险，促进金融机构稳健经营，中国人民银行决定，对开展代客远期售汇业务的金融机构收取外汇风险准备金。",
                MSG_UN_READ));
        messageReminderBeanList.add(new MessageReminderBean(
                ConstantConfig.MSG_TYPE_ID_FORWARD,
                "远期外汇",
                "2015-01-03",
                "远期外汇业务即预约购买与预约出卖的外汇业务，亦即买卖双方先签订合同，规定买卖外汇的币种、数额、汇率和将来交割的时间，到规定的交割日期，再按合同规定，卖方交汇，买方付款的外汇业务。",
                MSG_UN_READ));
        messageReminderBeanList.add(new MessageReminderBean(
                ConstantConfig.MSG_TYPE_ID_FORWARD,
                "代理结售汇业务通知",
                "2015-01-02",
                "远期外汇业务即预约购买与预约出卖的外汇业务，",
                MSG_UN_READ));
        messageReminderBeanList.add(new MessageReminderBean(
                ConstantConfig.MSG_TYPE_ID_FORWARD,
                "代客外汇风险管理提示",
                "2015-01-01",
                "远期外汇业务即预约购买与预约出卖的外汇业务，亦即买卖双方先签订合同，规定买卖外汇的币种、数额、汇率和将来交割的时间，到规定的交割日期，再按合同规定，卖方交汇，买方付款的外汇业务。",
                MSG_UN_READ));

        //插入数据库
        for (MessageReminderBean bean : messageReminderBeanList) {
            DataBaseSQLiteUtil.insertToMessageReminder(bean);
        }
        return messageReminderBeanList;
    }

    /**
     * 设置业务类别信息
     */
    public static void setExpandableListData(List<BusinessTypeBean> groups, List<List<BusinessBean>> children) {
        //一级列表目录
        groups.add(0, new BusinessTypeBean("进口业务"));
        groups.add(1, new BusinessTypeBean("出口业务"));
        groups.add(2, new BusinessTypeBean("保函业务"));
        groups.add(3, new BusinessTypeBean("保理及供应链融资业务"));
        //二级列表目录
        List<BusinessBean> childItems1 = new ArrayList<BusinessBean>();
        childItems1.add(new BusinessBean("进口信用证开立查询"));
        childItems1.add(new BusinessBean("进口信用证承兑业务查询"));
        childItems1.add(new BusinessBean("进口信用证到单查询"));
        childItems1.add(new BusinessBean("进口代收到单查询"));
        children.add(0, childItems1);
        List<BusinessBean> childItems2 = new ArrayList<BusinessBean>();
        childItems2.add(new BusinessBean("出口信用证开立查询"));
        childItems2.add(new BusinessBean("出口信用证承兑业务查询"));
        childItems2.add(new BusinessBean("出口信用证到单查询"));
        childItems2.add(new BusinessBean("出口代收到单查询"));
        children.add(1, childItems2);
        List<BusinessBean> childItems3 = new ArrayList<BusinessBean>();
        childItems3.add(new BusinessBean("保函信用证开立查询"));
        childItems3.add(new BusinessBean("保函信用证承兑业务查询"));
        childItems3.add(new BusinessBean("保函信用证到单查询"));
        childItems3.add(new BusinessBean("保函代收到单查询"));
        children.add(2, childItems3);
        List<BusinessBean> childItems4 = new ArrayList<BusinessBean>();
        childItems4.add(new BusinessBean("保理信用证开立查询"));
        childItems4.add(new BusinessBean("保理信用证承兑业务查询"));
        childItems4.add(new BusinessBean("保理信用证到单查询"));
        childItems4.add(new BusinessBean("保理代收到单查询"));
        children.add(3, childItems4);
    }

    /**
     * 设置业务信息
     */
    public static List<ContractBean> setContractDate() {
        //将业务类别插入数据库业务类别表
        List<BusinessTypeBean> businessTypeBeanList = new ArrayList<>();
        businessTypeBeanList.add(0, new BusinessTypeBean("进口业务"));
        businessTypeBeanList.add(1, new BusinessTypeBean("出口业务"));
        businessTypeBeanList.add(2, new BusinessTypeBean("保函业务"));
        businessTypeBeanList.add(3, new BusinessTypeBean("保理及供应链融资业务"));
        for (BusinessTypeBean bean : businessTypeBeanList) {
            DataBaseSQLiteUtil.insertToBusinessType(bean);
        }
        //插入业务表
        List<BusinessBean> businessBeanList = new ArrayList<>();
        businessBeanList.add(0, new BusinessBean("进口业务", "进口信用证开立查询"));
        businessBeanList.add(1, new BusinessBean("进口业务", "进口信用证承兑业务查询"));
        businessBeanList.add(2, new BusinessBean("进口业务", "进口信用证到单查询"));
        businessBeanList.add(3, new BusinessBean("进口业务", "进口代收到单查询"));
        businessBeanList.add(4, new BusinessBean("出口业务", "出口信用证开立查询"));
        businessBeanList.add(5, new BusinessBean("出口业务", "出口信用证承兑业务查询"));
        businessBeanList.add(6, new BusinessBean("出口业务", "出口信用证到单查询"));
        businessBeanList.add(7, new BusinessBean("出口业务", "出口代收到单查询"));
        businessBeanList.add(8, new BusinessBean("保函业务", "保函信用证开立查询"));
        businessBeanList.add(9, new BusinessBean("保函业务", "保函信用证承兑业务查询"));
        businessBeanList.add(10, new BusinessBean("保函业务", "保函代收到单查询"));
        businessBeanList.add(11, new BusinessBean("保函业务", "保函代收到单"));
        businessBeanList.add(12, new BusinessBean("保理及供应链融资业务", "保理信用证开立查询"));
        businessBeanList.add(13, new BusinessBean("保理及供应链融资业务", "保理信用证承兑业务查询"));
        businessBeanList.add(14, new BusinessBean("保理及供应链融资业务", "保理信用证到单查询"));
        businessBeanList.add(15, new BusinessBean("保理及供应链融资业务", "保理代收到单查询"));
        for (BusinessBean bean : businessBeanList) {
            DataBaseSQLiteUtil.insertToBusiness(bean);
        }
        //插合同表
        HashMap<Object, Object> mapObject1 = new HashMap<>();
        mapObject1.put(OPENING_AMOUNT, "1000000");
        mapObject1.put(CREDIT_BALANCE, "11000000");
        HashMap<Object, Object> mapObject2 = new HashMap<>();
        mapObject2.put(OPENING_AMOUNT, "2000000");
        mapObject2.put(CREDIT_BALANCE, "22000000");
        HashMap<Object, Object> mapObject3 = new HashMap<>();
        mapObject3.put(OPENING_AMOUNT, "3000000");
        mapObject3.put(CREDIT_BALANCE, "33000000");
        HashMap<Object, Object> mapObject4 = new HashMap<>();
        mapObject4.put(OPENING_AMOUNT, "4000000");
        mapObject4.put(CREDIT_BALANCE, "44000000");
        List<ContractBean> list = new ArrayList<>();
        list.add(new ContractBean("100001", "进口信用证开立查询", "2017-06-03", mapObject1));
        list.add(new ContractBean("100002", "进口信用证承兑业务查询", "2017-06-04", mapObject2));
        list.add(new ContractBean("100003", "进口信用证到单查询", "2017-06-15", mapObject3));
        list.add(new ContractBean("100004", "进口代收到单查询", "2017-06-18", mapObject4));
        list.add(new ContractBean("100005", "出口信用证开立查询", "2017-06-03", mapObject1));
        list.add(new ContractBean("100006", "出口信用证承兑业务查询", "2017-06-04", mapObject2));
        list.add(new ContractBean("100007", "出口信用证到单查询", "2017-06-15", mapObject3));
        list.add(new ContractBean("100008", "出口代收到单查询", "2017-06-18", mapObject4));
        list.add(new ContractBean("100009", "保函信用证开立查询", "2017-06-03", mapObject1));
        list.add(new ContractBean("1000010", "保函信用证开立查询", "2017-06-04", mapObject2));
        list.add(new ContractBean("1000011", "保函信用证承兑业务查询", "2017-06-15", mapObject3));
        list.add(new ContractBean("1000012", "保函代收到单查询", "2017-06-18", mapObject4));
        list.add(new ContractBean("1000013", "保理信用证开立查询", "2017-06-03", mapObject1));
        list.add(new ContractBean("1000014", "保理信用证承兑业务查询", "2017-06-04", mapObject2));
        list.add(new ContractBean("1000015", "保理信用证到单查询", "2017-06-15", mapObject3));
        list.add(new ContractBean("1000016", "保理代收到单查询", "2017-06-18", mapObject4));
        for (ContractBean bean : list) {
            DataBaseSQLiteUtil.insertToContract(bean);
        }
        return list;
    }

    /**
     * 设置通知提醒数据
     *
     * @return
     */
    public static List<PleasantMessageBean> setPleasantMessageData() {
        List<PleasantMessageBean> pleasantMessageBeanList = new ArrayList<>();
        //进口通知
        pleasantMessageBeanList.add(new PleasantMessageBean(
                "温馨提示1",
                "        根据贵公司在我行外汇业务的开展情况，本行觉得我行“远期结汇售”业务对贵公司的业务发展很有帮助，欲了解详情，请咨询您的客户经理或访问官网www.boc.cn进行查询。",
                MSG_UN_READ,
                "2015-11-04    19:58"));
        pleasantMessageBeanList.add(new PleasantMessageBean(
                "温馨提示2",
                "尊敬的客户：\n" +
                        "        您好！\n" +
                        "        衷心欢迎您成为我行代发工资客户，为指导您正确用卡，我行特温馨提示您：\n" +
                        "        1、工资卡除办理代发工资业务外，还具备存款、取款、转账、理财、消费等功能。\n" +
                        "        2、请不要销卡，否则，您下次的工资或补助款将无法发放。如卡片丢失，请及时来我行挂失。",
                MSG_UN_READ,
                "2015-11-03    15:50"));

        pleasantMessageBeanList.add(new PleasantMessageBean(
                "温馨提示3",
                "尊敬的客户：\n" +
                        "        为提高我行电子银行服务质量、维护您的资金安全和合法权益，请不要将您的电子银行密码设为相同或连续升降排列的数字和字母、不要设为银行卡号、账号、身份证号、电话号码、手机号的连续几位。",
                MSG_UN_READ,
                "2015-11-02    08:50"));

        pleasantMessageBeanList.add(new PleasantMessageBean(
                "温馨提示4",
                "尊敬的客户：\n" +
                        "        我行网上银行提供了从登录、查询、交易、直到退出的每一个环节的短信提醒服务，您可以直接通过网上银行选择开通，捆绑您的手机，随时掌握网上银行使用情况。该短信提醒发送号码为建行全国统一号码95533。",
                MSG_UN_READ,
                "2015-11-01    10:50"));
        //插入数据库
        for (PleasantMessageBean bean : pleasantMessageBeanList) {
            DataBaseSQLiteUtil.insertToPleasantMessage(bean);
        }
        return pleasantMessageBeanList;
    }

}
