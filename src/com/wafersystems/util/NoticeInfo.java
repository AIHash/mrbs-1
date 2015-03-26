package com.wafersystems.util;

import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.PropertyResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wafersystems.mrbs.vo.meeting.ApplicationDepartment;
import com.wafersystems.mrbs.vo.meeting.ApplicationICD10;
import com.wafersystems.mrbs.vo.meeting.ICUDepartment;
import com.wafersystems.mrbs.vo.meeting.ICUICD10;
import com.wafersystems.mrbs.vo.meeting.ICUMonitor;
import com.wafersystems.mrbs.vo.meeting.ICUVisitation;
import com.wafersystems.mrbs.vo.meeting.Meeting;
import com.wafersystems.mrbs.vo.meeting.MeetingApplication;
import com.wafersystems.mrbs.vo.meeting.VideoMeetingApp;

public class NoticeInfo 

{
	private final static Logger logger = LoggerFactory.getLogger(NoticeInfo.class);
	private static Map<String,String> option = new HashMap<String,String>();
	static
	{
	try{
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
	    if (null == classLoader)
	       classLoader = NoticeInfo.class.getClassLoader();
		InputStream is = classLoader.getResourceAsStream("notice.properties");
		PropertyResourceBundle rBundle  = new PropertyResourceBundle(is);	 
		Enumeration<String> enur = rBundle.getKeys();
		String keyName = null;
		String unicodeValue = null;
		while (enur.hasMoreElements())
		{
			keyName = enur.nextElement();			
			unicodeValue = rBundle.getString(keyName);
			unicodeValue = unicodeValue.replace('{', '#');
			unicodeValue = unicodeValue.replace('}', '#');
			option.put(keyName.toLowerCase(), unicodeValue);//从资源文件获取的键值采用小写.
		}
		is.close();
		logger.debug("加载notice.properties完成！");
		
	}catch(Exception e){
		logger.error("加载notice.properties失败！",e);
	}
	}
	
	public static String getMessage(String name)
	{	
		String value=option.get(name.toLowerCase());		
		if(StrUtil.isEmptyStr(value))
		{
			value="";
		}
		return value;
	}
	
	public static String getMessage(String name,String []param)
	{	
		String resource = name.toLowerCase();
		String []parameters = param;

		try
		{
			//键值采用小写.
			String keyName = name;

			if (null != keyName && !"".equals(keyName.trim()))
			{
				resource = option.get(keyName.toLowerCase());
				if (null == resource)
				{
					resource = "";
					if (-1 != keyName.indexOf("."))
						logger.warn("资源文件没有定义:" + keyName);
				}
				else
				{
					if (null != parameters)
					{										
						for (int i = 0; i < parameters.length; i++)
						{
							if (null == parameters[i])
								resource = resource.replace("#" + i + "#","");
							else
								resource = resource.replace("#" + i + "#", parameters[i]);
						}
					}
				}
			}
		}
		catch (Exception e)
		{
			logger.warn("获取资源失败,keyname="+ name,e);
			resource=name;
		}		
		
		return resource;
	}
	
