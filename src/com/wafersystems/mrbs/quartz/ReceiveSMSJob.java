package com.wafersystems.mrbs.quartz;

import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.wafersystems.mrbs.GlobalConstent;
import com.wafersystems.mrbs.GlobalParam;
import com.wafersystems.mrbs.service.SystemServiceLogService;
import com.wafersystems.mrbs.service.mas.MtTeleconService;
import com.wafersystems.mrbs.service.meeadmdashbaord.MeeAdmDbdService;
import com.wafersystems.mrbs.service.meeting.MeetingMemberService;
import com.wafersystems.mrbs.sms.APIClient;
import com.wafersystems.mrbs.sms.MOItem;
import com.wafersystems.mrbs.tag.MessageTag;
import com.wafersystems.mrbs.vo.SystemServiceLog;
import com.wafersystems.mrbs.vo.mas.MoTelecon;
import com.wafersystems.mrbs.vo.mas.MtTelecon;
import com.wafersystems.mrbs.vo.meeting.Meeting;
import com.wafersystems.mrbs.vo.meeting.MeetingMember;
import com.wafersystems.util.DateUtil;
import com.wafersystems.util.StrUtil;

@Component("receiveSMSJob")
public class ReceiveSMSJob {

	private static Logger logger = LoggerFactory.getLogger(ReceiveSMSJob.class);
	private MtTeleconService mtTeleconService;
	private SystemServiceLogService serviceLogService;
	private MeeAdmDbdService meeAdmDbdService;
	private MeetingMemberService meetingMemberService;
	
	private String imIP = GlobalParam.sms_db_ip;//移动代理服务器的IP地址
	private String dbName = GlobalParam.sms_db_name;//数据库名
	private String apiCode = GlobalParam.sms_api_code;//接口编码
	private String loginName = GlobalParam.sms_db_user;//接口创建时的接口登录名
	private String loginPWD = GlobalParam.sms_db_pwd;//接口创建时的接口登录密码
	
	private APIClient handler = new APIClient();

	public void doJob() {
		logger.debug("Enter ReceiveSMSJob.doJob");
		logger.debug("Enter ReceiveSMSJob.init");
		logger.debug("ReceiveSMSJob.init imIP="+imIP);
		logger.debug("ReceiveSMSJob.init loginName="+loginName);
		logger.debug("ReceiveSMSJob.init loginPWD="+loginPWD);
		logger.debug("ReceiveSMSJob.init apiCode="+apiCode);
		logger.debug("ReceiveSMSJob.init dbName="+dbName);
		int connectRe = handler.init(imIP, loginName, loginPWD, apiCode,dbName);
        if(connectRe == APIClient.IMAPI_SUCC){
        	logger.debug("ReceiveSMSJob.init Initial success");
        	saveLocalJob();
        	updateLocalJob();
        }
        else if(connectRe == APIClient.IMAPI_CONN_ERR){
        	logger.debug("ReceiveSMSJob.init Connection Fail");
        	SystemServiceLog log = new SystemServiceLog();
    		log.setObjectId(GlobalConstent.SERVICE_SMS_TAG);
    		log.setCreateTime(new Date());
    		log.setName(MessageTag.getMessage("service.sms.receive.desc"));
    		log.setContent(MessageTag.getMessage("service.sms.conn.error"));
			log.setResult(GlobalConstent.SYSTEM_LOG_FAILED);
			serviceLogService.saveSystemServiceLog(log);
        }else if(connectRe == APIClient.IMAPI_API_ERR){
        	logger.debug("ReceiveSMSJob.init imIP not exist ");
        	SystemServiceLog log = new SystemServiceLog();
    		log.setObjectId(GlobalConstent.SERVICE_SMS_TAG);
    		log.setCreateTime(new Date());
    		log.setName(MessageTag.getMessage("service.sms.receive.desc"));
    		log.setContent(MessageTag.getMessage("service.sms.imIP.not.exist"));
			log.setResult(GlobalConstent.SYSTEM_LOG_FAILED);
			serviceLogService.saveSystemServiceLog(log);
        }
	}

