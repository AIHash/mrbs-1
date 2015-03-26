package com.wafersystems.mrbs.vo.user;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class UnifiedUserType implements Serializable {

	private static final long serialVersionUID = 5827164414420251134L;

	private Short id;
	private String name;
	private Short value;

	public UnifiedUserType() {
		super();
	}

	public UnifiedUserType(Short id) {
		super();
		this.id = id;
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
