/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.recommend.service;

import java.util.List;

import com.google.common.base.Strings;
import com.thinkgem.jeesite.common.utils.IdGen;
import com.thinkgem.jeesite.modules.account.entity.Account;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.recommend.entity.RecommendUnder;
import com.thinkgem.jeesite.modules.recommend.dao.RecommendUnderDao;

/**
 * 线下活动Service
 * @author 李金辉
 * @version 2019-03-01
 */
@Service
@Transactional(readOnly = true)
public class RecommendUnderService extends CrudService<RecommendUnderDao, RecommendUnder> {

	public RecommendUnder get(String id) {
		return super.get(id);
	}
	
	public List<RecommendUnder> findList(RecommendUnder recommendUnder) {
		return super.findList(recommendUnder);
	}
	
	public Page<RecommendUnder> findPage(Page<RecommendUnder> page, RecommendUnder recommendUnder) {
		return super.findPage(page, recommendUnder);
	}
	
	@Transactional(readOnly = false)
	public void save(RecommendUnder recommendUnder) {
		super.save(recommendUnder);
	}
	
	@Transactional(readOnly = false)
	public void delete(RecommendUnder recommendUnder) {
		super.delete(recommendUnder);
	}

	@Transactional(readOnly = false)
    public Object enroll(Account account, String id) {
		String code=dao.EnrollCode(account.getId(),id);
		if(Strings.isNullOrEmpty(code)){
			code= IdGen.randomBase62(10);
			dao.enroll(account.getId(),id,code);

		}
		return code;
    }

	public boolean isEnroll(Account account, String id) {
		return !Strings.isNullOrEmpty(dao.EnrollCode(account.getId(),id));
	}
}