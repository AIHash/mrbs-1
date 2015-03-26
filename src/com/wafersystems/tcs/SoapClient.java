package com.wafersystems.tcs;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.NoSuchAlgorithmException;
import java.util.Iterator;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wafersystems.util.MD5;


public class SoapClient {
	
	private static int serverPort = 80;
	
	private static String serverIp = "10.0.31.14";
	// Cisco TelePresence Content Server
	// TANDBERG Content Server
	public static String authorizationString = "Digest username=\"admin\",realm=\"TANDBERG Content Server\",nonce=\"54cafff09753e\",uri=\"/tcs/SoapServer.php\",cnonce=\"0a4f113b\",nc=00000001,response=\"07c62cd689adef7b1dfbb2ee9acde64a\",qop=\"auth\",opaque=\"8f26ba16a05e575bf3abddd4c33d5f9d\"";
	
	private static String getURI = "/tcs/SoapServer.php";
	
	private static Logger logger = LoggerFactory.getLogger(SoapClient.class);
	
	private String username="admin";// admin
	private String password="49A31176";// 49A31176   Etyy@2014*
	private String method="POST";
	private String uri="/tcs/SoapServer.php";
	private String nc="00000001";
	private String cnonce="0a4f113b";
	private String qop="auth";
	
	//unauthorizedAuthenticateInfo : "HTTP/1.1 401 Unauthorized WWW-Authenticate: Digest realm=\"testrealm@host.com\",nonce=\"dcd98b7102dd2f0e8b11d0f600bfb0c093\", opaque=\"5ccc069c403ebaf9f0171e9517f40e41\""
	public String getAuthorization(String unauthorizedAuthenticateInfo){
		StringBuffer authorizationSB = new StringBuffer(2048);
		try {
			if(StrUtil.isEmptyStr(unauthorizedAuthenticateInfo)){
				logger.error("Error SoapClient.getAuthorization parameter unauthorizedAuthenticateInfo is null or empty.");
				return null;
			}
			/** Get challenge contents from digest */
			int start = unauthorizedAuthenticateInfo.indexOf("WWW-Authenticate: Digest ");
			int end = unauthorizedAuthenticateInfo.indexOf("WWW-Authenticate: Basic");
			String digest = unauthorizedAuthenticateInfo.substring(start, end);
			
			String str[] = digest.split(",");
			int count = str.length;
			String realm="";
			String nonce="";
			String opaque="";
			for(int i=0;i<count;i++){
				String strValue = str[i];
				if(i==0){
					realm = strValue.substring(strValue.indexOf("=\"")+2, strValue.length()-1);
				}else if(i==1){
					this.qop = strValue.substring(strValue.indexOf("=\"")+2, strValue.length()-1);
				}else if(i==2){
					nonce = strValue.substring(strValue.indexOf("=\"")+2, strValue.length()-1);
				}else if(i==3){
					opaque = strValue.substring(strValue.indexOf("=\"")+2, strValue.length()-1);
				}
			}
			String HA1 = MD5.MD5_32(this.username+":"+realm+":"+this.password);
			String HA2 = MD5.MD5_32(method+":"+this.uri);
			String response =  MD5.MD5_32(HA1+":"+nonce+":"+this.nc+":"+this.cnonce+":"+this.qop+":"+HA2);
			authorizationSB.append("Digest ")
						   .append("username=\""+username+"\",")
						   .append("realm=\""+realm+"\",")
						   .append("nonce=\""+nonce+"\",")
						   .append("uri=\""+uri+"\",")
						   .append("qop=\""+qop+"\",")
						   .append("nc="+nc+",")
						   .append("cnonce=\""+cnonce+"\",")
						   .append("response=\""+response+"\",")
						   .append("opaque=\""+opaque);
			return authorizationSB.toString().trim();
		} catch (NoSuchAlgorithmException e) {
			logger.error("Error SoapClient.getAuthorization",e);
			return null;
		}
	}
	
