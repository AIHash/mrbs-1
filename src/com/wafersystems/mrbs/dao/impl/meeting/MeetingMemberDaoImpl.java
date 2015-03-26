package com.wafersystems.mrbs.dao.impl.meeting;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.wafersystems.mrbs.GlobalConstent;
import com.wafersystems.mrbs.dao.base.GenericDaoImpl;
import com.wafersystems.mrbs.dao.meeting.MeetingMemberDao;
import com.wafersystems.mrbs.vo.meeting.MeetingMember;
import com.wafersystems.mrbs.web.report.vo.StatsMeetingMember;
import com.wafersystems.util.StrUtil;

@Repository
public class MeetingMemberDaoImpl extends GenericDaoImpl<MeetingMember> implements MeetingMemberDao {

	private static Logger logger = LoggerFactory.getLogger(MeetingMemberDaoImpl.class);

	@Override
	public List<MeetingMember> getMeetingMemberByDuration(Date startDate, Date endDate) {

		StringBuilder builder = new StringBuilder();
		builder.append("from MeetingMember me where me.member.userType = ").append(GlobalConstent.USER_TYPE_UNION);
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

		if(startDate != null)
			builder.append(" and  me.meetingId.startTime >= '").append(dateFormat.format(startDate)).append("' ");
		if(endDate != null)
			builder.append(" and me.meetingId.endTime <= '").append(dateFormat.format(endDate)).append("' ");

		logger.debug("MeetingMemberDaoImpl.getMeetingMemberByDuration 查询语句：" + builder.toString());
		return this.findTbyHql(builder.toString());
	}
	
	/**
	 * 统计次数/人数
	 * @param startDate 讲座/会诊开始时间
	 * @param endDate 讲座/会诊结束时间
	 * @param meetingKind //代表申请的会诊类型（远程会诊为1；视频讲座为2）
	 * @return
	 */
	public List<StatsMeetingMember> statsMeetingMemberByFrequency(String startDate, String endDate,Short meetingKind){
		List<StatsMeetingMember> statsMeetingMemberList = null;
		try{
			logger.debug("Enter MeetingMemberDaoImpl.statsMeetingMemberByFrequency");
			String hql = "select m.member.name, count(m.id), sum(m.attendNo) from MeetingMember m " 
						+ " where m.attendState ='"+GlobalConstent.APPLICATION_STATE_ACCEPT+"' ";
			
			if(!StrUtil.isEmptyStr(startDate)&&!StrUtil.isEmptyStr(endDate))
			 {
				 hql+="and m.meetingId.startTime >= '" + startDate + " 00:00:00' and m.meetingId.startTime <= '" + endDate + " 23:59:59' and m.meetingId.endTime<='"+endDate+" 23:59:59'"+" and m.meetingId.endTime>='"+startDate+" 00:00:00'";
				 //hql+="and m.meetingId.endTime<='"+endDate+" 23:59:59'"+" and m.meetingId.endTime>='"+startDate+" 00:00:00'";
			 }
			
			//代表申请的会诊类型（远程会诊为1；视频讲座为2）
			if(meetingKind!=null){
				 hql+=" and m.meetingId.meetingKind='"+meetingKind+"'";
			}	
			hql+= " group by m.member.name order by count(m.id) desc,convert_gbk(m.member.name) asc";
			List<?> result = template.find(hql); 
			if(result!=null&&!result.isEmpty()){
				statsMeetingMemberList = new ArrayList<StatsMeetingMember>();
				StatsMeetingMember statsMeetingMember = null;
				int i=1;
				for (Object object : result) {
					statsMeetingMember = new StatsMeetingMember();
					Object[] obj = (Object[]) object;
					statsMeetingMember.setNumber(String.valueOf(i));
					statsMeetingMember.setName((String)obj[0]);
					statsMeetingMember.setTimesCount(((Long)obj[1]).toString());
					statsMeetingMember.setMemberCount(((Long)obj[2]).toString());
					statsMeetingMemberList.add(statsMeetingMember);
					i++;
				}
			}
			
		}catch (Exception e){
			logger.error("Error MeetingMemberDaoImpl.statsMeetingMemberByFrequency",e);
		}
		return statsMeetingMemberList;
	}
	
