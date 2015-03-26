package com.wafersystems.mrbs.service.impl.meeting;

import java.util.LinkedHashMap;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.wafersystems.mrbs.dao.base.PageSortModel;
import com.wafersystems.mrbs.dao.meeting.EvaluationDao;
import com.wafersystems.mrbs.logmng.ann.MrbsLog;
import com.wafersystems.mrbs.service.meeting.EvaluationService;
import com.wafersystems.mrbs.vo.meeting.Evaluation;
import com.wafersystems.mrbs.vo.user.UnifiedUserType;

@Service
public class EvaluationServiceImpl implements EvaluationService {

	private EvaluationDao dao;
	private static final Logger logger = LoggerFactory.getLogger(EvaluationServiceImpl.class);
	@Override
	@MrbsLog(desc="group.evaluation_create")
	public void saveEvaluation(Evaluation vo) {
		dao.save(vo);
	}

	@Override
	@MrbsLog(desc="group.evaluation_update")
	public void updateEvaluation(Evaluation vo) {
		dao.update(vo);
	}

	@Override
	@MrbsLog(desc="group.evaluation_delete")
	public void delEvaluation(Integer id) {
		dao.delete(id);
	}
	
	@Override
	public Evaluation getEvaluationById(Integer id){
		return dao.get(id);
	}

	@Resource(type = EvaluationDao.class)
	public void setEvaluationDao(EvaluationDao dao) {
		this.dao = dao;
	}

	
	public List<Evaluation> getEvaluationList(PageSortModel psm){
		LinkedHashMap<String, Object> orderby = new LinkedHashMap<String, Object>();
		orderby.put("name", "desc");
		return  dao.getScrollData(psm, null, null, orderby).getResultlist();
	}
	@Override
	public List<Evaluation> getEvaluationList(){
		String  hql= "from Evaluation e where e.id <> 1";
		
		return  dao.findTbyHql(hql);
	}

	@Override
	public Double getEvaluationRateByid(Integer evaluationId) {	
		return  dao.getEvaluationRate(evaluationId);
	}

	@Override
	public Long getEvaluationMeetNumberByid(Integer evaluationId) {
		// TODO Auto-generated method stub
		return dao.getEvaluationMeetNumberByid(evaluationId);
	}

	@Override
	public Long getEvaluationMeetNumberByScore(Integer evaluationId,
			Integer score) {
		// TODO Auto-generated method stub
		return dao.getEvaluationMeetNumberByScore(evaluationId, score);
	}

	@Override
	public List<Evaluation> getMainEvaluationList() {
		String  hql= "from Evaluation e where e.id = 1";
		
		return  dao.findTbyHql(hql);
	}

	@Override
	public Double getMainEvaluationRateByid() {
		// TODO Auto-generated method stub
		return dao.getMainEvaluationRate();
	}

	@Override
	public Long getMainEvaluationMeetNumberByid() {
		// TODO Auto-generated method stub
		return dao.getMainEvaluationMeetNumberByid();
	}

	@Override
	public Long getMainEvaluationMeetNumberByScore(
			Integer score) {
		// TODO Auto-generated method stub
		return dao.getMainEvaluationMeetNumberByScore( score);
	}

	@Override
	public List<Evaluation> getEvaluationList(UnifiedUserType ut) {
		String  hql= "from Evaluation e where e.userType = '"+ut.getValue()+"' order by e.id asc";			
		return  dao.findTbyHql(hql);
	}
	
	/**
	 * 根据用户类型取得评价明细
	 * @param userTypeValue
	 * @return
	 */
	@Override
	public List<Evaluation> getEvaluationListByuserTypeValue(Short userTypeValue)throws Exception {
		try{
			String  hql= "from Evaluation e where e.userType = '"+userTypeValue+"' order by e.id asc";			
			return  dao.findTbyHql(hql);
		} catch (Exception e) {
			logger.error("Error MeeAdmIndex.lectureEvaluation",e);
			throw e;
		} 
	}



}