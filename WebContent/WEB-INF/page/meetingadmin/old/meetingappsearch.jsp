<%@ page pageEncoding="UTF-8" isELIgnored="false"%>
<%@include file="/common.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<comm:pageHeader />
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
	<title><comm:message key="system.system_name" /></title>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/My97DatePicker/WdatePicker.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/fancybox/jquery.mousewheel-3.0.4.pack.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/fancybox/jquery.fancybox-1.3.4.pack.js"></script>
	<link href="<%=request.getContextPath()%>/resources/style/main.css" rel="StyleSheet" type="text/css"/>
	<link href="<%=request.getContextPath()%>/resources/style/comm.css" rel="StyleSheet" type="text/css"/>
	<link  href="<%=request.getContextPath()%>/resources/fancybox/jquery.fancybox-1.3.4.css"rel="stylesheet" type="text/css" media="screen" />
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

	$(function(){
		var ecside1 = new ECSide("ec");
	    ecside1.doPrep=false;
	    ecside1.doPrepPrev=false;
	    ecside1.init();

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

		if(parseInt($('#ec')[0].scrollHeight) < 300){
			$('#ec').css('height', $('#ec')[0].scrollHeight + screen.availHeight - document.body.scrollHeight);
		}
	});

</script>
<style type="text/css">
    A:link { COLOR:blue; TEXT-DECORATION:underline} 
	A:visited{COLOR:#000000;TEXT-DECORATION:underline} 
	A:hover{COLOR:#ff0000;TEXT-DECORATION:none}
</style>
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
        	<li ><a style="COLOR:blue;" href="<%=request.getContextPath()%>/meeadmdbd/listmeetingaplication"><comm:message key='meeting.m.homepage'/></a></li>
            <li class="active"><a style="COLOR:blue;" href="<%=request.getContextPath()%>/meeadmdbd/meetingappsearchlist"><comm:message key='meeting.m.myaccraditation'/></a></li>
            <li><a style="COLOR:blue;" href="<%=request.getContextPath()%>/meeadmdbd/meetingsearchlist"><comm:message key='meeting.m.consultation'/></a></li>
            <li><a style="COLOR:blue;" href="<%=request.getContextPath()%>/meeadmdbd/reportManagement">报表管理</a></li>
        </ul>
    </div>
</div>
<div id="main" class="c">
  <div id="class-search" class="c">
  <form id="refusereasomform" name="refusereasomform" method="post" action="<%=request.getContextPath()%>/meeadmdbd/meetingappsearchlist">
		<table width="100%" border="0" cellspacing="5" cellpadding="0">
			<tr>
				<td align="right"><comm:message key='meeting.m.app.name'/>：</td>
				<td><input type="text" name="title" id="title" style="width: 134px;"/></td>
				<td align="right"><comm:message key='meeting.m.requestlever'/>：</td>
				<td><select name="level.id" id="meetingleveld">
					<option value="-1"><comm:message key='meeting.m.selectdefault'/></option>
					<c:forEach var="c_meetinglevel" items="${basecode['meetinglevel']}">
						<option value="${c_meetinglevel.id}">${c_meetinglevel.name}</option>
					</c:forEach>
				</select></td>
				<td align="right"><comm:message key='meeting.m.app.expected.time'/>：</td>
				<td>
				<input type="text" name="expectedTime" id="expectedTime" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});" style="border:#999 1px solid;height:20px;background:#fff url(<%=request.getContextPath()%>/resources/My97DatePicker/skin/datePicker.gif) no-repeat right;" readonly="readonly" size="25"/>
				</td>
				<td align="right"><comm:message key='meeting.m.app.type'/>：</td>
				<td><select name="meetingType.id" id="meetingType.id">
					<option value="-1"><comm:message key='meeting.m.selectdefault'/></option>
					<c:forEach var="c_meetingtype" items="${basecode['meetingtype']}">
						<option value="${c_meetingtype.id}">${c_meetingtype.name}</option>
					</c:forEach>
				</select></td>
			</tr>
			<tr>
				<td align="right"><comm:message key='meeting.m.app.request'/>：</td>
				<td><input type="text" name="requester.name" id="requester.name"  style="width: 134px;"/></td>				
				<td align="right"><comm:message key='meeting.m.app.depart'/>：</td>
				<td><select name="department.deptcode" id="department.deptcode">
					<option value="-1"><comm:message key='meeting.m.selectdefault'/></option>
					<c:forEach var="c_deptment" items="${basecode['deptment']}">
                		<option value="${c_deptment.deptcode}">${c_deptment.name}</option>  
                	</c:forEach>   
				</select></td>
				<td align="right"><comm:message key='meeting.m.appTime'/>：</td>
				<td>
				<input type="text" name="applicationTime" id="applicationTime" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});" style="border:#999 1px solid;height:20px;background:#fff url(<%=request.getContextPath()%>/resources/My97DatePicker/skin/datePicker.gif) no-repeat right;" readonly="readonly" size="25"/>
				</td>				
				<td align="right"><comm:message key='meeting.m.state'/>：</td>
				<td ><select name="state" id="state">
					<option value="-1" ><comm:message key='meeting.m.selectdefault'/></option>
					<option value="${MEETING_APPLICATION_STATE_PENDING}" ><comm:message key='meeting.m.statewait'/></option>
      		  		<option value="${GlobalConstent.MEETING_APPLICATION_STATE_PASS }"><comm:message key='meeting.m.statepass'/></option>
      		  		<option value="${GlobalConstent.MEETING_APPLICATION_STATE_REFUSED }"><comm:message key='meeting.m.staterefuse'/></option>
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
			action="${root}/meeadmdbd/meetingappsearchlist"
			title=""
			retrieveRowsCallback=""
			filterRowsCallback=""
			sortRowsCallback="" 
			xlsFileName="Reoprt.xls"
			sortable="false"
			>
			<ec:row>
				<ec:column property="purpose" title="meeting.m.app.name" />
				<ec:column property="meetingType.name" title="meeting.m.app.type" />
				<ec:column property="requester.name" title="meeting.m.app.request" />
			 	<ec:column property="applicationTime" title="meeting.m.appTime" sortable="true" cell="date" format="yyyy-MM-dd HH:mm" />
			 	<ec:column property="expectedTime" title="unified.datetime"  sortable="true" cell="date" format="yyyy-MM-dd HH:mm" />
			 	<ec:column property="state" title="meeting.m.state" sortable="true">
			 		<c:if test="${meeting.state==1}"><comm:message key='meeting.m.statewait'/></c:if>
			 		<c:if test="${meeting.state==2}"><comm:message key='meeting.m.statepass'/></c:if>
			 		<c:if test="${meeting.state==3}"><comm:message key='meeting.m.staterefuse'/></c:if>
			 	</ec:column>
				<ec:column property="null" title="meeting.m.operate" sortable="false" viewsAllowed="html" resizeable="false" style="text-align:center;">
				 	<a style="COLOR:blue;" href="<%=request.getContextPath()%>/meeadmdbd/viewappmeeting/${meeting.id}" class="various3"><comm:message key='meeting.m.view'/></a>
				 	<c:if test="${meeting.state == 1}">
    					&nbsp<a style="COLOR:blue;" href="<%=request.getContextPath()%>/meeadmdbd/redirctmeetingpass?meetingappserchflag=1&requestmeetid=${meeting.id}" class="various3"><comm:message key='meeting.m.through'/></a>
    					&nbsp<a style="COLOR:blue;" href="<%=request.getContextPath()%>/meeadmdbd/redirctrefuse?meetingappserchflag=1&refusemeetingid=${meeting.id}" class="various3"><comm:message key='meeting.m.refuse'/></a>
				 	</c:if>
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