/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.resume.entity.other;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 简历其他Entity
 * @author 李金辉
 * @version 2019-01-28
 */
public class StudentResumeOther extends DataEntity<StudentResumeOther> {
	
	private static final long serialVersionUID = 1L;
	private String studentId;		// student_id
	private String skill;		// 技能
	private String selfEvaluation;		// 自评
	private String certificate;		// 证书

	public StudentResumeOther() {
		super();
	}

	public StudentResumeOther(String id){
		super(id);
	}

	public String getCertificate() {
		return certificate;
	}

	public void setCertificate(String certificate) {
		this.certificate = certificate;
	}

	@Length(min=0, max=100, message="student_id长度必须介于 0 和 100 之间")
	public String getStudentId() {
		return studentId;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}
	
	@Length(min=0, max=255, message="技能长度必须介于 0 和 255 之间")
	public String getSkill() {
		return skill;
	}

	public void setSkill(String skill) {
		this.skill = skill;
	}
	
	@Length(min=0, max=150, message="自评长度必须介于 0 和 150 之间")
	public String getSelfEvaluation() {
		return selfEvaluation;
	}

	public void setSelfEvaluation(String selfEvaluation) {
		this.selfEvaluation = selfEvaluation;
	}
	
}