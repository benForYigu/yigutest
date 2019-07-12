package com.thinkgem.jeesite.API.controller;


import com.alibaba.fastjson.JSON;
import com.aliyuncs.exceptions.ClientException;

import com.thinkgem.jeesite.API.entity.SocketHandler;
import com.thinkgem.jeesite.API.entity.SocketMsg;

import com.thinkgem.jeesite.common.utils.DateUtils;


import com.thinkgem.jeesite.modules.account.entity.accounthr.AccountCompanyhr;
import com.thinkgem.jeesite.modules.account.entity.accountstudent.AccountStudentinfo;
import com.thinkgem.jeesite.modules.account.service.accounthr.AccountCompanyhrService;
import com.thinkgem.jeesite.modules.account.service.accountstudent.AccountStudentinfoService;
import com.thinkgem.jeesite.modules.company.entity.Company;
import com.thinkgem.jeesite.modules.company.service.CompanyService;
import com.thinkgem.jeesite.modules.interaction.entity.teachin.InteractionTeachin;
import com.thinkgem.jeesite.modules.interaction.service.teachin.InteractionTeachinService;

import com.thinkgem.jeesite.modules.message.entity.Message;
import com.thinkgem.jeesite.modules.message.service.MessageService;
import com.thinkgem.jeesite.modules.order.service.order.OrderService;
import com.thinkgem.jeesite.modules.order.service.vip.OrderVipService;
import com.thinkgem.jeesite.modules.unders.service.sign.UndersSignService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;

import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;



/**
 *
 *
 * @author Shixing
 * @version 1.0
 */

@Service
@Lazy(false)
public class Scheduleds {





    @Autowired
    SocketHandler socketHandler;
    @Autowired
    InteractionTeachinService teachinService;
    @Autowired
    OrderVipService orderVipService;
    @Autowired
    OrderService orderService;
    @Autowired
    UndersSignService undersSignService;
    @Autowired
    AccountStudentinfoService studentinfoService;
    @Autowired
    MessageService messageService;
    @Autowired
    CompanyService companyService;
    @Autowired
    AccountCompanyhrService companyhrService;
    @Autowired
    ThreadPoolTaskExecutor threadPool;


    private final Log log = LogFactory.getLog(getClass());


    /**
     * 每天定时推送即将进行宣讲
     */
    @Scheduled(cron="0 0 7,8,11,12,13,16 * * ?")
     public void SoonTeachin() throws ClientException {
        //更新未支付的订单为已关闭
        orderService.delExpire();
        orderVipService.delExpire();
        undersSignService.delExpire();
         log.info("开始定时任务");
         for (InteractionTeachin t:teachinService.findList(initSoonTeachin())) {
             Company company=companyService.get(t.getCompanyId());
             AccountStudentinfo studentinfo=new AccountStudentinfo();
             studentinfo.setSchoolId(t.getSchoolId());
             for (AccountStudentinfo s:studentinfoService.findList(studentinfo)) {
                 Message message=new Message();
                 message.setAccountId(s.getAccountId());
                 message.setTeachinId(t.getId());
                 message.setIsRead("0");
                 message.setContent(company.getName()+"企业线上宣讲室将在3小时后开始，请准时参加！");
                 messageService.save(message);
             }
             AccountCompanyhr companyhr=companyhrService.getByCompanyId(company.getId());
             Message message=new Message();
             message.setAccountId(companyhr.getAccountId());
             message.setTeachinId(t.getId());
             message.setContent("您的"+t.getTitle()+"线上宣讲室将在3小时后开始。");
             messageService.save(message);
        }
    }
    /**
     * 每天定时推送即将进行宣讲
     */
     @Scheduled(cron="0 55 9,10,13,14,15,18 * * ?")
     public void checkSoonTeachin() throws ClientException {
         log.info("开始定时任务");
         for (InteractionTeachin t:teachinService.findList(initSoonTeachin())) {
            SocketMsg socketMsg=new SocketMsg("33");
            socketMsg.setContent("距离宣讲 "+t.getTitle()+" 开始还有5分钟。");
            socketHandler.sendMessageToUser(t.getAccountId(), JSON.toJSONString(socketMsg));

            threadPool.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(5*60*1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    SocketHandler.teachinTimeMap.put(t.getId(),45);
                    t.setStatus(InteractionTeachin.STATUS_ING);
                    teachinService.save(t);
                }
            });
        }
    }


    private InteractionTeachin initSoonTeachin() {
        InteractionTeachin teachin =new InteractionTeachin();
        teachin.setShelf(InteractionTeachin.SHELF_ON);
        teachin.setStatus(InteractionTeachin.STATUS_SOON);
        teachin.setDate(DateUtils.parseDate(DateUtils.getDate()));
        if("9".equals(DateUtils.getHour())){
            teachin.setTime("1");
        }else if("10".equals(DateUtils.getHour())){
            teachin.setTime("2");
        }else if("13".equals(DateUtils.getHour())){
            teachin.setTime("3");
        }else if("14".equals(DateUtils.getHour())){
            teachin.setTime("4");
        }else if("15".equals(DateUtils.getHour())){
            teachin.setTime("5");
        }else if("18".equals(DateUtils.getHour())){
            teachin.setTime("6");
        }
        return teachin;
    }
    /**
     * 每天定时推送即将结束宣讲
     */
    @Scheduled(cron="0 40 10,11,14,15,16,19 * * ?")
    public void checkStopTeachin() throws ClientException {
        log.info("开始定时任务");
        for (InteractionTeachin t:teachinService.findList(initStopTeachin())) {
            SocketMsg socketMsg=new SocketMsg("34");
            socketMsg.setContent("距离宣讲 "+t.getTitle()+" 结束还有5分钟。");
            socketHandler.sendMessageToUser(t.getAccountId(), JSON.toJSONString(socketMsg));
            threadPool.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(5*60*1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    t.setStatus(InteractionTeachin.STATUS_END);
                    teachinService.save(t);
                }
            });
        }
    }


    private InteractionTeachin initStopTeachin() {
        InteractionTeachin teachin =new InteractionTeachin();
        teachin.setShelf(InteractionTeachin.SHELF_ON);
        teachin.setStatus(InteractionTeachin.STATUS_ING);
        teachin.setDate(DateUtils.parseDate(DateUtils.getDate()));
        if("10".equals(DateUtils.getHour())){
            teachin.setTime("1");
        }else if("11".equals(DateUtils.getHour())){
            teachin.setTime("2");
        }else if("14".equals(DateUtils.getHour())){
            teachin.setTime("3");
        }else if("15".equals(DateUtils.getHour())){
            teachin.setTime("4");
        }else if("16".equals(DateUtils.getHour())){
            teachin.setTime("5");
        }else if("19".equals(DateUtils.getHour())){
            teachin.setTime("6");
        }
        return teachin;
    }



}
