/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.school.dao.notice;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.school.entity.notice.SchoolNotice;

/**
 * 学习公告DAO接口
 * @author 李金辉
 * @version 2019-01-29
 */
@MyBatisDao
public interface SchoolNoticeDao extends CrudDao<SchoolNotice> {
	
}