package com.wafersystems.mrbs.dao.icu;

import java.util.List;

import com.wafersystems.mrbs.dao.base.GenericDao;
import com.wafersystems.mrbs.vo.meeting.IcuNoticeDetail;

public interface IcuNoticeDetailDao extends  GenericDao<IcuNoticeDetail>{

	/**
	 * add by wangzhenglin 
	 * 根据ICUID查询通知明细
	 * @param meetingApplicationId
	 * @return
	 */
	public List<IcuNoticeDetail> getNoticeDetailByIcuMonitorId(final Integer icuId);
	/**
	 * 根据病历讨论id删除通知明细
	 * @param meetingApplicationId
	 * @return size
	 */
	public Integer deleteIcuNoticeDetailByIcuId(Integer icuId);
	
}
