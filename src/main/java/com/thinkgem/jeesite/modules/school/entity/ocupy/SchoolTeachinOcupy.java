/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.school.entity.ocupy;

import com.thinkgem.jeesite.modules.school.entity.School;
import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 学校宣讲时间Entity
 * @author 李金辉
 * @version 2019-04-12
 */
public class SchoolTeachinOcupy extends DataEntity<SchoolTeachinOcupy> {
	
	private static final long serialVersionUID = 1L;
	private String schoolId;		// 学校名称
	private Date date;		// 日期


	private String time;		// 宣讲时间段
	private School school;		//  学校

	private Integer flag;		//  1购买 2占用



	private Date endDate;		// 日期
	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public Integer getFlag() {
		return flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}

	public SchoolTeachinOcupy() {
		super();
	}

	public SchoolTeachinOcupy(String id){
		super(id);
	}

	public School getSchool() {
		return school;
	}

	public void setSchool(School school) {
		this.school = school;
	}

	@Length(min=0, max=100, message="学校名称长度必须介于 0 和 100 之间")
	public String getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(String schoolId) {
		this.schoolId = schoolId;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}
	
}