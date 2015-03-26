package com.wafersystems.tcs;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wafersystems.tcs.Exception.ErrorExceptionParameter;
import com.wafersystems.tcs.vo.DataVO;

public class SocketCall {
	
	private Logger logger = LoggerFactory.getLogger(SocketCall.class);
	private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public SocketCall() {
		super();
	}
	
	/** HttpRequest发起请求,返回 HttpResponse响应 */
	private String getSoapResponse(SoapClient soapClient,String soapRequest, String soapAction) throws Exception{
		String soapResponse = soapClient.getSoapResponse(soapRequest, soapAction.toString());
		logger.info("SocketCall.getSoapResponse soapResponse : "+soapResponse);
		if(!StrUtil.isEmptyStr(soapResponse)){
			if(soapResponse.contains("401 Unauthorized")){
				boolean flag = true;
				int count = 1;
				while(flag){
					SoapClient.authorizationString = soapClient.getAuthorization(soapResponse);
					soapResponse = soapClient.getSoapResponse(soapRequest, soapAction.toString());
					if(soapResponse.contains("200 OK")){
						break;
					}
					if(count == 3){
						break;
					}
					count++;
				}
			}else if(soapResponse.contains("500 Internal Service Error")){
				soapResponse =  soapResponse.substring(soapResponse.indexOf("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"), soapResponse.length());
				String faultfaultcode = XMLReader.readerXMLByErrorMessage(soapResponse);
				logger.error("Error SocketCall.getSoapResponse soapResponse : Error parameter"+faultfaultcode);
				throw new ErrorExceptionParameter("Error parameter "+faultfaultcode);
			}
			soapResponse =  soapResponse.substring(soapResponse.indexOf("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"), soapResponse.length());
		}
		return soapResponse;
	}

	/**
	 * 储备一个会议ID和执行BindAliasConferenceID
	 * @param alias The GUID, E.164, H.323 alias, or RecordingAliasId of the recording alias - string GUID,e .,h .的别名,或RecordingAliasId的记录别名
	 * @param bookingAddress The alias that the incoming call will use - string 别名,来电时使用
	 * @param groupID 空字符串良好的GUID。The GUID of the conference’s group, if it is recurring - string
	 * @param isRecurring Indicates whether the conference is recurring - bool 会议是否反复出现
	 * @param title The title that will appear in the Content Library - string
	 * @param owner Username of the owner - string
	 * @param password 密码字段仅限于20个字符。 Password for the conference - string
	 * @param startDateTime Start date of the recording using GNU date formats - string
	 * @param duration Duration of call in seconds - integer
	 * @return
	 * @throws Exception 
	 */
	//<SOAP-ENV:Envelope xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/" xmlns:ns1="http://www.tandberg.net/XML/Streaming/1.0"><SOAP-ENV:Body><ns1:PrepareConferenceResponse><ns1:PrepareConferenceResult><ns1:ConferenceID>876557AE-9A0B-4D3E-AD9E-425507B398A1</ns1:ConferenceID><ns1:Error></ns1:Error><ns1:ErrorCode>0</ns1:ErrorCode><ns1:E164Alias></ns1:E164Alias><ns1:H323Id>wafer2222@wafersystems.com</ns1:H323Id><ns1:SIPURI></ns1:SIPURI></ns1:PrepareConferenceResult></ns1:PrepareConferenceResponse></SOAP-ENV:Body></SOAP-ENV:Envelope>
	public Map<String, Object> prepareConference(String alias,String bookingAddress,String groupID,boolean isRecurring,
			String title,String owner,String password,Date startDateTime,int duration) throws Exception{
		//XML中的name和Value值
		Map<String, Object> readerXMLMap = null;
		try{
			
			if(password!=null&&!"".equals(password)&&password.length()>20){
				logger.error("Error SocketCall.prepareConference,password : "+password+" length is more than 20 ");
				throw new ErrorExceptionParameter("Error SocketCall.prepareConference,password : "+password+" length is more than 20 ");
			}
			SoapClient soapClient = new SoapClient();
			StringBuffer soapAction = new StringBuffer();
			Map<String, Object> param = new HashMap<String, Object>();
			soapAction.append("PrepareConference");
			param.put("Alias", alias==null?"":alias);
			param.put("BookingAddress", bookingAddress==null?"":bookingAddress);
			param.put("GroupID", groupID==null?"":groupID);
			param.put("IsRecurring", isRecurring);
			param.put("Title", title==null?"":title);
			param.put("Owner", owner==null?"":owner);
			param.put("Password", password==null?"":password);
			param.put("StartDateTime", startDateTime==null?"":dateFormat.format(startDateTime));
			param.put("Duration", duration);
			String soapRequest = soapClient.getRequestStrByMethodName(soapAction.toString(), param);
			logger.info("SocketCall.prepareConference soapRequest : "+soapRequest);
			String soapResponse = getSoapResponse(soapClient, soapRequest, soapAction.toString());
			logger.info("SocketCall.prepareConference soapResponse : "+soapResponse);
			readerXMLMap = XMLReader.readerXMLByTagName(soapResponse, "prepareConference");
		}catch(Exception e){
			logger.error("Error SocketCall.prepareConference",e);
			throw e;
		}
		return readerXMLMap;
	}
	
	/**
	 * 这个请求储备会议ID用于稍后的拨号命令。它返回会议ID需要打电话和填充数据库的信息提供的。所有的参数必须在场。
	 * @param owner Username of the owner - string
	 * @param password Password for the conference - string Set password as an empty string for no conference password, this field is limited to 20 
	 * @param startDateTime Start date of the recording using GNU date formats - string 设置为0意味着调用将立即开始。
	 * @param duration Duration of call in seconds - integer Setting a 0 duration will make the length of the call unlimited. 
	 * @param title The title that will appear in the Content Library - string
	 * @param groupId The GUID of the conference’s group, if it is recurring - string
	 * @param isRecurring Indicates whether the conference is recurring - bool
	 * @return
	 */
	//<SOAP-ENV:Envelope xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/" xmlns:ns1="http://www.tandberg.net/XML/Streaming/1.0"><SOAP-ENV:Body><ns1:RequestConferenceIDResponse><ns1:RequestConferenceIDResult>8E5BB235-3344-451F-B540-FCBF166D3167</ns1:RequestConferenceIDResult></ns1:RequestConferenceIDResponse></SOAP-ENV:Body></SOAP-ENV:Envelope>
	public Map<String, Object> requestConferenceID(String owner,String password,Date startDateTime,Integer duration,
			String title,String groupId,boolean isRecurring)throws Exception{
		//XML中的name和Value值
		Map<String, Object> readerXMLMap = null;
		try{
			SoapClient soapClient = new SoapClient();
			StringBuffer soapAction = new StringBuffer();
			Map<String, Object> param = new HashMap<String, Object>();
			soapAction.append("RequestConferenceID");
			param.put("owner", owner==null?"":owner);
			param.put("password", password==null?"":password);
			param.put("startDateTime", startDateTime==null?"":dateFormat.format(startDateTime));
			param.put("duration", duration==null?"":duration);
			param.put("title", title==null?"":title);
			param.put("groupId", groupId==null?"":groupId);
			param.put("isRecurring", isRecurring);
			
			String soapRequest = soapClient.getRequestStrByMethodName(soapAction.toString(), param);
			logger.info("SocketCall.requestConferenceID soapRequest : "+soapRequest);
			String soapResponse = getSoapResponse(soapClient, soapRequest, soapAction.toString());
			logger.info("SocketCall.requestConferenceID soapResponse : "+soapResponse);
			readerXMLMap = XMLReader.readerXMLByTagName(soapResponse, "requestConferenceID");
		
		}catch(Exception e){
			logger.error("Error SocketCall.requestConferenceID",e);
			throw e;
		}
		return readerXMLMap;
	}
	
	/**
	 * 
	 * @param number The number to dial - string
	 * @param bitrate The desired bandwidth - string
	 * @param conferenceID ConferenceID to be used for this call - string
	 * @param alias Alias to use – specifies call settings - string
	 * @param callType Protocol for the call – optional (“sip”, “h323”) – string
	 * @param SetMetadata Inherit conference metadata from the alias – boolean
	 * @param PIN MCU conference PIN if required – string
	 * @return
	 */
	//<SOAP-ENV:Envelope xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/" xmlns:ns1="http://www.tandberg.net/XML/Streaming/1.0"><SOAP-ENV:Body><ns1:DialResponse><ns1:DialResult><ns1:CallRef>1</ns1:CallRef><ns1:LogTag>59</ns1:LogTag><ns1:ConferenceID>876557AE-9A0B-4D3E-AD9E-425507B398A1</ns1:ConferenceID><ns1:Error></ns1:Error><ns1:ErrorCode>0</ns1:ErrorCode></ns1:DialResult></ns1:DialResponse></SOAP-ENV:Body></SOAP-ENV:Envelope>
	public Map<String, Object> dial(String number,String bitrate,String conferenceID,String alias,String callType,boolean setMetadata,String PIN)throws Exception{
		//XML中的name和Value值
		Map<String, Object> readerXMLMap = null;
		try{
			
			SoapClient soapClient = new SoapClient();
			StringBuffer soapAction = new StringBuffer();
			Map<String, Object> param = new HashMap<String, Object>();
			soapAction.append("Dial");
			param.put("Number", number==null?"":number);
			param.put("Bitrate", bitrate==null?"":bitrate);
			param.put("ConferenceID", conferenceID==null?"":conferenceID);
			param.put("Alias", alias==null?"":alias);
			param.put("CallType",callType==null?"":callType);
			param.put("SetMetadata",setMetadata);
			param.put("PIN", PIN==null?"":PIN);
			
			String soapRequest = soapClient.getRequestStrByMethodName(soapAction.toString(), param);
			logger.info("SocketCall.dial soapRequest : "+soapRequest);
			String soapResponse = getSoapResponse(soapClient, soapRequest, soapAction.toString());
			logger.info("SocketCall.dial soapResponse : "+soapResponse);
			readerXMLMap = XMLReader.readerXMLByTagName(soapResponse, "dial");
		
		}catch(Exception e){
			logger.error("Error SocketCall.dial",e);
			throw e;
		}
		return readerXMLMap;
	}
	
