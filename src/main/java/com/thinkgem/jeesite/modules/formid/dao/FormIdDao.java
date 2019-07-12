/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.formid.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.formid.entity.FormId;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * orm表单DAO接口
 * @author 李金辉
 * @version 2018-12-06
 */
@MyBatisDao
public interface FormIdDao extends CrudDao<FormId> {
    @Select("select * from form_id where open_id=#{0} and del_flag=0 and PERIOD_DIFF(date_format(now(),'%Y%m%d'),date_format(create_date,'%Y%m%d')) <=7 GROUP BY open_id,create_date ORDER BY create_date asc")
    List<FormId> selectByOpenId(String openId);

    @Delete("delete from form_id where id = #{0}")
    Integer del(String formId);
}