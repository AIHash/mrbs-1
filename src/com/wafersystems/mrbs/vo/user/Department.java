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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

@Entity
public class Department implements Serializable {

	private static final long serialVersionUID = -6937960012600259133L;
	private Integer id;
	private String name;
	private String deptcode;
	private String parentDeptCode;
	private Department parentDetp;
	private String remark;
	private String hisCode;// His系统中的代码
	private String pyCode;// 拼音代码
	private Integer state = 1;// 状态,0为不可用，1为可用
	private Set<UserInfo> users;
	private Set<Department> subDepts;

	public Department() {
		super();
	}

	public Department(Integer id) {
		super();
		this.id = id;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(nullable = false, length = 20)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDeptcode() {
		return deptcode;
	}

	public void setDeptcode(String deptcode) {
		this.deptcode = deptcode;
	}

	public String getParentDeptCode() {
		return parentDeptCode;
	}

	public void setParentDeptCode(String parentDeptCode) {
		this.parentDeptCode = parentDeptCode;
	}

	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinColumn
	public Department getParentDetp() {
		return parentDetp;
	}

	public void setParentDetp(Department parentDetp) {
		this.parentDetp = parentDetp;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * 不加mappedBy会多生成一张新表 mappedBy属性用于双向关联实体时，标注在不保存关系的实体中，基本上相当于 inverse="true"
	 */
	@OneToMany(mappedBy = "deptId", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@OrderBy("userId")
	public Set<UserInfo> getUsers() {
		return users;
	}

	public void setUsers(Set<UserInfo> users) {
		this.users = users;
	}

	@OneToMany(mappedBy = "parentDetp", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@OrderBy("deptcode")
	public Set<Department> getSubDepts() {
		return subDepts;
	}

	public void setSubDepts(Set<Department> subDepts) {
		this.subDepts = subDepts;
	}

	@Column(updatable = false)
	public String getHisCode() {
		return hisCode;
	}

	public void setHisCode(String hisCode) {
		this.hisCode = hisCode;
	}

	@Column
	public String getPyCode() {
		return pyCode;
	}

	public void setPyCode(String pyCode) {
		this.pyCode = pyCode;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

}
