package com.wafersystems.mrbs.quartz;

import java.text.SimpleDateFormat;
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
import com.wafersystems.mrbs.sms.APIClient;
import com.wafersystems.mrbs.tag.MessageTag;
import com.wafersystems.mrbs.vo.SystemServiceLog;
import com.wafersystems.mrbs.vo.mas.MtTelecon;
import com.wafersystems.util.StrUtil;

@Component("sendSMSJob")
public class SendSMSJob {

	private static Logger logger = LoggerFactory.getLogger(SendSMSJob.class);
	private MtTeleconService mtTeleconService;
	private SystemServiceLogService serviceLogService;
	//各种错误类型
	private static final int RECEIVER_EMPTY = 1111;//接收人为空
	
	private String imIP = GlobalParam.sms_db_ip;//移动代理服务器的IP地址
	private String dbName = GlobalParam.sms_db_name;//数据库名
	private String apiCode = GlobalParam.sms_api_code;//接口编码
	private String loginName = GlobalParam.sms_db_user;//接口创建时的接口登录名
	private String loginPWD = GlobalParam.sms_db_pwd;//接口创建时的接口登录密码
	SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private APIClient handler = new APIClient();

	public void doJob() {
		logger.debug("Enter SendSMSJob.doJob");
		logger.debug("Enter SendSMSJob.init");
		logger.debug("SendSMSJob.init imIP="+imIP);
		logger.debug("SendSMSJob.init loginName="+loginName);
		logger.debug("SendSMSJob.init loginPWD="+loginPWD);
		logger.debug("SendSMSJob.init apiCode="+apiCode);
		logger.debug("SendSMSJob.init dbName="+dbName);
		int connectRe = handler.init(imIP, loginName, loginPWD, apiCode,dbName);
        if(connectRe == APIClient.IMAPI_SUCC){
        	logger.debug("SendSMSJob.init Initial success");
        	execJob();
        }
        else if(connectRe == APIClient.IMAPI_CONN_ERR){
        	logger.error("SendSMSJob.init Connection Fail");
        	SystemServiceLog log = new SystemServiceLog();
    		log.setObjectId(GlobalConstent.SERVICE_SMS_TAG);
    		log.setCreateTime(new Date());
    		log.setName(MessageTag.getMessage("service.sms.desc"));
    		log.setContent(MessageTag.getMessage("service.sms.conn.error"));
			log.setResult(GlobalConstent.SYSTEM_LOG_FAILED);
			serviceLogService.saveSystemServiceLog(log);
        }else if(connectRe == APIClient.IMAPI_API_ERR){
        	logger.error("SendSMSJob.init imIP not exist ");
        	SystemServiceLog log = new SystemServiceLog();
    		log.setObjectId(GlobalConstent.SERVICE_SMS_TAG);
    		log.setCreateTime(new Date());
    		log.setName(MessageTag.getMessage("service.sms.desc"));
    		log.setContent(MessageTag.getMessage("service.sms.imIP.not.exist"));
			log.setResult(GlobalConstent.SYSTEM_LOG_FAILED);
			serviceLogService.saveSystemServiceLog(log);
        }
	}

	private void execJob() {
		logger.debug("Enter SendSMSJob.execJob");
		try {
			List<MtTelecon> mtTeleconList = mtTeleconService.getMtTeleconList(String.valueOf(GlobalConstent.NOTICE_NOT_SEND));
			for (MtTelecon mtTelecon : mtTeleconList) {
				//收短信人电话为空时忽略
				if(StrUtil.isEmptyStr(mtTelecon.getMobile())){
					saveState(RECEIVER_EMPTY, mtTelecon);
					continue;
				}
				if(mtTelecon.getResendTimes() != null && mtTelecon.getResendTimes() >= 3)
					continue;
				String content = new String(mtTelecon.getContent().getBytes(), "GBK");
				if(mtTelecon.getSendTime()!=null){
					int result = handler.sendSM(mtTelecon.getMobile().split(","), content,sdfDate.format(mtTelecon.getSendTime()), Long.parseLong(mtTelecon.getSmId()), Long.parseLong(mtTelecon.getSrcId()));//发送短信
					//保存状态
					saveState(result, mtTelecon);
				}else{
					int result = handler.sendSM(mtTelecon.getMobile().split(","), content,Long.parseLong(mtTelecon.getSmId()), Long.parseLong(mtTelecon.getSrcId()));//发送短信
					//保存状态
					saveState(result, mtTelecon);
				}
			}
		} catch (Throwable e) {
			logger.error("Enter SendSMSJob.execJob, catch Exception", e);
		}
	}

	@PostConstruct
	public void init(){
		logger.debug("SMS Send Service Start");
		GlobalParam.serviceDesc.put(GlobalConstent.SERVICE_SMS_TAG, MessageTag.getMessage("service.sms.desc"));
	}

	@PreDestroy
	public void destroy(){
		logger.debug("SMS Send Service End");
	}

