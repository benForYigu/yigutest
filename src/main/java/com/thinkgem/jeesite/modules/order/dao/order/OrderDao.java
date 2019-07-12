/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.order.dao.order;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.order.entity.order.Order;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.Date;

/**
 * 订单DAO接口
 * @author 李金辉
 * @version 2019-03-09
 */
@MyBatisDao
public interface OrderDao extends CrudDao<Order> {

    @Select("Select id from a_order where school_id=#{0} and date=#{1} and time=#{2} and del_flag='0'")
    boolean isPayed(String schoolId, Date date, String time);

    //待支付改成失败    pay_type 2 直接购买
    @Update("update a_order set `status`='2' where TIMESTAMPDIFF(MINUTE,create_date,now())>2 and pay_type='2' and status='1'")
    void delExpire();

    //对公超过24小时失败
    @Update("update a_order set `status`='2' where TIMESTAMPDIFF(HOUR,create_date,now())>24 and pay_type='4' and status='1'")
    void updateStatus();
}