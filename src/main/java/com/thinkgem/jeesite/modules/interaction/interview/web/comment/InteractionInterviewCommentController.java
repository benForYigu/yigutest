/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.interaction.interview.web.comment;

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
import com.thinkgem.jeesite.modules.interaction.interview.entity.comment.InteractionInterviewComment;
import com.thinkgem.jeesite.modules.interaction.interview.service.comment.InteractionInterviewCommentService;

/**
 * 面试评论Controller
 * @author 李金辉
 * @version 2019-01-30
 */
@Controller
@RequestMapping(value = "${adminPath}/interaction.interview/comment/interactionInterviewComment")
public class InteractionInterviewCommentController extends BaseController {

	@Autowired
	private InteractionInterviewCommentService interactionInterviewCommentService;
	
	@ModelAttribute
	public InteractionInterviewComment get(@RequestParam(required=false) String id) {
		InteractionInterviewComment entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = interactionInterviewCommentService.get(id);
		}
		if (entity == null){
			entity = new InteractionInterviewComment();
		}
		return entity;
	}
	
	@RequiresPermissions("interaction.interview:comment:interactionInterviewComment:view")
	@RequestMapping(value = {"list", ""})
	public String list(InteractionInterviewComment interactionInterviewComment, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<InteractionInterviewComment> page = interactionInterviewCommentService.findPage(new Page<InteractionInterviewComment>(request, response), interactionInterviewComment); 
		model.addAttribute("page", page);
		return "modules/interaction.interview/comment/interactionInterviewCommentList";
	}

	@RequiresPermissions("interaction.interview:comment:interactionInterviewComment:view")
	@RequestMapping(value = "form")
	public String form(InteractionInterviewComment interactionInterviewComment, Model model) {
		model.addAttribute("interactionInterviewComment", interactionInterviewComment);
		return "modules/interaction.interview/comment/interactionInterviewCommentForm";
	}

	@RequiresPermissions("interaction.interview:comment:interactionInterviewComment:edit")
	@RequestMapping(value = "save")
	public String save(InteractionInterviewComment interactionInterviewComment, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, interactionInterviewComment)){
			return form(interactionInterviewComment, model);
		}
		interactionInterviewCommentService.save(interactionInterviewComment);
		addMessage(redirectAttributes, "保存面试评论成功");
		return "redirect:"+Global.getAdminPath()+"/interaction.interview/comment/interactionInterviewComment/?repage";
	}
	
	@RequiresPermissions("interaction.interview:comment:interactionInterviewComment:edit")
	@RequestMapping(value = "delete")
	public String delete(InteractionInterviewComment interactionInterviewComment, RedirectAttributes redirectAttributes) {
		interactionInterviewCommentService.delete(interactionInterviewComment);
		addMessage(redirectAttributes, "删除面试评论成功");
		return "redirect:"+Global.getAdminPath()+"/interaction.interview/comment/interactionInterviewComment/?repage";
	}

}