/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.account.entity;

import com.google.common.base.Strings;
import com.thinkgem.jeesite.API.entity.Code;
import com.thinkgem.jeesite.modules.account.dao.accountstudent.AccountStudentinfoDao;
import com.thinkgem.jeesite.modules.account.entity.accounthr.AccountCompanyhr;
import com.thinkgem.jeesite.modules.account.entity.accountschool.AccountTeacherinfo;
import com.thinkgem.jeesite.modules.account.entity.accountstudent.AccountStudentinfo;
import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 用户Entity
 * @author 李金辉
 * @version 2019-01-23
 */
public class Account extends DataEntity<Account> {
	
	private static final long serialVersionUID = 1L;

	public static final String AUTH_YES = "1";
	public static final String AUTH_NO = "2";

	public static final String ROLE_STUDENT = "1";
	public static final String ROLE_SCHOOL = "2";
	public static final String ROLE_COMPANY = "3";
	public static final String STATUS_NORMAL = "1";
	public static final String STATUS_DISABLE = "2";
	public static final String STATUS_PASS = "3";
	private String role;		// 用户权限
	private String username;		// 账户
	private String password;		// 密码
	private String accid;		// 网易云信id
	private String token;		// 网易云信token
	private String status;		// 状态
	private String remark;		// 备注

	private String authToken;		// 接口验证token

	public String getAuthToken() {
		return authToken;
	}

	public void setAuthToken(String authToken) {
		this.authToken = authToken;
	}

	public Account() {
		super();
	}

	public Account(String id){
		super(id);
	}

	@Length(min=0, max=1, message="用户权限长度必须介于 0 和 1 之间")
	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
	
	@Length(min=0, max=20, message="账户长度必须介于 0 和 20 之间")
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	@Length(min=0, max=100, message="密码长度必须介于 0 和 100 之间")
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	@Length(min=0, max=32, message="网易云信id长度必须介于 0 和 32 之间")
	public String getAccid() {
		return accid;
	}

	public void setAccid(String accid) {
		this.accid = accid;
	}
	
	@Length(min=0, max=128, message="网易云信token长度必须介于 0 和 128 之间")
	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
	@Length(min=0, max=1, message="状态长度必须介于 0 和 1 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	@Length(min=0, max=500, message="备注长度必须介于 0 和 500 之间")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}


}