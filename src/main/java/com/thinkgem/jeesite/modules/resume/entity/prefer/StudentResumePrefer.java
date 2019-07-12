/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.resume.entity.prefer;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 简历意向Entity
 * @author 李金辉
 * @version 2019-01-28
 */
public class StudentResumePrefer extends DataEntity<StudentResumePrefer> {
	
	private static final long serialVersionUID = 1L;
	private String studentId;		// student_id
	private Object positionId;		// 行业
	private Object professionId;		// 职位
	private Object city;		// 工作城市
	private Object salary;		// salary
	
	public StudentResumePrefer() {
		super();
	}

	public StudentResumePrefer(String id){
		super(id);
	}

	@Length(min=1, max=100, message="student_id长度必须介于 1 和 100 之间")
	public String getStudentId() {
		return studentId;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}
	

	public Object getPositionId() {
		return positionId;
	}

	public void setPositionId(Object positionId) {
		this.positionId = positionId;
	}
	

	public Object getProfessionId() {
		return professionId;
	}

	public void setProfessionId(Object professionId) {
		this.professionId = professionId;
	}
	

	public Object getCity() {
		return city;
	}

	public void setCity(Object city) {
		this.city = city;
	}
	

	public Object getSalary() {
		return salary;
	}

	public void setSalary(Object salary) {
		this.salary = salary;
	}
	
}