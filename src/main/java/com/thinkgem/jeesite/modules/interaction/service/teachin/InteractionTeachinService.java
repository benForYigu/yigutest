/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.interaction.service.teachin;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.*;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Strings;

import com.thinkgem.jeesite.API.entity.Code;
import com.thinkgem.jeesite.API.entity.Response;
import com.thinkgem.jeesite.API.util.AliMessageUtils;
import com.thinkgem.jeesite.API.util.ChineseUtils;
import com.thinkgem.jeesite.API.util.MyUtils;
import com.thinkgem.jeesite.API.util.PushUtil;
import com.thinkgem.jeesite.API.weixin.api.PayMchAPI;
import com.thinkgem.jeesite.API.weixin.bean.paymch.MchBaseResult;
import com.thinkgem.jeesite.API.weixin.bean.paymch.MchPayNotify;
import com.thinkgem.jeesite.API.weixin.bean.paymch.Unifiedorder;
import com.thinkgem.jeesite.API.weixin.bean.paymch.UnifiedorderResult;
import com.thinkgem.jeesite.API.weixin.support.ExpireKey;
import com.thinkgem.jeesite.API.weixin.support.expirekey.DefaultExpireKey;
import com.thinkgem.jeesite.API.weixin.util.PayUtil;
import com.thinkgem.jeesite.API.weixin.util.SignatureUtil;
import com.thinkgem.jeesite.API.weixin.util.StreamUtils;
import com.thinkgem.jeesite.API.weixin.util.XMLConverUtil;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.IdGen;
import com.thinkgem.jeesite.common.utils.TimeUtils;
import com.thinkgem.jeesite.modules.account.dao.accounthr.AccountCompanyhrDao;
import com.thinkgem.jeesite.modules.account.dao.accountschool.AccountTeacherinfoDao;
import com.thinkgem.jeesite.modules.account.dao.accountstudent.AccountStudentinfoDao;
import com.thinkgem.jeesite.modules.account.entity.Account;
import com.thinkgem.jeesite.modules.account.entity.accounthr.AccountCompanyhr;
import com.thinkgem.jeesite.modules.account.entity.accountschool.AccountTeacherinfo;
import com.thinkgem.jeesite.modules.account.entity.accountstudent.AccountStudentinfo;
import com.thinkgem.jeesite.modules.company.dao.CompanyDao;
import com.thinkgem.jeesite.modules.company.dao.profession.CompanyProfessionDao;
import com.thinkgem.jeesite.modules.company.entity.profession.CompanyProfession;
import com.thinkgem.jeesite.modules.interaction.entity.interview.InteractionInterview;
import com.thinkgem.jeesite.modules.order.dao.order.OrderDao;
import com.thinkgem.jeesite.modules.order.entity.order.Order;
import com.thinkgem.jeesite.modules.order.service.order.OrderService;
import com.thinkgem.jeesite.modules.orderinvoices.dao.OrderInvoicesDao;
import com.thinkgem.jeesite.modules.orderinvoices.entity.OrderInvoices;
import com.thinkgem.jeesite.modules.school.dao.SchoolDao;
import com.thinkgem.jeesite.modules.school.dao.ocupy.SchoolTeachinOcupyDao;
import com.thinkgem.jeesite.modules.school.entity.ocupy.SchoolTeachinOcupy;
import com.thinkgem.jeesite.modules.vip.dao.accountvip.VipAccountDao;
import com.thinkgem.jeesite.modules.vip.entity.accountvip.VipAccount;
import com.thoughtworks.xstream.core.ReferenceByIdMarshaller;
import org.apache.commons.collections.ListUtils;
import org.apache.poi.ss.formula.functions.T;
import org.apache.shiro.crypto.hash.Hash;
import org.codehaus.groovy.transform.sc.transformers.RangeExpressionTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.method.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.interaction.entity.teachin.InteractionTeachin;
import com.thinkgem.jeesite.modules.interaction.dao.teachin.InteractionTeachinDao;
import com.thinkgem.jeesite.modules.interaction.entity.teachin.InteractionTeachinData;
import com.thinkgem.jeesite.modules.interaction.dao.teachin.InteractionTeachinDataDao;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.print.attribute.HashPrintJobAttributeSet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * 宣讲Service
 *
 * @author 李金辉
 * @version 2019-01-24
 */
