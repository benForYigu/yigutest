package com.thinkgem.jeesite.common.listener;


import com.thinkgem.jeesite.API.weixin.support.TokenManager;
import com.thinkgem.jeesite.common.config.Global;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

@Component
public class TokenManagerListener implements ServletContextListener {


    private String wxAppId = Global.getConfig("weixin.appid");


    private String wxAppSecret = Global.getConfig("weixin.appsecret");
    private String wxAppIdP = Global.getConfig("weixin.appidP");


    private String wxAppSecretP = Global.getConfig("weixin.appsecretP");

    @Value("${weixin.mp:#{null}}")
    private String mp;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("初始化TokenManager");

      //小程序
        TokenManager.init(wxAppId, wxAppSecret);
      //公众号
        TokenManager.init(wxAppIdP, wxAppSecretP);

    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

        // WEB容器  关闭时调用
        TokenManager.destroyed();
    }
}
