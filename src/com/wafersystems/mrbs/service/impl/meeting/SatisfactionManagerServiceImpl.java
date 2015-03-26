package com.wafersystems.mrbs.service.impl.meeting;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.wafersystems.mrbs.dao.meeting.SatisfactionManagerDao;
import com.wafersystems.mrbs.logmng.ann.MrbsLog;
import com.wafersystems.mrbs.service.meeting.SatisfactionManagerService;
import com.wafersystems.mrbs.vo.meeting.Meeting;
import com.wafersystems.mrbs.vo.meeting.SatisfactionManager;
import com.wafersystems.mrbs.vo.user.UserInfo;
import com.wafersystems.util.DateUtil;

@Service
public class SatisfactionManagerServiceImpl implements SatisfactionManagerService {

	private SatisfactionManagerDao dao;

	@Override
	@MrbsLog(desc="SatisfactionManager_create")
	public void saveSatisfactionManager(SatisfactionManager vo) {
		dao.save(vo);
	}
    
	@Override
	@MrbsLog(desc="SatisfactionManager_update")
	public void updateSatisfactionManager(SatisfactionManager vo) {
		dao.update(vo);
	}

	@Override
	@MrbsLog(desc="SatisfactionManager_delete")
	public void delSatisfactionManager(SatisfactionManager vo) {
		
	}
	
	@Override
	public SatisfactionManager getSatisfactionManagerById(int id){
		return dao.get(id);
	}

	@Override
	public void saveMeetingStartRealTime(Meeting meeting) {
		dao.save(meeting);
	}
	@Override
	@MrbsLog(desc="SatisfactionManager_createOrUpdate")
	public void saveOrUpdateSatisfactionManager(Object[] array, int score,String meetingTime,Meeting meeting,String realStartTime,String realEndTime){
		String  hql= "from SatisfactionManager s where s.meeting.id = ?  and s.user.userId = ? ";
		List<SatisfactionManager> list= dao.findTbyHql(hql, array);
		SatisfactionManager SatisfactionManager = null;
		if(list.size() > 0){
			SatisfactionManager = list.get(0);
			SatisfactionManager.setScore(score);
			SatisfactionManager.setMeetingTime(meetingTime);
			SatisfactionManager.getMeeting().setRealStartTime(DateUtil.getDateTimeByHours(realStartTime));
			SatisfactionManager.getMeeting().setRealEndTime(DateUtil.getDateTimeByHours(realEndTime));
			SatisfactionManager.setCreateTime(new Date());
		}else{
			SatisfactionManager = new SatisfactionManager();

			//Meeting meeting = new Meeting();
			//meeting.setId((Integer)array[0]);
			meeting.setRealStartTime(DateUtil.getDateTimeByHours(realStartTime));
			meeting.setRealEndTime(DateUtil.getDateTimeByHours(realEndTime));
			this.saveMeetingStartRealTime(meeting);
			SatisfactionManager.setMeeting(meeting);


			UserInfo user = new UserInfo();
			user.setUserId((String)array[1]);
			SatisfactionManager.setUser(user);
			
			SatisfactionManager.setMeetingTime(meetingTime);

			SatisfactionManager.setScore(score);
			
			SatisfactionManager.setCreateTime( new Date());

			this.saveSatisfactionManager(SatisfactionManager);
		}
	}

	@Resource(type = SatisfactionManagerDao.class)
	public void setSatisfactionManagerDao(SatisfactionManagerDao dao) {
		this.dao = dao;
	}

	@Override
	public SatisfactionManager getSatisfactionManager(Object[] array) {
		String  hql= "from SatisfactionManager s where s.meeting.id = ?  and s.user.userId = ? ";
		List<SatisfactionManager> list= dao.findTbyHql(hql, array);
		SatisfactionManager SatisfactionManager = null;
		if(list.size() > 0)
			SatisfactionManager = list.get(0);
			return SatisfactionManager;
	}


}