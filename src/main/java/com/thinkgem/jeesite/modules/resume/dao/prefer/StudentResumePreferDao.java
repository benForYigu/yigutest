/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.resume.dao.prefer;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.resume.entity.prefer.StudentResumePrefer;

/**
 * 简历意向DAO接口
 * @author 李金辉
 * @version 2019-01-28
 */
@MyBatisDao
public interface StudentResumePreferDao extends CrudDao<StudentResumePrefer> {
	
}