	@Resource
	public void setMtTeleconService(MtTeleconService mtTeleconService) {
		this.mtTeleconService = mtTeleconService;
	}

	@Resource
	public void setServiceLogService(SystemServiceLogService serviceLogService) {
		this.serviceLogService = serviceLogService;
	}

	//保存状态
	private void saveState(int result, MtTelecon mtTelecon){
		int sendTimes = mtTelecon.getResendTimes() == null ? 0 : mtTelecon.getResendTimes();

		mtTelecon.setResendTimes(++sendTimes);
//		mtTelecon.setSendTime(new Date());

		SystemServiceLog log = new SystemServiceLog();
		log.setObjectId(GlobalConstent.SERVICE_SMS_TAG);
		log.setCreateTime(new Date());
		log.setName(MessageTag.getMessage("service.sms.desc"));

		if(result == APIClient.IMAPI_SUCC) {//成功
			mtTelecon.setState(GlobalConstent.NOTICE_SEND);

			log.setContent(MessageTag.getMessage("service.sms.send.succ", mtTelecon.getMobile() + ",短信"));
			log.setResult(GlobalConstent.SYSTEM_LOG_SUCCESS);

			logger.debug("SendSMSJob.saveState To : " + mtTelecon.getMobile() + "Send SMS Success ");
			
		}else if(result == APIClient.IMAPI_INIT_ERR) {//未初始化
			log.setContent(MessageTag.getMessage("service.sms.init.error"));
			log.setResult(GlobalConstent.SYSTEM_LOG_FAILED);
			
			logger.error("SendSMSJob.saveState To :" + mtTelecon.getMobile() + "is error,not init ");
			
		}else if(result == APIClient.IMAPI_CONN_ERR){//数据库连接失败
			log.setContent(MessageTag.getMessage("service.sms.conn.error"));
			log.setResult(GlobalConstent.SYSTEM_LOG_FAILED);
			
			logger.error("SendSMSJob.saveState To :" + mtTelecon.getMobile() + "is error,connection database failed ");
			
		}else if(result == RECEIVER_EMPTY){//接收人为空
			mtTelecon.setState(GlobalConstent.NOTICE_SEND);
			mtTelecon.setResendTimes(3);
//			mtTelecon.setSendTime(new Date());

			log.setContent(MessageTag.getMessage("service.sms.no.reciver"));
			log.setResult(GlobalConstent.SYSTEM_LOG_FAILED);
			
			logger.error("SendSMSJob.saveState To :" + mtTelecon.getMobile() + "is error,reciver is null ");
			
		}else if(result == APIClient.IMAPI_DATA_TOOLONG){//短信内容太长
			mtTelecon.setState(GlobalConstent.NOTICE_SEND);
			mtTelecon.setResendTimes(3);
//			mtTelecon.setSendTime(new Date());

			log.setContent(MessageTag.getMessage("service.sms.content.too.lang"));
			log.setResult(GlobalConstent.SYSTEM_LOG_FAILED);
			
			logger.error("SendSMSJob.saveState To :" + mtTelecon.getMobile() + "is error,content is too lang,content = "+mtTelecon.getContent());
		}else if(result == APIClient.IMAPI_DATA_ERR){//参数错误
			mtTelecon.setState(GlobalConstent.NOTICE_SEND);
			mtTelecon.setResendTimes(3);
//			mtTelecon.setSendTime(new Date());

			log.setContent(MessageTag.getMessage("service.sms.parameter.error"));
			log.setResult(GlobalConstent.SYSTEM_LOG_FAILED);
			
			logger.error("SendSMSJob.saveState To :" + mtTelecon.getMobile() + "is error,parameter is error");
		}else if(result == APIClient.IMAPI_INS_ERR){//数据库插入错误
			mtTelecon.setState(GlobalConstent.NOTICE_SEND);
			mtTelecon.setResendTimes(3);
//			mtTelecon.setSendTime(new Date());

			log.setContent(MessageTag.getMessage("service.sms.database.insert.mistakes.error"));
			log.setResult(GlobalConstent.SYSTEM_LOG_FAILED);
			
			logger.error("SendSMSJob.saveState To :" + mtTelecon.getMobile() + "is error,database insert mistakes");
		}else{
			if(mtTelecon.getMobile() == null || sendTimes < 3)
				log.setContent(MessageTag.getMessage("service.mail.send.fail", mtTelecon.getMobile() + ",短信"));
			else
				log.setContent(MessageTag.getMessage("service.mail.send.three.fail", mtTelecon.getMobile() + ",短信"));

			log.setResult(GlobalConstent.SYSTEM_LOG_FAILED);

			logger.error("SendSMSJob.saveState To :" + mtTelecon.getMobile() + "Send SMS failed ");
		}
		mtTeleconService.updateMtTelecon(mtTelecon);
		serviceLogService.saveSystemServiceLog(log);
	}

}