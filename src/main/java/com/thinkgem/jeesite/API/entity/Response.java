package com.thinkgem.jeesite.API.entity;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.github.pagehelper.PageInfo;
import com.thinkgem.jeesite.API.util.MyUtils;
import com.thinkgem.jeesite.common.utils.SpringContextHolder;
import com.thinkgem.jeesite.modules.sys.dao.DictDao;
import org.omg.CORBA.OBJ_ADAPTER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import java.util.List;

/**
 * 接口返回值格式定义.
 * <p>
 * 接口返回均为JSON格式, 形式为: {code: XXX, message:, YYY, data: ZZZ}
 * 其中code为错误码, message为简单的错误描述, data为请求成功时返回的所需数据.
 * 注意: message不能作为前端显示错误的凭据, 该值仅为开发调试使用.
 * <p>
 * Created by xingfinal on 15/11/27.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Component
public class Response<T> {


    private long code;

    private String message;


    private T data;

    public Response(T data) {
        this(Code.SUCCESS, data);



        //同统一设置标签
        if(data instanceof List){
            data= (T) MyUtils.setLabelList((List)data);
        }else if(data instanceof PageInfo){
            ((PageInfo) data).setList(MyUtils.setLabelList(((PageInfo) data).getList()));
        } else{
            data= (T) MyUtils.setLabel(data);
        }
        if(data!=null &&data.getClass().equals(Code.class)){
            Code code=(Code)data;
            this.code=code.getCode();
            this.message=code.getMessage();
            this.data=null;
        }
        if(data==null){
            this.data=(T) new JSONObject();
        }

    }

    public Response(Code code) {
        this(code.getCode(), code.getMessage(), null);
    }

    public Response(Code code, T data) {
        this(code.getCode(), code.getMessage(), data);
    }

    public Response(long code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public long getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }
}
