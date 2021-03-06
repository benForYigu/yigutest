/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.school.web;

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
import com.thinkgem.jeesite.modules.school.entity.School;
import com.thinkgem.jeesite.modules.school.service.SchoolService;

/**
 * 学校Controller
 * @author 李金辉
 * @version 2019-01-24
 */
@Controller
@RequestMapping(value = "${adminPath}/school/school")
public class SchoolController extends BaseController {

	@Autowired
	private SchoolService schoolService;
	
	@ModelAttribute
	public School get(@RequestParam(required=false) String id) {
		School entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = schoolService.get(id);
		}
		if (entity == null){
			entity = new School();
		}
		return entity;
	}
	
	@RequiresPermissions("school:school:view")
	@RequestMapping(value = {"list", ""})
	public String list(School school, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<School> page = schoolService.findPage(new Page<School>(request, response), school); 
		model.addAttribute("page", page);
		return "modules/school/schoolList";
	}

	@RequiresPermissions("school:school:view")
	@RequestMapping(value = "form")
	public String form(School school, Model model) {
		model.addAttribute("school", school);
		return "modules/school/schoolForm";
	}

	@RequiresPermissions("school:school:edit")
	@RequestMapping(value = "save")
	public String save(School school, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, school)){
			return form(school, model);
		}
		schoolService.save(school);
		addMessage(redirectAttributes, "保存学校成功");
		return "redirect:"+Global.getAdminPath()+"/school/school/?repage";
	}
	
	@RequiresPermissions("school:school:edit")
	@RequestMapping(value = "delete")
	public String delete(School school, RedirectAttributes redirectAttributes) {
		schoolService.delete(school);
		addMessage(redirectAttributes, "删除学校成功");
		return "redirect:"+Global.getAdminPath()+"/school/school/?repage";
	}

}