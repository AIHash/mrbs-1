package com.wafersystems.mrbs.service.impl.icu;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.wafersystems.mcu.Packaging;
import com.wafersystems.mrbs.GlobalConstent;
import com.wafersystems.mrbs.GlobalParam;
import com.wafersystems.mrbs.dao.icu.ICUAuthorInfoDao;
import com.wafersystems.mrbs.dao.icu.ICUMonitorDao;
import com.wafersystems.mrbs.dao.icu.ICUPatientInfoDao;
import com.wafersystems.mrbs.dao.icu.ICUVisitAuthorInfoDao;
import com.wafersystems.mrbs.dao.icu.ICUVisitDao;
import com.wafersystems.mrbs.dao.icu.ICUVisitPatientInfoDao;
import com.wafersystems.mrbs.dao.icu.MeetingMobileDevicesDao;
import com.wafersystems.mrbs.dao.mas.MtTeleconDao;
import com.wafersystems.mrbs.dao.meeting.AccessoriesDao;
import com.wafersystems.mrbs.dao.meeting.AccessoriesTypeDao;
import com.wafersystems.mrbs.dao.meeting.Icd10DicDao;
import com.wafersystems.mrbs.dao.meeting.MeetingDao;
import com.wafersystems.mrbs.dao.meeting.MeetingLevelDao;
import com.wafersystems.mrbs.dao.meeting.MeetingMemberDao;
import com.wafersystems.mrbs.dao.meeting.MeetingTypeDao;
import com.wafersystems.mrbs.dao.user.DepartmentDao;
import com.wafersystems.mrbs.dao.user.UserDao;
import com.wafersystems.mrbs.integrate.haina.facade.HainaCall;
import com.wafersystems.mrbs.service.icu.ICUMonitorService;
import com.wafersystems.mrbs.service.impl.meeting.ApplicationOperateServiceImpl;
import com.wafersystems.mrbs.service.notice.factory.NoticeFactory;
import com.wafersystems.mrbs.vo.mas.MtTelecon;
import com.wafersystems.mrbs.vo.meeting.Accessories;
import com.wafersystems.mrbs.vo.meeting.AccessoriesType;
import com.wafersystems.mrbs.vo.meeting.ICD10DIC;
import com.wafersystems.mrbs.vo.meeting.ICUDepartment;
import com.wafersystems.mrbs.vo.meeting.ICUICD10;
import com.wafersystems.mrbs.vo.meeting.ICUMonitor;
import com.wafersystems.mrbs.vo.meeting.ICUVisitation;
import com.wafersystems.mrbs.vo.meeting.ICUVisitationAuthorInfo;
import com.wafersystems.mrbs.vo.meeting.ICUVisitationPatientInfo;
import com.wafersystems.mrbs.vo.meeting.Meeting;
import com.wafersystems.mrbs.vo.meeting.MeetingLevel;
import com.wafersystems.mrbs.vo.meeting.MeetingMember;
import com.wafersystems.mrbs.vo.meeting.MeetingMobileDevices;
import com.wafersystems.mrbs.vo.meeting.MeetingRoom;
import com.wafersystems.mrbs.vo.meeting.MeetingType;
import com.wafersystems.mrbs.vo.notice.UnifiedNotice;
import com.wafersystems.mrbs.vo.user.Department;
import com.wafersystems.mrbs.vo.user.UserInfo;
import com.wafersystems.util.DateUtil;
import com.wafersystems.util.NoticeInfo;
import com.wafersystems.util.StrUtil;

@Service
public class ICUMonitorServiceImpl implements ICUMonitorService{
	private static final Logger logger = LoggerFactory.getLogger(ApplicationOperateServiceImpl.class);
	private ICUMonitorDao iCUMonitorDao;
	private ICUVisitDao iCUVisitDao;
	private AccessoriesTypeDao accessoriesTypeDao;
	private AccessoriesDao accessoriesDao;
	private DepartmentDao departmentDao;
	private Icd10DicDao icd10DicDao;
	private ICUPatientInfoDao iCUPatientInfoDao;
	private ICUVisitPatientInfoDao iCUvisitPatientInfoDao;
	private MeetingTypeDao meetingTypeDao;
	private MeetingLevelDao meetingLevelDao;
	private ICUAuthorInfoDao iCUAuthorInfoDao;
	private ICUVisitAuthorInfoDao iCUVisitAuthorInfoDao;
	private UserDao userDao;
	private NoticeFactory noticefactory;//邮件通知服务
	private HainaCall hainaCall;
	private MeetingDao meetingDao;
	private MeetingMemberDao meetingMemberDao; 
	private NoticeFactory noticeFactory;
	private MtTeleconDao mtTeleconDao;
	private MeetingMobileDevicesDao meetingMobileDevicesDao;
	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

