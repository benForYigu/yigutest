/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.smsrecord.web;

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
import com.thinkgem.jeesite.modules.smsrecord.entity.SmsRecord;
import com.thinkgem.jeesite.modules.smsrecord.service.SmsRecordService;

/**
 * 验证码Controller
 * @author 李金辉
 * @version 2019-01-30
 */
@Controller
@RequestMapping(value = "${adminPath}/smsrecord/smsRecord")
public class SmsRecordController extends BaseController {

	@Autowired
	private SmsRecordService smsRecordService;
	
	@ModelAttribute
	public SmsRecord get(@RequestParam(required=false) String id) {
		SmsRecord entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = smsRecordService.get(id);
		}
		if (entity == null){
			entity = new SmsRecord();
		}
		return entity;
	}
	
	@RequiresPermissions("smsrecord:smsRecord:view")
	@RequestMapping(value = {"list", ""})
	public String list(SmsRecord smsRecord, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<SmsRecord> page = smsRecordService.findPage(new Page<SmsRecord>(request, response), smsRecord); 
		model.addAttribute("page", page);
		return "modules/smsrecord/smsRecordList";
	}

	@RequiresPermissions("smsrecord:smsRecord:view")
	@RequestMapping(value = "form")
	public String form(SmsRecord smsRecord, Model model) {
		model.addAttribute("smsRecord", smsRecord);
		return "modules/smsrecord/smsRecordForm";
	}

	@RequiresPermissions("smsrecord:smsRecord:edit")
	@RequestMapping(value = "save")
	public String save(SmsRecord smsRecord, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, smsRecord)){
			return form(smsRecord, model);
		}
		smsRecordService.save(smsRecord);
		addMessage(redirectAttributes, "保存验证码成功");
		return "redirect:"+Global.getAdminPath()+"/smsrecord/smsRecord/?repage";
	}
	
	@RequiresPermissions("smsrecord:smsRecord:edit")
	@RequestMapping(value = "delete")
	public String delete(SmsRecord smsRecord, RedirectAttributes redirectAttributes) {
		smsRecordService.delete(smsRecord);
		addMessage(redirectAttributes, "删除验证码成功");
		return "redirect:"+Global.getAdminPath()+"/smsrecord/smsRecord/?repage";
	}

}