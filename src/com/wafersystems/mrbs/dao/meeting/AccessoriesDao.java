package com.wafersystems.mrbs.dao.meeting;

import java.util.List;

import com.wafersystems.mrbs.dao.base.GenericDao;
import com.wafersystems.mrbs.dao.base.PageData;
import com.wafersystems.mrbs.dao.base.PageSortModel;
import com.wafersystems.mrbs.vo.meeting.Accessories;
import com.wafersystems.mrbs.vo.meeting.Meeting;
import com.wafersystems.mrbs.vo.user.UserInfo;
import com.wafersystems.mrbs.web.criteriavo.ManagementOfAccessoriesVO;

public interface AccessoriesDao extends GenericDao<Accessories>{
	
	public void saveBatchData(Accessories[] vos);

	/**
	 * 删除远程会诊关联的附件，但是不删除文件
	 * @param appId
	 * @return
	 */
	Integer deleteAccessoriesByAppId(Integer appId);

	/**
	 * 删除远程会诊关联的附件，并删除文件
	 * @param appId
	 * @return
	 */
	Integer deleteAccessoriesAndFileByAppId(String accPath, Integer appId);

	/**
	 * 删除视频申请的附件，但是不删除文件
	 */
	public Integer deleteAccessoriesByVoidId(Integer voidId);

	/**
	 * 删除视频申请的附件，并删除文件
	 */
	public Integer deleteAccessoriesAndFileByVideoId(String accPath, Integer voidId);
	
	/**
	 * 获得库中所有的附件
	 */
	public PageData<Accessories> getAllAccessories(PageSortModel psm,ManagementOfAccessoriesVO managementOfAccessoriesVo,UserInfo userinfo) throws Throwable;
	/**
	 * 删除icu重症监护病例对应的文件
	 */
	public Integer deleteAccessoriesByMonitorId(final Integer monitorId);
	/**
	 * add by wangzhenglin  删除重症监护信息对应的附件信息
	 * @param accPath
	 * @param icuMonitorId
	 * @return
	 */
	public Integer deleteAccessoriesAndFileByIcuMonitor(final String accPath,final Integer icuMonitorId);
}
