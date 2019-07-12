/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.dict.web.position;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.dict.entity.position.DictPosition;
import com.thinkgem.jeesite.modules.dict.service.position.DictPositionService;

/**
 * 职位Controller
 * @author 李金辉
 * @version 2019-02-23
 */
@Controller
@RequestMapping(value = "${adminPath}/dict/position/dictPosition")
public class DictPositionController extends BaseController {

	@Autowired
	private DictPositionService dictPositionService;
	
	@ModelAttribute
	public DictPosition get(@RequestParam(required=false) String id) {
		DictPosition entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = dictPositionService.get(id);
		}
		if (entity == null){
			entity = new DictPosition();
		}
		return entity;
	}
	
	@RequiresPermissions("dict:position:dictPosition:view")
	@RequestMapping(value = {"list", ""})
	public String list(DictPosition dictPosition, HttpServletRequest request, HttpServletResponse response, Model model) {
		List<DictPosition> list = dictPositionService.findList(dictPosition); 
		model.addAttribute("list", list);
		return "modules/dict/position/dictPositionList";
	}

	@RequiresPermissions("dict:position:dictPosition:view")
	@RequestMapping(value = "form")
	public String form(DictPosition dictPosition, Model model) {
		if (dictPosition.getParent()!=null && StringUtils.isNotBlank(dictPosition.getParent().getId())){
			dictPosition.setParent(dictPositionService.get(dictPosition.getParent().getId()));
			// 获取排序号，最末节点排序号+30
			if (StringUtils.isBlank(dictPosition.getId())){
				DictPosition dictPositionChild = new DictPosition();
				dictPositionChild.setParent(new DictPosition(dictPosition.getParent().getId()));
				List<DictPosition> list = dictPositionService.findList(dictPosition); 
				if (list.size() > 0){
					dictPosition.setSort(list.get(list.size()-1).getSort());
					if (dictPosition.getSort() != null){
						dictPosition.setSort(dictPosition.getSort() + 30);
					}
				}
			}
		}
		if (dictPosition.getSort() == null){
			dictPosition.setSort(30);
		}
		model.addAttribute("dictPosition", dictPosition);
		return "modules/dict/position/dictPositionForm";
	}

	@RequiresPermissions("dict:position:dictPosition:edit")
	@RequestMapping(value = "save")
	public String save(DictPosition dictPosition, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, dictPosition)){
			return form(dictPosition, model);
		}
		dictPositionService.save(dictPosition);
		addMessage(redirectAttributes, "保存职位成功");
		return "redirect:"+Global.getAdminPath()+"/dict/position/dictPosition/?repage";
	}
	
	@RequiresPermissions("dict:position:dictPosition:edit")
	@RequestMapping(value = "delete")
	public String delete(DictPosition dictPosition, RedirectAttributes redirectAttributes) {
		dictPositionService.delete(dictPosition);
		addMessage(redirectAttributes, "删除职位成功");
		return "redirect:"+Global.getAdminPath()+"/dict/position/dictPosition/?repage";
	}

	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "treeData")
	public List<Map<String, Object>> treeData(@RequestParam(required=false) String extId, HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		List<DictPosition> list = dictPositionService.findList(new DictPosition());
		for (int i=0; i<list.size(); i++){
			DictPosition e = list.get(i);
			if (StringUtils.isBlank(extId) || (extId!=null && !extId.equals(e.getId()) && e.getParentIds().indexOf(","+extId+",")==-1)){
				Map<String, Object> map = Maps.newHashMap();
				map.put("id", e.getId());
				map.put("pId", e.getParentId());
				map.put("name", e.getName());
				mapList.add(map);
			}
		}
		return mapList;
	}
	
}