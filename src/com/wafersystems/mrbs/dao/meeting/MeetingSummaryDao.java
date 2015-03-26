package com.wafersystems.mrbs.dao.meeting;

import com.wafersystems.mrbs.dao.base.GenericDao;
import com.wafersystems.mrbs.vo.meeting.MeetingSummary;

public interface MeetingSummaryDao extends GenericDao<MeetingSummary>{

	/**
	 * 会诊管理员保存数据前删除之前已经保存的本次会诊的总结
	 * @param meetingId
	 * @return
	 */
	public Integer delSummaryForManagerSaveData(int meetingId);

	/**
	 * 非会诊管理员保存数据前，删除已经保存的自己的总结
	 * @param meetingId
	 * @return
	 */
	public Integer delSummaryForUserSaveData(int meetingId, final String userId);
}
