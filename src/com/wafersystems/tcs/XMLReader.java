package com.wafersystems.tcs;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.wafersystems.tcs.vo.ConferenceVO;
import com.wafersystems.tcs.vo.DataVO;
import com.wafersystems.tcs.vo.DownloadableMovieVO;
import com.wafersystems.tcs.vo.LabelVO;
import com.wafersystems.tcs.vo.OwnerVO;
import com.wafersystems.tcs.vo.WatchableMovieVO;

public class XMLReader {
	
	public static String prepareConference = "prepareConference";
	public static String requestConferenceID = "requestConferenceID";
	public static String dial = "dial";
	public static String clusterDial = "clusterDial";
	public static String disconnectCall = "disconnectCall";
	public static String disconnectAllCalls = "disconnectAllCalls";
	public static String getCallInfo = "getCallInfo";
	public static String getSystemHealth = "getSystemHealth";
	public static String getCallCapacity = "getCallCapacity";
	public static String getSystemInformation = "getSystemInformation";
	public static String bindAliasConferenceID = "bindAliasConferenceID";
	public static String unBindAliasConferenceID = "unBindAliasConferenceID";
	public static String getRecordingAlias = "getRecordingAlias";
	public static String addRecordingAlias = "addRecordingAlias";
	public static String modifyRecordingAlias = "modifyRecordingAlias";
	public static String deleteRecordingAlias = "deleteRecordingAlias";
	public static String restartService = "restartService";
	public static String modifyConference = "modifyConference";
	public static String getConference = "getConference";
	public static String getConferences = "getConferences";
	public static String getConferenceCount = "getConferenceCount";
	public static String deleteRecording = "deleteRecording";
	public static String getConferenceThumbnails = "getConferenceThumbnails";
	public static String setConferenceThumbnailDefault = "setConferenceThumbnailDefault";
	public static String deleteConferenceThumbnail = "deleteConferenceThumbnail";
	
	/**
	 * readerXMLByTagName 根据方法名取得其对应XML的TagName，根据TagName取得TagValue（不包含有循环的XML）
	 * @param strXML 要解析的XML，String类型
	 * @param methodName 需要解析XML的方法名
	 * @return Map<String, Object> XML中的name和Value值
	 * @throws Exception 
	 */
	public static Map<String, Object> readerXMLByTagName(String strXML,String methodName) throws Exception{
		//XML中的name和Value值
		Map<String, Object> readerXMLMap = null;
		try{
			//需要从XML中读取值的tagName集合
			List<String> tagNameList = getTagNameList(methodName);
			//判断传人的XML与tagName是否有值
			if(strXML!=null&&!"".equals(strXML)&&tagNameList!=null&&!tagNameList.isEmpty()){
				readerXMLMap = new HashMap<String, Object>();
				
				//将要解析的XML转成Document对象，便于根据tagName取得tagValue
				StringReader stringReader  =  new StringReader(strXML);
				InputSource  inputSource  =  new  InputSource(stringReader);
				DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
				DocumentBuilder builder=factory.newDocumentBuilder();
				Document document = builder.parse(inputSource );
				
				String tagValue = null;
				for(String tagName : tagNameList){
					if("DataVO".equals(tagName)){
						readerXMLMap.put(tagName, XMLReader.getDataVO(document));
					}else if("ConferenceVO".equals(tagName)&&document.getElementsByTagName("ns1:Conference")!=null){
						NodeList list1  = document.getChildNodes();
						Element element1 = (Element)list1.item(0);
						Element element2 = (Element)element1.getChildNodes().item(0);
						Element element3 = (Element)element2.getChildNodes().item(0);
						Element element4 = (Element)element3.getChildNodes().item(0);
						readerXMLMap.put(tagName, getConferenceVO(element4));
					}else if("ConferenceVOs".equals(tagName)&&document.getElementsByTagName("ns1:Conference")!=null){
						NodeList list1  = document.getChildNodes();
						Element element1 = (Element)list1.item(0);
						Element element2 = (Element)element1.getChildNodes().item(0);
						Element element3 = (Element)element2.getChildNodes().item(0);
						Element element4 = (Element)element3.getChildNodes().item(0);
						NodeList conferences = element4.getChildNodes();
						List<ConferenceVO> conferenceVOList = new ArrayList<ConferenceVO>();
						int count = conferences.getLength();
						for(int i=0;i<count;i++){
							Element element = (Element) conferences.item(i); 
							conferenceVOList.add(getConferenceVO(element));
						}
						readerXMLMap.put(tagName, conferenceVOList);
					}else if("GetConferenceThumbnails".equals(tagName)&&document.getElementsByTagName("ns1:GetConferenceThumbnailsResult")!=null&&document.getElementsByTagName("ns1:GetConferenceThumbnailsResult").item(0)!=null){
						Element element = (Element) document.getElementsByTagName("ns1:GetConferenceThumbnailsResult").item(0); 
						readerXMLMap.put(tagName, getConferenceThumbnailsResult(element));
					}else if(document.getElementsByTagName(tagName)!=null&&document.getElementsByTagName(tagName).item(0)!=null
							&&document.getElementsByTagName(tagName).item(0).getFirstChild()!=null){
						tagValue = document.getElementsByTagName(tagName).item(0).getFirstChild().getNodeValue();
						readerXMLMap.put(tagName.substring(4), tagValue);
						System.out.println(tagName.substring(4)+" : "+tagValue);
					}else{
						readerXMLMap.put(tagName.substring(4), null);
						System.out.println(tagName.substring(4)+" : "+null);
					}
				}
				
			}
			
			
		}catch(Exception e){
			throw e;
		}
		return readerXMLMap;
	}
	
	/**
	 * readerXMLByTagName 根据方法名取得其对应XML的循环TagName，根据TagName取得TagValue
	 * @param strXML 要解析的XML，String类型
	 * @param methodName 需要解析XML的方法名
	 * @return Map<String, Object> XML中的name和Value值
	 * @throws Exception 
	 */
	public static String readerXMLByErrorMessage(String errorMessage) throws Exception{
		String returnValue = "";
		try{
			//判断传人的XML与tagName是否有值
			if(errorMessage!=null&&!"".equals(errorMessage)){

				//将要解析的XML转成Document对象，便于根据tagName取得tagValue
				StringReader stringReader  =  new StringReader(errorMessage);
				InputSource  inputSource  =  new  InputSource(stringReader);
				DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
				DocumentBuilder builder=factory.newDocumentBuilder();
				Document document = builder.parse(inputSource );
				String faultcode = "";
				String faultstring = "";
				if(document.getElementsByTagName("faultcode")!=null&&document.getElementsByTagName("faultcode").item(0)!=null
						&&document.getElementsByTagName("faultcode").item(0).getFirstChild()!=null){
					faultcode = document.getElementsByTagName("faultcode").item(0).getFirstChild().getNodeValue();
					System.out.println("faultcode : "+faultcode);
				}
				if(document.getElementsByTagName("faultstring")!=null&&document.getElementsByTagName("faultstring").item(0)!=null
						&&document.getElementsByTagName("faultstring").item(0).getFirstChild()!=null){
					faultstring = document.getElementsByTagName("faultstring").item(0).getFirstChild().getNodeValue();
					System.out.println("faultstring : "+faultstring);
				}
				returnValue = "faultcode is "+faultcode+",faultstring is "+faultstring;
			}
			
		}catch(Exception e){
			throw e;
		}
		return returnValue;
	}
	
