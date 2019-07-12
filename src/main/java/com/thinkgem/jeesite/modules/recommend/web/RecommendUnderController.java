/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.recommend.web;

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
import com.thinkgem.jeesite.modules.recommend.entity.RecommendUnder;
import com.thinkgem.jeesite.modules.recommend.service.RecommendUnderService;

/**
 * 线下活动Controller
 * @author 李金辉
 * @version 2019-03-01
 */
@Controller
@RequestMapping(value = "${adminPath}/recommend/recommendUnder")
public class RecommendUnderController extends BaseController {

	@Autowired
	private RecommendUnderService recommendUnderService;
	
	@ModelAttribute
	public RecommendUnder get(@RequestParam(required=false) String id) {
		RecommendUnder entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = recommendUnderService.get(id);
		}
		if (entity == null){
			entity = new RecommendUnder();
		}
		return entity;
	}
	
	@RequiresPermissions("recommend:recommendUnder:view")
	@RequestMapping(value = {"list", ""})
	public String list(RecommendUnder recommendUnder, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<RecommendUnder> page = recommendUnderService.findPage(new Page<RecommendUnder>(request, response), recommendUnder); 
		model.addAttribute("page", page);
		return "modules/recommend/recommendUnderList";
	}

	@RequiresPermissions("recommend:recommendUnder:view")
	@RequestMapping(value = "form")
	public String form(RecommendUnder recommendUnder, Model model) {
		model.addAttribute("recommendUnder", recommendUnder);
		return "modules/recommend/recommendUnderForm";
	}

	@RequiresPermissions("recommend:recommendUnder:edit")
	@RequestMapping(value = "save")
	public String save(RecommendUnder recommendUnder, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, recommendUnder)){
			return form(recommendUnder, model);
		}
		recommendUnderService.save(recommendUnder);
		addMessage(redirectAttributes, "保存线下活动成功");
		return "redirect:"+Global.getAdminPath()+"/recommend/recommendUnder/?repage";
	}
	
	@RequiresPermissions("recommend:recommendUnder:edit")
	@RequestMapping(value = "delete")
	public String delete(RecommendUnder recommendUnder, RedirectAttributes redirectAttributes) {
		recommendUnderService.delete(recommendUnder);
		addMessage(redirectAttributes, "删除线下活动成功");
		return "redirect:"+Global.getAdminPath()+"/recommend/recommendUnder/?repage";
	}

}