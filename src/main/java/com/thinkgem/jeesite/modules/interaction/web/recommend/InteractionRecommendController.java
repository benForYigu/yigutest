/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.interaction.web.recommend;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thinkgem.jeesite.modules.school.entity.School;
import com.thinkgem.jeesite.modules.school.service.SchoolService;
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
import com.thinkgem.jeesite.modules.interaction.entity.recommend.InteractionRecommend;
import com.thinkgem.jeesite.modules.interaction.service.recommend.InteractionRecommendService;

/**
 * 推荐位Controller
 * @author 李金辉
 * @version 2019-01-24
 */
@Controller
@RequestMapping(value = "${adminPath}/interaction/recommend/interactionRecommend")
public class InteractionRecommendController extends BaseController {

	@Autowired
	private InteractionRecommendService interactionRecommendService;
	@Autowired
	private SchoolService schoolService;

	@ModelAttribute
	public InteractionRecommend get(@RequestParam(required=false) String id) {
		InteractionRecommend entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = interactionRecommendService.get(id);
		}
		if (entity == null){
			entity = new InteractionRecommend();
		}
		return entity;
	}
	
	@RequiresPermissions("interaction:recommend:interactionRecommend:view")
	@RequestMapping(value = {"list", ""})
	public String list(InteractionRecommend interactionRecommend, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<InteractionRecommend> page = interactionRecommendService.findPage(new Page<InteractionRecommend>(request, response), interactionRecommend); 
		model.addAttribute("page", page);
		return "modules/interaction/recommend/interactionRecommendList";
	}

	@RequiresPermissions("interaction:recommend:interactionRecommend:view")
	@RequestMapping(value = "form")
	public String form(InteractionRecommend interactionRecommend, Model model) {
		model.addAttribute("interactionRecommend", interactionRecommend);
		model.addAttribute("schools", schoolService.findList(new School()));
		return "modules/interaction/recommend/interactionRecommendForm";
	}

	@RequiresPermissions("interaction:recommend:interactionRecommend:edit")
	@RequestMapping(value = "save")
	public String save(InteractionRecommend interactionRecommend,
					   Model model,
					   RedirectAttributes redirectAttributes,
	String applyTos) {
		logger.info(applyTos);
		interactionRecommend.setApplyTo(applyTos);
		if (!beanValidator(model, interactionRecommend)){
			return form(interactionRecommend, model);
		}
		interactionRecommendService.save(interactionRecommend);
		addMessage(redirectAttributes, "保存推荐位成功");
		return "redirect:"+Global.getAdminPath()+"/interaction/recommend/interactionRecommend/?repage";
	}
	
	@RequiresPermissions("interaction:recommend:interactionRecommend:edit")
	@RequestMapping(value = "delete")
	public String delete(InteractionRecommend interactionRecommend, RedirectAttributes redirectAttributes) {
		interactionRecommendService.delete(interactionRecommend);
		addMessage(redirectAttributes, "删除推荐位成功");
		return "redirect:"+Global.getAdminPath()+"/interaction/recommend/interactionRecommend/?repage";
	}

}