package com.wafersystems.mrbs.dao.impl.meeting;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wafersystems.mrbs.dao.base.GenericDaoImpl;
import com.wafersystems.mrbs.dao.meeting.NoticeDetailDao;
import com.wafersystems.mrbs.vo.meeting.NoticeDetail;

@Repository
public class NoticeDetailDaoImpl extends GenericDaoImpl<NoticeDetail> implements NoticeDetailDao{
	private static Logger logger = LoggerFactory.getLogger(NoticeDetailDaoImpl.class);
	/**
	 * 根据病历讨论id查询通知明细
	 * @param meetingApplicationId
	 * @return
	 */
	public List<NoticeDetail> getNoticeDetailByMeetingApplicationId(final Integer meetingApplicationId){
		try{
			logger.debug("Enter NoticeDetailDaoImpl.getNoticeDetailByMeetingApplicationId");
			return getHibernateTemplate().execute(new HibernateCallback<List<NoticeDetail>>() {
				@Override
				public List<NoticeDetail> doInHibernate(Session session)
						throws HibernateException, SQLException {
					String hql = "from NoticeDetail nd where nd.meetingApplicationId.id = ?";
					return session.createQuery(hql).setInteger(0, meetingApplicationId).list();
				}
			});
		}catch(Exception e){
			logger.error("Enter NoticeDetailDaoImpl.getNoticeDetailByMeetingApplicationId", e);
			return null;
		}
	}
	
	/**
	 * 根据病历讨论id删除通知明细
	 * @param meetingApplicationId
	 * @return size
	 */
	public Integer deleteNoticeDetailByAppId(Integer meetingApplicationId){
		try{
			logger.debug("Enter NoticeDetailDaoImpl.deleteNoticeDetailByAppId");
			final String hql = "delete from notice_detail where meeting_application_id =  " + meetingApplicationId ;
			return getHibernateTemplate().execute(new HibernateCallback<Integer>() {
				@Override
				public Integer doInHibernate(Session session) throws HibernateException, SQLException {
					Integer size = session.createSQLQuery(hql).executeUpdate();
					return size;
				}
			});
		}catch(Exception e){
			logger.error("Enter NoticeDetailDaoImpl.deleteNoticeDetailByAppId", e);
			return null;
		}
	}
	
}
