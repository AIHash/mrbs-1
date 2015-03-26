package com.wafersystems.mrbs.dao.icu;


import java.util.List;

import com.wafersystems.mrbs.dao.base.GenericDao;
import com.wafersystems.mrbs.dao.base.PageSortModel;
import com.wafersystems.mrbs.vo.meeting.ICUMonitor;
import com.wafersystems.mrbs.vo.meeting.ICUVisitation;
import com.wafersystems.mrbs.web.criteriavo.ICUMonitorCriteriaVO;
public interface ICUVisitDao extends  GenericDao<ICUVisitation>{

	/**
	 * 查询共同体的申请远程监护
	 * @param psm 分页
	 * @return
	 * @throws Throwable 
	 */
	public List<ICUVisitation> searchICUICUVisitList(PageSortModel psm, ICUMonitorCriteriaVO iCUMonitorCriteriaVO)throws Throwable;
	/**
	 * 查询远程探视审核未信息
	 * @param psm
	 * @param iCUMonitorCriteriaVO
	 * @return
	 * @throws Throwable
	 */
	public List<ICUVisitation> searchApplyICUVisitList(PageSortModel psm,ICUMonitorCriteriaVO iCUMonitorCriteriaVO) throws Throwable;
}
