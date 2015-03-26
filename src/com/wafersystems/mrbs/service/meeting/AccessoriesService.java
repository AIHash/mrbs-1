package com.wafersystems.mrbs.service.meeting;

import java.util.List;

import com.wafersystems.mrbs.dao.base.PageData;
import com.wafersystems.mrbs.dao.base.PageSortModel;
import com.wafersystems.mrbs.vo.meeting.Accessories;
import com.wafersystems.mrbs.vo.meeting.Meeting;
import com.wafersystems.mrbs.vo.user.UserInfo;
import com.wafersystems.mrbs.web.criteriavo.ManagementOfAccessoriesVO;

public interface AccessoriesService {

	public void saveAccessories(Accessories vo);

	public void saveBatch(Accessories[] vos);

	public void updateAccessories(Accessories vo);

	public void delAccessories(Accessories vo);

	public Accessories getAccessoriesById(int id);

	public void fulsh();

	public void clear();

	public Integer deleteAccessoriesByAppId(Integer appId);
	/**
	 * 删除视频申请的附件
	 */
	public Integer deleteAccessoriesByVoidId(Integer voidId);
	
	public PageData<Accessories> getAllAccessories(PageSortModel psm,ManagementOfAccessoriesVO managementOfAccessoriesVo,UserInfo userinfo) throws Throwable;//获得所有的附件
}
