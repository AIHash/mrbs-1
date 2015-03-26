package com.wafersystems.mrbs.dao.impl.meeting;

import java.sql.SQLException;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.wafersystems.mrbs.dao.base.GenericDaoImpl;
import com.wafersystems.mrbs.dao.meeting.SatisfactionDao;
import com.wafersystems.mrbs.vo.meeting.Satisfaction;

@Repository
public class SatisfactionDaoImpl extends GenericDaoImpl<Satisfaction> implements SatisfactionDao {

	@Override
	public Integer delSatisfactionByMeetingId(final int meetingId) {
		return getHibernateTemplate().execute(new HibernateCallback<Integer>() {
			@Override
			public Integer doInHibernate(Session session) throws HibernateException, SQLException {
				return session.createQuery("delete from Satisfaction where meeting.id = ?")
					.setInteger(0, meetingId).executeUpdate();
			}
		});
	}

}
