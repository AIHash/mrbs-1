package com.wafersystems.mrbs.service.impl.user;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.wafersystems.mrbs.dao.base.PageSortModel;
import com.wafersystems.mrbs.dao.user.DepartmentDao;
import com.wafersystems.mrbs.exception.BaseException;
import com.wafersystems.mrbs.exception.CreateFailedException;
import com.wafersystems.mrbs.exception.InfoExistException;
import com.wafersystems.mrbs.exception.UpdateFailedException;
import com.wafersystems.mrbs.logmng.ann.MrbsLog;
import com.wafersystems.mrbs.service.user.DepartmentService;
import com.wafersystems.mrbs.tag.tree.TreeNode;
import com.wafersystems.mrbs.vo.FinalUnifiedUser;
import com.wafersystems.mrbs.vo.user.Department;
import com.wafersystems.mrbs.web.criteriavo.AutoCompleteItem;
import com.wafersystems.util.StrUtil;

@Service
public class DepartmentServiceImpl implements DepartmentService {

	private DepartmentDao departmentDao;
	
	private static Logger logger = LoggerFactory.getLogger(DepartmentService.class);

	@Override
	@MrbsLog(desc="admin.department_create")
	public boolean saveDepartment(Department department)throws BaseException {
		try{
			List<Department> exists = getDepartmentByName(department.getParentDeptCode(), department.getName());
			if(exists != null && exists.size() > 0){
				 return  false;
			}else{
				Department parentDept = getDepartmentByNodeCode(department.getParentDeptCode());
				department.setParentDetp(parentDept);
				department.setDeptcode(getMaxTreeNodeCode(department.getParentDeptCode(), 3));
				departmentDao.save(department);
				return true;
			}
			
		}catch(Exception e){
			logger.error("创建部门异常", e);
			return false;
		}
		 
	}

	@Override
	@MrbsLog(desc="admin.department_update")
	public void updateDepartment(Department department)throws BaseException {
		try{
			departmentDao.update(department);
		}catch(Exception e){
			logger.error("更新部门异常", e);
			throw new UpdateFailedException();
		}
	}

	@Override
	@MrbsLog(desc="admin.department_delDepartment")
	public boolean delDepartment(Department department){
		try{
			//如果为院内、院际、共同体组织(父部门ID为001)，则不能被删除
			if(department.getParentDeptCode().equals("001"))
				return false;
			departmentDao.deleteVO(department.getId());
			departmentDao.fulsh();
			return true;
		}catch(Throwable e){
			logger.error("删除部门异常", e);
			return false;
		}
	}
	
	@Override
	public Department getDepartment(int id) {
		return departmentDao.get(id);

	}
	
	@Override
	public Department getDepartmentByNodeCode(String nodeCode){
		String hql = "From Department Where deptcode = '" + nodeCode + "'";
		List<Department> results = departmentDao.findTbyHql(hql);
		if(results != null && results.size() > 0){
			return results.get(0);
		}else{
			return null;
		}
	}
	
	@Override
	public List<TreeNode> getSubDepartmentLikeNodeCode(String nodeCode){
		String hql = "From Department Where parentDeptCode like '" + nodeCode + "%' Order by deptcode";
		List<Department> results = departmentDao.findTbyHql(hql);
		Department dept = null;
		List<TreeNode> datas = new ArrayList<TreeNode>();
    	for (Iterator<Department> itr = results.iterator(); itr.hasNext();)
		{
    		dept = itr.next();
			TreeNode node = new TreeNode();
            node.setId(dept.getId() + "");
            node.setNodeName(dept.getName());
            node.setNodeNo(dept.getDeptcode());
            node.setParentNodeNo(dept.getParentDeptCode());
            datas.add(node);
		}
		return datas;
	}

	public List<Department> getDepartmentLikeParentNodeCode(String nodeCode){
		String hql = " from Department Where parentDeptCode like '" + nodeCode + "%' Order by deptcode";
		
		return departmentDao.findTbyHql(hql);
	}

	@Override
	public List<FinalUnifiedUser> getFinalUnifiedUserLikeParentNodeCode(String nodeCode) {
		String sql = "select d.name as dname ,d.deptcode,u.user_id,u.name as uname,u.user_type,u.username ,u.telephone,u.mobile,u.mail from department d left join unified_user u on   d.id = u.dept_id where d.parent_dept_code like '" + nodeCode + "%' or d.deptcode= '" + nodeCode + "' Order by deptcode";	
		return departmentDao.findBySql(sql);
	}
	
	@Override
	public int getMaxDeptCode(String nodeCode) {
		String sql = "select MAX(LENGTH(d.deptcode)) from department d where d.parent_dept_code like '" + nodeCode +"%'";
		return departmentDao.executeSql(sql);
	}
	
