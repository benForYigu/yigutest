/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.account.dao.accountschool;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.account.entity.accountschool.AccountTeacherinfo;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;

/**
 * 学校人员DAO接口
 * @author 李金辉
 * @version 2019-01-28
 */
@MyBatisDao
public interface AccountTeacherinfoDao extends CrudDao<AccountTeacherinfo> {

    //@Select("SELECT * FROM a_account_teacherinfo WHERE account_id=#{0} and del_flag='0'")
    AccountTeacherinfo getByAccountId(String accountId);

    @Delete("delete from a_account_teacherinfo where id=#{0}")
    Integer rdelete(String id);
}