package com.wafersystems.mrbs.service.impl.meeting;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.wafersystems.mrbs.dao.meeting.SatisfactionDao;
import com.wafersystems.mrbs.logmng.ann.MrbsLog;
import com.wafersystems.mrbs.service.meeting.SatisfactionService;
import com.wafersystems.mrbs.vo.meeting.Meeting;
import com.wafersystems.mrbs.vo.meeting.Satisfaction;
import com.wafersystems.mrbs.vo.user.UserInfo;

@Service
public class SatisfactionServiceImpl implements SatisfactionService {

	private SatisfactionDao dao;

	@Override
	@MrbsLog(desc="group.satisfaction_create")
	public void saveSatisfaction(Satisfaction vo) {
		dao.save(vo);
	}

	@Override
	@MrbsLog(desc="group.satisfaction_update")
	public void updateSatisfaction(Satisfaction vo) {
		dao.update(vo);
	}

	@Override
	@MrbsLog(desc="group.satisfaction_delete")
	public void delSatisfaction(Satisfaction vo) {
		dao.delete(vo);
	}
	
	@Override
	public Satisfaction getSatisfactionById(int id){
		return dao.get(id);
	}

	@Override
	@MrbsLog(desc="group.saveOrUpdateSatisfaction")
	public void saveOrUpdateSatisfaction(Object[] array,Short localNumber,String evalvalue1,String evalvalue2,String evalvalue3, String suggestion){
		String  hql= "from Satisfaction s where s.meeting.id = ?  and s.user.userId = ? ";
		List<Satisfaction> list= dao.findTbyHql(hql, array);
		Satisfaction satisfaction = null;
		if(list.size() > 0){
			satisfaction = list.get(0);
			satisfaction.setLocalNumber(localNumber);
			satisfaction.setEvaluationScore1(evalvalue1);
			satisfaction.setEvaluationScore2(evalvalue2);
			satisfaction.setEvaluationScore3(evalvalue3);
			satisfaction.setSuggestion(suggestion);
			satisfaction.setCreateTime(new Date());
			this.updateSatisfaction(satisfaction);
		}else{
			satisfaction = new Satisfaction();

			Meeting meeting = new Meeting();
			meeting.setId((Integer)array[0]);
			satisfaction.setMeeting(meeting);


			UserInfo user = new UserInfo();
			user.setUserId((String)array[1]);
			satisfaction.setUser(user);
			satisfaction.setLocalNumber(localNumber);
			satisfaction.setEvaluationScore1(evalvalue1);
			satisfaction.setEvaluationScore2(evalvalue2);
			satisfaction.setEvaluationScore3(evalvalue3);
			satisfaction.setSuggestion(suggestion);
			satisfaction.setCreateTime(new Date());
			this.saveSatisfaction(satisfaction);
		}
	}

	@Resource(type = SatisfactionDao.class)
	public void setSatisfactionDao(SatisfactionDao dao) {
		this.dao = dao;
	}

	@Override
	public boolean getOpintionState(Object[] array) {
		String  hql= "from Satisfaction s where s.meeting.id = ?  and s.user.userId = ? ";
		List<Satisfaction> list= dao.findTbyHql(hql, array);
		if(list.size() > 0)
			return true;
		else
			return false;
	}
	
	/**
	 * 根据meetingId和userId取得Satisfaction
	 */
	public Satisfaction getSatisfaction(Object[] array) {
		String  hql= "from Satisfaction s where s.meeting.id = ?  and s.user.userId = ? ";
		List<Satisfaction> list= dao.findTbyHql(hql, array);
		if(list.size() > 0)
			return list.get(0);
		else
			return null;
	}


}