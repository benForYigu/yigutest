/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.interaction.interview.service.comment;

import java.util.List;

import com.thinkgem.jeesite.API.entity.Code;
import com.thinkgem.jeesite.API.entity.Response;
import com.thinkgem.jeesite.modules.account.dao.accountstudent.AccountStudentinfoDao;
import com.thinkgem.jeesite.modules.account.entity.Account;
import com.thinkgem.jeesite.modules.account.entity.accountstudent.AccountStudentinfo;
import com.thinkgem.jeesite.modules.interaction.dao.interview.InteractionInterviewDao;
import com.thinkgem.jeesite.modules.interaction.entity.interview.InteractionInterview;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.interaction.interview.entity.comment.InteractionInterviewComment;
import com.thinkgem.jeesite.modules.interaction.interview.dao.comment.InteractionInterviewCommentDao;

/**
 * 面试评论Service
 * @author 李金辉
 * @version 2019-01-30
 */
@Service
@Transactional(readOnly = true)
public class InteractionInterviewCommentService extends CrudService<InteractionInterviewCommentDao, InteractionInterviewComment> {

	@Autowired
	AccountStudentinfoDao accountStudentinfoDao;
	@Autowired
	InteractionInterviewDao interactionInterviewDao;
	@Value("${comment.coin}")
	String commentCoin;
	public InteractionInterviewComment get(String id) {
		return super.get(id);
	}
	
	public List<InteractionInterviewComment> findList(InteractionInterviewComment interactionInterviewComment) {
		return super.findList(interactionInterviewComment);
	}
	
	public Page<InteractionInterviewComment> findPage(Page<InteractionInterviewComment> page, InteractionInterviewComment interactionInterviewComment) {
		return super.findPage(page, interactionInterviewComment);
	}
	
	@Transactional(readOnly = false)
	public void save(InteractionInterviewComment interactionInterviewComment) {
		super.save(interactionInterviewComment);
	}
	
	@Transactional(readOnly = false)
	public void delete(InteractionInterviewComment interactionInterviewComment) {
		super.delete(interactionInterviewComment);
	}

    public Object getByinterviewId(String interviewId) {
		return dao.getByinterviewId(interviewId);
    }


    public Object getComment(Account account, String interviewid) {
		InteractionInterviewComment comment=dao.getByinterviewId(interviewid);
		InteractionInterview interview =interactionInterviewDao.get(interviewid);
		if(comment==null || interview==null){
			return Code.API_INTERVIEWID_ERROR;
		}
		if(!interview.getStudentId().equals(account.getId())){
			return Code.API_USER_ROLE_ERROR;
		}
		return comment;
    }




	@Transactional(readOnly = false)
    public Object comment(Account account, String interviewId,
						  Integer charm, Integer affinity,
						  Integer quality, Integer positive,
						  Integer culture, Integer consciousness,
						  Integer interest, Integer trust,
						  Integer integrate) {
		if(charm>5||charm<0||
				affinity>5||affinity<0||
				quality>5||quality<0||
				positive>5||positive<0||
				culture>5||culture<0||
				consciousness>5||consciousness<0||
				interest>5||interest<0||
				trust>5||trust<0||
				integrate>5||integrate<0
				){
			return new Response(Code.API_NUMBER_ERROR);
		}
		//判断是否存在面试
		InteractionInterview interview=interactionInterviewDao.get(interviewId);
		if(interview==null||!interview.getStudentId().equals(account.getId())){
			return Code.API_USER_ROLE_ERROR;
		}
		//判断已评论过
		if(dao.getByinterviewId(interviewId)!=null){
			return Code.API_COMMENTED;
		}
		InteractionInterviewComment comment=new InteractionInterviewComment();
		comment.setInterviewId(interviewId);
		comment.setTrust(trust);
		comment.setAffinity(affinity);
		comment.setCharm(charm);
		comment.setConsciousness(consciousness);
		comment.setCulture(culture);
		comment.setIntegrate(integrate);
		comment.setInterest(interest);
		comment.setPositive(positive);
		comment.setQuality(quality);
		comment.preInsert();
		dao.insert(comment);
		/*while (true){
			AccountStudentinfo studentinfo=accountStudentinfoDao.getByAccountId(account.getId());
			studentinfo.setCoin(studentinfo.getCoin()+Integer.valueOf(commentCoin));
			if(accountStudentinfoDao.casLock(studentinfo)>0){
				break;
			}
		}*/
		AccountStudentinfo studentinfo=accountStudentinfoDao.getByAccountId(account.getId());
		studentinfo.setCoin(studentinfo.getCoin()+Integer.valueOf(commentCoin));
		studentinfo.preUpdate();
		accountStudentinfoDao.update(studentinfo);
		return commentCoin;
    }


}