/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.resume.dao.other;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.resume.entity.other.StudentResumeOther;
import org.apache.ibatis.annotations.Select;

/**
 * 简历其他DAO接口
 * @author 李金辉
 * @version 2019-01-28
 */
@MyBatisDao
public interface StudentResumeOtherDao extends CrudDao<StudentResumeOther> {

    @Select("SELECT * from a_student_resume_other where student_id=#{0} and del_flag='0'")
    StudentResumeOther getByAccountId(String id);
}