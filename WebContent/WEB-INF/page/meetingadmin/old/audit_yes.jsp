<%@page import="com.wafersystems.mrbs.GlobalConstent"%>
<%@ page pageEncoding="UTF-8" isELIgnored="false"%>
<%@include file="/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
	<title><comm:message key="system.system_name" /></title>
	<link href="<%=request.getContextPath()%>/resources/style/comm.css" rel="StyleSheet" type="text/css"/>
	<script type="text/javascript" language="javascript" src="<%=request.getContextPath()%>/resources/js/jquery.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/fancybox/jquery.mousewheel-3.0.4.pack.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/fancybox/jquery.fancybox-1.3.4.pack.js"></script>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/fancybox/jquery.fancybox-1.3.4.css" media="screen" />
	<link href="${pageContext.request.contextPath }/resources/css/style.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/validate.js"></script>
	<script type="text/javascript">
		$(function() {
			$(".various3").fancybox({
				'width'				: '100%',
				'height'			: '100%',
				'autoScale'			: false,
				'transitionIn'  	: 'elastic',
                'transitionOut' 	: 'elastic',
			//	'padding'			: '5px',
				'centerOnScroll'	: true,
				'type'				: 'iframe'
			});
		});
	</script>
	<style type="text/css">
		/*会议管理员中首页面表格*/
#class-table{ border:#d2d2d2 solid 1px; width:738px;height:500px;}
#class-table th{ height:25px; line-height:25px;font-size:12px; padding-left:10px; text-align:left; color:#0b4278; background:#fafaff; border-bottom:1px solid #d2d2d2;}
#class-table td{ height:20px; line-height:20px; padding:5px 10px; border-bottom:1px solid #d2d2d2;}
</style>
</head>

<body onload="heigthReset(64);" onresize="heigthReset(64);" style="padding:0; margin:0;background:url('../resources/images/theme/bg_right-2.gif');" class="bg">
<div id="center">
		<comm:navigator position="consultation.app>>meeting.m.statepass" />
		<div id="main">
<div id="class-table" class="c">
<table id="class-table-1" class="hd" width="738" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <th><comm:message key='meeting.m.app.name'/></th>
    <th><comm:message key='meeting.m.app.request'/></th>
    <th><comm:message key='meeting.m.app.type'/></th>
    <th><comm:message key='meeting.m.app.expected.time'/></th>
    <th><comm:message key='meeting.m.operate'/></th>
    </tr>
    <c:set var="index" value="0" scope="page"/>
   <c:forEach var="ma_shenhetongguo" items="${ma_shenhetongguo}">
    <c:if test="${ma_shenhetongguo.state == 2}">
    <tr>
	    <td>${ma_shenhetongguo.title}&nbsp;</td>
	    <td>${ma_shenhetongguo.requester.name}&nbsp;</td>
	    <td>${ma_shenhetongguo.meetingType.name}&nbsp;</td>
	    <td><fmt:formatDate value="${ma_shenhetongguo.expectedTime}" pattern="yyyy-MM-dd HH:mm" />&nbsp;</td>
	    <td>
	         <c:if test="${ma_shenhetongguo.meetingType.value != 3}">
	             <a style="COLOR:blue;" href="<%=request.getContextPath()%>/meeadmdbd/viewappmeeting/${ma_shenhetongguo.id}" class="various3"><comm:message key='meeting.m.view' /></a>
	         </c:if>
	         <c:if test="${ma_shenhetongguo.meetingType.value == 3}"></c:if>&nbsp;
	    </td>
    </tr>     
    </c:if>
    <c:set var="index" value="${index+1}" scope="page"/>    
    </c:forEach>
  <c:forEach var="i" begin="${index}" end="6">
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