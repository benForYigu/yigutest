/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.resume.entity.active;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 简历活动Entity
 * @author 李金辉
 * @version 2019-05-06
 */
public class StudentResumeSchoolActive extends DataEntity<StudentResumeSchoolActive> {
	
	private static final long serialVersionUID = 1L;
	private String studentId;		// student_id
	private Date startDate;		// 开始时间
	private Date endDate;		// 结束时间
	private String name;		// 职位
	private String content;		// 职位描述
	
	public StudentResumeSchoolActive() {
		super();
	}

	public StudentResumeSchoolActive(String id){
		super(id);
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
	
	@Length(min=0, max=255, message="职位长度必须介于 0 和 255 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=0, max=150, message="职位描述长度必须介于 0 和 150 之间")
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
}