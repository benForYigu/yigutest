/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.account.service;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.google.common.base.Strings;
import com.sun.corba.se.spi.presentation.rmi.IDLNameTranslator;
import com.thinkgem.jeesite.API.entity.Code;
import com.thinkgem.jeesite.API.util.NetEasy.BasicResult;
import com.thinkgem.jeesite.API.util.NetEasy.NetEasyAPI;
import com.thinkgem.jeesite.API.util.NetEasy.RegisterResult;
import com.thinkgem.jeesite.API.util.TokenUtils;
import com.thinkgem.jeesite.API.util.UpEncodeUtils;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.IdGen;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.account.dao.accounthr.AccountCompanyhrDao;
import com.thinkgem.jeesite.modules.account.dao.accountschool.AccountTeacherinfoDao;
import com.thinkgem.jeesite.modules.account.dao.accountstudent.AccountStudentinfoDao;
import com.thinkgem.jeesite.modules.account.entity.accounthr.AccountCompanyhr;
import com.thinkgem.jeesite.modules.account.entity.accountschool.AccountTeacherinfo;
import com.thinkgem.jeesite.modules.account.entity.accountstudent.AccountStudentinfo;
import com.thinkgem.jeesite.modules.accountrecommend.dao.AccountRecommendDao;
import com.thinkgem.jeesite.modules.accountrecommend.entity.AccountRecommend;
import com.thinkgem.jeesite.modules.coin.dao.AccountCoinDao;
import com.thinkgem.jeesite.modules.coin.entity.AccountCoin;
import com.thinkgem.jeesite.modules.company.dao.CompanyDao;
import com.thinkgem.jeesite.modules.company.entity.Company;
import com.thinkgem.jeesite.modules.school.dao.SchoolDao;
import com.thinkgem.jeesite.modules.smsrecord.dao.SmsRecordDao;
import com.thinkgem.jeesite.modules.smsrecord.entity.SmsRecord;
import com.thinkgem.jeesite.modules.sys.dao.RoleDao;
import com.thinkgem.jeesite.modules.sys.dao.UserDao;
import com.thinkgem.jeesite.modules.sys.entity.Role;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.service.SystemService;
import com.thinkgem.jeesite.modules.vip.entity.Vip;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.apache.bcel.generic.RET;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.account.entity.Account;
import com.thinkgem.jeesite.modules.account.dao.AccountDao;

import javax.xml.bind.SchemaOutputResolver;

/**
 * 用户Service
 *
 * @author 李金辉
 * @version 2019-01-23
 */
@Service
@Transactional(readOnly = true)
public class AccountService extends CrudService<AccountDao, Account> {

    @Autowired
    AccountDao accountDao;
    @Autowired
    AccountStudentinfoDao accountStudentinfoDao;
    @Autowired
    AccountCompanyhrDao accountCompanyhrDao;
    @Autowired
    AccountTeacherinfoDao accountTeacherinfoDao;
    @Autowired
    SchoolDao schoolDao;
    @Autowired
    CompanyDao companyDao;
    @Autowired
    SmsRecordDao smsRecordDao;
    @Autowired
    private NetEasyAPI netEasyAPI;
    @Autowired
    private TokenUtils tokenUtils;
    @Autowired
    private UserDao userDao;
    @Autowired
    private AccountCoinDao coinDao;
    @Autowired
    private AccountRecommendDao accountRecommendDao;
    private final Log log = LogFactory.getLog(getClass());
    @Value("${school.changetime}")
    private String MAX_SCHOOL_CHANGE_TIME;
    @Value("${name.changetime}")
    private String MAX_NAME_CHANGE_TIME;
    @Value("${company.logo}")
    private String companyLogo;
    @Value("${company.img}")
    private String companyImg;
    @Value("${recommend.coin}")
    private String coin;
    @Value("${be_recommend.coin}")
    private String be_coin;

    public Account get(String id) {
        return super.get(id);
    }

    public List<Account> findList(Account account) {
        return super.findList(account);
    }

    public Page<Account> findPage(Page<Account> page, Account account) {
        return super.findPage(page, account);
    }

    @Transactional(readOnly = false)
    public void save(Account account) {
        super.save(account);
    }

    @Transactional(readOnly = false)
    public void delete(Account account) {
        super.delete(account);
    }


    public Account selectByUsernameAndPassword(String username, String password) {
        return dao.selectByUsernameAndPassword(username, password, null);
    }

    public Account selectByUsernameAndPassword(String username, String password, String role) {
        return dao.selectByUsernameAndPassword(username, password, role);
    }

