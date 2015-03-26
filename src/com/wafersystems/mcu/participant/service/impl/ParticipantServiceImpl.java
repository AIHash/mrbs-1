package com.wafersystems.mcu.participant.service.impl;

import java.net.MalformedURLException;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import net.sf.ezmorph.object.DateMorpher;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.JSONUtils;
import redstone.xmlrpc.XmlRpcClient;
import redstone.xmlrpc.XmlRpcException;
import redstone.xmlrpc.XmlRpcFault;

import com.wafersystems.mcu.JsonValueProcessorImpl;
import com.wafersystems.mcu.MCUParams;
import com.wafersystems.mcu.XMLClientFactory;
import com.wafersystems.mcu.participant.model.Diagnostics;
import com.wafersystems.mcu.participant.model.Participant;
import com.wafersystems.mcu.participant.service.ParticipantService;

public class ParticipantServiceImpl implements ParticipantService {

	@Override
	public List<Diagnostics> getDiagnostics(String participantGUID,
			String receiverURI, String sourceIdentifier) throws MalformedURLException, XmlRpcException, XmlRpcFault {
		List<Diagnostics> result = new LinkedList<Diagnostics>();
		XmlRpcClient client = XMLClientFactory.getMCUClient();
		List<Map<String, String>> list = new LinkedList<Map<String, String>>();
		Map<String, String> map = new HashMap<String, String>();
		map.put("participantGUID", participantGUID);
		map.put("receiverURI", receiverURI);
		map.put("sourceIdentifier", sourceIdentifier);
		list.add(map);
		
		JsonConfig cfg=new JsonConfig();
		cfg.registerJsonValueProcessor(Date.class, new JsonValueProcessorImpl());
		JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher(new String[] {"yyyy-MM-dd HH:mm:ss"}) );
		
		JSONObject jo = JSONObject.fromObject(client.invoke("participant.diagnostics", list), cfg);
		Diagnostics d = (Diagnostics) JSONObject.toBean(jo, Diagnostics.class);
		result.add(d);
		
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Participant> enumerateParticipant(String enumerateID) throws XmlRpcException, XmlRpcFault, MalformedURLException {
		List<Participant> result = new LinkedList<Participant>();
		XmlRpcClient client = XMLClientFactory.getMCUClient();
		List<Map<String, String>> list = new LinkedList<Map<String, String>>();
		Map<String, String> map = new HashMap<String, String>();
		map.put("authenticationUser", MCUParams.getInstance().getAuthenticationUser());
		map.put("authenticationPassword", MCUParams.getInstance().getAuthenticationPassword());
		map.put("enumerateID", enumerateID);
		list.add(map);
		
		JsonConfig cfg=new JsonConfig();
		cfg.registerJsonValueProcessor(Date.class, new JsonValueProcessorImpl());
		JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher(new String[] {"yyyy-MM-dd HH:mm:ss"}) );
		
		JSONObject jo = JSONObject.fromObject(client.invoke("participant.enumerate", list), cfg);
		result = (List<Participant>) JSONArray.toCollection(jo.getJSONArray("participants"), Participant.class);
		
		return result;
		
	}

	@Override
	public void setParticipant(Participant participant) throws MalformedURLException, XmlRpcException, XmlRpcFault {
		XmlRpcClient client = XMLClientFactory.getMCUClient();
		List<Participant> list = new LinkedList<Participant>();
		list.add(participant);
		
		client.invoke("participant.set", list);
	}

	@Override
	public void tidylayout(String participantGUID) throws MalformedURLException, XmlRpcException, XmlRpcFault {
		List<Map<String, String>> list = new LinkedList<Map<String, String>>();
		Map<String, String> map = new HashMap<String, String>();
		map.put("authenticationUser", MCUParams.getInstance().getAuthenticationUser());
		map.put("authenticationPassword", MCUParams.getInstance().getAuthenticationPassword());
		map.put("participantGUID", participantGUID);
		list.add(map);
		
		XMLClientFactory.getMCUClient().invoke("participant.tidylayout", list);
	}

}
