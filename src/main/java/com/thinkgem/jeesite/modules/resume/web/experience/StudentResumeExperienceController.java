/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.resume.web.experience;

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
import com.thinkgem.jeesite.modules.resume.entity.experience.StudentResumeExperience;
import com.thinkgem.jeesite.modules.resume.service.experience.StudentResumeExperienceService;

/**
 * 简历工作经历Controller
 * @author 李金辉
 * @version 2019-01-28
 */
@Controller
@RequestMapping(value = "${adminPath}/resume/experience/studentResumeExperience")
public class StudentResumeExperienceController extends BaseController {

	@Autowired
	private StudentResumeExperienceService studentResumeExperienceService;
	
	@ModelAttribute
	public StudentResumeExperience get(@RequestParam(required=false) String id) {
		StudentResumeExperience entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = studentResumeExperienceService.get(id);
		}
		if (entity == null){
			entity = new StudentResumeExperience();
		}
		return entity;
	}
	
	@RequiresPermissions("resume:experience:studentResumeExperience:view")
	@RequestMapping(value = {"list", ""})
	public String list(StudentResumeExperience studentResumeExperience, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<StudentResumeExperience> page = studentResumeExperienceService.findPage(new Page<StudentResumeExperience>(request, response), studentResumeExperience); 
		model.addAttribute("page", page);
		return "modules/resume/experience/studentResumeExperienceList";
	}

	@RequiresPermissions("resume:experience:studentResumeExperience:view")
	@RequestMapping(value = "form")
	public String form(StudentResumeExperience studentResumeExperience, Model model) {
		model.addAttribute("studentResumeExperience", studentResumeExperience);
		return "modules/resume/experience/studentResumeExperienceForm";
	}

	@RequiresPermissions("resume:experience:studentResumeExperience:edit")
	@RequestMapping(value = "save")
	public String save(StudentResumeExperience studentResumeExperience, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, studentResumeExperience)){
			return form(studentResumeExperience, model);
		}
		studentResumeExperienceService.save(studentResumeExperience);
		addMessage(redirectAttributes, "保存简历工作经历成功");
		return "redirect:"+Global.getAdminPath()+"/resume/experience/studentResumeExperience/?repage";
	}
	
	@RequiresPermissions("resume:experience:studentResumeExperience:edit")
	@RequestMapping(value = "delete")
	public String delete(StudentResumeExperience studentResumeExperience, RedirectAttributes redirectAttributes) {
		studentResumeExperienceService.delete(studentResumeExperience);
		addMessage(redirectAttributes, "删除简历工作经历成功");
		return "redirect:"+Global.getAdminPath()+"/resume/experience/studentResumeExperience/?repage";
	}

}