	public static String getMessage(String name,MeetingApplication application)
	{			
//		String title=application.getPurpose();
		String porpose=application.getPurpose().replace("<", " &lt;");
//		String dept=application.getDepartment().getName();
		String content=application.getMedicalRecord().replace("<", " &lt;");
		String meetingtype=application.getMeetingType().getName();
		
		String level=application.getLevel().getName();
		String expectedTime="";
		if(null!=application.getExpectedTime())
		{
			expectedTime=DateUtil.getDateTimeStr(application.getExpectedTime());
		}
		String applicationTime="";
		if(null!=application.getApplicationTime())
		{
			applicationTime=DateUtil.getDateTimeStr(application.getApplicationTime());
		}
		
		String deptName="";
		
		if(application.getMainDept()!=null&&!StrUtil.isEmptyStr(application.getMainDept().getName())){
			deptName+=application.getMainDept().getName()+"，";
		}
		boolean flag = false;
		if(application.getDepts()!=null){
			int i=0;
			for(ApplicationDepartment applicationDepartment:application.getDepts()){
				if(applicationDepartment.getDepartment()!=null&&!StrUtil.isEmptyStr(applicationDepartment.getDepartment().getName())){
					deptName+=applicationDepartment.getDepartment().getName()+"，";
					//如果部门超过5个时只显示前5个
					if(i==4){
						deptName = deptName.substring(0, deptName.length()-1);
						deptName = deptName+" ......";
						flag = true;
						break;
					}
					i++;
				}
				
			}
		}
		if(!StrUtil.isEmptyStr(deptName)&&!flag){
			deptName = deptName.substring(0, deptName.length()-1);
		}
		
		String applicationICD10s="";
		flag = false;
		if(application.getApplicationICD10s()!=null){
			int i=0;
			for(ApplicationICD10 applicationICD10:application.getApplicationICD10s()){
				if(applicationICD10.getIcd()!=null&&!StrUtil.isEmptyStr(applicationICD10.getIcd().getDiagnosis())){
					applicationICD10s+=applicationICD10.getIcd().getDiagnosis()+"，";
					
					//如果ICD超过10个时只显示前10个
					if(i==9){
						applicationICD10s = applicationICD10s.substring(0, applicationICD10s.length()-1);
						applicationICD10s = applicationICD10s+" ......";
						flag = true;
						break;
					}
					i++;
				}
			}
		}else{
			applicationICD10s =application.getIcdUserDefined();
			flag = true;
		}
		if(!StrUtil.isEmptyStr(applicationICD10s)&&!flag){
			applicationICD10s = applicationICD10s.substring(0, applicationICD10s.length()-1);
		}
		
		String requester=application.getRequester().getName();
//		String applicant=application.getRequester().getName();
//		String []param={title,porpose,dept,content,meetingtype,level,starttime,endtime,requester,applicant};
		String patientName=application.getPatientInfo().getName();
		String patientAge=application.getPatientInfo().getAge();
		String patientSex=application.getPatientInfo().getSex();
		String department=application.getRequester().getDeptId().getName();
		String parentDepartment=application.getRequester().getDeptId().getParentDetp().getName();
		if(patientSex.equals("0")) 
			patientSex="男";
		else 
			patientSex="女";
		String []param={porpose,content,applicationICD10s,deptName,meetingtype,level,expectedTime,requester,applicationTime,patientName,patientAge,patientSex,department,parentDepartment};
		return getMessage(name,param);
	}
	//病历讨论，视频讲座审批通过
	public static String getNoticeMessage(String name,Meeting meeting,String member)
	{		
		String title=meeting.getTitle().replace("<", " &lt;");
		String content=meeting.getContent().replace("<", " &lt;");
		String meetingtype=meeting.getMeetingType().getName();
		String meetingroom=meeting.getMeetingRoomId().getName();
		
		String speaker="";
		String deptName="";
		
		String patientName="";
		String patientAge="";
		String patientSex="";
		String applicationICD10s="";
		String department="";
		String parentDepartment="";
		//视频讲座邮件内容添加主讲人，主讲人科室
		if(meeting.getMeetingKind()==2){
			speaker=meeting.getVideoapplicationId().getUserName().getName();
			deptName=meeting.getVideoapplicationId().getDeptName().getName();
 }else if(meeting.getMeetingKind()==3){//重症监护

			//病历讨论添加患者信息，部门信息，初步诊断，专家部门
			//获取患者信息
			patientName=meeting.getiCUMonitorId().getPatientInfo().getName();
			patientAge=meeting.getiCUMonitorId().getPatientInfo().getAge();
			patientSex=meeting.getiCUMonitorId().getPatientInfo().getSex();
			if(patientSex.equals("0")) 
				patientSex="男";
			else 
				patientSex="女";
			//获取科室			
			if(meeting.getiCUMonitorId().getMainDept()!=null&&!StrUtil.isEmptyStr(meeting.getiCUMonitorId().getMainDept().getName())){
				deptName+=meeting.getiCUMonitorId().getMainDept().getName()+"，";
			}
			boolean flag = false;
			if(meeting.getiCUMonitorId().getDepts()!=null){
				int i=0;
				for(ICUDepartment icuDepartment:meeting.getiCUMonitorId().getDepts()){
					if(icuDepartment.getDepartment()!=null&&!StrUtil.isEmptyStr(icuDepartment.getDepartment().getName())){
						deptName+=icuDepartment.getDepartment().getName()+"，";
						//如果部门超过5个时只显示前5个
						if(i==4){
							deptName = deptName.substring(0, deptName.length()-1);
							deptName = deptName+" ......";
							flag = true;
							break;
						}
						i++;
					}
					
				}
			}
			if(!StrUtil.isEmptyStr(deptName)&&!flag){
				deptName = deptName.substring(0, deptName.length()-1);
			}
			
			//初步诊断
			if(meeting.getiCUMonitorId().getIcdDisplayRadio() != 0){
				flag = false;
				if(meeting.getiCUMonitorId().getIcuICD10()!=null){
					int i=0;
					for(ICUICD10 applicationICD10:meeting.getiCUMonitorId().getIcuICD10()){
						if(applicationICD10.getIcd()!=null&&!StrUtil.isEmptyStr(applicationICD10.getIcd().getDiagnosis())){
							applicationICD10s+=applicationICD10.getIcd().getDiagnosis()+"，";
							
							//如果ICD超过10个时只显示前10个
							if(i==9){
								applicationICD10s = applicationICD10s.substring(0, applicationICD10s.length()-1);
								applicationICD10s = applicationICD10s+" ......";
								flag = true;
								break;
							}
							i++;
						}
					}
				}
				if(!StrUtil.isEmptyStr(applicationICD10s)&&!flag){
					applicationICD10s = applicationICD10s.substring(0, applicationICD10s.length()-1);
				}
			}else{
				applicationICD10s = meeting.getiCUMonitorId().getIcdUserDefined();
			}
			
			//获取专家部门
			department=meeting.getiCUMonitorId().getRequester().getDeptId().getName();
			parentDepartment=meeting.getiCUMonitorId().getRequester().getDeptId().getParentDetp().getName();
		}else{
			//病历讨论添加患者信息，部门信息，初步诊断，专家部门
			//获取患者信息
			patientName=meeting.getApplicationId().getPatientInfo().getName();
			patientAge=meeting.getApplicationId().getPatientInfo().getAge();
			patientSex=meeting.getApplicationId().getPatientInfo().getSex();
			if(patientSex.equals("0")) 
				patientSex="男";
			else 
				patientSex="女";
			//获取科室			
			if(meeting.getApplicationId().getMainDept()!=null&&!StrUtil.isEmptyStr(meeting.getApplicationId().getMainDept().getName())){
				deptName+=meeting.getApplicationId().getMainDept().getName()+"，";
			}
			boolean flag = false;
			if(meeting.getApplicationId().getDepts()!=null){
				int i=0;
				for(ApplicationDepartment applicationDepartment:meeting.getApplicationId().getDepts()){
					if(applicationDepartment.getDepartment()!=null&&!StrUtil.isEmptyStr(applicationDepartment.getDepartment().getName())){
						deptName+=applicationDepartment.getDepartment().getName()+"，";
						//如果部门超过5个时只显示前5个
						if(i==4){
							deptName = deptName.substring(0, deptName.length()-1);
							deptName = deptName+" ......";
							flag = true;
							break;
						}
						i++;
					}
					
				}
			}
			if(!StrUtil.isEmptyStr(deptName)&&!flag){
				deptName = deptName.substring(0, deptName.length()-1);
			}
			
			//初步诊断
			if(meeting.getApplicationId().getIcdDisplayRadio() != 0){
				flag = false;
				if(meeting.getApplicationId().getApplicationICD10s()!=null){
					int i=0;
					for(ApplicationICD10 applicationICD10:meeting.getApplicationId().getApplicationICD10s()){
						if(applicationICD10.getIcd()!=null&&!StrUtil.isEmptyStr(applicationICD10.getIcd().getDiagnosis())){
							applicationICD10s+=applicationICD10.getIcd().getDiagnosis()+"，";
							
							//如果ICD超过10个时只显示前10个
							if(i==9){
								applicationICD10s = applicationICD10s.substring(0, applicationICD10s.length()-1);
								applicationICD10s = applicationICD10s+" ......";
								flag = true;
								break;
							}
							i++;
						}
					}
				}
				if(!StrUtil.isEmptyStr(applicationICD10s)&&!flag){
					applicationICD10s = applicationICD10s.substring(0, applicationICD10s.length()-1);
				}
			} else{
				applicationICD10s = meeting.getApplicationId().getIcdUserDefined();
			}
			
			//获取专家部门
			department=meeting.getApplicationId().getRequester().getDeptId().getName();
			parentDepartment=meeting.getApplicationId().getRequester().getDeptId().getParentDetp().getName();
			
		}
		
		String starttime="";
		if(null!=meeting.getStartTime())
		{
			starttime=DateUtil.getDateTimeStr(meeting.getStartTime());
		}
		String endtime="";
		if(null!=meeting.getEndTime())
		{
			 endtime=DateUtil.getDateTimeStr(meeting.getEndTime());
		}
		String creator=meeting.getCreator().getName();
		String holder=meeting.getHolder().getName();
		String remark=meeting.getRemark();
		String level=meeting.getLevel().getName();
		if(meeting.getMeetingKind()==2){
			String []param={member,title,content,meetingtype,meetingroom,starttime,endtime,creator,holder,level,remark,speaker,deptName};
			return getMessage(name,param);
		}else{
			String []param={member,title,content,meetingtype,meetingroom,starttime,endtime,creator,holder,level,remark,deptName,applicationICD10s,patientName,patientAge,patientSex,department,parentDepartment};
			return getMessage(name,param);
		}					
	}
	public static String getNoticeMessage(String name,MeetingApplication meeting,String member)
	{		
		String title=meeting.getPurpose().replace("<", " &lt;");
		String content=meeting.getMedicalRecord().replace("<", " &lt;");
		String meetingtype=meeting.getMeetingType().getName();
		String starttime="";
		if(null!=meeting.getExpectedTime())
		{
			starttime=DateUtil.getDateTimeStr(meeting.getExpectedTime());
		}
		String endtime="";
		if(null!=meeting.getApplicationTime())
		{
			 endtime=DateUtil.getDateTimeStr(meeting.getApplicationTime());
		}
        String deptName="";
		
		if(meeting.getMainDept()!=null&&!StrUtil.isEmptyStr(meeting.getMainDept().getName())){
			deptName+=meeting.getMainDept().getName()+"，";
		}
		boolean flag = false;
		if(meeting.getDepts()!=null){
			int i=0;
			for(ApplicationDepartment applicationDepartment:meeting.getDepts()){
				if(applicationDepartment.getDepartment()!=null&&!StrUtil.isEmptyStr(applicationDepartment.getDepartment().getName())){
					deptName+=applicationDepartment.getDepartment().getName()+"，";
					//如果部门超过5个时只显示前5个
					if(i==4){
						deptName = deptName.substring(0, deptName.length()-1);
						deptName = deptName+" ......";
						flag = true;
						break;
					}
					i++;
				}				
			}
		}
		if(!StrUtil.isEmptyStr(deptName)&&!flag){
			deptName = deptName.substring(0, deptName.length()-1);
		}
		
		String applicationICD10s="";
		flag = false;
		if(meeting.getIcdDisplayRadio() != 0){
			if(meeting.getApplicationICD10s()!=null){
				int i=0;
				for(ApplicationICD10 applicationICD10:meeting.getApplicationICD10s()){
					if(applicationICD10.getIcd()!=null&&!StrUtil.isEmptyStr(applicationICD10.getIcd().getDiagnosis())){
						applicationICD10s+=applicationICD10.getIcd().getDiagnosis()+"，";
						
						//如果ICD超过10个时只显示前10个
						if(i==9){
							applicationICD10s = applicationICD10s.substring(0, applicationICD10s.length()-1);
							applicationICD10s = applicationICD10s+" ......";
							flag = true;
							break;
						}
						i++;
					}
				}
			}
			if(!StrUtil.isEmptyStr(applicationICD10s)&&!flag){
				applicationICD10s = applicationICD10s.substring(0, applicationICD10s.length()-1);
			}
			
		}else{
			applicationICD10s = meeting.getIcdUserDefined();
		}
		
		String level=meeting.getLevel().getName();
		String refuseReason = meeting.getRefuseReason().replace("<", " &lt;");
		//获取专家部门
		String department=meeting.getRequester().getDeptId().getName();
		String parentDepartment=meeting.getRequester().getDeptId().getParentDetp().getName();
		//获取患者信息
		String patientName=meeting.getPatientInfo().getName();
		String patientAge=meeting.getPatientInfo().getAge();
		String patientSex=meeting.getPatientInfo().getSex();
		if(patientSex.equals("0")) 
			patientSex="男";
		else 
			patientSex="女";
		
		String []param={member,title,content,meetingtype,starttime,endtime,level,refuseReason,department,parentDepartment,patientName,patientAge,patientSex,deptName,applicationICD10s};
		return getMessage(name,param);
	}
	public static String getNoticeMessage(String name,VideoMeetingApp meeting,String member)
	{		
		//String title=meeting.getVideoApplicationPurpose().getName();
		String content=meeting.getLectureContent().replace("<", " &lt;");
		String meetingtype=meeting.getMeetingType().getName();
		String starttime="";
		String speaker="";
		String deptName="";
		if(null!=meeting.getUserName()&&null!=meeting.getUserName().getName()){
			speaker=meeting.getUserName().getName();
			deptName=meeting.getDeptName().getName();
		}
		
		if(null!=meeting.getSuggestDate())
		{
			starttime=DateUtil.getDateTimeStr(meeting.getSuggestDate());
		}
		String endtime="";
		if(null!=meeting.getAppDate())
		{
			 endtime=DateUtil.getDateTimeStr(meeting.getAppDate());
		}
		String level=meeting.getLevel().getName();
		String refuseReason = meeting.getRefuseReason().replace("<", " &lt;");
		String []param={member,content,meetingtype,starttime,endtime,level,refuseReason,speaker,deptName};
		return getMessage(name,param);
	}
	
