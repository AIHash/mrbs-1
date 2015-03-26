package com.wafersystems.mrbs.dao.impl.meeting;

import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Repository;

import com.wafersystems.mrbs.dao.base.GenericDaoImpl;
import com.wafersystems.mrbs.dao.base.PageSortModel;
import com.wafersystems.mrbs.dao.meeting.VideoMeetingAppDao;
import com.wafersystems.mrbs.vo.meeting.VideoMeetingApp;
import com.wafersystems.mrbs.web.criteriavo.VideoMeetingAppCriteriaVO;
import com.wafersystems.util.StrUtil;

@Repository
public class VideoMeetingAppDaoImpl extends GenericDaoImpl<VideoMeetingApp> implements VideoMeetingAppDao {
	@Override
	public List<VideoMeetingApp> getVideoMeetingList(Short state) {
		String hql=" and o.state=?";
		Short  queryParams[]=new Short[]{state};
		LinkedHashMap<String, Object> orderby=new LinkedHashMap<String, Object>();
		orderby.put("id", "desc");
		return this.limitFindByHql(0, 7, hql, queryParams, orderby);
	}
	
	@Override
	public Long countAccraditation(Short state) {
		String hql=" and o.state=?";
		Short  queryParams[]=new Short[]{state};
		return this.getCount(hql, queryParams);
	}

	@Override
	public List<VideoMeetingApp> searchVideoMeetingApplicationList(PageSortModel psm, VideoMeetingAppCriteriaVO videoMeetingAppCriteriaVO) throws Throwable {
		String wherejpql = "";
//		List<Object> tempParams = new ArrayList<Object>(4);
		//视频申请目的
/*		String videoApplicationPurpose=videoMeetingAppCriteriaVO.getVideoApplicationPurpose();
		if(!StrUtil.isEmptyStr(videoApplicationPurpose)&&!videoApplicationPurpose.equals("-1")){
			wherejpql += " and videoApplicationPurpose.id = "+videoApplicationPurpose;
//			tempParams.add( application.getVideoApplicationPurpose().getId() );
		}*/
		try{
			//建议日期
			String suggestDate = videoMeetingAppCriteriaVO.getSuggestDate();
			if(!StrUtil.isEmptyStr(suggestDate)){
				wherejpql += " and suggestDate >= '"+suggestDate+" 00:00:00' and suggestDate <= '"+suggestDate+" 23:59:59' ";
			}
			//建议日期起
			String expectedTimeStart = videoMeetingAppCriteriaVO.getExpectedTimeStart();
			if(!StrUtil.isEmptyStr(expectedTimeStart)){
				wherejpql += " and suggestDate >= '"+expectedTimeStart+" 00:00:00'";
			}
			//建议日期止
			String expectedTimeEnd = videoMeetingAppCriteriaVO.getExpectedTimeEnd();
			if(!StrUtil.isEmptyStr(expectedTimeEnd)){
				wherejpql += " and suggestDate <= '"+expectedTimeEnd+" 23:59:59'";
			}
			//申请日期
			String appDate = videoMeetingAppCriteriaVO.getAppDate();
			if(!StrUtil.isEmptyStr(appDate)){
				wherejpql += " and appDate >= '"+appDate+" 00:00:00' and appDate <= '"+appDate+" 23:59:59' ";
			}
			//申请人
			String requesterId = videoMeetingAppCriteriaVO.getRequesterId();
			if(!StrUtil.isEmptyStr(requesterId)){
				wherejpql += " and (videoRequester.name like '%"+requesterId+"%' or videoRequester.userId = '"+requesterId+"')";
				//wherejpql += " and videoRequester.userId = '"+requesterId+"'";
			}
			//主讲人所在科室
			String deptName=videoMeetingAppCriteriaVO.getDeptName();
			if(!StrUtil.isEmptyStr(deptName)&&!deptName.equals("-1")){
				wherejpql += " and deptName.name like '%"+deptName+"%'";
			}
			//主讲人姓名
			String userId=videoMeetingAppCriteriaVO.getUserId();
			if(!StrUtil.isEmptyStr(userId)&&!userId.equals("-1")){
				wherejpql += " and userName.name like '%"+userId+"%'";
			}
			//状态
			String state=videoMeetingAppCriteriaVO.getState();
			if(!StrUtil.isEmptyStr(state)&&!state.equals("-1")){
				wherejpql += " and state = "+state;
			}
			//关键字(按讲座内容来搜索)
			String Str=videoMeetingAppCriteriaVO.getKeyWord();
			if(!StrUtil.isEmptyStr(Str)){
				String[] keyWords = Str.split("；");
				for(String keyWord : keyWords){
					wherejpql+=" and lectureContent like '%"+keyWord+"%'";
				}				
			}
			LinkedHashMap<String, Object> orderby = new LinkedHashMap<String, Object>();
			orderby.put("state", "asc");
			orderby.put("suggestDate", "desc");
			return this.getScrollData(psm, wherejpql, null, orderby).getResultlist();
		}catch(Exception e){
			logger.error("Error VideoMeetingAppDaoImpl.searchVideoMeetingApplicationList",e);
			throw e;			
		}catch(Throwable e){
			logger.error("Error VideoMeetingAppDaoImpl.searchVideoMeetingApplicationList",e);
			throw e;			
		}

	}
}