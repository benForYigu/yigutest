/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.smsrecord.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import javax.validation.constraints.NotNull;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 验证码Entity
 * @author 李金辉
 * @version 2019-01-30
 */
public class SmsRecord extends DataEntity<SmsRecord> {
	
	private static final long serialVersionUID = 1L;
	public static final String STATUS_WAITING = "0";
	public static final String STATUS_VERIFIED = "1";
	private String platformNo;		// 平台记录号
	private String phone;		// 手机号
	private String code;		// 验证码
	private String status;		// 验证状态
	private Date sendTime;		// 发送时间
	private Date verifyTime;		// 验证时间
	
	public SmsRecord() {
		super();
	}

	public SmsRecord(String id){
		super(id);
	}

	@Length(min=1, max=100, message="平台记录号长度必须介于 1 和 100 之间")
	public String getPlatformNo() {
		return platformNo;
	}

	public void setPlatformNo(String platformNo) {
		this.platformNo = platformNo;
	}
	
	@Length(min=1, max=50, message="手机号长度必须介于 1 和 50 之间")
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	@Length(min=1, max=10, message="验证码长度必须介于 1 和 10 之间")
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	@Length(min=1, max=11, message="验证状态长度必须介于 1 和 11 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="发送时间不能为空")
	public Date getSendTime() {
		return sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getVerifyTime() {
		return verifyTime;
	}

	public void setVerifyTime(Date verifyTime) {
		this.verifyTime = verifyTime;
	}
	
}