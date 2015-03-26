package com.wafersystems.mrbs.dao.meeting;

import java.util.List;

import com.wafersystems.mrbs.dao.base.GenericDao;
import com.wafersystems.mrbs.vo.meeting.AccessoriesType;

public interface AccessoriesTypeDao extends GenericDao<AccessoriesType>{
	
	public Short getMaxAccessoriesType();//得到附件类型表中最大的value

	public AccessoriesType getAccessoriesTypeByName(String name);
	
	public List<AccessoriesType> getAllAccessoriesType();//得到所有附件类型

}
