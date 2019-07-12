package com.thinkgem.jeesite.API.entity;

/**
 * 错误码表.
 * <p>
 * Created by xingfinal on 15/11/28.
 */
public enum Code {
    // 1024以内, 同HTTP
    SUCCESS(200, "Success."),
    BAD_REQUEST(400, "错误的请求"),
    UNKNOWN_ERROR(900, "未知错误"),

    API_NOT_REG(800,"请先登录在访问"),

    API_IS_USED(901, "信息失效"),
    API_NOT_EXITS(902, "不存在"),
    API_PHONE_ERROR(903, "手机号授权失败"),
    API_PAY_ORDER_FAIL(9000,"订单创建失败"),



    API_VERIFY_SMS_ERROR(11002,"验证码错误"),
    API_STUDENT_INFO(11003,"请先完善学生信息"),
    API_STUDENT_INFO_NULL(11003,null),


    API_ACCOUNT_LOCKED(1000,"用户禁止登陆"),

    API_PARRAM_ERROR(1111,"请完善必填内容"),
    API_PARRAM_ERROR_AVATAR(1111,"请完善用户头像"),
    API_PARRAM_ERROR_NAME(1111,"请完善真实姓名"),
    API_PARRAM_ERROR_SCHOOLID(1111,"请选择学校"),
    API_PARRAM_ERROR_EDU(1111,"请完善学历信息"),
    API_PARRAM_ERROR_MAJOR(1111,"请完善专业信息"),
    API_PARRAM_ERROR_BIRTH(1111,"请完善出生年月"),
    API_PARRAM_ERROR_GRADUAT(1111,"请完善毕业时间"),
    API_PARRAM_ERROR_STUDENTID(1111,"请完善学号信息"),



    API_ACCOUNT_NOT_EXIT(1000,"用户不存在"),
    API_ACCOUNT_COMPLETE(1001,"用户信息已完善"),


	API_ACCOUNT_USERNAME_OR_PASSWORD_ERROR(70001,"用户名或密码错误"),
	API_ACCOUNT_STATUS_FORBIDEN(70002,"该用户被禁止登录，请联系管理员"),

    API_PHONE_IS_REG(71001,"该号码已注册"),
    API_SEND_SMS_REP(71002,"短信发送过于频繁"),
    API_SEND_SMS_ERROR(71003,"短信发送失败"),


    API_REG_ROLE_ERROR(72001,"权限参数错误"),
    API_PASSWORD_LENGTH_ERROR(72002,"密码需大于8位数"),
    API_REALNAME_NULL(72003,"用户名不能为空"),
    API_SCHOOLID_NULL(72004,"学校id不能为空"),
    API_COMPANYNAME_NULL(72005,"企业名称不能为空"),
    API_CONTACT_NULL(72006,"联系人不能为空"),
    API_EMAIL_NULL(72007,"邮箱不能为空"),
    API_STUDENTID_ERROR(72008,"学号需小于12位"),

    API_USERNAME_LENGTH_ERROR(73001,"用户名过长"),
    API_SCHOOL_NOT_EXIST(73002,"学校不存在"),
    API_EDU_ERROR(73003,"教育背景不存在"),
    API_SEX_ERROR(73004,"性别参数不存在"),
    API_SCHOOL_CHANGE_TIME_NOT_ENOUTH(73005,"学校修改次数已达上限"),
    API_NAME_CHANGE_TIME_NOT_ENOUTH(73006,"姓名修改次数已达上限"),

    API_OLDPASSWORD_ERROR(74001,"旧密码错误"),

    API_SCHOOL_ERROR(75001,"还未绑定学校"),

    API_TEACHIN_NOT_EXIT(76001,"宣讲不存在"),
    API_USER_ROLE_ERROR(76002, "不存在该权限"),
    API_TEACHIN_NOT_ACT(76003, "宣讲未开始"),
    API_TEACHIN_TIME_ERROR(76004, "宣讲时间需要大于一天"),


