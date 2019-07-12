/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.orderinvoices.entity;

import org.activiti.engine.test.mock.NoOpServiceTasks;
import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

import javax.validation.constraints.NotNull;

/**
 * 企业发票Entity
 * @author 李金辉
 * @version 2019-04-19
 */
public class OrderInvoices extends DataEntity<OrderInvoices> {
	
	private static final long serialVersionUID = 1L;
	public static final String STATUS_ING = "1";//已申请
	public static final String STATUS_OK= "2";//已开票

	private String accountId;		// 用户id
	private String companyId;		// 企业id
	private String orderId;		// 订单id
	private String name;		// 联系人
	private String phone;		// 联系人电话
	private String address;		// 联系人地址
	private String taxcode;		// 税号
	private String companyName;		// 单位名称
	private String companyAddress;		// 单位地址
	private String companyPhone;		// 单位电话
	private String bank;		// 开户银行
	private String bankCard;		// 银行卡号
	private String img;		// 银行卡号
	private Object status;		// 银行卡号

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public Object getStatus() {
		return status;
	}

	public void setStatus(Object status) {
		this.status = status;
	}

	public OrderInvoices() {
		super();
	}

	public OrderInvoices(String id){
		super(id);
	}


	@Length(min=1, max=100, message="用户id长度必须介于 1 和 100 之间")
	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	@Length(min=1, max=100, message="企业id长度必须介于 1 和 100 之间")
	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	@NotNull
	@Length(min=1, max=100, message="订单id长度必须介于 1 和 100 之间")
	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	@NotNull
	@Length(min=1, max=255, message="联系人长度必须介于 1 和 255 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	@NotNull
	@Length(min=1, max=20, message="联系人电话长度必须介于 1 和 20 之间")
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	@NotNull
	@Length(min=1, max=255, message="联系人地址长度必须介于 1 和 255 之间")
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	@Length(min=0, max=100, message="税号长度必须介于 0 和 100 之间")
	public String getTaxcode() {
		return taxcode;
	}

	public void setTaxcode(String taxcode) {
		this.taxcode = taxcode;
	}
	
	@Length(min=0, max=100, message="单位名称长度必须介于 0 和 100 之间")
	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	
	@Length(min=0, max=500, message="单位地址长度必须介于 0 和 500 之间")
	public String getCompanyAddress() {
		return companyAddress;
	}

	public void setCompanyAddress(String companyAddress) {
		this.companyAddress = companyAddress;
	}
	
	@Length(min=0, max=20, message="单位电话长度必须介于 0 和 20 之间")
	public String getCompanyPhone() {
		return companyPhone;
	}

	public void setCompanyPhone(String companyPhone) {
		this.companyPhone = companyPhone;
	}
	
	@Length(min=0, max=100, message="开户银行长度必须介于 0 和 100 之间")
	public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}
	
	@Length(min=0, max=100, message="银行卡号长度必须介于 0 和 100 之间")
	public String getBankCard() {
		return bankCard;
	}

	public void setBankCard(String bankCard) {
		this.bankCard = bankCard;
	}
	
}