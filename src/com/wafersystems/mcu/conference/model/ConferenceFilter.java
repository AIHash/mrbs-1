package com.wafersystems.mcu.conference.model;

import java.io.Serializable;

/**
 * <p>@Desc 枚举会议所需的过滤输入条件
 * <p>@Author baininghan
 * <p>@Date 2014年12月3日
 * <p>@version 1.0
 *
 */
public class ConferenceFilter implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String authenticationUser;
	private String authenticationPassword;
	
	/**
	 * 枚举编号，用于遍历下一个会议
	 * 如果响应结果中包含一个enumerateID，则应用会把这个枚举编号传给下一个枚举Call去遍历下一组会议
	 * 如果省略这个参数，则目标设备将开始一个新的遍历，并返回第一个结果集
	 */
	private Integer enumerateID;
	/**
	 * true：请求激活的会议
	 * false：请求所有的会议
	 */
	private Boolean activeFilter;
	
	
	/**
	 * @return the enumerateID
	 */
	public Integer getEnumerateID() {
		return enumerateID;
	}
	/**
	 * @param enumerateID the enumerateID to set
	 */
	public void setEnumerateID(Integer enumerateID) {
		this.enumerateID = enumerateID;
	}
	/**
	 * @return the activeFilter
	 */
	public Boolean getActiveFilter() {
		return activeFilter;
	}
	/**
	 * @param activeFilter the activeFilter to set
	 */
	public void setActiveFilter(Boolean activeFilter) {
		this.activeFilter = activeFilter;
	}
	/**
	 * @return the authenticationUser
	 */
	public String getAuthenticationUser() {
		return authenticationUser;
	}
	/**
	 * @param authenticationUser the authenticationUser to set
	 */
	public void setAuthenticationUser(String authenticationUser) {
		this.authenticationUser = authenticationUser;
	}
	/**
	 * @return the authenticationPassword
	 */
	public String getAuthenticationPassword() {
		return authenticationPassword;
	}
	/**
	 * @param authenticationPassword the authenticationPassword to set
	 */
	public void setAuthenticationPassword(String authenticationPassword) {
		this.authenticationPassword = authenticationPassword;
	}
	
}
