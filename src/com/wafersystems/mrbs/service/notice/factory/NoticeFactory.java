package com.wafersystems.mrbs.service.notice.factory;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.wafersystems.mrbs.GlobalConstent;
import com.wafersystems.mrbs.service.mas.MtTeleconService;
import com.wafersystems.mrbs.service.notice.NoticeService;
import com.wafersystems.mrbs.vo.mas.MtTelecon;
import com.wafersystems.mrbs.vo.notice.NoticeType;
import com.wafersystems.mrbs.vo.notice.UnifiedNotice;
import com.wafersystems.util.StrUtil;

@Component
public class NoticeFactory {

	private NoticeService noticeService;
	private MtTeleconService mtTeleconService;

	/**
	 * 增加站内通知
	 * @param notice
	 * @return
	 */
	public Integer addInnerNotice(UnifiedNotice notice){
		NoticeType noticeType = new NoticeType();
		noticeType.setId((short)1);
		noticeType.setValue(GlobalConstent.NOTICE_TYPE_INNER);

		notice.setType(noticeType);
		notice.setSendTime(new Date());
		notice.setState(GlobalConstent.NOTICE_NOT_SEND);
		notice.setResendTimes(0);
		noticeService.saveNotice(notice);
		return notice.getId();
	}

	/**
	 * 增加邮件通知
	 * @param notice
	 * @return
	 */
	public Integer addMailNotice(UnifiedNotice notice){
		//当邮箱号或手机号为空时就不往DB存了 add by leo 2011/10/21
		if(notice == null || StrUtil.isEmptyStr(notice.getReceiver())){
			return 0;
		}
		NoticeType noticeType = new NoticeType();
		noticeType.setId((short)2);
		noticeType.setValue(GlobalConstent.NOTICE_TYPE_MAIL);

		notice.setType(noticeType);
		notice.setState(GlobalConstent.NOTICE_NOT_SEND);
		notice.setSendTime(new Date());
		notice.setResendTimes(0);
		noticeService.saveNotice(notice);
		return notice.getId();
	}

	/**
	 * 增加短信通知
	 * @param notice
	 * @return
	 */
	public Integer addSMSNotice(UnifiedNotice notice){
		if(notice == null || StrUtil.isEmptyStr(notice.getReceiver())){
			return 0;
		}
		NoticeType noticeType = new NoticeType();
		noticeType.setId((short)3);
		noticeType.setValue(GlobalConstent.NOTICE_TYPE_SMS);

		notice.setType(noticeType);
		notice.setState(GlobalConstent.NOTICE_NOT_SEND);
		notice.setResendTimes(0);
		noticeService.saveNotice(notice);
		return notice.getId();
	}
	
	/**
	 * 增加短信通知
	 * @param mtTelecon
	 * @return
	 */
	public Integer addMtTelecon(MtTelecon mtTelecon){
		if(mtTelecon == null || StrUtil.isEmptyStr(mtTelecon.getMobile())){
			return 0;
		}
		String content=mtTelecon.getContent();
		if(mtTelecon.getSmsServiceCode().equals(GlobalConstent.SMS_SERVICE_CODE_007)||
				mtTelecon.getSmsServiceCode().equals(GlobalConstent.SMS_SERVICE_CODE_008)){			
			content=content+"  "+"直接回复'"+GlobalConstent.ACCEPT_YES+"'或'"+GlobalConstent.ACCEPT_ZH_YES+"'表示接受，"+
			"回复'"+GlobalConstent.ACCEPT_NO+"'或'"+GlobalConstent.ACCEPT_ZH_NO+"'表示拒绝。";
			mtTelecon.setContent(content);
		}else{
			mtTelecon.setAcceptSmsUserId(null);
			mtTelecon.setApplicationId(null);
			mtTelecon.setSmId("0");
			mtTelecon.setSrcId("0");
			
		}
		//短信内容长度大于120个字时，拆分短信内容
		int i=1;
		MtTelecon subMtTelecon = null;
//        while(content.length()>118){
//        	content=String.valueOf(i)+"."+content;
//        	subMtTelecon =new MtTelecon();        	       	
//        	subMtTelecon.setSmId(mtTelecon.getSmId());
//        	subMtTelecon.setSrcId(mtTelecon.getSrcId());
//    		subMtTelecon.setSendTime(mtTelecon.getSendTime());
//    		subMtTelecon.setDataExpiredFlag(mtTelecon.getDataExpiredFlag());
//    		subMtTelecon.setSmsServiceCode(mtTelecon.getSmsServiceCode());
//    		subMtTelecon.setState(mtTelecon.getState());
//    		subMtTelecon.setResendTimes(mtTelecon.getResendTimes());
//    		subMtTelecon.setAcceptSmsUserId(mtTelecon.getAcceptSmsUserId());
//    		subMtTelecon.setApplicationId(mtTelecon.getApplicationId());
//    		subMtTelecon.setMobile(mtTelecon.getMobile());
//    		    		
//        	String subContent=content.substring(0, 120);
//        	subMtTelecon.setContent(subContent);
//        	mtTeleconService.saveMtTelecon(subMtTelecon);
//        	i++; 
//        	content=content.substring(120, content.length());
//        }
        if(content.length()>0){
        	subMtTelecon =new MtTelecon();        	       	
        	subMtTelecon.setSmId(mtTelecon.getSmId());
        	subMtTelecon.setSrcId(mtTelecon.getSrcId());
    		subMtTelecon.setSendTime(mtTelecon.getSendTime());
    		subMtTelecon.setDataExpiredFlag(mtTelecon.getDataExpiredFlag());
    		subMtTelecon.setSmsServiceCode(mtTelecon.getSmsServiceCode());
    		subMtTelecon.setState(mtTelecon.getState());
    		subMtTelecon.setResendTimes(mtTelecon.getResendTimes());
    		subMtTelecon.setAcceptSmsUserId(mtTelecon.getAcceptSmsUserId());
    		subMtTelecon.setApplicationId(mtTelecon.getApplicationId());
    		subMtTelecon.setMobile(mtTelecon.getMobile());
        	if(i==1){
        		subMtTelecon.setContent(content);
        	}else{
        		content=String.valueOf(i)+"."+content;
        		subMtTelecon.setContent(content);
        	}        	
        	mtTeleconService.saveMtTelecon(subMtTelecon);
        }      		
		return mtTelecon.getId();
	}

	@Resource(type=NoticeService.class)
	public void setNoticeService(NoticeService noticeService) {
		this.noticeService = noticeService;
	}
	
	@Resource(type=MtTeleconService.class)
	public void setMtTeleconService(MtTeleconService mtTeleconService) {
		this.mtTeleconService = mtTeleconService;
	}

}