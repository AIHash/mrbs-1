package com.wafersystems.mrbs.service.impl.meeting;

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
import com.wafersystems.mrbs.dao.meeting.AccessoriesDao;
import com.wafersystems.mrbs.dao.meeting.AccessoriesTypeDao;
import com.wafersystems.mrbs.dao.meeting.ApplicationAuthorInfoDao;
import com.wafersystems.mrbs.dao.meeting.ApplicationPatientInfoDao;
import com.wafersystems.mrbs.dao.meeting.Icd10DicDao;
import com.wafersystems.mrbs.dao.meeting.MeetingApplicationDao;
import com.wafersystems.mrbs.dao.meeting.MeetingDao;
import com.wafersystems.mrbs.dao.meeting.MeetingLevelDao;
import com.wafersystems.mrbs.dao.meeting.MeetingMemberDao;
import com.wafersystems.mrbs.dao.meeting.MeetingTypeDao;
import com.wafersystems.mrbs.dao.meeting.VideoMeetingAppDao;
import com.wafersystems.mrbs.dao.user.DepartmentDao;
import com.wafersystems.mrbs.dao.user.UserDao;
import com.wafersystems.mrbs.integrate.haina.facade.HainaCall;
import com.wafersystems.mrbs.logmng.ann.MrbsLog;
import com.wafersystems.mrbs.service.meeting.ApplicationOperateService;
import com.wafersystems.mrbs.service.notice.factory.NoticeFactory;
import com.wafersystems.mrbs.vo.mas.MtTelecon;
import com.wafersystems.mrbs.vo.meeting.Accessories;
import com.wafersystems.mrbs.vo.meeting.AccessoriesType;
import com.wafersystems.mrbs.vo.meeting.ApplicationDepartment;
import com.wafersystems.mrbs.vo.meeting.ApplicationICD10;
import com.wafersystems.mrbs.vo.meeting.ICD10DIC;
import com.wafersystems.mrbs.vo.meeting.Meeting;
import com.wafersystems.mrbs.vo.meeting.MeetingApplication;
import com.wafersystems.mrbs.vo.meeting.MeetingLevel;
import com.wafersystems.mrbs.vo.meeting.MeetingMember;
import com.wafersystems.mrbs.vo.meeting.MeetingRoom;
import com.wafersystems.mrbs.vo.meeting.MeetingType;
import com.wafersystems.mrbs.vo.meeting.VideoMeetingApp;
import com.wafersystems.mrbs.vo.notice.UnifiedNotice;
import com.wafersystems.mrbs.vo.user.Department;
import com.wafersystems.mrbs.vo.user.UserInfo;
import com.wafersystems.util.DateUtil;
import com.wafersystems.util.NoticeInfo;
import com.wafersystems.util.StrUtil;

@Service
public class ApplicationOperateServiceImpl implements ApplicationOperateService  {
	private static final Logger logger = LoggerFactory.getLogger(ApplicationOperateServiceImpl.class);
	private AccessoriesTypeDao accessoriesTypeDao;
	private AccessoriesDao accessoriesDao;
	private DepartmentDao departmentDao;
	private Icd10DicDao icd10DicDao;
	private ApplicationPatientInfoDao applicationPatientInfoDao;
	private ApplicationAuthorInfoDao applicationAuthorInfoDao;
	private MeetingTypeDao meetingTypeDao;
	private MeetingLevelDao meetingLevelDao;
	private MeetingApplicationDao meetingApplicationDao;
	private VideoMeetingAppDao videoMeetingAppDao;
	private UserDao userDao;
	private HainaCall hainaCall;
	private NoticeFactory noticefactory;//邮件通知服务
	private MeetingDao meetingDao;
	private MeetingMemberDao meetingMemberDao; 
	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	@Override
	@MrbsLog(desc="group.meetingApplication_create")
	public MeetingApplication saveMeetingApplication(UserInfo userinfo,String expectedTime,String[] files,String[] hainaDatas, String[] deptmentid,String[] icd10Dic,MeetingApplication application){
		try{
			//添加附件信息
			AccessoriesType accessoriesType = null;//附件类型表
			Set<Accessories> acces = new HashSet<Accessories>();
			application.setExpectedTime(dateFormat.parse(expectedTime));
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
			application.setAccessories(acces);
			logger.debug("总共有" + acces.size() + "个附件");
			
			//添加会诊科室
			Department deptment = null;//部门信息表
		    ApplicationDepartment appDept = null;
		    if(deptmentid != null && deptmentid.length > 0){
		    	deptment = departmentDao.get(Integer.parseInt(deptmentid[0]));
		    	application.setMainDept(deptment);
				if(deptmentid.length > 1){
					Set<ApplicationDepartment> depts  = new HashSet<ApplicationDepartment>(deptmentid.length);
					for(int i = 1; i < deptmentid.length; i++){
						appDept = new ApplicationDepartment();
						deptment = departmentDao.get(Integer.parseInt(deptmentid[i]));
				    	appDept.setDepartment(deptment);
				    	appDept.setMeetingApplication(application);
				    	depts.add(appDept);
					}
					application.setDepts(depts);
					logger.debug("选择了" + depts.size() + "个部门进行病历讨论");
				}
			}
			
			//添加ICD10
		    ICD10DIC icd10 = null;//icd10信息
		    ApplicationICD10 ic = null;
			if(icd10Dic != null && icd10Dic.length > 0){
				Set<ApplicationICD10> icds = new HashSet<ApplicationICD10>(icd10Dic.length);
				for(int i = 0 ; i < icd10Dic.length; i++) {
					ic = new ApplicationICD10();
					icd10 = icd10DicDao.get(Integer.parseInt(icd10Dic[i]));
			    	ic.setIcd(icd10);
			    	ic.setMeetingApplication(application);
			    	icds.add(ic);
				}
				application.setApplicationICD10s(icds);
				logger.debug("选择了" + icds.size() + "个ICD10数据");
			}
		    
			//添加会议申请信息
			if(application.getPatientInfo().getId() == null)
				//患者基本信息保存
				applicationPatientInfoDao.save(application.getPatientInfo());
		    logger.debug("患者基本信息保存成功！");
			if(application.getAuthorInfo().getId() == null)
				//病例提交人基本信息保存
				applicationAuthorInfoDao.save(application.getAuthorInfo());
			logger.debug("病例提交人基本信息保存成功！");
		
		    application.setRequester(userinfo);//申请人
		    application.setApplicationTime(new Date());//申请时间
		    Short stats = 1;
		    application.setState(stats);
			application.setAccessories(acces);		
			if(application.getMeetingType()!=null&&application.getMeetingType().getId()!=null){
				application.setMeetingType(meetingTypeDao.get(application.getMeetingType().getId()));
			}
			if(application.getLevel()!=null&&application.getLevel().getId()!=null){
				application.setLevel(meetingLevelDao.get(application.getLevel().getId()));
			}
			meetingApplicationDao.save(application);
			logger.debug("病历讨论信息保存成功！");
			
			//发送通知
			List<UserInfo> list=userDao.findTbyHql("From UserInfo where userType=2 and state=1");
			UnifiedNotice mailNotice = null;
			for(int i=0;i<list.size();i++) {
				UserInfo user = list.get(i);

				mailNotice = new UnifiedNotice();
				mailNotice.setTitle(NoticeInfo.getMessage("application_notice_title"));
				mailNotice.setMessage(NoticeInfo.getMessage("application_notice_content", application));
				mailNotice.setReceiver(user.getMail());
				String url="";
				try {
					url = URLEncoder.encode("/meeadmdbd/meetingappsearchlist?id="+application.getId(), "utf-8");
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
					mtTelecon.setContent(application.getRequester().getName() +"提交了目的为《"+application.getPurpose()+"》的病历讨论申请，请您审核");
					//Date sendTime = new Date(new Date().getTime() + GlobalConstent.TO_MANAGER_SMS_DELAY * 1000 * 60);
					mtTelecon.setSendTime(null);
					mtTelecon.setDataExpiredFlag(GlobalConstent.DATE_EXPIRED_FLAG_N);
					mtTelecon.setSmsServiceCode(GlobalConstent.SMS_SERVICE_CODE_001);
					mtTelecon.setState(GlobalConstent.NOTICE_NOT_SEND);
					mtTelecon.setResendTimes(0);
					mtTelecon.setAcceptSmsUserId(user.getUserId());
					mtTelecon.setApplicationId(application.getId());					
					noticefactory.addMtTelecon(mtTelecon);
				}
			}
			
			//设置海纳关联
				try{
					if(values != null && values.length > 0){
						hainaCall.setStudy(application.getId().toString(), values);
						logger.debug("海纳影像设置成功！");
					}
				}catch(Exception e){
					logger.debug("海纳影像设置失败！");
				}												
			
		    }catch(Exception e){				
				logger.error("申请病历讨论失败", e);
			}
			return application;
	  }
	
