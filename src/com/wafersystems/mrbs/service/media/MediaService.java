package com.wafersystems.mrbs.service.media;

import com.wafersystems.mrbs.dao.base.PageData;
import com.wafersystems.mrbs.dao.base.PageSortModel;
import com.wafersystems.mrbs.vo.meeting.Media;
import com.wafersystems.mrbs.vo.meeting.MediaRole;
import com.wafersystems.mrbs.vo.user.UserInfo;

public interface MediaService {

	public PageData<Media> getMediaList(PageSortModel psm,Media media,UserInfo user) throws Throwable;
	
	public void delMedia(int mediaId) throws Throwable;
	/**
	 * add by wangzhenglin 添加共享资源
	 */
	public void saveMedia(Media media)throws Throwable;
	/**
	 * add by wangzhenglin 添加共享资源对应权限
	 */
	public void saveMediaRole(MediaRole mediaRole)throws Throwable;
}
