/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.school.web.ocupy;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sun.tools.corba.se.idl.StringGen;
import com.thinkgem.jeesite.API.util.MyUtils;
import com.thinkgem.jeesite.modules.interaction.service.teachin.InteractionTeachinService;
import com.thinkgem.jeesite.modules.school.entity.School;
import com.thinkgem.jeesite.modules.school.service.SchoolService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.bouncycastle.jce.provider.JCEBlockCipher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.school.entity.ocupy.SchoolTeachinOcupy;
import com.thinkgem.jeesite.modules.school.service.ocupy.SchoolTeachinOcupyService;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

/**
 * 学校宣讲时间Controller
 * @author 李金辉
 * @version 2019-04-12
 */
@Controller
@RequestMapping(value = "${adminPath}/school/ocupy/schoolTeachinOcupy")
public class SchoolTeachinOcupyController extends BaseController {

	@Autowired
	private SchoolTeachinOcupyService schoolTeachinOcupyService;
	@Autowired
	private SchoolService schoolService;
	@Autowired
	private InteractionTeachinService teachinService;

	@ModelAttribute
	public SchoolTeachinOcupy get(@RequestParam(required=false) String id) {
		SchoolTeachinOcupy entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = schoolTeachinOcupyService.get(id);
		}
		if (entity == null){
			entity = new SchoolTeachinOcupy();
		}
		return entity;
	}

	@RequestMapping(value = {"list", ""})
	public String list(SchoolTeachinOcupy schoolTeachinOcupy, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<SchoolTeachinOcupy> page = schoolTeachinOcupyService.findPage(new Page<SchoolTeachinOcupy>(request, response), schoolTeachinOcupy); 
		model.addAttribute("page", page);
		model.addAttribute("schools", schoolService.findList(new School()));
		return "modules/school/ocupy/schoolTeachinOcupyList";
	}


	@RequestMapping(value = "form")
	public String form(SchoolTeachinOcupy schoolTeachinOcupy, Model model) {
		model.addAttribute("schoolTeachinOcupy", schoolTeachinOcupy);
		model.addAttribute("schools", schoolService.findList(new School()));
		return "modules/school/ocupy/schoolTeachinOcupyForm";
	}


	@RequestMapping(value = "save")
	public String save(SchoolTeachinOcupy schoolTeachinOcupy, Model model, RedirectAttributes redirectAttributes) throws ParseException {
		if (!beanValidator(model, schoolTeachinOcupy)){
			return form(schoolTeachinOcupy, model);
		}
		if(schoolTeachinOcupy.getDate().compareTo(schoolTeachinOcupy.getEndDate())<=0){

			String[] str=schoolTeachinOcupy.getTime().split(",");

			while (schoolTeachinOcupy.getDate().compareTo(schoolTeachinOcupy.getEndDate())<=0){
				//没有被购买
				for (String s:str) {
					if(teachinService.isUsedDateTime(schoolTeachinOcupy.getSchoolId(),schoolTeachinOcupy.getDate(),s)==null){
						schoolTeachinOcupy.setTime(s);
						schoolTeachinOcupy.setId(null);
						try{
							schoolTeachinOcupyService.save(schoolTeachinOcupy);
						}catch (Exception e){
							e.printStackTrace();
						}
					}

				}
				schoolTeachinOcupy.setDate(MyUtils.plusDay(1,schoolTeachinOcupy.getDate(),3));
			}
		}



		addMessage(redirectAttributes, "保存学校宣讲时间成功");
		return "redirect:"+Global.getAdminPath()+"/school/ocupy/schoolTeachinOcupy/?repage";
	}
	

	@RequestMapping(value = "delete")
	public String delete(SchoolTeachinOcupy schoolTeachinOcupy, RedirectAttributes redirectAttributes) {
		schoolTeachinOcupyService.ldelete(schoolTeachinOcupy.getId());
		addMessage(redirectAttributes, "删除学校宣讲时间成功");
		return "redirect:"+Global.getAdminPath()+"/school/ocupy/schoolTeachinOcupy/?repage";
	}

	@RequestMapping(value = "isUsed")
	@ResponseBody
	public List<SchoolTeachinOcupy> isUsed(@RequestParam String schoolId, @RequestParam String date)  {
		;
		return schoolTeachinOcupyService.isUsed(schoolId,date);
	}
}