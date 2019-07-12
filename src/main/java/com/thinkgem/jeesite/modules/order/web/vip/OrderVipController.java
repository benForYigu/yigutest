/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.order.web.vip;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thinkgem.jeesite.API.entity.Code;
import com.thinkgem.jeesite.modules.account.entity.Account;
import com.thinkgem.jeesite.modules.account.service.AccountService;
import com.thinkgem.jeesite.modules.account.service.accounthr.AccountCompanyhrService;
import com.thinkgem.jeesite.modules.interaction.entity.teachin.InteractionTeachin;
import com.thinkgem.jeesite.modules.message.entity.Message;
import com.thinkgem.jeesite.modules.message.service.MessageService;
import com.thinkgem.jeesite.modules.order.entity.order.Order;
import com.thinkgem.jeesite.modules.sys.security.SystemAuthorizingRealm;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.thinkgem.jeesite.modules.vip.entity.Vip;
import com.thinkgem.jeesite.modules.vip.entity.accountvip.VipAccount;
import com.thinkgem.jeesite.modules.vip.service.VipService;
import com.thinkgem.jeesite.modules.vip.service.accountvip.VipAccountService;
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
import com.thinkgem.jeesite.modules.order.entity.vip.OrderVip;
import com.thinkgem.jeesite.modules.order.service.vip.OrderVipService;

import java.util.Date;

/**
 * vip订单Controller
 * @author 李金辉
 * @version 2019-03-23
 */
@Controller
@RequestMapping(value = "${adminPath}/order/vip/orderVip")
public class OrderVipController extends BaseController {

	@Autowired
	private OrderVipService orderVipService;
	@Autowired
	private AccountService accountService;
	@Autowired
	private AccountCompanyhrService companyhrService;
	@Autowired
	private VipAccountService vipAccountService;
	@Autowired
	private VipService vipService;
	@Autowired
	private MessageService messageService;
	@ModelAttribute
	public OrderVip get(@RequestParam(required=false) String id) {
		OrderVip entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = orderVipService.get(id);
		}
		if (entity == null){
			entity = new OrderVip();
		}
		return entity;
	}
	
	@RequiresPermissions("order:vip:orderVip:view")
	@RequestMapping(value = {"list", ""})
	public String list(OrderVip orderVip, HttpServletRequest request, HttpServletResponse response, Model model) {
		SystemAuthorizingRealm.Principal principal = UserUtils.getPrincipal();
		Account account=accountService.selectByPhone(principal.getLoginName());
		if(account!=null){
			model.addAttribute("companyUsers", "companyUsers");
			orderVip.setCompanyId(companyhrService.getByAccountId(account.getId()).getCompanyId());
		}

		Page pages =new Page<OrderVip>(request, response);
		pages.setOrderBy(" ov.status desc");
		Page<OrderVip> page = orderVipService.findPage(pages, orderVip);
		model.addAttribute("page", page);
		return "modules/order/vip/orderVipList";
	}

	@RequiresPermissions("order:vip:orderVip:view")
	@RequestMapping(value = "form")
	public String form(OrderVip orderVip, Model model) {
		model.addAttribute("orderVip", orderVip);
		return "modules/order/vip/orderVipForm";
	}

	@RequiresPermissions("order:vip:orderVip:edit")
	@RequestMapping(value = "save")
	public String save(OrderVip orderVip, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, orderVip)){
			return form(orderVip, model);
		}
		if(OrderVip.PAY_UNDER.equals(orderVip.getPayType())){
			//添加消息
			Message message=new Message();
			message.setAccountId(orderVip.getAccountId());
			message.setTeachinId(orderVip.getId());
			message.setIsRead("0");

			//通过
			if(Order.STATUS_PAYED.equals(orderVip.getStatus())){
				orderVip.setEndTime(new Date());
				//判断用户宣讲是否用完
				VipAccount vipAccount= vipAccountService.selectByAccountId(orderVip.getAccountId());
				if(vipAccount!=null&&vipAccount.getTeachin()>0){
					model.addAttribute("message","用户宣讲次数未使用完，不可以开通vip");
					model.addAttribute("orderVip", orderVip);
					return "modules/order/vip/orderVipForm";
				}else{
					Vip vip=vipService.get(orderVip.getVipId());
					vipAccount.setTeachin(vip.getTeachin());
					vipAccount.setInterviewTime(vipAccount.getTeachin()+(vip.getInterviewTime()==null?0:vip.getInterviewTime()));
					vipAccountService.save(vipAccount);

					message.setContent("您的vip订单审核通过");
					messageService.save(message);
				}
			}
			if(Order.STATUS_CLOSE.equals(orderVip.getStatus())){
				message.setContent("您的vip订单审核失败");
				messageService.save(message);
			}

		}
		orderVipService.save(orderVip);
		addMessage(redirectAttributes, "保存vip订单成功");
		return "redirect:"+Global.getAdminPath()+"/order/vip/orderVip/?repage";
	}
	
	@RequiresPermissions("order:vip:orderVip:edit")
	@RequestMapping(value = "delete")
	public String delete(OrderVip orderVip, RedirectAttributes redirectAttributes) {
		orderVipService.delete(orderVip);
		addMessage(redirectAttributes, "删除vip订单成功");
		return "redirect:"+Global.getAdminPath()+"/order/vip/orderVip/?repage";
	}

}