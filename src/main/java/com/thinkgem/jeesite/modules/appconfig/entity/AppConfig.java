/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.appconfig.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 配置数据Entity
 * @author 李金辉
 * @version 2019-03-26
 */
public class AppConfig extends DataEntity<AppConfig> {
	
	private static final long serialVersionUID = 1L;
	public  static final String APK_COM = "1";
	public  static final String APK_STU = "2";
	public  static final String IOS_COM = "3";
	public  static final String IOS_STU = "4";
	private String version;		// 版本名称
	private String name;		// 版本好
	private String configType;		// 适配版本
	private String title;		// 标题
	private String content;		// 内容
	private String url;		// 地址

	public String getConfigType() {
		return configType;
	}

	public void setConfigType(String configType) {
		this.configType = configType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public AppConfig() {
		super();
	}

	public AppConfig(String id){
		super(id);
	}

	@Length(min=0, max=100, message="版本长度必须介于 0 和 100 之间")
	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}
	
	@Length(min=0, max=100, message="标题长度必须介于 0 和 100 之间")
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	@Length(min=0, max=1000, message="地址长度必须介于 0 和 1000 之间")
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
}