    public Account selectByPhone(String phone) {
        return dao.selectByPhone(phone);
    }

    @Transactional(readOnly = false)
    public Object user(Account account, String avatar, String realname, String schoolId, String department, String educationBackground, String major, String sex, String birthday, String graduationTime, String email, String studentId) {
        //学生
        if (Account.ROLE_STUDENT.equals(account.getRole())) {
            AccountStudentinfo accountStudentinfo = accountStudentinfoDao.getByAccountId(account.getId());
            if (!Strings.isNullOrEmpty(realname) && !realname.equals(accountStudentinfo.getRealname())) {
                if (accountStudentinfo.getNameChangeTime() >= Integer.valueOf(MAX_NAME_CHANGE_TIME)) {
                    return Code.API_NAME_CHANGE_TIME_NOT_ENOUTH;
                }

                if (realname.length() > 20) {
                    return Code.API_USERNAME_LENGTH_ERROR;
                } else {
                    accountStudentinfo.setNameChangeTime(accountStudentinfo.getNameChangeTime() + 1);
                    accountStudentinfo.setRealname(realname);
                }
            }
            if (!Strings.isNullOrEmpty(schoolId) && !schoolId.equals(accountStudentinfo.getSchoolId())) {
                if (accountStudentinfo.getSchoolChangeTime() >= Integer.valueOf(MAX_SCHOOL_CHANGE_TIME)) {
                    return Code.API_SCHOOL_CHANGE_TIME_NOT_ENOUTH;
                }
                if (schoolDao.get(schoolId) == null) {
                    return Code.API_SCHOOL_NOT_EXIST;
                } else {
                    accountStudentinfo.setSchoolChangeTime((accountStudentinfo.getSchoolChangeTime() + 1));
                    accountStudentinfo.setSchoolId(schoolId);
                }
            }
            if (!Strings.isNullOrEmpty(avatar)) {
                accountStudentinfo.setAvatar(avatar);
            }
            if (!Strings.isNullOrEmpty(department)) {
                accountStudentinfo.setDepartment(department);
            }
            if (!Strings.isNullOrEmpty(educationBackground)) {
                if (!"1".equals(educationBackground) && !"2".equals(educationBackground) && !"3".equals(educationBackground) && !"4".equals(educationBackground)) {
                    return Code.API_EDU_ERROR;
                } else {
                    accountStudentinfo.setEducationBackground(educationBackground);
                }
            }
            if (!Strings.isNullOrEmpty(major)) {
                accountStudentinfo.setMajor(major);
            }
            if (!Strings.isNullOrEmpty(sex)) {
                if (!"1".equals(sex) && !"2".equals(sex)) {
                    return Code.API_SEX_ERROR;
                } else {
                    accountStudentinfo.setSex(sex);
                }
            }
            if (!Strings.isNullOrEmpty(birthday)) {
                accountStudentinfo.setBirthday(DateUtils.parseDate(birthday));
            }
            if (!Strings.isNullOrEmpty(graduationTime)) {
                accountStudentinfo.setGraduationTime(DateUtils.parseDate(graduationTime));
            }
            if (!Strings.isNullOrEmpty(studentId)) {
                accountStudentinfo.setStudentId(studentId);
            }
            accountStudentinfo.preUpdate();
            accountStudentinfoDao.update(accountStudentinfo);
            return accountStudentinfo;
        }
        //hr
        if (Account.ROLE_COMPANY.equals(account.getRole())) {
            AccountCompanyhr accountCompanyhr = accountCompanyhrDao.getByAccountId(account.getId());
            if (!Strings.isNullOrEmpty(email)) {
                accountCompanyhr.setEmail(email);
            }
            if (!Strings.isNullOrEmpty(avatar)) {
                accountCompanyhr.setAvatar(avatar);
            }
            if (!Strings.isNullOrEmpty(realname)) {
                accountCompanyhr.setRealname(realname);
               /* Company company=companyDao.get(accountCompanyhr.getCompanyId());
                company.setContact(realname);
                company.preUpdate();
                companyDao.update(company);*/
            }
            accountCompanyhr.preUpdate();
            accountCompanyhrDao.update(accountCompanyhr);
            return accountCompanyhr;
        }
        //学校
        if (Account.ROLE_SCHOOL.equals(account.getRole())) {
            AccountTeacherinfo accountTeacherinfo = accountTeacherinfoDao.getByAccountId(account.getId());
            if (!Strings.isNullOrEmpty(email)) {
                accountTeacherinfo.setEmail(email);
            }
            accountTeacherinfo.preUpdate();
            accountTeacherinfoDao.update(accountTeacherinfo);
            return accountTeacherinfo;
        }
        return Code.API_PARRAM_ERROR;
    }

