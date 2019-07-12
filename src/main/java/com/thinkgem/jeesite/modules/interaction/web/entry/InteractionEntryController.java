/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.interaction.web.entry;

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
import com.thinkgem.jeesite.modules.interaction.entity.entry.InteractionEntry;
import com.thinkgem.jeesite.modules.interaction.service.entry.InteractionEntryService;

/**
 * 入职秀Controller
 * @author 李金辉
 * @version 2019-01-24
 */
@Controller
@RequestMapping(value = "${adminPath}/interaction/entry/interactionEntry")
public class InteractionEntryController extends BaseController {

	@Autowired
	private InteractionEntryService interactionEntryService;
	
	@ModelAttribute
	public InteractionEntry get(@RequestParam(required=false) String id) {
		InteractionEntry entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = interactionEntryService.get(id);
		}
		if (entity == null){
			entity = new InteractionEntry();
		}
		return entity;
	}
	
	@RequiresPermissions("interaction:entry:interactionEntry:view")
	@RequestMapping(value = {"list", ""})
	public String list(InteractionEntry interactionEntry, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<InteractionEntry> page = interactionEntryService.findPage(new Page<InteractionEntry>(request, response), interactionEntry); 
		model.addAttribute("page", page);
		return "modules/interaction/entry/interactionEntryList";
	}

	@RequiresPermissions("interaction:entry:interactionEntry:view")
	@RequestMapping(value = "form")
	public String form(InteractionEntry interactionEntry, Model model) {
		model.addAttribute("interactionEntry", interactionEntry);
		return "modules/interaction/entry/interactionEntryForm";
	}

	@RequiresPermissions("interaction:entry:interactionEntry:edit")
	@RequestMapping(value = "save")
	public String save(InteractionEntry interactionEntry, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, interactionEntry)){
			return form(interactionEntry, model);
		}
		interactionEntryService.save(interactionEntry);
		addMessage(redirectAttributes, "保存入职秀成功");
		return "redirect:"+Global.getAdminPath()+"/interaction/entry/interactionEntry/?repage";
	}
	
	@RequiresPermissions("interaction:entry:interactionEntry:edit")
	@RequestMapping(value = "delete")
	public String delete(InteractionEntry interactionEntry, RedirectAttributes redirectAttributes) {
		interactionEntryService.delete(interactionEntry);
		addMessage(redirectAttributes, "删除入职秀成功");
		return "redirect:"+Global.getAdminPath()+"/interaction/entry/interactionEntry/?repage";
	}

}