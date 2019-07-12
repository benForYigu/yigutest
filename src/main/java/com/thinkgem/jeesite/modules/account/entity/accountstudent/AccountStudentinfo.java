/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.account.entity.accountstudent;

import com.thinkgem.jeesite.modules.dict.entity.major.DictMajor;
import com.thinkgem.jeesite.modules.school.entity.School;
import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 用户_学生Entity
 * @author 李金辉
 * @version 2019-01-23
 */
public class AccountStudentinfo extends DataEntity<AccountStudentinfo> {
	
	private static final long serialVersionUID = 1L;
	private String accountId;		// account_id
	private String schoolId;		// 学校id
	private String nickname;		// 昵称
	private String avatar;		// 头像
	private String realname;		// 姓名
	private String phone;		// 电话
	private String school;		// 学校



	private Integer schoolChangeTime;		// 学校修改次数
	private Integer nameChangeTime;		// 名称修改次数
	private String department;		// 院系
	private Object educationBackground;		// 学历
	private Object major;		// 专业
	private Object sex;		// 1男2女
	private Date birthday;		// birthday
	private Date graduationTime;		// 毕业时间
	private String idCard;		// 身份证
	private Object idCardStatus;		// 身份证审核状态
	private String studentId;		// 学号
	private Object studentIdStatus;		// 学号审核状态
	private String email;		// 邮箱
	private Object incumbency;		// 职业状态
	private Integer coin;		// 岗币
	private String remark;		// 备注
	private String collect;		// 企业收藏
	private String professionCollect;		// 职位收藏

	private Integer age;		// 年龄
	private String schoolName;		// 学校名称
	private String majorName;		// 专业名称

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public Integer getNameChangeTime() {
		return nameChangeTime;
	}

	public void setNameChangeTime(Integer nameChangeTime) {
		this.nameChangeTime = nameChangeTime;
	}

	public String getSchoolName() {
		return schoolName;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}

	public String getMajorName() {
		return majorName;
	}

	public void setMajorName(String majorName) {
		this.majorName = majorName;
	}

	public String getProfessionCollect() {
		return professionCollect;
	}

	public void setProfessionCollect(String professionCollect) {
		this.professionCollect = professionCollect;
	}

	public String getCollect() {
		return collect;
	}

	public void setCollect(String collect) {
		this.collect = collect;
	}

	public AccountStudentinfo() {
		super();
	}

	public AccountStudentinfo(String id){
		super(id);
	}

	@Length(min=1, max=100, message="account_id长度必须介于 1 和 100 之间")
	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	
	@Length(min=1, max=100, message="学校id长度必须介于 1 和 100 之间")
	public String getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(String schoolId) {
		this.schoolId = schoolId;
	}
	
	@Length(min=0, max=100, message="昵称长度必须介于 0 和 100 之间")
	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	
	@Length(min=0, max=200, message="头像长度必须介于 0 和 200 之间")
	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	
	@Length(min=0, max=100, message="姓名长度必须介于 0 和 100 之间")
	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}
	
	@Length(min=0, max=20, message="电话长度必须介于 0 和 20 之间")
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	@Length(min=0, max=255, message="学校长度必须介于 0 和 255 之间")
	public String getSchool() {
		return school;
	}

	public void setSchool(String school) {
		this.school = school;
	}
	

	public Integer getSchoolChangeTime() {
		return schoolChangeTime;
	}

	public void setSchoolChangeTime(Integer schoolChangeTime) {
		this.schoolChangeTime = schoolChangeTime;
	}
	
	@Length(min=0, max=255, message="院系长度必须介于 0 和 255 之间")
	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}
	

	public Object getEducationBackground() {
		return educationBackground;
	}

	public void setEducationBackground(Object educationBackground) {
		this.educationBackground = educationBackground;
	}

	public Object getMajor() {
		return major;
	}

	public void setMajor(Object major) {
		this.major = major;
	}


	public Object getSex() {
		return sex;
	}

	public void setSex(Object sex) {
		this.sex = sex;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getGraduationTime() {
		return graduationTime;
	}

	public void setGraduationTime(Date graduationTime) {
		this.graduationTime = graduationTime;
	}
	
	@Length(min=0, max=255, message="身份证长度必须介于 0 和 255 之间")
	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}
	

	public Object getIdCardStatus() {
		return idCardStatus;
	}

	public void setIdCardStatus(Object idCardStatus) {
		this.idCardStatus = idCardStatus;
	}
	

	public String getStudentId() {
		return studentId;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}
	

	public Object getStudentIdStatus() {
		return studentIdStatus;
	}

	public void setStudentIdStatus(Object studentIdStatus) {
		this.studentIdStatus = studentIdStatus;
	}
	
	@Length(min=0, max=255, message="邮箱长度必须介于 0 和 255 之间")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	

	public Object getIncumbency() {
		return incumbency;
	}

	public void setIncumbency(Object incumbency) {
		this.incumbency = incumbency;
	}
	

	public Integer getCoin() {
		return coin;
	}

	public void setCoin(Integer coin) {
		this.coin = coin;
	}
	
	@Length(min=0, max=500, message="备注长度必须介于 0 和 500 之间")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}