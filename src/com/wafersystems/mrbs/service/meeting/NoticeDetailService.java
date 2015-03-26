package com.wafersystems.mrbs.service.meeting;

import java.util.List;

import com.wafersystems.mrbs.vo.meeting.NoticeDetail;

public interface NoticeDetailService {
	
	/**
	 * 保存通知明细
	 * @param id,sendTime,String userName,userType
	 * @return
	 */
	public void saveNoticeDetail(Integer id,String sendTime,String expertsName,String communitiesName,String InnaiName);
	/**
	 * 更新通知明细
	 * @param vo
	 * @return
	 */
	public void updateNoticeDetail(NoticeDetail vo);
	/**
	 * 删除通知明细
	 * @param vo
	 * @return
	 */
	public void deleteNoticeDetail(NoticeDetail vo);
	/**
	 * 根据id查询通知明细
	 * @param id
	 * @return
	 */
	public NoticeDetail getNoticeDetailById(int id);
	
	/**
	 * 根据病历讨论id查询通知明细
	 * @param meetingApplicationId
	 * @return
	 */
	public List<NoticeDetail> getNoticeDetailByMeetingApplicationId(Integer meetingApplicationId);


}
