package com.wafersystems.mrbs.dao.impl.meeting;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.wafersystems.mrbs.dao.base.GenericDaoImpl;
import com.wafersystems.mrbs.dao.meeting.EvaluationDao;
import com.wafersystems.mrbs.vo.meeting.Evaluation;

@Repository
public class EvaluationImpl extends GenericDaoImpl<Evaluation> implements EvaluationDao {

	@Override
	public Double getEvaluationRate(Integer evaluationId) {
		// TODO Auto-generated method stub
		  String  hql= "select avg(score) as evaluationrate from Satisfaction e where e.evaluation.id ="+evaluationId+" group by e.evaluation.id";
		  List<?> list =  template.find(hql);	   
		return (Double)list.get(0);
	}
	@Override
	public Long getEvaluationMeetNumberByid(Integer evaluationId) {
		// TODO Auto-generated method stub
		  String  hql= "select count(*) as evaluationmeetnumber from Satisfaction e where e.evaluation.id ="+evaluationId;
		  List<?> list =  template.find(hql);	   
		return (Long)list.get(0);
	}

	@Override
	public Long getEvaluationMeetNumberByScore(Integer evaluationId,Integer score) {
		// TODO Auto-generated method stub
		  String  hql= "select count(*) as evaluationmeetnumber from Satisfaction e where e.evaluation.id ="+evaluationId +" and score = "+score;
		  List<?> list =  template.find(hql);		   
		  return (Long)list.get(0);
	}
	@Override
	public Double getMainEvaluationRate() {
		  String  hql= "select avg(score) as evaluationrate from SatisfactionMain ";
		  List<?> list =  template.find(hql);	   
		return (Double)list.get(0);
	}
	@Override
	public Long getMainEvaluationMeetNumberByid() {
		  String  hql= "select count(*) as evaluationmeetnumber from SatisfactionMain ";
		  List<?> list =  template.find(hql);	   
		return (Long)list.get(0);
	}
	@Override
	public Long getMainEvaluationMeetNumberByScore(	Integer score) {
		  String  hql= "select count(*) as evaluationmeetnumber from SatisfactionMain e where  score = "+score;
		  List<?> list =  template.find(hql);		   
		  return (Long)list.get(0);
	}

}
