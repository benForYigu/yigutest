/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.company.dao.profession;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.company.entity.Company;
import com.thinkgem.jeesite.modules.company.entity.profession.CompanyProfession;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 企业职位DAO接口
 * @author 李金辉
 * @version 2019-01-30
 */
@MyBatisDao
public interface CompanyProfessionDao extends CrudDao<CompanyProfession> {

    @Select("select * from a_company_profession where FIND_IN_SET(id,#{0})")
    List<CompanyProfession> findInSet(String professionIds);

    @Select("SELECT * FROM a_company_profession WHERE FIND_IN_SET(id,#{0})")
    List<CompanyProfession> gCollect(String collect);
}