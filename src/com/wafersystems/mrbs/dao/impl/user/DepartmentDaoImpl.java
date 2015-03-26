package com.wafersystems.mrbs.dao.impl.user;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.wafersystems.mrbs.GlobalConstent;
import com.wafersystems.mrbs.dao.base.GenericDaoImpl;
import com.wafersystems.mrbs.dao.user.DepartmentDao;
import com.wafersystems.mrbs.vo.user.Department;
import com.wafersystems.mrbs.vo.user.UserInfo;
import com.wafersystems.mrbs.web.criteriavo.AutoCompleteItem;
import com.wafersystems.util.StrUtil;

@Repository
public class DepartmentDaoImpl extends GenericDaoImpl<Department> implements DepartmentDao {

	@Override
	public Set<UserInfo> getUserInfoByDepart(final Integer id) {
		return getHibernateTemplate().execute(new HibernateCallback<Set<UserInfo>>(){
			@Override
			public Set<UserInfo> doInHibernate(Session session) throws HibernateException, SQLException {
				String hql = "from UserInfo u where u.deptId.id = ? and u.state = ?";
				List<UserInfo> list = session.createQuery(hql).setInteger(0, id).setShort(1, GlobalConstent.USER_STATE_ON).list();
				return new HashSet<UserInfo>(list);
			}
		});
	}
	
	/**
	 * 根据部门Id取得允许参加会议的用户信息
	 * @param id 部门Id
	 * @return Set<UserInfo>
	 */
	public Set<UserInfo> getUserInfoOfExpertsByDepartId(final Integer id) {
		return getHibernateTemplate().execute(new HibernateCallback<Set<UserInfo>>(){
			@Override
			public Set<UserInfo> doInHibernate(Session session) throws HibernateException, SQLException {
				String hql = "from UserInfo u where u.deptId.id = ? and u.state = ? and u.allowedOrRefusedFlag = ?";
				List<UserInfo> list = session.createQuery(hql).setInteger(0, id).setShort(1, GlobalConstent.USER_STATE_ON).setShort(2, GlobalConstent.USER_STATE_ON).list();
				return new HashSet<UserInfo>(list);
			}
		});
	}

