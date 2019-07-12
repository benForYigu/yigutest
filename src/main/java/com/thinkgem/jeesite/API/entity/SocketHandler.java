package com.thinkgem.jeesite.API.entity;

import com.alibaba.fastjson.JSON;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.thinkgem.jeesite.API.util.MyUtils;
import com.thinkgem.jeesite.modules.account.dao.AccountDao;
import com.thinkgem.jeesite.modules.account.dao.accounthr.AccountCompanyhrDao;
import com.thinkgem.jeesite.modules.account.dao.accountschool.AccountTeacherinfoDao;
import com.thinkgem.jeesite.modules.account.dao.accountstudent.AccountStudentinfoDao;
import com.thinkgem.jeesite.modules.account.entity.Account;
import com.thinkgem.jeesite.modules.account.entity.accounthr.AccountCompanyhr;
import com.thinkgem.jeesite.modules.account.entity.accountschool.AccountTeacherinfo;
import com.thinkgem.jeesite.modules.account.entity.accountstudent.AccountStudentinfo;
import com.thinkgem.jeesite.modules.company.dao.CompanyDao;
import com.thinkgem.jeesite.modules.company.entity.Company;
import com.thinkgem.jeesite.modules.interaction.dao.interview.InteractionInterviewDao;
import com.thinkgem.jeesite.modules.interaction.dao.teachin.InteractionTeachinDao;
import com.thinkgem.jeesite.modules.interaction.entity.interview.InteractionInterview;
import com.thinkgem.jeesite.modules.interaction.entity.teachin.InteractionTeachin;
import com.thinkgem.jeesite.modules.interaction.teachin.dao.chat.InteractionTeachinChatDao;
import com.thinkgem.jeesite.modules.interaction.teachin.entity.chat.Chat;
import com.thinkgem.jeesite.modules.interaction.teachin.entity.chat.InteractionTeachinChat;
import com.thinkgem.jeesite.modules.messagelog.dao.MessageLogDao;
import com.thinkgem.jeesite.modules.messagelog.entity.MessageLog;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;


import java.io.IOException;
import java.util.*;
import java.util.concurrent.*;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

/**
 * ClassName: MyMessageHandler
 * Function: 实现webscoket接口
 *
 */

@Component
public class SocketHandler implements WebSocketHandler {
    @Autowired
    AccountDao accountDao;
    @Autowired
    AccountCompanyhrDao companyhrDao;
    @Autowired
    AccountStudentinfoDao studentinfoDao;
    @Autowired
    AccountTeacherinfoDao teacherinfoDao;
    @Autowired
    InteractionTeachinChatDao chatDao;
    @Autowired
    InteractionInterviewDao interviewDao;
    @Autowired
    CompanyDao companyDao;

    @Autowired
    InteractionTeachinDao teachinDao;
    @Autowired
    ThreadPoolTaskExecutor threadPool;
    @Autowired
    MessageLogDao messageLogDao;
    @Value("${push.key}")
    String pushKey;
    @Value("${pull.key}")
    String pullKey;
    @Value("${push.url}")
    String pushUrl;
    @Value("${pull.url}")
    String pullUrl;


    //用户key
    public static final String USER_KEY = "current_user";
    private final Log log = LogFactory.getLog(getClass());

    /**
     * userMap:存储用户连接webscoket信息
     *
     */
    private final static Map<String, WebSocketSession> userMap;
    public static Map<String, List<String>> teachinMap;
    public static Map<String, Integer> teachinTimeMap;
    //public static ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
    public static ScheduledExecutorService executorService = Executors.newScheduledThreadPool(5);

    static {
        teachinMap = new ConcurrentHashMap<String, List<String>>();
        teachinTimeMap = new ConcurrentHashMap<String, Integer>();
        userMap = new ConcurrentHashMap<String, WebSocketSession>(30);

    }

