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
	.passTitleStyle{color:#3c3645;font-size:18px;font-weight: bold;height:30px;line-height:30px;margin-bottom:0px;margin-left:30px;margin-right:30px;}
	#viewappinfor3{border:0px solid red;width:95%;align:center;margin-left:18px;margin-top:15px;margin-bottom:5px;margin-right:18px;position:relative;background-color:#dee;}	
	</style>

<script type="text/javascript"  >
	function check()
	{     
		 var meetingType = document.getElementById("meetingType").value;
		 var startTime=document.getElementById("startTime").value;
		 if(meetingType != 4 && strTrim(startTime) == "")
		 {
			 jAlert("<comm:message key='meeting.m.starttimealert'/>", "<comm:message key='meeting.m.infotishi'/>");
			 return false;
		 }
		 var endTime=document.getElementById("endTime").value;
		 if(meetingType != 4 && strTrim(endTime) == "")
		 {
			 jAlert("<comm:message key='meeting.m.endtimealert'/>", "<comm:message key='meeting.m.infotishi'/>");
			 return false;
		 }

		 if (meetingType != 4 && compareCheckDate(startTime,endTime) != '-1'){
			 jAlert("<comm:message key='js.need_input_larger' args='meeting.m.endtime,meeting.m.starttime'/>", "<comm:message key='meeting.m.infotishi'/>");
			 return false;
		 }
		 var meetingRoom=document.getElementById("meetingRoom").value;
		 if(strTrim(meetingRoom) == "")
		 {
			 jAlert("<comm:message key='js.need_select' args='admin.meetingroom' />", "<comm:message key='meeting.m.infotishi'/>");
			 return false;
		 }
		 var  meetingType = $("#meetingType").val();
		 $.ajaxSettings.traditional = true;
		 $.ajax({url:'${pageContext.request.contextPath}/meeadmdbd/checkTimeRepeat',
			 	type: "POST",
			 	dataType : 'text',
			 	async:false,
			 	data: {startTime :startTime, endTime : endTime,meetingRoom : meetingRoom,exceptMeetingId:'',meetingTypestr:meetingType},
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
		 var intSelectedInterUser = $('#interUserSelected option').length;
		 var intSelectedCommonUser = $('#commonUserSelected option').length;
		 if(intSelectedInnaiUser==0&&intSelectedInterUser==0&&intSelectedCommonUser==0){
			 jAlert("<comm:message key='meeting.pleaseChoosePeople'/>", "<comm:message key='meeting.m.infotishi'/>");
			 return false;
		 }
		 $('#innaiUserSelected option').each(function(i, n){
			 $(n).attr('selected', 'selected');
		 });
		 $('#interUserSelected option').each(function(i, n){
			 $(n).attr('selected', 'selected');
		 });
		 $('#commonUserSelected option').each(function(i, n){
			 $(n).attr('selected', 'selected');
		 });
		 return true;
	}
	
	$(function() {
		$(document).ready(function() {
			$("#innaiUserSelect").dblclick(function(){
				rightmoveInnaiUser();
			});
			$("#innaiUserSelected").dblclick(function(){
				leftmoveInnaiUser();
			});
			$("#interUserSelect").dblclick(function(){
				rightmoveInterUser();
			});
			$("#interUserSelected").dblclick(function(){
				leftmoveInterUser();
			});
			$("#commonUserSelect").dblclick(function(){
				rightmoveCommonUser();
			});
			$("#commonUserSelected").dblclick(function(){
				leftmoveCommonUser();
			});
			$("#passbutton").click( function() {
				passsubmit();
			});
			//院内
			changeInnaiUser();
			var intSelectedInnaiUser = $('#innaiUserSelected option').length;
	        document.getElementById("intSelectedInnaiUser").innerHTML=intSelectedInnaiUser;
	        
	      	//院际
			changeInterUser();
			var intSelectedInterUser = $('#interUserSelected option').length;
	        document.getElementById("intSelectedInterUser").innerHTML=intSelectedInterUser;
	        
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
				
				//比对院际专家的速查条件是否有变化
				var interUserOldValue = document.getElementById("interUserOldValue").value;
				var queryInterUserName=$('#queryInterUserName').val();
				if(interUserOldValue!=queryInterUserName){
					changeInterUser();
				}
				document.getElementById("interUserOldValue").value=queryInterUserName;
				
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
	//院际
	function changeInterUser(){
		//模糊查询条件
		var queryInterUserName=$('#queryInterUserName').val();
		//清空左边选单
		$("#interUserSelect").empty(); 
		//获取被选择的院际专家
		var interUserSelecteds=$('#interUserSelected option');
		//迭代每一个专家，将所有专家用“|”区分，组合成字符串
		var interUserIds = "";
		$.each(interUserSelecteds,function(key,val){
			interUserIds = interUserIds + interUserSelecteds[key].value+"|";
		});
		$.ajaxSettings.traditional = true;
	    $.ajax({type :'POST',  url : '${pageContext.request.contextPath}/meeadmdbd/getUserSelectItem', 
				data : {queryUserName:queryInterUserName,userIds:interUserIds,departmentKind:'${OUT_DEPT_CODE}'},
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
						$("<option value="+json[i].id+ ">"+json[i].name+"</option>").appendTo("#interUserSelect");
					});
			}
		});
	}
	//双击添加院际
	function rightmoveInterUser(){
		var so = $("#interUserSelect option:selected");
        $("#interUserSelected").append(so);
        var intSelectedInterUser = $('#interUserSelected option').length;
        document.getElementById("intSelectedInterUser").innerHTML=intSelectedInterUser;
	}
	
	//双击删除院际
	function leftmoveInterUser(){
		var so = $("#interUserSelected option:selected");
        $("#interUserSelect").append(so);
        var intSelectedInterUser = $('#interUserSelected option').length;
        document.getElementById("intSelectedInterUser").innerHTML=intSelectedInterUser;
        sortOptions($('#interUserSelect option'),$("#interUserSelect"));
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
	
	function passsubmit(){
		//得到当前审核会议的会议类型
		if(check()){
			var meetingType= $("#meetingType").val();
			var meetingApplicationId=$('#meetingApplicationId').val();
			var starttime=$('#startTime').val();//开始时间 
			var endtime=$('#endTime').val();//结束时间
			var meetingRoom=document.getElementById("meetingRoom").value;
			var selectedUserIds = "";
			
			//获取被选择的专家
			var innaiUserSelecteds=$('#innaiUserSelected option');
			//迭代每一个专家，将所有专家用“|”区分，组合成字符串
			$.each(innaiUserSelecteds,function(key,val){
				selectedUserIds = selectedUserIds + innaiUserSelecteds[key].value+"|";
			});
			
			//获取被选择的院际专家
			var interUserSelecteds=$('#interUserSelected option');
			//迭代每一个专家，将所有专家用“|”区分，组合成字符串
			$.each(interUserSelecteds,function(key,val){
				selectedUserIds = selectedUserIds + interUserSelecteds[key].value+"|";
			});
			
			//获取被选择的共同体专家
			var commonUserSelecteds=$('#commonUserSelected option');
			//迭代每一个共同体，将所有专家用“|”区分，组合成字符串
			$.each(commonUserSelecteds,function(key,val){
				selectedUserIds = selectedUserIds + commonUserSelecteds[key].value+"|";
			});
			$.ajaxSettings.traditional = true;
			$.ajax({type :'POST',  url : '${pageContext.request.contextPath}/meeadmdbd/auditCaseDiscussion', 
				data :  {meetingApplicationId:meetingApplicationId,startTime:starttime,endTime:endtime,selectedUserIds:selectedUserIds,meetingRoom:meetingRoom,meetingType:meetingType},
				dataType : 'json',
				dataFilter : function(json, type){
					if(json.indexOf('<html>') != -1){
						window.location.href="<%=request.getContextPath()%>/index.jsp?message=system.session_expire";
						return;
					}
					return json;
				},
				success : function(json) {
				if(json.flag){
					jAlert("<comm:message key='meeting.m.submitsucc'/>", "<comm:message key='meeting.m.infotishi'/>", function(r) {
						url="<%=request.getContextPath()%>/meeadIndex/teleconferenceList";
						parent.document.getElementById("WorkBench").contentWindow.document.getElementById("dataFrame").contentWindow.location.href = url;
				    	parent.$.fancybox.close();
					});
				}else{
					jAlert("<comm:message key='meeting.m.submitfalse'/>", "<comm:message key='meeting.m.infotishi'/>");
				}
			}});
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
</script>

</head>
<body id="windows" style="overflow-y:auto;">
  <div id="usual1" class="usual">   
    <ul id="tabHeight">    
        <li><a href="#tab1" class="selected"><comm:message key='meeting.pass'/></a></li>
        <%-- <li><a href="#tab2"><comm:message key='meeting.arranged'/></a></li> --%> 
    </ul>
   <div id="tab1"> 
    <form id="formtongguo" name="formtongguo" method="post" action="<%=request.getContextPath()%>/meeadmdbd/auditCaseDiscussion" onsubmit="">
    <input type="hidden" name="meetingApplicationId" id="meetingApplicationId" value="${requestmeetid_add}"/>
     <input type="hidden" name="meetingappserchflag" id="meetingappserchflag" value="${meetingappserchflag}"/>
     <input type="hidden" id="innaiUserOldValue" value=""/>
     <input type="hidden" id="interUserOldValue" value=""/>
     <input type="hidden" id="commonUserOldValue" value=""/>
     <input type="hidden" id="timeRepeatFlag" value="timeIsRepeat"/>
     <input type="hidden" id="meetingType" value="${mApplication.meetingType.id}"/>
	     <div id="viewappinfor">
			<div id="titleStyle">
				<span>&nbsp;</span>
			</div>		
            <div class="subinfor" id="patientinfor1">
				  <table class="submit_table" align="center" width="100%" border="1" >
					<tr>
						<td align="right" style="vertical-align:middle;">&nbsp;&nbsp;<strong style="white-space:nowrap;"><comm:message key='meeting.m.starttime'/></strong>:&nbsp;<font color="red">*</font>&nbsp;</td>
						<td style="vertical-align:middle;">
							<input type="text" name="startTime" style="margin-left:20px;" id="startTime" class="Wdate" onclick="var endTime=$dp.$('endTime');WdatePicker({readOnly:true,onpicked:setEndTime,dateFmt:'yyyy-M-d H:mm',minDate:'%y-%M-%d %H:%m:%s'})" onfocus="this.blur()" size="25"/>
						</td>
										
						<td align="right" style="vertical-align:middle;">&nbsp;&nbsp;<strong style="white-space:nowrap;"><comm:message key='meeting.m.endtime'/></strong>:&nbsp;<font color="red">*</font>&nbsp;</td>
						<td style="vertical-align:middle;">
							<input type="text" name="endTime" style="margin-left:20px;" id="endTime" class="Wdate" onclick="WdatePicker({readOnly:true,dateFmt:'yyyy-M-d H:mm'})" onfocus="this.blur()" size="25"/>								
						</td>
						
					</tr>
					<tr >
						<td  align="right" style="vertical-align:middle;">&nbsp;&nbsp;<strong style="white-space:nowrap;"><comm:message key='meeing.admin.meetingroom'/></strong>:&nbsp;<font color="red">*</font>&nbsp;</td>		
					    <td colspan="3" style="vertical-align:middle;">
					     <c:if test="${mApplication.meetingType.id == 4 }">
					     <select name="meetingRoom" id="meetingRoom" style="width:160px;height:22px;margin-left:20px;">
					        <option value="1" selected>离线会议</option>
					     </select>
					     </c:if> 
					     <c:if test="${mApplication.meetingType.id != 4 }">
					    	<select name="meetingRoom" id="meetingRoom" style="width:160px;height:22px;margin-left:20px;">
								<c:forEach var="meetingRoom" items="${basecode['meetingroom']}">
									<option value="${meetingRoom.id}">${meetingRoom.name}</option>
								</c:forEach>
							</select>
						 </c:if>
					    </td>
					</tr>
					<tr >
			            <td  align="right" style="vertical-align:middle;"><strong><comm:message key='meeting.m.attend.departexperts'/></strong>:&nbsp;&nbsp;&nbsp;</td>		
					    <td colspan="3" style="vertical-align:middle;">
					    	<table style="border:1px solid #000;margin-left:20px;margin-right:20px;margin-bottom:10px;margin-top:10px;">
								<tr>
									<td bgcolor="#D0D0D0" style="height:22px;border-right:1px solid #000;border-left:1px solid #000;"" >
										 &nbsp;<strong>速查条件</strong><input id="queryInnaiUserName" style="align:right;line-height:18px;height:18px;" value="" size="30" />
									</td>
									<td style="border-bottom:0px;border-right:1px solid #000;">
									&nbsp;
									</td>
									<td bgcolor="#D0D0D0"  style="height:22px;border-right:1px solid #000;">
										&nbsp;<strong>已选择<span id="intSelectedInnaiUser">0</span>项</strong>
									</td>
								</tr>
								<tr>
									<td style="border-right:1px solid #000;border-bottom:1px solid #000;">
										<select multiple="multiple" name="innaiUserSelect" id="innaiUserSelect"  style="align:center;width:260px;height:143px;border:none;">
										</select>	
									</td>
									
									<td align="center" style="border-right:1px solid #000;border-bottom:1px solid #000;">
										<div id="rightdown" onclick="rightmoveInnaiUser()" style="margin-top:30px;"></div>
										<div id="leftup" onclick="leftmoveInnaiUser()" style="margin-top:30px;margin-bottom:20px;"></div>
									</td>
									
									<td style="border-left:1px solid #000;border-right:1px solid #000;border-bottom:1px solid #000;">
										<select align="center" multiple="multiple" name="innaiUserSelected" id="innaiUserSelected" style="width:260px;height:143px;border:none;">
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
			            <td align="right" style="vertical-align:middle;"><strong><comm:message key='meeing.meeting.m.community'/></strong>:&nbsp;&nbsp;&nbsp;</td>		
					    <td colspan="3" style="vertical-align:middle;">
					    	<table style="border:1px solid #000;margin-left:20px;margin-right:20px;margin-bottom:10px;margin-top:10px;">
								<tr>
									<td bgcolor="#D0D0D0" style="height:22px;border-right:1px solid #000;border-left:1px solid #000;"" >
										 &nbsp;<strong>速查条件</strong><input id="queryCommonUserName" style="align:right;line-height:18px;height:18px;" value="" size="30" />
									</td>
									<td style="border-bottom:0px;border-right:1px solid #000;">
									&nbsp;
									</td>
									<td bgcolor="#D0D0D0"  style="height:22px;border-right:1px solid #000;">
										&nbsp;<strong>已选择<span id="intSelectedCommonUser">0</span>项</strong>
									</td>
								</tr>
								<tr>
									<td style="border-right:1px solid #000;border-bottom:1px solid #000;">
										<select multiple="multiple" name="commonUserSelect" id="commonUserSelect"  style="align:center;width:260px;height:143px;border:none;">
										</select>	
									</td>
									
									<td align="center" style="border-right:1px solid #000;border-bottom:1px solid #000;">
										<div id="rightdown" onclick="rightmoveCommonUser()" style="margin-top:30px;"></div>
										<div id="leftup" onclick="leftmoveCommonUser()" style="margin-top:30px;margin-bottom:20px;"></div>
									</td>
									
									<td style="border-left:1px solid #000;border-right:1px solid #000;border-bottom:1px solid #000;">
										<select align="center" multiple="multiple" name="commonUserSelected" id="commonUserSelected" style="width:260px;height:143px;border:none;">
											<c:if test="${commonRequestUser != null}">
												<option value="${commonRequestUser.userId}">${commonRequestUser.name}(${commonRequestUser.deptId.name})</option>
											</c:if>
										</select>
									</td>
								</tr>
							</table>
					    </td>
					</tr>
					<tr >
			            <td align="right" style="vertical-align:middle;"><strong><comm:message key='meeting.type.outter.infor'/></strong>:&nbsp;&nbsp;&nbsp;</td>		
					    <td colspan="3" style="vertical-align:middle;">
					    	<table style="border:1px solid #000;margin-left:20px;margin-right:20px;margin-bottom:10px;margin-top:10px;">
								<tr>
									<td bgcolor="#D0D0D0" style="height:22px;border-right:1px solid #000;border-left:1px solid #000;"" >
										 &nbsp;<strong>速查条件</strong><input id="queryInterUserName" style="align:right;line-height:18px;height:18px;" value="" size="30" />
									</td>
									<td style="border-bottom:0px;border-right:1px solid #000;">
									&nbsp;
									</td>
									<td bgcolor="#D0D0D0"  style="height:22px;border-right:1px solid #000;">
										&nbsp;<strong>已选择<span id="intSelectedInterUser">0</span>项</strong>
									</td>
								</tr>
								<tr>
									<td style="border-right:1px solid #000;border-bottom:1px solid #000;">
										<select multiple="multiple" name="interUserSelect" id="interUserSelect"  style="align:center;width:260px;height:143px;border:none;">
										</select>	
									</td>
									
									<td align="center" style="border-right:1px solid #000;border-bottom:1px solid #000;">
										<div id="rightdown" onclick="rightmoveInterUser()" style="margin-top:30px;"></div>
										<div id="leftup" onclick="leftmoveInterUser()" style="margin-top:30px;margin-bottom:20px;"></div>
									</td>
									
									<td style="border-left:1px solid #000;border-right:1px solid #000;border-bottom:1px solid #000;">
										<select align="center" multiple="multiple" name="interUserSelected" id="interUserSelected" style="width:260px;height:143px;border:none;"></select>
									</td>
								</tr>
							</table>
					    </td>
					</tr>
				</table>
				<table border="0" align="center" width="90%">
					<tr>
						<td colspan="4" align="center" height="30"><input class="button" type="button"  name="passbutton" id="passbutton" value="<comm:message key='meeting.m.commit'/>" /></td>
			         </tr>
				</table>            
            </div>
			<div id="titleStyle">
				<span>&nbsp;</span>
			</div>
        </div>   
	</form>
	</div>
    <%-- <div id="tab2">
     <div id="viewappinfor">
		<div id="titleStyle">
			<span>&nbsp;</span>
		</div>		
        <div class="subinfor" id="patientinfor1">
		   <table id="shenqing" class="submit_table" align="center" width="100%" border="1">
		      <tr>
		         <td align="center"><strong><comm:message key='comm.start_time'/></strong></td> 
		         <td align="center"><strong><comm:message key='comm.end_time'/></strong></td>
		         <td align="center"><strong><comm:message key='comm.start_time'/></strong></td> 
		         <td align="center"><strong><comm:message key='comm.end_time'/></strong></td> 
		      </tr>
		      <c:forEach var="meetingpass" items="${meetings}" varStatus="status">	      
		        <c:choose>
		             <c:when test="${status.count == 1 && status.count % 2 == 1}">
                        <tr>
                           <td align="center">${fn:substring(meetingpass.startTime, 0, 16)}</td>
                           <td align="center">${fn:substring(meetingpass.endTime, 0, 16)}</td>		             
		             </c:when>
		             <c:when test="${status.count % 2 == 0}">
                           <td align="center">${fn:substring(meetingpass.startTime, 0, 16)}</td>
                           <td align="center">${fn:substring(meetingpass.endTime, 0, 16)}</td>
                        </tr>   
		             </c:when>
		             <c:otherwise>
                           <td align="center">${fn:substring(meetingpass.startTime, 0, 16)}</td>
                           <td align="center">${fn:substring(meetingpass.endTime, 0, 16)}</td>
		             </c:otherwise>
		        </c:choose>  
		      </c:forEach>
		   </table>      
        </div>
		<div id="titleStyle">
			<span>&nbsp;</span>
		</div>
	</div>	   
   </div> --%>
  </div>
<script type="text/javascript">    
    $("#usual1 ul").idTabs();    
</script>
</body>
</html>
