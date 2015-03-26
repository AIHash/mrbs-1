package com.wafersystems.mrbs.service.meeting;

import java.util.List;



import com.wafersystems.mrbs.dao.base.PageSortModel;
import com.wafersystems.mrbs.vo.meeting.Evaluation;
import com.wafersystems.mrbs.vo.user.UnifiedUserType;

public interface EvaluationService {

	public void saveEvaluation(Evaluation vo);

	public void updateEvaluation(Evaluation vo);

	public void delEvaluation(Integer id);
	
	public Evaluation getEvaluationById(Integer evaluationId);
	
	public List<Evaluation> getEvaluationList(PageSortModel psm);
	
	public List<Evaluation> getEvaluationList();
	
	public List<Evaluation> getEvaluationList(UnifiedUserType ut);
	
	public List<Evaluation> getMainEvaluationList();
	
	public Double getMainEvaluationRateByid();
	
	public Double getEvaluationRateByid(Integer evaluationId);
	
	public Long getMainEvaluationMeetNumberByid();
	
	public Long getEvaluationMeetNumberByid(Integer evaluationId);
	
	public Long getMainEvaluationMeetNumberByScore(Integer score);
	
	public Long getEvaluationMeetNumberByScore(Integer evaluationId,Integer score);

	/**
	 * 根据用户类型取得评价明细
	 * @param userTypeValue
	 * @return
	 */
	public List<Evaluation> getEvaluationListByuserTypeValue(Short userTypeValue) throws Exception;

}
