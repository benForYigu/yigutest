/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.account.service.accountschool;

import java.util.List;

import com.thinkgem.jeesite.modules.account.dao.AccountDao;
import com.thinkgem.jeesite.modules.account.entity.accountstudent.AccountStudentinfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.account.entity.accountschool.AccountTeacherinfo;
import com.thinkgem.jeesite.modules.account.dao.accountschool.AccountTeacherinfoDao;

/**
 * 学校人员Service
 * @author 李金辉
 * @version 2019-01-28
 */
@Service
@Transactional(readOnly = true)
public class AccountTeacherinfoService extends CrudService<AccountTeacherinfoDao, AccountTeacherinfo> {

	@Autowired
	AccountDao accountDao;
	public AccountTeacherinfo get(String id) {
		return super.get(id);
	}
	
	public List<AccountTeacherinfo> findList(AccountTeacherinfo accountTeacherinfo) {
		return super.findList(accountTeacherinfo);
	}
	
	public Page<AccountTeacherinfo> findPage(Page<AccountTeacherinfo> page, AccountTeacherinfo accountTeacherinfo) {
		return super.findPage(page, accountTeacherinfo);
	}
	
	@Transactional(readOnly = false)
	public void save(AccountTeacherinfo accountTeacherinfo) {
		super.save(accountTeacherinfo);
	}
	
	@Transactional(readOnly = false)
	public void delete(AccountTeacherinfo accountTeacherinfo) {
		super.delete(accountTeacherinfo);
	}
	@Transactional(readOnly = false)
	public Object rdelete(AccountTeacherinfo accountTeacherinfo) {
		accountDao.rdelete(accountTeacherinfo.getAccountId());
		return dao.rdelete(accountTeacherinfo.getId());
	}
	public AccountTeacherinfo getByAccountId(String accountId) {
		return  dao.getByAccountId(accountId);
	}
}