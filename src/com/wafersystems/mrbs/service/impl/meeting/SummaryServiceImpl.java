package com.wafersystems.mrbs.service.impl.meeting;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.wafersystems.mrbs.dao.meeting.SummaryDao;
import com.wafersystems.mrbs.logmng.ann.MrbsLog;
import com.wafersystems.mrbs.service.meeting.SummaryService;
import com.wafersystems.mrbs.vo.meeting.Summary;

@Service
public class SummaryServiceImpl implements SummaryService {

	private SummaryDao dao;

	@Override
	@MrbsLog(desc="meeting.summary_create")
	public void saveSummary(Summary vo) {
		dao.save(vo);
	}

	@Override
	@MrbsLog(desc="meeting.summary_update")
	public void updateSummary(Summary vo) {
		dao.update(vo);
	}

	@Override
	@MrbsLog(desc="meeting.summary_delete")
	public void delSummary(Summary vo) {
		dao.delete(vo);

	}
	
	@Override
	public Summary getSummaryById(int id){
		return dao.get(id);
	}

	@Resource(type = SummaryDao.class)
	public void setSummaryDao(SummaryDao dao) {
		this.dao = dao;
	}

}