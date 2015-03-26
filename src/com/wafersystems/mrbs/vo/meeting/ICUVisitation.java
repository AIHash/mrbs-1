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
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.wafersystems.mrbs.vo.user.Department;
import com.wafersystems.mrbs.vo.user.UserInfo;
@Entity
@Table(name="icu_visitation")
public class ICUVisitation implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private ICUVisitationPatientInfo patientInfo;//患者基本信息
	 
	private Short state;//
	private Date expectedTime;// 期望会诊时间
	private UserInfo requester;//申请人
	private ICUVisitationAuthorInfo authorInfo;//病历提交人信息
	private Date applicationTime;//申请时间
	private String refuseReason;//拒绝原因
	private Date startTime;//审核通过的开始时间
	private Date endTime;//审核通过的结束时间
	private Integer noticeSendTimes = 0;//通知发送次数
	 
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	@OneToOne(cascade = {CascadeType.REMOVE})
	@JoinColumn(nullable = false)
	public ICUVisitationPatientInfo getPatientInfo() {
		return patientInfo;
	}
	public void setPatientInfo(ICUVisitationPatientInfo patientInfo) {
		this.patientInfo = patientInfo;
	}
	
	@Column(nullable=false)
	public Short getState() {
		return state;
	}
	public void setState(Short state) {
		this.state = state;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	public Date getExpectedTime() {
		return expectedTime;
	}
	public void setExpectedTime(Date expectedTime) {
		this.expectedTime = expectedTime;
	}
	
	@ManyToOne
	@JoinColumn(nullable=false)
	public UserInfo getRequester() {
		return requester;
	}
	public void setRequester(UserInfo requester) {
		this.requester = requester;
	}
	
	@OneToOne(cascade = {CascadeType.REMOVE})
	@JoinColumn(nullable=true)
	public ICUVisitationAuthorInfo getAuthorInfo() {
		return authorInfo;
	}
	public void setAuthorInfo(ICUVisitationAuthorInfo authorInfo) {
		this.authorInfo = authorInfo;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	public Date getApplicationTime() {
		return applicationTime;
	}
	public void setApplicationTime(Date applicationTime) {
		this.applicationTime = applicationTime;
	}
	
	
	public String getRefuseReason() {
		return refuseReason;
	}
	public void setRefuseReason(String refuseReason) {
		this.refuseReason = refuseReason;
	}
	
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	
	public Integer getNoticeSendTimes() {
		return noticeSendTimes;
	}
	public void setNoticeSendTimes(Integer noticeSendTimes) {
		this.noticeSendTimes = noticeSendTimes;
	}
	
}
