/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.account.web.accountstudent;

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
import com.thinkgem.jeesite.modules.account.entity.accountstudent.AccountStudentinfo;
import com.thinkgem.jeesite.modules.account.service.accountstudent.AccountStudentinfoService;

/**
 * 用户_学生Controller
 * @author 李金辉
 * @version 2019-01-23
 */
@Controller
@RequestMapping(value = "${adminPath}/account/accountstudent/accountStudentinfo")
public class AccountStudentinfoController extends BaseController {

	@Autowired
	private AccountStudentinfoService accountStudentinfoService;
	@Autowired
	private SchoolService schoolService;

	@ModelAttribute
	public AccountStudentinfo get(@RequestParam(required=false) String id) {
		AccountStudentinfo entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = accountStudentinfoService.get(id);
		}
		if (entity == null){
			entity = new AccountStudentinfo();
		}
		return entity;
	}
	
	@RequiresPermissions("account:accountstudent:accountStudentinfo:view")
	@RequestMapping(value = {"list", ""})
	public String list(AccountStudentinfo accountStudentinfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AccountStudentinfo> page = accountStudentinfoService.findPage(new Page<AccountStudentinfo>(request, response), accountStudentinfo); 
		model.addAttribute("schools", schoolService.findList(new School()));
		model.addAttribute("page", page);
		return "modules/account/accountstudent/accountStudentinfoList";
	}

	@RequiresPermissions("account:accountstudent:accountStudentinfo:view")
	@RequestMapping(value = "form")
	public String form(AccountStudentinfo accountStudentinfo, Model model) {
		model.addAttribute("accountStudentinfo", accountStudentinfo);
		return "modules/account/accountstudent/accountStudentinfoForm";
	}

	@RequiresPermissions("account:accountstudent:accountStudentinfo:edit")
	@RequestMapping(value = "save")
	public String save(AccountStudentinfo accountStudentinfo, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, accountStudentinfo)){
			return form(accountStudentinfo, model);
		}
		accountStudentinfoService.save(accountStudentinfo);
		addMessage(redirectAttributes, "保存用户_学生成功");
		return "redirect:"+Global.getAdminPath()+"/account/accountstudent/accountStudentinfo/?repage";
	}
	
	@RequiresPermissions("account:accountstudent:accountStudentinfo:edit")
	@RequestMapping(value = "delete")
	public String delete(AccountStudentinfo accountStudentinfo, RedirectAttributes redirectAttributes) {
		accountStudentinfoService.rdelete(accountStudentinfo);
		//accountStudentinfoService.delete(accountStudentinfo);
		addMessage(redirectAttributes, "删除用户_学生成功");
		return "redirect:"+Global.getAdminPath()+"/account/accountstudent/accountStudentinfo/?repage";
	}

}