	@Override
	public int isParentDept(String nodeCode) throws Throwable {
		return departmentDao.isParent(nodeCode);
	}
	
	@Override
	public List<Department> getSubDepartmentOfNodeCode(PageSortModel psm, String nodeCode){
		String wherejpql = " and parentDeptCode = ?";
		Object[] queryParams = new Object[]{nodeCode};
		LinkedHashMap<String, Object> orderby = new LinkedHashMap<String, Object>();
		orderby.put("deptcode", "asc");
		return  departmentDao.getScrollData(psm, wherejpql, queryParams, orderby).getResultlist();
	}
	
	@Override
	public List<Department> getDepartmentByName(String parentNodeCode, String name){
		String wherejpql = " and parentDeptCode = ? and name = ?";
		Object[] queryParams = new Object[]{parentNodeCode, name};
		return departmentDao.getScrollData(-1, -1, wherejpql, queryParams).getResultlist();
	}
	
	private String getMaxTreeNodeCode(String nodeCode, int levelWidth)
	{
		String createNodeCode = "";
		String Hql = "From Department Where parentDeptCode = '" + nodeCode + "' Order by deptcode desc ";
		List<Department> results = departmentDao.findTbyHql(Hql);
		if(results.size() > 0){
			createNodeCode = results.get(0).getDeptcode();
			if ("".equals(StrUtil.formatStr(createNodeCode)))
        	{
				createNodeCode = nodeCode;
            	for (int i = 0; i < levelWidth - 1; i++)
            		createNodeCode+= "0";

            	createNodeCode+= "1";
        	}
        	else
        	{
            	int max = Integer.parseInt(createNodeCode.substring(createNodeCode.length() - levelWidth)) + 1;
            	createNodeCode = createNodeCode.substring(0, createNodeCode.length() - levelWidth);
            	for (int i = String.valueOf(max).length(); i < levelWidth; i++)
            		createNodeCode+= "0";

            	createNodeCode+= max;
        	}
		}else
		{
			createNodeCode = nodeCode;
        	for (int i = 0; i < levelWidth - 1; i++)
        		createNodeCode+= "0";

        	createNodeCode+= "1";
		}
		
		logger.debug("create department nodeCode:" + createNodeCode);
        return createNodeCode;
	}

	@Override
	public List<Department> getDepartmentNoHospital(){
		return departmentDao.getDepartmentNoHospital();
	}

	@Override
	public List<Department> getDepartByDeptName(String deptName) {
		String hql = "from Department d Where 1=1 ";
		if(!StrUtil.isEmptyStr(deptName)){
			hql+=" and d.name = '" + deptName +"'";;
		}
		return departmentDao.findTbyHql(hql);
	}

	@Override
	public Department getDeptByHisCodeAndName(String hisCode, String name){
		return departmentDao.getDeptByHisCodeAndName(hisCode, name);
	}

	@Override
	public void saveSyncHisDept(List<Department> depts){
		logger.debug("DepartmentServiceImpl.saveSyncHisDept start 开始同步部门数据");
		int offCount = departmentDao.setRMYYDepartmentStateOff();
		logger.debug("共" + offCount + "个部门状态设置为不可用");
		Department dbDept = null;//数据库中的Dept
		Department department = null;
		for (Iterator<Department> iterator = depts.iterator(); iterator.hasNext();) {
			department = iterator.next();
			dbDept = departmentDao.getDeptByHisCodeAndName(department.getHisCode(), department.getName());
			if(null == dbDept){//不存在的时候执行插入
				try {
					this.saveDepartment(department);
					logger.debug("同步部门时，新增一个之前不存在的部门，部门名称为:" + department.getName());
				} catch (BaseException e) {
					e.printStackTrace();
				}
			}else{
				departmentDao.updateRMYYDept(department);
				logger.debug("同步部门时，更新部门，部门名称为:" + department.getName());
			}
		}
		logger.debug("DepartmentServiceImpl.saveSyncHisDept over 结束同步部门数据");
	}

	public List<Department> getDeptsByNameLikeString(String name){
		return departmentDao.getDeptsByNameLikeString(name);
	}
	
	public List<AutoCompleteItem> getDeptListByDeptName(String name)throws Throwable{
		try{
			return departmentDao.getDeptListByDeptName(name);
		}catch(Exception e){
			logger.error("Error DepartmentServiceImpl.getDeptListByDeptName",e);
			throw e;
		}catch(Throwable e){
			logger.error("Error DepartmentServiceImpl.getDeptListByDeptName",e);
			throw e;
		}
		
	}
	
	@Resource(type = DepartmentDao.class)
	public void setDepartmentDao(DepartmentDao departmentDao) {
		this.departmentDao = departmentDao;
	}
}