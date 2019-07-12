/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.orderinvoices.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.orderinvoices.entity.OrderInvoices;
import com.thinkgem.jeesite.modules.orderinvoices.dao.OrderInvoicesDao;

/**
 * 企业发票Service
 * @author 李金辉
 * @version 2019-04-19
 */
@Service
@Transactional(readOnly = true)
public class OrderInvoicesService extends CrudService<OrderInvoicesDao, OrderInvoices> {

	public OrderInvoices get(String id) {
		return super.get(id);
	}
	
	public List<OrderInvoices> findList(OrderInvoices orderInvoices) {
		return super.findList(orderInvoices);
	}
	
	public Page<OrderInvoices> findPage(Page<OrderInvoices> page, OrderInvoices orderInvoices) {
		return super.findPage(page, orderInvoices);
	}
	
	@Transactional(readOnly = false)
	public void save(OrderInvoices orderInvoices) {
		super.save(orderInvoices);
	}
	
	@Transactional(readOnly = false)
	public void delete(OrderInvoices orderInvoices) {
		super.delete(orderInvoices);
	}

    public Object selectLatest(String accountId) {
		return dao.selectLatest(accountId);
    }


    public OrderInvoices selectByOrderId(String orderId) {
		return dao.selectByOrderId(orderId);
    }
}