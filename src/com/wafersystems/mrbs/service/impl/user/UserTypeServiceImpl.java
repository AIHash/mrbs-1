package com.wafersystems.mrbs.service.impl.user;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.wafersystems.mrbs.dao.user.UserTypeDao;
import com.wafersystems.mrbs.service.user.UserTypeService;
import com.wafersystems.mrbs.vo.user.UnifiedUserType;

@Service
public class UserTypeServiceImpl implements UserTypeService {

	private UserTypeDao userTypeDao;

	@Override
	public void saveUserType(UnifiedUserType userType) {
		userTypeDao.save(userType);
	}

	@Override
	public void updateUserType(UnifiedUserType userType) {
		userTypeDao.update(userType);
	}

	@Override
	public void delUserType(UnifiedUserType userType) {
		userTypeDao.delete(userType);
	}
	
	@Override
	public List<UnifiedUserType> getAll(){
		return userTypeDao.findAll();
	}
	
	@Override
	public UnifiedUserType getUserTypeByValue(Short value){
		if(value == null){
			return null;
		}else {
			String wherehql = " and value = ?";
			Object[] param = new Object[]{value};
			List<UnifiedUserType> datas = userTypeDao.limitFindByHql(-1, -1, wherehql, param, null);
			if(datas != null && datas.size() > 0)
				return datas.get(0);
			else return null;
		}
	}

	@Resource(type = UserTypeDao.class)
	public void setUserTypeDao(UserTypeDao userTypeDao) {
		this.userTypeDao = userTypeDao;
	}

}