/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.company.service.profession;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.company.entity.profession.CompanyProfession;
import com.thinkgem.jeesite.modules.company.dao.profession.CompanyProfessionDao;

/**
 * 企业职位Service
 * @author 李金辉
 * @version 2019-01-30
 */
@Service
@Transactional(readOnly = true)
public class CompanyProfessionService extends CrudService<CompanyProfessionDao, CompanyProfession> {

	public CompanyProfession get(String id) {
		return super.get(id);
	}
	
	public List<CompanyProfession> findList(CompanyProfession companyProfession) {
		return super.findList(companyProfession);
	}
	
	public Page<CompanyProfession> findPage(Page<CompanyProfession> page, CompanyProfession companyProfession) {
		return super.findPage(page, companyProfession);
	}
	
	@Transactional(readOnly = false)
	public void save(CompanyProfession companyProfession) {
		super.save(companyProfession);
	}
	
	@Transactional(readOnly = false)
	public void delete(CompanyProfession companyProfession) {
		super.delete(companyProfession);
	}
	
}