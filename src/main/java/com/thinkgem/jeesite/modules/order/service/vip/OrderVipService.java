/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.order.service.vip;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.IdGen;
import com.thinkgem.jeesite.modules.account.dao.accounthr.AccountCompanyhrDao;
import com.thinkgem.jeesite.modules.account.entity.Account;
import com.thinkgem.jeesite.modules.account.entity.accounthr.AccountCompanyhr;
import com.thinkgem.jeesite.modules.company.entity.Company;
import com.thinkgem.jeesite.modules.interaction.entity.teachin.InteractionTeachin;
import com.thinkgem.jeesite.modules.order.entity.order.Order;
import com.thinkgem.jeesite.modules.orderinvoices.dao.OrderInvoicesDao;
import com.thinkgem.jeesite.modules.orderinvoices.entity.OrderInvoices;
import com.thinkgem.jeesite.modules.vip.dao.VipDao;
import com.thinkgem.jeesite.modules.vip.dao.accountvip.VipAccountDao;
import com.thinkgem.jeesite.modules.vip.entity.Vip;
import com.thinkgem.jeesite.modules.vip.entity.accountvip.VipAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.order.entity.vip.OrderVip;
import com.thinkgem.jeesite.modules.order.dao.vip.OrderVipDao;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.Action;

/**
 * vip订单Service
 * @author 李金辉
 * @version 2019-03-23
 */
@Service
@Transactional(readOnly = true)
public class OrderVipService extends CrudService<OrderVipDao, OrderVip> {

	@Autowired
	VipDao vipDao;
	@Autowired
	AccountCompanyhrDao companyhrDao;
	@Autowired
	VipAccountDao vipAccountDao;
	@Autowired
	OrderInvoicesDao orderInvoicesDao;
	@Value("${weixin.Mch_key}")
	private String key;
	@Value("${weixin.wxAppId}")
	private String wxAppId;
	@Value("${weixin.Mch_id}")
	private String Mch_id;
	@Value("${weixin.vip_notify_url}")
	private String vip_notify_url;
	//重复通知过滤
	private static ExpireKey expireKey = new DefaultExpireKey();
	@Transactional(readOnly = false)
	public OrderVip get(String id) {
		delExpire();
		/*OrderVip orderVip=super.get(id);
		orderVip.setVip(vipDao.get(orderVip.getVipId()));
		return orderVip;*/
		return super.get(id);
	}
	@Transactional(readOnly = false)
	public List<OrderVip> findList(OrderVip orderVip) {
		delExpire();
		/*List<OrderVip> list=super.findList(orderVip);
		for (OrderVip o: list) {
			o.setVip(vipDao.get(o.getVipId()));
		}
		return list;*/
		return super.findList(orderVip);
	}
	@Transactional(readOnly = false)
	public Page<OrderVip> findPage(Page<OrderVip> page, OrderVip orderVip) {
		delExpire();


		return super.findPage(page, orderVip);
	}
	
	@Transactional(readOnly = false)
	public void save(OrderVip orderVip) {
		super.save(orderVip);
	}
	
	@Transactional(readOnly = false)
	public void delete(OrderVip orderVip) {
		super.delete(orderVip);
	}