    public SocketHandler() {
        log.info("======开始宣讲计时器=============");
        executorService.scheduleAtFixedRate(() -> {
            //宣讲时间减少一秒
            for (Map.Entry<String, Integer> m : teachinTimeMap.entrySet()) {
                m.setValue(m.getValue() - 1);
                if(m.getValue()<=0){
                    log.info( "主动推送==================宣讲结束");
                    SocketMsg socketMsg=new SocketMsg("40");
                    socketMsg.setTeachinId(m.getKey());
                    InteractionTeachin teachin = teachinDao.get(m.getKey());
                    teachin.setStatus(InteractionTeachin.STATUS_END);
                    teachin.preUpdate();
                    teachinDao.update(teachin);
                    sendMessageToSbUsers(JSON.toJSONString(socketMsg), SocketHandler.teachinMap.get(m.getKey()));
                    teachinMap.remove(m.getKey());
                    teachinTimeMap.remove(m.getKey());
                }else{
                    log.info( "主动推送==================宣讲时间人数");
                    Map map = new HashMap<>();
                    //InteractionTeachin teachin = teachinDao.get(teachinId);
              /*  if (teachin != null && InteractionTeachin.STATUS_ING.equals(teachin.getStatus())) {*/
                    map.put("time", teachinTimeMap.get(m.getKey()));
                    map.put("number", teachinMap.get(m.getKey())==null?0:teachinMap.get(m.getKey()).size());
                    SocketMsg socketMsg=new SocketMsg("32");
                    socketMsg.setContent(JSON.toJSONString(map));
                    sendMessageToSbUsers( JSON.toJSONString(socketMsg),teachinMap.get(m.getKey()));
                }
            }

        }, 1, 60, TimeUnit.SECONDS);
    }

    public static void teachinAdd(String teachinId, String accountId) {
        if (teachinMap.get(teachinId) == null) {
            List list = new ArrayList<String>();
            list.add(accountId);
            teachinMap.put(teachinId, list);

            /*开发需要，之后删除*/
            teachinTimeMap.put(teachinId,45);
        } else {
            if (!teachinMap.get(teachinId).contains(accountId)) {
                teachinMap.get(teachinId).add(accountId);
            }
        }
    }

    public static void teachinDel(String teachinId, String accountId) {
        if (teachinMap.get(teachinId) == null) {

        } else {
            teachinMap.get(teachinId).remove(accountId);
        }

    }

