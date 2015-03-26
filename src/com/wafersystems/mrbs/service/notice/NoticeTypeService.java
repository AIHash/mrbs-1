package com.wafersystems.mrbs.service.notice;

import com.wafersystems.mrbs.vo.notice.NoticeType;

public interface NoticeTypeService {

	public void saveNoticeType(NoticeType noticeType);

	public void updateNoticeType(NoticeType noticeType);

	public void delNoticeType(NoticeType noticeType);
	
	public NoticeType getNoticeTypeById(Short id);

}
