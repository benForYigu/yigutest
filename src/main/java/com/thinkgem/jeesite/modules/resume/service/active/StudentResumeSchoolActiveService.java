/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.resume.service.active;

import java.util.List;

import com.google.common.base.Strings;
import com.thinkgem.jeesite.API.entity.Code;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.modules.account.entity.Account;
import com.thinkgem.jeesite.modules.resume.entity.schoolexperience.StudentResumeSchoolExperience;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.resume.entity.active.StudentResumeSchoolActive;
import com.thinkgem.jeesite.modules.resume.dao.active.StudentResumeSchoolActiveDao;

/**
 * 简历活动Service
 * @author 李金辉
 * @version 2019-05-06
 */
@Service
@Transactional(readOnly = true)
public class StudentResumeSchoolActiveService extends CrudService<StudentResumeSchoolActiveDao, StudentResumeSchoolActive> {

	public StudentResumeSchoolActive get(String id) {
		return super.get(id);
	}
	
	public List<StudentResumeSchoolActive> findList(StudentResumeSchoolActive studentResumeSchoolActive) {
		return super.findList(studentResumeSchoolActive);
	}
	
	public Page<StudentResumeSchoolActive> findPage(Page<StudentResumeSchoolActive> page, StudentResumeSchoolActive studentResumeSchoolActive) {
		return super.findPage(page, studentResumeSchoolActive);
	}
	
	@Transactional(readOnly = false)
	public void save(StudentResumeSchoolActive studentResumeSchoolActive) {
		super.save(studentResumeSchoolActive);
	}
	
	@Transactional(readOnly = false)
	public void delete(StudentResumeSchoolActive studentResumeSchoolActive) {
		super.delete(studentResumeSchoolActive);
	}

	@Transactional(readOnly = false)
    public Object active(Account account, String startDate, String endDate, String name, String content) {
		if (!Account.ROLE_STUDENT.equals(account.getRole())) {
			return Code.API_USER_ROLE_ERROR;
		}
		StudentResumeSchoolActive active = new StudentResumeSchoolActive();
		active.setStudentId(account.getId());
		active.setStartDate(DateUtils.parseDate(startDate));
		active.setEndDate(DateUtils.parseDate(endDate));
		active.setName(name);
		active.setContent(content);
		active.preInsert();
		dao.insert(active);
		return Code.SUCCESS;
	}

	@Transactional(readOnly = false)
	public Object gactive(Account account, String activeId) {
		if (Strings.isNullOrEmpty(activeId)) {
			StudentResumeSchoolActive active = new StudentResumeSchoolActive();
			active.setStudentId(account.getId());
			return dao.findList(active);
		} else {
			return dao.get(activeId);
		}
	}

	@Transactional(readOnly = false)
	public Object uactive(Account account, String activeId, String startDate, String endDate, String name, String content) {
		StudentResumeSchoolActive active = dao.get(activeId);
		if (active == null) {
			return Code.API_PARRAM_ERROR;
		}
		if (!account.getId().equals(active.getStudentId())) {
			return Code.API_PARRAM_ERROR;
		}
		active.setStartDate(DateUtils.parseDate(startDate));
		active.setEndDate(DateUtils.parseDate(endDate));
		active.setName(name);
		active.setContent(content);
		active.preUpdate();
		dao.update(active);
		return Code.SUCCESS;
	}
}