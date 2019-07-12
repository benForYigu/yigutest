package com.thinkgem.jeesite.API.entity;

import com.google.common.base.Strings;
import com.thinkgem.jeesite.API.util.TokenUtils;
import com.thinkgem.jeesite.modules.account.entity.Account;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;


import java.util.Map;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;

/**
 * ClassName: WebSocketInterceptor
 * Function: TODO ADD FUNCTION.
 * date: 2018年1月31日 上午11:42:34
 *
 * @author zhaoshouyun
 * @since JDK 1.7
 */


public class WebSocketInterceptor extends HttpSessionHandshakeInterceptor {

    private final Log log = LogFactory.getLog(getClass());

    TokenUtils tokenUtils=new TokenUtils();
    /**
     * TODO 简单描述该方法的实现功能（可选）.
     *
     * @see org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor#beforeHandshake(org.springframework.http.server.ServerHttpRequest, org.springframework.http.server.ServerHttpResponse, org.springframework.web.socket.WebSocketHandler, java.util.Map)
     */
    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
                                   Map<String, Object> attributes) throws Exception {
        if (request instanceof ServletServerHttpRequest) {
            ServletServerHttpRequest serverHttpRequest = (ServletServerHttpRequest) request;
            //获取参数
            String token = serverHttpRequest.getServletRequest().getParameter("token");
            if(!Strings.isNullOrEmpty(token)&&token.length()>8){
                String id = tokenUtils.getUsernameFromToken(token.substring(7));
                if (!Strings.isNullOrEmpty(id)){
                    attributes.put(SocketHandler.USER_KEY, id);
                    log.info("socket:"+id);
                    return true;
                }
            }
        }

        return false;
    }
}
