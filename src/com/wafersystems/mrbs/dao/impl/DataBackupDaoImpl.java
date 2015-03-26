package com.wafersystems.mrbs.dao.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.wafersystems.mrbs.dao.DataBackupDao;

@Repository
public class DataBackupDaoImpl implements DataBackupDao{

	private HibernateTemplate template;

	@Override
	public int getDatabaseSize() {
	 	return template.execute(new HibernateCallback<Integer>() {
			@Override
			public Integer doInHibernate(Session session) throws HibernateException, SQLException {
				List<?> list = session.createSQLQuery("show table status").addScalar("Data_length", StandardBasicTypes.INTEGER).list();
				int size = 0;
				for (Object tableSize : list) {
					size += (Integer)tableSize / (1024 * 1024);
				}
				return size;
			}
		});
	}

	@Resource
	public void setTemplate(HibernateTemplate template) {
		this.template = template;
	}

}