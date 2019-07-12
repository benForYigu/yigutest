/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.school.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 学校Entity
 * @author 李金辉
 * @version 2019-01-24
 */
public class School extends DataEntity<School> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 全称
	private String hot;		// 是否热门
	private String imgId;		// 图片id
	private Object type;		// 类型
	private String jurisdiction;		// 隶属
	private String code;		// 教育部统一编码
	private String introduce;		// 简介
	private Object fD11;		// 双一流
	private String slD11;		// 双一流学科数量
	private Object f211;		// 211工程
	private String f985pt;		// 985平台
	private Object f985;		// 985工程
	private Object natrue;		// 性质
	private String profession;		// 类别
	private String matriculate;		// 录取方式
	private String area;		// 区域
	private Object city;		// 城市
	private String logo;		// LOGO
	private Integer sort;		// LOGO

	public String getHot() {
		return hot;
	}

	public void setHot(String hot) {
		this.hot = hot;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public School() {
		super();
	}

	public School(String id){
		super(id);
	}

	@Length(min=0, max=255, message="全称长度必须介于 0 和 255 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	

	public String getImgId() {
		return imgId;
	}

	public void setImgId(String imgId) {
		this.imgId = imgId;
	}
	

	public Object getType() {
		return type;
	}

	public void setType(Object type) {
		this.type = type;
	}

	public String getJurisdiction() {
		return jurisdiction;
	}

	public void setJurisdiction(String jurisdiction) {
		this.jurisdiction = jurisdiction;
	}
	
	@Length(min=0, max=50, message="教育部统一编码长度必须介于 0 和 50 之间")
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	public String getIntroduce() {
		return introduce;
	}

	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}
	

	public Object getFD11() {
		return fD11;
	}

	public void setFD11(Object fD11) {
		this.fD11 = fD11;
	}
	

	public String getSlD11() {
		return slD11;
	}

	public void setSlD11(String slD11) {
		this.slD11 = slD11;
	}
	

	public Object getF211() {
		return f211;
	}

	public void setF211(Object f211) {
		this.f211 = f211;
	}
	

	public String getF985pt() {
		return f985pt;
	}

	public void setF985pt(String f985pt) {
		this.f985pt = f985pt;
	}
	

	public Object getF985() {
		return f985;
	}

	public void setF985(Object f985) {
		this.f985 = f985;
	}
	

	public Object getNatrue() {
		return natrue;
	}

	public void setNatrue(Object natrue) {
		this.natrue = natrue;
	}
	

	public String getProfession() {
		return profession;
	}

	public void setProfession(String profession) {
		this.profession = profession;
	}
	

	public String getMatriculate() {
		return matriculate;
	}

	public void setMatriculate(String matriculate) {
		this.matriculate = matriculate;
	}
	

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}
	

	public Object getCity() {
		return city;
	}

	public void setCity(Object city) {
		this.city = city;
	}
	
	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}
	
}