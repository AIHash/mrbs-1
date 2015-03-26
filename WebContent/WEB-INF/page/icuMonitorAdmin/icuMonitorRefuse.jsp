<%@ page pageEncoding="UTF-8" isELIgnored="false"%>
<%@include file="/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title><comm:message key="meeting.m.detailedpage"/></title>
	<comm:pageHeader hasEcList="false"></comm:pageHeader>
	<link href="${pageContext.request.contextPath }/resources/css/style.css" rel="stylesheet" type="text/css" />
	<link href="<%=request.getContextPath()%>/resources/jqueryalerts/jquery.alerts.css" rel="stylesheet" type="text/css" media="screen" />
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/jqueryalerts/jquery.ui.draggable.js" ></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/jqueryalerts/jquery.alerts.js" ></script>
<script type="text/javascript">
	function check(){
		 var refuseReson=document.getElementById("refuseReson").value;
		 if(refuseReson==""||trim(refuseReson)=="")
		 {
			 jAlert("<comm:message key='meeting.m.refusealert'/>", "<comm:message key='meeting.m.infotishi'/>");
			 return false;
		 } 
		 return true;

	}

	function trim(str){ //删除左右两端的空格
	    return str.replace(/(^\s*)|(\s*$)/g, "");
	}
	
	$( function($) {
		$("#refusebutton").click( function() {
			refsubmit();
		});
	});

	function refsubmit(){
		var refusemeetingid=$('#refusemeetingid').val();
		var refuseReson=$('#refuseReson').val();
		if(refuseReson==""||trim(refuseReson)=="")
		 {
			jAlert("<comm:message key='meeting.m.refusealert'/>", "<comm:message key='meeting.m.infotishi'/>");
			 return false;
		 } 
		$.ajax({type :'POST',  url : '${pageContext.request.contextPath}/icumonitor/refuseicuMonitor', 
			data : { 'refusemeetingid' : refusemeetingid,'refuseReson':refuseReson },
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
						redirctlist();
					});
				}else{
					jAlert("<comm:message key='meeting.m.submitfalse'/>", "<comm:message key='meeting.m.infotishi'/>");
				}
			}
		});
	}

	function redirctlist(){
		var meetingappserchflag=$('#meetingappserchflag').val();
	    var url;
	    url="<%=request.getContextPath()%>/meeadIndex/icuMonitList";   
    	parent.document.getElementById("WorkBench").contentWindow.document.getElementById("dataFrame").contentWindow.location.href = url;
    	parent.$.fancybox.close();

	}

	//获取div显示内容的高度，并给改div的内容以外的地方预留高度
    function getHeight() {      
	var bodyHeight = document.body.clientHeight;
	var patientinfor1Height =document.getElementById("patientinfor1").style.height = bodyHeight - 60;
	}
	
    window.onload = function() { 	
	getHeight();       	
	};
</script>	
</head>

<body >
	<form method="post" id="refusereasomform"  action="<%=request.getContextPath()%>/icumonitor/refuseicuMonitor" onsubmit="return check()">
	     <div id="viewappinfor">
			<div id="titleStyle">
				<span>&nbsp;</span>
			</div>		
            <div class="subinfor" id="patientinfor1">
				<input type="hidden" id="refusemeetingid" name="refusemeetingid" value="${refusemeetingid_add}" />
				<input type="hidden" id="meetingappserchflag" name="meetingappserchflag" value="${meetingappserchflag}" />
				<table border="0" cellspacing="0" cellpadding="0" align="center" width="100%">
				    <tr height="15"></tr>
					<tr align="center">
						<td colspan="2" align="center">
							<font size="3px"><strong><comm:message key='meeting.m.refusetext'/></strong></font>
						</td>
					</tr>
					<tr height="15"></tr>
					<tr>
						<td width="15%" align="right"><comm:message key='meeting.m.refuseReson'/>:<span	class="required">*</span><div id="hint" style="margin-right: 10px;">(500/500)</div></td>
						<td>
							<textarea name="refuseReson" rows="8" style="width:85%;border:1px solid #7F9DB9;" class="text-long" id="refuseReson" maxlength="500" onpaste="return realTimeCountClip(this,'hint');" onkeyup="javascript:realTimeCount(this,'hint');" title=" <comm:message key="comm.content_maxlength"/>500"></textarea>
						</td>
					</tr>
					<tr height="15"></tr>
					<tr>
						<td colspan="2"  align="center">
							<input class="button" type="button"  name="refusebutton" id="refusebutton" value="<comm:message key='meeting.m.commit'/>"  />
						</td>
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