package com.thinkgem.jeesite.API.util.leancloud.request;

/**
 * 验证短信验证码
 * @author Shixing
 * @version 1.0
 */
public class ValidateSmsRequest {

    private String mobilePhoneNumber;

    public String getMobilePhoneNumber() {
        return mobilePhoneNumber;
    }

    public void setMobilePhoneNumber(String mobilePhoneNumber) {
        this.mobilePhoneNumber = mobilePhoneNumber;
    }
}
