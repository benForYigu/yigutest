/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.account.entity.accounthr;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * hrEntity
 * @author 李金辉
 * @version 2019-01-25
 */
public class AccountCompanyhr extends DataEntity<AccountCompanyhr> {
	
	private static final long serialVersionUID = 1L;
	public  static final String  	AUTH_YES = "1";
	public  static final String  	AUTH_NO = "2";

	private String accountId;		// 用户id
	private String companyId;		// 企业ID
	private String avatar;		// 头像
	private String realname;		// 真实姓名
	private String phone;		// 电话
	private Object major;		// 职业
	private Object sex;		// 性别
	private String email;		// 邮箱
	private Object status;		// 认证状态
	private String card;		// 企业联系人名片
	private Integer coin;		// 港币
	private String remark;		// 备注
	private String vip;		// vipId
	private String vipName;		// vip名称

	private String companyName;		//

	public Integer getCoin() {
		return coin;
	}

	public void setCoin(Integer coin) {
		this.coin = coin;
	}

	public String getVipName() {
		return vipName;
	}

	public void setVipName(String vipName) {
		this.vipName = vipName;
	}

	public String getVip() {
		return vip;
	}

	public void setVip(String vip) {
		this.vip = vip;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public AccountCompanyhr() {
		super();
	}

	public AccountCompanyhr(String id){
		super(id);
	}

	@Length(min=0, max=100, message="用户id长度必须介于 0 和 100 之间")
	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	
	@Length(min=0, max=100, message="企业ID长度必须介于 0 和 100 之间")
	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	

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
	

	public Object getMajor() {
		return major;
	}

	public void setMajor(Object major) {
		this.major = major;
	}
	

	public Object getSex() {
		return sex;
	}

	public void setSex(Object sex) {
		this.sex = sex;
	}
	
	@Length(min=0, max=255, message="邮箱长度必须介于 0 和 255 之间")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	

	public Object getStatus() {
		return status;
	}

	public void setStatus(Object status) {
		this.status = status;
	}
	
	@Length(min=0, max=255, message="企业联系人名片长度必须介于 0 和 255 之间")
	public String getCard() {
		return card;
	}

	public void setCard(String card) {
		this.card = card;
	}
	
	@Length(min=0, max=500, message="备注长度必须介于 0 和 500 之间")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}