	/**
	 * 根据科室ID得到一个科室
	 * */
	@Override
	public Department getUserInfoByDepartId(final Integer deptId) {
		return (Department)getHibernateTemplate().execute(new HibernateCallback<Object>(){
			@Override
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				 Query query = session.createQuery("select o from Department o where o.id=" + deptId);      
                 query.setFirstResult(0).setMaxResults(1);   
                 Department dp=(Department) query.list().get(0);
                 getHibernateTemplate().initialize(dp.getUsers());
                 return dp;
			}
		});
	}
    
	/**
	 * 根据科室的父节点得到该科室的所有专家
	 * */
	@Override
	public Set<UserInfo> getUserInfoByDepartParentCode(final Integer parentDeptCode) {
		return getHibernateTemplate().execute(new HibernateCallback<Set<UserInfo>>(){
			@Override
			public Set<UserInfo> doInHibernate(Session session)
					throws HibernateException, SQLException {
				 Query query = session.createQuery("select o from Department o where o.parentDeptCode="+parentDeptCode);      
                 query.setFirstResult(0).setMaxResults(1);   
                 Department dp=(Department) query.list().get(0);
                 getHibernateTemplate().initialize(dp.getUsers());
                 return dp.getUsers();
			}
		});
	}
	
	@Override
	public Set<Department> getUserInfoByParentDeptCode(String parentDeptCode) {
		StringBuilder builder = new StringBuilder();
		builder.append("from Department o where o.parentDeptCode= "+parentDeptCode);
		return new HashSet<Department>(this.findTbyHql(builder.toString()));
	}
	
	@Override
	public int isParent(String DeptCode) throws Throwable{
		try{
			String sql = "select COUNT(1) from department d where d.parent_dept_code = '" + DeptCode + "'";
			final String querySql = sql;
			@SuppressWarnings("unchecked")
			List<Object> result = template.executeFind(new HibernateCallback<List<Object>>() {
				@Override
				public List<Object> doInHibernate(Session session)
						throws HibernateException, SQLException {
					return session.createSQLQuery(querySql).list();
				}
			});			
			return Integer.valueOf(result.get(0).toString());
		}catch(Exception e){			
			logger.error("Error DepartmentDaoImpl.isParent",e);
			throw e;
		}catch(Throwable e){
			logger.error("Error DepartmentDaoImpl.isParent",e);
			throw e;
		}	
				
	}

	@Override
	public Department getDeptByDeptId(Integer deptId) {
		return getHibernateTemplate().load(Department.class, deptId);
	}

	public List<Department> getDepartmentNoHospital(){
		String hql = "From Department where deptcode<>'001001' and deptcode like '001001%' order by deptcode";
		return getHibernateTemplate().find(hql);
	}

	@Override
	public Department getDeptByHisCodeAndName(final String hisCode, final String name){
		StringBuilder builder = new StringBuilder(" from Department d where 1=1 ");
		if(StringUtils.isNotBlank(hisCode))
			builder.append(" and d.hisCode = ").append(hisCode);
		if(StringUtils.isNotBlank(name))
			builder.append(" and d.name = ").append(name);
		final String hql = builder.toString();
		return getHibernateTemplate().execute(new HibernateCallback<Department>() {
			@Override
			public Department doInHibernate(Session session)
					throws HibernateException, SQLException {
				return (Department)session.createQuery(hql)
						.uniqueResult();
			}
		});
	}

	public int setRMYYDepartmentStateOff(){
		return getHibernateTemplate().execute(new HibernateCallback<Integer>() {
			@Override
			public Integer doInHibernate(Session session)
					throws HibernateException, SQLException {
				return session.createQuery("from Department set state = ?,deptcode = ? where parentDeptCode = ?")
						.setInteger(0, GlobalConstent.DEPT_STATE_OFF)
						.setString(1, null)
						.setString(2, GlobalConstent.HIS_DEPT_CODE)
						.executeUpdate();
			}
		});
	}

	public int updateRMYYDept(final Department dept){
		return getHibernateTemplate().execute(new HibernateCallback<Integer>() {
			@Override
			public Integer doInHibernate(Session session)
					throws HibernateException, SQLException {
				return session.createQuery("update Department set state = ?, deptcode =? where hisCode =? and name = ?")
						.setInteger(0, GlobalConstent.DEPT_STATE_ON)
						.setString(1, dept.getDeptcode())
						.setString(2, dept.getHisCode())
						.setString(3, dept.getName())
						.executeUpdate();
			}
		});
	}

	public List<Department> getDeptsByNameLikeString(final String name){
		return getHibernateTemplate().execute(new HibernateCallback<List<Department>>() {
			@Override
			public List<Department> doInHibernate(Session session)throws HibernateException, SQLException {
				return session.createQuery("from Department where parentDeptCode like '001001%' and ( name like ? or pyCode like ? )")
							.setString(0, "%"+name+"%").setString(1, "%"+name+"%").list();
			}
		});
	}
	
	public List<AutoCompleteItem> getDeptListByDeptName(String name)throws Throwable{		
		try{
			StringBuilder builder = new StringBuilder("select Department.name as deptName, Department.id as deptId from department Department");
			//模糊查询的条件
			if(!StrUtil.isEmptyStr(name)){
				builder.append(" where (Department.name like '%").append(name).append("%' or Department.py_code like '%").append(name).append("%')");
			}
			builder.append(" ORDER BY CONVERT(Department.name USING gbk) asc");
			logger.debug("DepartmentDaoImpl.getDeptListByUserName sql = " + builder.toString());
			final String querySql = builder.toString();
			return template.execute(new HibernateCallback<List<AutoCompleteItem>>() {
				@Override
				public List<AutoCompleteItem> doInHibernate(Session session)throws HibernateException, SQLException {
					List<?> result = session.createSQLQuery(querySql).list();
					List<AutoCompleteItem> selectItems;
					if(result != null && result.size() > 0){
						selectItems = new ArrayList<AutoCompleteItem>(result.size());
						AutoCompleteItem item;
						for (Object obj : result) {
							Object[] object =(Object[])obj;
							item = new AutoCompleteItem((String)object[0], String.valueOf(object[1]));
							selectItems.add(item);
						}
					}else{
						selectItems = Collections.emptyList();
					}
					return selectItems;
				}
			});
		}catch(Exception e){
			logger.error("Error DepartmentDaoImpl.getDeptListByUserName",e);
			throw e;
		}catch(Throwable e){
			logger.error("Error DepartmentDaoImpl.getDeptListByUserName",e);
			throw e;
	}
		
	}
}
