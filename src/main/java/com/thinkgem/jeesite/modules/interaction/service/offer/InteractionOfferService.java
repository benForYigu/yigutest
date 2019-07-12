/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.interaction.service.offer;

import java.util.List;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Strings;
import com.thinkgem.jeesite.API.entity.Code;
import com.thinkgem.jeesite.API.util.MyUtils;
import com.thinkgem.jeesite.API.util.leancloud.aoyi.DateUtil;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.modules.account.dao.accounthr.AccountCompanyhrDao;
import com.thinkgem.jeesite.modules.account.dao.accountstudent.AccountStudentinfoDao;
import com.thinkgem.jeesite.modules.account.entity.Account;
import com.thinkgem.jeesite.modules.account.entity.accounthr.AccountCompanyhr;
import com.thinkgem.jeesite.modules.account.entity.accountstudent.AccountStudentinfo;
import com.thinkgem.jeesite.modules.company.dao.CompanyDao;
import com.thinkgem.jeesite.modules.interaction.dao.interview.InteractionInterviewDao;
import com.thinkgem.jeesite.modules.interaction.entity.interview.InteractionInterview;
import com.thinkgem.jeesite.modules.messagelog.dao.MessageLogDao;
import com.thinkgem.jeesite.modules.messagelog.entity.MessageLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.interaction.entity.offer.InteractionOffer;
import com.thinkgem.jeesite.modules.interaction.dao.offer.InteractionOfferDao;

/**
 * offerService
 *
 * @author 李金辉
 * @version 2019-01-24
 */
@Service
@Transactional(readOnly = true)
public class InteractionOfferService extends CrudService<InteractionOfferDao, InteractionOffer> {

    @Autowired
    CompanyDao companyDao;
    @Autowired
    InteractionInterviewDao interviewDao;
    @Autowired
    AccountStudentinfoDao accountStudentinfoDao;
    @Autowired
    AccountCompanyhrDao accountCompanyhrDao;
    @Autowired
    MessageLogDao messageLogDao;
    public InteractionOffer get(String id) {
        InteractionOffer interactionOffer = super.get(id);
        interactionOffer.setCompany(companyDao.get(interactionOffer.getCompanyId()));
        interactionOffer.setStudent(accountStudentinfoDao.getByAccountId(interactionOffer.getStudentId()));
        return interactionOffer;

    }

    public List<InteractionOffer> findList(InteractionOffer interactionOffer) {
        List<InteractionOffer> list = super.findList(interactionOffer);
        for (InteractionOffer i : list) {
            i.setCompany(companyDao.get(i.getCompanyId()));
            i.setStudent(accountStudentinfoDao.getByAccountId(i.getStudentId()));
        }
        return list;
    }

    public Page<InteractionOffer> findPage(Page<InteractionOffer> page, InteractionOffer interactionOffer) {
        return super.findPage(page, interactionOffer);
    }

    @Transactional(readOnly = false)
    public void save(InteractionOffer interactionOffer) {
        super.save(interactionOffer);
    }

    @Transactional(readOnly = false)
    public void delete(InteractionOffer interactionOffer) {
        super.delete(interactionOffer);
    }

    public Object offer(Account account, String status, String offerId, Integer page, Integer size) {
        InteractionOffer interactionOffer = new InteractionOffer();
        //查询单个详情
        if (!Strings.isNullOrEmpty(offerId)) {
            interactionOffer = dao.get(offerId);
            if (interactionOffer == null) {
                return Code.API_OFFER_NOT_EXIT;
            }
            //学生
            if (Account.ROLE_STUDENT.equals(account.getRole())) {
                AccountStudentinfo accountStudentinfo = accountStudentinfoDao.getByAccountId(account.getId());
                if (accountStudentinfo.getAccountId().equals(interactionOffer.getStudentId())) {
                    return interactionOffer;
                }
            }
            //企业
            if (Account.ROLE_COMPANY.equals(account.getRole())) {
                AccountCompanyhr accountCompanyhr = accountCompanyhrDao.getByAccountId(account.getId());
                if (accountCompanyhr.getCompanyId().equals(interactionOffer.getCompanyId())) {
                    return interactionOffer;
                }
            }
            //查询列表
        } else {
            //学生
            if (Account.ROLE_STUDENT.equals(account.getRole())) {
                AccountStudentinfo accountStudentinfo = accountStudentinfoDao.getByAccountId(account.getId());
                interactionOffer.setStatus(status);
                interactionOffer.setStudentId(accountStudentinfo.getAccountId());
                PageHelper.startPage(page, size);
                return new PageInfo<InteractionOffer>(dao.findList(interactionOffer));
            }
            //企业
            if (Account.ROLE_COMPANY.equals(account.getRole())) {
                AccountCompanyhr accountCompanyhr = accountCompanyhrDao.getByAccountId(account.getId());
                interactionOffer.setStatus(status);
                interactionOffer.setCompanyId(accountCompanyhr.getCompanyId());
                PageHelper.startPage(page, size);
                List<InteractionOffer> list=dao.findList(interactionOffer);
                for (InteractionOffer i:list) {
                    i.setStudent((AccountStudentinfo) MyUtils.setLabel(accountStudentinfoDao.getByAccountId(i.getStudentId())));
                }
                return new PageInfo<InteractionOffer>(list);
            }
        }
        return Code.API_USER_ROLE_ERROR;


    }