	/**
	 * 将服务器上的数据同步到本地
	 */
	private void saveLocalJob() {
		logger.debug("Enter ReceiveSMSJob.saveLocalJob");
		try {
			SystemServiceLog log = new SystemServiceLog();
			log.setObjectId(GlobalConstent.SERVICE_SMS_TAG);
			log.setCreateTime(new Date());
			log.setName(MessageTag.getMessage("service.sms.receive.desc"));
			MOItem[] mos = handler.receiveSM();
			
			if(mos == null)
	        {
				log.setContent(MessageTag.getMessage("service.sms.initOrReceive.error"));
				log.setResult(GlobalConstent.SYSTEM_LOG_FAILED);
				
				logger.debug("ReceiveSMSJob.execJob not init or  receive failed");
				serviceLogService.saveSystemServiceLog(log);
	            return;
	        }
	        else if(mos.length == 0)
	        {
//	        	log.setContent(MessageTag.getMessage("service.sms.mo.is.null"));
//				log.setResult(GlobalConstent.SYSTEM_LOG_FAILED);
				
				logger.debug("ReceiveSMSJob.execJob there is not MO SMS");
//				serviceLogService.saveSystemServiceLog(log);
				return;
	        }else
	        {
	        	int len = 0, i = 0;
	            len = mos.length;
	            MoTelecon moTelecon = null;
	            while(i < len)
	            {
	            	log = new SystemServiceLog();
	    			log.setObjectId(GlobalConstent.SERVICE_SMS_TAG);
	    			log.setCreateTime(new Date());
	    			log.setName(MessageTag.getMessage("service.sms.receive.desc"));
	    			log.setContent(MessageTag.getMessage("service.sms.receive.succ", mos[i].getMobile() + ",短信"));
	    			log.setResult(GlobalConstent.SYSTEM_LOG_SUCCESS);

	    			logger.debug("ReceiveSMSJob.execJob Receive SMS Success Send By: " + mos[i].getMobile());
	    			moTelecon = new MoTelecon();
	    			moTelecon.setSmId(String.valueOf(mos[i].getSmID()));
	    			moTelecon.setMobile(mos[i].getMobile());
	    			moTelecon.setContent(mos[i].getContent());
	    			moTelecon.setMoTime(DateUtil.getDateTime(mos[i].getMoTime()));
	    			moTelecon.setDataExpiredFlag(GlobalConstent.DATE_EXPIRED_FLAG_N);
	    			moTelecon.setDataOperatedFlag(GlobalConstent.DATE_OPERATED_FALG_N);
	    			
	    			logger.debug("SmId : "+moTelecon.getSmId());
	    			logger.debug("Mobile : "+moTelecon.getMobile());
	    			logger.debug("Content : "+moTelecon.getContent());
	    			logger.debug("mos[i].getMoTime() : "+mos[i].getMoTime());
	    			logger.debug("MoTime : "+moTelecon.getMoTime());
	    			mtTeleconService.saveMoTelecon(moTelecon);
	    			serviceLogService.saveSystemServiceLog(log);
	                i++;
	            }
	             
	        }
		} catch (Throwable e) {
			logger.error("Enter ReceiveSMSJob.execJob, catch Exception", e);
		}
	}
	
