package com.wafersystems.mrbs.vo.param;

import java.io.Serializable;

public class UnifiedParameterPK implements Serializable {

	private static final long serialVersionUID = 6319787425520050683L;

	private ParamPackage packageKey;
	private String paramKey;

	public UnifiedParameterPK(ParamPackage packageKey, String paramKey){
		this.packageKey = packageKey;
		this.paramKey = paramKey;
	}

	public UnifiedParameterPK() {
		super();
	}

	public ParamPackage getPackageKey() {
		return packageKey;
	}

	public void setPackageKey(ParamPackage packageKey) {
		this.packageKey = packageKey;
	}

	public String getParamKey() {
		return paramKey;
	}

	public void setParamKey(String paramKey) {
		this.paramKey = paramKey;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((packageKey == null) ? 0 : packageKey.hashCode());
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
		UnifiedParameterPK other = (UnifiedParameterPK) obj;
		if (packageKey == null) {
			if (other.packageKey != null)
				return false;
		} else if (!packageKey.equals(other.packageKey))
			return false;
		if (paramKey == null) {
			if (other.paramKey != null)
				return false;
		} else if (!paramKey.equals(other.paramKey))
			return false;
		return true;
	}

}
