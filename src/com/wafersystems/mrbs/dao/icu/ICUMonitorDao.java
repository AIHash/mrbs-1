package com.wafersystems.mrbs.dao.icu;

import java.util.List;

import com.wafersystems.mrbs.dao.base.GenericDao;
import com.wafersystems.mrbs.dao.base.GenericDaoImpl;
import com.wafersystems.mrbs.dao.base.PageSortModel;
import com.wafersystems.mrbs.vo.meeting.ICUMonitor;
import com.wafersystems.mrbs.web.criteriavo.ICUMonitorCriteriaVO;
public interface ICUMonitorDao extends  GenericDao<ICUMonitor>{

	/**
	 * 查询远程者监护信息
	 * @param psm 分页
	 * @param application 查询VO
	 * @param expectedTimeStart 建议会诊日期起
	 * @param expectedTimeEnd 建议会诊日期止
	 * @param requester
	 * @return
	 * @throws Throwable 
	 */
	public List<ICUMonitor> searchICUMonitorList(PageSortModel psm, ICUMonitorCriteriaVO iCUMonitorCriteriaVO) throws Throwable;
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
	public List<ICUMonitor> searchUnifiedICUMonitAppList(PageSortModel psm, ICUMonitorCriteriaVO iCUMonitorCriteriaVO)throws Throwable;
	/**
	 * add  by wangzhenglin 删除icu监护和部门的关联关系
	 * @param icuId
	 * @return
	 */
	public Integer delDeptsByMonitorId(Integer icuId);
}
