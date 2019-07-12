/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.order.service.order;

import java.util.Date;
import java.util.List;

import com.thinkgem.jeesite.API.entity.Code;
import com.thinkgem.jeesite.API.entity.Response;
import com.thinkgem.jeesite.API.util.MyUtils;
import com.thinkgem.jeesite.modules.account.dao.accounthr.AccountCompanyhrDao;
import com.thinkgem.jeesite.modules.account.entity.Account;
import com.thinkgem.jeesite.modules.interaction.dao.teachin.InteractionTeachinDao;
import com.thinkgem.jeesite.modules.interaction.entity.teachin.InteractionTeachin;
import com.thinkgem.jeesite.modules.interaction.entity.teachin.InteractionTeachinData;
import com.thinkgem.jeesite.modules.message.entity.Message;
import com.thinkgem.jeesite.modules.order.dao.undersorder.OrderUndersDao;
import com.thinkgem.jeesite.modules.order.dao.vip.OrderVipDao;
import com.thinkgem.jeesite.modules.order.entity.undersorder.OrderUnders;
import com.thinkgem.jeesite.modules.order.entity.vip.OrderVip;
import com.thinkgem.jeesite.modules.orderinvoices.dao.OrderInvoicesDao;
import com.thinkgem.jeesite.modules.orderinvoices.entity.OrderInvoices;
import com.thinkgem.jeesite.modules.school.dao.SchoolDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.order.entity.order.Order;
import com.thinkgem.jeesite.modules.order.dao.order.OrderDao;

/**
 * 订单Service
 *
 * @author 李金辉
 * @version 2019-03-09
 */
@Service
@Transactional(readOnly = true)
public class OrderService extends CrudService<OrderDao, Order> {

    @Autowired
    InteractionTeachinDao teachinDao;
    @Autowired
    AccountCompanyhrDao companyhrDao;
    @Autowired
    OrderInvoicesDao orderInvoicesDao;
    @Autowired
    OrderVipDao orderVipDao;
    @Autowired
    OrderUndersDao orderUndersDao;
    @Transactional(readOnly = false)
    public Order get(String id) {
        delExpire();
        /*Order order=super.get(id);
		order.setTeachin((InteractionTeachin) MyUtils.setLabel(teachinDao.get(order.getTeachinId())));
		return order;*/
        return super.get(id);
    }
    @Transactional(readOnly = false)
    public List<Order> findList(Order order) {
        delExpire();
		/*List<Order> list=super.findList(order);
		for (Order o:
		list) {
			o.setTeachin((InteractionTeachin) MyUtils.setLabel(teachinDao.get(o.getTeachinId())));
		}
		return list;*/
        return super.findList(order);
    }
    @Transactional(readOnly = false)
    public Page<Order> findPage(Page<Order> page, Order order) {

        //更新超过24小时的对公账户
        delExpire();

        return super.findPage(page, order);
    }

    @Transactional(readOnly = false)
    public void save(Order order) {
        super.save(order);
    }

    @Transactional(readOnly = false)
    public void delete(Order order) {
        super.delete(order);
    }

    @Transactional(readOnly = false)
    public void saveStatus(Order order) {
        order.setEndTime(new Date());
        //处理对公账户
        if (Order.PAY_TYPE_PUBLIC.equals(order.getPayType())) {
            //处理成功审核的
            if (Order.STATUS_PAYED.equals(order.getStatus())) {
                InteractionTeachin teachin=teachinDao.get(order.getTeachinId());
                teachin.setStatus(InteractionTeachin.STATUS_SOON);
                teachin.preUpdate();
                teachinDao.update(teachin);


               /* //添加发票
                OrderInvoices invoices=new OrderInvoices();
                invoices.setAccountId(order.getAccountId());
                invoices.setCompanyId(companyhrDao.getByAccountId(order.getAccountId()).getCompanyId());
                invoices.setOrderId(order.getId());
                invoices.setStatus(OrderInvoices.STATUS_WAIT);
                invoices.preInsert();
                orderInvoicesDao.insert(invoices);*/
            }
            //处理失败审核的
            if (Order.STATUS_CLOSE.equals(order.getStatus())) {
                teachinDao.rdel(order.getTeachinId());
            }
            order.preUpdate();
            dao.update(order);

        }
    }
    @Transactional(readOnly = false)
    public void delExpire() {
        dao.delExpire();
        dao.updateStatus();
        teachinDao.updateStatus();
    }

    //
    @Transactional(readOnly = false)
    public Object voucher(Account account, String voucher, String orderId) {
        OrderVip orderVip= orderVipDao.get(orderId);
       if(orderVip!=null){
           if(!orderVip.getAccountId().equals(account.getId())||!OrderVip.PAY_UNDER.equals(orderVip.getPayType())){
               return Code.ORDER_ID_ERROR;
           }
           if(!Order.STATUS_WAIT.equals(orderVip.getStatus())){
               return Code.ORDER_ID_COM;
           }
           orderVip.setPayTime(new Date());
           orderVip.setVoucher(voucher);
           orderVip.preUpdate();
           orderVipDao.update(orderVip);
           return Code.SUCCESS;
       }


        OrderUnders orderUnders= orderUndersDao.get(orderId);
       if(orderUnders!=null){
           if(!orderUnders.getAccountId().equals(account.getId())||!OrderUnders.PAY_TYPE_PUB.equals(orderUnders.getPayType())){
               return Code.ORDER_ID_ERROR;
           }
           if(!Order.STATUS_WAIT.equals(orderUnders.getStatus())){
               return Code.ORDER_ID_COM;
           }
           orderUnders.setPayTime(new Date());
           orderUnders.setVoucher(voucher);
           orderUnders.preUpdate();
           orderUndersDao.update(orderUnders);
           return Code.SUCCESS;
       }

        return Code.ORDER_ID_ERROR;

        /*Order order=dao.get(orderId);
        if(order==null||!order.getAccountId().equals(account.getId())||!Order.PAY_TYPE_PUBLIC.equals(order.getPayType())){
            return new Response(Code.ORDER_ID_ERROR);
        }
        if(!Order.STATUS_WAIT.equals(order.getStatus())){
            return new Response(Code.ORDER_ID_COM);
        }
        order.setPayTime(new Date());
        order.setVoucher(voucher);
        order.preUpdate();
        dao.update(order);

        return Code.SUCCESS;*/
    }
}