	/**
	 * 
	 * @param number The number to dial - string
	 * @param bitrate The desired bandwidth - string
	 * @param conferenceID ConferenceID to be used for this call - string
	 * @param alias Alias to use – specifies call settings - string
	 * @param callType Protocol for the call – optional (“sip”, “h323”) – string
	 * @param SetMetadata Inherit conference metadata from the alias – boolean
	 * @param PIN MCU conference PIN if required – string
	 * @return
	 */
	public Map<String, Object> clusterDial (String number,String bitrate,String conferenceID,String alias,String callType,boolean setMetadata,String PIN)throws Exception{
		//XML中的name和Value值
		Map<String, Object> readerXMLMap = null;
		try{
			SoapClient soapClient = new SoapClient();
			StringBuffer soapAction = new StringBuffer();
			Map<String, Object> param = new HashMap<String, Object>();
			soapAction.append("ClusterDial");
			param.put("Number", number==null?"":number);
			param.put("Bitrate", bitrate==null?"":bitrate);
			param.put("ConferenceID", conferenceID==null?"":conferenceID);
			param.put("Alias", alias==null?"":alias);
			param.put("CallType",callType==null?"":callType);
			param.put("SetMetadata",setMetadata);
			param.put("PIN", PIN==null?"":PIN);
			
			String soapRequest = soapClient.getRequestStrByMethodName(soapAction.toString(), param);
			logger.info("SocketCall.clusterDial soapRequest : "+soapRequest);
			String soapResponse = getSoapResponse(soapClient, soapRequest, soapAction.toString());
			logger.info("SocketCall.clusterDial soapResponse : "+soapResponse);
			readerXMLMap = XMLReader.readerXMLByTagName(soapResponse, "clusterDial");
		
		}catch(Exception e){
			logger.error("Error SocketCall.clusterDial",e);
			throw e;
		}
		return readerXMLMap;
	}
	
	/**
	 * 
	 * @param conferenceID ConferenceID for the call being disconnected - string
	 * @param groupID The conference’s group – optional - string
	 * @return
	 */
	//<SOAP-ENV:Envelope xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/" xmlns:ns1="http://www.tandberg.net/XML/Streaming/1.0"><SOAP-ENV:Body><ns1:DisconnectCallResponse><ns1:DisconnectCallResult><ns1:Error></ns1:Error><ns1:ErrorCode>0</ns1:ErrorCode></ns1:DisconnectCallResult></ns1:DisconnectCallResponse></SOAP-ENV:Body></SOAP-ENV:Envelope>
	public Map<String, Object> disconnectCall(String conferenceID,String groupID)throws Exception{
		//XML中的name和Value值
		Map<String, Object> readerXMLMap = null;
		try{
			SoapClient soapClient = new SoapClient();
			StringBuffer soapAction = new StringBuffer();
			Map<String, Object> param = new HashMap<String, Object>();
			soapAction.append("DisconnectCall");
			param.put("ConferenceID", conferenceID==null?"":conferenceID);
			param.put("GroupID", groupID==null?"":groupID);
			
			String soapRequest = soapClient.getRequestStrByMethodName(soapAction.toString(), param);
			logger.info("SocketCall.disconnectCall soapRequest : "+soapRequest);
			String soapResponse = getSoapResponse(soapClient, soapRequest, soapAction.toString());
			logger.info("SocketCall.disconnectCall soapResponse : "+soapResponse);
			readerXMLMap = XMLReader.readerXMLByTagName(soapResponse, "disconnectCall");
		
		}catch(Exception e){
			logger.error("Error SocketCall.disconnectCall",e);
			throw e;
		}
		return readerXMLMap;
	}
	
	/**
	 * 
	 * @param serialNumber A specific Content Server SerialNumber or empty string for all servers in a cluster - string
	 * @return
	 */
	//<SOAP-ENV:Envelope xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/" xmlns:ns1="http://www.tandberg.net/XML/Streaming/1.0"><SOAP-ENV:Body><ns1:DisconnectAllCallsResponse><ns1:DisconnectAllCallsResult>true</ns1:DisconnectAllCallsResult></ns1:DisconnectAllCallsResponse></SOAP-ENV:Body></SOAP-ENV:Envelope>
	public Map<String, Object> disconnectAllCalls(String serialNumber)throws Exception{
		//XML中的name和Value值
		Map<String, Object> readerXMLMap = null;
		try{
			SoapClient soapClient = new SoapClient();
			StringBuffer soapAction = new StringBuffer();
			Map<String, Object> param = new HashMap<String, Object>();
			soapAction.append("DisconnectAllCalls");
			param.put("SerialNumber", serialNumber==null?"":serialNumber);
			
			String soapRequest = soapClient.getRequestStrByMethodName(soapAction.toString(), param);
			logger.info("SocketCall.disconnectAllCalls soapRequest : "+soapRequest);
			String soapResponse = getSoapResponse(soapClient, soapRequest, soapAction.toString());
			
			logger.info("SocketCall.disconnectAllCalls soapResponse : "+soapResponse);
			readerXMLMap = XMLReader.readerXMLByTagName(soapResponse, "disconnectAllCalls");
		
		}catch(Exception e){
			logger.error("Error SocketCall.disconnectAllCalls",e);
			throw e;
		}
		return readerXMLMap;
	}
	
	/**
	 * Get detailed information about a call in progresss
	 * @param conferenceID The Conference ID for the call - string
	 * @return
	 */
	//<SOAP-ENV:Envelope xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/" xmlns:ns1="http://www.tandberg.net/XML/Streaming/1.0"><SOAP-ENV:Body><ns1:GetCallInfoResponse><ns1:GetCallInfoResult><ns1:CallState>IN_CALL</ns1:CallState><ns1:RemoteEndpoint>123456</ns1:RemoteEndpoint><ns1:CallRate>3008</ns1:CallRate><ns1:RecordingAliasName>Default OnDemand only</ns1:RecordingAliasName><ns1:MediaState>RECORDING</ns1:MediaState><ns1:WriterStatus>OK</ns1:WriterStatus><ns1:AudioCodec>AAC-LD</ns1:AudioCodec><ns1:VideoCodec>H.264</ns1:VideoCodec></ns1:GetCallInfoResult></ns1:GetCallInfoResponse></SOAP-ENV:Body></SOAP-ENV:Envelope>
	public Map<String, Object> getCallInfo(String conferenceID)throws Exception{
		//XML中的name和Value值
		Map<String, Object> readerXMLMap = null;
		try{
			SoapClient soapClient = new SoapClient();
			StringBuffer soapAction = new StringBuffer();
			Map<String, Object> param = new HashMap<String, Object>();
			soapAction.append("GetCallInfo");
			param.put("ConferenceID", conferenceID==null?"":conferenceID);
			
			String soapRequest = soapClient.getRequestStrByMethodName(soapAction.toString(), param);
			logger.info("SocketCall.getCallInfo soapRequest : "+soapRequest);
			String soapResponse = getSoapResponse(soapClient, soapRequest, soapAction.toString());
			
			logger.info("SocketCall.getCallInfo soapResponse : "+soapResponse);
			readerXMLMap = XMLReader.readerXMLByTagName(soapResponse, "getCallInfo");
		
		}catch(Exception e){
			logger.error("Error SocketCall.getCallInfo",e);
			throw e;
		}
		return readerXMLMap;
	}
	
	/**
	 * This function is used for checking the Content Server system status.
	 * @return
	 */
	//<SOAP-ENV:Envelope xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/" xmlns:ns1="http://www.tandberg.net/XML/Streaming/1.0"><SOAP-ENV:Body><ns1:GetSystemHealthResponse><ns1:GetSystemHealthResult><ns1:EngineOK>true</ns1:EngineOK><ns1:LibraryOK>true</ns1:LibraryOK><ns1:DatabaseOK>true</ns1:DatabaseOK></ns1:GetSystemHealthResult></ns1:GetSystemHealthResponse></SOAP-ENV:Body></SOAP-ENV:Envelope>
	public Map<String, Object> getSystemHealth()throws Exception{
		//XML中的name和Value值
		Map<String, Object> readerXMLMap = null;
		try{
			SoapClient soapClient = new SoapClient();
			StringBuffer soapAction = new StringBuffer();
			soapAction.append("GetSystemHealth");
			String soapRequest = soapClient.getRequestStrByMethodName(soapAction.toString(), null);
			logger.info("SocketCall.getSystemHealth soapRequest : "+soapRequest);
			String soapResponse = getSoapResponse(soapClient, soapRequest, soapAction.toString());
			
			logger.info("SocketCall.getSystemHealth soapResponse : "+soapResponse);
			readerXMLMap = XMLReader.readerXMLByTagName(soapResponse, "getSystemHealth");
		
		}catch(Exception e){
			logger.error("Error SocketCall.getSystemHealth",e);
			throw e;
		}
		return readerXMLMap;
	}
	
