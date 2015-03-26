package com.wafersystems.mcu.participant.type;

/**
 * <p>@Desc <code>ParticipantType</code>参会人来源类型
 * <p>@Author baininghan
 * <p>@Date 2014年12月4日
 * <p>@version 1.0
 *
 */
public class ParticipantTypeType {
	 /**
	 * The participant was added via the API.
	 */
	public final static String by_address = "by_address";
	 /**
	 * The participant is not in the MCU's endpoint list. 
	 * May have joined conference by dialing in, by being dialed directly via the web interface, or by the API.
	 */
	public final static String by_hoc = "by_hoc";
	 /**
	 * The participant is configured on the MCU and is in the endpoint list. 
	 * May have joined the conference by dialing in or being invited by name.
	 */
	//public final static String by_name = "by_name";
}
