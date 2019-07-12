/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.company.entity.auth;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 企业认证信息Entity
 * @author 李金辉
 * @version 2019-01-29
 */
public class CompanyAuth extends DataEntity<CompanyAuth> {
	
	private static final long serialVersionUID = 1L;
	public  static final String AUTH_STATUS_NORMAL = "1";
	public  static final String AUTH_STATUS_LOCK = "2";
	private String companyId;		// company_id
	private String logo;		// logo
	private String industry;		// 行业
	private String webSite;		// 企业网址
	private String video;		// 公司视频
	private String img;		// 公司照片
	private String scale;		// 企业规模
	private String businessLicence;		// 营业执照
	private String status;		// 认证状态
	
	public CompanyAuth() {
		super();
	}

	public CompanyAuth(String id){
		super(id);
	}


	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	
	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}
	

	public String getIndustry() {
		return industry;
	}

	public void setIndustry(String industry) {
		this.industry = industry;
	}
	

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
	
	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}
	

	public String getScale() {
		return scale;
	}

	public void setScale(String scale) {
		this.scale = scale;
	}
	

	public String getBusinessLicence() {
		return businessLicence;
	}

	public void setBusinessLicence(String businessLicence) {
		this.businessLicence = businessLicence;
	}
	

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
}