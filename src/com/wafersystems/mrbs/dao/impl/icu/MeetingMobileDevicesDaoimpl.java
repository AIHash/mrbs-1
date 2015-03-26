package com.wafersystems.mrbs.dao.impl.icu;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.wafersystems.mrbs.dao.base.GenericDaoImpl;
import com.wafersystems.mrbs.dao.icu.MeetingMobileDevicesDao;
import com.wafersystems.mrbs.vo.meeting.Meeting;
import com.wafersystems.mrbs.vo.meeting.MeetingMobileDevices;
@Repository
public class MeetingMobileDevicesDaoimpl extends GenericDaoImpl<MeetingMobileDevices> implements MeetingMobileDevicesDao {

	@Override
	public MeetingMobileDevices getMobileDevicesByMeetingId(Integer meetingId) {
		String hql = "from MeetingMobileDevices m where m.meetingId = "+meetingId;
		List<MeetingMobileDevices> obj = this.findTbyHql(hql);

		return obj.size() == 0 ? null : obj.get(0);
	}

}