	int i = 1;
	public String getSoapResponse(String soapRequest, String soapAction)
	{
		StringBuffer sb = new StringBuffer(2048);
		byte[] bArray = null; // buffer for reading response from
		Socket socket = null; // socket to AXL server
		OutputStream out = null; // output stream to server
		InputStream in = null; // input stream from server
		String sAXLSOAPRequest = "";
		// Form the http header
		sAXLSOAPRequest = "POST " + getURI + " HTTP/1.0\r\n";
		sAXLSOAPRequest += "User-Agent: Mozilla/4.0 (compatible; MSIE 6.0; MS Web Services Client Protocol 2.0.50727.42)\r\n";
		sAXLSOAPRequest += "Content-type: text/xml; charset=utf-8\r\n";
		sAXLSOAPRequest += "SOAPAction: http://www.tandberg.net/XML/Streaming/1.0/" + soapAction + "\r\n";
		sAXLSOAPRequest += "Authorization: " + authorizationString + "\r\n";
		sAXLSOAPRequest += "Host: " + serverIp + "\r\n";
		sAXLSOAPRequest += "Content-length: " + soapRequest.length() + "\r\n";
		sAXLSOAPRequest += "Expect: 100-continue\r\n\r\n";
		//sAXLSOAPRequest += "Connection: Keep-Alive\r\n";

		sAXLSOAPRequest += soapRequest;
		
		try {
			socket = new Socket(serverIp, serverPort);
			in = socket.getInputStream();
			// send the request to the server
			// read the response from the server
			//StringBuffer sb = new StringBuffer(2048);
			bArray = new byte[2048];
			int ch = 0;
			//int sum = 0;
			out = socket.getOutputStream();
			out.write(sAXLSOAPRequest.getBytes());
			
			while ((ch = in.read(bArray)) != -1) {
				//sum += ch;
				sb.append(new String(bArray, 0, ch));
			}
			socket.close();
		} catch (UnknownHostException e) {
			logger.error("Error SoapClient.getSoapResponse connecting to host: " + e.getMessage(),e);
			return "";
		} catch (IOException ioe) {
			logger.error("Error SoapClient.getSoapResponse sending/receiving from server: " + ioe.getMessage(),ioe);
			// close the socket
		} catch (Exception ea) {
			logger.error("Error SoapClient.getSoapResponse Unknown exception " + ea.getMessage(),ea);
			return "";
		}
		finally{
			try {
				if (socket != null)socket.close();
			} catch (Exception exc) {
				exc.printStackTrace();
				logger.error("Error SoapClient.getSoapResponse closing connection to server: "+ exc.getMessage(),exc);
			}
		}
		
		return sb.toString();
	}
	
	/**
	 * 通过方法参数转换为Soap请求
	 * @param methodName
	 * @param param
	 * @return
	 */
	public String getRequestStrByMethodName(String methodName, Map<String, Object> param){
		StringBuffer buffer = new StringBuffer();
		buffer.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>")
			  .append("<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"")
			  .append("xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\"")
			  .append("xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">")
			  .append("<soap:Body>")
			  .append("<" + methodName + " xmlns=\"http://www.tandberg.net/XML/Streaming/1.0\" >");
		if(param != null){
			Iterator<String> it = param.keySet().iterator();
			while(it.hasNext()){
				String key = it.next();
				buffer.append("<" + key + ">" + param.get(key) + "</" + key + ">");
			}
		}
		buffer.append("</" + methodName + ">")
			  .append( "</soap:Body>")
			  .append("</soap:Envelope>");
		return buffer.toString();
	}
	
	/**
	 * 通过方法参数转换为Soap请求
	 * @param methodName
	 * @param param
	 * @return
	 */
	public String getRequestStrByMethodName(String methodName, Map<String, Object> param,String Data){
		StringBuffer buffer = new StringBuffer();
		buffer.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>")
			  .append("<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"")
			  .append("xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\"")
			  .append("xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">")
			  .append("<soap:Body>")
			  .append("<" + methodName + " xmlns=\"http://www.tandberg.net/XML/Streaming/1.0\" >");
		if(param != null){
			Iterator<String> it = param.keySet().iterator();
			while(it.hasNext()){
				String key = it.next();
				buffer.append("<" + key + ">" + param.get(key) + "</" + key + ">");
			}
		}
		buffer.append(Data);
		buffer.append("</" + methodName + ">")
			  .append( "</soap:Body>")
			  .append("</soap:Envelope>");
		return buffer.toString();
	}

}
