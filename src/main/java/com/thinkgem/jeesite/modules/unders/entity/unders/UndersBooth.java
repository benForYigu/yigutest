/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.unders.entity.unders;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 线下活动Entity
 * @author 李金辉
 * @version 2019-05-24
 */
public class UndersBooth extends DataEntity<UndersBooth> {
	
	private static final long serialVersionUID = 1L;
	private Unders undersId;		// 双选会id 父类
	private String img;		// 名称
	private String name;		// 名称
	private Integer number;		// 数量
	private Double price;		// 价格
	private String size;		// 尺寸

	private Integer orderNo;		// 尺寸

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public Integer getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}

	public UndersBooth() {
		super();
	}

	public UndersBooth(String id){
		super(id);
	}

	public UndersBooth(Unders underId){
		this.undersId = underId;
	}

	@Length(min=1, max=100, message="双选会id长度必须介于 1 和 100 之间")
	public Unders getUndersId() {
		return undersId;
	}

	public void setUndersId(Unders undersId) {
		this.undersId = undersId;
	}
	
	@Length(min=0, max=100, message="名称长度必须介于 0 和 100 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}
	
	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}
	
	@Length(min=0, max=100, message="尺寸长度必须介于 0 和 100 之间")
	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}
	
}