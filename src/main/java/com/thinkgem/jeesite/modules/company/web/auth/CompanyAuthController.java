/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.company.web.auth;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.common.base.Strings;
import com.thinkgem.jeesite.modules.account.entity.Account;
import com.thinkgem.jeesite.modules.account.service.AccountService;
import com.thinkgem.jeesite.modules.account.service.accounthr.AccountCompanyhrService;
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
import com.thinkgem.jeesite.modules.company.entity.auth.CompanyAuth;
import com.thinkgem.jeesite.modules.company.service.auth.CompanyAuthService;

/**
 * 企业认证信息Controller
 * @author 李金辉
 * @version 2019-01-29
 */
@Controller
@RequestMapping(value = "${adminPath}/company/auth/companyAuth")
public class CompanyAuthController extends BaseController {

	@Autowired
	private CompanyAuthService companyAuthService;
	@Autowired
	private CompanyService companyService;
	@Autowired
	private AccountService accountService;
	@Autowired
	private AccountCompanyhrService companyhrService;

	@ModelAttribute
	public CompanyAuth get(@RequestParam(required=false) String id) {
		CompanyAuth entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = companyAuthService.get(id);
		}
		if (entity == null){
			entity = new CompanyAuth();
		}
		return entity;
	}
	
	/*@RequiresPermissions("company:auth:companyAuth:view")*/
	@RequestMapping(value = {"list", ""})
	public String list(CompanyAuth companyAuth, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CompanyAuth> page = companyAuthService.findPage(new Page<CompanyAuth>(request, response), companyAuth); 
		model.addAttribute("page", page);
		return "modules/company/auth/companyAuthList";
	}

	//@RequiresPermissions("company:auth:companyAuth:view")
	@RequestMapping(value = "form")
	public String form(CompanyAuth companyAuth, Model model) {
		SystemAuthorizingRealm.Principal principal = UserUtils.getPrincipal();
		Account account=accountService.selectByPhone(principal.getLoginName());
		if(account!=null){
			model.addAttribute("companyUsers", "companyUsers");
			model.addAttribute("companyHr", companyhrService.getByAccountId(account.getId()));

		}
		model.addAttribute("companyAuth",companyAuth);
		return "modules/company/auth/companyAuthForm";

	}

	//@RequiresPermissions("company:auth:companyAuth:edit")
	@RequestMapping(value = "save")
	public String save(CompanyAuth companyAuth, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, companyAuth)){
			return form(companyAuth, model);
		}
		companyAuthService.save(companyAuth);
		//addMessage(redirectAttributes, "保存企业认证信息成功");
		//return "redirect:"+Global.getAdminPath()+"/company/auth/companyAuth/?repage";
		model.addAttribute("company", companyService.get(companyAuth.getId()));
		model.addAttribute("companyAuth", companyAuth);
		//return "modules/company/auth/companyAuthForm";
		addMessage(redirectAttributes, "保存企业认证信息成功");

		SystemAuthorizingRealm.Principal principal = UserUtils.getPrincipal();
		Account account=accountService.selectByPhone(principal.getLoginName());
		if(account!=null){
			model.addAttribute("companyUsers", "companyUsers");
			return "redirect:"+Global.getAdminPath()+"/company/company/form";
		}
		return "redirect:"+Global.getAdminPath()+"/company/company/?repage";
	}
	
	//@RequiresPermissions("company:auth:companyAuth:edit")
	@RequestMapping(value = "delete")
	public String delete(CompanyAuth companyAuth, RedirectAttributes redirectAttributes) {
		companyAuthService.delete(companyAuth);
		addMessage(redirectAttributes, "删除企业认证信息成功");
		return "redirect:"+Global.getAdminPath()+"/company/auth/companyAuth/?repage";
	}

}