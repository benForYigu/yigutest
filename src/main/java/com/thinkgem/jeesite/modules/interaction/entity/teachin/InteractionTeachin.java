/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.interaction.entity.teachin;

import com.thinkgem.jeesite.modules.company.entity.Company;
import com.thinkgem.jeesite.modules.school.entity.School;
import org.hibernate.validator.constraints.Length;

import java.util.Arrays;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.List;
import com.google.common.collect.Lists;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 宣讲Entity
 * @author 李金辉
 * @version 2019-01-24
 */
public class InteractionTeachin extends DataEntity<InteractionTeachin> {
	
	private static final long serialVersionUID = 1L;
	public static final String STATUS_PUBLIC_NOT_PAY = "-1";//状态-1对公支付未支付 0微信支付未支付 1即将进行 2进行中 3已结束 4被叫停
	public static final String STATUS_NOT_PAY = "0";
	public static final String STATUS_SOON = "1";
	public static final String STATUS_ING = "2";
	public static final String STATUS_END = "3";
	public static final String STATUS_STOP = "4";
	public static final String SHELF_ON = "1";
	public static final String SHELF_OFF = "2";
	private String companyId;		// 企业id
	private String accountId;		// 宣讲人id
	private String schoolId;		// 学校id
	private String profession;		// 相关职位

	private String img;		// 图片
	private String title;		// 宣讲名称
	private Date date;		// 宣讲日期
	private Object time;		// 宣讲时间段
	private String content;		// 宣讲内容
	private String shelf;		// 上架下架
	private Object status;		// 状态
	private List<InteractionTeachinData> interactionTeachinDataList = Lists.newArrayList();		// 子表列表

	private Company company;		//
	private String  companyName;		//
	private School school;		//
	private String schoolName;		//
	private String schoolLogo;		//
	private String[] professionArray ;		// jsp回显

	public String getShelf() {
		return shelf;
	}

	public void setShelf(String shelf) {
		this.shelf = shelf;
	}

	public String[] getProfessionArray() {
		return professionArray;
	}

	public void setProfessionArray(String[] professionArray) {
		this.professionArray = professionArray;
	}

	public String getSchoolName() {
		return schoolName;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}

	public String getSchoolLogo() {
		return schoolLogo;
	}

	public void setSchoolLogo(String schoolLogo) {
		this.schoolLogo = schoolLogo;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public School getSchool() {
		return school;
	}

	public void setSchool(School school) {
		this.school = school;
	}



	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public InteractionTeachin() {
		super();
	}

	public InteractionTeachin(String id){
		super(id);
	}

	@Length(min=0, max=255, message="企业id长度必须介于 0 和 255 之间")
	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	
	@Length(min=0, max=255, message="宣讲人id长度必须介于 0 和 255 之间")
	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	
	@Length(min=0, max=255, message="学校id长度必须介于 0 和 255 之间")
	public String getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(String schoolId) {
		this.schoolId = schoolId;
	}
	

	public String getProfession() {
		return profession;
	}

	public void setProfession(String profession) {
		this.profession = profession;
	}
	

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}
	
	@Length(min=0, max=255, message="宣讲名称长度必须介于 0 和 255 之间")
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	

	public Object getTime() {
		return time;
	}

	public void setTime(Object time) {
		this.time = time;
	}
	
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	

	public Object getStatus() {
		return status;
	}

	public void setStatus(Object status) {
		this.status = status;
	}
	
	public List<InteractionTeachinData> getInteractionTeachinDataList() {
		return interactionTeachinDataList;
	}

	public void setInteractionTeachinDataList(List<InteractionTeachinData> interactionTeachinDataList) {
		this.interactionTeachinDataList = interactionTeachinDataList;
	}

}