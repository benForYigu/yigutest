/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.account.dao.accounthr;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.account.entity.accounthr.AccountCompanyhr;
import com.thinkgem.jeesite.modules.account.entity.accountstudent.AccountStudentinfo;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * hrDAO接口
 * @author 李金辉
 * @version 2019-01-25
 */
@MyBatisDao
public interface AccountCompanyhrDao extends CrudDao<AccountCompanyhr> {

    //@Select("SELECT * FROM a_account_companyhr WHERE account_id=#{0} and del_flag='0'")
    AccountCompanyhr getByAccountId(String accountId);

    //@Select("SELECT * FROM a_account_companyhr WHERE company_id=#{0} and del_flag='0'")
    AccountCompanyhr getByCompanyId(String companyId);

    @Delete("delete from a_account_companyhr where id=#{0}")
    Integer rdelete(String id);

    @Update("update  a_account_companyhr set coin=#{1} where account_id=#{0}")
    Integer updateCoin(String accountId,Integer coin);
}