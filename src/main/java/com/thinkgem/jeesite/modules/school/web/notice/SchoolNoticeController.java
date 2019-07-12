/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.school.web.notice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.school.entity.notice.SchoolNotice;
import com.thinkgem.jeesite.modules.school.service.notice.SchoolNoticeService;

/**
 * 学习公告Controller
 * @author 李金辉
 * @version 2019-01-29
 */
@Controller
@RequestMapping(value = "${adminPath}/school/notice/schoolNotice")
public class SchoolNoticeController extends BaseController {

	@Autowired
	private SchoolNoticeService schoolNoticeService;
	
	@ModelAttribute
	public SchoolNotice get(@RequestParam(required=false) String id) {
		SchoolNotice entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = schoolNoticeService.get(id);
		}
		if (entity == null){
			entity = new SchoolNotice();
		}
		return entity;
	}
	
	/*@RequiresPermissions("school:notice:schoolNotice:view")*/
	@RequestMapping(value = {"list", ""})
	public String list(SchoolNotice schoolNotice, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<SchoolNotice> page = schoolNoticeService.findPage(new Page<SchoolNotice>(request, response), schoolNotice); 
		model.addAttribute("page", page);
		return "modules/school/notice/schoolNoticeList";
	}

	/*@RequiresPermissions("school:notice:schoolNotice:view")*/
	@RequestMapping(value = "form")
	public String form(SchoolNotice schoolNotice, Model model) {
		model.addAttribute("schoolNotice", schoolNotice);
		return "modules/school/notice/schoolNoticeForm";
	}

	/*@RequiresPermissions("school:notice:schoolNotice:edit")*/
	@RequestMapping(value = "save")
	public String save(SchoolNotice schoolNotice, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, schoolNotice)){
			return form(schoolNotice, model);
		}
		schoolNoticeService.save(schoolNotice);
		addMessage(redirectAttributes, "保存学习公告成功");
		return "redirect:"+Global.getAdminPath()+"/school/notice/schoolNotice/?repage&schoolId="+schoolNotice.getSchoolId();
	}
	
	/*@RequiresPermissions("school:notice:schoolNotice:edit")*/
	@RequestMapping(value = "delete")
	public String delete(SchoolNotice schoolNotice, RedirectAttributes redirectAttributes) {
		schoolNoticeService.delete(schoolNotice);
		addMessage(redirectAttributes, "删除学习公告成功");
		return "redirect:"+Global.getAdminPath()+"/school/notice/schoolNotice/?repage&schoolId="+schoolNotice.getSchoolId();
	}

}