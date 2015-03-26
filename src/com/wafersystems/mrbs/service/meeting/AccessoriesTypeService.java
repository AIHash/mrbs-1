package com.wafersystems.mrbs.service.meeting;

import java.util.List;

import com.wafersystems.mrbs.vo.meeting.AccessoriesType;

public interface AccessoriesTypeService {

	public void saveAccessoriesType(AccessoriesType vo);
	
	public short saveOrUpdateAccessoriesType(AccessoriesType vo);

	public void updateAccessoriesType(AccessoriesType vo);

	public void delAccessoriesType(AccessoriesType vo);
	
	public AccessoriesType getAccessoriesTypeById(Short id);
	
	public List<AccessoriesType> getAllAccessoriesTypes();//获得所有附件类型
	
}
