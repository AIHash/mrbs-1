<%@page import="com.wafersystems.mrbs.GlobalConstent"%>
<%@ page pageEncoding="UTF-8" isELIgnored="false"%>
<%@include file="/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
	<title><comm:message key="system.system_name" /></title>
	<link href="<%=request.getContextPath()%>/resources/style/main.css" rel="StyleSheet" type="text/css"/>
	<link href="<%=request.getContextPath()%>/resources/style/comm.css" rel="StyleSheet" type="text/css"/>
	<script type="text/javascript" language="javascript" src="<%=request.getContextPath()%>/resources/js/jquery.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/fancybox/jquery.mousewheel-3.0.4.pack.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/fancybox/jquery.fancybox-1.3.4.pack.js"></script>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/fancybox/jquery.fancybox-1.3.4.css" media="screen" />
    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/validate.js"></script>
	<script type="text/javascript">
		$(function() {
			$(".various3").fancybox({
				'width'				: '75%',
				'height'			: '100%',
				'autoScale'			: false,
				'transitionIn'		: 'none',
				'transitionOut'		: 'none',
				'padding'			: '5px',
				'centerOnScroll'	: true,
				'type'				: 'iframe'
			});
		});
	</script>
</head>

<body onload="heigthReset(64);" onresize="heigthReset(64);" class="bg">
<div id="head" class="c">
	<div id="user">
    	<div id="userinfo"> 
    	${USER_LOGIN_SESSION.name}
 
    	<br />[<a style="COLOR:blue;" href="<%=request.getContextPath()%>/unified/getUserInfo" class='various3'><comm:message key='meeting.m.editprofile'/></a>]&nbsp;&nbsp;[<a href="javascript:exitsys()"><img src="<%=request.getContextPath()%>/resources/skin/style/exit.gif" border="0"/>&nbsp;<comm:message key="admin.Exit" /></a>]</div>
    </div>
	<div id="navigation">
		<ul>
        	<li class="active"><a style="COLOR:blue;" href="<%=request.getContextPath()%>/meeadmdbd/listmeetingaplication"><comm:message key='meeting.m.homepage'/></a></li>
            <li><a style="COLOR:blue;" href="<%=request.getContextPath()%>/meeadmdbd/meetingappsearchlist"><comm:message key='meeting.m.myaccraditation'/></a></li>
            <li><a style="COLOR:blue;" href="<%=request.getContextPath()%>/meeadmdbd/meetingsearchlist"><comm:message key='meeting.m.consultation'/></a></li>
            <li><a style="COLOR:blue;" href="<%=request.getContextPath()%>/meeadmdbd/reportManagement">报表管理</a></li>
        </ul>
    </div>
</div>
<div id="main" class="c">
	<div id="main-left" class="l">
    	<div id="brief-top"></div>
    	<div id="brief"><h1><comm:message key='system.welcome1'/></h1><img src="<%=request.getContextPath()%>/resources/images/unified/index-brief-icon.jpg" width="85" height="85" align="left" /><span><comm:message key='system.welcome2'/><br />
    	 </span>
    </div>
        <div id="brief-bottom"></div>
        <ul id="index-tab">
            <li id="tab1" onclick="javascript:tab('1');" class="active"><comm:message key='meeting.m.toapproval'/></li>
            <li id="tab2" onclick="javascript:tab('2');"><comm:message key='meeting.m.approvalthrough'/></li>
            <li id="tab3" onclick="javascript:tab('3');"><comm:message key='meeting.m.approvalnotthrough'/></li>
            <li id="tab4" onclick="javascript:tab('4');"><comm:message key='meeting.m.consultation'/></li>
      	</ul>
        <div id="index-table">
