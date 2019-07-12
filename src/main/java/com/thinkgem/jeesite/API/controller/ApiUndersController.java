package com.thinkgem.jeesite.API.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Strings;
import com.thinkgem.jeesite.API.entity.Code;
import com.thinkgem.jeesite.API.entity.Response;
import com.thinkgem.jeesite.API.entity.SocketHandler;
import com.thinkgem.jeesite.API.util.MyUtils;
import com.thinkgem.jeesite.common.beanvalidator.BeanValidators;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.modules.account.entity.Account;
import com.thinkgem.jeesite.modules.account.entity.accounthr.AccountCompanyhr;
import com.thinkgem.jeesite.modules.account.entity.accountstudent.AccountStudentinfo;
import com.thinkgem.jeesite.modules.account.service.accounthr.AccountCompanyhrService;
import com.thinkgem.jeesite.modules.account.service.accountstudent.AccountStudentinfoService;
import com.thinkgem.jeesite.modules.company.entity.Company;
import com.thinkgem.jeesite.modules.company.service.CompanyService;
import com.thinkgem.jeesite.modules.interaction.entity.teachin.InteractionTeachin;
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
import com.thinkgem.jeesite.modules.unders.entity.companyfile.FakeUndersCompanyFile;
import com.thinkgem.jeesite.modules.unders.entity.companyfile.UndersCompanyFile;
import com.thinkgem.jeesite.modules.unders.entity.sign.UndersSign;
import com.thinkgem.jeesite.modules.unders.entity.unders.Unders;
import com.thinkgem.jeesite.modules.unders.service.companyfile.UndersCompanyFileService;
import com.thinkgem.jeesite.modules.unders.service.sign.UndersSignService;
import com.thinkgem.jeesite.modules.unders.service.unders.UndersService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import java.io.IOException;
import java.util.*;

/**
 * 线下活动
 */

@RestController
@RequestMapping("${apiPath}/unders")
public class ApiUndersController {
    private final Log log = LogFactory.getLog(getClass());






    @Autowired
    private UndersService undersService;
    @Autowired
    private UndersSignService signService;
    @Autowired
    private AccountCompanyhrService companyhrService;
    @Autowired
    private AccountStudentinfoService studentinfoService;
    @Autowired
    private UndersCompanyFileService fileService;
    @Autowired
    private CompanyService companyService;
    @Autowired
    private OrderUndersService orderUndersService;

    @Autowired
    OrderService orderService;
    @Autowired
    protected Validator validator;

    /**
     * 获取双选会列表 详情
     * @param request
     * @param page
     * @param size
     * @return
     */
    @RequestMapping(value = "/unders", method = RequestMethod.GET)
    public Response unders(HttpServletRequest request,String undersId,String title,
                            @RequestParam(value="page" ,defaultValue = "1") Integer page,
                            @RequestParam(value="size" ,defaultValue = "10") Integer size)  {
        Account account=(Account)request.getAttribute("account");
        return new Response(undersService.unders(account,undersId,title,page,size));
    }
    /**
     * 学生获取双选会企业列表
     * @param request

     * @return
     */
    @RequestMapping(value = "/undersCompany", method = RequestMethod.GET)
    public Response undersCompany(HttpServletRequest request,@RequestParam String undersId,String name)  {
        Account account=(Account)request.getAttribute("account");
       if(!Account.ROLE_STUDENT.equals(account.getRole())){
           return new Response(Code.API_USER_ROLE_ERROR);
       }
        AccountStudentinfo studentinfo=studentinfoService.getByAccountId(account.getId());
       List<Company> list=companyService.findCompanyBySchool(studentinfo.getSchoolId(),undersId,name);
        return new Response(MyUtils.companySort(list));
    }


    /**
     * 报名双选会
     * @param request
     * @param type 2对公支付
     * @return
     */
    @RequestMapping(value = "/sign", method = RequestMethod.POST)
    public Response sign(HttpServletRequest request,
                         @RequestParam  String undersBoothId,
                         String type)  {
        Account account=(Account)request.getAttribute("account");
        return new Response(signService.sign(request,account,undersBoothId,type));
    }

