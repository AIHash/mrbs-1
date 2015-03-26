package com.wafersystems.mrbs.dao.icu;

import com.wafersystems.mrbs.dao.base.GenericDao;
import com.wafersystems.mrbs.vo.meeting.MeetingMobileDevices;

public interface MeetingMobileDevicesDao extends GenericDao<MeetingMobileDevices>{
   public MeetingMobileDevices getMobileDevicesByMeetingId(Integer meetingId);
}
