/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.interaction.entity.recommend;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 推荐位Entity
 * @author 李金辉
 * @version 2019-01-24
 */
public class InteractionRecommend extends DataEntity<InteractionRecommend> {
	
	private static final long serialVersionUID = 1L;
	public static final String STATUS_NORM = "1";//正常
	public static final String STATUS_FORB = "2";//禁止
	public static final String TYPE_FORW = "1";//首页
	public static final String TYPE_PRO = "2";//职位
	public static final String TYPE_ABOUT = "3";//关于我们
	private String title;		// 标题
	private String subTitle;		// 副标题
	private String applyTo;		// 适用学校
	private String suportCompany;		// 适用学校
	private String sort;		// 排序
	private String img;		// 图片
	private Object type;		// 推荐位类型
	private String link;		// 链接
	private Object linkType;		// 链接类型
	private String status;		// 状态
	private String content;		// 内容

	public String getSuportCompany() {
		return suportCompany;
	}

	public void setSuportCompany(String suportCompany) {
		this.suportCompany = suportCompany;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public InteractionRecommend() {
		super();
	}

	public InteractionRecommend(String id){
		super(id);
	}

	@Length(min=1, max=255, message="标题长度必须介于 1 和 255 之间")
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
	

	public String getApplyTo() {
		return applyTo;
	}

	public void setApplyTo(String applyTo) {
		this.applyTo = applyTo;
	}
	
	@Length(min=1, max=255, message="排序长度必须介于 1 和 255 之间")
	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}
	
	@Length(min=0, max=255, message="图片长度必须介于 0 和 255 之间")
	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public Object getType() {
		return type;
	}

	public void setType(Object type) {
		this.type = type;
	}
	
	@Length(min=1, max=1000, message="链接长度必须介于 1 和 1000 之间")
	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}
	

	public Object getLinkType() {
		return linkType;
	}

	public void setLinkType(Object linkType) {
		this.linkType = linkType;
	}
	
	@Length(min=1, max=255, message="状态长度必须介于 1 和 255 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
}