@Service
@Transactional(readOnly = true)
public class InteractionTeachinService extends CrudService<InteractionTeachinDao, InteractionTeachin> {

    @Value("${weixin.Mch_key}")
    private String key;
    @Value("${weixin.wxAppId}")
    private String wxAppId;
    @Value("${weixin.Mch_id}")
    private String Mch_id;
    @Value("${weixin.notify_url}")
    private String notify_url;
    @Autowired
    private InteractionTeachinDataDao interactionTeachinDataDao;
    @Autowired
    CompanyDao companyDao;
    @Autowired
    OrderDao orderDao;
    @Autowired
    SchoolDao schoolDao;
    @Autowired
    AccountStudentinfoDao accountStudentinfoDao;
    @Autowired
    AccountTeacherinfoDao accountTeacherinfoDao;
    @Autowired
    AccountCompanyhrDao accountCompanyhrDao;
    @Autowired
    CompanyProfessionDao companyProfessionDao;
    @Autowired
    VipAccountDao vipAccountDao;
    @Autowired
    SchoolTeachinOcupyDao teachinOcupyDao;
    @Autowired
    OrderInvoicesDao orderInvoicesDao;
    @Value("${company.logo}")
    String companyLogo;
    //重复通知过滤
    private static ExpireKey expireKey = new DefaultExpireKey();

    public InteractionTeachin get(String id) {
        InteractionTeachin interactionTeachin = super.get(id);
        List<InteractionTeachinData> list = interactionTeachinDataDao.findList(new InteractionTeachinData(interactionTeachin));
        /*for (InteractionTeachinData i: list) {
			i.setUrl(((String)i.getUrl()).split("\\|"));
		}*/
        interactionTeachin.setInteractionTeachinDataList(list);
        interactionTeachin.setCompany(companyDao.get(interactionTeachin.getCompanyId()));
        interactionTeachin.setSchool(schoolDao.get(interactionTeachin.getSchoolId()));
        return interactionTeachin;
    }

    public List<InteractionTeachin> findList(InteractionTeachin interactionTeachin) {
		/*List<InteractionTeachin> list=super.findList(interactionTeachin);
		for (InteractionTeachin i:list) {
			i.setCompany(companyDao.get(i.getCompanyId()));
			i.setSchool(schoolDao.get(i.getSchoolId()));
		}
		return list;*/
        return super.findList(interactionTeachin);
    }

    public Page<InteractionTeachin> findPage(Page<InteractionTeachin> page, InteractionTeachin interactionTeachin) {
        Page<InteractionTeachin> p = super.findPage(page, interactionTeachin);
        for (InteractionTeachin i : p.getList()) {
            i.setCompany(companyDao.get(i.getCompanyId()));
            i.setSchool(schoolDao.get(i.getSchoolId()));
        }
        return p;
    }

    @Transactional(readOnly = false)
    public void save(InteractionTeachin interactionTeachin) {
        super.save(interactionTeachin);
        for (InteractionTeachinData interactionTeachinData : interactionTeachin.getInteractionTeachinDataList()) {
            if (interactionTeachinData.getId() == null) {
                continue;
            }
            if (InteractionTeachinData.DEL_FLAG_NORMAL.equals(interactionTeachinData.getDelFlag())) {
                if (StringUtils.isBlank(interactionTeachinData.getId())) {
                    interactionTeachinData.setTeachinId(interactionTeachin);
                    interactionTeachinData.preInsert();
                    interactionTeachinDataDao.insert(interactionTeachinData);
                } else {
                    interactionTeachinData.preUpdate();
                    interactionTeachinDataDao.update(interactionTeachinData);
                }
            } else {
                interactionTeachinDataDao.delete(interactionTeachinData);
            }
        }
    }

    @Transactional(readOnly = false)
    public void delete(InteractionTeachin interactionTeachin) {
        super.delete(interactionTeachin);
        interactionTeachinDataDao.delete(new InteractionTeachinData(interactionTeachin));
    }

