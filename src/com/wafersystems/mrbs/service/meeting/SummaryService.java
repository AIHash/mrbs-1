package com.wafersystems.mrbs.service.meeting;

import com.wafersystems.mrbs.vo.meeting.Summary;

public interface SummaryService {

	public void saveSummary(Summary vo);

	public void updateSummary(Summary vo);

	public void delSummary(Summary vo);
	
	public Summary getSummaryById(int id);

}
