<%@ page isELIgnored="false" pageEncoding="utf-8"%>
<%@include file="/common.jsp"%>
<%
	String returnmessage = (String) request.getSession().getAttribute(
			"returnmessage");
	request.getSession().removeAttribute("returnmessage");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title><comm:message key="unified.application" />
</title>
<comm:pageHeader hasEcList="false" />
<link href="${pageContext.request.contextPath}/resources/css/style.css" rel="stylesheet" type="text/css" />
<link href="${pageContext.request.contextPath}/resources/style/main.css" rel="StyleSheet" type="text/css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery.js"></script>
<link href="${pageContext.request.contextPath}/resources/jqueryalerts/jquery.alerts.css" rel="stylesheet" type="text/css" media="screen" />
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/jqueryalerts/jquery.alerts.js"></script>
<script type="text/javascript">
  function myAlert(){
	var message='<%=returnmessage%>';
	if(message&&message!=""&&message!="null") {
		jAlert(message, "<comm:message key='meeting.m.infotishi'/>");
		}
	}

  $(function(){
	  	$("#deptpersonid").empty();
	  	$.post('${pageContext.request.contextPath}/meeadmdbd/ajaxGetZhuanJian?depart=' + ${vmapp.deptName.id},  function(json) {
			$.each(json, function(i,n){
				$("<option value="+json[i].id+ ">"+json[i].name+"</option>").appendTo("#deptpersonid"); 	
			});
			$("#deptpersonid").val('${vmapp.userName.userId}');
		}, 'json');

		$('#deptmentid').change(function(){
			$("#deptpersonid").empty();
			$.ajax({type :'POST',  url : '${pageContext.request.contextPath}/meeadmdbd/ajaxGetZhuanJian', 
				data : {depart : $(this).val()},
				dataType : 'json',
				dataFilter : function(json, type){
					if(json.indexOf('<html>') != -1){
						window.location.href="<%=request.getContextPath()%>/index.jsp?message=system.session_expire";
						return;
					}
					return json;
				},
				success :  function(json) {
					$.each(json, function(i,n){
							$("<option value="+json[i].id+ ">"+json[i].name+"</option>").appendTo("#deptpersonid"); 	
					});   
				}
			});
		});

		$("#modify").click(function(){
			var expecttime = document.getElementById("expecttime").value;
			 if(expecttime == "") {
				 jAlert("建议日期不可以为空", "<comm:message key='meeting.m.infotishi'/>");
				 return;
			 }
			$.post('${pageContext.request.contextPath}/unified/editVideoApplicationPurpose',
					{id : ${vmapp.id}, deptmentid : $("#deptmentid").val(), deptpersonid : $('#deptpersonid').val(), expecttime: $('#expecttime').val()},
					function(text){
						if(text=='succ'){
							jAlert("<comm:message key='meeting.m.submitsucc'/>", "<comm:message key='meeting.m.infotishi'/>", function() {
								parent.location.href="<%=request.getContextPath()%>/unifiedindex/applyForLectureList";
							});
						}
					}, 
			'text');
		});
	});
	</script>
