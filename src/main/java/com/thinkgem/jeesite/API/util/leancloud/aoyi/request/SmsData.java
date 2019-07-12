package com.thinkgem.jeesite.API.util.leancloud.aoyi.request;

import java.util.Date;

/**
 * 奥易短信
 */
public class SmsData {

    private String [] mobiles;
    private String smscontent;
    private String extendedcode;
    private Date sendtime;

    public SmsData(String [] mobiles, String smscontent, String extendedcode, Date sendtime) {
        this.mobiles = mobiles;
        this.smscontent = smscontent;
        this.extendedcode = extendedcode;
        this.sendtime = sendtime;
    }

    public String[] getMobiles() {
        return mobiles;
    }

    public void setMobiles(String[] mobiles) {
        this.mobiles = mobiles;
    }

    public String getSmscontent() {
        return smscontent;
    }

    public void setSmscontent(String smscontent) {
        this.smscontent = smscontent;
    }

    public String getExtendedcode() {
        return extendedcode;
    }

    public void setExtendedcode(String extendedcode) {
        this.extendedcode = extendedcode;
    }

    public Date getSendtime() {
        return sendtime;
    }

    public void setSendtime(Date sendtime) {
        this.sendtime = sendtime;
    }
}
