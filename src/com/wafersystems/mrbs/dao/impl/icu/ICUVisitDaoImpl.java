package com.wafersystems.mrbs.dao.impl.icu;

import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.wafersystems.mrbs.dao.base.GenericDaoImpl;
import com.wafersystems.mrbs.dao.base.PageSortModel;
import com.wafersystems.mrbs.dao.icu.ICUVisitDao;
import com.wafersystems.mrbs.vo.meeting.ICUVisitation;
import com.wafersystems.mrbs.web.criteriavo.ICUMonitorCriteriaVO;
import com.wafersystems.util.StrUtil;

@Repository
public class ICUVisitDaoImpl extends GenericDaoImpl<ICUVisitation> implements ICUVisitDao {

	@Override
	public List<ICUVisitation> searchICUICUVisitList(PageSortModel psm,ICUMonitorCriteriaVO iCUMonitorCriteriaVO) throws Throwable {
		String wherejpql = "";
		try{
			if(iCUMonitorCriteriaVO!=null){
				//建议会诊日期
				String expectedTime = iCUMonitorCriteriaVO.getExpectedTime();
				if(!StrUtil.isEmptyStr(iCUMonitorCriteriaVO.getExpectedTime())){
					wherejpql += " and expectedTime >= '"+expectedTime+" 00:00:00' and expectedTime <= '"+expectedTime+" 23:59:59' ";
				}
				//建议会诊日期起
				String expectedTimeStart = iCUMonitorCriteriaVO.getExpectedTimeStart();
				if(!StrUtil.isEmptyStr(expectedTimeStart)){
					wherejpql += " and expectedTime >= '"+expectedTimeStart+" 00:00:00'";
				}
				//建议会诊日期止
				String expectedTimeEnd = iCUMonitorCriteriaVO.getExpectedTimeEnd();
				if(!StrUtil.isEmptyStr(expectedTimeEnd)){
					wherejpql += " and expectedTime <= '"+expectedTimeEnd+" 23:59:59'";
				}
				//申请人
				String requesterId = iCUMonitorCriteriaVO.getRequesterId();
				if(!StrUtil.isEmptyStr(requesterId)){
					wherejpql += " and requester.userId = '"+requesterId+"'";
				}
				//申请状态
				String state = iCUMonitorCriteriaVO.getState();
				if(!StrUtil.isEmptyStr(state)&&!state.equals("-1")){
					wherejpql += " and state = "+state;
				}
				//关键字
				String Str=iCUMonitorCriteriaVO.getKeyWord();
				if(!StrUtil.isEmptyStr(Str)){
					String[] keyWords = Str.split("；");
					for(String keyWord : keyWords){
						wherejpql+=" and (purpose like '%"+keyWord+"%' or medicalRecord like '%"+keyWord+"%')";
					}				
				}
				//患者姓名
				String patientName=iCUMonitorCriteriaVO.getPatientName();
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

	@Override
	public List<ICUVisitation> searchApplyICUVisitList(PageSortModel psm,
			ICUMonitorCriteriaVO iCUMonitorCriteriaVO) throws Throwable {
		String wherejpql = "";
		try{
			if(iCUMonitorCriteriaVO!=null){
				//建议会诊日期
				String expectedTime = iCUMonitorCriteriaVO.getExpectedTime();
				if(!StrUtil.isEmptyStr(iCUMonitorCriteriaVO.getExpectedTime())){
					wherejpql += " and expectedTime >= '"+expectedTime+" 00:00:00' and expectedTime <= '"+expectedTime+" 23:59:59' ";
				}
				//建议会诊日期起
				String expectedTimeStart = iCUMonitorCriteriaVO.getExpectedTimeStart();
				if(!StrUtil.isEmptyStr(expectedTimeStart)){
					wherejpql += " and expectedTime >= '"+expectedTimeStart+" 00:00:00'";
				}
				//建议会诊日期止
				String expectedTimeEnd = iCUMonitorCriteriaVO.getExpectedTimeEnd();
				if(!StrUtil.isEmptyStr(expectedTimeEnd)){
					wherejpql += " and expectedTime <= '"+expectedTimeEnd+" 23:59:59'";
				}
				//申请人
				String requesterId = iCUMonitorCriteriaVO.getRequesterId();
				if(!StrUtil.isEmptyStr(requesterId)){
					wherejpql += " and requester.name like '%"+requesterId+"%'";
				}
/*				if(!StrUtil.isEmptyStr(requesterId)){
					wherejpql += " and requester.userId = '"+requesterId+"'";
				}*/
				//申请状态
				String state = iCUMonitorCriteriaVO.getState();
				if(!StrUtil.isEmptyStr(state)&&!state.equals("-1")){
					wherejpql += " and state = "+state;
				}
				//申请级别
				String level = iCUMonitorCriteriaVO.getLevel();
				if(!StrUtil.isEmptyStr(level)){
					wherejpql += " and level.id = '"+level+"'";
				}
				//关键字
				String Str=iCUMonitorCriteriaVO.getKeyWord();
				if(!StrUtil.isEmptyStr(Str)){
					String[] keyWords = Str.split("；");
					for(String keyWord : keyWords){
						wherejpql+=" and (purpose like '%"+keyWord+"%' or medicalRecord like '%"+keyWord+"%')";
					}				
				}
				
			}
			
			LinkedHashMap<String, Object> orderby = new LinkedHashMap<String, Object>();
			orderby.put("state", "asc");
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

}
