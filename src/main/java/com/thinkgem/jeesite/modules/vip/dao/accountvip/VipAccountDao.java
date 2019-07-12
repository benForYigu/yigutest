/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.vip.dao.accountvip;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.vip.entity.accountvip.VipAccount;
import org.apache.ibatis.annotations.Select;

/**
 * 用户权益DAO接口
 * @author 李金辉
 * @version 2019-03-23
 */
@MyBatisDao
public interface VipAccountDao extends CrudDao<VipAccount> {

    @Select("SELECT * FROM a_vip_account WHERE account_id=#{0}")
    VipAccount selectByAccountId(String accountId);
}