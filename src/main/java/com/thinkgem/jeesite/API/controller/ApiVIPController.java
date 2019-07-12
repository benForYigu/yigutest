package com.thinkgem.jeesite.API.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Strings;
import com.thinkgem.jeesite.API.entity.Code;
import com.thinkgem.jeesite.API.entity.Response;
import com.thinkgem.jeesite.API.util.TokenUtils;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.modules.account.entity.Account;
import com.thinkgem.jeesite.modules.account.service.AccountService;
import com.thinkgem.jeesite.modules.account.service.accounthr.AccountCompanyhrService;
import com.thinkgem.jeesite.modules.order.entity.order.Order;
import com.thinkgem.jeesite.modules.order.entity.vip.OrderVip;
import com.thinkgem.jeesite.modules.order.service.order.OrderService;
import com.thinkgem.jeesite.modules.order.service.vip.OrderVipService;
import com.thinkgem.jeesite.modules.vip.service.VipService;
import com.thinkgem.jeesite.modules.vip.service.accountvip.VipAccountService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

/**
 * 客户操作
 */

@RestController
@RequestMapping("${apiPath}/vip")
public class ApiVIPController {
    private final Log log = LogFactory.getLog(getClass());
    @Autowired
    OrderVipService orderVipService;
    @Autowired
    AccountService accountService;
    @Autowired
    VipService vipService;
    @Autowired
    VipAccountService vipAccountService;
    @Autowired
    AccountCompanyhrService companyhrService;
    @Autowired
    OrderService orderService;

    @Value("${token.header}")
    private String tokenHeader;
    @Autowired
    private TokenUtils tokenUtils;
    @RequestMapping(value = "/vip", method = RequestMethod.GET)
    public Response vip(HttpServletRequest request,String vipId) {
        Account account = (Account) request.getAttribute("account");
        if(!Account.ROLE_COMPANY.equals(account.getRole())){
            return new Response(Code.API_USER_ROLE_ERROR);
        }
        return new Response(vipService.vip(account,vipId));
    }
    @RequestMapping(value = "/order", method = RequestMethod.GET)
    public Response order(HttpServletRequest request,String orderId,
                          @RequestParam(value = "page",defaultValue = "1") Integer page,
                          @RequestParam(value = "size",defaultValue = "10") Integer size) {
            if(!Strings.isNullOrEmpty(orderId)){
                return new Response(orderVipService.get(orderId));
            }else{
                Account account = (Account) request.getAttribute("account");
                OrderVip orderVip=new OrderVip();
                orderVip.setAccountId(account.getId());
                PageHelper.startPage(page,size);
                return new Response(new PageInfo<OrderVip>(orderVipService.findList(orderVip)));
            }


    }

    /**
     *
     * @param request
     * @param vipId
     * @param type 2对公支付
     * @return
     */
    @RequestMapping(value = "/pay", method = RequestMethod.POST)
    public Response pay(HttpServletRequest request,@RequestParam String vipId
            ,String type) {
        Account account = (Account) request.getAttribute("account");
        if(!Account.ROLE_COMPANY.equals(account.getRole())){
            return new Response(Code.API_USER_ROLE_ERROR);
        }
        return new Response(orderVipService.pay(request,account,vipId,type));
    }



    @RequestMapping(value = "/notify")
    public void notify(HttpServletRequest request,HttpServletResponse response) throws IOException {
        log.info("===================收到VIP回调===================");
        orderVipService.paynotify(request,response);
    }


    /**
     *免费
     * @param request

     * @return
     */
    @RequestMapping(value = "/right", method = RequestMethod.GET)
    public Response right(HttpServletRequest request
    )  {
        String token=request.getHeader(tokenHeader);
        if(!Strings.isNullOrEmpty(token)){
            token=token.substring(7);
        }
        String id = tokenUtils.getUsernameFromToken(token);
        Account account=accountService.get(id);
        if(account==null){
           return new Response(Code.API_VIP_NO);
        }

        if(!Account.ROLE_COMPANY.equals(account.getRole())||Strings.isNullOrEmpty(companyhrService.getByAccountId(account.getId()).getVip())){
            return new Response(Code.API_VIP_NO);
        }

        return new Response(vipAccountService.selectByAccountId(account.getId()));
    }


    /**
     * 添加转账凭证
     * @param request
     * @return
     */
    @RequestMapping(value = "/voucher", method = RequestMethod.PUT)
    public Response voucher(HttpServletRequest request,
                            @RequestParam String voucher,
                            @RequestParam String orderId)  {

        Account account=(Account)request.getAttribute("account");
        return new Response(orderService.voucher(account,voucher,orderId));
    }
}

