/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.order.entity.order;

import com.thinkgem.jeesite.modules.account.entity.accounthr.AccountCompanyhr;
import com.thinkgem.jeesite.modules.interaction.entity.teachin.InteractionTeachin;
import com.thinkgem.jeesite.modules.orderinvoices.entity.OrderInvoices;
import com.thinkgem.jeesite.modules.school.entity.School;
import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 订单Entity
 * @author 李金辉
 * @version 2019-03-09
 */
public class Order extends DataEntity<Order> {
	
	private static final long serialVersionUID = 1L;
	public static final String PAY_TYPE_VIP = "1";
	public static final String PAY_TYPE_NOR = "2";
	public static final String PAY_TYPE_DE = "3";
	public static final String PAY_TYPE_PUBLIC = "4";
	public static final String STATUS_WAIT = "1";//待支付 已关闭 已支付 已完成
	public static final String STATUS_CLOSE = "2";
	public static final String STATUS_PAYED = "3";
	//public static final String STATUS_COMP = "4";
	public static final String STATUS_NOT = "5";
	//public static final String STATUS_NOT_AUTH = "6";

	private String teachinId;		// 宣讲id
	private String companyId;		// 企业id
	private String accountId;		// 用户id
	private Double payment;		// 订单金额,单位分
	private Date payTime;		// 支付时间
	private Date endTime;		// 完成时间
	private String transactionId;		// transaction_id
	private Object payType;		// 购买方式
	private Object status;		// 状态
	private String voucher;		// 凭证

	private InteractionTeachin teachin;		// 宣讲
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

	public InteractionTeachin getTeachin() {
		return teachin;
	}

	public void setTeachin(InteractionTeachin teachin) {
		this.teachin = teachin;
	}

	public Order() {
		super();
	}

	public Order(String id){
		super(id);
	}

	@Length(min=0, max=100, message="宣讲id长度必须介于 0 和 100 之间")
	public String getTeachinId() {
		return teachinId;
	}

	public void setTeachinId(String teachinId) {
		this.teachinId = teachinId;
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
	
}