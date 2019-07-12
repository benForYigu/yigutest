package com.thinkgem.jeesite.API.controller;

import com.alibaba.fastjson.JSON;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Strings;
import com.thinkgem.jeesite.API.entity.*;

import com.thinkgem.jeesite.API.util.AliMessageUtils;
import com.thinkgem.jeesite.API.util.MyUtils;
import com.thinkgem.jeesite.API.util.NetEasy.NetEasyAPI;
import com.thinkgem.jeesite.API.util.TokenUtils;
import com.thinkgem.jeesite.API.util.UpEncodeUtils;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.IdGen;
import com.thinkgem.jeesite.common.utils.UploadUtil;
import com.thinkgem.jeesite.common.utils.UploadUtils;


import com.thinkgem.jeesite.modules.account.entity.Account;
import com.thinkgem.jeesite.modules.account.entity.accountstudent.AccountStudentinfo;
import com.thinkgem.jeesite.modules.account.service.AccountService;
import com.thinkgem.jeesite.modules.account.service.accounthr.AccountCompanyhrService;
import com.thinkgem.jeesite.modules.account.service.accountstudent.AccountStudentinfoService;
import com.thinkgem.jeesite.modules.appconfig.entity.AppConfig;
import com.thinkgem.jeesite.modules.appconfig.service.AppConfigService;
import com.thinkgem.jeesite.modules.company.entity.profession.CompanyProfession;
import com.thinkgem.jeesite.modules.company.service.CompanyService;
import com.thinkgem.jeesite.modules.company.service.profession.CompanyProfessionService;
import com.thinkgem.jeesite.modules.dict.entity.industry.DictIndustry;
import com.thinkgem.jeesite.modules.dict.entity.major.DictMajor;
import com.thinkgem.jeesite.modules.dict.entity.position.DictPosition;
import com.thinkgem.jeesite.modules.dict.service.industry.DictIndustryService;
import com.thinkgem.jeesite.modules.dict.service.major.DictMajorService;
import com.thinkgem.jeesite.modules.dict.service.position.DictPositionService;
import com.thinkgem.jeesite.modules.interaction.teachin.dao.chat.InteractionTeachinChatDao;
import com.thinkgem.jeesite.modules.interaction.teachin.entity.chat.Chat;
import com.thinkgem.jeesite.modules.order.entity.order.Order;
import com.thinkgem.jeesite.modules.order.entity.vip.OrderVip;
import com.thinkgem.jeesite.modules.order.service.order.OrderService;
import com.thinkgem.jeesite.modules.order.service.vip.OrderVipService;
import com.thinkgem.jeesite.modules.orderinvoices.entity.OrderInvoices;
import com.thinkgem.jeesite.modules.orderinvoices.service.OrderInvoicesService;
import com.thinkgem.jeesite.modules.school.entity.School;
import com.thinkgem.jeesite.modules.school.service.SchoolService;
import com.thinkgem.jeesite.modules.smsrecord.dao.SmsRecordDao;
import com.thinkgem.jeesite.modules.smsrecord.entity.SmsRecord;
import com.thinkgem.jeesite.modules.smsrecord.service.SmsRecordService;
import com.thinkgem.jeesite.modules.sys.dao.UserDao;
import com.thinkgem.jeesite.modules.sys.entity.Area;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.service.AreaService;
import com.thinkgem.jeesite.modules.sys.service.SystemService;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.method.P;
import org.springframework.security.config.authentication.UserServiceBeanDefinitionParser;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;

/**
 * 客户操作
 */

@RestController
@RequestMapping("${apiPath}/auth")
public class ApiAuthController {
    private final Log log = LogFactory.getLog(getClass());



    @Value("${otherPic}")
    private String otherPic;
    @Value("${project.name}")
    private String projectName;
    @Value("${recommend.coin}")
    private String coin;
    @Value("${be_recommend.coin}")
    private String be_coin;

    @Autowired
    private SmsRecordService smsRecordService;
    @Autowired
    private UserDao userDao;
    @Autowired
    private AppConfigService appConfigService;


    @Autowired
    private SchoolService schoolService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private DictPositionService positionService;

