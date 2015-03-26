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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.wafersystems.mrbs.vo.user.Department;
import com.wafersystems.mrbs.vo.user.UserInfo;
@Entity
@Table(name="icu_monitor")
public class ICUMonitor implements Serializable {
	private Integer id;
	private ICUPatientInfo patientInfo;//患者基本信息
	private String chiefComplaint;//主诉
	private String medicalRecord;//病历摘要
	private Set<ICUICD10> icuICD10;//ICD10初诊结果
	private String purpose;//会诊目的
	private String problem;//拟讨论问题
	private Department mainDept;//主会诊科室
	private Set<ICUDepartment> depts;//会诊科室
	private Set<Accessories> accessories;//附件
	
	private Short state;//
	private MeetingType meetingType;//会诊类型
	private MeetingLevel level;//申请级别
	private Date expectedTime;// 期望会诊时间
	private UserInfo requester;//申请人
	private ICUAuthorInfo authorInfo;//病历提交人信息
	private Date applicationTime;//申请时间
	private String refuseReason;//拒绝原因
	private Date startTime;//审核通过的开始时间
	private Date endTime;//审核通过的结束时间
	private Integer noticeSendTimes = 0;//通知发送次数
	
	private Short icdDisplayRadio;//初步诊断单选按钮 ：1:icd10  0：自定义
	private String icdUserDefined;//用户自定义初步诊断内容
	
	
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
	public ICUPatientInfo getPatientInfo() {
		return patientInfo;
	}
	public void setPatientInfo(ICUPatientInfo patientInfo) {
		this.patientInfo = patientInfo;
	}
	
	@Column(nullable=false, length=500)
	public String getChiefComplaint() {
		return chiefComplaint;
	}
	public void setChiefComplaint(String chiefComplaint) {
		this.chiefComplaint = chiefComplaint;
	}
	
	@Column(nullable=false, length=500)
	public String getMedicalRecord() {
		return medicalRecord;
	}
	public void setMedicalRecord(String medicalRecord) {
		this.medicalRecord = medicalRecord;
	}
	
	@OneToMany(mappedBy="icumonitor", cascade = CascadeType.ALL)
	@OrderBy(value = "id ASC")
	public Set<ICUICD10> getIcuICD10() {
		return icuICD10;
	}
	public void setIcuICD10(Set<ICUICD10> icuICD10) {
		this.icuICD10 = icuICD10;
	}
	
	@Column(nullable=false)
	public String getPurpose() {
		return purpose;
	}
	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}
	
	public String getProblem() {
		return problem;
	}
	public void setProblem(String problem) {
		this.problem = problem;
	}
	
	@ManyToOne
	@JoinColumn(nullable = false)
	public Department getMainDept() {
		return mainDept;
	}
	public void setMainDept(Department mainDept) {
		this.mainDept = mainDept;
	}
	
	@OneToMany(mappedBy="icumonitor", cascade = CascadeType.ALL)
	@OrderBy(value = "id ASC")
	public Set<ICUDepartment> getDepts() {
		return depts;
	}
	public void setDepts(Set<ICUDepartment> depts) {
		this.depts = depts;
	}
	
	@ManyToMany(cascade={CascadeType.ALL})
	public Set<Accessories> getAccessories() {
		return accessories;
	}
	public void setAccessories(Set<Accessories> accessories) {
		this.accessories = accessories;
	}
	
	@Column(nullable=false)
	public Short getState() {
		return state;
	}
	public void setState(Short state) {
		this.state = state;
	}
	
	@ManyToOne
	@JoinColumn(nullable=false)
	public MeetingType getMeetingType() {
		return meetingType;
	}
	public void setMeetingType(MeetingType meetingType) {
		this.meetingType = meetingType;
	}
	
	@ManyToOne
	public MeetingLevel getLevel() {
		return level;
	}
	public void setLevel(MeetingLevel level) {
		this.level = level;
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
	public ICUAuthorInfo getAuthorInfo() {
		return authorInfo;
	}
	public void setAuthorInfo(ICUAuthorInfo authorInfo) {
		this.authorInfo = authorInfo;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	public Date getApplicationTime() {
		return applicationTime;
	}
	public void setApplicationTime(Date applicationTime) {
		this.applicationTime = applicationTime;
	}
	
	@Column(length=1000)
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
	
	public Short getIcdDisplayRadio() {
		return icdDisplayRadio;
	}
	public void setIcdDisplayRadio(Short icdDisplayRadio) {
		this.icdDisplayRadio = icdDisplayRadio;
	}
	public String getIcdUserDefined() {
		return icdUserDefined;
	}
	public void setIcdUserDefined(String icdUserDefined) {
		this.icdUserDefined = icdUserDefined;
	}
	
	
}
