/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.recommend.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.recommend.entity.RecommendUnder;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

/**
 * 线下活动DAO接口
 * @author 李金辉
 * @version 2019-03-01
 */
@MyBatisDao
public interface RecommendUnderDao extends CrudDao<RecommendUnder> {

    @Select("select code from  a_interaction_recommend_under_enroll where account_id=#{0} and under_id=#{1}")
    String EnrollCode(String id, String id1);

    @Insert("insert into a_interaction_recommend_under_enroll values(#{0},#{1},#{2})")
    void enroll(String id, String id1, String code);
}