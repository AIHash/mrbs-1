package com.wafersystems.mrbs.service.impl.notice;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.wafersystems.mrbs.GlobalConstent;
import com.wafersystems.mrbs.dao.notice.UnifiedNoticeDao;
import com.wafersystems.mrbs.logmng.ann.MrbsLog;
import com.wafersystems.mrbs.service.notice.NoticeService;
import com.wafersystems.mrbs.vo.notice.UnifiedNotice;

@Service("noticeService")
public class NoticeServiceImpl implements NoticeService {

	private UnifiedNoticeDao unifiedNoticeDao;

	@Override
	@MrbsLog(desc="group.notice_saveNotice")
	public void saveNotice(UnifiedNotice notice) {
		unifiedNoticeDao.save(notice);
	}

	@Override
	@MrbsLog(desc="group.notice_updateNotice")
	public void updateNotice(UnifiedNotice notice) {
		unifiedNoticeDao.update(notice);
	}

	@Override
	@MrbsLog(desc="group.notice_delNotice")
	public void delNotice(UnifiedNotice notice) {
		unifiedNoticeDao.delete(notice);
	}

	@Override
	public List<UnifiedNotice> getNotSendNotice(short type) {
		return unifiedNoticeDao.findTbyHql("from UnifiedNotice n where n.type = " + type 
				+ " and n.state = " + GlobalConstent.NOTICE_NOT_SEND + " and (n.resendTimes < 3 or n.resendTimes is null)");
	}

	@Resource(type = UnifiedNoticeDao.class)
	public void setUnifiedNoticeDao(UnifiedNoticeDao unifiedNoticeDao) {
		this.unifiedNoticeDao = unifiedNoticeDao;
	}

}