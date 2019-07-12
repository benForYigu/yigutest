/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.testsex.entity;

import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;
import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 测试表Entity
 * @author ben
 * @version 2019-07-05
 */
public class ABenTest extends DataEntity<ABenTest> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 姓名
	private String sex;		// 性别
	private String mobile;		// 手机号
	private String status;		// 状态 1正常2审核中3禁止
	private String remark;		// 备注
	
	public ABenTest() {
		super();
	}

	public ABenTest(String id){
		super(id);
	}

	@Length(min=1, max=36, message="姓名长度必须介于 1 和 36 之间")
	@ExcelField(title="姓名",align=2, sort=10)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=1, max=10, message="性别长度必须介于 1 和 10 之间")
	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}
	
	@Length(min=0, max=16, message="手机号长度必须介于 0 和 16 之间")
	@ExcelField(title="电话",align=2, sort=20)
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	@Length(min=0, max=1, message="状态 1正常2审核中3禁止长度必须介于 0 和 1 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	@Length(min=0, max=500, message="备注长度必须介于 0 和 500 之间")
	@ExcelField(title="备注",align=2, sort=30)
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}