package com.thinkgem.jeesite.API.util.leancloud.aoyi;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class HttpInterfaceExmple {

	public void vSend(boolean isEncry,String public_key, String private_key, String[] mobiles, String smscontext,String extendedcode, Date sendTime,String ip,int port) throws UnsupportedEncodingException {
		Map<String, String> params = new HashMap<String, String>();
		params.put("public_key", public_key);
		SendSmsData data = new SendSmsData(mobiles, smscontext, sendTime,extendedcode);
		String smsdata = JsonHelper.toJsonString(data);
		if(isEncry){
			smsdata = EncryptionUtils.parseByte2HexStr(EncryptionUtils.aesEncrypt(smsdata, private_key));
			params.put("encry", "true");
		}else{
			smsdata = URLEncoder.encode(smsdata, "UTF-8");
			params.put("encry", "false");
		}
		String sign = EncryptionUtils.parseByte2HexStr(EncryptionUtils.aesEncrypt(DateUtil.toString(new Date(), "yyyyMMddHHmmssSSS"), private_key));
		params.put("sms_data", smsdata);
		params.put("sign", sign);
		System.out.println("send sms >>>>>> ");
		System.out.println("\tpublic_key : " + public_key);
		System.out.println("\tsms_data : " + smsdata);
		System.out.println("\tsign : " + sign);
		HttpRequestBody request = new HttpRequestBody("http://"+ip+":"+port+"/ayht-interface/sendsms", "UTF-8", null, null, null, params);
		HttpClient client = new HttpClient(180, 180, false);
		long begin = System.currentTimeMillis();
		HttpResponseBody res = client.service(request);
		long end = System.currentTimeMillis();
		System.out.println("send sms result  >>>>>>\n\t" + res.getResultString());
		System.out.println("send sms use time : " + (end - begin) + "ms\n");
	}


	public static class SendSmsData {

		private String[] mobiles;

		private String smscontent;
		
		private String extendedcode;

		private Date sendtime;

		public SendSmsData(String[] mobiles, String smscontent, Date sendtime, String extendedcode) {
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

	public static void main(String[] args) throws UnsupportedEncodingException {
		//ip
		String ip = "127.0.0.1";
		int port = 8080;
		//公钥
		String pubkey = "AYHT-3003-3D4A-E883";
		//私钥
		String prikey = "33D4AE8831BA4BAB";
		//是否加密
		boolean encry = true;
		//手机号
		String mobiles = "15910761745";
		//内容
		String context = "【奥易互通】：你的验证码为:123456";
		//扩展码
		String  extendedcode = "123456";
		// 定时时间
		//Date sendtime = DateUtil.parseDate("2016-03-15 12:00:00", "yyyy-MM-dd HH:mm:ss"); // 定时发送
		Date sendtime = null; // 非定时发送
		// 发送
		new HttpInterfaceExmple().vSend(encry,pubkey, prikey, mobiles.split(","), context, extendedcode, sendtime,ip,port);

	}

}
