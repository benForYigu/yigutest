/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.unders.service.sign;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.RecursiveTask;

import com.google.common.base.Strings;
import com.thinkgem.jeesite.API.entity.Code;
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
import com.thinkgem.jeesite.common.utils.IdGen;
import com.thinkgem.jeesite.modules.account.dao.accounthr.AccountCompanyhrDao;
import com.thinkgem.jeesite.modules.account.entity.Account;
import com.thinkgem.jeesite.modules.account.entity.accounthr.AccountCompanyhr;
import com.thinkgem.jeesite.modules.order.dao.undersorder.OrderUndersDao;
import com.thinkgem.jeesite.modules.order.entity.order.Order;
import com.thinkgem.jeesite.modules.order.entity.undersorder.OrderUnders;
import com.thinkgem.jeesite.modules.order.entity.vip.OrderVip;
import com.thinkgem.jeesite.modules.orderinvoices.dao.OrderInvoicesDao;
import com.thinkgem.jeesite.modules.orderinvoices.entity.OrderInvoices;
import com.thinkgem.jeesite.modules.unders.dao.companyfile.UndersCompanyFileDao;
import com.thinkgem.jeesite.modules.unders.dao.unders.UndersBoothDao;
import com.thinkgem.jeesite.modules.unders.dao.unders.UndersDao;
import com.thinkgem.jeesite.modules.unders.entity.companyfile.UndersCompanyFile;
import com.thinkgem.jeesite.modules.unders.entity.unders.Unders;
import com.thinkgem.jeesite.modules.unders.entity.unders.UndersBooth;
import com.thinkgem.jeesite.modules.vip.entity.Vip;
import com.thinkgem.jeesite.modules.vip.entity.accountvip.VipAccount;
import org.aspectj.apache.bcel.generic.RET;
import org.aspectj.apache.bcel.generic.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.unders.entity.sign.UndersSign;
import com.thinkgem.jeesite.modules.unders.dao.sign.UndersSignDao;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * 双选会报名Service
 * @author 李金辉
 * @version 2019-05-24
 */
@Service
@Transactional(readOnly = true)
public class UndersSignService extends CrudService<UndersSignDao, UndersSign> {

	@Autowired
	UndersDao undersDao;
	@Autowired
	UndersBoothDao boothDao;
	@Autowired
	UndersCompanyFileDao companyFileDao;
	@Autowired
	AccountCompanyhrDao companyhrDao;
	@Autowired
	OrderUndersDao orderUndersDao;
	@Autowired
	OrderInvoicesDao orderInvoicesDao;
	@Value("${weixin.Mch_key}")
	private String key;
	@Value("${weixin.wxAppId}")
	private String wxAppId;
	@Value("${weixin.Mch_id}")
	private String Mch_id;
	@Value("${weixin.unders_notify_url}")
	private String unders_notify_url;
	//重复通知过滤
	private static ExpireKey expireKey = new DefaultExpireKey();
	public UndersSign get(String id) {
		return super.get(id);
	}
	public UndersSign selectByOrderId(String OrderId) {
		return dao.selectByOrderId(OrderId);
	}
	@Transactional(readOnly = false)
	public List<UndersSign> findList(UndersSign undersSign) {
		delExpire();
		return super.findList(undersSign);
	}
	@Transactional(readOnly = false)
	public Page<UndersSign> findPage(Page<UndersSign> page, UndersSign undersSign) {
		delExpire();
		return super.findPage(page, undersSign);
	}
	
	@Transactional(readOnly = false)
	public void save(UndersSign undersSign) {
		super.save(undersSign);
	}
	
	@Transactional(readOnly = false)
	public void delete(UndersSign undersSign) {
		super.delete(undersSign);
	}

