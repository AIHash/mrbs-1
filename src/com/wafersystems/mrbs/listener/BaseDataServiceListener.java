package com.wafersystems.mrbs.listener;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Repository;
import org.springframework.web.context.ServletContextAware;

import sun.misc.BASE64Decoder;

import com.wafersystems.mrbs.dao.meeting.AccessoriesTypeDao;
import com.wafersystems.mrbs.dao.meeting.ApplicationPositionDao;
import com.wafersystems.mrbs.dao.meeting.MeetingLevelDao;
import com.wafersystems.mrbs.dao.meeting.MeetingRoomDao;
import com.wafersystems.mrbs.dao.meeting.MeetingTypeDao;
import com.wafersystems.mrbs.dao.meeting.VideoApplicationPurposeDao;
import com.wafersystems.mrbs.dao.param.UnifiedParameterDao;
import com.wafersystems.mrbs.dao.user.DepartmentDao;
import com.wafersystems.mrbs.vo.meeting.AccessoriesType;
import com.wafersystems.mrbs.vo.meeting.ApplicationPosition;
import com.wafersystems.mrbs.vo.meeting.MeetingLevel;
import com.wafersystems.mrbs.vo.meeting.MeetingRoom;
import com.wafersystems.mrbs.vo.meeting.MeetingType;
import com.wafersystems.mrbs.vo.meeting.VideoApplicationPurpose;
import com.wafersystems.mrbs.vo.param.UnifiedParameter;
import com.wafersystems.mrbs.vo.user.Department;

@Repository("initListener")
public class BaseDataServiceListener implements ServletContextAware, ApplicationListener<ApplicationEvent> {

	private static Logger logger = LoggerFactory.getLogger(BaseDataServiceListener.class);
	private DepartmentDao departmentDao;// 部门,科室
	private MeetingTypeDao meetingTypeDao;// 会议类型
	private MeetingLevelDao meetingLevelDao;// 申请级别
	private MeetingRoomDao meetingRoomDao;// 会议室
	private UnifiedParameterDao parameterDao;
	private AccessoriesTypeDao accessoriesTypedao;
	private VideoApplicationPurposeDao videoApplicationPurposedao;
	private ApplicationPositionDao applicationPositionDao;
	private ServletContext servletContext;

	private static Map<String, List<?>> baseMap;
	private static boolean isInit = true;

	public List<Department> getdeptment() {
		String hql = "From Department where deptcode<>'001001' and deptcode like '001001%' order by deptcode";
		List<Department> results = departmentDao.findTbyHql(hql);
//		if (results != null && results.size() > 0) {
//			for (int i = 0; i < results.size(); i++) {
//				Department vo = results.get(i);
//				String depecode = vo.getDeptcode();
//				int length = (depecode.length()) / 3 - 3;
//				for (int j = 0; j < length; j++) {
//					vo.setName("——" + vo.getName());
//				}
//			}
//		}
		return results;
	}

	public List<MeetingRoom> getMeetingRoom() {
		String hql = "From MeetingRoom where mark = 0 order by convert_gbk(name) asc";
		List<MeetingRoom> results = meetingRoomDao.findTbyHql(hql);
		return results;
	}
	
	public List<Department> getAlldeptments() {
		String hql = "From Department order by deptcode";
		List<Department> results = departmentDao.findTbyHql(hql);
//		if (results != null && results.size() > 0) {
//			for (int i = 0; i < results.size(); i++) {
//				Department vo = results.get(i);
//				String depecode = vo.getDeptcode();
//				int length = (depecode.length()) / 3 - 3;
//				for (int j = 0; j < length; j++) {
//					vo.setName("——" + vo.getName());
//				}
//			}
//		}
		return results;
	}

	public List<Department> getdeptmentunified() {
		String hql = "From Department where deptcode like '001003%' order by deptcode";
		List<Department> results = departmentDao.findTbyHql(hql);
//		if (results != null && results.size() > 0) {
//			for (int i = 0; i < results.size(); i++) {
//				Department vo = results.get(i);
//				String depecode = vo.getDeptcode();
//				int length = (depecode.length()) / 3 - 3;
//				for (int j = 0; j < length; j++) {
//					vo.setName("——" + vo.getName());
//				}
//			}
//		}
		return results;
	}

	//得到会诊类型
	public List<MeetingType> getmeetingtype() {
		String hql = "From MeetingType order by id";
		List<MeetingType> results = meetingTypeDao.findTbyHql(hql);
		return results;
	}

	//得到会诊级别
	public List<MeetingLevel> getmeetinglevel() {
		String hql = "From MeetingLevel order by id";
		List<MeetingLevel> results = meetingLevelDao.findTbyHql(hql);
		return results;
	}

	//得到视频申请目的
	public List<VideoApplicationPurpose> getVideoApplicationPurpose() {
		String hql = "From VideoApplicationPurpose";
		List<VideoApplicationPurpose> results = videoApplicationPurposedao.findTbyHql(hql);
		return results;
	}
	
	//得到申请职位
	public List<ApplicationPosition> getApplicationPosition() {
		String hql = "From ApplicationPosition";
		List<ApplicationPosition> results = applicationPositionDao.findTbyHql(hql);
		return results;
	}
	
	//得到附件类型
	public List<AccessoriesType> getAccessoriesType() {
		String hql = "From AccessoriesType a where a.id between 1 and 8 order by id desc";
		List<AccessoriesType> results = accessoriesTypedao.findTbyHql(hql);
		return results;
	}
	
//	public List<MeetingRoom> getMeetingRoom() {
//		List<MeetingRoom> results = this.meetingRoomDao.findAll();
//		return results;
//	}

