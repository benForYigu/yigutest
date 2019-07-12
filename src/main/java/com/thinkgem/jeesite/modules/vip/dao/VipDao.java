/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.vip.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.vip.entity.Vip;

/**
 * vipDAO接口
 * @author 李金辉
 * @version 2019-03-23
 */
@MyBatisDao
public interface VipDao extends CrudDao<Vip> {
	
}