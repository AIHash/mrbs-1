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
	<link href="${pageContext.request.contextPath}/resources/css/style.css"	rel="stylesheet" type="text/css" />
	<link href="<%=request.getContextPath()%>/resources/jqueryalerts/jquery.alerts.css" rel="stylesheet" type="text/css" media="screen" />
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/jqueryalerts/jquery.ui.draggable.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/jqueryalerts/jquery.alerts.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/validate.js"></script>

	
<script type="text/javascript">
 function inviteview(id)
 {
	 var url="<%=request.getContextPath()%>/unified/meetView?meetingid="+id;
	 window.open(url, 'newwindow', ' width=800,height=700, top=100, left=100, toolbar=no, menubar=no, scrollbars=no, resizable=yes,location=no, status=no');//这句要写成一行
	 return false;
 }
 function applicationeview(id)
 {
	 var url="<%=request.getContextPath()%>/unified/applictionView?applicationid="+id;
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
		
	});

	function myAlert() {
		var message = '<%=returnmessage%>';
		if (message && message != "" && message != "null") {
			jAlert(message, "<comm:message key='meeting.m.infotishi'/>");
		}
	}
</script>
	<style>
		/*专家申请视频讲座的展现页面*/
#class-table{ border:#d2d2d2 solid 1px; width:738px;height:500px;}
#class-table th{ height:25px; line-height:25px;font-size:12px; padding-left:10px; text-align:left; color:#0b4278; background:#fafaff; border-bottom:1px solid #d2d2d2;}
#class-table td{ height:20px; line-height:20px; padding:5px 10px; border-bottom:1px solid #d2d2d2;}
html,body {
	margin: 0;
	height: 100%;
	font: 14px "宋体", Arial;
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
    background-color: #fff;
}
</style>
</head>
<body style="padding:0; margin:0;background:url('../resources/images/theme/bg_right-2.gif');" bgcolor="white" class="bg" onload="myAlert();getTotal();heigthReset(64);" onresize="heigthReset(64);">
<div id="center">
		<comm:navigator position="unified.myinvite>>unified.myinvite" />
	<div id="main" >
	<div id="class-table" class="c">			
<table  border="0" width="738" cellspacing="0" cellpadding="0">
  <tr>
    <th><comm:message key='unified.meeting.name'/></th>
    <th><comm:message key='unified.meeting.requester'/></th>
    <th><comm:message key='unified.meetingtype'/></th>
    <th><comm:message key='unified.meeting.starttime'/></th>
    <th><comm:message key='unified.operater'/></th>
    </tr>
   
   <c:set var="deptindex" value="0" scope="page"/>
    <c:forEach var="inviteinfo" items="${invite}" begin="0" end="6">
    <tr>
    <td>${inviteinfo.title}&nbsp;</td>
    <td>${inviteinfo.applicationId.requester.name}&nbsp;</td>
    <td>${inviteinfo.meetingType.name}&nbsp;</td>
    <td><fmt:formatDate value="${inviteinfo.startTime}" pattern="yyyy-MM-dd HH:mm" />&nbsp;</td>
    <td>
     <a style="COLOR:blue;" href="<%=request.getContextPath()%>/unified/meetView/${inviteinfo.id}"  class='various3'><comm:message key='unified.detail'/></a>&nbsp;<a href="<%=request.getContextPath()%>/unified/operater?operatermeetingid=${inviteinfo.id}&type=accept&isindex=true"><comm:message key='unified.accept'/></a>&nbsp;<a href="<%=request.getContextPath()%>/unified/operater?operatermeetingid=${inviteinfo.id}&type=refuse&isindex=true"><comm:message key='unified.refuse'/></a>
     <c:set var="deptindex" value="${deptindex+1}" scope="page"/>&nbsp;
    </td>
    </tr>    
   </c:forEach>
  <c:forEach var="i" begin="${deptindex}" end="6">
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
</div>
</div>
<div id="center_right">
		<img src="${pageContext.request.contextPath}/resources/images/theme/bg_right.gif" width="25"/>
</div>	
	<script language="javascript"> 
		//table隔行换色
		function tr_color(o,a,b,c,d){
		 var t=document.getElementById(o).getElementsByTagName("tr");
		 for(var i=0;i<t.length;i++){
		  t[i].style.backgroundColor=(t[i].sectionRowIndex%2==0)?a:b;
		  t[i].onclick=function(){
		   if(this.x!="1"){
		    this.x="1";
		    this.style.backgroundColor=d;
		   }else{
		    this.x="0";
		    this.style.backgroundColor=(this.sectionRowIndex%2==0)?a:b;
		   }
		  }
		  t[i].onmouseover=function(){
		   if(this.x!="1")this.style.backgroundColor=c;
		  }
		  t[i].onmouseout=function(){
		   if(this.x!="1")this.style.backgroundColor=(this.sectionRowIndex%2==0)?a:b;
		  }
		 }
		}
		
		tr_color("class-table","#fafaff","#ffffff","#ecfbd4","#e7e7e7");
</script>			
</body>
</html>