    @Autowired
    private DictIndustryService industryService;
    @Autowired
    private AreaService areaService;
    @Autowired
    private DictMajorService majorService;
    @Autowired
    private SmsRecordDao smsRecordDao;
    @Autowired
    private TokenUtils tokenUtils;
    private static Map<String, String> tokenMap = new LinkedHashMap<String, String>();

    @Autowired
    NetEasyAPI netEasyAPI;

    @Autowired
    SocketHandler socketHandler;


    @Autowired
    InteractionTeachinChatDao chatDao;


    @Autowired
    CompanyProfessionService professionService;
    @Autowired
    CompanyService companyService;
    @Autowired
    AccountStudentinfoService studentinfoService;
    @Autowired
    AccountCompanyhrService companyhrService;

    @Autowired
    OrderService orderService;
    @Autowired
    OrderVipService orderVipService;
    @Autowired
    OrderInvoicesService invoicesService;
    @Autowired
    AccountStudentinfoService accountStudentinfoService;
    @Value("${token.header}")
    private String tokenHeader ;

    @RequestMapping(value = "/test")
    public String test() throws IOException {

        return "Bearer " + tokenUtils.generateToken("040266974e8d43d097b40ae45406ab8f", "1");

        /*for (Order o:orderService.findList(new Order())) {
            if(Strings.isNullOrEmpty(o.getInvoicesId())){
                //添加发票
                OrderInvoices invoices=new OrderInvoices();
                invoices.setAccountId(o.getAccountId());
                invoices.setCompanyId(o.getCompanyId());
                invoices.setOrderId(o.getId());
                invoices.setStatus(OrderInvoices.STATUS_WAIT);
                invoicesService.save(invoices);
            }
        }
        for (OrderVip o: orderVipService.findList(new OrderVip())) {
            if(Strings.isNullOrEmpty(o.getInvoicesId())){
                //添加发票
                OrderInvoices invoices=new OrderInvoices();
                invoices.setAccountId(o.getAccountId());
                invoices.setCompanyId(o.getCompanyId());
                invoices.setOrderId(o.getId());
                invoices.setStatus(OrderInvoices.STATUS_WAIT);
                invoicesService.save(invoices);
            }
        }*/

    }

