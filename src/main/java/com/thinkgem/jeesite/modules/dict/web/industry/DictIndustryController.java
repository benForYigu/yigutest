/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.dict.web.industry;

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
import com.thinkgem.jeesite.modules.dict.entity.industry.DictIndustry;
import com.thinkgem.jeesite.modules.dict.service.industry.DictIndustryService;

/**
 * 行业Controller
 * @author 李金辉
 * @version 2019-02-23
 */
@Controller
@RequestMapping(value = "${adminPath}/dict/industry/dictIndustry")
public class DictIndustryController extends BaseController {

	@Autowired
	private DictIndustryService dictIndustryService;
	
	@ModelAttribute
	public DictIndustry get(@RequestParam(required=false) String id) {
		DictIndustry entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = dictIndustryService.get(id);
		}
		if (entity == null){
			entity = new DictIndustry();
		}
		return entity;
	}
	
	@RequiresPermissions("dict:industry:dictIndustry:view")
	@RequestMapping(value = {"list", ""})
	public String list(DictIndustry dictIndustry, HttpServletRequest request, HttpServletResponse response, Model model) {
		List<DictIndustry> list = dictIndustryService.findList(dictIndustry); 
		model.addAttribute("list", list);
		return "modules/dict/industry/dictIndustryList";
	}

	@RequiresPermissions("dict:industry:dictIndustry:view")
	@RequestMapping(value = "form")
	public String form(DictIndustry dictIndustry, Model model) {
		if (dictIndustry.getParent()!=null && StringUtils.isNotBlank(dictIndustry.getParent().getId())){
			dictIndustry.setParent(dictIndustryService.get(dictIndustry.getParent().getId()));
			// 获取排序号，最末节点排序号+30
			if (StringUtils.isBlank(dictIndustry.getId())){
				DictIndustry dictIndustryChild = new DictIndustry();
				dictIndustryChild.setParent(new DictIndustry(dictIndustry.getParent().getId()));
				List<DictIndustry> list = dictIndustryService.findList(dictIndustry); 
				if (list.size() > 0){
					dictIndustry.setSort(list.get(list.size()-1).getSort());
					if (dictIndustry.getSort() != null){
						dictIndustry.setSort(dictIndustry.getSort() + 30);
					}
				}
			}
		}
		if (dictIndustry.getSort() == null){
			dictIndustry.setSort(30);
		}
		model.addAttribute("dictIndustry", dictIndustry);
		return "modules/dict/industry/dictIndustryForm";
	}

	@RequiresPermissions("dict:industry:dictIndustry:edit")
	@RequestMapping(value = "save")
	public String save(DictIndustry dictIndustry, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, dictIndustry)){
			return form(dictIndustry, model);
		}
		dictIndustryService.save(dictIndustry);
		addMessage(redirectAttributes, "保存行业成功");
		return "redirect:"+Global.getAdminPath()+"/dict/industry/dictIndustry/?repage";
	}
	
	@RequiresPermissions("dict:industry:dictIndustry:edit")
	@RequestMapping(value = "delete")
	public String delete(DictIndustry dictIndustry, RedirectAttributes redirectAttributes) {
		dictIndustryService.delete(dictIndustry);
		addMessage(redirectAttributes, "删除行业成功");
		return "redirect:"+Global.getAdminPath()+"/dict/industry/dictIndustry/?repage";
	}

	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "treeData")
	public List<Map<String, Object>> treeData(@RequestParam(required=false) String extId, HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		List<DictIndustry> list = dictIndustryService.findList(new DictIndustry());
		for (int i=0; i<list.size(); i++){
			DictIndustry e = list.get(i);
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