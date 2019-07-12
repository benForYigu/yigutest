package com.thinkgem.jeesite.API.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Strings;
import com.thinkgem.jeesite.API.entity.Code;
import com.thinkgem.jeesite.API.entity.Response;
import com.thinkgem.jeesite.API.util.NetEasy.NetEasyAPI;
import com.thinkgem.jeesite.API.util.TokenUtils;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.modules.account.dao.accountstudent.AccountStudentinfoDao;
import com.thinkgem.jeesite.modules.account.entity.Account;
import com.thinkgem.jeesite.modules.account.entity.accountstudent.AccountStudentinfo;
import com.thinkgem.jeesite.modules.account.service.AccountService;
import com.thinkgem.jeesite.modules.account.service.accountstudent.AccountStudentinfoService;
import com.thinkgem.jeesite.modules.company.dao.profession.CompanyProfessionDao;
import com.thinkgem.jeesite.modules.company.entity.profession.CompanyProfession;
import com.thinkgem.jeesite.modules.company.service.profession.CompanyProfessionService;
import com.thinkgem.jeesite.modules.interaction.entity.recommend.InteractionRecommend;
import com.thinkgem.jeesite.modules.interaction.service.recommend.InteractionRecommendService;
import com.thinkgem.jeesite.modules.recommend.entity.RecommendUnder;
import com.thinkgem.jeesite.modules.recommend.service.RecommendUnderService;
import com.thinkgem.jeesite.modules.school.service.SchoolService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.ParseException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 推荐位
 */

@RestController
@RequestMapping("${apiPath}/recommend")
public class ApiRecommendController {
    private final Log log = LogFactory.getLog(getClass());



    @Autowired
    private InteractionRecommendService interactionRecommendService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private CompanyProfessionService professionService;
    @Autowired
    private RecommendUnderService underService;
    @Autowired
    private SchoolService schoolService;
    @Autowired
    private TokenUtils tokenUtils;

    @Value("${token.header}")
    private String tokenHeader ;
    /**
     * 首页推荐位
     * @param request
     * @param recommendId 单个推荐详情id
     * @return
     */
    @RequestMapping(value = "/recommend", method = RequestMethod.GET)
    public Response recommend(HttpServletRequest request,String recommendId,
                              @RequestParam(required = false,defaultValue = "1") String type){
        String token=request.getHeader(tokenHeader);
        if(!Strings.isNullOrEmpty(token)&&token.length()>7){
            token=token.substring(7);
        }
        String id = tokenUtils.getUsernameFromToken(token);
        Account account=accountService.get(id);
        if(account==null){
            account=new Account();
            account.setRole(Account.ROLE_COMPANY);
        }
        return new Response(interactionRecommendService.recommend(account,recommendId,type));

    }

    /**
     * 首页热门岗位
     * @param professionId
     * @param type 1查询热门
     * @param professionName 职位名称
     * @param sort 1推荐排序 2更新时间排序
     * @param city 城市
     * @param professionType 职位类型
     * @param salary 薪水
     * @param educationalBackground 教育背景
     * @param page
     * @param size
     * @return
     */
    @RequestMapping(value = "/hotProfession", method = RequestMethod.GET)
    public Response hotProfession(String professionId,String type,String professionName,
                                  @RequestParam(required = false,defaultValue = "2") String sort,String city,
                                  String professionType,
                                  String salary,
                                  String educationalBackground,
                                  @RequestParam(value="page" ,defaultValue = "1") Integer page,
                                  @RequestParam(value="size" ,defaultValue = "10") Integer size){
        if(Strings.isNullOrEmpty(professionId)){
            CompanyProfession profession=new CompanyProfession();
            //profession.setSource(CompanyProfession.SOURCE_OUT);
            profession.setName(professionName);
            profession.setCity(city);
            profession.setType(professionType);
            profession.setSalary(salary);
            profession.setEducationalBackground(educationalBackground);
            if("1".equals(type)){
                profession.setHot("1");
            }


            //更新时间
            if("2".equals(sort)){
                profession.getPage().setOrderBy(" a.create_date desc ");
            }else{
                profession.getPage().setOrderBy(" a.sort asc,a.create_date desc");
            }
            /*//推荐
            if("1".equals(sort)){
                profession.getPage().setOrderBy(" a.sort asc,a.create_date desc");
            }*/
            PageHelper.startPage(page,size);
            List<CompanyProfession> list=professionService.findList(profession);
             return new Response(new PageInfo<CompanyProfession>(list));
        }
        CompanyProfession profession=professionService.get(professionId);
       /* if(profession.getSource().equals(CompanyProfession.SOURCE_SCHOOG)){
            return new Response(Code.API_PARRAM_ERROR);
        }*/
        return new Response(profession);

    }

    /**
     * 查看线下宣讲会 线下双选会

     * @param type 1 线下宣讲会  2线下双选会
     * @return
     */
    @RequestMapping(value = "/under", method = RequestMethod.GET)
    public Response under(@RequestParam String type){
        RecommendUnder under=new RecommendUnder();
        under.setType(type);
       return new Response(underService.findList(under));

    }
    /**
     * 线下宣讲和选下双选会是否报名
     * @param id
     * @return
     */
    @RequestMapping(value = "/isEnroll", method = RequestMethod.GET)
    public Response<Boolean> isEnroll(HttpServletRequest request,@RequestParam String id){
        Account account=(Account)request.getAttribute("account");
       return new Response(underService.isEnroll(account,id));
    }
    /**
     * 报名线下宣讲和选下双选会
     * @param id
     * @return
     */
    @RequestMapping(value = "/enroll", method = RequestMethod.POST)
    public Response enroll(HttpServletRequest request,@RequestParam String id){
        Account account=(Account)request.getAttribute("account");
       return new Response(underService.enroll(account,id));

    }

    /**
     * 学校公告
     * @param noticeId
     * @return
     */
    @RequestMapping(value = "/notice", method = RequestMethod.GET)
    public Response notice(HttpServletRequest request,String noticeId,
                           @RequestParam(value="page" ,defaultValue = "1") Integer page,
                           @RequestParam(value="size" ,defaultValue = "10") Integer size){
        Account account=(Account)request.getAttribute("account");
        if(!account.getRole().equals(Account.ROLE_STUDENT)&&!account.getRole().equals(Account.ROLE_SCHOOL)){
            return new Response(Code.API_USER_ROLE_ERROR);
        }
       return new Response(schoolService.notice(account,noticeId,page,size));

    }

}