    API_USER_PROFESSION_ERROR(77001, "查询列表时需关联宣讲id"),

    API_INTERVIEW_NOT_EXIT(78001,"面试不存在"),

    API_MESSAGEID_ERROR(79001, "messageId参数错误"),

    API_NUMBER_ERROR(80001, "参数为0-5之间"),
    API_COMMENTED(80002, "该面试已评论过"),


    API_INTERVIEWID_ERROR(81001, "interviewId参数错误"),

    API_OFFER_NOT_EXIT(82001, "offerId参数错误"),

    API_STATUS_ERROR(83001, "status参数错误"),
	API_OFFER_REFUSE_ERROR(83002,"已有一个终极确认offer"),
    API_OFFER_FIRST_ERROR(83003,"已终极确认,不可初步确认。或已拒绝的不可终极确认"),

    API_EXPERIENCEID_ERROR(84001,"experienceId参数错误"),

    API_EDUCAATIONID_ERROR(85001,"educationId参数错误"),


    API_PREFERID_ERROR(86001,"preferId参数错误"),
    API_PREFERID_TIME(86002,"工作意向添加已达上限"),
    API_SCHOOLEXPERIENCE_ERROR(86003,"schoolExperienceId参数错误"),

    API_INTERVIEW_RESUME_ERROR(87001,"先完善简历"),
    API_INTERVIEW_SEND_ERROR(87002,"该宣讲并未在此学校"),
    API_INTERVIEW_PROFESSION_ERROR(87003,"该宣讲不存在该职位"),
    API_USER_PROFESSION_SENDED(87004, "已投递过该职位"),
    API_PROFESSION_NULL(87005, "已投递过该职位"),

    API_COMPANYID_NULL(97001, "companyId参数错误"),
    API_COMPANY_AUTH(97002, "企业需要资质认证后才可以发布招聘信息"),



    API_HR_OFFERED(98001, "不可重复发送offer"),


    API_INTERVIEW_STATUS_ERROR(99001, "已经发起过面试"),

    API_COMPANY_NAME_ERROR(100001, "已认证不可修改企业全称或简称"),

    API_TEACHIN_PAYED(110001, "该时段已被占用"),
    API_TEACHIN_WAIT_PAYED(110002, "该时间段有订单未支付,请稍后再试"),
    API_VIP_NOT_EXIST(110003, "vip信息不存在"),
    API_VIP_TIME_NOT_ENO(110004, "vip抵扣次数不足"),
    API_VIP_NO(110005, "请先开通vip"),
    API_VIP_PAYED(110006, "请升级更高级vip"),
    API_VIP_PAY_FAL(110100, "获取预支付订单号失败"),
    API_PAY_TEACHIN_TIMES(110101, "使用完宣讲次数后才可购买vip"),
    API_PAY_VIP_UNDERS(110102, "存在未完成的对公支付账单，不可以再次下单。"),

    API_PROFESSIONID_ERROR(120001, "professionId参数错误"),

    API_PROFESSIONID_SOURCE_ERROR(130001, "该职位为校招企业"),


    API_SOCKET_NOT_ONLINE(140001, "用户不在线"),



    API_ORDERINVOICESD(141001, "该订单已开票"),

    ORDER_ID_ERROR(141002, "订单不存在"),
    ORDER_ID_COM(141003, "订单已完成"),


    RECOMMEND_NULL(142000, "推荐人不存在"),

    UNDERS_BOOTH(143000, "该展位已满，请选择其他展位"),
    UNDERS_BOOTH_NULL(143001, "该展位不存在"),
    UNDERS_BOOTH_SIGNED(143002, "已存在报名记录"),
    UNDERS_COMPANY_FILE(143003, "请先完善参展资料"),
    UNDERS_TIME_ERROR(143004, "不在报名时间段内"),





    ;

    private long code;
    private String message;

    Code(long code, String message) {
        this.code = code;
        this.message = message;
    }

    public long getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
