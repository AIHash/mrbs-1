package com.wafersystems.mrbs.vo.mas;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.wafersystems.mrbs.GlobalConstent;
@Entity
public class MtTelecon implements Serializable {

	private static final long serialVersionUID = -7058917995122521558L;

	/**
	 * PK
	 */
	private Integer id;

	/**
	 * 短信ID，缺省值0。当SM_ID为0时，表示这类短信不需要辨别其回执、回复。
	 */
	private String smId;
	
	/**
	 * 手机上显示尾号（终端源地址）
	 */
	private String srcId;
	
	/**
	 * 手机号
	 */
	private String mobile;
	
	/**
	 * 短信内容
	 */
	private String content;
	
	/**
	 * 短信定时发送时间，格式为：yyyy-MM-dd HH:mm:ss，为null时立即发送。
	 */
	private Date sendTime;
	
	/**
	 * 数据是否过期标记
	 * 0 未过期
	 * 1 已过期
	 */
	private Integer dataExpiredFlag = GlobalConstent.DATE_EXPIRED_FLAG_N;
	
	/**
	 * 短信业务代码
	 * 001 通知管理员有需要审核的病历讨论申请
	 * 002 通知申请人，其申请病历讨论已被审核通过
	 * 003 通知申请人，其申请病历讨论审核时被拒绝
	 * 004 通知管理员有需要审核的视频讲座申请
	 * 005 通知申请人，其申请视频讲座已被审核通过
	 * 006 通知申请人，其申请视频讲座审核时被拒绝 
	 * 007 通知受邀人，有需要其接受的病历讨论邀请
	 * 008 通知受邀人，有需要其接受的视频讲座邀请
	 */
	private String smsServiceCode;
	
	/**
	 * 短信发送状态
	 */
	private Short state = GlobalConstent.NOTICE_NOT_SEND;
	
	/**
	 * 短信发送次数
	 */
	private Integer resendTimes = 0;
	
	/**
	 * 短信接受人的Id
	 */
	private String acceptSmsUserId;
	
	/**
	 * 病历讨论或讲座申请的Id
	 */
	private Integer applicationId;
	
	/**
	 * constructor
	 */
	public MtTelecon() {

	}

	/**
	 * @return the id
	 */
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	public Integer getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @return the smId
	 */
	@Column(nullable=false, length=10)
	public String getSmId() {
		return smId;
	}

	/**
	 * @param smId the smId to set
	 */
	public void setSmId(String smId) {
		this.smId = smId;
	}

	/**
	 * @return the srcId
	 */
	@Column(nullable=false, length=10)
	public String getSrcId() {
		return srcId;
	}

	/**
	 * @param srcId the srcId to set
	 */
	public void setSrcId(String srcId) {
		this.srcId = srcId;
	}

	/**
	 * @return the mobile
	 */
	@Column(nullable=false, length=15)
	public String getMobile() {
		return mobile;
	}

	/**
	 * @param mobile the mobile to set
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	/**
	 * @return the content
	 */
	@Column(nullable=false,length=2000)
	public String getContent() {
		return content;
	}

	/**
	 * @param content the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * @return the sendTime
	 */
	@Temporal(TemporalType.TIMESTAMP)
	public Date getSendTime() {
		return sendTime;
	}

	/**
	 * @param sendTime the sendTime to set
	 */
	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}

	/**
	 * @return the dataExpiredFlag
	 */
	public Integer getDataExpiredFlag() {
		return dataExpiredFlag;
	}

	/**
	 * @param dataExpiredFlag the dataExpiredFlag to set
	 */
	public void setDataExpiredFlag(Integer dataExpiredFlag) {
		this.dataExpiredFlag = dataExpiredFlag;
	}

	/**
	 * @return the smsServiceCode
	 */
	@Column(nullable=false, length=10)
	public String getSmsServiceCode() {
		return smsServiceCode;
	}

	/**
	 * @param smsServiceCode the smsServiceCode to set
	 */
	public void setSmsServiceCode(String smsServiceCode) {
		this.smsServiceCode = smsServiceCode;
	}

	/**
	 * @return the state
	 */
	@Column(nullable=false)
	public Short getState() {
		return state;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(Short state) {
		this.state = state;
	}

	/**
	 * @return the resendTimes
	 */
	public Integer getResendTimes() {
		return resendTimes;
	}

	/**
	 * @param resendTimes the resendTimes to set
	 */
	public void setResendTimes(Integer resendTimes) {
		this.resendTimes = resendTimes;
	}

	/**
	 * @return the acceptSmsUserId
	 */
	@Column(length=20)
	public String getAcceptSmsUserId() {
		return acceptSmsUserId;
	}

	/**
	 * @param acceptSmsUserId the acceptSmsUserId to set
	 */
	public void setAcceptSmsUserId(String acceptSmsUserId) {
		this.acceptSmsUserId = acceptSmsUserId;
	}

	/**
	 * @return the applicationId
	 */
	public Integer getApplicationId() {
		return applicationId;
	}

	/**
	 * @param applicationId the applicationId to set
	 */
	public void setApplicationId(Integer applicationId) {
		this.applicationId = applicationId;
	}
	
}
