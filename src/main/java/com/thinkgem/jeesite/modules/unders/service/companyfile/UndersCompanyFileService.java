/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.unders.service.companyfile;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.unders.entity.companyfile.UndersCompanyFile;
import com.thinkgem.jeesite.modules.unders.dao.companyfile.UndersCompanyFileDao;

/**
 * 企业双选会资料Service
 * @author 李金辉
 * @version 2019-05-27
 */
@Service
@Transactional(readOnly = true)
public class UndersCompanyFileService extends CrudService<UndersCompanyFileDao, UndersCompanyFile> {

	public UndersCompanyFile get(String id) {
		return super.get(id);
	}
	
	public List<UndersCompanyFile> findList(UndersCompanyFile undersCompanyFile) {
		return super.findList(undersCompanyFile);
	}
	
	public Page<UndersCompanyFile> findPage(Page<UndersCompanyFile> page, UndersCompanyFile undersCompanyFile) {
		return super.findPage(page, undersCompanyFile);
	}
	
	@Transactional(readOnly = false)
	public void save(UndersCompanyFile undersCompanyFile) {
		super.save(undersCompanyFile);
	}
	
	@Transactional(readOnly = false)
	public void delete(UndersCompanyFile undersCompanyFile) {
		super.delete(undersCompanyFile);
	}
	

	public UndersCompanyFile selectByCompanyId(String companyId) {
		return dao.selectByCompanyId(companyId);
	}

}