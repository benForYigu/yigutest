package com.thinkgem.jeesite.API.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Strings;
import com.thinkgem.jeesite.API.entity.Code;
import com.thinkgem.jeesite.API.entity.Response;
import com.thinkgem.jeesite.API.util.NetEasy.NetEasyAPI;
import com.thinkgem.jeesite.API.util.TokenUtils;
import com.thinkgem.jeesite.modules.account.entity.Account;
import com.thinkgem.jeesite.modules.account.entity.accounthr.AccountCompanyhr;
import com.thinkgem.jeesite.modules.account.service.AccountService;
import com.thinkgem.jeesite.modules.account.service.accounthr.AccountCompanyhrService;
import com.thinkgem.jeesite.modules.account.service.accountstudent.AccountStudentinfoService;
import com.thinkgem.jeesite.modules.company.entity.Company;
import com.thinkgem.jeesite.modules.company.entity.address.CompanyAddress;
import com.thinkgem.jeesite.modules.company.entity.profession.CompanyProfession;
import com.thinkgem.jeesite.modules.company.service.CompanyService;
import com.thinkgem.jeesite.modules.company.service.address.CompanyAddressService;
import com.thinkgem.jeesite.modules.company.service.profession.CompanyProfessionService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.ParseException;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 企业操作
 */

@RestController
@RequestMapping("${apiPath}/company")
public class ApiCompanyController {
    private final Log log = LogFactory.getLog(getClass());

    @Autowired
    private AccountStudentinfoService accountStudentinfoService;
    @Autowired
    private AccountCompanyhrService accountCompanyhrService;
    @Autowired
    private CompanyService companyService;
    @Autowired
    private AccountService accountService;
    @Autowired
    CompanyProfessionService companyProfessionService;
    @Autowired
    CompanyAddressService addressService;
    @Value("${token.header}")
    private String tokenHeader ;
    @Autowired
    private TokenUtils tokenUtils;
    /**
     * 获取企业详情
     *
     * @param companyId
     * @return
     */
    @RequestMapping(value = "/company", method = RequestMethod.GET)
    public Response company(HttpServletRequest request, String companyId) {
        Account account = (Account) request.getAttribute("account");
        //企业用户
        if (Account.ROLE_COMPANY.equals(account.getRole())) {
            return new Response(companyService.get(accountCompanyhrService.getByAccountId(account.getId()).getCompanyId()));
        }
        //非企业用户
        if (Strings.isNullOrEmpty(companyId)) {
            return new Response(Code.API_COMPANYID_NULL);
        }
        Company company = companyService.get(companyId);
        if (company == null) {
            return new Response(Code.API_COMPANYID_NULL);
        } else {
            return new Response(company);
        }

    }
    /**
     * 更新企业信息
     *
     * @return
     */
    @RequestMapping(value = "/company", method = RequestMethod.PUT)
    public Response company(HttpServletRequest request,Company company) {
        String token=request.getHeader(tokenHeader);
        if(!Strings.isNullOrEmpty(token)){
            token=token.substring(7);
        }
        String id = tokenUtils.getUsernameFromToken(token);
        Account account=accountService.get(id);
        //Account account = (Account) request.getAttribute("account");

        return new Response(accountService.company(account,company));
    }

   /* *//**
     * 获取企业职位
     *
     * @return
     *//*
    @RequestMapping(value = "/profession", method = RequestMethod.GET)
    public Response profession(HttpServletRequest request,String  companyId) {
        Account account = (Account) request.getAttribute("account");
        Company company=companyService.get(companyId);
        if(company==null||!Company.AUTH_YES.equals(company.getStatus())){
            return new Response(null);
        }else{

            CompanyProfession companyProfession=new CompanyProfession();
            companyProfession.setCompanyId(companyId);
            return new Response(companyProfessionService.findList(companyProfession));
        }
    }*/



    /**
     * 收藏或取消收藏企业
     *
     * @param request
     * @param type    company profession
     * @param id
     * @return
     */
    @RequestMapping(value = "/collect/{type}", method = RequestMethod.PUT)
    public Response collect(HttpServletRequest request,
                            @RequestParam(value = "id", required = true) String id,
                            @PathVariable("type") String type) {
        Account account = (Account) request.getAttribute("account");
        return new Response(accountStudentinfoService.collect(account, id, type));
    }