	/**
	 * 
	 * @param serialNumber Optional parameter for cluster use, if present the function will return capacity information for that cluster node, rather than the whole cluster
	 * @return
	 */
	//<SOAP-ENV:Envelope xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/" xmlns:ns1="http://www.tandberg.net/XML/Streaming/1.0"><SOAP-ENV:Body><ns1:GetCallCapacityResponse><ns1:GetCallCapacityResult><ns1:TranscodingCalls>0</ns1:TranscodingCalls><ns1:MaxTranscodingCalls>0</ns1:MaxTranscodingCalls><ns1:ArchivingCalls>0</ns1:ArchivingCalls><ns1:MaxArchivingCalls>0</ns1:MaxArchivingCalls><ns1:MaxCalls>5</ns1:MaxCalls><ns1:CurrentCalls>0</ns1:CurrentCalls><ns1:MaxLiveCalls>2</ns1:MaxLiveCalls><ns1:CurrentLiveCalls>0</ns1:CurrentLiveCalls><ns1:CurrentPlaybackCalls>0</ns1:CurrentPlaybackCalls></ns1:GetCallCapacityResult></ns1:GetCallCapacityResponse></SOAP-ENV:Body></SOAP-ENV:Envelope>
	public Map<String, Object> getCallCapacity(String serialNumber)throws Exception{
		//XML中的name和Value值
		Map<String, Object> readerXMLMap = null;
		try{
			SoapClient soapClient = new SoapClient();
			StringBuffer soapAction = new StringBuffer();
			Map<String, Object> param = new HashMap<String, Object>();
			soapAction.append("GetCallCapacity");
			param.put("SerialNumber", serialNumber==null?"":serialNumber);
			
			String soapRequest = soapClient.getRequestStrByMethodName(soapAction.toString(), param);
			logger.info("SocketCall.getCallCapacity soapRequest : "+soapRequest);
			String soapResponse = getSoapResponse(soapClient, soapRequest, soapAction.toString());
			
			logger.info("SocketCall.getCallCapacity soapResponse : "+soapResponse);
			readerXMLMap = XMLReader.readerXMLByTagName(soapResponse, "getCallCapacity");
		
		}catch(Exception e){
			logger.error("Error SocketCall.getCallCapacity",e);
			throw e;
		}
		return readerXMLMap;
	}
	
	/**
	 * This call returns general system information about the Content Server being queried. 
	 * @return
	 */
	//<SOAP-ENV:Envelope xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/" xmlns:ns1="http://www.tandberg.net/XML/Streaming/1.0"><SOAP-ENV:Body><ns1:GetSystemInformationResponse><ns1:GetSystemInformationResult><ns1:ProductID>TANDBERG Content Server</ns1:ProductID><ns1:VersionMajor>5</ns1:VersionMajor><ns1:VersionMinor>0</ns1:VersionMinor><ns1:ReleaseType></ns1:ReleaseType><ns1:ReleaseNumber>0</ns1:ReleaseNumber><ns1:BuildNumber>3043</ns1:BuildNumber><ns1:IPAddress>10.2.222.18</ns1:IPAddress><ns1:SerialNumber>49A20538</ns1:SerialNumber><ns1:TranscodingOptions>0</ns1:TranscodingOptions><ns1:ArchivingOptions>0</ns1:ArchivingOptions><ns1:MaxCallOptions>5</ns1:MaxCallOptions><ns1:MaxLiveCallOptions>2</ns1:MaxLiveCallOptions><ns1:EngineOK>true</ns1:EngineOK><ns1:LicenseValid>true</ns1:LicenseValid><ns1:RevisionNumber>18306</ns1:RevisionNumber></ns1:GetSystemInformationResult></ns1:GetSystemInformationResponse></SOAP-ENV:Body></SOAP-ENV:Envelope>
	public Map<String, Object> getSystemInformation()throws Exception{
		//XML中的name和Value值
		Map<String, Object> readerXMLMap = null;
		try{
			SoapClient soapClient = new SoapClient();
			StringBuffer soapAction = new StringBuffer();
			soapAction.append("GetSystemInformation");
			
			String soapRequest = soapClient.getRequestStrByMethodName(soapAction.toString(), null);
			logger.info("SocketCall.getSystemInformation soapRequest : "+soapRequest);
			String soapResponse = getSoapResponse(soapClient, soapRequest, soapAction.toString());
			
			logger.info("SocketCall.getSystemInformation soapResponse : "+soapResponse);
			readerXMLMap = XMLReader.readerXMLByTagName(soapResponse, "getSystemInformation");
		
		}catch(Exception e){
			logger.error("Error SocketCall.getSystemInformation",e);
			throw e;
		}
		return readerXMLMap;
	}
	
	/**
	 * 
	 * @param aliasID The GUID, E.164 or H.323 alias of the recording alias - string
	 * @param conferenceID The ConferenceID to bind to
	 * @param uniqueOnly Bind only a 10 digit E.164 alias (ignored) - boolean
	 * @param bookingAddress The alias that the incoming call will use - string
	 * @param groupID The conference’s group, if it is recurring - string
	 * @return
	 */
	//<SOAP-ENV:Envelope xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/" xmlns:ns1="http://www.tandberg.net/XML/Streaming/1.0"><SOAP-ENV:Body><ns1:BindAliasConferenceIDResponse><ns1:BindAliasConferenceIDResult>2222@wafersystems.com</ns1:BindAliasConferenceIDResult></ns1:BindAliasConferenceIDResponse></SOAP-ENV:Body></SOAP-ENV:Envelope>
	public Map<String, Object> bindAliasConferenceID(String aliasID,String conferenceID,boolean uniqueOnly,String bookingAddress,String groupID)throws Exception{
		//XML中的name和Value值
		Map<String, Object> readerXMLMap = null;
		try{
			SoapClient soapClient = new SoapClient();
			StringBuffer soapAction = new StringBuffer();
			Map<String, Object> param = new HashMap<String, Object>();
			soapAction.append("BindAliasConferenceID");
			param.put("AliasID", aliasID==null?"":aliasID);
			param.put("ConferenceID", conferenceID==null?"":conferenceID);
			param.put("UniqueOnly", uniqueOnly);
			param.put("BookingAddress", bookingAddress==null?"":bookingAddress);
			param.put("GroupID", groupID==null?"":groupID);
			
			String soapRequest = soapClient.getRequestStrByMethodName(soapAction.toString(), param);
			logger.info("SocketCall.bindAliasConferenceID soapRequest : "+soapRequest);
			String soapResponse = getSoapResponse(soapClient, soapRequest, soapAction.toString());
			
			logger.info("SocketCall.bindAliasConferenceID soapResponse : "+soapResponse);
			readerXMLMap = XMLReader.readerXMLByTagName(soapResponse, "bindAliasConferenceID");
		
		}catch(Exception e){
			logger.error("Error SocketCall.bindAliasConferenceID",e);
			throw e;
		}
		return readerXMLMap;
	}
	
	/**
	 * @param conferenceID The Conference ID you wish to unbind – string
	 * @param aliasID The GUID, E.164 or H.323 alias of the recording alias - string
	 * @param groupID The conference’s group, if it is recurring - string
	 * @return
	 */
	public Map<String, Object> unBindAliasConferenceID(String conferenceID,String aliasID,String groupID)throws Exception{
		//XML中的name和Value值
		Map<String, Object> readerXMLMap = null;
		try{
			
			SoapClient soapClient = new SoapClient();
			StringBuffer soapAction = new StringBuffer();
			Map<String, Object> param = new HashMap<String, Object>();
			soapAction.append("UnBindAliasConferenceID");
			param.put("ConferenceID", conferenceID==null?"":conferenceID);
			param.put("AliasID", aliasID==null?"":aliasID);
			param.put("GroupID", groupID==null?"":groupID);
			
			String soapRequest = soapClient.getRequestStrByMethodName(soapAction.toString(), param);
			logger.info("SocketCall.unBindAliasConferenceID soapRequest : "+soapRequest);
			String soapResponse = getSoapResponse(soapClient, soapRequest, soapAction.toString());
			
			logger.info("SocketCall.unBindAliasConferenceID soapResponse : "+soapResponse);
			readerXMLMap = XMLReader.readerXMLByTagName(soapResponse, "unBindAliasConferenceID");
		
		}catch(Exception e){
			logger.error("Error SocketCall.unBindAliasConferenceID",e);
			throw e;
		}
		return readerXMLMap;
	}
	
	/**
	 * @param alias The GUID, E.164 or H.323 alias of the recording alias – string
	 * @return
	 */
	//<SOAP-ENV:Envelope xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/" xmlns:ns1="http://www.tandberg.net/XML/Streaming/1.0"><SOAP-ENV:Body><ns1:GetRecordingAliasResponse><ns1:GetRecordingAliasResult><ns1:AliasID>8B229AFD-FA27-458D-B99A-633783A716FF</ns1:AliasID><ns1:Name>IBD_TEST_RECORDING</ns1:Name><ns1:SystemAlias>false</ns1:SystemAlias><ns1:Owner>Administrator</ns1:Owner><ns1:Permissions><ns1:AllUsersAccess>true</ns1:AllUsersAccess><ns1:Users/><ns1:Editors><ns1:string>Administrator</ns1:string></ns1:Editors><ns1:Password></ns1:Password></ns1:Permissions></ns1:GetRecordingAliasResult></ns1:GetRecordingAliasResponse></SOAP-ENV:Body></SOAP-ENV:Envelope>
	public Map<String, Object> getRecordingAlias(String alias)throws Exception{
		//XML中的name和Value值
		Map<String, Object> readerXMLMap = null;
		try{
			SoapClient soapClient = new SoapClient();
			StringBuffer soapAction = new StringBuffer();
			Map<String, Object> param = new HashMap<String, Object>();
			soapAction.append("GetRecordingAlias");
			param.put("Alias", alias==null?"":alias);
			
			String soapRequest = soapClient.getRequestStrByMethodName(soapAction.toString(), param);
			logger.info("SocketCall.getRecordingAlias soapRequest : "+soapRequest);
			String soapResponse = getSoapResponse(soapClient, soapRequest, soapAction.toString());
			
			logger.info("SocketCall.getRecordingAlias soapResponse : "+soapResponse);
			readerXMLMap = XMLReader.readerXMLByTagName(soapResponse, "getRecordingAlias");
		
		}catch(Exception e){
			logger.error("Error SocketCall.getRecordingAlias",e);
			throw e;
		}
		return readerXMLMap;
	}
	
