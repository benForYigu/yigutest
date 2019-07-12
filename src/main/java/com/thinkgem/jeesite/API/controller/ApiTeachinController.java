package com.thinkgem.jeesite.API.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Strings;
import com.thinkgem.jeesite.API.entity.Code;
import com.thinkgem.jeesite.API.entity.Response;
import com.thinkgem.jeesite.API.entity.SocketHandler;
import com.thinkgem.jeesite.API.util.MyUtils;
import com.thinkgem.jeesite.common.beanvalidator.BeanValidators;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.JedisUtils;
import com.thinkgem.jeesite.modules.account.entity.Account;
import com.thinkgem.jeesite.modules.account.service.accounthr.AccountCompanyhrService;
import com.thinkgem.jeesite.modules.interaction.entity.teachin.InteractionTeachin;
import com.thinkgem.jeesite.modules.interaction.entity.teachin.InteractionTeachinData;
import com.thinkgem.jeesite.modules.interaction.service.teachin.InteractionTeachinService;
import com.thinkgem.jeesite.modules.interaction.teachin.entity.chat.InteractionTeachinChat;
import com.thinkgem.jeesite.modules.interaction.teachin.service.chat.InteractionTeachinChatService;
import com.thinkgem.jeesite.modules.order.entity.order.Order;
import com.thinkgem.jeesite.modules.order.entity.undersorder.OrderUnders;
import com.thinkgem.jeesite.modules.order.entity.vip.OrderVip;
import com.thinkgem.jeesite.modules.order.service.order.OrderService;
import com.thinkgem.jeesite.modules.order.service.undersorder.OrderUndersService;
import com.thinkgem.jeesite.modules.order.service.vip.OrderVipService;
import com.thinkgem.jeesite.modules.orderinvoices.entity.OrderInvoices;
import com.thinkgem.jeesite.modules.orderinvoices.service.OrderInvoicesService;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import java.io.IOException;
import java.text.ParseException;
import java.util.*;

/**
 * 宣讲
 */

@RestController
@RequestMapping("${apiPath}/teachin")
public class ApiTeachinController {
    private final Log log = LogFactory.getLog(getClass());






    @Autowired
    private InteractionTeachinService interactionTeachinService;
    @Autowired
    private InteractionTeachinChatService chatService;
    @Autowired
    private OrderInvoicesService invoicesService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderVipService orderVipService;
    @Autowired
    private AccountCompanyhrService companyhrService;
    @Autowired
    private OrderUndersService orderUndersService;

    @Autowired
    protected Validator validator;

    private static Map<String, String> tokenMap = new LinkedHashMap<String, String>();


    /**
     * 根据account获取宣讲列表或详情
     * @param request
     * @param status 状态 1即将进行 2进行中 3已结束
     * @param teachinId
     * @param page
     * @param size
     * @return
     */
    @RequestMapping(value = "/teachin", method = RequestMethod.GET)
    public Response teachin(HttpServletRequest request,String status,String teachinId,
                            @RequestParam(value="page" ,defaultValue = "1") Integer page,
                            @RequestParam(value="size" ,defaultValue = "10") Integer size)  {
        Account account=(Account)request.getAttribute("account");
        return new Response(interactionTeachinService.teachin(account,status,teachinId,page,size));
    }


    @RequestMapping(value = "/teachinData", method = RequestMethod.GET)
    public Response teachinData(HttpServletRequest request,@RequestParam String teachinId)  {
        Account account=(Account)request.getAttribute("account");
        return new Response(interactionTeachinService.teachinData(teachinId));
    }



    /**
     * 学校组人员详情
     * @param request
     * @return
     */
    @RequestMapping(value = "/schoolGroup", method = RequestMethod.GET)
    public Response schoolGroup(HttpServletRequest request,String studentName)  {
        Account account=(Account)request.getAttribute("account");
        return new Response(interactionTeachinService.schoolGroup(account,studentName));
    }


    /**
     *宣讲相关职位
     * @param request
     * @param teachinId 宣讲id 查询单个时可不传
     * @param professionId 职位id 查询单个详情时传
     * @param page
     * @param size
     * @return
     */
    @RequestMapping(value = "/profession", method = RequestMethod.GET)
    public Response profession(HttpServletRequest request,
                              String teachinId,
                              String companyId,
                               String professionId,
                               @RequestParam(value="page" ,defaultValue = "1") Integer page,
                               @RequestParam(value="size" ,defaultValue = "10") Integer size)  {

        if(Strings.isNullOrEmpty(teachinId)&&Strings.isNullOrEmpty(companyId)&&Strings.isNullOrEmpty(professionId)){
            return new Response(Code.API_PARRAM_ERROR);
        }
        return new Response(interactionTeachinService.profession(teachinId,professionId,companyId,page,size));
    }

