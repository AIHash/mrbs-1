package com.wafersystems.mrbs.vo;

import java.io.Serializable;

public class FinalUnifiedUser implements Serializable {

	private static final long serialVersionUID = -3506495249682857627L;
	private String deptName;//部门名称
	private String deptNode;
	private String userId;//登录名
	private String userName;//用户名
	private Integer userType;
	private String lianxiren;//联系人
	private String telephone;//电话
	private String moblie;//手机
	private String mail;

	public Integer getUserType() {
		return userType;
	}

	public void setUserType(Integer userType) {
		this.userType = userType;
	}

	public String getDeptNode() {
		return deptNode;
	}

	public void setDeptNode(String deptNode) {
		this.deptNode = deptNode;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getLianxiren() {
		return lianxiren;
	}

	public void setLianxiren(String lianxiren) {
		this.lianxiren = lianxiren;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getMoblie() {
		return moblie;
	}

	public void setMoblie(String moblie) {
		this.moblie = moblie;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