	@Override
	@MrbsLog(desc="group.meetingApplication_update")
    public void updateMeetingApplication(UserInfo userinfo,String expectedTime,String[] files,String[] hainaDatas, String[] deptmentid,String[] icd10Dic,MeetingApplication application){
		try{
			  accessoriesDao.deleteAccessoriesByAppId(application.getId());
	    	  icd10DicDao.delIcdByAppId(application.getId());
	    	  meetingApplicationDao.delDeptsByAppId(application.getId());
			  //添加附件信息 
			  AccessoriesType accessoriesType = null;//附件类型表
			  Set<Accessories> acces = new HashSet<Accessories>();
			  application.setExpectedTime(dateFormat.parse(expectedTime));
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
				application.setAccessories(acces);
				logger.debug("总共有" + acces.size() + "个附件");
			   //添加ICD10
				ICD10DIC icd10 = null;//icd10信息
			    ApplicationICD10 ic = null;
				if(icd10Dic != null && icd10Dic.length > 0){
					Set<ApplicationICD10> icds = new HashSet<ApplicationICD10>(icd10Dic.length);
					for(int i = 0 ; i < icd10Dic.length; i++) {
						ic = new ApplicationICD10();
				    	icd10 = new ICD10DIC(Integer.parseInt(icd10Dic[i]));
				    	ic.setIcd(icd10);
				    	ic.setMeetingApplication(application);
				    	icds.add(ic);
					}
					application.setApplicationICD10s(icds);
					logger.debug("选择了" + icds.size() + "个ICD10数据");
				}
		      //添加会诊科室
				Department deptment = null;//部门信息表
			    ApplicationDepartment appDept = null;
			    if(deptmentid != null && deptmentid.length > 0){
			    	deptment = new Department(Integer.parseInt(deptmentid[0]));
			    	application.setMainDept(deptment);
					if(deptmentid.length > 1){
						Set<ApplicationDepartment> depts  = new HashSet<ApplicationDepartment>(deptmentid.length);
						for(int i = 1; i < deptmentid.length; i++){
							appDept = new ApplicationDepartment();
					    	deptment = new Department(Integer.parseInt(deptmentid[i]));
					    	appDept.setDepartment(deptment);
					    	appDept.setMeetingApplication(application);
					    	depts.add(appDept);
						}
						application.setDepts(depts);
						logger.debug("选择了" + depts.size() + "个部门进行会诊");
					}
				}
		      //添加会议申请信息
			    if(application.getPatientInfo().getId() == null){
					applicationPatientInfoDao.save(application.getPatientInfo());
					logger.debug("患者基本信息保存成功！");
			    }
				if(application.getAuthorInfo().getId() == null)
					applicationAuthorInfoDao.save(application.getAuthorInfo());
				    logger.debug("病例提交人基本信息保存成功！");

			    application.setRequester(userinfo);//申请人
			    application.setApplicationTime(new Date());//申请时间
			    Short stats = 1;
			    application.setState(stats);
				application.setAccessories(acces);
				meetingApplicationDao.update(application);
				logger.debug("远程会诊信息修改成功！");
				//设置海纳关联
				try{
					if(values != null && values.length > 0){
						hainaCall.setStudy(application.getId().toString(), values);
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
	 * 会议管理员修改病历讨论
	 * @param userinfo 添加用户
	 * @param files 附件
	 * @param hainaDatas 海纳附件
	 * @param deptmentid 科室
	 * @param icd10Dic icd初诊
	 * @param application 病历讨论
	 * @param startTime 审核开始时间
	 * @param endTime 审核结束时间
	 * @param selectedUserIds 受邀人
	 * @param meetingRoomId 会议室
	 */
	public void updateMeetingApplication(UserInfo userinfo,String[] files,String[] hainaDatas, String expectedTime,
			String[] deptmentid,String[] icd10Dic,MeetingApplication application,String startTime,String endTime,
			String selectedUserIds,String meetingRoomId)throws Exception{
		try{
			  accessoriesDao.deleteAccessoriesByAppId(application.getId());
	    	  icd10DicDao.delIcdByAppId(application.getId());
	    	  meetingApplicationDao.delDeptsByAppId(application.getId());
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
				application.setAccessories(acces);
				logger.debug("总共有" + acces.size() + "个附件");
			   //添加ICD10
				ICD10DIC icd10 = null;//icd10信息
			    ApplicationICD10 ic = null;
				if(icd10Dic != null && icd10Dic.length > 0){
					Set<ApplicationICD10> icds = new HashSet<ApplicationICD10>(icd10Dic.length);
					for(int i = 0 ; i < icd10Dic.length; i++) {
						ic = new ApplicationICD10();
				    	icd10 = new ICD10DIC(Integer.parseInt(icd10Dic[i]));
				    	ic.setIcd(icd10);
				    	ic.setMeetingApplication(application);
				    	icds.add(ic);
					}
					application.setApplicationICD10s(icds);
					logger.debug("选择了" + icds.size() + "个ICD10数据");
				}
		      //添加会诊科室
				Department deptment = null;//部门信息表
			    ApplicationDepartment appDept = null;
			    if(deptmentid != null && deptmentid.length > 0){
			    	deptment = new Department(Integer.parseInt(deptmentid[0]));
			    	application.setMainDept(deptment);
					if(deptmentid.length > 1){
						Set<ApplicationDepartment> depts  = new HashSet<ApplicationDepartment>(deptmentid.length);
						for(int i = 1; i < deptmentid.length; i++){
							appDept = new ApplicationDepartment();
					    	deptment = new Department(Integer.parseInt(deptmentid[i]));
					    	appDept.setDepartment(deptment);
					    	appDept.setMeetingApplication(application);
					    	depts.add(appDept);
						}
						application.setDepts(depts);
						logger.debug("选择了" + depts.size() + "个部门进行会诊");
					}
				}
		      //添加会议申请信息
			    if(application.getPatientInfo().getId() == null){
					applicationPatientInfoDao.save(application.getPatientInfo());
					logger.debug("患者基本信息保存成功！");
			    }
				if(application.getAuthorInfo().getId() == null){
					applicationAuthorInfoDao.save(application.getAuthorInfo());
			    	logger.debug("病例提交人基本信息保存成功！");
				}
					
		    	//开始时间
			    Date startDate = DateUtil.getDateTimeByHours(startTime);
			    //结束时间
			    Date endDate = DateUtil.getDateTimeByHours(endTime);
			    application.setExpectedTime(startDate);//期望时间
			    application.setRequester(userinfo);//申请人
			    application.setApplicationTime(new Date());//申请时间
				application.setAccessories(acces);
				
				application.setState(GlobalConstent.MEETING_APPLICATION_STATE_PASS);//审批默认通过
				application.setStartTime(startDate);//会议开始时间
				application.setEndTime(endDate);//会议结束时间
				
				meetingApplicationDao.update(application);
				logger.debug("远程会诊信息修改成功！");
				//设置海纳关联
				try{
					if(values != null && values.length > 0){
						hainaCall.setStudy(application.getId().toString(), values);
						logger.debug("海纳影像设置成功！");
					}
				}catch(Exception e){
					logger.debug("海纳影像设置失败！");
				}
				
				Meeting meeting = meetingDao.getMeetingByApplicationId(application.getId());
    			if(meeting!=null){
    				if(meeting.getEndTime().getTime() != endDate.getTime()){
    					//如果修改时间，则修改MCU设备
    					meeting.setEndTime(endDate);
        				Packaging.packagConferenceForSet(meeting);
    				}
    				short state = meeting.getState();
    				meetingMemberDao.delMembersByMeetingId(meeting.getId());
    				meeting.setApplicationId(application);
    				meeting.setTitle(application.getPurpose());
    				meeting.setContent(application.getMedicalRecord());
    				meeting.setStartTime(startDate);
    				meeting.setLevel(application.getLevel());
    				meeting.setMeetingType(application.getMeetingType());
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
    				
    				meeting.setApplicationId(application);
    				meeting.setTitle(application.getPurpose());
    				meeting.setContent(application.getMedicalRecord());
    				meeting.setStartTime(startDate);
    				meeting.setEndTime(endDate);
    				meeting.setLevel(application.getLevel());
    				meeting.setMeetingType(application.getMeetingType());
    				meeting.setCreator(userinfo);
    				meeting.setHolder(userinfo);
    				meeting.setMeetingKind(GlobalConstent.APPLICATION_TYPE_VIDEOLECTURES);
    				
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
			logger.error("Error ApplicationOperateServiceImpl.updateMeetingApplication 会议管理员修改审核通过的病历讨论失败",e);
			throw e;
		}
		
	}
	
	/**
	 * 会议管理员补录病历讨论
	 * @param userinfo 添加用户
	 * @param files 附件
	 * @param hainaDatas 海纳附件
	 * @param deptmentid 科室
	 * @param icd10Dic icd初诊
	 * @param application 病历讨论
	 * @param startTime 审核开始时间
	 * @param endTime 审核结束时间
	 * @param selectedUserIds 受邀人
	 * @param meetingRoomId 会议室
	 */
	public MeetingApplication saveMeetingApplicationCollection(UserInfo userinfo,String[] files,String[] hainaDatas, 
			String[] deptmentid,String[] icd10Dic,MeetingApplication application,String startTime,String endTime,
			String selectedUserIds,String meetingRoomId)throws Exception{
		try{
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
						logger.debug("病历讨论补录非放射影像的附件有" + acces.size());
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
		    	logger.debug("病历讨论补录总共有" + accessories.size() + "个放射影像");
		    	acces.addAll(accessories);
		    	accessoriesDao.saveBatchData(acces.toArray(new Accessories[acces.size()]));
		    }		
			application.setAccessories(acces);
			logger.debug("病历讨论补录总共有" + acces.size() + "个附件");
			
			//添加会诊科室
			Department deptment = null;//部门信息表
		    ApplicationDepartment appDept = null;
		    if(deptmentid != null && deptmentid.length > 0){
		    	deptment = departmentDao.get(Integer.parseInt(deptmentid[0]));
		    	application.setMainDept(deptment);
				if(deptmentid.length > 1){
					Set<ApplicationDepartment> depts  = new HashSet<ApplicationDepartment>(deptmentid.length);
					for(int i = 1; i < deptmentid.length; i++){
						appDept = new ApplicationDepartment();
						deptment = departmentDao.get(Integer.parseInt(deptmentid[i]));
				    	appDept.setDepartment(deptment);
				    	appDept.setMeetingApplication(application);
				    	depts.add(appDept);
					}
					application.setDepts(depts);
					logger.debug("病历讨论补录选择了" + depts.size() + "个部门进行病历讨论");
				}
			}
			
			//添加ICD10
		    ICD10DIC icd10 = null;//icd10信息
		    ApplicationICD10 ic = null;
			if(icd10Dic != null && icd10Dic.length > 0){
				Set<ApplicationICD10> icds = new HashSet<ApplicationICD10>(icd10Dic.length);
				for(int i = 0 ; i < icd10Dic.length; i++) {
					ic = new ApplicationICD10();
					icd10 = icd10DicDao.get(Integer.parseInt(icd10Dic[i]));
			    	ic.setIcd(icd10);
			    	ic.setMeetingApplication(application);
			    	icds.add(ic);
				}
				application.setApplicationICD10s(icds);
				logger.debug("病历讨论补录选择了" + icds.size() + "个ICD10数据");
			}
		    
			//添加会议申请信息
			if(application.getPatientInfo().getId() == null)
				//患者基本信息保存
				applicationPatientInfoDao.save(application.getPatientInfo());
		    logger.debug("病历讨论补录患者基本信息保存成功！");
			if(application.getAuthorInfo().getId() == null)
				//病例提交人基本信息保存
				applicationAuthorInfoDao.save(application.getAuthorInfo());
			logger.debug("病历讨论补录病例提交人基本信息保存成功！");
		
		    application.setRequester(userinfo);//申请人
		    //开始时间
			Date startDate = DateUtil.getDateTimeByHours(startTime);
			//结束时间
			Date endDate = DateUtil.getDateTimeByHours(endTime);
			application.setExpectedTime(startDate);//期望时间
			logger.debug("病历讨论补录期望时间为" + startDate);
		    application.setApplicationTime(startDate);//申请时间
			application.setAccessories(acces);		
			if(application.getMeetingType()!=null&&application.getMeetingType().getId()!=null){
				application.setMeetingType(meetingTypeDao.get(application.getMeetingType().getId()));
			}
			if(application.getLevel()!=null&&application.getLevel().getId()!=null){
				application.setLevel(meetingLevelDao.get(application.getLevel().getId()));
			}
			application.setState(GlobalConstent.MEETING_APPLICATION_STATE_PASS);//审批默认通过
			application.setStartTime(startDate);//会议开始时间
			application.setEndTime(endDate);//会议结束时间
			meetingApplicationDao.save(application);
			logger.debug("病历讨论补录信息保存成功！");
			
			//设置海纳关联
			try{
				if(values != null && values.length > 0){
					hainaCall.setStudy(application.getId().toString(), values);
					logger.debug("病历讨论补录海纳影像设置成功！");
				}
			}catch(Exception e){
				logger.debug("病历讨论补录海纳影像设置失败！");
			}												
			Meeting meeting = new Meeting();
			meeting.setApplicationId(application);
			meeting.setTitle(application.getPurpose());
			meeting.setContent(application.getMedicalRecord());
			meeting.setStartTime(startDate);
			meeting.setEndTime(endDate);
			meeting.setLevel(application.getLevel());
			meeting.setMeetingType(application.getMeetingType());
			meeting.setCreator(userinfo);
			meeting.setHolder(userinfo);
			
			meeting.setMeetingKind(GlobalConstent.APPLICATION_TYPE_TELECONSULTATION);

			MeetingRoom meetingRoom = new MeetingRoom();
			if (!StrUtil.isEmptyStr(meetingRoomId)) {
				meetingRoom.setId(Integer.valueOf(meetingRoomId));
			}else{
				meetingRoom.setId(1);
			}
			meeting.setMeetingRoomId(meetingRoom);

			meeting.setState(GlobalConstent.MEETING_STATE_END);
			this.meetingDao.save(meeting);

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
			UserInfo user = null;
			//添加参会人
			if(attendUserIdObjs!=null&&attendUserIdObjs.length>0){
				for(String userId:attendUserIdObjs){
					meetingMember=new MeetingMember();
					meetingMember.setMeetingId(meeting);
					user=userDao.get(userId);
					meetingMember.setMember(user);
					//邀请的对象默认参会
					meetingMember.setAttendState(GlobalConstent.APPLICATION_STATE_ACCEPT);
					
					if(user!=null&&user.getUserType()!=null&&user.getUserType().getValue().equals(GlobalConstent.USER_TYPE_PROFESSIONAL)){
						//当参会者为专家时，参会人数为1
						meetingMember.setAttendNo((short)1);
					}else{
						//当参会者为共同体时，参会人数默认为1
						meetingMember.setAttendNo((short)1);
					}
					this.meetingMemberDao.save(meetingMember);
				}
				
			}
			
	    }catch(Exception e){				
			logger.error("ApplicationOperateServiceImpl.saveMeetingApplicationCollection 病历讨论补录失败", e);
			throw e;
		}
		return application;
	}
	
    @MrbsLog(desc="group.videoApplication_create")
    public void saveVideoApplication(UserInfo user,String expecttime,String[] files,VideoMeetingApp videoMeetingApp) throws Exception{
	     try{
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
							accessories.setOwner(user);
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
							logger.debug("附件个数为" + acces.size());
						}
					}
					   //accessoriesDao.saveBatchData(acces.toArray(new Accessories[acces.size()]));
					   videoMeetingApp.setAccessories(acces);
			    }
			  
			    videoMeetingApp.setAppDate(new Date());

				videoMeetingApp.setState(GlobalConstent.MEETING_APPLICATION_STATE_PENDING);
				videoMeetingApp.setVideoRequester(user);
				MeetingType type = new MeetingType((short)1);
				videoMeetingApp.setMeetingType(type);
				if (!StrUtil.isEmptyStr(expecttime)) {
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
					Date date = null;
					try {
						date = sdf.parse(expecttime);
					} catch (Exception e) {
						logger.error("期望会议时间无法正常解析");							
					}
					videoMeetingApp.setSuggestDate(date);
				}
				if(videoMeetingApp.getLevel()!=null&&videoMeetingApp.getLevel().getId()!=null)
					videoMeetingApp.setLevel(meetingLevelDao.get(videoMeetingApp.getLevel().getId()));
				
				/*if(videoMeetingApp.getVideoApplicationPurpose()!=null&&videoMeetingApp.getVideoApplicationPurpose().getId()!=null)
					videoMeetingApp.setVideoApplicationPurpose(videoApplicationPurposeDao.get(videoMeetingApp.getVideoApplicationPurpose().getId()));*/
				
				if(videoMeetingApp.getUserName()!=null&&videoMeetingApp.getUserName().getUserId()!=null&&videoMeetingApp.getUserName().getUserId()!=""){												
					UserInfo speaker=userDao.get(videoMeetingApp.getUserName().getUserId());
					videoMeetingApp.setUserName(speaker);
					Department department=speaker.getDeptId();
					videoMeetingApp.setDeptName(department);						
				}else{
					videoMeetingApp.setUserName(null);
					videoMeetingApp.setDeptName(null);
				}
				
				videoMeetingAppDao.save(videoMeetingApp);
				logger.debug("申请视频讲座成功");
				
				//发送通知
				int typeId=videoMeetingApp.getVideoRequester().getRole().getId();
				if(typeId!=4){
					List<UserInfo> list = userDao.findTbyHql("From UserInfo where userType=2 and state=1");
					UnifiedNotice mailNotice = null;
					for(int i=0;i<list.size();i++){
						UserInfo userInfo=list.get(i);
						mailNotice = new UnifiedNotice();
						mailNotice.setTitle(NoticeInfo.getMessage("video_notice_title"));
						mailNotice.setMessage(NoticeInfo.getNoticeMessage("video_notice_content", videoMeetingApp));
						mailNotice.setReceiver(userInfo.getMail());
						String url="";
						try {
							url = URLEncoder.encode("/meeadmdbd/meetingappsearchlist?id="+videoMeetingApp.getId(), "utf-8");
						} catch (UnsupportedEncodingException e) {
							e.printStackTrace();
						}
						url = GlobalParam.outter_access_ip + "/login/notice" + "?uid=" + userInfo.getUserId() + "&pwd="+userInfo.getPassword() + "&direct=" + url;
						mailNotice.setAcceptUrl(url);
						noticefactory.addMailNotice(mailNotice);

						if(StringUtils.isNotBlank(userInfo.getMobile())){
							MtTelecon mtTelecon = new MtTelecon();
							mtTelecon.setSmId("0");
							mtTelecon.setSrcId("0");
							mtTelecon.setMobile(userInfo.getMobile());
							mtTelecon.setContent(videoMeetingApp.getVideoRequester().getName() +"提交了题目为《"+videoMeetingApp.getLectureContent()+"》的视频讲座申请，请您审核");
							//Date sendTime = new Date(new Date().getTime() + GlobalConstent.TO_MANAGER_SMS_DELAY * 1000 * 60);
							mtTelecon.setSendTime(null);
							mtTelecon.setDataExpiredFlag(GlobalConstent.DATE_EXPIRED_FLAG_N);
							mtTelecon.setSmsServiceCode(GlobalConstent.SMS_SERVICE_CODE_004);
							mtTelecon.setState(GlobalConstent.NOTICE_NOT_SEND);
							mtTelecon.setResendTimes(0);
							mtTelecon.setAcceptSmsUserId(userInfo.getUserId());
							mtTelecon.setApplicationId(videoMeetingApp.getId());					
							noticefactory.addMtTelecon(mtTelecon);
						}							
					}
				}
			   
	     }catch(Exception e){
	    	 logger.error("申请视频讲座出现错误", e);
	    	 throw e;
	     }
    }
    
    /**
	 * 会议管理员添加视频讲座
	 * @param userinfo 添加用户
	 * @param files 附件
	 * @param videoMeetingApp 视频讲座主表内容
	 * @param startTime 审核开始时间
	 * @param endTime 审核结束时间
	 * @param selectedUserIds 受邀人
	 * @param meetingRoomId 会议室
	 */
	public void saveVideoApplicationByManager(UserInfo userinfo,String[] files,VideoMeetingApp videoMeetingApp,String startTime,String endTime,String selectedUserIds,String meetingRoomId)throws Exception{
		try{
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
						logger.debug("附件个数为" + acces.size());
					}
				}
				   //accessoriesDao.saveBatchData(acces.toArray(new Accessories[acces.size()]));
				   videoMeetingApp.setAccessories(acces);
			}
		  
