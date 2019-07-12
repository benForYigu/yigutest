/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.smsrecord.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.smsrecord.entity.SmsRecord;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * 验证码DAO接口
 * @author 李金辉
 * @version 2019-01-30
 */
@MyBatisDao
public interface SmsRecordDao extends CrudDao<SmsRecord> {
    @Select("SELECT * FROM a_sms_record WHERE phone = #{0} and status = 0")
    SmsRecord findRecordByPhone(String phone);

    @Update("UPDATE a_sms_record SET status = 2 WHERE phone = #{0} and status = 0")
    Integer cancelOldVerifyCode(String phone);


    @Select("SELECT * FROM a_sms_record WHERE phone = #{0} and status = #{1}")
    SmsRecord selectByPhoneAndStatus(String phone, String statusWaiting);
}