package com.wafersystems.mrbs.web.meeting;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.wafersystems.mrbs.GlobalConstent;
import com.wafersystems.mrbs.dao.base.PageSortModel;
import com.wafersystems.mrbs.exception.BaseException;
import com.wafersystems.mrbs.exception.CreateFailedException;
import com.wafersystems.mrbs.exception.InfoExistException;
import com.wafersystems.mrbs.exception.UpdateFailedException;
import com.wafersystems.mrbs.listener.BaseDataServiceListener;
import com.wafersystems.mrbs.service.meeting.MeetingRoomService;
import com.wafersystems.mrbs.tag.MessageTag;
import com.wafersystems.mrbs.vo.meeting.MeetingRoom;

@Controller
@RequestMapping(value="/meeting/room")
public class MeetingRoomController {

	private static final Logger logger = LoggerFactory.getLogger(MeetingRoomController.class);


	private MeetingRoomService meetingRoomService;
	private BaseDataServiceListener serviceListener;

	
	@RequestMapping(value="/list")
	public String list(HttpSession session, HttpServletRequest request, Model model) {
		List<MeetingRoom> datas;
		PageSortModel psm = new PageSortModel(request); //eXtremeTable
		datas = meetingRoomService.getMeetingRoom(psm);
		session.setAttribute(GlobalConstent.REPORT_DATA, datas);
		return "room/list";
	}
	
	@RequestMapping(value="/add", method=RequestMethod.GET)
	public String add(Model model) {
		return "room/add";
	}
	
	@RequestMapping(value="/create", method=RequestMethod.POST)
	public String create(String name, String sn, String purpose,String size,
			Short confirm, Short state,String remark,String icuDeviceNO,Model model,
			HttpSession session, HttpServletRequest request) {
		try{
			MeetingRoom meetingroom = new MeetingRoom();
			meetingroom.setName(name);
			meetingroom.setNeedConfirm(confirm == 1);
			meetingroom.setPurpose(purpose);
			meetingroom.setRemark(remark);
			meetingroom.setSize(size);
			meetingroom.setSn(sn);
			meetingroom.setState(state);
			meetingroom.setMark((short)0);
			meetingroom.setIcuDeviceNO(icuDeviceNO);
			meetingRoomService.saveMeetingRoom(meetingroom);
			serviceListener.loadPara();
			logger.debug("会议室添加成功， SN=" + sn);
			return list(session, request ,model);
		}catch(BaseException e){
			if(e instanceof InfoExistException){
				session.setAttribute("message", MessageTag.getMessage("comm.infoexistexception","admin.meetingroom"));
			}else if(e instanceof CreateFailedException){
				session.setAttribute("message", MessageTag.getMessage("comm.failedexception","admin.meetingroom_create"));
			}else{
				session.setAttribute("message", MessageTag.getMessage("comm.operation_result_failure"));
			}
			return "redirect:/message.jsp";
		}
	}
	
	@RequestMapping(value="/edit/{roomId}", method=RequestMethod.GET)
	public String edit(@PathVariable Integer roomId, Model model) {
		MeetingRoom meetingRoom = meetingRoomService.getMeetingRoomById(roomId);
		model.addAttribute("MeetingRoom", meetingRoom);
		return "room/edit";
	}
	
	@RequestMapping(value="/update/{roomId}", method=RequestMethod.POST)
	public String update(@PathVariable Integer roomId,
			@RequestParam String name,
			@RequestParam String sn, @RequestParam String purpose,
			@RequestParam String size,@RequestParam Short confirm,
			@RequestParam Short state,@RequestParam String remark, 
			@RequestParam String icuDeviceNO,
			Model model,
			HttpSession session, HttpServletRequest request) {
		try {
			MeetingRoom meetingRoom = meetingRoomService.getMeetingRoomById(roomId);
			if(meetingRoom != null){
				meetingRoom.setName(name);
				meetingRoom.setNeedConfirm(confirm == 1);
				meetingRoom.setPurpose(purpose);
				meetingRoom.setRemark(remark);
				meetingRoom.setSize(size);
				meetingRoom.setSn(sn);
				meetingRoom.setState(state);
				meetingRoom.setIcuDeviceNO(icuDeviceNO);
				meetingRoomService.updateMeetingRoom(meetingRoom);
			}
			serviceListener.loadPara();
			return list(session, request, model);
		}catch(BaseException e){
			if(e instanceof InfoExistException){
				session.setAttribute("message", MessageTag.getMessage("comm.infoexistexception","admin.meetingroom"));
			}else if(e instanceof UpdateFailedException){
				session.setAttribute("message", MessageTag.getMessage("comm.failedexception","admin.meetingroom_update"));
			}else{
				session.setAttribute("message", MessageTag.getMessage("comm.operation_result_failure"));
			}
			return "redirect:/message.jsp";
		}
	}
	
	@RequestMapping(value="/delete/{roomId}", method=RequestMethod.GET)
	public String delete(@PathVariable Integer roomId,HttpServletResponse response) throws IOException {
		try{
			MeetingRoom meetingRoom = meetingRoomService.getMeetingRoomById(roomId);
			if(meetingRoom != null){
				meetingRoomService.delMeetingRoom(meetingRoom);
			}
			serviceListener.loadPara();
			response.getWriter().write("succ");
		}catch (Throwable e){
			response.getWriter().write("fail");
		}
		return null;
	}

	@Resource(type = MeetingRoomService.class)
	public void setMeetingRoomService(MeetingRoomService meetingRoomService) {
		this.meetingRoomService = meetingRoomService;
	}

	@Resource(name = "initListener")
	public void setServiceListener(BaseDataServiceListener serviceListener) {
		this.serviceListener = serviceListener;
	}
}

