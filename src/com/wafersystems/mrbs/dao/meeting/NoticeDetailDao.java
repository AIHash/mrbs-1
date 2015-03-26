package com.wafersystems.mrbs.dao.meeting;

import java.util.List;

import com.wafersystems.mrbs.dao.base.GenericDao;
import com.wafersystems.mrbs.vo.meeting.NoticeDetail;

public interface NoticeDetailDao extends GenericDao<NoticeDetail>{
	/**
	 * 根据病历讨论id查询通知明细
	 * @param meetingApplicationId
	 * @return
	 */
	public List<NoticeDetail> getNoticeDetailByMeetingApplicationId(Integer meetingApplicationId);
	/**
	 * 根据病历讨论id删除通知明细
	 * @param meetingApplicationId
	 * @return size
	 */
	public Integer deleteNoticeDetailByAppId(Integer meetingApplicationId);

}
