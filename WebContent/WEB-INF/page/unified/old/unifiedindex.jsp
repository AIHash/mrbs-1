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
	<link href="<%=request.getContextPath()%>/resources/fancybox/jquery.fancybox-1.3.4.css" rel="stylesheet" type="text/css" media="screen" />
	<link href="<%=request.getContextPath()%>/resources/jqueryalerts/jquery.alerts.css" rel="stylesheet" type="text/css" media="screen" />
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/jqueryalerts/jquery.ui.draggable.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/jqueryalerts/jquery.alerts.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/fancybox/jquery.mousewheel-3.0.4.pack.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/fancybox/jquery.fancybox-1.3.4.pack.js"></script>
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
</head>
<body  bgcolor="white" class="bg" onload="myAlert();getTotal();heigthReset(64);" onresize="heigthReset(64);">
<div id="head" class="c">
	<div id="user">
    	<div id="userinfo">
    	<c:if test="${USER_LOGIN_SESSION!=null}">
    	  ${USER_LOGIN_SESSION.name}
    	</c:if>  
    	<br />[<a style="COLOR:blue;" href="<%=request.getContextPath()%>/unified/getUserInfo" class='various3'><comm:message key='meeting.m.editprofile'/></a>]&nbsp;&nbsp;[<a href="javascript:exitsys()"><img src="<%=request.getContextPath()%>/resources/skin/style/exit.gif" border="0"/>&nbsp;<comm:message key="admin.Exit" /></a>]</div>
    </div>
	<div id="navigation">
		<ul>
        	<li class="active"><a style="COLOR:blue;" href="<%=request.getContextPath()%>/unifiedindex/index"><comm:message key='unified.index'/></a></li>
        	<li><a style="COLOR:blue;" href="<%=request.getContextPath()%>/unifiedindex/applicationSearch"><comm:message key='unified.applicationManager'/></a></li>
            <li><a style="COLOR:blue;" href="<%=request.getContextPath()%>/unifiedindex/meetingSearch"><comm:message key='unified.meetingManager'/></a></li>
        </ul>
    </div>
</div>
<div id="main" class="c">
	<div id="main-left" class="l">
    	<div id="brief-top"></div>
    	<div id="brief"><h1><comm:message key='system.welcome1'/></h1><img src="<%=request.getContextPath()%>/resources/images/unified/index-brief-icon.jpg" width="85" height="85" align="left" /><span><comm:message key='system.welcome2'/>
    	  </span>
    </div>
        <div id="brief-bottom"></div>
        <ul id="index-tab">
            <li id="tab1" onclick="javascript:tab('1');" class="active"><comm:message key='unified.recent.invite'/></li>
            <li id="tab2" onclick="javascript:tab('2');"><comm:message key='unified.myapplication'/></li>
            <li id="tab3" onclick="javascript:tab('3');"><comm:message key='unified.mymeeting'/></li>
      	</ul>
        <div id="index-table">
<table id="index-table-1" class="active" width="691" border="0" cellspacing="0" cellpadding="0">
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
    <td>${inviteinfo.title}</td>
    <td>${inviteinfo.applicationId.requester.name}</td>
    <td>${inviteinfo.meetingType.name}</td>
    <td><fmt:formatDate value="${inviteinfo.startTime}" pattern="yyyy-MM-dd HH:mm" /></td>
    <td>
     <a style="COLOR:blue;" href="<%=request.getContextPath()%>/unified/meetView/${inviteinfo.id}"  class='various3'><comm:message key='unified.detail'/></a>&nbsp;<a href="<%=request.getContextPath()%>/unified/operater?operatermeetingid=${inviteinfo.id}&type=accept&isindex=true"><comm:message key='unified.accept'/></a>&nbsp;<a href="<%=request.getContextPath()%>/unified/operater?operatermeetingid=${inviteinfo.id}&type=refuse&isindex=true"><comm:message key='unified.refuse'/></a>
     <c:set var="deptindex" value="${deptindex+1}" scope="page"/>
    </td>
    </tr>    
   </c:forEach>
   <c:forEach var="i" begin="${deptindex}" end="5">
    <tr>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    </tr>
   </c:forEach>
