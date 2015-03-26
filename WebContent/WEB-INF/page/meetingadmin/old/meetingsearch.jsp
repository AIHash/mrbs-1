<%@ page pageEncoding="UTF-8" isELIgnored="false"%>
<%@include file="/common.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
	<title><comm:message key="system.system_name" /></title>
	<comm:pageHeader />
	<link href="<%=request.getContextPath()%>/resources/style/main.css" rel="StyleSheet" type="text/css"/>
	<link href="<%=request.getContextPath()%>/resources/style/comm.css" rel="StyleSheet" type="text/css"/>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery.js"></script>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/fancybox/jquery.fancybox-1.3.4.css" media="screen" />
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/My97DatePicker/WdatePicker.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/fancybox/jquery.mousewheel-3.0.4.pack.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/fancybox/jquery.fancybox-1.3.4.pack.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/validate.js"></script>

<script type="text/javascript">
	function setTimeToText(temp){
		var obj=document.getElementById(temp);
		setDayHM(obj);
	}

	function changeDiv(obj){
		alert("obj.value="+obj.value);
		if(obj.value==0){
			document.getElementById("m_search").style.display ="block";
			document.getElementById("mapp_search").style.display ="none";
		}
		if(obj.value==1){
			document.getElementById("m_search").style.diplay="none";
			alert(document.getElementById("m_search").style.diplay);
			document.getElementById("mapp_search").style.diplay="block";
			alert(document.getElementById("mapp_search").style.diplay);
		}
	}

	function changeSubmit(){
		document.refusereasomform.submit();
	}

	function init(){
	      var ecside1 = new ECSide("ec");
	      ecside1.doPrep=false;
	      ecside1.doPrepPrev=false;
	      ecside1.init();
	}

	$(document).ready(function() {
		$(".various3").fancybox({
			'width'				: '75%',
			'height'			: '85%',
			'autoScale'			: false,
			'transitionIn'		: 'none',
			'transitionOut'		: 'none',
			'padding'			: '5px',
			'type'				: 'iframe'
		});
/**/
		if(parseInt($('#ec')[0].scrollHeight) < 300){
			$('#ec').css('height', $('#ec')[0].scrollHeight + screen.availHeight - document.body.scrollHeight);
		}

	});
</script>
</head>

<body onload="init();heigthReset(64);" onresize="heigthReset(64);" class="bg">
<div id="head" class="c">
	<div id="user">
    	<div id="userinfo"> 
    	${USER_LOGIN_SESSION.name}
 
    	<br />[<a style="COLOR:blue;" href="<%=request.getContextPath()%>/unified/getUserInfo" class='various3'><comm:message key='meeting.m.editprofile'/></a>]&nbsp;&nbsp;[<a href="javascript:exitsys()"><img src="<%=request.getContextPath()%>/resources/skin/style/exit.gif" border="0"/>&nbsp;<comm:message key="admin.Exit" /></a>]</div>
    </div>
	<div id="navigation">
		<ul>
        	<li ><a style="COLOR:blue;" href="<%=request.getContextPath()%>/meeadmdbd/listmeetingaplication"><comm:message key='meeting.m.homepage'/></a></li>
            <li ><a style="COLOR:blue;" href="<%=request.getContextPath()%>/meeadmdbd/meetingappsearchlist"><comm:message key='meeting.m.myaccraditation'/></a></li>
            <li class="active"><a style="COLOR:blue;" href="<%=request.getContextPath()%>/meeadmdbd/meetingsearchlist"><comm:message key='meeting.m.consultation'/></a></li>
        	<li><a href="<%=request.getContextPath()%>/meeadmdbd/reportManagement">报表管理</a></li>
        </ul>
    </div>
