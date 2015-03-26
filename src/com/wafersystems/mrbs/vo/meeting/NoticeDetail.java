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
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import sun.awt.image.BufImgSurfaceData.ICMColorData;

import com.wafersystems.mrbs.vo.user.UnifiedUserType;

@Entity
public class NoticeDetail implements Serializable{
	
	private static final long serialVersionUID = 1530655809821331202L;
	
	/**
	 * 主键
	 */
	private Integer id;                               
	
	/**
	 * 病历讨论id
	 */
	private MeetingApplication meetingApplicationId;  
	
	/**
	 * 通知发送时间
	 */
	private String sendTime;                            
	
	/**
	 * 接收人姓名
	 */
	private String userName;                          
	
	/**
	 * 接收人类型
	 */
	private UnifiedUserType userType;                
	
	
	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	public Integer getId() {
		return id;
	}
	
	/**
	 * @param meetingApplicationId the meetingApplicationId to set
	 */
	public void setMeetingApplicationId(MeetingApplication meetingApplicationId) {
		this.meetingApplicationId = meetingApplicationId;
	}

	/**
	 * @return the meetingApplicationId
	 */
	@ManyToOne(cascade={CascadeType.PERSIST, CascadeType.MERGE})
	@JoinColumn(nullable=false)
	public MeetingApplication getMeetingApplicationId() {
		return meetingApplicationId;
	}
	
	/**
	 * @param sendTime the sendTime to set
	 */
	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}
	
	/**
	 * @return the sendTime
	 */
	@Column(nullable=false)
	public String getSendTime() {
		return sendTime;
	}
	
	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	/**
	 * @return the userName
	 */
	@Column(nullable=false)
	public String getUserName() {
		return userName;
	}
	
	/**
	 * @param userType the userType to set
	 */
	public void setUserType(UnifiedUserType userType) {
		this.userType = userType;
	}
	
	/**
	 * @return the userType
	 */
	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinColumn(nullable=false)
	public UnifiedUserType getUserType() {
		return userType;
	}
	
	
}
