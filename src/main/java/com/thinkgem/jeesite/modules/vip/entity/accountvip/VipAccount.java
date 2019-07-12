/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.vip.entity.accountvip;

import com.thinkgem.jeesite.modules.vip.entity.Vip;
import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 用户权益Entity
 * @author 李金辉
 * @version 2019-03-23
 */
public class VipAccount extends DataEntity<VipAccount> {
	
	private static final long serialVersionUID = 1L;
	private String accountId;		// 用户id
	private Object offCampusInformation;		// 校外信息发布
	private Object profession;		// 招聘岗位
	private Object underTeachinRead;		// 线下宣讲查看
	private Object underTeachinSign;		// 线下宣讲报名
	private Object doubleRead;		// 双选会查看
	private Object doubleSign;		// 双选会报名
	private Integer teachin;		// 宣讲
	private Integer interviewTime;		// 宣讲
	private Integer video;		// 企业宣传视频
	private Object exchangeMeeting;		// 校企交流会

	public Integer getInterviewTime() {
		return interviewTime;
	}

	public void setInterviewTime(Integer interviewTime) {
		this.interviewTime = interviewTime;
	}

	public VipAccount() {
		super();
	}

	public VipAccount(String id){
		super(id);
	}

	public VipAccount(Vip vip){
		this.offCampusInformation=(String)vip.getOffCampusInformation();
		this.profession=(String)vip.getProfession();
		this.underTeachinRead=(String)vip.getUnderTeachinRead();
		this.underTeachinSign=(String)vip.getUnderTeachinSign();
		this.doubleRead=(String)vip.getDoubleRead();
		this.doubleSign=(String)vip.getDoubleSign();
		this.teachin=vip.getTeachin();
		this.video=vip.getVideo();
		this.exchangeMeeting=(String)vip.getExchangeMeeting();
	}

	@Length(min=0, max=100, message="用户id长度必须介于 0 和 100 之间")
	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}


	public Object getOffCampusInformation() {
		return offCampusInformation;
	}

	public void setOffCampusInformation(Object offCampusInformation) {
		this.offCampusInformation = offCampusInformation;
	}

	public Object getProfession() {
		return profession;
	}

	public void setProfession(Object profession) {
		this.profession = profession;
	}

	public Object getUnderTeachinRead() {
		return underTeachinRead;
	}

	public void setUnderTeachinRead(Object underTeachinRead) {
		this.underTeachinRead = underTeachinRead;
	}

	public Object getUnderTeachinSign() {
		return underTeachinSign;
	}

	public void setUnderTeachinSign(Object underTeachinSign) {
		this.underTeachinSign = underTeachinSign;
	}

	public Object getDoubleRead() {
		return doubleRead;
	}

	public void setDoubleRead(Object doubleRead) {
		this.doubleRead = doubleRead;
	}

	public Object getDoubleSign() {
		return doubleSign;
	}

	public void setDoubleSign(Object doubleSign) {
		this.doubleSign = doubleSign;
	}

	public Object getExchangeMeeting() {
		return exchangeMeeting;
	}

	public void setExchangeMeeting(Object exchangeMeeting) {
		this.exchangeMeeting = exchangeMeeting;
	}

	public void setDoubleSign(String doubleSign) {
		this.doubleSign = doubleSign;
	}
	
	public Integer getTeachin() {
		return teachin;
	}

	public void setTeachin(Integer teachin) {
		this.teachin = teachin;
	}
	
	public Integer getVideo() {
		return video;
	}

	public void setVideo(Integer video) {
		this.video = video;
	}
	

	
}