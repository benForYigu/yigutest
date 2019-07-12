package com.thinkgem.jeesite.API.util.leancloud.aoyi;


import com.thinkgem.jeesite.API.util.Constant;
import com.thinkgem.jeesite.API.util.leancloud.LeanCloudUtils;
import com.thinkgem.jeesite.API.util.leancloud.aoyi.request.SmsData;
import com.thinkgem.jeesite.API.util.leancloud.aoyi.response.SmsDataResonse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class AoLeanCloudUtils {

    private final Logger logger = LoggerFactory.getLogger(LeanCloudUtils.class);
    public SmsDataResonse sendSms(String phone, String code, String smstemplate, boolean isEncry)throws UnsupportedEncodingException {
        if(code!=null)
            smstemplate=String.format(smstemplate, code);
        String [] mobiles={phone};
        StringBuilder sb=new StringBuilder();
        Map<String, String> params = new HashMap<String, String>();
        String sign = EncryptionUtils.parseByte2HexStr(EncryptionUtils.
                aesEncrypt(DateUtil.toString(new Date(), "yyyyMMddHHmmssSSS"), Constant.AOYI_SEND_PRIVATE_KEY));
        sb.append("sign="+sign);
        sb.append("&public_key="+Constant.AOYI_SMS_SEND_PUBLIC_KEY);
       // params.put("public_key", Constant.AOYI_SMS_SEND_PUBLIC_KEY);
       // params.put("sign", sign);
        SmsData data = new SmsData(mobiles,smstemplate,null,null);
        String smsdata = JsonHelper.toJsonString(data);
        if(isEncry){
            smsdata = EncryptionUtils.parseByte2HexStr(EncryptionUtils.aesEncrypt(smsdata,Constant.AOYI_SEND_PRIVATE_KEY));
           // params.put("sms_data", smsdata);
            sb.append("&sms_data="+smsdata);
            sb.append("&encry=true");
            //params.put("encry", "true");
        }else{
            smsdata = URLEncoder.encode(smsdata, "UTF-8");
           // params.put("sms_data", smsdata);
            //params.put("encry", "false");
            sb.append("&sms_data="+smsdata);
            sb.append("&encry=false");
        }

        HttpRequestBody request =new HttpRequestBody(Constant.AOYI_SMS_SEND_URL,"UTF-8", sb.toString(),
                null,null,null);

        HttpClient client = new HttpClient(180, 180, false);
        HttpResponseBody res = client.service(request);
        SmsDataResonse smsDataResonse=JsonHelper.fromJson(SmsDataResonse.class,res.getResultString());

        return smsDataResonse;
    }

}
