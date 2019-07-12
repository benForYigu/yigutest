/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.resume.service.experience;

import java.util.List;

import com.google.common.base.Strings;
import com.thinkgem.jeesite.API.entity.Code;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.IdGen;
import com.thinkgem.jeesite.modules.account.entity.Account;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.resume.entity.experience.StudentResumeExperience;
import com.thinkgem.jeesite.modules.resume.dao.experience.StudentResumeExperienceDao;

/**
 * 简历工作经历Service
 * @author 李金辉
 * @version 2019-01-28
 */
@Service
@Transactional(readOnly = true)
public class StudentResumeExperienceService extends CrudService<StudentResumeExperienceDao, StudentResumeExperience> {

	public StudentResumeExperience get(String id) {
		return super.get(id);
	}
	
	public List<StudentResumeExperience> findList(StudentResumeExperience studentResumeExperience) {
		return super.findList(studentResumeExperience);
	}
	
	public Page<StudentResumeExperience> findPage(Page<StudentResumeExperience> page, StudentResumeExperience studentResumeExperience) {
		return super.findPage(page, studentResumeExperience);
	}
	
	@Transactional(readOnly = false)
	public void save(StudentResumeExperience studentResumeExperience) {
		super.save(studentResumeExperience);
	}
	
	@Transactional(readOnly = false)
	public void delete(StudentResumeExperience studentResumeExperience) {
		super.delete(studentResumeExperience);
	}

	@Transactional(readOnly = false)
    public Object uExperience(Account account, String id, String companyName, String startDate, String endDate,String content) {
		StudentResumeExperience studentResumeExperience = dao.get(id);
		if (studentResumeExperience == null ) {
			return Code.API_EXPERIENCEID_ERROR;
		}
		if (!account.getId().equals(studentResumeExperience.getStudentId())) {
			return Code.API_USER_ROLE_ERROR;
		}
		studentResumeExperience.setCompanyName(companyName);
		studentResumeExperience.setStartDate(DateUtils.parseDate(startDate));
		studentResumeExperience.setEndDate(DateUtils.parseDate(endDate));
		studentResumeExperience.setContent(content);
		studentResumeExperience.preUpdate();
		dao.update(studentResumeExperience);
		return Code.SUCCESS;
	}

	@Transactional(readOnly = false)
	public Object experience(Account account, String companyName, String startDate, String endDate,String content) {
		if (!Account.ROLE_STUDENT.equals(account.getRole())) {
			return Code.API_USER_ROLE_ERROR;
		}
		StudentResumeExperience studentResumeExperience = new StudentResumeExperience();
		studentResumeExperience.setStudentId(account.getId());
		studentResumeExperience.setCompanyName(companyName);
		studentResumeExperience.setStartDate(DateUtils.parseDate(startDate));
		studentResumeExperience.setEndDate(DateUtils.parseDate(endDate));
		studentResumeExperience.setContent(content);
		studentResumeExperience.preInsert();
		dao.insert(studentResumeExperience);
		return Code.SUCCESS;
	}

	//获取工作经历
    public Object gexperience(Account account, String experienceId) {
		if(Strings.isNullOrEmpty(experienceId)){
			StudentResumeExperience experience=new StudentResumeExperience();
			experience.setStudentId(account.getId());
			return dao.findList(experience);
		}else{
			return dao.get(experienceId);
		}

    }
}