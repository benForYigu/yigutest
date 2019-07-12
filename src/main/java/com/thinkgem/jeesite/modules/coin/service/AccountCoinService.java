/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.coin.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.coin.entity.AccountCoin;
import com.thinkgem.jeesite.modules.coin.dao.AccountCoinDao;

/**
 * 岗币记录Service
 * @author 李金辉
 * @version 2019-05-10
 */
@Service
@Transactional(readOnly = true)
public class AccountCoinService extends CrudService<AccountCoinDao, AccountCoin> {

	public AccountCoin get(String id) {
		return super.get(id);
	}
	
	public List<AccountCoin> findList(AccountCoin accountCoin) {
		return super.findList(accountCoin);
	}
	
	public Page<AccountCoin> findPage(Page<AccountCoin> page, AccountCoin accountCoin) {
		return super.findPage(page, accountCoin);
	}
	
	@Transactional(readOnly = false)
	public void save(AccountCoin accountCoin) {
		super.save(accountCoin);
	}
	
	@Transactional(readOnly = false)
	public void delete(AccountCoin accountCoin) {
		super.delete(accountCoin);
	}
	
}