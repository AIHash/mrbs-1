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
public class MoTelecon implements Serializable {
	
	private static final long serialVersionUID = 4126991958399448769L;

	/**
	 * PK
	 */
	private Integer id;

	/**
	 * MO 短信的短信ID ，对应发送队列表（MT 表）的SRC_ID字段。
	 */
	private String smId;
	
	/**
	 * 手机号
	 */
	private String mobile;
	
	/**
	 * 短信内容
	 */
	private String content;
	
	/**
	 * 短信时间
	 */
	private Date moTime;
	
	/**
	 * 数据是否过期标记
	 * 0 未过期
	 * 1 已过期
	 */
	private Integer dataExpiredFlag = GlobalConstent.DATE_EXPIRED_FLAG_N;
	
	/**
	 * 数据是否处理标记
	 * 0 未处理
	 * 1 已处理
	 */
	private Integer dataOperatedFlag = GlobalConstent.DATE_OPERATED_FALG_N;

	/**
	 * 
	 */
	public MoTelecon() {
		super();
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
	@Column(nullable=false)
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
	 * @return the moTime
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable=false)
	public Date getMoTime() {
		return moTime;
	}

	/**
	 * @param moTime the moTime to set
	 */
	public void setMoTime(Date moTime) {
		this.moTime = moTime;
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
	 * @return the dataOperatedFlag
	 */
	@Column(nullable=false)
	public Integer getDataOperatedFlag() {
		return dataOperatedFlag;
	}

	/**
	 * @param dataOperatedFlag the dataOperatedFlag to set
	 */
	public void setDataOperatedFlag(Integer dataOperatedFlag) {
		this.dataOperatedFlag = dataOperatedFlag;
	}

}
