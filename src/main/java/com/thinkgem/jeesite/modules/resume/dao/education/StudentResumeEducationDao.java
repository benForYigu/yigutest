/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.resume.dao.education;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.resume.entity.education.StudentResumeEducation;

/**
 * 简历教育经历DAO接口
 * @author 李金辉
 * @version 2019-01-28
 */
@MyBatisDao
public interface StudentResumeEducationDao extends CrudDao<StudentResumeEducation> {
	
}