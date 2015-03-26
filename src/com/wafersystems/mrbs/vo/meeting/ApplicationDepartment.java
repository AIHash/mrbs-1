package com.wafersystems.mrbs.vo.meeting;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.wafersystems.mrbs.vo.user.Department;

@Entity
@Table(name = "application_department")
/**
 * 会诊科室
 */
public class ApplicationDepartment implements Serializable {

	private static final long serialVersionUID = -1793681714686295236L;
	private Integer id;
	private MeetingApplication meetingApplication;
	private Department department;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@ManyToOne
	@JoinColumn(nullable = false)
	public MeetingApplication getMeetingApplication() {
		return meetingApplication;
	}

	public void setMeetingApplication(MeetingApplication meetingApplication) {
		this.meetingApplication = meetingApplication;
	}

	@ManyToOne
	@JoinColumn(nullable = false)
	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

}
