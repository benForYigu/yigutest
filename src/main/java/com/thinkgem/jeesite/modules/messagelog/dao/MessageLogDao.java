/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.messagelog.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.messagelog.entity.MessageLog;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * 消息记录DAO接口
 * @author 李金辉
 * @version 2019-01-28
 */
@MyBatisDao
public interface MessageLogDao extends CrudDao<MessageLog> {




    @Update("update a_message_log set is_read='1' " +
            " where " +
            " student_id=#{0} " +
            " and company_profession_id=#{1}" +
            " and (type='2' or type='4')" +
            " ")
    Integer updateStudentRead(String id, String companyProfessionId);

    @Update("update a_message_log set is_read='1' " +
            " where " +
            " student_id=#{0} " +
            " and company_profession_id=#{1}" +
            " and (type='1' or type='3')" +
            " ")
    Integer updateHrRead(String id, String companyProfessionId);

    @Select("<script>" +
            " select b.* from (SELECT" +
            " a.id AS 'id'," +
            " a.student_id AS 'studentId'," +
            " e.avatar AS 'student.avatar'," +
            " e.realname AS 'student.realname'," +
            " a.company_id AS 'companyId'," +
            " a.offer_id AS 'offerId'," +
            " a.type AS 'type'," +
            "  a.company_profession_id AS 'companyProfessionId'," +
            " a.teachin_id AS 'teachinId'," +
            " a.interview_id AS 'interviewId'," +
            " a.interview_type AS 'interviewType'," +
            "  a.interview_time AS 'interviewTime'," +
            " a.change_time AS 'changeTime'," +
            " a.department AS 'department'," +
            " a.is_read AS 'isRead'," +
            " a.create_by AS 'createBy.id'," +
            " a.create_date AS 'createDate'," +
            " a.update_by AS 'updateBy.id'," +
            " a.update_date AS 'updateDate'," +
            " a.remarks AS 'remarks'," +
            " a.del_flag AS 'delFlag'," +
            "  b.avatar AS 'companyhr.avatar'," +
            " b.realname AS 'companyhr.realname'," +
            "  c. NAME AS 'company.name'," +
            " c.logo AS 'company.logo'," +
            " d. NAME AS 'profession.name'" +
            " FROM" +
            " a_message_log a" +
            " LEFT JOIN a_account_companyhr b ON a.company_id = b.company_id" +
            " AND b.del_flag = '0'" +
            " LEFT JOIN a_account_studentinfo e ON a.student_id = e.account_id" +
            " AND e.del_flag = '0'" +
            "  LEFT JOIN a_company c ON a.company_id = c.id" +
            " AND c.del_flag = '0'" +
            " LEFT JOIN a_company_profession d ON a.company_profession_id = d.id" +
            " AND d.del_flag = '0'" +
            " WHERE" +
            " a.del_flag = '0'" +
            "<if test='companyId!=null'>"+
            " AND a.company_id = #{companyId} " +
            "</if>"+
            "<if test='studentId!=null'>"+
            " AND a.student_id = #{studentId} " +
            "</if>"+
            " ORDER BY" +
            " a.type DESC)  b" +
            " GROUP BY" +
            " b.studentId," +
            " b.companyProfessionId" +
            "</script>")
    List<MessageLog> findMList(MessageLog messageLog);

    @Select("select * from a_message_log where interview_id=#{0} limit 1")
    MessageLog getByInterveiwId(String interviewId);



}