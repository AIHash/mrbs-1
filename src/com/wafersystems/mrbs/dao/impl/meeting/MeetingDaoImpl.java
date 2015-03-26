package com.wafersystems.mrbs.dao.impl.meeting;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.wafersystems.mrbs.GlobalConstent;
import com.wafersystems.mrbs.dao.base.GenericDaoImpl;
import com.wafersystems.mrbs.dao.base.PageData;
import com.wafersystems.mrbs.dao.base.PageSortModel;
import com.wafersystems.mrbs.dao.meeting.MeetingDao;
import com.wafersystems.mrbs.vo.meeting.Meeting;
import com.wafersystems.mrbs.web.criteriavo.MeetingCriteriaVO;
import com.wafersystems.util.StrUtil;

@Repository
public class MeetingDaoImpl extends GenericDaoImpl<Meeting> implements MeetingDao {
	Logger logger = LoggerFactory.getLogger(MeetingDaoImpl.class);
	/**
	 * 查询已经审批通过的会诊/视频讲座
	 * @param MeetingCriteriaVO
	 * @return
	 * @throws Throwable 
	 */
	public PageData<Meeting> getMeetingList(PageSortModel psm,MeetingCriteriaVO meetingCriteriaVO) throws Throwable{
		try{
			logger.debug("Enter MeetingDaoImpl.getMeetingList");
			String hql=" ";
			if(!StrUtil.isEmptyStr(meetingCriteriaVO.getMeetingKind()) && !meetingCriteriaVO.getMeetingKind().equals(GlobalConstent.APPLICATION_TYPE_ICUVISIT+"")){
			  hql="  and exists (select s.member from o.members as s where s.meetingId.id=o.id";
				//接受人
				if(!StrUtil.isEmptyStr(meetingCriteriaVO.getAcceptUserId())){
					hql+=" and s.member='"+meetingCriteriaVO.getAcceptUserId()+"'";
				}
				 
				//受邀状态
				if(!StrUtil.isEmptyStr(meetingCriteriaVO.getInvitedState())){
					hql+=" and s.attendState='"+meetingCriteriaVO.getInvitedState()+"'";
				}
				hql+=")";
			}else{
				hql +=" and o.creator.userId='"+meetingCriteriaVO.getAcceptUserId()+"'";;
			} 
			
			 
			//主键PK
			if(!StrUtil.isEmptyStr(meetingCriteriaVO.getMeetingId())){
				hql+=" and o.id='"+meetingCriteriaVO.getMeetingId()+"'";
			}
			//代表申请的会诊类型（远程会诊为1；视频讲座为2）
			if(!StrUtil.isEmptyStr(meetingCriteriaVO.getMeetingKind())){
				hql+=" and o.meetingKind='"+meetingCriteriaVO.getMeetingKind()+"'";
			}
			//开始时间
			String meetingStartTime=meetingCriteriaVO.getMeetingStartTime();
			if(!StrUtil.isEmptyStr(meetingStartTime)){
				hql+=" and o.startTime>='"+meetingStartTime+" 00:00:00'";
			}
			//结束时间
			String meetingEndTime=meetingCriteriaVO.getMeetingEndTime();
			if(!StrUtil.isEmptyStr(meetingEndTime)){
				hql+=" and o.startTime<='"+meetingEndTime+" 23:59:59'";
			}
			//当天开始时间
			String startTime=meetingCriteriaVO.getStartTime();
			if(!StrUtil.isEmptyStr(startTime)){
				hql+=" and o.startTime>='"+startTime+" 00:00:00' and o.startTime<='"+startTime+" 23:59:59'";
			}
			//会诊类型
			String meetingtype=meetingCriteriaVO.getMeetingType();
			if(!StrUtil.isEmptyStr(meetingtype)&&!meetingtype.equals("-1")){
				hql+=" and o.meetingType.id='"+meetingtype+"'";
			}
			//讲座目的
			String purposeName=meetingCriteriaVO.getPurposeName();
			if(!StrUtil.isEmptyStr(purposeName)&&!purposeName.equals("-1")){
				hql += " and o.videoapplicationId.videoApplicationPurpose.id ='"+purposeName+"'";
			}
			//主讲人所在科室
			String departmentName=meetingCriteriaVO.getDepartmentName();
			if(!StrUtil.isEmptyStr(departmentName)&&!departmentName.equals("-1")){
				hql += " and o.videoapplicationId.deptName.name like'%"+departmentName+"%'";
			}
			//主讲人姓名
			String mainUserId=meetingCriteriaVO.getMainUserId();
			if(!StrUtil.isEmptyStr(mainUserId)&&!mainUserId.equals("-1")){
				hql += " and o.videoapplicationId.userName.name like '%"+mainUserId+"%'";
			}
			//状态
			String state=meetingCriteriaVO.getState();
			if(!StrUtil.isEmptyStr(state)&&!state.equals("-1")){
				hql+=" and o.state="+state;
			}
			//申请人
			String requesterUserName=meetingCriteriaVO.getRequesterUserName();
			if(!StrUtil.isEmptyStr(requesterUserName)){
				hql+=" and o.creator.name like '%"+requesterUserName+"%'";
			}
			
			//关键字
			String Str=meetingCriteriaVO.getKeyWord();
			if(!StrUtil.isEmptyStr(Str)){
				String[] keyWords = Str.split("；");
				for(String keyWord : keyWords){
					hql+=" and (o.content like '%"+keyWord+"%' or o.title like '%"+keyWord+"%' )" ;
				}				
			}
			LinkedHashMap<String,Object> linkmap=new LinkedHashMap<String,Object>();
			linkmap.put("state", "asc");
			linkmap.put("startTime", "desc");
			PageData<Meeting> data = this.getScrollData(psm, hql, null, linkmap);
			return data;
		}catch(Exception e){
			logger.error("Error MeetingDaoImpl.getMeetingList",e);
			throw e;
		}catch(Throwable e){
			logger.error("Error MeetingDaoImpl.getMeetingList",e);
			throw e;
		}
		
	}
	