</table>
<table id="index-table-2" class="hd" width="691" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <th><comm:message key='unified.meeting.name'/></th>
<!--
    <th><comm:message key='unified.meeting.requester'/></th>
-->  
    <th><comm:message key='unified.meetingtype'/></th>
    <th><comm:message key='unified.datetime'/></th>
    <th><comm:message key='unified.app.time'/></th>
    <th><comm:message key='unified.operater'/></th>
    </tr>

   
    <c:set var="applicationindex" value="0" scope="page"/>
    <c:forEach var="myapplicationinfo" items="${myapplication}" begin="0" end="6">
    <tr>
    <td>${myapplicationinfo.purpose}</td>
<!--
    <td>${myapplicationinfo.requester.name}</td>
-->
    <td>${myapplicationinfo.meetingType.name}</td>
    <td><fmt:formatDate value="${myapplicationinfo.expectedTime}" pattern="yyyy-MM-dd HH:mm" /></td>
    <td><fmt:formatDate value="${myapplicationinfo.applicationTime}" pattern="yyyy-MM-dd HH:mm" /></td>
    <td>
    <a style="COLOR:blue;" href="<%=request.getContextPath()%>/unified/applictionView/${myapplicationinfo.id}"  class='various3'><comm:message key='unified.detail'/></a>
    <c:if test="${myapplicationinfo.state==3}">
    &nbsp;<a style="COLOR:blue;" href="<%=request.getContextPath()%>/unified/appEdit/${myapplicationinfo.id}"><comm:message key='unified.edit'/></a>
    </c:if>
    <c:set var="applicationindex" value="${applicationindex+1}" scope="page"/>
    </td>
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
<table id="index-table-3" class="hd" width="691" border="0" cellspacing="0" cellpadding="0">
  <tr>
     <th><comm:message key='unified.meeting.name'/></th>
    <th><comm:message key='unified.meeting.requester'/></th>
    <th><comm:message key='unified.meetingtype'/></th>
    <th><comm:message key='unified.meeting.starttime'/></th>
    <th><comm:message key='unified.operater'/></th>
    </tr>

   
    <c:set var="meetingindex" value="0" scope="page"/>
   <c:forEach var="meetinginfo"  items="${meeting}" begin="0" end="6">
     <tr>
    <td>${meetinginfo.title}</td>
    <td>${meetinginfo.applicationId.requester.name}</td>
    <td>${meetinginfo.meetingType.name}</td>
    <td><fmt:formatDate value="${meetinginfo.startTime}" pattern="yyyy-MM-dd HH:mm" /></td>
    <td>
    <a style="COLOR:blue;" href="<%=request.getContextPath()%>/unified/meetView/${meetinginfo.id}"   class='various3'><comm:message key='unified.detail'/></a>  
     <c:if test="${meetinginfo.state==3 && !meetinginfo.isSummaryOff }">
    &nbsp;<a style="COLOR:blue;" href="<%=request.getContextPath()%>/unified/meetOpintion?opintionmeetingid=${meetinginfo.id}&userType=5" class='various3'><comm:message key='unified.opintion'/></a>
    </c:if>
    <c:set var="meetingindex" value="${meetingindex+1}" scope="page"/>
    </td>
    </tr>    
   </c:forEach>
    <c:forEach var="i" begin="${meetingindex}" end="5">
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
    <div id="main2" class="r">
    	<div><a style="COLOR:blue;" href="<%=request.getContextPath()%>/unified/application"><img src="<%=request.getContextPath()%>/resources/images/unified/btn-sq.jpg" width="232" height="55" /></a></div>
    	<div><a style="COLOR:blue;" href="<%=request.getContextPath()%>/unified/applicationVideo"><img src="<%=request.getContextPath()%>/resources/images/unified/btn-video.jpg" width="232" height="55" /></a></div>
        <div class="info-top"></div>
        <div class="info">
        	<ul>
            	<li><img src="<%=request.getContextPath()%>/resources/images/unified/ico1.gif" width="16" height="16" align="top" />&nbsp;<a href="<%=request.getContextPath()%>/unified/meetingSearch?applicatonstate=${APPLICATION_STATE_NONE }"><comm:message key='unified.myinvite'/></a><span id="myinvite">(0)</span></li>
                <li><img src="<%=request.getContextPath()%>/resources/images/unified/ico2.gif" width="16" height="16" align="top" />&nbsp;<a href="<%=request.getContextPath()%>/unified/meetingSearch?applicatonstate=${APPLICATION_STATE_ACCEPT}"><comm:message key='unified.join.meeting'/></a><span id="mymeeting">(0)</span></li>
                <!--<li><img src="<%=request.getContextPath()%>/resources/images/unified/ico3.gif" width="16" height="16" align="top" />&nbsp;<a href="#">我的消息</a><span>(3)</span></li>
                <li><img src="<%=request.getContextPath()%>/resources/images/unified/ico4.gif" width="16" height="16" align="top" />&nbsp;<a href="#">我的文件</a><span>(3)</span></li>
                <li><img src="<%=request.getContextPath()%>/resources/images/unified/ico5.gif" width="16" height="16" align="top" />&nbsp;<a href="#">我的评价</a><span>(3)</span></li>-->
            </ul>
        </div>
      <div class="info-bottom"></div>
       <div class="ad"><a style="COLOR:blue;" href="${rmyy_url_1 }" target="_blank"><img src="<%=request.getContextPath()%>/resources/images/unified/ad1.jpg" width="232" height="116" /></a></div>
        <div class="ad"><a style="COLOR:blue;" href="${rmyy_url_2 }" target="_blank"><img src="<%=request.getContextPath()%>/resources/images/unified/ad2.jpg" width="233" height="82" /></a></div>
    </div>
