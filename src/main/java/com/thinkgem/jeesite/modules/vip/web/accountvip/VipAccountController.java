/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.vip.web.accountvip;

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
import com.thinkgem.jeesite.modules.vip.entity.accountvip.VipAccount;
import com.thinkgem.jeesite.modules.vip.service.accountvip.VipAccountService;

/**
 * 用户权益Controller
 * @author 李金辉
 * @version 2019-03-23
 */
@Controller
@RequestMapping(value = "${adminPath}/vip/accountvip/vipAccount")
public class VipAccountController extends BaseController {

	@Autowired
	private VipAccountService vipAccountService;
	
	@ModelAttribute
	public VipAccount get(@RequestParam(required=false) String id) {
		VipAccount entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = vipAccountService.get(id);
		}
		if (entity == null){
			entity = new VipAccount();
		}
		return entity;
	}
	
	@RequiresPermissions("vip:accountvip:vipAccount:view")
	@RequestMapping(value = {"list", ""})
	public String list(VipAccount vipAccount, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<VipAccount> page = vipAccountService.findPage(new Page<VipAccount>(request, response), vipAccount); 
		model.addAttribute("page", page);
		return "modules/vip/accountvip/vipAccountList";
	}

	@RequiresPermissions("vip:accountvip:vipAccount:view")
	@RequestMapping(value = "form")
	public String form(VipAccount vipAccount, Model model) {
		model.addAttribute("vipAccount", vipAccount);
		return "modules/vip/accountvip/vipAccountForm";
	}

	@RequiresPermissions("vip:accountvip:vipAccount:edit")
	@RequestMapping(value = "save")
	public String save(VipAccount vipAccount, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, vipAccount)){
			return form(vipAccount, model);
		}
		vipAccountService.save(vipAccount);
		addMessage(redirectAttributes, "保存用户权益成功");
		return "redirect:"+Global.getAdminPath()+"/vip/accountvip/vipAccount/?repage";
	}
	
	@RequiresPermissions("vip:accountvip:vipAccount:edit")
	@RequestMapping(value = "delete")
	public String delete(VipAccount vipAccount, RedirectAttributes redirectAttributes) {
		vipAccountService.delete(vipAccount);
		addMessage(redirectAttributes, "删除用户权益成功");
		return "redirect:"+Global.getAdminPath()+"/vip/accountvip/vipAccount/?repage";
	}

}