package com.wafersystems.mrbs.dao.meeting;


import com.wafersystems.mrbs.dao.base.GenericDao;
import com.wafersystems.mrbs.vo.meeting.Evaluation;

public interface EvaluationDao extends GenericDao<Evaluation>{
	
	public Double getEvaluationRate(Integer evaluationId);
	
	public Long getEvaluationMeetNumberByid(Integer evaluationId);
	
	public Long getEvaluationMeetNumberByScore(Integer evaluationId,Integer score);
	
	public Double getMainEvaluationRate();
	
	public Long getMainEvaluationMeetNumberByid();
	
	public Long getMainEvaluationMeetNumberByScore(Integer score);
	
}