<table id="index-table-1" class="active" width="691" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <th><comm:message key='meeting.m.app.name'/></th>
    <th><comm:message key='meeting.m.app.request'/></th>
    <th><comm:message key='meeting.m.app.type'/></th>
    <th><comm:message key='meeting.m.app.expected.time'/></th>
    <th><comm:message key='meeting.m.operate'/></th>
    </tr>
     <c:set var="index1" value="0" scope="page"/>
     <c:forEach var="ma_shenhe" items="${ma_shenhe}">
    <tr>
        <c:if test="${ma_shenhe.state == 1}">
		    <td>${ma_shenhe.title}</td>
		    <td>${ma_shenhe.requester.name}</td>
		    <td>${ma_shenhe.meetingType.name}</td>
		    <td><fmt:formatDate value="${ma_shenhe.expectedTime}" pattern="yyyy-MM-dd HH:mm" /></td>
		    <td>
		        <c:if test="${ma_shenhe.meetingType.value != 3 }">
			        <a style="COLOR:blue;" href="<%=request.getContextPath()%>/meeadmdbd/viewappmeeting/${ma_shenhe.id}" class="various3"  ><comm:message key='meeting.m.view'/></a>
			    	&nbsp;<a style="COLOR:blue;" href="<%=request.getContextPath()%>/meeadmdbd/redirctmeetingpass?meetingappserchflag=0&requestmeetid=${ma_shenhe.id}" class="various3"><comm:message key='meeting.m.through'/></a>
			    	&nbsp;<a style="COLOR:blue;" href="<%=request.getContextPath()%>/meeadmdbd/redirctrefuse?meetingappserchflag=0&refusemeetingid=${ma_shenhe.id}" class="various3"><comm:message key='meeting.m.refuse'/></a>        
		        </c:if>
		        <c:if test="${ma_shenhe.meetingType.value == 3 }">&nbsp;</c:if>
		   </td>        
        </c:if>
    </tr>  
    <c:set var="index1" value="${index1+1}" scope="page"/>  
   </c:forEach>
  <c:forEach var="i" begin="${index1}" end="6">
    <tr>
    <td>&nbsp;</td>
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
    <th><comm:message key='meeting.m.app.name'/></th>
    <th><comm:message key='meeting.m.app.request'/></th>
    <th><comm:message key='meeting.m.app.type'/></th>
    <th><comm:message key='meeting.m.app.expected.time'/></th>
    <th><comm:message key='meeting.m.operate'/></th>
    </tr>
    <c:set var="index2" value="0" scope="page"/>
   <c:forEach var="ma_shenhetongguo" items="${ma_shenhetongguo}">
    <tr>
    <c:if test="${ma_shenhetongguo.state == 2}">
	    <td>${ma_shenhetongguo.title}</td>
	    <td>${ma_shenhetongguo.requester.name}</td>
	    <td>${ma_shenhetongguo.meetingType.name}</td>
	    <td><fmt:formatDate value="${ma_shenhetongguo.expectedTime}" pattern="yyyy-MM-dd HH:mm" /></td>
	    <td>
	         <c:if test="${ma_shenhetongguo.meetingType.value != 3}">
	             <a style="COLOR:blue;" href="<%=request.getContextPath()%>/meeadmdbd/viewappmeeting/${ma_shenhetongguo.id}" class="various3"><comm:message key='meeting.m.view'/></a>
	         </c:if>
	         <c:if test="${ma_shenhetongguo.meetingType.value == 3}">&nbsp;</c:if>
	    </td>    
    </c:if>

    </tr> 
    <c:set var="index2" value="${index2+1}" scope="page"/>    
    </c:forEach>
  <c:forEach var="i" begin="${index2}" end="6">
    <tr>
    <td>&nbsp;</td>
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
    <th><comm:message key='meeting.m.app.name'/></th>
    <th><comm:message key='meeting.m.app.request'/></th>
    <th><comm:message key='meeting.m.app.type'/></th>
    <th><comm:message key='meeting.m.app.expected.time'/></th>
    <th><comm:message key='meeting.m.operate'/></th>
    </tr>
        <c:set var="index3" value="0" scope="page"/>
   <c:forEach var="ma_shenheweitongguo" items="${ma_shenheweitongguo}">

    <tr>
         <c:if test="${ma_shenheweitongguo.state == 3}">
		    <td>${ma_shenheweitongguo.title}</td>
		    <td>${ma_shenheweitongguo.department.name}</td>
		    <td>${ma_shenheweitongguo.requester.name}</td>
		    <td>${ma_shenheweitongguo.meetingType.name}</td>
		    <td><fmt:formatDate value="${ma_shenheweitongguo.expectedTime}" pattern="yyyy-MM-dd HH:mm" /></td>
		    <td>
		         <c:if test="${ma_shenheweitongguo.meetingType.value != 3}">
			         <a style="COLOR:blue;" href="<%=request.getContextPath()%>/meeadmdbd/viewappmeeting/${ma_shenheweitongguo.id}" class="various3">
			              <comm:message key='meeting.m.view'/>
			         </a>         
		         </c:if>
		         <c:if test="${ma_shenheweitongguo.meetingType.value == 3}">&nbsp;</c:if>
		    </td>         
         </c:if> 

    </tr>    
  <c:set var="index3" value="${index3+1}" scope="page"/>    
    </c:forEach>
  <c:forEach var="i" begin="${index3}" end="6">
    <tr>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    </tr>
   </c:forEach>