	/**
	 * @param sourceAlias The GUID, E.164 or H.323 alias of the recording alias – string
	 * @param dataVO Get A RecordingAliasData structure by dataVO
	 * @return
	 */
	//<SOAP-ENV:Envelope xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/" xmlns:ns1="http://www.tandberg.net/XML/Streaming/1.0"><SOAP-ENV:Body><ns1:GetRecordingAliasResponse><ns1:GetRecordingAliasResult><ns1:AliasID>8B229AFD-FA27-458D-B99A-633783A716FF</ns1:AliasID><ns1:Name>IBD_TEST_RECORDING</ns1:Name><ns1:SystemAlias>false</ns1:SystemAlias><ns1:Owner>Administrator</ns1:Owner><ns1:Permissions><ns1:AllUsersAccess>true</ns1:AllUsersAccess><ns1:Users/><ns1:Editors><ns1:string>Administrator</ns1:string></ns1:Editors><ns1:Password></ns1:Password></ns1:Permissions></ns1:GetRecordingAliasResult></ns1:GetRecordingAliasResponse></SOAP-ENV:Body></SOAP-ENV:Envelope>
	public Map<String, Object> addRecordingAlias(String sourceAlias,DataVO dataVO)throws Exception{
		//XML中的name和Value值
		Map<String, Object> readerXMLMap = null;
		try{
			SoapClient soapClient = new SoapClient();
			StringBuffer soapAction = new StringBuffer();
			Map<String, Object> param = new HashMap<String, Object>();
			soapAction.append("AddRecordingAlias");
			param.put("SourceAlias", sourceAlias==null?"":sourceAlias);
			String soapRequest = soapClient.getRequestStrByMethodName(soapAction.toString(), param,this.getData(dataVO));
			logger.info("SocketCall.addRecordingAlias soapRequest : "+soapRequest);
			String soapResponse = getSoapResponse(soapClient, soapRequest, soapAction.toString());
			
			logger.info("SocketCall.addRecordingAlias soapResponse : "+soapResponse);
			readerXMLMap = XMLReader.readerXMLByTagName(soapResponse, "addRecordingAlias");
		
		}catch(Exception e){
			logger.error("Error SocketCall.addRecordingAlias",e);
			throw e;
		}
		return readerXMLMap;
	}
	
	/**
	 * @param alias The GUID, E.164 or H.323 alias of the recording alias – string
	 * @param dataVO Get A RecordingAliasData structure by dataVO
	 * @return
	 */
	//<SOAP-ENV:Envelope xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/" xmlns:ns1="http://www.tandberg.net/XML/Streaming/1.0"><SOAP-ENV:Body><ns1:GetRecordingAliasResponse><ns1:GetRecordingAliasResult><ns1:AliasID>8B229AFD-FA27-458D-B99A-633783A716FF</ns1:AliasID><ns1:Name>IBD_TEST_RECORDING</ns1:Name><ns1:SystemAlias>false</ns1:SystemAlias><ns1:Owner>Administrator</ns1:Owner><ns1:Permissions><ns1:AllUsersAccess>true</ns1:AllUsersAccess><ns1:Users/><ns1:Editors><ns1:string>Administrator</ns1:string></ns1:Editors><ns1:Password></ns1:Password></ns1:Permissions></ns1:GetRecordingAliasResult></ns1:GetRecordingAliasResponse></SOAP-ENV:Body></SOAP-ENV:Envelope>
	public void modifyRecordingAlias(String alias,DataVO dataVO)throws Exception{
		try{
			SoapClient soapClient = new SoapClient();
			StringBuffer soapAction = new StringBuffer();
			Map<String, Object> param = new HashMap<String, Object>();
			soapAction.append("ModifyRecordingAlias");
			param.put("Alias", alias==null?"":alias);
			String soapRequest = soapClient.getRequestStrByMethodName(soapAction.toString(), param,this.getData(dataVO));
			logger.info("SocketCall.modifyRecordingAlias soapRequest : "+soapRequest);
			String soapResponse = getSoapResponse(soapClient, soapRequest, soapAction.toString());
			
			logger.info("SocketCall.modifyRecordingAlias soapResponse : "+soapResponse);
		}catch(Exception e){
			logger.error("Error SocketCall.modifyRecordingAlias",e);
			throw e;
		}
	}
	
	/**
	 * Delete a recording alias. 
	 * @param alias The GUID, E.164 or H.323 alias of the recording alias – string
	 * @return
	 */
	//<SOAP-ENV:Envelope xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/" xmlns:ns1="http://www.tandberg.net/XML/Streaming/1.0"><SOAP-ENV:Body><ns1:GetRecordingAliasResponse><ns1:GetRecordingAliasResult><ns1:AliasID>8B229AFD-FA27-458D-B99A-633783A716FF</ns1:AliasID><ns1:Name>IBD_TEST_RECORDING</ns1:Name><ns1:SystemAlias>false</ns1:SystemAlias><ns1:Owner>Administrator</ns1:Owner><ns1:Permissions><ns1:AllUsersAccess>true</ns1:AllUsersAccess><ns1:Users/><ns1:Editors><ns1:string>Administrator</ns1:string></ns1:Editors><ns1:Password></ns1:Password></ns1:Permissions></ns1:GetRecordingAliasResult></ns1:GetRecordingAliasResponse></SOAP-ENV:Body></SOAP-ENV:Envelope>
	public void deleteRecordingAlias(String alias)throws Exception{
		try{
			SoapClient soapClient = new SoapClient();
			StringBuffer soapAction = new StringBuffer();
			Map<String, Object> param = new HashMap<String, Object>();
			soapAction.append("DeleteRecordingAlias");
			param.put("Alias", alias==null?"":alias);
			String soapRequest = soapClient.getRequestStrByMethodName(soapAction.toString(), param);
			logger.info("SocketCall.deleteRecordingAlias soapRequest : "+soapRequest);
			String soapResponse = getSoapResponse(soapClient, soapRequest, soapAction.toString());
			logger.info("SocketCall.deleteRecordingAlias soapResponse : "+soapResponse);
		}catch(Exception e){
			logger.error("Error SocketCall.deleteRecordingAlias",e);
			throw e;
		}
	}
	
	/**
	 * Restarts the call control and live transcoding service.
	 * @return
	 */
	//<SOAP-ENV:Envelope xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/" xmlns:ns1="http://www.tandberg.net/XML/Streaming/1.0"><SOAP-ENV:Body><ns1:RestartServiceResponse><ns1:RestartServiceResult>true</ns1:RestartServiceResult></ns1:RestartServiceResponse></SOAP-ENV:Body></SOAP-ENV:Envelope>
	public Map<String, Object> restartService()throws Exception{
		//XML中的name和Value值
		Map<String, Object> readerXMLMap = null;
		try{
			SoapClient soapClient = new SoapClient();
			StringBuffer soapAction = new StringBuffer();
			soapAction.append("RestartService");
			String soapRequest = soapClient.getRequestStrByMethodName(soapAction.toString(), null);
			logger.info("SocketCall.restartService soapRequest : "+soapRequest);
			String soapResponse = getSoapResponse(soapClient, soapRequest, soapAction.toString());
			
			logger.info("SocketCall.restartService soapResponse : "+soapResponse);
			readerXMLMap = XMLReader.readerXMLByTagName(soapResponse, "restartService");
		
		}catch(Exception e){
			logger.error("Error SocketCall.restartService",e);
			throw e;
		}
		return readerXMLMap;
	}
	
	/**
	 * This call enables modification of existing Content Server conferences. 
	 * @param owner The username of the conference owner – string
	 * @param password The conference password – string
	 * @param startDateTime Start date of the recording using GNU date formats - string
	 * @param duration Duration of call in seconds – integer
	 * @param title The title that will appear in the Content Library – string
	 * @param conferenceId The conferenceId – string
	 * @return
	 */
	//<SOAP-ENV:Envelope xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/" xmlns:ns1="http://www.tandberg.net/XML/Streaming/1.0"><SOAP-ENV:Body><ns1:ModifyConferenceResponse/></SOAP-ENV:Body></SOAP-ENV:Envelope>
	public Map<String, Object> modifyConference(String owner,String password,String startDateTime,String duration,String title,String conferenceId)throws Exception{
		//XML中的name和Value值
		Map<String, Object> readerXMLMap = null;
		try{
			
			SoapClient soapClient = new SoapClient();
			StringBuffer soapAction = new StringBuffer();
			Map<String, Object> param = new HashMap<String, Object>();
			soapAction.append("ModifyConference");
			param.put("owner", owner==null?"":owner);
			param.put("password", password==null?"":password);
			param.put("startDateTime", startDateTime==null?"":dateFormat.format(startDateTime));
			param.put("duration", duration==null?"":duration);
			param.put("title", title==null?"":title);
			param.put("conferenceId", conferenceId==null?"":conferenceId);
			
			String soapRequest = soapClient.getRequestStrByMethodName(soapAction.toString(), param);
			logger.info("SocketCall.modifyConference soapRequest : "+soapRequest);
			String soapResponse = getSoapResponse(soapClient, soapRequest, soapAction.toString());
			
			logger.info("SocketCall.modifyConference soapResponse : "+soapResponse);
			readerXMLMap = XMLReader.readerXMLByTagName(soapResponse, "modifyConference");
		
		}catch(Exception e){
			logger.error("Error SocketCall.modifyConference",e);
			throw e;
		}
		return readerXMLMap;
	}
	
