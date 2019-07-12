/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.account.service.accountstudent;

import java.util.List;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.thinkgem.jeesite.API.entity.Code;
import com.thinkgem.jeesite.modules.account.dao.AccountDao;
import com.thinkgem.jeesite.modules.account.entity.Account;
import com.thinkgem.jeesite.modules.company.dao.CompanyDao;
import com.thinkgem.jeesite.modules.company.dao.profession.CompanyProfessionDao;
import com.thinkgem.jeesite.modules.company.entity.Company;
import com.thinkgem.jeesite.modules.company.entity.profession.CompanyProfession;
import org.codehaus.groovy.transform.sc.transformers.RangeExpressionTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.account.entity.accountstudent.AccountStudentinfo;
import com.thinkgem.jeesite.modules.account.dao.accountstudent.AccountStudentinfoDao;

/**
 * 用户_学生Service
 * @author 李金辉
 * @version 2019-01-23
 */
@Service
@Transactional(readOnly = true)
public class AccountStudentinfoService extends CrudService<AccountStudentinfoDao, AccountStudentinfo> {

	@Autowired
	CompanyDao companyDao;
	@Autowired
	CompanyProfessionDao companyProfessionDao;
	@Autowired
	AccountDao accountDao;
	public AccountStudentinfo get(String id) {
		return super.get(id);
	}
	
	public List<AccountStudentinfo> findList(AccountStudentinfo accountStudentinfo) {
		return super.findList(accountStudentinfo);
	}
	
	public Page<AccountStudentinfo> findPage(Page<AccountStudentinfo> page, AccountStudentinfo accountStudentinfo) {
		return super.findPage(page, accountStudentinfo);
	}
	
	@Transactional(readOnly = false)
	public void save(AccountStudentinfo accountStudentinfo) {
		super.save(accountStudentinfo);
	}
	
	@Transactional(readOnly = false)
	public void delete(AccountStudentinfo accountStudentinfo) {
		super.delete(accountStudentinfo);
	}

	public AccountStudentinfo getByAccountId(String accountId) {
		return  dao.getByAccountId(accountId);
	}

	@Transactional(readOnly = false)
	public Object collect(Account account, String id, String type) {
		AccountStudentinfo accountStudentinfo=dao.getByAccountId(account.getId());
		if("company".equals(type)){
			//已关注
			if((""+accountStudentinfo.getCollect()).indexOf(id)>-1){
				accountStudentinfo.setCollect(accountStudentinfo.getCollect().replaceAll(id+",",","));
				accountStudentinfo.preUpdate();
				dao.update(accountStudentinfo);
				return "成功取关企业";
				//未关注
			}else{
				accountStudentinfo.setCollect((accountStudentinfo.getCollect()==null?"":accountStudentinfo.getCollect())+id+",");
				accountStudentinfo.preUpdate();
				dao.update(accountStudentinfo);
				return "成功关注企业";
			}
		}
		if("profession".equals(type)){
			//已关注
			if((""+accountStudentinfo.getProfessionCollect()).indexOf(id)>-1){
				accountStudentinfo.setProfessionCollect(accountStudentinfo.getProfessionCollect().replaceAll(id+",",""));
				accountStudentinfo.preUpdate();
				dao.update(accountStudentinfo);
				return "成功取关职位";
				//未关注
			}else{
				accountStudentinfo.setProfessionCollect((accountStudentinfo.getProfessionCollect()==null?"":accountStudentinfo.getProfessionCollect())+id+",");
				accountStudentinfo.preUpdate();
				dao.update(accountStudentinfo);
				return "成功关注职位";
			}
		}
		return null;



	}

	//收藏记录
	public Object gCollect(Account account, String type, Integer page, Integer size) {
		String collect;
		if("company".equals(type)){
			collect=dao.getByAccountId(account.getId()).getCollect();
			PageHelper.startPage(page,size);
			return new PageInfo<Company>(companyDao.gCollect(collect));
		}else if("profession".equals(type)){
			collect=dao.getByAccountId(account.getId()).getProfessionCollect();
			PageHelper.startPage(page,size);
			return new PageInfo<CompanyProfession>(companyProfessionDao.gCollect(collect));
		}
		return null;
	}

	@Transactional(readOnly = false)
    public Object rdelete(AccountStudentinfo accountStudentinfo) {
		accountDao.rdelete(accountStudentinfo.getAccountId());
		return dao.rdelete(accountStudentinfo.getId());
    }
}