    /*企业============*/






    /**
     * 购买宣讲
     * @param request
     * @param schoolId
     * @param date
     * @param type 1vip购买 2普通购买 3抵扣 4对公账户
     * @param time 110:00 -10:45 2 11:00 -11:45 3 14:00 -14:45 4 15:00 -15:45 5 16:00 -16:45 6 19:00 -19:45
     * @return
     */
    @RequestMapping(value = "/teachin", method = RequestMethod.POST)
    public Response pTeachin(HttpServletRequest request,
                             @RequestParam String schoolId,
                             @RequestParam String date,
                             @RequestParam String time,
                             @RequestParam(value="type" ,defaultValue = "2") String type
    )  {
        Account account=(Account)request.getAttribute("account");
        if(!Account.ROLE_COMPANY.equals(account.getRole())){
            return new Response(Code.API_USER_ROLE_ERROR);
        }
        if(Strings.isNullOrEmpty(time)){
            return new Response(Code.API_PARRAM_ERROR,"time参数错误");
        }
        if(Strings.isNullOrEmpty(date)){
            return new Response(Code.API_PARRAM_ERROR,"date参数错误");
        }

        if(Strings.isNullOrEmpty(time)||"123456".indexOf(time)<0 || "1234".indexOf(type)<0){
            return new Response(Code.API_PARRAM_ERROR);
        }

        //日期比现在小
        if(DateUtils.parseDate(date).compareTo(new Date())<=0){
                return new Response(Code.API_TEACHIN_TIME_ERROR);
        }
        return new Response(interactionTeachinService.pTeachin(request,account,schoolId,date,time,type));
    }

