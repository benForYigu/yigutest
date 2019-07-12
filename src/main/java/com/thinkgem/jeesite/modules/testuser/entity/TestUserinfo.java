/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.testuser.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import javax.validation.constraints.NotNull;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 测试类Entity
 * @author Owen
 * @version 2017-10-18
 */
public class TestUserinfo extends DataEntity<TestUserinfo> {
	
	private static final long serialVersionUID = 1L;
	private String userName;		// user_name
	private Date userDste;		// user_dste
	private String userDete;		// user_dete
	
	public TestUserinfo() {
		super();
	}

	public TestUserinfo(String id){
		super(id);
	}

	@Length(min=1, max=45, message="user_name长度必须介于 1 和 45 之间")
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="user_dste不能为空")
	public Date getUserDste() {
		return userDste;
	}

	public void setUserDste(Date userDste) {
		this.userDste = userDste;
	}
	
	@Length(min=1, max=100, message="user_dete长度必须介于 1 和 100 之间")
	public String getUserDete() {
		return userDete;
	}

	public void setUserDete(String userDete) {
		this.userDete = userDete;
	}
	
}