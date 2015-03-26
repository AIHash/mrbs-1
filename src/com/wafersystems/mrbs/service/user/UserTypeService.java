package com.wafersystems.mrbs.service.user;

import java.util.List;

import com.wafersystems.mrbs.vo.user.UnifiedUserType;

public interface UserTypeService {

	public void saveUserType(UnifiedUserType userType);

	public void updateUserType(UnifiedUserType userType);

	public void delUserType(UnifiedUserType userType);
	
	public List<UnifiedUserType> getAll();
	
	public UnifiedUserType getUserTypeByValue(Short value);

}
