/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.resume.service.schoolexperience;

import java.util.List;

import com.google.common.base.Strings;
import com.thinkgem.jeesite.API.entity.Code;
import com.thinkgem.jeesite.API.util.leancloud.aoyi.DateUtil;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.modules.account.entity.Account;
import com.thinkgem.jeesite.modules.resume.entity.prefer.StudentResumePrefer;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.resume.entity.schoolexperience.StudentResumeSchoolExperience;
import com.thinkgem.jeesite.modules.resume.dao.schoolexperience.StudentResumeSchoolExperienceDao;

/**
 * 简历校园经历Service
 * @author 李金辉
 * @version 2019-05-05
 */
@Service
@Transactional(readOnly = true)
public class StudentResumeSchoolExperienceService extends CrudService<StudentResumeSchoolExperienceDao, StudentResumeSchoolExperience> {

	public StudentResumeSchoolExperience get(String id) {
		return super.get(id);
	}
	
	public List<StudentResumeSchoolExperience> findList(StudentResumeSchoolExperience studentResumeSchoolExperience) {
		return super.findList(studentResumeSchoolExperience);
	}
	
	public Page<StudentResumeSchoolExperience> findPage(Page<StudentResumeSchoolExperience> page, StudentResumeSchoolExperience studentResumeSchoolExperience) {
		return super.findPage(page, studentResumeSchoolExperience);
	}
	
	@Transactional(readOnly = false)
	public void save(StudentResumeSchoolExperience studentResumeSchoolExperience) {
		super.save(studentResumeSchoolExperience);
	}
	
	@Transactional(readOnly = false)
	public void delete(StudentResumeSchoolExperience studentResumeSchoolExperience) {
		super.delete(studentResumeSchoolExperience);
	}

	@Transactional(readOnly = false)
    public Object uSchoolExperience(Account account, String schoolExperienceId, String startDate, String endDate, String job, String jobDescribe) {
		StudentResumeSchoolExperience schoolExperience = dao.get(schoolExperienceId);
		if (schoolExperience == null) {
			return Code.API_PARRAM_ERROR;
		}
		if (!account.getId().equals(schoolExperience.getStudentId())) {
			return Code.API_PARRAM_ERROR;
		}
		schoolExperience.setStartDate(DateUtils.parseDate(startDate));
		schoolExperience.setEndDate(DateUtils.parseDate(endDate));
		schoolExperience.setJob(job);
		schoolExperience.setJobDescribe(jobDescribe);
		schoolExperience.preUpdate();
		dao.update(schoolExperience);
		return Code.SUCCESS;
	}


	public Object gschoolExperience(Account account, String schoolExperienceId) {
		if (Strings.isNullOrEmpty(schoolExperienceId)) {
			StudentResumeSchoolExperience schoolExperience = new StudentResumeSchoolExperience();
			schoolExperience.setStudentId(account.getId());
			return dao.findList(schoolExperience);
		} else {
			return dao.get(schoolExperienceId);
		}
	}

	@Transactional(readOnly = false)
	public Object schoolExperience(Account account, String startDate, String endDate, String job, String jobDescribe) {
		if (!Account.ROLE_STUDENT.equals(account.getRole())) {
			return Code.API_USER_ROLE_ERROR;
		}
		StudentResumeSchoolExperience schoolExperience = new StudentResumeSchoolExperience();
		schoolExperience.setStudentId(account.getId());
		schoolExperience.setStartDate(DateUtils.parseDate(startDate));
		schoolExperience.setEndDate(DateUtils.parseDate(endDate));
		schoolExperience.setJob(job);
		schoolExperience.setJobDescribe(jobDescribe);

		schoolExperience.preInsert();
		dao.insert(schoolExperience);
		return Code.SUCCESS;
	}


}