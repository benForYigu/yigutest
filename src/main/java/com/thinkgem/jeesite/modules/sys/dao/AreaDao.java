/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.dao;

import com.thinkgem.jeesite.API.entity.DictLabelDao;
import com.thinkgem.jeesite.common.persistence.TreeDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.sys.entity.Area;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * 区域DAO接口
 * @author ThinkGem
 * @version 2014-05-16
 */
@MyBatisDao
public interface AreaDao extends TreeDao<Area> {

    /*@Select("select a.id AS id," +
            " a.name AS name," +
            " a.parent_id AS parentId" +
            " from sys_area a inner join a_company_profession b on FIND_IN_SET(b.city,a.parent_ids) or a.id=b.city" +
            " where a.type!='3' and  a.del_flag='0' and b.del_flag='0' group by a.id")*/
    @Select("<script>" +
            "SELECT" +
            " a.id AS id," +
            " a. NAME AS NAME," +
            " a.parent_id AS parentId" +
            " FROM" +
            " sys_area a inner join " +
            " (SELECT" +
            " a.id AS id," +
            " a. NAME AS NAME," +
            " a.parent_id AS parentId," +
            " a.parent_ids AS parentIds," +
            " b.name as dname" +
            " FROM" +
            " sys_area a" +
            " INNER JOIN a_company_profession b ON " +
            " a.id = b.city " +
            " WHERE" +
            "  a.del_flag = '0'" +
            " AND b.del_flag = '0'" +
            " " +
            " GROUP BY" +
            " a.id) " +
            "d on a.id=d.id OR FIND_IN_SET( a.id,d.parentIds)" +
            " where a.type!='3'" +

            "<if test='parent!=null and parent.id!=&quot;&quot; and parent.id!=null'>"+
            " and a.parent_id=#{parent.id}" +
            "</if>"+
            "<if test='type!=&quot;&quot; and type!=null'>"+
            "  and a.type=#{type}" +
            "</if>"+

            " GROUP BY" +
            " a.id order by a.id" +
            " </script>")
    List<DictLabelDao> professionExistArea(Area area);

   /*@Select("<script>" +
           "SELECT " +
           "  a.id AS id, " +
           "  a. NAME AS NAME, " +
           "  a.parent_id AS parentId " +
           " FROM " +
           " sys_area a inner join  " +
           " (SELECT " +
           "  a.id AS id, " +
           "  a. NAME AS NAME, " +
           "  a.parent_id AS parentId, " +
           " a.parent_ids AS parentIds, " +
           " b.name as dname " +
           " FROM " +
           "  sys_area a " +
           " INNER JOIN a_school b ON  " +
           " a.id = b.city  " +
           " WHERE " +
           "   a.del_flag = '0' " +
           " AND b.del_flag = '0' " +
           "  " +
           " GROUP BY " +
           "  a.id) d on a.id=d.id OR FIND_IN_SET( a.id,d.parentIds)" +
           " where a.type!='3' " +
           "<if test='parent!=null and parent.id!=&quot;&quot; and parent.id!=null'>"+
           " and a.parent_id=#{parent.id}" +
           "</if>"+
           "<if test='type!=&quot;&quot; and type!=null'>"+
           " and a.type=#{type}" +
           "</if>"+
           " GROUP BY" +
           " a.id order by a.id" +
           " </script>")*/
    @Select("SELECT DISTINCT r.province_id AS id," +
            " r.province_name AS name," +
            " r.province_parent_id AS parentId" +
            " FROM (" +
            " SELECT a_city.id AS city_id," +
            " a_city.parent_id AS city_parent_id," +
            " a_city.`name` AS city_name," +
            " IFNULL(a_province.id,a_city.id) AS province_id," +
            " IFNULL(a_province.`name`,a_city.`name`) AS province_name," +
            " IFNULL(a_province.parent_id,a_city.parent_id) AS province_parent_id," +
            " IFNULL(a_province.sort,a_city.sort) AS province_sort" +
            " LEFT JOIN sys_area a_city on s.city=a_city.id" +
            " FROM (SELECT DISTINCT city from a_school where del_flag = 0 and city is not null) s" +
            "LEFT JOIN sys_area a_city on s.city=a_city.id" +
            "LEFT JOIN sys_area a_province on a_city.parent_id=a_province.id" +
            ") r ORDER BY r.province_sort")
    List<Map<String, Object>> findAllByExistSchool();

   @Select("<script>" +
           "SELECT " +
           "  a.id AS id, " +
           "  a. NAME AS NAME, " +
           "  a.parent_id AS parentId " +
           " FROM " +
           " sys_area a where type!='3' and del_flag='0' " +
           "<if test='parent!=null and parent.id!=&quot;&quot; and parent.id!=null'>"+
           " and a.parent_id=#{parent.id}" +
           "</if>"+
           "<if test='type!=&quot;&quot; and type!=null'>"+
           " and a.type=#{type}" +
           "</if>"+
           " </script>")
    List<DictLabelDao> not3Area(Area area);
}
