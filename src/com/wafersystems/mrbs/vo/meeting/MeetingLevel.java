package com.wafersystems.mrbs.vo.meeting;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class MeetingLevel implements Serializable,Comparable<MeetingLevel> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7532968572551482687L;
	
	private Short id;
	private String name;
	private String remark;
	private Short value;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	public Short getId() {
		return id;
	}
	public void setId(Short id) {
		this.id = id;
	}
	
	@Column(nullable=false, length=20)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	@Column(nullable=false)
	public Short getValue() {
		return value;
	}
	public void setValue(Short value) {
		this.value = value;
	}

	@Override
	public int compareTo(MeetingLevel o) {
		return this.id - o.id;
	}

}
