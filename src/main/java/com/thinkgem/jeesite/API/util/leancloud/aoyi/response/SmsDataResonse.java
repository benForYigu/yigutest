package com.thinkgem.jeesite.API.util.leancloud.aoyi.response;

public class SmsDataResonse {
    private String code;
    private String massage;
    private String sms_id;
    private Integer total_number;
    private Integer success_number;
    private Integer fail_number;
    private  FailMobiles[] fail_mobiles;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMassage() {
        return massage;
    }

    public void setMassage(String massage) {
        this.massage = massage;
    }

    public String getSms_id() {
        return sms_id;
    }

    public void setSms_id(String sms_id) {
        this.sms_id = sms_id;
    }

    public Integer getTotal_number() {
        return total_number;
    }

    public void setTotal_number(Integer total_number) {
        this.total_number = total_number;
    }

    public Integer getSuccess_number() {
        return success_number;
    }

    public void setSuccess_number(Integer success_number) {
        this.success_number = success_number;
    }

    public Integer getFail_number() {
        return fail_number;
    }

    public void setFail_number(Integer fail_number) {
        this.fail_number = fail_number;
    }

    public FailMobiles[] getFail_mobiles() {
        return fail_mobiles;
    }

    public void setFail_mobiles(FailMobiles[] fail_mobiles) {
        this.fail_mobiles = fail_mobiles;
    }
}
