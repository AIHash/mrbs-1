<%@ page pageEncoding="utf-8"%>
<%@include file="/common.jsp"%>
<%
	String returnmessage = (String) request.getSession().getAttribute("returnmessage");
	request.getSession().removeAttribute("returnmessage");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title><comm:message key="unified.application" /></title>
	<comm:pageHeader hasEcList="false" />
	<link href="${pageContext.request.contextPath}/resources/css/style.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/My97DatePicker/WdatePicker.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery.js"></script>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/jqueryui/css/jquery-ui-1.8.16.custom.css"/>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/jqueryalerts/jquery.alerts.css"  media="screen" />
    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/jqueryalerts/jquery.alerts.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/jqueryui/jquery-ui-1.8.16.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/validate.js"></script>
    
<script type="text/javascript"> 

	function setTimeToText(temp)
	{
		var obj=document.getElementById(temp);
		setDayHM(obj);
	}
	
	function check(){
		 var content=document.getElementById("lectureContent").value;
		 if(content==""){
			 jAlert("<comm:message key='meeting.lectureContent.notNull'/>", "<comm:message key='meeting.m.infotishi'/>");
			 return false;
		 }
		  var meetingleveld=document.getElementById("meetingleveld").value;
		 if(meetingleveld==""){
			  jAlert("<comm:message key='unified.need.meetinglevel'/>", "<comm:message key='meeting.m.infotishi'/>");
			 return false;
		 }
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
		 var intSelectedInnaiUser = $('#innaiUserSelected option').length;
		 var intSelectedCommonUser = $('#commonUserSelected option').length;
		 if(intSelectedInnaiUser==0){
			 jAlert("<comm:message key='meeting.pleaseChooseSpeaker'/>", "<comm:message key='meeting.m.infotishi'/>");
			 return false;
		 }
		 if(intSelectedInnaiUser>1){
			 jAlert("<comm:message key='meeting.pleaseChooseOnlySpeaker'/>", "<comm:message key='meeting.m.infotishi'/>");
			 return false;
		 }
		 if(intSelectedCommonUser==0){
			 jAlert("<comm:message key='meeting.pleaseChooseCommunity'/>", "<comm:message key='meeting.m.infotishi'/>");
			 return false;
		 }
		 
		 var selectedUserIds = "";
			
		 //获取被选择的专家
		 var innaiUserSelecteds=$('#innaiUserSelected option');
		 //迭代每一个专家，将所有专家用“|”区分，组合成字符串
		 $.each(innaiUserSelecteds,function(key,val){
			selectedUserIds = selectedUserIds + innaiUserSelecteds[key].value+"|";
		 });
		
		 //获取被选择的共同体专家
		 var commonUserSelecteds=$('#commonUserSelected option');
		 //迭代每一个共同体，将所有专家用“|”区分，组合成字符串
		 $.each(commonUserSelecteds,function(key,val){
			selectedUserIds = selectedUserIds + commonUserSelecteds[key].value+"|";
		 });
		 document.getElementById("selectedUserIds").value=selectedUserIds;
		 return true;
	}
	function myAlert(){
		var message='<%=returnmessage%>';
		if(message&&message!=""&&message!="null") {
			jAlert(message, "<comm:message key='meeting.m.infotishi'/>");
		}
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
	$(function() {
		$(document).ready(function() {
			$("#innaiUserSelect").dblclick(function(){
				rightmoveInnaiUser();
			});
			$("#innaiUserSelected").dblclick(function(){
				leftmoveInnaiUser();
			});
			$("#commonUserSelect").dblclick(function(){
				rightmoveCommonUser();
			});
			$("#commonUserSelected").dblclick(function(){
				leftmoveCommonUser();
			});
			//院内
			changeInnaiUser();
			var intSelectedInnaiUser = $('#innaiUserSelected option').length;
	        document.getElementById("intSelectedInnaiUser").innerHTML=intSelectedInnaiUser;
	        
	      	//共同体
			changeCommonUser();
			var intSelectedCommonUser = $('#commonUserSelected option').length;
	        document.getElementById("intSelectedCommonUser").innerHTML=intSelectedCommonUser;
	        
			setInterval(function() {
				//比对院内专家的速查条件是否有变化
				var innaiUserOldValue = document.getElementById("innaiUserOldValue").value;
				var queryInnaiUserName=$('#queryInnaiUserName').val();
				if(innaiUserOldValue!=queryInnaiUserName){
					changeInnaiUser();
				}
				document.getElementById("innaiUserOldValue").value=queryInnaiUserName;
				
				//比对共同体专家的速查条件是否有变化
				var commonUserOldValue = document.getElementById("commonUserOldValue").value;
				var queryCommonUserName=$('#queryCommonUserName').val();
				if(commonUserOldValue!=queryCommonUserName){
					changeCommonUser();
				}
				document.getElementById("commonUserOldValue").value=queryCommonUserName;
				
			}, 3000);
		});
	});
	
	function changeInnaiUser(){
		var queryInnaiUserName=$('#queryInnaiUserName').val();
		$("#innaiUserSelect").empty();  	   
		var innaiUserSelecteds=$('#innaiUserSelected option');//获取被选择的专家
		
		//迭代每一个专家，将所有专家用“|”区分，组合成字符串
		var innaiUserIds = "";
		$.each(innaiUserSelecteds,function(key,val){
			innaiUserIds = innaiUserIds + innaiUserSelecteds[key].value+"|";
		});
   	    //var innaiUserId;
	    //var innaiUserSelected = $("#innaiUserSelected").find("option");
		$.ajaxSettings.traditional = true;
	    $.ajax({type :'POST',  url : '${pageContext.request.contextPath}/meeadmdbd/getUserSelectItem', 
				data : {queryUserName:queryInnaiUserName,userIds:innaiUserIds,departmentKind:'${HIS_DEPT_CODE}'},
				dataType : 'json',
				dataFilter : function(json, type){
					if(json.indexOf('<html>') != -1){
						window.location.href="<%=request.getContextPath()%>/index.jsp?message=system.session_expire";
						return;
					}
					return json;
				},
				success : function(json) {
				$.each(json, function(i,n){
					$("<option value="+json[i].id+ ">"+json[i].name+"</option>").appendTo("#innaiUserSelect");
				});
			}
		});
	}
	//双击添加院内专家
	function rightmoveInnaiUser(){
		var intSelectedInnaiUser1 = $('#innaiUserSelected option').length;
		var intSelectedInnaiUser2 = $("#innaiUserSelect option:selected").length;
		if(intSelectedInnaiUser1>0||intSelectedInnaiUser2>1){
			jAlert("<comm:message key='meeting.pleaseChooseOnlySpeaker'/>", "<comm:message key='meeting.m.infotishi'/>");
			return false;
		}
		var so = $("#innaiUserSelect option:selected");
        $("#innaiUserSelected").append(so);
        var intSelectedInnaiUser = $('#innaiUserSelected option').length;
        document.getElementById("intSelectedInnaiUser").innerHTML=intSelectedInnaiUser;
        var queryInnaiUserName=$('#queryInnaiUserName').val();
        if(queryInnaiUserName==null||queryInnaiUserName.length==0){
        	changeInnaiUser();
        }
	}
	
	//双击删除院内专家
	function leftmoveInnaiUser(){
		var so = $("#innaiUserSelected option:selected");
        $("#innaiUserSelect").append(so);
        var intSelectedInnaiUser = $('#innaiUserSelected option').length;
        document.getElementById("intSelectedInnaiUser").innerHTML=intSelectedInnaiUser;
        var queryInnaiUserName=$('#queryInnaiUserName').val();
        if(queryInnaiUserName==null||queryInnaiUserName.length==0){
        	changeInnaiUser();
        }
	}

	//共同体
	function changeCommonUser(){
		//模糊查询条件
		var queryCommonUserName=$('#queryCommonUserName').val();
		//清空左边选单
		$("#commonUserSelect").empty(); 
		//获取被选择的共同体专家
		var commonUserSelecteds=$('#commonUserSelected option');
		//迭代每一个共同体，将所有专家用“|”区分，组合成字符串
		var commonUserIds = "";
		$.each(commonUserSelecteds,function(key,val){
			commonUserIds = commonUserIds + commonUserSelecteds[key].value+"|";
		});
		$.ajaxSettings.traditional = true;
	    $.ajax({type :'POST',  url : '${pageContext.request.contextPath}/meeadmdbd/getUserSelectItem', 
				data : {queryUserName:queryCommonUserName,userIds:commonUserIds,departmentKind:'${COMMUNITY_DEPT_CODE}'},
				dataType : 'json',
				dataFilter : function(json, type){
					if(json.indexOf('<html>') != -1){
						window.location.href="<%=request.getContextPath()%>/index.jsp?message=system.session_expire";
						return;
					}
					return json;
				},
				success : function(json) {
					$.each(json, function(i,n){
						$("<option value="+json[i].id+ ">"+json[i].name+"</option>").appendTo("#commonUserSelect");
					});
			}
		});
	}
	//双击添加共同体
	function rightmoveCommonUser(){
		var so = $("#commonUserSelect option:selected");
        $("#commonUserSelected").append(so);
        var intSelectedCommonUser = $('#commonUserSelected option').length;
        document.getElementById("intSelectedCommonUser").innerHTML=intSelectedCommonUser;
	}
	
	//双击删除共同体
	function leftmoveCommonUser(){
		var so = $("#commonUserSelected option:selected");
        $("#commonUserSelect").append(so);
        var intSelectedCommonUser = $('#commonUserSelected option').length;
        document.getElementById("intSelectedCommonUser").innerHTML=intSelectedCommonUser;
        sortOptions($('#commonUserSelect option'),$("#commonUserSelect"));
	}
	
	//重置
	function resetValue(){
		var intSelectedDept = $('#innaiUserSelected option').length;
		if(intSelectedDept>0){
			document.getElementById("innaiUserOldValue").value="";
	        document.getElementById("queryInnaiUserName").value="";
	        $("#innaiUserSelected").empty();  
	        changeInnaiUser();
	        document.getElementById("intSelectedInnaiUser").innerHTML="0";
		}
		
        var intSelectedIcd10 = $('#commonUserSelected option').length;
        if(intSelectedIcd10>0){
        	document.getElementById("queryCommonUserName").value="";
	        document.getElementById("commonUserOldValue").value="";
	        $("#commonUserSelected").empty();  
	        changeCommonUser();
			document.getElementById("intSelectedCommonUser").innerHTML="0";
        }
	}
	function sAlert(strTitle){ 
	    var msgw,msgh,bordercolor; 
	    msgw=450;//提示窗口的宽度 
	    msgh=230;//提示窗口的高度 
	    titleheight=25; //提示窗口标题高度 
	    bordercolor="#336699";//提示窗口的边框颜色 
	    titlecolor="#99CCFF";//提示窗口的标题颜色

	    var sWidth,sHeight; 
	    sWidth=document.body.offsetWidth; 
	    sHeight=screen.height; 
	    var bgObj=document.createElement("div"); 
	    bgObj.setAttribute('id','bgDiv'); 
	    bgObj.style.position="absolute"; 
	    bgObj.style.top="0"; 
	    bgObj.style.background="#777"; 
	    bgObj.style.filter="progid:DXImageTransform.Microsoft.Alpha(style=3,opacity=25,finishOpacity=75"; 
	    bgObj.style.opacity="0.6"; 
	    bgObj.style.left="0"; 
	    bgObj.style.width=sWidth + "px"; 
	    bgObj.style.height=sHeight + "px"; 
	    bgObj.style.zIndex = "10000"; 
	    document.body.appendChild(bgObj);

	    var msgObj=document.createElement("div");
	    msgObj.setAttribute("id","msgDiv"); 
	    msgObj.setAttribute("align","center"); 
	    msgObj.style.background="white"; 
	    msgObj.style.border="1px solid " + bordercolor; 
	    msgObj.style.position = "absolute"; 
	    msgObj.style.left = "50%"; 
	    msgObj.style.top = "50%"; 
	    msgObj.style.font="12px/1.6em Verdana, Geneva, Arial, Helvetica, sans-serif"; 
	    msgObj.style.marginLeft = "-225px" ; 
	    msgObj.style.marginTop = -75+document.documentElement.scrollTop+"px"; 
	    msgObj.style.width = msgw + "px"; 
	    msgObj.style.height = msgh + "px"; 
	    msgObj.style.textAlign = "center"; 
	    msgObj.style.lineHeight ="25px"; 
	    msgObj.style.zIndex = "10001";

	    var title=document.createElement("h4"); 
	    title.setAttribute("id","msgTitle"); 
	    title.setAttribute("align","right"); 
	    title.style.margin="0"; 
	    title.style.padding="0px"; 
	    title.style.background=bordercolor; 
	    title.style.filter="progid:DXImageTransform.Microsoft.Alpha(startX=20, startY=20, finishX=100, finishY=100,style=1,opacity=75,finishOpacity=100);"; 
	    title.style.opacity="0.75"; 
	    title.style.border="1px solid " + bordercolor; 
	    title.style.height="28px"; 
	    title.style.width="100%";
	    title.style.font="12px Verdana, Geneva, Arial, Helvetica, sans-serif"; 
	    title.style.color="white"; 
	    title.style.cursor="pointer"; 
	    title.title ="<comm:message key='unified.close'/>"; 
	    title.close="<comm:message key='unified.window.close'/>"; 
	    title.innerHTML="<table border='0′ align='center' width='100%' ><tr><td align='left' width='12%'>"+' '+"</td><td align='left' width='69%'>"+strTitle+"</td><td width='9%' >"+title.close+"</td></tr></table>";
	    title.onclick = function(){
		    document.body.removeChild(bgObj);
		    document.getElementById("msgDiv").removeChild(title);
		    document.body.removeChild(msgObj);
	    };
	    document.body.appendChild(msgObj); 
	    document.getElementById("msgDiv").appendChild(title); 
	    var txt=document.createElement("p"); 
	    txt.style.margin="0em 0";
	    //txt.style.border="1px solid red";
	    txt.setAttribute("id","msgTxt"); 
	    txt.innerHTML="<iframe style='width:100%;height:200px;'  src='<%=request.getContextPath()%>/resources/fileupload.jsp' scrolling='no'></iframe>"; 
	    document.getElementById("msgDiv").appendChild(txt); 
	}

	function  delfile(liid,fileid){
		jConfirm("<comm:message key='js.confirm_delete_accessory'/>\n\n<comm:message key='admin.click_ok'/>", "<comm:message key='meeting.m.infotishi'/>", function(select){
			if(select){
				var filelist=document.getElementById("filelist");
			    var filevalue=document.getElementById("filevalue");
				var delobj=document.getElementById(fileid);
				var testobj=document.getElementById(liid);	
				testobj.parentNode.removeChild(testobj);
				filevalue.removeChild(delobj);
			}
			return false;	
		});
	}

	function deleteTextareaScroll(){
 		//document.getElementById('textarea').scrollTop = document.getElementById('textarea').scrollHeight;
		 var textareaRows = document.getElementById("lectureContent").rows;
		 if(textareaRows >= 5){
			 document.getElementById("lectureContent").style.overflowY = 'hidden';
		 }else{
			 document.getElementById("lectureContent").style.overflowY = 'auto'; 
		 }
	};
	
</script>
<style>
	#center{background-color:#ffffff;}
	#titleStyle{color:#3c3645;font-size:18px;font-weight: bold;height:30px;line-height:30px;margin-top:15px;margin-bottom:0px;margin-left:40px;margin-right:40px;}
	#viewappinfor{margin-left:25px;margin-top:0px;margin-bottom:5px;margin-right:25px;position:relative;background-color:#dee; overflow-y:auto;}
	.subinfor{border:0px solid #7F9DB9;background-color:#F8F8FF;margin-left:40px;margin-right:40px;}
	select{background: #ffffff;border: 1px solid #7F9DB9;width:100px;color:#000000;height:22px;}
	.main1_sub{border:0px solid #000000;background-color:#F8F8FF;}
	._table{border-collapse:collapse; border:solid #D2D2D2; border-width:1px 0 0 1px;margin:auto; }
	._table caption {font-size:18px; font-weight:bolder;}
	._table tr td input{width:151px;}
	._table td{height:25px; line-height:25px;border:1px solid #D2D2D2;}
	a:hover{text-decoration:underline;}
	.submit_table1{ border-collapse:collapse; border:solid #D2D2D2; border-width:1px 0 0 1px;width:98%;}
	.submit_table1 caption {font-size:18px; font-weight:bolder;}
	.submit_table1 td{ height:30px; line-height:30px;}
	.submit_table1 th,.submit_table td {border:solid #D2D2D2; border-width:0 1px 1px 0;}
	#rightdown{background-image: url("../resources/images/table/down.gif");background-repeat: repeat-x;width:19px;height:28px;margin-bottom:2px;margin-left:15px;margin-right:15px;margin-top:8px;}
	#leftup{background-image: url("../resources/images/table/up.gif");background-repeat: repeat-x;width:19px;height:28px;margin-bottom:2px;margin-left:15px;margin-right:15px;margin-top:20px;}
</style>
</head>
<body style="padding:0; margin:0;background:url('../resources/images/theme/bg_right-2.gif');" class="bg" onload="myAlert();heigthReset(64);" onresize="heigthReset(64);">
	<div id="center">
		<comm:navigator position="application.vedio>>application.vedio" />
		  <div id="main" style=" overflow-y:auto;overflow-x:hidden;width:725px;margin:0px;float:left;">
			<form method="post" action="<%=request.getContextPath()%>/meeadmdbd/addVideoApplicationPurpose" onsubmit="return check()">
			  <input type="hidden" id="innaiUserOldValue" value=""/>
		      <input type="hidden" id="commonUserOldValue" value=""/>
		      <input type="hidden" id="timeRepeatFlag" value="timeIsRepeat"/>
		      <input type="hidden" id="selectedUserIds" name="selectedUserIds" value=""/>
		      
			  <div id="viewappinfor">
				<div id="titleStyle">
					<span><comm:message key='unified.video.application.manage' /></span>
				</div>		
	            <div class="subinfor" id="patientinfor1">
					<table id="shenqing" width="100%" class="shenqing_table" border="0">						
						<tr>
							<td width="15%"  align="right"><comm:message key='unified.lecture.content' />:<span class="required">*</span><div id="hint" style="margin-right: 10px;">(200/200)</div></td>
							<td colspan="3">
							     <textarea name="lectureContent" style="border: 1px solid #7F9DB9;width:98%; height:30px; overflow-y: auto;" id="lectureContent" class="text-long" maxlength="200" onpaste="return realTimeCountClip(this,'hint');" onkeyup="javascript:realTimeCount(this,'hint');" title=" <comm:message key="comm.content_maxlength"/>200"></textarea>
							</td>
						</tr>
						<tr>
							<td width="15%"  align="right"><comm:message key='meeting.teachingObject' />:&nbsp;<div id="hint1" style="margin-right: 10px;">(500/500)</div></td>
							<td colspan="3"><textarea name="teachingObject" style="border: 1px solid #7F9DB9;width:98%; height:50px; overflow-y: auto;" id="teachingObject" class="text-long" maxlength="500" onpaste="return realTimeCountClip(this,'hint1');" onkeyup="javascript:realTimeCount(this,'hint1');" title=" <comm:message key="comm.content_maxlength"/>500"></textarea>
							</td>
						</tr>						
						<tr>
							<td width="15%"  align="right"><comm:message key='meeting.m.starttime' />:<span class="required">*</span></td>
							<td width="30%" align="left">
								<input type="text" name="startTime" id="startTime" class="Wdate"  onclick="var endTime=$dp.$('endTime');WdatePicker({readOnly:true,onpicked:setEndTime,dateFmt:'yyyy-M-d H:mm',minDate:'%y-%M-%d %H:%m:%s'})" onfocus="this.blur()" style="border:#999 1px solid;height:20px;background:#fff url(<%=request.getContextPath()%>/resources/My97DatePicker/skin/datePicker.gif) no-repeat right;width:126px;" readonly="readonly" />
							</td>
							<td width="30%"  align="right">			
		                      <comm:message key='meeting.m.endtime' />:<span class="required">*</span> 
		                    </td>			
							<td>
								<input type="text" name="endTime" id="endTime" class="Wdate"  onclick="WdatePicker({readOnly:true,dateFmt:'yyyy-M-d H:mm'})" onfocus="this.blur()" style="border:#999 1px solid;height:20px;background:#fff url(<%=request.getContextPath()%>/resources/My97DatePicker/skin/datePicker.gif) no-repeat right;width:126px;" readonly="readonly" />								
							</td>
						</tr>
						<tr>
						   <td style="display:none;" width="15%"  align="right"><comm:message key='lecture.m.lecturelevel' />:<span class="required">*</span></td>
		                   <td style="display:none;" width="30%" align="left">
								<select name="level.id" id="meetingleveld" style="width:130px;margin-left:0px;">
										<c:forEach var="meetinglevel" items="${basecode['meetinglevel']}">
											<option value="${meetinglevel.id}">${meetinglevel.name}</option>
										</c:forEach>
								</select>						                   
		                   </td>
		                   <td width="15%"  align="right">			
		                      <comm:message key='view.video.meetingroom' />:<span class="required">*</span> 
		                   </td>
						   <td colspan="3">
						     	<select name="meetingRoom" id="meetingRoom" style="width:130px;margin-left:0px;">
									<c:forEach var="meetingRoom" items="${basecode['meetingroom']}">
										<option value="${meetingRoom.id}">${meetingRoom.name}</option>
									</c:forEach>
								</select>
						   </td>	
						</tr>
						<tr >
				            <td  align="right" style="vertical-align:middle;"><comm:message key='meeting.lecture.people.name'/>:<span class="required">*</span> </td>		
						    <td colspan="3" style="vertical-align:middle;">
						    	<table style="border:1px solid #000;margin-bottom:10px;margin-top:10px;margin-right:20px;"class="submit_table1" >
									<tr>
										<td bgcolor="#D0D0D0" style="height:22px;border-right:1px solid #000;border-left:1px solid #000;"" >
											 &nbsp;<strong>速查条件</strong><input id="queryInnaiUserName" style="align:right;line-height:18px;height:18px;" value="" size="25" />
										</td>
										<td style="border-bottom:0px;border-right:1px solid #000;">
										&nbsp;
										</td>
										<td bgcolor="#D0D0D0"  style="height:22px;border-right:1px solid #000;">
											&nbsp;<strong>已选择<span id="intSelectedInnaiUser">0</span>项</strong>
										</td>
									</tr>
									<tr>
										<td style="border-right:1px solid #000;border-bottom:1px solid #000;width:222px;">
											<select multiple="multiple" name="innaiUserSelect" id="innaiUserSelect"  style="align:center;width:222px;height:143px;border:none;">
											</select>	
										</td>
										
										<td align="center" style="border-right:1px solid #000;border-bottom:1px solid #000;">
											<div id="rightdown" onclick="rightmoveInnaiUser()" style="margin-top:30px;"></div>
											<div id="leftup" onclick="leftmoveInnaiUser()" style="margin-top:30px;margin-bottom:20px;"></div>
										</td>
										
										<td style="border-left:1px solid #000;border-right:1px solid #000;border-bottom:1px solid #000;">
											<select align="center" multiple="multiple" name="innaiUserSelected" id="innaiUserSelected" style="width:210px;height:143px;border:none;">
												<c:if test="${innaiRequestUser != null}">
													<option value="${innaiRequestUser.userId}">${innaiRequestUser.name}(${innaiRequestUser.deptId.name})</option>
												</c:if>
											</select>
										</td>
									</tr>
								</table>
						    </td>
						</tr>
						
						<tr >
				            <td align="right" style="vertical-align:middle;"><comm:message key='meeing.meeting.m.community'/>:<span class="required">*</span> </td>		
						    <td colspan="3" style="vertical-align:middle;">
						    	<table style="border:1px solid #000;margin-bottom:10px;margin-top:10px;margin-right:20px;"class="submit_table1" >
									<tr>
										<td bgcolor="#D0D0D0" style="height:22px;border-right:1px solid #000;border-left:1px solid #000;"" >
											 &nbsp;<strong>速查条件</strong><input id="queryCommonUserName" style="align:right;line-height:18px;height:18px;" value="" size="25" />
										</td>
										<td style="border-bottom:0px;border-right:1px solid #000;">
										&nbsp;
										</td>
										<td bgcolor="#D0D0D0"  style="height:22px;border-right:1px solid #000;">
											&nbsp;<strong>已选择<span id="intSelectedCommonUser">0</span>项</strong>
										</td>
									</tr>
									<tr>
										<td style="border-right:1px solid #000;border-bottom:1px solid #000;width:222px;">
											<select multiple="multiple" name="commonUserSelect" id="commonUserSelect"  style="align:center;width:222px;height:143px;border:none;">
											</select>	
										</td>
										
										<td align="center" style="border-right:1px solid #000;border-bottom:1px solid #000;">
											<div id="rightdown" onclick="rightmoveCommonUser()" style="margin-top:30px;"></div>
											<div id="leftup" onclick="leftmoveCommonUser()" style="margin-top:30px;margin-bottom:20px;"></div>
										</td>
										
										<td style="border-left:1px solid #000;border-right:1px solid #000;border-bottom:1px solid #000;">
											<select align="center" multiple="multiple" name="commonUserSelected" id="commonUserSelected" style="width:210px;height:143px;border:none;">
												<c:if test="${commonRequestUser != null}">
													<option value="${commonRequestUser.userId}">${commonRequestUser.name}(${commonRequestUser.deptId.name})</option>
												</c:if>
											</select>
										</td>
									</tr>
								</table>
						    </td>
						</tr>
								
						<tr>
							<td width="15%" align="right"><comm:message key='unified.and.expert_accessories' />:&nbsp;</td>
							<td colspan="3">
							<div class="main1_sub">
							        <table width="100%" border="0">
							           <tr>
							                <td width="50%">
							                	 <a href="#" onclick='sAlert("<comm:message key='unified.upload.accessories'/>")' style="COLOR:blue;margin-top:10px;">
													  <comm:message key='unified.select.accessories' />
												 </a>
							                </td>
							           </tr>
							           <tr>
							                <td valign="top" width="50%">
												<ul id="filelist"></ul>
													<table style="width:100%;" border="0" style="display: none">
														<tr>
															<td id="filevalue">&nbsp;</td>
														</tr>
													</table>                      
							               </td>
							           </tr>
							      </table>
							</div>
						  </td>
						</tr>
						<tr>
							<td width="15%"  align="right">
						        <comm:message key='unified.and.expert.meeting.requester' />:&nbsp;
							</td>
							<td align="left" colspan="3">
							      <table width="99%" style="margin-left:0px;" class="_table" cellspacing="0" cellpadding="0" border="0">
							             <tr>
							                <td align="right" width="15%"><comm:message key='group.name'/>:&nbsp;&nbsp;</td>
							                <td><comm:message key='meeting.type.inner'/></td>
							                <td align="right" width="15%"><comm:message key='unified.and.expert_username'/>:&nbsp;&nbsp;</td>
							                <td>${user.name}&nbsp;</td>
							             </tr>
							             <tr>
							                <td align="right" width="15%"><comm:message key='unified.and.expert_mobile'/>:&nbsp;&nbsp;</td>
							                <td>${user.mobile}&nbsp;</td>
							                <td align="right" width="15%"><comm:message key='admin.user_tel'/>:&nbsp;&nbsp;</td>
							                <td>${user.telephone}&nbsp;</td>
							             </tr>
							             <tr>
							                <td align="right" width="15%"><comm:message key='group.department'/>:&nbsp;&nbsp;</td>
							                <td>${user.deptId.name}&nbsp;</td>
							                <td align="right" width="15%"><comm:message key='unified.and.expert_email'/>:&nbsp;&nbsp;</td>
							                <c:if test="${fn:length(user.mail) <= 40}">
							                  <td>${user.mail}&nbsp;<input type="hidden" name="applicantid" id="applicantid"/></td>
							                </c:if>
							                <c:if test="${fn:length(user.mail) > 40}">
							                  <td title="${user.mail}">${fn:substring(user.mail, 0, 40)}...&nbsp;<input type="hidden" name="applicantid" id="applicantid"/></td>
							                </c:if>
							             </tr>
							     </table>		
							</td>
						</tr>											
						<tr>
							<td colspan="4" align="center">
								<input class="button" type="submit" name="button" id="button" value="<comm:message key='comm.save'/>" onclick="check()"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							 	<input	class="button" type="reset" name="button2" id="button2" onclick="return resetValue();" value="<comm:message key='unified.reset'/>" />
							</td>
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
	<div id="center_right">
		<img src="${pageContext.request.contextPath}/resources/images/theme/bg_right.gif" width="25"/>
	</div>	
	<script type="text/javascript">
		function exitsys()
		{
   			 var ask=confirm("<comm:message key='admin.exit_sure'/>\n\n<comm:message key='admin.click_ok'/>");
    		 if(ask)
				parent.location.href="${root}/login/exit";
		}
</script>
</body>

</html>