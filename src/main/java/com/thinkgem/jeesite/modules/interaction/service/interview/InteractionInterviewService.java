/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.interaction.service.interview;

import java.util.ArrayList;
import java.util.List;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Strings;
import com.thinkgem.jeesite.API.entity.Code;
import com.thinkgem.jeesite.API.entity.Response;
import com.thinkgem.jeesite.API.util.MyUtils;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.modules.account.dao.accounthr.AccountCompanyhrDao;
import com.thinkgem.jeesite.modules.account.dao.accountstudent.AccountStudentinfoDao;
import com.thinkgem.jeesite.modules.account.entity.Account;
import com.thinkgem.jeesite.modules.account.entity.accounthr.AccountCompanyhr;
import com.thinkgem.jeesite.modules.account.entity.accountstudent.AccountStudentinfo;
import com.thinkgem.jeesite.modules.account.web.accountstudent.AccountStudentinfoController;
import com.thinkgem.jeesite.modules.company.dao.CompanyDao;
import com.thinkgem.jeesite.modules.company.dao.profession.CompanyProfessionDao;
import com.thinkgem.jeesite.modules.company.entity.Company;
import com.thinkgem.jeesite.modules.company.entity.profession.CompanyProfession;
import com.thinkgem.jeesite.modules.interaction.dao.teachin.InteractionTeachinDao;
import com.thinkgem.jeesite.modules.interaction.entity.teachin.InteractionTeachin;
import com.thinkgem.jeesite.modules.interaction.entity.teachin.InteractionTeachinData;
import com.thinkgem.jeesite.modules.messagelog.dao.MessageLogDao;
import com.thinkgem.jeesite.modules.messagelog.entity.MessageLog;
import com.thinkgem.jeesite.modules.resume.dao.education.StudentResumeEducationDao;
import com.thinkgem.jeesite.modules.resume.dao.experience.StudentResumeExperienceDao;
import com.thinkgem.jeesite.modules.resume.dao.other.StudentResumeOtherDao;
import com.thinkgem.jeesite.modules.resume.dao.prefer.StudentResumePreferDao;
import com.thinkgem.jeesite.modules.resume.entity.education.StudentResumeEducation;
import com.thinkgem.jeesite.modules.resume.entity.experience.StudentResumeExperience;
import com.thinkgem.jeesite.modules.resume.entity.other.StudentResumeOther;
import com.thinkgem.jeesite.modules.resume.entity.prefer.StudentResumePrefer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.interaction.entity.interview.InteractionInterview;
import com.thinkgem.jeesite.modules.interaction.dao.interview.InteractionInterviewDao;
import sun.misc.Cache;


/**
 * 面试Service
 *
 * @author 李金辉
 * @version 2019-01-24
 */
@Service
@Transactional(readOnly = true)
public class InteractionInterviewService extends CrudService<InteractionInterviewDao, InteractionInterview> {

    @Autowired
    CompanyDao companyDao;
    @Autowired
    MessageLogDao messageLogDao;


    @Autowired
    CompanyProfessionDao companyProfessionDao;
    @Autowired
    AccountStudentinfoDao accountStudentinfoDao;
    @Autowired
    AccountCompanyhrDao accountCompanyhrDao;
    @Autowired
    InteractionTeachinDao interactionTeachinDao;

    @Autowired
    StudentResumeOtherDao studentResumeOtherDao;
    @Autowired
    StudentResumePreferDao studentResumePreferDao;
    @Autowired
    StudentResumeExperienceDao studentResumeExperienceDao;
    @Autowired
    StudentResumeEducationDao studentResumeEducationDao;

    public InteractionInterview get(String id) {
        InteractionInterview interactionInterview = super.get(id);
        interactionInterview.setCompany(companyDao.get(interactionInterview.getCompanyId()));
        interactionInterview.setStudent(accountStudentinfoDao.getByAccountId(interactionInterview.getStudentId()));
        return interactionInterview;
    }

