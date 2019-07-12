/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.school.dao.ocupy;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.school.entity.ocupy.SchoolTeachinOcupy;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;

/**
 * 学校宣讲时间DAO接口
 * @author 李金辉
 * @version 2019-04-12
 */
@MyBatisDao
public interface SchoolTeachinOcupyDao extends CrudDao<SchoolTeachinOcupy> {
    @Select(" select * from (SELECT" +
            " NULL AS id," +
            " time," +
            " 1 AS flag" +
            " FROM" +
            " a_interaction_teachin" +
            " WHERE" +
            " school_id = #{0}" +
            " AND date = #{1}" +
            " AND time IS NOT NULL" +
            " AND del_flag = '0' " +
            "" +
            " UNION " +
            " SELECT" +
            " id," +
            " time," +
            " 2 AS flag" +
            " FROM" +
            " a_school_teachin_ocupy" +
            " WHERE" +
            " school_id = #{0}" +
            " AND date = #{1}" +
            " AND time IS NOT NULL" +
            " AND del_flag = '0' ) a" +
            " GROUP BY" +
            " a.time" +
            " ORDER BY" +
            " a.time")
    List<SchoolTeachinOcupy> isUsed(String schoolId, Date date);
    @Select(" Select id,time from a_interaction_teachin where school_id=#{0} and date=#{1}  " +
            " and time=#{2} and del_flag='0' ")
    SchoolTeachinOcupy isUsedDateTime(String schoolId, Date date,String time);

    @Delete("delete from a_school_teachin_ocupy where id =#{0}")
    void ldelete(String id);
}