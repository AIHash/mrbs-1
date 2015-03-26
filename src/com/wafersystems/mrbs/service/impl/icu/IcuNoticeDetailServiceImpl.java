package com.wafersystems.mrbs.service.impl.icu;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.wafersystems.mrbs.GlobalConstent;
import com.wafersystems.mrbs.dao.icu.ICUMonitorDao;
import com.wafersystems.mrbs.dao.icu.IcuNoticeDetailDao;
import com.wafersystems.mrbs.dao.user.UserTypeDao;
import com.wafersystems.mrbs.service.icu.IcuNoticeDetailService;
import com.wafersystems.mrbs.vo.meeting.ICUMonitor;
import com.wafersystems.mrbs.vo.meeting.IcuNoticeDetail;
@Service
public class IcuNoticeDetailServiceImpl implements IcuNoticeDetailService{

	private ICUMonitorDao iCUMonitorDao;
	private UserTypeDao userTypeDao;
	private IcuNoticeDetailDao icuNoticeDetailDao;
	
	/**
	 * icu重症监护通知信息
	 * @param icuId
	 * @param sendTime
	 * @param expertsName
	 * @param communitiesName
	 * @param InnaiName
	 */
	public void saveIcuMonitorNoticeDetail(Integer icuId,String sendTime,String expertsName,String communitiesName,String InnaiName){
		ICUMonitor iCUMonitor= iCUMonitorDao.get(icuId);
		IcuNoticeDetail noticeDetail;		
		if(StringUtils.isNotBlank(expertsName)){
			noticeDetail=new IcuNoticeDetail();
			noticeDetail.setiCUMonitorId(iCUMonitor);
			noticeDetail.setSendTime(sendTime);
			noticeDetail.setUserName(expertsName);
			noticeDetail.setUserType(userTypeDao.get(GlobalConstent.USER_TYPE_PROFESSIONAL));
			icuNoticeDetailDao.save(noticeDetail);
		}
		if(StringUtils.isNotBlank(communitiesName)){
			noticeDetail=new IcuNoticeDetail();
			noticeDetail.setiCUMonitorId(iCUMonitor);
			noticeDetail.setSendTime(sendTime);
			noticeDetail.setUserName(communitiesName);
			noticeDetail.setUserType(userTypeDao.get(GlobalConstent.USER_TYPE_UNION));
			icuNoticeDetailDao.save(noticeDetail);
		}
		if(StringUtils.isNotBlank(InnaiName)){
			noticeDetail=new IcuNoticeDetail();
			noticeDetail.setiCUMonitorId(iCUMonitor);
			noticeDetail.setSendTime(sendTime);
			noticeDetail.setUserName(InnaiName);
			noticeDetail.setUserType(userTypeDao.get(GlobalConstent.USER_TYPE_PROFESSIONAL));
			icuNoticeDetailDao.save(noticeDetail);
		}		
	}
	/**
	 * add by wangzhenglin
	 */
	@Override
	public List<IcuNoticeDetail> getNoticeDetailByicuMonitorId(Integer icuMonitorId){
		return icuNoticeDetailDao.getNoticeDetailByIcuMonitorId(icuMonitorId);
	}

	//===============================get======set=========================================
	@Resource(type = ICUMonitorDao.class)
	public void setiCUMonitorDao(ICUMonitorDao iCUMonitorDao) {
		this.iCUMonitorDao = iCUMonitorDao;
	}
	@Resource(type = UserTypeDao.class)
	public void setUserTypeDao(UserTypeDao userTypeDao) {
		this.userTypeDao = userTypeDao;
	}
	@Resource(type = IcuNoticeDetailDao.class)
	public void setIcuNoticeDetailDao(IcuNoticeDetailDao icuNoticeDetailDao) {
		this.icuNoticeDetailDao = icuNoticeDetailDao;
	}
	
}
