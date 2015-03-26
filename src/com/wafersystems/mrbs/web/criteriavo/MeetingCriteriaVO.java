package com.wafersystems.mrbs.web.criteriavo;

public class MeetingCriteriaVO {
	
	/**
	 * 接受人
	 */
	private String acceptUserId;
	
	/**
	 * 受邀状态
	 */
	private String invitedState;
	
	/**
	 * 主键PK
	 */
	private String meetingId;
	
	/**
	 * 代表申请的会诊类型（远程会诊为1；视频讲座为2）
	 */
	private String meetingKind;
	
	/**
	 * 开始时间
	 */
	private String meetingStartTime;
	
	/**
	 * 结束时间
	 */
	private String meetingEndTime;
	
	/**
	 * 当天开始时间
	 */
	private String startTime;
	
	/**
	 * 会诊类型
	 */
	private String meetingType;
	
	/**
	 * 讲座目的
	 */
	private String purposeName;
	
	/**
	 * 主讲人所在科室
	 */
	private String departmentName;
	
	/**
	 * 主讲人姓名Id
	 */
	private String mainUserId;

	/**
	 * 当前状态
	 */
	private String state;
	
	/**
	 * 申请人姓名
	 */
	private String requesterUserName;
	
	/**
	 * 关键字
	 */
	private String keyWord;

	/**
	 * @return the acceptUserId
	 */
	public String getAcceptUserId() {
		return acceptUserId;
	}

	/**
	 * @param acceptUserId the acceptUserId to set
	 */
	public void setAcceptUserId(String acceptUserId) {
		this.acceptUserId = acceptUserId;
	}

	/**
	 * @return the invitedState
	 */
	public String getInvitedState() {
		return invitedState;
	}

	/**
	 * @param invitedState the invitedState to set
	 */
	public void setInvitedState(String invitedState) {
		this.invitedState = invitedState;
	}

	/**
	 * @return the meetingId
	 */
	public String getMeetingId() {
		return meetingId;
	}

	/**
	 * @param meetingId the meetingId to set
	 */
	public void setMeetingId(String meetingId) {
		this.meetingId = meetingId;
	}

	/**
	 * @return the meetingKind
	 */
	public String getMeetingKind() {
		return meetingKind;
	}

	/**
	 * @param meetingKind the meetingKind to set
	 */
	public void setMeetingKind(String meetingKind) {
		this.meetingKind = meetingKind;
	}

	/**
	 * @return the meetingStartTime
	 */
	public String getMeetingStartTime() {
		return meetingStartTime;
	}

	/**
	 * @param meetingStartTime the meetingStartTime to set
	 */
	public void setMeetingStartTime(String meetingStartTime) {
		this.meetingStartTime = meetingStartTime;
	}

	/**
	 * @return the meetingEndTime
	 */
	public String getMeetingEndTime() {
		return meetingEndTime;
	}

	/**
	 * @param meetingEndTime the meetingEndTime to set
	 */
	public void setMeetingEndTime(String meetingEndTime) {
		this.meetingEndTime = meetingEndTime;
	}

	/**
	 * @return the startTime
	 */
	public String getStartTime() {
		return startTime;
	}

	/**
	 * @param startTime the startTime to set
	 */
	public void setStartTime(String startTime) {
		this.startTime = startTime;
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
	 * @return the purposeName
	 */
	public String getPurposeName() {
		return purposeName;
	}

	/**
	 * @param purposeName the purposeName to set
	 */
	public void setPurposeName(String purposeName) {
		this.purposeName = purposeName;
	}

	/**
	 * @return the departmentName
	 */
	public String getDepartmentName() {
		return departmentName;
	}

	/**
	 * @param departmentName the departmentName to set
	 */
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	/**
	 * @return the mainUserId
	 */
	public String getMainUserId() {
		return mainUserId;
	}

	/**
	 * @param mainUserId the mainUserId to set
	 */
	public void setMainUserId(String mainUserId) {
		this.mainUserId = mainUserId;
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
	 * @return the requesterUserName
	 */
	public String getRequesterUserName() {
		return requesterUserName;
	}

	/**
	 * @param requesterUserName the requesterUserName to set
	 */
	public void setRequesterUserName(String requesterUserName) {
		this.requesterUserName = requesterUserName;
	}
	/**
	 * @param keyWord the keyWord to set
	 */
	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}
	/**
	 * @return the keyWord
	 */
	public String getKeyWord() {
		return keyWord;
	}
	
}