    /**
     * 企业报名资料添加或修改
     * @param request
     * @return
     */
    @RequestMapping(value = "/companyFile", method = RequestMethod.POST)
    public Response companyFile(HttpServletRequest request,FakeUndersCompanyFile fakeUndersCompanyFile)  {
        Account account=(Account)request.getAttribute("account");
        AccountCompanyhr companyhr=companyhrService.getByAccountId(account.getId());
        UndersCompanyFile companyFile=fileService.selectByCompanyId(companyhr.getCompanyId());
         if(companyFile==null){
             companyFile=  new UndersCompanyFile();
         }
        companyFile.setCompanyId(companyhr.getCompanyId());
        companyFile.setCompanyName(fakeUndersCompanyFile.getCompanyName());
        companyFile.setContact1(fakeUndersCompanyFile.getContact1());
        companyFile.setContact2(fakeUndersCompanyFile.getContact2());
        companyFile.setPhone1(fakeUndersCompanyFile.getPhone1());
        companyFile.setPhone2(fakeUndersCompanyFile.getPhone2());
        companyFile.setContent(fakeUndersCompanyFile.getContent());
        companyFile.setEmail(fakeUndersCompanyFile.getEmail());
        companyFile.setLicense(fakeUndersCompanyFile.getLicense());

        try{
            BeanValidators.validateWithException(validator, companyFile);
        }catch(ConstraintViolationException ex){
            List<String> list = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
            return new Response(Code.API_PARRAM_ERROR,list.toArray(new String[]{}));
        }

        try {
            fileService.save(companyFile);
        }catch (Exception e){
            return new Response(Code.API_PARRAM_ERROR,"缺失id");
        }
        return new Response(Code.SUCCESS);
    }
    /**
     * 企业报名资料添加或修改
     * @param request
     * @return
     */
    @RequestMapping(value = "/companyFile", method = RequestMethod.GET)
    public Response GcompanyFile(HttpServletRequest request)  {
        Account account=(Account)request.getAttribute("account");
        AccountCompanyhr companyhr=companyhrService.getByAccountId(account.getId());
        if(companyhr==null){
            return new Response(Code.API_USER_ROLE_ERROR);
        }
        UndersCompanyFile companyFile=fileService.selectByCompanyId(companyhr.getCompanyId());
        return new Response(companyFile);
    }

    @RequestMapping(value = "/notify")
    public void notify(HttpServletRequest request,HttpServletResponse response) throws IOException {
        log.info("============signService=======收到VIP回调===================");
        signService.paynotify(request,response);
    }


    /**
     * 双选会订单
     * @param request
     * @return
     */
    @RequestMapping(value = "/order", method = RequestMethod.GET)
    public Response order(HttpServletRequest request,
                          @RequestParam(value="page" ,defaultValue = "1") Integer page,
                          @RequestParam(value="size" ,defaultValue = "10") Integer size)  {
        Account account=(Account)request.getAttribute("account");
        OrderUnders orderUnders=new OrderUnders();
        orderUnders.setAccountId(account.getId());
        PageHelper.startPage(page,size);
        List<OrderUnders> list= orderUndersService.findList(orderUnders);
        return new Response(new PageInfo<OrderUnders>(list));
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
     * 双选会订单
     * @param request
     * @return
     */
    @RequestMapping(value = "/otherOrders", method = RequestMethod.GET)
    public Response otherOrders(HttpServletRequest request,
                          @RequestParam(value="page" ,defaultValue = "1") Integer page,
                          @RequestParam(value="size" ,defaultValue = "10") Integer size)  {
        Account account=(Account)request.getAttribute("account");
        PageHelper.startPage(page,size);
        List<Map<String,Object>> list= orderUndersService.otherOrders(account.getId());
        return new Response(new PageInfo<Map<String,Object>>(list));
    }
}
