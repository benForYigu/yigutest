/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.school.entity.notice;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 学习公告Entity
 * @author 李金辉
 * @version 2019-01-29
 */
public class SchoolNotice extends DataEntity<SchoolNotice> {
	
	private static final long serialVersionUID = 1L;
	public static final String  STATUS_ON = "1";
	public static final String  STATUS_OFF = "2";
	private String schoolId;		// 学校id
	private String img;		// 图片
	private String title;		// 标题
	private String subTitle;		// 子标题
	private String status;		// 状态
	private String content;		// 内容

	private String schoolName;		// 学校名称

	public String getSchoolName() {
		return schoolName;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}

	public SchoolNotice() {
		super();
	}

	public SchoolNotice(String id){
		super(id);
	}

	@Length(min=0, max=100, message="学校id长度必须介于 0 和 100 之间")
	public String getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(String schoolId) {
		this.schoolId = schoolId;
	}
	
	@Length(min=0, max=255, message="图片长度必须介于 0 和 255 之间")
	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}
	
	@Length(min=0, max=255, message="标题长度必须介于 0 和 255 之间")
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	@Length(min=0, max=255, message="子标题长度必须介于 0 和 255 之间")
	public String getSubTitle() {
		return subTitle;
	}

	public void setSubTitle(String subTitle) {
		this.subTitle = subTitle;
	}
	
	@Length(min=0, max=255, message="状态长度必须介于 0 和 255 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
}