/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.interaction.web.teachin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.common.base.Strings;
import com.thinkgem.jeesite.modules.account.entity.Account;
import com.thinkgem.jeesite.modules.account.service.AccountService;
import com.thinkgem.jeesite.modules.account.service.accounthr.AccountCompanyhrService;
import com.thinkgem.jeesite.modules.company.entity.Company;
import com.thinkgem.jeesite.modules.company.entity.profession.CompanyProfession;
import com.thinkgem.jeesite.modules.company.service.CompanyService;
import com.thinkgem.jeesite.modules.company.service.profession.CompanyProfessionService;
import com.thinkgem.jeesite.modules.interaction.entity.teachin.InteractionTeachinData;
import com.thinkgem.jeesite.modules.school.entity.School;
import com.thinkgem.jeesite.modules.school.service.SchoolService;

import com.thinkgem.jeesite.modules.sys.security.SystemAuthorizingRealm.Principal;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
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
import com.thinkgem.jeesite.modules.interaction.entity.teachin.InteractionTeachin;
import com.thinkgem.jeesite.modules.interaction.service.teachin.InteractionTeachinService;

/**
 * 宣讲Controller
 * @author 李金辉
 * @version 2019-01-24
 */
@Controller
@RequestMapping(value = "${adminPath}/interaction/teachin/interactionTeachin")
public class InteractionTeachinController extends BaseController {

	@Autowired
	private InteractionTeachinService interactionTeachinService;
	@Autowired
	private AccountService accountService;
	@Autowired
	private AccountCompanyhrService companyhrService;
	@Autowired
	private CompanyService companyService;
	@Autowired
	private CompanyProfessionService professionService;
	@Autowired
	private SchoolService schoolService;

	@ModelAttribute
	public InteractionTeachin get(@RequestParam(required=false) String id) {
		InteractionTeachin entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = interactionTeachinService.get(id);
		}
		if (entity == null){
			entity = new InteractionTeachin();
		}
		return entity;
	}
	
	@RequiresPermissions("interaction:teachin:interactionTeachin:view")
	@RequestMapping(value = {"list", ""})
	public String list(InteractionTeachin interactionTeachin, HttpServletRequest request, HttpServletResponse response, Model model) {
		Principal principal = UserUtils.getPrincipal();
		Account account=accountService.selectByPhone(principal.getLoginName());
		if(account!=null){
			interactionTeachin.setCompanyId(companyhrService.getByAccountId(account.getId()).getCompanyId());
			model.addAttribute("companyUsers", "companyUsers");
		}
		if(Strings.isNullOrEmpty(interactionTeachin.getShelf())){
			interactionTeachin.setShelf("-1");//查询所有
		}
		Page<InteractionTeachin> page = interactionTeachinService.findPage(new Page<InteractionTeachin>(request, response), interactionTeachin);

		model.addAttribute("page", page);
		model.addAttribute("companys", companyService.findList(new Company()));
		model.addAttribute("schools", schoolService.findList(new School()));
		return "modules/interaction/teachin/interactionTeachinList";
	}

	@RequiresPermissions("interaction:teachin:interactionTeachin:view")
	@RequestMapping(value = "form")
	public String form(InteractionTeachin interactionTeachin, Model model) {
		String[] s={""};
		interactionTeachin.setProfessionArray(Strings.isNullOrEmpty(interactionTeachin.getProfession())?s:interactionTeachin.getProfession().split(","));
		/*for (InteractionTeachinData i:interactionTeachin.getInteractionTeachinDataList()) {
			i.setUrl((String[])i.getUrl());
		}*/
		model.addAttribute("interactionTeachin", interactionTeachin);
		CompanyProfession profession=new CompanyProfession();
		profession.setCompanyId(interactionTeachin.getCompanyId());

		model.addAttribute("professions", professionService.findList(profession));
		return "modules/interaction/teachin/interactionTeachinForm";
	}

	@RequiresPermissions("interaction:teachin:interactionTeachin:edit")
	@RequestMapping(value = "save")
	public String save(InteractionTeachin interactionTeachin, Model model, RedirectAttributes redirectAttributes) {
		interactionTeachin.setProfession(StringUtils.join(interactionTeachin.getProfessionArray(),","));

		if (!beanValidator(model, interactionTeachin)){
			return form(interactionTeachin, model);
		}
		interactionTeachinService.save(interactionTeachin);
		addMessage(redirectAttributes, "保存宣讲成功");
		return "redirect:"+Global.getAdminPath()+"/interaction/teachin/interactionTeachin/?repage";
	}
	
	@RequiresPermissions("interaction:teachin:interactionTeachin:edit")
	@RequestMapping(value = "delete")
	public String delete(InteractionTeachin interactionTeachin, RedirectAttributes redirectAttributes) {
		interactionTeachinService.delete(interactionTeachin);
		addMessage(redirectAttributes, "删除宣讲成功");
		return "redirect:"+Global.getAdminPath()+"/interaction/teachin/interactionTeachin/?repage";
	}


}