package com.wafersystems.mcu.conference.service;

import java.net.MalformedURLException;
import java.util.List;

import redstone.xmlrpc.XmlRpcException;
import redstone.xmlrpc.XmlRpcFault;

import com.wafersystems.mcu.conference.model.Conference;
import com.wafersystems.mcu.conference.model.ConferenceFilter;
import com.wafersystems.mcu.participant.model.Participant;

public interface ConferenceService {

	/**
	 * <p>@Desc <code>conference.create</code>
	 * 创建一个会议
	 * <p>@Author baininghan
	 * <p>@Date 2014年12月2日
	 * @param conference 会议对象实例，<code>conferenceName</code> 必填，其他都可以默认
	 * @return <code>conferenceGUID</code>
	 */
	public String createConference(Conference conference) throws MalformedURLException, XmlRpcException, XmlRpcFault;
	
	/**
	 * <p>@Desc <code>conference.delete</code>
	 * <p>@Author baininghan
	 * <p>@Date 2014年12月2日
	 * @param conferenceGUID
	 * @throws MalformedURLException
	 * @throws XmlRpcException
	 * @throws XmlRpcFault
	 */
	public void deleteConference(String conferenceGUID) throws MalformedURLException, XmlRpcException, XmlRpcFault;
	/**
	 * <p>@Desc <code>conference.enumerate</code> 
	 * 请求网真服务器上的所有会议信息，完整的枚举响应可能需要多次调用。
	 * 如果没有可以枚举的会议，则此方法不会返回<code>conferences</code>数组
	 * <p>@Author baininghan
	 * <p>@Date 2014年12月2日
	 * @param enumerateID 用于遍历会议信息组，如果忽略此值，则目标设备将重新进行遍历，并返回可用的第一组会议信息
	 * @param activeFilter 枚举过滤，true：只过滤活跃的会议；false：过滤所有的会议（默认）
	 * <pre>满足一下条件，即是一个活跃的会议：
	 * 这个会议至少有一个参与人
	 * 这个会议有一个数字校验码，由H.323或者SIP协议生成
	 * </pre>
	 * @return List{conferences,enumerateID}
	 * <pre>
	 * conferences:Each member is a struct which contains all the returned information about a single conference.
	 * enumerateID:Enumerate calls may return many results so they may include this parameter in the response.
	 * </pre>
	 */
	public List<Conference> enumerateConference(ConferenceFilter conferenceFilter) throws MalformedURLException, XmlRpcException, XmlRpcFault;
	
	/**
	 * <p>@Desc <code>conference.invite</code>
	 * 邀请指定参会人（一个或者多个）到指定会议
	 * <p>@Author baininghan
	 * <p>@Date 2014年12月2日
	 * @param conferenceGUID
	 * @param participants 一组参与人，<code>address</code>（格式如：sip:7005）是必填的，其他选项如果忽略将为默认值
	 * @return List{participantGUID,participantID,address,groupAddressList}
	 */
	public List<Participant> inviteParticipants(String conferenceGUID, List<Participant> participants) throws MalformedURLException, XmlRpcException, XmlRpcFault;
	
	/**
	 * <p>@Desc <code>conference.senddtmf</code>
	 * 发送DTMF序列给部分或所有的参与者在指定的会议。您必须指定会议和DTMF字符串(最多50个字符)。
	 * <p>@Author baininghan
	 * <p>@Date 2014年12月2日
	 * @param conferenceGUID Required
	 * @param dtmf Required 127 length
	 * @param participantGUID 指定某个参会人接收 dtmf
	 * @param omitGUID 指定某个参会人忽略 dtmf
	 */
	public void sendDTMF(String conferenceGUID, String dtmf, String participantGUID, String omitGUID) throws MalformedURLException, XmlRpcException, XmlRpcFault;
	
	/**
	 * <pre>
	 * <p>@Desc <code>conference.sendmessage</code>
	 * 给指定会议的所有参会人发送消息，你也可以指定具体某个参会人接收消息
	 * <p>@Author baininghan
	 * <p>@Date 2014年12月2日
	 * @param conferenceGUID Required
	 * @param message Required 500 length
	 * @param participantGUID 给指定的参会人发送消息
	 * @param position 消息出现在屏幕中的位置，可选如下：
	 * 	1,2, or 3: The message displays near the top of the layout; aligned to the left,center, or right respectively.
	 * 4, 5 (default), or 6: The message displays in the middle of the layout; aligned to the left, center, or right respectively.
	 * 7, 8, or 9: The message displays near the bottom of the layout; aligned to the left, center, or right respectively.
	 * @param duration 消息显示的持续时间，默认30s
	 */
	public void sendMessage(String conferenceGUID, String message, String participantGUID, int position, int duration) throws MalformedURLException, XmlRpcException, XmlRpcFault;
	
	/**
	 * <p>@Desc <code>conference.sendwarning</code>
	 * 给指定会议的所有参会人发送类似“会议即将结束”的警告信息
	 * <p>@Author baininghan
	 * <p>@Date 2014年12月2日
	 * @param conferenceGUID Required
	 * @param secondsRemaining The number of seconds from now in which the conference will end.This value is used when informing CTS endpoints (using XCCP) that the conference is ending.
	 */
	public void sendWarning(String conferenceGUID, int secondsRemaining) throws MalformedURLException, XmlRpcException, XmlRpcFault;
	
	/**
	 * <p>@Desc <code>conference.set</code>
	 * 修改指定会议
	 * <p>@Author baininghan
	 * <p>@Date 2014年12月2日
	 * @param conference
	 */
	public void setConference(Conference conference) throws MalformedURLException, XmlRpcException, XmlRpcFault;
	
	/**
	 * <p>@Desc <code>conference.status</code>
	 * 获取指定会议和他的所有参会人的状态
	 * <p>@Author baininghan
	 * <p>@Date 2014年12月2日
	 * @param conferenceGUID
	 * @param enumerateID
	 * @return
	 */
	public List<Participant> getConferenceStatus(String conferenceGUID, String enumerateID) throws MalformedURLException, XmlRpcException, XmlRpcFault;
	
	/**
	 * <p>@Desc <code>conference.uninvite</code>
	 * 删除指定会议的参会人
	 * <p>@Author baininghan
	 * <p>@Date 2014年12月3日
	 * @param conferenceGUID Required
	 * @param participantListGUID Required 用逗号分隔开每个需要删除的参会人主键， For example, C8200C3F-49CE-4763-98E0-790B4F038995, B1101410-6BB8-487E-9D6F-91E810E80651.
	 * @param participantList 如果<code>conferenceGUID</code>和<code>participantListGUID</code>都忽略不填，则这项必填，用逗号分隔开的参会人地址, 例如：sip:7005,sip:7000
	 */
	public void uninviteConference(String conferenceGUID, String participantListGUID, String participantList) throws MalformedURLException, XmlRpcException, XmlRpcFault;
	
}
