/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.testsex.service;

import java.util.List;

import com.thinkgem.jeesite.common.utils.JedisUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import org.apache.poi.util.SystemOutLogger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.testsex.entity.ABenTest;
import com.thinkgem.jeesite.modules.testsex.dao.ABenTestDao;
import redis.clients.jedis.Jedis;

/**
 * 测试表Service
 * @author ben
 * @version 2019-07-05
 */
@Service
@Transactional(readOnly = true)
public class ABenTestService extends CrudService<ABenTestDao, ABenTest> {

	private static Jedis jedis = JedisUtils.getResource();

	public ABenTest get(String id) {
		return super.get(id);
	}
	
	public List<ABenTest> findList(ABenTest aBenTest) {
		return super.findList(aBenTest);
	}
	
	public Page<ABenTest> findPage(Page<ABenTest> page, ABenTest aBenTest) {
		return super.findPage(page, aBenTest);
	}
	
	@Transactional(readOnly = false)
	public void save(ABenTest aBenTest) {
		super.save(aBenTest);
	}
	
	@Transactional(readOnly = false)
	public void delete(ABenTest aBenTest) {
		super.delete(aBenTest);
	}

	public String setJedis(ABenTest aBenTest){
		String key = !StringUtils.isNullOrEmpty(aBenTest.getName()) ? aBenTest.getName() : "空";
		String value = !StringUtils.isNullOrEmpty(aBenTest.getRemark()) ? aBenTest.getRemark() : "空";
		return jedis.set(key, value);
	}
	public String getJedis(ABenTest aBenTest){
		String key = !StringUtils.isNullOrEmpty(aBenTest.getName()) ? aBenTest.getName() : "空";
		return jedis.get(key);
	}
}