	/**
	 * 根据本地数据库的接收数据修改接受状态
	 */
	private void updateLocalJob() {
		logger.debug("Enter ReceiveSMSJob.updateLocalJob");
		try {
			
			//根据短信业务代码取得所有远程会诊要处理的短信
			List<MtTelecon> mtTeleconList = mtTeleconService.getMtTeleconList(String.valueOf(GlobalConstent.NOTICE_SEND));
			Meeting meeting = null;
			List<MoTelecon> moTelecons= null;
			String acceptFlag="";//是否接受的标记
			List<MeetingMember> meetingMembers;
			SystemServiceLog log = null;
			for (MtTelecon mtTelecon : mtTeleconList) {
				log = new SystemServiceLog();
    			log.setObjectId(GlobalConstent.SERVICE_SMS_TAG);
    			log.setCreateTime(new Date());
    			log.setName(MessageTag.getMessage("service.sms.do.member"));
    			log.setResult(GlobalConstent.SYSTEM_LOG_SUCCESS);
				moTelecons = mtTeleconService.getMoTeleconList(mtTelecon.getSrcId());
				if(moTelecons==null||moTelecons.isEmpty()){
					logger.debug("There is not any reply content in undisposed values weith SrcId = "+mtTelecon.getSrcId());
					continue;
				}else{
					for(MoTelecon moTelecon:moTelecons){
						acceptFlag = moTelecon.getContent();
						if(compareContentAccepet(acceptFlag)||compareContentRefuse(acceptFlag)){
							break;
						}
					}
				}
				//判断用户是否回复的内容为Y或N
				if(compareContentAccepet(acceptFlag)||compareContentRefuse(acceptFlag)){
					//根据meeting Id 取得审核的meeting数据
					if(mtTelecon.getApplicationId()!=null&&mtTelecon.getApplicationId()!=0){
						if(GlobalConstent.SMS_SERVICE_CODE_007.equals(mtTelecon.getSmsServiceCode())){
							meeting = meeAdmDbdService.getMeetingByApplicationId(mtTelecon.getApplicationId());
						}else if(GlobalConstent.SMS_SERVICE_CODE_008.equals(mtTelecon.getSmsServiceCode())){
							meeting = meeAdmDbdService.getMeetingByVideoAppId(mtTelecon.getApplicationId());
						}else{
							continue;
						}
						
					}
					//根据MeetingId和UserId取得member
					if(meeting!=null&&String.valueOf(GlobalConstent.MEETING_STATE_PENDING).equals(String.valueOf(meeting.getState()))
							&&!StrUtil.isEmptyStr(mtTelecon.getAcceptSmsUserId())){//未开始
						
						meetingMembers = meetingMemberService.getMembersByMeetingIdAndUserId(meeting.getId(), mtTelecon.getAcceptSmsUserId());
						
						if(compareContentAccepet(acceptFlag)){//接受
							for(MeetingMember meetingMember:meetingMembers){
								if(String.valueOf(GlobalConstent.APPLICATION_STATE_NONE).equals(String.valueOf(meetingMember.getAttendState()))){//未接受
									meetingMember.setAttendState(GlobalConstent.APPLICATION_STATE_ACCEPT);
									meetingMemberService.updateMeetingMember(meetingMember);//修改状态为拒绝
									log.setContent("唯一标示是： "+mtTelecon.getSrcId()+", 电话号码是： "+mtTelecon.getMobile()+",回复接受邀请");
									logger.debug("SrcId is "+mtTelecon.getSrcId()+", Mobile is "+mtTelecon.getMobile()+",Reply to accept the invitation");
									serviceLogService.saveSystemServiceLog(log);
								}else{
									logger.debug("SrcId is "+mtTelecon.getSrcId()+", Mobile is "+mtTelecon.getMobile()+",Meeting Member is "+meetingMember.getAttendState());
								}
							}
						}else if(compareContentRefuse(acceptFlag)){//拒绝
							for(MeetingMember meetingMember:meetingMembers){
								if(String.valueOf(GlobalConstent.APPLICATION_STATE_NONE).equals(String.valueOf(meetingMember.getAttendState()))){//未接受
									meetingMember.setAttendState(GlobalConstent.APPLICATION_STATE_REFUSED);
									meetingMemberService.updateMeetingMember(meetingMember);//修改状态为拒绝
									log.setContent("唯一标示是： "+mtTelecon.getSrcId()+", 电话号码是： "+mtTelecon.getMobile()+",回复拒绝邀请");
									logger.debug("SrcId is "+mtTelecon.getSrcId()+", Mobile is "+mtTelecon.getMobile()+",Reply to refuse the invitation");
									serviceLogService.saveSystemServiceLog(log);//记录系统日志
								}else{
									logger.debug("SrcId is "+mtTelecon.getSrcId()+", Mobile is "+mtTelecon.getMobile()+",Meeting Member is "+meetingMember.getAttendState());
								}
							}
						}
						mtTeleconService.updateMoTeleconBySmIdAndUpdateMtTeleconForReplyed(mtTelecon.getSrcId(),mtTelecon);
					}else{
						String state = meeting!=null?String.valueOf(meeting.getState()):" meeting is null";
						logger.debug("meeting is null or meeting State is not pending meeting State = "+state+" weith SrcId = "+mtTelecon.getSrcId()+" content  = "+acceptFlag);
					}
					
				}else{
					logger.debug("The reply content is not 'Y' or 'N' weith SrcId = "+mtTelecon.getSrcId()+" content  = "+acceptFlag);
					mtTeleconService.updateMoTeleconBySmIdAndUpdateMtTeleconForReplyed(mtTelecon.getSrcId(),null);
					continue;
				}
			}
		} catch (Throwable e) {
			logger.error("Enter ReceiveSMSJob.execJob, catch Exception", e);
		}
	}


	public boolean compareContentAccepet(String content){
		if(StrUtil.isEmptyStr(content)){
			return false;
		}
		if(GlobalConstent.ACCEPT_YES.equalsIgnoreCase(content)||GlobalConstent.ACCEPT_ZH_YES.equalsIgnoreCase(content)){
			return true;
		}
		return false;
	}
	
	public boolean compareContentRefuse(String content){
		if(StrUtil.isEmptyStr(content)){
			return false;
		}
		if(GlobalConstent.ACCEPT_NO.equalsIgnoreCase(content)||GlobalConstent.ACCEPT_ZH_NO.equalsIgnoreCase(content)){
			return true;
		}
		return false;
	}

	@PostConstruct
	public void init(){
		logger.debug("SMS Receive Service Start");
		GlobalParam.serviceDesc.put(GlobalConstent.SERVICE_SMS_TAG, MessageTag.getMessage("service.sms.receive.desc"));
	}

	@PreDestroy
	public void destroy(){
		logger.debug("SMS Receive Service End");
	}

	@Resource
	public void setMtTeleconService(MtTeleconService mtTeleconService) {
		this.mtTeleconService = mtTeleconService;
	}

	@Resource
	public void setServiceLogService(SystemServiceLogService serviceLogService) {
		this.serviceLogService = serviceLogService;
	}
	
	@Resource
	public void setMeeAdmDbdService(MeeAdmDbdService meeAdmDbdService) {
		this.meeAdmDbdService = meeAdmDbdService;
	}

	@Resource
	public void setMeetingMemberService(MeetingMemberService meetingMemberService) {
		this.meetingMemberService = meetingMemberService;
	}

}