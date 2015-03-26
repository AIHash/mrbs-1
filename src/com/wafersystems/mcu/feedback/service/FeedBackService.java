package com.wafersystems.mcu.feedback.service;

import java.util.List;

import com.wafersystems.mcu.feedback.model.FeedBackReceiver;

public interface FeedBackService {

	/**
	 * <p>@Desc <code>feedbackReceiver.configure</code>
	 * <p>@Author baininghan
	 * <p>@Date 2014年12月2日
	 * @param receiverURI
	 * @param receiverIndex
	 * @param sourceIdentifier
	 * @param subscribedEvents
	 * @return receiverIndex Position of this feedback receiver in the device's table of feedback receivers.
	 */
	public Integer configure(String receiverURI, String receiverIndex, String sourceIdentifier, String subscribedEvents);
	
	/**
	 * <p>@Desc <code>feedbackReceiver.query</code>
	 * 查询一组已经在设备终端上配置过的反馈接收器，不需要接收任何参数，除了需要进行验证的用户名和密码
	 * <p>@Author baininghan
	 * <p>@Date 2014年12月2日
	 * @return
	 */
	public List<FeedBackReceiver> query();
	
	/**
	 * <p>@Desc <code>feedbackReceiver.reconfigure</code>
	 * Overwrites the configuration of an existing feedback receiver with any parameters that you supply. 
	 * The TelePresence Server keeps the current configuration for any parameters that you do not specify.
	 * <p>@Author baininghan
	 * <p>@Date 2014年12月2日
	 * @param receiverURI
	 * @param receiverIndex
	 * @param sourceIdentifier
	 * @param subscribedEvents
	 */
	public void reconfigure(String receiverURI, String receiverIndex, String sourceIdentifier, String subscribedEvents);
	
	/**
	 * <p>@Desc <code>feedbackReceiver.remove</code>
	 * 删除指定的反馈接收器
	 * <p>@Author baininghan
	 * <p>@Date 2014年12月2日
	 * @param receiverIndex
	 */
	public void remove(Integer receiverIndex);
	
	/**
	 * <p>@Desc <code>feedbackReceiver.status</code>
	 * 获得指定反馈接收器的状态
	 * <p>@Author baininghan
	 * <p>@Date 2014年12月2日
	 * @param receiverIndex
	 * @return
	 */
	public List<FeedBackReceiver> getStatus(Integer receiverIndex);
}
