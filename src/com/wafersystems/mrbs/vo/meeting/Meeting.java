package com.wafersystems.mrbs.vo.meeting;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.wafersystems.mrbs.vo.user.UserInfo;

@Entity
public class Meeting implements Serializable {

	private static final long serialVersionUID = -8890225144135856927L;
	private Integer id;
	private MeetingRoom meetingRoomId;
	private String title;
	private String content;
	private MeetingType meetingType;
	private Date startTime;
	private Date endTime;
	private Date realStartTime;
	private Date realEndTime;
	private Short beforeMin;
	private Short state;
	private UserInfo creator;
	private UserInfo holder;
	private Set<MeetingMember> members;
	private String remark;
	private MeetingApplication applicationId;
	private VideoMeetingApp videoapplicationId;

	private MeetingLevel level;
	private Set<Satisfaction> satisfactions;
	private Set<SatisfactionManager> satisfactionManager;
//	private Summary summary;
	private Set<VideoREC> records;
	private Short meetingKind;//代表申请的会诊类型（远程会诊为1；视频讲座为2）
	private Set<MeetingSummary> meetingSummarys;//会诊总结

	private Boolean isSummaryOff = false;//代表当前登录用户是否对此会议进行过评价
	
	private String conferenceGUID;//Globally unique identifier of the conference.
	private Integer haveCallMCU;//定义是否已经在MCU创建了此会议
	private ICUMonitor iCUMonitorId;
	private ICUVisitation iCUVisitationId;
	
	private  String tcsURL;
	
	@Column(name="tcsURL",length=200)
	public String getTcsURL() {
		return tcsURL;
	}

	public void setTcsURL(String tcsURL) {
		this.tcsURL = tcsURL;
	}

	/**
	 * @return the haveCallMCU
	 */
	@Column(name="haveCallMCU")
	public Integer getHaveCallMCU() {
		return haveCallMCU;
	}

	/**
	 * @param haveCallMCU the haveCallMCU to set
	 */
	public void setHaveCallMCU(Integer haveCallMCU) {
		this.haveCallMCU = haveCallMCU;
	}

	/**
	 * @return the conferenceGUID
	 */
	@Column(name="conferenceGUID")
	public String getConferenceGUID() {
		return conferenceGUID;
	}

	/**
	 * @param conferenceGUID the conferenceGUID to set
	 */
	public void setConferenceGUID(String conferenceGUID) {
		this.conferenceGUID = conferenceGUID;
	}

	public Meeting(Integer id) {
		super();
		this.id = id;
	}

	public Meeting() {
		super();
	}

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@ManyToOne
	@JoinColumn(nullable=false)
	public MeetingRoom getMeetingRoomId() {
		return meetingRoomId;
	}

	public void setMeetingRoomId(MeetingRoom meetingRoomId) {
		this.meetingRoomId = meetingRoomId;
	}

	@Column(nullable=false, length=50)
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@ManyToOne
	@JoinColumn(nullable=false)
	public MeetingType getMeetingType() {
		return meetingType;
	}

	public void setMeetingType(MeetingType meetingType) {
		this.meetingType = meetingType;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable=false)
	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	@Temporal(TemporalType.TIMESTAMP)
	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Short getBeforeMin() {
		return beforeMin;
	}

	public void setBeforeMin(Short beforeMin) {
		this.beforeMin = beforeMin;
	}
	
	@Column(nullable=false)
	public Short getState() {
		return state;
	}

	public void setState(Short state) {
		this.state = state;
	}

	@ManyToOne(cascade={CascadeType.PERSIST, CascadeType.MERGE})
	@JoinColumn(nullable=false)
	public UserInfo getCreator() {
		return creator;
	}

	public void setCreator(UserInfo creator) {
		this.creator = creator;
	}

	@ManyToOne(cascade={CascadeType.PERSIST, CascadeType.MERGE})
	public UserInfo getHolder() {
		return holder;
	}

