/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.order.web.undersorder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thinkgem.jeesite.modules.message.entity.Message;
import com.thinkgem.jeesite.modules.message.service.MessageService;
import com.thinkgem.jeesite.modules.order.entity.order.Order;
import com.thinkgem.jeesite.modules.order.entity.vip.OrderVip;
import com.thinkgem.jeesite.modules.unders.entity.sign.UndersSign;
import com.thinkgem.jeesite.modules.unders.entity.unders.Unders;
import com.thinkgem.jeesite.modules.unders.service.companyfile.UndersCompanyFileService;
import com.thinkgem.jeesite.modules.unders.service.sign.UndersSignService;
import com.thinkgem.jeesite.modules.vip.entity.Vip;
import com.thinkgem.jeesite.modules.vip.entity.accountvip.VipAccount;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.order.entity.undersorder.OrderUnders;
import com.thinkgem.jeesite.modules.order.service.undersorder.OrderUndersService;

import java.util.Date;

/**
 * 双选会订单Controller
 *
 * @author 李金辉
 * @version 2019-05-29
 */
@Controller
@RequestMapping(value = "${adminPath}/order/undersorder/orderUnders")
public class OrderUndersController extends BaseController {

    @Autowired
    private OrderUndersService orderUndersService;
    @Autowired
    private UndersSignService undersSignService;
    @Autowired
    private MessageService messageService;

    @Autowired
    private UndersSignService signService;

    @ModelAttribute
    public OrderUnders get(@RequestParam(required = false) String id) {
        OrderUnders entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = orderUndersService.get(id);
        }
        if (entity == null) {
            entity = new OrderUnders();
        }
        return entity;
    }

    @RequiresPermissions("order:undersorder:orderUnders:view")
    @RequestMapping(value = {"list", ""})
    public String list(OrderUnders orderUnders, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<OrderUnders> page = orderUndersService.findPage(new Page<OrderUnders>(request, response), orderUnders);
        model.addAttribute("page", page);
        return "modules/order/undersorder/orderUndersList";
    }

    @RequiresPermissions("order:undersorder:orderUnders:view")
    @RequestMapping(value = "form")
    public String form(OrderUnders orderUnders, Model model) {
        model.addAttribute("orderUnders", orderUnders);
        return "modules/order/undersorder/orderUndersForm";
    }

    @ResponseBody
    @RequestMapping(value = "signFile")
    public Object signFile(String orderId) {
        return signService.selectByOrderId(orderId);
    }

    @RequiresPermissions("order:undersorder:orderUnders:edit")
    @RequestMapping(value = "save")
    public String save(OrderUnders orderUnders, Model model, RedirectAttributes redirectAttributes) {
        if (!beanValidator(model, orderUnders)) {
            return form(orderUnders, model);
        }
        if (OrderUnders.PAY_TYPE_PUB.equals(orderUnders.getPayType())) {
            //添加消息
            Message message=new Message();
            message.setAccountId(orderUnders.getAccountId());
            message.setTeachinId(orderUnders.getId());
            message.setIsRead("0");
            if(OrderUnders.STATUS_PAYED.equals(orderUnders.getStatus())){
                orderUnders.setEndTime(new Date());
                //更改线报名状态
                UndersSign undersSign = undersSignService.selectByOrderId(orderUnders.getId());
                undersSign.setStatus(UndersSign.STATUS_NORMAL);
                undersSignService.save(undersSign);
                message.setContent("您的双选会订单审核通过");
                messageService.save(message);


            }

            if(OrderUnders.STATUS_CLOSE.equals(orderUnders.getStatus())){
                //删除报名状态
                undersSignService.rdeleteByOrderId(orderUnders.getId());
                message.setContent("您的双选会订单审核失败");
                messageService.save(message);
            }

        }
        orderUndersService.save(orderUnders);


        addMessage(redirectAttributes, "保存双选会订单成功");
        return "redirect:" + Global.getAdminPath() + "/order/undersorder/orderUnders/?repage";
    }

    @RequiresPermissions("order:undersorder:orderUnders:edit")
    @RequestMapping(value = "delete")
    public String delete(OrderUnders orderUnders, RedirectAttributes redirectAttributes) {
        orderUndersService.delete(orderUnders);
        addMessage(redirectAttributes, "删除双选会订单成功");
        return "redirect:" + Global.getAdminPath() + "/order/undersorder/orderUnders/?repage";
    }

}