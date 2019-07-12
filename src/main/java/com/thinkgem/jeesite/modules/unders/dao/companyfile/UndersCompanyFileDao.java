/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.unders.dao.companyfile;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.unders.entity.companyfile.UndersCompanyFile;
import org.apache.ibatis.annotations.Param;

/**
 * 企业双选会资料DAO接口
 * @author 李金辉
 * @version 2019-05-27
 */
@MyBatisDao
public interface UndersCompanyFileDao extends CrudDao<UndersCompanyFile> {


    UndersCompanyFile selectByCompanyId(@Param(value = "id") String id);
}