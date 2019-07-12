/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.resume.web.certificate;

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
import com.thinkgem.jeesite.modules.resume.entity.certificate.StudentResumeSchoolCertificate;
import com.thinkgem.jeesite.modules.resume.service.certificate.StudentResumeSchoolCertificateService;

/**
 * 简历证书Controller
 * @author 李金辉
 * @version 2019-05-06
 */
@Controller
@RequestMapping(value = "${adminPath}/resume/certificate/studentResumeSchoolCertificate")
public class StudentResumeSchoolCertificateController extends BaseController {

	@Autowired
	private StudentResumeSchoolCertificateService studentResumeSchoolCertificateService;
	
	@ModelAttribute
	public StudentResumeSchoolCertificate get(@RequestParam(required=false) String id) {
		StudentResumeSchoolCertificate entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = studentResumeSchoolCertificateService.get(id);
		}
		if (entity == null){
			entity = new StudentResumeSchoolCertificate();
		}
		return entity;
	}
	
	@RequiresPermissions("resume:certificate:studentResumeSchoolCertificate:view")
	@RequestMapping(value = {"list", ""})
	public String list(StudentResumeSchoolCertificate studentResumeSchoolCertificate, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<StudentResumeSchoolCertificate> page = studentResumeSchoolCertificateService.findPage(new Page<StudentResumeSchoolCertificate>(request, response), studentResumeSchoolCertificate); 
		model.addAttribute("page", page);
		return "modules/resume/certificate/studentResumeSchoolCertificateList";
	}

	@RequiresPermissions("resume:certificate:studentResumeSchoolCertificate:view")
	@RequestMapping(value = "form")
	public String form(StudentResumeSchoolCertificate studentResumeSchoolCertificate, Model model) {
		model.addAttribute("studentResumeSchoolCertificate", studentResumeSchoolCertificate);
		return "modules/resume/certificate/studentResumeSchoolCertificateForm";
	}

	@RequiresPermissions("resume:certificate:studentResumeSchoolCertificate:edit")
	@RequestMapping(value = "save")
	public String save(StudentResumeSchoolCertificate studentResumeSchoolCertificate, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, studentResumeSchoolCertificate)){
			return form(studentResumeSchoolCertificate, model);
		}
		studentResumeSchoolCertificateService.save(studentResumeSchoolCertificate);
		addMessage(redirectAttributes, "保存简历证书成功");
		return "redirect:"+Global.getAdminPath()+"/resume/certificate/studentResumeSchoolCertificate/?repage";
	}
	
	@RequiresPermissions("resume:certificate:studentResumeSchoolCertificate:edit")
	@RequestMapping(value = "delete")
	public String delete(StudentResumeSchoolCertificate studentResumeSchoolCertificate, RedirectAttributes redirectAttributes) {
		studentResumeSchoolCertificateService.delete(studentResumeSchoolCertificate);
		addMessage(redirectAttributes, "删除简历证书成功");
		return "redirect:"+Global.getAdminPath()+"/resume/certificate/studentResumeSchoolCertificate/?repage";
	}

}