	/**
	 * 判断会议时间是否在预约时间之内
	 * @param startTime 开始时间
	 * @param endTime 结束时间
	 * @param meetingRoomId 会议室Id
	 * @return
	 * @throws Throwable 
	 */
	public int getMeetingCountByStartTimeAndEndTime(String startTime,String endTime,String meetingRoomId,String exceptMeetingId) throws Throwable {
		try{
			if(StrUtil.isEmptyStr(startTime)&&StrUtil.isEmptyStr(startTime)){
				return 0;
			}
			String  sql= "select count(*) from meeting WHERE meeting_type != 4 and (state = '1' or state = '2')";
			if(!StrUtil.isEmptyStr(startTime)&&!StrUtil.isEmptyStr(endTime)){
				sql+=" and ((start_time <= '"+startTime+"' and end_time  >= '"+startTime+"') or (start_time <= '"+endTime+"' and end_time  >= '"+endTime+"') or (start_time >= '"+startTime+"' and end_time  <= '"+endTime+"'))";  
			}else{
				//开始时间在已经预约的时间之间
				if(!StrUtil.isEmptyStr(startTime)){
					sql+=" and start_time <= '"+startTime+"' and end_time  >= '"+startTime+"'";  
				}
				//结束时间在已经预约的时间之间
				if(!StrUtil.isEmptyStr(endTime)){
					sql+=" and start_time <= '"+endTime+"' and end_time  >= '"+endTime+"'";  
				}
			}
			if(!StrUtil.isEmptyStr(meetingRoomId)){
				sql+=" and meeting_room_id='"+meetingRoomId+"'";  
			}
			if(!StrUtil.isEmptyStr(exceptMeetingId)){
				sql+=" and id<>'"+exceptMeetingId+"'";  
			}
			logger.debug("MeetingDaoImpl.getMeetingCountByStartTimeAndEndTime sql="+sql);
			final String querySql = sql;
			@SuppressWarnings("unchecked")
			List<Object> result = template.executeFind(new HibernateCallback<List<Object>>() {

				@Override
				public List<Object> doInHibernate(Session session)
						throws HibernateException, SQLException {
					return session.createSQLQuery(querySql).list();
				}
			});
			return Integer.valueOf(result.get(0).toString()) ;
		}catch(Exception e){
			logger.error("Error MeetingDaoImpl.getMeetingCountByStartTimeAndEndTime",e);
			throw e;
		}catch(Throwable e){
			logger.error("Error MeetingDaoImpl.getMeetingCountByStartTimeAndEndTime",e);
			throw e;
		}
	}
	