    //获取宣讲列表
    public Object teachin(Account account, String status, String teachinId, Integer page, Integer size) {
        InteractionTeachin interactionTeachin = new InteractionTeachin();
        //查询单个详情
        if (!Strings.isNullOrEmpty(teachinId)) {
            interactionTeachin = get(teachinId);
            if (interactionTeachin == null) {
                return Code.API_TEACHIN_NOT_EXIT;
            }
            //学生
            if (Account.ROLE_STUDENT.equals(account.getRole())) {
                AccountStudentinfo accountStudentinfo = accountStudentinfoDao.getByAccountId(account.getId());
                if (accountStudentinfo.getSchoolId().equals(interactionTeachin.getSchoolId())) {
                    for (InteractionTeachinData i : interactionTeachin.getInteractionTeachinDataList()) {
                        String[] s = ((String) (i.getUrl())).split("\\|");
                        List n = new ArrayList();
                        for (String ss : s) {
                            if (!Strings.isNullOrEmpty(ss)) {
                                n.add(ss);
                            }
                        }
                        i.setUrl(n);
                    }
                    return interactionTeachin;
                }
            }
            //企业
            if (Account.ROLE_COMPANY.equals(account.getRole())) {
                AccountCompanyhr accountCompanyhr = accountCompanyhrDao.getByAccountId(account.getId());
                if (accountCompanyhr.getCompanyId().equals(interactionTeachin.getCompanyId())) {
                    for (InteractionTeachinData i : interactionTeachin.getInteractionTeachinDataList()) {
                        String[] s = ((String) i.getUrl()).split("\\|");
                        List n = new ArrayList();
                        for (String ss : s) {
                            if (!Strings.isNullOrEmpty(ss)) {
                                n.add(ss);
                            }
                        }
                        i.setUrl(n);
                    }
                    return interactionTeachin;
                }
            }
            //学校工作人员
            AccountTeacherinfo accountTeacherinfo = accountTeacherinfoDao.getByAccountId(account.getId());
            if (accountTeacherinfo.getSchoolId().equals(interactionTeachin.getSchoolId())) {
                for (InteractionTeachinData i : interactionTeachin.getInteractionTeachinDataList()) {
                    String[] s = ((String) i.getUrl()).split("\\|");
                    List n = new ArrayList();
                    for (String ss : s) {
                        if (!Strings.isNullOrEmpty(ss)) {
                            n.add(ss);
                        }
                    }
                    i.setUrl(n);
                }
                return interactionTeachin;
            }

            //查询列表
        } else {
            //学生
            if (Account.ROLE_STUDENT.equals(account.getRole())) {
                AccountStudentinfo accountStudentinfo = accountStudentinfoDao.getByAccountId(account.getId());
                interactionTeachin.setStatus(status);
                interactionTeachin.setSchoolId(accountStudentinfo.getSchoolId());
                PageHelper.startPage(page, size);
                return new PageInfo<InteractionTeachin>(dao.findList(interactionTeachin));
            }
            //企业
            if (Account.ROLE_COMPANY.equals(account.getRole())) {
                AccountCompanyhr accountCompanyhr = accountCompanyhrDao.getByAccountId(account.getId());
                interactionTeachin.setStatus(status);
                interactionTeachin.setCompanyId(accountCompanyhr.getCompanyId());
                PageHelper.startPage(page, size);
                return new PageInfo<InteractionTeachin>(dao.findList(interactionTeachin));
            }
            //学校工作人员
            AccountTeacherinfo accountTeacherinfo = accountTeacherinfoDao.getByAccountId(account.getId());
            interactionTeachin.setStatus(status);
            interactionTeachin.setCompanyId(accountTeacherinfo.getSchoolId());
            PageHelper.startPage(page, size);
            return new PageInfo<InteractionTeachin>(dao.findList(interactionTeachin));

        }
        return Code.API_USER_ROLE_ERROR;
    }

