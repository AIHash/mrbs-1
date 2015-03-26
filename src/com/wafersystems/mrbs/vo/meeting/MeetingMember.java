package com.wafersystems.mrbs.vo.meeting;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.wafersystems.mrbs.vo.user.UserInfo;

@Entity
public class MeetingMember implements Serializable {
	private static final long serialVersionUID = -6578635855719091358L;
	private Integer id;
	private Meeting meetingId;
	private UserInfo member;
	private Short attendState;
	private Short attendNo;
	private Date ackTime;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	@ManyToOne(cascade={CascadeType.PERSIST, CascadeType.MERGE})
	@JoinColumn(nullable=false)
	public Meeting getMeetingId() {
		return meetingId;
	}

	public void setMeetingId(Meeting meetingId) {
		this.meetingId = meetingId;
	}
	
	@ManyToOne(cascade={CascadeType.PERSIST, CascadeType.MERGE})
	@JoinColumn(nullable=false)
	public UserInfo getMember() {
		return member;
	}

	public void setMember(UserInfo member) {
		this.member = member;
	}
	
	@Column(nullable=false)
	public Short getAttendState() {
		return attendState;
	}

	public void setAttendState(Short attendState) {
		this.attendState = attendState;
	}

	public Short getAttendNo() {
		return attendNo;
	}

	public void setAttendNo(Short attendNo) {
		this.attendNo = attendNo;
	}

	@Temporal(TemporalType.TIMESTAMP)
	public Date getAckTime() {
		return ackTime;
	}

	public void setAckTime(Date ackTime) {
		this.ackTime = ackTime;
	}

}
