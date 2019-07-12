/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.interaction.teachin.dao.chat;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.interaction.teachin.entity.chat.Chat;
import com.thinkgem.jeesite.modules.interaction.teachin.entity.chat.InteractionTeachinChat;

import java.util.List;

/**
 * 宣讲聊天DAO接口
 * @author 李金辉
 * @version 2019-03-20
 */
@MyBatisDao
public interface InteractionTeachinChatDao extends CrudDao<InteractionTeachinChat> {
	List<Chat> findLists(Chat chat);
}