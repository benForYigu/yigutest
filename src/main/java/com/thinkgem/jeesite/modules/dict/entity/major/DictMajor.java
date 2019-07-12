/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.dict.entity.major;

import com.fasterxml.jackson.annotation.JsonBackReference;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.TreeEntity;

/**
 * 专业Entity
 * @author 李金辉
 * @version 2019-02-26
 */
public class DictMajor extends TreeEntity<DictMajor> {
	
	private static final long serialVersionUID = 1L;

	public DictMajor() {
		super();
	}

	public DictMajor(String id){
		super(id);
	}

	@JsonBackReference
	@NotNull(message="父级编号不能为空")
	public DictMajor getParent() {
		return parent;
	}

	public void setParent(DictMajor parent) {
		this.parent = parent;
	}
	


	public String getParentId() {
		return parent != null && parent.getId() != null ? parent.getId() : "0";
	}
}