    public List<InteractionInterview> findList(InteractionInterview interactionInterview) {
        List<InteractionInterview> list = super.findList(interactionInterview);
        for (InteractionInterview i : list) {
            i.setCompany(companyDao.get(i.getCompanyId()));
            i.setStudent(accountStudentinfoDao.getByAccountId(i.getStudentId()));
        }
        return list;
    }

    public Page<InteractionInterview> findPage(Page<InteractionInterview> page, InteractionInterview interactionInterview) {
        return super.findPage(page, interactionInterview);
    }

    @Transactional(readOnly = false)
    public void save(InteractionInterview interactionInterview) {
        super.save(interactionInterview);
    }

    @Transactional(readOnly = false)
    public void delete(InteractionInterview interactionInterview) {
        super.delete(interactionInterview);
    }

    //面试列表或详情
    public Object Interview(Account account, String status, String InterviewId, Integer page, Integer size) {
        InteractionInterview interactionInterview = new InteractionInterview();
        //查询单个详情
        if (!Strings.isNullOrEmpty(InterviewId)) {
            interactionInterview = dao.get(InterviewId);
            if (interactionInterview == null) {
                return Code.API_TEACHIN_NOT_EXIT;
            }
            //学生
            if (Account.ROLE_STUDENT.equals(account.getRole())) {
                AccountStudentinfo accountStudentinfo = accountStudentinfoDao.getByAccountId(account.getId());
                if (accountStudentinfo.getAccountId().equals(interactionInterview.getStudentId())) {

                    return interactionInterview;
                }
            }
            //企业
            if (Account.ROLE_COMPANY.equals(account.getRole())) {
                AccountCompanyhr accountCompanyhr = accountCompanyhrDao.getByAccountId(account.getId());
                if (accountCompanyhr.getCompanyId().equals(interactionInterview.getCompanyId())) {
                    return interactionInterview;
                }
            }
            //查询列表
        } else {
            //学生
            if (Account.ROLE_STUDENT.equals(account.getRole())) {
                AccountStudentinfo accountStudentinfo = accountStudentinfoDao.getByAccountId(account.getId());
                interactionInterview.setInterviewStatus(status);
                interactionInterview.setStudentId(accountStudentinfo.getAccountId());
                PageHelper.startPage(page, size);
                List<InteractionInterview> list = dao.findList(interactionInterview);
                Company c;
                for (InteractionInterview i : list) {
                    c = companyDao.get(i.getCompanyId());
                    i.setCompanyName(c.getName());
                    i.setCompanyLogo(c.getLogo());
                    i.setProfessionName(companyProfessionDao.get(i.getCompanyProfessionId()).getName());
                }
                return new PageInfo<InteractionInterview>(list);
            }
            //企业
            if (Account.ROLE_COMPANY.equals(account.getRole())) {
                AccountCompanyhr accountCompanyhr = accountCompanyhrDao.getByAccountId(account.getId());
                interactionInterview.setInterviewStatus(status);
                interactionInterview.setCompanyId(accountCompanyhr.getCompanyId());
                PageHelper.startPage(page, size);
                List<InteractionInterview> list = dao.findList(interactionInterview);

                AccountStudentinfo studentinfo;
                List<AccountStudentinfo> is = new ArrayList();
                for (InteractionInterview i : list) {
                    studentinfo = accountStudentinfoDao.getByAccountId(i.getStudentId());
                    if (is.contains(studentinfo)) {
                        i.setStudent(studentinfo);
                    } else {
                        i.setStudent((AccountStudentinfo) MyUtils.setLabel(studentinfo));
                        is.add(studentinfo);
                    }
                }
                return new PageInfo<InteractionInterview>(list);
            }
        }
        return Code.API_USER_ROLE_ERROR;


    }

