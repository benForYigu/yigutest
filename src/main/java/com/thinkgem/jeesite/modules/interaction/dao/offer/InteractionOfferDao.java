/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.interaction.dao.offer;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.interaction.entity.offer.InteractionOffer;
import org.apache.ibatis.annotations.Select;

/**
 * offerDAO接口
 * @author 李金辉
 * @version 2019-01-24
 */
@MyBatisDao
public interface InteractionOfferDao extends CrudDao<InteractionOffer> {

    @Select("select id from a_interaction_offer where interview_id=#{0} and del_flag='0'")
    InteractionOffer selectByInterviewId(String interviewId);
}