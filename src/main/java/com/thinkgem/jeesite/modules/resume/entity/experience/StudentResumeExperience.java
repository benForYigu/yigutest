/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.resume.entity.experience;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 简历工作经历Entity
 * @author 李金辉
 * @version 2019-01-28
 */
public class StudentResumeExperience extends DataEntity<StudentResumeExperience> {
	
	private static final long serialVersionUID = 1L;
	private String studentId;		// student_id
	private Date startDate;		// start_date
	private Date endDate;		// end_date
	private String companyName;		// 企业名称
	private String content;		// 内容

	public StudentResumeExperience() {
		super();
	}

	public StudentResumeExperience(String id){
		super(id);
	}

	@Length(min=0, max=150, message="内容长度必须介于 0 和 150 之间")
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Length(min=0, max=100, message="student_id长度必须介于 0 和 100 之间")
	public String getStudentId() {
		return studentId;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	@Length(min=0, max=255, message="企业名称长度必须介于 0 和 255 之间")
	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	
}