/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.company.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.common.base.Strings;
import com.thinkgem.jeesite.modules.account.entity.Account;
import com.thinkgem.jeesite.modules.account.entity.accounthr.AccountCompanyhr;
import com.thinkgem.jeesite.modules.account.service.AccountService;
import com.thinkgem.jeesite.modules.account.service.accounthr.AccountCompanyhrService;
import com.thinkgem.jeesite.modules.company.entity.auth.CompanyAuth;
import com.thinkgem.jeesite.modules.company.service.auth.CompanyAuthService;
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
import com.thinkgem.jeesite.modules.company.entity.Company;
import com.thinkgem.jeesite.modules.company.service.CompanyService;

import java.util.ArrayList;
import java.util.List;

/**
 * 企业基本信息Controller
 * @author 李金辉
 * @version 2019-01-29
 */
@Controller
@RequestMapping(value = "${adminPath}/company/company")
public class CompanyController extends BaseController {

	@Autowired
	private CompanyService companyService;
	@Autowired
	private CompanyAuthService companyAuthService;
	@Autowired
	private AccountService accountService;
	@Autowired
	private AccountCompanyhrService accountCompanyhrService;

	@ModelAttribute
	public Company get(@RequestParam(required=false) String id) {
		Company entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = companyService.get(id);
		}
		if (entity == null){
			entity = new Company();
		}
		return entity;
	}
	
	@RequiresPermissions("company:company:view")
	@RequestMapping(value = {"list", ""})
	public String list(Company company, HttpServletRequest request, HttpServletResponse response, Model model) {


		Page<Company> page = companyService.findPage(new Page<Company>(request, response), company);
		for (Company c:page.getList()) {
			c.setAuth(companyAuthService.getByCompanyId(c.getId()));
			c.setAccount(accountService.get(accountCompanyhrService.getByCompanyId(c.getId()).getAccountId()));
		}


		/*Page page1=new Page();
		SystemAuthorizingRealm.Principal principal = UserUtils.getPrincipal();
		Account account=accountService.selectByPhone(principal.getLoginName());
		if(account!=null){
			model.addAttribute("companyUsers", "companyUsers");
			List list=new ArrayList<>();
			list.add(company);
			page.setList(list);
		}
*/


		model.addAttribute("page", page);
		return "modules/company/companyList";
	}

	@RequiresPermissions("company:company:view")
	@RequestMapping(value = "form")
	public String form(Company company, Model model) {
		SystemAuthorizingRealm.Principal principal = UserUtils.getPrincipal();
		Account account=accountService.selectByPhone(principal.getLoginName());
		if(account!=null){
			company=companyService.get(accountCompanyhrService.getByAccountId(account.getId()).getCompanyId());
			model.addAttribute("companyUsers", "companyUsers");

		}
		model.addAttribute("company", company);
		return "modules/company/companyForm";
	}

	@RequestMapping(value = "forbidden")
	public String forbidden(Company company, HttpServletRequest request, HttpServletResponse response, Model model) {
		Account account=accountService.get(accountCompanyhrService.getByCompanyId(company.getId()).getAccountId());
		if(Account.STATUS_NORMAL.equals(account.getStatus())){
			account.setStatus(Account.STATUS_DISABLE);
		}else if(Account.STATUS_DISABLE.equals(account.getStatus())){
			account.setStatus(Account.STATUS_NORMAL);
		}
		account.preUpdate();
		accountService.save(account);
		Page<Company> page = companyService.findPage(new Page<Company>(request, response), company);
		for (Company c:page.getList()) {
			c.setAuth(companyAuthService.getByCompanyId(c.getId()));
			c.setAccount(accountService.get(accountCompanyhrService.getByCompanyId(c.getId()).getAccountId()));
		}
		model.addAttribute("page", page);
		return "modules/company/companyList";
	}

	@RequiresPermissions("company:company:edit")
	@RequestMapping(value = "save")
	public String save(Company company, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, company)){
			return form(company, model);
		}
		boolean flag=false;
		if(Strings.isNullOrEmpty(company.getId())){
			flag=true;
		}
		companyService.save(company);
		//添加企业认真信息
		if(flag==true){
			CompanyAuth companyAuth=new CompanyAuth();
			companyAuth.setCompanyId(company.getId());
			companyAuth.setStatus(CompanyAuth.AUTH_STATUS_LOCK);
			companyAuthService.save(companyAuth);
		}
		addMessage(redirectAttributes, "保存企业基本信息成功");

		SystemAuthorizingRealm.Principal principal = UserUtils.getPrincipal();
		Account account=accountService.selectByPhone(principal.getLoginName());
		if(account!=null){

			model.addAttribute("companyUsers", "companyUsers");
			return "redirect:"+Global.getAdminPath()+"/company/company/form";

		}
		return "redirect:"+Global.getAdminPath()+"/company/company/?repage";
	}
	
	@RequiresPermissions("company:company:edit")
	@RequestMapping(value = "delete")
	public String delete(Company company, RedirectAttributes redirectAttributes) {
		companyService.delete(company);
		addMessage(redirectAttributes, "删除企业基本信息成功");
		return "redirect:"+Global.getAdminPath()+"/company/company/?repage";
	}

}