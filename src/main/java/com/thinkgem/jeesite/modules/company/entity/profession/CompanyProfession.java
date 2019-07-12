/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.company.entity.profession;

import com.thinkgem.jeesite.modules.company.entity.Company;
import org.apache.xmlbeans.impl.xb.xsdschema.Public;
import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 企业职位Entity
 * @author 李金辉
 * @version 2019-01-30
 */
public class CompanyProfession extends DataEntity<CompanyProfession> {
	
	private static final long serialVersionUID = 1L;
	public static final String SOURCE_SCHOOG = "1";
	public static final String SOURCE_OUT = "2";
	private String source;		// 1校招  2社招
	private String companyId;		// company_id
	private String companyName;		//企业名称
	private String name;		// 名称
	private Object type;		// 职位类型
	private Object city;		// 城市
	private String area;		// 区域
	private Object experience;		// 工作经验
	private Object major;		// 专业
	private Object educationalBackground;		// 学历
	private String tag;		// 标签,逗号隔开
	private Object nature;		// 性质
	private Object salary;		// 薪资
	private String number;		// 招聘人数
	private Object board;		// 吃住
	private Object trafficSubsidy;		// 交通补贴
	private String content;		// 内容
	private String hot;		// 内容
	private Integer sort;		// 排序

	private String cityName;		// 城市名称
	private Company company;		//

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getHot() {
		return hot;
	}

	public void setHot(String hot) {
		this.hot = hot;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public CompanyProfession() {
		super();
	}

	public CompanyProfession(String id){
		super(id);
	}

	public Object getNature() {
		return nature;
	}

	public void setNature(Object nature) {
		this.nature = nature;
	}

	@Length(min=1, max=100, message="company_id长度必须介于 1 和 100 之间")
	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	
	@Length(min=0, max=255, message="名称长度必须介于 0 和 255 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	

	public Object getType() {
		return type;
	}

	public void setType(Object type) {
		this.type = type;
	}
	
	public Object getCity() {
		return city;
	}

	public void setCity(Object city) {
		this.city = city;
	}
	

	public Object getExperience() {
		return experience;
	}

	public void setExperience(Object experience) {
		this.experience = experience;
	}
	

	public Object getMajor() {
		return major;
	}

	public void setMajor(Object major) {
		this.major = major;
	}
	

	public Object getEducationalBackground() {
		return educationalBackground;
	}

	public void setEducationalBackground(Object educationalBackground) {
		this.educationalBackground = educationalBackground;
	}
	
	@Length(min=0, max=255, message="标签,逗号隔开长度必须介于 0 和 255 之间")
	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}
	

	public Object getSalary() {
		return salary;
	}

	public void setSalary(Object salary) {
		this.salary = salary;
	}
	

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}
	

	public Object getBoard() {
		return board;
	}

	public void setBoard(Object board) {
		this.board = board;
	}
	

	public Object getTrafficSubsidy() {
		return trafficSubsidy;
	}

	public void setTrafficSubsidy(Object trafficSubsidy) {
		this.trafficSubsidy = trafficSubsidy;
	}
	
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
}