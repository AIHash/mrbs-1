package com.wafersystems.mrbs.vo.meeting;

import java.util.Date;

import com.wafersystems.mrbs.vo.user.Department;
import com.wafersystems.mrbs.vo.user.UserInfo;

public class CommonMeeting {

	private Integer id;
	private String title;
	private Department department;
	private UserInfo requester;
	private MeetingType meetingType;
	private Date expectedTime;
	private Short state;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Department getDepartment() {
		return department;
	}
	public void setDepartment(Department department) {
		this.department = department;
	}
	public UserInfo getRequester() {
		return requester;
	}
	public void setRequester(UserInfo requester) {
		this.requester = requester;
	}
	public MeetingType getMeetingType() {
		return meetingType;
	}
	public void setMeetingType(MeetingType meetingType) {
		this.meetingType = meetingType;
	}
	public Date getExpectedTime() {
		return expectedTime;
	}
	public void setExpectedTime(Date expectedTime) {
		this.expectedTime = expectedTime;
	}
	public Short getState() {
		return state;
	}
	public void setState(Short state) {
		this.state = state;
	}

	
	
}
