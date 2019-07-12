/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.resume.service.other;

import java.util.List;

import com.thinkgem.jeesite.API.entity.Code;
import com.thinkgem.jeesite.modules.account.entity.Account;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.resume.entity.other.StudentResumeOther;
import com.thinkgem.jeesite.modules.resume.dao.other.StudentResumeOtherDao;

/**
 * 简历其他Service
 * @author 李金辉
 * @version 2019-01-28
 */
@Service
@Transactional(readOnly = true)
public class StudentResumeOtherService extends CrudService<StudentResumeOtherDao, StudentResumeOther> {

	public StudentResumeOther get(String id) {
		return super.get(id);
	}
	
	public List<StudentResumeOther> findList(StudentResumeOther studentResumeOther) {
		return super.findList(studentResumeOther);
	}
	
	public Page<StudentResumeOther> findPage(Page<StudentResumeOther> page, StudentResumeOther studentResumeOther) {
		return super.findPage(page, studentResumeOther);
	}
	
	@Transactional(readOnly = false)
	public void save(StudentResumeOther studentResumeOther) {
		super.save(studentResumeOther);
	}
	
	@Transactional(readOnly = false)
	public void delete(StudentResumeOther studentResumeOther) {
		super.delete(studentResumeOther);
	}

	@Transactional(readOnly = false)
    public Object other(Account account,String type, String skill, String selfEvaluation,String certificate) {
		StudentResumeOther studentResumeOther=dao.getByAccountId(account.getId());
		if(studentResumeOther==null){
			studentResumeOther=new StudentResumeOther();
			if("selfEvaluation".equals(type)){
				studentResumeOther.setSelfEvaluation(selfEvaluation);
			}
			if("skill".equals(type)){
				studentResumeOther.setSkill(skill);
			}
			if("certificate".equals(type)){
				studentResumeOther.setCertificate(certificate);
			}
			studentResumeOther.setStudentId(account.getId());
			studentResumeOther.preInsert();
			dao.insert(studentResumeOther);
			return Code.SUCCESS;
		}else{
			if("selfEvaluation".equals(type)){
				studentResumeOther.setSelfEvaluation(selfEvaluation);
			}
			if("skill".equals(type)){
				studentResumeOther.setSkill(skill);
			}
			if("certificate".equals(type)){
				studentResumeOther.setCertificate(certificate);
			}
			studentResumeOther.preUpdate();
			 dao.update(studentResumeOther);
			return Code.SUCCESS;

		}
    }

	public StudentResumeOther getByAccountId(String id) {
		return dao.getByAccountId(id);
	}
}