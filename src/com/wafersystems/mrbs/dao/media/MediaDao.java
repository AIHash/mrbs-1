package com.wafersystems.mrbs.dao.media;

import com.wafersystems.mrbs.dao.base.GenericDao;
import com.wafersystems.mrbs.dao.base.PageData;
import com.wafersystems.mrbs.dao.base.PageSortModel;
import com.wafersystems.mrbs.vo.meeting.Media;
import com.wafersystems.mrbs.vo.user.UserInfo;

public interface MediaDao extends GenericDao<Media>{
	public PageData<Media> getMediaList(PageSortModel psm,Media media,UserInfo user) throws Throwable;
}
