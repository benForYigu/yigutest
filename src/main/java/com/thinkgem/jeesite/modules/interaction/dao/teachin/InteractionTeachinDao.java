/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.interaction.dao.teachin;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.interaction.entity.teachin.InteractionTeachin;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.Date;
import java.util.List;

/**
 * 宣讲DAO接口
 * @author 李金辉
 * @version 2019-01-24
 */
@MyBatisDao
public interface InteractionTeachinDao extends CrudDao<InteractionTeachin> {

    @Select("Select id,status,create_date from a_interaction_teachin where school_id=#{0} and date=#{1} and time=#{2} " +
            " and del_flag='0'")
    InteractionTeachin isPayed(String schoolId, Date date, String time);


    @Delete("delete from a_interaction_teachin where id=#{0}")
    Integer rdel(String id);

    @Delete("Delete from a_interaction_teachin  where TIMESTAMPDIFF(HOUR,create_date,now())>1 and status='0'")
    void updateStatus();
}