/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.dict.dao.industry;

import com.thinkgem.jeesite.common.persistence.TreeDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.dict.entity.industry.DictIndustry;

/**
 * 行业DAO接口
 * @author 李金辉
 * @version 2019-02-23
 */
@MyBatisDao
public interface DictIndustryDao extends TreeDao<DictIndustry> {
	
}