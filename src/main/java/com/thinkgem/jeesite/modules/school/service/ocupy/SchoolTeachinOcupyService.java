/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.school.service.ocupy;

import java.util.Date;
import java.util.List;

import com.thinkgem.jeesite.API.util.leancloud.aoyi.DateUtil;
import com.thinkgem.jeesite.common.utils.DateUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.school.entity.ocupy.SchoolTeachinOcupy;
import com.thinkgem.jeesite.modules.school.dao.ocupy.SchoolTeachinOcupyDao;

/**
 * 学校宣讲时间Service
 * @author 李金辉
 * @version 2019-04-12
 */
@Service
@Transactional(readOnly = true)
public class SchoolTeachinOcupyService extends CrudService<SchoolTeachinOcupyDao, SchoolTeachinOcupy> {

	public SchoolTeachinOcupy get(String id) {
		return super.get(id);
	}
	
	public List<SchoolTeachinOcupy> findList(SchoolTeachinOcupy schoolTeachinOcupy) {
		return super.findList(schoolTeachinOcupy);
	}
	
	public Page<SchoolTeachinOcupy> findPage(Page<SchoolTeachinOcupy> page, SchoolTeachinOcupy schoolTeachinOcupy) {
		return super.findPage(page, schoolTeachinOcupy);
	}
	
	@Transactional(readOnly = false)
	public void save(SchoolTeachinOcupy schoolTeachinOcupy) {
		super.save(schoolTeachinOcupy);
	}
	
	@Transactional(readOnly = false)
	public void delete(SchoolTeachinOcupy schoolTeachinOcupy) {
		super.delete(schoolTeachinOcupy);
	}

	public  List<SchoolTeachinOcupy> isUsed(String schoolId, String date) {
		return dao.isUsed(schoolId, DateUtils.parseDate(date));
	}
	@Transactional(readOnly = false)
    public void ldelete(String id) {
		dao.ldelete(id);
    }
}