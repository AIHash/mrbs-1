package com.wafersystems.mrbs.service.impl.meeting;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.wafersystems.mrbs.dao.meeting.MeetingSummaryDao;
import com.wafersystems.mrbs.service.meeting.MeetingSummaryService;
import com.wafersystems.mrbs.vo.meeting.MeetingSummary;

@Service
public class MeetingSummaryServiceImpl implements MeetingSummaryService {

	private static Logger logger = LoggerFactory.getLogger(MeetingSummaryServiceImpl.class);
	private MeetingSummaryDao summaryDao;

	@Override
	public void saveSummary(MeetingSummary vo) {
		summaryDao.save(vo);
	}

	@Override
	public void updateSummary(MeetingSummary vo) {
		summaryDao.update(vo);
	}

	@Override
	public void delSummary(MeetingSummary vo) {
		summaryDao.delete(vo.getId());
	}

	@Override
	public MeetingSummary getSummaryById(int id) {
		return summaryDao.get(id);
	}

	@Override
	public void saveSummarysForManager(Integer meetingId, List<MeetingSummary> summarys) throws Exception{
		Integer delCount = summaryDao.delSummaryForManagerSaveData(meetingId);
		logger.debug("删除会诊id为[" + meetingId + "]的评价" + delCount + "条");
		for (MeetingSummary meetingSummary : summarys) {
			summaryDao.save(meetingSummary);
		}
	}

	@Override
	public void saveSummaryForUser(Integer meetingId, String userId, MeetingSummary summary) throws Exception{
		Integer delCount = summaryDao.delSummaryForUserSaveData(meetingId, userId);
		logger.debug("删除会诊id为[" + meetingId + "]的，userId为" + userId + "评价" + delCount + "条");
		summaryDao.save(summary);
	}

	@Resource
	public void setSummaryDao(MeetingSummaryDao summaryDao) {
		this.summaryDao = summaryDao;
	}

}