    public Boolean resumeStatus(Account account) {
        StudentResumeEducation education = new StudentResumeEducation();
        education.setStudentId(account.getId());
        StudentResumeExperience experience = new StudentResumeExperience();
        experience.setStudentId(account.getId());
        StudentResumePrefer prefer = new StudentResumePrefer();
        prefer.setStudentId(account.getId());
        StudentResumeOther other = new StudentResumeOther();
        other.setStudentId(account.getId());
        if (studentResumeEducationDao.findList(education).size() != 0 &&
               // studentResumeExperienceDao.findList(experience).size() != 0 &&
                studentResumeOtherDao.findList(other).size() != 0 &&
                studentResumePreferDao.findList(prefer).size() != 0) {
            return true;
        }
        return false;
    }

    //同意面试
    @Transactional(readOnly = false)
    public Object agree(Account account, String interviewId) {
        InteractionInterview interview = dao.get(interviewId);
        if (interview == null) {
            return Code.API_INTERVIEW_NOT_EXIT;
        }
        if (!interview.getStudentId().equals(account.getId())) {
            return Code.API_USER_ROLE_ERROR;
        }
        interview.setInterviewStatus(InteractionInterview.STATUS_COMU);
        interview.preUpdate();
        dao.update(interview);


        //增加消息记录
        MessageLog messageLog = new MessageLog();
        messageLog.setInterviewId(interview.getId());
        messageLog.setType(interview.getInterviewType());
        messageLog.setCompanyProfessionId(interview.getCompanyProfessionId());
        messageLog.setStudentId(interview.getStudentId());
        if (messageLogDao.findList(messageLog).size() == 0) {
            messageLog = MyUtils.createMessage(interview);
            messageLog.setType(MessageLog.TYPE_3);
            messageLog.preInsert();
            messageLogDao.insert(messageLog);
        } else {
            logger.info("插入消息记录已存在");
        }

        return Code.SUCCESS;
    }

    //面试jieshu
    @Transactional(readOnly = false)
    public Object finish(Account account, String interviewId) {
        InteractionInterview interview = dao.get(interviewId);
        if (interview == null) {
            return Code.API_INTERVIEW_NOT_EXIT;
        }
        if (interview.getStudentId().equals(account.getId())) {
            return Code.API_USER_ROLE_ERROR;
        }
        interview.setInterviewStatus(InteractionInterview.STATUS_FINISH);
        interview.preUpdate();
        dao.update(interview);
        return Code.SUCCESS;
    }


    @Transactional(readOnly = false)
    public Object send(Account account, String teachinId, String professionId) {
        CompanyProfession companyProfession=companyProfessionDao.get(professionId);
        if(companyProfession==null){
            return Code.API_PROFESSION_NULL;
        }
        //判断是否已投递过
        if (dao.getByAccountIdAndProfessionId(account.getId(), professionId) != null) {
            return Code.API_USER_PROFESSION_SENDED;
        }


        //从宣讲进来的投递
        if(!Strings.isNullOrEmpty(teachinId)){
            //判断是否有权投递
            InteractionTeachin teachin = interactionTeachinDao.get(teachinId);
            if (teachin == null) {
                return Code.API_TEACHIN_NOT_EXIT;
            }
            //是同一所学校才可以投递
            if (teachin.getSchoolId().equals(accountStudentinfoDao.getByAccountId(account.getId()).getSchoolId())) {
                InteractionInterview interview = new InteractionInterview();
                interview.setStudentId(account.getId());
                interview.setCompanyId(teachin.getCompanyId());
                //判断该宣讲是否有该职位
                if (((String) teachin.getProfession()).indexOf(professionId) > -1) {
                    interview.setCompanyProfessionId(professionId);
                    interview.setInterviewStatus(InteractionInterview.STATUS_ACT);
                    interview.setTeachinId(teachinId);
                    interview.preInsert();
                    dao.insert(interview);
                    //增加消息记录
                    MessageLog messageLog = new MessageLog();
                    messageLog.setInterviewId(interview.getId());
                    messageLog.setType(interview.getInterviewType());
                    messageLog.setCompanyProfessionId(interview.getCompanyProfessionId());
                    messageLog.setStudentId(interview.getStudentId());
                    if (messageLogDao.findList(messageLog).size() == 0) {
                        messageLog = MyUtils.createMessage(interview);
                        messageLog.setType(MessageLog.TYPE_1);
                        messageLog.preInsert();
                        messageLogDao.insert(messageLog);
                    } else {
                        logger.info("插入消息记录已存在");
                    }

                    return Code.SUCCESS;
                } else {
                    return Code.API_INTERVIEW_PROFESSION_ERROR;
                }
            } else {
                return Code.API_INTERVIEW_SEND_ERROR;
            }
        }else{
            InteractionInterview interview = new InteractionInterview();
            interview.setStudentId(account.getId());
            interview.setCompanyId(companyProfession.getCompanyId());
            interview.setCompanyProfessionId(professionId);
            interview.setInterviewStatus(InteractionInterview.STATUS_ACT);
            interview.setTeachinId(teachinId);
            interview.preInsert();
            dao.insert(interview);
            //增加消息记录
            MessageLog messageLog = new MessageLog();
            messageLog.setInterviewId(interview.getId());
            messageLog.setType(interview.getInterviewType());
            messageLog.setCompanyProfessionId(interview.getCompanyProfessionId());
            messageLog.setStudentId(interview.getStudentId());
            if (messageLogDao.findList(messageLog).size() == 0) {
                messageLog = MyUtils.createMessage(interview);
                messageLog.setType(MessageLog.TYPE_1);
                messageLog.preInsert();
                messageLogDao.insert(messageLog);
            } else {
                logger.info("插入消息记录已存在");
            }

            return Code.SUCCESS;
        }
    }

