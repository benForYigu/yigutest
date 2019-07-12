/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.resume.service.prefer;

import java.util.List;

import com.google.common.base.Strings;
import com.thinkgem.jeesite.API.entity.Code;
import com.thinkgem.jeesite.API.entity.Response;
import com.thinkgem.jeesite.common.utils.IdGen;
import com.thinkgem.jeesite.modules.account.entity.Account;
import com.thinkgem.jeesite.modules.resume.entity.experience.StudentResumeExperience;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.resume.entity.prefer.StudentResumePrefer;
import com.thinkgem.jeesite.modules.resume.dao.prefer.StudentResumePreferDao;

/**
 * 简历意向Service
 *
 * @author 李金辉
 * @version 2019-01-28
 */
@Service
@Transactional(readOnly = true)
public class StudentResumePreferService extends CrudService<StudentResumePreferDao, StudentResumePrefer> {

    public StudentResumePrefer get(String id) {
        return super.get(id);
    }

    public List<StudentResumePrefer> findList(StudentResumePrefer studentResumePrefer) {
        return super.findList(studentResumePrefer);
    }

    public Page<StudentResumePrefer> findPage(Page<StudentResumePrefer> page, StudentResumePrefer studentResumePrefer) {
        return super.findPage(page, studentResumePrefer);
    }

    @Transactional(readOnly = false)
    public void save(StudentResumePrefer studentResumePrefer) {
        super.save(studentResumePrefer);
    }

    @Transactional(readOnly = false)
    public void delete(StudentResumePrefer studentResumePrefer) {
        super.delete(studentResumePrefer);
    }

    @Transactional(readOnly = false)
    public Object uPrefer(Account account, String id, String positionId, String professionId, String city, String salary) {
        StudentResumePrefer studentResumePrefer = dao.get(id);
        if (studentResumePrefer == null) {
            return Code.API_PARRAM_ERROR;
        }
        if (!account.getId().equals(studentResumePrefer.getStudentId())) {
            return Code.API_PARRAM_ERROR;
        }
        studentResumePrefer.setPositionId(positionId);
        studentResumePrefer.setProfessionId(professionId);
        studentResumePrefer.setCity(city);
        studentResumePrefer.setSalary(salary);
        studentResumePrefer.preUpdate();
        dao.update(studentResumePrefer);
        return Code.SUCCESS;
    }

    @Transactional(readOnly = false)
    public Object prefer(Account account, String positionId, String professionId, String city, String salary) {
        if (!Account.ROLE_STUDENT.equals(account.getRole())) {
            return Code.API_USER_ROLE_ERROR;
        }
        StudentResumePrefer studentResumePrefer = new StudentResumePrefer();
        studentResumePrefer.setStudentId(account.getId());
        studentResumePrefer.setPositionId(positionId);
        studentResumePrefer.setProfessionId(professionId);
        studentResumePrefer.setCity(city);
        studentResumePrefer.setSalary(salary);
        studentResumePrefer.preInsert();
        dao.insert(studentResumePrefer);
        return Code.SUCCESS;
    }

    //获取简历意向
    public Object gprefer(Account account, String preferId) {
        if (Strings.isNullOrEmpty(preferId)) {
            StudentResumePrefer prefer = new StudentResumePrefer();
            prefer.setStudentId(account.getId());
            return dao.findList(prefer);
        } else {
            return dao.get(preferId);
        }
    }


}