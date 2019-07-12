/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.company.service.auth;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.company.entity.auth.CompanyAuth;
import com.thinkgem.jeesite.modules.company.dao.auth.CompanyAuthDao;

/**
 * 企业认证信息Service
 * @author 李金辉
 * @version 2019-01-29
 */
@Service
@Transactional(readOnly = true)
public class CompanyAuthService extends CrudService<CompanyAuthDao, CompanyAuth> {

	public CompanyAuth get(String id) {
		return super.get(id);
	}
	
	public List<CompanyAuth> findList(CompanyAuth companyAuth) {
		return super.findList(companyAuth);
	}
	
	public Page<CompanyAuth> findPage(Page<CompanyAuth> page, CompanyAuth companyAuth) {
		return super.findPage(page, companyAuth);
	}
	
	@Transactional(readOnly = false)
	public void save(CompanyAuth companyAuth) {
		super.save(companyAuth);
	}
	
	@Transactional(readOnly = false)
	public void delete(CompanyAuth companyAuth) {
		super.delete(companyAuth);
	}

    public CompanyAuth getByCompanyId(String companyId) {
		return 	dao.get(companyId);
    }
}