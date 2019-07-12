/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.interaction.service.entry;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.interaction.entity.entry.InteractionEntry;
import com.thinkgem.jeesite.modules.interaction.dao.entry.InteractionEntryDao;

/**
 * 入职秀Service
 * @author 李金辉
 * @version 2019-01-24
 */
@Service
@Transactional(readOnly = true)
public class InteractionEntryService extends CrudService<InteractionEntryDao, InteractionEntry> {

	public InteractionEntry get(String id) {
		return super.get(id);
	}
	
	public List<InteractionEntry> findList(InteractionEntry interactionEntry) {
		return super.findList(interactionEntry);
	}
	
	public Page<InteractionEntry> findPage(Page<InteractionEntry> page, InteractionEntry interactionEntry) {
		return super.findPage(page, interactionEntry);
	}
	
	@Transactional(readOnly = false)
	public void save(InteractionEntry interactionEntry) {
		super.save(interactionEntry);
	}
	
	@Transactional(readOnly = false)
	public void delete(InteractionEntry interactionEntry) {
		super.delete(interactionEntry);
	}
	
}