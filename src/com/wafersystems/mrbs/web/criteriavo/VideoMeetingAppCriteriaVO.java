package com.wafersystems.mrbs.web.criteriavo;

public class VideoMeetingAppCriteriaVO {
	
	/**
	 * 视频申请目的
	 */
	private String videoApplicationPurpose;

	/**
	 * 建议会诊日期
	 */
	private String suggestDate;
	
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
	private String appDate;
	
	/**
	 * 申请人
	 */
	private String requesterId;
	
	/**
	 * 主讲人所在科室
	 */
	private String deptName;
	
	/**
	 * 主讲人姓名
	 */
	private String userId;
	
	/**
	 * 申请状态
	 */
	private String state;
		
	/**
	 * 关键字
	 */
	private String keyWord;
	
	public VideoMeetingAppCriteriaVO() {

	}

	/**
	 * @return the videoApplicationPurpose
	 */
	public String getVideoApplicationPurpose() {
		return videoApplicationPurpose;
	}

	/**
	 * @param videoApplicationPurpose the videoApplicationPurpose to set
	 */
	public void setVideoApplicationPurpose(String videoApplicationPurpose) {
		this.videoApplicationPurpose = videoApplicationPurpose;
	}

	/**
	 * @return the suggestDate
	 */
	public String getSuggestDate() {
		return suggestDate;
	}

	/**
	 * @param suggestDate the suggestDate to set
	 */
	public void setSuggestDate(String suggestDate) {
		this.suggestDate = suggestDate;
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
	 * @return the appDate
	 */
	public String getAppDate() {
		return appDate;
	}

	/**
	 * @param appDate the appDate to set
	 */
	public void setAppDate(String appDate) {
		this.appDate = appDate;
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
	 * @return the deptName
	 */
	public String getDeptName() {
		return deptName;
	}

	/**
	 * @param deptName the deptName to set
	 */
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
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

}
