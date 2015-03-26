package com.wafersystems.mcu.conference.service.impl;

import java.net.MalformedURLException;
import java.util.ArrayList;
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

import com.wafersystems.mcu.MCUParams;
import com.wafersystems.mcu.JsonValueProcessorImpl;
import com.wafersystems.mcu.XMLClientFactory;
import com.wafersystems.mcu.conference.model.Conference;
import com.wafersystems.mcu.conference.model.ConferenceFilter;
import com.wafersystems.mcu.conference.service.ConferenceService;
import com.wafersystems.mcu.participant.model.Participant;

public class ConferenceServiceImpl implements ConferenceService {

	@Override
	public String createConference(Conference conference) throws MalformedURLException, XmlRpcException, XmlRpcFault {
		XmlRpcClient client = XMLClientFactory.getMCUClient();
		
		List<Conference> list = new ArrayList<Conference>();
		conference.setAuthenticationUser(MCUParams.getInstance().getAuthenticationUser());
		conference.setAuthenticationPassword(MCUParams.getInstance().getAuthenticationPassword());
		list.add(conference);
		String conferenceGUID = "";
		
		JSONObject jo = JSONObject.fromObject(client.invoke("conference.create", list));
		
		if(!jo.isEmpty() && jo.containsKey("conferenceGUID")){
			conferenceGUID = (String)jo.get("conferenceGUID");
		}
		
		return conferenceGUID;
	}

	@Override
	public void deleteConference(String conferenceGUID) throws MalformedURLException, XmlRpcException, XmlRpcFault{
		XmlRpcClient client = XMLClientFactory.getMCUClient();
		Map<String, String> map = new HashMap<String, String>();
		map.put("authenticationUser", MCUParams.getInstance().getAuthenticationUser());
		map.put("authenticationPassword", MCUParams.getInstance().getAuthenticationPassword());
		map.put("conferenceGUID", conferenceGUID);
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		list.add(map);
		
		client.invoke("conference.delete", list);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Conference> enumerateConference(ConferenceFilter conferenceFilter) throws MalformedURLException, XmlRpcException, XmlRpcFault {
		XmlRpcClient client = XMLClientFactory.getMCUClient();
		
		conferenceFilter.setAuthenticationUser(MCUParams.getInstance().getAuthenticationUser());
		conferenceFilter.setAuthenticationPassword(MCUParams.getInstance().getAuthenticationPassword());
		List<ConferenceFilter> list = new LinkedList<ConferenceFilter>();
		list.add(conferenceFilter);
		
		JsonConfig cfg=new JsonConfig();
		cfg.registerJsonValueProcessor(Date.class, new JsonValueProcessorImpl());
		JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher(new String[] {"yyyy-MM-dd HH:mm:ss"}) );
		
		JSONObject jo = JSONObject.fromObject(client.invoke("conference.enumerate", list), cfg);
		
		List<Conference> result = new LinkedList<Conference>();
		if(jo.containsKey("conferences")){
			result = (List<Conference>) JSONArray.toCollection(jo.getJSONArray("conferences"), Conference.class);
		}else{
			result = new ArrayList<Conference>();
		}
		
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Participant> inviteParticipants(String conferenceGUID, List<Participant> participants) 
			throws XmlRpcException, XmlRpcFault, MalformedURLException {
		XmlRpcClient client = XMLClientFactory.getMCUClient();
		List<Map<String, Object>> list = new LinkedList<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("authenticationUser", MCUParams.getInstance().getAuthenticationUser());
		map.put("authenticationPassword", MCUParams.getInstance().getAuthenticationPassword());
		map.put("conferenceGUID", conferenceGUID);
		map.put("participants", participants);
		list.add(map);
		
		JsonConfig cfg = new JsonConfig();
		cfg.registerJsonValueProcessor(Date.class, new JsonValueProcessorImpl());
		JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher(new String[] {"yyyy-MM-dd HH:mm:ss"}) );
		
		JSONObject jo = JSONObject.fromObject(client.invoke("conference.invite", list), cfg);
		List<Participant> result = (List<Participant>) JSONArray.toCollection(jo.getJSONArray("participantList"), Participant.class);
		
		return result;
	}