	public void setHolder(UserInfo holder) {
		this.holder = holder;
	}

	@OneToMany(mappedBy = "meetingId", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, fetch=FetchType.EAGER)
	public Set<MeetingMember> getMembers() {
		return members;
	}

	public void setMembers(Set<MeetingMember> members) {
		this.members = members;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@OneToOne(cascade={CascadeType.PERSIST, CascadeType.MERGE})
	public MeetingApplication getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(MeetingApplication applicationId) {
		this.applicationId = applicationId;
	}
	
	@OneToOne(cascade={CascadeType.PERSIST, CascadeType.MERGE})
	public ICUMonitor getiCUMonitorId() {
		return iCUMonitorId;
	}

	public void setiCUMonitorId(ICUMonitor iCUMonitorId) {
		this.iCUMonitorId = iCUMonitorId;
	}
	
	@OneToOne(cascade={CascadeType.PERSIST, CascadeType.MERGE})
	public ICUVisitation getiCUVisitationId() {
		return iCUVisitationId;
	}

	public void setiCUVisitationId(ICUVisitation iCUVisitationId) {
		this.iCUVisitationId = iCUVisitationId;
	}
	
	@OneToOne(cascade={CascadeType.PERSIST, CascadeType.MERGE})
	public VideoMeetingApp getVideoapplicationId() {
		return videoapplicationId;
	}

	public void setVideoapplicationId(VideoMeetingApp videoapplicationId) {
		this.videoapplicationId = videoapplicationId;
	}

	@ManyToOne(cascade={CascadeType.PERSIST, CascadeType.MERGE})
	public MeetingLevel getLevel() {
		return level;
	}

	public void setLevel(MeetingLevel level) {
		this.level = level;
	}

	@OneToMany(mappedBy="meeting", cascade={CascadeType.PERSIST, CascadeType.REMOVE}, fetch=FetchType.EAGER)
	public Set<Satisfaction> getSatisfactions() {
		return satisfactions;
	}

	public void setSatisfactions(Set<Satisfaction> satisfactions) {
		this.satisfactions = satisfactions;
	}
/*
	@OneToOne(mappedBy="meeting", cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	public Summary getSummary() {
		return summary;
	}

	public void setSummary(Summary summary) {
		this.summary = summary;
	}
*/
	@OneToMany(mappedBy="meeting", cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	public Set<VideoREC> getRecords() {
		return records;
	}

	public void setRecords(Set<VideoREC> records) {
		this.records = records;
	}

	@Transient
	public Boolean getIsSummaryOff() {
		return isSummaryOff;
	}

	public void setIsSummaryOff(Boolean isSummaryOff) {
		this.isSummaryOff = isSummaryOff;
	}

	@OneToMany(mappedBy="meeting", cascade={CascadeType.PERSIST, CascadeType.REMOVE}, fetch=FetchType.LAZY)
	public Set<SatisfactionManager> getSatisfactionManager() {
		return satisfactionManager;
	}

	public void setSatisfactionManager(Set<SatisfactionManager> satisfactionManager) {
		this.satisfactionManager = satisfactionManager;
	}

	@Column(nullable=false)
	public Short getMeetingKind() {
		return meetingKind;
	}

	public void setMeetingKind(Short meetingKind) {
		this.meetingKind = meetingKind;
	}

	@OneToMany(mappedBy="meeting", cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	public Set<MeetingSummary> getMeetingSummarys() {
		return meetingSummarys;
	}

	public void setMeetingSummarys(Set<MeetingSummary> meetingSummarys) {
		this.meetingSummarys = meetingSummarys;
	}

	public Date getRealStartTime() {
		return realStartTime;
	}

	public void setRealStartTime(Date realStartTime) {
		this.realStartTime = realStartTime;
	}

	public Date getRealEndTime() {
		return realEndTime;
	}

	public void setRealEndTime(Date realEndTime) {
		this.realEndTime = realEndTime;
	}
	

}
