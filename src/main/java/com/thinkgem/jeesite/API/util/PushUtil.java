package com.thinkgem.jeesite.API.util;


import com.thinkgem.jeesite.API.entity.MpTemplateMessage;

import com.thinkgem.jeesite.API.weixin.api.MessageAPI;


import com.thinkgem.jeesite.API.weixin.bean.message.templatemessage.Miniprogram;
import com.thinkgem.jeesite.API.weixin.bean.message.templatemessage.TemplateMessage;
import com.thinkgem.jeesite.API.weixin.bean.message.templatemessage.TemplateMessageItem;
import com.thinkgem.jeesite.API.weixin.bean.message.templatemessage.TemplateMessageResult;
import com.thinkgem.jeesite.API.weixin.support.TokenManager;
import com.thinkgem.jeesite.common.config.Global;

import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.List;

@Component
public class PushUtil {

    @Value("${weixin.appid}")
    private  String wxAppId;


    private  static final String  PAGEPATH="pages/index/index/index";

    public static final String TID_PAY="k4Nv0_ElVklD5ZxCJzeTb1K-GrnQVImpHDPjACowu2s";







    public  TemplateMessageResult push(String openId, String flag, List<String> word){
        TemplateMessage templateMessage=new TemplateMessage();
        String template_id=flag;

        templateMessage.setTemplate_id(template_id);
        //templateMessage.setMiniprogram(new Miniprogram(wxAppId,PAGEPATH));
        templateMessage.setTouser(openId);
        templateMessage.setData(templateMessage(word));

        return MessageAPI.messageTemplateSend(TokenManager.getToken(wxAppId),templateMessage);
    }

    public  LinkedHashMap<String, TemplateMessageItem> templateMessage(List<String> words){
        LinkedHashMap<String, TemplateMessageItem> map=new LinkedHashMap<>();
        for (int a=0;a<words.size();a++) {
            if(a==0){
                map.put("first",new TemplateMessageItem(words.get(a),null));
            }else if(a==words.size()-1){
                map.put("remark",new TemplateMessageItem(words.get(a),null));
            }else{
                map.put("keyword"+a,new TemplateMessageItem(words.get(a),null));
            }
        }
        return map;
    }





}
