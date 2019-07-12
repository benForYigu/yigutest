/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.dict.dao.major;

import com.thinkgem.jeesite.common.persistence.TreeDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.dict.entity.major.DictMajor;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 专业DAO接口
 * @author 李金辉
 * @version 2019-02-26
 */
@MyBatisDao
public interface DictMajorDao extends TreeDao<DictMajor> {


}