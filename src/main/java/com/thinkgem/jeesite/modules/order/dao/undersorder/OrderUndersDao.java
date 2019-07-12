/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.order.dao.undersorder;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.order.entity.undersorder.OrderUnders;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

/**
 * 双选会订单DAO接口
 * @author 李金辉
 * @version 2019-05-29
 */
@MyBatisDao
public interface OrderUndersDao extends CrudDao<OrderUnders> {

    //待支付改成失败    pay_type 2 直接购买
    @Update("update a_order_unders set `status`='2' where TIMESTAMPDIFF(MINUTE,create_date,now())>1 and pay_type='2' and status='1'")
    void delExpire();

    //对公超过24小时失败
    @Update("update a_order_unders set `status`='2' where TIMESTAMPDIFF(HOUR,create_date,now())>24 and pay_type='4' and status='1'")
    void updateStatus();


    List<Map<String,Object>> otherOrders(@Param(value = "accountId") String accountId);
}