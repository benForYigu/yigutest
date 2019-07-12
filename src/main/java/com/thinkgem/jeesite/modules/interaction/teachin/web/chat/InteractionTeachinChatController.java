/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.interaction.teachin.web.chat;

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
import com.thinkgem.jeesite.modules.interaction.teachin.entity.chat.InteractionTeachinChat;
import com.thinkgem.jeesite.modules.interaction.teachin.service.chat.InteractionTeachinChatService;

/**
 * 宣讲聊天Controller
 * @author 李金辉
 * @version 2019-03-20
 */
@Controller
@RequestMapping(value = "${adminPath}/interaction.teachin/chat/interactionTeachinChat")
public class InteractionTeachinChatController extends BaseController {

	@Autowired
	private InteractionTeachinChatService interactionTeachinChatService;
	
	@ModelAttribute
	public InteractionTeachinChat get(@RequestParam(required=false) String id) {
		InteractionTeachinChat entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = interactionTeachinChatService.get(id);
		}
		if (entity == null){
			entity = new InteractionTeachinChat();
		}
		return entity;
	}
	
	@RequiresPermissions("interaction.teachin:chat:interactionTeachinChat:view")
	@RequestMapping(value = {"list", ""})
	public String list(InteractionTeachinChat interactionTeachinChat, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<InteractionTeachinChat> page = interactionTeachinChatService.findPage(new Page<InteractionTeachinChat>(request, response), interactionTeachinChat); 
		model.addAttribute("page", page);
		return "modules/interaction.teachin/chat/interactionTeachinChatList";
	}

	@RequiresPermissions("interaction.teachin:chat:interactionTeachinChat:view")
	@RequestMapping(value = "form")
	public String form(InteractionTeachinChat interactionTeachinChat, Model model) {
		model.addAttribute("interactionTeachinChat", interactionTeachinChat);
		return "modules/interaction.teachin/chat/interactionTeachinChatForm";
	}

	@RequiresPermissions("interaction.teachin:chat:interactionTeachinChat:edit")
	@RequestMapping(value = "save")
	public String save(InteractionTeachinChat interactionTeachinChat, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, interactionTeachinChat)){
			return form(interactionTeachinChat, model);
		}
		interactionTeachinChatService.save(interactionTeachinChat);
		addMessage(redirectAttributes, "保存宣讲聊天成功");
		return "redirect:"+Global.getAdminPath()+"/interaction.teachin/chat/interactionTeachinChat/?repage";
	}
	
	@RequiresPermissions("interaction.teachin:chat:interactionTeachinChat:edit")
	@RequestMapping(value = "delete")
	public String delete(InteractionTeachinChat interactionTeachinChat, RedirectAttributes redirectAttributes) {
		interactionTeachinChatService.delete(interactionTeachinChat);
		addMessage(redirectAttributes, "删除宣讲聊天成功");
		return "redirect:"+Global.getAdminPath()+"/interaction.teachin/chat/interactionTeachinChat/?repage";
	}

}