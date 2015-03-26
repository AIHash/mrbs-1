package com.wafersystems.mrbs.vo.meeting;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class AccessoriesType implements Serializable {

	private static final long serialVersionUID = 5083107558379673733L;

	private Short id;
	private String name;
	private Short value;

	public AccessoriesType(Short id) {
		super();
		this.id = id;
	}

	public AccessoriesType() {
		super();
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Short getId() {
		return id;
	}

	public void setId(Short id) {
		this.id = id;
	}

	@Column(nullable = false, length = 20)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(nullable = false)
	public Short getValue() {
		return value;
	}

	public void setValue(Short value) {
		this.value = value;
	}

}
