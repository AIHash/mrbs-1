package com.wafersystems.mrbs.service.impl.meeting;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.wafersystems.mrbs.GlobalConstent;
import com.wafersystems.mrbs.dao.icu.ICUMonitorDao;
import com.wafersystems.mrbs.dao.meeting.MeetingApplicationDao;
import com.wafersystems.mrbs.dao.meeting.NoticeDetailDao;
import com.wafersystems.mrbs.dao.user.UserTypeDao;
import com.wafersystems.mrbs.service.meeting.NoticeDetailService;
import com.wafersystems.mrbs.vo.meeting.ICUMonitor;
import com.wafersystems.mrbs.vo.meeting.MeetingApplication;
import com.wafersystems.mrbs.vo.meeting.NoticeDetail;

@Service
public class NoticeDetailServiceImpl implements NoticeDetailService {
	private MeetingApplicationDao meetingApplicationDao;
	private UserTypeDao userTypeDao;
	private NoticeDetailDao noticeDetailDao;
	
	/**
	 * 保存通知明细
	 * @param id,sendTime,String userName,userType
	 * @return
	 */
	@Override
	public void saveNoticeDetail(Integer meetingApplicationId,String sendTime,String expertsName,String communitiesName,String InnaiName){
		MeetingApplication meetingApplication=meetingApplicationDao.get(meetingApplicationId);
		NoticeDetail noticeDetail;		
		if(StringUtils.isNotBlank(expertsName)){
			noticeDetail=new NoticeDetail();
			noticeDetail.setMeetingApplicationId(meetingApplication);
			noticeDetail.setSendTime(sendTime);
			noticeDetail.setUserName(expertsName);
			noticeDetail.setUserType(userTypeDao.get(GlobalConstent.USER_TYPE_PROFESSIONAL));
			noticeDetailDao.save(noticeDetail);
		}
		if(StringUtils.isNotBlank(communitiesName)){
			noticeDetail=new NoticeDetail();
			noticeDetail.setMeetingApplicationId(meetingApplication);
			noticeDetail.setSendTime(sendTime);
			noticeDetail.setUserName(communitiesName);
			noticeDetail.setUserType(userTypeDao.get(GlobalConstent.USER_TYPE_UNION));
			noticeDetailDao.save(noticeDetail);
		}
		if(StringUtils.isNotBlank(InnaiName)){
			noticeDetail=new NoticeDetail();
			noticeDetail.setMeetingApplicationId(meetingApplication);
			noticeDetail.setSendTime(sendTime);
			noticeDetail.setUserName(InnaiName);
			noticeDetail.setUserType(userTypeDao.get(GlobalConstent.USER_TYPE_PROFESSIONAL));
			noticeDetailDao.save(noticeDetail);
		}		
	}

	/**
	 * 根据病历讨论id查询通知明细
	 * @param meetingApplicationId
	 * @return
	 */
	@Override
	public List<NoticeDetail> getNoticeDetailByMeetingApplicationId(Integer meetingApplicationId){
		return noticeDetailDao.getNoticeDetailByMeetingApplicationId(meetingApplicationId);
	}
    
	/**
	 * 更新通知明细
	 * @param vo
	 * @return
	 */
	@Override
	public void updateNoticeDetail(NoticeDetail vo){
		noticeDetailDao.update(vo);
	}
	
	/**
	 * 删除通知明细
	 * @param vo
	 * @return
	 */
	@Override
	public void deleteNoticeDetail(NoticeDetail vo){
		noticeDetailDao.delete(vo);
	}

	/**
	 * 根据id查询通知明细
	 * @param id
	 * @return
	 */
	@Override
	public NoticeDetail getNoticeDetailById(int id){
		return noticeDetailDao.get(id);
	}
	
	
	@Resource(type = MeetingApplicationDao.class)
	public void setMeetingApplicationDao(MeetingApplicationDao meetingApplicationDao) {
		this.meetingApplicationDao = meetingApplicationDao;
	}
	
	@Resource(type = UserTypeDao.class)
	public void setUserTypeDao(UserTypeDao userTypeDao) {
		this.userTypeDao = userTypeDao;
	}
	
	@Resource(type = NoticeDetailDao.class)
	public void setNoticeDetailDao(NoticeDetailDao noticeDetailDao) {
		this.noticeDetailDao = noticeDetailDao;
	}

}
