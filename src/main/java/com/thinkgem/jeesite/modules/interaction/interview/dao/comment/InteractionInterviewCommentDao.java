/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.interaction.interview.dao.comment;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.interaction.interview.entity.comment.InteractionInterviewComment;
import org.apache.ibatis.annotations.Select;

/**
 * 面试评论DAO接口
 * @author 李金辉
 * @version 2019-01-30
 */
@MyBatisDao
public interface InteractionInterviewCommentDao extends CrudDao<InteractionInterviewComment> {

    @Select("select * from a_interaction_interview_comment where interview_id=#{0} and del_flag='0'")
    InteractionInterviewComment getByinterviewId(String interviewId);


}