    /**
     * 关闭websocket时调用该方法
     *
     * @see org.springframework.web.socket.WebSocketHandler#afterConnectionClosed(org.springframework.web.socket.WebSocketSession, org.springframework.web.socket.CloseStatus)
     */
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        String userId = this.getUserId(session);
        if (StringUtils.isNoneBlank(userId)) {
            userMap.remove(userId);

            log.debug("该" + userId + "用户已成功关闭");
        } else {
            log.debug("关闭时，获取用户id为空");
        }

    }


    /**
     * 建立websocket连接时调用该方法
     *
     * @see org.springframework.web.socket.WebSocketHandler#afterConnectionEstablished(org.springframework.web.socket.WebSocketSession)
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {

        String userId = this.getUserId(session);
        log.info("==============有链接建立了================"+userId+" id:"+session.getId());
        if (StringUtils.isNoneBlank(userId)) {
            userMap.put(userId, session);
            session.sendMessage(new TextMessage(JSON.toJSON(new SocketMsg("0")).toString()));
        } else {
            log.info("有链接建立了,但是没有userId");
        }

    }

    /**
     * 客户端调用websocket.send时候，会调用该方法,进行数据通信
     *
     * @see org.springframework.web.socket.WebSocketHandler#handleMessage(org.springframework.web.socket.WebSocketSession, org.springframework.web.socket.WebSocketMessage)
     */
    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        for (Map.Entry<String, WebSocketSession> entry : userMap.entrySet()) {
            log.info("在线用户为:" + entry.getKey() + "==连接为:" + entry.getValue().getId());
        }

        String msg = (String) message.getPayload();
        String userId = this.getUserId(session);
        Map<String, String> respMap = new HashMap<>();

        userMap.put(userId, session);

        try {
            respMap = (Map<String, String>) JSON.parse(msg);

            String flag = respMap.get("flag");
            String accountId = respMap.get("accountId");
            String teachinId = respMap.get("teachinId");
            String interviewId = respMap.get("interviewId");
            String content = respMap.get("content");
            String title = respMap.get("title");
            SocketMsg socketMsg = new SocketMsg(respMap.get("flag"));
            if ("1".equals(respMap.get("flag"))) {//发起面试
                log.info(userId + "调用事件==================发起面试");
                if (!isOnline(accountId)) {
                    socketMsg = new SocketMsg("100");
                    socketMsg.setAccountId(accountId);
                    socketMsg.setInterviewId(interviewId);
                    sendMessageToUser(userId, JSON.toJSONString(socketMsg));
                } else {
                    //通知对方面试
                    socketMsg.setCompanyName(companyDao.get(companyhrDao.getByAccountId(userId).getCompanyId()).getName());
                    socketMsg.setAccountId(userId);
                    socketMsg.setInterviewId(interviewId);
                    socketMsg.setTitle(title);
                    log.info(JSON.toJSONString(socketMsg));

                    sendMessageToUser(accountId, JSON.toJSONString(socketMsg));
                }
            } else if ("2".equals(flag)) {//同意面试
                log.info(userId + "调用事件==================同意面试");

                //socketMsg.setCompanyName(companyDao.get(companyhrDao.getByAccountId(accountId).getCompanyId()).getName());
                socketMsg.setAccountId(userId);
                socketMsg.setInterviewId(interviewId);
                socketMsg.setTitle(title);
                sendMessageToUser(accountId, JSON.toJSONString(socketMsg));

                //给hr拉流地址
                socketMsg = new SocketMsg("53");
                socketMsg.setContent(pullUrl + userId/*+".flv?"*/ /*+ "?" + MyUtils.getSafeUrl(pullKey, userId, (int) ((System.currentTimeMillis() + (60 * 1000 * 60 * 24)) / 1000))*/);
                log.info("给" + accountId + "发送拉流" + JSON.toJSONString(socketMsg));

                sendMessageToUser(accountId, JSON.toJSONString(socketMsg));
                //给hr推流地址
                socketMsg = new SocketMsg("52");
                socketMsg.setContent(pushUrl + accountId /*+ "?" + MyUtils.getSafeUrl(pushKey, accountId, (int) ((System.currentTimeMillis() + (60 * 1000 * 60 * 24)) / 1000))*/);
                log.info("给" + accountId + "发送推流" + JSON.toJSONString(socketMsg));
                sendMessageToUser(accountId, JSON.toJSONString(socketMsg));


                //给学生拉流地址
                socketMsg = new SocketMsg("53");
                socketMsg.setContent(pullUrl + accountId/*+".flv?"*/ /*+ "?" + MyUtils.getSafeUrl(pullKey, accountId, (int) ((System.currentTimeMillis() + (60 * 1000 * 60 * 24)) / 1000))*/);
                log.info("给" + userId + "发送拉流" + JSON.toJSONString(socketMsg));
                log.info(isOnline(userId));
                //sendMessageToUser(userId,JSON.toJSONString(socketMsg));
                session.sendMessage(new TextMessage(JSON.toJSONString(socketMsg)));
                //给学生推流地址
                socketMsg = new SocketMsg("52");
                socketMsg.setContent(pushUrl + userId /*+ "?" + MyUtils.getSafeUrl(pushKey, userId, (int) ((System.currentTimeMillis() + (60 * 1000 * 60 * 24)) / 1000))*/);
                log.info("给" + userId + "发送推流" + JSON.toJSONString(socketMsg));
                //sendMessageToUser(userId,JSON.toJSONString(socketMsg));
                session.sendMessage(new TextMessage(JSON.toJSONString(socketMsg)));

            } else if ("3".equals(flag)) {//拒绝通话
                log.info(userId + "调用事件==================拒绝通话");
                socketMsg.setAccountId(userId);
                socketMsg.setInterviewId(interviewId);
                socketMsg.setTitle(title);
                sendMessageToUser(accountId, JSON.toJSONString(socketMsg));
            } else if ("4".equals(flag)) {//关闭通话
                log.info(userId + "调用事件==================关闭通话");
                socketMsg.setAccountId(userId);
                socketMsg.setInterviewId(interviewId);
                socketMsg.setTitle(title);
                InteractionInterview interview = interviewDao.get(interviewId);
                interview.setInterviewStatus(InteractionInterview.STATUS_FINISH);
                //增加消息记录
                MessageLog messageLog = new MessageLog();
                messageLog.setInterviewId(interview.getId());
                messageLog.setType(interview.getInterviewType());
                messageLog.setCompanyProfessionId(interview.getCompanyProfessionId());
                messageLog.setStudentId(interview.getStudentId());
                if (messageLogDao.findList(messageLog).size() == 0) {
                    messageLog = MyUtils.createMessage(interview);
                    messageLog.setType(MessageLog.TYPE_4);
                    messageLog.preInsert();
                    messageLogDao.insert(messageLog);
                } else {
                    log.info("插入消息记录已存在");
                }


                interview.preUpdate();
                interviewDao.update(interview);
                sendMessageToUser(accountId, JSON.toJSONString(socketMsg));
            } else if ("10".equals(flag)) {//进入宣讲
                log.info(userId + "调用事件==================进入宣讲");
                SocketHandler.teachinAdd(teachinId, userId);
            } else if ("11".equals(flag)) {//退出宣讲
                log.info(userId + "调用事件==================退出宣讲");

                if(Account.ROLE_COMPANY.equals(accountDao.get(userId))){
                    InteractionTeachin teachin = teachinDao.get(teachinId);
                    teachin.setStatus(InteractionTeachin.STATUS_END);
                    teachin.preUpdate();
                    teachinDao.update(teachin);
                    socketMsg.setFlag("40");
                    sendMessageToSbUsers(JSON.toJSONString(socketMsg), teachinMap.get(teachinId));
                    teachinMap.remove(teachinId);
                    teachinTimeMap.remove(teachinId);
                }else{
                    SocketHandler.teachinDel(teachinId, userId);
                }

            } else if ("15".equals(flag)) {//警告宣讲
                log.info(userId + "调用事件==================警告宣讲");

                if(Account.ROLE_SCHOOL.equals(accountDao.get(userId))){
                    InteractionTeachin teachin = teachinDao.get(teachinId);
                    socketMsg.setFlag("15");
                    sendMessageToUser( teachin.getAccountId(),JSON.toJSONString(socketMsg));

                }
            }else if ("16".equals(flag)) {//强制关闭宣讲
                log.info(userId + "调用事件==================强制关闭宣讲");

                if(Account.ROLE_SCHOOL.equals(accountDao.get(userId))){
                    InteractionTeachin teachin = teachinDao.get(teachinId);
                    teachin.setStatus(InteractionTeachin.STATUS_END);
                    teachin.preUpdate();
                    teachinDao.update(teachin);
                    socketMsg.setFlag("16");
                    sendMessageToSbUsers(JSON.toJSONString(socketMsg), teachinMap.get(teachinId));
                    teachinMap.remove(teachinId);
                    teachinTimeMap.remove(teachinId);
                }
            } else if ("30".equals(flag)) {//获取聊天室消息
                log.info(userId + "调用事件==================获取聊天室消息");
                Chat chat = new Chat();
                chat.setTeachinId(teachinId);
                socketMsg.setContent(JSON.toJSONString(chatDao.findLists(chat)));
                sendMessageToUser(userId, JSON.toJSONString(socketMsg));
            } else if ("31".equals(flag)) {//聊天室发送消息并广播
                log.info(userId + "调用事件==================聊天室发送消息并广播");
                SocketHandler.teachinAdd(teachinId, userId);
                socketMsg.setContent(content);
                Account account = accountDao.get(userId);
                InteractionTeachinChat chat = new InteractionTeachinChat();
                chat.setAccountId(account.getId());
                chat.setTeachinId(teachinId);
                chat.setContent(content);
                if (Account.ROLE_COMPANY.equals(account.getRole())) {
                    AccountCompanyhr companyhr = companyhrDao.getByAccountId(account.getId());
                    chat.setAvatar(companyhr.getAvatar());
                    chat.setRealname(companyhr.getRealname());
                }
                if (Account.ROLE_STUDENT.equals(account.getRole())) {
                    AccountStudentinfo studentinfo = studentinfoDao.getByAccountId(account.getId());
                    chat.setAvatar(studentinfo.getAvatar());
                    chat.setRealname(studentinfo.getRealname());
                }
                if (Account.ROLE_SCHOOL.equals(account.getRole())) {
                    AccountTeacherinfo teacherinfo = teacherinfoDao.getByAccountId(account.getId());
                    chat.setAvatar(teacherinfo.getAvatar());
                    chat.setRealname(teacherinfo.getRealname());
                }
                chat.preInsert();
                chatDao.insert(chat);
                Chat chat1 = new Chat();
                chat1.setAccountId(chat.getAccountId());
                chat1.setAvatar(chat.getAvatar());
                chat1.setContent(chat.getContent());
                chat1.setRealname(chat.getRealname());
                chat1.setTeachinId(chat.getTeachinId());
                chat1.setCreateDate(chat.getCreateDate());
                socketMsg.setContent(JSON.toJSONString(chat1));
                JSON.toJSONString(socketMsg);
                sendMessageToSbUsers(JSON.toJSONString(socketMsg), SocketHandler.teachinMap.get(teachinId));

            } else if ("32".equals(flag)) {//获取宣讲时间人数
                log.info(userId + "调用事件==================获取宣讲时间人数");
                Map map = new HashMap<>();
                //InteractionTeachin teachin = teachinDao.get(teachinId);
              /*  if (teachin != null && InteractionTeachin.STATUS_ING.equals(teachin.getStatus())) {*/
                    map.put("time", teachinTimeMap.get(teachinId));
                    map.put("number", teachinMap.get(teachinId)==null?0:teachinMap.get(teachinId).size());
                    socketMsg.setContent(JSON.toJSONString(map));
                    sendMessageToUser(userId, JSON.toJSONString(socketMsg));
               /* }*/
            } else if ("35".equals(flag)) {//宣讲关闭
                log.info(userId + "调用事件==================宣讲关闭");

                socketMsg.setTeachinId(teachinId);
                sendMessageToSbUsers(JSON.toJSONString(socketMsg), teachinMap.get(teachinId));
            } else if ("50".equals(flag)) {//获取宣讲推流地址
                log.info(userId + "调用事件==================取宣讲推流地址");
                Account account = accountDao.get(userId);
                if (Account.ROLE_COMPANY.equals(account.getRole())) {
                    teachinAdd(teachinId, account.getId());
                    socketMsg.setContent(pushUrl + teachinId /*+ "?" + MyUtils.getSafeUrl(pushKey, teachinId, (int) ((System.currentTimeMillis() + (60 * 1000 * 60 * 24)) / 1000))*/);
                    sendMessageToUser(userId, JSON.toJSONString(socketMsg));
                }
            } else if ("51".equals(flag)) {//获取宣讲拉流地址
                log.info(userId + "调用事件==================获取宣讲拉流地址");

                SocketHandler.teachinAdd(teachinId, userId);
                socketMsg.setContent(pullUrl + teachinId/*+".flv?"*/ /*+ "?" + MyUtils.getSafeUrl(pullKey, teachinId, (int) ((System.currentTimeMillis() + (60 * 1000 * 60 * 24)) / 1000))*/);
                sendMessageToUser(userId, JSON.toJSONString(socketMsg));
            } else if ("52".equals(flag)) {//获取面试推流地址
                log.info(userId + "调用事件==================获取面试推流地址");

                socketMsg.setContent(pushUrl + userId /*+ "?" + MyUtils.getSafeUrl(pushKey, userId, (int) ((System.currentTimeMillis() + (60 * 1000 * 60 * 24)) / 1000))*/);
                sendMessageToUser(userId, JSON.toJSONString(socketMsg));
            } else if ("53".equals(flag)) {//获取面试拉流地址

                log.info(userId + "调用事件==================获取面试拉流地址");
                socketMsg.setContent(pullUrl + accountId/*+".flv?"*/ /*+ "?" + MyUtils.getSafeUrl(pullKey, accountId, (int) ((System.currentTimeMillis() + (60 * 1000 * 60 * 24)) / 1000))*/);
                sendMessageToUser(userId, JSON.toJSONString(socketMsg));
            }
            else if ("80".equals(flag)) {//ping
                sendMessageToUser(userId, JSON.toJSONString(socketMsg));
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.info("出错");
        }


        log.debug("该" + userId + "用户发送的消息是：" + msg);
       /* message = new TextMessage("服务端已经接收到消息，msg=" + msg);
        session.sendMessage(message);*/

    }

    /**
     * 传输过程出现异常时，调用该方法
     *
     * @see org.springframework.web.socket.WebSocketHandler#handleTransportError(org.springframework.web.socket.WebSocketSession, java.lang.Throwable)
     */
    @Override
    public void handleTransportError(WebSocketSession session, Throwable e) throws Exception {
        WebSocketMessage<String> message = new TextMessage("异常信息：" + e.getMessage());
        session.sendMessage(message);
    }

    /**
     * @see org.springframework.web.socket.WebSocketHandler#supportsPartialMessages()
     */
    @Override
    public boolean supportsPartialMessages() {

        return false;
    }

    /**
     * 是否在线
     *
     * @param userId
     * @author zhaoshouyun
     * @since JDK 1.7
     */
    public Boolean isOnline(String userId) {
        for (Map.Entry<String, WebSocketSession> entry : userMap.entrySet()) {
            log.info("在线用户为:" + entry.getKey() + "==连接为:" + entry.getValue().getId());
        }
        WebSocketSession session = userMap.get(userId);
        if (session != null && session.isOpen()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * sendMessageToUser:发给指定用户
     *
     * @param userId
     * @param contents
     * @author zhaoshouyun
     * @since JDK 1.7
     */
    public void sendMessageToUser(String userId, String contents) {
        WebSocketSession session = userMap.get(userId);
        if (session != null && session.isOpen()) {
            try {
                TextMessage message = new TextMessage(contents);
                session.sendMessage(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * sendMessageToAllUsers:发给所有的用户
     *
     * @param contents
     * @author zhaoshouyun
     * @since JDK 1.7
     */
    public void sendMessageToAllUsers(String contents) {
        Set<String> userIds = userMap.keySet();
        for (String userId : userIds) {
            this.sendMessageToUser(userId, contents);
        }
    }

    /**
     * sendMessageToAllUsers:发给部分用户
     *
     * @param contents
     * @author zhaoshouyun
     * @since JDK 1.7
     */
    public void sendMessageToSbUsers(String contents, List<String> list) {
       if(list!=null){
           for (String userId : list) {
               this.sendMessageToUser(userId, contents);
           }
       }

    }

    /**
     * getUserId:获取用户id
     *
     * @param session
     * @return
     * @author zhaoshouyun
     * @since JDK 1.7
     */
    private String getUserId(WebSocketSession session) {
        try {
            String userId = (String) session.getAttributes().get(USER_KEY);
            return userId;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
