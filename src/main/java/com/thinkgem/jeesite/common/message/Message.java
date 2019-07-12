
package com.thinkgem.jeesite.common.message;
/**
 * 返回消息结果
 * @author Owen
 *
 */
public class Message{
	
	private String message;
	private Integer code;
	private Integer status;
	private long pageTotal;
	private int pageSize;
	private int pageNo;
	//登录或修改密码验证验证码时使用
	private String token;
	private Object datas;
	
	public long getPageTotal() {
		return pageTotal;
	}
	public void setPageTotal(long pageTotal) {
		this.pageTotal = pageTotal;
	}
	public Object getDatas() {
		return datas;
	}
	public void setDatas(Object datas) {
		this.datas = datas;
	}
	public Message(String message, Integer code) {
		super();
		this.message = message;
		this.code = code;
	}
	public Message() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getMessage() {
		return message;
	}
	/**
	 * 
	 * @param message
	 */
	public void setMessage(String message) {
		this.message=message;
		
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getPageNo() {
		return pageNo;
	}
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = "Bearer "+token;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
}
