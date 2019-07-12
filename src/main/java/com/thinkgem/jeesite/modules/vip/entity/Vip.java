/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.vip.entity;

import com.alibaba.druid.sql.dialect.mysql.ast.MysqlForeignKey;
import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * vipEntity
 * @author 李金辉
 * @version 2019-03-23
 */
public class Vip extends DataEntity<Vip> {
	
	private static final long serialVersionUID = 1L;
	public static final String VIP_YES = "1";
	public static final String VIP_NO = "0";
	public static final String PAY_STATUS_YES = "1";//可购买
	public static final String PAY_STATUS_NOT = "2";//已购买高级
	public static final String PAY_STATUS_PAYED = "3";//已购买
	private String name;		// 名称
	private Double price;		// 价格
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

	private Object payStatus;		// 支付状态

	public Integer getInterviewTime() {
		return interviewTime;
	}

	public void setInterviewTime(Integer interviewTime) {
		this.interviewTime = interviewTime;
	}

	public Object getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(Object payStatus) {
		this.payStatus = payStatus;
	}

	public Vip() {
		super();
	}

	public Vip(String id){
		super(id);
	}

	@Length(min=0, max=100, message="名称长度必须介于 0 和 100 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
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
	

	public Object getExchangeMeeting() {
		return exchangeMeeting;
	}

	public void setExchangeMeeting(Object exchangeMeeting) {
		this.exchangeMeeting = exchangeMeeting;
	}
	
}