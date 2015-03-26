package com.wafersystems.mrbs.dao.impl.mas;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.wafersystems.mrbs.GlobalConstent;
import com.wafersystems.mrbs.dao.base.GenericDaoImpl;
import com.wafersystems.mrbs.dao.mas.MtTeleconDao;
import com.wafersystems.mrbs.vo.mas.MtTelecon;
import com.wafersystems.util.StrUtil;

@Repository
public class MtTeleconDaoImpl extends GenericDaoImpl<MtTelecon> implements MtTeleconDao {

	private Logger logger = LoggerFactory.getLogger(MtTeleconDaoImpl.class);
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
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
			String hql = "from MtTelecon mt where (mt.dataExpiredFlag = '"+GlobalConstent.DATE_EXPIRED_FLAG_N+"' or mt.dataExpiredFlag is null)";
			if(!StrUtil.isEmptyStr(state)){
				if(state.equals(String.valueOf(GlobalConstent.NOTICE_NOT_SEND))){
					hql+=" and mt.state = '" + GlobalConstent.NOTICE_NOT_SEND + "' and (mt.resendTimes < 3 or mt.resendTimes is null) ";
				}else if(state.equals(String.valueOf(GlobalConstent.NOTICE_SEND))){
					hql+=" and mt.state = '" + GlobalConstent.NOTICE_SEND + "' and mt.acceptSmsUserId is not null and mt.applicationId is not null ";
					hql+=" and (mt.smsServiceCode = '" + GlobalConstent.SMS_SERVICE_CODE_007 + "' or mt.smsServiceCode = '" + GlobalConstent.SMS_SERVICE_CODE_008 + "' )";
					hql+=" and (mt.sendTime is null or mt.sendTime <= '" + sdf.format(new Date()) + "' )";
				}
			}
			hql+="ORDER BY mt.id asc";
			logger.debug("MtTeleconDaoImpl.getMtTeleconList hql="+hql);
			return this.findTbyHql(hql);
		}catch(Exception e){
			logger.error("Error MtTeleconDaoImpl.getMtTeleconList",e);
			throw e;
		}catch(Throwable e){
			logger.error("Error MtTeleconDaoImpl.getMtTeleconList",e);
			throw e;
		}
	}
	
	/**
	 * 取得发送短信中最大的手机上显示尾号（终端源地址）SrcId
	 * @return
	 * @throws Throwable 
	 */
	public long getMaxSrcId() throws Throwable {
		try{
			String  sql= "select max(CAST(src_id AS UNSIGNED)) from mt_telecon";
			logger.debug("MtTeleconDaoImpl.getMaxSrcId sql="+sql);
			final String querySql = sql;
			@SuppressWarnings("unchecked")
			List<Object> result = template.executeFind(new HibernateCallback<List<Object>>() {

				@Override
				public List<Object> doInHibernate(Session session)
						throws HibernateException, SQLException {
					return session.createSQLQuery(querySql).list();
				}
			});
			if(result==null||result.get(0)==null){
				return 1;
			}else{
				return Long.valueOf(result.get(0).toString())+1 ;
			}
		}catch(Exception e){
			logger.error("Error MtTeleconDaoImpl.getMaxSrcId",e);
			throw e;
		}catch(Throwable e){
			logger.error("Error MtTeleconDaoImpl.getMaxSrcId",e);
			throw e;
		}
	}
	
	/**
	 * 将MtTelecon中已经回复过的短信改为过期数据
	 * @param data_expired_flag 0 未过期， 1 已过期
	 * @return Integer
	 * @throws Throwable
	 */
	public Integer updateMtTeleconForReplyed(MtTelecon mtTelecon) throws Throwable{
		try{
			String sql = "update mt_telecon set data_expired_flag = '"+GlobalConstent.DATE_EXPIRED_FLAG_Y+"' where state = '" + GlobalConstent.NOTICE_SEND + "' ";
			if(mtTelecon!=null){
				//短信业务代码
				if(!StrUtil.isEmptyStr(mtTelecon.getSmsServiceCode())){
					sql +=" and sms_service_code = '"+mtTelecon.getSmsServiceCode()+"' ";
				}
				//短信接受人的Id
				if(!StrUtil.isEmptyStr(mtTelecon.getAcceptSmsUserId())){
					sql +=" and accept_sms_user_id = '"+mtTelecon.getAcceptSmsUserId()+"' ";
				}
				//病历讨论或讲座申请的Id
				if(mtTelecon.getApplicationId()!=null){
					sql +=" and application_id = '"+mtTelecon.getApplicationId()+"' ";
				}
			}
			final String updateSql = sql;
			logger.debug("MtTeleconDaoImpl.updateMtTeleconForReplyed updateSql = "+sql);
			return getHibernateTemplate().execute(new HibernateCallback<Integer>() {
				@Override
				public Integer doInHibernate(Session session) throws HibernateException, SQLException {
					Integer size = session.createSQLQuery(updateSql).executeUpdate();
					return size;
				}
			});
		}catch(Exception e){
			logger.error("Error MtTeleconDaoImpl.updateMtTeleconForReplyed",e);
			throw e;
		}catch(Throwable e){
			logger.error("Error MtTeleconDaoImpl.updateMtTeleconForReplyed",e);
			throw e;
		}
	}
	
}
