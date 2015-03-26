<%@ page pageEncoding="utf-8"%>
<%@include file="/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<title><comm:message key="admin.system_param_config" /></title>
	<comm:pageHeader hasEcList="false"/>
	<link href="${pageContext.request.contextPath }/resources/css/style.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/jqueryalerts/jquery.ui.draggable.js" ></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/jqueryalerts/jquery.alerts.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/Base64.js"></script>
    <link href="<%=request.getContextPath()%>/resources/jqueryalerts/jquery.alerts.css" rel="stylesheet" type="text/css" media="screen" />
<script type="text/javascript">
$(function(){
	$('#updateParam').click(function(){
		//海纳系统IP地址 
		var haina=document.getElementById("INTEGRATE_HAINA-URL").value;
		 if(haina==""){
			 jAlert("<comm:message key='admin.hainaIP.notnull'/>", "<comm:message key='meeting.m.infotishi'/>");
			 return false;
		 }
		 if(haina!=""&&!IsURL(haina))
		 {	
			 jAlert("<comm:message key='admin.hainaIp.notRight'/>", "<comm:message key='meeting.m.infotishi'/>");
			 return false;
		 }
		 //Internet访问URL
		 var soai = document.getElementById("SERVICE_OUTTER-ACCESS-IP").value;
		 if(soai==""){
			 jAlert("<comm:message key='admin.Internet.notnull'/>", "<comm:message key='meeting.m.infotishi'/>");
			 return false;
		 }
		 if(soai!=""&&!IsURL(soai))
		 {	
			 jAlert("<comm:message key="admin.Internet.notRight" />", "<comm:message key='meeting.m.infotishi'/>");
			 return false;
		 }
		 //TMS访问URL
		 var sta = document.getElementById("SERVICE_TMS-ADDRESS").value;
		 if(sta==""){
			 jAlert("<comm:message key='admin.TmsUrl.notnull'/>", "<comm:message key='meeting.m.infotishi'/>");
			 return false;
		 }
		 if(sta!=""&&!IsURL(sta))
		 {	
			 jAlert("<comm:message key='admin.TmsUrl.notRight'/>", "<comm:message key='meeting.m.infotishi'/>");
			 return false;
		 }
		 //会议通知提前(分钟)
		 var time = document.getElementById("SERVICE_BEFORE-MINUTE").value;
		 if(time==""){
			 jAlert("<comm:message key='admin.beforetime.notnull'/>", "<comm:message key='meeting.m.infotishi'/>");
			 return false;			 
		 }
		 if(time!=""&&!isNumber(time))
		 {	
			 jAlert("<comm:message key='admin.beforetime.notRight' />", "<comm:message key='meeting.m.infotishi'/>");
			 return false;
		 }
		 //数据库名IP地址
		 var ssdi = document.getElementById("SMS_SMS-DB-IP").value;
		 if(ssdi==""){
			 jAlert("<comm:message key='admin.SMSIp.notnull' />", "<comm:message key='meeting.m.infotishi'/>");
			 return false;
		 }
		 if(ssdi!=""&&!IsIP(ssdi))
		 {	
			 jAlert("<comm:message key='admin.SMSIp.notRight'/>", "<comm:message key='meeting.m.infotishi'/>");
			 return false;
		 }
		//接口编码 		
		 var smsApiCode= document.getElementById("SMS_SMS-API-CODE").value;
		 if(smsApiCode=="")
		 {	
			 jAlert("<comm:message key='admin.smsApiCode.notRight'/>", "<comm:message key='meeting.m.infotishi'/>");
			 return false;
		 }
		 
		 //数据库名
		 var smsDBName = document.getElementById("SMS_SMS-DB-NAME").value;
		 if(smsDBName=="")
		 {	
			 jAlert("<comm:message key='admin.sms.db_name.notnull' />", "<comm:message key='meeting.m.infotishi'/>");
			 return false;
		 }
		 //接口登录名
		 var smsDbUser= document.getElementById("SMS_SMS-DB-USER").value;
		 if(smsDbUser=="")
		 {	
			 jAlert("<comm:message key='admin.smsDbUser.notRight'/>", "<comm:message key='meeting.m.infotishi'/>");
			 return false;
		 }
/* 		 var smsDBUserName = document.getElementById("SMS_SEND-SMS-DB-USR").value;
		 if(smsDBUserName=="")
		 {	
			 jAlert("<comm:message key='admin.sms.db_username.notnull' />", "<comm:message key='meeting.m.infotishi'/>");
			 return false;
		 } */
		 
		 //同步数据参数-数据库名
		 var syncDBName = document.getElementById("SYNC_SYNC-DATA-DB-NAME").value;
		 if(syncDBName=="")
		 {	
			 jAlert("<comm:message key='admin.sync.db_name.notnull' />", "<comm:message key='meeting.m.infotishi'/>");
			 return false;
		 }
		//同步数据参数-数据库用户名
		 var syncDBUserName = document.getElementById("SYNC_SYNC-DATA-DB-USER").value;
		 if(syncDBUserName=="")
		 {	
			 jAlert("<comm:message key='admin.sync.db_username.notnull' />", "<comm:message key='meeting.m.infotishi'/>");
			 return false;
		 }
		 //同步数据参数-数据库名IP地址
		 var sync = document.getElementById("SYNC_SYNC-DATA-INNER-IP").value;
		 if(sync==""){
			 jAlert("<comm:message key='admin.syscIp.notnull' />", "<comm:message key='meeting.m.infotishi'/>");
			 return false;
		 }
		 if(sync !=""&&!IsIP(sync))
		 {	
			 jAlert("<comm:message key='admin.syscIp.notRight'/>", "<comm:message key='meeting.m.infotishi'/>");
			 return false;
		 }
		 //默认语言
		 var defaultLanguage=document.getElementById("SYSTEM_LANGUAGE").value;
		 if(defaultLanguage =="")
		 {	
			 jAlert("<comm:message key='admin.defaultLanguage.notnull'/>", "<comm:message key='meeting.m.infotishi'/>");
			 return false;
		 }

		$.post('${root}/system/param/update', $("form").serialize(), function(result){
			if(result == "check_successful"){
				jAlert("<comm:message key='comm.save_ok'/>", "<comm:message key='meeting.m.infotishi'/>");
			}else if(text.indexOf('<HTML>') != -1){
				window.location.href="<%=request.getContextPath()%>/index.jsp?message=system.session_expire";
			}else{
				jAlert("<comm:message key='comm.save_not_ok'/>", "<comm:message key='meeting.m.infotishi'/>");
			}
		},"text");

	});
});

