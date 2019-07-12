/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.unders.web.unders;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.common.base.Strings;
import com.thinkgem.jeesite.modules.school.entity.School;
import com.thinkgem.jeesite.modules.school.service.SchoolService;
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
import com.thinkgem.jeesite.modules.unders.entity.unders.Unders;
import com.thinkgem.jeesite.modules.unders.service.unders.UndersService;

/**
 * 线下活动Controller
 * @author 李金辉
 * @version 2019-05-24
 */
@Controller
@RequestMapping(value = "${adminPath}/unders/unders/unders")
public class UndersController extends BaseController {

	@Autowired
	private UndersService undersService;

	@Autowired
	private SchoolService schoolService;

	@ModelAttribute
	public Unders get(@RequestParam(required=false) String id) {
		Unders entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = undersService.get(id);
		}
		if (entity == null){
			entity = new Unders();
		}
		return entity;
	}
	
	@RequiresPermissions("unders:unders:unders:view")
	@RequestMapping(value = {"list", ""})
	public String list(Unders unders, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Unders> page = undersService.findPage(new Page<Unders>(request, response), unders); 
		model.addAttribute("page", page);
		return "modules/unders/unders/undersList";
	}

	@RequiresPermissions("unders:unders:unders:view")
	@RequestMapping(value = "form")
	public String form(Unders unders, Model model) {

		model.addAttribute("unders", unders);
		model.addAttribute("schools", schoolService.findList(new School()));
		return "modules/unders/unders/undersForm";
	}

	@RequiresPermissions("unders:unders:unders:edit")
	@RequestMapping(value = "save")
	public String save(Unders unders, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, unders)){
			return form(unders, model);
		}
		if(Strings.isNullOrEmpty(unders.getId())){
			unders.setImg(schoolService.get(unders.getSchoolId()).getLogo());
		}
		undersService.save(unders);
		addMessage(redirectAttributes, "保存线下活动成功");
		return "redirect:"+Global.getAdminPath()+"/unders/unders/unders/?repage";
	}
	
	@RequiresPermissions("unders:unders:unders:edit")
	@RequestMapping(value = "delete")
	public String delete(Unders unders, RedirectAttributes redirectAttributes) {
		undersService.delete(unders);
		addMessage(redirectAttributes, "删除线下活动成功");
		return "redirect:"+Global.getAdminPath()+"/unders/unders/unders/?repage";
	}

}