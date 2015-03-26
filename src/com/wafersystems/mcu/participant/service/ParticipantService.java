package com.wafersystems.mcu.participant.service;

import java.net.MalformedURLException;
import java.util.List;

import redstone.xmlrpc.XmlRpcException;
import redstone.xmlrpc.XmlRpcFault;

import com.wafersystems.mcu.participant.model.Diagnostics;
import com.wafersystems.mcu.participant.model.Participant;

public interface ParticipantService {

	/**
	 * <p>@Desc <code>participant.diagnostics</code>
	 * <p>@Author baininghan
	 * <p>@Date 2014年12月2日
	 * @param participantGUID
	 * @param receiverURI
	 * @param sourceIdentifier
	 * @return
	 */
	public List<Diagnostics> getDiagnostics(String participantGUID, String receiverURI, String sourceIdentifier)  throws MalformedURLException, XmlRpcException, XmlRpcFault;
	
	/**
	 * <p>@Desc <code>participant.enumerate</code>
	 * <p>@Author baininghan
	 * <p>@Date 2014年12月2日
	 * @param enumerateID
	 * @return
	 */
	public List<Participant> enumerateParticipant(String enumerateID) throws MalformedURLException, XmlRpcException, XmlRpcFault;
	
	/**
	 * <p>@Desc <code>participant.set</code>
	 * <p>@Author baininghan
	 * <p>@Date 2014年12月2日
	 * @param participant
	 */
	public  void setParticipant(Participant participant) throws MalformedURLException, XmlRpcException, XmlRpcFault;
	
	/**
	 * <p>@Desc <code>participant.tidylayout</code>
	 * Tidies up the composed video layout sent to the specified participant's endpoint.
	 * <p>@Author baininghan
	 * <p>@Date 2014年12月2日
	 * @param participantGUID
	 */
	public void tidylayout(String participantGUID) throws MalformedURLException, XmlRpcException, XmlRpcFault;
}
