package com.wafersystems.mrbs.dao.mas;

import java.util.List;

import com.wafersystems.mrbs.dao.base.GenericDao;
import com.wafersystems.mrbs.vo.mas.MoTelecon;

public interface MoTeleconDao  extends GenericDao<MoTelecon>{

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
	 * 根据smId将短信接收表中对应数据的状态改为已处理
	 * @param smId MO 短信的短信ID ，对应发送队列表（MT 表）的SRC_ID字段。
	 * @return Integer
	 * @throws Throwable
	 */
	public Integer updateMoTeleconBySmId(String smId) throws Throwable;
}
