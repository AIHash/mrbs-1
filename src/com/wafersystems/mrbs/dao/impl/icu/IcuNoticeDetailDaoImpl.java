package com.wafersystems.mrbs.dao.impl.icu;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.wafersystems.mrbs.dao.base.GenericDaoImpl;
import com.wafersystems.mrbs.dao.icu.IcuNoticeDetailDao;
import com.wafersystems.mrbs.vo.meeting.IcuNoticeDetail;
import com.wafersystems.mrbs.vo.meeting.NoticeDetail;
@Repository
public class IcuNoticeDetailDaoImpl extends GenericDaoImpl<IcuNoticeDetail> implements IcuNoticeDetailDao{

	/**
	 * add by wangzhenglin 
	 * 根据ICUID查询通知明细
	 * @param meetingApplicationId
	 * @return
	 */
	public List<IcuNoticeDetail> getNoticeDetailByIcuMonitorId(final Integer icuId){
		try{
			logger.debug("Enter IcuNoticeDetailDaoImpl.getNoticeDetailByIcuMonitorId");
			return getHibernateTemplate().execute(new HibernateCallback<List<IcuNoticeDetail>>() {
				@Override
				public List<IcuNoticeDetail> doInHibernate(Session session)
						throws HibernateException, SQLException {
					String hql = "from IcuNoticeDetail nd where nd.iCUMonitorId.id = ?";
					return session.createQuery(hql).setInteger(0, icuId).list();
				}
			});
		}catch(Exception e){
			logger.error("Enter ICUNoticeDetailDaoImpl.getNoticeDetailByIcuMonitorId", e);
			return null;
		}
	}
	
	/**
	 * 根据病历讨论id删除通知明细
	 * @param meetingApplicationId
	 * @return size
	 */
	public Integer deleteIcuNoticeDetailByIcuId(Integer icuId){
		try{
			logger.debug("Enter NoticeDetailDaoImpl.deleteNoticeDetailByAppId");
			final String hql = "delete from icu_notice_detail where icumonitor_id =  " + icuId ;
			return getHibernateTemplate().execute(new HibernateCallback<Integer>() {
				@Override
				public Integer doInHibernate(Session session) throws HibernateException, SQLException {
					Integer size = session.createSQLQuery(hql).executeUpdate();
					return size;
				}
			});
		}catch(Exception e){
			logger.error("Enter IcuNoticeDetailDaoImpl.deleteIcuNoticeDetailByIcuId", e);
			return null;
		}
	}
	
}
