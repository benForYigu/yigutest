/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.order.service.undersorder;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.order.entity.undersorder.OrderUnders;
import com.thinkgem.jeesite.modules.order.dao.undersorder.OrderUndersDao;

/**
 * 双选会订单Service
 * @author 李金辉
 * @version 2019-05-29
 */
@Service
@Transactional(readOnly = true)
public class OrderUndersService extends CrudService<OrderUndersDao, OrderUnders> {

	public OrderUnders get(String id) {
		return super.get(id);
	}
	
	public List<OrderUnders> findList(OrderUnders orderUnders) {
		return super.findList(orderUnders);
	}
	
	public Page<OrderUnders> findPage(Page<OrderUnders> page, OrderUnders orderUnders) {
		return super.findPage(page, orderUnders);
	}
	
	@Transactional(readOnly = false)
	public void save(OrderUnders orderUnders) {
		super.save(orderUnders);
	}
	
	@Transactional(readOnly = false)
	public void delete(OrderUnders orderUnders) {
		super.delete(orderUnders);
	}


    public List<Map<String,Object>> otherOrders(String accountId) {
		return dao.otherOrders(accountId);
    }
}