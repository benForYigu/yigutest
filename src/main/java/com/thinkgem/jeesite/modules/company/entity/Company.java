/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.company.entity;

import com.thinkgem.jeesite.modules.account.entity.Account;
import com.thinkgem.jeesite.modules.account.entity.accounthr.AccountCompanyhr;
import com.thinkgem.jeesite.modules.company.entity.address.CompanyAddress;
import com.thinkgem.jeesite.modules.company.entity.auth.CompanyAuth;
import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * 企业基本信息Entity
 * @author 李金辉
 * @version 2019-01-29
 */
public class Company extends DataEntity<Company> {
	
	private static final long serialVersionUID = 1L;
	public static final String STATUS_YES = "1";
	public static final String STATUS_NO = "2";
	public  static final String  	AUTH_YES = "1";
	public  static final String  	AUTH_NO = "2";
	private String name;		// 全称
	private String shortName;		// 简称
	private String contact;		// 联系人
	private String contactPhone;		// 联系人手机
	private String email;		// 邮箱
	private String logo;		// logo
	private Object industry;		// 行业
	private String webSite;		// 企业网址
	private String address;		// 企业地址
	private String video;		// 公司视频
	private Object img;		// 公司照片
	private Object scale;		// 企业规模
	private String businessLicence;		// 营业执照
	private String intruduce;		// 营业执照
	private Object status;		// 认证状态
	private Object nature;		// 性质

	private String industryName;		// 行业名称
	private List<CompanyAddress> addressList=new ArrayList<CompanyAddress>();		// 行业地址
	private AccountCompanyhr companyhr;

	public AccountCompanyhr getCompanyhr() {
		return companyhr;
	}

	public void setCompanyhr(AccountCompanyhr companyhr) {
		this.companyhr = companyhr;
	}

	public List<CompanyAddress> getAddressList() {
		return addressList;
	}

	public void setAddressList(List<CompanyAddress> addressList) {
		this.addressList = addressList;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getIndustryName() {
		return industryName;
	}

	public void setIndustryName(String industryName) {
		this.industryName = industryName;
	}

	public String getIntruduce() {
		return intruduce;
	}

	public void setIntruduce(String intruduce) {
		this.intruduce = intruduce;
	}

	private CompanyAuth auth;		// 认证信息
	private Account account;		// 登录信息

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public Object getNature() {
		return nature;
	}

	public void setNature(Object nature) {
		this.nature = nature;
	}

	public CompanyAuth getAuth() {
		return auth;
	}

	public void setAuth(CompanyAuth auth) {
		this.auth = auth;
	}

	public Company() {
		super();
	}

	public Company(String id){
		super(id);
	}

	@Length(min=0, max=255, message="全称长度必须介于 0 和 255 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=0, max=255, message="简称长度必须介于 0 和 255 之间")
	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}
	
	@Length(min=0, max=255, message="联系人长度必须介于 0 和 255 之间")
	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}
	
	@Length(min=0, max=255, message="联系人手机长度必须介于 0 和 255 之间")
	public String getContactPhone() {
		return contactPhone;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}
	
	@Length(min=0, max=255, message="邮箱长度必须介于 0 和 255 之间")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}


	public Object getIndustry() {
		return industry;
	}

	public void setIndustry(Object industry) {
		this.industry = industry;
	}

	@Length(min=0, max=255, message="企业网址长度必须介于 0 和 255 之间")
	public String getWebSite() {
		return webSite;
	}

	public void setWebSite(String webSite) {
		this.webSite = webSite;
	}

	public String getVideo() {
		return video;
	}

	public void setVideo(String video) {
		this.video = video;
	}

	public Object getImg() {
		return img;
	}

	public void setImg(Object img) {
		this.img = img;
	}


	public Object getScale() {
		return scale;
	}

	public void setScale(Object scale) {
		this.scale = scale;
	}


	public String getBusinessLicence() {
		return businessLicence;
	}

	public void setBusinessLicence(String businessLicence) {
		this.businessLicence = businessLicence;
	}


	public Object getStatus() {
		return status;
	}

	public void setStatus(Object status) {
		this.status = status;
	}


}