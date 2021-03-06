package com.thinkgem.jeesite.API.weixin.api;


import com.thinkgem.jeesite.API.weixin.bean.ticket.Ticket;
import com.thinkgem.jeesite.API.weixin.client.LocalHttpClient;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;

/**
 * JSAPI ticket
 *
 * @author LiYi
 */
public class TicketAPI extends BaseAPI {

    /**
     * 获取 jsapi_ticket
     *
     * @param access_token
     * @return
     */
    public static Ticket ticketGetticket(String access_token) {
        HttpUriRequest httpUriRequest = RequestBuilder.post()
                .setUri(BASE_URI + "/cgi-bin/ticket/getticket")
                .addParameter("access_token", access_token)
                .addParameter("type", "jsapi")
                .build();
        return LocalHttpClient.executeJsonResult(httpUriRequest, Ticket.class);
    }
}
