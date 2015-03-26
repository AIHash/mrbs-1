package com.wafersystems.util;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public class FormParToMap {
	static public  Map<String,Object>  createMap(HttpServletRequest request,String[][]meetcon){
		if(meetcon==null)
			return null;
		Map<String,Object> map=new HashMap<String,Object>();
		String temp="";
		for(int i=0;i<meetcon.length;i++){
			temp=request.getParameter(meetcon[i][0]);
			if(isParameEmpety(temp)){
				if("string".equals(meetcon[i][1])){
					map.put(meetcon[i][0], temp);
				}
				else if("date".equals(meetcon[i][1])){
					map.put(meetcon[i][0], DateUtil.getDate(temp));
				}
				else if("integer".equals(meetcon[i][1])){
					map.put(meetcon[i][0],Integer.valueOf((String)(temp)));
				}
				else  if("short".equals(meetcon[i][1])){
					map.put(meetcon[i][0],Short.valueOf((String)(temp)));
				}
				else if("float".equals(meetcon[i][1])){
					map.put(meetcon[i][0],Float.valueOf((String)(temp)));
				}
				else{
					
				}
			}
		}
		return map;
	
	}
	public static  boolean isParameEmpety(String parmer){
		boolean flag=true;
		if(parmer==null||"".equals(parmer.trim())||"-1".equals(parmer))
			flag=false;
		return flag;
	}
	public static void fromReqParToMap(HttpServletRequest request,String[]temp,Map<String,Object> map){
		if(temp==null||temp.length==0)
			return ;
		for(String tem:temp){
			if(request.getParameter(tem)==null)
				map.put(tem, "");
			else{
				map.put(tem, request.getParameter(tem));
			}
			
				
		}
	}
}
