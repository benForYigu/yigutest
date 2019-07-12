/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.unders.dao.unders;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.unders.entity.unders.Unders;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 线下活动DAO接口
 * @author 李金辉
 * @version 2019-05-24
 */
@MyBatisDao
public interface UndersDao extends CrudDao<Unders> {


    List<Unders> findListByCompany(@Param(value = "companyId") String companyId,
                                   @Param(value = "title")String title);
}