/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.resume.web.education;

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
import com.thinkgem.jeesite.modules.resume.entity.education.StudentResumeEducation;
import com.thinkgem.jeesite.modules.resume.service.education.StudentResumeEducationService;

/**
 * 简历教育经历Controller
 * @author 李金辉
 * @version 2019-01-28
 */
@Controller
@RequestMapping(value = "${adminPath}/resume/education/studentResumeEducation")
public class StudentResumeEducationController extends BaseController {

	@Autowired
	private StudentResumeEducationService studentResumeEducationService;
	
	@ModelAttribute
	public StudentResumeEducation get(@RequestParam(required=false) String id) {
		StudentResumeEducation entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = studentResumeEducationService.get(id);
		}
		if (entity == null){
			entity = new StudentResumeEducation();
		}
		return entity;
	}
	
	@RequiresPermissions("resume:education:studentResumeEducation:view")
	@RequestMapping(value = {"list", ""})
	public String list(StudentResumeEducation studentResumeEducation, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<StudentResumeEducation> page = studentResumeEducationService.findPage(new Page<StudentResumeEducation>(request, response), studentResumeEducation); 
		model.addAttribute("page", page);
		return "modules/resume/education/studentResumeEducationList";
	}

	@RequiresPermissions("resume:education:studentResumeEducation:view")
	@RequestMapping(value = "form")
	public String form(StudentResumeEducation studentResumeEducation, Model model) {
		model.addAttribute("studentResumeEducation", studentResumeEducation);
		return "modules/resume/education/studentResumeEducationForm";
	}

	@RequiresPermissions("resume:education:studentResumeEducation:edit")
	@RequestMapping(value = "save")
	public String save(StudentResumeEducation studentResumeEducation, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, studentResumeEducation)){
			return form(studentResumeEducation, model);
		}
		studentResumeEducationService.save(studentResumeEducation);
		addMessage(redirectAttributes, "保存简历教育经历成功");
		return "redirect:"+Global.getAdminPath()+"/resume/education/studentResumeEducation/?repage";
	}
	
	@RequiresPermissions("resume:education:studentResumeEducation:edit")
	@RequestMapping(value = "delete")
	public String delete(StudentResumeEducation studentResumeEducation, RedirectAttributes redirectAttributes) {
		studentResumeEducationService.delete(studentResumeEducation);
		addMessage(redirectAttributes, "删除简历教育经历成功");
		return "redirect:"+Global.getAdminPath()+"/resume/education/studentResumeEducation/?repage";
	}

}