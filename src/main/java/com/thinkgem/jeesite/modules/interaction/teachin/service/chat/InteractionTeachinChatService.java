/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.interaction.teachin.service.chat;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Maps;
import com.thinkgem.jeesite.API.entity.Code;
import com.thinkgem.jeesite.API.entity.SocketHandler;
import com.thinkgem.jeesite.API.weixin.support.ExpireKey;
import com.thinkgem.jeesite.API.weixin.support.TokenManager;
import com.thinkgem.jeesite.API.weixin.support.expirekey.DefaultExpireKey;
import com.thinkgem.jeesite.common.utils.JedisUtils;
import com.thinkgem.jeesite.modules.account.dao.accounthr.AccountCompanyhrDao;
import com.thinkgem.jeesite.modules.account.dao.accountschool.AccountTeacherinfoDao;
import com.thinkgem.jeesite.modules.account.dao.accountstudent.AccountStudentinfoDao;
import com.thinkgem.jeesite.modules.account.entity.Account;
import com.thinkgem.jeesite.modules.account.entity.accounthr.AccountCompanyhr;
import com.thinkgem.jeesite.modules.account.entity.accountschool.AccountTeacherinfo;
import com.thinkgem.jeesite.modules.account.entity.accountstudent.AccountStudentinfo;
import com.thinkgem.jeesite.modules.account.service.accountschool.AccountTeacherinfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.interaction.teachin.entity.chat.InteractionTeachinChat;
import com.thinkgem.jeesite.modules.interaction.teachin.dao.chat.InteractionTeachinChatDao;

import javax.servlet.http.HttpServletRequest;

/**
 * 宣讲聊天Service
 * @author 李金辉
 * @version 2019-03-20
 */
@Service
@Transactional(readOnly = true)
public class InteractionTeachinChatService extends CrudService<InteractionTeachinChatDao, InteractionTeachinChat> {

	@Autowired
	AccountStudentinfoDao studentinfoDao;
	@Autowired
	AccountCompanyhrDao companyhrDao;
	@Autowired
	AccountTeacherinfoDao teacherinfoDao;
	@Autowired
	SocketHandler socketHandler;


	//重复通知过滤
	private static ExpireKey expireKey = new DefaultExpireKey();
	public InteractionTeachinChat get(String id) {
		return super.get(id);
	}
	
	public List<InteractionTeachinChat> findList(InteractionTeachinChat interactionTeachinChat) {
		return super.findList(interactionTeachinChat);
	}
	
	public Page<InteractionTeachinChat> findPage(Page<InteractionTeachinChat> page, InteractionTeachinChat interactionTeachinChat) {
		return super.findPage(page, interactionTeachinChat);
	}
	
	@Transactional(readOnly = false)
	public void save(InteractionTeachinChat interactionTeachinChat) {
		super.save(interactionTeachinChat);
	}
	
	@Transactional(readOnly = false)
	public void delete(InteractionTeachinChat interactionTeachinChat) {
		super.delete(interactionTeachinChat);
	}

	@Transactional(readOnly = false)
    public Object chat(HttpServletRequest request,String teachinId,String content) {
		Account account=(Account)request.getAttribute("account");
		InteractionTeachinChat chat=new InteractionTeachinChat();
		chat.setAccountId(account.getId());
		chat.setTeachinId(teachinId);
		chat.setContent(content);
		if(Account.ROLE_COMPANY.equals(account.getRole())){
			AccountCompanyhr companyhr=companyhrDao.getByAccountId(account.getId());
			chat.setAvatar(companyhr.getAvatar());
			chat.setRealname(companyhr.getRealname());
		}
		if(Account.ROLE_STUDENT.equals(account.getRole())){
			AccountStudentinfo studentinfo=studentinfoDao.getByAccountId(account.getId());
			chat.setAvatar(studentinfo.getAvatar());
			chat.setRealname(studentinfo.getRealname());
		}
		if(Account.ROLE_SCHOOL.equals(account.getRole())){
			AccountTeacherinfo teacherinfo=teacherinfoDao.getByAccountId(account.getId());
			chat.setAvatar(teacherinfo.getAvatar());
			chat.setRealname(teacherinfo.getRealname());
		}
		chat.preInsert();
		dao.insert(chat);
		try {
			socketHandler.sendMessageToSbUsers(content,SocketHandler.teachinMap.get(teachinId));
		}catch (Exception e){
			logger.info("发送错误");
		}

		return Code.SUCCESS;
	}
}