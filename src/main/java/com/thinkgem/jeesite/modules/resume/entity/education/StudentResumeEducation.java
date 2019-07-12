/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.resume.entity.education;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 简历教育经历Entity
 * @author 李金辉
 * @version 2019-01-28
 */
public class StudentResumeEducation extends DataEntity<StudentResumeEducation> {
	
	private static final long serialVersionUID = 1L;
	private String studentId;		// student_id
	private Date startDate;		// start_date
	private Date endDate;		// end_date
	private String schoolName;		// 学校名称
	private Object educationBackground;		// 教育背景
	private Object major;		// 专业
	private String content;		// neir

	public Object getMajor() {
		return major;
	}

	public void setMajor(Object major) {
		this.major = major;
	}

	public Object getEducationBackground() {
		return educationBackground;
	}

	public void setEducationBackground(Object educationBackground) {
		this.educationBackground = educationBackground;
	}

	public StudentResumeEducation() {
		super();
	}

	public StudentResumeEducation(String id){
		super(id);
	}

	@Length(min=0, max=150, message="内容描述长度必须介于 0 和 150 之间")
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
	
	@Length(min=0, max=255, message="学校名称长度必须介于 0 和 255 之间")
	public String getSchoolName() {
		return schoolName;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}
	
}