function IsURL(str_url){ 
	       var strRegex = "^((https|http|ftp|rtsp|mms)?://)"  
	        + "?(([0-9a-z_!~*'().&=+$%-]+: )?[0-9a-z_!~*'().&=+$%-]+@)?" //ftp的user@  
	        + "(([0-9]{1,3}\.){3}[0-9]{1,3}" // IP形式的URL- 199.194.52.184  
	        + "|" // 允许IP和DOMAIN（域名） 
	        + "([0-9a-z_!~*'()-]+\.)*" // 域名- www.  
	        + "([0-9a-z][0-9a-z-]{0,61})?[0-9a-z]\." // 二级域名  
	       + "[a-z]{2,6})" // first level domain- .com or .museum  
	        + "(:[0-9]{1,4})?" // 端口- :80  
	        + "((/?)|" // a slash isn't required if there is no file name  
	        + "(/[0-9a-z_!~*'().;?:@&=+$,%#-]+)+/?)$";  
	        var re=new RegExp(strRegex);  
	        //re.test() 
	        if (re.test(str_url)){ 
	            return (true);  
	         }else{  
	            return (false);  
	         } 
	     } 
function IsIP(strIP) {
	if (isNull(strIP)) return false;
	var re=/^(\d+)\.(\d+)\.(\d+)\.(\d+)$/g //匹配IP地址的正则表达式
	if(re.test(strIP))
	{
	if( RegExp.$1 <256 && RegExp.$2<256 && RegExp.$3<256 && RegExp.$4<256) return true;
	}
	return false;
	}
   
