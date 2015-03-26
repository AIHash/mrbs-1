package com.wafersystems.mrbs.dao.impl.meeting;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.jdbc.Work;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.wafersystems.mrbs.dao.base.GenericDaoImpl;
import com.wafersystems.mrbs.dao.base.PageData;
import com.wafersystems.mrbs.dao.base.PageSortModel;
import com.wafersystems.mrbs.dao.meeting.AccessoriesDao;
import com.wafersystems.mrbs.vo.meeting.Accessories;
import com.wafersystems.mrbs.vo.meeting.AccessoriesType;
import com.wafersystems.mrbs.vo.meeting.Meeting;
import com.wafersystems.mrbs.vo.user.UserInfo;
import com.wafersystems.mrbs.web.criteriavo.ManagementOfAccessoriesVO;
import com.wafersystems.util.StrUtil;

@Repository
public class AccessoriesDaoImpl extends GenericDaoImpl<Accessories> implements
		AccessoriesDao {

	public void saveBatchData(final Accessories[] vos) {
		getHibernateTemplate().execute(new HibernateCallback<Void>() {
			@Override
			public Void doInHibernate(Session session)
					throws HibernateException, SQLException {
				if (vos == null || vos.length == 0)
					return null;
				session.doWork(new Work(){
					@Override
					public void execute(Connection connection) throws SQLException {
						PreparedStatement ps = null;
						String sql = "insert into accessories(name,path,owner,type) values (?,?,?,?)";
						ps = connection.prepareStatement(sql);
						for (Accessories acces : vos) {
							ps.setString(1, acces.getName());
							ps.setString(2, acces.getPath());
							ps.setString(3, acces.getOwner().getUserId());
							ps.setShort(4, acces.getType().getId());
							ps.addBatch();
						}
						ps.executeBatch();
						ps.close();
					}});
				session.flush();
				session.close();
				return null;
			}
		});
	}

	public Integer deleteAccessoriesByAppId(final Integer appId){
		return getHibernateTemplate().execute(new HibernateCallback<Integer>() {
			@Override
			public Integer doInHibernate(Session session)throws HibernateException, SQLException {
				List<?> list = session.createSQLQuery("SELECT accessories.id FROM accessories, meeting_application_accessories "
						+ " WHERE accessories.id = meeting_application_accessories.accessories "
						+ " AND meeting_application_accessories.meeting_application = ?")
						.setInteger(0, appId).list();
				session.createSQLQuery("delete from meeting_application_accessories where meeting_application =  ?")
					.setInteger(0, appId).executeUpdate();
				if(list.size() > 0)
					session.createSQLQuery("delete from accessories where id in (:idArray)")
						.setParameterList("idArray", list).executeUpdate();
				return list.size();
			}
		});
	}

	public Integer deleteAccessoriesAndFileByAppId(final String accPath,final Integer appId){
		return getHibernateTemplate().execute(new HibernateCallback<Integer>() {
			@Override
			public Integer doInHibernate(Session session)throws HibernateException, SQLException {
				List<?> list = session.createSQLQuery("SELECT accessories.path, accessories.type, accessories.id"
					+ " FROM accessories, meeting_application_accessories "
					+ " WHERE accessories.id = meeting_application_accessories.accessories  "
					+ " AND meeting_application_accessories.meeting_application = ?")
					.setInteger(0, appId).list();
				int listCount = 0;
				if(list != null && list.size() > 0){
					File accFile;
					String accFileString;
					Integer accId;
					Short accType;
					listCount = list.size();
					Integer[] idArray = new Integer[listCount];
					for (int i = 0; i < listCount; i++) {
						Object[] objects = (Object[])list.get(i);
						accId = (Integer)objects[2];//附件id
						idArray[i] = accId;
						accType = (Short)objects[1];//附件类型
						if(!accType.equals((short)2)){//非放射影像才删除文件
							accFileString = (String)objects[0];
							accFile = new File(accPath + "/" + accFileString);
							accFile.delete();
						}
					}
					session.createSQLQuery("delete from meeting_application_accessories where meeting_application =  ?")
						.setInteger(0, appId).executeUpdate();
					session.createSQLQuery("delete from accessories where id in (:idArray)")
							.setParameterList("idArray", idArray).executeUpdate();
				}
				return listCount;
			}
		});
	}

	/**
	 * 删除视频申请的附件
	 */
	public Integer deleteAccessoriesByVoidId(final Integer videoId){
		return getHibernateTemplate().execute(new HibernateCallback<Integer>() {
			@Override
			public Integer doInHibernate(Session session)throws HibernateException, SQLException {
				List<?> list = session.createSQLQuery("SELECT accessories.id FROM accessories, video_meeting_app_accessories "
						+ " WHERE accessories.id = video_meeting_app_accessories.accessories  "
						+ " AND video_meeting_app_accessories.video_meeting_app = ?")
						.setInteger(0, videoId).list();
				session.createSQLQuery("delete from video_meeting_app_accessories where video_meeting_app = ?")
					.setInteger(0, videoId).executeUpdate();
				if(list.size() > 0)
					session.createSQLQuery("delete from accessories where id in (:idArray)")
						.setParameterList("idArray", list).executeUpdate();
				return list.size();
			}
		});
	}

	public Integer deleteAccessoriesAndFileByVideoId(final String accPath, final Integer videoId){
		return getHibernateTemplate().execute(new HibernateCallback<Integer>() {
			@Override
			public Integer doInHibernate(Session session)throws HibernateException, SQLException {
				List<?> list = session.createSQLQuery("SELECT accessories.path, accessories.type, accessories.id " 
					+ " FROM accessories, video_meeting_app_accessories "
					+ " WHERE accessories.id = video_meeting_app_accessories.accessories  "
					+ " AND video_meeting_app_accessories.video_meeting_app = ?")
					.setInteger(0, videoId).list();
				int listCount = 0;
				if(list != null && list.size() > 0){
					File accFile;
					String accFileString;
					Integer accId;
					Short accType;
					listCount = list.size();
					Integer[] idArray = new Integer[listCount];
					for (int i = 0; i < listCount; i++) {
						Object[] objects = (Object[])list.get(i);
						accId = (Integer)objects[2];//附件id
						idArray[i] = accId;
						accType = (Short)objects[1];//附件类型
						if(!accType.equals((short)2)){//非放射影像才删除文件
							accFileString = (String)objects[0];
							accFile = new File(accPath + "/" + accFileString);
							accFile.delete();
						}
					}
					session.createSQLQuery("delete from video_meeting_app_accessories where video_meeting_app = ?")
						.setInteger(0, videoId).executeUpdate();
					session.createSQLQuery("delete from accessories where id in (:idArray)")
							.setParameterList("idArray", idArray).executeUpdate();
				}
				return listCount;
			}
		});
	}

	/**
	 * 获得所有的附件
	 * */
	@Override
	public PageData<Accessories>  getAllAccessories(PageSortModel psm,ManagementOfAccessoriesVO managementOfAccessoriesVo,UserInfo userinfo) throws Throwable {
		managementOfAccessoriesVo.getAttachmentAttribution();
		String hql = "";
		
        //附件归属
		if(!StrUtil.isEmptyStr(managementOfAccessoriesVo.getAttachmentAttribution())){
			int intAttachmentAttribution = Integer.parseInt(managementOfAccessoriesVo.getAttachmentAttribution());
			String userId = userinfo.getUserId();
			if(intAttachmentAttribution == 1){
				hql+= " and o.owner.id != '" + userId + "'" ;
			}
			if(intAttachmentAttribution == 2){
				hql+= " and o.owner.id = '" + userId + "'" ;
			}
		}
		
		//附件类型
		if((!StrUtil.isEmptyStr(managementOfAccessoriesVo.getAccessoriesType())) && (managementOfAccessoriesVo.getAccessoriesType() != "8")){
			int intAccessoriesType = Integer.parseInt(managementOfAccessoriesVo.getAccessoriesType());
			if(intAccessoriesType != 8){
				hql+= " and o.type.id = '" + managementOfAccessoriesVo.getAccessoriesType()+"'";
			}else {
				hql+= " and o.type.id >=8";
			}
		}
		
		//附件上传人
		String attachmentUploadPerson=managementOfAccessoriesVo.getAttachmentUploadPerson();
		if(!StrUtil.isEmptyStr(attachmentUploadPerson)&&!attachmentUploadPerson.equals("-1")){
			hql += " and o.owner.name like '%"+attachmentUploadPerson+"%'";
		}
		
		//附件名称
		String accessoriesName=managementOfAccessoriesVo.getAccessoriesName();
		if(!StrUtil.isEmptyStr(accessoriesName)){
			String[] accessoriesNames = accessoriesName.split("；");
			for(String accName : accessoriesNames){
				hql+=" and (o.name like '%"+accName+"%' )" ;
			}				
		}
		
		//手动输入类型名
		//String sql = "select * from accessories o , accessories_type ac where o.type = ac.id and ac.name  like '%威发%'";
		//String sql = "select * from accessories o where o.type =  (select ac.id from accessories_type ac where ac.name  like '%威发%'";
		String manualInput=managementOfAccessoriesVo.getManualInput();
		if(!StrUtil.isEmptyStr(manualInput)){
			String[] manualInputs = manualInput.split("；");
			for(String mInput : manualInputs){
				hql += " and o.type.id > 8 and o.type.name = (select ac.name from AccessoriesType ac where ac.name like '%"+mInput+"%')";
			}				
		}
		LinkedHashMap<String,Object> linkmap=new LinkedHashMap<String,Object>();
		//linkmap.put("name", "asc");
		PageData<Accessories> data = this.getScrollData(psm, hql, null, linkmap);
		return data;
	}
	/**
	 * 删除icu重症监护病例对应的文件
	 */
	public Integer deleteAccessoriesByMonitorId(final Integer monitorId){
		return getHibernateTemplate().execute(new HibernateCallback<Integer>() {
			@Override
			public Integer doInHibernate(Session session)throws HibernateException, SQLException {
				List<?> list = session.createSQLQuery("SELECT accessories.id FROM accessories, icu_monitor_accessories "
						+ " WHERE accessories.id = icu_monitor_accessories.accessories "
						+ " AND icu_monitor_accessories.icu_monitor = ?")
						.setInteger(0, monitorId).list();
				session.createSQLQuery("delete from icu_monitor_accessories where icu_monitor =  ?")
					.setInteger(0, monitorId).executeUpdate();
				if(list.size() > 0)
					session.createSQLQuery("delete from accessories where id in (:idArray)")
						.setParameterList("idArray", list).executeUpdate();
				return list.size();
			}
		});
	}
	/**
	 * add by wangzhenglin  删除重症监护信息对应的附件信息
	 * @param accPath
	 * @param icuMonitorId
	 * @return
	 */
	public Integer deleteAccessoriesAndFileByIcuMonitor(final String accPath,final Integer icuMonitorId){
		return getHibernateTemplate().execute(new HibernateCallback<Integer>() {
			@Override
			public Integer doInHibernate(Session session)throws HibernateException, SQLException {
				List<?> list = session.createSQLQuery("SELECT accessories.path, accessories.type, accessories.id"
					+ " FROM accessories, icu_monitor_accessories "
					+ " WHERE accessories.id =icu_monitor_accessories.accessories  "
					+ " AND icu_monitor_accessories.icu_monitor = ?")
					.setInteger(0, icuMonitorId).list();
				int listCount = 0;
				if(list != null && list.size() > 0){
					File accFile;
					String accFileString;
					Integer accId;
					Short accType;
					listCount = list.size();
					Integer[] idArray = new Integer[listCount];
					for (int i = 0; i < listCount; i++) {
						Object[] objects = (Object[])list.get(i);
						accId = (Integer)objects[2];//附件id
						idArray[i] = accId;
						accType = (Short)objects[1];//附件类型
						if(!accType.equals((short)2)){//非放射影像才删除文件
							accFileString = (String)objects[0];
							accFile = new File(accPath + "/" + accFileString);
							accFile.delete();
						}
					}
					session.createSQLQuery("delete from icu_monitor_accessories where icu_monitor =  ?")
						.setInteger(0, icuMonitorId).executeUpdate();
					session.createSQLQuery("delete from accessories where id in (:idArray)")
							.setParameterList("idArray", idArray).executeUpdate();
				}
				return listCount;
			}
		});
	}
}
