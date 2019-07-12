/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.company.dao.auth;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.company.entity.auth.CompanyAuth;
import org.apache.ibatis.annotations.Select;

/**
 * 企业认证信息DAO接口
 * @author 李金辉
 * @version 2019-01-29
 */
@MyBatisDao
public interface CompanyAuthDao extends CrudDao<CompanyAuth> {


}