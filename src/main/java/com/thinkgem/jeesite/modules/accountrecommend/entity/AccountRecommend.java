/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.accountrecommend.entity;

import com.thinkgem.jeesite.modules.account.entity.Account;
import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 推荐记录Entity
 * @author 李金辉
 * @version 2019-05-10
 */
public class AccountRecommend extends DataEntity<AccountRecommend> {
	
	private static final long serialVersionUID = 1L;
	private String accountId;		// 推荐人id
	private String beAccountId;		// 被推荐人id
	private Account inviteAccount;		// 被推荐人id
	private Account beInviteAccount;		// 被推荐人id
	private String  iavatar;		// 被推荐人id
	private String  irealname;		// 被推荐人id
	private String  bavatar;		// 被推荐人id
	private String  brealname;		// 被推荐人id

	public String getIavatar() {
		return iavatar;
	}

	public void setIavatar(String iavatar) {
		this.iavatar = iavatar;
	}

	public String getIrealname() {
		return irealname;
	}

	public void setIrealname(String irealname) {
		this.irealname = irealname;
	}

	public String getBavatar() {
		return bavatar;
	}

	public void setBavatar(String bavatar) {
		this.bavatar = bavatar;
	}

	public String getBrealname() {
		return brealname;
	}

	public void setBrealname(String brealname) {
		this.brealname = brealname;
	}

	public Account getInviteAccount() {
		return inviteAccount;
	}

	public void setInviteAccount(Account inviteAccount) {
		this.inviteAccount = inviteAccount;
	}

	public Account getBeInviteAccount() {
		return beInviteAccount;
	}

	public void setBeInviteAccount(Account beInviteAccount) {
		this.beInviteAccount = beInviteAccount;
	}

	public AccountRecommend() {
		super();
	}

	public AccountRecommend(String id){
		super(id);
	}

	@Length(min=1, max=100, message="推荐人id长度必须介于 1 和 100 之间")
	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	
	@Length(min=1, max=100, message="被推荐人id长度必须介于 1 和 100 之间")
	public String getBeAccountId() {
		return beAccountId;
	}

	public void setBeAccountId(String beAccountId) {
		this.beAccountId = beAccountId;
	}
	
}