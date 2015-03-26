<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page pageEncoding="utf-8"%>
<%@include file="/common.jsp"%>
<html>
   <head>
    <comm:pageHeader hasEcList="false"></comm:pageHeader>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/resources/skin/style/main.css"/>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery.js"></script>
	<script src="<%=request.getContextPath()%>/resources/jqueryalerts/jquery.ui.draggable.js" type="text/javascript"></script>
	<script src="<%=request.getContextPath()%>/resources/jqueryalerts/jquery.alerts.js" type="text/javascript"></script>
	<link href="<%=request.getContextPath()%>/resources/jqueryalerts/jquery.alerts.css" rel="stylesheet" type="text/css" media="screen" />
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/validate.js"></script>	
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/resources/css/style.css"/>
<script type="text/javascript">
function noticemit(){
	var intSelectedInnaiUser = $('#innaiUserSelected option').length;
	var intSelectedInterUser = $('#interUserSelected option').length;
	var intSelectedCommonUser = $('#commonUserSelected option').length;
	if(intSelectedInnaiUser!=null&&intSelectedInnaiUser>0){
		var expertsNotice=document.getElementById("expertsNotice").value;
		if(strTrim(expertsNotice) == "")
		 {
			jAlert("<comm:message key='meeting.expertsNoticeNotNull'/>", "<comm:message key='meeting.m.infotishi'/>");
			return false;
		 }
	}
	
	if(intSelectedCommonUser!=null&&intSelectedCommonUser>0){
		var communityNotice=document.getElementById("communityNotice").value;
		if(strTrim(communityNotice) == "")
		 {
			jAlert("<comm:message key='meeting.communityNoticeNotNull'/>", "<comm:message key='meeting.m.infotishi'/>");
			return false;
		 }
	}
	
	if(intSelectedInterUser!=null&&intSelectedInterUser>0){
		var internationalCourtNotice=document.getElementById("internationalCourtNotice").value;
		if(strTrim(internationalCourtNotice) == "")
		 {
			jAlert("<comm:message key='meeting.internationalCourtNoticeNotNull'/>", "<comm:message key='meeting.m.infotishi'/>");
			return false;
		 }
	}
	
	if(intSelectedInnaiUser==0&&intSelectedInterUser==0&&intSelectedCommonUser==0){
		 jAlert("<comm:message key='meeting.selectNoticeUsers'/>", "<comm:message key='meeting.m.infotishi'/>");
		 return false;
	}
	
	jConfirm("<comm:message key='meeting.sendNoticeSubmit'/>\n\n<comm:message key='admin.click_ok'/>", "<comm:message key='meeting.m.infotishi'/>",function(resultConfirm){
		if(resultConfirm){						
			var selectedInnaiUserIds = "";
			var selectedInterUserIds = "";
			var selectedcommunityUserIds = "";
			
			if(intSelectedInnaiUser!=null&&intSelectedInnaiUser>0){
				//获取被选择的专家
				var innaiUserSelecteds=$('#innaiUserSelected option');
				//迭代每一个专家，将所有专家用“|”区分，组合成字符串
				$.each(innaiUserSelecteds,function(key,val){
					selectedInnaiUserIds = selectedInnaiUserIds + innaiUserSelecteds[key].value+"|";
				});
			}
			
			if(intSelectedInterUser!=null&&intSelectedInterUser>0){
				//获取被选择的院际专家
				var interUserSelecteds=$('#interUserSelected option');
				//迭代每一个专家，将所有专家用“|”区分，组合成字符串
				$.each(interUserSelecteds,function(key,val){
					selectedInterUserIds = selectedInterUserIds + interUserSelecteds[key].value+"|";
				});
			}
			
			if(intSelectedCommonUser!=null&&intSelectedCommonUser>0){
				//获取被选择的共同体专家
				var commonUserSelecteds=$('#commonUserSelected option');
				//迭代每一个共同体，将所有专家用“|”区分，组合成字符串
				$.each(commonUserSelecteds,function(key,val){
					selectedcommunityUserIds = selectedcommunityUserIds + commonUserSelecteds[key].value+"|";
				});
			}
			
		     $.post('<%=request.getContextPath()%>/meeadmdbd/teleconferenceNotice',
		    		 {meetingApplicationId: $('#meetingApplicationId').val(),expertsNotice:$('#expertsNotice').val(),internationalCourtNotice:$('#internationalCourtNotice').val(),communityNotice:$('#communityNotice').val(),
		    	 		selectedInnaiUserIds:selectedInnaiUserIds,selectedInterUserIds:selectedInterUserIds,selectedcommunityUserIds:selectedcommunityUserIds},
			  function(text){	 
				  if(text == 'success'){
				    	parent.document.getElementById("WorkBench").contentWindow.document.getElementById("dataFrame").contentWindow.location.href = parent.document.getElementById("WorkBench").contentWindow.document.getElementById("dataFrame").contentWindow.location.href;
				    	parent.$.fancybox.close();
				  } else if(text.indexOf('<HTML>') != -1){
					window.location.href="<%=request.getContextPath()%>/index.jsp?message=system.session_expire";
				  }
		     }, 'text');
	 	} 
 	});
};

	$(document).ready(function() {
		var intSelectedInnaiUser = $('#innaiUserSelected option').length;
		var intSelectedInterUser = $('#interUserSelected option').length;
		var intSelectedCommonUser = $('#commonUserSelected option').length;
		
		if(intSelectedInnaiUser!=null){
			if(intSelectedInnaiUser>0){
				$("#innaiUserSelect").dblclick(function(){
					rightmoveInnaiUser();
				});
				$("#innaiUserSelected").dblclick(function(){
					leftmoveInnaiUser();
				});
				//院内
				//changeInnaiUser();
		        document.getElementById("intSelectedInnaiUser").innerHTML=intSelectedInnaiUser;
			}			
	        if("${expertsList}"!=""){
	        	var countExportNotice = getUnicodeLength(document.getElementById("expertsNotice").value);
			    $('#tip1').html(500-countExportNotice);
	        }
			
		}
		
		if(intSelectedInterUser!=null){
			if(intSelectedInterUser>0){
				$("#interUserSelect").dblclick(function(){
					rightmoveInterUser();
				});
				$("#interUserSelected").dblclick(function(){
					leftmoveInterUser();
				});
				//院际
				//changeInterUser();
		        document.getElementById("intSelectedInterUser").innerHTML=intSelectedInterUser;
			}			
	        if("${interList}"!=""){
	        	var countInternationalCourtNotice = getUnicodeLength(document.getElementById("internationalCourtNotice").value);
			   	$('#tip2').html(500-countInternationalCourtNotice);
	        }
		}
		
		if(intSelectedCommonUser!=null){
			if(intSelectedCommonUser>0){
				$("#commonUserSelect").dblclick(function(){
					rightmoveCommonUser();
				});
				$("#commonUserSelected").dblclick(function(){
					leftmoveCommonUser();
				});
				//共同体
				//changeCommonUser();
		        document.getElementById("intSelectedCommonUser").innerHTML=intSelectedCommonUser;
			}
			if("${communityList}"!=""){
				var countCommunityNotice = getUnicodeLength(document.getElementById("communityNotice").value);
			   	$('#tip3').html(500-countCommunityNotice);
			}
	        
		}
		
		/*setInterval(function() {
			if(intSelectedInnaiUser!=null&&intSelectedInnaiUser>0){
				//比对院内专家的速查条件是否有变化
				var innaiUserOldValue = document.getElementById("innaiUserOldValue").value;
				var queryInnaiUserName=$('#queryInnaiUserName').val();
				if(innaiUserOldValue!=queryInnaiUserName){
					changeInnaiUser();
				}
				document.getElementById("innaiUserOldValue").value=queryInnaiUserName;
			}
			
			if(intSelectedInterUser!=null&&intSelectedInterUser>0){
				//比对院际专家的速查条件是否有变化
				var interUserOldValue = document.getElementById("interUserOldValue").value;
				var queryInterUserName=$('#queryInterUserName').val();
				if(interUserOldValue!=queryInterUserName){
					changeInterUser();
				}
				document.getElementById("interUserOldValue").value=queryInterUserName;
			}
			
			if(intSelectedCommonUser!=null&&intSelectedCommonUser>0){
				//比对共同体专家的速查条件是否有变化
				var commonUserOldValue = document.getElementById("commonUserOldValue").value;
				var queryCommonUserName=$('#queryCommonUserName').val();
				if(commonUserOldValue!=queryCommonUserName){
					changeCommonUser();
				}
				document.getElementById("commonUserOldValue").value=queryCommonUserName;
			}
			
		}, 3000);*/
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
        var intNotSelectedInnaiUser = $('#innaiUserSelect option').length;
        document.getElementById("intNotSelectedInnaiUser").innerHTML=intNotSelectedInnaiUser;
        //var queryInnaiUserName=$('#queryInnaiUserName').val();
        //if(queryInnaiUserName==null||queryInnaiUserName.length==0){
        //	changeInnaiUser();
        //}
	}
	
	//双击删除院内专家
	function leftmoveInnaiUser(){
		var so = $("#innaiUserSelected option:selected");
        $("#innaiUserSelect").append(so);
        var intSelectedInnaiUser = $('#innaiUserSelected option').length;
        document.getElementById("intSelectedInnaiUser").innerHTML=intSelectedInnaiUser;
        var intNotSelectedInnaiUser = $('#innaiUserSelect option').length;
        document.getElementById("intNotSelectedInnaiUser").innerHTML=intNotSelectedInnaiUser;
        
       // var queryInnaiUserName=$('#queryInnaiUserName').val();
        //if(queryInnaiUserName==null||queryInnaiUserName.length==0){
        //	changeInnaiUser();
       // }
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
        var intNotSelectedInterUser = $('#interUserSelect option').length;
        document.getElementById("intNotSelectedInterUser").innerHTML=intNotSelectedInterUser;
	}
	
	//双击删除院际
	function leftmoveInterUser(){
		var so = $("#interUserSelected option:selected");
        $("#interUserSelect").append(so);
        var intSelectedInterUser = $('#interUserSelected option').length;
        document.getElementById("intSelectedInterUser").innerHTML=intSelectedInterUser;
        var intNotSelectedInterUser = $('#interUserSelect option').length;
        document.getElementById("intNotSelectedInterUser").innerHTML=intNotSelectedInterUser;
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
        var intNotSelectedCommonUser = $('#commonUserSelect option').length;
        document.getElementById("intNotSelectedCommonUser").innerHTML=intNotSelectedCommonUser;
	}
	
	//双击删除共同体
	function leftmoveCommonUser(){
		var so = $("#commonUserSelected option:selected");
        $("#commonUserSelect").append(so);
        var intSelectedCommonUser = $('#commonUserSelected option').length;
        document.getElementById("intSelectedCommonUser").innerHTML=intSelectedCommonUser;
        var intNotSelectedCommonUser = $('#commonUserSelect option').length;
        document.getElementById("intNotSelectedCommonUser").innerHTML=intNotSelectedCommonUser;
	}
	
	//获取div显示内容的高度，并给改div的内容以外的地方预留高度
    function getHeight() {      
		var bodyHeight = document.documentElement.clientHeight;
		var tableHeight = (document.getElementById("teleconferenceNotice").offsetHeight)+(document.getElementById("teleconferenceAuditNoticeBut").offsetHeight)+60;
		if(bodyHeight >= tableHeight ){
			var patientinfor1Height =document.getElementById("patientinfor1").style.height = bodyHeight-60;		
	    }else{
	    	//document.documentElement.style.overflowY = 'auto';
	    }
	}
	
    window.onload = function() { 	
	getHeight();       	
	};
</script>
<style type="text/css">
#titleStyle{color:#3c3645;font-size:18px;font-weight: bold;height:30px;line-height:30px;}
#viewappinfor{background-color:#dee;}
.subinfor{border:0px solid #7F9DB9;background-color:#F8F8FF;margin-left:30px;margin-right:30px;}
#rightdown{background-image: url("../resources/images/table/down.gif");background-repeat: repeat-x;width:19px;height:28px;margin-bottom:2px;margin-left:15px;margin-right:15px;margin-top:8px;}
		#leftup{background-image: url("../resources/images/table/up.gif");background-repeat: repeat-x;width:19px;height:28px;margin-bottom:2px;margin-left:15px;margin-right:15px;margin-top:20px;}
</style>
</head>
<body style="overflow-y:auto;">
   <form method="post" action="<%=request.getContextPath()%>/meeadmdbd/teleconferenceNotice">
     <div id="viewappinfor" style="overflow-y:hidden;">
     <input type="hidden" id="innaiUserOldValue" value=""/>
     <input type="hidden" id="interUserOldValue" value=""/>
     <input type="hidden" id="commonUserOldValue" value=""/>
		<div id="titleStyle">
			<span>&nbsp;</span>
		</div>		
        <div class="subinfor" id="patientinfor1">
		      <table align="center" class="submit_table" width="80%" border="1"  id="teleconferenceNotice">
		      <tr>
		          <td align="center" colspan="2" style="font-weight:bold;text-align:center; vertical-align:middle;"><comm:message key='meeting.discussedMedicalRecords'/>
		              <input type="hidden" id="meetingApplicationId" name="meetingApplicationId" value="${meetingApplicationId}">
		          </td>         
		      </tr>
		      <c:if test="${expertsList!=null}">
			      <tr>
				      <td width="25%"  align="right" style="vertical-align:middle;"><strong><comm:message key='meeting.expertsNoticeContent'/></strong>:<span class="required">*</span><div id="hint1" style="margin-right: 10px;">(<span id="tip1"></span>/500)</div></td>	
				      <td style="vertical-align:middle;"><textarea name="expertsNotice" id="expertsNotice" style="width:99.5%;border:1px;vertical-align:middle;" rows="5" class="text-long" maxlength="500" onpaste="return realTimeCountClip(this,'hint1');" onkeyup="javascript:realTimeCount(this,'hint1');" title=" <comm:message key="comm.content_maxlength"/>500">${unifiedVaue}</textarea>
				      </td>
			      </tr>
			      <tr>
				     <td width="25%" align="right" style="vertical-align:middle;"><comm:message key='meeting.expertsNames'/></strong>:&nbsp;&nbsp;</td>	
				     <td style="vertical-align:middle;">
				     	<table style="border:1px solid #000;margin-left:20px;margin-right:20px;margin-bottom:10px;margin-top:10px;">
							<tr>
								<td bgcolor="#D0D0D0" style="height:22px;border-right:1px solid #000;border-left:1px solid #000;"" >
									&nbsp;<strong>待选择<span id="intNotSelectedInnaiUser">0</span>项</strong>
								</td>
								<td style="border-bottom:0px;border-right:1px solid #000;">
								&nbsp;
								</td>
								<td bgcolor="#D0D0D0"  style="height:22px;border-right:1px solid #000;">
									&nbsp;<strong>已选择<span id="intSelectedInnaiUser">0</span>项</strong>
								</td>
							</tr>
							<tr>
								<td style="border-right:1px solid #000;border-bottom:1px solid #000;" rowspan="1">
									<select multiple="multiple" name="innaiUserSelect" id="innaiUserSelect"  style="align:center;width:260px;height:143px;border:none;">
									</select>	
								</td>
								
								<td align="center" style="border-right:1px solid #000;border-bottom:1px solid #000;">
									<div id="rightdown" onclick="rightmoveInnaiUser()" style="margin-top:30px;"></div>
									<div id="leftup" onclick="leftmoveInnaiUser()" style="margin-top:30px;margin-bottom:20px;"></div>
								</td>
								
								<td style="border-left:1px solid #000;border-right:1px solid #000;border-bottom:1px solid #000;">
									<select align="center" multiple="multiple" name="innaiUserSelected" id="innaiUserSelected" style="width:260px;height:143px;border:none;">
										<c:forEach var="experts" items="${expertsList}">
											<option value="${experts.id}">${experts.name}</option>
										</c:forEach>
									</select>
								</td>
							</tr>
						</table>
				     </td>
			      </tr>
			  </c:if>
			  
			  <c:if test="${communityList!=null}">
			      <tr>
				      <td width="25%"  align="right" style="vertical-align:middle;"><strong><comm:message key='meeting.communityNoticeContent'/></strong>:<span class="required">*</span><div id="hint3" style="margin-right: 10px;">(<span id="tip3"></span>/500)</div></td>	
				      <td style="vertical-align:middle;"><textarea name="communityNotice" id="communityNotice" style="width:99.5%;border: 1px;" rows="5" class="text-long" maxlength="500" onpaste="return realTimeCountClip(this,'hint3');" onkeyup="javascript:realTimeCount(this,'hint3');" title=" <comm:message key="comm.content_maxlength"/>500">${unifiedVaue}</textarea>
				      </td>
			      </tr>
			      <tr>
				     <td width="25%" align="right" style="vertical-align:middle;"><comm:message key='meeting.communityNames'/></strong>:&nbsp;&nbsp;</td>	
				     <td style="vertical-align:middle;">
				     	<table style="border:1px solid #000;margin-left:20px;margin-right:20px;margin-bottom:10px;margin-top:10px;">
							<tr>
								<td bgcolor="#D0D0D0" style="height:22px;border-right:1px solid #000;border-left:1px solid #000;"" >
									&nbsp;<strong>待选择<span id="intNotSelectedCommonUser">0</span>项</strong>
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
										<c:forEach var="community" items="${communityList}">
											<option value="${community.id}">${community.name}</option>
										</c:forEach>
									</select>
								</td>
							</tr>
						</table>
				     </td>
			      </tr>
			  </c:if>
			  
			  <c:if test="${interList!=null}">
			      <tr>
				      <td width="25%"  align="right" style="vertical-align:middle;"><strong><comm:message key='meeting.internationalCourtNoticeContent'/></strong>:<span class="required">*</span><div id="hint2" style="margin-right: 10px;">(<span id="tip2"></span>/500)</div></td>	
				      <td style="vertical-align:middle;"><textarea name="internationalCourtNotice" id="internationalCourtNotice" style="width:99.5%;border: 1px;" rows="5" class="text-long" maxlength="500" onpaste="return realTimeCountClip(this,'hint2');" onkeyup="javascript:realTimeCount(this,'hint2');" title=" <comm:message key="comm.content_maxlength"/>500">${unifiedVaue}</textarea>
				      </td>
			      </tr>
			      <tr>
				     <td width="25%" align="right" style="vertical-align:middle;"><comm:message key='meeting.internationalCourtNames'/></strong>:&nbsp;&nbsp;</td>	
				     <td style="vertical-align:middle;">
				     	<table style="border:1px solid #000;margin-left:20px;margin-right:20px;margin-bottom:10px;margin-top:10px;">
							<tr>
								<td bgcolor="#D0D0D0" style="height:22px;border-right:1px solid #000;border-left:1px solid #000;"" >
									 &nbsp;<strong>待选择<span id="intNotSelectedInterUser">0</span>项</strong>
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
									<select align="center" multiple="multiple" name="interUserSelected" id="interUserSelected" style="width:260px;height:143px;border:none;">
										<c:forEach var="inter" items="${interList}">
											<option value="${inter.id}">${inter.name}</option>
										</c:forEach>
									</select>
								</td>
							</tr>
						</table>
				     </td>
			      </tr>
			  </c:if>
		      
		      </table>
		      <table align="center" id="teleconferenceAuditNoticeBut">
		        <tr>
		           <td colspan="2" align="center"><input type="button" onclick="noticemit();" class="button" id="noticeSubmit" align="center" value="<comm:message key='meeting.sendNotice'/>" ></td>
		       </tr>
		     </table>      
        </div>
		<div id="titleStyle">
			<span>&nbsp;</span>
		</div>
	</div>	

   </form>
</body>
</html>