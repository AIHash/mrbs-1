<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@include file="/common.jsp"%>
<%@ page pageEncoding="utf-8" isELIgnored="false"%>
<%
 String returnmessage=(String)request.getSession().getAttribute("returnmessage");
 request.getSession().removeAttribute("returnmessage"); 
%>

<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title><comm:message key="common.videoLectures.applyForLecture" /></title>
	<comm:pageHeader hasEcList="false"/>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/style.css"/>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/jqueryalerts/jquery.alerts.css" media="screen" />
	<link rel="stylesheet" href="${pageContext.request.contextPath }/resources/jqueryui/css/jquery-ui-1.8.16.custom.css" />
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/My97DatePicker/WdatePicker.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/jqueryalerts/jquery.ui.draggable.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/jqueryalerts/jquery.alerts.js"></script>
	<script src="${pageContext.request.contextPath }/resources/jqueryui/ui/jquery.ui.core.min.js"></script>
	<script src="${pageContext.request.contextPath }/resources/jqueryui/ui/jquery.ui.widget.min.js"></script>
	<script src="${pageContext.request.contextPath }/resources/jqueryui/ui/jquery.ui.position.min.js"></script>
	<script src="${pageContext.request.contextPath }/resources/jqueryui/ui/jquery.ui.autocomplete.min.js"></script>
	<script type="text/javascript"> 
		$(function(){
			$('#deptmentid').change(function(){
				$("#userName").empty();
				$("<option value='-1'><comm:message key='unified.status.all' /></option>").appendTo("#userName");
			     //动态的给主讲人姓名填值
			     $.ajax({type :'POST',  url : '${pageContext.request.contextPath}/meeadmdbd/ajaxGetZhuanJian', 
						data : {'depart' : $(this).val()},
						dataType : 'json',
						dataFilter : function(json, type){
							if(json.indexOf('<html>') != -1){
								window.location.href="<%=request.getContextPath()%>/index.jsp?message=system.session_expire";
								return;
							}
							return json;
						},
						success : function(json) {
							$.each(json, function(i, item){
								$("<option value=" + item.id+ ">" + item.name + "</option>").appendTo("#userName");
							});
				}});
			});
		});
		
		/* $(function(){
			$('#autoDepartmentName').autocomplete({
				source		: '${root}/meeadIndex/searchDept',
				minLength	: 2,
				scroll		: true,
				select		: function(event, ui) {
					$('#autoDepartmentName').val(ui.item.label);
					//$('#departmentName').val(ui.item.label);
					return false;
				}
			});			
			$('#userId').autocomplete({
				source		: '${root}/meeadIndex/searchUser',
				minLength	: 2,
				scroll		: true,
				select		: function(event, ui) {
					$('#userId').val(ui.item.label);
					return false;
				}
			});
		}); */
		
		function check(){
			var startTime=document.getElementById("expectedTimeStart").value;
			 var endTime=document.getElementById("expectedTimeEnd").value;
			 if (startTime.length> 1&&endTime.length > 1&&!compareDateString(startTime, endTime)){
				jAlert("<comm:message key='meeting.m.timeRage.notRight'/>", "<comm:message key='meeting.m.infotishi'/>");				
				return false;
			  }
		}
	</script>
	<style type="text/css">
	.ui-autocomplete {
		max-height: 300px;
		overflow-y: auto;
		width: 145px;
	}
    </style>
