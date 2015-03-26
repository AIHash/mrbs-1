package com.wafersystems.mrbs.vo.meeting;

import java.io.Serializable;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.wafersystems.mrbs.vo.user.Department;
import com.wafersystems.mrbs.vo.user.UserInfo;

@Entity
@Table(name = "video_meeting_app")
public class VideoMeetingApp implements Serializable {

	private static final long serialVersionUID = -8890225144135856927L;
	private Integer id;
	//private VideoApplicationPurpose videoApplicationPurpose;// 视频申请目的
	private String lectureContent;// 讲座内容
	private Department deptName;// 主讲人所在科室
	private UserInfo userName;// 主讲人姓名
	private ApplicationPosition applicationPosition;// 职称
	private MeetingLevel level;
	private Date suggestDate;// 建议日期
	private Date appDate;// 申请日期
	private UserInfo videoRequester;// 申请人
	private Short state;// 状态
	private MeetingType meetingType;//
	private String refuseReason;//
	private Set<Accessories> accessories;//附件
	private Integer noticeSendTimes = 0;//通知发送次数
	private String teachingObject; //授课对象
	private Date startTime;//审核通过的开始时间
	private Date endTime;//审核通过的结束时间

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	@ManyToMany(cascade={CascadeType.ALL})
	public Set<Accessories> getAccessories() {
		return accessories;
	}

	public void setAccessories(Set<Accessories> accessories) {
		this.accessories = accessories;
	}
/*
	@ManyToOne
	@JoinColumn(nullable = true)
	public VideoApplicationPurpose getVideoApplicationPurpose() {
		return videoApplicationPurpose;
	}

	public void setVideoApplicationPurpose(
			VideoApplicationPurpose videoApplicationPurpose) {
		this.videoApplicationPurpose = videoApplicationPurpose;
	}
*/
	@Column(nullable = false)
	public String getLectureContent() {
		return lectureContent;
	}

	public void setLectureContent(String lectureContent) {
		this.lectureContent = lectureContent;
	}

	@ManyToOne
	@JoinColumn(nullable = true)
	public UserInfo getUserName() {
		return userName;
	}

	public void setUserName(UserInfo userName) {
		this.userName = userName;
	}

	@ManyToOne
	@JoinColumn(nullable = true)
	public Department getDeptName() {
		return deptName;
	}

	public void setDeptName(Department deptName) {
		this.deptName = deptName;
	}

	@ManyToOne
	@JoinColumn(nullable = true)
	public ApplicationPosition getApplicationPosition() {
		return applicationPosition;
	}

	public void setApplicationPosition(ApplicationPosition applicationPosition) {
		this.applicationPosition = applicationPosition;
	}

	@ManyToOne
	@JoinColumn(nullable = false)
	public MeetingLevel getLevel() {
		return level;
	}

	public void setLevel(MeetingLevel level) {
		this.level = level;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@JoinColumn(nullable = false)
	public Date getSuggestDate() {
		return suggestDate;
	}

	public void setSuggestDate(Date suggestDate) {
		this.suggestDate = suggestDate;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@JoinColumn(nullable = false)
	public Date getAppDate() {
		return appDate;
	}

	public void setAppDate(Date appDate) {
		this.appDate = appDate;
	}

	@ManyToOne
	@JoinColumn(nullable = false)
	public UserInfo getVideoRequester() {
		return videoRequester;
	}

	public void setVideoRequester(UserInfo videoRequester) {
		this.videoRequester = videoRequester;
	}

	@Column(nullable = false)
	public Short getState() {
		return state;
	}

	public void setState(Short state) {
		this.state = state;
	}

	@ManyToOne
	@JoinColumn(nullable = false)
	public MeetingType getMeetingType() {
		return meetingType;
	}

	public void setMeetingType(MeetingType meetingType) {
		this.meetingType = meetingType;
	}

	public String getRefuseReason() {
		return refuseReason;
	}

	public void setRefuseReason(String refuseReason) {
		this.refuseReason = refuseReason;
	}

	public Integer getNoticeSendTimes() {
		return noticeSendTimes;
	}

	public void setNoticeSendTimes(Integer noticeSendTimes) {
		this.noticeSendTimes = noticeSendTimes;
	}

	public void setTeachingObject(String teachingObject) {
		this.teachingObject = teachingObject;
	}
	@Column(nullable = true)
	public String getTeachingObject() {
		return teachingObject;
	}

	/**
	 * @return the startTime
	 */
	public Date getStartTime() {
		return startTime;
	}

	/**
	 * @param startTime the startTime to set
	 */
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	/**
	 * @return the endTime
	 */
	public Date getEndTime() {
		return endTime;
	}

	/**
	 * @param endTime the endTime to set
	 */
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

}
