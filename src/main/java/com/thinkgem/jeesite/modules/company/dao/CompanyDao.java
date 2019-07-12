/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.company.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.account.entity.accountstudent.AccountStudentinfo;
import com.thinkgem.jeesite.modules.company.entity.Company;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 企业基本信息DAO接口
 * @author 李金辉
 * @version 2019-01-29
 */
@MyBatisDao
public interface CompanyDao extends CrudDao<Company> {

    @Select("SELECT * FROM a_company WHERE FIND_IN_SET(id,#{0})")
    List<Company> gCollect(String collect);

    @Select("<script>" +
            " SELECT" +
            " c.*" +
            " FROM" +
            " a_interaction_recommend_unders a" +
            " INNER JOIN a_interaction_recommend_unders_sign b ON a.id = b.unders_id" +
            " inner join a_company c on b.company_id=c.id" +
            " where a.school_id=#{schoolId}" +
            " and a.id=#{undersId}" +
            " and b.status='1'" +
            "<if test='name!=null and name!=&quot;&quot;'>" +
            " and c.name like concat('%',#{name},'%')" +
            " </if>" +
            " group by c.id" +
            "</script>")
    List<Company> findCompanyBySchool(@Param(value ="schoolId")String schoolId,
                                      @Param(value ="undersId")String undersId,
                                      @Param(value ="name")String name
    );
}