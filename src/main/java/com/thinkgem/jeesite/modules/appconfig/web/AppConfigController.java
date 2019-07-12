/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.appconfig.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thinkgem.jeesite.common.utils.UploadUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.appconfig.entity.AppConfig;
import com.thinkgem.jeesite.modules.appconfig.service.AppConfigService;

/**
 * 配置数据Controller
 * @author 李金辉
 * @version 2019-03-26
 */
@Controller
@RequestMapping(value = "${adminPath}/appconfig/appConfig")
public class AppConfigController extends BaseController {

	@Autowired
	private AppConfigService appConfigService;
	@Value("${otherPic}")
	private String otherPic;

	@ModelAttribute
	public AppConfig get(@RequestParam(required=false) String id) {
		AppConfig entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = appConfigService.get(id);
		}
		if (entity == null){
			entity = new AppConfig();
		}
		return entity;
	}
	
	@RequiresPermissions("appconfig:appConfig:view")
	@RequestMapping(value = {"list", ""})
	public String list(AppConfig appConfig, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AppConfig> page = appConfigService.findPage(new Page<AppConfig>(request, response), appConfig); 
		model.addAttribute("page", page);
		return "modules/appconfig/appConfigList";
	}

	@RequiresPermissions("appconfig:appConfig:view")
	@RequestMapping(value = "form")
	public String form(AppConfig appConfig, Model model) {
		model.addAttribute("appConfig", appConfig);
		return "modules/appconfig/appConfigForm";
	}

	@RequiresPermissions("appconfig:appConfig:edit")
	@RequestMapping(value = "save")
	public String save(AppConfig appConfig, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, appConfig)){
			return form(appConfig, model);
		}
		appConfigService.save(appConfig);
		addMessage(redirectAttributes, "保存配置数据成功");
		return "redirect:"+Global.getAdminPath()+"/appconfig/appConfig/?repage";
	}
	
	@RequiresPermissions("appconfig:appConfig:edit")
	@RequestMapping(value = "delete")
	public String delete(AppConfig appConfig, RedirectAttributes redirectAttributes) {
		appConfigService.delete(appConfig);
		addMessage(redirectAttributes, "删除配置数据成功");
		return "redirect:"+Global.getAdminPath()+"/appconfig/appConfig/?repage";
	}


	@RequestMapping(value = "uploads")
	@ResponseBody
	public Object uploads(HttpServletRequest httpServletRequest) {
		UploadUtils uploadUtils=new UploadUtils(otherPic+"app/");
		uploadUtils.setDirName("files");
		return uploadUtils.uploadFile(httpServletRequest);
	}

}