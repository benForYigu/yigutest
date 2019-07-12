/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.accountrecommend.web;

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
import com.thinkgem.jeesite.modules.accountrecommend.entity.AccountRecommend;
import com.thinkgem.jeesite.modules.accountrecommend.service.AccountRecommendService;

/**
 * 推荐记录Controller
 * @author 李金辉
 * @version 2019-05-10
 */
@Controller
@RequestMapping(value = "${adminPath}/accountrecommend/accountRecommend")
public class AccountRecommendController extends BaseController {

	@Autowired
	private AccountRecommendService accountRecommendService;
	
	@ModelAttribute
	public AccountRecommend get(@RequestParam(required=false) String id) {
		AccountRecommend entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = accountRecommendService.get(id);
		}
		if (entity == null){
			entity = new AccountRecommend();
		}
		return entity;
	}
	
	@RequiresPermissions("accountrecommend:accountRecommend:view")
	@RequestMapping(value = {"list", ""})
	public String list(AccountRecommend accountRecommend, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AccountRecommend> page = accountRecommendService.findPage(new Page<AccountRecommend>(request, response), accountRecommend); 
		model.addAttribute("page", page);
		return "modules/accountrecommend/accountRecommendList";
	}

	@RequiresPermissions("accountrecommend:accountRecommend:view")
	@RequestMapping(value = "form")
	public String form(AccountRecommend accountRecommend, Model model) {
		model.addAttribute("accountRecommend", accountRecommend);
		return "modules/accountrecommend/accountRecommendForm";
	}

	@RequiresPermissions("accountrecommend:accountRecommend:edit")
	@RequestMapping(value = "save")
	public String save(AccountRecommend accountRecommend, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, accountRecommend)){
			return form(accountRecommend, model);
		}
		accountRecommendService.save(accountRecommend);
		addMessage(redirectAttributes, "保存推荐记录成功");
		return "redirect:"+Global.getAdminPath()+"/accountrecommend/accountRecommend/?repage";
	}
	
	@RequiresPermissions("accountrecommend:accountRecommend:edit")
	@RequestMapping(value = "delete")
	public String delete(AccountRecommend accountRecommend, RedirectAttributes redirectAttributes) {
		accountRecommendService.delete(accountRecommend);
		addMessage(redirectAttributes, "删除推荐记录成功");
		return "redirect:"+Global.getAdminPath()+"/accountrecommend/accountRecommend/?repage";
	}

}