	/**
	 * 保存监护申请
	 * @param userinfo
	 * @param expectedTime
	 * @param files
	 * @param hainaDatas
	 * @param deptmentid
	 * @param icd10Dic
	 * @param application
	 * @return
	 */
	public ICUMonitor saveICUMonit(UserInfo userinfo,String expectedTime,String[] files,String[] hainaDatas, String[] deptmentid,String[] icd10Dic,ICUMonitor icuMonitor){

		try{
			//添加附件信息
			AccessoriesType accessoriesType = null;//附件类型表
			Set<Accessories> acces = new HashSet<Accessories>();
			icuMonitor.setExpectedTime(dateFormat.parse(expectedTime));
			logger.debug("申请时间为" + expectedTime);
			if(files != null && files.length > 0){
				for(int i = 0; i < files.length; i++){
					String fileinfo = files[i];
					String[] temp = fileinfo.split("@");
					if(temp.length > 1 && temp.length < 6){
						Accessories accessories = new Accessories() ;//附件信息表
						accessories.setName(temp[0]);
						accessories.setPath(temp[1]);
						accessories.setOwner(userinfo);
						//附件的类型
						Short id = Short.parseShort(temp[2]);
						accessoriesType = new AccessoriesType();
						if(id == 8){//其它类型的附件
							accessoriesType.setName(temp[4]);
							AccessoriesType typeName = accessoriesTypeDao.getAccessoriesTypeByName(accessoriesType.getName());
							if(typeName==null){
								short val = accessoriesTypeDao.getMaxAccessoriesType();
								accessoriesType.setValue(val++);
								accessoriesTypeDao.save(accessoriesType);
								accessoriesType.setId(accessoriesType.getId());
								accessoriesType.setValue(accessoriesType.getId());
							}else{
								accessoriesType.setId(typeName.getId());
							}						
						}else{
							accessoriesType.setId(id);
						}
						accessories.setType(accessoriesType);
						acces.add(accessories);
						logger.debug("非放射影像的附件有" + acces.size());
					}
				}
			}
			
			//处理海纳数据信息
			List<Accessories> accessories = null;//放射影像List
			String [] values = null;//用于和海纳关联
			if(hainaDatas != null && hainaDatas.length > 0){
		    	//放射影像的类型
		    	AccessoriesType accType = new AccessoriesType((short)2);
		    	int hainaSize = hainaDatas.length;
		    	accessories = new ArrayList<Accessories>(hainaSize);
		    	values = new String[hainaSize];
		    	for (int i = 0; i < hainaSize; i++) {
		    		String[] temps = hainaDatas[i].split("@");
		    		values[i] = temps[0];//此为海纳的study的id
		    		Accessories acce = new Accessories() ;//附件信息表
		    		acce.setName(temps[1]);
		    		acce.setOwner(userinfo);
		    		acce.setPath(temps[0] + System.currentTimeMillis());//此为海纳的study的id
		    		acce.setType(accType);
		    		accessories.add(acce);
				}
		    	logger.debug("总共有" + accessories.size() + "个放射影像");
		    	acces.addAll(accessories);
		    	accessoriesDao.saveBatchData(acces.toArray(new Accessories[acces.size()]));
		    }		
			icuMonitor.setAccessories(acces);
			logger.debug("总共有" + acces.size() + "个附件");
			
			//添加会诊科室
			Department deptment = null;//部门信息表
			ICUDepartment appDept = null;
		    if(deptmentid != null && deptmentid.length > 0){
		    	deptment = departmentDao.get(Integer.parseInt(deptmentid[0]));
		    	icuMonitor.setMainDept(deptment);
				if(deptmentid.length > 1){
					Set<ICUDepartment> depts  = new HashSet<ICUDepartment>(deptmentid.length);
					for(int i = 1; i < deptmentid.length; i++){
						appDept = new ICUDepartment();
						deptment = departmentDao.get(Integer.parseInt(deptmentid[i]));
				    	appDept.setDepartment(deptment);
				    	appDept.setIcumonitor(icuMonitor);
				    	depts.add(appDept);
					}
					icuMonitor.setDepts(depts);
					logger.debug("选择了" + depts.size() + "个部门进行参与重症监护");
				}
			}
			
			//添加ICD10
		    ICD10DIC icd10 = null;//icd10信息
		    ICUICD10 ic = null;
			if(icd10Dic != null && icd10Dic.length > 0){
				Set<ICUICD10> icds = new HashSet<ICUICD10>(icd10Dic.length);
				for(int i = 0 ; i < icd10Dic.length; i++) {
					ic = new ICUICD10();
					icd10 = icd10DicDao.get(Integer.parseInt(icd10Dic[i]));
			    	ic.setIcd(icd10);
			    	ic.setIcumonitor(icuMonitor);
			    	icds.add(ic);
				}
				icuMonitor.setIcuICD10(icds);
				logger.debug("选择了" + icds.size() + "个ICD10数据");
			}
		    
			//添加会议申请信息
			if(icuMonitor.getPatientInfo().getId() == null)
				//患者基本信息保存
				iCUPatientInfoDao.save(icuMonitor.getPatientInfo());
		    logger.debug("患者基本信息保存成功！");
			if(icuMonitor.getAuthorInfo().getId() == null)
				//病例提交人基本信息保存
				iCUAuthorInfoDao.save(icuMonitor.getAuthorInfo());
			logger.debug("重症监护申请提交人基本信息保存成功！");
		
			icuMonitor.setRequester(userinfo);//申请人
			icuMonitor.setApplicationTime(new Date());//申请时间
		    Short stats = 1;
		    icuMonitor.setState(stats);
		    icuMonitor.setAccessories(acces);		
			if(icuMonitor.getMeetingType()!=null&&icuMonitor.getMeetingType().getId()!=null){
				icuMonitor.setMeetingType(meetingTypeDao.get(icuMonitor.getMeetingType().getId()));
			}
			if(icuMonitor.getLevel()!=null&&icuMonitor.getLevel().getId()!=null){
				icuMonitor.setLevel(meetingLevelDao.get(icuMonitor.getLevel().getId()));
			}
			iCUMonitorDao.save(icuMonitor);
			logger.debug("重症监护信息保存成功！");
			
			//发送通知
			List<UserInfo> list=userDao.findTbyHql("From UserInfo where userType=2 and state=1");
			if(userinfo.getUserType() != null && userinfo.getUserType().getValue()!=GlobalConstent.USER_TYPE_MEETING_ADMIN){
				UnifiedNotice mailNotice = null;
				for(int i=0;i<list.size();i++) {
					UserInfo user = list.get(i);

					mailNotice = new UnifiedNotice();
					mailNotice.setTitle(NoticeInfo.getMessage("icuMonitor_notice_title"));
					mailNotice.setMessage(NoticeInfo.getMessage("icuMonitor_notice_content", icuMonitor));
					mailNotice.setReceiver(user.getMail());
					String url="";
					try {
						url = URLEncoder.encode("/meeadmdbd/meetingappsearchlist?id="+icuMonitor.getId(), "utf-8");
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
					url = GlobalParam.outter_access_ip + "/login/notice" + "?uid=" + user.getUserId() + "&pwd="+user.getPassword() + "&direct=" + url;
					mailNotice.setAcceptUrl(url);
					noticefactory.addMailNotice(mailNotice);
					
					/*smsNotice = new UnifiedNotice();
					smsNotice.setMessage(application.getRequester().getName() +"提交了远程会诊申请，请您审核");
					smsNotice.setTitle("短信通知");
					smsNotice.setReceiver(user.getMobile());
					Date sendTime = new Date(new Date().getTime() + GlobalConstent.TO_MANAGER_SMS_DELAY * 1000 * 60);
					smsNotice.setSendTime(sendTime);
					noticefactory.addSMSNotice(smsNotice);*/
					
					if(StringUtils.isNotBlank(user.getMobile())){
						MtTelecon mtTelecon = new MtTelecon();
						mtTelecon.setSmId("0");
						mtTelecon.setSrcId("0");
						mtTelecon.setMobile(user.getMobile());
						mtTelecon.setContent(icuMonitor.getRequester().getName() +"提交了《"+icuMonitor.getPurpose()+"》重症监护申请，请您审核");
						//Date sendTime = new Date(new Date().getTime() + GlobalConstent.TO_MANAGER_SMS_DELAY * 1000 * 60);
						mtTelecon.setSendTime(null);
						mtTelecon.setDataExpiredFlag(GlobalConstent.DATE_EXPIRED_FLAG_N);
						mtTelecon.setSmsServiceCode(GlobalConstent.SMS_SERVICE_CODE_001);
						mtTelecon.setState(GlobalConstent.NOTICE_NOT_SEND);
						mtTelecon.setResendTimes(0);
						mtTelecon.setAcceptSmsUserId(user.getUserId());
						mtTelecon.setApplicationId(icuMonitor.getId());					
						noticefactory.addMtTelecon(mtTelecon);
					}
				}
			}
			
			
			//设置海纳关联
				try{
					if(values != null && values.length > 0){
						hainaCall.setStudy(icuMonitor.getId().toString(), values);
						logger.debug("海纳影像设置成功！");
					}
				}catch(Exception e){
					logger.debug("海纳影像设置失败！");
				}												
			
		    }catch(Exception e){				
				logger.error("申请重症监护失败", e);
			}
			return icuMonitor;
	  
	}
	
	public ICUMonitor getICUMonitById(int id){
		return iCUMonitorDao.get(id);
	}
	/**
	 * add by wangzhenglin 更新，修改重症监护信息
	 * @param userinfo
	 * @param expectedTime
	 * @param files
	 * @param hainaDatas
	 * @param deptmentid
	 * @param icd10Dic
	 * @param application
	 */
	public void updateICUMonitor(UserInfo userinfo,String expectedTime,String[] files,String[] hainaDatas, String[] deptmentid,String[] icd10Dic,ICUMonitor iCUMonitor){
		try{
			  accessoriesDao.deleteAccessoriesByMonitorId(iCUMonitor.getId());
	    	  icd10DicDao.delIcdByMonitorId(iCUMonitor.getId());
	    	  iCUMonitorDao.delDeptsByMonitorId(iCUMonitor.getId());
			  //添加附件信息 
			  AccessoriesType accessoriesType = null;//附件类型表
			  Set<Accessories> acces = new HashSet<Accessories>();
			  iCUMonitor.setExpectedTime(dateFormat.parse(expectedTime));
			  logger.debug("申请时间为" + expectedTime);
		      if(files != null && files.length > 0){
				for(int i = 0; i < files.length; i++){
					String fileinfo = files[i];
					String[] temp = fileinfo.split("@");
					if(temp.length > 1 && temp.length < 6){
						Accessories accessories = new Accessories() ;//附件信息表
						accessories.setName(temp[0]);
						accessories.setPath(temp[1]);
						accessories.setOwner(userinfo);
						//附件的类型
						Short id = Short.parseShort(temp[2]);
						accessoriesType = new AccessoriesType();
						if(id == 8){//其它类型的附件
							accessoriesType.setName(temp[4]);
							AccessoriesType typeName = accessoriesTypeDao.getAccessoriesTypeByName(accessoriesType.getName());
							if(typeName==null){
								short val = accessoriesTypeDao.getMaxAccessoriesType();
								accessoriesType.setValue(val++);
								accessoriesTypeDao.save(accessoriesType);
								accessoriesType.setId(accessoriesType.getId());
								accessoriesType.setValue(accessoriesType.getId());
							}else{
								accessoriesType.setId(typeName.getId());
							}						
						}else{
							accessoriesType.setId(id);
						}
						accessories.setType(accessoriesType);
						acces.add(accessories);
						logger.debug("非放射影像的附件有" + acces.size());
					}
				}
			}
		      
		      //处理海纳数据信息
		      List<Accessories> accessories = null;//放射影像List
				String [] values = null;//用于和海纳关联
				if(hainaDatas != null && hainaDatas.length > 0){
			    	//放射影像的类型
			    	AccessoriesType accType = new AccessoriesType((short)2);
			    	int hainaSize = hainaDatas.length;
			    	accessories = new ArrayList<Accessories>(hainaSize);
			    	values = new String[hainaSize];
			    	for (int i = 0; i < hainaSize; i++) {
			    		String[] temps = hainaDatas[i].split("@");
			    		values[i] = temps[0];//此为海纳的study的id
			    		Accessories acce = new Accessories() ;//附件信息表
			    		acce.setName(temps[1]);//患者姓名
			    		acce.setOwner(userinfo);
			    		acce.setPath(temps[0]);//此为海纳的study的id
			    		acce.setType(accType);
			    		accessories.add(acce);
					}
			    	logger.debug("总共有" + accessories.size() + "个放射影像");
			    	acces.addAll(accessories);
			    	accessoriesDao.saveBatchData(acces.toArray(new Accessories[acces.size()]));
			    }
				iCUMonitor.setAccessories(acces);
				logger.debug("总共有" + acces.size() + "个附件");
			   //添加ICD10
				ICD10DIC icd10 = null;//icd10信息
			    ICUICD10 ic = null;
				if(icd10Dic != null && icd10Dic.length > 0){
					Set<ICUICD10> icds = new HashSet<ICUICD10>(icd10Dic.length);
					for(int i = 0 ; i < icd10Dic.length; i++) {
						ic = new ICUICD10();
				    	icd10 = new ICD10DIC(Integer.parseInt(icd10Dic[i]));
				    	ic.setIcd(icd10);
				    	ic.setIcumonitor(iCUMonitor);
				    	icds.add(ic);
					}
					iCUMonitor.setIcuICD10(icds);
					logger.debug("选择了" + icds.size() + "个ICD10数据");
				}
		      //添加会诊科室
				Department deptment = null;//部门信息表
			    ICUDepartment appDept = null;
			    if(deptmentid != null && deptmentid.length > 0){
			    	deptment = new Department(Integer.parseInt(deptmentid[0]));
			    	iCUMonitor.setMainDept(deptment);
					if(deptmentid.length > 1){
						Set<ICUDepartment> depts  = new HashSet<ICUDepartment>(deptmentid.length);
						for(int i = 1; i < deptmentid.length; i++){
							appDept = new ICUDepartment();
					    	deptment = new Department(Integer.parseInt(deptmentid[i]));
					    	appDept.setDepartment(deptment);
					    	appDept.setIcumonitor(iCUMonitor);
					    	depts.add(appDept);
						}
						iCUMonitor.setDepts(depts);
						logger.debug("选择了" + depts.size() + "个部门参与监护");
					}
				}
		      //添加会议申请信息
			    if(iCUMonitor.getPatientInfo().getId() == null){
			    	iCUPatientInfoDao.save(iCUMonitor.getPatientInfo());
					logger.debug("患者基本信息保存成功！");
			    }
				if(iCUMonitor.getAuthorInfo().getId() == null)
					iCUPatientInfoDao.save(iCUMonitor.getAuthorInfo());
				    logger.debug("病例提交人基本信息保存成功！");

				    iCUMonitor.setRequester(userinfo);//申请人
				    iCUMonitor.setApplicationTime(new Date());//申请时间
			    Short stats = 1;
			    iCUMonitor.setState(stats);
			    iCUMonitor.setAccessories(acces);
				iCUMonitorDao.update(iCUMonitor);
				logger.debug("重症监护信息修改成功！");
				//设置海纳关联
				try{
					if(values != null && values.length > 0){
						hainaCall.setStudy(iCUMonitor.getId().toString(), values);
						logger.debug("海纳影像设置成功！");
					}
				}catch(Exception e){
					logger.debug("海纳影像设置失败！");
				}
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	/**
	 * add by wangzhenglin 修改审核通过但是未结束的重症监护信息
	 * @param userinfo
	 * @param files
	 * @param hainaDatas
	 * @param expectedTime
	 * @param deptmentid
	 * @param icd10Dic
	 * @param iCUMonitor
	 * @param startTime
	 * @param endTime
	 * @param selectedUserIds
	 * @param meetingRoomId
	 * @throws Exception
	 */
	public void updateICUMonitor(UserInfo userinfo,String[] files,String[] hainaDatas, String expectedTime,
			String[] deptmentid,String[] icd10Dic,ICUMonitor iCUMonitor,String startTime,String endTime,
			String selectedUserIds,String meetingRoomId,String devicesNo)throws Exception{
		try{
			  accessoriesDao.deleteAccessoriesByMonitorId(iCUMonitor.getId());
	    	  icd10DicDao.delIcdByMonitorId(iCUMonitor.getId());
	    	  iCUMonitorDao.delDeptsByMonitorId(iCUMonitor.getId());
			  //添加附件信息 
			  AccessoriesType accessoriesType = null;//附件类型表
			  Set<Accessories> acces = new HashSet<Accessories>();
		      if(files != null && files.length > 0){
				for(int i = 0; i < files.length; i++){
					String fileinfo = files[i];
					String[] temp = fileinfo.split("@");
					if(temp.length > 1 && temp.length < 6){
						Accessories accessories = new Accessories() ;//附件信息表
						accessories.setName(temp[0]);
						accessories.setPath(temp[1]);
						accessories.setOwner(userinfo);
						//附件的类型
						Short id = Short.parseShort(temp[2]);
						accessoriesType = new AccessoriesType();
						if(id == 8){//其它类型的附件
							accessoriesType.setName(temp[4]);
							AccessoriesType typeName = accessoriesTypeDao.getAccessoriesTypeByName(accessoriesType.getName());
							if(typeName==null){
								short val = accessoriesTypeDao.getMaxAccessoriesType();
								accessoriesType.setValue(val++);
								accessoriesTypeDao.save(accessoriesType);
								accessoriesType.setId(accessoriesType.getId());
								accessoriesType.setValue(accessoriesType.getId());
							}else{
								accessoriesType.setId(typeName.getId());
							}						
						}else{
							accessoriesType.setId(id);
						}
						accessories.setType(accessoriesType);
						acces.add(accessories);
						logger.debug("非放射影像的附件有" + acces.size());
					}
				}
			}
		      
		      //处理海纳数据信息
		      List<Accessories> accessories = null;//放射影像List
				String [] values = null;//用于和海纳关联
				if(hainaDatas != null && hainaDatas.length > 0){
			    	//放射影像的类型
			    	AccessoriesType accType = new AccessoriesType((short)2);
			    	int hainaSize = hainaDatas.length;
			    	accessories = new ArrayList<Accessories>(hainaSize);
			    	values = new String[hainaSize];
			    	for (int i = 0; i < hainaSize; i++) {
			    		String[] temps = hainaDatas[i].split("@");
			    		values[i] = temps[0];//此为海纳的study的id
			    		Accessories acce = new Accessories() ;//附件信息表
			    		acce.setName(temps[1]);//患者姓名
			    		acce.setOwner(userinfo);
			    		acce.setPath(temps[0]);//此为海纳的study的id
			    		acce.setType(accType);
			    		accessories.add(acce);
					}
			    	logger.debug("总共有" + accessories.size() + "个放射影像");
			    	acces.addAll(accessories);
			    	accessoriesDao.saveBatchData(acces.toArray(new Accessories[acces.size()]));
			    }
				iCUMonitor.setAccessories(acces);
				logger.debug("总共有" + acces.size() + "个附件");
			   //添加ICD10
				ICD10DIC icd10 = null;//icd10信息
				ICUICD10 ic = null;
				if(icd10Dic != null && icd10Dic.length > 0){
					Set<ICUICD10> icds = new HashSet<ICUICD10>(icd10Dic.length);
					for(int i = 0 ; i < icd10Dic.length; i++) {
						ic = new ICUICD10();
				    	icd10 = new ICD10DIC(Integer.parseInt(icd10Dic[i]));
				    	ic.setIcd(icd10);
				    	ic.setIcumonitor(iCUMonitor);
				    	icds.add(ic);
					}
					iCUMonitor.setIcuICD10(icds);
					logger.debug("选择了" + icds.size() + "个ICD10数据");
				}
		      //添加会诊科室
				Department deptment = null;//部门信息表
				ICUDepartment appDept = null;
			    if(deptmentid != null && deptmentid.length > 0){
			    	deptment = new Department(Integer.parseInt(deptmentid[0]));
			    	iCUMonitor.setMainDept(deptment);
					if(deptmentid.length > 1){
						Set<ICUDepartment> depts  = new HashSet<ICUDepartment>(deptmentid.length);
						for(int i = 1; i < deptmentid.length; i++){
							appDept = new ICUDepartment();
					    	deptment = new Department(Integer.parseInt(deptmentid[i]));
					    	appDept.setDepartment(deptment);
					    	appDept.setIcumonitor(iCUMonitor);
					    	depts.add(appDept);
						}
						iCUMonitor.setDepts(depts);
						logger.debug("选择了" + depts.size() + "个部门进行会诊");
					}
				}
		      //添加会议申请信息
			    if(iCUMonitor.getPatientInfo().getId() == null){
			    	iCUPatientInfoDao.save(iCUMonitor.getPatientInfo());
					logger.debug("患者基本信息保存成功！");
			    }
				if(iCUMonitor.getAuthorInfo().getId() == null){
					iCUPatientInfoDao.save(iCUMonitor.getAuthorInfo());
			    	logger.debug("病例提交人基本信息保存成功！");
				}
					
		    	//开始时间
			    Date startDate = DateUtil.getDateTimeByHours(startTime);
			    //结束时间
			    Date endDate = DateUtil.getDateTimeByHours(endTime);
			    iCUMonitor.setExpectedTime(startDate);//期望时间
			    iCUMonitor.setRequester(userinfo);//申请人
			    iCUMonitor.setApplicationTime(new Date());//申请时间
			    iCUMonitor.setAccessories(acces);
				
			    iCUMonitor.setState(GlobalConstent.MEETING_APPLICATION_STATE_PASS);//审批默认通过
			    iCUMonitor.setStartTime(startDate);//会议开始时间
			    iCUMonitor.setEndTime(endDate);//会议结束时间
				
			    iCUMonitorDao.update(iCUMonitor);
				logger.debug("重症监护信息修改成功！");
				//设置海纳关联
				try{
					if(values != null && values.length > 0){
						hainaCall.setStudy(iCUMonitor.getId().toString(), values);
						logger.debug("海纳影像设置成功！");
					}
				}catch(Exception e){
					logger.debug("海纳影像设置失败！");
				}
				
				Meeting meeting = meetingDao.getMeetingByIcuMonitor(iCUMonitor.getId());
    			if(meeting!=null){
    				if(meeting.getEndTime() != endDate){
    					//如果修改时间，则修改MCU设备
        				Packaging pack = new Packaging();
        				pack.packagConferenceForSet(meeting);
    				}
    				short state = meeting.getState();
    				meetingMemberDao.delMembersByMeetingId(meeting.getId());
    				meeting.setiCUMonitorId(iCUMonitor);
    				meeting.setTitle(iCUMonitor.getPurpose());
    				meeting.setContent(iCUMonitor.getMedicalRecord());
    				meeting.setStartTime(startDate);
    				meeting.setEndTime(endDate);
    				meeting.setLevel(iCUMonitor.getLevel());
    				meeting.setMeetingType(iCUMonitor.getMeetingType());
    				meeting.setCreator(userinfo);
    				meeting.setHolder(userinfo);
        			
        			MeetingRoom meetingRoom = new MeetingRoom();
        			if (!StrUtil.isEmptyStr(meetingRoomId)) {
        				meetingRoom.setId(Integer.valueOf(meetingRoomId));
        			}else{
        				meetingRoom.setId(1);
        			}
        			meeting.setMeetingRoomId(meetingRoom);
        			meeting.setState(state);
        			this.meetingDao.update(meeting);
    			}else{
    				meeting = new Meeting();
    				
    				meeting.setiCUMonitorId(iCUMonitor);
    				meeting.setTitle(iCUMonitor.getPurpose());
    				meeting.setContent(iCUMonitor.getMedicalRecord());
    				meeting.setStartTime(startDate);
    				meeting.setEndTime(endDate);
    				meeting.setLevel(iCUMonitor.getLevel());
    				meeting.setMeetingType(iCUMonitor.getMeetingType());
    				meeting.setCreator(userinfo);
    				meeting.setHolder(userinfo);
    				meeting.setMeetingKind(GlobalConstent.APPLICATION_TYPE_ICUMONITOR);
    				
    				MeetingRoom meetingRoom = new MeetingRoom();
    				if (!StrUtil.isEmptyStr(meetingRoomId)) {
    					meetingRoom.setId(Integer.valueOf(meetingRoomId));
    				}else{
    					meetingRoom.setId(1);
    				}
    				meeting.setMeetingRoomId(meetingRoom);
    				
    				meeting.setState(Short.valueOf(GlobalConstent.MEETING_STATE_PENDING));
    				this.meetingDao.save(meeting);
    			}
				MeetingMobileDevices mm = meetingMobileDevicesDao.getMobileDevicesByMeetingId(meeting.getId());
				if(mm !=null){
					mm.setDevicesNo(devicesNo);
					meetingMobileDevicesDao.update(mm);
				}else{
					mm = new MeetingMobileDevices();
					mm.setDevicesNo(devicesNo);
					mm.setMeetingId(meeting.getId());
					meetingMobileDevicesDao.save(mm);
				}

    			//保存参与会诊的人员
    			MeetingMember meetingMember=null;
    			UserInfo user = null;
    			//所选择的参会人
    			String [] attendUserIdObjs = null;
    			if (!StrUtil.isEmptyStr(selectedUserIds)) {
    				if (selectedUserIds.endsWith("|")) {
    					selectedUserIds = selectedUserIds.substring(0, selectedUserIds.length() - 1);
    				}
    				attendUserIdObjs = selectedUserIds.split("\\|");
    			}
    			//添加参会人
    			if(attendUserIdObjs!=null&&attendUserIdObjs.length>0){
    				Date currDate = new Date();
    				//判断会议是否开始
    				boolean flag = startDate.getTime()<=currDate.getTime()&&endDate.getTime()>=currDate.getTime();
    				
    				for(String userId:attendUserIdObjs){
    					meetingMember=new MeetingMember();
    					meetingMember.setMeetingId(meeting);
    					user=userDao.get(userId);
    					meetingMember.setMember(user);
    					meetingMember.setAttendState(Short.valueOf(GlobalConstent.APPLICATION_STATE_NONE));
    					//当参会者为专家时，参会人数为1
    					if(user!=null&&user.getUserType()!=null&&user.getUserType().getValue().equals(GlobalConstent.USER_TYPE_PROFESSIONAL)){
    						meetingMember.setAttendNo((short)1);
    						//Bug #5690 审核病历讨论或视频讲座时邀请的专家默认参会
    						meetingMember.setAttendState(GlobalConstent.APPLICATION_STATE_ACCEPT);
    					}else{
    						meetingMember.setAttendNo((short)0);
    					}
    					//已经开始的审核病历讨论或视频讲座时邀请的专家默认参会
    					if(flag){
    						meetingMember.setAttendState(GlobalConstent.APPLICATION_STATE_ACCEPT);
    					}
    					this.meetingMemberDao.save(meetingMember);
    				}
    			}
				
		}catch(Exception e){
			logger.error("Error ICUMonitorServiceImpl.java.updateICUMonitor 会议管理员修改审核通过的重症监护失败",e);
			throw e;
		}
		
	}
	
	/**
	 * add by wangzhenglin 审核重症监护
	 * @param user
	 * @param meetingApplicationId
	 * @param startTime
	 * @param endTime
	 * @param selectedUserIds
	 * @param meetingRoomId
	 * @throws Throwable
	 */
	public void saveAuditIcuMonitor(UserInfo user,String icuId,String startTime,String endTime,String selectedUserIds,String meetingRoomId,String devicesNo) throws Throwable {
		try{
			logger.debug("Enter ICUMonitorServiceImpl.saveAuditIcuMonitor  icuId="+icuId);
			logger.debug("Enter ICUMonitorServiceImpl.saveAuditIcuMonitor  startTime="+startTime);
			logger.debug("Enter ICUMonitorServiceImpl.saveAuditIcuMonitor  endTime="+endTime);
			logger.debug("Enter ICUMonitorServiceImpl.saveAuditIcuMonitor  selectedUserIds="+selectedUserIds);
			ICUMonitor iCUMonitor=this.iCUMonitorDao.get(Integer.valueOf(icuId));
			Meeting meeting = new Meeting();
			//开始时间
			Date startDate = DateUtil.getDateTimeByHours(startTime);
			//结束时间
			Date endDate = DateUtil.getDateTimeByHours(endTime);
			meeting.setiCUMonitorId(iCUMonitor);
			meeting.setTitle(iCUMonitor.getPurpose());
			meeting.setContent(iCUMonitor.getMedicalRecord());
			meeting.setStartTime(startDate);
			meeting.setEndTime(endDate);
			meeting.setLevel(iCUMonitor.getLevel());
			meeting.setMeetingType(iCUMonitor.getMeetingType());
			meeting.setCreator(iCUMonitor.getRequester());
			meeting.setHolder(user);
			
			meeting.setMeetingKind(GlobalConstent.APPLICATION_TYPE_ICUMONITOR);

			MeetingRoom meetingRoom = new MeetingRoom();
			if (!StrUtil.isEmptyStr(meetingRoomId)) {
				meetingRoom.setId(Integer.valueOf(meetingRoomId));
			}else{
				meetingRoom.setId(1);
			}
			meeting.setMeetingRoomId(meetingRoom);

			meeting.setState(GlobalConstent.MEETING_STATE_PENDING);
			this.meetingDao.save(meeting);
			MeetingMobileDevices mm = new MeetingMobileDevices();
			mm.setDevicesNo(devicesNo);
			mm.setMeetingId(meeting.getId());
			meetingMobileDevicesDao.save(mm);
			//修改申请的状态
			iCUMonitor.setState(GlobalConstent.MEETING_APPLICATION_STATE_PASS);
			iCUMonitor.setStartTime(startDate);
			iCUMonitor.setEndTime(endDate);
			iCUMonitor.setExpectedTime(startDate);
			this.iCUMonitorDao.update(iCUMonitor);
			//所选择的参会人
			String [] attendUserIdObjs = null;
			if (!StrUtil.isEmptyStr(selectedUserIds)) {
				if (selectedUserIds.endsWith("|")) {
					selectedUserIds = selectedUserIds.substring(0, selectedUserIds.length() - 1);
				}
				attendUserIdObjs = selectedUserIds.split("\\|");
			}

			//保存参与会诊的人员
			MeetingMember meetingMember=null;
			UserInfo userInfo = null;
			UnifiedNotice mailNotice = null;
			//短信
			MtTelecon mtTelecon=null;
			//添加参会人
			if(attendUserIdObjs!=null&&attendUserIdObjs.length>0){
				for(String userId:attendUserIdObjs){
					meetingMember=new MeetingMember();
					meetingMember.setMeetingId(meeting);
					userInfo=userDao.get(userId);
					meetingMember.setMember(userInfo);
					meetingMember.setAttendState(Short.valueOf(GlobalConstent.APPLICATION_STATE_NONE));
					//当参会者为专家时，参会人数为1
					if(userInfo!=null&&userInfo.getUserType()!=null&&userInfo.getUserType().getValue().equals(GlobalConstent.USER_TYPE_PROFESSIONAL)){
						meetingMember.setAttendNo((short)1);
						//Bug #5690 审核病历讨论或视频讲座时邀请的专家默认参会
						meetingMember.setAttendState(GlobalConstent.APPLICATION_STATE_ACCEPT);
					}else{
						meetingMember.setAttendNo((short)0);
					}
					//审批通过当申请户默认我已接受
					if(userId.equals(iCUMonitor.getRequester().getUserId())){
						meetingMember.setAttendState(GlobalConstent.APPLICATION_STATE_ACCEPT);
					}
					this.meetingMemberDao.save(meetingMember);
				}
				//申请人
				userInfo = iCUMonitor.getRequester();
				//申请人为启用状态
				if(userInfo.getState()==1){
					//通知申请者，申请通过
					mailNotice = new UnifiedNotice();				
					mailNotice.setReceiver(userInfo.getMail());
					mailNotice.setTitle(NoticeInfo.getMessage("man_icuMonitor_notice_title"));
					mailNotice.setMessage(NoticeInfo.getNoticeMessage("man_icuMonitor_notice_content", meeting,userInfo.getName()));
					String url="";
					try {
						url = URLEncoder.encode("/unifiedindex/emailApplication?applicationid="+meeting.getId(), "utf-8");
					} catch (Exception e) {
						e.printStackTrace();
					}
					mailNotice.setAcceptUrl(GlobalParam.outter_access_ip + "/login/notice" + "?uid="+userInfo.getUserId()+"&pwd="+userInfo.getPassword()+"&direct="+url);
					this.noticeFactory.addMailNotice(mailNotice);
					
					SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
					//如果申请者有电话，给用户发送短信
					if(StringUtils.isNotBlank(userInfo.getMobile())){					
						mtTelecon = new MtTelecon();
						mtTelecon.setSmId("0");
						mtTelecon.setSrcId("0");
						mtTelecon.setMobile(userInfo.getMobile());
						mtTelecon.setContent(userInfo.getName()+"您好，您在" + dateFormat.format(iCUMonitor.getApplicationTime()) + "申请的重症监护《"+iCUMonitor.getPurpose()+"》已经审核通过");
						//Date sendTime = new Date(new Date().getTime() + GlobalConstent.TO_MANAGER_SMS_DELAY * 1000 * 60);
						mtTelecon.setSendTime(null);
						mtTelecon.setDataExpiredFlag(GlobalConstent.DATE_EXPIRED_FLAG_N);
						mtTelecon.setSmsServiceCode(GlobalConstent.SMS_SERVICE_CODE_002);
						mtTelecon.setState(GlobalConstent.NOTICE_NOT_SEND);
						mtTelecon.setResendTimes(0);
						this.noticeFactory.addMtTelecon(mtTelecon);
					}
				}
				
			}
		}catch(Exception e){
			logger.error("Error ICUMonitorServiceImpl.saveAuditIcuMonitor",e);
			throw e;
		}catch(Throwable e){
			logger.error("Error ICUMonitorServiceImpl.saveAuditIcuMonitor",e);
			throw e;
		}
	}
	/**
	 * 审核拒绝通过提交时间
	 * @param id
	 * @param userInfo
	 * @param refuseReason
	 */
	@Override
	public void refuseIcuMonitorPass(Integer id, UserInfo userInfo,String refuseReason) {
		try{
			logger.debug("Enter ICUMonitorServiceImpl.refuseIcuMonitorPass refuseReason="+refuseReason);
			ICUMonitor iCUMonitor=this.iCUMonitorDao.get(id);
			iCUMonitor.setRefuseReason(refuseReason);
			iCUMonitor.setState(Short.valueOf(GlobalConstent.MEETING_APPLICATION_STATE_REFUSED));
			iCUMonitorDao.update(iCUMonitor);
			//通知申请者，申请拒绝
			UserInfo ui=iCUMonitor.getRequester();
			//判断申请者为启用状态
			if(ui.getState()==1){
				UnifiedNotice notice=null;
				notice=new UnifiedNotice();			
				notice.setReceiver(ui.getMail());
				notice.setTitle(NoticeInfo.getMessage("man_icuMonitor_refuse_notice_title"));
				notice.setMessage(NoticeInfo.getNoticeMessage("man_icuMonitor_refuse_notice_content", iCUMonitor,ui.getName()));
				String url="";
				try {
					url = URLEncoder.encode("/unifiedindex/emailApplication?applicationid="+iCUMonitor.getId(), "utf-8");
				} catch (Exception e) {
					e.printStackTrace();
				}
				notice.setAcceptUrl(GlobalParam.outter_access_ip + "/login/notice" + "?uid="+ui.getUserId()+"&pwd="+ui.getPassword()+"&direct="+url);
				this.noticeFactory.addMailNotice(notice);
		
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				if(StringUtils.isNotBlank(ui.getMobile())){	
					MtTelecon mtTelecon = new MtTelecon();
					mtTelecon.setSmId("0");
					mtTelecon.setSrcId("0");
					mtTelecon.setMobile(ui.getMobile());
					mtTelecon.setContent(ui.getName()+"您好，您在" + dateFormat.format(iCUMonitor.getApplicationTime()) + "申请的重症监护《"+iCUMonitor.getPurpose()+"》审核未通过，拒绝原因是："+iCUMonitor.getRefuseReason()+"，请重新申请");
					//Date sendTime = new Date(new Date().getTime() + GlobalConstent.TO_MANAGER_SMS_DELAY * 1000 * 60);
					mtTelecon.setSendTime(null);
					mtTelecon.setDataExpiredFlag(GlobalConstent.DATE_EXPIRED_FLAG_N);
					mtTelecon.setSmsServiceCode(GlobalConstent.SMS_SERVICE_CODE_003);
					mtTelecon.setState(GlobalConstent.NOTICE_NOT_SEND);
					mtTelecon.setResendTimes(0);
					this.noticeFactory.addMtTelecon(mtTelecon);					
				}
			}
			
		}catch(Exception e){
			logger.error("Error ICUMonitorServiceImpl.refuseIcuMonitorPass",e);
		}
	}
	/**
	 * 审核通过后，管理员通过通知功能，通知指定的专家共同体
	 * @param appId
	 * @param expertNotice
	 * @param communityNotice
	 * @param outNotice
	 * @param expertsSelectedUserIds
	 * @param communitySelectedUserIds
	 * @param interSelectedUserIds
	 * @throws Exception
	 */
	public void sendIcuMonitorNotice(int appId, String expertNotice, String communityNotice, String outNotice,String expertsSelectedUserIds,String communitySelectedUserIds,String interSelectedUserIds)throws Exception{
		try{
			ICUMonitor ma = iCUMonitorDao.get(appId);
			UserInfo member = null;//参会人
			//所选择的院内专家
			String[] attendExpertUserIds = this.splitAttendMembersUserId(expertsSelectedUserIds);
			for (String expertUserId : attendExpertUserIds) {
				member = userDao.get(expertUserId);
				sendMeetingMsg(member, expertNotice, appId);
			}

			//所选择的共同体
			String[] attendCommunityUserIds = this.splitAttendMembersUserId(communitySelectedUserIds);
			for (String communityUserId : attendCommunityUserIds) {
				member = userDao.get(communityUserId);
				sendMeetingMsg(member, communityNotice, appId);
			}
			//所选择的院际专家
			String[] attendOutUserIds = this.splitAttendMembersUserId(interSelectedUserIds);
			for (String outUserId : attendOutUserIds) {
				member = userDao.get(outUserId);
				sendMeetingMsg(member, outNotice, appId);
			}
			//发送次数+1
			int times = ma.getNoticeSendTimes() == null ? 1 :  ma.getNoticeSendTimes() + 1 ;
			ma.setNoticeSendTimes(times);
			iCUMonitorDao.update(ma);
		}catch(Exception e){
			logger.error("sendApplicationNotice, 重症监护添加通知出错", e);
		}
	}
	private void sendMeetingMsg(UserInfo user, String msg, int id) throws UnsupportedEncodingException{
		try{
			ICUMonitor ma = iCUMonitorDao.get(id);
			//邮件
			UnifiedNotice mailNotice = new UnifiedNotice();
			mailNotice.setTitle(NoticeInfo.getMessage("successIcuMonitorNotice_title"));
			String url = URLEncoder.encode("/unifiedindex/emailMeeting?meetingid="	+ id, "utf-8");
			mailNotice.setAcceptUrl(GlobalParam.outter_access_ip  + "/login/notice" + "?uid=" + user.getUserId() + "&pwd=" + user.getPassword()+ "&direct=" + url);			
			
			//短信
			MtTelecon mtTelecon = new MtTelecon();	
			String srcId=String.valueOf(mtTeleconDao.getMaxSrcId());				
			mtTelecon.setSmId(srcId);
			mtTelecon.setSrcId(srcId);
			//Date sendTime = new Date(new Date().getTime() + GlobalConstent.TO_MANAGER_SMS_DELAY * 1000 * 60);
			mtTelecon.setSendTime(null);
			mtTelecon.setDataExpiredFlag(GlobalConstent.DATE_EXPIRED_FLAG_N);
			//判断接收人是否为专家
			if(user.getRole().getId()==3){
				mtTelecon.setSmsServiceCode(GlobalConstent.SMS_SERVICE_CODE_009);
			}else{
				mtTelecon.setSmsServiceCode(GlobalConstent.SMS_SERVICE_CODE_007);
			}
			//如果接收人为申请者
			if(user.getUserId().equals(ma.getRequester().getUserId())){
				mtTelecon.setSmsServiceCode(GlobalConstent.SMS_SERVICE_CODE_011);
			}
			
			mtTelecon.setState(GlobalConstent.NOTICE_NOT_SEND);
			mtTelecon.setResendTimes(0);
			mtTelecon.setAcceptSmsUserId(user.getUserId());
			mtTelecon.setApplicationId(id);			
			
			if(StringUtils.isNotBlank(user.getMail())){
				mailNotice.setReceiver(user.getMail());
				mailNotice.setMessage(msg);
				this.noticeFactory.addMailNotice(mailNotice);
			}		
			if(StringUtils.isNotBlank(user.getMobile())){	
				mtTelecon.setMobile(user.getMobile());
				mtTelecon.setContent(msg);
				this.noticeFactory.addMtTelecon(mtTelecon);				
			}
		}catch(Exception e){
			logger.error("ICUMonitorServiceImpl sendMsg error",e);
			e.printStackTrace();
		}catch(Throwable e){
			logger.error("ICUMonitorServiceImpl sendMsg error",e);
			e.printStackTrace();
		}
	}
	private String[] splitAttendMembersUserId(String userIds){
		String [] attendUserIdObjs;
		if (!StrUtil.isEmptyStr(userIds) && userIds.endsWith("|")) {
			userIds = userIds.substring(0, userIds.length() - 1);
			 attendUserIdObjs = userIds.split("\\|");
		}else{
			attendUserIdObjs = new String[0];
		}
		return attendUserIdObjs;
	}
	/////////////////get set ////////////////////////////////////////
	
	@Resource(type = ICUMonitorDao.class)
	public void setiCUMonitorDao(ICUMonitorDao iCUMonitorDao) {
		this.iCUMonitorDao = iCUMonitorDao;
	}
	 
	@Resource(type = MeetingDao.class)
    public void setMeetingDao(MeetingDao meetingDao) {
		this.meetingDao = meetingDao;
	}
	@Resource(type = MeetingMemberDao.class)
	public void setMeetingMemberDao(MeetingMemberDao meetingMemberDao) {
		this.meetingMemberDao = meetingMemberDao;
	}
	@Resource(type = NoticeFactory.class)
	public void setNoticeFactory(NoticeFactory noticeFactory) {
		this.noticeFactory = noticeFactory;
	}

	@Resource(type = AccessoriesTypeDao.class)
	public void setAccessoriesTypeDao(AccessoriesTypeDao accessoriesTypeDao) {
		this.accessoriesTypeDao = accessoriesTypeDao;
	}
	
    @Resource(type = AccessoriesDao.class)
	public void setAccessoriesDao(AccessoriesDao accessoriesDao) {
		this.accessoriesDao = accessoriesDao;
	}
	
    @Resource(type = DepartmentDao.class)
	public void setDepartmentDao(DepartmentDao departmentDao) {
		this.departmentDao = departmentDao;
	}
	
    @Resource(type = Icd10DicDao.class)
	public void setIcd10DicDao(Icd10DicDao icd10DicDao) {
		this.icd10DicDao = icd10DicDao;
	}
    
    @Resource(type = MeetingTypeDao.class)
	public void setMeetingTypeDao(MeetingTypeDao meetingTypeDao) {
		this.meetingTypeDao = meetingTypeDao;
	}
	
    @Resource(type = MeetingLevelDao.class)
	public void setMeetingLevelDao(MeetingLevelDao meetingLevelDao) {
		this.meetingLevelDao = meetingLevelDao;
	}

    @Resource
	public void setHainaCall(HainaCall hainaCall) {
		this.hainaCall = hainaCall;
	}

    @Resource(type = UserDao.class)
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
    
    @Resource(type = NoticeFactory.class)
	public void setNoticefactory(NoticeFactory noticefactory) {
		this.noticefactory = noticefactory;
	}
    
    @Resource(type = ICUPatientInfoDao.class)
	public void setiCUPatientInfoDao(ICUPatientInfoDao iCUPatientInfoDao) {
		this.iCUPatientInfoDao = iCUPatientInfoDao;
	}

    @Resource(type = ICUAuthorInfoDao.class)
    public void setiCUAuthorInfoDao(ICUAuthorInfoDao iCUAuthorInfoDao) {
    	this.iCUAuthorInfoDao = iCUAuthorInfoDao;
    }
    @Resource(type = ICUVisitDao.class)
	public void setICUVisitDao(ICUVisitDao iCUVisitDao) {
		this.iCUVisitDao = iCUVisitDao;
	}
	@Resource
	public void setMtTeleconDao(MtTeleconDao mtTeleconDao){
		this.mtTeleconDao=mtTeleconDao;
	}
   
    @Resource(type = ICUVisitPatientInfoDao.class)
    public void setICUVisitPatientInfoDao(ICUVisitPatientInfoDao iCUvisitPatientInfoDao) {
    	this.iCUvisitPatientInfoDao = iCUvisitPatientInfoDao;
    }
    @Resource(type = ICUVisitAuthorInfoDao.class)
    public void setICUVisitAuthorInfoDao(ICUVisitAuthorInfoDao iCUVisitAuthorInfoDao) {
    	this.iCUVisitAuthorInfoDao = iCUVisitAuthorInfoDao;
    }
    @Resource(type = MeetingMobileDevicesDao.class)
    public void setMeetingMobileDevicesDao(MeetingMobileDevicesDao meetingMobileDevicesDao) {
    	this.meetingMobileDevicesDao = meetingMobileDevicesDao;
    }

    
	@Override
	public void saveICUVisit(UserInfo userinfo, String expectedTime, ICUVisitation visitation) {
		try{
			//添加会议申请信息
			visitation.setExpectedTime(dateFormat.parse(expectedTime));
			if(visitation.getPatientInfo().getId() == null)
				//患者基本信息保存
				iCUvisitPatientInfoDao.save(visitation.getPatientInfo());
		    logger.debug("患者基本信息保存成功！");
			if(visitation.getAuthorInfo().getId() == null)
				//病例提交人基本信息保存
				iCUVisitAuthorInfoDao.save(visitation.getAuthorInfo());
			logger.debug("重症监护申请提交人基本信息保存成功！");
		
			visitation.setRequester(userinfo);//申请人
			visitation.setApplicationTime(new Date());//申请时间
		    Short stats = 1;
		    visitation.setState(stats);
		    iCUVisitDao.save(visitation);
			logger.debug("重症监护信息保存成功！");
			
			 //发送通知
			 List<UserInfo> list=userDao.findTbyHql("From UserInfo where userType=2 and state=1");
			UnifiedNotice mailNotice = null;
			UnifiedNotice smsNotice = null;
			  for(int i=0;i<list.size();i++) {
				UserInfo user = list.get(i);

				 
				mailNotice = new UnifiedNotice();
				mailNotice.setTitle(NoticeInfo.getMessage("pass_icuVisit_notice_pass_title"));
				mailNotice.setMessage(NoticeInfo.getMessage("pass_icuVisit_notice_pass_content", visitation));
				mailNotice.setReceiver(user.getMail());
				String url="";
				try {
					url = URLEncoder.encode("/meeadmdbd/meetingappsearchlist?id="+visitation.getId(), "utf-8");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				url = GlobalParam.outter_access_ip + "/login/notice" + "?uid=" + user.getUserId() + "&pwd="+user.getPassword() + "&direct=" + url;
				mailNotice.setAcceptUrl(url);
				noticefactory.addMailNotice(mailNotice);
				
				/*smsNotice = new UnifiedNotice();
				smsNotice.setMessage(visitation.getRequester().getName() +"提交了远程会诊申请，请您审核");
				smsNotice.setTitle("短信通知");
				smsNotice.setReceiver(user.getMobile());
				Date sendTime = new Date(new Date().getTime() + GlobalConstent.TO_MANAGER_SMS_DELAY * 1000 * 60);
				smsNotice.setSendTime(sendTime);
				noticefactory.addSMSNotice(smsNotice);
				
				if(StringUtils.isNotBlank(user.getMobile())){
					MtTelecon mtTelecon = new MtTelecon();
					mtTelecon.setSmId("0");
					mtTelecon.setSrcId("0");
					mtTelecon.setMobile(user.getMobile());
					mtTelecon.setContent(visitation.getRequester().getName() +"提交了目的为《"+visitation.getPurpose()+"》的病历讨论申请，请您审核");
					//Date sendTime = new Date(new Date().getTime() + GlobalConstent.TO_MANAGER_SMS_DELAY * 1000 * 60);
					mtTelecon.setSendTime(null);
					mtTelecon.setDataExpiredFlag(GlobalConstent.DATE_EXPIRED_FLAG_N);
					mtTelecon.setSmsServiceCode(GlobalConstent.SMS_SERVICE_CODE_001);
					mtTelecon.setState(GlobalConstent.NOTICE_NOT_SEND);
					mtTelecon.setResendTimes(0);
					mtTelecon.setAcceptSmsUserId(user.getUserId());
					mtTelecon.setApplicationId(visitation.getId());					
					noticefactory.addMtTelecon(mtTelecon);
				}*/
			 } 
			
			 											
			
		    }catch(Exception e){
			logger.debug("重症监护申请失败！");
			e.printStackTrace();
		}
	}

	@Override
	public ICUVisitation getICUVisitationById(Integer id) {
		return   iCUVisitDao.get(id);
	}

	@Override
	public void updateICUVisit(UserInfo userinfo, String expectedTime,ICUVisitation visitation) {
		try{
			visitation.setExpectedTime(dateFormat.parse(expectedTime));
			//添加会议申请信息
		    if(visitation.getPatientInfo().getId() == null){
		    	iCUPatientInfoDao.save(visitation.getPatientInfo());
				logger.debug("患者基本信息保存成功！");
		    }
			if(visitation.getAuthorInfo().getId() == null)
				iCUPatientInfoDao.save(visitation.getAuthorInfo());
			    logger.debug("病例提交人基本信息保存成功！");
			    visitation.setRequester(userinfo);//申请人
			    visitation.setApplicationTime(new Date());//申请时间
		    Short stats = 1;
		    visitation.setState(stats);
			this.iCUVisitDao.update(visitation);
			logger.debug("远程探视信息修改成功！");
		}catch(Exception e){
			logger.debug("远程探视信息修改失败！");
			e.printStackTrace();
		}
		
		
	}

	@Override
	public void refuseIcuVisitPass(Integer id, UserInfo userInfo,String refuseReason) {

		try{
			logger.debug("Enter MeeAdmDbdServiceImpl.refuseIcuVisitPass refuseReason="+refuseReason);
			ICUVisitation visit=this.iCUVisitDao.get(id);
			visit.setRefuseReason(refuseReason);
			visit.setState(Short.valueOf(GlobalConstent.MEETING_APPLICATION_STATE_REFUSED));
			//插入拒绝原因，修改状态
			iCUVisitDao.update(visit);
			//通知申请者，申请拒绝
			UserInfo ui=visit.getRequester();
			//判断申请者为启用状态
			if(ui.getState()==1){
				UnifiedNotice notice=null;
				notice=new UnifiedNotice();			
				notice.setReceiver(ui.getMail());
				notice.setTitle(NoticeInfo.getMessage("pass_icuVisit_notice_title"));
				notice.setMessage(NoticeInfo.getICUVisitMessage("pass_icuVisit_notice_content", visit));
				String url="";
				try {
					url = URLEncoder.encode("/unifiedindex/emailApplication?applicationid="+visit.getId(), "utf-8");
				} catch (Exception e) {
					e.printStackTrace();
				}
				notice.setAcceptUrl(GlobalParam.outter_access_ip + "/login/notice" + "?uid="+ui.getUserId()+"&pwd="+ui.getPassword()+"&direct="+url);
				this.noticeFactory.addMailNotice(notice);
		
				//SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				//发送短息
//				if(StringUtils.isNotBlank(ui.getMobile())){	
//					MtTelecon mtTelecon = new MtTelecon();
//					mtTelecon.setSmId("0");
//					mtTelecon.setSrcId("0");
//					mtTelecon.setMobile(ui.getMobile());
//					mtTelecon.setContent(ui.getName()+"您好，您在" + dateFormat.format(iCUMonitor.getApplicationTime()) + "申请的重症监护《"+iCUMonitor.getPurpose()+"》审核未通过，拒绝原因是："+iCUMonitor.getRefuseReason()+"，请重新申请");
//					//Date sendTime = new Date(new Date().getTime() + GlobalConstent.TO_MANAGER_SMS_DELAY * 1000 * 60);
//					mtTelecon.setSendTime(null);
//					mtTelecon.setDataExpiredFlag(GlobalConstent.DATE_EXPIRED_FLAG_N);
//					mtTelecon.setSmsServiceCode(GlobalConstent.SMS_SERVICE_CODE_003);
//					mtTelecon.setState(GlobalConstent.NOTICE_NOT_SEND);
//					mtTelecon.setResendTimes(0);
//					this.noticeFactory.addMtTelecon(mtTelecon);					
//				}
			}
			
		}catch(Exception e){
			logger.error("Error MeeAdmDbdServiceImpl.refuseMeeting",e);
		}
	
		
	}

	@Override
	public void saveAuditIcuVisit(UserInfo user, String icuId,String startTime, String endTime, String meetingRoomId,
			String mobileDevicesId) throws Throwable {
		try{
			logger.debug("Enter ICUMonitorServiceImpl.saveAuditIcuMonitor  icuVisitId="+icuId);
			logger.debug("Enter ICUMonitorServiceImpl.saveAuditIcuMonitor  startTime="+startTime);
			logger.debug("Enter ICUMonitorServiceImpl.saveAuditIcuMonitor  endTime="+endTime);
			logger.debug("Enter ICUMonitorServiceImpl.saveAuditIcuMonitor  mobileDevicesId="+mobileDevicesId);
			ICUVisitation visit=this.iCUVisitDao.get(Integer.valueOf(icuId));
			
			Meeting meeting = new Meeting();
			//开始时间
			Date startDate = DateUtil.getDateTimeByHours(startTime);
			//结束时间
			Date endDate = DateUtil.getDateTimeByHours(endTime);
			meeting.setiCUVisitationId(visit);
			meeting.setTitle("远程探视");
			meeting.setContent("远程探视");
			meeting.setStartTime(startDate);
			meeting.setEndTime(endDate);
			MeetingLevel obj = meetingLevelDao.get((short)1);
			meeting.setLevel(obj);
			MeetingType o = meetingTypeDao.get((short)1);
			meeting.setMeetingType(o);
			meeting.setCreator(visit.getRequester());
			meeting.setHolder(user);
			meeting.setMeetingKind(GlobalConstent.APPLICATION_TYPE_ICUVISIT);

			MeetingRoom meetingRoom = new MeetingRoom();
			if (!StrUtil.isEmptyStr(meetingRoomId)) {
				meetingRoom.setId(Integer.valueOf(meetingRoomId));
			}else{
				meetingRoom.setId(1);
			}
			meeting.setMeetingRoomId(meetingRoom);

			 meeting.setState(GlobalConstent.MEETING_STATE_PENDING);
			 this.meetingDao.save(meeting);
			 //保存移动设备编号
			 MeetingMobileDevices mmd = new MeetingMobileDevices();
			 mmd.setDevicesNo(mobileDevicesId);
			 mmd.setMeetingId(meeting.getId());
			 this.meetingMobileDevicesDao.save(mmd);
			//修改申请的状态
			visit.setState(GlobalConstent.MEETING_APPLICATION_STATE_PASS);
			visit.setStartTime(startDate);
			visit.setEndTime(endDate);
			visit.setExpectedTime(startDate);
			this.iCUVisitDao.update(visit);
			UserInfo ui=visit.getRequester();
			//判断申请者为启用状态
			if(ui.getState()==1){
				UnifiedNotice notice=null;
				notice=new UnifiedNotice();			
				notice.setReceiver(ui.getMail());
				notice.setTitle(NoticeInfo.getMessage("pass_icuVisit_notice_pass_title1"));
				notice.setMessage(NoticeInfo.getICUVisitPassMessage("pass_icuVisit_notice_pass_content1", visit));
				String url="";
				try {
					url = URLEncoder.encode("/unifiedindex/emailApplication?applicationid="+visit.getId(), "utf-8");
				} catch (Exception e) {
					e.printStackTrace();
				}
				notice.setAcceptUrl(GlobalParam.outter_access_ip + "/login/notice" + "?uid="+ui.getUserId()+"&pwd="+ui.getPassword()+"&direct="+url);
				this.noticeFactory.addMailNotice(notice);
	 
			}
				
		}catch(Exception e){
			logger.error("Error ICUMonitorServiceImpl.saveAuditIcuMonitor",e);
			throw e;
		} 
	}

	@Override
	public MeetingMobileDevices getMobileDevicesByMeetingId(Integer meetingId) {
		// TODO Auto-generated method stub
		MeetingMobileDevices obj=meetingMobileDevicesDao.getMobileDevicesByMeetingId(meetingId);
		return obj;
	}

	@Override
	public void EditICUVisitForPass(UserInfo userinfo, String expectedTime,ICUVisitation visit, String startTime, String endTime,
			String meetingRoomId,String mobileDevices) {
		try{ 
			     
		    	//开始时间
			    Date startDate = DateUtil.getDateTimeByHours(startTime);
			    //结束时间
			    Date endDate = DateUtil.getDateTimeByHours(endTime);
		        Meeting meeting = meetingDao.getMeetingByIcuVisit(visit.getId());
  			  if(meeting!=null){
  				if(meeting.getEndTime() != endDate){
					//如果修改时间，则修改MCU设备
    				Packaging.packagConferenceForSet(meeting);
				}
  				short state = meeting.getState();
  				meeting.setiCUVisitationId(visit);
  				meeting.setStartTime(startDate);
  				meeting.setCreator(userinfo);
  				meeting.setHolder(userinfo);
      			
      			MeetingRoom meetingRoom = new MeetingRoom();
      			if (!StrUtil.isEmptyStr(meetingRoomId)) {
      				meetingRoom.setId(Integer.valueOf(meetingRoomId));
      			}else{
      				meetingRoom.setId(1);
      			}
      			meeting.setMeetingRoomId(meetingRoom);
      			meeting.setState(state);
      			this.meetingDao.update(meeting);
      			MeetingMobileDevices mmd = meetingMobileDevicesDao.getMobileDevicesByMeetingId(meeting.getId());
      			mmd.setDevicesNo(mobileDevices);
      			meetingMobileDevicesDao.update(mmd);
      			visit.setExpectedTime(startDate);//期望时间
			    visit.setRequester(userinfo);//申请人
			    visit.setApplicationTime(new Date());//申请时间
			    visit.setState(GlobalConstent.MEETING_APPLICATION_STATE_PASS);//审批默认通过
			    visit.setStartTime(startDate);//会议开始时间
			    visit.setEndTime(endDate);//会议结束时间
			    this.iCUVisitDao.update(visit);
			  //添加会议申请信息
			    if(visit.getPatientInfo().getId() == null){
			    	iCUvisitPatientInfoDao.save(visit.getPatientInfo());
					logger.debug("患者基本信息保存成功！");
			    }
				if(visit.getAuthorInfo().getId() == null){
					iCUVisitAuthorInfoDao.save(visit.getAuthorInfo());
			    	logger.debug("病例提交人基本信息保存成功！");
				}
				logger.debug("远程探视修改成功！");
  			} 
		}catch(Exception e){
			e.printStackTrace();
			logger.error("Error ApplicationOperateServiceImpl.updateMeetingApplication 会议管理员修改审核通过的病历讨论失败",e);
		}
		
	}
		
	/**
	 * 管理员添加远程探视
	 * @param userinfo
	 * @param expectedTime
	 * @param visitation
	 */
	public void managerSaveICUVisit(UserInfo userinfo,String startTime,String endTime,
			String meetingRoomId,String mobileDevices,ICUVisitation visitation) {
		try{
			//添加会议申请信息
			visitation.setExpectedTime(this.dateFormat.parse(startTime));
			if(visitation.getPatientInfo().getId() == null)
				//患者基本信息保存
				iCUvisitPatientInfoDao.save(visitation.getPatientInfo());
		    logger.debug("患者基本信息保存成功！");
			if(visitation.getAuthorInfo().getId() == null)
				//病例提交人基本信息保存
				iCUVisitAuthorInfoDao.save(visitation.getAuthorInfo());
			logger.debug("重症监护申请提交人基本信息保存成功！");
		
			visitation.setRequester(userinfo);//申请人
			visitation.setApplicationTime(new Date());//申请时间
		    visitation.setState(GlobalConstent.MEETING_APPLICATION_STATE_PASS);
		    visitation.setStartTime(this.dateFormat.parse(startTime));
		    visitation.setEndTime(this.dateFormat.parse(endTime));
		    iCUVisitDao.save(visitation);
			logger.debug("重症监护信息保存成功！");
			
			Meeting meeting = new Meeting();
			//开始时间
			Date startDate = DateUtil.getDateTimeByHours(startTime);
			//结束时间
			Date endDate = DateUtil.getDateTimeByHours(endTime);
			meeting.setiCUVisitationId(visitation);
			meeting.setTitle("远程探视");
			meeting.setContent("远程探视");
			meeting.setStartTime(startDate);
			meeting.setEndTime(endDate);
			MeetingLevel obj = meetingLevelDao.get((short)1);
			meeting.setLevel(obj);
			MeetingType o = meetingTypeDao.get((short)1);
			meeting.setMeetingType(o);
			meeting.setCreator(visitation.getRequester());
			meeting.setHolder(userinfo);
			meeting.setMeetingKind(GlobalConstent.APPLICATION_TYPE_ICUVISIT);

			MeetingRoom meetingRoom = new MeetingRoom();
			if (!StrUtil.isEmptyStr(meetingRoomId)) {
				meetingRoom.setId(Integer.valueOf(meetingRoomId));
			}else{
				meetingRoom.setId(1);
			}
			meeting.setMeetingRoomId(meetingRoom);

			 meeting.setState(GlobalConstent.MEETING_STATE_PENDING);
			 this.meetingDao.save(meeting);
			//保存移动设备编号
			 MeetingMobileDevices mmd = new MeetingMobileDevices();
			 mmd.setDevicesNo(mobileDevices);
			 mmd.setMeetingId(meeting.getId());
			 this.meetingMobileDevicesDao.save(mmd);
			 //发送通知
			List<UserInfo> list=userDao.findTbyHql("From UserInfo where userType=2 and state=1");
			UnifiedNotice mailNotice = null;
			UnifiedNotice smsNotice = null;
			for(int i=0;i<list.size();i++) {
				UserInfo user = list.get(i);


				mailNotice = new UnifiedNotice();
				mailNotice.setTitle(NoticeInfo.getMessage("pass_icuVisit_notice_pass_title"));
				mailNotice.setMessage(NoticeInfo.getMessage("pass_icuVisit_notice_pass_content", visitation));
				mailNotice.setReceiver(user.getMail());
				String url="";
				try {
					url = URLEncoder.encode("/meeadmdbd/meetingappsearchlist?id="+visitation.getId(), "utf-8");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				url = GlobalParam.outter_access_ip + "/login/notice" + "?uid=" + user.getUserId() + "&pwd="+user.getPassword() + "&direct=" + url;
				mailNotice.setAcceptUrl(url);
				noticefactory.addMailNotice(mailNotice);
				
				/*smsNotice = new UnifiedNotice();
				smsNotice.setMessage(visitation.getRequester().getName() +"提交了远程会诊申请，请您审核");
				smsNotice.setTitle("短信通知");
				smsNotice.setReceiver(user.getMobile());
				Date sendTime = new Date(new Date().getTime() + GlobalConstent.TO_MANAGER_SMS_DELAY * 1000 * 60);
				smsNotice.setSendTime(sendTime);
				noticefactory.addSMSNotice(smsNotice);
				
				if(StringUtils.isNotBlank(user.getMobile())){
					MtTelecon mtTelecon = new MtTelecon();
					mtTelecon.setSmId("0");
					mtTelecon.setSrcId("0");
					mtTelecon.setMobile(user.getMobile());
					mtTelecon.setContent(visitation.getRequester().getName() +"提交了目的为《"+visitation.getPurpose()+"》的病历讨论申请，请您审核");
					//Date sendTime = new Date(new Date().getTime() + GlobalConstent.TO_MANAGER_SMS_DELAY * 1000 * 60);
					mtTelecon.setSendTime(null);
					mtTelecon.setDataExpiredFlag(GlobalConstent.DATE_EXPIRED_FLAG_N);
					mtTelecon.setSmsServiceCode(GlobalConstent.SMS_SERVICE_CODE_001);
					mtTelecon.setState(GlobalConstent.NOTICE_NOT_SEND);
					mtTelecon.setResendTimes(0);
					mtTelecon.setAcceptSmsUserId(user.getUserId());
					mtTelecon.setApplicationId(visitation.getId());					
					noticefactory.addMtTelecon(mtTelecon);
				}*/
			} 
			
			 											
			
		    }catch(Exception e){
			logger.debug("重症监护申请失败！");
			e.printStackTrace();
		}
	}

	 
}
