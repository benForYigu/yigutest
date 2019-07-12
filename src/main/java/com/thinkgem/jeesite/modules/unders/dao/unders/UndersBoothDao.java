/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.unders.dao.unders;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.unders.entity.unders.UndersBooth;

/**
 * 线下活动DAO接口
 * @author 李金辉
 * @version 2019-05-24
 */
@MyBatisDao
public interface UndersBoothDao extends CrudDao<UndersBooth> {
	
}