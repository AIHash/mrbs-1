package com.wafersystems.mrbs.service.meeting;

import java.util.Date;
import java.util.List;

import com.wafersystems.mrbs.vo.meeting.MeetingMember;
import com.wafersystems.mrbs.web.report.vo.StatsMeetingMember;

public interface MeetingMemberService {

	public void saveMeetingMember(MeetingMember vo);

	public void updateMeetingMember(MeetingMember vo);

	public void delMeetingMember(MeetingMember vo);
	
	public MeetingMember getMeetingMemberById(int id);

	public List<MeetingMember> getMeetingMemberByDuration(Date startDate, Date endDate);
	/**
	 * 根据meetingId和userId修改MeetingMember的attendNo
	 * @param array
	 */
	public void updateMeetingMember(Object[] array,Short localNumber);
	
	/**
	 * 统计次数
	 * @param startDate 讲座/会诊开始时间
	 * @param endDate 讲座/会诊结束时间
	 * @param meetingKind //代表申请的会诊类型（远程会诊为1；视频讲座为2）
	 * @return
	 */
	public List<StatsMeetingMember> statsMeetingMemberByFrequency(String startDate, String endDate,Short meetingKind);
	
	/**
	 * 统计人数
	 * @param startDate 讲座/会诊开始时间
	 * @param endDate 讲座/会诊结束时间
	 * @param meetingKind //代表申请的会诊类型（远程会诊为1；视频讲座为2）
	 * @return
	 */
	public List<StatsMeetingMember> statsMeetingMemberByPeopleCount(String startDate, String endDate,Short meetingKind);

	/**
	 * 根据会议id查询参会人
	 * @param meetingId
	 * @return
	 */
	public List<MeetingMember> getMembersByMeetingId(Integer meetingId);
	
	/**
	 * 根据会议id和受邀人Id查询参会人
	 * @param meetingId 会议id
	 * @param userId 受邀人Id
	 * @return
	 */
	public List<MeetingMember> getMembersByMeetingIdAndUserId(final Integer meetingId,final String userId);
	/**
	 * 根据参会人类型统计人数/次数
	 * @param startDate 讲座/会诊开始时间
	 * @param endDate 讲座/会诊结束时间
	 * @param meetingKind 代表申请的会诊类型（远程会诊为1；视频讲座为2）
	 * @param memberType 参会人类型
	 * @return
	 */
	public List<StatsMeetingMember> statsMeetingMemberByMemberType(String startDate, String endDate,Short meetingKind,Short meetingType);
}