	public static DataVO getDataVO(Document document){
		DataVO dataVO = new DataVO();
		
		if(document.getElementsByTagName("ns1:AliasID")!=null&&document.getElementsByTagName("ns1:AliasID").item(0)!=null
				&&document.getElementsByTagName("ns1:AliasID").item(0).getFirstChild()!=null){
			dataVO.setAliasID(document.getElementsByTagName("ns1:AliasID").item(0).getFirstChild().getNodeValue());
		}else{
			dataVO.setAliasID("");
		}
		
		if(document.getElementsByTagName("ns1:Name")!=null&&document.getElementsByTagName("ns1:Name").item(0)!=null
				&&document.getElementsByTagName("ns1:Name").item(0).getFirstChild()!=null){
			dataVO.setName(document.getElementsByTagName("ns1:Name").item(0).getFirstChild().getNodeValue());
		}else{
			dataVO.setName("");
		}
		
		if(document.getElementsByTagName("ns1:SystemAlias")!=null&&document.getElementsByTagName("ns1:SystemAlias").item(0)!=null
				&&document.getElementsByTagName("ns1:SystemAlias").item(0).getFirstChild()!=null){
			dataVO.setSystemAlias(Boolean.valueOf(document.getElementsByTagName("ns1:SystemAlias").item(0).getFirstChild().getNodeValue()));
		}else{
			dataVO.setSystemAlias(false);
		}
		
		if(document.getElementsByTagName("ns1:Owner")!=null&&document.getElementsByTagName("ns1:Owner").item(0)!=null
				&&document.getElementsByTagName("ns1:Owner").item(0).getFirstChild()!=null){
			dataVO.setOwner(document.getElementsByTagName("ns1:Owner").item(0).getFirstChild().getNodeValue());
		}else{
			dataVO.setOwner("");
		}

		if(document.getElementsByTagName("ns1:E164Alias")!=null&&document.getElementsByTagName("ns1:E164Alias").item(0)!=null
				&&document.getElementsByTagName("ns1:E164Alias").item(0).getFirstChild()!=null){
			dataVO.setE164Alias(document.getElementsByTagName("ns1:E164Alias").item(0).getFirstChild().getNodeValue());
		}else{
			dataVO.setE164Alias("");
		}
		
		if(document.getElementsByTagName("ns1:H323Alias")!=null&&document.getElementsByTagName("ns1:H323Alias").item(0)!=null
				&&document.getElementsByTagName("ns1:H323Alias").item(0).getFirstChild()!=null){
			dataVO.setH323Alias(document.getElementsByTagName("ns1:H323Alias").item(0).getFirstChild().getNodeValue());
		}else{
			dataVO.setH323Alias("");
		}
		
		if(document.getElementsByTagName("ns1:SipURI")!=null&&document.getElementsByTagName("ns1:SipURI").item(0)!=null
				&&document.getElementsByTagName("ns1:SipURI").item(0).getFirstChild()!=null){
			dataVO.setSipURI(document.getElementsByTagName("ns1:SipURI").item(0).getFirstChild().getNodeValue());
		}else{
			dataVO.setSipURI("");
		}
		
		if(document.getElementsByTagName("ns1:SipDisplayName")!=null&&document.getElementsByTagName("ns1:SipDisplayName").item(0)!=null
				&&document.getElementsByTagName("ns1:SipDisplayName").item(0).getFirstChild()!=null){
			dataVO.setSipDisplayName(document.getElementsByTagName("ns1:SipDisplayName").item(0).getFirstChild().getNodeValue());
		}else{
			dataVO.setSipDisplayName("");
		}
		
		if(document.getElementsByTagName("ns1:ShowCountdown")!=null&&document.getElementsByTagName("ns1:ShowCountdown").item(0)!=null
				&&document.getElementsByTagName("ns1:ShowCountdown").item(0).getFirstChild()!=null){
			dataVO.setShowCountdown(Boolean.valueOf(document.getElementsByTagName("ns1:ShowCountdown").item(0).getFirstChild().getNodeValue()));
		}else{
			dataVO.setShowCountdown(false);
		}
		
		if(document.getElementsByTagName("ns1:SendEmail")!=null&&document.getElementsByTagName("ns1:SendEmail").item(0)!=null
				&&document.getElementsByTagName("ns1:SendEmail").item(0).getFirstChild()!=null){
			dataVO.setSendEmail(Boolean.valueOf(document.getElementsByTagName("ns1:SendEmail").item(0).getFirstChild().getNodeValue()));
		}else{
			dataVO.setSendEmail(false);
		}
		
		if(document.getElementsByTagName("ns1:EmailAddress")!=null&&document.getElementsByTagName("ns1:EmailAddress").item(0)!=null
				&&document.getElementsByTagName("ns1:EmailAddress").item(0).getFirstChild()!=null){
			dataVO.setEmailAddress(document.getElementsByTagName("ns1:EmailAddress").item(0).getFirstChild().getNodeValue());
		}else{
			dataVO.setEmailAddress("");
		}
		
		if(document.getElementsByTagName("ns1:RecordingTitle")!=null&&document.getElementsByTagName("ns1:RecordingTitle").item(0)!=null
				&&document.getElementsByTagName("ns1:RecordingTitle").item(0).getFirstChild()!=null){
			dataVO.setRecordingTitle(document.getElementsByTagName("ns1:RecordingTitle").item(0).getFirstChild().getNodeValue());
		}else{
			dataVO.setRecordingTitle("");
		}
		
		if(document.getElementsByTagName("ns1:RecordingDescription")!=null&&document.getElementsByTagName("ns1:RecordingDescription").item(0)!=null
				&&document.getElementsByTagName("ns1:RecordingDescription").item(0).getFirstChild()!=null){
			dataVO.setRecordingDescription(document.getElementsByTagName("ns1:RecordingDescription").item(0).getFirstChild().getNodeValue());
		}else{
			dataVO.setRecordingDescription("");
		}
		
		if(document.getElementsByTagName("ns1:RecordingSpeaker")!=null&&document.getElementsByTagName("ns1:RecordingSpeaker").item(0)!=null
				&&document.getElementsByTagName("ns1:RecordingSpeaker").item(0).getFirstChild()!=null){
			dataVO.setRecordingSpeaker(document.getElementsByTagName("ns1:RecordingSpeaker").item(0).getFirstChild().getNodeValue());
		}else{
			dataVO.setRecordingSpeaker("");
		}
		
		if(document.getElementsByTagName("ns1:RecordingKeywords")!=null&&document.getElementsByTagName("ns1:RecordingKeywords").item(0)!=null
				&&document.getElementsByTagName("ns1:RecordingKeywords").item(0).getFirstChild()!=null){
			dataVO.setRecordingKeywords(document.getElementsByTagName("ns1:RecordingKeywords").item(0).getFirstChild().getNodeValue());
		}else{
			dataVO.setRecordingKeywords("");
		}
		
		if(document.getElementsByTagName("ns1:RecordingCopyright")!=null&&document.getElementsByTagName("ns1:RecordingCopyright").item(0)!=null
				&&document.getElementsByTagName("ns1:RecordingCopyright").item(0).getFirstChild()!=null){
			dataVO.setRecordingCopyright(document.getElementsByTagName("ns1:RecordingCopyright").item(0).getFirstChild().getNodeValue());
		}else{
			dataVO.setRecordingCopyright("");
		}
		
		if(document.getElementsByTagName("ns1:RecordingLocation")!=null&&document.getElementsByTagName("ns1:RecordingLocation").item(0)!=null
				&&document.getElementsByTagName("ns1:RecordingLocation").item(0).getFirstChild()!=null){
			dataVO.setRecordingLocation(document.getElementsByTagName("ns1:RecordingLocation").item(0).getFirstChild().getNodeValue());
		}else{
			dataVO.setRecordingLocation("");
		}
		
		if(document.getElementsByTagName("ns1:RecordingCategory")!=null&&document.getElementsByTagName("ns1:RecordingCategory").item(0)!=null
				&&document.getElementsByTagName("ns1:RecordingCategory").item(0).getFirstChild()!=null){
			dataVO.setRecordingCategory(document.getElementsByTagName("ns1:RecordingCategory").item(0).getFirstChild().getNodeValue());
		}else{
			dataVO.setRecordingCategory("");
		}
		
		if(document.getElementsByTagName("ns1:RecordingOwner")!=null&&document.getElementsByTagName("ns1:RecordingOwner").item(0)!=null
				&&document.getElementsByTagName("ns1:RecordingOwner").item(0).getFirstChild()!=null){
			dataVO.setRecordingOwner(document.getElementsByTagName("ns1:RecordingOwner").item(0).getFirstChild().getNodeValue());
		}else{
			dataVO.setRecordingOwner("");
		}
		
		if(document.getElementsByTagName("ns1:AutomaticallyPublishRecordings")!=null&&document.getElementsByTagName("ns1:AutomaticallyPublishRecordings").item(0)!=null
				&&document.getElementsByTagName("ns1:AutomaticallyPublishRecordings").item(0).getFirstChild()!=null){
			dataVO.setAutomaticallyPublishRecordings(Boolean.valueOf(document.getElementsByTagName("ns1:AutomaticallyPublishRecordings").item(0).getFirstChild().getNodeValue()));
		}else{
			dataVO.setAutomaticallyPublishRecordings(false);
		}
		
		if(document.getElementsByTagName("ns1:EnableEndpointPlaybackForRecordings")!=null&&document.getElementsByTagName("ns1:EnableEndpointPlaybackForRecordings").item(0)!=null
				&&document.getElementsByTagName("ns1:EnableEndpointPlaybackForRecordings").item(0).getFirstChild()!=null){
			dataVO.setEnableEndpointPlaybackForRecordings(Boolean.valueOf(document.getElementsByTagName("ns1:EnableEndpointPlaybackForRecordings").item(0).getFirstChild().getNodeValue()));
		}else{
			dataVO.setEnableEndpointPlaybackForRecordings(false);
		}
		
		if(document.getElementsByTagName("ns1:AllUsersAccess")!=null&&document.getElementsByTagName("ns1:AllUsersAccess").item(0)!=null
				&&document.getElementsByTagName("ns1:AllUsersAccess").item(0).getFirstChild()!=null){
			dataVO.setAllUsersAccess(Boolean.valueOf(document.getElementsByTagName("ns1:AllUsersAccess").item(0).getFirstChild().getNodeValue()));
		}else{
			dataVO.setAllUsersAccess(false);
		}
		
		Element element = (Element) document.getElementsByTagName("ns1:Users").item(0); 
		NodeList nodeList = element.getElementsByTagName("ns1:string");
		int count = nodeList.getLength();
		if(count>0){
			String [] users = new String [count];
			for(int i = 0 ; i < count; i++){
				 Node childNode = nodeList.item(i);
				 users[i] =childNode.getNodeValue();
			}
			dataVO.setUsers(users);
		}
		
		Element elementEditors = (Element) document.getElementsByTagName("ns1:Editors").item(0); 
		NodeList nodeListEditors = elementEditors.getElementsByTagName("ns1:string");
		int countEditors = nodeListEditors.getLength();
		if(countEditors>0){
			String [] editors = new String [countEditors];
			for(int i = 0 ; i < countEditors; i++){
				 Node childNode = nodeListEditors.item(i);
				 editors[i] =childNode.getNodeValue();
			}
			dataVO.setEditors(editors);
		}
		
		if(document.getElementsByTagName("ns1:Password")!=null&&document.getElementsByTagName("ns1:Password").item(0)!=null
				&&document.getElementsByTagName("ns1:Password").item(0).getFirstChild()!=null){
			dataVO.setPassword(document.getElementsByTagName("ns1:Password").item(0).getFirstChild().getNodeValue());
		}else{
			dataVO.setPassword("");
		}
		
		if(document.getElementsByTagName("ns1:RecordingPin")!=null&&document.getElementsByTagName("ns1:RecordingPin").item(0)!=null
				&&document.getElementsByTagName("ns1:RecordingPin").item(0).getFirstChild()!=null){
			dataVO.setRecordingPin(document.getElementsByTagName("ns1:RecordingPin").item(0).getFirstChild().getNodeValue());
		}else{
			dataVO.setRecordingPin("");
		}
		return dataVO;
		
	}
	
	
	public static ConferenceVO getConferenceVO(Element element){
		ConferenceVO conferenceVO = new ConferenceVO();
		if(element.getElementsByTagName("ns1:ConferenceID")!=null&&element.getElementsByTagName("ns1:ConferenceID").item(0)!=null
				&&element.getElementsByTagName("ns1:ConferenceID").item(0).getNodeValue()!=null){
			conferenceVO.setConferenceID(element.getElementsByTagName("ns1:ConferenceID").item(0).getNodeValue());
		}else{
			conferenceVO.setConferenceID("");
		}
		
		if(element.getElementsByTagName("ns1:Title")!=null&&element.getElementsByTagName("ns1:Title").item(0)!=null
				&&element.getElementsByTagName("ns1:Title").item(0).getNodeValue()!=null){
			conferenceVO.setTitle(element.getElementsByTagName("ns1:Title").item(0).getNodeValue());
		}else{
			conferenceVO.setTitle("");
		}
		
		if(element.getElementsByTagName("ns1:Description")!=null&&element.getElementsByTagName("ns1:Description").item(0)!=null
				&&element.getElementsByTagName("ns1:Description").item(0).getNodeValue()!=null){
			conferenceVO.setDescription(element.getElementsByTagName("ns1:Description").item(0).getNodeValue());
		}else{
			conferenceVO.setDescription("");
		}
		
		if(element.getElementsByTagName("ns1:URL")!=null&&element.getElementsByTagName("ns1:URL").item(0)!=null
				&&element.getElementsByTagName("ns1:URL").item(0).getNodeValue()!=null){
			conferenceVO.setURL(element.getElementsByTagName("ns1:URL").item(0).getNodeValue());
		}else{
			conferenceVO.setURL("");
		}
		
		if(element.getElementsByTagName("ns1:Speaker")!=null&&element.getElementsByTagName("ns1:Speaker").item(0)!=null
				&&element.getElementsByTagName("ns1:Speaker").item(0).getNodeValue()!=null){
			conferenceVO.setSpeaker(element.getElementsByTagName("ns1:Speaker").item(0).getNodeValue());
		}else{
			conferenceVO.setSpeaker("");
		}
		
		if(element.getElementsByTagName("ns1:Keywords")!=null&&element.getElementsByTagName("ns1:Keywords").item(0)!=null
				&&element.getElementsByTagName("ns1:Keywords").item(0).getNodeValue()!=null){
			conferenceVO.setKeywords(element.getElementsByTagName("ns1:Keywords").item(0).getNodeValue());
		}else{
			conferenceVO.setKeywords("");
		}
		
		if(element.getElementsByTagName("ns1:Copyright")!=null&&element.getElementsByTagName("ns1:Copyright").item(0)!=null
				&&element.getElementsByTagName("ns1:Copyright").item(0).getNodeValue()!=null){
			conferenceVO.setCopyright(element.getElementsByTagName("ns1:Copyright").item(0).getNodeValue());
		}else{
			conferenceVO.setCopyright("");
		}
		
		if(element.getElementsByTagName("ns1:Location")!=null&&element.getElementsByTagName("ns1:Location").item(0)!=null
				&&element.getElementsByTagName("ns1:Location").item(0).getNodeValue()!=null){
			conferenceVO.setLocation(element.getElementsByTagName("ns1:Location").item(0).getNodeValue());
		}else{
			conferenceVO.setLocation("");
		}
		
		//Movies ?
		
		if(element.getElementsByTagName("ns1:DateTime")!=null&&element.getElementsByTagName("ns1:DateTime").item(0)!=null
				&&element.getElementsByTagName("ns1:DateTime").item(0).getNodeValue()!=null){
			conferenceVO.setDateTime(Integer.valueOf(element.getElementsByTagName("ns1:DateTime").item(0).getNodeValue()));
		}else{
			conferenceVO.setDateTime(0);
		}
		
		if(element.getElementsByTagName("ns1:UpdateTime")!=null&&element.getElementsByTagName("ns1:UpdateTime").item(0)!=null
				&&element.getElementsByTagName("ns1:UpdateTime").item(0).getNodeValue()!=null){
			conferenceVO.setUpdateTime(Integer.valueOf(element.getElementsByTagName("ns1:UpdateTime").item(0).getNodeValue()));
		}else{
			conferenceVO.setUpdateTime(0);
		}
		
		if(element.getElementsByTagName("ns1:Duration")!=null&&element.getElementsByTagName("ns1:Duration").item(0)!=null
				&&element.getElementsByTagName("ns1:Duration").item(0).getNodeValue()!=null){
			conferenceVO.setDuration(Integer.valueOf(element.getElementsByTagName("ns1:Duration").item(0).getNodeValue()));
		}else{
			conferenceVO.setDuration(0);
		}
		
		if(element.getElementsByTagName("ns1:Owner")!=null&&element.getElementsByTagName("ns1:Owner").item(0)!=null
				&&element.getElementsByTagName("ns1:Owner").item(0).getNodeValue()!=null){
			conferenceVO.setLocation(element.getElementsByTagName("ns1:Owner").item(0).getNodeValue());
		}else{
			conferenceVO.setLocation("");
		}
		
		if(element.getElementsByTagName("ns1:OwnerDetails")!=null&&element.getElementsByTagName("ns1:OwnerDetails").item(0)!=null){
			Element elementOwnerDetails = (Element) element.getElementsByTagName("ns1:OwnerDetails").item(0);
			OwnerVO ownerDetails = new OwnerVO();
			if(elementOwnerDetails.getElementsByTagName("ns1:UserName")!=null&&elementOwnerDetails.getElementsByTagName("ns1:UserName").item(0)!=null
					&&elementOwnerDetails.getElementsByTagName("ns1:UserName").item(0).getNodeValue()!=null){
				ownerDetails.setUserName(elementOwnerDetails.getElementsByTagName("ns1:UserName").item(0).getNodeValue());
			}else{
				ownerDetails.setUserName("");
			}
			if(elementOwnerDetails.getElementsByTagName("ns1:DisplayName")!=null&&elementOwnerDetails.getElementsByTagName("ns1:DisplayName").item(0)!=null
					&&elementOwnerDetails.getElementsByTagName("ns1:DisplayName").item(0).getNodeValue()!=null){
				ownerDetails.setDisplayName(elementOwnerDetails.getElementsByTagName("ns1:DisplayName").item(0).getNodeValue());
			}else{
				ownerDetails.setDisplayName("");
			}
			conferenceVO.setOwnerDetails(ownerDetails);
		}
		
		if(element.getElementsByTagName("ns1:Deleted")!=null&&element.getElementsByTagName("ns1:Deleted").item(0)!=null
				&&element.getElementsByTagName("ns1:Deleted").item(0).getNodeValue()!=null){
			conferenceVO.setDeleted(Boolean.valueOf(element.getElementsByTagName("ns1:Deleted").item(0).getNodeValue()));
		}else{
			conferenceVO.setDeleted(false);
		}
		
		if(element.getElementsByTagName("ns1:Labels")!=null&&element.getElementsByTagName("ns1:Labels").item(0)!=null){
			Element elementLabels = (Element) element.getElementsByTagName("ns1:Labels").item(0); 
			if(elementLabels!=null&&elementLabels.getElementsByTagName("ns1:Label")!=null){
				NodeList nodeListLabel = elementLabels.getElementsByTagName("ns1:Label");
				int count = nodeListLabel.getLength();
				if(count>0){
					LabelVO[] labels = new LabelVO[count];
					LabelVO labelVO = null;
					for(int i = 0 ; i < count; i++){
						Element elementLabel = (Element)nodeListLabel.item(i);
						if(elementLabel!=null){
							labelVO = new LabelVO();
							if(elementLabel.getElementsByTagName("ns1:Name")!=null&&elementLabel.getElementsByTagName("ns1:Name").item(0)!=null
									&&elementLabel.getElementsByTagName("ns1:Name").item(0).getNodeValue()!=null){
								labelVO.setName(elementLabel.getElementsByTagName("ns1:Name").item(0).getNodeValue());
							}else{
								labelVO.setName("");
							}
							if(elementLabel.getElementsByTagName("ns1:Description")!=null&&elementLabel.getElementsByTagName("ns1:Description").item(0)!=null
									&&elementLabel.getElementsByTagName("ns1:Description").item(0).getNodeValue()!=null){
								labelVO.setDescription(elementLabel.getElementsByTagName("ns1:Description").item(0).getNodeValue());
							}else{
								labelVO.setDescription("");
							}
							labels[i] = labelVO;
						}
					}
					conferenceVO.setLabels(labels);
				}
			}
		}
		
		if(element.getElementsByTagName("ns1:GuestAccess")!=null&&element.getElementsByTagName("ns1:GuestAccess").item(0)!=null
				&&element.getElementsByTagName("ns1:GuestAccess").item(0).getNodeValue()!=null){
			conferenceVO.setGuestAccess(Boolean.valueOf(element.getElementsByTagName("ns1:GuestAccess").item(0).getNodeValue()));
		}else{
			conferenceVO.setGuestAccess(false);
		}
		
		if(element.getElementsByTagName("ns1:HasWatchableMovie")!=null&&element.getElementsByTagName("ns1:HasWatchableMovie").item(0)!=null
				&&element.getElementsByTagName("ns1:HasWatchableMovie").item(0).getNodeValue()!=null){
			conferenceVO.setHasWatchableMovie(Boolean.valueOf(element.getElementsByTagName("ns1:HasWatchableMovie").item(0).getNodeValue()));
		}else{
			conferenceVO.setHasWatchableMovie(false);
		}
		
		Element elementWatchableMovies = (Element)element.getElementsByTagName("ns1:WatchableMovies").item(0);
		if(elementWatchableMovies!=null){
			NodeList elementArrayOfWatchableMovies = elementWatchableMovies.getElementsByTagName("ns1:ArrayOfWatchableMovie");
			if(elementArrayOfWatchableMovies!=null&&elementArrayOfWatchableMovies.getLength()>0){
				int count = elementArrayOfWatchableMovies.getLength();
				WatchableMovieVO [][] watchableMovieVOs=new WatchableMovieVO[count][];
				WatchableMovieVO watchableMovieVO = null;
				WatchableMovieVO [] watchableMovieVOins= null;
				for(int i=0;i<count;i++){
					Element elementArrayOfWatchableMovie = (Element)elementArrayOfWatchableMovies.item(i);
					NodeList elementWatchableMovieNodeList = elementArrayOfWatchableMovie.getElementsByTagName("ns1:WatchableMovie");
					if(elementWatchableMovieNodeList!=null&&elementWatchableMovieNodeList.getLength()>0){
						int countNodeList = elementWatchableMovieNodeList.getLength();
						watchableMovieVOins=new WatchableMovieVO[countNodeList];
						for(int j=0;j<countNodeList;j++){
							Element elementWatchableMovie = (Element)elementWatchableMovieNodeList.item(j);
							if(elementWatchableMovie!=null){
								watchableMovieVO = new WatchableMovieVO();
								
								if(elementWatchableMovie.getElementsByTagName("ns1:ClipStart")!=null&&elementWatchableMovie.getElementsByTagName("ns1:ClipStart").item(0)!=null
										&&elementWatchableMovie.getElementsByTagName("ns1:ClipStart").item(0).getNodeValue()!=null){
									watchableMovieVO.setClipStart(Integer.valueOf(elementWatchableMovie.getElementsByTagName("ns1:ClipStart").item(0).getNodeValue()));
								}else{
									watchableMovieVO.setClipStart(0);
								}
								
								if(elementWatchableMovie.getElementsByTagName("ns1:ClipEnd")!=null&&elementWatchableMovie.getElementsByTagName("ns1:ClipEnd").item(0)!=null
										&&elementWatchableMovie.getElementsByTagName("ns1:ClipEnd").item(0).getNodeValue()!=null){
									watchableMovieVO.setClipEnd(Integer.valueOf(elementWatchableMovie.getElementsByTagName("ns1:ClipEnd").item(0).getNodeValue()));
								}else{
									watchableMovieVO.setClipEnd(0);
								}
								
								if(elementWatchableMovie.getElementsByTagName("ns1:Format")!=null&&elementWatchableMovie.getElementsByTagName("ns1:Format").item(0)!=null
										&&elementWatchableMovie.getElementsByTagName("ns1:Format").item(0).getNodeValue()!=null){
									watchableMovieVO.setFormat(elementWatchableMovie.getElementsByTagName("ns1:Format").item(0).getNodeValue());
								}else{
									watchableMovieVO.setFormat("");
								}
								
								if(elementWatchableMovie.getElementsByTagName("ns1:Quality")!=null&&elementWatchableMovie.getElementsByTagName("ns1:Quality").item(0)!=null
										&&elementWatchableMovie.getElementsByTagName("ns1:Quality").item(0).getNodeValue()!=null){
									watchableMovieVO.setQuality(elementWatchableMovie.getElementsByTagName("ns1:Quality").item(0).getNodeValue());
								}else{
									watchableMovieVO.setQuality("");
								}
								
								if(elementWatchableMovie.getElementsByTagName("ns1:OfflineTranscoded")!=null&&elementWatchableMovie.getElementsByTagName("ns1:OfflineTranscoded").item(0)!=null
										&&elementWatchableMovie.getElementsByTagName("ns1:OfflineTranscoded").item(0).getNodeValue()!=null){
									watchableMovieVO.setOfflineTranscoded(Boolean.valueOf(elementWatchableMovie.getElementsByTagName("ns1:OfflineTranscoded").item(0).getNodeValue()));
								}else{
									watchableMovieVO.setOfflineTranscoded(false);
								}
								
								if(elementWatchableMovie.getElementsByTagName("ns1:TotalBandwidth")!=null&&elementWatchableMovie.getElementsByTagName("ns1:TotalBandwidth").item(0)!=null
										&&elementWatchableMovie.getElementsByTagName("ns1:TotalBandwidth").item(0).getNodeValue()!=null){
									watchableMovieVO.setTotalBandwidth(Integer.valueOf(elementWatchableMovie.getElementsByTagName("ns1:TotalBandwidth").item(0).getNodeValue()));
								}else{
									watchableMovieVO.setTotalBandwidth(0);
								}
								
								if(elementWatchableMovie.getElementsByTagName("ns1:MainURL")!=null&&elementWatchableMovie.getElementsByTagName("ns1:MainURL").item(0)!=null
										&&elementWatchableMovie.getElementsByTagName("ns1:MainURL").item(0).getNodeValue()!=null){
									watchableMovieVO.setMainURL(elementWatchableMovie.getElementsByTagName("ns1:MainURL").item(0).getNodeValue());
								}else{
									watchableMovieVO.setMainURL("");
								}
								
								if(elementWatchableMovie.getElementsByTagName("ns1:MainWidth")!=null&&elementWatchableMovie.getElementsByTagName("ns1:MainWidth").item(0)!=null
										&&elementWatchableMovie.getElementsByTagName("ns1:MainWidth").item(0).getNodeValue()!=null){
									watchableMovieVO.setMainWidth(Integer.valueOf(elementWatchableMovie.getElementsByTagName("ns1:MainWidth").item(0).getNodeValue()));
								}else{
									watchableMovieVO.setMainWidth(0);
								}
								
								if(elementWatchableMovie.getElementsByTagName("ns1:MainHeight")!=null&&elementWatchableMovie.getElementsByTagName("ns1:MainHeight").item(0)!=null
										&&elementWatchableMovie.getElementsByTagName("ns1:MainHeight").item(0).getNodeValue()!=null){
									watchableMovieVO.setMainHeight(Integer.valueOf(elementWatchableMovie.getElementsByTagName("ns1:MainHeight").item(0).getNodeValue()));
								}else{
									watchableMovieVO.setMainHeight(0);
								}
								
								if(elementWatchableMovie.getElementsByTagName("ns1:DualURL")!=null&&elementWatchableMovie.getElementsByTagName("ns1:DualURL").item(0)!=null
										&&elementWatchableMovie.getElementsByTagName("ns1:DualURL").item(0).getNodeValue()!=null){
									watchableMovieVO.setDualURL(elementWatchableMovie.getElementsByTagName("ns1:DualURL").item(0).getNodeValue());
								}else{
									watchableMovieVO.setDualURL("");
								}
								
								if(elementWatchableMovie.getElementsByTagName("ns1:DualWidth")!=null&&elementWatchableMovie.getElementsByTagName("ns1:DualWidth").item(0)!=null
										&&elementWatchableMovie.getElementsByTagName("ns1:DualWidth").item(0).getNodeValue()!=null){
									watchableMovieVO.setDualWidth(Integer.valueOf(elementWatchableMovie.getElementsByTagName("ns1:DualWidth").item(0).getNodeValue()));
								}else{
									watchableMovieVO.setDualWidth(0);
								}
								
								if(elementWatchableMovie.getElementsByTagName("ns1:DualHeight")!=null&&elementWatchableMovie.getElementsByTagName("ns1:DualHeight").item(0)!=null
										&&elementWatchableMovie.getElementsByTagName("ns1:DualHeight").item(0).getNodeValue()!=null){
									watchableMovieVO.setDualHeight(Integer.valueOf(elementWatchableMovie.getElementsByTagName("ns1:DualHeight").item(0).getNodeValue()));
								}else{
									watchableMovieVO.setDualHeight(0);
								}
							}
							watchableMovieVOins[j] = watchableMovieVO;
						}
						watchableMovieVOs[i] = watchableMovieVOins;
					}
				}
				conferenceVO.setWatchableMovies(watchableMovieVOs);
			}
		}
		
		if(element.getElementsByTagName("ns1:HasDownloadableMovie")!=null&&element.getElementsByTagName("ns1:HasDownloadableMovie").item(0)!=null
				&&element.getElementsByTagName("ns1:HasDownloadableMovie").item(0).getNodeValue()!=null){
			conferenceVO.setHasDownloadableMovie(Boolean.valueOf(element.getElementsByTagName("ns1:HasDownloadableMovie").item(0).getNodeValue()));
		}else{
			conferenceVO.setHasDownloadableMovie(false);
		}
		
		if(element.getElementsByTagName("ns1:DownloadableMovies")!=null&&element.getElementsByTagName("ns1:DownloadableMovies").item(0)!=null){
			Element elementDownloadableMovies = (Element) element.getElementsByTagName("ns1:DownloadableMovies").item(0); 
			if(elementDownloadableMovies!=null&&elementDownloadableMovies.getElementsByTagName("ns1:DownloadableMovie")!=null){
				NodeList nodeListDownloadableMovie = elementDownloadableMovies.getElementsByTagName("ns1:DownloadableMovie");
				int count = nodeListDownloadableMovie.getLength();
				if(count>0){
					DownloadableMovieVO[] downloadableMovieVOs = new DownloadableMovieVO[count];
					DownloadableMovieVO downloadableMovieVO = null;
					for(int i = 0 ; i < count; i++){
						Element elementDownloadableMovie = (Element)nodeListDownloadableMovie.item(i);
						if(elementDownloadableMovie!=null){
							downloadableMovieVO = new DownloadableMovieVO();
							if(elementDownloadableMovie.getElementsByTagName("ns1:Display")!=null&&elementDownloadableMovie.getElementsByTagName("ns1:Display").item(0)!=null
									&&elementDownloadableMovie.getElementsByTagName("ns1:Display").item(0).getNodeValue()!=null){
								downloadableMovieVO.setDisplay(elementDownloadableMovie.getElementsByTagName("ns1:Display").item(0).getNodeValue());
							}else{
								downloadableMovieVO.setDisplay("");
							}
							if(elementDownloadableMovie.getElementsByTagName("ns1:URL")!=null&&elementDownloadableMovie.getElementsByTagName("ns1:URL").item(0)!=null
									&&elementDownloadableMovie.getElementsByTagName("ns1:URL").item(0).getNodeValue()!=null){
								downloadableMovieVO.setURL(elementDownloadableMovie.getElementsByTagName("ns1:URL").item(0).getNodeValue());
							}else{
								downloadableMovieVO.setURL("");
							}
							
							if(elementDownloadableMovie.getElementsByTagName("ns1:URL")!=null&&elementDownloadableMovie.getElementsByTagName("ns1:URL").item(0)!=null
									&&elementDownloadableMovie.getElementsByTagName("ns1:URL").item(0).getNodeValue()!=null){
								downloadableMovieVO.setWidth(Integer.valueOf(elementDownloadableMovie.getElementsByTagName("ns1:URL").item(0).getNodeValue()));
							}else{
								downloadableMovieVO.setWidth(0);
							}
							
							if(elementDownloadableMovie.getElementsByTagName("ns1:Height")!=null&&elementDownloadableMovie.getElementsByTagName("ns1:Height").item(0)!=null
									&&elementDownloadableMovie.getElementsByTagName("ns1:Height").item(0).getNodeValue()!=null){
								downloadableMovieVO.setHeight(Integer.valueOf(elementDownloadableMovie.getElementsByTagName("ns1:Height").item(0).getNodeValue()));
							}else{
								downloadableMovieVO.setHeight(0);
							}
							
							downloadableMovieVOs[i] = downloadableMovieVO;
						}
					}
					
					conferenceVO.setDownloadableMovies(downloadableMovieVOs);
				}
			}
		}
		
		if(element.getElementsByTagName("ns1:HasPending")!=null&&element.getElementsByTagName("ns1:HasPending").item(0)!=null
				&&element.getElementsByTagName("ns1:HasPending").item(0).getNodeValue()!=null){
			conferenceVO.setHasPending(Boolean.valueOf(element.getElementsByTagName("ns1:HasPending").item(0).getNodeValue()));
		}else{
			conferenceVO.setHasPending(false);
		}
		
		if(element.getElementsByTagName("ns1:PercentTranscoded")!=null&&element.getElementsByTagName("ns1:PercentTranscoded").item(0)!=null
				&&element.getElementsByTagName("ns1:PercentTranscoded").item(0).getNodeValue()!=null){
			conferenceVO.setPercentTranscoded(Integer.valueOf(element.getElementsByTagName("ns1:PercentTranscoded").item(0).getNodeValue()));
		}else{
			conferenceVO.setPercentTranscoded(0);
		}
		
		return conferenceVO;
	}
	