<style>
#center{background-color:#ffffff;}
#title1{color:#3c3645;font-size:18px;font-weight: bold;height:45px;margin-top:25px;margin-bottom:20px;margin-left:40px;margin-right:40px;border-bottom:1px dashed #3c3645;}
html,body {
	margin: 0;
	height: 100%;
	font: 14px "宋体", Arial;
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
	background:
		url(${pageContext.request.contextPath}/resources/images/bgbg1.gif);
}
</style>
</head>
<body class="bg" onload="myAlert();">
	<div id="center">
		<form method="post">
		<input type="hidden" name="id" value="${vmapp.id }"/>
			<div id="title1">
				<span><comm:message key='unified.video.application.edit' /></span>
			</div>
			<table id="shenqing" class="shenqing_table" border="0">
				<tr>
					<td width="20%"  align="right"><comm:message key='meeting.m.porpose' />：</td>
					<td>${vmapp.videoApplicationPurpose.name}</td>
				</tr>
				<tr>
					<td width="20%" align="right"><comm:message key='unified.lecture.content' />：</td>
					<td>${vmapp.lectureContent}</td>
				</tr>
				<tr>
					<td width="20%" align="right">
						<comm:message key='unified.lecture.people.dept' />：&nbsp;
					</td>
					<td><select name="deptmentid" id="deptmentid" style="width:130px;" >
							<c:forEach var="deptment" items="${basecode['deptment']}">
								<option value="${deptment.id}" <c:if test="${deptment.id == vmapp.deptName.id}">selected</c:if>>${deptment.name}</option>
							</c:forEach>
						</select> &nbsp;&nbsp;&nbsp;&nbsp;
						<comm:message key='unified.lecture.people.name' />：
						<select name="deptpersonid" id="deptpersonid" style="width:130px;">
						</select>
					</td>
				</tr>
				<tr>
					<td width="20%" align="right"><comm:message key='unified.applicationlevel' />：&nbsp;</td>
					<td>${vmapp.level.name}&nbsp;&nbsp;&nbsp;&nbsp;
						<comm:message key='unified.lecture.people.position' />：${vmapp.applicationPosition.name }
					</td>
				</tr>
				<tr>	
				    <td width="20%"  align="right">建议日期：<span class="required">*</span> 
					 </td>
					 <td>
					     <input type="text" name="expecttime" id="expecttime" onclick="WdatePicker({dateFmt:'yyyy-M-d H:mm',minDate:'%y-%M-%d %H:%m:%s',maxDate:'{%y+1}-%M-%d 21:00:00'})" style="border:#999 1px solid;height:20px;background:#fff url(<%=request.getContextPath()%>/resources/My97DatePicker/skin/datePicker.gif) no-repeat right;" readonly="readonly" size="25" value="${vmapp.suggestDate}"/>
					</td>
				</tr>
				<tr>
					<td width="20%"  align="right"><comm:message key='unified.requester.administrator' />：</td>
					<td><div class="main1_sub">
							<table width="100%" border="0">
					             <tr>
									<td align="right"><comm:message key='unified.department.name' />： </td>
					                <td align="center">
										<c:if test="${vmapp.videoRequester.userType.value == 4}">
											<c:if test="${fn:substring(vmapp.videoRequester.deptId.parentDeptCode, 0, 6) == '001001'}">
												<input type="text" name="Email" id="requester_email" value="<comm:message key='unified.beijing.his' />" style="background: transparent; border: 1px solid #ffffff;"readonly />
											</c:if>
											<c:if test="${fn:substring(vmapp.videoRequester.parentDeptCode, 0, 6) == '001003'}">
											     <input type="text" name="Email" id="requester_email" value="${user.deptId.name}" style="background: transparent; border: 1px solid #ffffff;"readonly />
											</c:if>
										</c:if>
										<c:if test="${vmapp.videoRequester.userType.value == 5}">
											<input type="text" name="Email" id="requester_email" value="${user.deptId.name}" style="background: transparent; border: 1px solid #ffffff;"readonly />
										</c:if>&nbsp;					                  
					                </td>
					                <td align="right"><comm:message key='unified.suoshu.department' />：</td>
					                <td align="center">
			                            <input type="text" name="Email" id="requester_email" value="${user.deptId.name}" style="background: transparent; border: 1px solid #ffffff;"readonly />						                  
					                </td>
					             </tr>
					             <tr>
					                  <td align="right"><comm:message key='unified.name' />： </td>
					                  <td align="center">
											<input type="text" name="name" id="requesterid" value="${user.name}" style="background: transparent; border: 1px solid #ffffff;" readonly />&nbsp;					                  
					                  </td>
					                  <td align="right"><comm:message key='admin.user_mobile' />：</td>
					                  <td align="center">
			                                           <input type="text" name="telephone" id="requester_phone" value="${user.mobile}" style="background: transparent; border: 1px solid #ffffff;" readonly />&nbsp;					                  
					                  </td>
					             </tr>
					             <tr>
					                  <td align="right"><comm:message key='unified.Email' />： </td>
					                  <td align="center">
											<input type="text" name="Email" id="requester_email" value="${user.mail}" style="background: transparent; border: 1px solid #ffffff;"readonly />&nbsp;					                  
					                  </td>
					                  <td align="right"><comm:message key='admin.user_tel' />：</td>
					                  <td align="center">
								<input type="text" name="telephone" id="requester_phone" value="${user.telephone}" style="background: transparent; border: 1px solid #ffffff;" readonly />
								<input type="hidden" name="applicantid" id="applicantid" value="" />&nbsp;					                  
					                  </td>
					             </tr>
					        </table>	
						</div>
					</td>
				</tr>
				<tr>
					<td colspan="2" align="center"><input class="button" id="modify" type="button" value="<comm:message key='comm.modify'/>" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					</td>
				</tr>
			</table>
			</form>
		</div>
</body>

</html>