/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.smsrecord.service;
/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.modules.smsrecord.dao.SmsRecordDao;
import com.thinkgem.jeesite.modules.smsrecord.entity.SmsRecord;



import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.smsrecord.dao.SmsRecordDao;
import com.thinkgem.jeesite.modules.smsrecord.entity.SmsRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 验证码Service
 * @author 黎锦晖
 * @version 2018-05-29
 */
@Service
@Transactional(readOnly = true)
public class SmsRecordService extends CrudService<SmsRecordDao, SmsRecord> {
	@Autowired
	private SmsRecordDao smsRecordDao;

	@Override
	public SmsRecord get(String id) {
		return super.get(id);
	}

	@Override
	public List<SmsRecord> findList(SmsRecord smsRecord) {
		return super.findList(smsRecord);
	}

	@Override
	public Page<SmsRecord> findPage(Page<SmsRecord> page, SmsRecord smsRecord) {
		return super.findPage(page, smsRecord);
	}

	@Override
	@Transactional(readOnly = false)
	public void save(SmsRecord smsRecord) {
		super.save(smsRecord);
	}

	@Override
	@Transactional(readOnly = false)
	public void delete(SmsRecord smsRecord) {
		super.delete(smsRecord);
	}

	public SmsRecord findRecordByPhone(String phone) {
		return smsRecordDao.findRecordByPhone(phone);
	}

	@Transactional(readOnly = false)
	public Integer cancelOldVerifyCode(String phone) {
		return smsRecordDao.cancelOldVerifyCode(phone);
	}


	public SmsRecord selectByPhoneAndStatus(String phone, String status) {
		return smsRecordDao.selectByPhoneAndStatus(phone,status);
	}

	@Transactional(readOnly = false)
	public Integer insert(SmsRecord record) {
		return smsRecordDao.insert(record);
	}
}