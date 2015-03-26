package com.wafersystems.mrbs.vo.meeting;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.wafersystems.mrbs.vo.user.UserInfo;

//@Entity(name="meeting_summary")
@Deprecated
public class Summary implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -154175795387539260L;
	
	private Integer id;
	private UserInfo holder;
	private Meeting meeting;
	private String content;
	private Accessories accessories;
	
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
	public UserInfo getHolder() {
		return holder;
	}
	public void setHolder(UserInfo holder) {
		this.holder = holder;
	}
	
	@ManyToOne(cascade={CascadeType.PERSIST, CascadeType.MERGE})
	@JoinColumn(nullable=false)
	public Meeting getMeeting() {
		return meeting;
	}
	public void setMeeting(Meeting meeting) {
		this.meeting = meeting;
	}
	
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	@OneToOne(cascade=CascadeType.MERGE)
	public Accessories getAccessories() {
		return accessories;
	}
	public void setAccessories(Accessories accessories) {
		this.accessories = accessories;
	}

}
