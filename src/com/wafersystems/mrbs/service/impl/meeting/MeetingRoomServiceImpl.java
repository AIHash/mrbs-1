package com.wafersystems.mrbs.service.impl.meeting;

import java.util.LinkedHashMap;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.wafersystems.mrbs.dao.base.PageSortModel;
import com.wafersystems.mrbs.dao.meeting.MeetingRoomDao;
import com.wafersystems.mrbs.exception.BaseException;
import com.wafersystems.mrbs.exception.CreateFailedException;
import com.wafersystems.mrbs.exception.DeleteFailedException;
import com.wafersystems.mrbs.exception.InfoExistException;
import com.wafersystems.mrbs.logmng.ann.MrbsLog;
import com.wafersystems.mrbs.service.meeting.MeetingRoomService;
import com.wafersystems.mrbs.vo.meeting.MeetingRoom;

@Service
public class MeetingRoomServiceImpl implements MeetingRoomService {
	
	Logger logger = LoggerFactory.getLogger(MeetingRoomService.class);

	private MeetingRoomDao meetingRoomDao;

	@MrbsLog(desc="admin.meetingroom_create")
	@Override
	public void saveMeetingRoom(MeetingRoom meetingRoom) throws BaseException{
		try{
			MeetingRoom mr = getMeetingRoomBySn(meetingRoom.getSn());
			if(mr != null)
				throw new InfoExistException();
			meetingRoomDao.save(meetingRoom);
		}catch(InfoExistException e){
			throw e;
		}catch(Exception e){
			logger.error("添加会议室异常", e);
			throw new CreateFailedException();
		}
	}

	@MrbsLog(desc="admin.meetingroom_update")
	@Override
	public void updateMeetingRoom(MeetingRoom meetingRoom) throws BaseException{
		try{
			MeetingRoom mr = getMeetingRoomBySn(meetingRoom.getSn());
			if(mr != null && mr.getId().intValue() != meetingRoom.getId().intValue())
				throw new InfoExistException();
			meetingRoomDao.update(meetingRoom);
		}catch(InfoExistException e){
			throw e;
		}catch(Exception e){
			logger.error("更新会议室异常", e);
			throw new CreateFailedException();
		}
	}

	@MrbsLog(desc="admin.meetingroom_delete")
	@Override
	public void delMeetingRoom(MeetingRoom meetingRoom) throws BaseException{
		try{
			meetingRoomDao.delete(meetingRoom.getId());
		}catch(Exception e){
			logger.error("删除会议室异常", e);
			throw new DeleteFailedException();
		}
	}
	
	@Override
	public MeetingRoom getMeetingRoomBySn(String sn){
		String hql = "From MeetingRoom Where mark=0 and sn = '" + sn + "'";
		List<MeetingRoom> datas = meetingRoomDao.findTbyHql(hql);
		if(datas != null && datas.size() > 0)
			return datas.get(0);
		else return null;
	}
	
	@Override
	public MeetingRoom getMeetingRoomById(Integer id){
		return meetingRoomDao.get(id);
	}
	
	@Override
	public List<MeetingRoom> getMeetingRoom(PageSortModel psm){
		LinkedHashMap<String, Object> orderby = new LinkedHashMap<String, Object>();
		orderby.put("name", "asc");
		String wherejsql = " and mark = 0";
		return  meetingRoomDao.getScrollData(psm, wherejsql, null, orderby).getResultlist();
	}

	@Resource(type = MeetingRoomDao.class)
	public void setRoleDao(MeetingRoomDao meetingRoomDao) {
		this.meetingRoomDao = meetingRoomDao;
	}

}