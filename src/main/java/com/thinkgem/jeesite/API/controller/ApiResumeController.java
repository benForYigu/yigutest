package com.thinkgem.jeesite.API.controller;

import com.thinkgem.jeesite.API.entity.Code;
import com.thinkgem.jeesite.API.entity.Response;
import com.thinkgem.jeesite.API.util.MyUtils;
import com.thinkgem.jeesite.API.util.NetEasy.NetEasyAPI;
import com.thinkgem.jeesite.API.util.TokenUtils;
import com.thinkgem.jeesite.modules.account.entity.Account;
import com.thinkgem.jeesite.modules.account.entity.accountstudent.AccountStudentinfo;
import com.thinkgem.jeesite.modules.account.service.AccountService;
import com.thinkgem.jeesite.modules.account.service.accountstudent.AccountStudentinfoService;
import com.thinkgem.jeesite.modules.resume.dao.active.StudentResumeSchoolActiveDao;
import com.thinkgem.jeesite.modules.resume.entity.active.StudentResumeSchoolActive;
import com.thinkgem.jeesite.modules.resume.entity.certificate.StudentResumeSchoolCertificate;
import com.thinkgem.jeesite.modules.resume.entity.education.StudentResumeEducation;
import com.thinkgem.jeesite.modules.resume.entity.experience.StudentResumeExperience;
import com.thinkgem.jeesite.modules.resume.entity.other.StudentResumeOther;
import com.thinkgem.jeesite.modules.resume.entity.prefer.StudentResumePrefer;
import com.thinkgem.jeesite.modules.resume.entity.schoolexperience.StudentResumeSchoolExperience;
import com.thinkgem.jeesite.modules.resume.service.active.StudentResumeSchoolActiveService;
import com.thinkgem.jeesite.modules.resume.service.certificate.StudentResumeSchoolCertificateService;
import com.thinkgem.jeesite.modules.resume.service.education.StudentResumeEducationService;
import com.thinkgem.jeesite.modules.resume.service.experience.StudentResumeExperienceService;
import com.thinkgem.jeesite.modules.resume.service.other.StudentResumeOtherService;
import com.thinkgem.jeesite.modules.resume.service.prefer.StudentResumePreferService;
import com.thinkgem.jeesite.modules.resume.service.schoolexperience.StudentResumeSchoolExperienceService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 简历操作
 */

@RestController
@RequestMapping("${apiPath}/resume")
public class ApiResumeController {

    @Autowired
    StudentResumeEducationService studentResumeEducationService;
    @Autowired
    StudentResumeExperienceService studentResumeExperienceService;
    @Autowired
    StudentResumeOtherService studentResumeOtherService;
    @Autowired
    StudentResumePreferService studentResumePreferService;
    @Autowired
    StudentResumeSchoolExperienceService schoolExperienceService;
    @Autowired
    AccountStudentinfoService studentinfoService;
    @Autowired
    StudentResumeSchoolActiveService activeService;
    @Autowired
    StudentResumeSchoolCertificateService certificateService;
    /**
     * 新增简历工作经历
     *
     * @param request
     * @param companyName
     * @param startDate
     * @param endDate
     * @return
     */
    @RequestMapping(value = "/experience", method = RequestMethod.POST)
    public Response experience(HttpServletRequest request,
                               @RequestParam String companyName,
                               @RequestParam String startDate,
                               @RequestParam String endDate,
                                String content
    ) {
        Account account = (Account) request.getAttribute("account");
        return new Response(studentResumeExperienceService.experience(account,  companyName,  startDate,  endDate,content));
    }
    /**
     * 获取简历工作经历
     *
     * @param request
     * @param experienceId
     * @return
     */
    @RequestMapping(value = "/experience", method = RequestMethod.GET)
    public Response gexperience(HttpServletRequest request,String experienceId) {
        Account account = (Account) request.getAttribute("account");
        return new Response(studentResumeExperienceService.gexperience(account,experienceId));
    }

