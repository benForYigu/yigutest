/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.company.entity.address;

import org.hibernate.validator.constraints.Length;
import com.thinkgem.jeesite.modules.sys.entity.Area;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 企业地址Entity
 * @author 李金辉
 * @version 2019-01-29
 */
public class CompanyAddress extends DataEntity<CompanyAddress> {
	
	private static final long serialVersionUID = 1L;
	private String companyId;		// company_id
	private String name;		// 名字
	private String phone;		// 联系电话
	private Object province;		// 省份
	private Object city;		// 市
	private Object area;		// 省市区域
	private String address;		// 详细地址

	public CompanyAddress() {
		super();
	}

	public CompanyAddress(String id){
		super(id);
	}

	@Length(min=0, max=100, message="company_id长度必须介于 0 和 100 之间")
	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	
	@Length(min=0, max=255, message="名字长度必须介于 0 和 255 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Object getProvince() {
		return province;
	}

	public void setProvince(Object province) {
		this.province = province;
	}

	public Object getCity() {
		return city;
	}

	public void setCity(Object city) {
		this.city = city;
	}

	public Object getArea() {
		return area;
	}

	public void setArea(Object area) {
		this.area = area;
	}

	@Length(min=0, max=255, message="详细地址长度必须介于 0 和 255 之间")
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
}