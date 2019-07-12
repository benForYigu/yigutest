/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.interaction.entity.offer;

import com.thinkgem.jeesite.modules.account.entity.accountstudent.AccountStudentinfo;
import com.thinkgem.jeesite.modules.company.entity.Company;
import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * offerEntity
 * @author 李金辉
 * @version 2019-01-24
 */
public class InteractionOffer extends DataEntity<InteractionOffer> {
	
	private static final long serialVersionUID = 1L;
	public static final String STATUS_WAIT = "1";
	public static final String STATUS_FIRST_SURE = "2";
	public static final String STATUS_LAST_SURE = "3";
	public static final String STATUS_REFUSE = "4";
	private String interviewId;		// intervew_id
	private String studentId;		// 学生id
	private String companyId;		// 企业id
	private String department;		// 部门
	private String position;		// 职位
	private Date entryTime;		// 入职时间
	private Object province;		// 省份
	private Object city;		// 城市
	private String workAddress;		// 工作地址
	private String signAddress;		// 报道地址
	private Object salary;		// 薪水
	private String notice;		// 注意事项
	private Object status;		// 确认状态
	private String agreement;		// 协议
	private AccountStudentinfo student;		// 学生
	private Company company;		//

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
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

	public InteractionOffer() {
		super();
	}

	public InteractionOffer(String id){
		super(id);
	}

	public String getInterviewId() {
		return interviewId;
	}

	public void setInterviewId(String interviewId) {
		this.interviewId = interviewId;
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
	
	@Length(min=0, max=100, message="职位长度必须介于 0 和 100 之间")
	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getEntryTime() {
		return entryTime;
	}

	public void setEntryTime(Date entryTime) {
		this.entryTime = entryTime;
	}
	

	public Object getProvince() {
		return province;
	}

	public void setProvince(Object province) {
		this.province = province;
	}
	

	public Object getCity() {
		return city;
	}

	public void setCity(Object city) {
		this.city = city;
	}


	public String getWorkAddress() {
		return workAddress;
	}

	public void setWorkAddress(String workAddress) {
		this.workAddress = workAddress;
	}

	public String getSignAddress() {
		return signAddress;
	}

	public void setSignAddress(String signAddress) {
		this.signAddress = signAddress;
	}

	public Object getSalary() {
		return salary;
	}

	public void setSalary(Object salary) {
		this.salary = salary;
	}
	
	@Length(min=0, max=500, message="注意事项长度必须介于 0 和 500 之间")
	public String getNotice() {
		return notice;
	}

	public void setNotice(String notice) {
		this.notice = notice;
	}
	

	public Object getStatus() {
		return status;
	}

	public void setStatus(Object status) {
		this.status = status;
	}
	
	@Length(min=0, max=255, message="协议长度必须介于 0 和 255 之间")
	public String getAgreement() {
		return agreement;
	}

	public void setAgreement(String agreement) {
		this.agreement = agreement;
	}
	
}