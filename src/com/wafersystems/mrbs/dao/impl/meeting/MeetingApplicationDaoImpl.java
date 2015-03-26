package com.wafersystems.mrbs.dao.impl.meeting;

import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.wafersystems.mrbs.dao.base.GenericDaoImpl;
import com.wafersystems.mrbs.dao.base.PageSortModel;
import com.wafersystems.mrbs.dao.meeting.MeetingApplicationDao;
import com.wafersystems.mrbs.vo.meeting.MeetingApplication;
import com.wafersystems.mrbs.web.criteriavo.MeetingApplicationCriteriaVO;
import com.wafersystems.util.StrUtil;

@Repository
public class MeetingApplicationDaoImpl extends GenericDaoImpl<MeetingApplication> implements MeetingApplicationDao {

	@Override
	public List<MeetingApplication> getMeetingApplicationList(Short state) {
		String hql=" and o.state=?";
		Short  queryParams[]=new Short[]{state};
		LinkedHashMap<String, Object> orderby=new LinkedHashMap<String, Object>();
		orderby.put("id", "desc");
		return this.limitFindByHql(0, 7, hql, queryParams, orderby);
	}

	@Override
	public List<MeetingApplication> searchMeetingApplicationList(PageSortModel psm,
			Map<String, Object> map) {
		String wherejpql = null;
		Object[] queryParams=null;
		LinkedHashMap<String, Object> orderby = new LinkedHashMap<String, Object>();
		orderby.put("state", "asc");
		orderby.put("expectedTime", "desc");
		if(map != null && !map.isEmpty()){
			Set<String>set=map.keySet();
			wherejpql = this.createWhereSqlBySet(map);
			queryParams = new Object[set.size()];
			int i=0;
			for(String temp:set){
				queryParams[i]=map.get(temp);
			    i++;
			}
		}
		return this.getScrollData(psm, wherejpql, queryParams, orderby).getResultlist();
	}