	public static String getNoticeMessage(String name,VideoMeetingApp meeting)
	{	
		String member=meeting.getVideoRequester().getName();
		//String title=meeting.getVideoApplicationPurpose().getName();
		String content=meeting.getLectureContent().replace("<", " &lt;");
		String starttime="";
		String deptname="";
		String username="";
		if(null!=meeting.getDeptName()&&null!=meeting.getDeptName().getName())
		{
		    deptname=meeting.getDeptName().getName();
		}
		//String applicationPosition=meeting.getApplicationPosition().getName();
		if(null!=meeting.getUserName()&&null!=meeting.getUserName().getName())
		{
			username=meeting.getUserName().getName();
		}		
		if(null!=meeting.getSuggestDate())
		{
			starttime=DateUtil.getDateTimeStr(meeting.getSuggestDate());
		}
		String level=meeting.getLevel().getName();
		String []param={member,content,deptname,username,starttime,level};
		return getMessage(name,param);
	}
	
	//add by wangzhenglin 
	/**
	 * 
	 * @param name
	 * @param application
	 * @return
	 */
	public static String getMessage(String name,ICUMonitor application)
	{			
//		String title=application.getPurpose();
		String porpose=application.getPurpose().replace("<", " &lt;");
//		String dept=application.getDepartment().getName();
		String content=application.getMedicalRecord().replace("<", " &lt;");
		String meetingtype=application.getMeetingType().getName();
		
		String level=application.getLevel().getName();
		String expectedTime="";
		if(null!=application.getExpectedTime())
		{
			expectedTime=DateUtil.getDateTimeStr(application.getExpectedTime());
		}
		String applicationTime="";
		if(null!=application.getApplicationTime())
		{
			applicationTime=DateUtil.getDateTimeStr(application.getApplicationTime());
		}
		
		String deptName="";
		
		if(application.getMainDept()!=null&&!StrUtil.isEmptyStr(application.getMainDept().getName())){
			deptName+=application.getMainDept().getName()+"，";
		}
		boolean flag = false;
		if(application.getDepts()!=null){
			int i=0;
			for(ICUDepartment applicationDepartment:application.getDepts()){
				if(applicationDepartment.getDepartment()!=null&&!StrUtil.isEmptyStr(applicationDepartment.getDepartment().getName())){
					deptName+=applicationDepartment.getDepartment().getName()+"，";
					//如果部门超过5个时只显示前5个
					if(i==4){
						deptName = deptName.substring(0, deptName.length()-1);
						deptName = deptName+" ......";
						flag = true;
						break;
					}
					i++;
				}
				
			}
		}
		if(!StrUtil.isEmptyStr(deptName)&&!flag){
			deptName = deptName.substring(0, deptName.length()-1);
		}
		
		String applicationICD10s="";
		if(application.getIcdDisplayRadio() !=0){
			flag = false;
			if(application.getIcuICD10()!=null){
				int i=0;
				for(ICUICD10 applicationICD10:application.getIcuICD10()){
					if(applicationICD10.getIcd()!=null&&!StrUtil.isEmptyStr(applicationICD10.getIcd().getDiagnosis())){
						applicationICD10s+=applicationICD10.getIcd().getDiagnosis()+"，";
						
						//如果ICD超过10个时只显示前10个
						if(i==9){
							applicationICD10s = applicationICD10s.substring(0, applicationICD10s.length()-1);
							applicationICD10s = applicationICD10s+" ......";
							flag = true;
							break;
						}
						i++;
					}
				}
			}
			if(!StrUtil.isEmptyStr(applicationICD10s)&&!flag){
				applicationICD10s = applicationICD10s.substring(0, applicationICD10s.length()-1);
			}
		}else{
			applicationICD10s = application.getIcdUserDefined();
		}
		
		
		String requester=application.getRequester().getName();
//		String applicant=application.getRequester().getName();
//		String []param={title,porpose,dept,content,meetingtype,level,starttime,endtime,requester,applicant};
		String patientName=application.getPatientInfo().getName();
		String patientAge=application.getPatientInfo().getAge();
		String patientSex=application.getPatientInfo().getSex();
		String department=application.getRequester().getDeptId().getName();
		String parentDepartment=application.getRequester().getDeptId().getParentDetp().getName();
		if(patientSex.equals("0")) 
			patientSex="男";
		else 
			patientSex="女";
		String []param={porpose,content,applicationICD10s,deptName,meetingtype,level,expectedTime,requester,applicationTime,patientName,patientAge,patientSex,department,parentDepartment};
		return getMessage(name,param);
	}

