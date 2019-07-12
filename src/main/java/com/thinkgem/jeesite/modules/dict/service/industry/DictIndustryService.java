/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.dict.service.industry;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.service.TreeService;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.dict.entity.industry.DictIndustry;
import com.thinkgem.jeesite.modules.dict.dao.industry.DictIndustryDao;

/**
 * 行业Service
 * @author 李金辉
 * @version 2019-02-23
 */
@Service
@Transactional(readOnly = true)
public class DictIndustryService extends TreeService<DictIndustryDao, DictIndustry> {

	public DictIndustry get(String id) {
		return super.get(id);
	}
	
	public List<DictIndustry> findList(DictIndustry dictIndustry) {
		if (StringUtils.isNotBlank(dictIndustry.getParentIds())){
			dictIndustry.setParentIds(","+dictIndustry.getParentIds()+",");
		}
		return super.findList(dictIndustry);
	}
	
	@Transactional(readOnly = false)
	public void save(DictIndustry dictIndustry) {
		super.save(dictIndustry);
	}
	
	@Transactional(readOnly = false)
	public void delete(DictIndustry dictIndustry) {
		super.delete(dictIndustry);
	}
	
}