	@Override
	public void onApplicationEvent(ApplicationEvent event) {
		if (event instanceof ContextRefreshedEvent) {
			if (isInit) {
				isInit = false;
				loadPara();
			}
		}
	}

	public void loadPara(){
		BASE64Decoder decoder = new BASE64Decoder();
		baseMap = new HashMap<String, List<?>>();
		baseMap.put("deptment", this.getdeptment());
		baseMap.put("alldeptments", this.getAlldeptments());
		baseMap.put("deptmentunified", this.getdeptmentunified());
		baseMap.put("meetingtype", this.getmeetingtype());
		baseMap.put("meetinglevel", this.getmeetinglevel());
		baseMap.put("meetingroom", this.getMeetingRoom());
		baseMap.put("accessoriesType", this.getAccessoriesType());
		baseMap.put("videoApplicationPurpose", this.getVideoApplicationPurpose());
		baseMap.put("applicationPosition", this.getApplicationPosition());
		servletContext.setAttribute("basecode", baseMap);

		try {
			//加载全局变量
			Class<?> param = Class.forName("com.wafersystems.mrbs.GlobalParam");

			List<UnifiedParameter> list = parameterDao.findAll();
			for (UnifiedParameter unifiedParameter : list) {
				String fieldName = unifiedParameter.getParamKey().replace('-', '_').toLowerCase();
				if(unifiedParameter.getParamKey().equals("SMS-DB-PWD") || unifiedParameter.getParamKey().equals("SYNC-DATA-DB-PWD")){
					unifiedParameter.setValue(new String(decoder.decodeBuffer(unifiedParameter.getValue())));
				}
				Field field = param.getField(fieldName);
				Class<?> fieldType = field.getType();

				if(fieldType == boolean.class)
					field.setBoolean(null, Boolean.parseBoolean(unifiedParameter.getValue()));
				else if(fieldType == short.class)
					field.setShort(null, Short.parseShort(unifiedParameter.getValue()));
				else if(fieldType == int.class)
					field.setInt(null, Integer.parseInt(unifiedParameter.getValue()));
				else if(fieldType == long.class)
					field.setLong(null, Long.parseLong(unifiedParameter.getValue()));
				else if(fieldType == float.class)
					field.setFloat(null, Float.parseFloat(unifiedParameter.getValue()));
				else if(fieldType == double.class)
					field.setDouble(null, Double.parseDouble(unifiedParameter.getValue()));
				else if(fieldType == String.class)
					param.getField(fieldName).set(null, unifiedParameter.getValue());
			}

			//放置全局常量到Application中
			Class<?> constent = Class.forName("com.wafersystems.mrbs.GlobalConstent");
			Field[] fields = constent.getFields();
			String fieldName;
			for (Field field : fields) {
				fieldName = field.getName();
				if(!fieldName.equals("USER_LOGIN_SESSION") && !fieldName.equals("REPORT_DATA"))//忽略用户登录Session、ec的数据的常量
					servletContext.setAttribute(field.getName(),field.get(null));
			}
		} catch (ClassNotFoundException e) {
			logger.error("反射生成GlobalParam对象时出错", e);
		} catch (SecurityException e) {
			logger.error("反射得到GlobalParam属性时出错", e);
		} catch (NoSuchFieldException e) {
			logger.error("反射得到GlobalParam属性时出错", e);
		}catch (IllegalArgumentException e) {
			logger.error("设置GlobalParam参数时出错", e);
		} catch (IllegalAccessException e) {
			logger.error("设置GlobalParam参数时出错", e);
		} catch (IOException e) {
			logger.error("设置GlobalParam参数时出错", e);
		}
	}
	
	@Resource(type = VideoApplicationPurposeDao.class)
	public void setVideoApplicationPurposedao(
			VideoApplicationPurposeDao videoApplicationPurposedao) {
		this.videoApplicationPurposedao = videoApplicationPurposedao;
	}
	@Resource(type = ApplicationPositionDao.class)
	public void setApplicationPositionDao(
			ApplicationPositionDao applicationPositionDao) {
		this.applicationPositionDao = applicationPositionDao;
	}
	@Resource(type = AccessoriesTypeDao.class)
	public void setAccessoriesTypedao(AccessoriesTypeDao accessoriesTypedao) {
		this.accessoriesTypedao = accessoriesTypedao;
	}
	@Override
	public void setServletContext(ServletContext context) {
		this.servletContext = context;
	}

	@Resource(type = DepartmentDao.class)
	public void setDepartmentDao(DepartmentDao departmentDao) {
		this.departmentDao = departmentDao;

	}

	@Resource(type = MeetingTypeDao.class)
	public void setMeetingTypeDao(MeetingTypeDao meetingTypeDao) {
		this.meetingTypeDao = meetingTypeDao;
	}

	@Resource(type = MeetingLevelDao.class)
	public void setMeetingLevelDao(MeetingLevelDao meetingLevelDao) {
		this.meetingLevelDao = meetingLevelDao;
	}

	@Resource(type = MeetingRoomDao.class)
	public void setMeetingRoomDao(MeetingRoomDao meetingRoomDao) {
		this.meetingRoomDao = meetingRoomDao;
	}

	@Resource
	public void setParameterDao(UnifiedParameterDao parameterDao) {
		this.parameterDao = parameterDao;
	}

}