	/**
	 * 查询远程会诊信息
	 * @param psm 分页
	 * @param application 查询VO
	 * @param expectedTimeStart 建议会诊日期起
	 * @param expectedTimeEnd 建议会诊日期止
	 * @param requester
	 * @return
	 * @throws Throwable 
	 */
	public List<MeetingApplication> searchMeetingApplicationList(PageSortModel psm, MeetingApplicationCriteriaVO meetingApplicationCriteriaVO) throws Throwable {
		String wherejpql = "";
//		List<Object> tempParams = new ArrayList<Object>(4);
		//会诊目的
//		if(!StrUtil.isEmptyStr(application.getPurpose())){
//			wherejpql += " and purpose like ? ";
//			tempParams.add("%" + application.getPurpose() + "%");
//		}
		try{
			if(meetingApplicationCriteriaVO!=null){
				//建议会诊日期
				String expectedTime = meetingApplicationCriteriaVO.getExpectedTime();
				if(!StrUtil.isEmptyStr(meetingApplicationCriteriaVO.getExpectedTime())){
					wherejpql += " and expectedTime >= '"+expectedTime+" 00:00:00' and expectedTime <= '"+expectedTime+" 23:59:59' ";
				}
				//建议会诊日期起
				String expectedTimeStart = meetingApplicationCriteriaVO.getExpectedTimeStart();
				if(!StrUtil.isEmptyStr(expectedTimeStart)){
					wherejpql += " and expectedTime >= '"+expectedTimeStart+" 00:00:00'";
				}
				//建议会诊日期止
				String expectedTimeEnd = meetingApplicationCriteriaVO.getExpectedTimeEnd();
				if(!StrUtil.isEmptyStr(expectedTimeEnd)){
					wherejpql += " and expectedTime <= '"+expectedTimeEnd+" 23:59:59'";
				}
				//申请时间
				String applicationTime = meetingApplicationCriteriaVO.getApplicationTime();
				if(!StrUtil.isEmptyStr(applicationTime)){
					wherejpql += " and applicationTime >= '"+applicationTime+" 00:00:00' and applicationTime <= '"+applicationTime+" 23:59:59' ";
				}
				//会诊类型
				String meetingType = meetingApplicationCriteriaVO.getMeetingType();
				if(!StrUtil.isEmptyStr(meetingType)&&!meetingType.equals("-1")){
					wherejpql += " and meetingType.id = "+meetingType;
				}
				//申请人
				String requesterId = meetingApplicationCriteriaVO.getRequesterId();
				if(!StrUtil.isEmptyStr(requesterId)){
					wherejpql += " and requester.name like '%"+requesterId+"%'";
				}
/*				if(!StrUtil.isEmptyStr(requesterId)){
					wherejpql += " and requester.userId = '"+requesterId+"'";
				}*/
				//申请状态
				String state = meetingApplicationCriteriaVO.getState();
				if(!StrUtil.isEmptyStr(state)&&!state.equals("-1")){
					wherejpql += " and state = "+state;
				}
				//申请级别
				String level = meetingApplicationCriteriaVO.getLevel();
				if(!StrUtil.isEmptyStr(level)){
					wherejpql += " and level.id = '"+level+"'";
				}
				//关键字
				String Str=meetingApplicationCriteriaVO.getKeyWord();
				if(!StrUtil.isEmptyStr(Str)){
					String[] keyWords = Str.split("；");
					for(String keyWord : keyWords){
						wherejpql+=" and (purpose like '%"+keyWord+"%' or medicalRecord like '%"+keyWord+"%')";
					}				
				}
				
			}
			
			LinkedHashMap<String, Object> orderby = new LinkedHashMap<String, Object>();
			orderby.put("state", "asc");
//			orderby.put("level", "asc");
			orderby.put("expectedTime", "desc");
			return this.getScrollData(psm, wherejpql, null, orderby).getResultlist();			
		}catch(Exception e){
			logger.error("Error MeetingApplicationDaoImpl.searchMeetingApplicationList",e);
			throw e;
		}catch(Throwable e){
			logger.error("Error MeetingApplicationDaoImpl.searchMeetingApplicationList",e);
			throw e;
		}
	}
	/**
	 * 查询共同体的申请病历讨论
	 * @param psm 分页
	 * @param application 查询VO
	 * @param expectedTimeStart 建议会诊日期起
	 * @param expectedTimeEnd 建议会诊日期止
	 * @param requester
	 * @return
	 * @throws Throwable 
	 */
	public List<MeetingApplication> searchUnifiedMeetingApplicationList(PageSortModel psm, MeetingApplicationCriteriaVO meetingApplicationCriteriaVO) throws Throwable {
		String wherejpql = "";
//		List<Object> tempParams = new ArrayList<Object>(4);
		//会诊目的
//		if(!StrUtil.isEmptyStr(application.getPurpose())){
//			wherejpql += " and purpose like ? ";
//			tempParams.add("%" + application.getPurpose() + "%");
//		}
		try{
			if(meetingApplicationCriteriaVO!=null){
				//建议会诊日期
				String expectedTime = meetingApplicationCriteriaVO.getExpectedTime();
				if(!StrUtil.isEmptyStr(meetingApplicationCriteriaVO.getExpectedTime())){
					wherejpql += " and expectedTime >= '"+expectedTime+" 00:00:00' and expectedTime <= '"+expectedTime+" 23:59:59' ";
				}
				//建议会诊日期起
				String expectedTimeStart = meetingApplicationCriteriaVO.getExpectedTimeStart();
				if(!StrUtil.isEmptyStr(expectedTimeStart)){
					wherejpql += " and expectedTime >= '"+expectedTimeStart+" 00:00:00'";
				}
				//建议会诊日期止
				String expectedTimeEnd = meetingApplicationCriteriaVO.getExpectedTimeEnd();
				if(!StrUtil.isEmptyStr(expectedTimeEnd)){
					wherejpql += " and expectedTime <= '"+expectedTimeEnd+" 23:59:59'";
				}
				//申请时间
				String applicationTime = meetingApplicationCriteriaVO.getApplicationTime();
				if(!StrUtil.isEmptyStr(applicationTime)){
					wherejpql += " and applicationTime >= '"+applicationTime+" 00:00:00' and applicationTime <= '"+applicationTime+" 23:59:59' ";
				}
				//会诊类型
				String meetingType = meetingApplicationCriteriaVO.getMeetingType();
				if(!StrUtil.isEmptyStr(meetingType)&&!meetingType.equals("-1")){
					wherejpql += " and meetingType.id = "+meetingType;
				}
				//申请人
				String requesterId = meetingApplicationCriteriaVO.getRequesterId();
				if(!StrUtil.isEmptyStr(requesterId)){
					wherejpql += " and requester.userId = '"+requesterId+"'";
				}
				//申请状态
				String state = meetingApplicationCriteriaVO.getState();
				if(!StrUtil.isEmptyStr(state)&&!state.equals("-1")){
					wherejpql += " and state = "+state;
				}
				//关键字
				String Str=meetingApplicationCriteriaVO.getKeyWord();
				if(!StrUtil.isEmptyStr(Str)){
					String[] keyWords = Str.split("；");
					for(String keyWord : keyWords){
						wherejpql+=" and (purpose like '%"+keyWord+"%' or medicalRecord like '%"+keyWord+"%')";
					}				
				}
				//患者姓名
				String patientName=meetingApplicationCriteriaVO.getPatientName();
				if(!StrUtil.isEmptyStr(patientName)){
					wherejpql+=" and patientInfo.name like '%"+patientName+"%'";
				}
			}
			
			LinkedHashMap<String, Object> orderby = new LinkedHashMap<String, Object>();
			orderby.put("state", "asc");
			orderby.put("expectedTime", "desc");
			return this.getScrollData(psm, wherejpql, null, orderby).getResultlist();			
		}catch(Exception e){
			logger.error("Error MeetingApplicationDaoImpl.searchUnifiedMeetingApplicationList",e);
			throw e;			
		}catch(Throwable e){
			logger.error("Error MeetingApplicationDaoImpl.searchUnifiedMeetingApplicationList",e);
			throw e;			
		}
	}
	public Integer delDeptsByAppId(Integer appId){
		final String hql = "delete from application_department where meeting_application =  " + appId ;
		return getHibernateTemplate().execute(new HibernateCallback<Integer>() {
			@Override
			public Integer doInHibernate(Session session) throws HibernateException, SQLException {
				Integer size = session.createSQLQuery(hql).executeUpdate();
				return size;
			}
		});
	}

	@Override
	public void delApplicationAuthorInfoByAppId(final Integer appId) {
		getHibernateTemplate().execute(new HibernateCallback<Void>() {
			@Override
			public Void doInHibernate(Session session) throws HibernateException, SQLException {
				session.createQuery("delete from ApplicationAuthorInfo where id = ?")
					.setInteger(0, appId).executeUpdate();
				return null;
			}
		});
	}

	@Override
	public void delApplicationPatientInfoByAppId(final Integer appId) {
		getHibernateTemplate().execute(new HibernateCallback<Void>() {
			@Override
			public Void doInHibernate(Session session) throws HibernateException, SQLException {
				session.createQuery("delete from ApplicationPatientInfo where id = ?")
					.setInteger(0, appId).executeUpdate();
				return null;
			}
		});
	}

}