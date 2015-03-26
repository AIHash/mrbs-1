package com.wafersystems.mrbs.vo.param;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.ManyToOne;

@Entity
@IdClass(UnifiedParameterPK.class)
public class UnifiedParameter implements Serializable, Comparable<UnifiedParameter> {
	private static final long serialVersionUID = 6518137080891888848L;

	private ParamPackage packageKey;
	private String paramKey;
	private String paramDESC;
	private String value;
	private Short dispalyOrder;

	@Id
	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	public ParamPackage getPackageKey() {
		return packageKey;
	}

	public void setPackageKey(ParamPackage packageKey) {
		this.packageKey = packageKey;
	}

	@Id
	@Column(length = 128)
	public String getParamKey() {
		return paramKey;
	}

	public void setParamKey(String paramKey) {
		this.paramKey = paramKey;
	}

	@Column(nullable = false, length = 20)
	public String getParamDESC() {
		return paramDESC;
	}

	public void setParamDESC(String paramDESC) {
		this.paramDESC = paramDESC;
	}

	@Column(nullable = false)
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Short getDispalyOrder() {
		return dispalyOrder;
	}

	public void setDispalyOrder(Short dispalyOrder) {
		this.dispalyOrder = dispalyOrder;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((paramKey == null) ? 0 : paramKey.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UnifiedParameter other = (UnifiedParameter) obj;
		if (paramKey == null) {
			if (other.paramKey != null)
				return false;
		} else if (!paramKey.equals(other.paramKey))
			return false;
		return true;
	}

	@Override
	public int compareTo(UnifiedParameter o) {
		return this.dispalyOrder - o.dispalyOrder;
	}

}