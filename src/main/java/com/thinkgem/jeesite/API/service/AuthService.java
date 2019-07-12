/*
package com.thinkgem.jeesite.API.service;

import com.google.common.base.Strings;
import com.thinkgem.jeesite.API.entity.Code;
import com.thinkgem.jeesite.API.entity.Response;
import com.thinkgem.jeesite.API.util.MyUtils;
import com.thinkgem.jeesite.API.util.PushUtil;
import com.thinkgem.jeesite.API.util.PushUtils;
import com.thinkgem.jeesite.API.util.TokenUtils;
import com.thinkgem.jeesite.API.weixin.bean.sns.SnsToken;
import com.thinkgem.jeesite.common.utils.IdGen;
import com.thinkgem.jeesite.common.utils.TimeUtils;
import com.thinkgem.jeesite.modules.coupon.dao.SpYhqDao;
import com.thinkgem.jeesite.modules.coupon.entity.SpYhq;
import com.thinkgem.jeesite.modules.coupon.service.SpYhqService;
import com.thinkgem.jeesite.modules.coustomer.dao.KhXxDao;
import com.thinkgem.jeesite.modules.coustomer.dao.yhq.KhYhqDao;
import com.thinkgem.jeesite.modules.coustomer.entity.KhXx;
import com.thinkgem.jeesite.modules.coustomer.entity.yhq.KhYhq;
import com.thinkgem.jeesite.modules.coustomer.service.yhq.KhYhqService;
import com.thinkgem.jeesite.modules.invite.dao.KhinviteDao;
import com.thinkgem.jeesite.modules.invite.entity.Khinvite;
import com.thinkgem.jeesite.modules.util.KhXwUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

*/
/**
 * 用户Service
 * @author xfcy
 *//*

@Service
@Transactional(readOnly = true)
public class AuthService {

    @Autowired
    private KhXxDao khXxDao;
    @Autowired
    private SpYhqDao spYhqDao;
    @Autowired
    private TokenUtils tokenUtils;
    @Autowired
    private KhYhqService khYhqService;
    @Autowired
    private KhYhqDao khYhqDao;
    @Autowired
    private KhinviteDao khinviteDao;
    @Autowired
    private PushUtils pushUtils;

    @Transactional(readOnly = false)
    public Response login(SnsToken token,String userId) throws ParseException {
        KhXx khXx=khXxDao.getKhXx(token.getOpenid());
        //没有注册为新用户
        if(khXx==null){
            khXx = new KhXx();
            khXx.setOpenId(token.getOpenid());
            khXx.setUnionId(token.getUnionid());
            khXx.setStatus(KhXx.STATUS_NORMAL);
            khXx.setId(IdGen.randomBase62(30));
            khXx.setFollow("");
            khXx.setCreateDate(new Date());
            khXx.setUpdateDate(new Date());
            khXx.setType("1");
            khXx.setJf(0);
            khXxDao.insert(khXx);
            //添加客户行为
            KhXwUtil.addXw("1",khXx.getId(),token.getOpenid(),null);
            String jwtToken = tokenUtils.generateToken(khXx.getId() , "");
            khXx.setToken("Bearer " + jwtToken);

            //判断是否推荐用户 1用户id不能为空  2不能为本人 3邀请人存在
            KhXx bykhxx=khXxDao.get(userId);
            if(!Strings.isNullOrEmpty(userId)&&!userId.equals(khXx.getId())&&bykhxx!=null){
                //添加邀请记录
                Khinvite khinvite=new Khinvite();
                khinvite.setKhid(userId);
                khinvite.setBykhid(khXx.getId());
                khinvite.preInsert();
                khinviteDao.insert(khinvite);

                KhYhq khYhq=new KhYhq();
                khYhq.setKhid(userId);
                khYhq.setSource(KhYhq.SOURCE_INVITE);
                khYhq.setSourceId(khinvite.getId());
                SpYhq spYhq=new SpYhq();
                spYhq.setType(SpYhq.TYPE_INVITE);
                String yhq="";
                for (SpYhq s:spYhqDao.findList(spYhq)) {
                    //
                    khYhq.setYhqid(s.getId());
                    if("1".equals(s.getYhSxlx())){
                        khYhq.setStartDate(s.getYhStart());
                        khYhq.setEndDate(s.getYhEnd());
                    }else{
                        khYhq.setStartDate(new Date());
                        khYhq.setEndDate(MyUtils.plusDay(Integer.valueOf(s.getYhRsx()),3));
                    }
                    khYhq.setStatus(KhYhq.STATUS_NORMAL);
                    khYhq.preInsert();
                    khYhqDao.insert(khYhq);
                    yhq+=s.getYhName()+" ";
                }

                //推送
                List words = new ArrayList();
                words.add(bykhxx.getWxnc());
                words.add(yhq);
                words.add(TimeUtils.formatTime(new Date()));
                pushUtils.push(khXxDao.get(userId).getOpenId(), PushUtils.TID_INVITE, words,null);


            }
            return new Response<>(khXx);
        }else{
            String jwtToken = tokenUtils.generateToken(khXx.getId() , "");
            khXx.setToken("Bearer " + jwtToken);
            if (khXx.getStatus().equals(KhXx.STATUS_DISABLE)) {
                return new Response<>(Code.API_ACCOUNT_BLOCKED);
            }
        }
        return new Response(khXx);
    }

    */
/**
     * 添加用户昵称头像
     *
     * @return
     *//*

    @Transactional(readOnly = false)
    public Response updateUser(HttpServletRequest request, KhXx kh) {
        KhXx khXx = (KhXx) request.getAttribute("khXx");
        //保存个人信息
        if(Strings.isNullOrEmpty(kh.getWxtx())||Strings.isNullOrEmpty(kh.getWxnc())){
            if(!Strings.isNullOrEmpty(kh.getSj())){
                khXx.setSj(kh.getSj());
            }
            if(!Strings.isNullOrEmpty(kh.getSjb())){
                khXx.setSjb(kh.getSjb());
            }
            if(!Strings.isNullOrEmpty(kh.getXm())){
                khXx.setXm(kh.getXm());
            }
            if(!Strings.isNullOrEmpty(kh.getEmail())){
                khXx.setEmail(kh.getEmail());
            }
            if(!Strings.isNullOrEmpty(kh.getCompanyName())){
                khXx.setCompanyName(kh.getCompanyName());
            }
            if(!Strings.isNullOrEmpty(kh.getPosition())){
                khXx.setPosition(kh.getPosition());
            }
            if(!Strings.isNullOrEmpty(kh.getProvince())){
                khXx.setProvince(kh.getProvince());
            }
            if(!Strings.isNullOrEmpty(kh.getCity())){
                khXx.setCity(kh.getCity());
            }
            if(!Strings.isNullOrEmpty(kh.getArea())){
                khXx.setArea(kh.getArea());
            }
            if(!Strings.isNullOrEmpty(kh.getAddress())){
                khXx.setAddress(kh.getAddress());
            }
            if(!Strings.isNullOrEmpty(kh.getCode())){
                khXx.setCode(kh.getCode());
            }

            //保存图像昵称
        }else{
            khXx.setWxtx(kh.getWxtx());
            khXx.setWxnc(kh.getWxnc());
        }
        khXx.setUpdateDate(new Date());
        return new Response(khXxDao.update(khXx));
    }

    */
/**
     * 获取客户信息
     * @return
     *//*

    public Response getKhInfo(String id,Date yhDate){
        KhXx khXx = khXxDao.getKhXxById(id);
        khXx.setYhq(khYhqService.findYhqListShuLiang(id,yhDate));
        return new Response(khXx);
    }



}
*/
