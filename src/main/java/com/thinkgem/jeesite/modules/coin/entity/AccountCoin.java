/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.coin.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 岗币记录Entity
 * @author 李金辉
 * @version 2019-05-10
 */
public class AccountCoin extends DataEntity<AccountCoin> {
	
	private static final long serialVersionUID = 1L;
	public static final String TYPE_RECOMMEND = "1";//1推荐人  2被推荐
	public static final String TYPE_BE_RECOMMEND = "2";
	private String accountId;		// 用户
	private String type;		// 来源类型
	private Integer coin;		// 港币数量
	private String aboutId;		// 相关ID
	
	public AccountCoin() {
		super();
	}

	public AccountCoin(String id){
		super(id);
	}

	@Length(min=0, max=100, message="用户长度必须介于 0 和 100 之间")
	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	
	@Length(min=1, max=1, message="来源类型长度必须介于 1 和 1 之间")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public Integer getCoin() {
		return coin;
	}

	public void setCoin(Integer coin) {
		this.coin = coin;
	}
	
	@Length(min=0, max=100, message="相关ID长度必须介于 0 和 100 之间")
	public String getAboutId() {
		return aboutId;
	}

	public void setAboutId(String aboutId) {
		this.aboutId = aboutId;
	}
	
}