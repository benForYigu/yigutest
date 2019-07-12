/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.interaction.web.offer;

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
import com.thinkgem.jeesite.modules.interaction.entity.offer.InteractionOffer;
import com.thinkgem.jeesite.modules.interaction.service.offer.InteractionOfferService;

/**
 * offerController
 * @author 李金辉
 * @version 2019-01-24
 */
@Controller
@RequestMapping(value = "${adminPath}/interaction/offer/interactionOffer")
public class InteractionOfferController extends BaseController {

	@Autowired
	private InteractionOfferService interactionOfferService;
	
	@ModelAttribute
	public InteractionOffer get(@RequestParam(required=false) String id) {
		InteractionOffer entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = interactionOfferService.get(id);
		}
		if (entity == null){
			entity = new InteractionOffer();
		}
		return entity;
	}
	
	@RequiresPermissions("interaction:offer:interactionOffer:view")
	@RequestMapping(value = {"list", ""})
	public String list(InteractionOffer interactionOffer, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<InteractionOffer> page = interactionOfferService.findPage(new Page<InteractionOffer>(request, response), interactionOffer); 
		model.addAttribute("page", page);
		return "modules/interaction/offer/interactionOfferList";
	}

	@RequiresPermissions("interaction:offer:interactionOffer:view")
	@RequestMapping(value = "form")
	public String form(InteractionOffer interactionOffer, Model model) {
		model.addAttribute("interactionOffer", interactionOffer);
		return "modules/interaction/offer/interactionOfferForm";
	}

	@RequiresPermissions("interaction:offer:interactionOffer:edit")
	@RequestMapping(value = "save")
	public String save(InteractionOffer interactionOffer, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, interactionOffer)){
			return form(interactionOffer, model);
		}
		interactionOfferService.save(interactionOffer);
		addMessage(redirectAttributes, "保存offer成功");
		return "redirect:"+Global.getAdminPath()+"/interaction/offer/interactionOffer/?repage";
	}
	
	@RequiresPermissions("interaction:offer:interactionOffer:edit")
	@RequestMapping(value = "delete")
	public String delete(InteractionOffer interactionOffer, RedirectAttributes redirectAttributes) {
		interactionOfferService.delete(interactionOffer);
		addMessage(redirectAttributes, "删除offer成功");
		return "redirect:"+Global.getAdminPath()+"/interaction/offer/interactionOffer/?repage";
	}

}