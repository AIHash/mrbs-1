package com.wafersystems.mrbs.vo.meeting;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.wafersystems.mrbs.vo.user.UserInfo;

@Entity
@Table(name="meeting_satisfaction")
public class Satisfaction implements Serializable {

	private static final long serialVersionUID = -8359043975465152721L;
	private Integer id;
	private UserInfo user;
	private Meeting meeting;
	private String evaluationScore1;
	private String evaluationScore2;
	private String evaluationScore3;
	private Short localNumber;
	private String suggestion;
	private Date createTime;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@ManyToOne
	@JoinColumn(nullable=false)
	public Meeting getMeeting() {
		return meeting;
	}

	public void setMeeting(Meeting meeting) {
		this.meeting = meeting;
	}

	@Column(nullable = false)
	public String getEvaluationScore1() {
		return evaluationScore1;
	}

	public void setEvaluationScore1(String evaluationScore1) {
		this.evaluationScore1 = evaluationScore1;
	}

	@Column(nullable = false)
	public String getEvaluationScore2() {
		return evaluationScore2;
	}

	public void setEvaluationScore2(String evaluationScore2) {
		this.evaluationScore2 = evaluationScore2;
	}

	@Column(nullable = false)
	public String getEvaluationScore3() {
		return evaluationScore3;
	}

	public void setEvaluationScore3(String evaluationScore3) {
		this.evaluationScore3 = evaluationScore3;
	}

	@ManyToOne
	@JoinColumn(nullable=false)
	public UserInfo getUser() {
		return user;
	}

	public void setUser(UserInfo user) {
		this.user = user;
	}

	@Column(nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Column(nullable = false)
	public Short getLocalNumber() {
		return localNumber;
	}

	public void setLocalNumber(Short localNumber) {
		this.localNumber = localNumber;
	}

	public String getSuggestion() {
		return suggestion;
	}

	public void setSuggestion(String suggestion) {
		this.suggestion = suggestion;
	}

}