	/**
	 * Return information for a specific conference. 
	 * @param conferenceID The conference GUID – string
	 * @param userName Username of user performing request
	 * @return
	 */
	public Map<String, Object> getConference(String conferenceID,String userName)throws Exception{
		//XML中的name和Value值
		Map<String, Object> readerXMLMap = null;
		try{
			SoapClient soapClient = new SoapClient();
			StringBuffer soapAction = new StringBuffer();
			Map<String, Object> param = new HashMap<String, Object>();
			soapAction.append("GetConference");
			param.put("ConferenceID", conferenceID==null?"":conferenceID);
			param.put("UserName", userName==null?"":userName);
			
			String soapRequest = soapClient.getRequestStrByMethodName(soapAction.toString(), param);
			logger.info("SocketCall.getConference soapRequest : "+soapRequest);
			String soapResponse = getSoapResponse(soapClient, soapRequest, soapAction.toString());
			
			logger.info("SocketCall.getConference soapResponse : "+soapResponse);
			readerXMLMap = XMLReader.readerXMLByTagName(soapResponse, "getConference");
		
		}catch(Exception e){
			logger.error("Error SocketCall.getConference",e);
			throw e;
		}
		return readerXMLMap;
	}
	
	/**
	 * 
	 * @param searchExpression The full text search expression [see below] – string
	 * @param resultRange The result range [see notes] – integer
	 * @param dateTime The date time range [see notes] – integer
	 * @param updateTime The update time range [see notes] – integer
	 * @param owner The conference owner username – string
	 * @param category The Category assigned to the conference – string
	 * @param sort The order in which you require the results to be sorted – string
	 * @return
	 */
	//<SOAP-ENV:Envelope xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/" xmlns:ns1="http://www.tandberg.net/XML/Streaming/1.0"><SOAP-ENV:Body><ns1:GetConferencesResponse><ns1:GetConferencesResult><ns1:Conference><ns1:ConferenceID>876557AE-9A0B-4D3E-AD9E-425507B398A1</ns1:ConferenceID><ns1:Title>TCSMEETING</ns1:Title><ns1:Description>PSS</ns1:Description><ns1:URL>http://10.2.222.18/tcs/?id=876557AE-9A0B-4D3E-AD9E-425507B398A1</ns1:URL><ns1:Speaker>Lei</ns1:Speaker><ns1:Keywords>wafer</ns1:Keywords><ns1:Copyright>@Wafer</ns1:Copyright><ns1:Location>Guangzhou</ns1:Location><ns1:Movies/><ns1:DateTime>1345219200</ns1:DateTime><ns1:UpdateTime>1345189486</ns1:UpdateTime><ns1:Duration>67860</ns1:Duration><ns1:Owner>Administrator</ns1:Owner><ns1:OwnerDetails><ns1:UserName>Administrator</ns1:UserName><ns1:DisplayName>System Administrator</ns1:DisplayName></ns1:OwnerDetails><ns1:Deleted>false</ns1:Deleted><ns1:Labels><ns1:Label><ns1:Name>Meetings</ns1:Name><ns1:Description></ns1:Description></ns1:Label></ns1:Labels><ns1:GuestAccess>true</ns1:GuestAccess><ns1:HasWatchableMovie>false</ns1:HasWatchableMovie><ns1:WatchableMovies/><ns1:HasDownloadableMovie>true</ns1:HasDownloadableMovie><ns1:DownloadableMovies><ns1:DownloadableMovie><ns1:Display>MPEG-4 for QuickTime Large 1280x720 (22.7 MB)</ns1:Display><ns1:URL>http://10.2.222.18/tcs/download/9FB28A05-4F9F-4AB4-BDCB-E55C74DDA3F5.mp4</ns1:URL><ns1:Width>1280</ns1:Width><ns1:Height>720</ns1:Height></ns1:DownloadableMovie></ns1:DownloadableMovies><ns1:HasPending>false</ns1:HasPending><ns1:PercentTranscoded>100</ns1:PercentTranscoded><ns1:Password></ns1:Password><ns1:PlaybackEnabled>false</ns1:PlaybackEnabled><ns1:PlaybackH323Id></ns1:PlaybackH323Id><ns1:PlaybackE164Alias></ns1:PlaybackE164Alias></ns1:Conference><ns1:Conference><ns1:ConferenceID>CF872928-07EF-4B19-B1D9-0275321AF3E5</ns1:ConferenceID><ns1:Title>TCSMEETING</ns1:Title><ns1:Description>PSS</ns1:Description><ns1:URL>http://10.2.222.18/tcs/?id=CF872928-07EF-4B19-B1D9-0275321AF3E5</ns1:URL><ns1:Speaker>Lei</ns1:Speaker><ns1:Keywords>wafer</ns1:Keywords><ns1:Copyright>@Wafer</ns1:Copyright><ns1:Location>Guangzhou</ns1:Location><ns1:Movies/><ns1:DateTime>1345219200</ns1:DateTime><ns1:UpdateTime>1345190750</ns1:UpdateTime><ns1:Duration>27040</ns1:Duration><ns1:Owner>Administrator</ns1:Owner><ns1:OwnerDetails><ns1:UserName>Administrator</ns1:UserName><ns1:DisplayName>System Administrator</ns1:DisplayName></ns1:OwnerDetails><ns1:Deleted>false</ns1:Deleted><ns1:Labels><ns1:Label><ns1:Name>Meetings</ns1:Name><ns1:Description></ns1:Description></ns1:Label></ns1:Labels><ns1:GuestAccess>true</ns1:GuestAccess><ns1:HasWatchableMovie>true</ns1:HasWatchableMovie><ns1:WatchableMovies><ns1:ArrayOfWatchableMovie><ns1:WatchableMovie><ns1:ClipStart>0</ns1:ClipStart><ns1:ClipEnd>0</ns1:ClipEnd><ns1:Format>WindowsMedia</ns1:Format><ns1:Quality>Large</ns1:Quality><ns1:OfflineTranscoded>true</ns1:OfflineTranscoded><ns1:TotalBandwidth>2511</ns1:TotalBandwidth><ns1:MainURL>mms://10.2.222.18/O134519079954-17409488.wmv</ns1:MainURL><ns1:MainWidth>1280</ns1:MainWidth><ns1:MainHeight>720</ns1:MainHeight><ns1:DualURL></ns1:DualURL><ns1:DualWidth>0</ns1:DualWidth><ns1:DualHeight>0</ns1:DualHeight></ns1:WatchableMovie></ns1:ArrayOfWatchableMovie></ns1:WatchableMovies><ns1:HasDownloadableMovie>false</ns1:HasDownloadableMovie><ns1:DownloadableMovies/><ns1:HasPending>false</ns1:HasPending><ns1:PercentTranscoded>100</ns1:PercentTranscoded><ns1:Password>123</ns1:Password><ns1:PlaybackEnabled>false</ns1:PlaybackEnabled><ns1:PlaybackH323Id></ns1:PlaybackH323Id><ns1:PlaybackE164Alias></ns1:PlaybackE164Alias></ns1:Conference><ns1:Conference><ns1:ConferenceID>04A64371-D3D8-4B2E-A7E7-BF50FCF9614D</ns1:ConferenceID><ns1:Title>Wafer IBD Product</ns1:Title><ns1:Description>PSS</ns1:Description><ns1:URL>http://10.2.222.18/tcs/?id=04A64371-D3D8-4B2E-A7E7-BF50FCF9614D</ns1:URL><ns1:Speaker>Lei</ns1:Speaker><ns1:Keywords>wafer</ns1:Keywords><ns1:Copyright>@Wafer</ns1:Copyright><ns1:Location>Guangzhou</ns1:Location><ns1:Movies/><ns1:DateTime>1345192730</ns1:DateTime><ns1:UpdateTime>1345192730</ns1:UpdateTime><ns1:Duration>25800</ns1:Duration><ns1:Owner>Administrator</ns1:Owner><ns1:OwnerDetails><ns1:UserName>Administrator</ns1:UserName><ns1:DisplayName>System Administrator</ns1:DisplayName></ns1:OwnerDetails><ns1:Deleted>false</ns1:Deleted><ns1:Labels><ns1:Label><ns1:Name>Meetings</ns1:Name><ns1:Description></ns1:Description></ns1:Label></ns1:Labels><ns1:GuestAccess>true</ns1:GuestAccess><ns1:HasWatchableMovie>true</ns1:HasWatchableMovie><ns1:WatchableMovies><ns1:ArrayOfWatchableMovie><ns1:WatchableMovie><ns1:ClipStart>0</ns1:ClipStart><ns1:ClipEnd>0</ns1:ClipEnd><ns1:Format>WindowsMedia</ns1:Format><ns1:Quality>Large</ns1:Quality><ns1:OfflineTranscoded>true</ns1:OfflineTranscoded><ns1:TotalBandwidth>2511</ns1:TotalBandwidth><ns1:MainURL>mms://10.2.222.18/O134519277956-17409488.wmv</ns1:MainURL><ns1:MainWidth>1280</ns1:MainWidth><ns1:MainHeight>720</ns1:MainHeight><ns1:DualURL></ns1:DualURL><ns1:DualWidth>0</ns1:DualWidth><ns1:DualHeight>0</ns1:DualHeight></ns1:WatchableMovie></ns1:ArrayOfWatchableMovie></ns1:WatchableMovies><ns1:HasDownloadableMovie>false</ns1:HasDownloadableMovie><ns1:DownloadableMovies/><ns1:HasPending>false</ns1:HasPending><ns1:PercentTranscoded>100</ns1:PercentTranscoded><ns1:Password></ns1:Password><ns1:PlaybackEnabled>false</ns1:PlaybackEnabled><ns1:PlaybackH323Id></ns1:PlaybackH323Id><ns1:PlaybackE164Alias></ns1:PlaybackE164Alias></ns1:Conference><ns1:Conference><ns1:ConferenceID>148A9281-6116-4187-A5F9-CF79C354772A</ns1:ConferenceID><ns1:Title>Wafer IBD Product</ns1:Title><ns1:Description>PSS</ns1:Description><ns1:URL>http://10.2.222.18/tcs/?id=148A9281-6116-4187-A5F9-CF79C354772A</ns1:URL><ns1:Speaker>Lei</ns1:Speaker><ns1:Keywords>wafer</ns1:Keywords><ns1:Copyright>@Wafer</ns1:Copyright><ns1:Location>Guangzhou</ns1:Location><ns1:Movies/><ns1:DateTime>1345191169</ns1:DateTime><ns1:UpdateTime>1345191169</ns1:UpdateTime><ns1:Duration>52340</ns1:Duration><ns1:Owner>Administrator</ns1:Owner><ns1:OwnerDetails><ns1:UserName>Administrator</ns1:UserName><ns1:DisplayName>System Administrator</ns1:DisplayName></ns1:OwnerDetails><ns1:Deleted>false</ns1:Deleted><ns1:Labels><ns1:Label><ns1:Name>Meetings</ns1:Name><ns1:Description></ns1:Description></ns1:Label></ns1:Labels><ns1:GuestAccess>true</ns1:GuestAccess><ns1:HasWatchableMovie>true</ns1:HasWatchableMovie><ns1:WatchableMovies><ns1:ArrayOfWatchableMovie><ns1:WatchableMovie><ns1:ClipStart>0</ns1:ClipStart><ns1:ClipEnd>0</ns1:ClipEnd><ns1:Format>WindowsMedia</ns1:Format><ns1:Quality>Large</ns1:Quality><ns1:OfflineTranscoded>true</ns1:OfflineTranscoded><ns1:TotalBandwidth>2511</ns1:TotalBandwidth><ns1:MainURL>mms://10.2.222.18/O134519125155-17409488.wmv</ns1:MainURL><ns1:MainWidth>1280</ns1:MainWidth><ns1:MainHeight>720</ns1:MainHeight><ns1:DualURL></ns1:DualURL><ns1:DualWidth>0</ns1:DualWidth><ns1:DualHeight>0</ns1:DualHeight></ns1:WatchableMovie></ns1:ArrayOfWatchableMovie></ns1:WatchableMovies><ns1:HasDownloadableMovie>false</ns1:HasDownloadableMovie><ns1:DownloadableMovies/><ns1:HasPending>false</ns1:HasPending><ns1:PercentTranscoded>100</ns1:PercentTranscoded><ns1:Password></ns1:Password><ns1:PlaybackEnabled>false</ns1:PlaybackEnabled><ns1:PlaybackH323Id></ns1:PlaybackH323Id><ns1:PlaybackE164Alias></ns1:PlaybackE164Alias></ns1:Conference><ns1:Conference><ns1:ConferenceID>D133B88D-8768-4E33-A006-B815FF9CE212</ns1:ConferenceID><ns1:Title>Wafer IBD Product</ns1:Title><ns1:Description>PSS</ns1:Description><ns1:URL>http://10.2.222.18/tcs/?id=D133B88D-8768-4E33-A006-B815FF9CE212</ns1:URL><ns1:Speaker>Lei</ns1:Speaker><ns1:Keywords>wafer</ns1:Keywords><ns1:Copyright>@Wafer</ns1:Copyright><ns1:Location>Guangzhou</ns1:Location><ns1:Movies/><ns1:DateTime>1345182768</ns1:DateTime><ns1:UpdateTime>1345182768</ns1:UpdateTime><ns1:Duration>70859</ns1:Duration><ns1:Owner>Administrator</ns1:Owner><ns1:OwnerDetails><ns1:UserName>Administrator</ns1:UserName><ns1:DisplayName>System Administrator</ns1:DisplayName></ns1:OwnerDetails><ns1:Deleted>false</ns1:Deleted><ns1:Labels><ns1:Label><ns1:Name>Meetings</ns1:Name><ns1:Description></ns1:Description></ns1:Label></ns1:Labels><ns1:GuestAccess>true</ns1:GuestAccess><ns1:HasWatchableMovie>false</ns1:HasWatchableMovie><ns1:WatchableMovies/><ns1:HasDownloadableMovie>true</ns1:HasDownloadableMovie><ns1:DownloadableMovies><ns1:DownloadableMovie><ns1:Display>MPEG-4 for QuickTime Large 1024x576 (9.3 MB)</ns1:Display><ns1:URL>http://10.2.222.18/tcs/download/7046C7BC-CAA9-48F0-9CF9-7E275161AA1F.mp4</ns1:URL><ns1:Width>1024</ns1:Width><ns1:Height>576</ns1:Height></ns1:DownloadableMovie></ns1:DownloadableMovies><ns1:HasPending>false</ns1:HasPending><ns1:PercentTranscoded>100</ns1:PercentTranscoded><ns1:Password></ns1:Password><ns1:PlaybackEnabled>false</ns1:PlaybackEnabled><ns1:PlaybackH323Id></ns1:PlaybackH323Id><ns1:PlaybackE164Alias></ns1:PlaybackE164Alias></ns1:Conference><ns1:Conference><ns1:ConferenceID>7A91B6C8-5E3B-4CD0-94DE-8A549DC7A1F4</ns1:ConferenceID><ns1:Title>Wafer IBD Product</ns1:Title><ns1:Description>PSS</ns1:Description><ns1:URL>http://10.2.222.18/tcs/?id=7A91B6C8-5E3B-4CD0-94DE-8A549DC7A1F4</ns1:URL><ns1:Speaker>Lei</ns1:Speaker><ns1:Keywords>wafer</ns1:Keywords><ns1:Copyright>@Wafer</ns1:Copyright><ns1:Location>Guangzhou</ns1:Location><ns1:Movies/><ns1:DateTime>1345180970</ns1:DateTime><ns1:UpdateTime>1345180970</ns1:UpdateTime><ns1:Duration>53440</ns1:Duration><ns1:Owner>Administrator</ns1:Owner><ns1:OwnerDetails><ns1:UserName>Administrator</ns1:UserName><ns1:DisplayName>System Administrator</ns1:DisplayName></ns1:OwnerDetails><ns1:Deleted>false</ns1:Deleted><ns1:Labels><ns1:Label><ns1:Name>Meetings</ns1:Name><ns1:Description></ns1:Description></ns1:Label></ns1:Labels><ns1:GuestAccess>true</ns1:GuestAccess><ns1:HasWatchableMovie>true</ns1:HasWatchableMovie><ns1:WatchableMovies><ns1:ArrayOfWatchableMovie><ns1:WatchableMovie><ns1:ClipStart>0</ns1:ClipStart><ns1:ClipEnd>0</ns1:ClipEnd><ns1:Format>WindowsMedia</ns1:Format><ns1:Quality>Large</ns1:Quality><ns1:OfflineTranscoded>true</ns1:OfflineTranscoded><ns1:TotalBandwidth>1093</ns1:TotalBandwidth><ns1:MainURL>mms://10.2.222.18/O134518104738-17409488.wmv</ns1:MainURL><ns1:MainWidth>1024</ns1:MainWidth><ns1:MainHeight>576</ns1:MainHeight><ns1:DualURL></ns1:DualURL><ns1:DualWidth>0</ns1:DualWidth><ns1:DualHeight>0</ns1:DualHeight></ns1:WatchableMovie></ns1:ArrayOfWatchableMovie></ns1:WatchableMovies><ns1:HasDownloadableMovie>false</ns1:HasDownloadableMovie><ns1:DownloadableMovies/><ns1:HasPending>false</ns1:HasPending><ns1:PercentTranscoded>100</ns1:PercentTranscoded><ns1:Password></ns1:Password><ns1:PlaybackEnabled>false</ns1:PlaybackEnabled><ns1:PlaybackH323Id></ns1:PlaybackH323Id><ns1:PlaybackE164Alias></ns1:PlaybackE164Alias></ns1:Conference><ns1:Conference><ns1:ConferenceID>C936BE76-B0B5-4A88-B227-6A3441C4F086</ns1:ConferenceID><ns1:Title>Wafer IBD Product</ns1:Title><ns1:Description>PSS</ns1:Description><ns1:URL>http://10.2.222.18/tcs/?id=C936BE76-B0B5-4A88-B227-6A3441C4F086</ns1:URL><ns1:Speaker>Lei</ns1:Speaker><ns1:Keywords>wafer</ns1:Keywords><ns1:Copyright>@Wafer</ns1:Copyright><ns1:Location>Guangzhou</ns1:Location><ns1:Movies/><ns1:DateTime>1345158238</ns1:DateTime><ns1:UpdateTime>1345158239</ns1:UpdateTime><ns1:Duration>537300</ns1:Duration><ns1:Owner>Administrator</ns1:Owner><ns1:OwnerDetails><ns1:UserName>Administrator</ns1:UserName><ns1:DisplayName>System Administrator</ns1:DisplayName></ns1:OwnerDetails><ns1:Deleted>false</ns1:Deleted><ns1:Labels><ns1:Label><ns1:Name>Meetings</ns1:Name><ns1:Description></ns1:Description></ns1:Label></ns1:Labels><ns1:GuestAccess>true</ns1:GuestAccess><ns1:HasWatchableMovie>true</ns1:HasWatchableMovie><ns1:WatchableMovies><ns1:ArrayOfWatchableMovie><ns1:WatchableMovie><ns1:ClipStart>0</ns1:ClipStart><ns1:ClipEnd>0</ns1:ClipEnd><ns1:Format>WindowsMedia</ns1:Format><ns1:Quality>Large</ns1:Quality><ns1:OfflineTranscoded>true</ns1:OfflineTranscoded><ns1:TotalBandwidth>2511</ns1:TotalBandwidth><ns1:MainURL>mms://10.2.222.18/O134515882725-17409488.wmv</ns1:MainURL><ns1:MainWidth>1280</ns1:MainWidth><ns1:MainHeight>720</ns1:MainHeight><ns1:DualURL></ns1:DualURL><ns1:DualWidth>0</ns1:DualWidth><ns1:DualHeight>0</ns1:DualHeight></ns1:WatchableMovie></ns1:ArrayOfWatchableMovie></ns1:WatchableMovies><ns1:HasDownloadableMovie>false</ns1:HasDownloadableMovie><ns1:DownloadableMovies/><ns1:HasPending>false</ns1:HasPending><ns1:PercentTranscoded>100</ns1:PercentTranscoded><ns1:Password></ns1:Password><ns1:PlaybackEnabled>false</ns1:PlaybackEnabled><ns1:PlaybackH323Id></ns1:PlaybackH323Id><ns1:PlaybackE164Alias></ns1:PlaybackE164Alias></ns1:Conference><ns1:Conference><ns1:ConferenceID>E3EE9DED-8A6B-446F-B165-A9967427895D</ns1:ConferenceID><ns1:Title>Wafer IBD Product</ns1:Title><ns1:Description>PSS</ns1:Description><ns1:URL>http://10.2.222.18/tcs/?id=E3EE9DED-8A6B-446F-B165-A9967427895D</ns1:URL><ns1:Speaker>Lei</ns1:Speaker><ns1:Keywords>wafer</ns1:Keywords><ns1:Copyright>@Wafer</ns1:Copyright><ns1:Location>Guangzhou</ns1:Location><ns1:Movies/><ns1:DateTime>1345157167</ns1:DateTime><ns1:UpdateTime>1345157167</ns1:UpdateTime><ns1:Duration>107480</ns1:Duration><ns1:Owner>Administrator</ns1:Owner><ns1:OwnerDetails><ns1:UserName>Administrator</ns1:UserName><ns1:DisplayName>System Administrator</ns1:DisplayName></ns1:OwnerDetails><ns1:Deleted>false</ns1:Deleted><ns1:Labels><ns1:Label><ns1:Name>Meetings</ns1:Name><ns1:Description></ns1:Description></ns1:Label></ns1:Labels><ns1:GuestAccess>true</ns1:GuestAccess><ns1:HasWatchableMovie>true</ns1:HasWatchableMovie><ns1:WatchableMovies><ns1:ArrayOfWatchableMovie><ns1:WatchableMovie><ns1:ClipStart>0</ns1:ClipStart><ns1:ClipEnd>0</ns1:ClipEnd><ns1:Format>WindowsMedia</ns1:Format><ns1:Quality>Large</ns1:Quality><ns1:OfflineTranscoded>true</ns1:OfflineTranscoded><ns1:TotalBandwidth>1093</ns1:TotalBandwidth><ns1:MainURL>mms://10.2.222.18/O134515730021-17409488.wmv</ns1:MainURL><ns1:MainWidth>1280</ns1:MainWidth><ns1:MainHeight>720</ns1:MainHeight><ns1:DualURL></ns1:DualURL><ns1:DualWidth>0</ns1:DualWidth><ns1:DualHeight>0</ns1:DualHeight></ns1:WatchableMovie></ns1:ArrayOfWatchableMovie></ns1:WatchableMovies><ns1:HasDownloadableMovie>false</ns1:HasDownloadableMovie><ns1:DownloadableMovies/><ns1:HasPending>false</ns1:HasPending><ns1:PercentTranscoded>100</ns1:PercentTranscoded><ns1:Password></ns1:Password><ns1:PlaybackEnabled>false</ns1:PlaybackEnabled><ns1:PlaybackH323Id></ns1:PlaybackH323Id><ns1:PlaybackE164Alias></ns1:PlaybackE164Alias></ns1:Conference><ns1:Conference><ns1:ConferenceID>54D9E9A2-24E2-4FCD-9DE9-366B62009F20</ns1:ConferenceID><ns1:Title>Wafer IBD Product</ns1:Title><ns1:Description>PSS</ns1:Description><ns1:URL>http://10.2.222.18/tcs/?id=54D9E9A2-24E2-4FCD-9DE9-366B62009F20</ns1:URL><ns1:Speaker>Lei</ns1:Speaker><ns1:Keywords>wafer</ns1:Keywords><ns1:Copyright>@Wafer</ns1:Copyright><ns1:Location>Guangzhou</ns1:Location><ns1:Movies/><ns1:DateTime>1345156906</ns1:DateTime><ns1:UpdateTime>1345156907</ns1:UpdateTime><ns1:Duration>230179</ns1:Duration><ns1:Owner>Administrator</ns1:Owner><ns1:OwnerDetails><ns1:UserName>Administrator</ns1:UserName><ns1:DisplayName>System Administrator</ns1:DisplayName></ns1:OwnerDetails><ns1:Deleted>false</ns1:Deleted><ns1:Labels><ns1:Label><ns1:Name>Meetings</ns1:Name><ns1:Description></ns1:Description></ns1:Label></ns1:Labels><ns1:GuestAccess>true</ns1:GuestAccess><ns1:HasWatchableMovie>true</ns1:HasWatchableMovie><ns1:WatchableMovies><ns1:ArrayOfWatchableMovie><ns1:WatchableMovie><ns1:ClipStart>0</ns1:ClipStart><ns1:ClipEnd>0</ns1:ClipEnd><ns1:Format>WindowsMedia</ns1:Format><ns1:Quality>Large</ns1:Quality><ns1:OfflineTranscoded>true</ns1:OfflineTranscoded><ns1:TotalBandwidth>2511</ns1:TotalBandwidth><ns1:MainURL>mms://10.2.222.18/O134515721220-17409488.wmv</ns1:MainURL><ns1:MainWidth>1280</ns1:MainWidth><ns1:MainHeight>720</ns1:MainHeight><ns1:DualURL></ns1:DualURL><ns1:DualWidth>0</ns1:DualWidth><ns1:DualHeight>0</ns1:DualHeight></ns1:WatchableMovie></ns1:ArrayOfWatchableMovie></ns1:WatchableMovies><ns1:HasDownloadableMovie>false</ns1:HasDownloadableMovie><ns1:DownloadableMovies/><ns1:HasPending>false</ns1:HasPending><ns1:PercentTranscoded>100</ns1:PercentTranscoded><ns1:Password></ns1:Password><ns1:PlaybackEnabled>false</ns1:PlaybackEnabled><ns1:PlaybackH323Id></ns1:PlaybackH323Id><ns1:PlaybackE164Alias></ns1:PlaybackE164Alias></ns1:Conference></ns1:GetConferencesResult></ns1:GetConferencesResponse></SOAP-ENV:Body></SOAP-ENV:Envelope>
	public Map<String, Object> getConferences(String searchExpression,int[]resultRange,int[]dateTime,int[]updateTime,String owner,String category,String sort)throws Exception{
		//XML中的name和Value值
		Map<String, Object> readerXMLMap = null;
		try{
			
			SoapClient soapClient = new SoapClient();
			StringBuffer soapAction = new StringBuffer();
			Map<String, Object> param = new HashMap<String, Object>();
			soapAction.append("GetConferences");
			param.put("SearchExpression",searchExpression==null?"":searchExpression);
			String resultRangeStr = getStringValue(resultRange);
			if(resultRangeStr!=null){
				param.put("ResultRange", resultRangeStr);
			}
			String dateTimeStr = getStringValue(dateTime);
			if(dateTimeStr!=null){
				param.put("DateTime", dateTimeStr);
			}
			String updateTimeStr = getStringValue(updateTime);
			if(updateTimeStr!=null){
				param.put("UpdateTime", updateTimeStr);
			}
			param.put("Owner", owner==null?"":owner);
			param.put("Category", category==null?"":category);
			param.put("Sort", sort==null?"":sort);
			
			String soapRequest = soapClient.getRequestStrByMethodName(soapAction.toString(), param);
			logger.info("SocketCall.getConferences soapRequest : "+soapRequest);
			String soapResponse = getSoapResponse(soapClient, soapRequest, soapAction.toString());
			
			logger.info("SocketCall.getConferences soapResponse : "+soapResponse);
			readerXMLMap = XMLReader.readerXMLByTagName(soapResponse, "getConferences");
		
		}catch(Exception e){
			logger.error("Error SocketCall.getConferences",e);
			throw e;
		}
		return readerXMLMap;
		
	}
	
