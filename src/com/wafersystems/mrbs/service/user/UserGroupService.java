package com.wafersystems.mrbs.service.user;

import java.util.List;

import com.wafersystems.mrbs.dao.base.PageSortModel;
import com.wafersystems.mrbs.vo.user.UserGroup;

public interface UserGroupService {

	public void saveUserGroup(UserGroup userGroup);

	public void updateUserGroup(UserGroup userGroup);

	public void delUserGroup(short id);

	public void delUserGroup(UserGroup userGroup);

	public void saveUserGroupAndMember(UserGroup userGroup, String[]members);

	public UserGroup getUserGroup(short id);

	public List<UserGroup> getUserGroupByPro(PageSortModel psm,UserGroup userGroup);

	public List<UserGroup> getUserGroupAll();
}
