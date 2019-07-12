/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.accountrecommend.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.accountrecommend.entity.AccountRecommend;

/**
 * 推荐记录DAO接口
 * @author 李金辉
 * @version 2019-05-10
 */
@MyBatisDao
public interface AccountRecommendDao extends CrudDao<AccountRecommend> {
	
}