	@Override
	public List<Meeting> searchMeetingList(PageSortModel psm,
			Map<String, Object> map) {
		String wherejpql = null;
		Object[] queryParams = null;
		LinkedHashMap<String, Object> orderby = new LinkedHashMap<String, Object>();
		orderby.put("state", "asc");
		orderby.put("startTime", "desc");
		if (map != null && !map.isEmpty()) {
			Set<String> set = map.keySet();
			wherejpql = this.createWhereSqlBySet(map);
			queryParams = new Object[set.size()];
			int i = 0;
			for (String temp : set) {
				queryParams[i] = map.get(temp);
				i++;
			}
		}
		return this.getScrollData(psm, wherejpql, queryParams, orderby)
				.getResultlist();
	}

	@Override
	public List<Meeting> getMeetingListIndex(Short state) {
		String hql = " and o.state=? and o.meetingKind=1";
		Short queryParams[] = new Short[] { state };
		LinkedHashMap<String, Object> orderby = new LinkedHashMap<String, Object>();
		orderby.put("id", "desc");
		return this.limitFindByHql(0, 7, hql, queryParams, orderby);
	}

	@Override
	public Long countMeetingConsultation(Short state) {
		String hql = null;
		Short queryParams[] = null;
		if (state != null) {
			hql = " and o.state=?";
			queryParams = new Short[] { state };
		}
		return this.getCount(hql, queryParams);
	}

