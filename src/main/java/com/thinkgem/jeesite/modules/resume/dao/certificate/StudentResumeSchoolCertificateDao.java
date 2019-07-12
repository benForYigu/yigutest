/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.resume.dao.certificate;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.resume.entity.certificate.StudentResumeSchoolCertificate;

/**
 * 简历证书DAO接口
 * @author 李金辉
 * @version 2019-05-06
 */
@MyBatisDao
public interface StudentResumeSchoolCertificateDao extends CrudDao<StudentResumeSchoolCertificate> {
	
}