/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.testuser.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.testuser.entity.TestUserinfo;

/**
 * 测试类DAO接口
 * @author Owen
 * @version 2017-10-18
 */
@MyBatisDao
public interface TestUserinfoDao extends CrudDao<TestUserinfo> {
	
}