	/**
	 * This call returns a count of the recorded conferences in the database as an integer.
	 * @return
	 */
	//<SOAP-ENV:Envelope xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/" xmlns:ns1="http://www.tandberg.net/XML/Streaming/1.0"><SOAP-ENV:Body><ns1:GetConferenceCountResponse><ns1:GetConferenceCountResult>40</ns1:GetConferenceCountResult></ns1:GetConferenceCountResponse></SOAP-ENV:Body></SOAP-ENV:Envelope>
	public Map<String, Object> getConferenceCount()throws Exception{
		//XML中的name和Value值
		Map<String, Object> readerXMLMap = null;
		try{
			SoapClient soapClient = new SoapClient();
			StringBuffer soapAction = new StringBuffer();
			soapAction.append("GetConferenceCount");
			String soapRequest = soapClient.getRequestStrByMethodName(soapAction.toString(), null);
			logger.info("SocketCall.getConferenceCount soapRequest : "+soapRequest);
			String soapResponse = getSoapResponse(soapClient, soapRequest, soapAction.toString());
			
			logger.info("SocketCall.getConferenceCount soapResponse : "+soapResponse);
			readerXMLMap = XMLReader.readerXMLByTagName(soapResponse, "getConferenceCount");
		
		}catch(Exception e){
			logger.error("Error SocketCall.getConferenceCount",e);
			throw e;
		}
		return readerXMLMap;
	}
	
