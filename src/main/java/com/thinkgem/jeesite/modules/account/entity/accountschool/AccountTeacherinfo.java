/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.account.entity.accountschool;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 学校人员Entity
 * @author 李金辉
 * @version 2019-01-28
 */
public class AccountTeacherinfo extends DataEntity<AccountTeacherinfo> {
	
	private static final long serialVersionUID = 1L;
	private String accountId;		// 用户id
	private String schoolId;		// 学校
	private String avatar;		// 头像
	private String realname;		// 真实姓名
	private String phone;		// 电话
	private String sex;		// 性别
	private String idCard;		// 身份证
	private String idCardStatus;		// 身份证审核状态
	private String email;		// 邮箱
	private String remark;		// 备注

	private String schoolName;		// 学校名称

	public String getSchoolName() {
		return schoolName;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}

	public AccountTeacherinfo() {
		super();
	}

	public AccountTeacherinfo(String id){
		super(id);
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	@Length(min=0, max=100, message="学校长度必须介于 0 和 100 之间")
	public String getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(String schoolId) {
		this.schoolId = schoolId;
	}
	
	@Length(min=0, max=200, message="头像长度必须介于 0 和 200 之间")
	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	
	@Length(min=0, max=100, message="真实姓名长度必须介于 0 和 100 之间")
	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}
	
	@Length(min=0, max=20, message="电话长度必须介于 0 和 20 之间")
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}
	
	@Length(min=0, max=255, message="身份证长度必须介于 0 和 255 之间")
	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}
	

	public String getIdCardStatus() {
		return idCardStatus;
	}

	public void setIdCardStatus(String idCardStatus) {
		this.idCardStatus = idCardStatus;
	}
	
	@Length(min=0, max=255, message="邮箱长度必须介于 0 和 255 之间")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	@Length(min=0, max=500, message="备注长度必须介于 0 和 500 之间")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}