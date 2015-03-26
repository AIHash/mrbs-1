<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page pageEncoding="UTF-8"%>
<%@include file="/common.jsp"%>
<html>

<head>
	<title><comm:message key="admin.user_manage" /></title>
	<comm:pageHeader hasEcList="false"/>
	<link href="${pageContext.request.contextPath }/resources/css/style.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery.js"></script>
	<script src="<%=request.getContextPath()%>/resources/jqueryalerts/jquery.ui.draggable.js" type="text/javascript"></script>
	<script src="<%=request.getContextPath()%>/resources/jqueryalerts/jquery.alerts.js" type="text/javascript"></script>
	<link href="<%=request.getContextPath()%>/resources/jqueryalerts/jquery.alerts.css" rel="stylesheet" type="text/css" media="screen" />
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/resources/skin/style/main.css"/>
	<script src="${pageContext.request.contextPath}/resources/js/md5.js" type="text/javascript"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/jqueryalerts/jquery.alerts.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/validate.js"></script>
	<script type="text/javascript">
function check(){
	var userName = document.getElementById("userName");
	var userPassword = document.getElementById("userPassword");
	var confirmPassword = document.getElementById("confirmPassword");
	var username = document.getElementById("username");
	var telephone = document.getElementById("telephone");
	var mobile = document.getElementById("mobile");
	var mail = document.getElementById("mail");

	if ((strTrim(userName.value)) == ""){
	   jAlert("<comm:message key='js.need_input' args='admin.user_name' />", "<comm:message key='meeting.m.infotishi'/>");
	   //userName.focus();
	   return false;
	}
	
	if(!checkUserName((strTrim(userName.value)))){
		jAlert("<comm:message key='admin.userName.notRight'/>", "<comm:message key='meeting.m.infotishi'/>");
		//userName.focus();
		return false;		
	}

	var usertype = $('#usertype').val();
	if(usertype == 5){
		if ((strTrim(form.username.value)) == "")
		{
		   jAlert("<comm:message key='js.need_input' args='admin.user_username' />", "<comm:message key='meeting.m.infotishi'/>");
		   //form.username.focus();
		   return false;
		}		
	}

	if ((strTrim(userPassword.value)) != (strTrim(confirmPassword.value))){
	   jAlert("<comm:message key='js.pass_not_newpassword' args='admin.confirm_password,admin.logon_newpassword'/>", "<comm:message key='meeting.m.infotishi'/>");
	  // confirmPassword.focus();
	   return false;
	}

	if(usertype == 5){
		if ((strTrim(form.telephone.value)) == ""){
			jAlert("<comm:message key='js.need_input' args='admin.user_tel' />", "<comm:message key='meeting.m.infotishi'/>");
			   //form.telephone.focus();
			   return false;
		}				 
		if((strTrim(form.telephone.value))!= ""&& !isTelNmbr((strTrim(form.telephone.value)))){
			jAlert("<comm:message key='unified.tel.format'/>", "<comm:message key='meeting.m.infotishi'/>");
		    return false;
		}
	}

	//if ((strTrim(mobile.value)) == ""){
	//	   alert("<comm:message key='js.need_input' args='admin.logon_phone' />");
	//	   mobile.focus();
	//	   return false;
	//}

	if(!(strTrim(mobile.value) == "") && !isNumber(mobile.value))	{
	   jAlert("<comm:message key='js.need_input_number' args='admin.user_mobile' />", "<comm:message key='meeting.m.infotishi'/>");
	   return false;
	}
	
	if(form.mobile.value !=""&&!isMobel(form.mobile.value)){
		  jAlert("<comm:message key='meeting.type.mobilephone.notright'/>", "<comm:message key='meeting.m.infotishi'/>");
		  return false;
	 } 

	//if ((strTrim(mail.value)) == ""){
	//	   alert("<comm:message key='js.need_input' args='admin.logon_email_address' />");
	//	   mail.focus();
	//	   return false;
	//	}	
	
	if ((strTrim(mail.value)) != ""){
		if (!isMailAddress(mail.value)){
			jAlert("<comm:message key='js.need_input_right' args='admin.user_email'/>", "<comm:message key='meeting.m.infotishi'/>");
			return false;
		}
	}

	if(userPassword.value != "" && confirmPassword.value != ""){
		document.getElementById("userPassword").value =  b64_md5(userPassword.value);
	}

	return true;
}

//手机验证
function isMobel(value){  
	if(isNumber(value)&&value.length==11){
	  return true;
	}else{
	  return false;
	}
}