    /**推荐人信息和推荐港币
     *
     * @param accountId 不传为获取推荐人港币收益
     * @return
     */
    @RequestMapping(value = "/recommend", method = RequestMethod.GET)
    public Response recommend( String accountId){
        Map map=new HashMap();
        if(!Strings.isNullOrEmpty(accountId)){
           Account account=accountService.get(accountId);
           if(account==null){
               return new Response(Code.RECOMMEND_NULL);
           }
           map.put("coin",be_coin);
           if(Account.ROLE_STUDENT.equals(account.getRole())){
               map.put("accountInfo",studentinfoService.getByAccountId(accountId).getRealname());
           }else if(Account.ROLE_COMPANY.equals(account.getRole())){
               map.put("accountInfo",companyhrService.getByAccountId(accountId).getRealname());
           }
       }else{
            map.put("coin",coin);
       }
        return new Response(map);
    }
    /**
     * 用户注册
     *
     * @param phone
     * @param password
     * @param code
     * @param role
     * @param schoolId
     * @param realname
     * @return
     * @throws ParseException
     * @throws IOException
     */
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public Response register(HttpServletRequest request,@RequestParam(required = false) String phone,
                             @RequestParam(required = false) String password,
                             @RequestParam(required = false) String code,
                             @RequestParam(required = false) String role,
                             @RequestParam(required = false) String schoolId,
                             @RequestParam(required = false) String address,
                             @RequestParam(required = false) String companyName,
                             @RequestParam(required = false) String contact,
                             @RequestParam(required = false) String email,
                             @RequestParam(required = false) String realname,
                             @RequestParam(required = false) String avatar,
                             @RequestParam(required = false) String educationBackground,
                             @RequestParam(required = false) String major,
                             @RequestParam(required = false) String sex,
                             @RequestParam(required = false) String birthday,
                             @RequestParam(required = false) String graduationTime,
                             @RequestParam(required = false) String studentId,
                             @RequestParam(required = false) String recommendId //推荐人id
                             ) throws ParseException, IOException {


        String token=request.getHeader(tokenHeader);
        if(!Strings.isNullOrEmpty(token)&&token.length()>7){
            token=token.substring(7);
        }
        String id = tokenUtils.getUsernameFromToken(token);
        Account account=accountService.get(id);
        if(account!=null){
            AccountStudentinfo student=accountStudentinfoService.getByAccountId(account.getId());

            if(!Strings.isNullOrEmpty(student.getSchoolId())){
                return new Response(Code.API_ACCOUNT_COMPLETE);
            }
            if (!Strings.isNullOrEmpty(schoolId) && schoolService.get(schoolId) == null) {
                return new Response(Code.API_SCHOOL_NOT_EXIST);
            }

            student.setAvatar(avatar);
            student.setRealname(realname);
            student.setSchoolId(schoolId);
            student.setEducationBackground(educationBackground);
            student.setMajor(major);
            student.setSex(sex);
            student.setBirthday(DateUtils.parseDate(birthday));
            student.setGraduationTime(DateUtils.parseDate(graduationTime));
            student.setStudentId(studentId);
            accountStudentinfoService.save(student);
            return new Response(Code.SUCCESS);


            //注册
        }else{
            if (Strings.isNullOrEmpty(role)||!role.equals(Account.ROLE_STUDENT) &&
                    !role.equals(Account.ROLE_COMPANY) &&
                    !role.equals(Account.ROLE_SCHOOL)) {
                return new Response(Code.API_REG_ROLE_ERROR);
            }
            if (password.length() < 8) {
                return new Response(Code.API_PASSWORD_LENGTH_ERROR);
            }
            return new Response(accountService.register(phone, password, code, role, schoolId, realname
                    , address, companyName, contact, email, avatar, educationBackground, major, sex, birthday, graduationTime, studentId,recommendId));
        }




    }
    @RequestMapping(value = "/registerValidate", method = RequestMethod.POST)
    public Response registerValidate(@RequestParam(required = true) String phone,
                                     @RequestParam(required = true) String  password,
                                     @RequestParam(required = true) String code){
        if (password.length() < 8) {
            return new Response(Code.API_PASSWORD_LENGTH_ERROR);
        }
        //验证短信
        SmsRecord record =  smsRecordDao.selectByPhoneAndStatus(phone,SmsRecord.STATUS_WAITING);
        if (record == null||!code.equals(record.getCode())) {
            return new Response(Code.API_VERIFY_SMS_ERROR);
        }
        return new Response(Code.SUCCESS);
    }


    /**
     * @param username
     * @param password // * @param role 权限 1学生 2工作人员 3企业hr
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Response login(@RequestParam String username,
                          @RequestParam String password,
                          @RequestParam String role) {
        Account account = accountService.selectByUsernameAndPassword(username, UpEncodeUtils.md5(password), role);
        if (account == null) {
            return new Response(Code.API_ACCOUNT_USERNAME_OR_PASSWORD_ERROR);
        } else {
            if(Account.ROLE_STUDENT.equals(account.getRole())){
                AccountStudentinfo studentinfo=studentinfoService.getByAccountId(account.getId());
                if(Strings.isNullOrEmpty(studentinfo.getSchoolId())){
                    account.setPassword(null);

                    account.setAuthToken("Bearer " + tokenUtils.generateToken(account.getId(), account.getRole()));
                    return new Response(Code.API_STUDENT_INFO_NULL,account);

                }
            }
            if (!Account.STATUS_NORMAL.equals(account.getStatus())) {
                return new Response(Code.API_ACCOUNT_STATUS_FORBIDEN);
            } else {
                account.setAuthToken("Bearer " + tokenUtils.generateToken(account.getId(), account.getRole()));
            }
        }
        account.setPassword(null);
        return new Response(account);
    }

    /**
     *
     * @param phone
     * @param flag m默认 1都是注册 2是修改密码
     * @return
     * @throws ClientException
     */
    @RequestMapping(value = "/sendSms", method = RequestMethod.POST)
    public Response<String> sendRegisterSms(@RequestParam String phone,String flag) throws ClientException {
       // return new Response<>(Code.SUCCESS);
       if(!"2".equals(flag)){
           Account account=accountService.selectByPhone(phone);
           if(account!=null){
               return new Response(Code.API_PHONE_IS_REG);
       }
       }
        SmsRecord lastRecord = smsRecordService.findRecordByPhone(phone);
        // 1分钟之内, 不允许发送多条
        if (lastRecord != null && MyUtils.dateToLocalDateTime(lastRecord.getSendTime()).plusMinutes(1).compareTo(LocalDateTime.now()) >= 0) {
            return new Response<>(Code.API_SEND_SMS_REP);
        }
        String verifyCode = MyUtils.generateSmsVerifyCode();
        SendSmsResponse result;
        Map<String,String> map=new HashMap();
        map.put("code",verifyCode);
        synchronized(this){
            result = AliMessageUtils.sendSms(phone,AliMessageUtils.PHONE_BIND,map);
        }

        if (result == null) {
            return new Response<>(Code.API_SEND_SMS_ERROR, "未知错误.");
        }
        String data;
        switch (result.getCode()) {
            case "OK": {
                // 将旧的验证码作废
                smsRecordService.cancelOldVerifyCode(phone);
                // 取得运营商返回的记录号
                SmsRecord record = new SmsRecord();
                record.setPlatformNo(result.getBizId());
                record.setPhone(phone);
                record.setCode(verifyCode);
                record.setSendTime(new Date());
                record.setStatus(SmsRecord.STATUS_WAITING);
                record.preInsert();
                smsRecordService.insert(record);
                return new Response<>(Code.SUCCESS);
            }
            default:
                data = "未知错误.";
        }

        return new Response<>(Code.API_SEND_SMS_ERROR, data);
    }



