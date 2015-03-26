package com.wafersystems.mrbs.dao.impl.meeting;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.wafersystems.mrbs.dao.base.GenericDaoImpl;
import com.wafersystems.mrbs.dao.meeting.AccessoriesTypeDao;
import com.wafersystems.mrbs.vo.meeting.AccessoriesType;

@Repository
public class AccessoriesTypeDaoImpl extends GenericDaoImpl<AccessoriesType> implements AccessoriesTypeDao {

	@Override
	/**
	 * 得到附件类型表中vaule最大的值
	 * */
	public Short getMaxAccessoriesType() {
		return this.getHibernateTemplate().execute(new HibernateCallback<Short>() {
			@Override
			public Short doInHibernate(Session session) throws HibernateException, SQLException {
				return (Short) session.createQuery("select max(a.value) from  AccessoriesType a")
						.uniqueResult();
			}
		});
	}

	@Override
	public AccessoriesType getAccessoriesTypeByName(final String name) {
		return this.getHibernateTemplate().execute(new HibernateCallback<AccessoriesType>() {
			@Override
			public AccessoriesType doInHibernate(Session session) throws HibernateException, SQLException {
				return (AccessoriesType) session.createQuery("from AccessoriesType at where at.name = ?")
						.setString(0, name).uniqueResult();
			}
		});
	}

	@Override
	/**
	 * 得到所有附件类型
	 * */
	public List<AccessoriesType> getAllAccessoriesType() {
		return this.getHibernateTemplate().execute(new HibernateCallback <List<AccessoriesType>>() {
			@Override
			public List<AccessoriesType> doInHibernate(Session session) throws HibernateException,SQLException {
				return (List<AccessoriesType>) session.createQuery("select a from AccessoriesType a").list();
			}
		});
	}

}
