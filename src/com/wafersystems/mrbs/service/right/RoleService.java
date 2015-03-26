package com.wafersystems.mrbs.service.right;

import com.wafersystems.mrbs.vo.right.Role;

public interface RoleService {

	public void saveRole(Role vo);

	public void updateRole(Role vo);

	public void delRole(Role vo);
	
	public Role getRoleById(int id);

}
