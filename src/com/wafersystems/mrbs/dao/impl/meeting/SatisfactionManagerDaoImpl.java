package com.wafersystems.mrbs.dao.impl.meeting;

import java.sql.SQLException;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;


import com.wafersystems.mrbs.dao.base.GenericDaoImpl;
import com.wafersystems.mrbs.dao.meeting.SatisfactionManagerDao;
import com.wafersystems.mrbs.vo.meeting.SatisfactionManager;

@Repository
public class SatisfactionManagerDaoImpl extends GenericDaoImpl<SatisfactionManager> implements SatisfactionManagerDao {
	public Integer delSatisfactionManagerByMeetingId(final int meetingId){
		return getHibernateTemplate().execute(new HibernateCallback<Integer>() {
			@Override
			public Integer doInHibernate(Session session)throws HibernateException, SQLException {
				return session.createQuery("delete from SatisfactionManager where meeting.id = ?")
						.setInteger(0, meetingId).executeUpdate();
			}
		});
	}
}
