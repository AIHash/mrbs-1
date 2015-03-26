package com.wafersystems.mrbs.service.impl.meeting;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.wafersystems.mrbs.dao.meeting.MeetingMemberDao;
import com.wafersystems.mrbs.logmng.ann.MrbsLog;
import com.wafersystems.mrbs.service.meeting.MeetingMemberService;
import com.wafersystems.mrbs.vo.meeting.MeetingMember;
import com.wafersystems.mrbs.web.report.vo.StatsMeetingMember;

@Service
public class MeetingMemberServiceImpl implements MeetingMemberService {

	private MeetingMemberDao meetingMemberDao;

	@Override
	@MrbsLog(desc="group.application_saveMeetingMember")
	public void saveMeetingMember(MeetingMember meetingMember) {
		meetingMemberDao.save(meetingMember);
	}

	@Override
	@MrbsLog(desc="group.application_updateMeetingMember")
	public void updateMeetingMember(MeetingMember meetingMember) {
		meetingMemberDao.update(meetingMember);
	}

	@Override
	@MrbsLog(desc="group.application_delMeetingMember")
	public void delMeetingMember(MeetingMember meetingMember) {
		meetingMemberDao.delete(meetingMember);

	}
	
	/**
	 * 根据meetingId和userId修改MeetingMember的attendNo
	 * @param array
	 */
	@Override
	@MrbsLog(desc="group.meetingMember_update")
	public void updateMeetingMember(Object[] array,Short localNumber){
		String  hql= "from MeetingMember m where m.meetingId.id = ?  and m.member.userId = ? ";
		List<MeetingMember> list= meetingMemberDao.findTbyHql(hql, array);
		MeetingMember meetingMember = null;
		if(list.size() > 0){
			meetingMember = list.get(0);
			meetingMember.setAttendNo(localNumber);
			this.updateMeetingMember(meetingMember);
		}
	}
	
	/**
	 * 根据参会人类型统计人数/次数
	 * @param startDate 讲座/会诊开始时间
	 * @param endDate 讲座/会诊结束时间
	 * @param meetingKind 代表申请的会诊类型（远程会诊为1；视频讲座为2）
	 * @param memberType 参会人类型
	 * @return
	 */
	public List<StatsMeetingMember> statsMeetingMemberByMemberType(String startDate, String endDate,Short meetingKind,Short meetingType){
		return meetingMemberDao.statsMeetingMemberByMemberType(startDate, endDate, meetingKind,meetingType);
	}
	
	/**
	 * 统计次数
	 * @param startDate 讲座/会诊开始时间
	 * @param endDate 讲座/会诊结束时间
	 * @param meetingKind //代表申请的会诊类型（远程会诊为1；视频讲座为2）
	 * @return
	 */
	public List<StatsMeetingMember> statsMeetingMemberByFrequency(String startDate, String endDate,Short meetingKind){
		return meetingMemberDao.statsMeetingMemberByFrequency(startDate, endDate, meetingKind);
	}
	
	
	/**
	 * 统计人数
	 * @param startDate 讲座/会诊开始时间
	 * @param endDate 讲座/会诊结束时间
	 * @param meetingKind //代表申请的会诊类型（远程会诊为1；视频讲座为2）
	 * @return
	 */
	public List<StatsMeetingMember> statsMeetingMemberByPeopleCount(String startDate, String endDate,Short meetingKind){
		return meetingMemberDao.statsMeetingMemberByPeopleCount(startDate, endDate, meetingKind);
	}

	/**
	 * 根据会议id和受邀人Id查询参会人
	 * @param meetingId 会议id
	 * @param userId 受邀人Id
	 * @return
	 */
	public List<MeetingMember> getMembersByMeetingIdAndUserId(final Integer meetingId,final String userId){
		return meetingMemberDao.getMembersByMeetingIdAndUserId(meetingId, userId);
	}
	
	public List<MeetingMember> getMembersByMeetingId(Integer meetingId){
		return meetingMemberDao.getMembersByMeetingId(meetingId);
	}
	
	@Override
	public MeetingMember getMeetingMemberById(int id){
		return meetingMemberDao.get(id);
	}

	public List<MeetingMember> getMeetingMemberByDuration(Date startDate, Date endDate){
		return meetingMemberDao.getMeetingMemberByDuration(startDate, endDate);
	}

	@Resource(type = MeetingMemberDao.class)
	public void setMeetingMemberDao(MeetingMemberDao meetingMember) {
		this.meetingMemberDao = meetingMember;
	}

}