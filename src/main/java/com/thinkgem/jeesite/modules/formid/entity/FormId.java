/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.formid.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * orm表单Entity
 * @author 李金辉
 * @version 2018-12-06
 */
public class FormId extends DataEntity<FormId> {
	
	private static final long serialVersionUID = 1L;
	private String formId;		// form_id
	private String openId;		// 用户open_id
	
	public FormId() {
		super();
	}

	public FormId(String id){
		super(id);
	}

	@Length(min=1, max=200, message="form_id长度必须介于 1 和 200 之间")
	public String getFormId() {
		return formId;
	}

	public void setFormId(String formId) {
		this.formId = formId;
	}
	
	@Length(min=1, max=100, message="用户open_id长度必须介于 1 和 100 之间")
	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}
	
}