/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.interaction.teachin.entity.chat;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 宣讲聊天Entity
 * @author 李金辉
 * @version 2019-03-20
 */
public class InteractionTeachinChat extends DataEntity<InteractionTeachinChat> {
	
	private static final long serialVersionUID = 1L;
	private String teachinId;		// 宣讲id
	private String accountId;		// 用户id
	private String avatar;		// 头像
	private String realname;		// 姓名
	private String content;		// 内容

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public InteractionTeachinChat() {
		super();
	}

	public InteractionTeachinChat(String id){
		super(id);
	}

	@Length(min=0, max=255, message="宣讲id长度必须介于 0 和 255 之间")
	public String getTeachinId() {
		return teachinId;
	}

	public void setTeachinId(String teachinId) {
		this.teachinId = teachinId;
	}
	
	@Length(min=0, max=255, message="用户id长度必须介于 0 和 255 之间")
	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	
	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	
	@Length(min=0, max=255, message="姓名长度必须介于 0 和 255 之间")
	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}
	
}