function getusertype(){
	var usertype=$('#usertype').val();
	if(usertype == 5){
		$("#usernameInfo").show(); 
	}else{
		$("#usernameInfo").hide();
	}
};

$(function(){
	$("#submitEditUser").click(function(){
		var flag=check();
		if(flag){
		var usertypes = $('#usertype').val();
		var userid = $("#userId").val();
		var state = $(":radio[name=state][checked]").val(); 
		var allowedOrRefusedFlag = $(":radio[name=allowedOrRefusedFlag][checked]").val(); 
		$.post('${pageContext.request.contextPath}/user/update/userId=' + userid,{userId:userid,userName:$("#userName").val(),password:$("#userPassword").val(),allowedOrRefusedFlag:allowedOrRefusedFlag,userType:usertypes,state:state,username:$("#username").val(),telephone:$("#telephone").val(),mobile:$("#mobile").val(),mail:$("#mail").val(),terminal:$("#terminal").val(),gender:$("#gender").val()}, function(msg){
			if(msg == "editdept_successful"){
					jAlert("<comm:message key='admin.user_edit_success'/>", "<comm:message key='meeting.m.infotishi'/>",function(resultConfirm){
						if(resultConfirm)
							parent.location.href="<%=request.getContextPath()%>/user/queryUser";
					}						
				);
			}else{
				jAlert("<comm:message key='admin.user_edit_fail'/>", "<comm:message key='meeting.m.infotishi'/>");
			}	 
		});
		}else{
			return false;
		}
	});		
});

