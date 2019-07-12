/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.coin.web;

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
import com.thinkgem.jeesite.modules.coin.entity.AccountCoin;
import com.thinkgem.jeesite.modules.coin.service.AccountCoinService;

/**
 * 岗币记录Controller
 * @author 李金辉
 * @version 2019-05-10
 */
@Controller
@RequestMapping(value = "${adminPath}/coin/accountCoin")
public class AccountCoinController extends BaseController {

	@Autowired
	private AccountCoinService accountCoinService;
	
	@ModelAttribute
	public AccountCoin get(@RequestParam(required=false) String id) {
		AccountCoin entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = accountCoinService.get(id);
		}
		if (entity == null){
			entity = new AccountCoin();
		}
		return entity;
	}
	
	@RequiresPermissions("coin:accountCoin:view")
	@RequestMapping(value = {"list", ""})
	public String list(AccountCoin accountCoin, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AccountCoin> page = accountCoinService.findPage(new Page<AccountCoin>(request, response), accountCoin); 
		model.addAttribute("page", page);
		return "modules/coin/accountCoinList";
	}

	@RequiresPermissions("coin:accountCoin:view")
	@RequestMapping(value = "form")
	public String form(AccountCoin accountCoin, Model model) {
		model.addAttribute("accountCoin", accountCoin);
		return "modules/coin/accountCoinForm";
	}

	@RequiresPermissions("coin:accountCoin:edit")
	@RequestMapping(value = "save")
	public String save(AccountCoin accountCoin, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, accountCoin)){
			return form(accountCoin, model);
		}
		accountCoinService.save(accountCoin);
		addMessage(redirectAttributes, "保存岗币记录成功");
		return "redirect:"+Global.getAdminPath()+"/coin/accountCoin/?repage";
	}
	
	@RequiresPermissions("coin:accountCoin:edit")
	@RequestMapping(value = "delete")
	public String delete(AccountCoin accountCoin, RedirectAttributes redirectAttributes) {
		accountCoinService.delete(accountCoin);
		addMessage(redirectAttributes, "删除岗币记录成功");
		return "redirect:"+Global.getAdminPath()+"/coin/accountCoin/?repage";
	}

}