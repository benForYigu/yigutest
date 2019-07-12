/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.account.service.accounthr;

import java.util.List;


import com.thinkgem.jeesite.API.entity.Code;
import com.thinkgem.jeesite.modules.account.dao.AccountDao;
import com.thinkgem.jeesite.modules.account.entity.Account;
import com.thinkgem.jeesite.modules.account.entity.accountstudent.AccountStudentinfo;
import com.thinkgem.jeesite.modules.company.dao.CompanyDao;
import com.thinkgem.jeesite.modules.company.dao.profession.CompanyProfessionDao;
import com.thinkgem.jeesite.modules.company.entity.Company;
import com.thinkgem.jeesite.modules.company.entity.profession.CompanyProfession;
import com.thinkgem.jeesite.modules.sys.dao.UserDao;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.service.SystemService;
import com.thinkgem.jeesite.modules.vip.dao.VipDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.account.entity.accounthr.AccountCompanyhr;
import com.thinkgem.jeesite.modules.account.dao.accounthr.AccountCompanyhrDao;

/**
 * hrService
 * @author 李金辉
 * @version 2019-01-25
 */
@Service
@Transactional(readOnly = true)
public class AccountCompanyhrService extends CrudService<AccountCompanyhrDao, AccountCompanyhr> {

	@Autowired
	CompanyProfessionDao professionDao;
	@Autowired
	AccountDao accountDao;
	@Autowired
	VipDao vipDao;
	@Autowired
	UserDao userDao;
	@Autowired
	CompanyDao companyDao;


	@Autowired
	AccountCompanyhrDao accountCompanyhrDao;
	public AccountCompanyhr get(String id) {
		return super.get(id);
	}
	
	public List<AccountCompanyhr> findList(AccountCompanyhr accountCompanyhr) {
		return super.findList(accountCompanyhr);
	}
	
	public Page<AccountCompanyhr> findPage(Page<AccountCompanyhr> page, AccountCompanyhr accountCompanyhr) {
		return super.findPage(page, accountCompanyhr);
	}
	
	@Transactional(readOnly = false)
	public void save(AccountCompanyhr accountCompanyhr) {
		super.save(accountCompanyhr);
	}
	
	@Transactional(readOnly = false)
	public void delete(AccountCompanyhr accountCompanyhr) {
		super.delete(accountCompanyhr);
	}
	@Transactional(readOnly = false)
	public Object rdelete(AccountCompanyhr accountCompanyhr) {
		accountDao.rdelete(accountCompanyhr.getAccountId());

		companyDao.delete(new Company(accountCompanyhr.getCompanyId()));

		User user=new User();
		user.setLoginName(accountCompanyhr.getPhone());
		user=userDao.getByLoginName(user);
		if(user!=null){
			userDao.rdeleteByUsernameAndPassword(accountCompanyhr.getPhone());
			userDao.myDelUserRole(user.getId());
		}


		return dao.rdelete(accountCompanyhr.getId());
	}
	public AccountCompanyhr getByAccountId(String accountId) {

		return  dao.getByAccountId(accountId);
	}

    public AccountCompanyhr getByCompanyId(String companyId) {
		return  dao.getByCompanyId(companyId);
    }


}