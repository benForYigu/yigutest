/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.dict.dao.position;

import com.thinkgem.jeesite.API.entity.DictLabelDao;
import com.thinkgem.jeesite.common.persistence.TreeDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.dict.entity.position.DictPosition;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 职位DAO接口
 * @author 李金辉
 * @version 2019-02-23
 */
@MyBatisDao
public interface DictPositionDao extends TreeDao<DictPosition> {

    @Select(" select a.id AS id," +
            " a.name AS name," +
            " a.parent_id AS parentId" +
            " from a_dict_position a inner join a_company_profession b on FIND_IN_SET(b.type,a.parent_ids) or a.id=b.type" +
            " where a.del_flag='0' and b.del_flag='0' group by a.id")
    List<DictLabelDao> existProfession();
}