    //获取学校群组
    public Object schoolGroup(Account account, String studentName) {
		/*if(!Account.ROLE_STUDENT.equals(account.getRole())){
			return Code.API_USER_ROLE_ERROR;
		}*/

        String schoolId = accountStudentinfoDao.getByAccountId(account.getId()).getSchoolId();
        if (Strings.isNullOrEmpty(schoolId)) {
            return Code.API_SCHOOL_ERROR;
        }
        AccountStudentinfo student = new AccountStudentinfo();
        student.setSchoolId(schoolId);
        student.setRealname(studentName);

        List<AccountStudentinfo> list = accountStudentinfoDao.findList(student);
        List l = new ArrayList();
        Map ret = new HashMap();
        ret.put("allNumber", list.size());
        ret.put("schoolLogo", schoolDao.get(schoolId).getLogo());

        ret.put("list", MyUtils.accountStudentinfoSort(list));
        return ret;
    }

    //职位查询
    public Object profession(String teachinId, String professionId,String companyId, Integer page, Integer size) {
        //查询列表
        if (Strings.isNullOrEmpty(professionId)) {
            if (Strings.isNullOrEmpty(teachinId)) {
                CompanyProfession c=new CompanyProfession();
                c.setCompanyId(companyId);
                return companyProfessionDao.findList(c);
            }
            String professionIds = (String) dao.get(teachinId).getProfession();
            if (page == null || size == null) {
                return companyProfessionDao.findInSet(professionIds);
            }
            PageHelper.startPage(page, size);
            return new PageInfo<CompanyProfession>(companyProfessionDao.findInSet(professionIds));
            //查询单个
        } else {
            return companyProfessionDao.get(professionId);
        }

    }

