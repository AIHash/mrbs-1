package com.wafersystems.mrbs.web.unified;

import java.util.Date;

public class MeetingInfoData {
	private Integer id;
	/** 会诊时间 */
	private Date startTime;
	/** 时间类型 */
	private String eventType;
	/** 会诊名称 */
	private String title;
	/** 会诊科室 */
	private String dept;
	/** 申请人 */
	private String requester;
	/** 会诊类型 */
	private String meetingType;
	/** 状态 */
	private String status;
	/** 操作 */
	private String operater;
	/** 评价是否结束 */
	private Boolean isSummaryOff;

	public String getOperater() {
		return operater;
	}

	public void setOperater(String operater) {
		this.operater = operater;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public String getEventType() {
		return eventType;
	}

	public void setEventType(String eventType) {
		this.eventType = eventType;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	public String getRequester() {
		return requester;
	}

	public void setRequester(String requester) {
		this.requester = requester;
	}

	public String getMeetingType() {
		return meetingType;
	}

	public void setMeetingType(String meetingType) {
		this.meetingType = meetingType;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Boolean getIsSummaryOff() {
		return isSummaryOff;
	}

	public void setIsSummaryOff(Boolean isSummaryOff) {
		this.isSummaryOff = isSummaryOff;
	}

}
