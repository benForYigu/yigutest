/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.dict.entity.industry;

import com.fasterxml.jackson.annotation.JsonBackReference;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.TreeEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * 行业Entity
 * @author 李金辉
 * @version 2019-02-23
 */
public class DictIndustry extends TreeEntity<DictIndustry> {
	
	private static final long serialVersionUID = 1L;

	
	public DictIndustry() {
		super();
	}

	public DictIndustry(String id){
		super(id);
	}
	private List<DictIndustry> dictIndustries=new ArrayList<>();

	public List<DictIndustry> getDictIndustries() {
		return dictIndustries;
	}

	public void setDictIndustries(List<DictIndustry> dictIndustries) {
		this.dictIndustries = dictIndustries;
	}

	@JsonBackReference
	@NotNull(message="父级编号不能为空")
	public DictIndustry getParent() {
		return parent;
	}

	public void setParent(DictIndustry parent) {
		this.parent = parent;
	}

	public void setParentId(String parentId) {
		this.parent = new DictIndustry(parentId);
	}


	

}