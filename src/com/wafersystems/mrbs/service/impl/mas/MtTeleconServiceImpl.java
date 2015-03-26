package com.wafersystems.mrbs.service.impl.mas;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.wafersystems.mrbs.dao.mas.MoTeleconDao;
import com.wafersystems.mrbs.dao.mas.MtTeleconDao;
import com.wafersystems.mrbs.service.mas.MtTeleconService;
import com.wafersystems.mrbs.vo.mas.MoTelecon;
import com.wafersystems.mrbs.vo.mas.MtTelecon;

@Service("MtTeleconService")
public class MtTeleconServiceImpl implements MtTeleconService {
	
	private Logger logger = LoggerFactory.getLogger(MtTeleconServiceImpl.class);
	
	private MtTeleconDao mtTeleconDao;
	private MoTeleconDao moTeleconDao;
	
	/**
	 * 新增发送短信
	 * @param mtTelecon
	 */
	public void saveMtTelecon(MtTelecon mtTelecon){
		mtTeleconDao.save(mtTelecon);
	}

	/**
	 * 修改发送短信
	 * @param mtTelecon
	 */
	public void updateMtTelecon(MtTelecon mtTelecon){
		mtTeleconDao.update(mtTelecon);
	}

	/**
	 * 删除发送短信
	 * @param mtTelecon
	 */
	public void deleteMtTelecon(MtTelecon mtTelecon){
		mtTeleconDao.delete(mtTelecon);
	}
	
	/**
	 * 新增接收短信
	 * @param mtTelecon
	 */
	public void saveMoTelecon(MoTelecon moTelecon){
		moTeleconDao.save(moTelecon);
	}

	/**
	 * 修改接收短信
	 * @param mtTelecon
	 */
	public void updateMoTelecon(MoTelecon moTelecon){
		moTeleconDao.update(moTelecon);
	}

	/**
	 * 删除接收短信
	 * @param mtTelecon
	 */
	public void deleteMoTelecon(MoTelecon moTelecon){
		moTeleconDao.delete(moTelecon);
	}
	
	/**
	 * 取得所有要发送的短信
	 * @param dataExpiredFlag 0 未过期， 1 已过期
	 * @param state 0 未发送， 1 已发送
	 * @param smsServiceCode 007 通知受邀人，有需要其接受的病历讨论邀请,008 通知受邀人，有需要其接受的视频讲座邀请
	 * @param resendTimes 次数
	 * @return List<MtTelecon>
	 * @throws Throwable
	 */
	public List<MtTelecon> getMtTeleconList(String state) throws Throwable{
		try{
			return mtTeleconDao.getMtTeleconList(state);
		}catch(Exception e){
			logger.error("Error MtTeleconServiceImpl.getMtTeleconList",e);
			throw e;
		}catch(Throwable e){
			logger.error("Error MtTeleconServiceImpl.getMtTeleconList",e);
			throw e;
		}
	}
	
	/**
	 * 取得发送短信中最大的手机上显示尾号（终端源地址）SrcId
	 * @return
	 * @throws Throwable 
	 */
	public synchronized long getMaxSrcId() throws Throwable{
		try{
			return mtTeleconDao.getMaxSrcId();
		}catch(Exception e){
			logger.error("Error MtTeleconServiceImpl.getMaxSrcId",e);
			throw e;
		}catch(Throwable e){
			logger.error("Error MtTeleconServiceImpl.getMaxSrcId",e);
			throw e;
		}
	}
	
	/**
	 * 根据smId取得所有接收到的短信
	 * @param dataExpiredFlag 0 未过期， 1 已过期
	 * @param dataOperatedFlag 0 未处理， 1 已处理
	 * @param smId MO 短信的短信ID ，对应发送队列表（MT 表）的SRC_ID字段。
	 * @return List<MtTelecon>
	 * @throws Throwable
	 */
	public List<MoTelecon> getMoTeleconList(String smId) throws Throwable{
		try{
			return moTeleconDao.getMoTeleconList(smId);
		}catch(Exception e){
			logger.error("Error MtTeleconServiceImpl.getMoTeleconList",e);
			throw e;
		}catch(Throwable e){
			logger.error("Error MtTeleconServiceImpl.getMoTeleconList",e);
			throw e;
		}
	}
	
	/**
	 * 根据smId将短信接收表中对应数据的状态改为已处理,将MtTelecon中已经回复过的短信改为过期数据
	 * @param smId MO 短信的短信ID ，对应发送队列表（MT 表）的SRC_ID字段。
	 * @param data_operated_flag 0 未处理， 1 已处理
	 * @param data_expired_flag 0 未过期， 1 已过期
	 * @return Integer
	 * @throws Throwable
	 */
	public Integer updateMoTeleconBySmIdAndUpdateMtTeleconForReplyed(String smId,MtTelecon mtTelecon) throws Throwable{
		try{
			int intReturn = 0;
			intReturn = moTeleconDao.updateMoTeleconBySmId(smId);
			if(mtTelecon!=null){
				intReturn = mtTeleconDao.updateMtTeleconForReplyed(mtTelecon);
			}
			return intReturn;
			
		}catch(Exception e){
			logger.error("Error MtTeleconServiceImpl.updateMoTeleconBySmIdAndUpdateMtTeleconForReplyed",e);
			throw e;
		}catch(Throwable e){
			logger.error("Error MtTeleconServiceImpl.updateMoTeleconBySmIdAndUpdateMtTeleconForReplyed",e);
			throw e;
		}
	}

	/**
	 * @param mtTeleconDao the mtTeleconDao to set
	 */
	@Resource(type = MtTeleconDao.class)
	public void setMtTeleconDao(MtTeleconDao mtTeleconDao) {
		this.mtTeleconDao = mtTeleconDao;
	}

	/**
	 * @param moTeleconDao the moTeleconDao to set
	 */
	@Resource(type = MoTeleconDao.class)
	public void setMoTeleconDao(MoTeleconDao moTeleconDao) {
		this.moTeleconDao = moTeleconDao;
	}
	
	
	
}
