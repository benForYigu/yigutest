/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.unders.web.sign;

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
import com.thinkgem.jeesite.modules.unders.entity.sign.UndersSign;
import com.thinkgem.jeesite.modules.unders.service.sign.UndersSignService;

/**
 * 双选会报名Controller
 * @author 李金辉
 * @version 2019-05-24
 */
@Controller
@RequestMapping(value = "${adminPath}/unders/sign/undersSign")
public class UndersSignController extends BaseController {

	@Autowired
	private UndersSignService undersSignService;
	
	@ModelAttribute
	public UndersSign get(@RequestParam(required=false) String id) {
		UndersSign entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = undersSignService.get(id);
		}
		if (entity == null){
			entity = new UndersSign();
		}
		return entity;
	}
	

	@RequestMapping(value = {"list", ""})
	public String list(UndersSign undersSign, HttpServletRequest request, HttpServletResponse response, Model model) {
		undersSign.setStatus(UndersSign.STATUS_NORMAL);
		Page<UndersSign> page = undersSignService.findPage(new Page<UndersSign>(request, response), undersSign);
		model.addAttribute("page", page);
		return "modules/unders/sign/undersSignList";
	}


	@RequestMapping(value = "form")
	public String form(UndersSign undersSign, Model model) {
		model.addAttribute("undersSign", undersSign);
		return "modules/unders/sign/undersSignForm";
	}


	@RequestMapping(value = "save")
	public String save(UndersSign undersSign, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, undersSign)){
			return form(undersSign, model);
		}
		undersSignService.save(undersSign);
		addMessage(redirectAttributes, "保存双选会报名成功");
		return "redirect:"+Global.getAdminPath()+"/unders/sign/undersSign/?repage";
	}
	

	@RequestMapping(value = "delete")
	public String delete(UndersSign undersSign, RedirectAttributes redirectAttributes) {
		undersSignService.delete(undersSign);
		addMessage(redirectAttributes, "删除双选会报名成功");
		return "redirect:"+Global.getAdminPath()+"/unders/sign/undersSign/?repage";
	}

}