	@Transactional(readOnly = false)
    public Object pay(HttpServletRequest request,Account account, String vipId,String type) {
		Vip vip=vipDao.get(vipId);
		if(vip==null){
			return Code.API_VIP_NOT_EXIST;
		}
		AccountCompanyhr companyhr=companyhrDao.getByAccountId(account.getId());

		//更高级vip
		/*if(companyhr.getVip()!=null){
			Vip newVip=vipDao.get(vipId);
			Vip oldVip=vipDao.get(companyhr.getVip());
			if(newVip.getPrice()<=oldVip.getPrice()){
				return Code.API_VIP_PAYED;
			}
		}*/
		//判断是否有未支付订单
		delExpire();
			//查询未支付对公vip订单
		if(dao.selectByUsers(account.getId()).size()>0){
			return Code.API_PAY_VIP_UNDERS;
		}
		//判断用户宣讲是否用完
		VipAccount vipAccount=vipAccountDao.selectByAccountId(account.getId());
		if(vipAccount!=null&&vipAccount.getTeachin()>0){
			return Code.API_PAY_TEACHIN_TIMES;
		}
		OrderVip orderVip=new OrderVip();
		orderVip.setAccountId(account.getId());
		orderVip.setVipId(vipId);
		orderVip.setCompanyId(companyhr.getCompanyId());
		orderVip.setPayment(vip.getPrice());
		orderVip.setStatus(Order.STATUS_WAIT);
		orderVip.setPayType(OrderVip.PAY_ONLINE);
		orderVip.preInsert();
		orderVip.setId(IdGen.randomBase62(20));
		//对公支付
		if("2".equals(type)){
			orderVip.setPayType(OrderVip.PAY_UNDER);
			dao.insert(orderVip);
			Map map = new HashMap();
			map.put("orderId", orderVip.getId());
			return map;
		}

		/*===================*/
		Unifiedorder unifiedorder = new Unifiedorder();
		unifiedorder.setAppid(wxAppId);

		unifiedorder.setMch_id(Mch_id);

		unifiedorder.setNonce_str(IdGen.wxRandom(32));
		unifiedorder.setBody("易校招(企业)-vip");
		//unifiedorder.setBody("宣讲");

		unifiedorder.setOut_trade_no(orderVip.getId());
		//unifiedorder.setOut_trade_no(IdGen.uuid());
		unifiedorder.setAttach(vipId);
		unifiedorder.setTotal_fee(String.valueOf((int)(orderVip.getPayment()*100)));//单位分
		logger.info(String.valueOf((orderVip.getPayment()*100)));
		unifiedorder.setSpbill_create_ip(request.getRemoteAddr());
		//unifiedorder.setTime_expire(DateUtils.addOneMin());//失效时间订单失效时间，格式为yyyyMMddHHmmss，
		unifiedorder.setNotify_url(vip_notify_url);
		//unifiedorder.setNotify_url("http://pyywm6.natappfree.cc/schoolappserver/api/vip/notify");
		unifiedorder.setTrade_type("APP");
		//unifiedorder.setTrade_type("MWEB");

		UnifiedorderResult unifiedorderResult = PayMchAPI.payUnifiedorder(unifiedorder, key);
		System.err.println(unifiedorderResult);
		System.err.println("获取与支付订单号回执：" + unifiedorderResult.getReturn_msg());

		if ("SUCCESS".equals(unifiedorderResult.getResult_code()) && "SUCCESS".equals(unifiedorderResult.getReturn_code())) {


			//需要在成功时插入
			dao.insert(orderVip);

			Map map = new HashMap();
			map.put("pay", PayUtil.generateMchPayAppRequestJson(unifiedorderResult.getPrepay_id(),Mch_id, wxAppId, key));
			map.put("orderId",orderVip.getId());
			System.err.println(map.get("pay"));
			return map;

		} else {
			//回滚事务

			//TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); //事务回滚


			return "获取预支付订单号失败";
		}



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
				OrderVip orderVip = dao.getLock(payNotify.getOut_trade_no());
				/*if(Integer.valueOf(order.getStatus())>2){
					return;
				}*/
				Vip vip=vipDao.get(payNotify.getAttach());
				//更新用户
				AccountCompanyhr companyhr=companyhrDao.getByAccountId(orderVip.getAccountId());
				companyhr.setVip(orderVip.getVipId());
				companyhr.setVipName(vip.getName());
				companyhr.preUpdate();
				companyhrDao.update(companyhr);
				VipAccount vipAccount=vipAccountDao.selectByAccountId(orderVip.getAccountId());

				if(vipAccount==null) {
					vipAccount=new VipAccount(vip);
					vipAccount.setAccountId(orderVip.getAccountId());
					vipAccount.preInsert();
					vipAccountDao.insert(vipAccount);
					orderVip.setStatus(Order.STATUS_PAYED);
					orderVip.setPayTime(new Date());
					orderVip.setTransactionId(payNotify.getTransaction_id());
					orderVip.setCompanyId(companyhr.getCompanyId());
					orderVip.preUpdate();
					dao.update(orderVip);
				}else {
					if("2".equals(vipAccount.getOffCampusInformation())){
						vipAccount.setOffCampusInformation((String) vip.getOffCampusInformation());
					}
					if("2".equals(vipAccount.getProfession())){
						vipAccount.setProfession((String)vip.getProfession());
					}
					if("2".equals(vipAccount.getUnderTeachinRead())){
						vipAccount.setUnderTeachinRead((String)vip.getUnderTeachinRead());
					}
					if("2".equals(vipAccount.getUnderTeachinSign())){
						vipAccount.setUnderTeachinSign((String)vip.getUnderTeachinSign());
					}
					if("2".equals(vipAccount.getDoubleRead())){
						vipAccount.setDoubleRead((String)vip.getDoubleRead());
					}
					if("2".equals(vipAccount.getDoubleSign())){
						vipAccount.setDoubleSign((String)vip.getDoubleSign());
					}
					if("2".equals(vipAccount.getExchangeMeeting())){
						vipAccount.setExchangeMeeting((String)vip.getExchangeMeeting());
					}

					vipAccount.setTeachin(vipAccount.getTeachin()+vip.getTeachin());
					vipAccount.setVideo(vipAccount.getVideo()+vip.getVideo());
					vipAccount.setInterviewTime(vipAccount.getInterviewTime()+vip.getInterviewTime());
					vipAccount.preUpdate();
					vipAccountDao.update(vipAccount);
				}
/*
				//添加发票
				OrderInvoices invoices=new OrderInvoices();
				invoices.setAccountId(orderVip.getAccountId());
				invoices.setCompanyId(orderVip.getCompanyId());
				invoices.setOrderId(orderVip.getId());
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
	public void delExpire() {
		dao.delExpire();
		dao.updateStatus();
	}
}