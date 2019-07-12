/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.vip.web;

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
import com.thinkgem.jeesite.modules.vip.entity.Vip;
import com.thinkgem.jeesite.modules.vip.service.VipService;

/**
 * vipController
 * @author 李金辉
 * @version 2019-03-23
 */
@Controller
@RequestMapping(value = "${adminPath}/vip/vip")
public class VipController extends BaseController {

	@Autowired
	private VipService vipService;
	
	@ModelAttribute
	public Vip get(@RequestParam(required=false) String id) {
		Vip entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = vipService.get(id);
		}
		if (entity == null){
			entity = new Vip();
		}
		return entity;
	}
	
	@RequiresPermissions("vip:vip:view")
	@RequestMapping(value = {"list", ""})
	public String list(Vip vip, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Vip> page = vipService.findPage(new Page<Vip>(request, response), vip); 
		model.addAttribute("page", page);
		return "modules/vip/vipList";
	}

	@RequiresPermissions("vip:vip:view")
	@RequestMapping(value = "form")
	public String form(Vip vip, Model model) {
		model.addAttribute("vip", vip);
		return "modules/vip/vipForm";
	}

	@RequiresPermissions("vip:vip:edit")
	@RequestMapping(value = "save")
	public String save(Vip vip, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, vip)){
			return form(vip, model);
		}
		vipService.save(vip);
		addMessage(redirectAttributes, "保存vip成功");
		return "redirect:"+Global.getAdminPath()+"/vip/vip/?repage";
	}
	
	@RequiresPermissions("vip:vip:edit")
	@RequestMapping(value = "delete")
	public String delete(Vip vip, RedirectAttributes redirectAttributes) {
		vipService.delete(vip);
		addMessage(redirectAttributes, "删除vip成功");
		return "redirect:"+Global.getAdminPath()+"/vip/vip/?repage";
	}

}