/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.resume.dao.active;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.resume.entity.active.StudentResumeSchoolActive;

/**
 * 简历活动DAO接口
 * @author 李金辉
 * @version 2019-05-06
 */
@MyBatisDao
public interface StudentResumeSchoolActiveDao extends CrudDao<StudentResumeSchoolActive> {
	
}