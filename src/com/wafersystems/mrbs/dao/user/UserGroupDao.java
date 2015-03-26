package com.wafersystems.mrbs.dao.user;

import com.wafersystems.mrbs.dao.base.GenericDao;
import com.wafersystems.mrbs.vo.user.UserGroup;

public interface UserGroupDao extends GenericDao<UserGroup>{

	public void delUserGroup(UserGroup userGroup);
}