    @Transactional(readOnly = false)
    public Object status(Account account, String flag, String interviewId) {
        InteractionInterview interview = dao.get(interviewId);
        if (interview == null) {
            return Code.API_INTERVIEW_NOT_EXIT;
        }
        //判断是否为此公司hr
        /*AccountCompanyhr companyhr=accountCompanyhrDao.getByAccountId(account.getId());
		if(companyhr==null||!companyhr.getCompanyId().equals(interview.getCompanyId())){
			return Code.API_USER_ROLE_ERROR;
		}*/

        interview.setInterviewStatus(flag);
        interview.preUpdate();
        dao.update(interview);
        return Code.SUCCESS;
    }

    @Transactional(readOnly = false)
    public Object invite(Account account, String interviewId, String interviewType, String interviewTime) {
        InteractionInterview interview = dao.get(interviewId);
        if (interview == null) {
            return Code.API_INTERVIEW_NOT_EXIT;
        }
        //判断是否为此公司hr
		/*AccountCompanyhr companyhr=accountCompanyhrDao.getByAccountId(account.getId());
		if(companyhr==null||!companyhr.getCompanyId().equals(interview.getCompanyId())){
			return Code.API_USER_ROLE_ERROR;
		}*/
        if (!interview.getInterviewStatus().equals("1")) {
            return Code.API_INTERVIEW_STATUS_ERROR;
        }
        interview.setInterviewType(interviewType);
        interview.setInterviewTime(DateUtils.parseDate(interviewTime));
        interview.setInterviewStatus(InteractionInterview.STATUS_WAIT);
        interview.preUpdate();
        dao.update(interview);

        //增加消息记录
        MessageLog messageLog = new MessageLog();
        messageLog.setInterviewId(interview.getId());
        messageLog.setType(interview.getInterviewType());
        messageLog.setCompanyProfessionId(interview.getCompanyProfessionId());
        messageLog.setStudentId(interview.getStudentId());
        if (messageLogDao.findList(messageLog).size() == 0) {
            messageLog = MyUtils.createMessage(interview);
            messageLog.setType(MessageLog.TYPE_2);
            messageLog.preInsert();
            messageLogDao.insert(messageLog);
        } else {
            logger.info("插入消息记录已存在");
        }


        MessageLog m = new MessageLog();
        m.setCompanyId(messageLog.getCompanyId());
        m.setCompanyProfessionId(messageLog.getCompanyProfessionId());
        m.getPage().setOrderBy("type ");

        return messageLogDao.findList(m);

    }


}