    /**
     * 修改简历工作经历
     *
     * @param experienceId
     * @param companyName
     * @param startDate
     * @param endDate
     * @return
     */
    @RequestMapping(value = "/experience", method = RequestMethod.PUT)
    public Response uExperience(HttpServletRequest request,
                                @RequestParam  String experienceId,
                                @RequestParam String companyName,
                                @RequestParam String startDate,
                                @RequestParam String endDate,
                                 String content
                                ) {
        Account account = (Account) request.getAttribute("account");
        return new Response(studentResumeExperienceService.uExperience(account, experienceId,companyName,startDate,endDate,content));
    }

    /**
     * 删除简历工作经历
     *
     * @param experienceId
     * @return
     */
    @RequestMapping(value = "/experience", method = RequestMethod.DELETE)
    public Response uExperience(HttpServletRequest request,
                                @RequestParam  String experienceId) {
        Account account = (Account) request.getAttribute("account");
        StudentResumeExperience experience=studentResumeExperienceService.get(experienceId);
        if(experience!=null&&!experience.getStudentId().equals(account.getId())){
            return new Response(Code.API_EXPERIENCEID_ERROR);
        }
        studentResumeExperienceService.delete(experience);
        return new Response(Code.SUCCESS);
    }

    /**
     * 新增简历教育经历
     *
     * @param request
     * @param schoolName
     * @param startDate
     * @param endDate
     * @return
     */
    @RequestMapping(value = "/education", method = RequestMethod.POST)
    public Response education(HttpServletRequest request,
                              @RequestParam  String schoolName,
                              @RequestParam  String educationBackground,
                              @RequestParam  String major,
                              @RequestParam String startDate,
                              @RequestParam String endDate,
                               String content) {
        Account account = (Account) request.getAttribute("account");
        return new Response(studentResumeEducationService.education(account,  schoolName,educationBackground,major,  startDate,  endDate,content));
    }

    /**
     * 获取简历教育经历
     *
     * @param request
     * @param educationId
     * @return
     */
    @RequestMapping(value = "/education", method = RequestMethod.GET)
    public Response geducation(HttpServletRequest request,String educationId) {
        Account account = (Account) request.getAttribute("account");
        return new Response(studentResumeEducationService.geducation(account,educationId));
    }


    /**
     * 修改简历教育经历
     *
     * @param educationId
     * @param schoolName
     * @param startDate
     * @param endDate
     * @return
     */
    @RequestMapping(value = "/education", method = RequestMethod.PUT)
    public Response uEducation(HttpServletRequest request,
                               @RequestParam String educationId,
                               @RequestParam String schoolName,
                               @RequestParam  String educationBackground,
                               @RequestParam  String major,
                               @RequestParam String startDate,
                               String content,
                               @RequestParam String endDate) {
        Account account = (Account) request.getAttribute("account");
        return new Response(studentResumeEducationService.uEducation(account,  educationId,  schoolName,educationBackground,major,  startDate,  endDate, content));
    }

    /**
     * 删除简历教育经历
     *
     * @param educationId
     * @return
     */
    @RequestMapping(value = "/education", method = RequestMethod.DELETE)
    public Response uEducation(HttpServletRequest request,
                               @RequestParam String educationId) {
        Account account = (Account) request.getAttribute("account");
        StudentResumeEducation education=studentResumeEducationService.get(educationId);
        if(education!=null&&!education.getStudentId().equals(account.getId())){
            return new Response(Code.API_EDUCAATIONID_ERROR);
        }
        studentResumeEducationService.delete(education);
        return new Response(Code.SUCCESS);
    }

    /**
     * 新增工作意向
     * @param request
     * @param positionId   行业
     * @param professionId 职位
     * @param city
     * @param salary       1:全部  2:3k以下  3:3k-5k 4:5k-10k 5:10k-20k 6:20k-50k 7:50k以上
     * @return
     */
    @RequestMapping(value = "/prefer", method = RequestMethod.POST)
    public Response prefer(HttpServletRequest request,
                           @RequestParam String positionId,
                           @RequestParam String professionId,
                           @RequestParam String city,
                           @RequestParam String salary) {
        Account account = (Account) request.getAttribute("account");
        StudentResumePrefer prefer=new StudentResumePrefer();
        prefer.setStudentId(account.getId());
        if(studentResumePreferService.findList(prefer).size()>=3){
            return new Response(Code.API_PREFERID_TIME);
        }
        return new Response(studentResumePreferService.prefer(account,  positionId,  professionId,  city,  salary));
    }

