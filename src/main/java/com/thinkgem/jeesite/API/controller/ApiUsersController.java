package com.thinkgem.jeesite.API.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Strings;
import com.thinkgem.jeesite.API.entity.Code;
import com.thinkgem.jeesite.API.entity.Response;
import com.thinkgem.jeesite.API.util.MyUtils;
import com.thinkgem.jeesite.API.util.NetEasy.NetEasyAPI;
import com.thinkgem.jeesite.API.util.TokenUtils;
import com.thinkgem.jeesite.API.util.UpEncodeUtils;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.IdGen;
import com.thinkgem.jeesite.common.utils.UploadUtils;
import com.thinkgem.jeesite.modules.account.dao.accountstudent.AccountStudentinfoDao;
import com.thinkgem.jeesite.modules.account.entity.Account;
import com.thinkgem.jeesite.modules.account.entity.accounthr.AccountCompanyhr;
import com.thinkgem.jeesite.modules.account.entity.accountschool.AccountTeacherinfo;
import com.thinkgem.jeesite.modules.account.entity.accountstudent.AccountStudentinfo;
import com.thinkgem.jeesite.modules.account.service.AccountService;
import com.thinkgem.jeesite.modules.account.service.accounthr.AccountCompanyhrService;
import com.thinkgem.jeesite.modules.account.service.accountschool.AccountTeacherinfoService;
import com.thinkgem.jeesite.modules.account.service.accountstudent.AccountStudentinfoService;
import com.thinkgem.jeesite.modules.company.entity.Company;
import com.thinkgem.jeesite.modules.company.entity.profession.CompanyProfession;
import com.thinkgem.jeesite.modules.company.service.CompanyService;
import com.thinkgem.jeesite.modules.company.service.profession.CompanyProfessionService;
import com.thinkgem.jeesite.modules.interaction.entity.offer.InteractionOffer;
import com.thinkgem.jeesite.modules.interaction.entity.recommend.InteractionRecommend;
import com.thinkgem.jeesite.modules.interaction.entity.teachin.InteractionTeachin;
import com.thinkgem.jeesite.modules.interaction.service.recommend.InteractionRecommendService;
import com.thinkgem.jeesite.modules.message.entity.Message;
import com.thinkgem.jeesite.modules.message.service.MessageService;
import com.thinkgem.jeesite.modules.resume.entity.education.StudentResumeEducation;
import com.thinkgem.jeesite.modules.resume.entity.experience.StudentResumeExperience;
import com.thinkgem.jeesite.modules.resume.entity.other.StudentResumeOther;
import com.thinkgem.jeesite.modules.resume.entity.prefer.StudentResumePrefer;
import com.thinkgem.jeesite.modules.resume.service.education.StudentResumeEducationService;
import com.thinkgem.jeesite.modules.resume.service.experience.StudentResumeExperienceService;
import com.thinkgem.jeesite.modules.resume.service.other.StudentResumeOtherService;
import com.thinkgem.jeesite.modules.resume.service.prefer.StudentResumePreferService;
import com.thinkgem.jeesite.modules.school.entity.School;
import com.thinkgem.jeesite.modules.school.service.SchoolService;
import com.thinkgem.jeesite.modules.sys.dao.UserDao;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.service.SystemService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.bouncycastle.crypto.digests.MD5Digest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import sun.security.provider.MD5;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 客户操作
 */

@RestController
@RequestMapping("${apiPath}/user")
public class ApiUsersController {
    private final Log log = LogFactory.getLog(getClass());
    @Autowired
    AccountService accountService;
    @Autowired
    UserDao userDao;
    @Autowired
    CompanyService companyService;
    @Autowired
    AccountStudentinfoService accountStudentinfoService;
    @Autowired
    AccountCompanyhrService accountCompanyhrService;
    @Autowired
    AccountTeacherinfoService accountTeacherinfoService;
    @Autowired
    SchoolService schoolService;
    @Autowired
    InteractionRecommendService recommendService;
    @Autowired
    MessageService messageService;


    @Value("${token.header}")
    private String tokenHeader ;
    @Autowired
    private TokenUtils tokenUtils;
    @Value("${otherPic}")
    private String otherPic;

