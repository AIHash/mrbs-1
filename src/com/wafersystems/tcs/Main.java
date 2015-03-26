package com.wafersystems.tcs;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import com.wafersystems.tcs.vo.ConferenceVO;
import com.wafersystems.tcs.vo.DataVO;

public class Main {

public static void main(String arge[]){
		
		try {
			SocketCall socketCall = new SocketCall();
//			socketCall.prepareConference("6666", "33@wafersystems.com", "", false, "TCSMEETING", "admin", "49A31176", new Date(), 180);
			
//			System.out.println(map.get("requestConferenceID"));
//			socketCall.dial("2222", "1920", "", "6666", "H.323", false, "");
//			socketCall.getSystemHealth();
			
//			Map map = socketCall.getRecordingAlias("7012");
//			DataVO dv = (DataVO)map.get("DataVO");
//			System.out.println(dv.getAliasID());
			

//			Map map = socketCall.disconnectAllCalls("49A31176");
//			System.out.println(map.get("DisconnectAllCallsResult"));
			
			// 7999:22f83080-9ae0-11e4-b63f-000d7c119668
			// 8211:8c10c790-9bc5-11e4-b647-000d7c119668
			// 8212:20b2f360-9bfc-11e4-b656-000d7c119668
			// require:556E3254-6A53-452E-A376-6C20E5C6C669
			
			/** 预创建一个会议 */
//			Map map = socketCall.requestConferenceID("admin","49A31176",new Date(), 300, "TCSMEETING", "", false);
//			System.out.println(map.get("requestConferenceID"));
			/** 根据预创建的会议ID拨号 8012 加入录播服务 */
//			socketCall.dial("8012", "64", "map.get("requestConferenceID")", "7012", "h323", false, "");
			/** 根据预创建的会议ID获得录播的播放URL字符串 */
			Map map = socketCall.getConference("8c10c790-9bc5-11e4-b647-000d7c119668","admin");// 8c10c790-9bc5-11e4-b647-000d7c119668
			// conferenceId,URL
			ConferenceVO cvo = (ConferenceVO)map.get("ConferenceVO");
			System.out.println(cvo.getConferenceID());
			System.out.println(cvo.getURL());
			
//			socketCall.getCallCapacity("49A31176");
//			socketCall.getManagementSystems();
			
//			Map<String, Object> map = socketCall.getConferenceCount();
//			System.out.println(map.get("GetConferenceCountResult"));
			
//			int[] intA = {1,2,3};
//			Calendar calendar = Calendar.getInstance();
////			int[] intD = { new Date().getDate()};// declared method
//			int[] intD = {calendar.get(Calendar.DAY_OF_MONTH)};
//			Map map = socketCall.getConferences("", intA, intD, null, "admin", "", "");
//			System.out.println(map.get("getConferences"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
