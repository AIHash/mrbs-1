package com.wafersystems.mrbs.vo.meeting;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.wafersystems.mrbs.vo.user.UserInfo;

/**
 * 会诊总结表
 * @author yangsf
 */
@Entity
public class MeetingSummary{

	private Integer id;
	private Meeting meeting;// 会诊
	private UserInfo user;// 代表评价的人
	private String summary;// 评价内容
	private UserInfo submitUser;//评价内容提交人

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@OneToOne
	public Meeting getMeeting() {
		return meeting;
	}

	public void setMeeting(Meeting meeting) {
		this.meeting = meeting;
	}

	@Column(length = 500)
	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	@ManyToOne
	public UserInfo getUser() {
		return user;
	}

	public void setUser(UserInfo user) {
		this.user = user;
	}

	@ManyToOne
	public UserInfo getSubmitUser() {
		return submitUser;
	}

	public void setSubmitUser(UserInfo submitUser) {
		this.submitUser = submitUser;
	}

}
