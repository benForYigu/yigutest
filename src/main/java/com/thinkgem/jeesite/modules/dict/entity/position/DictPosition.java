/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.dict.entity.position;

import com.fasterxml.jackson.annotation.JsonBackReference;
import javax.validation.constraints.NotNull;

import com.thinkgem.jeesite.modules.dict.entity.industry.DictIndustry;
import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.TreeEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * 职位Entity
 * @author 李金辉
 * @version 2019-02-23
 */
public class DictPosition extends TreeEntity<DictPosition> {
	
	private static final long serialVersionUID = 1L;

	
	public DictPosition() {
		super();
	}

	public DictPosition(String id){
		super(id);
	}
	private List<DictPosition> dictPositions=new ArrayList<>();

	public List<DictPosition> getDictPositions() {
		return dictPositions;
	}

	public void setDictPositions(List<DictPosition> dictPositions) {
		this.dictPositions = dictPositions;
	}

	@JsonBackReference
	@NotNull(message="父级编号不能为空")
	public DictPosition getParent() {
		return parent;
	}

	public void setParent(DictPosition parent) {
		this.parent = parent;
	}
	
	public void setParentId(String parentId) {
		this.parent = new DictPosition(parentId);
	}


}