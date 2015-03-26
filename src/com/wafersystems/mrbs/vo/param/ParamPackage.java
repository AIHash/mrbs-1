package com.wafersystems.mrbs.vo.param;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

@Entity(name="unified_parameter_package")
public class ParamPackage implements Serializable {

	private static final long serialVersionUID = 5016845786676200362L;
	private String packageKey;
	private String remark;
	private Boolean readOnly;
	private Set<UnifiedParameter> subParameter;

	@Id
	@GeneratedValue(generator="keyGenerator")
	@GenericGenerator(name="keyGenerator", strategy = "assigned", parameters={@Parameter(name = "property", value = "key")})
	@Column(length=128)
	public String getPackageKey() {
		return packageKey;
	}

	public void setPackageKey(String packageKey) {
		this.packageKey = packageKey;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Boolean getReadOnly() {
		return readOnly;
	}

	public void setReadOnly(Boolean readOnly) {
		this.readOnly = readOnly;
	}
	
	@OneToMany(mappedBy="packageKey", cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	public Set<UnifiedParameter> getSubParameter() {
		return subParameter;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((packageKey == null) ? 0 : packageKey.hashCode());
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
		ParamPackage other = (ParamPackage) obj;
		if (packageKey == null) {
			if (other.packageKey != null)
				return false;
		} else if (!packageKey.equals(other.packageKey))
			return false;
		return true;
	}

	public void setSubParameter(Set<UnifiedParameter> subParameter) {
		this.subParameter = subParameter;
	}
}