    /**
     * 获取工作意向
     *
     * @param request
     * @param preferId
     * @return
     */
    @RequestMapping(value = "/prefer", method = RequestMethod.GET)
    public Response gprefer(HttpServletRequest request,String preferId) {
        Account account = (Account) request.getAttribute("account");
        return new Response(studentResumePreferService.gprefer(account,preferId));
    }

    /**
     * 修改工作意向
     *
     * @param preferId
     * @param positionId   行业
     * @param professionId 职位
     * @param city
     * @param salary       1:全部  2:3k以下  3:3k-5k 4:5k-10k 5:10k-20k 6:20k-50k 7:50k以上
     * @return
     */
    @RequestMapping(value = "/prefer", method = RequestMethod.PUT)
    public Response uPrefer(HttpServletRequest request, String preferId, String positionId, String professionId, String city, String salary) {
        Account account = (Account) request.getAttribute("account");
        return new Response(studentResumePreferService.uPrefer(account,  preferId,  positionId,  professionId,  city,  salary));
    }

    /**
     * 删除工作意向
     *
     * @param preferId
     * @return
     */
    @RequestMapping(value = "/prefer", method = RequestMethod.DELETE)
    public Response uPrefer(HttpServletRequest request, String preferId) {
        Account account = (Account) request.getAttribute("account");
        StudentResumePrefer prefer=studentResumePreferService.get(preferId);
        if(prefer!=null&&!prefer.getStudentId().equals(account.getId())){
            return new Response(Code.API_PREFERID_ERROR);
        }
        studentResumePreferService.delete(prefer);
        return new Response(Code.SUCCESS);
    }

    /**
     * 新增校内职务
     * @param request

     * @return
     */
    @RequestMapping(value = "/schoolExperience", method = RequestMethod.POST)
    public Response schoolExperience(HttpServletRequest request,
                                     @RequestParam String startDate,
                                     @RequestParam String endDate,
                                     @RequestParam String job,
                                     String jobDescribe) {
        Account account = (Account) request.getAttribute("account");
        return new Response(schoolExperienceService.schoolExperience(account,  startDate,  endDate,  job,  jobDescribe));
    }

    /**
     * 获取校园经历
     *
     * @param request
     * @param schoolExperienceId
     * @return
     */
    @RequestMapping(value = "/schoolExperience", method = RequestMethod.GET)
    public Response schoolExperience(HttpServletRequest request,String schoolExperienceId) {
        Account account = (Account) request.getAttribute("account");
        return new Response(schoolExperienceService.gschoolExperience(account,schoolExperienceId));
    }

    /**
     * 修改校园经历
     *
     * @param schoolExperienceId

     * @return
     */
    @RequestMapping(value = "/schoolExperience", method = RequestMethod.PUT)
    public Response schoolExperience(HttpServletRequest request,
                                     @RequestParam String schoolExperienceId,
                                     @RequestParam String startDate,
                                     @RequestParam String endDate,
                                     @RequestParam String job,
                                     String jobDescribe) {
        Account account = (Account) request.getAttribute("account");
        return new Response(schoolExperienceService.uSchoolExperience(account,  schoolExperienceId,  startDate,  endDate,  job,  jobDescribe));
    }

