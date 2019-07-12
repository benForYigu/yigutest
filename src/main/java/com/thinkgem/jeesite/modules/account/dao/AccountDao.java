/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.account.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.account.entity.Account;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * 用户DAO接口
 * @author 李金辉
 * @version 2019-01-23
 */
@MyBatisDao
public interface AccountDao extends CrudDao<Account> {

    @Select("<script>" +
            "select * from a_account where username=#{username} and password=#{password}" +
            "<if test='role!=null and role!=&quot;&quot;'>" +
            " and role=#{role}" +
            " </if>" +
            " and del_flag='0'" +
            "</script>")
    Account selectByUsernameAndPassword(@Param(value ="username" )String username,
                                        @Param(value ="password" )String password,
                                        @Param(value ="role" )String role);


    @Select("select * from a_account where username=#{0} and del_flag='0'")
    Account selectByPhone(String phone);

    @Delete("delete from a_account where id=#{0}")
    Integer rdelete(String accountId);
}