	@Override
	public void sendDTMF(String conferenceGUID, String dtmf,
			String participantGUID, String omitGUID) {

	}

	@Override
	public void sendMessage(String conferenceGUID, String message,
			String participantGUID, int position, int duration) throws XmlRpcException, MalformedURLException, XmlRpcFault {
		List<Map<String, Object>> list = new LinkedList<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("authenticationUser", MCUParams.getInstance().getAuthenticationUser());
		map.put("authenticationPassword", MCUParams.getInstance().getAuthenticationPassword());
		map.put("conferenceGUID", conferenceGUID);
		map.put("message", message);
		map.put("participantGUID", participantGUID);
		map.put("position", position);
		map.put("duration", duration);
		list.add(map);
		
		XMLClientFactory.getMCUClient().invoke("conference.sendwarning", list);
	}

	@Override
	public void sendWarning(String conferenceGUID, int secondsRemaining) throws XmlRpcException, MalformedURLException, XmlRpcFault {
		List<Map<String, Object>> list = new LinkedList<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("authenticationUser", MCUParams.getInstance().getAuthenticationUser());
		map.put("authenticationPassword", MCUParams.getInstance().getAuthenticationPassword());
		map.put("conferenceGUID", conferenceGUID);
		map.put("secondsRemaining", secondsRemaining);
		list.add(map);
		
		XMLClientFactory.getMCUClient().invoke("conference.sendwarning", list);
		
	}

	@Override
	public void setConference(Conference conference) throws MalformedURLException, XmlRpcException, XmlRpcFault {
		XmlRpcClient client = XMLClientFactory.getMCUClient();
		List<Conference> list = new LinkedList<Conference>();
		conference.setAuthenticationUser(MCUParams.getInstance().getAuthenticationUser());
		conference.setAuthenticationPassword(MCUParams.getInstance().getAuthenticationPassword());
		list.add(conference);
		
		client.invoke("conference.set", list);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Participant> getConferenceStatus(String conferenceGUID,
			String enumerateID) throws MalformedURLException, XmlRpcException, XmlRpcFault {
		List<Participant> result = new LinkedList<Participant>();
		XmlRpcClient client = XMLClientFactory.getMCUClient();
		List<Map<String, Object>> list = new LinkedList<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("authenticationUser", MCUParams.getInstance().getAuthenticationUser());
		map.put("authenticationPassword", MCUParams.getInstance().getAuthenticationPassword());
		map.put("conferenceGUID", conferenceGUID);
		list.add(map);
		
		JsonConfig cfg = new JsonConfig();
		cfg.registerJsonValueProcessor(Date.class, new JsonValueProcessorImpl());
		JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher(new String[] {"yyyy-MM-dd HH:mm:ss"}) );
		
		JSONObject jo = JSONObject.fromObject(client.invoke("conference.status", list), cfg);
		
		if(jo.containsKey("participantList")){
			result = (List<Participant>) JSONArray.toCollection(jo.getJSONArray("participantList"), Participant.class);
		}
		return result;
	}

	@Override
	public void uninviteConference(String conferenceGUID,
			String participantListGUID, String participantList) throws MalformedURLException, XmlRpcException, XmlRpcFault {
		XmlRpcClient client = XMLClientFactory.getMCUClient();
		List<Map<String, Object>> list = new LinkedList<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("authenticationUser", MCUParams.getInstance().getAuthenticationUser());
		map.put("authenticationPassword", MCUParams.getInstance().getAuthenticationPassword());
		map.put("conferenceGUID", conferenceGUID);
		map.put("participantListGUID", participantListGUID==null?"":participantListGUID);
		map.put("participantList", participantList);
		list.add(map);
		
		client.invoke("conference.uninvite", list);

	}

}
