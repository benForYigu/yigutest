package com.thinkgem.jeesite.API.entity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;


/**
 * Project Name:springRabbitMQ
 * File Name:WebSocketConfig.java
 * Package Name:com.zsy.websocket
 * Date:2018年1月31日下午1:10:33
 * Copyright (c) 2018, zhaoshouyun All Rights Reserved.
 *
 */


import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

/**
 * ClassName: WebSocketConfig

 */

@Component
@EnableWebSocket
@Configuration
@EnableWebMvc
public class WebSocketConfig implements WebSocketConfigurer {

    @Autowired
    private SocketHandler socketHandler;
    /**
     * 注册handle
     *
     * @see org.springframework.web.socket.config.annotation.WebSocketConfigurer#registerWebSocketHandlers(org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry)
     */
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(myHandler(), "/api/auth/socket").addInterceptors(new WebSocketInterceptor()).setAllowedOrigins("*");
        registry.addHandler(myHandler(), "/api/auth/socketjs").addInterceptors(new WebSocketInterceptor()).withSockJS();

    }


    public WebSocketHandler myHandler() {

      return socketHandler;
    }

}
