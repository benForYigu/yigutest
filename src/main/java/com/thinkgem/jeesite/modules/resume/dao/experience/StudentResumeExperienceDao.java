/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.resume.dao.experience;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.resume.entity.experience.StudentResumeExperience;

/**
 * 简历工作经历DAO接口
 * @author 李金辉
 * @version 2019-01-28
 */
@MyBatisDao
public interface StudentResumeExperienceDao extends CrudDao<StudentResumeExperience> {
	
}