	@Override
	public List<Meeting> getMeetingByDuration(Date startDate, Date endDate) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		StringBuilder builder = new StringBuilder();
		builder.append("from Meeting m where 1=1 ");
		if (startDate != null)
			builder.append(" and m.startTime >= '").append(dateFormat.format(startDate)).append("' ");
		if (endDate != null)
			builder.append(" and m.endTime <= '").append(dateFormat.format(endDate)).append("' ");
		return this.findTbyHql(builder.toString());
	}

	@Override
	public Meeting getMeetingByApplicationId(int application_id) {
		String hql = "from Meeting m where m.applicationId.id = "+application_id;
		List<Meeting> meeting = this.findTbyHql(hql);

		return meeting.size() == 0 ? null : meeting.get(0);
	}

	@Override
	public Map<String, Long> summaryReportByDepart(String startDate, String endDate){
		Map<String, Long> resultMap = new LinkedHashMap<String, Long>();

		String hql = "select m.applicationId.department.name, count(m.id) from Meeting m "
			+ " where m.startTime > '" + startDate + "' and m.endTime < '" + endDate + "' "
			+ " group by m.applicationId.department"
			+ " order by count(m.id)";
		List<?> result = template.find(hql);
		for (Object object : result) {
			Object[] obj = (Object[]) object;
			resultMap.put((String)obj[0], (Long)obj[1]);
		}
		return resultMap;
	}

	@Override
	public Map<String, Long> summaryReportByExpert(String startDate, String endDate){
		Map<String, Long> resultMap = new LinkedHashMap<String, Long>();

		String hql = "select mm.member.name, count(m.id) from Meeting m, MeetingMember mm  where m.id = mm.meetingId "
			+ " and m.startTime > '" + startDate + "' and m.endTime < '" + endDate + "' "
			+ " and mm.attendState = " + GlobalConstent.APPLICATION_STATE_ACCEPT
			+ " group by mm.member "
			+ " order by count(m.id)";
		List<?> result = template.find(hql);
		for (Object object : result) {
			Object[] obj = (Object[]) object;
			resultMap.put((String)obj[0], (Long)obj[1]);
		}
		return resultMap;
	}

	@Override
	public Map<String, Long> summaryReportByMeetingType(String startDate, String endDate){
		Map<String, Long> resultMap = new HashMap<String, Long>();

		String hql = "select m.applicationId.meetingType.name, count(m.id) from Meeting m " 
				+ " where m.startTime > '" + startDate + "' and m.endTime < '" + endDate + "' "
				+ " group by m.applicationId.meetingType "
				+ " order by count(m.id) ";
		List<?> result = template.find(hql);
		for (Object object : result) {
			Object[] obj = (Object[]) object;
			resultMap.put((String)obj[0], (Long)obj[1]);
		}
		return resultMap;
	}

	@Override
	public Map<String, List<Meeting>> detailReportByDepart(String startDate, String endDate){
		Map<String, List<Meeting>> resultMap = new HashMap<String, List<Meeting>>();

		String hql = "select m.applicationId.department.name, m from Meeting m "
				+ " where m.startTime > '" + startDate + "' and m.endTime < '" + endDate + "' "
				+ " order by m.applicationId.department";
		List<?> result = template.find(hql);

		List<Meeting> value = null;
		Meeting meeting = null;
		for (Object object : result) {
			Object[] obj = (Object[]) object;
			String key = (String)obj[0];
			meeting = (Meeting)obj[1];
			value = resultMap.get(key);
			if(value == null){
				value = new LinkedList<Meeting>();
				value.add(meeting);
			}else{
				value.add(meeting);
			}
			resultMap.put(key, value);
		}
		return resultMap;
	}

	@Override
	public Map<String, List<Meeting>> detailReportByExpert(String startDate, String endDate){
		Map<String, List<Meeting>> resultMap = new HashMap<String, List<Meeting>>();

		String hql = "select mm.member.name, m from Meeting m, MeetingMember mm  where m.id = mm.meetingId "
				+ " and m.startTime > '" + startDate + "' and m.endTime < '" + endDate + "' "
				+ " and mm.attendState = " + GlobalConstent.APPLICATION_STATE_ACCEPT
				+ " order by mm.member ";
		List<?> result = template.find(hql);

		List<Meeting> value = null;
		Meeting meeting = null;
		for (Object object : result) {
			Object[] obj = (Object[]) object;
			String key = (String)obj[0];
			meeting = (Meeting)obj[1];
			value = resultMap.get(key);
			if(value == null){
				value = new LinkedList<Meeting>();
				value.add(meeting);
			}else{
				value.add(meeting);
			}
			resultMap.put(key, value);
		}
		return resultMap;
	}

	@Override
	public Map<String, List<Meeting>> detailReportByMeetingType(String startDate, String endDate){
		Map<String, List<Meeting>> resultMap = new HashMap<String, List<Meeting>>();

		String hql = "select m.applicationId.meetingType.name, m from Meeting m " 
				+ " where m.startTime > '" + startDate + "' and m.endTime < '" + endDate + "' "
				+ " order by m.applicationId.meetingType ";
		List<?> result = template.find(hql);
		
		List<Meeting> value = null;
		Meeting meeting = null;
		for (Object object : result) {
			Object[] obj = (Object[]) object;
			String key = (String)obj[0];
			meeting = (Meeting)obj[1];
			value = resultMap.get(key);
			if(value == null){
				value = new LinkedList<Meeting>();
				value.add(meeting);
			}else{
				value.add(meeting);
			}
			resultMap.put(key, value);
		}
		return resultMap;
	}

	@Override
	public List<Meeting> getVdeioMeetingListIndex(Short state) {
		String hql = " and o.state=? and o.meetingKind=2";
		Short queryParams[] = new Short[] { state };
		LinkedHashMap<String, Object> orderby = new LinkedHashMap<String, Object>();
		orderby.put("id", "desc");
		return this.limitFindByHql(0, 7, hql, queryParams, orderby);
	}

	@Override
	public Meeting getMeetingByVideoAppId(final Integer videoAppId){
		return getHibernateTemplate().execute(new HibernateCallback<Meeting>() {
			@Override
			public Meeting doInHibernate(Session session) throws HibernateException, SQLException {
				return (Meeting) session.createQuery("from Meeting m where m.videoapplicationId.id = ?")
							.setInteger(0, videoAppId).uniqueResult();
			}
		});
	}
	
	@Override
	public List<Meeting> getMeetingOrderByDesc() {
		String hql = "from Meeting m order by m.startTime desc";
		List<Meeting> meetings = this.findTbyHql(hql);
		return meetings;
	}

	@Override
	public List<Meeting> getVideoStartTimeOrderByDesc() {
		String hql = "from Meeting m order by m.startTime desc";
		List<Meeting> meetings = this.findTbyHql(hql);
		return meetings;
	}
	@Override
	public Meeting getMeetingByIcuMonitor(int icuMonitorId) {
		String hql = "from Meeting m where m.iCUMonitorId.id = "+icuMonitorId;
		List<Meeting> meeting = this.findTbyHql(hql);

		return meeting.size() == 0 ? null : meeting.get(0);
	}

	@Override
	public Meeting getMeetingByIcuVisit(int icuVisitId) {
		String hql = "from Meeting m where m.iCUVisitationId.id = "+icuVisitId;
		List<Meeting> meeting = this.findTbyHql(hql);

		return meeting.size() == 0 ? null : meeting.get(0);
	}

}