    @Transactional(readOnly = false)
    public Object register(String phone, String password, String code, String role, String schoolId, String realname, String address, String companyName, String contact, String email,
                           String avatar, String educationBackground,
                           String major, String sex, String birthday,
                           String graduationTime, String studentId, String recommendId) throws IOException {
        //验证短信
        SmsRecord record = smsRecordDao.selectByPhoneAndStatus(phone, SmsRecord.STATUS_WAITING);
        if (record == null || !code.equals(record.getCode())) {
            return Code.API_VERIFY_SMS_ERROR;
        }
        /*record.setStatus(SmsRecord.STATUS_VERIFIED);
        record.setVerifyTime(new Date());
		record.preUpdate();
		smsRecordDao.update(record);*/ //后面更新

        if (accountDao.selectByPhone(phone) != null) {
            return Code.API_PHONE_IS_REG;
        }
        if (!Strings.isNullOrEmpty(schoolId) && schoolDao.get(schoolId) == null) {
            return Code.API_SCHOOL_NOT_EXIST;
        }

        //开始注册用户信息
        Account account = new Account();
        account.preInsert();
        account.setRole(role);
        account.setUsername(phone);
        account.setStatus(Account.STATUS_NORMAL);
        account.setPassword(UpEncodeUtils.md5(password));


        //学生
        if (Account.ROLE_STUDENT.equals(role)) {
            Account recommend = dao.get(recommendId);
            AccountStudentinfo recommendStudentinfo = null;
            if (recommend != null) {
                recommendStudentinfo = accountStudentinfoDao.getByAccountId(recommend.getId());
            }
            //推荐人存在,进入推荐注册流程
            if (recommendStudentinfo != null) {
                //岗币记录 推荐人
                AccountCoin accountCoin = new AccountCoin();
                accountCoin.setCoin(Integer.valueOf(coin));
                accountCoin.setType(AccountCoin.TYPE_RECOMMEND);
                accountCoin.setAccountId(recommendId);
                accountCoin.setAboutId(account.getId());
                accountCoin.preInsert();
                coinDao.insert(accountCoin);
                //岗币记录 被推荐人
                AccountCoin accountCoinBe = new AccountCoin();
                accountCoinBe.setCoin(Integer.valueOf(be_coin));
                accountCoinBe.setType(AccountCoin.TYPE_BE_RECOMMEND);
                accountCoinBe.setAccountId(account.getId());
                accountCoinBe.setAboutId(recommendId);
                accountCoinBe.preInsert();
                coinDao.insert(accountCoinBe);
                //更新推荐人岗币
                accountStudentinfoDao.updateCoin(recommendStudentinfo.getAccountId(), recommendStudentinfo.getCoin() + Integer.valueOf(coin));
                //推荐记录
                AccountRecommend recommend1 = new AccountRecommend();
                recommend1.setAccountId(recommendId);
                recommend1.setBeAccountId(account.getId());
                recommend1.preInsert();
                accountRecommendDao.insert(recommend1);


                AccountStudentinfo student = new AccountStudentinfo();
                student.setAccountId(account.getId());
                student.setCollect("");
                student.setSchoolChangeTime(0);
                student.setNameChangeTime(0);
                student.setPhone(phone);
                student.setProfessionCollect("");
                student.setCoin(Integer.valueOf(be_coin));
                student.setIdCardStatus(Account.AUTH_NO);
                student.setStudentIdStatus(Account.AUTH_NO);

                student.preInsert();
                accountStudentinfoDao.insert(student);

                //正常app注册流程
            } else {
                /*if (Strings.isNullOrEmpty(avatar) ||
                        Strings.isNullOrEmpty(realname) ||
                        Strings.isNullOrEmpty(schoolId) ||
                        Strings.isNullOrEmpty(educationBackground) ||
                        Strings.isNullOrEmpty(major) ||
                        Strings.isNullOrEmpty(birthday) ||
                        Strings.isNullOrEmpty(graduationTime) ||
                        Strings.isNullOrEmpty(studentId)
                        ) {
                    return Code.API_PARRAM_ERROR;
                }*/
               /* if (Strings.isNullOrEmpty(avatar)) {
                    return Code.API_PARRAM_ERROR_AVATAR;
                }*/
                if (Strings.isNullOrEmpty(realname)) {
                    return Code.API_PARRAM_ERROR_NAME;
                }
                if (Strings.isNullOrEmpty(schoolId)) {
                    return Code.API_PARRAM_ERROR_SCHOOLID;
                }
                if (Strings.isNullOrEmpty(educationBackground)) {
                    return Code.API_PARRAM_ERROR_EDU;
                }
                if (Strings.isNullOrEmpty(major)) {
                    return Code.API_PARRAM_ERROR_MAJOR;
                }
                if (Strings.isNullOrEmpty(birthday)) {
                    return Code.API_PARRAM_ERROR_BIRTH;
                }
                if (Strings.isNullOrEmpty(graduationTime)) {
                    return Code.API_PARRAM_ERROR_GRADUAT;
                }
                if (Strings.isNullOrEmpty(studentId)) {
                    return Code.API_PARRAM_ERROR_STUDENTID;
                }



                AccountStudentinfo student = new AccountStudentinfo();
                student.setAccountId(account.getId());
                student.setCollect("");
                student.setSchoolChangeTime(0);
                student.setNameChangeTime(0);
                student.setPhone(phone);
                student.setProfessionCollect("");
                student.setCoin(0);
                student.setIdCardStatus(Account.AUTH_NO);
                student.setStudentIdStatus(Account.AUTH_NO);


                student.setAvatar(avatar);
                student.setRealname(realname);
                student.setSchoolId(schoolId);
                student.setEducationBackground(educationBackground);
                student.setMajor(major);
                student.setSex(sex);
                student.setBirthday(DateUtils.parseDate(birthday));
                student.setGraduationTime(DateUtils.parseDate(graduationTime));
                student.setStudentId(studentId);
                student.preInsert();
                accountStudentinfoDao.insert(student);
            }


        }
        //学校
        if (Account.ROLE_SCHOOL.equals(role)) {
            if (Strings.isNullOrEmpty(realname)) {
                return Code.API_REALNAME_NULL;
            }
            if (Strings.isNullOrEmpty(schoolId)) {
                return Code.API_SCHOOLID_NULL;
            }


            AccountTeacherinfo teacher = new AccountTeacherinfo();
            teacher.setAccountId(account.getId());
            teacher.setPhone(phone);
            teacher.setRealname(realname);
            teacher.setSchoolId(schoolId);
            teacher.setIdCardStatus(Account.AUTH_NO);
            teacher.preInsert();
            accountTeacherinfoDao.insert(teacher);
        }
        //企业
        if (Account.ROLE_COMPANY.equals(role)) {
            int becoin = 0;
            Account recommend = dao.get(recommendId);
            //推荐人
            if (recommend != null) {
                AccountCompanyhr recommendCompanyhr = accountCompanyhrDao.getByAccountId(recommend.getId());
                //推荐人存在,进入推荐注册流程
                if (recommendCompanyhr != null) {

                    //岗币记录 推荐人
                    AccountCoin accountCoin = new AccountCoin();
                    accountCoin.setCoin(Integer.valueOf(coin));
                    accountCoin.setType(AccountCoin.TYPE_RECOMMEND);
                    accountCoin.setAccountId(recommendId);
                    accountCoin.setAboutId(account.getId());
                    accountCoin.preInsert();
                    coinDao.insert(accountCoin);
                    //岗币记录 被推荐人
                    AccountCoin accountCoinBe = new AccountCoin();
                    accountCoinBe.setCoin(Integer.valueOf(be_coin));
                    accountCoinBe.setType(AccountCoin.TYPE_BE_RECOMMEND);
                    accountCoinBe.setAccountId(account.getId());
                    accountCoinBe.setAboutId(recommendId);
                    accountCoinBe.preInsert();
                    coinDao.insert(accountCoinBe);
                    accountCompanyhrDao.updateCoin(recommendCompanyhr.getAccountId(), recommendCompanyhr.getCoin() + Integer.valueOf(coin));


                    becoin = Integer.valueOf(be_coin);
                    //推荐记录
                    AccountRecommend recommend1 = new AccountRecommend();
                    recommend1.setAccountId(recommendId);
                    recommend1.setBeAccountId(account.getId());
                    recommend1.preInsert();
                    accountRecommendDao.insert(recommend1);
                }


            }


            //companyName companyAdress contact email
            if (Strings.isNullOrEmpty(companyName)) {
                return Code.API_COMPANYNAME_NULL;
            }
            if (Strings.isNullOrEmpty(contact)) {
                return Code.API_CONTACT_NULL;
            }
            if (Strings.isNullOrEmpty(email)) {
                return Code.API_EMAIL_NULL;
            }
            if (Strings.isNullOrEmpty(address)) {
                return Code.API_PARRAM_ERROR;
            }


            Company company = new Company();
            company.setLogo(companyLogo);
            company.setName(companyName);
            company.setAddress(address);
            company.setContact(contact);
            company.setEmail(email);
            company.setContactPhone(phone);
            company.setStatus(Company.STATUS_NO);
            company.preInsert();
            companyDao.insert(company);

            AccountCompanyhr companyhr = new AccountCompanyhr();
            companyhr.setAccountId(account.getId());
            companyhr.setPhone(phone);
            companyhr.setRealname(contact);
            companyhr.setCompanyId(company.getId());
            companyhr.setAvatar(companyImg);
            companyhr.setCoin(becoin);

            companyhr.setStatus(Account.AUTH_NO);
            companyhr.preInsert();
            accountCompanyhrDao.insert(companyhr);

            //添加后台账户
            User user = userDao.get("100");
            user.setLoginName(phone);
            user.setPassword(SystemService.entryptPassword(password));
            user.preInsert();
            user.setId(account.getId());
            userDao.insert(user);


            userDao.myInsertUserRole(user.getId(), "49a8d18b1d024353ac966f0f3eed98fa");

        }


        record.setStatus(SmsRecord.STATUS_VERIFIED);
        record.setVerifyTime(new Date());
        record.preUpdate();
        smsRecordDao.update(record);

        //最好需要的时候在创建id
        try {
            BasicResult<RegisterResult> Result = netEasyAPI.creatAccid();
            account.setAccid(Result.getInfo().getAccid());
            account.setToken(Result.getInfo().getToken());
            log.info("用户：" + account.getUsername() + "创建网易云信id成功");
        } catch (Exception e) {
            log.info("用户：" + account.getUsername() + "创建网易云信id失败");
        }

        dao.insert(account);
        account.setAuthToken("Bearer " + tokenUtils.generateToken(account.getId(), account.getRole()));
        account.setPassword(null);
        return account;
    }

