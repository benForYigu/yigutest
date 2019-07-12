/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 *//*

package com.thinkgem.jeesite.modules.formid.service;

import java.util.List;

import com.thinkgem.jeesite.API.entity.Code;
import com.thinkgem.jeesite.API.entity.Response;
import com.thinkgem.jeesite.modules.coustomer.entity.KhXx;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.formid.entity.FormId;
import com.thinkgem.jeesite.modules.formid.dao.FormIdDao;

import javax.servlet.http.HttpServletRequest;

*/
/**
 * orm表单Service
 * @author 李金辉
 * @version 2018-12-06
 *//*

@Service
@Transactional(readOnly = true)
public class FormIdService extends CrudService<FormIdDao, FormId> {

	public FormId get(String id) {
		return super.get(id);
	}

	public List<FormId> findList(FormId formId) {
		return super.findList(formId);
	}

	public Page<FormId> findPage(Page<FormId> page, FormId formId) {
		return super.findPage(page, formId);
	}

	@Transactional(readOnly = false)
	public void save(FormId formId) {
		super.save(formId);
	}

	@Transactional(readOnly = false)
	public void delete(FormId formId) {
		super.delete(formId);
	}

	//保存formId
	@Transactional(readOnly = false)
	public Response saveFormId(KhXx khXx, String formId) {

		if(khXx.getOpenId()==null ){
			return new Response(Code.API_USER_OPENID_NULL);
		}
		System.out.println("formId==========="+formId);
		if("undefined".equals(formId) ||  "the formId is a mock one".equals(formId)){
			return new Response(1);
		}
		FormId formId1=new FormId();
		formId1.setOpenId(khXx.getOpenId());
		formId1.setFormId(formId);
		formId1.preInsert();
		return new Response(dao.insert(formId1));
	}
}*/
