package com.thinkgem.jeesite.API.entity;


/**
 * 错误信息
 */
public class FailMobiles {

    private String fail_code;

    private String mobile;

    public String getFail_code() {
        return fail_code;
    }

    public void setFail_code(String fail_code) {
        this.fail_code = fail_code;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}