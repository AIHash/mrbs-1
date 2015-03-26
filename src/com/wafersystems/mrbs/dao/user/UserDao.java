package com.wafersystems.mrbs.dao.user;

import java.util.List;

import com.wafersystems.mrbs.dao.base.GenericDao;
import com.wafersystems.mrbs.dao.base.PageSortModel;
import com.wafersystems.mrbs.vo.user.UserInfo;
import com.wafersystems.mrbs.web.criteriavo.AutoCompleteItem;
import com.wafersystems.mrbs.web.criteriavo.SelectItem;

public interface UserDao extends GenericDao<UserInfo> {

	/**
	 * 根据医院hisCode和姓名查询当前用户是否已经在本系统
	 * @param hisCode
	 * @param name
	 * @return
	 */
	public UserInfo getUserByHisCode(String hisCode, String name);

	/**
	 * 设置全部专家状态为删除
	 * @return 更新的数目
	 */
	public int updateAllHisExpertStateDelete();

	/**
	 * hisCode和用户名相同后，将用户状态改为可用
	 */
	public void updateHisExpertInfo(UserInfo user);
	
	/**
	 * 根据用户名、部门名模糊查询用户选单
	 * @param departmentKind 部门类型
	 * @param userNameOrDeptName 模糊查询的条件
	 * @param notInUserId 不包含的userId
	 * @return
	 * @throws Throwable
	 */
	public List<SelectItem> getUserListByUserNameOrDeptName(String departmentKind,String userNameOrDeptName,String notInUserId)throws Throwable;

	/**
	 * 根据用户的姓名的汉语或字母检索返回Autocomplete项
	 * @param name
	 * @return
	 * @throws Throwable
	 */
	public List<AutoCompleteItem> getUserListByUserName(String name) throws Throwable;
	/**
	 * 根据用户登陆名和用户姓名模糊检索用户信息
	 * @param psm userId name 
	 * @return
	 * @throws Throwable
	 */
	public List<UserInfo> getUserList(PageSortModel psm, String userId, String userName) throws Throwable;
}
