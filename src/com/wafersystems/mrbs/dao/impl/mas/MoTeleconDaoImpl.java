package com.wafersystems.mrbs.dao.impl.mas;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.wafersystems.mrbs.GlobalConstent;
import com.wafersystems.mrbs.dao.base.GenericDaoImpl;
import com.wafersystems.mrbs.dao.mas.MoTeleconDao;
import com.wafersystems.mrbs.vo.mas.MoTelecon;
import com.wafersystems.util.StrUtil;

@Repository
public class MoTeleconDaoImpl extends GenericDaoImpl<MoTelecon> implements MoTeleconDao {

	private Logger logger = LoggerFactory.getLogger(MoTeleconDaoImpl.class);
	
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
			String hql = "from MoTelecon mo where (mo.dataExpiredFlag = '"+GlobalConstent.DATE_EXPIRED_FLAG_N+"' or mo.dataExpiredFlag is null) and (mo.dataOperatedFlag = '"+GlobalConstent.DATE_OPERATED_FALG_N+"' or mo.dataOperatedFlag is null)";
			if(!StrUtil.isEmptyStr(smId)){
				hql +=" and mo.smId = '"+smId+"'";
			}
			hql +=" order by mo.id asc";
			logger.debug("MtTeleconDaoImpl.getMoTeleconList hql="+hql);
			return this.findTbyHql(hql);
		}catch(Exception e){
			logger.error("Error MoTeleconDaoImpl.getMoTeleconList",e);
			throw e;
		}catch(Throwable e){
			logger.error("Error MoTeleconDaoImpl.getMoTeleconList",e);
			throw e;
		}
	}
	
	/**
	 * 根据smId将短信接收表中对应数据的状态改为已处理
	 * @param smId MO 短信的短信ID ，对应发送队列表（MT 表）的SRC_ID字段。
	 * @param data_operated_flag 0 未处理， 1 已处理
	 * @param data_expired_flag 0 未过期， 1 已过期
	 * @return Integer
	 * @throws Throwable
	 */
	public Integer updateMoTeleconBySmId(String smId) throws Throwable{
		try{
			final String hql = "update mo_telecon set data_operated_flag = '"+GlobalConstent.DATE_OPERATED_FALG_Y+"' where sm_id='"+smId+"' and (data_expired_flag = '"+GlobalConstent.DATE_EXPIRED_FLAG_N+"' or data_expired_flag is null)";
			return getHibernateTemplate().execute(new HibernateCallback<Integer>() {
				@Override
				public Integer doInHibernate(Session session) throws HibernateException, SQLException {
					Integer size = session.createSQLQuery(hql).executeUpdate();
					return size;
				}
			});
		}catch(Exception e){
			logger.error("Error MoTeleconDaoImpl.updateMoTeleconBySmId",e);
			throw e;
		}catch(Throwable e){
			logger.error("Error MoTeleconDaoImpl.updateMoTeleconBySmId",e);
			throw e;
		}
	}
}
