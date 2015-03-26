package com.wafersystems.mrbs.service.param;

import java.util.Collection;
import java.util.List;

import com.wafersystems.mrbs.exception.BaseException;
import com.wafersystems.mrbs.vo.param.ParamPackage;

public interface ParamPackageService {

	public void updateParamPackage(ParamPackage vo) throws BaseException;
	
	public void updateParamPackageBatch(Collection<ParamPackage> paramPackages) throws BaseException;
	
	public ParamPackage getParamPackageById(String key);
	
	public List<ParamPackage> getAll();

}