    /**
     * 删除校园经历
     *
     * @param schoolExperienceId
     * @return
     */
    @RequestMapping(value = "/schoolExperience", method = RequestMethod.DELETE)
    public Response dschoolExperience(HttpServletRequest request, String schoolExperienceId) {
        Account account = (Account) request.getAttribute("account");
        StudentResumeSchoolExperience schoolExperience=schoolExperienceService.get(schoolExperienceId);
        if(schoolExperience!=null&&!schoolExperience.getStudentId().equals(account.getId())){
            return new Response(Code.API_SCHOOLEXPERIENCE_ERROR);
        }
        schoolExperienceService.delete(schoolExperience);
        return new Response(Code.SUCCESS);
    }


    /**
     * 新增证书
     * @param request

     * @return
     */
    @RequestMapping(value = "/certificate", method = RequestMethod.POST)
    public Response certificate(HttpServletRequest request,
                                     @RequestParam String startDate,
                                     String endDate,
                                     @RequestParam String certificateName,
                                     @RequestParam String certificateType) {
        Account account = (Account) request.getAttribute("account");
        return new Response(certificateService.certificate(account,  startDate,  endDate,  certificateName,  certificateType));
    }

    /**
     * 获取证书
     *
     * @param request
     * @param certificateId
     * @return
     */
    @RequestMapping(value = "/certificate", method = RequestMethod.GET)
    public Response certificate(HttpServletRequest request,String certificateId) {
        Account account = (Account) request.getAttribute("account");
        return new Response(certificateService.gcertificate(account,certificateId));
    }

    /**
     * 修改证书
     *

     * @return
     */
    @RequestMapping(value = "/certificate", method = RequestMethod.PUT)
    public Response certificate(HttpServletRequest request,
                                     @RequestParam String certificateId,
                                @RequestParam String startDate,
                                String endDate,
                                @RequestParam String certificateName,
                                @RequestParam String certificateType) {
        Account account = (Account) request.getAttribute("account");
        return new Response(certificateService.ucertificate(account,  certificateId,  startDate,  endDate,  certificateName,  certificateType));
    }

    /**
     * 删除证书
     *

     * @return
     */
    @RequestMapping(value = "/certificate", method = RequestMethod.DELETE)
    public Response dcertificate(HttpServletRequest request, String certificateId) {
        Account account = (Account) request.getAttribute("account");
        StudentResumeSchoolCertificate certificate=certificateService.get(certificateId);
        if(certificate!=null&&!certificate.getStudentId().equals(account.getId())){
            return new Response(Code.API_SCHOOLEXPERIENCE_ERROR);
        }
        certificateService.delete(certificate);
        return new Response(Code.SUCCESS);
    }


    /**
     * 新增证书
     * @param request

     * @return
     */
    @RequestMapping(value = "/active", method = RequestMethod.POST)
    public Response active(HttpServletRequest request,
                                @RequestParam String startDate,
                                @RequestParam String endDate,
                                @RequestParam String name,
                                @RequestParam String content) {
        Account account = (Account) request.getAttribute("account");
        return new Response(activeService.active(account,startDate,endDate,name,content));
    }

    /**
     * 获取证书
     *
     * @param request
     * @param activeId
     * @return
     */
    @RequestMapping(value = "/active", method = RequestMethod.GET)
    public Response active(HttpServletRequest request, String activeId) {
        Account account = (Account) request.getAttribute("account");
        return new Response(activeService.gactive(account,activeId));
    }

    /**
     * 修改证书
     *


     * @return
     */
    @RequestMapping(value = "/active", method = RequestMethod.PUT)
    public Response active(HttpServletRequest request,
                                @RequestParam String activeId,
                           @RequestParam String startDate,
                           @RequestParam String endDate,
                           @RequestParam String name,
                           @RequestParam String content) {
        Account account = (Account) request.getAttribute("account");
        return new Response(activeService.uactive(account,  activeId,  startDate,  endDate,  name,  content));
    }

    /**
     * 删除证书
     *

     * @return
     */
    @RequestMapping(value = "/active", method = RequestMethod.DELETE)
    public Response dactive(HttpServletRequest request, String activeId) {
        Account account = (Account) request.getAttribute("account");
        StudentResumeSchoolActive active=activeService.get(activeId);
        if(active!=null&&!active.getStudentId().equals(account.getId())){
            return new Response(Code.API_SCHOOLEXPERIENCE_ERROR);
        }
        activeService.delete(active);
        return new Response(Code.SUCCESS);
    }


