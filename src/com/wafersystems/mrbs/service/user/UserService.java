package com.wafersystems.mrbs.service.user;

import java.util.List;

import com.wafersystems.mrbs.dao.base.PageSortModel;
import com.wafersystems.mrbs.exception.BaseException;
import com.wafersystems.mrbs.exception.LoginFailedException;
import com.wafersystems.mrbs.vo.user.Department;
import com.wafersystems.mrbs.vo.user.UserInfo;
import com.wafersystems.mrbs.web.criteriavo.AutoCompleteItem;
import com.wafersystems.mrbs.web.criteriavo.SelectItem;

public interface UserService {

	public void saveUser(UserInfo user)throws BaseException;

	public void updateUser(UserInfo user)throws BaseException;

	public void delUser(UserInfo userInfo)throws BaseException;

	public UserInfo getUserByUserId(String userId);	
	
	public List<UserInfo> getUserList(PageSortModel psm, String userId, String userName) throws Throwable;
	
	public List<UserInfo> getUsersByUserName(String userName);
	
	public List<UserInfo> getUserListByDeptId(PageSortModel psm, Department deptId);
	
	public UserInfo checkUserLogin(String userName, String password)throws LoginFailedException;
	
	public void changePassword(UserInfo user, String oldPassword, String newPassword)throws BaseException;
	
	public void checkUserExit(String userId)throws BaseException;

	public List<UserInfo> getAllUnifiedUser();
	
	public List<UserInfo> getUserList(String hql);

	/**
	 * 同步专家，首先将专家全部改为删除状态，然后根据比对员工信息，当工号和姓名一致时，
	 * 修改状态为启用状态；不相同时创建新用户
	 * @param experts 专家列表
	 */
	public void saveSyncExpert(List<UserInfo> experts);

	/**
	 * 根据用户名、部门名模糊查询用户选单
	 * @param departmentKind 部门类型
	 * @param userNameOrDeptName 模糊查询的条件
	 * @param notInUserId 不包含的userId
	 * @return
	 * @throws Throwable
	 */
	public List<SelectItem> getUserListByUserNameOrDeptName(String departmentKind,String userNameOrDeptName,String notInUserId) throws Throwable;

	/**
	 * 根据用户的姓名的汉语或字母检索返回Autocomplete项
	 * @param name
	 * @return
	 * @throws Throwable
	 */
	public List<AutoCompleteItem> getUserListByUserName(String name)throws Throwable;
}
