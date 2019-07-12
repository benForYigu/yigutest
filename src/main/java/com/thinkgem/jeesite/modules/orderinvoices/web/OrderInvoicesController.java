/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.orderinvoices.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.common.base.Strings;
import com.thinkgem.jeesite.modules.order.entity.order.Order;
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
import com.thinkgem.jeesite.modules.orderinvoices.entity.OrderInvoices;
import com.thinkgem.jeesite.modules.orderinvoices.service.OrderInvoicesService;

/**
 * 企业发票Controller
 * @author 李金辉
 * @version 2019-04-19
 */
@Controller
@RequestMapping(value = "${adminPath}/orderinvoices/orderInvoices")
public class OrderInvoicesController extends BaseController {

	@Autowired
	private OrderInvoicesService orderInvoicesService;
	
	@ModelAttribute
	public OrderInvoices get(@RequestParam(required=false) String id) {
		OrderInvoices entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = orderInvoicesService.get(id);
		}
		if (entity == null){
			entity = new OrderInvoices();
		}
		return entity;
	}
	

	@RequestMapping(value = {"list", ""})
	public String list(OrderInvoices orderInvoices, HttpServletRequest request, HttpServletResponse response, Model model) {

		Page<OrderInvoices> page = orderInvoicesService.findPage(new Page<OrderInvoices>(request, response), orderInvoices);
		model.addAttribute("page", page);
		return "modules/orderinvoices/orderInvoicesList";
	}


	@RequestMapping(value = "form")
	public String form(OrderInvoices orderInvoices, Model model,String flag) {
		if(Strings.isNullOrEmpty(orderInvoices.getId())){
			orderInvoices= orderInvoicesService.selectByOrderId(orderInvoices.getOrderId());
		}
		model.addAttribute("orderInvoices", orderInvoices);
		model.addAttribute("flag", flag);
		return "modules/orderinvoices/orderInvoicesForm";
	}


	@RequestMapping(value = "save")
	public String save(OrderInvoices orderInvoices,String flag, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, orderInvoices)){
			return form(orderInvoices, model,null);
		}
		orderInvoicesService.save(orderInvoices);
		addMessage(redirectAttributes, "保存发票成功");
		if("1".equals(flag)){
			return "redirect:"+Global.getAdminPath()+"/order/order/order/?repage";
		}else{
			return "redirect:"+Global.getAdminPath()+"/order/vip/orderVip/?repage";
		}
		//return "redirect:"+Global.getAdminPath()+"/orderinvoices/orderInvoices/?repage";
	}
	

	@RequestMapping(value = "delete")
	public String delete(OrderInvoices orderInvoices, RedirectAttributes redirectAttributes) {
		orderInvoicesService.delete(orderInvoices);
		addMessage(redirectAttributes, "删除企业发票成功");
		return "redirect:"+Global.getAdminPath()+"/orderinvoices/orderInvoices/?repage";
	}

}