	public static String [] getConferenceThumbnailsResult(Element element){
		NodeList nodeList = element.getElementsByTagName("ns1:string");
		int count = nodeList.getLength();
		if(count>0){
			String [] strArray = new String [count];
			for(int i = 0 ; i < count; i++){
				 Node childNode = nodeList.item(i);
				 strArray[i] =childNode.getNodeValue();
			}
			return strArray;
		}
		return null;
	}
	
	/**
	 * 根据需要解析XML的方法名取得其XML中的TagName集合
	 * @param methodName 需要解析XML的方法名
	 * @return
	 */
	public static List<String> getTagNameList(String methodName){
		List<String> tagNameList = null;
		if(methodName!=null&&!"".equals(methodName)){
			tagNameList = new ArrayList<String>();
		}
		if(XMLReader.prepareConference.equals(methodName)){
			tagNameList.add("ns1:ConferenceID");
			tagNameList.add("ns1:Error");
			tagNameList.add("ns1:ErrorCode");
			tagNameList.add("ns1:E164Alias");
			tagNameList.add("ns1:H323Id");
			tagNameList.add("ns1:SIPURI");
			
		}else if(XMLReader.requestConferenceID.equals(methodName)){
			tagNameList.add("ns1:RequestConferenceIDResult");
			
		}else if(XMLReader.dial.equals(methodName)){
			tagNameList.add("ns1:CallRef");
			tagNameList.add("ns1:LogTag");
			tagNameList.add("ns1:ConferenceID");
			tagNameList.add("ns1:Error");
			tagNameList.add("ns1:ErrorCode");
			
		}else if(XMLReader.clusterDial.equals(methodName)){
			tagNameList.add("ns1:Recorder");
			tagNameList.add("ns1:CallRef");
			tagNameList.add("ns1:LogTag");
			tagNameList.add("ns1:ConferenceID");
			tagNameList.add("ns1:Error");
			tagNameList.add("ns1:ErrorCode");
		}else if(XMLReader.disconnectCall.equals(methodName)){
			tagNameList.add("ns1:Error");
			tagNameList.add("ns1:ErrorCode");
		}else if(XMLReader.disconnectAllCalls.equals(methodName)){
			tagNameList.add("ns1:DisconnectAllCallsResult");
		}else if(XMLReader.getCallInfo.equals(methodName)){
			tagNameList.add("ns1:CallState");
			tagNameList.add("ns1:RemoteEndpoint");
			tagNameList.add("ns1:CallRate");
			tagNameList.add("ns1:RecordingAliasName");
			tagNameList.add("ns1:MediaState");
			tagNameList.add("ns1:WriterStatus");
			tagNameList.add("ns1:AudioCodec");
			tagNameList.add("ns1:VideoCodec");
			
		}else if(XMLReader.getSystemHealth.equals(methodName)){
			tagNameList.add("ns1:EngineOK");
			tagNameList.add("ns1:LibraryOK");
			tagNameList.add("ns1:DatabaseOK");
			
		}else if(XMLReader.getCallCapacity.equals(methodName)){
			tagNameList.add("ns1:TranscodingCalls");
			tagNameList.add("ns1:MaxTranscodingCalls");
			tagNameList.add("ns1:ArchivingCalls");
			tagNameList.add("ns1:MaxArchivingCalls");
			tagNameList.add("ns1:MaxCalls");
			tagNameList.add("ns1:CurrentCalls");
			tagNameList.add("ns1:MaxLiveCalls");
			tagNameList.add("ns1:CurrentLiveCalls");
			tagNameList.add("ns1:CurrentPlaybackCalls");
			
		}else if(XMLReader.getSystemInformation.equals(methodName)){
			tagNameList.add("ns1:ProductID");
			tagNameList.add("ns1:VersionMajor");
			tagNameList.add("ns1:VersionMinor");
			tagNameList.add("ns1:ReleaseType");
			tagNameList.add("ns1:ReleaseNumber");
			tagNameList.add("ns1:BuildNumber");
			tagNameList.add("ns1:IPAddress");
			tagNameList.add("ns1:SerialNumber");
			tagNameList.add("ns1:TranscodingOptions");
			tagNameList.add("ns1:ArchivingOptions");
			tagNameList.add("ns1:MaxCallOptions");
			tagNameList.add("ns1:MaxLiveCallOptions");
			tagNameList.add("ns1:EngineOK");
			tagNameList.add("ns1:LicenseValid");
			tagNameList.add("ns1:RevisionNumber");
			
		}else if(XMLReader.bindAliasConferenceID.equals(methodName)){
			tagNameList.add("ns1:BindAliasConferenceIDResult");

		}else if(XMLReader.unBindAliasConferenceID.equals(methodName)){
			tagNameList.add("ns1:Error");
			tagNameList.add("ns1:ErrorCode");
		}else if(XMLReader.getRecordingAlias.equals(methodName)){
			tagNameList.add("DataVO");
//			tagNameList.add("ns1:AliasID");
//			tagNameList.add("ns1:Name");
//			tagNameList.add("ns1:SystemAlias");
//			tagNameList.add("ns1:Owner");
//			tagNameList.add("ns1:E164Alias");
//			tagNameList.add("ns1:H323Alias");
//			tagNameList.add("ns1:SipURI");
//			tagNameList.add("ns1:SipDisplayName");
//			tagNameList.add("ns1:ShowCountdown");
//			tagNameList.add("ns1:SendEmail");
//			tagNameList.add("ns1:EmailAddress");
//			tagNameList.add("ns1:RecordingTitle");
//			tagNameList.add("ns1:RecordingDescription");
//			tagNameList.add("ns1:RecordingSpeaker");
//			tagNameList.add("ns1:RecordingKeywords");
//			tagNameList.add("ns1:RecordingCopyright");
//			tagNameList.add("ns1:RecordingLocation");
//			tagNameList.add("ns1:RecordingCategory");
//			tagNameList.add("ns1:RecordingOwner");
//			tagNameList.add("ns1:AutomaticallyPublishRecordings");
//			tagNameList.add("ns1:EnableEndpointPlaybackForRecordings");
//			tagNameList.add("ns1:AllUsersAccess");
//			tagNameList.add("ns1:Password");
//			tagNameList.add("ns1:RecordingPin");
			
		}else if(XMLReader.addRecordingAlias.equals(methodName)){
			tagNameList.add("ns1:AddRecordingAliasResult");
			
		}else if(XMLReader.restartService.equals(methodName)){
			tagNameList.add("ns1:RestartServiceResult");

		}else if(XMLReader.modifyConference.equals(methodName)){
			tagNameList.add("ns1:ModifyConferenceResponse");

		}else if(XMLReader.getConference.equals(methodName)){
			tagNameList.add("ConferenceVO");
//			tagNameList.add("ns1:ConferenceID");
//			tagNameList.add("ns1:Title");
//			tagNameList.add("ns1:Description");
//			tagNameList.add("ns1:URL");
//			tagNameList.add("ns1:Speaker");
//			tagNameList.add("ns1:Keywords");
//			tagNameList.add("ns1:Copyright");
//			tagNameList.add("ns1:Location");
//			tagNameList.add("ns1:Movies");
//			tagNameList.add("ns1:DateTime");
//			tagNameList.add("ns1:UpdateTime");
//			tagNameList.add("ns1:Duration");
//			tagNameList.add("ns1:Owner");
//			tagNameList.add("ns1:UserName");
//			tagNameList.add("ns1:DisplayName");
//			tagNameList.add("ns1:Deleted");
//			tagNameList.add("ns1:Name");
//			tagNameList.add("ns1:Description");
//			tagNameList.add("ns1:GuestAccess");
//			tagNameList.add("ns1:HasWatchableMovie");
//			tagNameList.add("ns1:ClipStart");
//			tagNameList.add("ns1:ClipEnd");
//			tagNameList.add("ns1:Format");
//			tagNameList.add("ns1:Quality");
//			tagNameList.add("ns1:OfflineTranscoded");
//			tagNameList.add("ns1:TotalBandwidth");
//			tagNameList.add("ns1:MainURL");
//			tagNameList.add("ns1:MainWidth");
//			tagNameList.add("ns1:MainHeight");
//			tagNameList.add("ns1:DualURL");
//			tagNameList.add("ns1:DualWidth");
//			tagNameList.add("ns1:DualHeight");
//			tagNameList.add("ns1:HasDownloadableMovie");
//			tagNameList.add("ns1:Display");
//			tagNameList.add("ns1:URL");
//			tagNameList.add("ns1:Width");
//			tagNameList.add("ns1:Height");
//			tagNameList.add("ns1:HasPending");
//			tagNameList.add("ns1:PercentTranscoded");
//			tagNameList.add("ns1:Password");
//			tagNameList.add("ns1:PlaybackEnabled");
//			tagNameList.add("ns1:PlaybackH323Id");
//			tagNameList.add("ns1:PlaybackE164Alias");
			
		}else if(XMLReader.getConferences.equals(methodName)){
			tagNameList.add("ConferenceVOs");
//			tagNameList.add("ns1:ConferenceID");
//			tagNameList.add("ns1:Title");
//			tagNameList.add("ns1:Description");
//			tagNameList.add("ns1:URL");
//			tagNameList.add("ns1:Speaker");
//			tagNameList.add("ns1:Keywords");
//			tagNameList.add("ns1:Copyright");
//			tagNameList.add("ns1:Location");
//			tagNameList.add("ns1:Movies");
//			tagNameList.add("ns1:DateTime");
//			tagNameList.add("ns1:UpdateTime");
//			tagNameList.add("ns1:Duration");
//			tagNameList.add("ns1:Owner");
//			tagNameList.add("ns1:UserName");
//			tagNameList.add("ns1:DisplayName");
//			tagNameList.add("ns1:Deleted");
//			tagNameList.add("ns1:Name");
//			tagNameList.add("ns1:Description");
//			tagNameList.add("ns1:GuestAccess");
//			tagNameList.add("ns1:HasWatchableMovie");
//			tagNameList.add("ns1:ClipStart");
//			tagNameList.add("ns1:ClipEnd");
//			tagNameList.add("ns1:Format");
//			tagNameList.add("ns1:Quality");
//			tagNameList.add("ns1:OfflineTranscoded");
//			tagNameList.add("ns1:TotalBandwidth");
//			tagNameList.add("ns1:MainURL");
//			tagNameList.add("ns1:MainWidth");
//			tagNameList.add("ns1:MainHeight");
//			tagNameList.add("ns1:DualURL");
//			tagNameList.add("ns1:DualWidth");
//			tagNameList.add("ns1:DualHeight");
//			tagNameList.add("ns1:HasDownloadableMovie");
//			tagNameList.add("ns1:Display");
//			tagNameList.add("ns1:URL");
//			tagNameList.add("ns1:Width");
//			tagNameList.add("ns1:Height");
//			tagNameList.add("ns1:HasPending");
//			tagNameList.add("ns1:PercentTranscoded");
//			tagNameList.add("ns1:Password");
//			tagNameList.add("ns1:PlaybackEnabled");
//			tagNameList.add("ns1:PlaybackH323Id");
//			tagNameList.add("ns1:PlaybackE164Alias");
			
		}else if(XMLReader.getConferenceCount.equals(methodName)){
			tagNameList.add("ns1:GetConferenceCountResult");

		}else if(XMLReader.getConferenceThumbnails.equals(methodName)){
			tagNameList.add("GetConferenceThumbnails");

		}
		
		return tagNameList;
		
	}

}
