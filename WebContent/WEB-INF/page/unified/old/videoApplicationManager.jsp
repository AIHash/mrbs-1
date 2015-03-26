<%@include file="/common.jsp"%>
<%@ page pageEncoding="utf-8" isELIgnored="false"%>
<%
 String returnmessage=(String)request.getSession().getAttribute("returnmessage");
 request.getSession().removeAttribute("returnmessage"); 
%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title><comm:message key="system.system_name" /></title>
	<comm:pageHeader />
	<link href="${pageContext.request.contextPath}/resources/css/style.css"	rel="stylesheet" type="text/css" />
	<link href="<%=request.getContextPath()%>/resources/fancybox/jquery.fancybox-1.3.4.css" rel="stylesheet" type="text/css" media="screen" />
	<link href="<%=request.getContextPath()%>/resources/jqueryalerts/jquery.alerts.css" rel="stylesheet" type="text/css" media="screen" />
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/My97DatePicker/WdatePicker.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/jqueryalerts/jquery.ui.draggable.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/jqueryalerts/jquery.alerts.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/fancybox/jquery.mousewheel-3.0.4.pack.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/fancybox/jquery.fancybox-1.3.4.pack.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/validate.js"></script>

	<script type="text/javascript"> 

function begintime()
{
	var obj=document.getElementById("datetime");
	setDayHM(obj);
}
function resizeauto()
{
	if(parseInt(document.getElementById("ec").scrollHeight,"10")<400)
    {
		document.getElementById("ec").style.height=document.getElementById("ec").scrollHeight+screen.availHeight-document.body.scrollHeight-260+"px";
	}
}

   function init(){
      var ecside1=new ECSide("ec");
      ecside1.doPrep=false;
      ecside1.doPrepPrev=false;
      ecside1.init();
   }
		$(document).ready(function() {
			$(".various3").fancybox({
				'width'				: '75%',
				'height'			: '75%',
				'autoScale'			: false,
				'transitionIn'		: 'none',
				'transitionOut'		: 'none',
//				'padding'			: '50px',
				'type'				: 'iframe'
			});
			if(parseInt($('#ec')[0].scrollHeight) < 300){
				$('#ec').css('height', $('#ec')[0].scrollHeight + screen.availHeight - document.body.scrollHeight);
			}
		});

		function myAlert()
		{
			   var message='<%=returnmessage%>';
			    if(message&&message!=""&&message!="null")
			    {
			      jAlert(message, "<comm:message key='meeting.m.infotishi'/>");
			    }
		}
	</script>
	<style type="text/css">
	   input {height:20px;}
        select{background: #ffffff;border: 1px solid #7F9DB9;width:180px;color:#000000;height:24px;}
	</style>
</head>
<body style="padding:0; margin:0;background:url('../resources/images/theme/bg_right-2.gif');" onload="init();resizeauto();myAlert();heigthReset(64);" class="bg" onresize="heigthReset(64);">
<div id="center">
		<comm:navigator position="unified.applicationManager>>vedio.app" />
	<div id="main" >
			<form name="form1" method="post"
				action="<%=request.getContextPath()%>/unifiedindex/videoApplicationSearch">
				<table width="100%" border="0" cellspacing="5" cellpadding="0">
					<tr>
						<td align="right" class="text_left_red2"><comm:message
								key='unified.datetime' />：</td>
						<td><input type="text" name="expectedTime" id="expectedTime"
							onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})"
							style="border: 1px solid #7F9DB9;height:20px;background:#fff url(<%=request.getContextPath()%>/resources/My97DatePicker/skin/datePicker.gif) no-repeat right;"
							readonly="readonly" size="25" /></td>

						<td align="right" class="text_left_red2"><comm:message
								key='unified.app.time' />：</td>
						<td><input type="text" name="applicationTime"
							id="applicationTime"
							onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})"
							style="border: 1px solid #7F9DB9;height:20px;background:#fff url(<%=request.getContextPath()%>/resources/My97DatePicker/skin/datePicker.gif) no-repeat right;"
							readonly="readonly" size="25" /></td>
						<td align="right" class="text_left_red2"><comm:message key='unified.purpose' />：</td>
						<td><select name="videoApplicationPurpose.id" id="videoApplicationPurpose" style="width:130px;">
						<option value="-1" selected="selected">	<comm:message key='unified.status.all' /></option>
									<c:forEach var="videoApplicationPurpose" items="${basecode['videoApplicationPurpose']}">
										<option value="${videoApplicationPurpose.id}">${videoApplicationPurpose.name}</option>
									</c:forEach>
							</select></td>
					</tr>
					<tr>
						<td align="right" class="text_left_red2"><comm:message key='unified.lecture.people.dept' />：</td>
						<td><select name="deptmentid" id="deptmentid" style="width:160px;">
						<option value=""><comm:message key='unified.status.all'/></option>
										<c:forEach var="deptment" items="${basecode['deptment']}">
											<option value="${deptment.id}">${deptment.name}</option>
										</c:forEach>
								</select>
						</td>

						<td align="right" class="text_left_red2"><comm:message key='unified.lecture.people.name' />：</td>
						<td><select name="deptpersonid" id="deptpersonid" style="width:160px;">
						<option value=""><comm:message key='unified.status.all'/></option>
								</select></td>
						<td align="right" class="text_left_red2"><comm:message key='unified.lecture.people.position' />：</td>
						<td><select name="positionld" id="positionld" style="width:130px;">
						<option value="-1" selected="selected">	<comm:message key='unified.status.all' /></option>
								<c:forEach var="applicationPosition"
									items="${basecode['applicationPosition']}">
									<option value="${applicationPosition.id}">${applicationPosition.name}</option>
								</c:forEach>
						     </select>
						</td>
					</tr>
			      <tr>
						<td align="right" class="text_left_red2"><comm:message key='meeting.m.meetinglevel' />：</td>
						<td><select name="meetingleveld" id="meetingleveld" style="width:160px;">
						<option value="-1" selected="selected">	<comm:message key='unified.status.all' /></option>
								<c:forEach var="meetinglevel"
									items="${basecode['meetinglevel']}">
									<option value="${meetinglevel.id}">${meetinglevel.name}</option>
								</c:forEach>
						</select>
						</td>
						<td align="right" class="text_left_red2"><comm:message key='unified.lecture.content' />：</td>
						<td><input type="text" name="porpose" id="porpose" class="text-long" size="25" value="" />
						</td>
					</tr>
					<tr>
						<td align="right" colspan="6"><input class="button"
							type="submit" name="button" id="button"
							value="<comm:message key='unified.selected'/>" /></td>
					</tr>
				</table>
			</form>
			<ec:table tableId="ec"
	var="data"
	items="report_data"
	action="${root}/unifiedindex/videoApplicationSearch"
	title=""
	width="100%"
	sortable="false"
	retrieveRowsCallback=""
	filterRowsCallback=""
	sortRowsCallback=""
	xlsFileName="Reoprt.xls">
		<ec:row>
			<ec:column property="videoApplicationPurpose.name" title="unified.purpose" width="20%"/>
			<ec:column property="videoRequester.name" title="unified.meeting.requester" width="10%"/>
			<ec:column property="suggestDate" title="unified.datetime" cell="date" format="yyyy-MM-dd HH:mm" sortable="true"  width="12%"/>
			<ec:column property="appDate" title="unified.app.time" cell="date" format="yyyy-MM-dd HH:mm" sortable="true"  width="14%"/>
			<ec:column property="meetingType.name" title="unified.meetingtype" width="10%"/>
			<ec:column property="state" title="unified.status" sortable="true"  width="8%">
				<c:choose>
					<c:when test="${data.state == MEETING_APPLICATION_STATE_PENDING }">
						<comm:message key="unified.status.pending" />
					</c:when>
					<c:when test="${data.state == MEETING_APPLICATION_STATE_PASS }">
						<comm:message key="unified.status.pass" />
					</c:when>
					<c:when test="${data.state == MEETING_APPLICATION_STATE_REFUSED }">
						<comm:message key="unified.status.refused" />
					</c:when>
				</c:choose>
			</ec:column>
			<ec:column property="null" title="unified.operater" sortable="false"  width="14%">
				<c:choose>
					<c:when test="${data.state == MEETING_APPLICATION_STATE_PENDING }">
						<a style="COLOR:blue;" href='${pageContext.request.contextPath}/unified/applictionView/${data.id}' class='various3'><comm:message key="unified.detail"/></a>
					</c:when>
					<c:when test="${data.state == MEETING_APPLICATION_STATE_PASS }">
						<a style="COLOR:blue;" href='${pageContext.request.contextPath}/unified/applictionView/${data.id}' class='various3'><comm:message key="unified.detail"/></a>
					</c:when>
					<c:when test="${data.state == MEETING_APPLICATION_STATE_REFUSED }">
						<a style="COLOR:blue;" href='${pageContext.request.contextPath}/unified/applictionView/${data.id}' class='various3'><comm:message key="unified.detail"/></a>
					</c:when>
				</c:choose>
			</ec:column>
		</ec:row>
	</ec:table>
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
  };
  t[i].onmouseover=function(){
   if(this.x!="1")this.style.backgroundColor=c;
  };
  t[i].onmouseout=function(){
   if(this.x!="1")this.style.backgroundColor=(this.sectionRowIndex%2==0)?a:b;
  };
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
