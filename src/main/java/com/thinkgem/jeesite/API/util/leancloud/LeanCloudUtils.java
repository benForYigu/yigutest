package com.thinkgem.jeesite.API.util.leancloud;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.thinkgem.jeesite.API.util.Constant;
import com.thinkgem.jeesite.API.util.leancloud.request.ValidateSmsRequest;
import com.thinkgem.jeesite.API.weixin.util.JsonUtil;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * LeanCloud 短信验证工具类
 *
 * @author Shixing
 * @version 1.0
 */
@Component
public class LeanCloudUtils {
    private final Logger logger = LoggerFactory.getLogger(LeanCloudUtils.class);
    /**
     * 结果值
     */
    public static final int RESULT_SUCCESS = 0; // 成功
    public static final int RESULT_FAIL = 1; // 失败

    @Value("${leancloud.appId}")
    private String appId;

    @Value("${leancloud.appKey}")
    private String appKey;

    @Value("${leancloud.domain}")
    private String domain;

    @Value("${leancloud.api.sms.send}")
    private String apiSendSms;

    @Value("${leancloud.api.sms.validate}")
    private String apiValidateSms;

    @Value("${leancloud.api.function.call}")
    private String functionCall;

    private static OkHttpClient okHttpClient = new OkHttpClient();

    /**
     * 发送短信验证码
     *
     * @param phone 手机号
     * @return 发送结果
     */
    public String sendSms(String phone, String code,String smstemplate) {
        if(code!=null)
            smstemplate=String.format(smstemplate, code);
        FormBody body = new FormBody.Builder()
                .add("user", Constant.SMS_SEND_USERNAME)
                .add("passwd", Constant.SMS_SEND_PASSWORD)
                .add("msg", smstemplate)
                .add("mobs", phone)
                .build();

        Request request = new Request.Builder()
                .url(Constant.SMS_SEND_URL)
                .post(body)
                .build();

        try {
            Response response = okHttpClient.newCall(request).execute();
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 验证短信验证码
     *
     * @param phone 手机号
     * @param code  验证码
     * @return 发送结果
     */
    public String validateSms(String phone, String code) {
        String url = domain + apiValidateSms + code;

        ValidateSmsRequest validateSmsRequest = new ValidateSmsRequest();
        validateSmsRequest.setMobilePhoneNumber(phone);

        return executeHttpRequest(JsonUtil.toJSONString(validateSmsRequest), url);
    }

    public Map<String, String> callCloudFunction(String functionName, String json) {
        String url = domain + functionCall + functionName;

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),
                json);
        Request request = new Request.Builder()
                .url(url)
                .addHeader("X-LC-Id", appId)
                .addHeader("X-LC-Key", appKey)
                .post(requestBody)
                .build();

        System.out.print("responserequest"+request);

        try {
            Response response = okHttpClient.newCall(request).execute();

            System.out.print("responselvshukai"+response);
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(response.body().string(), HashMap.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 执行HTTP请求，并返回结果
     * 该方法暂时只能用在发送短信和验证短信使用
     *
     * @param json 请求的JSON字符串
     * @param url  请求地址
     * @return 请求结果，0=成功，1=失败
     */
    private String executeHttpRequest(String json, String url) {
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),
                json);
        Request request = new Request.Builder()
                .url(url)
                .addHeader("X-LC-Id", appId)
                .addHeader("X-LC-Key", appKey)
                .post(requestBody)
                .build();

        String resultString;
        try {
            Response response = okHttpClient.newCall(request).execute();
            String res = response.body().string();
            Map map = new ObjectMapper().readValue(res, HashMap.class);
            // Leancloud服务在调用成功, 且不需要返回值的接口中, 仅返回一个空的JSON

            if (map.isEmpty()) {
                resultString = String.valueOf(RESULT_SUCCESS);
                return resultString;
            }

            logger.info("错误请求结果: " + res);
            resultString = String.valueOf(RESULT_FAIL) + res;
            return resultString;
        } catch (IOException e) {
            e.printStackTrace();
            resultString = String.valueOf(RESULT_FAIL);
            return resultString;
        }
    }

}