		    videoMeetingApp.setAppDate(new Date());

			videoMeetingApp.setState(GlobalConstent.MEETING_APPLICATION_STATE_PENDING);
			videoMeetingApp.setVideoRequester(userinfo);
			MeetingType type = new MeetingType((short)1);
			videoMeetingApp.setMeetingType(type);
			//开始时间
			Date startDate = DateUtil.getDateTimeByHours(startTime);
			//结束时间
			Date endDate = DateUtil.getDateTimeByHours(endTime);
			videoMeetingApp.setStartTime(startDate);
			videoMeetingApp.setEndTime(endDate);
			videoMeetingApp.setSuggestDate(startDate);
			if(videoMeetingApp.getLevel()!=null&&videoMeetingApp.getLevel().getId()!=null)
				videoMeetingApp.setLevel(meetingLevelDao.get(videoMeetingApp.getLevel().getId()));
			
			
			//所选择的参会人
			String [] attendUserIdObjs = null;
			if (!StrUtil.isEmptyStr(selectedUserIds)) {
				if (selectedUserIds.endsWith("|")) {
					selectedUserIds = selectedUserIds.substring(0, selectedUserIds.length() - 1);
				}
				attendUserIdObjs = selectedUserIds.split("\\|");
			}
			if(attendUserIdObjs!=null&&attendUserIdObjs.length>0){
				//将主讲人放入申请表中
				UserInfo user=userDao.get(attendUserIdObjs[0]);
				videoMeetingApp.setUserName(user);
				videoMeetingApp.setDeptName(user.getDeptId());
			}else{
				videoMeetingApp.setUserName(null);
				videoMeetingApp.setDeptName(null);
			}
			videoMeetingAppDao.save(videoMeetingApp);
			logger.debug("申请视频讲座成功");
			
