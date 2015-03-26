package com.wafersystems.mrbs.vo.meeting;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.wafersystems.mrbs.vo.user.UserInfo;

@Entity
public class ApplicationOperationStep implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3015437718602198675L;
	
	private Integer id;
	private MeetingApplication application;
	private Integer step;
	private UserInfo owner;
	private String reason;
	private Short state;
	private String requestId;
	
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
	public MeetingApplication getApplication() {
		return application;
	}
	public void setApplication(MeetingApplication application) {
		this.application = application;
	}
	
	@Column(nullable=false)
	public Integer getStep() {
		return step;
	}
	public void setStep(Integer step) {
		this.step = step;
	}
	
	@ManyToOne(cascade={CascadeType.PERSIST, CascadeType.MERGE})
	@JoinColumn(nullable=false)
	public UserInfo getOwner() {
		return owner;
	}
	public void setOwner(UserInfo owner) {
		this.owner = owner;
	}
	
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	
	@Column(nullable=false)
	public Short getState() {
		return state;
	}
	public void setState(Short state) {
		this.state = state;
	}
	
	public String getRequestId() {
		return requestId;
	}
	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

}
