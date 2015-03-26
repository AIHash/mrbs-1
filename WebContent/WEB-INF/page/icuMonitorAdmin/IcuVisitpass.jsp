<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page pageEncoding="UTF-8"%>
<%@include file="/common.jsp"%>
<html>
<head>
    <comm:pageHeader hasEcList="false"/>
	<title><comm:message key="system.system_name" /></title>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/My97DatePicker/WdatePicker.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery.js"></script>
	<script src="<%=request.getContextPath()%>/resources/jqueryalerts/jquery.ui.draggable.js" type="text/javascript"></script>
	<script src="<%=request.getContextPath()%>/resources/jqueryalerts/jquery.alerts.js" type="text/javascript"></script>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/jqueryalerts/jquery.alerts.css" media="screen" />
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/resources/css/style.css"/>
    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/jqueryui/jqueryTab/jquery.idTabs.min.js"></script>
	<style type="text/css">
	/*主容器*/ 
	.usual { 
	    /* background:snow; */
	    background:#fff;
	    color:#111; 
	    padding:0px 0px; 
	    width:97%; 
	    border:0px solid red; 
	    margin:0px auto; 
	    vertial-align:middle;
	} 
	
	/*选项卡*/ 
	.usual ul 
	{ 
	margin:0; 
	padding:0; 
	padding-left:0em; 
	overflow:auto;/*火狐等清理浮动*/ 
	_display:inline-block;/*ie6清理浮动*/ 
	} 
	.usual li {list-style:none;float:left;} 
	.usual ul a { 
	    display:block; 
	    padding:6px 10px; 
	    text-decoration:none; 
	    font:12px /* Arial */; 
	    color:#000; 
	    background:#D2D2D2; 
	    outline:none; 
	} 
	.usual ul a:hover { 
	    color:#FFF; 
	    background:#D2D2D2; 
	    } 
	.usual ul a.selected { 
	    color:#000; 
	    background:snow; 
	    cursor:default; 
	    } 
	     
	    /*tab页*/ 
	.usual div { 
	    padding:1px 1px 0px 1px; 
	    /*background:snow;  
	    font:10pt Arial;  */
	}	
	.unifiedFont{margin-top:50px;}
	.exportcenter{width:100%;margin-top:12px;}
	.unifiedcenter{margin-top:12px;}
	#center{background-color:#ffffff;border:0px solid red;width:90%;align:center;position:relative;margin-left:10px;}
	#titleStyle{color:#3c3645;font-size:18px;font-weight: bold;height:30px;line-height:30px;margin-bottom:0px;margin-left:30px;margin-right:30px;}
	#viewappinfor{border:0px solid red;width:100%;align:center;/* margin-left:18px;margin-top:15px;margin-bottom:5px;margin-right:18px; */position:relative;background-color:#dee;}
	.subinfor{border:0px solid #7F9DB9;background-color:#F8F8FF;margin-left:25px;margin-right:25px;}
	#rightdown{background-image: url("../resources/images/table/down.gif");background-repeat: repeat-x;width:19px;height:28px;margin-bottom:2px;margin-left:15px;margin-right:15px;margin-top:8px;}
	#leftup{background-image: url("../resources/images/table/up.gif");background-repeat: repeat-x;width:19px;height:28px;margin-bottom:2px;margin-left:15px;margin-right:15px;margin-top:20px;}
	</style>

<script type="text/javascript"  >

	function setTimeToText(temp)
	{
		var obj=document.getElementById(temp);
		setDayHM(obj);
	}
	
	function check()
	{
		 var startTime=document.getElementById("startTime").value;
		 if(strTrim(startTime) == "")
		 {
			 jAlert("<comm:message key='meeting.m.starttimealert'/>", "<comm:message key='meeting.m.infotishi'/>");
			 return false;
		 }
		 var endTime=document.getElementById("endTime").value;
		 if(strTrim(endTime) == "")
		 {
			 jAlert("<comm:message key='meeting.m.endtimealert'/>", "<comm:message key='meeting.m.infotishi'/>");
			 return false;
		 }
		 if (compareCheckDate(startTime,endTime) != '-1'){
			 jAlert("<comm:message key='js.need_input_larger' args='meeting.m.endtime,meeting.m.starttime'/>", "<comm:message key='meeting.m.infotishi'/>");
			 return false;
		 }
		 var meetingRoom=document.getElementById("meetingRoom").value;
		 if(strTrim(meetingRoom) == "")
		 {
			 jAlert("<comm:message key='js.need_select' args='admin.meetingroom' />", "<comm:message key='meeting.m.infotishi'/>");
			 return false;
		 }
		 $.ajaxSettings.traditional = true;
		 $.ajax({url:'${pageContext.request.contextPath}/meeadmdbd/checkTimeRepeat',
			 	type: "POST",
			 	dataType : 'text',
			 	async:false,
			 	data: {startTime :startTime, endTime : endTime,meetingRoom : meetingRoom,exceptMeetingId:'',meetingTypestr:1},
				success:function(msg){
					if(msg == 'repeat'){
						$("#timeRepeatFlag").val("timeIsRepeat");
					}else if(msg == 'ok'){
						$("#timeRepeatFlag").val("timeIsOk");
					}
				}
		 });
		 var timeRepeatFlag = $("#timeRepeatFlag").val();
		 if(timeRepeatFlag=='timeIsRepeat'){
			 jAlert("<comm:message key='meeting.pleaseChooseNewTime'/>", "<comm:message key='meeting.m.infotishi'/>");
			return false;
		 }
		 var mobileDevices = $("#mobileDevices").val();
		 if(mobileDevices ==""){
			 jAlert("<comm:message key='icu.mobile.devices.notnull'/>", "<comm:message key='meeting.m.infotishi'/>");
				return false;
		 }
		 return true;
	}
	
	function setEndTime() {
		var startTime=document.getElementById("startTime").value;
		 if(!strTrim(startTime) == ""){
			 startTime=startTime.replace(/-/g, "/");
			 var startTimetemp = new Date(startTime);
			 var endDate = new Date(startTimetemp.valueOf() + 60*60*1000); 
			 document.getElementById("endTime").value=getDate(endDate,"YYYY-MM-DD HH:MM");
		 }
	}
	
	function passsubmit(){
		if(check()){
			var icuVisitId=$('#icuVisitId').val();
			var starttime=$('#startTime').val();//开始时间 
			var endtime=$('#endTime').val();//结束时间
			var meetingRoom=document.getElementById("meetingRoom").value;
			var mobileDevices = $("#mobileDevices").val();
			$.ajax({type :'POST',  url : '${pageContext.request.contextPath}/icumonitor/icuVisitpass', 
				data :  {icuVisitId:icuVisitId,startTime:starttime,endTime:endtime,meetingRoom:meetingRoom,mobileDevices:mobileDevices},
				dataType : 'text',
				success:function(msg){
				if(msg == 'succ'){
					jAlert("<comm:message key='meeting.m.submitsucc'/>", "<comm:message key='meeting.m.infotishi'/>", function(r) {
						url="<%=request.getContextPath()%>/meeadIndex/icuVisitQuery";
						parent.document.getElementById("WorkBench").contentWindow.location.href = url;
				    	parent.$.fancybox.close();
					});
				}else{
					jAlert("<comm:message key='meeting.m.submitfalse'/>", "<comm:message key='meeting.m.infotishi'/>");
				}
			}});
		}
	}
	
</script>

</head>
<body id="windows" style="overflow-y:auto;">
  <div id="usual1" class="usual">   
    <ul id="tabHeight">    
        <li><a href="#tab1" class="selected"><comm:message key='meeting.pass'/></a></li>
    </ul>
   <div id="tab1">
    <form id="formtongguo" name="formtongguo" method="post" action="<%=request.getContextPath()%>/icumonitor/icuVisitpass" onsubmit="return check()">
    <input type="hidden" name="icuVisitId" id="icuVisitId" value="${requestmeetid_add}"/>
     <input type="hidden" id="timeRepeatFlag" value="timeIsRepeat"/>
	     <div id="viewappinfor">
			<div id="titleStyle">
				<span>&nbsp;</span>
			</div>		
            <div class="subinfor" id="patientinfor1">
				  <table class="submit_table" align="center" width="100%" border="1" >
					<tr>
						<td align="right" style="vertical-align:middle;">&nbsp;&nbsp;<strong style="white-space:nowrap;"><comm:message key='meeting.m.starttime'/></strong>:&nbsp;<font color="red">*</font>&nbsp;</td>
						<td style="vertical-align:middle;">
							<input type="text" name="startTime" id="startTime" class="Wdate"  onclick="var endTime=$dp.$('endTime');WdatePicker({readOnly:true,onpicked:setEndTime,dateFmt:'yyyy-M-d H:mm',minDate:'%y-%M-%d %H:%m:%s'})" onfocus="this.blur()" style="border:#999 1px solid;height:20px;background:#fff url(<%=request.getContextPath()%>/resources/My97DatePicker/skin/datePicker.gif) no-repeat right;margin-left:20px;" readonly="readonly" size="25"/>
						</td>
										
						<td align="right" style="vertical-align:middle;">&nbsp;&nbsp;<strong style="white-space:nowrap;"><comm:message key='meeting.m.endtime'/></strong>:&nbsp;<font color="red">*</font>&nbsp;</td>
						<td style="vertical-align:middle;">
							<input type="text" name="endTime" id="endTime" class="Wdate"  onclick="WdatePicker({readOnly:true,dateFmt:'yyyy-M-d H:mm'})" onfocus="this.blur()" style="border:#999 1px solid;height:20px;background:#fff url(<%=request.getContextPath()%>/resources/My97DatePicker/skin/datePicker.gif) no-repeat right;margin-left:20px;" readonly="readonly" size="25"/>								
						</td>
						
					</tr>
					<tr >
						<td  align="right" style="vertical-align:middle;">&nbsp;&nbsp;<strong style="white-space:nowrap;"><comm:message key='icu.monitor.room'/></strong>:&nbsp;<font color="red">*</font>&nbsp;</td>		
					    <td colspan="3" style="vertical-align:middle;">
					    	<select name="meetingRoom" id="meetingRoom" style="width:160px;height:22px;margin-left:20px;">
								<c:forEach var="meetingRoom" items="${basecode['meetingroom']}">
									<option value="${meetingRoom.id}">${meetingRoom.name}</option>
								</c:forEach>
							</select>
					    </td>
					</tr>
					<tr >
						<td  align="right" style="vertical-align:middle;">&nbsp;&nbsp;<strong style="white-space:nowrap;"><comm:message key='icu.mobile.devices'/></strong>:&nbsp;<font color="red">*</font>&nbsp;</td>		
					    <td colspan="3" style="vertical-align:middle;">
					    	 &nbsp;&nbsp; <input type="text" id="mobileDevices" name="mobileDevices" style="width:160px;"/>
					    </td>
					</tr>
				</table>
				<table border="0" align="center" width="90%">
					<tr>
						<td colspan="4" align="center" height="30"><input class="button" type="button"  name="passbutton" id="passbutton" value="<comm:message key='meeting.m.commit'/>" onclick="passsubmit();"/></td>
			         </tr>
				</table>            
            </div>
			<div id="titleStyle">
				<span>&nbsp;</span>
			</div>
        </div>   
	</form>
   </div>
  </div>
<script type="text/javascript">    
    $("#usual1 ul").idTabs();    
</script>	
</body>
</html>