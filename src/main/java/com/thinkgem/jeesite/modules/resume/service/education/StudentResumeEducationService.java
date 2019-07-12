/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.resume.service.education;

import java.util.List;

import com.google.common.base.Strings;
import com.thinkgem.jeesite.API.entity.Code;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.IdGen;
import com.thinkgem.jeesite.modules.account.entity.Account;
import com.thinkgem.jeesite.modules.resume.entity.experience.StudentResumeExperience;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.resume.entity.education.StudentResumeEducation;
import com.thinkgem.jeesite.modules.resume.dao.education.StudentResumeEducationDao;

/**
 * 简历教育经历Service
 * @author 李金辉
 * @version 2019-01-28
 */
@Service
@Transactional(readOnly = true)
public class StudentResumeEducationService extends CrudService<StudentResumeEducationDao, StudentResumeEducation> {

	public StudentResumeEducation get(String id) {
		return super.get(id);
	}
	
	public List<StudentResumeEducation> findList(StudentResumeEducation studentResumeEducation) {
		return super.findList(studentResumeEducation);
	}
	
	public Page<StudentResumeEducation> findPage(Page<StudentResumeEducation> page, StudentResumeEducation studentResumeEducation) {
		return super.findPage(page, studentResumeEducation);
	}
	
	@Transactional(readOnly = false)
	public void save(StudentResumeEducation studentResumeEducation) {
		super.save(studentResumeEducation);
	}
	
	@Transactional(readOnly = false)
	public void delete(StudentResumeEducation studentResumeEducation) {
		super.delete(studentResumeEducation);
	}

	@Transactional(readOnly = false)
	public Object uEducation(Account account, String id, String schoolName,String educationBackground,String major, String startDate, String endDate,String content) {
		StudentResumeEducation studentResumeEducation = dao.get(id);
		if (studentResumeEducation == null) {
			return Code.API_PARRAM_ERROR;
		}
		if ( !account.getId().equals(studentResumeEducation.getStudentId())) {
			return Code.API_PARRAM_ERROR;
		}
		studentResumeEducation.setSchoolName(schoolName);
		studentResumeEducation.setStartDate(DateUtils.parseDate(startDate));
		studentResumeEducation.setEndDate(DateUtils.parseDate(endDate));
		studentResumeEducation.setEducationBackground(educationBackground);
		studentResumeEducation.setMajor(major);
		studentResumeEducation.setContent(content);
		studentResumeEducation.preUpdate();
		dao.update(studentResumeEducation);
		return Code.SUCCESS;
	}

	@Transactional(readOnly = false)
	public Object education(Account account, String schoolName,String educationBackground,String major, String startDate, String endDate,String content) {
		if (!Account.ROLE_STUDENT.equals(account.getRole())) {
			return Code.API_USER_ROLE_ERROR;
		}
		StudentResumeEducation studentResumeEducation = new StudentResumeEducation();
		studentResumeEducation.setStudentId(account.getId());
		studentResumeEducation.setSchoolName(schoolName);
		studentResumeEducation.setStartDate(DateUtils.parseDate(startDate));
		studentResumeEducation.setEndDate(DateUtils.parseDate(endDate));
		studentResumeEducation.setEducationBackground(educationBackground);
		studentResumeEducation.setMajor(major);
		studentResumeEducation.setContent(content);
		studentResumeEducation.preInsert();
		dao.insert(studentResumeEducation);
		return Code.SUCCESS;
	}

	//获取教育经历
    public Object geducation(Account account, String educationId) {
			if(Strings.isNullOrEmpty(educationId)){
				StudentResumeEducation education=new StudentResumeEducation();
				education.setStudentId(account.getId());
				return dao.findList(education);
			}else{
				return dao.get(educationId);
			}
    }
}