    @RequestMapping(value = "/changePassword",method = RequestMethod.POST)
    public Response<String> changePassword(@RequestParam String phone,
                                           @RequestParam String code,
                                           @RequestParam String password) {
        SmsRecord record = smsRecordService.selectByPhoneAndStatus(phone,SmsRecord.STATUS_WAITING);
        if (record == null) {
            return new Response<>(Code.API_VERIFY_SMS_ERROR, "验证码已过期.");
        }
        if (!code.equals(record.getCode())) {
            return new Response<>(Code.API_VERIFY_SMS_ERROR, "验证码不正确.");
        }
        if (password.length() < 8) {
            return new Response(Code.API_PASSWORD_LENGTH_ERROR);
        }
        Account account=accountService.selectByPhone(phone);
        if(account==null){
            return new Response(Code.API_ACCOUNT_NOT_EXIT);
        }
        account.setPassword(UpEncodeUtils.md5(password));
        accountService.save(account);

        //更新后台密码
        User user= userDao.get(account.getId());
        if(user!=null){
            user.setPassword(SystemService.entryptPassword(password));
            user.preUpdate();
            userDao.update(user);
            // 清除用户缓存
            user.setLoginName(phone);
            UserUtils.clearCache(user);
        }


        record.setStatus(SmsRecord.STATUS_VERIFIED);
        record.setVerifyTime(new Date());
        smsRecordService.save(record);
        return new Response<>(Code.SUCCESS);
    }






    /**
     * 获取学校列表或详情
     *natrue 1 985  2 211 3双一流
     * @type 1like 2wenke
     * @sort 1推荐排序  2默认排序(名称排序)
     * @param schoolId
     * @return
     */
    @RequestMapping(value = "/school", method = RequestMethod.GET)
    public Response school(String schoolId, String schoolName,String nature,String type,String cityId,
                           String sort,String hot,
                           @RequestParam(value="page",required = false) Integer page,
                           @RequestParam(value="size",required = false) Integer size) {
        if (Strings.isNullOrEmpty(schoolId)) {
            School school = new School();
            school.setName(schoolName);
            school.setType(type);
            school.setNatrue(nature);
            school.setCity(cityId);
            school.setHot(hot);

            if("1".equals(sort)){
                school.getPage().setOrderBy(" a.sort,a.name asc");
            }else if("2".equals(sort)){
                school.getPage().setOrderBy(" a.name asc");
            }else{
                school.getPage().setOrderBy(" a.sort,a.name asc");
            }
            if(size!=null && page!=null){
                PageHelper.startPage(page,size);
                return new Response(new PageInfo<School>(schoolService.findList(school)));
            }else{
                return new Response(schoolService.findList(school));
            }

        } else {
            return new Response(schoolService.get(schoolId));
        }
    }