</head>
<body onload="heigthReset(64);" onresize="heigthReset(64);" style="padding:0; margin:0;background:url('<%=request.getContextPath()%>/resources/images/theme/bg_right-2.gif');">
	<div id="center">
		<comm:navigator position="common.videoLectures>>common.videoLectures.applyForLecture" />
		<div id="main">
		<form name="MyForm" action="${root}/unifiedindex/applyForLectureList" method="post" onsubmit="return check()" target="Report">
		<table border="0" cellspacing="0" cellpadding="0" align="center" width="697">
			<tr id="inputForm"  style="display:block;">
				<td width="1" class='queryBackground'> &nbsp;</td>
				<td class='queryBackground' align="center">
					<table width="695" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="20%"  class="text_left_red2" align="right">
								<comm:message key="meeitng.suggestTime"/>:&nbsp;&nbsp;
						 	</td>
						 	<td width="30%" align="left">
						  		<input type="text" name="expectedTimeStart" id="expectedTimeStart" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" readonly="readonly" size="25" onfocus="this.blur()"
									style="width:84px;height: 20px;line-height:20px;border: 1px solid #7F9DB9;height:20px;background:#fff url(<%=request.getContextPath()%>/resources/My97DatePicker/skin/datePicker.gif) no-repeat right;"
							 	/>
								<span>-</span><span>&nbsp;</span><input type="text" name="expectedTimeEnd" id="expectedTimeEnd"
									onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" onfocus="this.blur()" size="25"
									style="width:84px;height: 20px;line-height:20px;background:#fff url(<%=request.getContextPath()%>/resources/My97DatePicker/skin/datePicker.gif) no-repeat right;"
							 	/>
						 	</td>
						 	<td width="20%" align="right" class="text_left_red2">
						  		<comm:message key="unified.and.expert_unified.state"/>:&nbsp;&nbsp;
						 	</td>
			             	<td width="30%" align="left">
			              		<select name="state" id="state" style="width:187px;height:20px;">
									<option value="-1">
										<comm:message key='unified.status.all' />
									</option>
									<option value="${applicationScope.MEETING_APPLICATION_STATE_PENDING}">
										<comm:message key='unified.status.pending' />
									</option>
									<option value="${applicationScope.MEETING_APPLICATION_STATE_PASS}">
										<comm:message key='unified.status.pass' />
									</option>
									<option value="${applicationScope.MEETING_APPLICATION_STATE_REFUSED}">
										<comm:message key='unified.status.refused' />
									</option>
								</select>
			             	</td>
		<%-- 				 	<td width="25%"  class="text_left_red2" align="right">
						  		<comm:message key="unified.app.time"/>:&nbsp;&nbsp;
						 	</td>
						 	<td width="25%" align="left">
						  		<input type="text" name="appDate" id="appDate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" onfocus="this.blur()" size="25"
									style="width:155px;height: 20px;line-height:20px;background:#fff url(<%=request.getContextPath()%>/resources/My97DatePicker/skin/datePicker.gif) no-repeat right;"
							 	/>
						 	</td> --%>
						</tr>
						<tr>
						 	<td width="20%"  class="text_left_red2" align="right">
						  		<comm:message key="metting.speakerDepartmentName"/>:&nbsp;&nbsp;
						 	</td>
						 	<td width="30%" align="left">
						 	    <input id="autoDepartmentName" name="deptName" style="width:187px;height:20px;"/>
						 		<%-- <select name="deptName" id="deptmentid" style="width:179px;height:20px;">
									<option value="-1"><comm:message key='unified.status.all'/></option>
									<c:forEach var="deptment" items="${basecode['deptment']}">
										<option value="${deptment.id}">${deptment.name}</option>
									</c:forEach>
								</select> --%>
						 	</td>
						 	<td width="20%" align="right" class="text_left_red2">
						  		<comm:message key="metting.speakerName"/>:&nbsp;&nbsp;
						 	</td>
			             	<td width="30%" align="left">
			              		<input name="userId" id="userId" style="width:187px;height:20px;"/>
			              		<%-- <select name="userId" id="userName" style="width:187px;height:20px;">
			              			<option value='-1'><comm:message key='unified.status.all' /></option>
			              		</select> --%>
			             	</td>
						</tr>
						<tr>
						 	<td width="20%"  class="text_left_red2" align="right">
						  		<comm:message key="unified.and.expert_app.keyword"/>:&nbsp;&nbsp;
						 	</td>
						 	<td width="30%" align="left">
						  		<input type="text" name="keyWord" maxlength="50" size="30" style="width:187px;height:20px;line-height:20px;" title="<comm:message key="meeting.m.keyWord.VideoTip"/>"/>
						 	</td>

						</tr>
					</table>
				</td>
				<td width="1" class='queryBackground'></td>
			</tr>
		</table>
		<table align="center" width="697">
			<tr>
			  <td height="30" align="center" valign="bottom" class='queryBackground' colspan="3">
				<table class='buttonToolBar' id="querybuttonBk">
				  <tr>
					<td align="center"><input type="submit" name="new" value=" <comm:message key="comm.view"/>" class="button" >&nbsp;&nbsp;&nbsp;<img id="img1" src="${root}/resources/skin/style/Button_top2.gif" style="cursor:hand;" border="0" onclick="javascript:hideOrDisplayInputForm(inputForm, img1)" alt="<comm:message key='comm.hidden_input_parameter'/>"></td>
				  </tr>
				</table>
			  </td>
			</tr>
			<tr>
				<td colspan="3" class="table_middle_bg"><iframe id="dataFrame" align="top" frameborder="0" name="Report" width="700" scrolling="no" src="${root}/unifiedindex/applyForLectureList"></iframe></td>
			</tr>
		</table>
	</form>
	</div>
	</div>
	<div id="center_right">
		<img src="${pageContext.request.contextPath}/resources/images/theme/bg_right.gif" width="25"/>
	</div>
</body>
</html>
