package com.thinkgem.jeesite.API.controller;

import com.google.common.base.Strings;
import com.thinkgem.jeesite.API.entity.Code;
import com.thinkgem.jeesite.API.entity.Response;
import com.thinkgem.jeesite.modules.account.entity.Account;
import com.thinkgem.jeesite.modules.interaction.entity.interview.InteractionInterview;
import com.thinkgem.jeesite.modules.interaction.entity.offer.InteractionOffer;
import com.thinkgem.jeesite.modules.interaction.service.offer.InteractionOfferService;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.apache.bcel.generic.RET;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * offer
 */

@RestController
@RequestMapping("${apiPath}/offer")
public class ApiOfferController {
    private final Log log = LogFactory.getLog(getClass());
    @Autowired
    private InteractionOfferService interactionOfferService;

    /**
     * 根据account获取offer
     * @param request
     * @param status 1等待确认 2初步确认 3终极确认 4拒绝
     * @param page
     * @param size
     * @return
     */
    @RequestMapping(value = "/offer", method = RequestMethod.GET)
    public Response offer(HttpServletRequest request,String status,String offerId,
                            @RequestParam(value="page" ,defaultValue = "1") Integer page,
                            @RequestParam(value="size" ,defaultValue = "10") Integer size)  {
        Account account=(Account)request.getAttribute("account");
        return new Response(interactionOfferService.offer(account,status,offerId,page,size));
    }

    @RequestMapping(value = "/confirm", method = RequestMethod.PUT)
    public Response confirm(HttpServletRequest request,String status,String offerId)  {
        Account account=(Account)request.getAttribute("account");
        if(!status.equals(InteractionOffer.STATUS_WAIT)&&
                !status.equals(InteractionOffer.STATUS_FIRST_SURE)&&
                !status.equals(InteractionOffer.STATUS_LAST_SURE)&&
                !status.equals(InteractionOffer.STATUS_REFUSE)){
            return new Response(Code.API_PARRAM_ERROR);
        }
        return new Response(interactionOfferService.confirm(account,status,offerId));
    }
    /*企业============*/

    /**
     * 发送offer
     * @param request
     * @param interviewId
     * @param position
     * @param entryTime
     * @param province
     * @param city

     * @param salary
     * @param notice
     * @return
     */
    @RequestMapping(value = "/offer", method = RequestMethod.POST)
    public Response offer(HttpServletRequest request,
                          @RequestParam String interviewId,
                          @RequestParam String department,
                          @RequestParam String position,
                          @RequestParam String entryTime,
                          @RequestParam String province,
                          @RequestParam String city,
                          @RequestParam String workAddress,
                          @RequestParam String signAddress,
                          @RequestParam String salary,
                          @RequestParam String notice)  {
        Account account=(Account)request.getAttribute("account");
        return new Response(interactionOfferService.pOffer(account,interviewId,department,position,entryTime,
                province,city,workAddress,signAddress,salary,notice));
    }


    @RequestMapping(value = "/offerAgreement", method = RequestMethod.PUT)
    public Response offerAgreement(HttpServletRequest request,
                          @RequestParam String offerId,
                                   @RequestParam String agreement)  {
        Account account=(Account)request.getAttribute("account");
        return new Response(interactionOfferService.offerAgreement(account,offerId,agreement));
    }


}