	/**
	 * 统计人数/次数
	 * @param startDate 讲座/会诊开始时间
	 * @param endDate 讲座/会诊结束时间
	 * @param meetingKind //代表申请的会诊类型（远程会诊为1；视频讲座为2）
	 * @return
	 */
	public List<StatsMeetingMember> statsMeetingMemberByPeopleCount(String startDate, String endDate,Short meetingKind){
		List<StatsMeetingMember> statsMeetingMemberList = null;
		try{
			logger.debug("Enter MeetingMemberDaoImpl.statsMeetingMemberByPeopleCount");
			String hql = "select m.member.name, sum(m.attendNo) from MeetingMember m " 
						+ " where m.attendState ='"+GlobalConstent.APPLICATION_STATE_ACCEPT+"'";
			if(!StrUtil.isEmptyStr(startDate)&&!StrUtil.isEmptyStr(endDate))
			 {
				 hql+="and m.meetingId.startTime >= '" + startDate + " 00:00:00' and m.meetingId.startTime <= '" + endDate + " 23:59:59' and m.meetingId.endTime<='"+endDate+" 23:59:59'"+" and m.meetingId.endTime>='"+startDate+" 00:00:00'";
				 //hql+="and m.meetingId.endTime<='"+endDate+" 23:59:59'"+" and m.meetingId.endTime>='"+startDate+" 00:00:00'";
			 }			
			
			//代表申请的会诊类型（远程会诊为1；视频讲座为2）
			if(meetingKind!=null){
				 hql+=" and m.meetingId.meetingKind='"+meetingKind+"'";
			}	
			hql+= " group by m.member.name order by count(m.attendNo) desc,convert_gbk(m.member.name) asc";
			List<?> result = template.find(hql);
			if(result!=null&&!result.isEmpty()){
				statsMeetingMemberList = new ArrayList<StatsMeetingMember>();
				StatsMeetingMember statsMeetingMember = null;
				int i=1;
				for (Object object : result) {
					statsMeetingMember = new StatsMeetingMember();
					Object[] obj = (Object[]) object;
					statsMeetingMember.setNumber(String.valueOf(i));
					statsMeetingMember.setName((String)obj[0]);
					statsMeetingMember.setMemberCount(((Long)obj[1]).toString());
					statsMeetingMemberList.add(statsMeetingMember);
					i++;
				}
			}
			
		}catch (Exception e){
			logger.error("Error MeetingMemberDaoImpl.statsMeetingMemberByPeopleCount",e);
		}
		return statsMeetingMemberList;
	}
	/**
	 * 根据参会人类型统计人数/次数
	 * @param startDate 讲座/会诊开始时间
	 * @param endDate 讲座/会诊结束时间
	 * @param meetingKind  代表申请的会诊类型（远程会诊为1；视频讲座为2）
	 * @param memberType  参会人类型
	 * @return
	 */
	public List<StatsMeetingMember> statsMeetingMemberByMemberType(String startDate, String endDate,Short meetingKind,Short memberType){
		List<StatsMeetingMember> statsMeetingMemberList = null;
		try{
			logger.debug("Enter MeetingMemberDaoImpl.statsMeetingMemberByFrequency");
			String hql = "select m.member.name, count(m.id), sum(m.attendNo) from MeetingMember m " 
						+ " where m.attendState ='"+GlobalConstent.APPLICATION_STATE_ACCEPT+"' ";
			
			if(!StrUtil.isEmptyStr(startDate)&&!StrUtil.isEmptyStr(endDate))
			 {
				 hql+="and m.meetingId.startTime >= '" + startDate + " 00:00:00' and m.meetingId.startTime <= '" + endDate + " 23:59:59' and m.meetingId.endTime<='"+endDate+" 23:59:59'"+" and m.meetingId.endTime>='"+startDate+" 00:00:00'";
				 //hql+="and m.meetingId.endTime<='"+endDate+" 23:59:59'"+" and m.meetingId.endTime>='"+startDate+" 00:00:00'";
			 }
			
			//代表申请的会诊类型（远程会诊为1；视频讲座为2）
			if(meetingKind!=null){
				 hql+=" and m.meetingId.meetingKind='"+meetingKind+"'";
			}
			//参会人类型
			if(memberType!=null){
				 hql+=" and m.member.userType.value="+memberType+"";
			}
			hql+= " group by m.member.name order by count(m.id) desc,convert_gbk(m.member.name) asc";
			List<?> result = template.find(hql); 
			if(result!=null&&!result.isEmpty()){
				statsMeetingMemberList = new ArrayList<StatsMeetingMember>();
				StatsMeetingMember statsMeetingMember = null;
				int i=1;
				for (Object object : result) {
					statsMeetingMember = new StatsMeetingMember();
					Object[] obj = (Object[]) object;
					statsMeetingMember.setNumber(String.valueOf(i));
					statsMeetingMember.setName((String)obj[0]);
					statsMeetingMember.setTimesCount(((Long)obj[1]).toString());
					statsMeetingMember.setMemberCount(((Long)obj[2]).toString());
					statsMeetingMemberList.add(statsMeetingMember);
					i++;
				}
			}
			
		}catch (Exception e){
			logger.error("Error MeetingMemberDaoImpl.statsMeetingMemberByFrequency",e);
		}
		return statsMeetingMemberList;
	}
	
	public List<MeetingMember> getMembersByMeetingId(final Integer meetingId){
		return getHibernateTemplate().execute(new HibernateCallback<List<MeetingMember>>() {
			@Override
			public List<MeetingMember> doInHibernate(Session session)
					throws HibernateException, SQLException {
				String hql = "from MeetingMember mm where mm.meetingId.id = ?";
				return session.createQuery(hql).setInteger(0, meetingId).list();
			}
		});
	}
	
	public List<MeetingMember> getMembersByMeetingIdAndUserId(final Integer meetingId,final String userId){
		return getHibernateTemplate().execute(new HibernateCallback<List<MeetingMember>>() {
			@Override
			public List<MeetingMember> doInHibernate(Session session)
					throws HibernateException, SQLException {
				String hql = "from MeetingMember mm where mm.meetingId.id = ? and mm.member.userId = ? ";
				return session.createQuery(hql).setInteger(0, meetingId).setString(1, userId).list();
			}
		});
	}

	public Integer delMembersByMeetingId(final Integer meetingId){
		return getHibernateTemplate().execute(new HibernateCallback<Integer>() {
			@Override
			public Integer doInHibernate(Session session)throws HibernateException, SQLException {
				session.createQuery("delete from MeetingMember mm where mm.meetingId.id = ?")
					.setInteger(0, meetingId).executeUpdate();
				return null;
			}
		});
	}
}