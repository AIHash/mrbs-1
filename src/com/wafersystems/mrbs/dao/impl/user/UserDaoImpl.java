package com.wafersystems.mrbs.dao.impl.user;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.wafersystems.mrbs.GlobalConstent;
import com.wafersystems.mrbs.dao.base.GenericDaoImpl;
import com.wafersystems.mrbs.dao.base.PageSortModel;
import com.wafersystems.mrbs.dao.user.UserDao;
import com.wafersystems.mrbs.vo.user.UserInfo;
import com.wafersystems.mrbs.web.criteriavo.AutoCompleteItem;
import com.wafersystems.mrbs.web.criteriavo.SelectItem;
import com.wafersystems.util.StrUtil;

@Repository
public class UserDaoImpl extends GenericDaoImpl<UserInfo> implements UserDao {

	public UserInfo getUserByHisCode(final String hisCode, final String name){
		return getHibernateTemplate().execute(new HibernateCallback<UserInfo>() {
			@Override
			public UserInfo doInHibernate(Session session) throws HibernateException, SQLException {
				return (UserInfo) session.createQuery("from UserInfo u where u.hisCode = ? and u.name = ?")
						.setString(0, hisCode)
						.setString(1, name).uniqueResult();
			}
		});
	}

	public int updateAllHisExpertStateDelete(){
		return getHibernateTemplate().execute(new HibernateCallback<Integer>() {
			@Override
			public Integer doInHibernate(Session session) throws HibernateException, SQLException {
				return session.createQuery("update UserInfo u set u.state = ? where u.userType.id = ? and u.source = ?")
					.setShort(0, GlobalConstent.USER_STATE_OFF)
					.setInteger(1, GlobalConstent.USER_TYPE_PROFESSIONAL)
					.setShort(2, GlobalConstent.USER_SOURCE_HIS).executeUpdate();
			}
		});
	}

	public void updateHisExpertInfo(final UserInfo user){
		if(user==null||StrUtil.isEmptyStr(user.getUserId())){
			return;
		}
		getHibernateTemplate().execute(new HibernateCallback<Void>() {
			@Override
			public Void doInHibernate(Session session) throws HibernateException, SQLException {
				session.createQuery("update UserInfo u set u.state = ? where u.userId =? ")
					.setShort(0, GlobalConstent.USER_STATE_ON)
					.setString(1, user.getUserId()).executeUpdate();
				return null;
			}
		});
	}
	
	/**
	 * 根据用户名、部门名模糊查询用户选单
	 * @param departmentKind 部门类型
	 * @param userNameOrDeptName 模糊查询的条件
	 * @param notInUserId 不包含的userId
	 * @return
	 * @throws Throwable
	 */
	public List<SelectItem> getUserListByUserNameOrDeptName(String departmentKind,String userNameOrDeptName,String notInUserId) throws Throwable{
		List<SelectItem> selectItems = new ArrayList<SelectItem>();
		try{
			String sql = "select userInfo.user_id as userId,userInfo.name as userName,dept.name as deptName,dept.id as deptId from unified_user userInfo,department dept "+
							" where userInfo.dept_id=dept.id and userInfo.state = '1' and userInfo.allowed_or_refused_flag = '1' and (userInfo.user_type = '4' or userInfo.user_type = '5' )";
			//部门类型
			if(!StrUtil.isEmptyStr(departmentKind)){
				sql+=" and (dept.parent_dept_code like '"+departmentKind+"%' or dept.deptcode='"+departmentKind+"')";
			}
			//模糊查询的条件
			if(!StrUtil.isEmptyStr(userNameOrDeptName)){
				sql+=" and (userInfo.name like '%"+userNameOrDeptName+"%' or dept.name like '%"+userNameOrDeptName+"%' or userInfo.py_code like '%"+userNameOrDeptName+"%' or dept.py_code like '%"+userNameOrDeptName+"%' )";
			}
			//不包含的userId
			if(!StrUtil.isEmptyStr(notInUserId)){
				sql+=" and userInfo.user_id not in ("+notInUserId+")";
			}
			sql+=" ORDER BY CONVERT(userInfo.name USING gbk) asc ,CONVERT(dept.name USING gbk) asc";
			logger.debug("UserDaoImpl.getUserListByUserNameOrDeptName sql="+sql);
			final String querySql = sql;
			@SuppressWarnings("unchecked")
			List<Object> result = template.executeFind(new HibernateCallback<List<Object>>() {

				@Override
				public List<Object> doInHibernate(Session session)
						throws HibernateException, SQLException {
					return session.createSQLQuery(querySql).list();
				}
			});
			if(result!=null&&!result.isEmpty()){
				SelectItem selectItem = null;
				for (Object object : result) {
					selectItem = new SelectItem();
					Object[] obj = (Object[]) object;
					if(obj[0]==null||obj[1]==null||StrUtil.isEmptyStr((String)obj[1])||StrUtil.isEmptyStr((String)obj[2])){
						continue;
					}
					selectItem.setId((String)obj[0]);
					selectItem.setName((String)obj[1]+"("+(String)obj[2]+")");
					selectItems.add(selectItem);
				}
			}
		}catch(Exception e){
			logger.error("Error UserDaoImpl.getUserListByUserNameOrDeptName",e);
			throw e;
		}catch(Throwable e){
			logger.error("Error UserDaoImpl.getUserListByUserNameOrDeptName",e);
			throw e;
		}
		return selectItems;
	}

	public List<AutoCompleteItem> getUserListByUserName(String name) throws Throwable{
		try{
			StringBuilder builder = new StringBuilder("select userInfo.name as userName, userInfo.user_id as userId from unified_user userInfo")
				.append(" where userInfo.state = ").append( GlobalConstent.USER_STATE_ON);
			//模糊查询的条件
			if(!StrUtil.isEmptyStr(name)){
				builder.append(" and (userInfo.name like '%").append(name).append("%' or userInfo.py_code like '%").append(name).append("%')");
			}
			builder.append(" ORDER BY CONVERT(userInfo.name USING gbk) asc");
			logger.debug("UserDaoImpl.getUserListByUserName sql = " + builder.toString());
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
							item = new AutoCompleteItem((String)object[0], (String)object[1]);
							selectItems.add(item);
						}
					}else{
						selectItems = Collections.emptyList();
					}
					return selectItems;
				}
			});
		}catch(Exception e){
			logger.error("Error UserDaoImpl.getUserListByUserName",e);
			throw e;
		}catch(Throwable e){
			logger.error("Error UserDaoImpl.getUserListByUserName",e);
			throw e;
	}
	}
	
	public List<UserInfo> getUserList(PageSortModel psm, String userId, String userName) throws Throwable{
		try{
			String wherejpql = "and state != ? and userId != ?";
			if(userId!=null&&userId!="")
				wherejpql+= "and userId like '%"+userId+"%'";
			if(userName!=null&&userName!="")
				wherejpql+= "and name like '%"+userName+"%'";
			Object[] queryParams = new Object[]{GlobalConstent.USER_STATE_DELETED, GlobalConstent.USER_ADMIN_ID};
			LinkedHashMap<String, Object> orderby = new LinkedHashMap<String, Object>();
			orderby.put("userId", "asc");
			return  this.getScrollData(psm, wherejpql, queryParams, orderby).getResultlist();
		}catch(Exception e){
			logger.error("Error UserDaoImpl.getUserListByUserName",e);
			throw e;
		}catch(Throwable e){
			logger.error("Error UserDaoImpl.getUserListByUserName",e);
			throw e;
	}
	}
}