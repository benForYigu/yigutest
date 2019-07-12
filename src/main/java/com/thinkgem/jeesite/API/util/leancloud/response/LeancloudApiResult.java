package com.thinkgem.jeesite.API.util.leancloud.response;

/**
 * LeanCloud API 返回结果
 * @author Shixing
 * @version 1.0
 */
public class LeancloudApiResult {

    private Integer code;
    private String error;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
