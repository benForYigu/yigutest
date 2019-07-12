/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.appconfig.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.appconfig.entity.AppConfig;
import org.apache.ibatis.annotations.Select;

/**
 * 配置数据DAO接口
 * @author 李金辉
 * @version 2019-03-26
 */
@MyBatisDao
public interface AppConfigDao extends CrudDao<AppConfig> {

    @Select("select * from a_app_config where config_type=#{0} and version=#{1}")
    AppConfig selectByVersionAndType(String type, String v);

    @Select("select * from a_app_config where config_type=#{0} and name=#{1}")
    AppConfig selectByVersionName(String type,String name);
}