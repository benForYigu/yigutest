/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.unders.entity.companyfile;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

import javax.validation.constraints.NotNull;

/**
 * 企业双选会资料Entity
 * @author 李金辉
 * @version 2019-05-27
 */
public class UndersCompanyFile extends DataEntity<UndersCompanyFile> {
	
	private static final long serialVersionUID = 1L;
	private String companyId;		// 企业

	private String companyName;		// 企业
	private String email;		// 名称
	private String contact1;		// 联系人1
	private String phone1;		// 联系方式1
	private String contact2;		// 联系人2
	private String phone2;		// 联系方式2
	private String content;		// 招聘简章
	private String license;		// 执照

	@NotNull
	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public UndersCompanyFile() {
		super();
	}

	public UndersCompanyFile(String id){
		super(id);
	}

	@Length(min=1, max=100, message="企业长度必须介于 1 和 100 之间")
	@NotNull
	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	@NotNull
	@Length(min=1, max=100, message="名称长度必须介于 0 和 100 之间")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	@NotNull
	@Length(min=1, max=20, message="联系人1长度必须介于 0 和 20 之间")
	public String getContact1() {
		return contact1;
	}

	public void setContact1(String contact1) {
		this.contact1 = contact1;
	}
	@NotNull
	@Length(min=1, max=255, message="联系方式1长度必须介于 0 和 255 之间")
	public String getPhone1() {
		return phone1;
	}

	public void setPhone1(String phone1) {
		this.phone1 = phone1;
	}
	

	public String getContact2() {
		return contact2;
	}

	public void setContact2(String contact2) {
		this.contact2 = contact2;
	}
	

	public String getPhone2() {
		return phone2;
	}

	public void setPhone2(String phone2) {
		this.phone2 = phone2;
	}
	@NotNull
	@Length(min=1, max=1000, message="招聘简章长度必须介于 0 和 1000 之间")
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	@NotNull
	@Length(min=1, max=1000, message="执照长度必须介于 0 和 1000 之间")
	public String getLicense() {
		return license;
	}

	public void setLicense(String license) {
		this.license = license;
	}
	
}