    /**
     * 企业是否收藏
     *
     * @param type company profession
     * @param id
     * @return
     */
    @RequestMapping(value = "/collect/{type}", method = RequestMethod.GET)
    public Response company(HttpServletRequest request,
                            @RequestParam String id,
                            @PathVariable("type") String type
    ) {
        Account account = (Account) request.getAttribute("account");
        if ("company".equals(type)) {
            return new Response(("" + accountStudentinfoService.getByAccountId(account.getId()).getCollect()).indexOf(id + ",") > -1);
        }
        if ("profession".equals(type)) {
            return new Response(("" + accountStudentinfoService.getByAccountId(account.getId()).getProfessionCollect()).indexOf(id + ",") > -1);
        }
        return new Response(null);
    }

/*企业============*/
    /**
     * 发布职位
     * @param request
     * @param companyProfession
     * @return
     */
    @RequestMapping(value = "/profession", method = RequestMethod.POST)
    public Response profession(HttpServletRequest request,CompanyProfession companyProfession) {
        Account account = (Account) request.getAttribute("account");
        if(!Account.ROLE_COMPANY.equals(account.getRole())){
            return new Response(Code.API_USER_ROLE_ERROR);
        }

        if(Strings.isNullOrEmpty((String)companyProfession.getBoard())||
                Strings.isNullOrEmpty(companyProfession.getContent())||
                Strings.isNullOrEmpty((String)companyProfession.getEducationalBackground())||
                /*Strings.isNullOrEmpty((String)companyProfession.getExperience())||*/
               /* Strings.isNullOrEmpty((String) companyProfession.getMajor())||*/
                Strings.isNullOrEmpty(companyProfession.getName())||
                Strings.isNullOrEmpty((String)companyProfession.getNature())||
                Strings.isNullOrEmpty((String)companyProfession.getSalary())||
                Strings.isNullOrEmpty(companyProfession.getTag())||
               /* Strings.isNullOrEmpty((String)companyProfession.getTrafficSubsidy())||*/
                Strings.isNullOrEmpty((String)companyProfession.getCity())||

                Strings.isNullOrEmpty((String)companyProfession.getType())
                ){
            new Response(Code.API_PARRAM_ERROR);
        }
        String companyId=accountCompanyhrService.getByAccountId(account.getId()).getCompanyId();
        Company company=companyService.get(companyId);
        if(!Company.AUTH_YES.equals(company.getStatus())){
            return new Response(Code.API_COMPANY_AUTH);
        }
        companyProfession.setSource(CompanyProfession.SOURCE_SCHOOG);
        companyProfession.setSort(100);
        companyProfession.setHot("0");
        companyProfession.setCompanyId(companyId);
        companyProfession.setCompanyName(company.getName());
        companyProfessionService.save(companyProfession);
        return new Response(companyProfession);

    }
    /**
     * 更新职位
     * @param request
     * @param companyProfession
     * @return
     */
    @RequestMapping(value = "/profession", method = RequestMethod.PUT)
    public Response pprofession(HttpServletRequest request,CompanyProfession companyProfession) {
        Account account = (Account) request.getAttribute("account");
        if(!Account.ROLE_COMPANY.equals(account.getRole())){
            return new Response(Code.API_USER_ROLE_ERROR);
        }
        String companyId=accountCompanyhrService.getByAccountId(account.getId()).getCompanyId();
        CompanyProfession c=companyProfessionService.get(companyProfession.getId());
        if(c==null){
             return new Response(Code.API_PROFESSIONID_ERROR);
        }
        //判断是否为本企业
        if(!c.getCompanyId().equals(companyId)){
            return new Response(Code.API_USER_ROLE_ERROR);
        }

            c.setBoard(companyProfession.getBoard());

            c.setEducationalBackground(companyProfession.getEducationalBackground());

            c.setContent(companyProfession.getContent());

            //c.setExperience(companyProfession.getExperience());


            c.setMajor(companyProfession.getMajor());


            c.setSalary(companyProfession.getSalary());


            c.setTag(companyProfession.getTag());


            c.setNature(companyProfession.getNature());
            c.setArea(companyProfession.getArea());


            //c.setTrafficSubsidy(companyProfession.getTrafficSubsidy());
        companyProfessionService.save(c);
        return new Response(c);

       /* if(!Strings.isNullOrEmpty((String) companyProfession.getBoard())){
            c.setBoard(companyProfession.getBoard());
        }
        if(!Strings.isNullOrEmpty((String) companyProfession.getEducationalBackground())){
            c.setEducationalBackground(companyProfession.getEducationalBackground());
        }
        if(!Strings.isNullOrEmpty((String) companyProfession.getContent())){
            c.setContent(companyProfession.getContent());
        }
        if(!Strings.isNullOrEmpty((String) companyProfession.getExperience())){
            c.setExperience(companyProfession.getExperience());
        }
        if(!Strings.isNullOrEmpty((String) companyProfession.getMajor())){
            c.setMajor(companyProfession.getMajor());
        }
        if(!Strings.isNullOrEmpty((String) companyProfession.getSalary())){
            c.setSalary(companyProfession.getSalary());
        }
        if(!Strings.isNullOrEmpty(companyProfession.getTag())){
            c.setTag(companyProfession.getTag());
        }
        if(!Strings.isNullOrEmpty((String) companyProfession.getNature())){
            c.setNature(companyProfession.getNature());
        }
        if(!Strings.isNullOrEmpty((String) companyProfession.getTrafficSubsidy())){
            c.setTrafficSubsidy(companyProfession.getTrafficSubsidy());
        }
*/






    }
    /**
     * 查询职位
     * @param request
     * @return
     */
    @RequestMapping(value = "/profession", method = RequestMethod.GET)
    public Response Gprofession(HttpServletRequest request,String professionId,
                                @RequestParam(value="page" ,defaultValue = "1") Integer page,
                                @RequestParam(value="size" ,defaultValue = "10") Integer size) {
        Account account = (Account) request.getAttribute("account");



        if(Account.ROLE_COMPANY.equals(account.getRole())){
            if(!Strings.isNullOrEmpty(professionId)){
                CompanyProfession profession=companyProfessionService.get(professionId);
                if(profession==null||!profession.getCompanyId().equals(accountCompanyhrService.getByAccountId(account.getId()).getCompanyId())){
                    return new Response(Code.API_USER_ROLE_ERROR);
                }
                return new Response(profession);
            }else{
                String companyId=accountCompanyhrService.getByAccountId(account.getId()).getCompanyId();
                CompanyProfession profession =new CompanyProfession();
                profession.setCompanyId(companyId);
                profession.getPage().setOrderBy(" a.update_date desc");
                PageHelper.startPage(page,size);
                return new Response(new PageInfo<CompanyProfession>(companyProfessionService.findList(profession)));

            }
        }else{
            String companyId=accountCompanyhrService.getByAccountId(account.getId()).getCompanyId();
            Company company=companyService.get(companyId);
            if(company==null||!Company.AUTH_YES.equals(company.getStatus())){
                return new Response(null);
            }else{
                CompanyProfession companyProfession=new CompanyProfession();
                companyProfession.setCompanyId(companyId);
                companyProfession.getPage().setOrderBy(" a.update_date desc");
                return new Response(companyProfessionService.findList(companyProfession));
            }
        }
    }
    /**
     * 新增更新地址
     * @param request
     * @param address
     * @return
     */
    @RequestMapping(value = "/address", method = RequestMethod.POST)
    public Response address(HttpServletRequest request,CompanyAddress address) {
        Account account = (Account) request.getAttribute("account");
        if(!Account.ROLE_COMPANY.equals(account.getRole())){
            return new Response(Code.API_USER_ROLE_ERROR);
        }
        String companyId=accountCompanyhrService.getByAccountId(account.getId()).getCompanyId();
        //修改
        if(!Strings.isNullOrEmpty(address.getId())){
            CompanyAddress a=addressService.get(address.getId());
            if(!a.getCompanyId().equals(companyId)){
                address.setId(null);
            }
        }
        address.setCompanyId(companyId);
        addressService.save(address);
        return new Response(Code.SUCCESS);
    }
    /**
     * 获取地址
     * @param request

     * @return
     */
    @RequestMapping(value = "/address", method = RequestMethod.GET)
    public Response gaddress(HttpServletRequest request,String addressId) {
        Account account = (Account) request.getAttribute("account");
        String companyId=accountCompanyhrService.getByAccountId(account.getId()).getCompanyId();
       if(Strings.isNullOrEmpty(addressId)){
           CompanyAddress address=new CompanyAddress();
           address.setCompanyId(companyId);
           return new Response( addressService.findList(address));
       }else{
           return new Response( addressService.get(addressId));
       }

    }



}

