/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.unders.entity.unders;

import com.thinkgem.jeesite.modules.school.entity.School;
import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.List;
import com.google.common.collect.Lists;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 线下活动Entity
 * @author 李金辉
 * @version 2019-05-24
 */
public class Unders extends DataEntity<Unders> {
	
	private static final long serialVersionUID = 1L;
	private String type;		// 类型1线下宣讲 2线下双选会
	private String schoolId;		// 学校id
	private String img;		// 标题
	private String title;		// 标题
	private String subTitle;		// 副标题
	private Date startDate;		// 开始时间
	private Date endDate;		// 结束时间
	private Date signStart;		// 报名开始
	private Date signEnd;		// 报名结束
	private String address;		// 规模数量
	private String number;		// 规模数量
	private String content;		// content
	private List<UndersBooth> undersBoothList = Lists.newArrayList();		// 子表列表

	private String schoolName;		// content
	private Object isSign;		// 是否报名 1已报名 0未
	private Object expire;		// 是否过期 0未 1已
	private Integer orderNo;		// 是否过期 0未 1已

	public Integer getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Object getExpire() {
		return expire;
	}

	public void setExpire(Object expire) {
		this.expire = expire;
	}

	public Object getIsSign() {
		return isSign;
	}

	public void setIsSign(Object isSign) {
		this.isSign = isSign;
	}

	public String getSchoolName() {
		return schoolName;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}

	public Unders() {
		super();
	}

	public Unders(String id){
		super(id);
	}

	@Length(min=1, max=1, message="类型1线下宣讲 2线下双选会长度必须介于 1 和 1 之间")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	@Length(min=0, max=100, message="学校id长度必须介于 0 和 100 之间")
	public String getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(String schoolId) {
		this.schoolId = schoolId;
	}
	
	@Length(min=0, max=255, message="标题长度必须介于 0 和 255 之间")
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	@Length(min=0, max=255, message="副标题长度必须介于 0 和 255 之间")
	public String getSubTitle() {
		return subTitle;
	}

	public void setSubTitle(String subTitle) {
		this.subTitle = subTitle;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm")
	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	
	@JsonFormat(pattern = "MM-dd HH:mm")
	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm")
	public Date getSignStart() {
		return signStart;
	}

	public void setSignStart(Date signStart) {
		this.signStart = signStart;
	}
	
	@JsonFormat(pattern = "MM-dd HH:mm")
	public Date getSignEnd() {
		return signEnd;
	}

	public void setSignEnd(Date signEnd) {
		this.signEnd = signEnd;
	}
	
	@Length(min=0, max=11, message="规模数量长度必须介于 0 和 11 之间")
	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}
	
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	public List<UndersBooth> getUndersBoothList() {
		return undersBoothList;
	}

	public void setUndersBoothList(List<UndersBooth> undersBoothList) {
		this.undersBoothList = undersBoothList;
	}
}