/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.accountrecommend.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.accountrecommend.entity.AccountRecommend;
import com.thinkgem.jeesite.modules.accountrecommend.dao.AccountRecommendDao;

/**
 * 推荐记录Service
 * @author 李金辉
 * @version 2019-05-10
 */
@Service
@Transactional(readOnly = true)
public class AccountRecommendService extends CrudService<AccountRecommendDao, AccountRecommend> {

	public AccountRecommend get(String id) {
		return super.get(id);
	}
	
	public List<AccountRecommend> findList(AccountRecommend accountRecommend) {
		return super.findList(accountRecommend);
	}
	
	public Page<AccountRecommend> findPage(Page<AccountRecommend> page, AccountRecommend accountRecommend) {
		return super.findPage(page, accountRecommend);
	}
	
	@Transactional(readOnly = false)
	public void save(AccountRecommend accountRecommend) {
		super.save(accountRecommend);
	}
	
	@Transactional(readOnly = false)
	public void delete(AccountRecommend accountRecommend) {
		super.delete(accountRecommend);
	}
	
}