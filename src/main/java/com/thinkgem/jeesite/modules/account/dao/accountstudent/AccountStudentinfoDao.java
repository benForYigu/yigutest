/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.account.dao.accountstudent;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.account.entity.accountstudent.AccountStudentinfo;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * 用户_学生DAO接口
 * @author 李金辉
 * @version 2019-01-23
 */
@MyBatisDao
public interface AccountStudentinfoDao extends CrudDao<AccountStudentinfo> {

    //@Select("SELECT * FROM a_account_studentinfo WHERE account_id=#{0} and del_flag='0'")
    AccountStudentinfo getByAccountId(String accountId);

    @Delete("delete from a_account_studentinfo where id=#{0}")
    Integer rdelete(String id);

    @Update("update  a_account_studentinfo set coin=#{1} where account_id=#{0}")
    Integer updateCoin(String accountId,Integer coin);
}