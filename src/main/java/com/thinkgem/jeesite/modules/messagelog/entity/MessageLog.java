/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.messagelog.entity;

import com.thinkgem.jeesite.modules.account.dao.accountstudent.AccountStudentinfoDao;
import com.thinkgem.jeesite.modules.account.entity.accounthr.AccountCompanyhr;
import com.thinkgem.jeesite.modules.account.entity.accountstudent.AccountStudentinfo;
import com.thinkgem.jeesite.modules.company.entity.Company;
import com.thinkgem.jeesite.modules.company.entity.profession.CompanyProfession;
import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 消息记录Entity
 * @author 李金辉
 * @version 2019-01-28
 */
public class MessageLog extends DataEntity<MessageLog> {
	
	private static final long serialVersionUID = 1L;
	public static final String ISREAD_YES = "0";//1是 0否
	public static final String ISREAD_NO = "1";
	public static final String TYPE_1 = "1";//1投递、2发起面试、3同意面试 4已面试  5发放offer
	public static final String TYPE_2 = "2";
	public static final String TYPE_3 = "3";
	public static final String TYPE_4 = "4";
	public static final String TYPE_5 = "5";

	private String studentId;		// 学生id
	private String companyId;		// 企业id
	private Object type;		// 类型
	private String companyProfessionId;		// 职位
	private String teachinId;		// 宣讲id
	private String interviewId;		// 面试id
	private Object interviewType;		// 面试方式
	private Date interviewTime;		// 面试时间
	private Integer changeTime;		// 时间更改次数
	private Object isRead;		// 是否已读 1是 0否
	private String department;		// 是否已读 1是 0否
	private String offerId;		//




	private Company company;
	private AccountCompanyhr companyhr;
	private CompanyProfession profession;
	private AccountStudentinfo student;

	public String getOfferId() {
		return offerId;
	}

	public void setOfferId(String offerId) {
		this.offerId = offerId;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getTeachinId() {
		return teachinId;
	}

	public void setTeachinId(String teachinId) {
		this.teachinId = teachinId;
	}

	public AccountStudentinfo getStudent() {
		return student;
	}

	public void setStudent(AccountStudentinfo student) {
		this.student = student;
	}

	private String groupFlag;		// sql分组

	public String getInterviewId() {
		return interviewId;
	}

	public void setInterviewId(String interviewId) {
		this.interviewId = interviewId;
	}

	public String getGroupFlag() {
		return groupFlag;
	}

	public void setGroupFlag(String groupFlag) {
		this.groupFlag = groupFlag;
	}

	public CompanyProfession getProfession() {
		return profession;
	}

	public void setProfession(CompanyProfession profession) {
		this.profession = profession;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}


	public AccountCompanyhr getCompanyhr() {
		return companyhr;
	}

	public void setCompanyhr(AccountCompanyhr companyhr) {
		this.companyhr = companyhr;
	}

	public MessageLog() {
		super();
	}

	public MessageLog(String id){
		super(id);
	}

	@Length(min=0, max=100, message="学生id长度必须介于 0 和 100 之间")
	public String getStudentId() {
		return studentId;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}
	
	@Length(min=0, max=100, message="企业id长度必须介于 0 和 100 之间")
	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	

	public Object getType() {
		return type;
	}

	public void setType(Object type) {
		this.type = type;
	}
	
	@Length(min=0, max=100, message="职位长度必须介于 0 和 100 之间")
	public String getCompanyProfessionId() {
		return companyProfessionId;
	}

	public void setCompanyProfessionId(String companyProfessionId) {
		this.companyProfessionId = companyProfessionId;
	}
	

	public Object getInterviewType() {
		return interviewType;
	}

	public void setInterviewType(Object interviewType) {
		this.interviewType = interviewType;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm")
	public Date getInterviewTime() {
		return interviewTime;
	}

	public void setInterviewTime(Date interviewTime) {
		this.interviewTime = interviewTime;
	}
	
	public Integer getChangeTime() {
		return changeTime;
	}

	public void setChangeTime(Integer changeTime) {
		this.changeTime = changeTime;
	}
	

	public Object getIsRead() {
		return isRead;
	}

	public void setIsRead(Object isRead) {
		this.isRead = isRead;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm")
	@Override
	public Date getCreateDate() {
		return createDate;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm")
	@Override
	public Date getUpdateDate() {
		return updateDate;
	}
}