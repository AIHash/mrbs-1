package com.wafersystems.mrbs.vo.meeting;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="meeting_mobile_devices")
public class MeetingMobileDevices implements Serializable {
	private static final long serialVersionUID = -2330843416789906487L;
    private Integer Id;
	private Integer meetingId;
	private String devicesNo;
	 
	 
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Integer getId() {
		return Id;
	}

	public void setId(Integer Id) {
		this.Id = Id;
	}

	@Column(nullable = false, length = 20)
	public Integer getMeetingId() {
		return meetingId;
	}

	public void setMeetingId(Integer meetingId) {
		this.meetingId = meetingId;
	}
	@Column(nullable = false, length = 200)
	public String getDevicesNo() {
		return devicesNo;
	}
	
	public void setDevicesNo(String devicesNo) {
		this.devicesNo = devicesNo;
	}
 
}
