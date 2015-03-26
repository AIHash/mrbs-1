package com.wafersystems.mrbs.service.mas;

import java.util.List;

import com.wafersystems.mrbs.vo.mas.MoTelecon;
import com.wafersystems.mrbs.vo.mas.MtTelecon;

public interface MtTeleconService {
	
	/**
	 * 新增发送短信
	 * @param mtTelecon
	 */
	public void saveMtTelecon(MtTelecon mtTelecon);

	/**
	 * 修改发送短信
	 * @param mtTelecon
	 */
	public void updateMtTelecon(MtTelecon mtTelecon);

	/**
	 * 删除发送短信
	 * @param mtTelecon
	 */
	public void deleteMtTelecon(MtTelecon mtTelecon);
	
	/**
	 * 新增接收短信
	 * @param mtTelecon
	 */
	public void saveMoTelecon(MoTelecon moTelecon);

	/**
	 * 修改接收短信
	 * @param mtTelecon
	 */
	public void updateMoTelecon(MoTelecon moTelecon);

	/**
	 * 删除接收短信
	 * @param mtTelecon
	 */
	public void deleteMoTelecon(MoTelecon moTelecon);
	
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
	 * 根据smId取得所有接收到的短信
	 * @param dataExpiredFlag 0 未过期， 1 已过期
	 * @param dataOperatedFlag 0 未处理， 1 已处理
	 * @param smId MO 短信的短信ID ，对应发送队列表（MT 表）的SRC_ID字段。
	 * @return List<MtTelecon>
	 * @throws Throwable
	 */
	public List<MoTelecon> getMoTeleconList(String smId) throws Throwable;
	
	/**
	 * 根据smId将短信接收表中对应数据的状态改为已处理,将MtTelecon中已经回复过的短信改为过期数据
	 * @param smId MO 短信的短信ID ，对应发送队列表（MT 表）的SRC_ID字段。
	 * @param data_operated_flag 0 未处理， 1 已处理
	 * @param data_expired_flag 0 未过期， 1 已过期
	 * @return Integer
	 * @throws Throwable
	 */
	public Integer updateMoTeleconBySmIdAndUpdateMtTeleconForReplyed(String smId,MtTelecon mtTelecon) throws Throwable;
}