	@Transactional(readOnly = false)
    public Object sign(HttpServletRequest request, Account account, String undersBoothId,String type) {

		if(!Account.ROLE_COMPANY.equals(account.getRole())){
			return Code.API_USER_ROLE_ERROR;
		}
		AccountCompanyhr companyHr=companyhrDao.getByAccountId(account.getId());
		UndersBooth booth=boothDao.get(undersBoothId);
		if(Strings.isNullOrEmpty(booth.getId())){
			return Code.UNDERS_BOOTH_NULL;
		}
		//判断是否过期
		Unders unders=undersDao.get(booth.getUndersId());
		if(!unders.getSignStart().before(new Date())||!unders.getSignEnd().after(new Date())){
			return Code.UNDERS_TIME_ERROR;
		}
		//判断是席位次数
		delExpire();
		UndersSign undersSign=new UndersSign();
		undersSign.setUndersId(booth.getUndersId().getId());
		if(dao.findList(undersSign).size()>=booth.getNumber()){
			return Code.UNDERS_BOOTH;
		}
		//判断是已报名
		undersSign.setCompanyId(companyHr.getCompanyId());
		if(dao.findList(undersSign).size()>0){
			return Code.UNDERS_BOOTH_SIGNED;
		}
		//判断是否有资料
		UndersCompanyFile companyFile=companyFileDao.selectByCompanyId(companyHr.getCompanyId());
		if(companyFile==null){
			return Code.UNDERS_COMPANY_FILE;
		}
		//生成订单
		OrderUnders orderUnders=new OrderUnders();
		orderUnders.setAccountId(account.getId());
		orderUnders.setCompanyId(companyHr.getCompanyId());
		orderUnders.setPayTime(new Date());
		orderUnders.setPayment(booth.getPrice());
		orderUnders.setUndersId(booth.getUndersId().getId());
		orderUnders.setStatus(OrderUnders.STATUS_WAIT);
		orderUnders.setPayType(OrderUnders.PAY_TYPE_WX);
		orderUnders.preInsert();


		undersSign.setUndersOrderId(orderUnders.getId());
		undersSign.setUndersBoothId(undersBoothId);
		undersSign.setCompanyId(companyHr.getCompanyId());
		undersSign.setCompanyName(companyFile.getCompanyName());
		undersSign.setUndersId(booth.getUndersId().getId());
		undersSign.setContact1(companyFile.getContact1());
		undersSign.setPhone1(companyFile.getPhone1());
		undersSign.setContact2(companyFile.getContact2());
		undersSign.setPhone2(companyFile.getPhone2());
		undersSign.setEmail(companyFile.getEmail());
		undersSign.setContent(companyFile.getContent());
		undersSign.setLicense(companyFile.getLicense());
		undersSign.setStatus(UndersSign.STATUS_WX_WAIT);
		undersSign.preInsert();










		//对公支付
		if("2".equals(type)){
			//需要在成功时插入
			undersSign.setStatus(UndersSign.STATUS_PUB_WAIT);
			dao.insert(undersSign);

			orderUnders.setPayType(OrderUnders.PAY_TYPE_PUB);
			orderUndersDao.insert(orderUnders);
			Map map = new HashMap();
			map.put("orderId", orderUnders.getId());
			return map;
		}

		/*===================*/
		Unifiedorder unifiedorder = new Unifiedorder();
		unifiedorder.setAppid(wxAppId);

		unifiedorder.setMch_id(Mch_id);

		unifiedorder.setNonce_str(IdGen.wxRandom(32));
		unifiedorder.setBody("易校招(企业)-vip");
		//unifiedorder.setBody("宣讲");

		unifiedorder.setOut_trade_no(orderUnders.getId());
		//unifiedorder.setOut_trade_no(IdGen.uuid());
		unifiedorder.setAttach(booth.getName());
		unifiedorder.setTotal_fee(String.valueOf((int)(orderUnders.getPayment()*100)));//单位分
		logger.info(String.valueOf((orderUnders.getPayment()*100)));
		unifiedorder.setSpbill_create_ip(request.getRemoteAddr());
		//unifiedorder.setTime_expire(DateUtils.addOneMin());//失效时间订单失效时间，格式为yyyyMMddHHmmss，
		unifiedorder.setNotify_url(unders_notify_url);
		//unifiedorder.setNotify_url("http://pyywm6.natappfree.cc/schoolappserver/api/vip/notify");
		unifiedorder.setTrade_type("APP");
		//unifiedorder.setTrade_type("MWEB");

		UnifiedorderResult unifiedorderResult = PayMchAPI.payUnifiedorder(unifiedorder, key);
		System.err.println(unifiedorderResult);
		System.err.println("获取与支付订单号回执：" + unifiedorderResult.getReturn_msg());

		if ("SUCCESS".equals(unifiedorderResult.getResult_code()) && "SUCCESS".equals(unifiedorderResult.getReturn_code())) {


			//需要在成功时插入
			dao.insert(undersSign);
			//需要在成功时插入
			orderUndersDao.insert(orderUnders);

			Map map = new HashMap();
			map.put("pay", PayUtil.generateMchPayAppRequestJson(unifiedorderResult.getPrepay_id(),Mch_id, wxAppId, key));
			map.put("orderId",orderUnders.getId());
			System.err.println(map.get("pay"));
			return map;

		} else {
			//回滚事务

			//TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); //事务回滚


			return "获取预支付订单号失败";
		}




	}

	@Transactional(readOnly = false)
	public void delExpire() {
		//超过一分钟未支付
		orderUndersDao.delExpire();
		//对公24小时
		orderUndersDao.updateStatus();
		dao.updateStatus();
		dao.updatePublicStatus();
	}


	@Transactional(readOnly = false)
	public void paynotify(HttpServletRequest request, HttpServletResponse response) throws IOException {
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
				OrderUnders orderUnders = orderUndersDao.get(payNotify.getOut_trade_no());
				orderUnders.setEndTime(new Date());
				orderUnders.setTransactionId(payNotify.getTransaction_id());
				orderUnders.setStatus(OrderUnders.STATUS_PAYED);
				orderUnders.preUpdate();
				orderUndersDao.update(orderUnders);

				UndersSign undersSign=dao.selectByOrderId(orderUnders.getId());
				undersSign.setStatus(UndersSign.STATUS_NORMAL);
				undersSign.preUpdate();
				dao.update(undersSign);

			/*	//添加发票
				OrderInvoices invoices=new OrderInvoices();
				invoices.setAccountId(orderUnders.getAccountId());
				invoices.setCompanyId(orderUnders.getCompanyId());
				invoices.setOrderId(orderUnders.getId());
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
	@Transactional(readOnly = false)
	public Integer rdeleteByOrderId(String orderId) {
		return dao.rdeleteByOrderId(orderId);
	}
}