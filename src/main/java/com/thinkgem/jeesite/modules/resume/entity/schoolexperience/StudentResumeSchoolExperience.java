/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.resume.entity.schoolexperience;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 简历校园经历Entity
 * @author 李金辉
 * @version 2019-05-05
 */
public class StudentResumeSchoolExperience extends DataEntity<StudentResumeSchoolExperience> {
	
	private static final long serialVersionUID = 1L;
	private String studentId;		// student_id
	private Date startDate;		// 开始时间
	private Date endDate;		// 结束时间
	private String job;		// 职位
	private String jobDescribe;		// 职位描述
	
	public StudentResumeSchoolExperience() {
		super();
	}

	public StudentResumeSchoolExperience(String id){
		super(id);
	}

	@Length(min=0, max=100, message="student_id长度必须介于 0 和 100 之间")
	public String getStudentId() {
		return studentId;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}
	
	@JsonFormat(pattern = "yyyy-MM")
	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	
	@JsonFormat(pattern = "yyyy-MM")
	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	@Length(min=0, max=255, message="职位长度必须介于 0 和 255 之间")
	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}
	
	@Length(min=0, max=255, message="职位描述长度必须介于 0 和 255 之间")
	public String getJobDescribe() {
		return jobDescribe;
	}

	public void setJobDescribe(String jobDescribe) {
		this.jobDescribe = jobDescribe;
	}
	
}