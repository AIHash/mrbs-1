<%@ page pageEncoding="utf-8"%>
<%@include file="/common.jsp"%>
<%
	String returnmessage = (String) request.getSession().getAttribute("returnmessage");
	request.getSession().removeAttribute("returnmessage");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title><comm:message key="unified.application" /></title>
	<comm:pageHeader hasEcList="false" />
	<link href="${pageContext.request.contextPath}/resources/css/style.css" rel="stylesheet" type="text/css" />
	<link href="<%=request.getContextPath()%>/resources/style/main.css" rel="StyleSheet" type="text/css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/jqueryui/css/jquery-ui-1.8.16.custom.css"/>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/jqueryalerts/jquery.alerts.css"  media="screen" />
    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/My97DatePicker/WdatePicker.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/jqueryalerts/jquery.alerts.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/validate.js"></script>
    <script src="<%=request.getContextPath()%>/resources/jqueryalerts/jquery.ui.draggable.js" type="text/javascript"></script>
	<script src="${pageContext.request.contextPath}/resources/js/Base64.js" type="text/javascript"></script>
	
	<script type="text/javascript"> 	
	//获取div显示内容的高度，并给改div的内容以外的地方预留高度
	function getHeight() {      
		var bodyHeight = document.body.clientHeight;
		var patientinfor1Height =document.getElementById("patientinfor1").style.height = bodyHeight - 180;
	}
	
	function querySubmit(){
		var filenumber = document.getElementById("filenumber").value;//病历号
		var patientName = document.getElementById("patientName").value;//病人姓名
		var inhospitaldate = document.getElementById("inhospitaldate").value;//入院日期
		if(strTrim(filenumber) == ""&&strTrim(patientName) == ""){
			 jAlert("<comm:message key='common.platformIntegration.write.filenumberOrPatientName'/>", "<comm:message key='meeting.m.infotishi'/>");
			 return false;
		}
		if(strTrim(filenumber) == ""){
			filenumber = "";
		}
		if(strTrim(patientName) == ""){
			patientName = "";
		}else{
			patientName = encode64(patientName);
		}
		if(strTrim(inhospitaldate) == ""){
			inhospitaldate = "";
		}else{
			inhospitaldate=inhospitaldate.replace("-","");//去掉第一个字符
			inhospitaldate=inhospitaldate.replace("-","");//去掉第二个字符
		}
		var windowUrl="http://172.16.100.23:9080/htemr2/ShowPatientInfo.jsp?filenumber="+filenumber+"&patientName="+patientName+"&inhospitaldate="+inhospitaldate;
		var x=screen.availWidth;
		var y=screen.availHeight;
		var tmp=window.open(windowUrl,"","toolbar=yes,Height=1024px,Width=768px,menubar=yes,location=yes,scrollbars=yes,resizable=yes,alwaysRaised=yes");
		tmp.focus();
		tmp.location=windowUrl;

	}
</script>
<style>
#center{background-color:#ffffff;}
#titleStyle{color:#3c3645;font-size:18px;font-weight: bold;height:30px;margin-top:15px;margin-bottom:0px;margin-left:40px;margin-right:40px;}
#viewappinfor{margin-left:25px;margin-top:0px;margin-bottom:5px;margin-right:25px;position:relative;background-color:#dee; overflow-y:auto;}
.subinfor{border:0px solid #7F9DB9;background-color:#F8F8FF;margin-left:35px;margin-right:35px;}
</style>
</head>
<body style="padding:0; margin:0;background:url('<%=request.getContextPath()%>/resources/images/theme/bg_right-2.gif');" class="bg" onload="heigthReset(64);getHeight();" onresize="heigthReset(64);getHeight();">
	<div id="center">
		<comm:navigator position="common.platformIntegration>>common.platformIntegration.EMR" />
		  <div id="main" style=" overflow-y:auto;overflow-x:hidden;width:725px;margin:0px;float:left;">
			<form method="post" action="" onsubmit="">
			  <div id="viewappinfor">
				<div id="titleStyle">
					<span><comm:message key='common.platformIntegration.EMR.query' /></span>
				</div>		
	            <div class="subinfor" id="patientinfor1">
					<table width="100%"  border="0">	
						<tr>
						   <td width="15%"  align="right"><comm:message key='common.platformIntegration.filenumber' />:&nbsp;</td>
		                   <td width="30%">
								<input type="text" name="filenumber" id="filenumber" maxlength="50" size="30" style="width:183px;height: 20px;line-height:20px;"/>						 						                   
		                   </td>
		                   <td width="15%" align="right">			
						       <comm:message key='common.platformIntegration.patientName' />:&nbsp;
						   </td>
						   <td align="left">
						       <input type="text" name="patientName" id="patientName" maxlength="50" size="30" style="width:183px;height: 20px;line-height:20px;"/>						 	
						   </td>
						</tr>				
						<tr height="4">&nbsp;
						</tr>
						<tr>
						   <td width="15%"  align="right"><comm:message key='common.platformIntegration.inhospitaldate' />:&nbsp;</td>
		                   <td width="30%" colspan="3">
								<input type="text" name="inhospitaldate" id="inhospitaldate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" style="width:182px;border: 1px solid #7F9DB9;height:20px;background:#fff url(<%=request.getContextPath()%>/resources/My97DatePicker/skin/datePicker.gif) no-repeat right;" onfocus="this.blur()" size="20" />					                   
		                   </td>
						</tr>				
						<tr>
							<td colspan="4" align="center">&nbsp;</td>
						</tr>				
						<tr>
							<td colspan="4" align="center"><input class="button" type="button" name="button" id="button" value="<comm:message key='comm.view'/>" onclick="return querySubmit();"/>
							</td>
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
	<script type="text/javascript">
		function exitsys()
		{
   			 var ask=confirm("<comm:message key='admin.exit_sure'/>\n\n<comm:message key='admin.click_ok'/>");
    		 if(ask)
				parent.location.href="${root}/login/exit";
		}
</script>
</body>

</html>