	/**
	 * The DeleteRecording call is used to delete a conference from the Content Library and remove media associated with that conference. 
	 * @param conferenceID The conferenceID – string
	 * @return
	 */
	//<SOAP-ENV:Envelope xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/" xmlns:ns1="http://www.tandberg.net/XML/Streaming/1.0"><SOAP-ENV:Body><ns1:DeleteRecordingResponse/></SOAP-ENV:Body></SOAP-ENV:Envelope>
	public void deleteRecording(String conferenceID)throws Exception{
		try{
			if(StrUtil.isEmptyStr(conferenceID)){
				logger.error("Error SocketCall.deleteRecording,parameter conferenceID is null or empty.");
			}
			SoapClient soapClient = new SoapClient();
			StringBuffer soapAction = new StringBuffer();
			Map<String, Object> param = new HashMap<String, Object>();
			soapAction.append("DeleteRecording");
			param.put("conferenceID", conferenceID==null?"":conferenceID);
			
			String soapRequest = soapClient.getRequestStrByMethodName(soapAction.toString(), param);
			logger.info("SocketCall.deleteRecording soapRequest : "+soapRequest);
			String soapResponse = getSoapResponse(soapClient, soapRequest, soapAction.toString());
			
			logger.info("SocketCall.deleteRecording soapResponse : "+soapResponse);
		}catch(Exception e){
			logger.error("Error SocketCall.deleteRecording",e);
			throw e;
		}
	}
	
