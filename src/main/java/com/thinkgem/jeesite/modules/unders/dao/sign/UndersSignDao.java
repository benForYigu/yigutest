/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.unders.dao.sign;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.unders.entity.sign.UndersSign;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * 双选会报名DAO接口
 * @author 李金辉
 * @version 2019-05-24
 */
@MyBatisDao
public interface UndersSignDao extends CrudDao<UndersSign> {


    UndersSign selectByOrderId(@Param(value ="undersOrderId" ) String undersOrderId);

    //wx
    @Delete("Delete from a_interaction_recommend_unders_sign  where TIMESTAMPDIFF(minute,create_date,now())>1 and status='0' and del_flag='0'")
    Integer updateStatus();
    //对公支付
   // @Delete("Delete from a_interaction_recommend_unders_sign  where TIMESTAMPDIFF(HOUR,create_date,now())>24 and status='-1' and del_flag='0'")
    @Update("Update a_interaction_recommend_unders_sign set del_flag='1' where TIMESTAMPDIFF(HOUR,create_date,now())>24 and status='-1' and del_flag='0'")
    void updatePublicStatus();

    @Delete("Delete from a_interaction_recommend_unders_sign  where unders_order_id=#{0}")
    Integer rdeleteByOrderId(String orderId);
}