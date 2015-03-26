package com.wafersystems.mrbs.service.icu;

import java.util.List;

import com.wafersystems.mrbs.vo.meeting.IcuNoticeDetail;

public interface IcuNoticeDetailService {

	/**
	 * icu重症监护通知信息
	 * @param icuId
	 * @param sendTime
	 * @param expertsName
	 * @param communitiesName
	 * @param InnaiName
	 */
	public void saveIcuMonitorNoticeDetail(Integer icuId,String sendTime,String expertsName,String communitiesName,String InnaiName);
	/**
	 * 通过icuid查询通知明细
	 *
	 * add by wangzhenglin
	 */
	public List<IcuNoticeDetail> getNoticeDetailByicuMonitorId(Integer icuMonitorId);
}
