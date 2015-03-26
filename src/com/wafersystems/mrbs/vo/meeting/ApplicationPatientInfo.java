package com.wafersystems.mrbs.vo.meeting;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "application_patient_info")
/**
 * 患者基本信息
 */
public class ApplicationPatientInfo implements Serializable {

	private static final long serialVersionUID = -8940325448320917272L;
	private Integer id;
	private String name;
	private String sex;
	private String age;
	private String address;
	private String socialSecurityNO;//社保号

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(nullable = false, length = 50)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(nullable = false, length = 4)
	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	@Column(nullable = false, length = 10)
	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	@Column(nullable = true, length = 250)
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * @return the socialSecurityNO
	 */
	@Column(nullable = true, length = 250)
	public String getSocialSecurityNO() {
		return socialSecurityNO;
	}

	/**
	 * @param socialSecurityNO the socialSecurityNO to set
	 */
	public void setSocialSecurityNO(String socialSecurityNO) {
		this.socialSecurityNO = socialSecurityNO;
	}

}