    /**
     * 获取职位
     *
     * @param parentId
     * @return
     */
    @RequestMapping(value = "/position", method = RequestMethod.GET)
    public Response position(String parentId, String id,String flag) {
        //获取学校查询有数据的职位类型
        if("1".equals(flag)){
            return new Response(positionService.existProfession());
        }
        if (!Strings.isNullOrEmpty(id)) {
            return new Response(positionService.get(id).getName());
        }
        DictPosition position = new DictPosition();
        if (!Strings.isNullOrEmpty(parentId)) {
            position.setParent(new DictPosition(parentId));
        }

        position.setParentId(parentId);


        return new Response(positionService.labelList(position));

    }

    /**
     * 获取行业
     *
     * @param parentId
     * @return
     */
    @RequestMapping(value = "/industry", method = RequestMethod.GET)
    public Response industry(String parentId, String id) {
        if (!Strings.isNullOrEmpty(id)) {
            return new Response(industryService.get(id).getName());
        }
        DictIndustry industry = new DictIndustry();
        if (!Strings.isNullOrEmpty(parentId)) {
            industry.setParent(new DictIndustry(parentId));
        }
        industry.setParentId(parentId);

        return new Response(industryService.labelList(industry));
    }


    /**
     * 获取区域
     *
     * @param parentId
     * @param flag 1职位列表有数据的区域 2学校列表有数据的区域 3求职意向area（只有省市没有区）
     * @return
     */
    @RequestMapping(value = "/area", method = RequestMethod.GET)
    public Response area(String parentId, String id,String type,String flag) {
        Area area=new Area();
        if (!Strings.isNullOrEmpty(parentId)) {
            area.setParent(new Area(parentId));
        }
        if(!Strings.isNullOrEmpty(type)){
            area.setType(type);
        }

        //职位有数据的
        if("1".equals(flag)){
            return new Response(areaService.professionExistArea(area));
        //学校有数据的
        }else if("2".equals(flag)){
            return new Response(areaService.schoolExistArea(area));
        }else if("3".equals(flag)){
           return new Response(areaService.not3Area(area));
        }

        if (!Strings.isNullOrEmpty(id)) {
            return new Response(areaService.get(id).getName());
        }



        return new Response(areaService.labelList(area));

    }




    /**
     * 获取专业
     *
     * @param parentId
     * @return
     */
    @RequestMapping(value = "/major", method = RequestMethod.GET)
    public Response major(String parentId, String id) {
        if (!Strings.isNullOrEmpty(id)) {
            return new Response(majorService.get(id).getName());
        }
        DictMajor major = new DictMajor();
        if (!Strings.isNullOrEmpty(parentId)) {
            major.setParent(new DictMajor(parentId));
        }

        major.setParent(new DictMajor(parentId));

        return new Response(majorService.labelList(major));
    }


    /**
     * 获取字典
     *
     * @param dict
     * @return
     */
    @RequestMapping(value = "/dict", method = RequestMethod.GET)
    public Response dict(@RequestParam String dict, String value) {
        if (!Strings.isNullOrEmpty(value)) {
            return new Response(DictUtils.getDictLabel(value, dict, ""));
        }
        return new Response(DictUtils.getDictLabelList(dict));
    }


    /**
     * 上传图片
     *
     * @param request
     * @return
     */

    @RequestMapping(value = "/img", method = RequestMethod.POST)
    public Response img(HttpServletRequest request,String type) throws IOException {
        UploadUtils uploadUtils;
        //
        System.out.println(type);
        if("1".equals(type)){
            uploadUtils=new UploadUtils(otherPic+"licence/");
        }else if("2".equals(type)){
            uploadUtils=new UploadUtils(otherPic+"flash/");
            uploadUtils.setDirName("flashs");
        }else{
             uploadUtils=new UploadUtils(otherPic);
        }
        Object o=(List<String>)uploadUtils.uploadFile(request);
        System.out.println(o);
        return new Response(o);
        //return new Response((List<String>)uploadUtils.uploadFile(request));

    }
    /**
     * 上传图片
     *
     * @param request
     * @return
     */

