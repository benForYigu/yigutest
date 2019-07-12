/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.API.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.thinkgem.jeesite.common.persistence.TreeEntity;
import com.thinkgem.jeesite.modules.dict.entity.major.DictMajor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 专业Entity
 * @author 李金辉
 * @version 2019-02-26
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DictLabelDao implements Serializable {
	String id;
	String name;
	String parentId;
	public DictLabelDao(Object id, String name){
		this.id=(String)id;
		this.name=name;
	}
	public DictLabelDao(){

	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "DictLabelDao{" +
				"id='" + id + '\'' +
				", name='" + name + '\'' +
				", parentId='" + parentId + '\'' +
				'}';
	}
}