    @RequestMapping(value = "/studentRegisterStepTwo", method = RequestMethod.PUT)
    public Response studentRegisterStepTwo(HttpServletRequest request ,
                                           @RequestParam(value ="schoolId" ) String schoolId,
                                           @RequestParam(value ="realname") String realname,
                                           @RequestParam(value ="avatar") String avatar,
                                           @RequestParam(value ="educationBackground") String educationBackground,
                                           @RequestParam(value ="major") String major,
                                           @RequestParam(value ="sex") String sex,
                                           @RequestParam(value ="birthday") String birthday,
                                           @RequestParam(value ="graduationTime") String graduationTime,
                                           @RequestParam(value ="studentId") String studentId) {
        Account account = (Account) request.getAttribute("account");
        AccountStudentinfo student=accountStudentinfoService.getByAccountId(account.getId());

        if(!Strings.isNullOrEmpty(student.getSchoolId())){
            return new Response(Code.API_ACCOUNT_COMPLETE);
        }
        if (!Strings.isNullOrEmpty(schoolId) && schoolService.get(schoolId) == null) {
            return new Response(Code.API_SCHOOL_NOT_EXIST);
        }

        student.setAvatar(avatar);
        student.setRealname(realname);
        student.setSchoolId(schoolId);
        student.setEducationBackground(educationBackground);
        student.setMajor(major);
        student.setSex(sex);
        student.setBirthday(DateUtils.parseDate(birthday));
        student.setGraduationTime(DateUtils.parseDate(graduationTime));
        student.setStudentId(studentId);
        accountStudentinfoService.save(student);
        return new Response(Code.SUCCESS);

    }




    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public Response gUser(HttpServletRequest request) {
        Account account = (Account) request.getAttribute("account");
        if(Account.ROLE_STUDENT.equals(account.getRole())){
            AccountStudentinfo studentinfo=accountStudentinfoService.getByAccountId(account.getId());
            if(Strings.isNullOrEmpty(studentinfo.getSchoolId())){
                return new Response(Code.API_STUDENT_INFO);
            }
            return new Response(studentinfo);
        }else if(Account.ROLE_COMPANY.equals(account.getRole())){
            return new Response(accountCompanyhrService.getByAccountId(account.getId()));
        }else{
            return new Response(accountTeacherinfoService.getByAccountId(account.getId()));
        }
/*
        if(Account.ROLE_STUDENT.equals(account.getRole())){
            return new Response(accountStudentinfoService.get(accountStudentinfoService.getByAccountId(account.getId()).getId()));
        }else if(Account.ROLE_COMPANY.equals(account.getRole())){
            return new Response(accountCompanyhrService.get(accountCompanyhrService.getByAccountId(account.getId()).getId()));
        }else{
            return new Response(accountTeacherinfoService.get(accountTeacherinfoService.getByAccountId(account.getId()).getId()));
        }
*/

    }

    /**
     * 更新用户新息
     *
     * @param request
     * @param realname
     * @param schoolId
     * @param department          院系
     * @param educationBackground 教育背景
     * @param major               专业
     * @param sex
     * @param birthday
     * @param graduationTime      毕业时间
     * @param email
     * @return
     */
    @RequestMapping(value = "/user", method = RequestMethod.PUT)
    public Response user(HttpServletRequest request, String avatar, String realname, String schoolId, String department,
                         String educationBackground, String major, String sex, String birthday, String graduationTime,
                         String email, String studentId) {
        String token=request.getHeader(tokenHeader);
        if(!Strings.isNullOrEmpty(token)){
            token=token.substring(7);
        }
        String id = tokenUtils.getUsernameFromToken(token);
        Account account=accountService.get(id);

        return new Response(accountService.user(account,avatar, realname, schoolId,department,educationBackground,major,sex,birthday,graduationTime,email,studentId));
    }

    /**
     * 更新用户密码
     *
     * @param request
     * @param oldPassword
     * @param newPassword
     * @return
     */
    @RequestMapping(value = "/password", method = RequestMethod.PUT)
    public Response password(HttpServletRequest request,
                             @RequestParam String oldPassword,
                             @RequestParam String newPassword) {
        Account account = (Account) request.getAttribute("account");
        if(newPassword.length()<8){
            return new Response(Code.API_PASSWORD_LENGTH_ERROR);
        }
        if (UpEncodeUtils.md5(oldPassword).equals(account.getPassword())) {
            account.setPassword(UpEncodeUtils.md5(newPassword));
            accountService.save(account);

            //更新后台密码
            User user=userDao.get(account.getId());
            user.setPassword(SystemService.entryptPassword(newPassword));
            user.preUpdate();
            userDao.update(user);

            return new Response(Code.SUCCESS);
        } else {
            return new Response(Code.API_OLDPASSWORD_ERROR);
        }

    }

    /**
     * 收藏的企业/职位列表
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/collect/{type}", method = RequestMethod.GET)
    public Response gCollect(HttpServletRequest request,
                             @PathVariable("type")String type,
                             @RequestParam(value="page" ,defaultValue = "1") Integer page,
                             @RequestParam(value="size" ,defaultValue = "10") Integer size) {
        Account account = (Account) request.getAttribute("account");
        return new Response(accountStudentinfoService.gCollect(account,type,page,size));
    }


    /**
     * 关于我们
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/aboutUs", method = RequestMethod.GET)
    public Response aboutUs(HttpServletRequest request){
        Account account = (Account) request.getAttribute("account");
        InteractionRecommend recommend=new InteractionRecommend();
        if(Account.ROLE_COMPANY.equals(account.getRole())){
            recommend.setSuportCompany("1");
        }else{
            recommend.setSuportCompany("0");
        }
        recommend.setType(InteractionRecommend.TYPE_ABOUT);
        List list=recommendService.findList(recommend);
        if(list.size()==0){
            return new Response(null);
        }
        return new Response(list.get(0));

    }



    /**
     * 消息记录
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/message", method = RequestMethod.GET)
    public Response message(HttpServletRequest request,
                            @RequestParam(value="page" ,defaultValue = "1") Integer page,
                            @RequestParam(value="size" ,defaultValue = "10") Integer size){
        Account account = (Account) request.getAttribute("account");

        return new Response(new PageInfo<Message>(messageService.message(account,page,size)));

    }








}

