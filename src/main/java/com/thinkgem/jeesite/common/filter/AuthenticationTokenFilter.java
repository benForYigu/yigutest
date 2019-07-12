package com.thinkgem.jeesite.common.filter;

import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JacksonAnnotation;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thinkgem.jeesite.API.entity.Code;
import com.thinkgem.jeesite.API.entity.DictLabelDao;
import com.thinkgem.jeesite.API.entity.Response;
import com.thinkgem.jeesite.API.util.TokenUtils;
import com.thinkgem.jeesite.API.weixin.util.JsonUtil;
import com.thinkgem.jeesite.common.config.Global;

import com.thinkgem.jeesite.common.mapper.JsonMapper;
import com.thinkgem.jeesite.modules.account.dao.AccountDao;
import com.thinkgem.jeesite.modules.account.entity.Account;

import com.thinkgem.jeesite.modules.account.entity.accounthr.AccountCompanyhr;
import com.thinkgem.jeesite.modules.company.entity.Company;
import com.thinkgem.jeesite.modules.sys.entity.Dict;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.method.P;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 身份过滤器.
 * 通过Header中的X-Auth-Token项, jwt校验.
 * <p>
 * 此过滤器需要在跨域过滤器过滤后执行.
 */
@Component
@Order(2)
public class AuthenticationTokenFilter extends GenericFilterBean {
    private Log log = LogFactory.getLog(getClass());


    private String tokenHeader = Global.getConfig("token.header");
    private String apiPath = Global.getConfig("apiPath");
    private String companyImg = Global.getConfig("company.img");
    private String companyLogo = Global.getConfig("company.logo");
    private String site="schoolappserver";


    private TokenUtils tokenUtils = new TokenUtils();

    @Autowired
    AccountDao accountDao;

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        final HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        // 不过滤OPTIONS请求
        if (request.getMethod().equalsIgnoreCase(HttpMethod.OPTIONS.toString())) {
            chain.doFilter(req, res);
            return;
        }
        /*  //开发阶段不需要验证token
        if (false) {
            chain.doFilter(req, res);
            return;
        }*/

        // 过滤不需要token的请求
        String uri = request.getRequestURI();
        System.out.println("请求地址uri================" + uri+"    方式:"+request.getMethod());

        if(isUriNotApiNeedToken(uri)){
            chain.doFilter(req, res);
            return;
        }

        if(!uri.startsWith("/"+site+"/"+apiPath)) {
            if (isUriNotApiNeedToken(uri)) {
                chain.doFilter(req, res);
                return;
            }
            // X-Auth-Token携带确认
            final String authHeader = request.getHeader(tokenHeader);
            log.info("Auth Header: " + authHeader);

            if ((authHeader == null || !authHeader.startsWith("Bearer "))) {
                if (uri.startsWith("/"+site+apiPath+"/user/user")) {
                    response.setHeader("Content-type","application/json;charset=UTF-8");
                    AccountCompanyhr companyhr=new AccountCompanyhr();
                    companyhr.setAvatar(companyImg);
                    companyhr.setRealname("游客");
                    //companyhr.setStatus("2");
                    //response.getWriter().write(JSON.toJSONString(new Response(companyhr)));
                    response.getWriter().write(JsonMapper.toJsonString(new Response(companyhr)));
                }else if (uri.startsWith("/"+site+apiPath+"/company/company")) {
                    response.setHeader("Content-type","application/json;charset=UTF-8");
                    Company company=new Company();
                    company.setName("游客");
                    company.setLogo(companyLogo);
                    //company.setStatus("2");
                    response.getWriter().write(JsonMapper.toJsonString(new Response(company)));
                }else{
                    response.setStatus(HttpStatus.FORBIDDEN.value());
                    response.setContentType("application/json;charset=UTF-8");
                }
                /*response.setHeader("Content-type","application/json;charset=UTF-8");
                response.getWriter().write(JSON.toJSONString(new Response(Code.API_NOT_REG)));*/
                return;
            }
            final String token = authHeader.substring(7); // The part after "Bearer "

            // 管理员相关接口进行AdminTokenFilter
            Account account = verifyKhXx(response, token);
            if (account != null) {
                request.setAttribute("account", account);
            } else {
                return;
            }
        }
        log.info("结束认证进行执行代码");
        chain.doFilter(req, res);
    }

    private Account verifyKhXx(HttpServletResponse response, String token) throws IOException{
        String id = tokenUtils.getUsernameFromToken(token);
        Account account = accountDao.get(id);
        if (account == null) {
            response.setStatus(HttpStatus.FORBIDDEN.value());
            return null;
        }

        if (!checkKhXxStatus(account, response)
                || !checkKhXxStatus(account, response)) {
            return null;
        }

        // token校验
        if (!tokenUtils.validateToken(token, id)) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return null;
        }

        String status = account.getStatus();
            if (status.equals(Account.STATUS_DISABLE)) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return null;
        }

        return account;
    }

    private boolean checkKhXxStatus(Account account, HttpServletResponse response) throws IOException {
        if (!account.getStatus().equals(Account.STATUS_DISABLE)) {
            return true;
        }
        response.setStatus(HttpStatus.OK.value());
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");

        ObjectMapper mapper = new ObjectMapper();
        String result = mapper.writeValueAsString(new Response(Code.API_ACCOUNT_LOCKED));

        PrintWriter writer = null;
        try {
            writer = response.getWriter();
            writer.write(result);
        } finally {
            if (writer != null) {
                writer.close();
            }
        }

        return false;
    }


   private boolean isUriNotApiNeedToken(final String uri) {

       if (uri.startsWith("/"+site+apiPath+"/auth")) {
           return true;
       }
       if (uri.startsWith("/"+site+apiPath+"/vip/notify")) {
           return true;
       }
       if (uri.startsWith("/"+site+apiPath+"/teachin/teachinPayNotify")) {
           return true;
       }
       if (uri.startsWith("/"+site+apiPath+"/recommend/recommend")) {
           return true;
       }
       if (uri.startsWith("/"+site+apiPath+"/teachin/isUsed")) {
           return true;
       }
       if (uri.startsWith("/"+site+apiPath+"/vip/right")) {
           return true;
       }


       if (!uri.startsWith("/"+site+apiPath)) {
           return true;
       }

        return false;

    }
}

