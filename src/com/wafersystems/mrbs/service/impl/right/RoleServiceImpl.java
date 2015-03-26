package com.wafersystems.mrbs.service.impl.right;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.wafersystems.mrbs.dao.right.RoleDao;
import com.wafersystems.mrbs.service.right.RoleService;
import com.wafersystems.mrbs.vo.right.Role;

@Service
public class RoleServiceImpl implements RoleService {

	private RoleDao dao;

	@Override
	public void saveRole(Role vo) {
		dao.save(vo);
	}

	@Override
	public void updateRole(Role vo) {
		dao.update(vo);
	}

	@Override
	public void delRole(Role vo) {
		dao.delete(vo);

	}
	
	@Override
	public Role getRoleById(int id){
		return dao.get(id);
	}

	@Resource(type = RoleDao.class)
	public void setRoleDao(RoleDao dao) {
		this.dao = dao;
	}

}