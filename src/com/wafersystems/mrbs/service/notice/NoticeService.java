package com.wafersystems.mrbs.service.notice;

import java.util.List;

import com.wafersystems.mrbs.vo.notice.UnifiedNotice;


public interface NoticeService {

	public void saveNotice(UnifiedNotice notice);

	public void updateNotice(UnifiedNotice notice);

	public void delNotice(UnifiedNotice notice);

	public List<UnifiedNotice> getNotSendNotice(short type);

}
