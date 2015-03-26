<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page pageEncoding="UTF-8"%>
<%@include file="/common.jsp"%>
<html>
<head>
	<title><comm:message key="admin.user_manage" /></title>
	<comm:pageHeader hasEcList="false"/>
</head>
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
function check()
{   
	var userId = document.getElementById("userId").value;
	if((strTrim(userId)) == "")
	{
	   jAlert("<comm:message key='js.need_input' args='admin.logon_name' />", "<comm:message key='meeting.m.infotishi'/>");
	   //form.userId.focus();
	   return false;
	}
	
	//判断登录名是否已被占用
	var value = document.getElementById("flagValue").value;
	if(value!=""){
		jAlert("<comm:message key='admin.userId.notAvailable'/>", "<comm:message key='meeting.m.infotishi'/>");
		return false;
	}
	
	var testUserName=/^\w{3,}$/;
	if((strTrim(userId)) != "" && !testUserName.test(strTrim(userId))){
		jAlert( "<comm:message key='admin.userId.notRight'/>", "<comm:message key='meeting.m.infotishi'/>");
		//form.userId.focus();
		return false;		
	}
	
	var userName=document.getElementById("userName").value;
	if((strTrim(userName)) == "")
	{	   
	   jAlert("<comm:message key='js.need_input' args='admin.user_name' />", "<comm:message key='meeting.m.infotishi'/>");
	   //form.userName.focus();
	   return false;
	}
		
	if(!checkUserName(strTrim(userName))){
		jAlert("<comm:message key='admin.userName.notRight'/>", "<comm:message key='meeting.m.infotishi'/>");
		//form.userName.focus();
		return false;		
	}
	

	var userPassword=document.getElementById("userPassword").value;
	if((strTrim(userPassword)) == "")
	{
	   jAlert("<comm:message key='js.need_input' args='admin.logon_password' />", "<comm:message key='meeting.m.infotishi'/>");
	   //form.userPassword.focus();
	   return false;
	}

	var confirmPassword=document.getElementById("confirmPassword").value;
	if((strTrim(confirmPassword)) == "")
	{
	   jAlert("<comm:message key='js.need_input' args='admin.confirm_password' />", "<comm:message key='meeting.m.infotishi'/>");
	   //form.confirmPassword.focus();
	   return false;
	}

	if((strTrim(userPassword)) != (strTrim(confirmPassword)))
	{
	   jAlert("<comm:message key='js.pass_not_consistent' args='admin.logon_password,admin.confirm_password'/>", "<comm:message key='meeting.m.infotishi'/>");
	   //form.confirmPassword.focus();
	   return false;
	}


	var usertypes = $('#usertype').val();
	if(usertypes == 5){		
		var username=document.getElementById("username").value;
		if ((strTrim(username)) == "")
		{
		   jAlert("<comm:message key='js.need_input' args='admin.user_username' />", "<comm:message key='meeting.m.infotishi'/>");

		   //form.username.focus();
		   return false;
		}		
		var telephone=document.getElementById("telephone").value;
		if ((strTrim(telephone)) == ""){
			   jAlert("<comm:message key='js.need_input' args='admin.user_tel' />", "<comm:message key='meeting.m.infotishi'/>");
			   //form.telephone.focus();
			   return false;
		}				 
		if((strTrim(telephone))!= ""&& !isTelNmbr((strTrim(telephone)))){
		   jAlert("<comm:message key='unified.tel.format'/>", "<comm:message key='meeting.m.infotishi'/>");
		   return false;
		}				
	}
	//if((strTrim(form.mobile.value)) == ""){
	//   alert("<comm:message key='js.need_input' args='admin.logon_phone' />");
	//   form.mobile.focus();
	//   return false;
	//}	

	var mobile = document.getElementById("mobile").value;
	if(mobile !=""&&!isMobel(mobile)){
		  jAlert("<comm:message key='meeting.type.mobilephone.notright'/>", "<comm:message key='meeting.m.infotishi'/>");
		  return false;
	 } 
	
	//if((strTrim(form.mail.value)) == ""){
	//	   alert("<comm:message key='js.need_input' args='admin.logon_email_address' />");
	//	   form.mail.focus();
	//	   return false;
	//	}	
	var mail=document.getElementById("mail").value;
	if((strTrim(mail)) != "")
	{
		if (!isMailAddress(mail))
		{
			jAlert("<comm:message key='js.need_input_right' args='admin.user_email'/>", "<comm:message key='meeting.m.infotishi'/>");
			return false;
		}
	}
	form.userPassword.value = b64_md5(form.userPassword.value);
	form.confirmPassword.value = b64_md5(form.confirmPassword.value);
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

function testId(){
	var userId = document.getElementById("userId").value;		
	$.post('${pageContext.request.contextPath}/user/testId?userIDs=' + userId,  function(text) {
		if(text=='success'){
			$("#flagValue").val("");
			return true;
		} else if(text=='failed'){
			$("#flagValue").val("failed");
			jAlert("<comm:message key='admin.userId.notAvailable'/>", "<comm:message key='meeting.m.infotishi'/>");			
			return false;			
		}
	}, 'text');
};

$(function(){
	$("#submitUser").click(function(){
		var flag=check();
		if(flag){
			var usertypes = $('#usertype').val();		
			var state = $(":radio[name=state][checked]").val(); 
			var allowedOrRefusedFlag = $(":radio[name=allowedOrRefusedFlag][checked]").val(); 
			$.post('${pageContext.request.contextPath}/user/create',{userId:$("#userId").val(),userName:$("#userName").val(),userPassword:$("#userPassword").val(),allowedOrRefusedFlag:allowedOrRefusedFlag,userType:usertypes,state:state,username:$("#username").val(),telephone:$("#telephone").val(),mobile:$("#mobile").val(),mail:$("#mail").val(),terminal:$("#terminal").val(),gender:$("#gender").val()}, function(msg){
				if(msg == "adduser_successful"){
					jAlert("<comm:message key='admin.user_add_success'/>", "<comm:message key='meeting.m.infotishi'/>",function(resultConfirm){
						if(resultConfirm)
							parent.location.href="<%=request.getContextPath()%>/user/queryUser";
					      }						
				    );
				}else{
					jAlert("<comm:message key='admin.user_add_fail'/>", "<comm:message key='meeting.m.infotishi'/>");	
				}	 
			});
		}else{
			return false;
		}
		
	});		
});

function cancelAddUser(){
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
<body style="overflow:hidden;" leftmargin="5" topmargin="0" onLoad="focus('form','userId');getusertype();" >
	<form name="form" onsubmit="return check()">
	  <input type="hidden" id="flagValue" value=""/>
	  <div id="viewappinfor">
			<div id="titleStyle">
				<span>&nbsp;</span>
			</div>		
            <div class="subinfor" id="patientinfor1">
				<table border="0" width="440" cellspacing="0" vertical-align="middle" cellpadding="0" align="center" height="200">				
					<tr>
						<td colspan="4" height="10"></td>
					</tr>
			
					<tr>
						<td width="23%" align="right"><comm:message key="unified.and.expert_user.logon_name"/>:<span class="required">*</span></td>
						<td width="27%" align="left"><input type="text" name="userId" id="userId" maxlength="16" size="30" style="width:150px " title=<comm:message key="admin.userId.tip"/>  onblur="testId()"/></td>
						<td  width="23%" align="right"><comm:message key="unified.and.expert_user_name"/>:<span class="required">*</span></td>
						<td width="27%" align="left"><input type="text" name="userName" id="userName" maxlength="20" size="30" style="width:150px " title=<comm:message key="admin.userName.tip"/> /></td>
					</tr>
					
					<tr>
						<td align="right"><comm:message key="unified.and.expert_user.logon_password"/>:<span class="required">*</span></td>
						<td align="left"><input type="password" name="userPassword" id="userPassword" maxlength="16" size="30" style="width:150px"></td>
						<td align="right"><comm:message key="admin.confirm_password"/>:<span class="required">*</span></td>
						<td><input type="password" name="confirmPassword" id="confirmPassword" maxlength="16" size="30" style="width:150px"></td>
					</tr>
			
					<tr>
						<td align="right"><comm:message key="admin.user_type"/>:<span class="required">*</span></td>
						<td>
							<c:choose>
								<c:when test="${parentNodeNo=='001003'||fn:substring(parentNodeNo, 0, 6) == '001003'}">
									<select id="usertype" name ="userType" style='width:150px' onchange="getusertype();">
										<c:forEach items="${USER_TYPE}" var="type" varStatus="var">
											<c:if test="${type.value==5}">
												<option value="${type.value}"><comm:message key="${type.name}"/></option>
											</c:if>
										</c:forEach>      
									</select>
								</c:when>
								<c:when test="${parentNodeNo=='001002'||fn:substring(parentNodeNo, 0, 6) == '001002'}">
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
												<option value="${type.value}"><comm:message key="${type.name}"/></option>
											</c:if>
										</c:forEach>      
									</select>
								</c:otherwise>
							</c:choose>
							
						</td>
						<td align="right"><comm:message key="unified.and.expert_meeting.m.state"/>:<span class="required">*</span></td>
						<td><input type="radio" style="border:0px;background:#F8F8FF;height:13px;vertical-align:middle;align:absmiddle;" name="state" value="${USER_STATE_ON}" checked><comm:message key="admin.user_state_on"/>&nbsp;&nbsp;<input type="radio" style="border:0px;background:#F8F8FF;height:13px;vertical-align:middle;align:absmiddle;" name="state" value="${USER_STATE_OFF}"><comm:message key="admin.user_state_off"/></td>
					</tr>
			        <tr id="usernameInfo" style="display:none;">
						<td align="right"><comm:message key="unified.and.expert_username"/>:<span class="required">*</span></td>
						<td><input type="text" name="username" id="username" maxlength="32" style="width:150px"></td>
						<td align="right"><comm:message key="admin.user_tel"/>:<span class="required">*</span></td>
						<td align="left"><input type="text" name="telephone" id="telephone" maxlength="25" size="25" style="width:150px"/></td>
					</tr>
					<tr>
						<td align="right"><comm:message key="unified.and.expert_mobile"/>:&nbsp;</td>
						<td><input type="text" name="mobile"  id="mobile" maxlength="32" style="width:150px"></td>
						<td align="right"><comm:message key="admin.user_email"/>:&nbsp;</td>
						<td align="left"><input type="text" name="mail" id="mail" maxlength="50" size="50" style="width:150px"></td>
					</tr>
			
					<tr>
						<td align="right"><comm:message key="admin.user_terminal"/>:&nbsp;</td>
						<td><input type="text" name="terminal" id="terminal" maxlength="100" style="width:150px"></td>
						<td align="right"><input type="hidden" name="gender" id="gender" ><comm:message key="unified.and.expert_admin.allowed_or_refused"/>:<span class="required">*</span></td>
						<td><input type="radio" style="border:0px;background:#F8F8FF;height:13px;vertical-align:middle;align:absmiddle;" name="allowedOrRefusedFlag" value="${USER_STATE_ON}" checked /><comm:message key="comm.yes"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" style="border:0px;background:#F8F8FF;height:13px;vertical-align:middle;align:absmiddle;" name="allowedOrRefusedFlag" value="${USER_STATE_OFF}" /><comm:message key="comm.no"/></td>
					</tr>
			
					<tr>
						<td height="5" colspan="4" align="right"></td>
					</tr>
			
					<tr>
					  <td height="20" colspan="4"><div align="center"><input type="button" id="submitUser" name="new" value=" <comm:message key="comm.save"/>" class="button" >&nbsp;<input type="button" name="new" value=" <comm:message key="comm.cancel"/>" class="button" onClick="cancelAddUser();"></div></td>
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