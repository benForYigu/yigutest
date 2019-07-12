/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.message.service;

import java.util.List;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.thinkgem.jeesite.API.util.leancloud.aoyi.DateUtil;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.modules.account.entity.Account;
import com.thinkgem.jeesite.modules.company.dao.CompanyDao;
import com.thinkgem.jeesite.modules.company.entity.Company;
import com.thinkgem.jeesite.modules.interaction.dao.teachin.InteractionTeachinDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.message.entity.Message;
import com.thinkgem.jeesite.modules.message.dao.MessageDao;

/**
 * 消息Service
 * @author 李金辉
 * @version 2019-04-29
 */
@Service
@Transactional(readOnly = true)
public class MessageService extends CrudService<MessageDao, Message> {

	@Autowired
	CompanyDao companyDao;
	@Autowired
	InteractionTeachinDao teachinDao;
	public Message get(String id) {
		return super.get(id);
	}
	
	public List<Message> findList(Message message) {
		return super.findList(message);
	}
	
	public Page<Message> findPage(Page<Message> page, Message message) {
		return super.findPage(page, message);
	}
	
	@Transactional(readOnly = false)
	public void save(Message message) {
		super.save(message);
	}
	
	@Transactional(readOnly = false)
	public void delete(Message message) {
		super.delete(message);
	}

	public List<Message> message(Account account, Integer page, Integer size) {
		Message message=new Message();
		message.setAccountId(account.getId());
		PageHelper.startPage(page,size);
		List<Message> list=dao.findList(message);
		if(Account.ROLE_STUDENT.equals(account.getRole())){
			for (Message m: list) {
				Company company=companyDao.get(teachinDao.get(m.getTeachinId()).getCompanyId());
				m.setCompanyLogo(company.getLogo());
				m.setCompanyName(company.getName());

			}
		}
		return list;
	}
}