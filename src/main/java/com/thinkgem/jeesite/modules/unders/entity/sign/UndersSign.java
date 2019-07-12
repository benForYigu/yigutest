/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.unders.entity.sign;

import com.thinkgem.jeesite.modules.account.entity.accounthr.AccountCompanyhr;
import com.thinkgem.jeesite.modules.school.entity.School;
import com.thinkgem.jeesite.modules.unders.entity.unders.Unders;
import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 双选会报名Entity
 * @author 李金辉
 * @version 2019-05-24
 */
public class UndersSign extends DataEntity<UndersSign> {
	
	private static final long serialVersionUID = 1L;
	public static final String STATUS_PUB_WAIT = "-1";//对公 微信待支付 正常
	public static final String STATUS_WX_WAIT = "0";
	public static final String STATUS_NORMAL = "1";

	public static final String PAY_TYPE_WX = "2";
	public static final String PAY_TYPE_PUB = "4";
	private String undersOrderId;		// 企业
	private String undersId;		// 双选会id
	private String undersBoothId;		// 双选会id
	private String companyId;		// 企业id
	private String companyName;		// 企业id
	private String email;		// 邮箱
	private String contact1;		// 联系人1
	private String phone1;		// 联系电话1
	private String contact2;		// 联系人2
	private String phone2;		// 联系电话2
	private String content;		// 数量
	private String license;		// 执照
	private String status;		// 执照

	private Unders unders;		// 执照

	private AccountCompanyhr companyhr;		// 执照



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

	public String getUndersOrderId() {
		return undersOrderId;
	}

	public void setUndersOrderId(String undersOrderId) {
		this.undersOrderId = undersOrderId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getUndersBoothId() {
		return undersBoothId;
	}

	public void setUndersBoothId(String undersBoothId) {
		this.undersBoothId = undersBoothId;
	}

	public UndersSign() {
		super();
	}

	public UndersSign(String id){
		super(id);
	}

	@Length(min=0, max=100, message="双选会id长度必须介于 0 和 100 之间")
	public String getUndersId() {
		return undersId;
	}

	public void setUndersId(String undersId) {
		this.undersId = undersId;
	}
	
	@Length(min=1, max=100, message="企业id长度必须介于 1 和 100 之间")
	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	
	@Length(min=0, max=100, message="邮箱长度必须介于 0 和 100 之间")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	@Length(min=0, max=20, message="联系人1长度必须介于 0 和 20 之间")
	public String getContact1() {
		return contact1;
	}

	public void setContact1(String contact1) {
		this.contact1 = contact1;
	}
	
	@Length(min=0, max=255, message="联系电话1长度必须介于 0 和 255 之间")
	public String getPhone1() {
		return phone1;
	}

	public void setPhone1(String phone1) {
		this.phone1 = phone1;
	}
	
	@Length(min=0, max=20, message="联系人2长度必须介于 0 和 20 之间")
	public String getContact2() {
		return contact2;
	}

	public void setContact2(String contact2) {
		this.contact2 = contact2;
	}
	
	@Length(min=0, max=255, message="联系电话2长度必须介于 0 和 255 之间")
	public String getPhone2() {
		return phone2;
	}

	public void setPhone2(String phone2) {
		this.phone2 = phone2;
	}
	
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	@Length(min=0, max=1000, message="执照长度必须介于 0 和 1000 之间")
	public String getLicense() {
		return license;
	}

	public void setLicense(String license) {
		this.license = license;
	}
	
}