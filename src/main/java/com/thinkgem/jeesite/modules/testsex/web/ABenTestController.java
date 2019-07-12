/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.testsex.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.excel.ExportExcel;
import com.thinkgem.jeesite.modules.account.entity.Account;
import com.thinkgem.jeesite.modules.account.service.AccountService;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.service.SystemService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.testsex.entity.ABenTest;
import com.thinkgem.jeesite.modules.testsex.service.ABenTestService;

/**
 * 测试表Controller
 * @author ben
 * @version 2019-07-05
 */
@Controller
@RequestMapping(value = "${adminPath}/testsex/aBenTest")
public class ABenTestController extends BaseController {

	@Autowired
	private ABenTestService aBenTestService;
	@Autowired
	private SystemService systemService;

	@ModelAttribute
	public ABenTest get(@RequestParam(required=false) String id) {
		ABenTest entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = aBenTestService.get(id);
		}
		if (entity == null){
			entity = new ABenTest();
		}
		return entity;
	}
	
	@RequiresPermissions("testsex:aBenTest:view")
	@RequestMapping(value = {"list", ""})
	public String list(ABenTest aBenTest, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ABenTest> page = aBenTestService.findPage(new Page<ABenTest>(request, response), aBenTest);
		model.addAttribute("page", page);
		return "modules/testsex/aBenTestList";
	}

	@RequiresPermissions("testsex:aBenTest:view")
	@RequestMapping(value = "form")
	public String form(ABenTest aBenTest, Model model) {
		model.addAttribute("aBenTest", aBenTest);
		return "modules/testsex/aBenTestForm";
	}

	@RequiresPermissions("testsex:aBenTest:edit")
	@RequestMapping(value = "save")
	public String save(ABenTest aBenTest, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, aBenTest)){
			return form(aBenTest, model);
		}
		aBenTestService.save(aBenTest);
		addMessage(redirectAttributes, "保存测试表成功");
		return "redirect:"+Global.getAdminPath()+"/testsex/aBenTest/?repage";
	}
	
	@RequiresPermissions("testsex:aBenTest:edit")
	@RequestMapping(value = "delete")
	public String delete(ABenTest aBenTest, RedirectAttributes redirectAttributes) {
		aBenTestService.delete(aBenTest);
		addMessage(redirectAttributes, "删除测试表成功");
		return "redirect:"+Global.getAdminPath()+"/testsex/aBenTest/?repage";
	}

	@RequiresPermissions("testsex:aBenTest:view")
	@RequestMapping(value = "/export")
	public String export(ABenTest aBenTest,HttpServletRequest request,
						 HttpServletResponse response, RedirectAttributes redirectAttributes) {
		User user = UserUtils.getUser();
		System.out.println("当前登录人："+user.getLoginName()+"，参数：name="+aBenTest.getName()+",mobile="+aBenTest.getMobile());
		try {
			String fileName = "测试表信息数据"+ DateUtils.getDate("yyyy年MM月dd日HH时mm分ss秒")+".xlsx";
			Page<ABenTest> page = aBenTestService.findPage(new Page<ABenTest>(request, response, -1), aBenTest);
			new ExportExcel("测试表信息数据",ABenTest.class).setDataList(page.getList()).write(response,fileName).dispose();
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			addMessage(redirectAttributes, "导出报名信息失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/testsex/aBenTest/?repage";
	}

	@RequestMapping(value = "/redis/set")
	@ResponseBody
	public String redisSet(ABenTest aBenTest) {
		System.out.println("参数：name="+aBenTest.getName()+",remark="+aBenTest.getRemark());
		return aBenTestService.setJedis(aBenTest);
	}

	@RequestMapping(value = "/redis/get")
	@ResponseBody
	public String redisGet(ABenTest aBenTest) {
		System.out.println("参数：name="+aBenTest.getName());
		return aBenTestService.getJedis(aBenTest);
	}


	//测试tag

}