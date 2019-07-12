/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.order.entity.vip;

import com.thinkgem.jeesite.modules.account.entity.accounthr.AccountCompanyhr;
import com.thinkgem.jeesite.modules.orderinvoices.entity.OrderInvoices;
import com.thinkgem.jeesite.modules.vip.entity.Vip;
import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * vip订单Entity
 * @author 李金辉
 * @version 2019-03-23
 */
public class OrderVip extends DataEntity<OrderVip> {
	
	private static final long serialVersionUID = 1L;
	public static final String PAY_ONLINE = "1";
	public static final String PAY_UNDER = "4";
	private String companyId;		// 企业id
	private String accountId;		// 用户id
	private String vipId;		// 用户id
	private Double payment;		// 订单金额
	private Date payTime;		// 支付时间
	private Date endTime;		// 完成时间
	private String transactionId;		// 交易号
	private Object payType;		// 购买方式
	private Object status;		// 支付状态
	private String voucher;		// 支付凭证
	private Vip vip;		// 支付状态


	private AccountCompanyhr companyhr;
	private String invoicesId;  //发票id
	private OrderInvoices orderInvoices;  //发票id

	public String getVoucher() {
		return voucher;
	}

	public void setVoucher(String voucher) {
		this.voucher = voucher;
	}

	public OrderInvoices getOrderInvoices() {
		return orderInvoices;
	}

	public void setOrderInvoices(OrderInvoices orderInvoices) {
		this.orderInvoices = orderInvoices;
	}
	public String getInvoicesId() {
		return invoicesId;
	}

	public void setInvoicesId(String invoicesId) {
		this.invoicesId = invoicesId;
	}
	public AccountCompanyhr getCompanyhr() {
		return companyhr;
	}

	public void setCompanyhr(AccountCompanyhr companyhr) {
		this.companyhr = companyhr;
	}

	public String getVipId() {
		return vipId;
	}

	public void setVipId(String vipId) {
		this.vipId = vipId;
	}

	public Vip getVip() {
		return vip;
	}

	public void setVip(Vip vip) {
		this.vip = vip;
	}

	public OrderVip() {
		super();
	}

	public OrderVip(String id){
		super(id);
	}

	@Length(min=0, max=100, message="企业id长度必须介于 0 和 100 之间")
	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	
	@Length(min=0, max=100, message="用户id长度必须介于 0 和 100 之间")
	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	
	public Double getPayment() {
		return payment;
	}

	public void setPayment(Double payment) {
		this.payment = payment;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getPayTime() {
		return payTime;
	}

	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	
	@Length(min=0, max=100, message="交易号长度必须介于 0 和 100 之间")
	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	

	public Object getPayType() {
		return payType;
	}

	public void setPayType(Object payType) {
		this.payType = payType;
	}
	

	public Object getStatus() {
		return status;
	}

	public void setStatus(Object status) {
		this.status = status;
	}
	
}