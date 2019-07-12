/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.account.web.accountschool;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thinkgem.jeesite.modules.school.entity.School;
import com.thinkgem.jeesite.modules.school.service.SchoolService;
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
import com.thinkgem.jeesite.modules.account.entity.accountschool.AccountTeacherinfo;
import com.thinkgem.jeesite.modules.account.service.accountschool.AccountTeacherinfoService;

/**
 * 学校人员Controller
 * @author 李金辉
 * @version 2019-01-28
 */
@Controller
@RequestMapping(value = "${adminPath}/account/accountschool/accountTeacherinfo")
public class AccountTeacherinfoController extends BaseController {

	@Autowired
	private AccountTeacherinfoService accountTeacherinfoService;
	
	@Autowired
	private SchoolService schoolService;

	@ModelAttribute
	public AccountTeacherinfo get(@RequestParam(required=false) String id) {
		AccountTeacherinfo entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = accountTeacherinfoService.get(id);
		}
		if (entity == null){
			entity = new AccountTeacherinfo();
		}
		return entity;
	}
	
	@RequiresPermissions("account:accountschool:accountTeacherinfo:view")
	@RequestMapping(value = {"list", ""})
	public String list(AccountTeacherinfo accountTeacherinfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AccountTeacherinfo> page = accountTeacherinfoService.findPage(new Page<AccountTeacherinfo>(request, response), accountTeacherinfo);
		model.addAttribute("schools", schoolService.findList(new School()));
		model.addAttribute("page", page);
		return "modules/account/accountschool/accountTeacherinfoList";
	}

	@RequiresPermissions("account:accountschool:accountTeacherinfo:view")
	@RequestMapping(value = "form")
	public String form(AccountTeacherinfo accountTeacherinfo, Model model) {
		model.addAttribute("accountTeacherinfo", accountTeacherinfo);
		return "modules/account/accountschool/accountTeacherinfoForm";
	}

	@RequiresPermissions("account:accountschool:accountTeacherinfo:edit")
	@RequestMapping(value = "save")
	public String save(AccountTeacherinfo accountTeacherinfo, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, accountTeacherinfo)){
			return form(accountTeacherinfo, model);
		}
		accountTeacherinfoService.save(accountTeacherinfo);
		addMessage(redirectAttributes, "保存学校人员成功");
		return "redirect:"+Global.getAdminPath()+"/account/accountschool/accountTeacherinfo/?repage";
	}
	
	@RequiresPermissions("account:accountschool:accountTeacherinfo:edit")
	@RequestMapping(value = "delete")
	public String delete(AccountTeacherinfo accountTeacherinfo, RedirectAttributes redirectAttributes) {
		accountTeacherinfoService.rdelete(accountTeacherinfo);
		addMessage(redirectAttributes, "删除学校人员成功");
		return "redirect:"+Global.getAdminPath()+"/account/accountschool/accountTeacherinfo/?repage";
	}

}