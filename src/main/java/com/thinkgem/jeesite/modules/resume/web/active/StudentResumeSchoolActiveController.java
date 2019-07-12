/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.resume.web.active;

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
import com.thinkgem.jeesite.modules.resume.entity.active.StudentResumeSchoolActive;
import com.thinkgem.jeesite.modules.resume.service.active.StudentResumeSchoolActiveService;

/**
 * 简历活动Controller
 * @author 李金辉
 * @version 2019-05-06
 */
@Controller
@RequestMapping(value = "${adminPath}/resume/active/studentResumeSchoolActive")
public class StudentResumeSchoolActiveController extends BaseController {

	@Autowired
	private StudentResumeSchoolActiveService studentResumeSchoolActiveService;
	
	@ModelAttribute
	public StudentResumeSchoolActive get(@RequestParam(required=false) String id) {
		StudentResumeSchoolActive entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = studentResumeSchoolActiveService.get(id);
		}
		if (entity == null){
			entity = new StudentResumeSchoolActive();
		}
		return entity;
	}
	
	@RequiresPermissions("resume:active:studentResumeSchoolActive:view")
	@RequestMapping(value = {"list", ""})
	public String list(StudentResumeSchoolActive studentResumeSchoolActive, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<StudentResumeSchoolActive> page = studentResumeSchoolActiveService.findPage(new Page<StudentResumeSchoolActive>(request, response), studentResumeSchoolActive); 
		model.addAttribute("page", page);
		return "modules/resume/active/studentResumeSchoolActiveList";
	}

	@RequiresPermissions("resume:active:studentResumeSchoolActive:view")
	@RequestMapping(value = "form")
	public String form(StudentResumeSchoolActive studentResumeSchoolActive, Model model) {
		model.addAttribute("studentResumeSchoolActive", studentResumeSchoolActive);
		return "modules/resume/active/studentResumeSchoolActiveForm";
	}

	@RequiresPermissions("resume:active:studentResumeSchoolActive:edit")
	@RequestMapping(value = "save")
	public String save(StudentResumeSchoolActive studentResumeSchoolActive, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, studentResumeSchoolActive)){
			return form(studentResumeSchoolActive, model);
		}
		studentResumeSchoolActiveService.save(studentResumeSchoolActive);
		addMessage(redirectAttributes, "保存简历活动成功");
		return "redirect:"+Global.getAdminPath()+"/resume/active/studentResumeSchoolActive/?repage";
	}
	
	@RequiresPermissions("resume:active:studentResumeSchoolActive:edit")
	@RequestMapping(value = "delete")
	public String delete(StudentResumeSchoolActive studentResumeSchoolActive, RedirectAttributes redirectAttributes) {
		studentResumeSchoolActiveService.delete(studentResumeSchoolActive);
		addMessage(redirectAttributes, "删除简历活动成功");
		return "redirect:"+Global.getAdminPath()+"/resume/active/studentResumeSchoolActive/?repage";
	}

}