    @Transactional(readOnly = false)
    public Object confirm(Account account, String status, String offerId) {
        InteractionOffer offer=dao.get(offerId);
        if(!account.getId().equals(offer.getStudentId())){
            return Code.API_USER_ROLE_ERROR;
        }
        //初步确认为不能为已终极确认和拒绝
        if(InteractionOffer.STATUS_REFUSE.equals(offer.getStatus())){
            if (InteractionOffer.STATUS_LAST_SURE.equals(offer.getStatus())||
                    InteractionOffer.STATUS_REFUSE.equals(offer.getStatus())){
                return Code.API_OFFER_FIRST_ERROR;
            }
        }
        //只有一个终极名额
        if(InteractionOffer.STATUS_LAST_SURE.equals(status)){

            //拒绝的不可以终极确认
            if (InteractionOffer.STATUS_REFUSE.equals(offer.getStatus())){
                return Code.API_OFFER_FIRST_ERROR;
            }
            InteractionOffer o=new InteractionOffer();
            o.setStatus(status);
            o.setStudentId(account.getId());
            if(dao.findList(o).size()>0){
                return Code.API_OFFER_REFUSE_ERROR;
            }
        }

        offer.setStatus(status);
        offer.preUpdate();
        dao.update(offer);
        return Code.SUCCESS;
    }

    @Transactional(readOnly = false)
    public Object pOffer(Account account, String interviewId,
                         String department,String position, String entryTime,
                         String province, String city,
                         String workAddress,String signAddress, String salary,
                         String notice) {
        InteractionInterview interview=interviewDao.get(interviewId);
        if(interview==null){
            return Code.API_INTERVIEW_NOT_EXIT;
        }
        //判断是否为此公司hr
       /* AccountCompanyhr companyhr=accountCompanyhrDao.getByAccountId(account.getId());
            if(companyhr==null||!companyhr.getCompanyId().equals(interview.getCompanyId())){
                return Code.API_USER_ROLE_ERROR;
        }*/
        if(dao.selectByInterviewId(interviewId)!=null){
            return Code.API_HR_OFFERED;
        }

        InteractionOffer offer=new InteractionOffer();
        offer.setStudentId(interview.getStudentId());
        offer.setCompanyId(interview.getCompanyId());
        offer.setInterviewId(interviewId);
        offer.setDepartment(department);
        offer.setPosition(position);
        offer.setEntryTime(DateUtils.parseDate(entryTime));
        offer.setProvince(province);
        offer.setCity(city);
        offer.setWorkAddress(workAddress);
        offer.setSignAddress(signAddress);
        offer.setSalary(salary);
        offer.setSalary(notice);
        offer.preInsert();
        dao.insert(offer);


        //增加消息记录
        MessageLog messageLog = new MessageLog();
        messageLog.setInterviewId(interview.getId());
        messageLog.setType(interview.getInterviewType());
        messageLog.setCompanyProfessionId(interview.getCompanyProfessionId());
        messageLog.setStudentId(interview.getStudentId());
        if (messageLogDao.findList(messageLog).size() == 0) {
            messageLog = MyUtils.createMessage(interview);
            messageLog.setType(MessageLog.TYPE_5);
            messageLog.setDepartment(department);
            messageLog.setOfferId(offer.getId());
            messageLog.preInsert();
            messageLogDao.insert(messageLog);
        } else {
            logger.info("插入消息记录已存在");
        }



        return Code.SUCCESS;
    }

    @Transactional(readOnly = false)
    public Object offerAgreement(Account account, String offerId, String agreement) {
       InteractionOffer offer= dao.get(offerId);
       if(offer==null||!offer.getCompanyId().equals(accountCompanyhrDao.getByAccountId(account.getId()).getCompanyId())){
           return Code.API_OFFER_NOT_EXIT;
       }
        offer.setAgreement(agreement);
       offer.preUpdate();
       dao.update(offer);
       return Code.SUCCESS;
    }
}