/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.dict.service.major;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.service.TreeService;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.dict.entity.major.DictMajor;
import com.thinkgem.jeesite.modules.dict.dao.major.DictMajorDao;

/**
 * 专业Service
 * @author 李金辉
 * @version 2019-02-26
 */
@Service
@Transactional(readOnly = true)
public class DictMajorService extends TreeService<DictMajorDao, DictMajor> {

	public DictMajor get(String id) {
		return super.get(id);
	}
	
	public List<DictMajor> findList(DictMajor dictMajor) {
		if (StringUtils.isNotBlank(dictMajor.getParentIds())){
			dictMajor.setParentIds(","+dictMajor.getParentIds()+",");
		}
		return super.findList(dictMajor);
	}
	
	@Transactional(readOnly = false)
	public void save(DictMajor dictMajor) {
		super.save(dictMajor);
	}
	
	@Transactional(readOnly = false)
	public void delete(DictMajor dictMajor) {
		super.delete(dictMajor);
	}
	
}