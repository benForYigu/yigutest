/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.resume.web.other;

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
import com.thinkgem.jeesite.modules.resume.entity.other.StudentResumeOther;
import com.thinkgem.jeesite.modules.resume.service.other.StudentResumeOtherService;

/**
 * 简历其他Controller
 * @author 李金辉
 * @version 2019-01-28
 */
@Controller
@RequestMapping(value = "${adminPath}/resume/other/studentResumeOther")
public class StudentResumeOtherController extends BaseController {

	@Autowired
	private StudentResumeOtherService studentResumeOtherService;
	
	@ModelAttribute
	public StudentResumeOther get(@RequestParam(required=false) String id) {
		StudentResumeOther entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = studentResumeOtherService.get(id);
		}
		if (entity == null){
			entity = new StudentResumeOther();
		}
		return entity;
	}
	
	@RequiresPermissions("resume:other:studentResumeOther:view")
	@RequestMapping(value = {"list", ""})
	public String list(StudentResumeOther studentResumeOther, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<StudentResumeOther> page = studentResumeOtherService.findPage(new Page<StudentResumeOther>(request, response), studentResumeOther); 
		model.addAttribute("page", page);
		return "modules/resume/other/studentResumeOtherList";
	}

	@RequiresPermissions("resume:other:studentResumeOther:view")
	@RequestMapping(value = "form")
	public String form(StudentResumeOther studentResumeOther, Model model) {
		model.addAttribute("studentResumeOther", studentResumeOther);
		return "modules/resume/other/studentResumeOtherForm";
	}

	@RequiresPermissions("resume:other:studentResumeOther:edit")
	@RequestMapping(value = "save")
	public String save(StudentResumeOther studentResumeOther, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, studentResumeOther)){
			return form(studentResumeOther, model);
		}
		studentResumeOtherService.save(studentResumeOther);
		addMessage(redirectAttributes, "保存简历其他成功");
		return "redirect:"+Global.getAdminPath()+"/resume/other/studentResumeOther/?repage";
	}
	
	@RequiresPermissions("resume:other:studentResumeOther:edit")
	@RequestMapping(value = "delete")
	public String delete(StudentResumeOther studentResumeOther, RedirectAttributes redirectAttributes) {
		studentResumeOtherService.delete(studentResumeOther);
		addMessage(redirectAttributes, "删除简历其他成功");
		return "redirect:"+Global.getAdminPath()+"/resume/other/studentResumeOther/?repage";
	}

}