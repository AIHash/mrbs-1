package com.wafersystems.mrbs.vo.user;

import java.io.Serializable;
import java.text.Collator;
import java.util.Date;
import java.util.Locale;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import com.wafersystems.mrbs.vo.right.Role;

@Entity
// 必须，其中name为可选，对应Hibernate映射时的name
@Table(name = "UnifiedUser")
// 实体类和数据库表名不一致时,才用这个
public class UserInfo implements Serializable, Comparable<UserInfo> {

	private static final long serialVersionUID = -3506495249682857627L;
	private String userId;
	private String name;// 共同体代表机构名，其他人代表姓名
	private String password;
	private Short gender;
	private String username;// 联系人姓名
	private String mail;// 联系人的Email
	private String mobile;// 联系人的手机号码
	private String telephone;// 联系人的电话号码
	private String videoPoint;
	private Date createTime;
	private Short state;
	private UserInfo creator;
	private UnifiedUserType userType;
	private Department deptId;
	private Role role;
	private String userLanguage;
	private String pyCode;
	private String hisCode;
	private Short source = 0;//用户来源；0代表从本系统录入，1代表从医院HIS系统录入，默认为0
	private Short allowedOrRefusedFlag;//是否允许当前人员参会

	public UserInfo(String userId) {
		super();
		this.userId = userId;
	}

	public UserInfo() {
		super();
	}

	@Id
	@GeneratedValue(generator = "userIdGenerator")
	@GenericGenerator(name = "userIdGenerator", strategy = "assigned", parameters = { @Parameter(name = "property", value = "userId") })
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Column(nullable = false, length = 25)
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	@Column(nullable = false, updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Column(nullable = false)
	public Short getState() {
		return state;
	}

	public void setState(Short state) {
		this.state = state;
	}

	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinColumn(nullable = false)
	public UnifiedUserType getUserType() {
		return userType;
	}

	public void setUserType(UnifiedUserType userType) {
		this.userType = userType;
	}

	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinColumn
	public Role getRole() {
		return role;
	}

	public void setRole(Role roleId) {
		this.role = roleId;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 共同体代表机构名，其他人代表姓名
	 */
	@Column(nullable = false, length = 50)
	public String getName() {
		return this.name;
	}

	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	public Department getDeptId() {
		return deptId;
	}

	public void setDeptId(Department deptId) {
		this.deptId = deptId;
	}

	public Short getGender() {
		return gender;
	}

	public void setGender(Short gender) {
		this.gender = gender;
	}

	public String getVideoPoint() {
		return videoPoint;
	}

	public void setVideoPoint(String videoPoint) {
		this.videoPoint = videoPoint;
	}

	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinColumn
	public UserInfo getCreator() {
		return creator;
	}

	public void setCreator(UserInfo creator) {
		this.creator = creator;
	}

	@Transient
	public String getUserLanguage() {
		return userLanguage;
	}

	public void setUserLanguage(String userLanguage) {
		this.userLanguage = userLanguage;
	}

	public String getPyCode() {
		return pyCode;
	}

	public void setPyCode(String pyCode) {
		this.pyCode = pyCode;
	}

	public String getHisCode() {
		return hisCode;
	}

	public void setHisCode(String hisCode) {
		this.hisCode = hisCode;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public Short getAllowedOrRefusedFlag() {
		return allowedOrRefusedFlag;
	}

	public void setAllowedOrRefusedFlag(Short allowedOrRefusedFlag) {
		this.allowedOrRefusedFlag = allowedOrRefusedFlag;
	}

	public Short getSource() {
		return source;
	}

	public void setSource(Short source) {
		this.source = source;
	}

	@Override
	public int compareTo(UserInfo o) {
		Collator collator = Collator.getInstance(Locale.CHINA);
		if(this.name.equals(o.getName()))
			return collator.compare(this.userId, o.userId);
		else
			return collator.compare(this.name, o.name);
		
	}

}
