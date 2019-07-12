/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.resume.service.certificate;

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
import com.thinkgem.jeesite.modules.resume.entity.certificate.StudentResumeSchoolCertificate;
import com.thinkgem.jeesite.modules.resume.dao.certificate.StudentResumeSchoolCertificateDao;

/**
 * 简历证书Service
 * @author 李金辉
 * @version 2019-05-06
 */
@Service
@Transactional(readOnly = true)
public class StudentResumeSchoolCertificateService extends CrudService<StudentResumeSchoolCertificateDao, StudentResumeSchoolCertificate> {

	public StudentResumeSchoolCertificate get(String id) {
		return super.get(id);
	}
	
	public List<StudentResumeSchoolCertificate> findList(StudentResumeSchoolCertificate studentResumeSchoolCertificate) {
		return super.findList(studentResumeSchoolCertificate);
	}
	
	public Page<StudentResumeSchoolCertificate> findPage(Page<StudentResumeSchoolCertificate> page, StudentResumeSchoolCertificate studentResumeSchoolCertificate) {
		return super.findPage(page, studentResumeSchoolCertificate);
	}
	
	@Transactional(readOnly = false)
	public void save(StudentResumeSchoolCertificate studentResumeSchoolCertificate) {
		super.save(studentResumeSchoolCertificate);
	}
	
	@Transactional(readOnly = false)
	public void delete(StudentResumeSchoolCertificate studentResumeSchoolCertificate) {
		super.delete(studentResumeSchoolCertificate);
	}

	@Transactional(readOnly = false)
    public Object certificate(Account account, String startDate, String endDate, String certificateName, String certificateType) {
		if (!Account.ROLE_STUDENT.equals(account.getRole())) {
			return Code.API_USER_ROLE_ERROR;
		}
		StudentResumeSchoolCertificate certificate = new StudentResumeSchoolCertificate();
		certificate.setStudentId(account.getId());
		certificate.setStartDate(DateUtils.parseDate(startDate));
		certificate.setEndDate(DateUtils.parseDate(endDate));
		certificate.setCertificateName(certificateName);
		certificate.setCertificateType(certificateType);

		certificate.preInsert();
		dao.insert(certificate);
		return Code.SUCCESS;
	}

	@Transactional(readOnly = false)
	public Object gcertificate(Account account, String certificateId) {
		if (Strings.isNullOrEmpty(certificateId)) {
			StudentResumeSchoolCertificate certificate = new StudentResumeSchoolCertificate();
			certificate.setStudentId(account.getId());
			return dao.findList(certificate);
		} else {
			return dao.get(certificateId);
		}
	}

	@Transactional(readOnly = false)
	public Code ucertificate(Account account, String schoolExperienceId, String startDate, String endDate, String certificateName, String certificateType) {
		StudentResumeSchoolCertificate certificate = dao.get(schoolExperienceId);
		if (certificate == null) {
			return Code.API_PARRAM_ERROR;
		}
		if (!account.getId().equals(certificate.getStudentId())) {
			return Code.API_PARRAM_ERROR;
		}
		certificate.setStartDate(DateUtils.parseDate(startDate));
		certificate.setEndDate(DateUtils.parseDate(endDate));
		certificate.setCertificateName(certificateName);
		certificate.setCertificateType(certificateType);
		certificate.preUpdate();
		dao.update(certificate);
		return Code.SUCCESS;
	}
}