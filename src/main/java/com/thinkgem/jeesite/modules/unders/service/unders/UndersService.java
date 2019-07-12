/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.unders.service.unders;

import java.util.ArrayList;
import java.util.List;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Strings;
import com.thinkgem.jeesite.modules.account.dao.accounthr.AccountCompanyhrDao;
import com.thinkgem.jeesite.modules.account.dao.accountstudent.AccountStudentinfoDao;
import com.thinkgem.jeesite.modules.account.entity.Account;
import com.thinkgem.jeesite.modules.account.entity.accounthr.AccountCompanyhr;
import com.thinkgem.jeesite.modules.account.entity.accountstudent.AccountStudentinfo;
import com.thinkgem.jeesite.modules.company.dao.CompanyDao;
import org.apache.commons.collections.ListUtils;
import org.omg.CORBA.OBJ_ADAPTER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.unders.entity.unders.Unders;
import com.thinkgem.jeesite.modules.unders.dao.unders.UndersDao;
import com.thinkgem.jeesite.modules.unders.entity.unders.UndersBooth;
import com.thinkgem.jeesite.modules.unders.dao.unders.UndersBoothDao;

/**
 * 线下活动Service
 * @author 李金辉
 * @version 2019-05-24
 */
@Service
@Transactional(readOnly = true)
public class UndersService extends CrudService<UndersDao, Unders> {

	@Autowired
	private UndersBoothDao undersBoothDao;
	
	@Autowired
	private AccountStudentinfoDao studentinfoDao;
	@Autowired
	private AccountCompanyhrDao companyhrDao;
	@Autowired
	private CompanyDao companyDao;

	public Unders get(String id) {
		Unders unders = super.get(id);
		unders.setUndersBoothList(undersBoothDao.findList(new UndersBooth(unders)));
		return unders;
	}
	
	public List<Unders> findList(Unders unders) {
		return super.findList(unders);
	}
	
	public Page<Unders> findPage(Page<Unders> page, Unders unders) {
		return super.findPage(page, unders);
	}
	
	@Transactional(readOnly = false)
	public void save(Unders unders) {
		super.save(unders);
		for (UndersBooth undersBooth : unders.getUndersBoothList()){
			if (undersBooth.getId() == null){
				continue;
			}
			if (UndersBooth.DEL_FLAG_NORMAL.equals(undersBooth.getDelFlag())){
				if (StringUtils.isBlank(undersBooth.getId())){
					undersBooth.setUndersId(unders);
					undersBooth.preInsert();
					undersBoothDao.insert(undersBooth);
				}else{
					undersBooth.preUpdate();
					undersBoothDao.update(undersBooth);
				}
			}else{
				undersBoothDao.delete(undersBooth);
			}
		}
	}
	
	@Transactional(readOnly = false)
	public void delete(Unders unders) {
		super.delete(unders);
		undersBoothDao.delete(new UndersBooth(unders));
	}


    public Object unders(Account account, String undersId, String title,Integer page, Integer size) {
		//企业获取双选会
		if(Account.ROLE_COMPANY.equals(account.getRole())){
			AccountCompanyhr companyhr=companyhrDao.getByAccountId(account.getId());
			//查询所有列表
			if(Strings.isNullOrEmpty(undersId)){
				PageHelper.startPage(page,size);
				/*Unders unders=new Unders();
				unders.setTitle(title);
				List<Unders> list=dao.findList(new Unders());

				return new PageInfo<Unders>();*/
				return new PageInfo<Unders>(dao.findListByCompany(companyhr.getCompanyId(),title));
			//查询席位
			}else{
				return undersBoothDao.findList(new UndersBooth(new Unders(undersId)));
			}

		}

		if(Account.ROLE_STUDENT.equals(account.getRole())){
			Unders unders=new Unders();
			PageHelper.startPage(page,size);
			String schoolId=studentinfoDao.getByAccountId(account.getId()).getSchoolId();
			unders.setSchoolId(schoolId);
			List<Unders> list=dao.findList(unders);
			for (Unders i:list) {

				i.setOrderNo(companyDao.findCompanyBySchool(schoolId,i.getId(),null).size());
			}
			return new PageInfo<Unders>(list);
		}

		return null;
    }
}