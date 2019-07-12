
package com.thinkgem.jeesite.common.message;

public enum MessageEnum {
	
	TIMESTYLEERROR("时间格式错误",50002,500),
	NULL("请求参数有空",40001,404),
	Success("Success",200,200),
	ErrorVerify("验证码错误",50001,500)
	,ERROR("内部错误,请稍后重试..",500,500)
	,XIAJIA("商户已下架",5001,500)
	,LOGINERROR("用户名或者密码错误",400002,500)
	,REGISTER("该账户已被注册",30001,500),
	NOREGISTER("该账户资料不齐",30002,500),
	NOTHREEREGISTER("该账户资料不齐",30003,500),
	PASSWORD("两次密码不一致",300002,500),
	NOTPHONE("手机号码不存在",300001,500),
	ResponsenuLL("还没有数据",200,500),
	CANCEL("已经被取消",20001,500),
	EXIST("已存在该账号",20002,500),
	PAY_ERROR("支付出现错误，请稍后重试",500006,500),
	ISUSERINFO("该账户已经注册为会员,请更换账号....",30004,500),
	FILEERROR("文件类型不对请上传.png格式的图片",50006,500),
	NOTUSEREXIST("不存在该用户",30007,500),
	TrackEXIST("",200,200),PHOTOSIZE("图片大小超过5M",20002,200),
	PHONENOTBING("未绑定手机号码",90001,200),
	BULANCEERROR("未知错误",80001,200),
	BULANCE_SEND_VERIFY("发送验证码成功",200,200),
	NOT_FIND_ACCOUNT("请登录在修改支付密码",40004,500),
	FAILURE_UPDATE_PASSWORD("修改密码失败",40005,500);
	private String message;
	
	private int code;
	
	private int status;
	private MessageEnum(String message, int code,int status) {
		this.message = message;
		this.code = code;
		this.status=status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
	public void toMessage(Message message){
		if(message.getMessage()==null || "".equals(message.getMessage()))
			message.setMessage(this.message);
		if(message.getStatus()==null || "".equals(message.getStatus()))
			message.setStatus(this.status);
		message.setCode(this.code);
	}
}
