/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.testuser.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.testuser.entity.TestUserinfo;
import com.thinkgem.jeesite.modules.testuser.dao.TestUserinfoDao;

/**
 * 测试类Service
 * @author Owen
 * @version 2017-10-18
 */
@Service
@Transactional(readOnly = true)
public class TestUserinfoService extends CrudService<TestUserinfoDao, TestUserinfo> {

	public TestUserinfo get(String id) {
		return super.get(id);
	}
	
	public List<TestUserinfo> findList(TestUserinfo testUserinfo) {
		return super.findList(testUserinfo);
	}
	
	public Page<TestUserinfo> findPage(Page<TestUserinfo> page, TestUserinfo testUserinfo) {
		return super.findPage(page, testUserinfo);
	}
	
	@Transactional(readOnly = false)
	public void save(TestUserinfo testUserinfo) {
		super.save(testUserinfo);
	}
	
	@Transactional(readOnly = false)
	public void delete(TestUserinfo testUserinfo) {
		super.delete(testUserinfo);
	}
	
}