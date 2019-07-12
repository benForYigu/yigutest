/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.interaction.entity.interview;

import com.thinkgem.jeesite.modules.account.entity.accountstudent.AccountStudentinfo;
import com.thinkgem.jeesite.modules.company.entity.Company;
import com.thinkgem.jeesite.modules.company.entity.profession.CompanyProfession;
import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 面试Entity
 * @author 李金辉
 * @version 2019-01-24
 */
public class InteractionInterview extends DataEntity<InteractionInterview> {
	
	private static final long serialVersionUID = 1L;
	public  static final String  INTERVIEW_PHONE = "1";// 1电话 2视频
	public  static final String  INTERVIEW_ONLINE = "2";
	public  static final String  STATUS_ACT = "1";//1投递中2待面试 3协商中 4拒绝  5人才库 6已面试
	public  static final String  STATUS_WAIT = "2";
	public  static final String  STATUS_COMU = "3";
	public  static final String  STATUS_END = "4";
	public  static final String  STATUS_TALENT = "5";
	public  static final String  STATUS_FINISH = "6";
	private String studentId;		// 学生id
	private String companyId;		// 企业id
	private String companyProfessionId;		// 投递职位
	private String teachinId;		// 宣讲id
	private Object interviewType;		// 面试方式
	private Date interviewTime;		// 面试时间
	private Object interviewStatus;		// 面试状态
	private Object interviewStep;		// 初试/复试
	private AccountStudentinfo student;		// 学生
	private Company company;		//

	private String companyName;		//
	private String companyLogo;		//
	private String professionName;		// 专业名称

	public String getTeachinId() {
		return teachinId;
	}

	public void setTeachinId(String teachinId) {
		this.teachinId = teachinId;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCompanyLogo() {
		return companyLogo;
	}

	public void setCompanyLogo(String companyLogo) {
		this.companyLogo = companyLogo;
	}

	public String getProfessionName() {
		return professionName;
	}

	public void setProfessionName(String professionName) {
		this.professionName = professionName;
	}

	public AccountStudentinfo getStudent() {
		return student;
	}

	public void setStudent(AccountStudentinfo student) {
		this.student = student;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public InteractionInterview() {
		super();
	}

	public InteractionInterview(String id){
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
	
	@Length(min=0, max=100, message="投递职位长度必须介于 0 和 100 之间")
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
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getInterviewTime() {
		return interviewTime;
	}

	public void setInterviewTime(Date interviewTime) {
		this.interviewTime = interviewTime;
	}
	

	public Object getInterviewStatus() {
		return interviewStatus;
	}

	public void setInterviewStatus(Object interviewStatus) {
		this.interviewStatus = interviewStatus;
	}
	

	public Object getInterviewStep() {
		return interviewStep;
	}

	public void setInterviewStep(Object interviewStep) {
		this.interviewStep = interviewStep;
	}
	
}