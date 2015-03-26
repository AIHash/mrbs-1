package com.wafersystems.mrbs.quartz;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.velocity.app.VelocityEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import com.wafersystems.mrbs.GlobalConstent;
import com.wafersystems.mrbs.GlobalParam;
import com.wafersystems.mrbs.service.SystemServiceLogService;
import com.wafersystems.mrbs.service.notice.NoticeService;
import com.wafersystems.mrbs.tag.MessageTag;
import com.wafersystems.mrbs.vo.SystemServiceLog;
import com.wafersystems.mrbs.vo.notice.UnifiedNotice;
import com.wafersystems.util.SendMail;

@Component("mailNoticeJob")
public class MailNoticeJob {

	private static Logger logger = LoggerFactory.getLogger(MailNoticeJob.class);
	private NoticeService noticeService;
	private JavaMailSender mailSender;
	private VelocityEngine velocityEngine;
	private String fromMail;
	private ExecutorService executor;
	private SystemServiceLogService serviceLogService;

	/**
	 * Quartz调用的方法
	 */
	public void doJob() {
		execJob();
	}

	private void execJob() {
		try {
			List<UnifiedNotice> notices = noticeService.getNotSendNotice(GlobalConstent.NOTICE_TYPE_MAIL);
			Map<String, Object> map;
			for (UnifiedNotice unifiedNotice : notices) {
				map = new HashMap<String, Object>();
				//收件人为空时忽略
				if(StringUtils.isBlank(unifiedNotice.getReceiver())){
					saveState(false, unifiedNotice);
					continue;
				}else
					map.put("to", unifiedNotice.getReceiver());

				map.put("from", fromMail);
				map.put("subject", unifiedNotice.getTitle());
				map.put("notice", unifiedNotice);

				//错误3次后，忽略请求
				if(unifiedNotice.getResendTimes() != null && unifiedNotice.getResendTimes() == 3){
					//保存状态
					saveState(false, unifiedNotice);
					continue;
				}else if(unifiedNotice.getResendTimes() != null && unifiedNotice.getResendTimes() > 3)
					continue;

				SendMail mail = new SendMail(map, mailSender, velocityEngine);
				Future<Boolean> future = executor.submit(mail);

				//保存状态
				saveState(future.get(), unifiedNotice);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
	}

	@PostConstruct
	public void init(){
		logger.debug("邮件发送服务开始执行");
		executor = Executors.newCachedThreadPool();
		GlobalParam.serviceDesc.put(GlobalConstent.SERVICE_MAIL_TAG, MessageTag.getMessage("service.mail.desc"));
	}

	@PreDestroy
	public void destroy(){
		if(executor != null && !executor.isShutdown())
			executor.shutdown();
		logger.debug("邮件发送服务停止");
	}

	@Resource
	public void setNoticeService(NoticeService noticeService) {
		this.noticeService = noticeService;
	}

	@Resource
	public void setMailSender(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}

	@Resource
	public void setVelocityEngine(VelocityEngine velocityEngine) {
		this.velocityEngine = velocityEngine;
	}

	@Value("${mail.username}")
	public void setFromMail(String fromMail) {
		this.fromMail = fromMail;
	}

	@Resource
	public void setServiceLogService(SystemServiceLogService serviceLogService) {
		this.serviceLogService = serviceLogService;
	}

	//保存状态
	private void saveState(boolean state, UnifiedNotice notice){
		int sendTimes = notice.getResendTimes() == null ? 0 : notice.getResendTimes();

		notice.setResendTimes(++sendTimes);
		notice.setSendTime(new Date());

		SystemServiceLog log = new SystemServiceLog();
		log.setObjectId(GlobalConstent.SERVICE_MAIL_TAG);
		log.setCreateTime(new Date());
		log.setName(MessageTag.getMessage("service.mail.desc"));

		if (state) {
			notice.setState(GlobalConstent.NOTICE_SEND);

			log.setContent(MessageTag.getMessage("service.mail.send.succ", notice.getReceiver() + ",邮件"));
			log.setResult(GlobalConstent.SYSTEM_LOG_SUCCESS);

			logger.debug("邮件发送成功");
		}else{
			if(notice.getReceiver() == null || sendTimes < 3)
				log.setContent(MessageTag.getMessage("service.mail.send.fail", notice.getReceiver() + ",邮件"));
			else
				log.setContent(MessageTag.getMessage("service.mail.send.three.fail",notice.getReceiver() + ",邮件"));

			log.setResult(GlobalConstent.SYSTEM_LOG_FAILED);

			logger.debug("邮件发送失败");
		}
		noticeService.updateNotice(notice);
		serviceLogService.saveSystemServiceLog(log);
	}
}