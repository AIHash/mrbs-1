package com.wafersystems.mrbs.dao.mas;

import java.util.List;

import com.wafersystems.mrbs.dao.base.GenericDao;
import com.wafersystems.mrbs.vo.mas.MtTelecon;

public interface MtTeleconDao extends GenericDao<MtTelecon>{

	/**
	 * 取得所有要发送的短信
	 * @param dataExpiredFlag 0 未过期， 1 已过期
	 * @param state 0 未发送， 1 已发送
	 * @param smsServiceCode 007 通知受邀人，有需要其接受的病历讨论邀请,008 通知受邀人，有需要其接受的视频讲座邀请
	 * @param resendTimes 次数
	 * @return List<MtTelecon>
	 * @throws Throwable
	 */
	public List<MtTelecon> getMtTeleconList(String state) throws Throwable;
	
	/**
	 * 取得发送短信中最大的手机上显示尾号（终端源地址）SrcId
	 * @return
	 * @throws Throwable 
	 */
	public long getMaxSrcId() throws Throwable;
	
	/**
	 * 将MtTelecon中已经回复过的短信改为过期数据
	 * @param data_expired_flag 0 未过期， 1 已过期
	 * @return Integer
	 * @throws Throwable
	 */
	public Integer updateMtTeleconForReplyed(MtTelecon mtTelecon) throws Throwable;
	
}
