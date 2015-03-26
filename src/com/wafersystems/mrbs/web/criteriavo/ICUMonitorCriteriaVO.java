package com.wafersystems.mrbs.web.criteriavo;

public class ICUMonitorCriteriaVO {


	
	/**
	 * 建议会诊日期
	 */
	private String expectedTime;
	
	/**
	 * 建议会诊日期起
	 */
	private String expectedTimeStart;
	
	/**
	 * 建议会诊日期止
	 */
	private String expectedTimeEnd;
	
	/**
	 * 申请时间
	 */
	private String applicationTime;
	
	/**
	 * 会诊类型
	 */
	private String meetingType;
	
	/**
	 * 申请人
	 */
	private String requesterId;
	
	/**
	 * 申请状态
	 */
	private String state;
	
	/**
	 * 申请级别
	 */
	private String level;
	
	
	/**
	 * 关键字
	 */
	private String keyWord;
	
	/**
	 * 患者姓名
	 */
	private String patientName;

	/**
	 * @return the expectedTime
	 */
	public String getExpectedTime() {
		return expectedTime;
	}

	/**
	 * @param expectedTime the expectedTime to set
	 */
	public void setExpectedTime(String expectedTime) {
		this.expectedTime = expectedTime;
	}

	/**
	 * @return the expectedTimeStart
	 */
	public String getExpectedTimeStart() {
		return expectedTimeStart;
	}

	/**
	 * @param expectedTimeStart the expectedTimeStart to set
	 */
	public void setExpectedTimeStart(String expectedTimeStart) {
		this.expectedTimeStart = expectedTimeStart;
	}

	/**
	 * @return the expectedTimeEnd
	 */
	public String getExpectedTimeEnd() {
		return expectedTimeEnd;
	}

	/**
	 * @param expectedTimeEnd the expectedTimeEnd to set
	 */
	public void setExpectedTimeEnd(String expectedTimeEnd) {
		this.expectedTimeEnd = expectedTimeEnd;
	}

	/**
	 * @return the applicationTime
	 */
	public String getApplicationTime() {
		return applicationTime;
	}

	/**
	 * @param applicationTime the applicationTime to set
	 */
	public void setApplicationTime(String applicationTime) {
		this.applicationTime = applicationTime;
	}

	/**
	 * @return the meetingType
	 */
	public String getMeetingType() {
		return meetingType;
	}

	/**
	 * @param meetingType the meetingType to set
	 */
	public void setMeetingType(String meetingType) {
		this.meetingType = meetingType;
	}

	/**
	 * @return the requesterId
	 */
	public String getRequesterId() {
		return requesterId;
	}

	/**
	 * @param requesterId the requesterId to set
	 */
	public void setRequesterId(String requesterId) {
		this.requesterId = requesterId;
	}

	/**
	 * @return the state
	 */
	public String getState() {
		return state;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}
	
	/**
	 * @return the level
	 */
	public String getLevel() {
		return level;
	}
	
	/**
	 * @param level the level to set
	 */
	public void setLevel(String level) {
		this.level = level;
	}
	
	/**
	 * @return the keyWord
	 */	
	public String getKeyWord() {
		return keyWord;
	}
	
	/**
	 * @param keyWord the keyWord to set
	 */
	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}
	
	/**
	 * @return the patientName
	 */	
	public String getPatientName() {
		return patientName;
	}
	
	/**
	 * @param patientName the patientName to set
	 */
	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}

	public ICUMonitorCriteriaVO() {

	}

}
