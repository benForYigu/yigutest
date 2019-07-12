/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.interaction.dao.interview;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.interaction.entity.interview.InteractionInterview;
import org.apache.ibatis.annotations.Select;

/**
 * 面试DAO接口
 * @author 李金辉
 * @version 2019-01-24
 */
@MyBatisDao
public interface InteractionInterviewDao extends CrudDao<InteractionInterview> {

    @Select("SELECT id FROM a_interaction_interview where student_id=#{0} and company_profession_id=#{1} and del_flag='0'")
    InteractionInterview getByAccountIdAndProfessionId(String id, String professionId);
}