/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.resume.web.prefer;

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
import com.thinkgem.jeesite.modules.resume.entity.prefer.StudentResumePrefer;
import com.thinkgem.jeesite.modules.resume.service.prefer.StudentResumePreferService;

/**
 * 简历意向Controller
 * @author 李金辉
 * @version 2019-01-28
 */
@Controller
@RequestMapping(value = "${adminPath}/resume/prefer/studentResumePrefer")
public class StudentResumePreferController extends BaseController {

	@Autowired
	private StudentResumePreferService studentResumePreferService;
	
	@ModelAttribute
	public StudentResumePrefer get(@RequestParam(required=false) String id) {
		StudentResumePrefer entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = studentResumePreferService.get(id);
		}
		if (entity == null){
			entity = new StudentResumePrefer();
		}
		return entity;
	}
	
	@RequiresPermissions("resume:prefer:studentResumePrefer:view")
	@RequestMapping(value = {"list", ""})
	public String list(StudentResumePrefer studentResumePrefer, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<StudentResumePrefer> page = studentResumePreferService.findPage(new Page<StudentResumePrefer>(request, response), studentResumePrefer); 
		model.addAttribute("page", page);
		return "modules/resume/prefer/studentResumePreferList";
	}

	@RequiresPermissions("resume:prefer:studentResumePrefer:view")
	@RequestMapping(value = "form")
	public String form(StudentResumePrefer studentResumePrefer, Model model) {
		model.addAttribute("studentResumePrefer", studentResumePrefer);
		return "modules/resume/prefer/studentResumePreferForm";
	}

	@RequiresPermissions("resume:prefer:studentResumePrefer:edit")
	@RequestMapping(value = "save")
	public String save(StudentResumePrefer studentResumePrefer, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, studentResumePrefer)){
			return form(studentResumePrefer, model);
		}
		studentResumePreferService.save(studentResumePrefer);
		addMessage(redirectAttributes, "保存简历意向成功");
		return "redirect:"+Global.getAdminPath()+"/resume/prefer/studentResumePrefer/?repage";
	}
	
	@RequiresPermissions("resume:prefer:studentResumePrefer:edit")
	@RequestMapping(value = "delete")
	public String delete(StudentResumePrefer studentResumePrefer, RedirectAttributes redirectAttributes) {
		studentResumePreferService.delete(studentResumePrefer);
		addMessage(redirectAttributes, "删除简历意向成功");
		return "redirect:"+Global.getAdminPath()+"/resume/prefer/studentResumePrefer/?repage";
	}

}