    @RequestMapping(value = "/imgStream", method = RequestMethod.POST)
    public Response imgStream(HttpServletRequest request,String type) throws IOException {
        InputStream s = request.getInputStream();
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
        String time = df.format(new Date());
        String fileName = IdGen.uuid()+ ".jpg";
        String path ;
        if("1".equals(type)){
            path =otherPic+"licence/"+"images/"+time+"/";
        }else{
            path =otherPic+"images/"+time+"/";
        }
        File saveDirFile =new File(path);
        if (!saveDirFile.exists()) {
            saveDirFile.mkdirs();
        }
        File uploadedFile = new File(path+fileName);
        UploadUtil.saveData(s, uploadedFile);
        return new Response("/"+projectName+path+fileName);
    }


    /**
     * 检查更新
     *version
     *os
     * device
     * {
     "type": "1",    ----------------------1企业端  2学生端
     "version": "0.0",
     "os": "as",
     "device": "123"
     }
     * @return
     */

    @RequestMapping(value = "/checkUpdate", method = RequestMethod.POST)
    public Object checkUpdate(@RequestBody Map<String ,Object> m, @RequestParam String bundleId, HttpServletResponse response) throws IOException {
       /* System.out.println("version========================="+m.get("version"));
        System.out.println("os========================="+m.get("os"));
        System.out.println("device========================="+m.get("device"));
        System.out.println("bundleId========================="+bundleId);

        AppConfig appConfig ;
        Map map=new HashMap();

        //学生端
        if("student.com.lemondm.yixiaozhao".equals(bundleId)){
            appConfig =appConfigService.get("2");
        }else if("company.com.lemondm.yixiaozhao".equals(bundleId)){
            appConfig =appConfigService.get("1");
        }else{
            response.setStatus(410);
            return "包名错误";
        }


        String[] vlocal=((String)m.get("version")).split("\\.");
        String[] vnew=(appConfig.getVersion()).split("\\.");
        System.out.println(Integer.valueOf(vlocal[0])>=Integer.valueOf(vnew[0]));
        if(Integer.valueOf(vlocal[0])>=Integer.valueOf(vnew[0])&&
                Integer.valueOf(vlocal[1])>=Integer.valueOf(vnew[1])&&
                Integer.valueOf(vlocal[2])>=Integer.valueOf(vnew[2])){
            System.out.println("is========================="+false);
            response.setStatus(410);
        }else{
            System.out.println("is========================="+true);
            map.put("name",appConfig.getVersion());
            map.put("title",appConfig.getTitle());
            map.put("description",appConfig.getContent());
            map.put("platform","both");
            map.put("hotupgrate_type","tip");
            map.put("android_url","http://yixiaozhao.lemondm.com"+appConfig.getUrl());
        }
        return map;
*/
        System.out.println("version========================="+m.get("version"));
        System.out.println("os========================="+m.get("os"));
        System.out.println("device========================="+m.get("device"));
        System.out.println("bundleId========================="+bundleId);

        AppConfig appConfig;
        Map map=new HashMap();
        System.out.println(((Map<String,String>)m.get("os")).get("name"));
        String type;

        //学生端
        if("student.com.lemondm.yixiaozhao".equals(bundleId)){
            if(((Map<String,String>)m.get("os")).get("name").equals("Android")){
                type=AppConfig.APK_STU;
            }else{
                type=AppConfig.IOS_STU;
            }
        }else if("company.com.lemondm.yixiaozhao".equals(bundleId)){
            if(((Map<String,String>)m.get("os")).get("name").equals("Android")){
                type=AppConfig.APK_COM;
            }else{
                type=AppConfig.IOS_COM;
            }
        }else{
            response.setStatus(410);
            return "包名错误";
        }
        appConfig =appConfigService.selectByVersionAndType(type,(String) m.get("version"));
        if(appConfig==null){
            response.setStatus(410);
            return "";
        }
        appConfig =appConfigService.selectByVersionName(type,""+(Integer.parseInt(appConfig.getName())+1));
        if(appConfig==null){
            System.out.println("is========================="+false);
            response.setStatus(410);
        }else{
            System.out.println("is========================="+true);
            map.put("name",appConfig.getVersion());
            map.put("title",appConfig.getTitle());
            map.put("description",appConfig.getContent());
            map.put("platform","both");
            map.put("hotupgrate_type","tip");
            map.put("android_url","http://yixiaozhao.lemondm.com"+appConfig.getUrl());
        }
        return map;

    }


}

