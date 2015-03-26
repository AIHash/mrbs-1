package com.wafersystems.mrbs.web.report.vo;

public class StatsMeetingMember {
	
	/**
	 * 编号
	 */
	private String number;
	
	/**
	 * 名称
	 */
	private String name;
	
	/**
	 * 人数
	 */
	private String memberCount;
	
	/**
	 * 次数
	 */
	private String timesCount;

	public StatsMeetingMember() {
		super();
	}

	/**
	 * @return the number
	 */
	public String getNumber() {
		return number;
	}

	/**
	 * @param number the number to set
	 */
	public void setNumber(String number) {
		this.number = number;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
    
	/**
	 * @param memberCount the memberCount to set
	 */
	public void setMemberCount(String memberCount) {
		this.memberCount = memberCount;
	}
    
	/**
	 * @return the memberCount
	 */
	public String getMemberCount() {
		return memberCount;
	}
    
	/**
	 * @param timesCount the timesCount to set
	 */
	public void setTimesCount(String timesCount) {
		this.timesCount = timesCount;
	}
    
	/**
	 * @return the timesCount
	 */
	public String getTimesCount() {
		return timesCount;
	}


	
}
