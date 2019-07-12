/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.dict.web.major;

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
import com.thinkgem.jeesite.modules.dict.entity.major.DictMajor;
import com.thinkgem.jeesite.modules.dict.service.major.DictMajorService;

/**
 * 专业Controller
 * @author 李金辉
 * @version 2019-02-26
 */
@Controller
@RequestMapping(value = "${adminPath}/dict/major/dictMajor")
public class DictMajorController extends BaseController {

	@Autowired
	private DictMajorService dictMajorService;
	
	@ModelAttribute
	public DictMajor get(@RequestParam(required=false) String id) {
		DictMajor entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = dictMajorService.get(id);
		}
		if (entity == null){
			entity = new DictMajor();
		}
		return entity;
	}
	
	@RequiresPermissions("dict:major:dictMajor:view")
	@RequestMapping(value = {"list", ""})
	public String list(DictMajor dictMajor, HttpServletRequest request, HttpServletResponse response, Model model) {
		List<DictMajor> list = dictMajorService.findList(dictMajor); 
		model.addAttribute("list", list);
		return "modules/dict/major/dictMajorList";
	}

	@RequiresPermissions("dict:major:dictMajor:view")
	@RequestMapping(value = "form")
	public String form(DictMajor dictMajor, Model model) {
		if (dictMajor.getParent()!=null && StringUtils.isNotBlank(dictMajor.getParent().getId())){
			dictMajor.setParent(dictMajorService.get(dictMajor.getParent().getId()));
			// 获取排序号，最末节点排序号+30
			if (StringUtils.isBlank(dictMajor.getId())){
				DictMajor dictMajorChild = new DictMajor();
				dictMajorChild.setParent(new DictMajor(dictMajor.getParent().getId()));
				List<DictMajor> list = dictMajorService.findList(dictMajor); 
				if (list.size() > 0){
					dictMajor.setSort(list.get(list.size()-1).getSort());
					if (dictMajor.getSort() != null){
						dictMajor.setSort(dictMajor.getSort() + 30);
					}
				}
			}
		}
		if (dictMajor.getSort() == null){
			dictMajor.setSort(30);
		}
		model.addAttribute("dictMajor", dictMajor);
		return "modules/dict/major/dictMajorForm";
	}

	@RequiresPermissions("dict:major:dictMajor:edit")
	@RequestMapping(value = "save")
	public String save(DictMajor dictMajor, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, dictMajor)){
			return form(dictMajor, model);
		}
		dictMajorService.save(dictMajor);
		addMessage(redirectAttributes, "保存专业成功");
		return "redirect:"+Global.getAdminPath()+"/dict/major/dictMajor/?repage";
	}
	
	@RequiresPermissions("dict:major:dictMajor:edit")
	@RequestMapping(value = "delete")
	public String delete(DictMajor dictMajor, RedirectAttributes redirectAttributes) {
		dictMajorService.delete(dictMajor);
		addMessage(redirectAttributes, "删除专业成功");
		return "redirect:"+Global.getAdminPath()+"/dict/major/dictMajor/?repage";
	}


	@ResponseBody
	@RequestMapping(value = "treeData")
	public List<Map<String, Object>> treeData(@RequestParam(required=false) String extId, HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		List<DictMajor> list = dictMajorService.findList(new DictMajor());
		for (int i=0; i<list.size(); i++){
			DictMajor e = list.get(i);
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