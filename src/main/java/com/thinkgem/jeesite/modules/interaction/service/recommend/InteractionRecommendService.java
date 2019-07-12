/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.interaction.service.recommend;

import java.util.List;

import com.google.common.base.Strings;
import com.thinkgem.jeesite.API.entity.Code;
import com.thinkgem.jeesite.modules.account.dao.accountschool.AccountTeacherinfoDao;
import com.thinkgem.jeesite.modules.account.dao.accountstudent.AccountStudentinfoDao;
import com.thinkgem.jeesite.modules.account.entity.Account;
import com.thinkgem.jeesite.modules.account.entity.accountschool.AccountTeacherinfo;
import com.thinkgem.jeesite.modules.account.entity.accountstudent.AccountStudentinfo;
import com.thinkgem.jeesite.modules.account.service.accountstudent.AccountStudentinfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.interaction.entity.recommend.InteractionRecommend;
import com.thinkgem.jeesite.modules.interaction.dao.recommend.InteractionRecommendDao;


/**
 * 推荐位Service
 * @author 李金辉
 * @version 2019-01-24
 */
@Service
@Transactional(readOnly = true)
public class InteractionRecommendService extends CrudService<InteractionRecommendDao, InteractionRecommend> {

	@Autowired
	AccountStudentinfoDao accountStudentinfoDao;
	@Autowired
	AccountTeacherinfoDao accountTeacherinfoDao;
	public InteractionRecommend get(String id) {
		return super.get(id);
	}
	
	public List<InteractionRecommend> findList(InteractionRecommend interactionRecommend) {
		return super.findList(interactionRecommend);
	}
	
	public Page<InteractionRecommend> findPage(Page<InteractionRecommend> page, InteractionRecommend interactionRecommend) {
		return super.findPage(page, interactionRecommend);
	}
	
	@Transactional(readOnly = false)
	public void save(InteractionRecommend interactionRecommend) {
		super.save(interactionRecommend);
	}
	
	@Transactional(readOnly = false)
	public void delete(InteractionRecommend interactionRecommend) {
		super.delete(interactionRecommend);
	}

    public Object recommend(Account account, String recommendId,String type) {

		InteractionRecommend interactionRecommend=new InteractionRecommend();
		interactionRecommend.setType(type);
		interactionRecommend.setStatus(InteractionRecommend.STATUS_NORM);
		//只查询一条
		if(InteractionRecommend.TYPE_ABOUT.equals(type)){
			if(Account.ROLE_COMPANY.equals(account.getRole())){
				interactionRecommend.setSuportCompany("1");
			}else{
				interactionRecommend.setSuportCompany("0");
			}

			List list=dao.findList(interactionRecommend);
			if(list.size()==0){
				return null;
			}
			return list.get(0);
		}
		//区分身份
		if(Account.ROLE_STUDENT.equals(account.getRole())){
			AccountStudentinfo accountStudentinfo=accountStudentinfoDao.getByAccountId(account.getId());
			if(accountStudentinfo==null){
				return Code.API_ACCOUNT_NOT_EXIT;
			}
			interactionRecommend.setApplyTo(accountStudentinfo.getSchoolId());

			if(Strings.isNullOrEmpty(recommendId)){
				return dao.findList(interactionRecommend);
			}else{
				return dao.get(recommendId);
			}
		}
		if(Account.ROLE_SCHOOL.equals(account.getRole())){
			AccountTeacherinfo accountTeacherinfo= accountTeacherinfoDao.getByAccountId(account.getId());
			if(accountTeacherinfo==null){
				return Code.API_ACCOUNT_NOT_EXIT;
			}
			interactionRecommend.setApplyTo(accountTeacherinfo.getSchoolId());
			if(Strings.isNullOrEmpty(recommendId)){
				return dao.findList(interactionRecommend);
			}else{
				return dao.get(recommendId);
			}
		}
		if(Account.ROLE_COMPANY.equals(account.getRole())){
			if(Strings.isNullOrEmpty(recommendId)){
				interactionRecommend.setSuportCompany("1");
				return dao.findList(interactionRecommend);
			}else{
				return dao.get(recommendId);
			}
		}
		return Code.API_PARRAM_ERROR;
    }
}