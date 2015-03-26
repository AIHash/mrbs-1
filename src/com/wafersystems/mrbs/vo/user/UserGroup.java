package com.wafersystems.mrbs.vo.user;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

@Entity
@Table(name="unified_user_group")
public class UserGroup implements Serializable {

	private static final long serialVersionUID = -8525297681719577550L;

	private Short id;
	private String name;
	private String remark;
	private Set<UserInfo> members;
	
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
	
	@ManyToMany(fetch = FetchType.LAZY,cascade={CascadeType.PERSIST, CascadeType.REMOVE})
	@OrderBy("userId")
	public Set<UserInfo> getMembers() {
		return members;
	}

	public void setMembers(Set<UserInfo> members) {
		this.members = members;
	}

}
