/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.company.service;

import java.util.List;

import com.sun.tools.internal.xjc.reader.xmlschema.bindinfo.BIConversion;
import com.thinkgem.jeesite.API.util.MyUtils;
import com.thinkgem.jeesite.modules.account.dao.AccountDao;
import com.thinkgem.jeesite.modules.account.dao.accounthr.AccountCompanyhrDao;
import com.thinkgem.jeesite.modules.account.entity.Account;
import com.thinkgem.jeesite.modules.account.entity.accounthr.AccountCompanyhr;
import com.thinkgem.jeesite.modules.company.dao.address.CompanyAddressDao;
import com.thinkgem.jeesite.modules.company.entity.address.CompanyAddress;
import com.thinkgem.jeesite.modules.sys.dao.UserDao;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.company.entity.Company;
import com.thinkgem.jeesite.modules.company.dao.CompanyDao;

/**
 * 企业基本信息Service
 * @author 李金辉
 * @version 2019-01-29
 */
@Service
@Transactional(readOnly = true)
public class CompanyService extends CrudService<CompanyDao, Company> {

	@Autowired
	CompanyAddressDao addressDao;
	@Autowired
	UserDao userDao;
	@Autowired
	AccountDao accountDao;
	@Autowired
	AccountCompanyhrDao companyhrDao;
	public Company get(String id) {
		Company company=super.get(id);
		company.setAddressList(MyUtils.setLabelList(addressDao.selectByCompanyId(id)));
		return company;
	}
	
	public List<Company> findList(Company company) {
		return super.findList(company);
	}
	
	public Page<Company> findPage(Page<Company> page, Company company) {
		return super.findPage(page, company);
	}
	
	@Transactional(readOnly = false)
	public void save(Company company) {
		super.save(company);
	}
	
	@Transactional(readOnly = false)
	public void delete(Company company) {
		super.delete(company);
		AccountCompanyhr companyhr=companyhrDao.getByCompanyId(company.getId());
		companyhrDao.rdelete(companyhr.getId());
		accountDao.rdelete(companyhr.getAccountId());
		User user=new User();
		user.setLoginName(companyhr.getPhone());
		user=userDao.getByLoginName(user);
		if(user!=null){
			userDao.rdeleteByUsernameAndPassword(companyhr.getPhone());
			userDao.myDelUserRole(user.getId());
		}


	}


	public List<Company> findCompanyBySchool(String schoolId,String undersId,String name) {
		return dao.findCompanyBySchool(schoolId,undersId,name);
	}
}