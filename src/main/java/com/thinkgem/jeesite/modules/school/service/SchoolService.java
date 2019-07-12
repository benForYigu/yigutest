/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.school.service;

import java.util.List;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Strings;
import com.thinkgem.jeesite.API.entity.Code;
import com.thinkgem.jeesite.modules.account.dao.accountschool.AccountTeacherinfoDao;
import com.thinkgem.jeesite.modules.account.dao.accountstudent.AccountStudentinfoDao;
import com.thinkgem.jeesite.modules.account.entity.Account;
import com.thinkgem.jeesite.modules.account.entity.accountschool.AccountTeacherinfo;
import com.thinkgem.jeesite.modules.account.entity.accountstudent.AccountStudentinfo;
import com.thinkgem.jeesite.modules.school.dao.notice.SchoolNoticeDao;
import com.thinkgem.jeesite.modules.school.entity.notice.SchoolNotice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.school.entity.School;
import com.thinkgem.jeesite.modules.school.dao.SchoolDao;

import javax.xml.ws.Action;

/**
 * 学校Service
 * @author 李金辉
 * @version 2019-01-24
 */
@Service
@Transactional(readOnly = true)
public class SchoolService extends CrudService<SchoolDao, School> {

	@Autowired
	AccountTeacherinfoDao teacherinfoDao;

	@Autowired
	AccountStudentinfoDao studentinfoDao;
	@Autowired
	SchoolNoticeDao noticeDao;

	public School get(String id) {
		return super.get(id);
	}
	
	public List<School> findList(School school) {
		return super.findList(school);
	}
	
	public Page<School> findPage(Page<School> page, School school) {
		return super.findPage(page, school);
	}
	
	@Transactional(readOnly = false)
	public void save(School school) {
		super.save(school);
	}
	
	@Transactional(readOnly = false)
	public void delete(School school) {
		super.delete(school);
	}

    public Object notice(Account account, String noticeId, Integer page, Integer size) {
		String schoolId="";
		if(Account.ROLE_STUDENT.equals(account.getRole())){
			schoolId=studentinfoDao.getByAccountId(account.getId()).getSchoolId();
		}else{
			schoolId=teacherinfoDao.getByAccountId(account.getId()).getSchoolId();
		}
		//查询列表
		if(Strings.isNullOrEmpty(noticeId)){
			SchoolNotice notice=new SchoolNotice();
			notice.setSchoolId(schoolId);
			notice.setStatus(SchoolNotice.STATUS_ON);
			PageHelper.startPage(page,size);
			return new PageInfo<SchoolNotice>(noticeDao.findList(notice));
		//查询单个
		}else{
			//是否为本校
			SchoolNotice notice=noticeDao.get(noticeId);
			if(schoolId.equals(notice.getSchoolId())){
				return notice;
			}else{
				return Code.API_USER_ROLE_ERROR;
			}
		}

    }
}