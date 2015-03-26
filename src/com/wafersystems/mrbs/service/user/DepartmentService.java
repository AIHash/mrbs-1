package com.wafersystems.mrbs.service.user;

import java.util.List;

import com.wafersystems.mrbs.dao.base.PageSortModel;
import com.wafersystems.mrbs.exception.BaseException;
import com.wafersystems.mrbs.tag.tree.TreeNode;
import com.wafersystems.mrbs.vo.FinalUnifiedUser;
import com.wafersystems.mrbs.vo.user.Department;
import com.wafersystems.mrbs.web.criteriavo.AutoCompleteItem;

public interface DepartmentService {

	public boolean saveDepartment(Department department)throws BaseException;

	public void updateDepartment(Department department)throws BaseException;

	public boolean delDepartment(Department department)throws BaseException;

	public Department getDepartment(int id);

	public Department getDepartmentByNodeCode(String NodeCode);

	public List<TreeNode> getSubDepartmentLikeNodeCode(String nodeCode);

	public List<Department> getSubDepartmentOfNodeCode(PageSortModel psm, String nodeCode);

	public List<Department> getDepartmentByName(String parentNodeCode, String name);

	public List<Department> getDepartmentLikeParentNodeCode(String nodeCode);

	public List<Department> getDepartmentNoHospital();

	public List<FinalUnifiedUser> getFinalUnifiedUserLikeParentNodeCode(String nodeCode);

	public int getMaxDeptCode(String nodeCode);
	
	public int isParentDept(String nodeCode) throws Throwable;

	public List<Department> getDepartByDeptName(String deptName);

	/**
	 * 根据医院hisCode和科室名称查找科室
	 * @param hisCode
	 * @param name
	 * @return
	 */
	public Department getDeptByHisCodeAndName(String hisCode, String name);
		
	/**
	 * 同步医院的部门
	 * 
	 * 默认将院内下的部门状态改为不可用,deptCode为null，对比hisCode与name，两项均相同时状态改为可用,并更新deptCode；
	 * 不相同时插入新数据
	 * @param depts
	 */
	public void saveSyncHisDept(List<Department> depts);

	/**
	 * 返回名称like %name%的部门
	 * @param name
	 * @return
	 */
	public List<Department> getDeptsByNameLikeString(String name);
	
	/**
	 * 根据部门的名称的汉语或字母检索返回Autocomplete项
	 * @param name
	 * @return
	 * @throws Throwable
	 */
	public List<AutoCompleteItem> getDeptListByDeptName(String name)throws Throwable;
}