	public static String getNoticeMessage(String name,ICUMonitor meeting,String member)
	{		
		String title=meeting.getPurpose().replace("<", " &lt;");
		String content=meeting.getMedicalRecord().replace("<", " &lt;");
		String meetingtype=meeting.getMeetingType().getName();
		String starttime="";
		if(null!=meeting.getExpectedTime())
		{
			starttime=DateUtil.getDateTimeStr(meeting.getExpectedTime());
		}
		String endtime="";
		if(null!=meeting.getApplicationTime())
		{
			 endtime=DateUtil.getDateTimeStr(meeting.getApplicationTime());
		}
        String deptName="";
		
		if(meeting.getMainDept()!=null&&!StrUtil.isEmptyStr(meeting.getMainDept().getName())){
			deptName+=meeting.getMainDept().getName()+"，";
		}
		boolean flag = false;
		if(meeting.getDepts()!=null){
			int i=0;
			for(ICUDepartment applicationDepartment:meeting.getDepts()){
				if(applicationDepartment.getDepartment()!=null&&!StrUtil.isEmptyStr(applicationDepartment.getDepartment().getName())){
					deptName+=applicationDepartment.getDepartment().getName()+"，";
					//如果部门超过5个时只显示前5个
					if(i==4){
						deptName = deptName.substring(0, deptName.length()-1);
						deptName = deptName+" ......";
						flag = true;
						break;
					}
					i++;
				}				
			}
		}
		if(!StrUtil.isEmptyStr(deptName)&&!flag){
			deptName = deptName.substring(0, deptName.length()-1);
		}
		
		String applicationICD10s="";
		if(meeting.getIcdDisplayRadio() != 0){
			
			flag = false;
			if(meeting.getIcuICD10()!=null){
				int i=0;
				for(ICUICD10 applicationICD10:meeting.getIcuICD10()){
					if(applicationICD10.getIcd()!=null&&!StrUtil.isEmptyStr(applicationICD10.getIcd().getDiagnosis())){
						applicationICD10s+=applicationICD10.getIcd().getDiagnosis()+"，";
						
						//如果ICD超过10个时只显示前10个
						if(i==9){
							applicationICD10s = applicationICD10s.substring(0, applicationICD10s.length()-1);
							applicationICD10s = applicationICD10s+" ......";
							flag = true;
							break;
						}
						i++;
					}
				}
			}
			if(!StrUtil.isEmptyStr(applicationICD10s)&&!flag){
				applicationICD10s = applicationICD10s.substring(0, applicationICD10s.length()-1);
			}
		}else{
			applicationICD10s = meeting.getIcdUserDefined();
		}
		
		String level=meeting.getLevel().getName();
		String refuseReason = meeting.getRefuseReason().replace("<", " &lt;");
		//获取专家部门
		String department=meeting.getRequester().getDeptId().getName();
		String parentDepartment=meeting.getRequester().getDeptId().getParentDetp().getName();
		//获取患者信息
		String patientName=meeting.getPatientInfo().getName();
		String patientAge=meeting.getPatientInfo().getAge();
		String patientSex=meeting.getPatientInfo().getSex();
		if(patientSex.equals("0")) 
			patientSex="男";
		else 
			patientSex="女";
		//申请人：{0}({8}，{9})<br/>目的：{1}<br/>患者：{10}({11}, {12})<br/>病历摘要：{2}<br/>初步诊断：{14}<br/>拟申请科室：{11}<br/>
		String []param={member,title,content,meetingtype,starttime,endtime,level,refuseReason,department,parentDepartment,patientName,patientAge,patientSex,deptName,applicationICD10s};
		return getMessage(name,param);
	}

	
	public static String getMessage(String name,ICUVisitation application){
		String member = application.getAuthorInfo().getName();
		String patientName = application.getPatientInfo().getName();
		String patientSex = application.getPatientInfo().getSex();
		String patientAge = application.getPatientInfo().getAge();
		String expectedTime = "";
		if(null!=application.getExpectedTime())
		{
			expectedTime=DateUtil.getDateTimeStr(application.getExpectedTime());
		}
		if(patientSex.equals("0")) 
			patientSex="男";
		else 
			patientSex="女";
		String[] param = {member,patientName,patientSex,patientAge,expectedTime};
		return getMessage(name,param);
	}

	
	   //远程探视拒绝后发送邮件通知
		public static String getICUVisitMessage(String name,ICUVisitation visit)
		{	 String member = visit.getAuthorInfo().getName();
		     String patientName = visit.getPatientInfo().getName();
		     String patientAge = visit.getPatientInfo().getAge();
		     String patientSex = visit.getPatientInfo().getSex();
		     if(patientSex.equals("0"))  patientSex="男";
			 else  patientSex="女";
		     String refuseReason = visit.getRefuseReason();
		    String []param={member,patientName,patientAge,patientSex,refuseReason};
			return getMessage(name,param);
			 			
		}
		
		public static String getICUVisitPassMessage(String name,ICUVisitation visit){
			 String member = visit.getAuthorInfo().getName();
		     String patientName = visit.getPatientInfo().getName();
		     String patientAge = visit.getPatientInfo().getAge();
		     String patientSex = visit.getPatientInfo().getSex();
		     if(patientSex.equals("0"))  patientSex="男";
			 else  patientSex="女";
		     String starttime = "";
				if(null!=visit.getStartTime())
				{
					starttime=DateUtil.getDateTimeStr(visit.getExpectedTime());
				}
		     String []param={member,patientName,patientAge,patientSex,starttime};
			return getMessage(name,param);
		}
 
}

