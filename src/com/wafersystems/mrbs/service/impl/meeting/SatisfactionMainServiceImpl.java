package com.wafersystems.mrbs.service.impl.meeting;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.wafersystems.mrbs.dao.meeting.SatisfactionMainDao;
import com.wafersystems.mrbs.logmng.ann.MrbsLog;
import com.wafersystems.mrbs.service.meeting.SatisfactionMainService;
import com.wafersystems.mrbs.vo.meeting.Meeting;
import com.wafersystems.mrbs.vo.meeting.SatisfactionMain;
import com.wafersystems.mrbs.vo.user.UserInfo;

@Service
public class SatisfactionMainServiceImpl implements SatisfactionMainService {

	private SatisfactionMainDao dao;

	@Override
	@MrbsLog(desc="group.SatisfactionMain_create")
	public void saveSatisfactionMain(SatisfactionMain vo) {
		dao.save(vo);
	}

	@Override
	@MrbsLog(desc="group.SatisfactionMain_update")
	public void updateSatisfactionMain(SatisfactionMain vo) {
		dao.update(vo);
	}

	@Override
	@MrbsLog(desc="group.SatisfactionMain_delete")
	public void delSatisfactionMain(SatisfactionMain vo) {
		
	}
	
	@Override
	public SatisfactionMain getSatisfactionMainById(int id){
		return dao.get(id);
	}

	@Override
	@MrbsLog(desc="group.SatisfactionMain_create")
	public void saveOrUpdateSatisfactionMain(Object[] array, int score,String suggestion){
		String  hql= "from SatisfactionMain s where s.meeting.id = ?  and s.user.userId = ? ";
		List<SatisfactionMain> list= dao.findTbyHql(hql, array);
		SatisfactionMain satisfactionMain = null;
		if(list.size() > 0){
			System.out.println("evalvalue==2"+score);
			System.out.println("suggestion==2"+suggestion);
			satisfactionMain = list.get(0);
			satisfactionMain.setScore(score);
			satisfactionMain.setSuggestion(suggestion);
			satisfactionMain.setCreateTime(new Date());
		}else{
			satisfactionMain = new SatisfactionMain();

			Meeting meeting = new Meeting();
			meeting.setId((Integer)array[0]);
			satisfactionMain.setMeeting(meeting);


			UserInfo user = new UserInfo();
			user.setUserId((String)array[1]);
			satisfactionMain.setUser(user);
			
			satisfactionMain.setSuggestion(suggestion);

			satisfactionMain.setScore(score);
			
			satisfactionMain.setCreateTime( new Date());

			this.saveSatisfactionMain(satisfactionMain);
		}
	}

	@Resource(type = SatisfactionMainDao.class)
	public void setSatisfactionMainDao(SatisfactionMainDao dao) {
		this.dao = dao;
	}

}