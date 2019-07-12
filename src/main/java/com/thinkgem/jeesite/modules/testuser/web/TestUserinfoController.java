/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.testuser.web;

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
import com.thinkgem.jeesite.modules.testuser.entity.TestUserinfo;
import com.thinkgem.jeesite.modules.testuser.service.TestUserinfoService;

/**
 * 测试类Controller
 * @author Owen
 * @version 2017-10-18
 */
@Controller
@RequestMapping(value = "${adminPath}/testuser/testUserinfo")
public class TestUserinfoController extends BaseController {

	@Autowired
	private TestUserinfoService testUserinfoService;
	
	@ModelAttribute
	public TestUserinfo get(@RequestParam(required=false) String id) {
		TestUserinfo entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = testUserinfoService.get(id);
		}
		if (entity == null){
			entity = new TestUserinfo();
		}
		return entity;
	}
	
	@RequiresPermissions("testuser:testUserinfo:view")
	@RequestMapping(value = {"list", ""})
	public String list(TestUserinfo testUserinfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<TestUserinfo> page = testUserinfoService.findPage(new Page<TestUserinfo>(request, response), testUserinfo); 
		model.addAttribute("page", page);
		return "modules/testuser/testUserinfoList";
	}

	@RequiresPermissions("testuser:testUserinfo:view")
	@RequestMapping(value = "form")
	public String form(TestUserinfo testUserinfo, Model model) {
		model.addAttribute("testUserinfo", testUserinfo);
		return "modules/testuser/testUserinfoForm";
	}

	@RequiresPermissions("testuser:testUserinfo:edit")
	@RequestMapping(value = "save")
	public String save(TestUserinfo testUserinfo, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, testUserinfo)){
			return form(testUserinfo, model);
		}
		testUserinfoService.save(testUserinfo);
		addMessage(redirectAttributes, "保存测试成功");
		return "redirect:"+Global.getAdminPath()+"/testuser/testUserinfo/?repage";
	}
	
	@RequiresPermissions("testuser:testUserinfo:delete")
	@RequestMapping(value = "delete")
	public String delete(TestUserinfo testUserinfo, RedirectAttributes redirectAttributes) {
		testUserinfoService.delete(testUserinfo);
		addMessage(redirectAttributes, "删除测试成功");
		return "redirect:"+Global.getAdminPath()+"/testuser/testUserinfo/?repage";
	}

}