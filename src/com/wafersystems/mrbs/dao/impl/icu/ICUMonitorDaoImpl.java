package com.wafersystems.mrbs.dao.impl.icu;

import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.wafersystems.mrbs.dao.base.GenericDaoImpl;
import com.wafersystems.mrbs.dao.base.PageSortModel;
import com.wafersystems.mrbs.dao.icu.ICUMonitorDao;
import com.wafersystems.mrbs.vo.meeting.ICUMonitor;
import com.wafersystems.mrbs.web.criteriavo.ICUMonitorCriteriaVO;
import com.wafersystems.util.StrUtil;

@Repository
public class ICUMonitorDaoImpl extends GenericDaoImpl<ICUMonitor> implements ICUMonitorDao{

	/**
	 * 查询重症监护信息
	 * @param psm 分页
	 * @param application 查询VO
	 * @param expectedTimeStart 建议会诊日期起
	 * @param expectedTimeEnd 建议会诊日期止
	 * @param requester
	 * @return
	 * @throws Throwable 
	 */
	public List<ICUMonitor> searchICUMonitorList(PageSortModel psm, ICUMonitorCriteriaVO iCUMonitorCriteriaVO) throws Throwable {
		String wherejpql = "";
//		List<Object> tempParams = new ArrayList<Object>(4);
		//会诊目的
//		if(!StrUtil.isEmptyStr(application.getPurpose())){
//			wherejpql += " and purpose like ? ";
//			tempParams.add("%" + application.getPurpose() + "%");
//		}
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
				//申请时间
				String applicationTime = iCUMonitorCriteriaVO.getApplicationTime();
				if(!StrUtil.isEmptyStr(applicationTime)){
					wherejpql += " and applicationTime >= '"+applicationTime+" 00:00:00' and applicationTime <= '"+applicationTime+" 23:59:59' ";
				}
				//会诊类型
				String meetingType = iCUMonitorCriteriaVO.getMeetingType();
				if(!StrUtil.isEmptyStr(meetingType)&&!meetingType.equals("-1")){
					wherejpql += " and meetingType.id = "+meetingType;
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
//			orderby.put("level", "asc");
			orderby.put("expectedTime", "desc");
			return this.getScrollData(psm, wherejpql, null, orderby).getResultlist();			
		}catch(Exception e){
			logger.error("Error ICUMonitorDaoImpl.searchICUMonitorList",e);
			throw e;
		}catch(Throwable e){
			logger.error("Error ICUMonitorDaoImpl.searchICUMonitorList",e);
			throw e;
		}
	}
	/**
	 * 查询共同体的申请重症监护
	 * @param psm 分页
	 * @param application 查询VO
	 * @param expectedTimeStart 建议会诊日期起
	 * @param expectedTimeEnd 建议会诊日期止
	 * @param requester
	 * @return
	 * @throws Throwable 
	 */
	public List<ICUMonitor> searchUnifiedICUMonitAppList(PageSortModel psm, ICUMonitorCriteriaVO iCUMonitorCriteriaVO) throws Throwable {
		String wherejpql = "";
//		List<Object> tempParams = new ArrayList<Object>(4);
		//会诊目的
//		if(!StrUtil.isEmptyStr(application.getPurpose())){
//			wherejpql += " and purpose like ? ";
//			tempParams.add("%" + application.getPurpose() + "%");
//		}
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
				//申请时间
				String applicationTime = iCUMonitorCriteriaVO.getApplicationTime();
				if(!StrUtil.isEmptyStr(applicationTime)){
					wherejpql += " and applicationTime >= '"+applicationTime+" 00:00:00' and applicationTime <= '"+applicationTime+" 23:59:59' ";
				}
				//会诊类型
				String meetingType = iCUMonitorCriteriaVO.getMeetingType();
				if(!StrUtil.isEmptyStr(meetingType)&&!meetingType.equals("-1")){
					wherejpql += " and meetingType.id = "+meetingType;
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
			logger.error("Error ICUMonitorDaoImpl.searchUnifiedICUMonitAppList",e);
			throw e;			
		}catch(Throwable e){
			logger.error("Error ICUMonitorDaoImpl.searchUnifiedICUMonitAppList",e);
			throw e;			
		}
	}
	/**
	 * add  by wangzhenglin 删除icu监护和部门的关联关系
	 * @param icuId
	 * @return
	 */
	public Integer delDeptsByMonitorId(Integer icuId){
		final String hql = "delete from icu_department where icumonitor =  " + icuId ;
		return getHibernateTemplate().execute(new HibernateCallback<Integer>() {
			@Override
			public Integer doInHibernate(Session session) throws HibernateException, SQLException {
				Integer size = session.createSQLQuery(hql).executeUpdate();
				return size;
			}
		});
	}

}