			Meeting meeting = new Meeting();
			
			meeting.setVideoapplicationId(videoMeetingApp);
			meeting.setTitle(videoMeetingApp.getLectureContent());
			meeting.setContent(videoMeetingApp.getLectureContent());
			meeting.setEndTime(endDate);
			meeting.setLevel(videoMeetingApp.getLevel());
			meeting.setMeetingType(videoMeetingApp.getMeetingType());
			meeting.setCreator(userinfo);
			meeting.setHolder(userinfo);
			meeting.setStartTime(startDate);
			meeting.setMeetingKind(GlobalConstent.APPLICATION_TYPE_VIDEOLECTURES);
			
			MeetingRoom meetingRoom = new MeetingRoom();
			if (!StrUtil.isEmptyStr(meetingRoomId)) {
				meetingRoom.setId(Integer.valueOf(meetingRoomId));
			}else{
				meetingRoom.setId(1);
			}
			meeting.setMeetingRoomId(meetingRoom);
			
			meeting.setState(Short.valueOf(GlobalConstent.MEETING_STATE_PENDING));
			this.meetingDao.save(meeting);
			
			//修改申请的状态
			videoMeetingApp.setState(GlobalConstent.MEETING_APPLICATION_STATE_PASS);
			
			this.videoMeetingAppDao.update(videoMeetingApp);