</script>
</head>
<body onload="heigthReset(64);" onresize="heigthReset(64);" style="padding:0; margin:0;background:url('../../resources/images/theme/bg_right-2.gif');">
	<div id="center">
		<comm:navigator position="admin.title_system_config>>admin.system_param_config" />
	<div id="main">
		<form id="paraForm" name="form" >
			  <div id="viewappinfor">
				<div id="titleStyle">
					<span>&nbsp;</span>
				</div>		
	            <div class="subinfor" width="600" id="patientinfor1">
					      <table border="0" width="100%" cellspacing="0" cellpadding="0" align="center">
								<tr>
									<td colspan="4" height="10"></td>
								</tr>
								<c:forEach var="group" items="${ParamPackage}">
								<tr><td colspan="4" height="10">
								<table width="100%" ><tr><td height="5">
									<fieldset>
										<legend>&nbsp;<comm:message key="${group.remark}"/>&nbsp;</legend>
										<table width="100%">
											<c:forEach var="data" items="${group.subParameter}" varStatus="cc">
												<c:if test="${cc.index % 2 == 0}"><tr></c:if>
												<td width="23%" align="right"><comm:message key="${data.paramDESC}"/>:
													<c:choose>
														<c:when test="${data.paramKey == 'SYNC-DATA-DB-PWD' }">&nbsp;</c:when>
														<c:when test="${data.paramKey == 'SMS-DB-PWD' }">&nbsp;</c:when>
														<c:otherwise><span class="required">*</span></c:otherwise>
													</c:choose>
												</td>
												<td width="27%" align="left">
													<c:choose>
														<c:when test="${data.paramKey == 'LANGUAGE'}">
															<select id="${group.packageKey}_${data.paramKey}" name="${group.packageKey}_${data.paramKey}" style="width:90%;height:20px;">
													        	<option value="${data.value}">${data.value}</option>
													      	</select>
														</c:when>
														<c:when test="${data.paramKey == 'SMS-DB-PWD' || data.paramKey == 'SYNC-DATA-DB-PWD'}">
															<input type="password" id="${group.packageKey}_${data.paramKey}" name='${group.packageKey}_${data.paramKey}' size="30" style="width:90%;height:20px;line-height:20px;" value="${data.value}" <c:if test="${group.readOnly == true}">disabled</c:if> />
														</c:when>
														<c:otherwise>
															<input type="text" id="${group.packageKey}_${data.paramKey}" name='${group.packageKey}_${data.paramKey}' size="30" style="width:90%;height:20px;line-height:20px;" value="${data.value}" <c:if test="${group.readOnly == true}">disabled</c:if> />
														</c:otherwise>
													</c:choose>
												</td>
											</c:forEach>
											<c:if test="${fn:length(group.subParameter) == 1}">
												<td width="23%" align="right"></td>
												<td width="27%" align="left">&nbsp;</td>
											</c:if>
										</table>
									</fieldset>
								</td></tr></table>
							</td></tr>
							</c:forEach>
							<tr>
								<td height="5" colspan="4" align="right"></td>
							</tr>

							<tr>
							  <td  height="40" colspan="4"><div align="center"><input type="button" id="updateParam" name="new" value="<comm:message key='comm.save'/>" class="button" /></div></td>
							</tr>
						</table>	            
	            </div>
	            <div id="titleStyle">
					<span>&nbsp;</span>
				</div>
	         </div>   		
			
	</form>
	</div>
	</div>
	<div id="center_right">
		<img src="${pageContext.request.contextPath}/resources/images/theme/bg_right.gif" width="25"/>
	</div>
</body>
</html>