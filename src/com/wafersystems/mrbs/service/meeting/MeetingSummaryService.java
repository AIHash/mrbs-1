package com.wafersystems.mrbs.service.meeting;

import java.util.List;

import com.wafersystems.mrbs.vo.meeting.MeetingSummary;

public interface MeetingSummaryService {

	public void saveSummary(MeetingSummary vo);

	public void updateSummary(MeetingSummary vo);

	public void delSummary(MeetingSummary vo);
	
	public MeetingSummary getSummaryById(int id);

	/**
	 * 会诊管理员代替专家和申请人保存总结数据
	 * @param meetingId
	 * @param summarys
	 * @throws Exception
	 */
	public void saveSummarysForManager(Integer meetingId,List<MeetingSummary> summarys) throws Exception;

	/**
	 * 非会诊管理员保存总结数据
	 * @param meetingId
	 * @param summarys
	 * @throws Exception
	 */
	public void saveSummaryForUser(Integer meetingId, String userId, MeetingSummary summary) throws Exception;
}
