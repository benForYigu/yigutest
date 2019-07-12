/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.interaction.dao.teachin;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.interaction.entity.teachin.InteractionTeachinData;

/**
 * 宣讲DAO接口
 * @author 李金辉
 * @version 2019-01-24
 */
@MyBatisDao
public interface InteractionTeachinDataDao extends CrudDao<InteractionTeachinData> {
	
}