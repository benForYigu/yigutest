/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.school.service.notice;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.school.entity.notice.SchoolNotice;
import com.thinkgem.jeesite.modules.school.dao.notice.SchoolNoticeDao;

/**
 * 学习公告Service
 * @author 李金辉
 * @version 2019-01-29
 */
@Service
@Transactional(readOnly = true)
public class SchoolNoticeService extends CrudService<SchoolNoticeDao, SchoolNotice> {

	public SchoolNotice get(String id) {
		return super.get(id);
	}
	
	public List<SchoolNotice> findList(SchoolNotice schoolNotice) {
		return super.findList(schoolNotice);
	}
	
	public Page<SchoolNotice> findPage(Page<SchoolNotice> page, SchoolNotice schoolNotice) {
		return super.findPage(page, schoolNotice);
	}
	
	@Transactional(readOnly = false)
	public void save(SchoolNotice schoolNotice) {
		super.save(schoolNotice);
	}
	
	@Transactional(readOnly = false)
	public void delete(SchoolNotice schoolNotice) {
		super.delete(schoolNotice);
	}
	
}