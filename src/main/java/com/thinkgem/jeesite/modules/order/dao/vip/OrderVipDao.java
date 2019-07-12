/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.order.dao.vip;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.order.entity.vip.OrderVip;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * vip订单DAO接口
 * @author 李金辉
 * @version 2019-03-23
 */
@MyBatisDao
public interface OrderVipDao extends CrudDao<OrderVip> {

    @Update("update a_order_vip set `status`='2' where TIMESTAMPDIFF(MINUTE,create_date,now())>1 and pay_type='1' and status='1'")
    void delExpire();

    @Update("update a_order_vip set `status`='2' where TIMESTAMPDIFF(HOUR,create_date,now())>24 and pay_type='4' and status='1'")
    void updateStatus();

    @Select("select * from a_order_vip where account_id=#{0} and pay_type='4' and status='1' and del_flag='0'")
    List<OrderVip> selectByUsers(String id);
}