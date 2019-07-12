/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.interaction.web.interview;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thinkgem.jeesite.modules.account.entity.Account;
import com.thinkgem.jeesite.modules.account.entity.accounthr.AccountCompanyhr;
import com.thinkgem.jeesite.modules.account.entity.accountstudent.AccountStudentinfo;
import com.thinkgem.jeesite.modules.account.service.AccountService;
import com.thinkgem.jeesite.modules.account.service.accounthr.AccountCompanyhrService;
import com.thinkgem.jeesite.modules.account.service.accountstudent.AccountStudentinfoService;
import com.thinkgem.jeesite.modules.company.entity.Company;
import com.thinkgem.jeesite.modules.resume.entity.active.StudentResumeSchoolActive;
import com.thinkgem.jeesite.modules.resume.entity.certificate.StudentResumeSchoolCertificate;
import com.thinkgem.jeesite.modules.resume.entity.education.StudentResumeEducation;
import com.thinkgem.jeesite.modules.resume.entity.experience.StudentResumeExperience;
import com.thinkgem.jeesite.modules.resume.entity.other.StudentResumeOther;
import com.thinkgem.jeesite.modules.resume.entity.prefer.StudentResumePrefer;
import com.thinkgem.jeesite.modules.resume.entity.schoolexperience.StudentResumeSchoolExperience;
import com.thinkgem.jeesite.modules.resume.service.active.StudentResumeSchoolActiveService;
import com.thinkgem.jeesite.modules.resume.service.certificate.StudentResumeSchoolCertificateService;
import com.thinkgem.jeesite.modules.resume.service.education.StudentResumeEducationService;
import com.thinkgem.jeesite.modules.resume.service.experience.StudentResumeExperienceService;
import com.thinkgem.jeesite.modules.resume.service.other.StudentResumeOtherService;
import com.thinkgem.jeesite.modules.resume.service.prefer.StudentResumePreferService;
import com.thinkgem.jeesite.modules.resume.service.schoolexperience.StudentResumeSchoolExperienceService;
import com.thinkgem.jeesite.modules.sys.security.SystemAuthorizingRealm;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
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
import com.thinkgem.jeesite.modules.interaction.entity.interview.InteractionInterview;
import com.thinkgem.jeesite.modules.interaction.service.interview.InteractionInterviewService;

/**
 * 面试Controller
 * @author 李金辉
 * @version 2019-01-24
 */
@Controller
@RequestMapping(value = "${adminPath}/interaction/interview/interactionInterview")
public class InteractionInterviewController extends BaseController {

	@Autowired
	private InteractionInterviewService interactionInterviewService;
	@Autowired
	private AccountService accountService;
	@Autowired
	private AccountStudentinfoService studentinfoService;
	@Autowired
	private AccountCompanyhrService companyhrService;
	@Autowired
	private StudentResumeEducationService educationService;
	@Autowired
	private StudentResumeExperienceService experienceService;
	@Autowired
	private StudentResumeOtherService otherService;
	@Autowired
	private StudentResumePreferService preferService;
	@Autowired
	private StudentResumeSchoolExperienceService schoolExperienceService;
	@Autowired
	private StudentResumeSchoolActiveService activeService;
	@Autowired
	private StudentResumeSchoolCertificateService certificateService;
	@ModelAttribute
	public InteractionInterview get(@RequestParam(required=false) String id) {
		InteractionInterview entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = interactionInterviewService.get(id);
		}
		if (entity == null){
			entity = new InteractionInterview();
		}
		return entity;
	}
	
	@RequiresPermissions("interaction:interview:interactionInterview:view")
	@RequestMapping(value = {"list", ""})
	public String list(InteractionInterview interactionInterview, HttpServletRequest request, HttpServletResponse response, Model model) {
		SystemAuthorizingRealm.Principal principal = UserUtils.getPrincipal();
		Account account=accountService.selectByPhone(principal.getLoginName());
		if(account!=null){
			AccountCompanyhr companyhr=companyhrService.getByAccountId(account.getId());
			interactionInterview.setCompanyId(companyhr.getCompanyId());
			model.addAttribute("companyUsers", "companyUsers");
			if(Company.STATUS_YES.equals(companyhr.getStatus())){
				model.addAttribute("companyStatus", "true");
			}
		}
		Page<InteractionInterview> page = interactionInterviewService.findPage(new Page<InteractionInterview>(request, response), interactionInterview);

		model.addAttribute("page", page);
		return "modules/interaction/interview/interactionInterviewList";
	}

	@RequiresPermissions("interaction:interview:interactionInterview:view")
	@RequestMapping(value = "form")
	public String form(InteractionInterview interactionInterview, Model model) {
		model.addAttribute("interactionInterview", interactionInterview);
		return "modules/interaction/interview/interactionInterviewForm";
	}

	@RequestMapping(value = "resume")
	public String resume(InteractionInterview interactionInterview, Model model) {
		model.addAttribute("accountStudentinfo", studentinfoService.getByAccountId(interactionInterview.getStudentId()));

		StudentResumeEducation education=new StudentResumeEducation();
		education.setStudentId(interactionInterview.getStudentId());
		model.addAttribute("educations", educationService.findList(education));
		StudentResumeExperience experience=new StudentResumeExperience();
		experience.setStudentId(interactionInterview.getStudentId());
		model.addAttribute("experiences", experienceService.findList(experience));
		StudentResumeOther other=new StudentResumeOther();
		other.setStudentId(interactionInterview.getStudentId());
		model.addAttribute("others", otherService.findList(other));
		StudentResumePrefer prefer=new StudentResumePrefer();
		prefer.setStudentId(interactionInterview.getStudentId());
		model.addAttribute("prefers", preferService.findList(prefer));

		StudentResumeSchoolExperience schoolExperience=new StudentResumeSchoolExperience();
		schoolExperience.setStudentId(interactionInterview.getStudentId());
		model.addAttribute("schoolExperiences", schoolExperienceService.findList(schoolExperience));

		StudentResumeSchoolActive active=new StudentResumeSchoolActive();
		active.setStudentId(interactionInterview.getStudentId());
		model.addAttribute("actives", activeService.findList(active));

		StudentResumeSchoolCertificate certificate =new StudentResumeSchoolCertificate();
		certificate.setStudentId(interactionInterview.getStudentId());
		model.addAttribute("certificates", certificateService.findList(certificate));
		return "modules/account/StudentResume";
	}

	@RequiresPermissions("interaction:interview:interactionInterview:edit")
	@RequestMapping(value = "save")
	public String save(InteractionInterview interactionInterview, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, interactionInterview)){
			return form(interactionInterview, model);
		}
		interactionInterviewService.save(interactionInterview);
		addMessage(redirectAttributes, "保存面试成功");
		return "redirect:"+Global.getAdminPath()+"/interaction/interview/interactionInterview/?repage";
	}
	
	@RequiresPermissions("interaction:interview:interactionInterview:edit")
	@RequestMapping(value = "delete")
	public String delete(InteractionInterview interactionInterview, RedirectAttributes redirectAttributes) {
		interactionInterviewService.delete(interactionInterview);
		addMessage(redirectAttributes, "删除面试成功");
		return "redirect:"+Global.getAdminPath()+"/interaction/interview/interactionInterview/?repage";
	}

}