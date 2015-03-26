package com.wafersystems.mrbs.service.impl.meeting;

import java.util.LinkedHashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.wafersystems.mrbs.dao.base.PageData;
import com.wafersystems.mrbs.dao.base.PageSortModel;
import com.wafersystems.mrbs.dao.meeting.MeetingApplicationDao;
import com.wafersystems.mrbs.dao.meeting.VideoMeetingAppDao;
import com.wafersystems.mrbs.logmng.ann.MrbsLog;
import com.wafersystems.mrbs.service.meeting.MeetingApplicationService;
import com.wafersystems.mrbs.vo.meeting.MeetingApplication;
import com.wafersystems.mrbs.vo.meeting.VideoMeetingApp;
import com.wafersystems.mrbs.vo.user.UserInfo;
import com.wafersystems.util.StrUtil;

@Service
public class MeetingApplicationServiceImpl implements MeetingApplicationService {

	private MeetingApplicationDao dao;
	
	private VideoMeetingAppDao vmdao;

	@Override
	@MrbsLog(desc="group.meetingApplication_create")
	public void saveMeetingApplication(MeetingApplication vo) {
		dao.save(vo);
	}

	@Override
	@MrbsLog(desc="group.meetingApplication_update")
	public void updateMeetingApplication(MeetingApplication vo) {
		dao.update(vo);
	}

	@Override
	@MrbsLog(desc="group.meetingApplication_delete")
	public void delMeetingApplication(MeetingApplication vo) {
		dao.delete(vo);
	}
	
	@Override
	public MeetingApplication getMeetingApplicationById(int id){
		return dao.get(id);
	}

	@Resource(type = MeetingApplicationDao.class)
	public void setMeetingApplicationDao(MeetingApplicationDao dao) {
		this.dao = dao;
	}
	
	@Resource(type = VideoMeetingAppDao.class)
	public void setVideoMeetingAppDao(VideoMeetingAppDao vmdao) {
		this.vmdao = vmdao;
	}

	@Override
	public List<MeetingApplication> getApplication(UserInfo vo) 
	{
		LinkedHashMap<String,Object> linkmap=new LinkedHashMap<String,Object>();
		String hql=" and requester='"+vo.getUserId()+"'";		
		linkmap.put("expectedTime", "desc");
		linkmap.put("applicationTime", "desc");
		List<MeetingApplication> results=dao.limitFindByHql(0, 7, hql, null, linkmap);
		return results;
	}

	@Override
	public PageData<MeetingApplication> getApplication(UserInfo vo,
			HttpServletRequest request) 
	{		
		 PageSortModel psm = new PageSortModel(request);
		 String hql=" and o.requester='"+vo.getUserId()+"'";
		 
		 String applicationid=request.getParameter("applicationid");
		 if(!StrUtil.isEmptyStr(applicationid))
		 {
			 hql+=" and id="+applicationid;
		 }
		 
		 String datetime=request.getParameter("datetime");
		 if(!StrUtil.isEmptyStr(datetime))
		 {
			 hql+=" and startTime>='"+datetime+" 00:00:00'";
		 }
		 String enddatetime=request.getParameter("enddatetime");
		 if(!StrUtil.isEmptyStr(enddatetime))
		 {
			 hql+=" and startTime<='"+enddatetime+" 23:59:59'";
		 }
		 String meetingname=request.getParameter("meetingname");
		 if(!StrUtil.isEmptyStr(meetingname))
		 {
			 hql+=" and title='"+meetingname+"'";
		 }
		 String meetingtype=request.getParameter("meetingtype");
		 if(!StrUtil.isEmptyStr(meetingtype))
		 {
			 hql+=" and meetingType="+meetingtype;
		 }
		 String dept=request.getParameter("dept");
		 if(!StrUtil.isEmptyStr(dept))
		 {
			 hql+=" and o.department.deptcode like '"+dept+"%'";
		 }
		 String meetingleveld=request.getParameter("meetingleveld");
		 if(!StrUtil.isEmptyStr(meetingleveld))
		 {
			 hql+=" and o.level="+meetingleveld;
		 }
		 String state=request.getParameter("state");
		 if(!StrUtil.isEmptyStr(state))
		 {
			 hql+=" and state="+state;
		 }
		 
		 LinkedHashMap<String,Object> linkmap=new LinkedHashMap<String,Object>();	
		linkmap.put("applicationTime", "desc");
		 PageData<MeetingApplication> date=dao.getScrollData(psm, hql, null, linkmap);
		//List<Object> results=dao.findTbyHql(hql);				
		return date;
	}

	@Override
	public VideoMeetingApp getVideoMeetingApplicationById(int id) {
		return vmdao.get(id);
	}

	public Integer delDeptsByAppId(Integer appId){
		return dao.delDeptsByAppId(appId);
	}

}