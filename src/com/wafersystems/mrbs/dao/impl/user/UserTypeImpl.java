package com.wafersystems.mrbs.dao.impl.user;

import org.springframework.stereotype.Repository;

import com.wafersystems.mrbs.dao.base.GenericDaoImpl;
import com.wafersystems.mrbs.dao.user.UserTypeDao;
import com.wafersystems.mrbs.vo.user.UnifiedUserType;

@Repository
public class UserTypeImpl extends GenericDaoImpl<UnifiedUserType> implements UserTypeDao {

}
