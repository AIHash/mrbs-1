<%@ page pageEncoding="UTF-8" isELIgnored="false"%>
<%@include file="/common.jsp"%>
<%
 String returnmessage=(String)request.getSession().getAttribute("returnmessage");
 request.getSession().removeAttribute("returnmessage"); 
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title><comm:message key="system.system_name" /></title>
	<link href="<%=request.getContextPath()%>/resources/style/main.css" rel="StyleSheet" type="text/css"/>
	<link href="${pageContext.request.contextPath}/resources/css/style.css" rel="stylesheet" type="text/css" />
	<link href="<%=request.getContextPath()%>/resources/jqueryalerts/jquery.alerts.css" rel="stylesheet" type="text/css" media="screen" />
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/jqueryalerts/jquery.ui.draggable.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/jqueryalerts/jquery.alerts.js"></script>

	
<script type="text/javascript">
 function applicationeview(id)
 {
	 var url="<%=request.getContextPath()%>/unifiedindex/vedioapplictionView?applicationid="+id;
	 window.open(url, 'newwindow', ' width=800,height=700, top=100, left=100, toolbar=no, menubar=no, scrollbars=no, resizable=yes,location=no, status=no'); //这句要写成一行
	 return false;
 }

 function getTotal()
 {
	$.post('<%=request.getContextPath()%>/unified/MeetingNum', function(json) 
		{
		  $.each(json,function (index, domEle)
			  {
			    if(index=="invitetotal")
			    	{
			    	$("#myinvite").html("("+domEle+")");
			    	}
			    if(index=="meetingtotal")
			    	{
			    	$("#mymeeting").html("("+domEle+")");
			    	}
			  });  
		}, 'json');
 }
  setInterval("getTotal", 10000);
		
	$(document).ready(function() {
		$(".various3").fancybox({
			'width' : '75%',
			'height' : '85%',
			'autoScale' : false,
			'transitionIn' : 'none',
			'transitionOut' : 'none',
			'padding' : '5px',
			'type' : 'iframe'
		});
	});

	function myAlert() {
		var message = '<%=returnmessage%>';
		if (message && message != "" && message != "null") {
			jAlert(message, "<comm:message key='meeting.m.infotishi'/>");
		}
	}
</script>
<style type="text/css">
<!--
html,body {
	margin:0;
    height:100%;
	font:14px "宋体",Arial;
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
	background:url(${pageContext.request.contextPath}/resources/images/bgbg1.gif);
}
-->
</style>
</head>
<body  bgcolor="white" class="bg" onload="myAlert();getTotal()">
    <div id="center">
		<comm:navigator position="unified.applicationManager>>vedio.app" />			
<table id="index-table-2" class="hd" width="691" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <th><comm:message key='unified.meeting.name'/></th> 
    <th><comm:message key='unified.meetingtype'/></th>
    <th><comm:message key='unified.datetime'/></th>
    <th><comm:message key='unified.app.time'/></th>
    <th><comm:message key='unified.operater'/></th>
    </tr>

   
    <c:set var="videoapplicationindex" value="0" scope="page"/>
    <c:forEach var="myvideoapplicationinfo" items="${myvideoapplication}" begin="0" end="6">
    <tr>
    <th>${myvideoapplicationinfo.videoApplication.name}</th>
    <th>${myvideoapplicationinfo.meetingType.name}</th>
    <th><fmt:formatDate value="${myvideoapplicationinfo.suggestDate}" pattern="yyyy-MM-dd HH:mm" /></th>
    <th><fmt:formatDate value="${myvideoapplicationinfo.appDate}" pattern="yyyy-MM-dd HH:mm" /></th>
    <th>
    <a style="COLOR:blue;" href="<%=request.getContextPath()%>/unifiedindex/vedioapplictionView/${myvideoapplicationinfo.id}"  class='various3'><comm:message key='unified.detail'/></a>
    </th>
    </tr>    
   </c:forEach>
   <c:forEach var="i" begin="${applicationindex}" end="5">
    <tr>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    </tr>
   </c:forEach>


</table>
</div>

			<div id="center_right">
				<img src="${pageContext.request.contextPath}/resources/images/bg_right.gif" width="25"/>
			</div>
</body>
</html>