    //购买宣讲
    @Transactional(readOnly = false)
    public Object pTeachin(HttpServletRequest request, Account account,
                           String schoolId,
                           String date, String time, String type) {


        AccountCompanyhr companyhr = accountCompanyhrDao.getByAccountId(account.getId());
        //vip方式
        if ((Order.PAY_TYPE_VIP.equals(type) || Order.PAY_TYPE_DE.equals(type)) && Strings.isNullOrEmpty(companyhr.getVip())) {
            return Code.API_VIP_NO;
        }
        if (!account.getRole().equals(Account.ROLE_COMPANY)) {
            return Code.API_USER_ROLE_ERROR;
        }
        if (schoolDao.get(schoolId) == null) {
            return Code.API_SCHOOL_NOT_EXIST;
        }
        InteractionTeachin t = dao.isPayed(schoolId, DateUtils.parseDate(date), time);
        if (t != null) {
            //微信支付未支付
            if (InteractionTeachin.STATUS_NOT_PAY.equals(t.getStatus())) {
                //少于一分钟
                if (MyUtils.secondDiff(t.getCreateDate()) < 60) {
                    return Code.API_TEACHIN_WAIT_PAYED;
                } else {
                    dao.rdel(t.getId());
                }
            //对公账户未支付
            } else if(InteractionTeachin.STATUS_PUBLIC_NOT_PAY.equals(t.getStatus())){
                //少于24小时
                if (MyUtils.secondDiff(t.getCreateDate()) < 60*60*24) {
                    return Code.API_TEACHIN_WAIT_PAYED;
                } else {
                    dao.rdel(t.getId());
                }
            }else {
                return Code.API_TEACHIN_PAYED;
            }
        }

        //验证结束 开始够购买逻辑

        InteractionTeachin teachin = new InteractionTeachin();
        teachin.setSchoolId(schoolId);
        teachin.setAccountId(account.getId());
        teachin.setImg(companyLogo);
        teachin.setDate(DateUtils.parseDate(date));
        teachin.setTime(time);
        teachin.setShelf(InteractionTeachin.SHELF_OFF);
        teachin.setStatus(InteractionTeachin.STATUS_NOT_PAY);
        teachin.setCompanyId(companyhr.getCompanyId());
        teachin.preInsert();


        Order order = new Order();
        order.setStatus(Order.STATUS_WAIT);
        order.setAccountId(account.getId());
        order.setCompanyId(companyhr.getCompanyId());
        order.setPayType(type);
        if (Order.PAY_TYPE_VIP.equals(type)) {
            order.setPayment(500D);//元
        } else if (Order.PAY_TYPE_NOR.equals(type)||Order.PAY_TYPE_PUBLIC.equals(type)) {
            order.setPayment(800D);//元
        } else {
            order.setPayment(0D);//元
        }
        order.setTeachinId(teachin.getId());
        order.preInsert();
        order.setId(IdGen.randomBase62(15));

        //对公账户
        if(Order.PAY_TYPE_PUBLIC.equals(type)){
            teachin.setStatus(InteractionTeachin.STATUS_PUBLIC_NOT_PAY);
            dao.insert(teachin);
            order.setStatus(Order.STATUS_WAIT);
            order.setTransactionId(order.getId());
            orderDao.insert(order);
            Map map = new HashMap();
            map.put("orderId", order.getId());
            return map;
        }

        //抵扣
        if (Order.PAY_TYPE_DE.equals(type)) {
            teachin.setStatus(InteractionTeachin.STATUS_SOON);
            order.setStatus(Order.STATUS_PAYED);
            order.setPayTime(new Date());
            order.setTransactionId(order.getId());
            VipAccount vipAccount = vipAccountDao.selectByAccountId(account.getId());
            if (vipAccount.getTeachin() <= 0) {
                return Code.API_VIP_TIME_NOT_ENO;
            }
            vipAccount.setTeachin(vipAccount.getTeachin() - 1);
            vipAccount.preUpdate();
            vipAccountDao.update(vipAccount);





            dao.insert(teachin);
            orderDao.insert(order);

            /*//添加发票
            OrderInvoices invoices=new OrderInvoices();
            invoices.setAccountId(account.getId());
            invoices.setCompanyId(companyhr.getCompanyId());
            invoices.setOrderId(order.getId());
            invoices.setStatus(OrderInvoices.STATUS_WAIT);
            invoices.preInsert();
            orderInvoicesDao.insert(invoices);*/

            Map map = new HashMap();
            map.put("orderId", order.getId());
            return map;




        }
		/*//需要在成功时插入
		dao.insert(teachin);
		orderDao.insert(order);*/

		/*InteractionTeachin interactionTeachin=new InteractionTeachin();
		AccountCompanyhr accountCompanyhr=accountCompanyhrDao.getByAccountId(account.getId());

		interactionTeachin.setCompanyId(accountCompanyhr.getCompanyId());
		PageHelper.startPage(1,10);
		return new PageInfo<InteractionTeachin>(dao.findList(interactionTeachin));
*/
        //获取与支付订单号回执：商户号该产品权限未开通，请前往商户平台>产品中心检查后重试
        Unifiedorder unifiedorder = new Unifiedorder();
        unifiedorder.setAppid(wxAppId);

        unifiedorder.setMch_id(Mch_id);
        unifiedorder.setNonce_str(IdGen.wxRandom(32));
        unifiedorder.setBody("易校招(企业)-宣讲");
        //unifiedorder.setBody("宣讲");

        unifiedorder.setOut_trade_no(order.getId());
        //unifiedorder.setOut_trade_no(IdGen.uuid());
        unifiedorder.setAttach(type + "," + teachin.getId());
        unifiedorder.setTotal_fee(String.valueOf((int) (order.getPayment() * 100)));//单位分

        unifiedorder.setSpbill_create_ip(request.getRemoteAddr());
        unifiedorder.setTime_expire(DateUtils.addOneMin());//失效时间订单失效时间，格式为yyyyMMddHHmmss，
        unifiedorder.setNotify_url(notify_url);
        //unifiedorder.setNotify_url("http://pyywm6.natappfree.cc/schoolappserver/api/teachin/teachinPayNotify");
        unifiedorder.setTrade_type("APP");

        UnifiedorderResult unifiedorderResult = PayMchAPI.payUnifiedorder(unifiedorder, key);
        System.err.println(unifiedorderResult);
        System.err.println("获取与支付订单号回执：" + unifiedorderResult.getReturn_msg());

        if ("SUCCESS".equals(unifiedorderResult.getResult_code()) && "SUCCESS".equals(unifiedorderResult.getReturn_code())) {


            //需要在成功时插入
            dao.insert(teachin);
            orderDao.insert(order);

            Map map = new HashMap();
            map.put("pay", PayUtil.generateMchPayAppRequestJson(unifiedorderResult.getPrepay_id(), Mch_id, wxAppId, key));
            map.put("orderId", order.getId());
            System.err.println(map.get("pay"));
            return map;

        } else {
            //回滚事务

            //TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); //事务回滚


            return "获取预支付订单号失败";
        }


    }

