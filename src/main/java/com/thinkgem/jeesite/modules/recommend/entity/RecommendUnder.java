/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.recommend.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 线下活动Entity
 * @author 李金辉
 * @version 2019-03-01
 */
public class RecommendUnder extends DataEntity<RecommendUnder> {
	
	private static final long serialVersionUID = 1L;
	private String type;		// 类型
	private String title;		// 标题
	private String subTitle;		// 副标题
	private String content;		// content
	
	public RecommendUnder() {
		super();
	}

	public RecommendUnder(String id){
		super(id);
	}


	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	@Length(min=0, max=255, message="标题长度必须介于 0 和 255 之间")
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	@Length(min=0, max=255, message="副标题长度必须介于 0 和 255 之间")
	public String getSubTitle() {
		return subTitle;
	}

	public void setSubTitle(String subTitle) {
		this.subTitle = subTitle;
	}
	
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
}