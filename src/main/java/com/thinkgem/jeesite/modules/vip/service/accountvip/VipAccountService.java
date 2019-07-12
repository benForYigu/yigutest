/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.vip.service.accountvip;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.thinkgem.jeesite.modules.account.entity.Account;
import org.apache.shiro.crypto.hash.Hash;
import org.springframework.security.access.method.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.vip.entity.accountvip.VipAccount;
import com.thinkgem.jeesite.modules.vip.dao.accountvip.VipAccountDao;

/**
 * 用户权益Service
 * @author 李金辉
 * @version 2019-03-23
 */
@Service
@Transactional(readOnly = true)
public class VipAccountService extends CrudService<VipAccountDao, VipAccount> {

	public VipAccount get(String id) {
		return super.get(id);
	}
	
	public List<VipAccount> findList(VipAccount vipAccount) {
		return super.findList(vipAccount);
	}
	
	public Page<VipAccount> findPage(Page<VipAccount> page, VipAccount vipAccount) {
		return super.findPage(page, vipAccount);
	}
	
	@Transactional(readOnly = false)
	public void save(VipAccount vipAccount) {
		super.save(vipAccount);
	}
	
	@Transactional(readOnly = false)
	public void delete(VipAccount vipAccount) {
		super.delete(vipAccount);
	}


	public VipAccount selectByAccountId(String accountId) {
		return dao.selectByAccountId(accountId);
	}


}