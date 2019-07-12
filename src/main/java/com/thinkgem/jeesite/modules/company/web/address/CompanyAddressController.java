/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.company.web.address;

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
import com.thinkgem.jeesite.modules.company.entity.address.CompanyAddress;
import com.thinkgem.jeesite.modules.company.service.address.CompanyAddressService;

/**
 * 企业地址Controller
 * @author 李金辉
 * @version 2019-01-29
 */
@Controller
@RequestMapping(value = "${adminPath}/company/address/companyAddress")
public class CompanyAddressController extends BaseController {

	@Autowired
	private CompanyAddressService companyAddressService;
	
	@ModelAttribute
	public CompanyAddress get(@RequestParam(required=false) String id) {
		CompanyAddress entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = companyAddressService.get(id);
		}
		if (entity == null){
			entity = new CompanyAddress();
		}
		return entity;
	}
	
	@RequiresPermissions("company:address:companyAddress:view")
	@RequestMapping(value = {"list", ""})
	public String list(CompanyAddress companyAddress, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CompanyAddress> page = companyAddressService.findPage(new Page<CompanyAddress>(request, response), companyAddress); 
		model.addAttribute("page", page);
		return "modules/company/address/companyAddressList";
	}

	@RequiresPermissions("company:address:companyAddress:view")
	@RequestMapping(value = "form")
	public String form(CompanyAddress companyAddress, Model model) {
		model.addAttribute("companyAddress", companyAddress);
		return "modules/company/address/companyAddressForm";
	}

	@RequiresPermissions("company:address:companyAddress:edit")
	@RequestMapping(value = "save")
	public String save(CompanyAddress companyAddress, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, companyAddress)){
			return form(companyAddress, model);
		}
		companyAddressService.save(companyAddress);
		addMessage(redirectAttributes, "保存企业地址成功");
		return "redirect:"+Global.getAdminPath()+"/company/address/companyAddress/?repage";
	}
	
	@RequiresPermissions("company:address:companyAddress:edit")
	@RequestMapping(value = "delete")
	public String delete(CompanyAddress companyAddress, RedirectAttributes redirectAttributes) {
		companyAddressService.delete(companyAddress);
		addMessage(redirectAttributes, "删除企业地址成功");
		return "redirect:"+Global.getAdminPath()+"/company/address/companyAddress/?repage";
	}

}