			//保存参与会诊的人员
			MeetingMember meetingMember=null;
			UserInfo user = null;
			//添加参会人
			if(attendUserIdObjs!=null&&attendUserIdObjs.length>0){
				
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
					this.meetingMemberDao.save(meetingMember);
				}
			}
	     }catch(Exception e){
	    	 logger.error("ApplicationOperateServiceImpl.saveVideoApplicationByManager 会议管理员添加视频讲座出现错误", e);
	    	 throw e;
	     }
	}
    
	/**
	 * 会议管理员补录视频讲座
	 * @param userinfo 添加用户
	 * @param files 附件
	 * @param videoMeetingApp 视频讲座主表内容
	 * @param startTime 审核开始时间
	 * @param endTime 审核结束时间
	 * @param selectedUserIds 受邀人
	 * @param meetingRoomId 会议室
	 */
	public void saveVideoLecturesCollection(UserInfo userinfo,String[] files,VideoMeetingApp videoMeetingApp,String startTime,String endTime,String selectedUserIds,String meetingRoomId)throws Exception{
		try{
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
						logger.debug("附件个数为" + acces.size());
					}
				}
				   accessoriesDao.saveBatchData(acces.toArray(new Accessories[acces.size()]));
				   videoMeetingApp.setAccessories(acces);
			}
		  
		    videoMeetingApp.setAppDate(new Date());

			videoMeetingApp.setState(GlobalConstent.MEETING_APPLICATION_STATE_PENDING);
			videoMeetingApp.setVideoRequester(userinfo);
			MeetingType type = new MeetingType((short)3);
			videoMeetingApp.setMeetingType(type);
			//开始时间
			Date startDate = DateUtil.getDateTimeByHours(startTime);
			//结束时间
			Date endDate = DateUtil.getDateTimeByHours(endTime);
			videoMeetingApp.setStartTime(startDate);
			videoMeetingApp.setEndTime(endDate);
			videoMeetingApp.setSuggestDate(startDate);
			if(videoMeetingApp.getLevel()!=null&&videoMeetingApp.getLevel().getId()!=null)
				videoMeetingApp.setLevel(meetingLevelDao.get(videoMeetingApp.getLevel().getId()));
			
			
			//所选择的参会人
			String [] attendUserIdObjs = null;
			if (!StrUtil.isEmptyStr(selectedUserIds)) {
				if (selectedUserIds.endsWith("|")) {
					selectedUserIds = selectedUserIds.substring(0, selectedUserIds.length() - 1);
				}
				attendUserIdObjs = selectedUserIds.split("\\|");
			}
			if(attendUserIdObjs!=null&&attendUserIdObjs.length>0){
				//将主讲人放入申请表中
				UserInfo user=userDao.get(attendUserIdObjs[0]);
				videoMeetingApp.setUserName(user);
				videoMeetingApp.setDeptName(user.getDeptId());
			}else{
				videoMeetingApp.setUserName(null);
				videoMeetingApp.setDeptName(null);
			}
			videoMeetingAppDao.save(videoMeetingApp);
			logger.debug("补录视频讲座成功");
			
			Meeting meeting = new Meeting();
			
			meeting.setVideoapplicationId(videoMeetingApp);
			meeting.setTitle(videoMeetingApp.getLectureContent());
			meeting.setContent(videoMeetingApp.getLectureContent());
			meeting.setEndTime(endDate);
			meeting.setLevel(videoMeetingApp.getLevel());
			meeting.setMeetingType(videoMeetingApp.getMeetingType());
			meeting.setCreator(userinfo);
			meeting.setHolder(userinfo);
			meeting.setStartTime(startDate);
			meeting.setMeetingKind(GlobalConstent.APPLICATION_TYPE_VIDEOLECTURES);
			
			MeetingRoom meetingRoom = new MeetingRoom();
			if (!StrUtil.isEmptyStr(meetingRoomId)) {
				meetingRoom.setId(Integer.valueOf(meetingRoomId));
			}else{
				meetingRoom.setId(1);
			}
			meeting.setMeetingRoomId(meetingRoom);
			
			meeting.setState(Short.valueOf(GlobalConstent.MEETING_STATE_END));
			this.meetingDao.save(meeting);
			
			//修改申请的状态
			videoMeetingApp.setState(GlobalConstent.MEETING_APPLICATION_STATE_PASS);
			this.videoMeetingAppDao.update(videoMeetingApp);

			//保存参与会诊的人员
			MeetingMember meetingMember=null;
			UserInfo user = null;
			//添加参会人
			if(attendUserIdObjs!=null&&attendUserIdObjs.length>0){
				
				for(String userId:attendUserIdObjs){
					meetingMember=new MeetingMember();
					meetingMember.setMeetingId(meeting);
					user=userDao.get(userId);
					meetingMember.setMember(user);
					//邀请的对象默认参会
					meetingMember.setAttendState(GlobalConstent.APPLICATION_STATE_ACCEPT);
					
					if(user!=null&&user.getUserType()!=null&&user.getUserType().getValue().equals(GlobalConstent.USER_TYPE_PROFESSIONAL)){
						//当参会者为专家时，参会人数为1
						meetingMember.setAttendNo((short)1);
					}else{
						//当参会者为共同体时，参会人数默认为1
						meetingMember.setAttendNo((short)1);
					}
					this.meetingMemberDao.save(meetingMember);
				}
			}
	     }catch(Exception e){
	    	 logger.error("ApplicationOperateServiceImpl.saveVideoLecturesCollection 补录视频讲座出现错误", e);
	    	 throw e;
	     }
	}
    
    /**
	 * 会议管理员修改审核通过的视频讲座
	 * @param userinfo 添加用户
	 * @param files 附件
	 * @param videoMeetingApp 视频讲座主表内容
	 * @param startTime 审核开始时间
	 * @param endTime 审核结束时间
	 * @param selectedUserIds 受邀人
	 * @param meetingRoomId 会议室
     * @throws Exception 
	 */
	@MrbsLog(desc="group.videoApplication_update")
	public void updateVideoApplication(UserInfo userinfo,String[] files,VideoMeetingApp videoApp,String startTime,String endTime,String selectedUserIds,String meetingRoomId) throws Exception{
    	logger.debug("Enter updateVideoApplication");	    	
    	try{
    		VideoMeetingApp videoMeetingApp=videoMeetingAppDao.get(videoApp.getId());
    		if(videoMeetingApp!=null&&videoMeetingApp.getState().toString().equals(String.valueOf(GlobalConstent.MEETING_APPLICATION_STATE_PASS))){
    			//删除附件
    			accessoriesDao.deleteAccessoriesByVoidId(videoApp.getId());
    	    	
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
    						logger.debug("附件个数为" + acces.size());
    					}
    				}
    				   //accessoriesDao.saveBatchData(acces.toArray(new Accessories[acces.size()]));
    				   videoMeetingApp.setAccessories(acces);
    			}
    		  
    			  
    			videoMeetingApp.setLectureContent(videoApp.getLectureContent());				
        		videoMeetingApp.setTeachingObject(videoApp.getTeachingObject());
    			
        		//开始时间
    			Date startDate = DateUtil.getDateTimeByHours(startTime);
    			//结束时间
    			Date endDate = DateUtil.getDateTimeByHours(endTime);
    			videoMeetingApp.setStartTime(startDate);
    			videoMeetingApp.setEndTime(endDate);
    			videoMeetingApp.setSuggestDate(startDate);
    			if(videoApp.getLevel()!=null&&videoApp.getLevel().getId()!=null)
    				videoMeetingApp.setLevel(meetingLevelDao.get(videoApp.getLevel().getId()));
    			
    			//所选择的参会人
    			String [] attendUserIdObjs = null;
    			if (!StrUtil.isEmptyStr(selectedUserIds)) {
    				if (selectedUserIds.endsWith("|")) {
    					selectedUserIds = selectedUserIds.substring(0, selectedUserIds.length() - 1);
    				}
    				attendUserIdObjs = selectedUserIds.split("\\|");
    			}
    			if(attendUserIdObjs!=null&&attendUserIdObjs.length>0){
    				//将主讲人放入申请表中
    				UserInfo user=userDao.get(attendUserIdObjs[0]);
    				videoMeetingApp.setUserName(user);
    				videoMeetingApp.setDeptName(user.getDeptId());
    			}else{
    				videoMeetingApp.setUserName(null);
    				videoMeetingApp.setDeptName(null);
    			}
    			
    			videoMeetingAppDao.update(videoMeetingApp);
    			
    			Meeting meeting = meetingDao.getMeetingByVideoAppId(videoMeetingApp.getId());
    			if(meeting!=null){
    				if(meeting.getEndTime().getTime() != endDate.getTime()){
    					//如果修改时间，则修改MCU设备
    					meeting.setEndTime(endDate);
        				Packaging.packagConferenceForSet(meeting);
    				}
    				short state = meeting.getState();
    				meetingMemberDao.delMembersByMeetingId(meeting.getId());
    				meeting.setVideoapplicationId(videoMeetingApp);
        			meeting.setTitle(videoMeetingApp.getLectureContent());
        			meeting.setContent(videoMeetingApp.getLectureContent());
        			meeting.setLevel(videoMeetingApp.getLevel());
        			meeting.setStartTime(startDate);
        			
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
    				
    				meeting.setVideoapplicationId(videoMeetingApp);
    				meeting.setTitle(videoMeetingApp.getLectureContent());
    				meeting.setContent(videoMeetingApp.getLectureContent());
    				meeting.setEndTime(endDate);
    				meeting.setLevel(videoMeetingApp.getLevel());
    				meeting.setMeetingType(videoMeetingApp.getMeetingType());
    				meeting.setCreator(userinfo);
    				meeting.setHolder(userinfo);
    				meeting.setStartTime(startDate);
    				meeting.setMeetingKind(GlobalConstent.APPLICATION_TYPE_VIDEOLECTURES);
    				
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
    			
    			//保存参与会诊的人员
    			MeetingMember meetingMember=null;
    			UserInfo user = null;
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
    		}
    		
			logger.debug("会议管理员修改审核通过的视频讲座成功");
			logger.debug("Over updateVideoApplication");
    	}catch(Exception e){
    		logger.error("会议管理员修改审核通过的视频讲座出错",e);
    		throw e;
    	}
		
    }
    
    @MrbsLog(desc="group.videoApplication_update")
    public void updateVideoApplication(String[] files,String expecttime,String deptpersonid,String lectureContent,String videoApplicationPurposeid,String meetinglevel,String videoMeetingAppId,String teachingObject) throws Exception{
    	logger.debug("Enter updateVideoApplication");
    	try{
	    	VideoMeetingApp videoMeetingApp=videoMeetingAppDao.get(Integer.parseInt(videoMeetingAppId));
	    	if(videoMeetingApp!=null){
		    	//删除附件
				accessoriesDao.deleteAccessoriesByVoidId(videoMeetingApp.getId());
		    	
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
							accessories.setOwner(videoMeetingApp.getVideoRequester());
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
							logger.debug("附件个数为" + acces.size());
						}
					}
				   //accessoriesDao.saveBatchData(acces.toArray(new Accessories[acces.size()]));
				   videoMeetingApp.setAccessories(acces);
				}
	    					  
		    	if(lectureContent!=null){
					videoMeetingApp.setLectureContent(lectureContent);				
				}
	    	
		    	if(teachingObject!=null){
		    		videoMeetingApp.setTeachingObject(teachingObject);
		    	}
			
				if(meetinglevel!=null){
					MeetingLevel meetingLevelName=new MeetingLevel();
					meetingLevelName.setId(Short.parseShort(meetinglevel));
					videoMeetingApp.setLevel(meetingLevelName);
				}
			
				/*if(videoApplicationPurposeid!=null){				
					VideoApplicationPurpose videoApplicationPurpose=new VideoApplicationPurpose();
					videoApplicationPurpose.setId(Short.parseShort(videoApplicationPurposeid));
					videoMeetingApp.setVideoApplicationPurpose(videoApplicationPurpose);				
				}*/
				
				if(expecttime != null){
					Date suggestDate=null;
					try{
					    suggestDate = dateFormat.parse(expecttime);
						
					}catch(Exception e){
						logger.error("期望会议时间无法正常解析");
						e.printStackTrace();
					}				
					 videoMeetingApp.setSuggestDate(suggestDate);
				}
	
				if(deptpersonid != null){					
					if(deptpersonid != ""){
						UserInfo userName=userDao.get(deptpersonid);
						videoMeetingApp.setUserName(userName);					
						Department department=userName.getDeptId();
						if(department!=null)
						videoMeetingApp.setDeptName(department);
					}else{
						videoMeetingApp.setUserName(null);
						videoMeetingApp.setDeptName(null);
					}
						
				}				
			
				videoMeetingAppDao.update(videoMeetingApp);
				logger.debug("Over updateVideoApplication success");
				logger.debug("修改视频讲座成功");
	    	}
    	}catch(Exception e){
    		logger.error("修改视频讲座出错");
    		throw e;
    	}
		
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
	
    @Resource(type = ApplicationPatientInfoDao.class)
	public void setApplicationPatientInfoDao(ApplicationPatientInfoDao applicationPatientInfoDao) {
		this.applicationPatientInfoDao = applicationPatientInfoDao;
	}
	
    @Resource(type = ApplicationAuthorInfoDao.class)
	public void setApplicationAuthorInfoDao(ApplicationAuthorInfoDao applicationAuthorInfoDao) {
		this.applicationAuthorInfoDao = applicationAuthorInfoDao;
	}
	
    @Resource(type = MeetingTypeDao.class)
	public void setMeetingTypeDao(MeetingTypeDao meetingTypeDao) {
		this.meetingTypeDao = meetingTypeDao;
	}
	
    @Resource(type = MeetingLevelDao.class)
	public void setMeetingLevelDao(MeetingLevelDao meetingLevelDao) {
		this.meetingLevelDao = meetingLevelDao;
	}
	
    @Resource(type = MeetingApplicationDao.class)
	public void setMeetingApplicationDao(MeetingApplicationDao meetingApplicationDao) {
		this.meetingApplicationDao = meetingApplicationDao;
	}
	
    @Resource
	public void setHainaCall(HainaCall hainaCall) {
		this.hainaCall = hainaCall;
	}
    
    @Resource(type = VideoMeetingAppDao.class)
    public void setVideoMeetingAppDao(VideoMeetingAppDao videoMeetingAppDao){
    	this.videoMeetingAppDao=videoMeetingAppDao;
    }

    @Resource(type = MeetingMemberDao.class)
	public void setMeetingMemberDao(MeetingMemberDao meetingMemberDao) {
		this.meetingMemberDao = meetingMemberDao;
	}
    
    @Resource(type = MeetingDao.class)
	public void setMeetingDao(MeetingDao meetingDao) {
		this.meetingDao = meetingDao;
	}

    @Resource(type = UserDao.class)
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
    
    @Resource(type = NoticeFactory.class)
	public void setNoticefactory(NoticeFactory noticefactory) {
		this.noticefactory = noticefactory;
	}
	    
}
