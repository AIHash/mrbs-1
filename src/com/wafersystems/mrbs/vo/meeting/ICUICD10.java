package com.wafersystems.mrbs.vo.meeting;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
@Entity
@Table(name = "ICU_icd10")
public class ICUICD10 implements Serializable {
	/**
	 * 重症icd10对应
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private ICUMonitor icumonitor;
	private ICD10DIC icd;
	
	
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
	public ICUMonitor getIcumonitor() {
		return icumonitor;
	}

	public void setIcumonitor(ICUMonitor icumonitor) {
		this.icumonitor = icumonitor;
	}

	@ManyToOne
	@JoinColumn(nullable=false)
	public ICD10DIC getIcd() {
		return icd;
	}
	public void setIcd(ICD10DIC icd) {
		this.icd = icd;
	}

}
