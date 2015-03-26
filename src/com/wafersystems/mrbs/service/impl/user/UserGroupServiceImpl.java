package com.wafersystems.mrbs.service.impl.user;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.wafersystems.mrbs.dao.base.PageSortModel;
import com.wafersystems.mrbs.dao.user.UserGroupDao;
import com.wafersystems.mrbs.logmng.ann.MrbsLog;
import com.wafersystems.mrbs.service.user.UserGroupService;
import com.wafersystems.mrbs.vo.user.UserGroup;
import com.wafersystems.mrbs.vo.user.UserInfo;

@Service(value = "userGroupService")
public class UserGroupServiceImpl implements UserGroupService {

	private UserGroupDao userGroupDao;

	@MrbsLog(desc="admin.userGroup_create")
	@Override
	public void saveUserGroup(UserGroup userGroup) {
		userGroupDao.save(userGroup);
	}

	@MrbsLog(desc="admin.userGroup_update")
	@Override
	public void updateUserGroup(UserGroup userGroup) {
		userGroupDao.update(userGroup);
	}

	@MrbsLog(desc="admin.userGroup_delete")	
	@Override
	public void delUserGroup(short id) {
		userGroupDao.delete(id);
	}

	@MrbsLog(desc="admin.userGroup_delete")
	@Override
	public void delUserGroup(UserGroup userGroup){
		userGroupDao.delUserGroup(userGroup);
	}

	@MrbsLog(desc="admin.userGroup_saveUserGroupAndMember")
	@Override
	public void saveUserGroupAndMember(UserGroup userGroup, String[]userIds){
		Set<UserInfo> members = new HashSet<UserInfo>();
		UserInfo user;
		for (String userId : userIds) {
			user = new UserInfo();
			user.setUserId(userId);
			members.add(user);
		}
		userGroup.setMembers(members);
		userGroupDao.saveOrUpdate(userGroup);
	}

	@Override
	public UserGroup getUserGroup(short id){
		return userGroupDao.get(id);
	}

	@Override
	public List<UserGroup> getUserGroupByPro(PageSortModel psm,UserGroup userGroup){
		String wherejpql = "";
		List<Object> tempParams = new ArrayList<Object>(2);
		if(StringUtils.isNotBlank(userGroup.getName())){
			wherejpql += " and name like ?";
			tempParams.add("%" + userGroup.getName() + "%");
		}

		if(StringUtils.isNotBlank(userGroup.getRemark())){
			wherejpql += " and remark like ?";
			tempParams.add("%" + userGroup.getRemark() + "%");
		}

		Object[] queryParams = tempParams.toArray();
		LinkedHashMap<String, Object> orderby = new LinkedHashMap<String, Object>();
		orderby.put("id", "desc");
		List<UserGroup> temp = userGroupDao.getScrollData(psm, wherejpql, queryParams, orderby).getResultlist();

		return temp;
	}

	@Override
	public List<UserGroup> getUserGroupAll() {
		return this.userGroupDao.findAll();
	}

	@Resource(type = UserGroupDao.class)
	public void setUserGroupDao(UserGroupDao userGroupDao) {
		this.userGroupDao = userGroupDao;
	}

}