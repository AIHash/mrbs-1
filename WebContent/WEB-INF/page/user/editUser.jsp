<%@ page  pageEncoding="UTF-8" isELIgnored="false"%>
<%@include file="/common.jsp"%>
<%
 String returnmessage=(String)request.getSession().getAttribute("returnmessage");
 request.getSession().removeAttribute("returnmessage"); 
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title><comm:message key="unified.view" /></title>
<comm:pageHeader hasEcList="false"/>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/jqueryalerts/jquery.ui.draggable.js" ></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/jqueryalerts/jquery.alerts.js"></script>
	<link href="<%=request.getContextPath()%>/resources/jqueryalerts/jquery.alerts.css" rel="stylesheet" type="text/css" media="screen" />
	<link href="${pageContext.request.contextPath }/resources/css/style.css" rel="stylesheet" type="text/css" />
<script src="${pageContext.request.contextPath}/resources/js/md5.js" type="text/javascript"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/validate.js"></script>
<script type="text/javascript">
	  function myAlert(){
			var message='<%=returnmessage%>';
			if(message&&message!=""&&message!="null") {
				jAlert(message, "<comm:message key='meeting.m.infotishi'/>");
				}
			}

	 $(function(){
		 $('#submit').click(function(){
				var name=document.getElementById("name").value;
				var mail=document.getElementById("mail").value;
				var mobile=document.getElementById("mobile").value;
				if(strTrim(name) == "") {
					jAlert("<comm:message key='js.need_input' args='admin.user_name' />", "<comm:message key='meeting.m.infotishi'/>");
					return;
				}
				
				if(!checkUserName((strTrim(name)))){
					jAlert("<comm:message key='admin.userName.notRight'/>");
					return false;		
				}

				if(mail != null &&  mail != "" && !isMailAddress(mail))
				{
					jAlert("<comm:message key='unified.mailaddress'/>", "<comm:message key='meeting.m.infotishi'/>");
					return ;
				}
				if(mobile != null &&  mobile != "" &&!isMobel(mobile))
				{
					jAlert("<comm:message key='meeting.type.mobilephone.notright'/>", "<comm:message key='meeting.m.infotishi'/>");
					return ;
				}
				
				var parameter = {};	
				$("input[type=text]", $("form")).each(function(){
					parameter[this.name] = this.value;
				});
				$("input[type=password]", $("form")).each(function(){
					parameter[this.name] = this.value;
				});
				parameter['userId'] = '${userinfo.userId}';
				$.post('${root}/user/editUser', parameter, function(result){
					if(result == "succ"){
						jAlert("<comm:message key='comm.save_ok'/>", "<comm:message key='meeting.m.infotishi'/>");
					}else if(result.indexOf('<HTML>') != -1){
						window.location.href="<%=request.getContextPath()%>/index.jsp?message=<comm:message key="system.session_expire"/>";
					}else{
						jAlert("<comm:message key='comm.save_not_ok'/>", "<comm:message key='meeting.m.infotishi'/>");
					}
				},"text");
		 });
	 });
	 
	//手机验证
	 function isMobel(value){
	 	if(isNumber(value)&&value.length==11){
	 	  return true;
	 	}else{
	 	  return false;
	 	}
	 }
	</script>
	<style type="text/css">
    #shenqing{border:#d2d2d2 solid 1px;width:725px;border-bottom:#d2d2d2 solid 0px;}
    #shenqing tr td{height:20px; line-height:20px; padding:5px 10px; border-bottom:1px solid #d2d2d2;}
	</style>
</head>
<body style="padding:0; margin:0;background:url('../resources/images/theme/bg_right-2.gif');" onload="myAlert();heigthReset(64);" onresize="heigthReset(64);">
<div id="center">
	<comm:navigator position="admin.title_self_manage>>unified.edit.user" />
	<div style="overflow-x:hidden;" id="main" >
       <form name="form1" method="post" action="<%=request.getContextPath()%>/user/editUser">
        <table id="shenqing" class="submit_table" align="center" cellspacing="0" cellpadding="0">
         <tr>
            <td colspan="4" align="center"><strong><comm:message key='unified.edit.user'/></strong></td>
          </tr>
          <tr>
            <td width="15%" align="right"><strong><comm:message key="unified.and.expert_admin.logon_name"/></strong>:</td>
            <td width="35%"><input type="text" style="width:70%;" value="${userinfo.userId}"  disabled= "true" maxlength="20"/>
            </td>
            <td width="15%" align="right"><strong><comm:message key="admin.user_name"/></strong>:<span class="required">*</span></td>
            <td width="35%" ><input type="text" id="name" style="width:70%;"name="name" value="${userinfo.name}" maxlength="20" title=<comm:message key="admin.userName.tip"/> /></td>
          </tr>
           <tr>
            <td width="15%" align="right"><strong><comm:message key="admin.user_email"/></strong>:</td>
            <td width="35%"><input type="text" id="mail" style="width:70%;" name="mail" value="${userinfo.mail}" maxlength="50"/></td>
            <td width="15%" align="right"><strong><comm:message key="unified.and.expert_admin.user_mobil"/></strong>:&nbsp;</td>
            <td width="35%" ><input type="text" id="mobile" style="width:70%;" name="mobile" value="${userinfo.mobile}" onkeyup="value=value.replace(/[^\d\.]/g,'')" maxlength="20"/></td>
          </tr>
          <tr>
            <td colspan="4" align="center"><input class="button" id="submit" type="button" value="<comm:message key='unified.submit'/>"/>&nbsp;&nbsp;&nbsp;&nbsp;<input type="reset" class="button" value="<comm:message key='unified.reset'/>"/></td>
          </tr>
        </table>        
        </form>
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
  t[i].onmouseover=function(){
   if(this.x!="1")this.style.backgroundColor=c;
  }
  t[i].onmouseout=function(){
   if(this.x!="1")this.style.backgroundColor=(this.sectionRowIndex%2==0)?a:b;
  }
 }
}

//tr_color("表格名称","奇数行背景","偶数行背景","鼠标经过背景","点击后背景");
tr_color("shenqing","#fafaff","#ffffff","#ecfbd4","#e7e7e7");
</script>
     
</body>
</html>