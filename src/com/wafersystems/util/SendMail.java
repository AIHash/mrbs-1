package com.wafersystems.util;

import java.util.Map;
import java.util.concurrent.Callable;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.VelocityException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.ui.velocity.VelocityEngineUtils;

/**
 * 邮件发送线程
 * 
 * @author yangsf
 */
public class SendMail implements Callable<Boolean> {
	private static Logger logger = LoggerFactory.getLogger(SendMail.class);
	private Map<String, Object> mailDetails;
	private JavaMailSender mailSender;
	private VelocityEngine velocityEngine;

	public SendMail(Map<String, Object> mailObject, JavaMailSender mailSender, VelocityEngine velocityEngine) {
		this.mailDetails = mailObject;
		this.mailSender = mailSender;
		this.velocityEngine = velocityEngine;
	}

	@Override
	public Boolean call() throws Exception {
		MimeMessage mailMessage = mailSender.createMimeMessage();
		// 设置utf-8或GBK编码，否则邮件会有乱码
		MimeMessageHelper messageHelper = null;
		try {
			messageHelper = new MimeMessageHelper(mailMessage, true, "utf-8");

			String to = (String)mailDetails.get("to");
			logger.debug("邮件的接收者:" + to);
			messageHelper.setTo(to);//接收者

			messageHelper.setFrom((String)mailDetails.get("from"));// 发送者

			messageHelper.setSubject((String)mailDetails.get("subject"));// 主题

			String content = generateEmailContent("notice.vm", mailDetails);
			messageHelper.setText(content, true);//true表示邮件为HTML邮件

			//messageHelper.addInline("a", new File("c:/1.jpg"));
			// File file = new File("E:/文档/DSM/指令.zip");

			//使用MimeUtility.encodeWord()来解决附件名称的中文问题
			// messageHelper.addAttachment(MimeUtility.encodeWord(file.getName()), file);
			mailSender.send(mailMessage);
		} catch (MessagingException e) {
			logger.debug("邮件服务器连接问题，本次发送失败");
			return false;
		}catch (MailSendException e) {
			logger.debug("邮件服务器连接问题，本次发送失败");
			return false;
		}

		logger.debug("邮件发送结束");

		return true;
	}

	/**
	 * 根据模板文件生成邮件正文
	 * 
	 * @param templateName
	 * @param map
	 * @return
	 */
	private String generateEmailContent(String templateName, Map<String, Object> map) {
		try {
			return VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, templateName, map);
		} catch (VelocityException e) {
			logger.error("Error while processing Vilocity template ", e);
		}
		return null;
	}

}