    /**
     * 获取技能和自我评价
     * @param request
     * @param type skill selfEvaluation
     * @return
     */

    @RequestMapping(value = "/{type}", method = RequestMethod.GET)
    public Response gother(HttpServletRequest request,@PathVariable("type") String type) {
        Account account = (Account) request.getAttribute("account");
        StudentResumeOther resumeOther=studentResumeOtherService.getByAccountId(account.getId());
        if("skill".equals(type)){
            return new Response(resumeOther!=null?resumeOther.getSkill():null);
        }
        if("selfEvaluation".equals(type)){
            return new Response(resumeOther!=null?resumeOther.getSelfEvaluation():null);
        }
        if("certificate".equals(type)){
            return new Response(resumeOther!=null?resumeOther.getCertificate():null);
        }
        return new Response(Code.API_PARRAM_ERROR);
    }


    /**
     * 更新技能和自我评价认证证书
     * @param request
     * @param skill 技能 逗号隔开
     * @param selfEvaluation 自我评价
     * @return
     */
    @RequestMapping(value = "/{type}", method = RequestMethod.PUT)
    public Response other(HttpServletRequest request,@PathVariable("type") String type,
                          String skill, String selfEvaluation,String certificate) {
        Account account = (Account) request.getAttribute("account");
        if(!"skill".equals(type)&&!"selfEvaluation".equals(type)&&!"certificate".equals(type)){
            return new Response(Code.API_PARRAM_ERROR);
        }
        return new Response(studentResumeOtherService.other(account,type, skill, selfEvaluation,certificate));
    }





    @RequestMapping(value = "/studentResume", method = RequestMethod.GET)
    public Response studentResume(HttpServletRequest request,@RequestParam String studentAccountId) {
        //获取简历意向
        StudentResumePrefer prefer=new StudentResumePrefer();
        prefer.setStudentId(studentAccountId);
        List<StudentResumePrefer> prefers= studentResumePreferService.findList(prefer);
        //获取简历工作经验
        StudentResumeExperience experience=new StudentResumeExperience();
        experience.setStudentId(studentAccountId);
        List<StudentResumeExperience> experiences= studentResumeExperienceService.findList(experience);
        //获取简历教育
        StudentResumeEducation education=new StudentResumeEducation();
        education.setStudentId(studentAccountId);
        List<StudentResumeEducation> educations= studentResumeEducationService.findList(education);
        //获取简历活动
        StudentResumeSchoolActive active=new StudentResumeSchoolActive();
        active.setStudentId(studentAccountId);
        List<StudentResumeSchoolActive> actives= activeService.findList(active);
        //获取简历证书
        StudentResumeSchoolCertificate certificate=new StudentResumeSchoolCertificate();
        certificate.setStudentId(studentAccountId);
        List<StudentResumeSchoolCertificate> certificates= certificateService.findList(certificate);
        //获取简历证书
        StudentResumeSchoolExperience schoolExperience=new StudentResumeSchoolExperience();
        schoolExperience.setStudentId(studentAccountId);
        List<StudentResumeSchoolExperience> schoolExperiences= schoolExperienceService.findList(schoolExperience);

        //获取简历学历
        StudentResumeOther other=new StudentResumeOther();
        other.setStudentId(studentAccountId);
        List<StudentResumeOther> others= studentResumeOtherService.findList(other);
        Map map=new HashMap();
        map.put("prefer", MyUtils.setLabelList(prefers));
        map.put("experience",MyUtils.setLabelList(experiences));
        map.put("education",MyUtils.setLabelList(educations));
        map.put("other",MyUtils.setLabelList(others));
        map.put("schoolExperience",schoolExperiences);
        map.put("active",actives);
        map.put("certificate",certificates);
        map.put("studentInfo",MyUtils.setLabel(studentinfoService.getByAccountId(studentAccountId)));
    return new Response(map);
    }

}

