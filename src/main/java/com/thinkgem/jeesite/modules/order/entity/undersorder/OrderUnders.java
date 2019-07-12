/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.order.entity.undersorder;

import com.thinkgem.jeesite.modules.account.entity.accounthr.AccountCompanyhr;
import com.thinkgem.jeesite.modules.orderinvoices.entity.OrderInvoices;
import com.thinkgem.jeesite.modules.unders.entity.unders.Unders;
import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 双选会订单Entity
 * @author 李金辉
 * @version 2019-05-29
 */
public class OrderUnders extends DataEntity<OrderUnders> {
	
	private static final long serialVersionUID = 1L;
	public static final String STATUS_WAIT = "1";//待支付 已关闭 已支付
	public static final String STATUS_CLOSE = "2";
	public static final String STATUS_PAYED = "3";
	public static final String PAY_TYPE_WX = "2";
	public static final String PAY_TYPE_PUB = "4";
	private String undersId;		// 活动id
	private String companyId;		// 企业id
	private String accountId;		// 用户id
	private Double payment;		// 订单金额
	private Date payTime;		// 支付时间
	private Date endTime;		// 完成时间
	private String transactionId;		// transaction_id
	private Object payType;		// 购买方式 2直接购买  4凭证
	private Object status;		// 1，待支付 2 已关闭 3已支付 4已完成 5未支付 6对公待审核
	private String voucher;		// 凭证


	private Unders unders;
	private AccountCompanyhr companyhr;
	private String invoicesId;  //发票id
	private OrderInvoices orderInvoices;  //发票id

	public Unders getUnders() {
		return unders;
	}

	public void setUnders(Unders unders) {
		this.unders = unders;
	}

	public AccountCompanyhr getCompanyhr() {
		return companyhr;
	}

	public void setCompanyhr(AccountCompanyhr companyhr) {
		this.companyhr = companyhr;
	}

	public String getInvoicesId() {
		return invoicesId;
	}

	public void setInvoicesId(String invoicesId) {
		this.invoicesId = invoicesId;
	}

	public OrderInvoices getOrderInvoices() {
		return orderInvoices;
	}

	public void setOrderInvoices(OrderInvoices orderInvoices) {
		this.orderInvoices = orderInvoices;
	}

	public OrderUnders() {
		super();
	}

	public OrderUnders(String id){
		super(id);
	}

	@Length(min=0, max=100, message="活动id长度必须介于 0 和 100 之间")
	public String getUndersId() {
		return undersId;
	}

	public void setUndersId(String undersId) {
		this.undersId = undersId;
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
	
	@Length(min=0, max=100, message="transaction_id长度必须介于 0 和 100 之间")
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
	
	@Length(min=0, max=255, message="凭证长度必须介于 0 和 255 之间")
	public String getVoucher() {
		return voucher;
	}

	public void setVoucher(String voucher) {
		this.voucher = voucher;
	}
	
}