    public Object teachinData(String teachinId) {

        List<InteractionTeachinData> list = interactionTeachinDataDao.findList(new InteractionTeachinData(new InteractionTeachin(teachinId)));
		/*List urls=new ArrayList();*/
        for (InteractionTeachinData i : list) {
            String[] s = ((String) (i.getUrl())).split("\\|");


            List n = new ArrayList();
            for (String ss : s) {
                if (!Strings.isNullOrEmpty(ss)) {
                    n.add(ss);
                }
            }
            i.setUrl(n);
        }
        return list;
    }

    @Transactional(readOnly = false)
    public void teachinPayNotify(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //获取请求数据
        String xmlData = StreamUtils.copyToString(request.getInputStream(), Charset.forName("utf-8"));
        //将XML转为MAP,确保所有字段都参与签名验证
        Map<String, String> mapData = XMLConverUtil.convertToMap(xmlData);
        //转换数据对象
        MchPayNotify payNotify = XMLConverUtil.convertToObject(MchPayNotify.class, xmlData);
        //已处理 去重
        if (payNotify == null || expireKey.exists(payNotify.getTransaction_id())) {
            return;
        } else {
            //签名验证
            if (SignatureUtil.validateSign(mapData, key)) {
                expireKey.add(payNotify.getTransaction_id());
                Order order = orderDao.getLock(payNotify.getOut_trade_no());
				/*if(Integer.valueOf(order.getStatus())>2){
					return;
				}*/
                //更新宣讲
                String[] str = payNotify.getAttach().split(",");
                String teachinId = str[1];
                InteractionTeachin teachin = dao.get(teachinId);
                teachin.setStatus(InteractionTeachin.STATUS_SOON);
                teachin.preUpdate();
                dao.update(teachin);
                order.setStatus(Order.STATUS_PAYED);
                order.setPayTime(new Date());
                order.setTransactionId(payNotify.getTransaction_id());
                order.preUpdate();
                orderDao.update(order);

              /*  //添加发票
                OrderInvoices invoices=new OrderInvoices();
                invoices.setAccountId(order.getAccountId());
                invoices.setCompanyId(order.getCompanyId());
                invoices.setOrderId(order.getId());
                invoices.setStatus(OrderInvoices.STATUS_WAIT);
                invoices.preInsert();
                orderInvoicesDao.insert(invoices);*/

                MchBaseResult baseResult = new MchBaseResult();
                baseResult.setReturn_code("SUCCESS");
                baseResult.setReturn_msg("OK");

                response.getOutputStream().write(XMLConverUtil.convertToXML(baseResult).getBytes());
            } else {
                MchBaseResult baseResult = new MchBaseResult();
                baseResult.setReturn_code("FAIL");
                baseResult.setReturn_msg("ERROR");
                response.getOutputStream().write(XMLConverUtil.convertToXML(baseResult).getBytes());
            }
        }
    }

    public List<String> isUsed(String schoolId, String date) {
        List<SchoolTeachinOcupy> list = teachinOcupyDao.isUsed(schoolId, DateUtils.parseDate(date));
        List<String> list1 = new ArrayList<>();
        for (int a = 1; a < 7; a++) {
            boolean flag=true;
            for (SchoolTeachinOcupy s : list) {
                if(s.getTime().equals(String.valueOf(a))){
                    list1.add(s.getTime());
                    flag=false;
                }
            }
            if(flag){
                list1.add("");
            }

        }
        return list1;
    }
    public  SchoolTeachinOcupy isUsedDateTime(String schoolId, Date date,String time) {
        return teachinOcupyDao.isUsedDateTime(schoolId, date,time);


    }
}