/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.company.service.address;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.company.entity.address.CompanyAddress;
import com.thinkgem.jeesite.modules.company.dao.address.CompanyAddressDao;

/**
 * 企业地址Service
 * @author 李金辉
 * @version 2019-01-29
 */
@Service
@Transactional(readOnly = true)
public class CompanyAddressService extends CrudService<CompanyAddressDao, CompanyAddress> {

	public CompanyAddress get(String id) {
		return super.get(id);
	}
	
	public List<CompanyAddress> findList(CompanyAddress companyAddress) {
		return super.findList(companyAddress);
	}
	
	public Page<CompanyAddress> findPage(Page<CompanyAddress> page, CompanyAddress companyAddress) {
		return super.findPage(page, companyAddress);
	}
	
	@Transactional(readOnly = false)
	public void save(CompanyAddress companyAddress) {
		super.save(companyAddress);
	}
	
	@Transactional(readOnly = false)
	public void delete(CompanyAddress companyAddress) {
		super.delete(companyAddress);
	}
	
}