	/**
	 * GetConferenceThumbnail returns a list of thumbnail URLs for a conference. 
	 * @param conferenceID
	 * @return
	 */
	//<SOAP-ENV:Envelope xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/" xmlns:ns1="http://www.tandberg.net/XML/Streaming/1.0"><SOAP-ENV:Body><ns1:GetConferenceThumbnailsResponse><ns1:GetConferenceThumbnailsResult><ns1:string>http://10.2.222.18/tcs/slides/00D4486F-F911-4BBB-A68B-D4CFBD447B13/thumbnails/320x180thumbnail60.jpg</ns1:string><ns1:string>http://10.2.222.18/tcs/slides/00D4486F-F911-4BBB-A68B-D4CFBD447B13/thumbnails/320x180thumbnail5.jpg</ns1:string></ns1:GetConferenceThumbnailsResult></ns1:GetConferenceThumbnailsResponse></SOAP-ENV:Body></SOAP-ENV:Envelope>
	public Map<String, Object> getConferenceThumbnails(String conferenceID)throws Exception{
		//XML中的name和Value值
		Map<String, Object> readerXMLMap = null;
		try{
			SoapClient soapClient = new SoapClient();
			StringBuffer soapAction = new StringBuffer();
			Map<String, Object> param = new HashMap<String, Object>();
			soapAction.append("GetConferenceThumbnails");
			param.put("ConferenceID", conferenceID==null?"":conferenceID);
			
			String soapRequest = soapClient.getRequestStrByMethodName(soapAction.toString(), param);
			logger.info("SocketCall.getConferenceThumbnails soapRequest : "+soapRequest);
			String soapResponse = getSoapResponse(soapClient, soapRequest, soapAction.toString());
			
			logger.info("SocketCall.getConferenceThumbnails soapResponse : "+soapResponse);
			readerXMLMap = XMLReader.readerXMLByTagName(soapResponse, "getConferenceThumbnails");
		
		}catch(Exception e){
			logger.error("Error SocketCall.getConferenceThumbnails",e);
			throw e;
		}
		return readerXMLMap;
	}
	
	
	/**
	 * This function sets the default thumbnail for a conference.
	 * @param conferenceID The conference GUID – string
	 * @param thumbnail The thumbnail – string
	 * @return
	 */
	//<SOAP-ENV:Envelope xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/" xmlns:ns1="http://www.tandberg.net/XML/Streaming/1.0"><SOAP-ENV:Body><ns1:SetConferenceThumbnailDefaultResponse/></SOAP-ENV:Body></SOAP-ENV:Envelope>
	public void setConferenceThumbnailDefault(String conferenceID,String thumbnail)throws Exception{
		try{
			SoapClient soapClient = new SoapClient();
			StringBuffer soapAction = new StringBuffer();
			Map<String, Object> param = new HashMap<String, Object>();
			soapAction.append("SetConferenceThumbnailDefault");
			param.put("ConferenceID", conferenceID==null?"":conferenceID);
			param.put("Thumbnail", thumbnail==null?"":thumbnail);
			String soapRequest = soapClient.getRequestStrByMethodName(soapAction.toString(), param);
			logger.info("SocketCall.setConferenceThumbnailDefault soapRequest : "+soapRequest);
			String soapResponse = getSoapResponse(soapClient, soapRequest, soapAction.toString());
			logger.info("SocketCall.setConferenceThumbnailDefault soapResponse : "+soapResponse);
		}catch(Exception e){
			logger.error("Error SocketCall.setConferenceThumbnailDefault",e);
			throw e;
		}
	}
	
	/**
	 * This function deletes a thumbnail held by a conference.
	 * @param conferenceID The conference GUID – string
	 * @param thumbnail The thumbnail – string
	 * @return
	 */
	//<SOAP-ENV:Envelope xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/" xmlns:ns1="http://www.tandberg.net/XML/Streaming/1.0"><SOAP-ENV:Body><ns1:DeleteConferenceThumbnailResponse/></SOAP-ENV:Body></SOAP-ENV:Envelope>
	public void deleteConferenceThumbnail(String conferenceID,String thumbnail)throws Exception{
		try{
			SoapClient soapClient = new SoapClient();
			StringBuffer soapAction = new StringBuffer();
			Map<String, Object> param = new HashMap<String, Object>();
			soapAction.append("DeleteConferenceThumbnail");
			param.put("ConferenceID", conferenceID==null?"":conferenceID);
			param.put("Thumbnail", thumbnail==null?"":thumbnail);
			String soapRequest = soapClient.getRequestStrByMethodName(soapAction.toString(), param);
			logger.info("SocketCall.deleteConferenceThumbnail soapRequest : "+soapRequest);
			String soapResponse = getSoapResponse(soapClient, soapRequest, soapAction.toString());
			logger.info("SocketCall.deleteConferenceThumbnail soapResponse : "+soapResponse);
		
		}catch(Exception e){
			logger.error("Error SocketCall.deleteConferenceThumbnail",e);
			throw e;
		}
	}
	
	private String getStringValue(int[] intArray){
		if(intArray!=null&&intArray.length>0){
			int count = intArray.length;
			String strValue = "";
			for(int i=0;i<count;i++){
				strValue+="<int>"+intArray[i]+"</int>";
			}
			return strValue;
		}
		return null;
	}
	
	
	private String getData(DataVO dataVO){
		StringBuffer buffer = new StringBuffer();
		buffer.append("<Data>")
			  .append("<AliasID>"+dataVO.getAliasID()+"</AliasID>")
			  .append("<Name>"+dataVO.getName()+"</Name>")
			  .append("<SystemAlias>"+dataVO.isSystemAlias()+"</SystemAlias>")
			  .append("<Owner>"+dataVO.getOwner()+"</Owner>")
			  .append("<E164Alias>"+dataVO.getE164Alias()+"</E164Alias>")
			  .append("<H323Alias>"+dataVO.getH323Alias()+"</H323Alias>")
			  .append("<SipURI>"+dataVO.getSipURI()+"</SipURI>")
			  .append("<SipDisplayName>"+dataVO.getSipDisplayName()+"</SipDisplayName>")
			  .append("<ShowCountdown>"+dataVO.isShowCountdown()+"</ShowCountdown>")
			  .append("<SendEmail>"+dataVO.isSendEmail()+"</SendEmail>")
			  .append("<EmailAddress>"+dataVO.getEmailAddress()+"</EmailAddress>")
			  .append("<RecordingTitle>"+dataVO.getRecordingTitle()+"</RecordingTitle>")
			  .append("<RecordingDescription>"+dataVO.getRecordingDescription()+"</RecordingDescription>")
			  .append("<RecordingSpeaker>"+dataVO.getRecordingSpeaker()+"</RecordingSpeaker>")
			  .append("<RecordingKeywords>"+dataVO.getRecordingKeywords()+"</RecordingKeywords>")
			  .append("<RecordingCopyright>"+dataVO.getRecordingCopyright()+"</RecordingCopyri,ght>")
			  .append("<RecordingLocation>"+dataVO.getRecordingLocation()+"</RecordingLocation>")
			  .append("<RecordingCategory>"+dataVO.getRecordingCategory()+"</RecordingCategory>")
			  .append("<RecordingOwner>"+dataVO.getRecordingOwner()+"</RecordingOwner>")
			  .append("<AutomaticallyPublishRecordings>"+dataVO.isAutomaticallyPublishRecordings()+"</AutomaticallyPublishRecordings>")
			  .append("<EnableEndpointPlaybackForRecordings>"+dataVO.isEnableEndpointPlaybackForRecordings()+"</EnableEndpointPlaybackForRecordings>")
			  .append("<Permissions>")
			  .append("<AllUsersAccess>"+dataVO.isAllUsersAccess()+"</AllUsersAccess>")
			  .append("<Users>");
		String [] users = dataVO.getUsers();
		if(users!=null&&users.length>0){
			int count = users.length;
			for(int i=0;i<count;i++){
				buffer.append("<string>"+users[i]+"</string>");
			}
		}
		buffer.append("</Users>")
			  .append("<Editors>");
		String [] Editors = dataVO.getUsers();
		if(Editors!=null&&Editors.length>0){
			int count = Editors.length;
			for(int i=0;i<count;i++){
				buffer.append("<string>"+Editors[i]+"</string>");
			}
		}
		buffer.append("</Editors>")
			  .append("<Password>"+dataVO.getPassword()+"</Password>")
			  .append("<RecordingPin>"+dataVO.getRecordingPin()+"</RecordingPin>")
			  .append("</Permissions>")
			  .append("</Data>");
		return buffer.toString();
	}
	

}
