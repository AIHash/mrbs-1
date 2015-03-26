package com.wafersystems.mrbs.dao.user;

import java.util.List;
import java.util.Set;

import com.wafersystems.mrbs.dao.base.GenericDao;
import com.wafersystems.mrbs.vo.user.Department;
import com.wafersystems.mrbs.vo.user.UserInfo;
import com.wafersystems.mrbs.web.criteriavo.AutoCompleteItem;

public interface DepartmentDao extends GenericDao<Department>{
	
	/**
	 * 根据部门Id取得允许参加会议的用户信息
	 * @param id 部门Id
	 * @return
	 */
	public Set<UserInfo> getUserInfoOfExpertsByDepartId(final Integer id);
	
	public Set<UserInfo> getUserInfoByDepart(Integer id) ;
	
	public Set<UserInfo> getUserInfoByDepartParentCode(Integer parentDeptCode) ;
	
	public Department getUserInfoByDepartId(Integer deptId) ;//根据科室ID得到一个科室
	
	public Department getDeptByDeptId(Integer deptId);
	
	public Set<Department> getUserInfoByParentDeptCode(String parentDeptCode) ;//根据父科室的deptCode得到该父科室中所有的子科室

	/**
	 * 根据医院code和name查看当前部门是否存在，不存在返回null
	 * @param hisCode
	 * @return
	 */
	public Department getDeptByHisCodeAndName(String hisCode, String name);

	/**
	 * 查询院内下的部门
	 * @return
	 */
	public List<Department> getDepartmentNoHospital();

	/**
	 * 
	 * @return 设置为不可用的部门数
	 */
	public int setRMYYDepartmentStateOff();

	/**
	 * 更新部门的状态为启用，deptCode(根据部门中文名汉语拼音顺序)
	 * @param dept
	 * @return
	 */
	public int updateRMYYDept(Department dept);

	/**
	 * 返回名称like %name%的部门
	 * @param name
	 * @return
	 */
	public List<Department> getDeptsByNameLikeString(String name);
	
	/**
	 * 判断部门是否含有子部门
	 * @param DeptCode
	 * @return
	 */
	public int isParent(String DeptCode) throws Throwable;
	
	/**
	 * 根据部门的名称的汉语或字母检索返回Autocomplete项
	 * @param name
	 * @return
	 * @throws Throwable
	 */
	public List<AutoCompleteItem> getDeptListByDeptName(String name)throws Throwable;
}
