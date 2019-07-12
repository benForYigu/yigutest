/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.common.base.Strings;
import com.thinkgem.jeesite.API.entity.DictLabelDao;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.account.entity.accountstudent.AccountStudentinfo;
import com.thinkgem.jeesite.modules.company.dao.profession.CompanyProfessionDao;
import com.thinkgem.jeesite.modules.company.entity.profession.CompanyProfession;
import com.thinkgem.jeesite.modules.dict.dao.industry.DictIndustryDao;
import com.thinkgem.jeesite.modules.dict.dao.major.DictMajorDao;
import com.thinkgem.jeesite.modules.dict.dao.position.DictPositionDao;
import com.thinkgem.jeesite.modules.dict.entity.industry.DictIndustry;
import com.thinkgem.jeesite.modules.dict.entity.major.DictMajor;
import com.thinkgem.jeesite.modules.dict.entity.position.DictPosition;
import com.thinkgem.jeesite.modules.interaction.dao.teachin.InteractionTeachinDao;
import com.thinkgem.jeesite.modules.sys.dao.AreaDao;
import com.thinkgem.jeesite.modules.sys.entity.Area;


import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.thinkgem.jeesite.common.mapper.JsonMapper;
import com.thinkgem.jeesite.common.utils.CacheUtils;
import com.thinkgem.jeesite.common.utils.SpringContextHolder;
import com.thinkgem.jeesite.modules.sys.dao.DictDao;
import com.thinkgem.jeesite.modules.sys.entity.Dict;

/**
 * 字典工具类
 *
 * @author ThinkGem
 * @version 2013-5-29
 */
public class DictUtils {

    private static DictDao dictDao = SpringContextHolder.getBean(DictDao.class);
    private static CompanyProfessionDao professionDao = SpringContextHolder.getBean(CompanyProfessionDao.class);
    private static DictMajorDao majorDao = SpringContextHolder.getBean(DictMajorDao.class);
    private static DictPositionDao positionDao = SpringContextHolder.getBean(DictPositionDao.class);
    private static DictIndustryDao industryDao = SpringContextHolder.getBean(DictIndustryDao.class);
    private static AreaDao areaDao = SpringContextHolder.getBean(AreaDao.class);

    public static final String CACHE_DICT_MAP = "dictMap";
    public static final String MY_DICT_MAP = "myDictMap";



    public static String getNewDictLabel(String value, String type, String defaultValue) {
        if (StringUtils.isNotBlank(type) && StringUtils.isNotBlank(value)) {
            for (Dict dict : getDictList(type)) {
                if (type.equals(dict.getType()) && value.equals(dict.getValue())) {
                    return dict.getLabel();
                }
            }
        }
        return defaultValue;
    }

    public static String getProfessionLabel(String id) {
        CompanyProfession companyProfession=professionDao.get(id);
        if(companyProfession!=null){
            return companyProfession.getName();
        }else{
           return id;
        }
    }

    public static String getDictLabel(String value, String type, String defaultValue) {

        if (StringUtils.isNotBlank(type) && StringUtils.isNotBlank(value)) {
            for (Dict dict : getDictList(type)) {
                if (type.equals(dict.getType()) && value.equals(dict.getValue())) {
                    return dict.getLabel();
                }
            }
        }
        return defaultValue;
    }

    //自定义返回对象
    public static DictLabelDao getDictLabelObject(Object value, String type, String defaultValue) {
        Map<String, List<Dict>> dictMap = (Map<String, List<Dict>>) CacheUtils.get(CACHE_DICT_MAP);
        if (dictMap == null) {
            dictMap = Maps.newHashMap();
            for (Dict dict : dictDao.findAllList(new Dict())) {
                List<Dict> dictList = dictMap.get(dict.getType());
                if (dictList != null) {
                    dictList.add(dict);
                } else {
                    dictMap.put(dict.getType(), Lists.newArrayList(dict));
                }
            }
            CacheUtils.put(CACHE_DICT_MAP, dictMap);
        }
        DictLabelDao label = new DictLabelDao();

        List<Dict> dictList = dictMap.get(type);
        if (dictList == null) {
            dictList = Lists.newArrayList();
        }
        if (value == null) {
            return new DictLabelDao();
        }
        if (!StringUtils.isNumeric((String) value)) {
            label.setId((String) value);
            label.setName((String) value);
        } else {



            for (Dict dd : dictList) {
                if (dd.getValue().equals((String) value)) {
                    label.setId(dd.getValue());
                    label.setName(dd.getLabel());

                    return label;
                }
            }


            label.setId((String) value);
            label.setName((String) value);
            return label;


        }
        return label;
        /*if(value==null){
			return new DictLabelDao();
		}
		Dict dict=new Dict();
		dict.setType(type);
		dict.setValue(value instanceof String?(String)value:String.valueOf(value));
		DictLabelDao label=dictDao.getByType(dict);
		if(label==null){
			label=new DictLabelDao();
			label.setId(value instanceof String?(String)value:String.valueOf(value));
			label.setName(label.getId());
		}


		return label;*/
    }

