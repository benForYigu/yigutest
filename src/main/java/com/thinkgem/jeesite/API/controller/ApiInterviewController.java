package com.thinkgem.jeesite.API.controller;

import com.google.common.base.Strings;
import com.thinkgem.jeesite.API.entity.Code;
import com.thinkgem.jeesite.API.entity.Response;
import com.thinkgem.jeesite.modules.account.entity.Account;
import com.thinkgem.jeesite.modules.interaction.entity.interview.InteractionInterview;
import com.thinkgem.jeesite.modules.interaction.interview.entity.comment.InteractionInterviewComment;
import com.thinkgem.jeesite.modules.interaction.interview.service.comment.InteractionInterviewCommentService;
import com.thinkgem.jeesite.modules.interaction.service.interview.InteractionInterviewService;

import com.thinkgem.jeesite.modules.messagelog.entity.MessageLog;
import com.thinkgem.jeesite.modules.messagelog.service.MessageLogService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Max;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 面试
 */

@RestController
@RequestMapping("${apiPath}/interview")
public class ApiInterviewController {
    private final Log log = LogFactory.getLog(getClass());

    @Autowired
    private InteractionInterviewService interactionInterviewService;
    @Autowired
    private MessageLogService messageLogService;
    @Autowired
    private InteractionInterviewCommentService interactionInterviewCommentService;

    private static Map<String, String> tokenMap = new LinkedHashMap<String, String>();


    /**
     * 根据account获取面试列表或详情
     *
     * @param request
     * @param status  面试状态 1投递中2待面试 3协商中 4结束
     * @param page
     * @param size
     * @return
     */
    @RequestMapping(value = "/interview", method = RequestMethod.GET)
    public Response teachin(HttpServletRequest request, String status, String interviewId,
                            @RequestParam(value = "page", defaultValue = "1") Integer page,
                            @RequestParam(value = "size", defaultValue = "10") Integer size) {
        Account account = (Account) request.getAttribute("account");
        return new Response(interactionInterviewService.Interview(account, status, interviewId, page, size));
    }

    /**
     * 获取面试消息列表和详情
     *
     * @param request
     * @param messageId
     * @param page
     * @param size
     * @return
     */
    @RequestMapping(value = "/message", method = RequestMethod.GET)
    public Response message(HttpServletRequest request, String messageId,String interviewId,
                            @RequestParam(value = "page", defaultValue = "1") Integer page,
                            @RequestParam(value = "size", defaultValue = "10") Integer size) {
        Account account = (Account) request.getAttribute("account");
       return new Response(messageLogService.message(account,messageId,interviewId,page,size));
    }


    /**
     * 简历是否完善
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/resumeStatus", method = RequestMethod.GET)
    public Response resumeStatus(HttpServletRequest request) {
        Account account = (Account) request.getAttribute("account");
        if (!Account.ROLE_STUDENT.equals(account.getRole())) {
            return new Response(Code.API_USER_ROLE_ERROR);
        }
        return new Response(interactionInterviewService.resumeStatus(account));
    }

    /**
     * 面试评论
     * @param charm  魅力度 数字0-5
     * @param   affinity  亲和度 数字0-5
     * @param   quality  素质度 数字0-5
     * @param positive 积极度 数字0-5
     * @param culture 文化度 数字0-5
     * @param consciousness  意识度 数字0-5
     * @param interest  兴趣度 数字0-5
     * @param trust  信赖度 数字0-5
     * @param integrate 融入度 数字0-5
     * @return
     */
    @RequestMapping(value = "/comment", method = RequestMethod.POST)
    public Response comment(HttpServletRequest request, @RequestParam String interviewId,
                            @RequestParam Integer charm,
                            @RequestParam Integer affinity,
                            @RequestParam Integer quality,
                            @RequestParam Integer positive,
                            @RequestParam Integer culture,
                            @RequestParam Integer consciousness,
                            @RequestParam Integer interest,
                            @RequestParam Integer trust,
                            @RequestParam Integer integrate) {
        Account account = (Account) request.getAttribute("account");

        return new Response(interactionInterviewCommentService.comment(account,interviewId,charm,
                affinity,quality,positive,culture,consciousness,interest,trust,integrate));
    }


    /**
     * 面试点评记录
     * @param request
     * @param interviewId
     * @return
     */
    @RequestMapping(value = "/comment", method = RequestMethod.GET)
    public Response gcomment(HttpServletRequest request,@RequestParam String interviewId) {
        Account account = (Account) request.getAttribute("account");
        return new Response(interactionInterviewCommentService.getComment(account,interviewId));
    }

    /**
     * 同意面试
     * @param request
     * @param interviewId
     * @return
     */
    @RequestMapping(value = "/agree", method = RequestMethod.PUT)
    public Response agree(HttpServletRequest request,@RequestParam String interviewId) {
        Account account = (Account) request.getAttribute("account");
        return new Response(interactionInterviewService.agree(account,interviewId));
    }

     /**
     * 面试完成
     * @param request
     * @param interviewId
     * @return
     */
    @RequestMapping(value = "/finish", method = RequestMethod.PUT)
    public Response finish(HttpServletRequest request,@RequestParam String interviewId) {
        Account account = (Account) request.getAttribute("account");
        return new Response(interactionInterviewService.finish(account,interviewId));
    }


    /**
     *
     * @param request
     * @param teachinId
     * @param professionId
     * @return
     */
    @RequestMapping(value = "/send", method = RequestMethod.POST)
    public Response send(HttpServletRequest request,
                          String teachinId,
            @RequestParam String professionId) {
        Account account = (Account) request.getAttribute("account");

        if(!Account.ROLE_STUDENT.equals(account.getRole())){
           return new Response(Code.API_USER_ROLE_ERROR);
        }
        if(!interactionInterviewService.resumeStatus(account)){
           return new Response(Code.API_INTERVIEW_RESUME_ERROR);
        }
        return new Response(interactionInterviewService.send(account,teachinId,professionId));
    }

    /*企业============*/

    /**
     * 加入人才库 不合适
     * @param request
     * @param interviewId
     * @return
     */
    @RequestMapping(value = "/status", method = RequestMethod.PUT)
    public Response status(HttpServletRequest request,
                           @RequestParam String flag,
                           @RequestParam String interviewId) {
        Account account = (Account) request.getAttribute("account");
        if(!"4".equals(flag)&&!"5".equals(flag)){
            return new Response(Code.API_PARRAM_ERROR);
        }
        return new Response(interactionInterviewService.status(account,flag,interviewId));
    }


    /**
     * 邀请面试
     * @param request
     * @param interviewId
     * @param interviewType 1电话 2视频
     * @param interviewTime
     * @return
     */

    @RequestMapping(value = "/interview/invite", method = RequestMethod.PUT)
    public Response invite(HttpServletRequest request,
                           @RequestParam String interviewId,
                           @RequestParam String interviewType,
                           @RequestParam String interviewTime) {
        Account account = (Account) request.getAttribute("account");
        if(!"1".equals(interviewType)&&!"2".equals(interviewType)){
            return new Response(Code.API_PARRAM_ERROR);
        }
        return new Response(interactionInterviewService.invite(account, interviewId, interviewType, interviewTime));
    }
}