</table>

<table id="index-table-4" class="hd" width="691" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <th><comm:message key='meeting.m.app.name'/></th>
    <th><comm:message key='meeting.m.room'/></th>
    <th><comm:message key='meeting.m.app.request'/></th>
    <th><comm:message key='meeting.m.app.type'/></th>
    <th><comm:message key='meeting.m.app.time'/></th>
    <th><comm:message key='meeting.m.operate'/></th>
    </tr>
   <c:set var="index4" value="0" scope="page"/>
   <c:forEach var="meeting_state_pending" items="${meeting_state_pending}">
  <tr>
    <td>${meeting_state_pending.title}</td>
    <td>${meeting_state_pending.meetingRoomId.name}</td>
    <td>${meeting_state_pending.creator.name}</td>
    <td>${meeting_state_pending.meetingType.name}</td>
    <td><fmt:formatDate value="${meeting_state_pending.startTime}" pattern="yyyy-MM-dd HH:mm"/></td>
    <td> 
        <c:if test="${meeting_state_pending.meetingType.value != 3}">
	    	<a style="COLOR:blue;" href="<%=request.getContextPath()%>/meeadmdbd/viewmeeting/${meeting_state_pending.id}" class="various3"><comm:message key='meeting.m.view'/></a>
			&nbsp;<c:if test="${meeting_state_pending.state==3}"><a href="<%=request.getContextPath()%>/meeadmdbd/redirctsummary?requestsummaryid=${meeting_state_pending.id}" class="various3" ><comm:message key='meeting.m.summary'/></a></c:if>        
        </c:if>
        <c:if test="${meeting_state_pending.meetingType.value == 3}">&nbsp;</c:if>

	</td>
  </tr>
  <c:set var="index4" value="${index4+1}" scope="page"/>    
    </c:forEach>
  <c:forEach var="i" begin="${index4}" end="6">
    <tr>
    <td>&nbsp;</td>
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
        	<div><a style="COLOR:blue;" href="${tms_address}" target="_blank"><img src="<%=request.getContextPath()%>/resources/images/unified/btn-wz.jpg" width="232" height="55" /></a></div>
        <div class="info-top"></div>
        <div class="info">
        	<ul>
            	<li>
					<table>
						<tr  align="left">
							<td width="65%"><strong><img src="<%=request.getContextPath()%>/resources/images/unified/ico1.gif" width="16" height="16" align="top" />&nbsp;
            					<a style="COLOR:blue;" href="<%=request.getContextPath()%>/meeadmdbd/meetingappsearchlist"><comm:message key='meeting.m.myaccraditation'/></a><span>(${ma_noaccraditationcount_all})</span> </strong>
            				</td>
            				<td width="35%"><comm:message key='meeting.m.toapproval'/><span>(${ma_noaccraditationcount})</span></td>
            			</tr>
            			<tr  align="left">
            				<td width="65%">&nbsp;</td>
            				<td width="35%"><comm:message key='meeting.m.hadapproval'/><span>(${ma_accraditationcount_ytg})</span></td>
						</tr>
					</table>
            	</li>
                
                <li>
                	<table>
                		<tr  align="left">
                			<td width="100%" >
                			 	<strong><img src="<%=request.getContextPath()%>/resources/images/unified/ico2.gif" width="16" height="16" align="top" />&nbsp;<a href="<%=request.getContextPath()%>/meeadmdbd/meetingsearchlist"><comm:message key='meeting.m.consultation'/></a><span>(${meeting_state_all_count})</span></strong>
                			</td>
                		</tr>
                	</table>
                </li>
                               <!--<li><img src="images/ico3.gif" width="16" height="16" align="top" />&nbsp;<a href="#">我的消息</a><span>(3)</span></li>
                <li><img src="images/ico4.gif" width="16" height="16" align="top" />&nbsp;<a href="#">我的文件</a><span>(3)</span></li>
                <li><img src="images/ico5.gif" width="16" height="16" align="top" />&nbsp;<a href="#">我的评价</a><span>(3)</span></li>-->
            </ul>
        </div>
      <div class="info-bottom"></div>
        <div class="ad"><a href="${rmyy_url_1}" target="_blank"><img src="<%=request.getContextPath()%>/resources/images/unified/ad1.jpg" width="232" height="116" /></a></div>
        <div class="ad"><a href="${rmyy_url_2}" target="_blank"><img src="<%=request.getContextPath()%>/resources/images/unified/ad2.jpg" width="233" height="82" /></a></div>
    </div>