    public static String getDictLabels(String values, String type, String defaultValue) {
        if (StringUtils.isNotBlank(type) && StringUtils.isNotBlank(values)) {
            List<String> valueList = Lists.newArrayList();
            for (String value : StringUtils.split(values, ",")) {
                valueList.add(getDictLabel(value, type, defaultValue));
            }
            return StringUtils.join(valueList, ",");
        }
        return defaultValue;
    }

    public static String getDictValue(String label, String type, String defaultLabel) {
        if (StringUtils.isNotBlank(type) && StringUtils.isNotBlank(label)) {
            for (Dict dict : getDictList(type)) {
                if (type.equals(dict.getType()) && label.equals(dict.getLabel())) {
                    return dict.getValue();
                }
            }
        }
        return defaultLabel;
    }

    public static List<Dict> getDictList(String type) {
        @SuppressWarnings("unchecked")
        Map<String, List<Dict>> dictMap = (Map<String, List<Dict>>) CacheUtils.get(CACHE_DICT_MAP);
        if (dictMap == null) {
            dictMap = Maps.newHashMap();
            for (Dict dict : dictDao.findAllList(new Dict())) {
                List<Dict> dictList = dictMap.get(dict.getType());
                if (dictList != null) {
                    dictList.add(dict);
                } else {
                    dictMap.put(dict.getType(), Lists.newArrayList(dict));
                }
            }
            CacheUtils.put(CACHE_DICT_MAP, dictMap);
        }
        List<Dict> dictList = dictMap.get(type);
        if (dictList == null) {
            dictList = Lists.newArrayList();
        }
        return dictList;
    }

    public static List<DictLabelDao> getDictLabelList(String type) {
        Dict dict = new Dict();
        dict.setType(type);
        return dictDao.labelList(dict);
    }

    /**
     * 返回字典列表（JSON）
     *
     * @param type
     * @return
     */
    public static String getDictListJson(String type) {
        return JsonMapper.toJsonString(getDictList(type));
    }

    public static String getAreaLabel(String id) {
        for (DictLabelDao dict:getMyDictMap("my_area")) {
            if(dict.getId().equals(id)){
                return dict.getName();
            }
        }
        return id;

        /*Area area = areaDao.get(id);
        return area == null ? id : area.getName();*/
    }

    public static String getMajorLabel(String id) {
        for (DictLabelDao dict:getMyDictMap("my_major")) {
            if(dict.getId().equals(id)){
                return dict.getName();
            }
        }
        return id;

        /*DictMajor major = majorDao.get(id);
        return major == null ? id : major.getName();*/
    }

    public static String getpositionLabel(String id) {
        for (DictLabelDao dict:getMyDictMap("my_position")) {
            if(dict.getId().equals(id)){
                return dict.getName();
            }
        }
        return id;

        /*DictPosition o = positionDao.get(id);
        return o == null ? id : o.getName();*/
    }

    public static String getindustryLabel(String id) {
        for (DictLabelDao dict:getMyDictMap("my_industry")) {
            if(dict.getId().equals(id)){
                return dict.getName();
            }
        }
        return id;

        /*DictIndustry o = industryDao.get(id);
        return o == null ? id : o.getName();*/
    }

    /*public static String getareaLabel(String id) {
        Area o = areaDao.get(id);
        return o == null ? id : o.getName();
    }*/

    public static List<DictLabelDao> getMyDictMap(String label) {
        Map<String, List<DictLabelDao>> dictMap = (Map<String, List<DictLabelDao>>) CacheUtils.get(MY_DICT_MAP);
        if (dictMap==null || dictMap.get(label) == null) {

            dictMap = Maps.newHashMap();
            List<DictLabelDao> labelDaos=new ArrayList<>();
           if("my_area".equals(label)){
               labelDaos=areaDao.labelList(new Area());
           }else if("my_major".equals(label)){
               labelDaos=majorDao.labelList(new DictMajor());
           }else if("my_position".equals(label)){
               labelDaos=positionDao.labelList(new DictPosition());
            }else if("my_industry".equals(label)){
               labelDaos=industryDao.labelList(new DictIndustry());
           }
            for (DictLabelDao dict : labelDaos) {
                List<DictLabelDao> dictList = dictMap.get(label);
                if (dictList != null) {
                    dictList.add(dict);
                } else {
                    dictMap.put(label, Lists.newArrayList(dict));
                }
            }
            CacheUtils.put(MY_DICT_MAP, dictMap);
        }
        return dictMap.get(label);
    }


}