    //更新企业信息
    @Transactional(readOnly = false)
    public Object company(Account account, Company company) {
        AccountCompanyhr companyhr = accountCompanyhrDao.getByAccountId(account.getId());
        if (!Account.ROLE_COMPANY.equals(account.getRole())) {
            return Code.API_USER_ROLE_ERROR;
        }
        Company c = companyDao.get(accountCompanyhrDao.getByAccountId(account.getId()).getCompanyId());
        if (!Strings.isNullOrEmpty(company.getLogo())) {
            c.setLogo(company.getLogo());
        }
        if (!c.getName().equals(company.getName()) && !Strings.isNullOrEmpty(company.getName())) {
            if (c.getStatus().equals(Company.STATUS_NO)) {
                c.setName(company.getName());
            } else {
                return Code.API_COMPANY_NAME_ERROR;
            }
        }


        if (!Strings.isNullOrEmpty(company.getShortName())) {
            c.setShortName(company.getShortName());
        }
        if (!Strings.isNullOrEmpty(company.getIntruduce())) {
            c.setIntruduce(company.getIntruduce());
        }
        if (!Strings.isNullOrEmpty((String) company.getIndustry())) {
            c.setIndustry(company.getIndustry());
        }
        if (!Strings.isNullOrEmpty(company.getVideo()) && !Strings.isNullOrEmpty(companyhr.getVip())) {
            c.setVideo(company.getVideo());
        }

        if (company.getImg() instanceof String[]) {
            if (!Strings.isNullOrEmpty(StringUtils.join((String[]) company.getImg(), "|"))) {
                c.setImg("|" + StringUtils.join((String[]) company.getImg(), "|"));
            }
        }
        if (company.getImg() instanceof String) {
            if (!Strings.isNullOrEmpty((String) company.getImg())) {
                c.setImg("|" + company.getImg());
            }
        }

        if (!Strings.isNullOrEmpty(company.getWebSite())) {
            c.setWebSite(company.getWebSite());
        }
        if (!Strings.isNullOrEmpty((String) company.getScale())) {
            c.setScale(company.getScale());
        }
        if (!Strings.isNullOrEmpty(company.getEmail())) {
            c.setEmail(company.getEmail());
        }
        if (!Strings.isNullOrEmpty((String) company.getNature())) {
            c.setNature(company.getNature());
        }
        if (!Strings.isNullOrEmpty(company.getBusinessLicence())) {
            c.setBusinessLicence(company.getBusinessLicence());
        }
        c.preUpdate();
        companyDao.update(c);
        return c;
    }

    public static void main(String[] args) throws IOException {
        System.out.println("Bearer " + new TokenUtils().generateToken("36c2c9052c33491095f56db80dc002a8", "3"));
    }
}