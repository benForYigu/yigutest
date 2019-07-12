/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.appconfig.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.appconfig.entity.AppConfig;
import com.thinkgem.jeesite.modules.appconfig.dao.AppConfigDao;

/**
 * 配置数据Service
 * @author 李金辉
 * @version 2019-03-26
 */
@Service
@Transactional(readOnly = true)
public class AppConfigService extends CrudService<AppConfigDao, AppConfig> {

	public AppConfig get(String id) {
		return super.get(id);
	}
	
	public List<AppConfig> findList(AppConfig appConfig) {
		return super.findList(appConfig);
	}
	
	public Page<AppConfig> findPage(Page<AppConfig> page, AppConfig appConfig) {
		return super.findPage(page, appConfig);
	}
	
	@Transactional(readOnly = false)
	public void save(AppConfig appConfig) {
		super.save(appConfig);
	}
	
	@Transactional(readOnly = false)
	public void delete(AppConfig appConfig) {
		super.delete(appConfig);
	}

	@Transactional(readOnly = false)
	public AppConfig selectByVersionAndType(String type, String version) {
		return dao.selectByVersionAndType(type,version);
	}

	@Transactional(readOnly = false)
	public AppConfig selectByVersionName(String type,String name) {
		return dao.selectByVersionName(type,name);
	}
}