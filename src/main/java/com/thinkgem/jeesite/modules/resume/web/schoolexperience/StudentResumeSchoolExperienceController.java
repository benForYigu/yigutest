/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.resume.web.schoolexperience;

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
import com.thinkgem.jeesite.modules.resume.entity.schoolexperience.StudentResumeSchoolExperience;
import com.thinkgem.jeesite.modules.resume.service.schoolexperience.StudentResumeSchoolExperienceService;

/**
 * 简历校园经历Controller
 * @author 李金辉
 * @version 2019-05-05
 */
@Controller
@RequestMapping(value = "${adminPath}/resume/schoolexperience/studentResumeSchoolExperience")
public class StudentResumeSchoolExperienceController extends BaseController {

	@Autowired
	private StudentResumeSchoolExperienceService studentResumeSchoolExperienceService;
	
	@ModelAttribute
	public StudentResumeSchoolExperience get(@RequestParam(required=false) String id) {
		StudentResumeSchoolExperience entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = studentResumeSchoolExperienceService.get(id);
		}
		if (entity == null){
			entity = new StudentResumeSchoolExperience();
		}
		return entity;
	}
	
	@RequiresPermissions("resume:schoolexperience:studentResumeSchoolExperience:view")
	@RequestMapping(value = {"list", ""})
	public String list(StudentResumeSchoolExperience studentResumeSchoolExperience, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<StudentResumeSchoolExperience> page = studentResumeSchoolExperienceService.findPage(new Page<StudentResumeSchoolExperience>(request, response), studentResumeSchoolExperience); 
		model.addAttribute("page", page);
		return "modules/resume/schoolexperience/studentResumeSchoolExperienceList";
	}

	@RequiresPermissions("resume:schoolexperience:studentResumeSchoolExperience:view")
	@RequestMapping(value = "form")
	public String form(StudentResumeSchoolExperience studentResumeSchoolExperience, Model model) {
		model.addAttribute("studentResumeSchoolExperience", studentResumeSchoolExperience);
		return "modules/resume/schoolexperience/studentResumeSchoolExperienceForm";
	}

	@RequiresPermissions("resume:schoolexperience:studentResumeSchoolExperience:edit")
	@RequestMapping(value = "save")
	public String save(StudentResumeSchoolExperience studentResumeSchoolExperience, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, studentResumeSchoolExperience)){
			return form(studentResumeSchoolExperience, model);
		}
		studentResumeSchoolExperienceService.save(studentResumeSchoolExperience);
		addMessage(redirectAttributes, "保存简历校园经历成功");
		return "redirect:"+Global.getAdminPath()+"/resume/schoolexperience/studentResumeSchoolExperience/?repage";
	}
	
	@RequiresPermissions("resume:schoolexperience:studentResumeSchoolExperience:edit")
	@RequestMapping(value = "delete")
	public String delete(StudentResumeSchoolExperience studentResumeSchoolExperience, RedirectAttributes redirectAttributes) {
		studentResumeSchoolExperienceService.delete(studentResumeSchoolExperience);
		addMessage(redirectAttributes, "删除简历校园经历成功");
		return "redirect:"+Global.getAdminPath()+"/resume/schoolexperience/studentResumeSchoolExperience/?repage";
	}

}