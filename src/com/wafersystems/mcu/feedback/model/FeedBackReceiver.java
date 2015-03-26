package com.wafersystems.mcu.feedback.model;

import java.util.List;

/**
 * <p>@Desc 反馈接收器信息
 * <p>@Author baininghan
 * <p>@Date 2014年12月2日
 * <p>@version 1.0
 *
 */
public class FeedBackReceiver {

	/**
	 * Position of this feedback receiver in the table of feedback receivers. The index number is also the feedback receiver ID.
	 * 1~20
	 */
	private Integer index;
	/**
	 * Source identifier string, which can be empty. 
	 * The originating device uses this parameter to identify itself to the listening receiver (or receivers). 
	 * If the parameter is not explicitly set, the device identifies itself with the MAC address of its Ethernet port A interface.
	 * string(255) ASCII characters only
	 */
	private String sourceIdentifier;
	/**
	 * Fully-qualified http or https URI (for example, http://tms1:8080/RPC2) to which feedback events are sent.
	 * 255 length
	 */
	private String receiverURI;
	
	/**
	 * Array of strings identifying the event names that are enabled for this feedback receiver.
	 */
	private List<FeedBackEvents> subscribedEvents;
	
	
	
	/**
	 * @return the subscribedEvents
	 */
	public List<FeedBackEvents> getSubscribedEvents() {
		return subscribedEvents;
	}
	/**
	 * @param subscribedEvents the subscribedEvents to set
	 */
	public void setSubscribedEvents(List<FeedBackEvents> subscribedEvents) {
		this.subscribedEvents = subscribedEvents;
	}
	/**
	 * @return the index
	 */
	public Integer getIndex() {
		return index;
	}
	/**
	 * @param index the index to set
	 */
	public void setIndex(Integer index) {
		this.index = index;
	}
	/**
	 * @return the sourceIdentifier
	 */
	public String getSourceIdentifier() {
		return sourceIdentifier;
	}
	/**
	 * @param sourceIdentifier the sourceIdentifier to set
	 */
	public void setSourceIdentifier(String sourceIdentifier) {
		this.sourceIdentifier = sourceIdentifier;
	}
	/**
	 * @return the receiverURI
	 */
	public String getReceiverURI() {
		return receiverURI;
	}
	/**
	 * @param receiverURI the receiverURI to set
	 */
	public void setReceiverURI(String receiverURI) {
		this.receiverURI = receiverURI;
	}
	
	
}
