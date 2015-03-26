package com.wafersystems.mrbs.dao.impl.user;

import org.springframework.stereotype.Repository;

import com.wafersystems.mrbs.dao.base.GenericDaoImpl;
import com.wafersystems.mrbs.dao.user.UserGroupDao;
import com.wafersystems.mrbs.vo.user.UserGroup;

@Repository
public class UserGroupDaoImpl extends GenericDaoImpl<UserGroup> implements UserGroupDao {

	@Override
	public void delUserGroup(UserGroup userGroup) {
		getHibernateTemplate().delete(userGroup);
	}

}
