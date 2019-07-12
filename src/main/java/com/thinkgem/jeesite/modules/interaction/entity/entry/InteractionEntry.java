/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.interaction.entity.entry;

import com.thinkgem.jeesite.modules.account.entity.accountstudent.AccountStudentinfo;
import com.thinkgem.jeesite.modules.account.web.accountstudent.AccountStudentinfoController;
import com.thinkgem.jeesite.modules.company.entity.Company;
import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 入职秀Entity
 * @author 李金辉
 * @version 2019-01-24
 */
public class InteractionEntry extends DataEntity<InteractionEntry> {
	
	private static final long serialVersionUID = 1L;
	private String studentId;		// 学生姓名
	private String companyId;		// 学校名称
	private String conpanyName;		// 投递企业
	private String entryBranch;		// 入职部门
	private Date entryTime;		// 入职时间
	private String area;		// 地点
	private Date signTime;		// 签到时间
	private String signContent;		// 签到内容

	private AccountStudentinfo student;		// 学生
	private Company company;		//

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

	public InteractionEntry() {
		super();
	}

	public InteractionEntry(String id){
		super(id);
	}

	@Length(min=0, max=255, message="学生姓名长度必须介于 0 和 255 之间")
	public String getStudentId() {
		return studentId;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}
	
	@Length(min=0, max=255, message="学校名称长度必须介于 0 和 255 之间")
	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	
	@Length(min=0, max=255, message="投递企业长度必须介于 0 和 255 之间")
	public String getConpanyName() {
		return conpanyName;
	}

	public void setConpanyName(String conpanyName) {
		this.conpanyName = conpanyName;
	}
	
	@Length(min=0, max=100, message="入职部门长度必须介于 0 和 100 之间")
	public String getEntryBranch() {
		return entryBranch;
	}

	public void setEntryBranch(String entryBranch) {
		this.entryBranch = entryBranch;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getEntryTime() {
		return entryTime;
	}

	public void setEntryTime(Date entryTime) {
		this.entryTime = entryTime;
	}
	
	@Length(min=0, max=255, message="地点长度必须介于 0 和 255 之间")
	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getSignTime() {
		return signTime;
	}

	public void setSignTime(Date signTime) {
		this.signTime = signTime;
	}
	
	public String getSignContent() {
		return signContent;
	}

	public void setSignContent(String signContent) {
		this.signContent = signContent;
	}
	
}