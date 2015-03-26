<%@ page pageEncoding="utf-8"%>
<%@include file="/common.jsp"%>
<%
 String returnmessage=(String)request.getSession().getAttribute("returnmessage");
 request.getSession().removeAttribute("returnmessage"); 
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title><comm:message key="unified.application" /></title>
<comm:pageHeader hasEcList="false" />
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/resources/css/style.css"/>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/jqueryalerts/jquery.alerts.css"  media="screen" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/style/multiselect/common.css" />
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/jqueryalerts/jquery.alerts.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/My97DatePicker/WdatePicker.js"></script>
<script type="text/JavaScript">
<!--
	function sAlert(strTitle){ 
	    var msgw,msgh,bordercolor; 
	    msgw=450;//提示窗口的宽度 
	    msgh=230;//提示窗口的高度 
	    titleheight=25; //提示窗口标题高度 
	    bordercolor="#336699";//提示窗口的边框颜色 
	    titlecolor="#99CCFF";//提示窗口的标题颜色

	    var sWidth,sHeight; 
	    sWidth=document.body.offsetWidth; 
	    sHeight=screen.height; 
	    var bgObj=document.createElement("div"); 
	    bgObj.setAttribute('id','bgDiv'); 
	    bgObj.style.position="absolute"; 
	    bgObj.style.top="0"; 
	    bgObj.style.background="#777"; 
	    bgObj.style.filter="progid:DXImageTransform.Microsoft.Alpha(style=3,opacity=25,finishOpacity=75"; 
	    bgObj.style.opacity="0.6"; 
	    bgObj.style.left="0"; 
	    bgObj.style.width=sWidth + "px"; 
	    bgObj.style.height=sHeight + "px"; 
	    bgObj.style.zIndex = "10000"; 
	    document.body.appendChild(bgObj);

	    var msgObj=document.createElement("div");
	    msgObj.setAttribute("id","msgDiv"); 
	    msgObj.setAttribute("align","center"); 
	    msgObj.style.background="white"; 
	    msgObj.style.border="1px solid " + bordercolor; 
	    msgObj.style.position = "absolute"; 
	    msgObj.style.left = "50%"; 
	    msgObj.style.top = "50%"; 
	    msgObj.style.font="12px/1.6em Verdana, Geneva, Arial, Helvetica, sans-serif"; 
	    msgObj.style.marginLeft = "-225px" ; 
	    msgObj.style.marginTop = -75+document.documentElement.scrollTop+"px"; 
	    msgObj.style.width = msgw + "px"; 
	    msgObj.style.height = msgh + "px"; 
	    msgObj.style.textAlign = "center"; 
	    msgObj.style.lineHeight ="25px"; 
	    msgObj.style.zIndex = "10001";

	    var title=document.createElement("h4"); 
	    title.setAttribute("id","msgTitle"); 
	    title.setAttribute("align","right"); 
	    title.style.margin="0"; 
	    title.style.padding="0px"; 
	    title.style.background=bordercolor; 
	    title.style.filter="progid:DXImageTransform.Microsoft.Alpha(startX=20, startY=20, finishX=100, finishY=100,style=1,opacity=75,finishOpacity=100);"; 
	    title.style.opacity="0.75"; 
	    title.style.border="1px solid " + bordercolor; 
	    title.style.height="28px"; 
	    title.style.width="100%";
	    title.style.font="12px Verdana, Geneva, Arial, Helvetica, sans-serif"; 
	    title.style.color="white"; 
	    title.style.cursor="pointer"; 
	    title.title ="<comm:message key='unified.close'/>"; 
	    title.close="<comm:message key='unified.window.close'/>"; 
	    title.innerHTML="<table border='0′ align='center' width='100%' ><tr><td align='left' width='12%'>"+' '+"</td><td align='left' width='69%'>"+strTitle+"</td><td width='9%' >"+title.close+"</td></tr></table>";
	    title.onclick = function(){
		    document.body.removeChild(bgObj);
		    document.getElementById("msgDiv").removeChild(title);
		    document.body.removeChild(msgObj);
	    };
	    document.body.appendChild(msgObj); 
	    document.getElementById("msgDiv").appendChild(title); 
	    var txt=document.createElement("p"); 
	    txt.style.margin="0em 0";
	    //txt.style.border="1px solid red";
	    txt.setAttribute("id","msgTxt"); 
	    txt.innerHTML="<iframe style='width:100%;height:200px;'  src='<%=request.getContextPath()%>/resources/fileupload.jsp' scrolling='no'></iframe>"; 
	    document.getElementById("msgDiv").appendChild(txt);
	}
	function sAlert2(strTitle){ 
	    var msgw,msgh,bordercolor; 
	    msgw=600;//提示窗口的宽度 
	    msgh=400;//提示窗口的高度 
	    titleheight=25; //提示窗口标题高度 
	    bordercolor="#336699";//提示窗口的边框颜色 
	    titlecolor="#99CCFF";//提示窗口的标题颜色

	    var sWidth,sHeight; 
	    sWidth=document.body.offsetWidth; 
	    sHeight=screen.height; 
	    var bgObj=document.createElement("div"); 
	    bgObj.setAttribute('id','bgDiv'); 
	    bgObj.style.position="absolute"; 
	    bgObj.style.top="5%"; 
	    bgObj.style.background="#777"; 
	    bgObj.style.filter="progid:DXImageTransform.Microsoft.Alpha(style=3,opacity=25,finishOpacity=75"; 
	    bgObj.style.opacity="0.6"; 
	    bgObj.style.left="5%"; 
	    bgObj.style.width=sWidth + "px"; 
	    bgObj.style.height=sHeight + "px"; 
	    bgObj.style.zIndex = "10000"; 
	    document.body.appendChild(bgObj);

	    var msgObj=document.createElement("div");
	    msgObj.setAttribute("id","msgDiv"); 
	    msgObj.setAttribute("align","center"); 
	    msgObj.style.background="#336699"; 
	    msgObj.style.border="1px solid " + bordercolor; 
	    msgObj.style.position = "absolute"; 
	    msgObj.style.left = "42%"; 
	    msgObj.style.top = "27%"; 
	    msgObj.style.bottom = "5%"; 
	    msgObj.style.font="12px/1.6em Verdana, Geneva, Arial, Helvetica, sans-serif"; 
	    msgObj.style.marginLeft = "-225px" ; 
	    msgObj.style.marginTop = -75+document.documentElement.scrollTop+"px"; 
	    msgObj.style.width = msgw + "px"; 
	    msgObj.style.height = msgh + "px"; 
	    msgObj.style.textAlign = "center"; 
	    msgObj.style.lineHeight ="22px"; 
	    msgObj.style.zIndex = "10001";

	    var title=document.createElement("h3"); 
	    title.setAttribute("id","msgTitle"); 
	    title.setAttribute("align","right"); 
	    title.style.margin="0"; 
	    title.style.padding="0px"; 
	    title.style.background=bordercolor; 
	    title.style.filter="progid:DXImageTransform.Microsoft.Alpha(startX=40, startY=40, finishX=80, finishY=80,style=1,opacity=75,finishOpacity=100);"; 
	    title.style.opacity="0.75"; 
	    title.style.border="0px solid " + bordercolor; 
	    title.style.height="22px"; 
	    title.style.width="100%";
	    title.style.font="12px Verdana, Geneva, Arial, Helvetica, sans-serif"; 
	    title.style.color="#336699"; 
	    title.style.cursor="pointer"; 
	    title.title ="<comm:message key='unified.close'/>"; 
	    title.close="<comm:message key='unified.window.close'/>"; 
	    title.innerHTML="<table border='0′ width='100%' ><tr style='width:100%' ><td align='left' style='color:#ffffff;' width='85%'>"+strTitle+"</td><td align='left' width='10%'>"+" "+"</td><td width='25%' style='color:#ffffff;'>"+title.close+"</td></tr></table>";
	    title.onclick = function(){
		    document.body.removeChild(bgObj);
		    document.getElementById("msgDiv").removeChild(title);
		    document.body.removeChild(msgObj);
	    };
	    document.body.appendChild(msgObj); 
	    document.getElementById("msgDiv").appendChild(title); 
	    var txt=document.createElement("p"); 
	    txt.style.margin="1em 0";
	    txt.setAttribute("id","msgTxt"); 
	    txt.innerHTML="<iframe style='width:100%;height:350px;background-color:#FFFFFF;' id='RadiationImagFrame' src='<%=request.getContextPath()%>/resources/fileRadiationImag.jsp' scrolling='yes'></iframe>"; 
	    document.getElementById("msgDiv").appendChild(txt); 
	}
	
	function queryFileRadiationImag(startDate,endDate){
		 if (startDate.length> 1&&endDate.length > 1&&!compareDateString(startDate, endDate)){
				jAlert("<comm:message key='meeting.m.timeRage.notRight'/>", "<comm:message key='meeting.m.infotishi'/>");				
				return false;
			  } 
			 if (startDate.length> 1&&endDate.length==0){
				jAlert("<comm:message key='meeting.m.timeRage.notRight'/>", "<comm:message key='meeting.m.infotishi'/>");				
				return false;
			  }
			 if (startDate.length==0&&endDate.length > 1){
				jAlert("<comm:message key='meeting.m.timeRage.notRight'/>", "<comm:message key='meeting.m.infotishi'/>");				
				return false;
			  }
			 
			$('#hiddenTr', document.getElementById('RadiationImagFrame').contentWindow.document).show();
			$.ajax({type :'POST',  url : '${pageContext.request.contextPath}/integrate/query', 
				data : {startDate:startDate, endDate:endDate},
				dataType : 'json',
				dataFilter : function(json, type){
					if(json.indexOf('<html>') != -1){
						window.location.href="<%=request.getContextPath()%>/index.jsp?message=system.session_expire";
						return;
					}
					return json;
				},
				success : function(json){
					var dataTable = $("#data tr:first", document.getElementById('RadiationImagFrame').contentWindow.document);
					$('tr', document.getElementById('RadiationImagFrame').contentWindow.document).remove('.dtr');//清除上次查询的数据
					if(json.msg == 'error'){
						$('#tip', document.getElementById('RadiationImagFrame').contentWindow.document).html('连接海纳服务器出错');
						$('#hiddenTr', document.getElementById('RadiationImagFrame').contentWindow.document).show();
					}else if(json.msg == 'nodata'){
						$('#tip', document.getElementById('RadiationImagFrame').contentWindow.document).html('您提交的时间段内无放射影像，请重新选择时间段');
						$('#hiddenTr', document.getElementById('RadiationImagFrame').contentWindow.document).show();
					}else{
						$('#hiddenTr', document.getElementById('RadiationImagFrame').contentWindow.document).hide();
						$.each(json.msg, function(i, item){//$(json).length;
							var row = $("<tr class='dtr'></tr>");
							var sex = item.sex == 1 ? "男" : "女";
							var check = $("<td aligh='right' width='10%'><input type='checkbox' name='haianBox' value='" + item.studyuid + ":" + item.name + "' /></td>");
							var name = $("<td align='center' width='15%' class='inputstyle'>"+item.name+"</td>");
							var sexTd = $("<td align='center' width='15%'  class='inputstyle'>"+sex+"</td>");
							var time = $("<td align='center' width='30%' class='inputstyle'>"+item.datetime+"</td>");
							var modality = $("<td align='center' width='30%' class='inputstyle'>"+item.modality+"</td>");
							row.append(check).append(name).append(sexTd).append(time).append(modality);
							dataTable.after(row);
					  });
					}
				}
			});
	}
	
	function check() {
		 var patient_name=document.getElementById("patient_name").value;
		 if(patient_name=="") {
			 jAlert("<comm:message key='unified.need.patient_name'/>", "<comm:message key='meeting.m.infotishi'/>");
			 return false;
		 }
		 var patient_sex=document.getElementById("patient_sex").value;
		 if(patient_sex=="") {
			 jAlert("<comm:message key='unified.need.patient_sex'/>", "<comm:message key='meeting.m.infotishi'/>");
			 return false;
		 }
		 var patient_age=document.getElementById("patient_age").value;
		 if(patient_age=="") {	
			 jAlert("<comm:message key='unified.need.patient_age'/>", "<comm:message key='meeting.m.infotishi'/>");
			 return false;
		 }
		 if(patient_age  >  200 || patient_age < 1) {
			 jAlert("<comm:message key='unified.need.patient_right_age'/>", "<comm:message key='meeting.m.infotishi'/>");
			 return false;
		 }
		var starttime=document.getElementById("starttime").value;
		 if(starttime=="") {
			  jAlert("<comm:message key='meeting.suggestTime.isNotNull'/>", "<comm:message key='meeting.m.infotishi'/>");
			 return false;
		 }
		 var author_mobile = document.getElementById("author_mobile").value;
		 if(author_mobile!=""&&!isMobel(author_mobile)){
			  jAlert("<comm:message key='meeting.type.mobilephone.notright'/>", "<comm:message key='meeting.m.infotishi'/>");
				 return false;
		 }
		 
		 var author_telephone = document.getElementById("author_telephone").value;
		 if(author_telephone!= ""&& !isTelNmbr(author_telephone)){
		   jAlert("<comm:message key='unified.tel.format'/>", "<comm:message key='meeting.m.infotishi'/>");
		   return false;
		 }
		 
		 var author_email = document.getElementById("author_email").value;
		 var myreg = /^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
		 if(author_email!=""&&!myreg.test(author_email)){
			 jAlert("<comm:message key='unified.mailaddress'/>", "<comm:message key='meeting.m.infotishi'/>");
		     return false;
		 }
		 var currentDate = new Date();
		 var currentDate = new Date();
		 $('#departmentSelected option').each(function(i, n){
			 $(n).attr('selected', 'selected');
		 });
		 $('#icd10Selected option').each(function(i, n){
			 $(n).attr('selected', 'selected');
		 });
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

	 
	  function myAlert(){
		var message='<%=returnmessage%>';
		if(message&&message!=""&&message!="null") {
			jAlert(message, "<comm:message key='meeting.m.infotishi'/>");
			}
		}
		
		function checkSSN(){
			var socialSecurityNO = document.getElementById("socialSecurityNO").value;
			if(strTrim(socialSecurityNO) == ""){
				jConfirm("<comm:message key='admin.not_need_ssn_sure'/>\n\n<comm:message key='admin.click_ok'/>", "<comm:message key='meeting.m.infotishi'/>",function(resultConfirm){
					if(resultConfirm){
						var windowUrl="${HAINA_URL}/HinacomWeb/DisplayImage.aspx?OEM=160&UserID=${USER_LOGIN_SESSION.userId}&UserName=${sessionScope.encodeName}&HospitalID=${USER_LOGIN_SESSION.userId}&UserRetrieve=0";
						var x=screen.availWidth;
						var y=screen.availHeight;
						var tmp=window.open(windowUrl,"","toolbar=yes,Height=1024px,Width=768px,menubar=yes,location=yes,scrollbars=yes,resizable=yes,alwaysRaised=yes");
						tmp.focus();
						tmp.location=windowUrl;
						
				    }else{
				    	document.getElementById("socialSecurityNO").focus();
				    	//document.getElementById("socialSecurityNO").style.backgroundColor='#f4eaf1';
						return false;
				    }				
				});
			}else{
				var windowUrl="${HAINA_URL}/HinacomWeb/DisplayImage.aspx?OEM=160&UserID=${USER_LOGIN_SESSION.userId}&UserName=${sessionScope.encodeName}&HospitalID=${USER_LOGIN_SESSION.userId}&UserRetrieve=0&SSN="+socialSecurityNO;
				var x=screen.availWidth;
				var y=screen.availHeight;
				var tmp=window.open(windowUrl,"","toolbar=yes,Height=1024px,Width=768px,menubar=yes,location=yes,scrollbars=yes,resizable=yes,alwaysRaised=yes");
				tmp.focus();
				tmp.location=windowUrl;
			}
			return true;
		}
		
//-->
</script>
<style type="text/css">
.multiselect{width:450px;border: 1px solid #7F9DB9;}
.required{color:red;}
.text-long{width:450px;}
#viewappinfor{margin-left:25px;margin-right:25px;margin-top:0px;margin-bottom:20px;position:relative;background-color:#dee; overflow-y:auto;}
.titleStyle{color:#3c3645;font-size:18px;font-weight: bold;height:28px;line-height:28px;margin-top:15px;margin-bottom:0px;margin-left:40px;border:0px dashed #3c3645;}
.subinfor{border:0px solid #7F9DB9;background-color:#F8F8FF;margin-left:35px;margin-right:35px;}
/* .meetingApp tr{border:1px solid red;}
.meetingApp td{border:1px solid red;} */
.meetingApp td input{height:20px;line-height:20px;}
select{background: #ffffff;border: 1px solid #7F9DB9;width:100px;color:#000000;height:22px;}
.patientInfor tr td input{/* margin-top:3px; */margin-bottom:3px;background: #ffffff;margin-left:3px;border: 1px solid #7F9DB9;height:20px;line-height:20px;}
.patientInfor{border: 1px solid #D2D2D2;height:20px;line-height:20px;}
.meetingsu{height:20px;line-height:20px;}
#rightdown{background-image: url("../resources/images/table/down.gif");background-repeat: repeat-x;width:19px;height:28px;margin-bottom:2px;margin-left:15px;margin-right:15px;margin-top:8px;}
#leftup{background-image: url("../resources/images/table/up.gif");background-repeat: repeat-x;width:19px;height:28px;margin-bottom:2px;margin-left:15px;margin-right:15px;margin-top:20px;}
._table{border-collapse:collapse; border:solid #D2D2D2; border-width:1px 0 0 1px; width:470; margin:auto; }
._table caption {font-size:18px; font-weight:bolder;}
._table td{height:20px; line-height:20px;border:1px solid #D2D2D2;}
._table tr td input{width:153p;}
._table th,.submit_table td {border:solid #D2D2D2; border-width:0 1px 1px 0;}
a:hover{text-decoration:underline;}
</style>

</head>
<body style="padding:0; margin:0;background:url('../resources/images/theme/bg_right-2.gif');" onload="myAlert();heigthReset(64);" onresize="heigthReset(64);">
	<div id="center">
		<comm:navigator position="icuVisit.consultation>>icuVisit.consultation" />
	<div style="overflow-x:hidden;" id="main" >
<form method="post"   action="<%=request.getContextPath()%>/unified/addIcuVisit"  onsubmit="return check();" >
	<div id="viewappinfor">
	    <div class="subtitle">
				<div class="titleStyle">
					<comm:message key='unified.basic_patient_information' />
				</div>	    
	    </div>
	    <div class="subinfor" id="patientinfor1">
			<table width="100%" border="0" cellspacing="2" cellpadding="1" class="meetingApp">
				<tr>
					<td align="right" width="20%"><comm:message key='common.patientInfo.socialSecurityNO' />:&nbsp;</td>
					<td height="30">
						<input type="text" name="patientInfo.socialSecurityNO" size="25" id="socialSecurityNO" onkeyup="value=value.replace(/[^\d\.]/g,'')" maxlength="20" />
					</td>
				</tr>
				<tr>
					<td width="20%"  align="right"><comm:message key='unified.and.expert_name' />:<span class="required">*</span></td>
					<td height="30">
					     <table width="100%" class="meetingApp" border="0">
					             <tr>
					                  <td width="20%"><input type="text" name="patientInfo.name" id="patient_name" size="25" maxlength="20"/> </td>
					                  <td width="20%" align="right"><comm:message key='unified.and.expert_user_sex' />:<span class="required">*</span></td>
					                  <td width="15%" align="left">
			                               <select name="patientInfo.sex" id="patient_sex" style="width:45px;">
										        <option value="0"><comm:message key='comm.male' /></option>
										        <option value="1"><comm:message key='comm.female' /></option>
								           </select>					                  
					                  </td>
					                  <td width="13%" align="right"><comm:message key='unified.and.expert_patient.age' />:<span class="required">*</span></td>
					                  <td width="27%" align="left"><input type="text" name="patientInfo.age" id="patient_age" size="8" onkeyup="value=value.replace(/[^\d\.]/g,'')" maxlength="3"/></td>
					             </tr>					             
					     </table>
					</td>
				</tr>
				<tr>
					<td align="right" width="20%"><comm:message key='unified.address' />:&nbsp;</td>
					<td height="30">
						<input type="text" name="patientInfo.address" size="80" id="address" maxlength="200" />
					</td>
				</tr>
			</table>
	    </div>
	     
	    <div class="subtitle">
			<div class="titleStyle">
				<strong><comm:message key='unified.application_information' /></strong>
			</div>	    
	    </div>
	    <div class="subinfor" id="patientinfor2">
			<table width="100%" border="0" cellspacing="2" cellpadding="1" class="meetingApp">
				<tr>
					<td width="20%" align="right"><comm:message key='meeitng.suggestTime' />:<span class="required">*</span></td>
					<td width="80%"><input type="text" name="expectedtime" id="starttime" class="Wdate" onclick="WdatePicker({dateFmt:'yyyy-M-d H:mm',minDate:'%y-%M-%d %H:%m:%s',maxDate:'{%y+1}-%M-%d 21:00:00'})" onfocus="this.blur()" size="25" />&nbsp;&nbsp;</td>
				</tr>
				<tr height="3"><td colspan="2">&nbsp;</td></tr>
				<tr>
				    <td colspan="2">
					<table background-color="#ddeefa">
						<tr>
							<td width="20%"  align="right">
					            <c:if test="${user.userType.value == 4}">
						            <comm:message key='unified.and.expert.meeting.requester' />:&nbsp;
					            </c:if>
					            <c:if test="${user.userType.value == 5}">
						            <comm:message key='unified.and.expert_requester'/>:&nbsp;
					            </c:if>
							</td>
							<td align="center" height="80">
							     <table width="470" align="center" class="_table" style="margin-left:3px; border-bottom:0px;border-right:0px;" border="1">
							            <tr>
							                <c:if test="${fn:substring(user.deptId.parentDeptCode, 0, 6) == '001001'||user.deptId.deptcode == '001001'}">
								                <td width="78" align="right" style="height:25px;"><comm:message key='group.name'/>:&nbsp;</td>
								                <td width="150" align="left"><comm:message key='meeting.type.inner'/></td>
								                <td width="78" align="right"><comm:message key='unified.and.expert_username'/>:&nbsp;</td>
							                	<c:if test="${fn:length(user.name) <= 10}">
								                  <td align="left">${user.name}&nbsp;</td>
								                </c:if>
								                <c:if test="${fn:length(user.name) > 10}">
								                  <td align="left" title="${user.name}">${fn:substring(user.name, 0, 10)}...&nbsp;</td>
								                </c:if>
								            </c:if>
								            <c:if test="${fn:substring(user.deptId.parentDeptCode, 0, 6) == '001002'||user.deptId.deptcode == '001002'}"> 
								            	<td align="right" style="height:25px;"><comm:message key='group.name'/>:&nbsp;</td>
								                <td align="left"><comm:message key='meeting.type.outter'/></td>
								                <td align="right"><comm:message key='unified.and.expert_username'/>:&nbsp;</td>
							                	<c:if test="${fn:length(user.name) <= 10}">
								                  <td align="left">${user.name}&nbsp;</td>
								                </c:if>
								                <c:if test="${fn:length(user.name) > 10}">
								                  <td align="left" title="${user.name}">${fn:substring(user.name, 0, 10)}...&nbsp;</td>
								                </c:if>
								            </c:if>
								            <c:if test="${fn:substring(user.deptId.parentDeptCode, 0, 6) == '001003'||user.deptId.deptcode == '001003'}">
								            	<td align="right" style="height:25px;"><comm:message key='group.name'/>:&nbsp;</td>
								                <c:if test="${fn:length(user.name) <= 10}">
								                  <td align="left">${user.name}&nbsp;</td>
								                </c:if>
								                <c:if test="${fn:length(user.name) > 10}">
								                  <td align="left" title="${user.name}">${fn:substring(user.name, 0, 10)}...&nbsp;</td>
								                </c:if>								                
								                <td align="right"><comm:message key='unified.and.expert_username'/>:&nbsp;</td>
								                <c:if test="${fn:length(user.username) <= 10}">
								                  <td align="left">${user.username}&nbsp;</td>
								                </c:if>
								                <c:if test="${fn:length(user.username) > 10}">
								                  <td align="left" title="${user.username}">${fn:substring(user.username, 0, 10)}...&nbsp;</td>
								                </c:if>								                
								            </c:if>
							            </tr>
							            <tr>
							                <td align="right" style="height:25px;"><comm:message key='unified.and.expert_mobile'/>:&nbsp;</td>
							                <td align="left">${user.mobile}</td>
							                <td align="right"><comm:message key='admin.user_tel'/>:&nbsp;</td>
							                <td align="left">${user.telephone}</td>
							            </tr>
							            <tr>
							                <td width="78" align="right" style="height:25px;"><comm:message key='group.department'/>:&nbsp;</td>
							                <td width="150" align="left">${user.deptId.name}</td>
							                <td width="78" align="right"><comm:message key='unified.and.expert_email'/>:&nbsp;</td>
									        <c:if test="${fn:length(user.mail) <= 21}">
							                  <td align="left">${user.mail}&nbsp;<input type="hidden" name="applicantid" id="applicantid"/></td>
							                </c:if>
							                <c:if test="${fn:length(user.mail) > 21}">
							                  <td title="${user.mail}" align="left" >${fn:substring(user.mail, 0, 21)}...&nbsp;<input type="hidden" name="applicantid" id="applicantid"/></td>
							                </c:if>							            
							            </tr>					            
							     </table>
							</td>
						</tr>
					</table>	
						<table>
						          <tr><td>&nbsp;</td></tr>
						</table>
				      <table background-color="#ddeefa">
						<tr>
							<td width="20%"  align="right"><comm:message key='unified.and.expert_author'/>:&nbsp;</td>
							    <td height="80" align="center">
							        <table width="470" class="_table" style="margin-left:3px;border-bottom:0px;border-right:0px;" align="center" border="1">
								                <tr>
								                
								                	<c:if test="${fn:substring(user.deptId.parentDeptCode, 0, 6) == '001001'||user.deptId.deptcode == '001001'}">
										               <td align="right" width="78"><comm:message key='group.name'/>:&nbsp;</td>
									                    <td align="left" width="150"><input style="width:153px;" type="text" name="authorInfo.groupname" id="author_groupname" size="15" value="<comm:message key='meeting.type.inner'/>" onfocus="this.blur()"/></td>
									                    <td align="right" width="78"><comm:message key='unified.and.expert_username'/>:&nbsp;</td>
									                    <td align="left" width="150"><input style="width:153px;" type="text" name="authorInfo.name" id="author_groupname" size="15" value="${user.name}"  /></td>
										            </c:if>
										            <c:if test="${fn:substring(user.deptId.parentDeptCode, 0, 6) == '001002'||user.deptId.deptcode == '001002'}"> 
										            	<td align="right"><comm:message key='group.name'/>:&nbsp;</td>
									                    <td align="left"><input style="width:153px;" type="text" name="authorInfo.groupname" id="author_groupname" size="15" value="<comm:message key='meeting.type.outter'/>" onfocus="this.blur()"/></td>
									                    <td align="right"><comm:message key='unified.and.expert_username'/>:&nbsp;</td>
									                    <td align="left"><input style="width:153px;" type="text" name="authorInfo.name" id="author_groupname" size="15" value="${user.name}"  /></td>
										            </c:if>
										            <c:if test="${fn:substring(user.deptId.parentDeptCode, 0, 6) == '001003'||user.deptId.deptcode == '001003'}">
										            	<td align="right"><comm:message key='group.name'/>:&nbsp;</td>
									                    <td align="left"><input style="width:153px;" type="text" name="authorInfo.groupname" id="author_groupname" size="15" value="${user.name}" onfocus="this.blur()"/></td>
									                    <td align="right"><comm:message key='unified.and.expert_username'/>:&nbsp;</td>
									                    <td align="left"><input style="width:153px;" type="text" name="authorInfo.name" style="width:220px;" id="author_groupname" size="15" value="${user.username}"  /></td>
										            </c:if>
								                
								                </tr>
								                <tr>
								                    <td align="right"><comm:message key='unified.and.expert_mobile'/>:&nbsp;</td>
								                    <td align="left"><input style="width:153px;" type="text" name="authorInfo.mobile" id="author_mobile"  size="15"  maxlength="15" value="${user.mobile}"/></td>
								                    <td align="right"><comm:message key='admin.user_tel'/>:&nbsp;</td>
								                    <td align="left"><input style="width:153px;" type="text" name="authorInfo.telephone" id="author_telephone" size="15"  maxlength="25" value="${user.telephone}"/></td>
								                </tr>
								                <tr>
								                    <td align="right" width="78"><comm:message key='group.department'/>:&nbsp;</td>
								                    <td align="left" width="150"><input style="width:153px;" type="text" name="authorInfo.department" id="author_department" size="15" maxlength="50" value="${user.deptId.name}"/></td>
								                    <td align="right" width="78"><comm:message key='unified.and.expert_email'/>:&nbsp;</td>
								                    <td align="left" width="150"><input style="width:153px;" type="text" name="authorInfo.email" id="author_email" size="15" maxlength="50" value="${user.mail}"/></td>
								                </tr>
								         </table>
								 </td>
					        </tr>
					        <tr height="60">
							    <td colspan="2" align="center">
									 <input class="button" type="submit" name="button" id="button" value="<comm:message key='comm.save'/>" />&nbsp;&nbsp;&nbsp;
									 <input	class="button" type="reset" name="button2" id="button2" onclick="return resetValue();"	value="<comm:message key='unified.reset'/>" />
							    </td>
				            </tr>	   
				      </table>
				    </td>  
				  </tr>
			</table>
	    </div>
		<div id="titleStyle2" style="height:5%;color:#3c3645;font-size:18px;font-weight: bold;margin-top:15px;margin-bottom:0px;margin-left:40px;margin-right:40px;">
			<span style="background-color:#dee; display:-moz-inline-box; display:inline-block; width:25px;">&nbsp;</span>&nbsp;</span>
		</div>
	  </form>
    </div>
  </div>
</div>
<div id="center_right">
	<img src="${pageContext.request.contextPath}/resources/images/theme/bg_right.gif" width="25"/>
</div>
</body>
</html>