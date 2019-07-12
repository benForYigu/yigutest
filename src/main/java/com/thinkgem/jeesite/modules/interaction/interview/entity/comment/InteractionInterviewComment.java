/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.interaction.interview.entity.comment;


import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 面试评论Entity
 * @author 李金辉
 * @version 2019-01-30
 */
public class InteractionInterviewComment extends DataEntity<InteractionInterviewComment> {
	
	private static final long serialVersionUID = 1L;
	private String interviewId;		// 学生id
	private Integer charm;		// 魅力度
	private Integer affinity;		// 亲和度
	private Integer quality;		// 素质度
	private Integer positive;		// 积极度
	private Integer culture;		// 文化度
	private Integer consciousness;		// 意识度
	private Integer interest;		// 兴趣度
	private Integer trust;		// 信赖度
	private Integer integrate;		// 融入度
	
	public InteractionInterviewComment() {
		super();
	}

	public InteractionInterviewComment(String id){
		super(id);
	}

	public String getInterviewId() {
		return interviewId;
	}

	public void setInterviewId(String interviewId) {
		this.interviewId = interviewId;
	}
	
	public Integer getCharm() {
		return charm;
	}

	public void setCharm(Integer charm) {
		this.charm = charm;
	}
	
	public Integer getAffinity() {
		return affinity;
	}

	public void setAffinity(Integer affinity) {
		this.affinity = affinity;
	}
	
	public Integer getQuality() {
		return quality;
	}

	public void setQuality(Integer quality) {
		this.quality = quality;
	}
	
	public Integer getPositive() {
		return positive;
	}

	public void setPositive(Integer positive) {
		this.positive = positive;
	}
	
	public Integer getCulture() {
		return culture;
	}

	public void setCulture(Integer culture) {
		this.culture = culture;
	}
	
	public Integer getConsciousness() {
		return consciousness;
	}

	public void setConsciousness(Integer consciousness) {
		this.consciousness = consciousness;
	}
	
	public Integer getInterest() {
		return interest;
	}

	public void setInterest(Integer interest) {
		this.interest = interest;
	}
	
	public Integer getTrust() {
		return trust;
	}

	public void setTrust(Integer trust) {
		this.trust = trust;
	}
	
	public Integer getIntegrate() {
		return integrate;
	}

	public void setIntegrate(Integer integrate) {
		this.integrate = integrate;
	}
	
}