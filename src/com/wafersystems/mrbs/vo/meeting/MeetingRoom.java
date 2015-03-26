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

@Entity
public class MeetingRoom implements Serializable {
	private static final long serialVersionUID = -2330843416789906487L;

	private Integer id;
	private String name;
	private String remark;
	private boolean needConfirm;
	private Short state;
	private String sn;
	private Short seats;
	private String size;
	private Short maxHours;
	private String purpose;
	private String mailAddress;
	private Set<Meeting> meetings;
	private String icuDeviceNO;
	private Short mark;    //会议标志1：离线，0：在线
	public MeetingRoom(Integer id) {
		super();
		this.id = id;
	}

	public MeetingRoom() {
		super();
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(nullable = false, length = 20)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public boolean isNeedConfirm() {
		return needConfirm;
	}

	public void setNeedConfirm(boolean needConfirm) {
		this.needConfirm = needConfirm;
	}

	@Column(nullable = false)
	public Short getState() {
		return state;
	}

	public void setState(Short state) {
		this.state = state;
	}

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public Short getSeats() {
		return seats;
	}

	public void setSeats(Short seats) {
		this.seats = seats;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public Short getMaxHours() {
		return maxHours;
	}

	public void setMaxHours(Short maxHours) {
		this.maxHours = maxHours;
	}

	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

	@Column(length = 50)
	public String getMailAddress() {
		return mailAddress;
	}

	public void setMailAddress(String mailAddress) {
		this.mailAddress = mailAddress;
	}

	@OneToMany(mappedBy = "meetingRoomId", fetch = FetchType.LAZY)
	public Set<Meeting> getMeetings() {
		return meetings;
	}

	public void setMeetings(Set<Meeting> meetings) {
		this.meetings = meetings;
	}

	@Column(nullable = false,name="mark")
	public Short getMark() {
		return mark;
	}

	/**
	 * @param mark the mark to set
	 */
	public void setMark(Short mark) {
		this.mark = mark;
	}
	@Column(name="icuDeviceNO")
	public String getIcuDeviceNO() {
		return icuDeviceNO;
	}

	public void setIcuDeviceNO(String icuDeviceNO) {
		this.icuDeviceNO = icuDeviceNO;
	}

}
