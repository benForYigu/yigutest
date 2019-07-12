/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.interaction.entity.teachin;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 宣讲Entity
 * @author 李金辉
 * @version 2019-01-24
 */
public class InteractionTeachinData extends DataEntity<InteractionTeachinData> {
	
	private static final long serialVersionUID = 1L;
	private InteractionTeachin teachinId;		// 宣讲id 父类
	private Object url;		// 内容
	
	public InteractionTeachinData() {
		super();
	}

	public InteractionTeachinData(String id){
		super(id);
	}

	public InteractionTeachinData(InteractionTeachin teachinId){
		this.teachinId = teachinId;
	}

	@Length(min=0, max=100, message="宣讲id长度必须介于 0 和 100 之间")
	public InteractionTeachin getTeachinId() {
		return teachinId;
	}

	public void setTeachinId(InteractionTeachin teachinId) {
		this.teachinId = teachinId;
	}
	

	public Object getUrl() {
		return url;
	}

	public void setUrl(Object url) {
		this.url = url;
	}
	
}