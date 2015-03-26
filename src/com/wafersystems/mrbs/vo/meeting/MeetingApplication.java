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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.OrderBy;
import javax.persistence.Transient;

import com.wafersystems.mrbs.vo.user.Department;
import com.wafersystems.mrbs.vo.user.UserInfo;
/**
 * 会诊申请
 *
 */
@Entity
public class MeetingApplication implements Serializable {
	private static final long serialVersionUID = 1899482024016785507L;
	private Integer id;
	private ApplicationPatientInfo patientInfo;//患者基本信息
	private String chiefComplaint;//主诉
	private String medicalRecord;//病历摘要
	private Set<ApplicationICD10> applicationICD10s;//ICD10初诊结果
	private String purpose;//会诊目的
	private String problem;//拟讨论问题
	private Department mainDept;//主会诊科室
	private Set<ApplicationDepartment> depts;//会诊科室
	private Set<Accessories> accessories;//附件
	
	private Short state;//
	private MeetingType meetingType;//会诊类型
	private MeetingLevel level;//申请级别
	private Date expectedTime;// 期望会诊时间
	private UserInfo requester;//申请人
	private ApplicationAuthorInfo authorInfo;//病历提交人信息
	private Date applicationTime;//申请时间
	private String refuseReason;//拒绝原因
	private Date startTime;//审核通过的开始时间
	private Date endTime;//审核通过的结束时间
	private Integer noticeSendTimes = 0;//通知发送次数
	
	private Short icdDisplayRadio;//初步诊断单选按钮 ：1:icd10  0：自定义
	private String icdUserDefined;//用户自定义初步诊断内容
    private String meetingTypestr; 
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
	public ApplicationPatientInfo getPatientInfo() {
		return patientInfo;
	}

	public void setPatientInfo(ApplicationPatientInfo patientInfo) {
		this.patientInfo = patientInfo;
	}

	@OneToMany(mappedBy="meetingApplication", cascade = CascadeType.ALL)
	@OrderBy(value = "id ASC")
	public Set<ApplicationDepartment> getDepts() {
		return depts;
	}

	public void setDepts(Set<ApplicationDepartment> depts) {
		this.depts = depts;
	}

	@Column(nullable=false)
	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
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
	@JoinColumn(nullable = false)
	public Department getMainDept() {
		return mainDept;
	}

	public void setMainDept(Department mainDept) {
		this.mainDept = mainDept;
	}

	@ManyToMany(cascade={CascadeType.ALL})
	public Set<Accessories> getAccessories() {
		return accessories;
	}

	public void setAccessories(Set<Accessories> accessories) {
		this.accessories = accessories;
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

	@Temporal(TemporalType.TIMESTAMP)
	public Date getApplicationTime() {
		return applicationTime;
	}

	public void setApplicationTime(Date applicationTime) {
		this.applicationTime = applicationTime;
	}

	@ManyToOne
	@JoinColumn(nullable=false)
	public UserInfo getRequester() {
		return requester;
	}

	public void setRequester(UserInfo requester) {
		this.requester = requester;
	}

	@OneToMany(mappedBy="meetingApplication", cascade = CascadeType.ALL)
	@OrderBy(value = "id ASC")
	public Set<ApplicationICD10> getApplicationICD10s() {
		return applicationICD10s;
	}
	
	public void setApplicationICD10s(Set<ApplicationICD10> applicationICD10s) {
		this.applicationICD10s = applicationICD10s;
	}

	@OneToOne(cascade = {CascadeType.REMOVE})
	@JoinColumn(nullable=true)
	public ApplicationAuthorInfo getAuthorInfo() {
		return authorInfo;
	}

	public void setAuthorInfo(ApplicationAuthorInfo authorInfo) {
		this.authorInfo = authorInfo;
	}

	public String getRefuseReason() {
		return refuseReason;
	}

	public void setRefuseReason(String refuseReason) {
		this.refuseReason = refuseReason;
	}

	public String getProblem() {
		return problem;
	}

	public void setProblem(String problem) {
		this.problem = problem;
	}

	public Integer getNoticeSendTimes() {
		return noticeSendTimes;
	}

	public void setNoticeSendTimes(Integer noticeSendTimes) {
		this.noticeSendTimes = noticeSendTimes;
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

	public Short getIcdDisplayRadio() {
		return icdDisplayRadio;
	}

	public void setIcdDisplayRadio(Short icdDisplayRadio) {
		this.icdDisplayRadio = icdDisplayRadio;
	}

	@Column(length=500)
	public String getIcdUserDefined() {
		return icdUserDefined;
	}

	public void setIcdUserDefined(String icdUserDefined) {
		this.icdUserDefined = icdUserDefined;
	}

	/**
	 * @return the meetingTypestr
	 */
	@Transient
	public String getMeetingTypestr() {
		return meetingTypestr;
	}

	/**
	 * @param meetingTypestr the meetingTypestr to set
	 */
	public void setMeetingTypestr(String meetingTypestr) {
		this.meetingTypestr = meetingTypestr;
	}	
	
}