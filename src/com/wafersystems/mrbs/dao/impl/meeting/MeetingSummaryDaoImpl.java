package com.wafersystems.mrbs.dao.impl.meeting;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.wafersystems.mrbs.dao.base.GenericDaoImpl;
import com.wafersystems.mrbs.dao.meeting.MeetingSummaryDao;
import com.wafersystems.mrbs.vo.meeting.MeetingSummary;

@Repository
public class MeetingSummaryDaoImpl extends GenericDaoImpl<MeetingSummary> implements MeetingSummaryDao {

	public Integer delSummaryForManagerSaveData(final int meetingId){
		return getHibernateTemplate().execute(new HibernateCallback<Integer>() {
			@Override
			public Integer doInHibernate(Session session) throws HibernateException, SQLException {
				List<MeetingSummary> delList = session.createQuery("from MeetingSummary ms where ms.meeting.id = ? ")
						.setInteger(0, meetingId).list();
				for (MeetingSummary meetingSummary : delList) {
					session.delete(meetingSummary);
				}
				return delList.size();
			}
		});
	}

	public Integer delSummaryForUserSaveData(final int meetingId, final String userId){
		return getHibernateTemplate().execute(new HibernateCallback<Integer>() {
			@Override
			public Integer doInHibernate(Session session) throws HibernateException, SQLException {
				List<MeetingSummary> delList = session.createQuery("from MeetingSummary ms where ms.meeting.id = ? and user.userId = ?")
						.setInteger(0, meetingId).setString(1, userId).list();
				for (MeetingSummary meetingSummary : delList) {
					session.delete(meetingSummary);
				}
				return delList.size();
			}
		});
	}
}
