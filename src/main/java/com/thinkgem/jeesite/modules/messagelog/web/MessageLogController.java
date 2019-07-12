/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.messagelog.web;

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
import com.thinkgem.jeesite.modules.messagelog.entity.MessageLog;
import com.thinkgem.jeesite.modules.messagelog.service.MessageLogService;

/**
 * 消息记录Controller
 * @author 李金辉
 * @version 2019-01-28
 */
@Controller
@RequestMapping(value = "${adminPath}/messagelog/messageLog")
public class MessageLogController extends BaseController {

	@Autowired
	private MessageLogService messageLogService;
	
	@ModelAttribute
	public MessageLog get(@RequestParam(required=false) String id) {
		MessageLog entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = messageLogService.get(id);
		}
		if (entity == null){
			entity = new MessageLog();
		}
		return entity;
	}
	
	@RequiresPermissions("messagelog:messageLog:view")
	@RequestMapping(value = {"list", ""})
	public String list(MessageLog messageLog, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<MessageLog> page = messageLogService.findPage(new Page<MessageLog>(request, response), messageLog); 
		model.addAttribute("page", page);
		return "modules/messagelog/messageLogList";
	}

	@RequiresPermissions("messagelog:messageLog:view")
	@RequestMapping(value = "form")
	public String form(MessageLog messageLog, Model model) {
		model.addAttribute("messageLog", messageLog);
		return "modules/messagelog/messageLogForm";
	}

	@RequiresPermissions("messagelog:messageLog:edit")
	@RequestMapping(value = "save")
	public String save(MessageLog messageLog, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, messageLog)){
			return form(messageLog, model);
		}
		messageLogService.save(messageLog);
		addMessage(redirectAttributes, "保存消息记录成功");
		return "redirect:"+Global.getAdminPath()+"/messagelog/messageLog/?repage";
	}
	
	@RequiresPermissions("messagelog:messageLog:edit")
	@RequestMapping(value = "delete")
	public String delete(MessageLog messageLog, RedirectAttributes redirectAttributes) {
		messageLogService.delete(messageLog);
		addMessage(redirectAttributes, "删除消息记录成功");
		return "redirect:"+Global.getAdminPath()+"/messagelog/messageLog/?repage";
	}

}