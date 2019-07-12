/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.unders.web.companyfile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.unders.entity.companyfile.UndersCompanyFile;
import com.thinkgem.jeesite.modules.unders.service.companyfile.UndersCompanyFileService;

/**
 * 企业双选会资料Controller
 * @author 李金辉
 * @version 2019-05-27
 */
@Controller
@RequestMapping(value = "${adminPath}/unders/companyfile/undersCompanyFile")
public class UndersCompanyFileController extends BaseController {

	@Autowired
	private UndersCompanyFileService undersCompanyFileService;
	
	@ModelAttribute
	public UndersCompanyFile get(@RequestParam(required=false) String id) {
		UndersCompanyFile entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = undersCompanyFileService.get(id);
		}
		if (entity == null){
			entity = new UndersCompanyFile();
		}
		return entity;
	}
	
	@RequiresPermissions("unders:companyfile:undersCompanyFile:view")
	@RequestMapping(value = {"list", ""})
	public String list(UndersCompanyFile undersCompanyFile, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<UndersCompanyFile> page = undersCompanyFileService.findPage(new Page<UndersCompanyFile>(request, response), undersCompanyFile); 
		model.addAttribute("page", page);
		return "modules/unders/companyfile/undersCompanyFileList";
	}

	@RequiresPermissions("unders:companyfile:undersCompanyFile:view")
	@RequestMapping(value = "form")
	public String form(UndersCompanyFile undersCompanyFile, Model model) {
		model.addAttribute("undersCompanyFile", undersCompanyFile);
		return "modules/unders/companyfile/undersCompanyFileForm";
	}

	@RequiresPermissions("unders:companyfile:undersCompanyFile:edit")
	@RequestMapping(value = "save")
	public String save(UndersCompanyFile undersCompanyFile, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, undersCompanyFile)){
			return form(undersCompanyFile, model);
		}
		undersCompanyFileService.save(undersCompanyFile);
		addMessage(redirectAttributes, "保存企业双选会资料成功");
		return "redirect:"+Global.getAdminPath()+"/unders/companyfile/undersCompanyFile/?repage";
	}
	
	@RequiresPermissions("unders:companyfile:undersCompanyFile:edit")
	@RequestMapping(value = "delete")
	public String delete(UndersCompanyFile undersCompanyFile, RedirectAttributes redirectAttributes) {
		undersCompanyFileService.delete(undersCompanyFile);
		addMessage(redirectAttributes, "删除企业双选会资料成功");
		return "redirect:"+Global.getAdminPath()+"/unders/companyfile/undersCompanyFile/?repage";
	}

}