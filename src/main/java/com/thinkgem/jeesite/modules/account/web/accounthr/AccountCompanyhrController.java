/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.account.web.accounthr;

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
import com.thinkgem.jeesite.modules.account.entity.accounthr.AccountCompanyhr;
import com.thinkgem.jeesite.modules.account.service.accounthr.AccountCompanyhrService;

/**
 * hrController
 * @author 李金辉
 * @version 2019-01-25
 */
@Controller
@RequestMapping(value = "${adminPath}/account/accounthr/accountCompanyhr")
public class AccountCompanyhrController extends BaseController {

	@Autowired
	private AccountCompanyhrService accountCompanyhrService;
	
	@ModelAttribute
	public AccountCompanyhr get(@RequestParam(required=false) String id) {
		AccountCompanyhr entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = accountCompanyhrService.get(id);
		}
		if (entity == null){
			entity = new AccountCompanyhr();
		}
		return entity;
	}
	
	@RequiresPermissions("account:accounthr:accountCompanyhr:view")
	@RequestMapping(value = {"list", ""})
	public String list(AccountCompanyhr accountCompanyhr, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AccountCompanyhr> page = accountCompanyhrService.findPage(new Page<AccountCompanyhr>(request, response), accountCompanyhr); 
		model.addAttribute("page", page);
		return "modules/account/accounthr/accountCompanyhrList";
	}

	@RequiresPermissions("account:accounthr:accountCompanyhr:view")
	@RequestMapping(value = "form")
	public String form(AccountCompanyhr accountCompanyhr, Model model) {
		model.addAttribute("accountCompanyhr", accountCompanyhr);
		return "modules/account/accounthr/accountCompanyhrForm";
	}

	@RequiresPermissions("account:accounthr:accountCompanyhr:edit")
	@RequestMapping(value = "save")
	public String save(AccountCompanyhr accountCompanyhr, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, accountCompanyhr)){
			return form(accountCompanyhr, model);
		}
		accountCompanyhrService.save(accountCompanyhr);
		addMessage(redirectAttributes, "保存hr成功");
		return "redirect:"+Global.getAdminPath()+"/account/accounthr/accountCompanyhr/?repage";
	}
	
	@RequiresPermissions("account:accounthr:accountCompanyhr:edit")
	@RequestMapping(value = "delete")
	public String delete(AccountCompanyhr accountCompanyhr, RedirectAttributes redirectAttributes) {
		accountCompanyhrService.rdelete(accountCompanyhr);
		// accountCompanyhrService.delete(accountCompanyhr);
		addMessage(redirectAttributes, "删除hr成功");
		return "redirect:"+Global.getAdminPath()+"/account/accounthr/accountCompanyhr/?repage";
	}

}