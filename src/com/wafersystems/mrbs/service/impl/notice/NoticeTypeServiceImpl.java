package com.wafersystems.mrbs.service.impl.notice;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.wafersystems.mrbs.dao.notice.NoticeTypeDao;
import com.wafersystems.mrbs.logmng.ann.MrbsLog;
import com.wafersystems.mrbs.service.notice.NoticeTypeService;
import com.wafersystems.mrbs.vo.notice.NoticeType;

@Service
public class NoticeTypeServiceImpl implements NoticeTypeService {

	private NoticeTypeDao noticeTypeDao;

	@Override
	@MrbsLog(desc="group.noticeType_saveNoticeType")
	public void saveNoticeType(NoticeType noticeType) {
		noticeTypeDao.save(noticeType);
	}

	@Override
	@MrbsLog(desc="group.noticeType_updateNoticeType")
	public void updateNoticeType(NoticeType noticeType) {
		noticeTypeDao.update(noticeType);
	}

	@Override
	@MrbsLog(desc="group.noticeType_delNoticeType")
	public void delNoticeType(NoticeType noticeType) {
		noticeTypeDao.delete(noticeType);

	}

	@Resource(type = NoticeTypeDao.class)
	public void setNoticeTypeDao(NoticeTypeDao noticeTypeDao) {
		this.noticeTypeDao = noticeTypeDao;
	}

	@Override
	public NoticeType getNoticeTypeById(Short id) {
		
		return noticeTypeDao.get(id);
	}

}