</div>
<div id="footer" class="c">
	<div id="copyright" class="c"><comm:message key='system.software_copy'/></div>
</div>

<script type="text/javascript">
	function exitsys()
	{
		var ask=confirm("<comm:message key='admin.exit_sure'/>\n\n<comm:message key='admin.click_ok'/>");
		if(ask)
			parent.location.href="${root}/login/exit";
	}
function tab(tabid){
		if (tabid==1){
		document.getElementById("tab1").className="active";
		document.getElementById("tab2").className="";
		document.getElementById("tab3").className="";
		document.getElementById("tab4").className="";
		document.getElementById("index-table-1").className="active";
		document.getElementById("index-table-2").className="hd";
		document.getElementById("index-table-3").className="hd";
		document.getElementById("index-table-4").className="hd";
		}
		if (tabid==2){
			document.getElementById("tab1").className="";
			document.getElementById("tab2").className="active";
			document.getElementById("tab3").className="";
			document.getElementById("tab4").className="";
			document.getElementById("index-table-1").className="hd";
			document.getElementById("index-table-2").className="active";
			document.getElementById("index-table-3").className="hd";
			document.getElementById("index-table-4").className="hd";
		}
		if (tabid==3){
			document.getElementById("tab1").className="";
			document.getElementById("tab2").className="";
			document.getElementById("tab3").className="active";
			document.getElementById("tab4").className="";
			document.getElementById("index-table-1").className="hd";
			document.getElementById("index-table-2").className="hd";
			document.getElementById("index-table-3").className="active";
			document.getElementById("index-table-4").className="hd";
		}
		if (tabid==4){
			document.getElementById("tab1").className="";
			document.getElementById("tab2").className="";
			document.getElementById("tab3").className="";
			document.getElementById("tab4").className="active";
			document.getElementById("index-table-1").className="hd";
			document.getElementById("index-table-2").className="hd";
			document.getElementById("index-table-3").className="hd";
			document.getElementById("index-table-4").className="active";
		}
}

//table隔行换色
function tr_color(o,a,b,c,d){
 var t=document.getElementById(o).getElementsByTagName("tr");
 for(var i=0;i<t.length;i++){
  t[i].style.backgroundColor=(t[i].sectionRowIndex%2==0)?a:b;

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
tr_color("index-table-4","#fafaff","#ffffff","#ecfbd4","#e7e7e7");
</script>
</body>
</html>