    /**
     * 是否已被占用
     * @param request

     * @return
     */
    @RequestMapping(value = "/isUsed", method = RequestMethod.GET)
    public Response isUsed(HttpServletRequest request,
                         @RequestParam String schoolId,
                          @RequestParam String date)  {
        Account account=(Account)request.getAttribute("account");
        return new Response(interactionTeachinService.isUsed(schoolId,date));
    }
    /**
     * 订单列表、详情
     * @param request
     * @param orderId
     * @return
     */
    @RequestMapping(value = "/order", method = RequestMethod.GET)
    public Response order(HttpServletRequest request,
                         String orderId,
                          @RequestParam(value = "page",defaultValue = "1") Integer page,
                          @RequestParam(value = "size",defaultValue = "10") Integer size)  {
        Account account=(Account)request.getAttribute("account");
        if(Strings.isNullOrEmpty(orderId)){
            Order order=new Order();
            order.setAccountId(account.getId());
            PageHelper.startPage(page,size);
            return new Response(new PageInfo<Order>(orderService.findList(order)));
        }
        return new Response(orderService.get(orderId));
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
    /**
     * 获取最近订单发票
     * @param request
     * @return
     */
    @RequestMapping(value = "/orderInvoices", method = RequestMethod.GET)
    public Response GorderInvoices(HttpServletRequest request,String InvoicesId)  {
        Account account=(Account)request.getAttribute("account");
        if(Strings.isNullOrEmpty(InvoicesId)){
            return new Response( invoicesService.selectLatest(account.getId()));
        }
        return new Response(invoicesService.get(InvoicesId));
    }

    /**
     * 保存订单发票
     * @param request
     * @param orderInvoices
     * @return
     */
    @RequestMapping(value = "/orderInvoices", method = RequestMethod.POST)
    public Response orderInvoices(HttpServletRequest request, OrderInvoices orderInvoices)  {
        Account account=(Account)request.getAttribute("account");
        try{
            BeanValidators.validateWithException(validator, orderInvoices);
        }catch(ConstraintViolationException ex){
            List<String> list = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
            return new Response(Code.API_PARRAM_ERROR,list.toArray(new String[]{}));
        }
        Order order=orderService.get(orderInvoices.getOrderId());

        OrderVip orderVip=orderVipService.get(orderInvoices.getOrderId());
        OrderUnders orderUnders=orderUndersService.get(orderInvoices.getOrderId());
        if(order==null && orderVip==null&&orderUnders==null){
            return new Response(Code.ORDER_ID_ERROR);
        }
        if(order!=null){
            if(!order.getAccountId().equals(account.getId())||!Order.STATUS_PAYED.equals(order.getStatus())){
                return new Response(Code.API_USER_ROLE_ERROR);
            }
        }
        if(orderVip!=null) {
            if (!orderVip.getAccountId().equals(account.getId())||!Order.STATUS_PAYED.equals(orderVip.getStatus())) {
                return new Response(Code.API_USER_ROLE_ERROR);
            }
        }
        if(orderUnders!=null) {
            if (!orderUnders.getAccountId().equals(account.getId())||!OrderUnders.STATUS_PAYED.equals(orderUnders.getStatus())) {
                return new Response(Code.API_USER_ROLE_ERROR);
            }
        }
        //判断是否保存过

        OrderInvoices oldOrder=invoicesService.selectByOrderId(orderInvoices.getOrderId());
        if(oldOrder!=null){
            if(!OrderInvoices.STATUS_ING.equals(oldOrder.getStatus())){
                return new Response(Code.API_ORDERINVOICESD);
            }
            oldOrder.setCompanyName(orderInvoices.getCompanyName());
            oldOrder.setAddress(orderInvoices.getAddress());
            oldOrder.setBank(orderInvoices.getBank());
            oldOrder.setBankCard(orderInvoices.getBankCard());
            oldOrder.setCompanyAddress(orderInvoices.getCompanyAddress());
            oldOrder.setCompanyPhone(orderInvoices.getCompanyPhone());
            oldOrder.setImg(orderInvoices.getImg());
            oldOrder.setName(orderInvoices.getName());
            oldOrder.setPhone(orderInvoices.getPhone());
            oldOrder.setTaxcode(orderInvoices.getTaxcode());
            invoicesService.save(oldOrder);
        }else{
            orderInvoices.setAccountId(account.getId());
            orderInvoices.setStatus(OrderInvoices.STATUS_ING);
            orderInvoices.setCompanyId(companyhrService.getByAccountId(account.getId()).getCompanyId());
            invoicesService.save(orderInvoices);
        }


        return new Response(Code.SUCCESS);
    }




    @RequestMapping(value = "/teachinPayNotify")
    public void pTeachin(HttpServletRequest request,HttpServletResponse response
    ) throws IOException {
        log.info("===================收到宣讲回调===================");
        interactionTeachinService.teachinPayNotify(request,response);
    }


    /**
     * 获取宣讲时间人数
     * @param request
     * @param teachinId
     * @return
     */
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public Response info(HttpServletRequest request,
                         @RequestParam String teachinId) {
        Map map=new HashMap<>();
        InteractionTeachin teachin=interactionTeachinService.get(teachinId);
        if(teachin!=null && InteractionTeachin.STATUS_ING.equals(teachin.getStatus())){
            map.put("time", "");
            map.put("number", SocketHandler.teachinMap.get(teachinId).size());
            return new Response(map);
        }

     return new Response(Code.API_TEACHIN_NOT_ACT);


    }
    /**
     * 获取聊天室内容
     * @param request
     * @param teachinId
     * @return
     */
    @RequestMapping(value = "/chat", method = RequestMethod.GET)
    public Response Gchat(HttpServletRequest request,
                         @RequestParam String teachinId,
                          @RequestParam(value = "page",defaultValue = "1") Integer page,
                          @RequestParam(value = "size",defaultValue = "10") Integer size
                          ) {
        InteractionTeachinChat chat=new InteractionTeachinChat();
        chat.setTeachinId(teachinId);
        chat.getPage().setOrderBy(" create_date");
        PageHelper.startPage(page,size);
        return new Response(new PageInfo<InteractionTeachinChat>(chatService.findList(chat)));
    }

    /**
     * 聊天室发言
     * @param request
     * @param teachinId
     * @param content
     * @return
     */
    @RequestMapping(value = "/chat", method = RequestMethod.POST)
    public Response chat(HttpServletRequest request,
                         @RequestParam String teachinId,
                         @RequestParam String content) {
        return new Response(chatService.chat(request,teachinId,content));
    }


    /**
     * 宣讲价格
     * @param request

     * @return
     */
    @RequestMapping(value = "/price", method = RequestMethod.GET)
    public Response price(HttpServletRequest request) {
        Map map=new HashMap();
        map.put("normal",800);
        map.put("vip",500);
        return new Response(map);
    }


}
