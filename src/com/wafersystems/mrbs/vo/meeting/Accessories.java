package com.wafersystems.mrbs.vo.meeting;

import java.io.Serializable;
import java.text.Collator;
import java.util.Locale;

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
public class Accessories implements Serializable,Comparable<Accessories> {
	private static final long serialVersionUID = 7483374838926208523L;
	private Integer id;
	private String name;
	private String remark;
	private String path;
	private UserInfo owner;
	private AccessoriesType type;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(nullable=false)
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
	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	@ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinColumn(nullable=false)
	public UserInfo getOwner() {
		return owner;
	}

	public void setOwner(UserInfo owner) {
		this.owner = owner;
	}

	@ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinColumn(nullable=false)
	public AccessoriesType getType() {
		return type;
	}

	public void setType(AccessoriesType type) {
		this.type = type;
	}

	@Override
	public int compareTo(Accessories o) {
		Collator collator = Collator.getInstance(Locale.CHINA);
		if(this.type.equals(o.type)){
			if(this.name.equals(o.name))
				return collator.compare(this.path, o.path);
			else
				return collator.compare(this.name, o.name);
		}
		else
			return collator.compare(this.type.getName(), o.getType().getName());
	}

}
