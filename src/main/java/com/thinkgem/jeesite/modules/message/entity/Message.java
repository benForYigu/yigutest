/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.message.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

import java.util.Date;

/**
 * 消息Entity
 * @author 李金辉
 * @version 2019-04-29
 */
public class Message extends DataEntity<Message> {
	
	private static final long serialVersionUID = 1L;
	private String accountId;		// 用户
	private String teachinId;		// 宣讲id
	private String content;		// 内容
	private String isRead;		// 是否已读
	private String companyName;		// 企业名称
	private String companyLogo;		// 企业头像

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCompanyLogo() {
		return companyLogo;
	}

	public void setCompanyLogo(String companyLogo) {
		this.companyLogo = companyLogo;
	}

	public String getTeachinId() {
		return teachinId;
	}

	public void setTeachinId(String teachinId) {
		this.teachinId = teachinId;
	}

	public Message() {
		super();
	}

	public Message(String id){
		super(id);
	}

	@Length(min=1, max=100, message="用户长度必须介于 1 和 100 之间")
	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	
	@Length(min=0, max=255, message="内容长度必须介于 0 和 255 之间")
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	@Length(min=0, max=1, message="是否已读长度必须介于 0 和 1 之间")
	public String getIsRead() {
		return isRead;
	}

	public void setIsRead(String isRead) {
		this.isRead = isRead;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm")
	@Override
	public Date getCreateDate() {
		return createDate;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm")
	@Override
	public Date getUpdateDate() {
		return updateDate;
	}
}