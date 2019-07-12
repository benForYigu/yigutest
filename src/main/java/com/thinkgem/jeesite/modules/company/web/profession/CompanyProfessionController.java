/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.company.web.profession;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.common.base.Strings;
import com.thinkgem.jeesite.modules.account.entity.Account;
import com.thinkgem.jeesite.modules.account.entity.accountstudent.AccountStudentinfo;
import com.thinkgem.jeesite.modules.account.service.AccountService;
import com.thinkgem.jeesite.modules.account.service.accounthr.AccountCompanyhrService;
import com.thinkgem.jeesite.modules.company.entity.Company;
import com.thinkgem.jeesite.modules.company.service.CompanyService;
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
import com.thinkgem.jeesite.modules.company.entity.profession.CompanyProfession;
import com.thinkgem.jeesite.modules.company.service.profession.CompanyProfessionService;

/**
 * 企业职位Controller
 * @author 李金辉
 * @version 2019-01-30
 */
@Controller
@RequestMapping(value = "${adminPath}/company/profession/companyProfession")
public class CompanyProfessionController extends BaseController {

	@Autowired
	private CompanyProfessionService companyProfessionService;
	@Autowired
	private AccountService accountService;
	@Autowired
	private AccountCompanyhrService companyhrService;
	@Autowired
	private CompanyService companyService;

	@ModelAttribute
	public CompanyProfession get(@RequestParam(required=false) String id) {
		CompanyProfession entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = companyProfessionService.get(id);
		}
		if (entity == null){
			entity = new CompanyProfession();
		}
		return entity;
	}
	
	@RequiresPermissions("company:profession:companyProfession:view")
	@RequestMapping(value = {"list", ""})
	public String list(CompanyProfession companyProfession, HttpServletRequest request, HttpServletResponse response, Model model) {
		SystemAuthorizingRealm.Principal principal = UserUtils.getPrincipal();
		Account account=accountService.selectByPhone(principal.getLoginName());
		if(account!=null){
			companyProfession.setCompanyId(companyhrService.getByAccountId(account.getId()).getCompanyId());
			if(Company.STATUS_YES.equals(companyService.get(companyProfession.getCompanyId()).getStatus())){
				model.addAttribute("companyStatus", "true");
			}
			model.addAttribute("companyUsers", "companyUsers");
		}
		Page<CompanyProfession> page = companyProfessionService.findPage(new Page<CompanyProfession>(request, response), companyProfession);
		model.addAttribute("page", page);
		return "modules/company/profession/companyProfessionList";
	}

	@RequiresPermissions("company:profession:companyProfession:view")
	@RequestMapping(value = "form")
	public String form(CompanyProfession companyProfession, Model model) {
		SystemAuthorizingRealm.Principal principal = UserUtils.getPrincipal();
		Account account=accountService.selectByPhone(principal.getLoginName());
		if(account!=null){
			companyProfession.setCompanyId(companyhrService.getByAccountId(account.getId()).getCompanyId());
			model.addAttribute("companyUsers", "companyUsers");
		}
		model.addAttribute("companyProfession", companyProfession);
		return "modules/company/profession/companyProfessionForm";
	}

	@RequiresPermissions("company:profession:companyProfession:edit")
	@RequestMapping(value = "save")
	public String save(CompanyProfession companyProfession, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, companyProfession)){
			return form(companyProfession, model);
		}
		if(Strings.isNullOrEmpty(companyProfession.getId())){
			Company company=companyService.get(companyProfession.getCompanyId());
			companyProfession.setCompanyName(company.getName());
			companyProfession.setSort(100);
			companyProfession.setHot("0");
			if(Strings.isNullOrEmpty(companyProfession.getSource())){
				companyProfession.setSource(CompanyProfession.SOURCE_SCHOOG);
			}
		}
		companyProfessionService.save(companyProfession);
		addMessage(redirectAttributes, "保存企业职位成功");
		return "redirect:"+Global.getAdminPath()+"/company/profession/companyProfession/?repage";
	}
	
	@RequiresPermissions("company:profession:companyProfession:edit")
	@RequestMapping(value = "delete")
	public String delete(CompanyProfession companyProfession, RedirectAttributes redirectAttributes) {
		companyProfessionService.delete(companyProfession);
		addMessage(redirectAttributes, "删除企业职位成功");
		return "redirect:"+Global.getAdminPath()+"/company/profession/companyProfession/?repage";
	}

}