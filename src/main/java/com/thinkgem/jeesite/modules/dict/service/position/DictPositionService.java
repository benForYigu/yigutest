/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.dict.service.position;

import java.util.List;

import com.thinkgem.jeesite.API.entity.DictLabelDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.service.TreeService;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.dict.entity.position.DictPosition;
import com.thinkgem.jeesite.modules.dict.dao.position.DictPositionDao;

/**
 * 职位Service
 * @author 李金辉
 * @version 2019-02-23
 */
@Service
@Transactional(readOnly = true)
public class DictPositionService extends TreeService<DictPositionDao, DictPosition> {

	public DictPosition get(String id) {
		return super.get(id);
	}

	public List<DictPosition> findByParentIdsLike(DictPosition dictPosition) {
		return dao.findByParentIdsLike(dictPosition);
	}

	public List<DictPosition> findList(DictPosition dictPosition) {
		if (StringUtils.isNotBlank(dictPosition.getParentIds())){
			dictPosition.setParentIds(","+dictPosition.getParentIds()+",");
		}
		return super.findList(dictPosition);
	}
	
	@Transactional(readOnly = false)
	public void save(DictPosition dictPosition) {
		super.save(dictPosition);
	}
	
	@Transactional(readOnly = false)
	public void delete(DictPosition dictPosition) {
		super.delete(dictPosition);
	}

	@Transactional(readOnly = false)
    public List<DictLabelDao> existProfession() {
		return dao.existProfession();
    }
}