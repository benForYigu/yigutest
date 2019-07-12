/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.vip.service;

import java.util.List;

import com.google.common.base.Strings;
import com.sun.tools.corba.se.idl.StringGen;
import com.thinkgem.jeesite.API.entity.Code;
import com.thinkgem.jeesite.modules.account.dao.accounthr.AccountCompanyhrDao;
import com.thinkgem.jeesite.modules.account.entity.Account;
import com.thinkgem.jeesite.modules.account.entity.accounthr.AccountCompanyhr;
import com.thinkgem.jeesite.modules.vip.dao.accountvip.VipAccountDao;
import com.thinkgem.jeesite.modules.vip.entity.accountvip.VipAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.vip.entity.Vip;
import com.thinkgem.jeesite.modules.vip.dao.VipDao;

/**
 * vipService
 * @author 李金辉
 * @version 2019-03-23
 */
@Service
@Transactional(readOnly = true)
public class VipService extends CrudService<VipDao, Vip> {

	@Autowired
	AccountCompanyhrDao companyhrDao;
	public Vip get(String id) {
		return super.get(id);
	}
	
	public List<Vip> findList(Vip vip) {


		return super.findList(vip);
	}
	
	public Page<Vip> findPage(Page<Vip> page, Vip vip) {
		return super.findPage(page, vip);
	}
	
	@Transactional(readOnly = false)
	public void save(Vip vip) {
		super.save(vip);
	}
	
	@Transactional(readOnly = false)
	public void delete(Vip vip) {
		super.delete(vip);
	}

	public Object vip(Account account, String vipId) {
		AccountCompanyhr companyhr=companyhrDao.getByAccountId(account.getId());
		Vip vip=dao.get((String)companyhr.getVip());
		//查询单个
		if(Strings.isNullOrEmpty(vipId)){
			List<Vip> list=dao.findList(new Vip());
			if(companyhr.getVip()!=null){
				for (Vip v: list) {
					if(v.getPrice()<vip.getPrice()&&(v.getPrice().equals(vip.getPrice())&&v.getId()!=vip.getId())){
						v.setPayStatus(Vip.PAY_STATUS_NOT);
					}else if(v.getPrice().equals(vip.getPrice())&&v.getId()==vip.getId()){
						v.setPayStatus(Vip.PAY_STATUS_PAYED);
					}else{
						v.setPayStatus(Vip.PAY_STATUS_YES);
					}
				}
			}
			return list;

		}else{


				Vip v=dao.get(vipId);
				if(vip!=null&&v.getPrice()<vip.getPrice()&&(v.getPrice().equals(vip.getPrice())&&!v.getId().equals(vip.getId()))){
					v.setPayStatus(Vip.PAY_STATUS_NOT);
				}else if(vip!=null&&v.getPrice().equals(vip.getPrice())&&v.getId().equals(vip.getId())){
					v.setPayStatus(Vip.PAY_STATUS_PAYED);
				}else{
					v.setPayStatus(Vip.PAY_STATUS_YES);
				}

			return v;
		}

	}
}