</div>
<div id="footer" class="c">
	<div id="copyright" class="c"><comm:message key='system.software_copy'/></div>
</div>
<script language="javascript"> 
function tab(tabid){
		if (tabid==1){
		document.getElementById("tab1").className="active";
		document.getElementById("tab2").className="";
		document.getElementById("tab3").className="";
		document.getElementById("index-table-1").className="active";
		document.getElementById("index-table-2").className="hd";
		document.getElementById("index-table-3").className="hd";
		}
		if (tabid==2){
		document.getElementById("tab1").className="";
		document.getElementById("tab2").className="active";
		document.getElementById("tab3").className="";
		document.getElementById("index-table-1").className="hd";
		document.getElementById("index-table-2").className="active";
		document.getElementById("index-table-3").className="hd";
		}
		if (tabid==3){
		document.getElementById("tab1").className="";
		document.getElementById("tab2").className="";
		document.getElementById("tab3").className="active";
		document.getElementById("index-table-1").className="hd";
		document.getElementById("index-table-2").className="hd";
		document.getElementById("index-table-3").className="active";
		}
}

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
  };
  t[i].onmouseover=function(){
   if(this.x!="1")this.style.backgroundColor=c;
  };
  t[i].onmouseout=function(){
   if(this.x!="1")this.style.backgroundColor=(this.sectionRowIndex%2==0)?a:b;
  };
 }
}

//tr_color("表格名称","奇数行背景","偶数行背景","鼠标经过背景","点击后背景");
tr_color("index-table-1","#fafaff","#ffffff","#ecfbd4","#e7e7e7");
tr_color("index-table-2","#fafaff","#ffffff","#ecfbd4","#e7e7e7");
tr_color("index-table-3","#fafaff","#ffffff","#ecfbd4","#e7e7e7");
</script>
<script language="JavaScript" type="text/javascript">
		function exitsys()
		{
   			 var ask=confirm("<comm:message key='admin.exit_sure'/>\n\n<comm:message key='admin.click_ok'/>");
    		 if(ask)
				parent.location.href="${root}/login/exit";
		}
</script>
<a id="myalert" href="<%=request.getContextPath()%>/resources/MyAlert.jsp" style="display:none"></a>
</body>
</html>