</div>
<div id="main" class="c">
  <div id="class-search" class="c">
  <form id="refusereasomform" name="refusereasomform" method="post"   action="<%=request.getContextPath()%>/meeadmdbd/meetingsearchlist" onsubmit="">
		<table width="100%" border="0" cellspacing="5" cellpadding="0">
			<tr>
				<td><comm:message key='meeting.m.app.name'/>：</td>
				<td><input type="text" name="title" id="title" /></td>
				<td><comm:message key='meeting.m.requestlever'/>：</td>
				<td><select name="level.id" id="meetingleveld">
					<option value="-1"><comm:message key='meeting.m.selectdefault'/></option>
					<c:forEach var="c_meetinglevel" items="${basecode['meetinglevel']}">
						<option value="${c_meetinglevel.id}">${c_meetinglevel.name}</option>
					</c:forEach>
				</select></td>
				<td><comm:message key='meeting.m.starttime'/>：</td>
				<td>
				<input type="text" name="startTime" id="startTime"  onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})"  style="border:#999 1px solid;height:20px;background:#fff url(<%=request.getContextPath()%>/resources/My97DatePicker/skin/datePicker.gif) no-repeat right;" readonly="readonly" size="25"/>
				</td>
				<td><comm:message key='meeting.m.app.type'/>：</td>
				<td><select name="meetingType.id" id="meetingType.id">
					<option value="-1"><comm:message key='meeting.m.selectdefault'/></option>
					<c:forEach var="c_meetingtype" items="${basecode['meetingtype']}">
						<option value="${c_meetingtype.id}">${c_meetingtype.name}</option>
					</c:forEach>
				</select></td>				
			</tr>
			<tr>

				<td>&nbsp;&nbsp;&nbsp;&nbsp;<comm:message key='meeting.m.app.request'/>：</td>
				<td><input type="text" name="creator.name" id="creator.name" /></td>		
				<td>&nbsp;&nbsp;&nbsp;&nbsp;<comm:message key='meeting.m.room'/>：</td>
				<td><select name="meetingRoomId.id" id="meetingRoomId.id">
					<option value="-1"><comm:message key='meeting.m.selectdefault'/></option>
					<c:forEach var="c_meetingroom" items="${basecode['meetingroom']}">
						<option value="${c_meetingroom.id}">${c_meetingroom.name}</option>
					</c:forEach>
				</select></td>
				<td><comm:message key='meeting.m.endtime'/>：</td>
				<td>
				<input type="text" name="endTime" id="endTime"  onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})"  style="border:#999 1px solid;height:20px;background:#fff url(<%=request.getContextPath()%>/resources/My97DatePicker/skin/datePicker.gif) no-repeat right;" readonly="readonly" size="25"/>
				</td>
				<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<comm:message key='meeting.m.state'/>：</td>
				<td ><select name="state" id="state">
					<option value="-1"><comm:message key='meeting.m.selectdefault'/></option>
					<option value="${MEETING_STATE_NONE }" ><comm:message key='meeting.m.stateinvalid'/></option>
      		  		<option value="${MEETING_STATE_PENDING }"><comm:message key='meeting.m.statenotstart'/></option>
      		  		<option value="${MEETING_STATE_START }" ><comm:message key='meeting.m.statehavastart'/></option>
      		  		<option value="${MEETING_STATE_END }"><comm:message key='meeting.m.stateend'/></option>
      		  		<option value="${MEETING_STATE_ABSENCE}" ><comm:message key='meeting.m.stateabsence'/></option>
				</select></td>
			</tr>
			<tr>
				<td align="right" colspan="8" ><input class="button"
					type="submit" name="button" id="button" value="<comm:message key='meeting.m.search'/>" /></td>
			</tr>
		</table>
	
	</form>
  </div>
    <div id="class-table" class="c">
    <ec:table tableId="ec" 
			var="meeting"
			items="meetingsearch_list"
			action="${root}/meeadmdbd/meetingsearchlist"
			title=""
			retrieveRowsCallback=""
			filterRowsCallback=""
			sortRowsCallback="" 
			xlsFileName="Reoprt.xls"
			sortable="false"
			>
			<ec:row style="width:1px">
				<ec:column property="title" title="meeting.m.app.name" />
				<ec:column property="meetingRoomId.name" title="meeting.m.room" />
				<ec:column property="meetingType.name" title="meeting.m.app.type" />
			 	<ec:column property="content" title="meeting.m.content" />
			 	<ec:column property="creator.name" title="meeting.m.app.request" />
			 	<ec:column property="startTime" title="meeting.m.starttime" cell="date" format="yyyy-MM-dd HH:mm" />
			 	<ec:column property="endTime" title="meeting.m.endtime" cell="date" format="yyyy-MM-dd HH:mm" />
			 	<ec:column property="state" title="meeting.m.state" sortable="true">
			 		<c:if test="${meeting.state==0}"><comm:message key='meeting.m.stateinvalid'/></c:if>
			 		<c:if test="${meeting.state==1}"><comm:message key='meeting.m.statenotstart'/></c:if>
			 		<c:if test="${meeting.state==2}"><comm:message key='meeting.m.statehavastart'/></c:if>
			 		<c:if test="${meeting.state==3}"><comm:message key='meeting.m.stateend'/></c:if>
			 		<c:if test="${meeting.state==4}"><comm:message key='meeting.m.stateabsence'/></c:if>
			 	</ec:column>
				<ec:column property="null" title="meeting.m.operate" sortable="false" viewsAllowed="html" resizeable="false" style="text-align:center;">
				  <a style="COLOR:blue;" style='color:blue' href="<%=request.getContextPath()%>/meeadmdbd/viewmeeting/${meeting.id}" class="various3"><comm:message key='meeting.m.view'/></a>
				  &nbsp<c:if test="${meeting.state==3}"><a style="COLOR:blue;" style='color:blue' href="<%=request.getContextPath()%>/unified/meetOpintion?opintionmeetingid=${meeting.id}&userType=2" class="various3" ><comm:message key='unified.opintion'/></a></c:if>
				</ec:column>
			</ec:row>
		</ec:table>
    </div>
</div>
<div id="footer" class="c">
	<div id="copyright" class="c"><comm:message key='system.software_copy'/></div>
</div>
<script language="javascript"> 
//table隔行换色
function tr_color(o,a,b,c,d){
 var t=document.getElementById(o).getElementsByTagName("tr");
 for(var i=0;i<t.length;i++){
  t[i].style.backgroundColor=(t[i].sectionRowIndex%2==0)?a:b;

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
<script language="JavaScript" type="text/javascript">
		function exitsys()
		{
   			 var ask=confirm("<comm:message key='admin.exit_sure'/>\n\n<comm:message key='admin.click_ok'/>");
    		 if(ask)
				parent.location.href="${root}/login/exit";
		}
</script>

</body>
</html>