package com.thinkgem.jeesite.API.weixin.bean.shorturl;

import com.thinkgem.jeesite.API.weixin.bean.BaseResult;

public class Shorturl extends BaseResult {

    private String short_url;

    public String getShort_url() {
        return short_url;
    }

    public void setShort_url(String short_url) {
        this.short_url = short_url;
    }
}
