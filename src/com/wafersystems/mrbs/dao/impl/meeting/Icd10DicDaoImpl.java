package com.wafersystems.mrbs.dao.impl.meeting;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.wafersystems.mrbs.dao.base.GenericDaoImpl;
import com.wafersystems.mrbs.dao.meeting.Icd10DicDao;
import com.wafersystems.mrbs.vo.meeting.ICD10DIC;
import com.wafersystems.util.StrUtil;

@Repository
public class Icd10DicDaoImpl  extends GenericDaoImpl<ICD10DIC> implements Icd10DicDao{
	@Override
	public List<String> getIcd10DicDepartmentList() {
		String  hql= "select department from ICD10DIC  group by department order by department";
		List<String> list = template.find(hql);
		return list;
	}
	public List<ICD10DIC> getIcd10DicList(String department){
		String  hql= "from ICD10DIC e where e.department= '"+department + "'";
		List<ICD10DIC> list = findTbyHql(hql);
		return list;
	}

	public Integer delIcdByAppId(Integer appId){
		final String hql = "delete from application_icd10 where meeting_application =  " + appId ;
		return getHibernateTemplate().execute(new HibernateCallback<Integer>() {
			@Override
			public Integer doInHibernate(Session session) throws HibernateException, SQLException {
				Integer size = session.createSQLQuery(hql).executeUpdate();
				return size;
			}
		});
	}

	public Map<Integer, String> getAllIcdDepartment(){
		Map<Integer, String> map = new HashMap<Integer, String>();
		String  hql= "select department from ICD10DIC  group by department order by department";
		List<String> list = template.find(hql);
		Integer i = 1;
		for (String departName : list) {
			map.put(i, departName);
		}
		return map;
	}

	public List<ICD10DIC> getIcd10DicListLikeString(final String s){
		if(StrUtil.isEmptyStr(s)){
			final String hql = "from ICD10DIC icd";
			return template.execute(new HibernateCallback<List<ICD10DIC>>() {
				@Override
				public List<ICD10DIC> doInHibernate(Session session)
						throws HibernateException, SQLException {
					return session.createQuery(hql).list();
				}
			});
		}else{
			final String hql = "from ICD10DIC icd where icd.diagnosis like ? or icd.pyCode like ? ";
			return template.execute(new HibernateCallback<List<ICD10DIC>>() {

				@Override
				public List<ICD10DIC> doInHibernate(Session session)
						throws HibernateException, SQLException {
					return session.createQuery(hql).setString(0, "%" + s + "%").setString(1, "%" + s + "%").list();
				}
			});
		}
			
	}
	
	/**
	 * 取得不包含指定id的Icd10
	 * @param icd10Ids 指定id
	 * @return
	 */
	public List<ICD10DIC> getIcd10DicListNotContainId(final Integer[] icd10Ids){
		if(icd10Ids!=null&&icd10Ids.length>0){
			final String hql = "from ICD10DIC icd where icd.id not in (:icd10Ids)";
			return template.execute(new HibernateCallback<List<ICD10DIC>>() {
				@Override
				public List<ICD10DIC> doInHibernate(Session session)
						throws HibernateException, SQLException {
					return session.createQuery(hql).setParameterList("icd10Ids", icd10Ids).list();
				}
			});
		}else{
			final String hql = "from ICD10DIC icd";
			return template.execute(new HibernateCallback<List<ICD10DIC>>() {
				@Override
				public List<ICD10DIC> doInHibernate(Session session)
						throws HibernateException, SQLException {
					return session.createQuery(hql).list();
				}
			});
		}
			
	}
	/**
	 * 删除重症监护对应的诊断病情信息
	 * @param monitorId
	 * @return
	 */
	public Integer delIcdByMonitorId(Integer monitorId){
		final String hql = "delete from icu_icd10 where icumonitor =  " + monitorId ;
		return getHibernateTemplate().execute(new HibernateCallback<Integer>() {
			@Override
			public Integer doInHibernate(Session session) throws HibernateException, SQLException {
				Integer size = session.createSQLQuery(hql).executeUpdate();
				return size;
			}
		});
	}
}