function cancelEditUser(){
	parent.location.href="<%=request.getContextPath()%>/user/queryUser";
};
</script>
<style type="text/css">
.required{color:red;}
select{background: #ffffff;border: 1px solid #7F9DB9;color:#000000;height: 20px;line-height:20px;}
#titleStyle{color:#3c3645;font-size:18px;font-weight: bold;height:30px;margin-top:15px;margin-bottom:0px;margin-left:20px;margin-right:20px;border-bottom:0px dashed #3c3645;}
#titleStyle2{height:5%;color:#3c3645;font-size:18px;font-weight: bold;margin-top:15px;margin-bottom:0px;margin-left:20px;margin-right:20px;}
#titleStyle2 span{background-color:#dee; display:-moz-inline-box; display:inline-block; width:25px;}
#viewappinfor{width:470px;margin-left:15px;margin-top:0px;margin-bottom:5px;margin-right:15px;position:relative;background-color:#dee;}
.subinfor{border:0px solid #7F9DB9;background-color:#F8F8FF;margin-left:15px;margin-right:15px;}
</style>
</head>

<body leftmargin="5" topmargin="0" onLoad="focus('form','userPassword');getusertype();" >
	<form name="form" onsubmit="return check();">
	  <div id="viewappinfor">
			<div id="titleStyle">
				<span>&nbsp;</span>
			</div>		
            <div class="subinfor" id="patientinfor1">
				<table border="0" cellspacing="0" width="440" vertical-align="middle" cellpadding="0" align="center" height="200">
					<tr>
						<td colspan="4" height="10"></td>
					</tr>
			
					<tr>
						<td width="23%" align="right"><comm:message key="unified.and.expert_user.logon_name"/>:<span class="required">*</span></td>
						<td width="27%" align="left"><input type="text" disabled= "true" name="userId" id="userId" maxlength="16" size="30" style="width:150px" value="${user.userId}"/></td>
						<td width="23%" align="right"><comm:message key="unified.and.expert_user_name"/>:<span class="required">*</span></td>
						<td width="27%" align="left"><input type="text" id="userName" name="userName" maxlength="20" size="30" style="width:150px" value="${user.name}" title=<comm:message key="admin.userName.tip"/> /></td>
					</tr>
					
					<tr>
						<td align="right"><comm:message key="unified.and.expert_admin.new_password"/>:&nbsp;</td>
						<td align="left"><input type="password" name="userPassword" id="userPassword" maxlength="16" size="30" style="width:150px"/></td>
						<td align="right"><comm:message key="admin.confirm_password"/>:&nbsp;</td>
						<td><input type="password" id="confirmPassword" name="confirmPassword" maxlength="16" size="30" style="width:150px"/>
							<input type="hidden" name="password" id="password"/>
						</td>
					</tr>
			
					<tr>
						<td align="right"><comm:message key="admin.user_type"/>:<span class="required">*</span></td>
						<td>
							<c:choose>
								<c:when test="${user.deptId.deptcode=='001003'||fn:substring(user.deptId.deptcode, 0, 6) == '001003'}">
									<select id="usertype" name ="userType" style='width:150px' onchange="getusertype();">
										<c:forEach items="${USER_TYPE}" var="type" varStatus="var">
											<c:if test="${type.value==5}">
												<option value="${type.value}"><comm:message key="${type.name}"/></option>
											</c:if>
										</c:forEach>      
									</select>
								</c:when>
								<c:when test="${user.deptId.deptcode=='001002'||fn:substring(user.deptId.deptcode, 0, 6) == '001002'}">
									<select id="usertype" name ="userType" style='width:150px' onchange="getusertype();">
										<c:forEach items="${USER_TYPE}" var="type" varStatus="var">
											<c:if test="${type.value==4}">
												<option value="${type.value}"><comm:message key="${type.name}"/></option>
											</c:if>
										</c:forEach>      
									</select>
								</c:when>
								<c:otherwise>
									<select id="usertype" name ="userType" style='width:150px' onchange="getusertype();">
										<c:forEach items="${USER_TYPE}" var="type" varStatus="var">
											<c:if test="${type.value!=5}">
												<option value="${type.value}" <c:if test="${user.userType.value == type.value}">selected</c:if>><comm:message key="${type.name}"/></option>
											</c:if>
										</c:forEach>      
									</select>
								</c:otherwise>
							</c:choose>
							
						</td>
						<td align="right"><comm:message key="unified.and.expert_meeting.m.state"/>:<span class="required">*</span></td>
						<td><input type="radio" style="border:0px;background:#F8F8FF;height:13px;vertical-align:middle;align:absmiddle;" name="state" value="${USER_STATE_ON}" <c:if test="${user.state == USER_STATE_ON}">checked</c:if> /><comm:message key="admin.user_state_on"/>&nbsp;&nbsp;<input type="radio" style="border:0px;background:#F8F8FF;height:13px;vertical-align:middle;align:absmiddle;" name="state" value="${USER_STATE_OFF}" <c:if test="${user.state == USER_STATE_OFF}">checked</c:if> /><comm:message key="admin.user_state_off"/></td>
					</tr>
					<tr id="usernameInfo" style="display:none;">
						<td align="right"><comm:message key="unified.and.expert_username"/>:<span class="required">*</span></td>
						<td><input type="text" name="username" id="username"  maxlength="32" style="width:150px" value="${user.username}" ></td>
						<td align="right"><comm:message key="admin.user_tel"/>:<span class="required">*</span></td>
						<td align="left"><input type="text" name="telephone" id="telephone" maxlength="25" size="25" style="width:150px" value="${user.telephone}"></td>
					</tr>
			
					<tr>
						<td align="right"><comm:message key="unified.and.expert_mobile"/>:&nbsp;</td>
						<td><input type="text" id="mobile" name="mobile"  maxlength="32" style="width:150px" value="${user.mobile}" ></td>
						<td align="right"><comm:message key="admin.user_email"/>:&nbsp;</td>
						<td align="left"><input type="text" id="mail" name="mail" maxlength="50" size="50" style="width:150px" value="${user.mail}" ></td>
					</tr>
			
					<tr>
						<td align="right"><comm:message key="admin.user_terminal"/>:&nbsp;</td>
						<td><input type="text" name="terminal" id="terminal" maxlength="100" style="width:150px" value="${user.videoPoint}"></td>
						<td align="right"><input type="hidden" name="gender" id="gender" ><comm:message key="unified.and.expert_admin.allowed_or_refused"/>:<span class="required">*</span></td>
						<td><input type="radio" style="border:0px;background:#F8F8FF;height:13px;vertical-align:middle;align:absmiddle;" name="allowedOrRefusedFlag" value="${USER_STATE_ON}" <c:if test="${user.allowedOrRefusedFlag == USER_STATE_ON}">checked</c:if> /><comm:message key="comm.yes"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" style="border:0px;background:#F8F8FF;height:13px;vertical-align:middle;align:absmiddle;" name="allowedOrRefusedFlag" value="${USER_STATE_OFF}" <c:if test="${user.allowedOrRefusedFlag != USER_STATE_ON}">checked</c:if> /><comm:message key="comm.no"/></td>
					</tr>
			
					<tr>
						<td height="5" colspan="4" align="right"></td>
					</tr>
			
					<tr>
					  <td height="20" colspan="4"><div align="center"><input type="button" id="submitEditUser" name="new" value=" <comm:message key="comm.save"/>" class="button" >&nbsp;<input type="button" name="new" value=" <comm:message key="comm.cancel"/>" class="button" onClick="cancelEditUser();"></div></td>
					</tr>
				</table>            
            </div>
			<div id="titleStyle2">
				<span>&nbsp;</span>
			</div>
      </div>      	
	</form>
</body>
</html>