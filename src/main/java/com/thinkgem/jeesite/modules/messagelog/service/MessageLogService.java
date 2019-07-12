/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.messagelog.service;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.google.common.base.Strings;
import com.thinkgem.jeesite.API.entity.Code;
import com.thinkgem.jeesite.API.util.MyUtils;
import com.thinkgem.jeesite.modules.account.dao.accounthr.AccountCompanyhrDao;
import com.thinkgem.jeesite.modules.account.dao.accountstudent.AccountStudentinfoDao;
import com.thinkgem.jeesite.modules.account.entity.Account;
import com.thinkgem.jeesite.modules.account.entity.accountstudent.AccountStudentinfo;
import com.thinkgem.jeesite.modules.account.service.accounthr.AccountCompanyhrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.messagelog.entity.MessageLog;
import com.thinkgem.jeesite.modules.messagelog.dao.MessageLogDao;

/**
 * 消息记录Service
 * @author 李金辉
 * @version 2019-01-28
 */
@Service
@Transactional(readOnly = true)
public class MessageLogService extends CrudService<MessageLogDao, MessageLog> {

	@Autowired
	AccountCompanyhrDao companyhrDao;
	@Autowired
	AccountStudentinfoDao studentinfoDao;
	public MessageLog get(String id) {
		return super.get(id);
	}
	
	public List<MessageLog> findList(MessageLog messageLog) {
		return super.findList(messageLog);
	}
	
	public Page<MessageLog> findPage(Page<MessageLog> page, MessageLog messageLog) {
		return super.findPage(page, messageLog);
	}
	
	@Transactional(readOnly = false)
	public void save(MessageLog messageLog) {
		super.save(messageLog);
	}
	
	@Transactional(readOnly = false)
	public void delete(MessageLog messageLog) {
		super.delete(messageLog);
	}

	@Transactional(readOnly = false)
    public Object message(Account account, String messageId,String interviewId, Integer page, Integer size) {
		MessageLog messageLog=new MessageLog();
		//查询消息列表
		if(Strings.isNullOrEmpty(messageId)&&Strings.isNullOrEmpty(interviewId)){
			if(Account.ROLE_COMPANY.equals(account.getRole())){
				messageLog.setCompanyId(companyhrDao.getByAccountId(account.getId()).getCompanyId());
			}else{
				messageLog.setStudentId(account.getId());
			}


			List<MessageLog> list=dao.findMList(messageLog);
			if(Account.ROLE_STUDENT.equals(account.getRole())){
				return list;
			}
			List<AccountStudentinfo> list1=new ArrayList<>();
			AccountStudentinfo o;
			for (MessageLog m: list) {
				o=studentinfoDao.getByAccountId(m.getStudentId());
				if(list1.contains(o)){

					m.setStudent(o);
				}else{
					m.setStudent((AccountStudentinfo) MyUtils.setLabel(o));
				}
				list1.add(o);
			}
			return list;
		}

		//通过面试id查询
		if(!Strings.isNullOrEmpty(interviewId)){
			MessageLog m=new MessageLog();
			m.setInterviewId(interviewId);
			m.getPage().setOrderBy("type ");
			List<MessageLog> list= dao.findList(m);
			int s=list.size();
			List<Object> l=new ArrayList<>(list);
			for (int i=0;i<5-s;i++){
				l.add(new JSONObject());
				list.add(null);
			}
			return l;

		}
		messageLog=dao.get(messageId);
		if(Account.ROLE_STUDENT.equals(account.getRole())){
			if(messageLog==null||!messageLog.getStudentId().equals(account.getId())){
				return Code.API_MESSAGEID_ERROR;
			}
		}
		if(Account.ROLE_COMPANY.equals(account.getRole())){
			if(messageLog==null||!messageLog.getCompanyId().equals(companyhrDao.getByAccountId(account.getId()).getCompanyId())){
				return Code.API_MESSAGEID_ERROR;
			}
		}

		//设置已读
		if(Account.ROLE_STUDENT.equals(account.getRole())){
			dao.updateStudentRead(account.getId(),messageLog.getCompanyProfessionId());
		}else{
			dao.updateHrRead(account.getId(),messageLog.getCompanyProfessionId());
		}

		MessageLog m=new MessageLog();
		m.setInterviewId(messageLog.getInterviewId());
		/*if(Account.ROLE_COMPANY.equals(account.getRole())){
			m.setCompanyId(messageLog.getCompanyId());
		}else{
			m.setStudentId(messageLog.getStudentId());
		}


		m.setCompanyProfessionId(messageLog.getCompanyProfessionId());*/
		m.getPage().setOrderBy("type ");

		List<MessageLog> list= dao.findList(m);
		int s=list.size();
		List<Object> l=new ArrayList<>(list);
		for (int i=0;i<5-s;i++){
			l.add(new JSONObject());
			list.add(null);
		}
		return l;

    }
}