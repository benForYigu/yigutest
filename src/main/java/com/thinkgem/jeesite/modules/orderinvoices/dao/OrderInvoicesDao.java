/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.orderinvoices.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.orderinvoices.entity.OrderInvoices;
import org.apache.ibatis.annotations.Select;

/**
 * 企业发票DAO接口
 * @author 李金辉
 * @version 2019-04-19
 */
@MyBatisDao
public interface OrderInvoicesDao extends CrudDao<OrderInvoices> {

    @Select("select * from a_order_invoices where account_id=#{0} and del_flag='0' and status='3' order by create_date desc limit 1")
    OrderInvoices selectLatest(String accountId);

    @Select("select * from a_order_invoices where order_id=#{0}")
    OrderInvoices selectByOrderId(String orderId);
}