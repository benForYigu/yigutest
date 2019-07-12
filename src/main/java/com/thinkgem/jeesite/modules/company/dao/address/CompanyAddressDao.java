/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.company.dao.address;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.company.entity.address.CompanyAddress;

import java.util.List;

/**
 * 企业地址DAO接口
 * @author 李金辉
 * @version 2019-01-29
 */
@MyBatisDao
public interface CompanyAddressDao extends CrudDao<CompanyAddress> {

    List<CompanyAddress> selectByCompanyId(String id);
}