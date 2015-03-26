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
<title><comm:message key="manager.icuMonit" />
</title>
<comm:pageHeader hasEcList="false" />
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/resources/css/style.css"/>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/jqueryalerts/jquery.alerts.css"  media="screen" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/style/multiselect/common.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/style/multiselect/jquery-ui-1.8.14.custom.css"/>
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/style/multiselect/ui.multiselect.css" />
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/jqueryalerts/jquery.alerts.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/My97DatePicker/WdatePicker.js"></script>
<script type="text/JavaScript">
<!--
	function upfile()
	{
	    window.open("<%=request.getContextPath()%>/resources/fileupload.jsp","mywindow","height=100, width=400, top=0, left=0, toolbar=no, menubar=no, scrollbars=no, resizable=no,location=no, status=no");
	}
	function begintime()
	{
		var obj=document.getElementById("datetime");
		setDayHM(obj);
	}

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
		 if  (patient_age  >  200  ||  patient_age  <  1) {
			 jAlert("<comm:message key='unified.need.patient_right_age'/>", "<comm:message key='meeting.m.infotishi'/>");
			 return false;
		 }
		 var chief_Complaint=document.getElementById("chief_Complaint").value;
		 if(chief_Complaint=="") {
			 jAlert("<comm:message key='unified.need.chief_Complaint'/>", "<comm:message key='meeting.m.infotishi'/>");
			 return false;
		 }
		 var medical_record=document.getElementById("medical_record").value;
		 if(medical_record=="") {
			 jAlert("<comm:message key='unified.need.medical_record'/>", "<comm:message key='meeting.m.infotishi'/>");
			 return false;
		 }
		 var purpose=document.getElementById("purpose").value;
		 if(purpose=="") {	
			 jAlert("<comm:message key='unified.need.purpose'/>", "<comm:message key='meeting.m.infotishi'/>");
			 return false;
		 }
		 var deptmentid=$('#departmentSelected option').length;
		 if(deptmentid==null||deptmentid<1) {
			  jAlert("<comm:message key='unified.need.dept'/>", "<comm:message key='meeting.m.infotishi'/>");
			 return false;
		 }
		 var icdDisplayRadio = $(":radio[name=icdDisplayRadio][checked]").val(); 
		 if(icdDisplayRadio=='0') {
			 var icdUserDefined = document.getElementById("icdUserDefined").value;
			 if(icdUserDefined==null||icdUserDefined=="") {
				  jAlert("<comm:message key='unified.need.userDefined'/>", "<comm:message key='meeting.m.infotishi'/>");
				 return false;
			 }
		 }else{
			 var intSelectedIcd10 = $('#icd10Selected option').length;
			 if(intSelectedIcd10==null||intSelectedIcd10<1) {
				  jAlert("<comm:message key='unified.need.icd10Dic'/>", "<comm:message key='meeting.m.infotishi'/>");
				 return false;
			 }
		 }
		 var meetingtypeid=document.getElementById("meetingtypeid").value;
		 if(meetingtypeid=="") {
			  jAlert("<comm:message key='unified.need.meetingtype'/>", "<comm:message key='meeting.m.infotishi'/>");
			 return false;
		 }
		  var meetingleveld=document.getElementById("meetingleveld").value;
		 if(meetingleveld=="") {
			  jAlert("<comm:message key='unified.need.meetinglevel'/>", "<comm:message key='meeting.m.infotishi'/>");
			 return false;
		 }
		 var author_mobile = document.getElementById("author_mobile").value;
		 if(author_mobile!=""&&!isMobel(author_mobile)){
			  jAlert("手机号码格式不正确", "<comm:message key='meeting.m.infotishi'/>");
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
			 jAlert("电子邮箱格式不正确", "<comm:message key='meeting.m.infotishi'/>");
		     return false;
		 }
		 $('#departmentSelected option').each(function(i, n){
			 $(n).attr('selected', 'selected');
		 });
		 $('#icd10Selected option').each(function(i, n){
			 $(n).attr('selected', 'selected');
		 });

		 
		 var startTime=document.getElementById("startTime").value;
		 if(strTrim(startTime) == "")
		 {
			 jAlert("<comm:message key='meeting.m.starttimealert'/>", "<comm:message key='meeting.m.infotishi'/>");
			 return false;
		 }
		 var endTime=document.getElementById("endTime").value;
		 if(strTrim(endTime) == "")
		 {
			 jAlert("<comm:message key='meeting.m.endtimealert'/>", "<comm:message key='meeting.m.infotishi'/>");
			 return false;
		 }

		 if (compareCheckDate(startTime,endTime) != '-1'){
			 jAlert("<comm:message key='js.need_input_larger' args='meeting.m.endtime,meeting.m.starttime'/>", "<comm:message key='meeting.m.infotishi'/>");
			 return false;
		 }
		 var meetingRoom=document.getElementById("meetingRoom").value;
		 if(strTrim(meetingRoom) == "")
		 {
			 jAlert("<comm:message key='js.need_select' args='admin.meetingroom' />", "<comm:message key='meeting.m.infotishi'/>");
			 return false;
		 }
		 
		 var devicesNo=document.getElementById("devicesNo").value;
		 if(strTrim(devicesNo) == "")
		 {
			 jAlert("<comm:message key='js.need_select' args='icu.mobile.devices.notnull' />", "<comm:message key='meeting.m.infotishi'/>");
			 return false;
		 }
		 //var timeRepeatCheckFlag = document.getElementById("timeRepeatCheckFlag").value;
		 //var exceptMeetingId = '';
		 //if(timeRepeatCheckFlag=='Y'){
			 //exceptMeetingId = document.getElementById("exceptMeetingId").value;
		 //}
		 var meetingTypestr = $("#meetingTypestr").val();
		 var exceptMeetingId = document.getElementById("exceptMeetingId").value;
		 $.ajaxSettings.traditional = true;
		 $.ajax({url:'${pageContext.request.contextPath}/meeadmdbd/checkTimeRepeat',
			 	type: "POST",
			 	dataType : 'text',
			 	async:false,
			 	data: {startTime :startTime, endTime : endTime,meetingRoom : meetingRoom,exceptMeetingId:exceptMeetingId,meetingTypestr:meetingTypestr},
				success:function(msg){
					if(msg == 'repeat'){
						$("#timeRepeatFlag").val("timeIsRepeat");
					}else if(msg == 'ok'){
						$("#timeRepeatFlag").val("timeIsOk");
					}
				}
		 });
		 
			 
		 var timeRepeatFlag = $("#timeRepeatFlag").val();
		 if(timeRepeatFlag=='timeIsRepeat'){
			 jAlert("<comm:message key='meeting.pleaseChooseNewTime'/>", "<comm:message key='meeting.m.infotishi'/>");
			return false;
		 }
		 var intSelectedInnaiUser = $('#innaiUserSelected option').length;
		 var intSelectedInterUser = $('#interUserSelected option').length;
		 var intSelectedCommonUser = $('#commonUserSelected option').length;
		 if(intSelectedInnaiUser==0&&intSelectedInterUser==0&&intSelectedCommonUser==0){
			 jAlert("<comm:message key='meeting.pleaseChoosePeople'/>", "<comm:message key='meeting.m.infotishi'/>");
			 return false;
		 }
		 
		 var selectedUserIds = "";
			
		//获取被选择的专家
		var innaiUserSelecteds=$('#innaiUserSelected option');
		//迭代每一个专家，将所有专家用“|”区分，组合成字符串
		$.each(innaiUserSelecteds,function(key,val){
			selectedUserIds = selectedUserIds + innaiUserSelecteds[key].value+"|";
		});
	
		//获取被选择的共同体
		var commonUserSelecteds=$('#commonUserSelected option');
		//迭代每一个共同体，将所有专家用“|”区分，组合成字符串
		$.each(commonUserSelecteds,function(key,val){
			selectedUserIds = selectedUserIds + commonUserSelecteds[key].value+"|";
		});
		
		//获取被选择的院际
		var interUserSelecteds=$('#interUserSelected option');
		//迭代每一个共同体，将所有专家用“|”区分，组合成字符串
		$.each(interUserSelecteds,function(key,val){
			selectedUserIds = selectedUserIds + interUserSelecteds[key].value+"|";
		});
		document.getElementById("selectedUserIds").value=selectedUserIds;

		return true;
	}
	
	function setEndTime() {
		var startTime=document.getElementById("startTime").value;
		 if(!strTrim(startTime) == ""){
			 startTime=startTime.replace(/-/g, "/");
			 var startTimetemp = new Date(startTime);
			 var endDate = new Date(startTimetemp.valueOf() + 60*60*1000); 
			 document.getElementById("endTime").value=getDate(endDate,"YYYY-MM-DD HH:MM");
		 }
	}
	
	
	
	//手机验证
	function isMobel(value){  
		if(isNumber(value)&&value.length==11){
			return true;
		}else{
		  return false;
		}
	}

	function  delstudy(liid,fileid){		
		jConfirm("<comm:message key='js.confirm_delete_image'/>\n\n<comm:message key='admin.click_ok'/>", "<comm:message key='meeting.m.infotishi'/>", function(select){
			if(select){
				var filelist=document.getElementById("studylist");
				var filevalue=document.getElementById("filevalue");
				var delobj=document.getElementById(fileid);
				var testobj=document.getElementById(liid);
		
				testobj.parentNode.removeChild(testobj);
				filevalue.removeChild(delobj);
			}
		return false;
		});
	}

	function  delfile(liid,fileid){
		jConfirm("<comm:message key='js.confirm_delete_accessory'/>\n\n<comm:message key='admin.click_ok'/>", "<comm:message key='meeting.m.infotishi'/>", function(select){
			if(select){
				var filelist=document.getElementById("filelist");
			    var filevalue=document.getElementById("filevalue");
				var delobj=document.getElementById(fileid);
				var testobj=document.getElementById(liid);	
				testobj.parentNode.removeChild(testobj);
				filevalue.removeChild(delobj);
			}
			return false;	
		});
	}
	
	function icdDisplayCheck(){
		
		var icdDisplayRadio = $(":radio[name=icdDisplayRadio][checked]").val(); 
		if(icdDisplayRadio=='0') {
			$("#icdUserDefinedDisplay").show();
			$("#icdDefaultDisplay").hide();
			var intSelectedIcd10 = $('#icd10Selected option').length;
	        if(intSelectedIcd10>0){
	        	document.getElementById("queryIcd10Name").value="";
		        document.getElementById("icd10OldValue").value="";
		        $("#icd10Selected").empty();  
		        changeIcd10();
				document.getElementById("intSelectedIcd10").innerHTML="0";
	        }
		} else {
			$("#icdDefaultDisplay").show();
			$("#icdUserDefinedDisplay").hide();
			document.getElementById("icdUserDefined").value="";
			document.getElementById("hint1").innerHTML = "("+200+"/" +200+ ") ";
		} 
	}

	  function myAlert(){
		var message='<%=returnmessage%>';
		if(message&&message!=""&&message!="null") {
			jAlert(message, "<comm:message key='meeting.m.infotishi'/>");
			}
		}
		$(document).ready(function() {
			$("#checkone").click(function() {
				if($("#checkone").val()=='1') {
					$("#itd").show();
					$("#checkone").val('0');
				} else {
					$("#itd").hide();
					$("#checkone").val('1');
				}
			});
	       	var count=getUnicodeLength(document.getElementById("medical_record").value);
		   	$('#tip').html(500-count);
		   	var count1=getUnicodeLength(document.getElementById("icdUserDefined").value);
		   	$('#tip1').html(200-count1);
		   	
		   	$("#departmentSelect").dblclick(function(){
				rightmoveDepartment();
			});
			$("#departmentSelected").dblclick(function(){
				leftmoveDepartment();
			});
			$("#icd10Select").dblclick(function(){
				rightmoveIcd10();
			});
			$("#icd10Selected").dblclick(function(){
				leftmoveIcd10();
			});
			$("#innaiUserSelect").dblclick(function(){
				rightmoveInnaiUser();
			});
			$("#innaiUserSelected").dblclick(function(){
				leftmoveInnaiUser();
			});
			$("#interUserSelect").dblclick(function(){
				rightmoveInterUser();
			});
			$("#interUserSelected").dblclick(function(){
				leftmoveInterUser();
			});
			$("#commonUserSelect").dblclick(function(){
				rightmoveCommonUser();
			});
			$("#commonUserSelected").dblclick(function(){
				leftmoveCommonUser();
			});
			
			//科室
			changedepart();
			
	        var intSelectedDept = $('#departmentSelected option').length;
	        document.getElementById("intSelectedDept").innerHTML=intSelectedDept;
			//Icd10
			changeIcd10();
			var intSelectedIcd10 = $('#icd10Selected option').length;
	        document.getElementById("intSelectedIcd10").innerHTML=intSelectedIcd10;
			
			//院内
			changeInnaiUser();
			var intSelectedInnaiUser = $('#innaiUserSelected option').length;
	        document.getElementById("intSelectedInnaiUser").innerHTML=intSelectedInnaiUser;
	        
	      	//院际
			changeInterUser();
			var intSelectedInterUser = $('#interUserSelected option').length;
	        document.getElementById("intSelectedInterUser").innerHTML=intSelectedInterUser;
	        
	      	//共同体
			changeCommonUser();
			var intSelectedCommonUser = $('#commonUserSelected option').length;
	        document.getElementById("intSelectedCommonUser").innerHTML=intSelectedCommonUser;
	        
			
	        
			setInterval(function() {
				//比对部门的速查条件是否有变化
				var departmentOldValue = document.getElementById("departmentOldValue").value;
				var queryDepartmentName=$('#queryDepartmentName').val();
				if(departmentOldValue!=queryDepartmentName){
					changedepart();
				}
				document.getElementById("departmentOldValue").value=queryDepartmentName;
				
				//比对icd10的速查条件是否有变化
				var icd10OldValue = document.getElementById("icd10OldValue").value;
				var queryIcd10Name=$('#queryIcd10Name').val();
				if(icd10OldValue!=queryIcd10Name){
					changeIcd10();
				}
				document.getElementById("icd10OldValue").value=queryIcd10Name;
				
				//比对院内专家的速查条件是否有变化
				var innaiUserOldValue = document.getElementById("innaiUserOldValue").value;
				var queryInnaiUserName=$('#queryInnaiUserName').val();
				if(innaiUserOldValue!=queryInnaiUserName){
					changeInnaiUser();
				}
				document.getElementById("innaiUserOldValue").value=queryInnaiUserName;
				
				//比对院际专家的速查条件是否有变化
				var interUserOldValue = document.getElementById("interUserOldValue").value;
				var queryInterUserName=$('#queryInterUserName').val();
				if(interUserOldValue!=queryInterUserName){
					changeInterUser();
				}
				document.getElementById("interUserOldValue").value=queryInterUserName;
				
				//比对共同体专家的速查条件是否有变化
				var commonUserOldValue = document.getElementById("commonUserOldValue").value;
				var queryCommonUserName=$('#queryCommonUserName').val();
				if(commonUserOldValue!=queryCommonUserName){
					changeCommonUser();
				}
				document.getElementById("commonUserOldValue").value=queryCommonUserName;
				
			}, 3000);
		});
		function changedepart(){
			var queryDepartmentName=$('#queryDepartmentName').val();
			$("#departmentSelect").empty();  	    
	   	    var departId;
		    var departmentSelected = $("#departmentSelected").find("option");
			$.ajaxSettings.traditional = true;
		    //点击第一个select多选框的某个科室并和第三个进行对比，如果第三个在第三个中有，则在第二个select框中不显示
		    $.ajax({type :'POST',  url : '${pageContext.request.contextPath}/unified/getDepts', 
					data : {queryDepartmentName : queryDepartmentName},
					dataType : 'json',
					dataFilter : function(json, type){
						if(json.indexOf('<html>') != -1){
							window.location.href="<%=request.getContextPath()%>/index.jsp?message=system.session_expire";
							return;
						}
						return json;
					},
					success : function(json) {
					$.each(json, function(i,n){
				        var ss = false;		   
				        //得到已选择的科室
					    $.each(departmentSelected,function(key,val){//key是数组的下标，val是数组的值
					    	departId = $(val).val();
		                     if(departId == json[i].id){
		                    	 ss = true;
		                     }
					    	});
					    if(!ss){
							$("<option value="+json[i].id+ ">"+json[i].name+"</option>").appendTo("#departmentSelect"); 	
					    }
					});
				}
			});
		}
		//双击添加科室
		function rightmoveDepartment(){
			var so = $("#departmentSelect option:selected");
	        $("#departmentSelected").append(so);
	        var intSelectedDept = $('#departmentSelected option').length;
	        document.getElementById("intSelectedDept").innerHTML=intSelectedDept;
		}
		
		//双击删除科室
		function leftmoveDepartment(){
			var so = $("#departmentSelected option:selected");
	        $("#departmentSelect").append(so);
	        var intSelectedDept = $('#departmentSelected option').length;
	        document.getElementById("intSelectedDept").innerHTML=intSelectedDept;
	        sortOptions($('#departmentSelect option'),$("#departmentSelect"));
		}
		
		function changeIcd10(){
			var queryIcd10Name=$('#queryIcd10Name').val();
			$("#icd10Select").empty();  	   
			var icd10SelectedIds=$('#icd10Selected option');//获取被选中的专家
			
			//迭代每一个专家，将所有专家用“|”区分，组合成字符串
			var icd10Ids = "";
			$.each(icd10SelectedIds,function(key,val){
				icd10Ids = icd10Ids + icd10SelectedIds[key].value+"|";
			});
	   	    var icd10Id;
		    var icd10Selected = $("#icd10Selected").find("option");
			$.ajaxSettings.traditional = true;
		    //点击第一个select多选框的某个科室并和第三个进行对比，如果第三个在第三个中有，则在第二个select框中不显示
		    $.ajax({type :'POST',  url : '${pageContext.request.contextPath}/unified/getDiagnosis', 
					data : {queryIcd10Name : queryIcd10Name,icd10Ids:icd10Ids},
					dataType : 'json',
					dataFilter : function(json, type){
						if(json.indexOf('<html>') != -1){
							window.location.href="<%=request.getContextPath()%>/index.jsp?message=system.session_expire";
							return;
						}
						return json;
					},
					success : function(json) {
					$.each(json, function(i,n){
				        var ss = false;		   
				        if(queryIcd10Name==null||queryIcd10Name.length==0){
				        	ss = false;
				        }else{
				        	//得到已选择的科室
						    $.each(icd10Selected,function(key,val){//key是数组的下标，val是数组的值
					    		 icd10Id = $(val).val();
			                     if(icd10Id == json[i].id){
			                    	 ss = true;
			                     }
					    	});
				        }
				        
					    if(!ss){
							$("<option value="+json[i].id+ ">"+json[i].name+"</option>").appendTo("#icd10Select"); 	
					    }
					});
				}
			});
		}
		//双击添加icd10
		function rightmoveIcd10(){
			var so = $("#icd10Select option:selected");
	        $("#icd10Selected").append(so);
	        var intSelectedIcd10 = $('#icd10Selected option').length;
	        document.getElementById("intSelectedIcd10").innerHTML=intSelectedIcd10;
	        var queryIcd10Name=$('#queryIcd10Name').val();
	        if(queryIcd10Name==null||queryIcd10Name.length==0){
	        	changeIcd10();
	        }
		}
		
		//双击删除icd10
		function leftmoveIcd10(){
			var so = $("#icd10Selected option:selected");
	        $("#icd10Select").append(so);
	        var intSelectedIcd10 = $('#icd10Selected option').length;
	        document.getElementById("intSelectedIcd10").innerHTML=intSelectedIcd10;
	        var queryIcd10Name=$('#queryIcd10Name').val();
	        if(queryIcd10Name==null||queryIcd10Name.length==0){
	        	changeIcd10();
	        }else{
	        	sortOptions($('#icd10Select option'),$("#icd10Select"));
	        }
		}
		
		function changeInnaiUser(){
			var queryInnaiUserName=$('#queryInnaiUserName').val();
			$("#innaiUserSelect").empty();  	   
			var innaiUserSelecteds=$('#innaiUserSelected option');//获取被选择的专家
			
			//迭代每一个专家，将所有专家用“|”区分，组合成字符串
			var innaiUserIds = "";
			$.each(innaiUserSelecteds,function(key,val){
				innaiUserIds = innaiUserIds + innaiUserSelecteds[key].value+"|";
			});
	   	    //var innaiUserId;
		    //var innaiUserSelected = $("#innaiUserSelected").find("option");
			$.ajaxSettings.traditional = true;
		    $.ajax({type :'POST',  url : '${pageContext.request.contextPath}/meeadmdbd/getUserSelectItem', 
					data : {queryUserName:queryInnaiUserName,userIds:innaiUserIds,departmentKind:'${HIS_DEPT_CODE}'},
					dataType : 'json',
					dataFilter : function(json, type){
						if(json.indexOf('<html>') != -1){
							window.location.href="<%=request.getContextPath()%>/index.jsp?message=system.session_expire";
							return;
						}
						return json;
					},
					success : function(json) {
					$.each(json, function(i,n){
						$("<option value="+json[i].id+ ">"+json[i].name+"</option>").appendTo("#innaiUserSelect");
					});
				}
			});
		}
		//双击添加院内专家
		function rightmoveInnaiUser(){
			var so = $("#innaiUserSelect option:selected");
	        $("#innaiUserSelected").append(so);
	        var intSelectedInnaiUser = $('#innaiUserSelected option').length;
	        document.getElementById("intSelectedInnaiUser").innerHTML=intSelectedInnaiUser;
	        var queryInnaiUserName=$('#queryInnaiUserName').val();
	        if(queryInnaiUserName==null||queryInnaiUserName.length==0){
	        	changeInnaiUser();
	        }
		}
		
		//双击删除院内专家
		function leftmoveInnaiUser(){
			var so = $("#innaiUserSelected option:selected");
	        $("#innaiUserSelect").append(so);
	        var intSelectedInnaiUser = $('#innaiUserSelected option').length;
	        document.getElementById("intSelectedInnaiUser").innerHTML=intSelectedInnaiUser;
	        var queryInnaiUserName=$('#queryInnaiUserName').val();
	        if(queryInnaiUserName==null||queryInnaiUserName.length==0){
	        	changeInnaiUser();
	        }
	        sortOptions($('#innaiUserSelect option'),$("#innaiUserSelect"));
		}
		//院际
		function changeInterUser(){
			//模糊查询条件
			var queryInterUserName=$('#queryInterUserName').val();
			//清空左边选单
			$("#interUserSelect").empty(); 
			//获取被选择的院际专家
			var interUserSelecteds=$('#interUserSelected option');
			//迭代每一个专家，将所有专家用“|”区分，组合成字符串
			var interUserIds = "";
			$.each(interUserSelecteds,function(key,val){
				interUserIds = interUserIds + interUserSelecteds[key].value+"|";
			});
			$.ajaxSettings.traditional = true;
		    $.ajax({type :'POST',  url : '${pageContext.request.contextPath}/meeadmdbd/getUserSelectItem', 
					data : {queryUserName:queryInterUserName,userIds:interUserIds,departmentKind:'${OUT_DEPT_CODE}'},
					dataType : 'json',
					dataFilter : function(json, type){
						if(json.indexOf('<html>') != -1){
							window.location.href="<%=request.getContextPath()%>/index.jsp?message=system.session_expire";
							return;
						}
						return json;
					},
					success : function(json) {
						$.each(json, function(i,n){
							$("<option value="+json[i].id+ ">"+json[i].name+"</option>").appendTo("#interUserSelect");
						});
				}
			});
		}
		//双击添加院际
		function rightmoveInterUser(){
			var so = $("#interUserSelect option:selected");
	        $("#interUserSelected").append(so);
	        var intSelectedInterUser = $('#interUserSelected option').length;
	        document.getElementById("intSelectedInterUser").innerHTML=intSelectedInterUser;
		}
		
		//双击删除院际
		function leftmoveInterUser(){
			var so = $("#interUserSelected option:selected");
	        $("#interUserSelect").append(so);
	        var intSelectedInterUser = $('#interUserSelected option').length;
	        document.getElementById("intSelectedInterUser").innerHTML=intSelectedInterUser;
	        sortOptions($('#interUserSelect option'),$("#interUserSelect"));
		}
		
		//共同体
		function changeCommonUser(){
			//模糊查询条件
			var queryCommonUserName=$('#queryCommonUserName').val();
			//清空左边选单
			$("#commonUserSelect").empty(); 
			//获取被选择的共同体专家
			var commonUserSelecteds=$('#commonUserSelected option');
			//迭代每一个共同体，将所有专家用“|”区分，组合成字符串
			var commonUserIds = "";
			$.each(commonUserSelecteds,function(key,val){
				commonUserIds = commonUserIds + commonUserSelecteds[key].value+"|";
			});
			$.ajaxSettings.traditional = true;
		    $.ajax({type :'POST',  url : '${pageContext.request.contextPath}/meeadmdbd/getUserSelectItem', 
					data : {queryUserName:queryCommonUserName,userIds:commonUserIds,departmentKind:'${COMMUNITY_DEPT_CODE}'},
					dataType : 'json',
					dataFilter : function(json, type){
						if(json.indexOf('<html>') != -1){
							window.location.href="<%=request.getContextPath()%>/index.jsp?message=system.session_expire";
							return;
						}
						return json;
					},
					success : function(json) {
						$.each(json, function(i,n){
							$("<option value="+json[i].id+ ">"+json[i].name+"</option>").appendTo("#commonUserSelect");
						});
				}
			});
		}
		//双击添加共同体
		function rightmoveCommonUser(){
			var so = $("#commonUserSelect option:selected");
	        $("#commonUserSelected").append(so);
	        var intSelectedCommonUser = $('#commonUserSelected option').length;
	        document.getElementById("intSelectedCommonUser").innerHTML=intSelectedCommonUser;
		}
		
		//双击删除共同体
		function leftmoveCommonUser(){
			var so = $("#commonUserSelected option:selected");
	        $("#commonUserSelect").append(so);
	        var intSelectedCommonUser = $('#commonUserSelected option').length;
	        document.getElementById("intSelectedCommonUser").innerHTML=intSelectedCommonUser;
	        sortOptions($('#commonUserSelect option'),$("#commonUserSelect"));
		}
		
		function cancelFun(){
			jConfirm("<comm:message key='js.confirm_cancel' args='comm.modify'/>\n\n<comm:message key='admin.click_ok'/>", "<comm:message key='meeting.m.infotishi'/>",function(resultConfirm){
				if(resultConfirm) {
					location='<%=request.getContextPath()%>/meeadIndex/icuMonitQuery';
				} else {
					return false;
				}
			});
			
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
		
		function Exp(filepath,title){  
			title = encodeURI(encodeURI(title));
			window.location.href="<%=request.getContextPath()%>/download?filepath="+filepath+"&filename="+title;
		}
//-->
</script>
<style type="text/css">
.multiselect{width:450px;border: 1px solid #7F9DB9;}
.required{color:red;}
.text-long{width:450px;}
#viewappinfor{margin-left:25px;margin-top:20px;margin-bottom:20px;margin-right:25px;position:relative;background-color:#dee; overflow-y:auto;}
.titleStyle{color:#3c3645;font-size:18px;font-weight: bold;height:45px;margin-top:15px;margin-bottom:0px;margin-left:40px;}
.subinfor{border:0px solid #7F9DB9;background-color:#F8F8FF;margin-left:25px;margin-right:25px;}
.meetingApp td input{height:20px;line-height:20px;}
select{background: #ffffff;border: 1px solid #7F9DB9;width:100px;color:#000000;height:22px;}
.patientInfor tr td{border:#D2D2D2;height:20px;line-height:20px;}
.patientInfor tr td input{background: #ffffff;margin-left:3px;border: 1px solid #7F9DB9;height:20px;line-height:20px;}
#rightdown{background-image: url("../../resources/images/table/down.gif");background-repeat: repeat-x;width:19px;height:28px;margin-bottom:2px;margin-left:15px;margin-right:15px;margin-top:8px;}
#leftup{background-image: url("../../resources/images/table/up.gif");background-repeat: repeat-x;width:19px;height:28px;margin-bottom:2px;margin-left:15px;margin-right:15px;margin-top:20px;}
._table{border-collapse:collapse; border:solid #D2D2D2; border-width:1px 0 0 1px; width:470; margin:auto; }
._table caption {font-size:18px; font-weight:bolder;}
._table tr td input{width:151px;}
._table td{height:20px; line-height:20px;border:1px solid #D2D2D2;}
._table th,.submit_table td {border:solid #D2D2D2; border-width:0 1px 1px 0; padding:2px;}
a:hover{text-decoration:underline;}
</style>

</head>
<body style="padding:0; margin:0;background:url('../../resources/images/theme/bg_right-2.gif');" onload="myAlert();heigthReset(64);" onresize="heigthReset(64);">
<div id="center">
	<comm:navigator position="common.icMonit.applyForConsultation>>common.icMonit.applyForConsultation.modify" />
	<div style="overflow-x:hidden;" id="main" >
<form method="post"   action="<%=request.getContextPath()%>/meeadmdbd/editIcuMonitorForPass"  onsubmit="return check()">
	
	<div id="viewappinfor">
	    <div class="subtitle">
				<div class="titleStyle">
					<comm:message key='unified.basic_patient_information' />
				</div>	    
	    </div>
	    <div class="subinfor" id="patientinfor1">
	    	<input type="hidden" id="departmentOldValue" value=""/>
	    	<input type="hidden" id="icd10OldValue" value=""/>
	    	
	    	<input type="hidden" id="innaiUserOldValue" value=""/>
		    <input type="hidden" id="interUserOldValue" value=""/>
		    <input type="hidden" id="commonUserOldValue" value=""/>
		    <input type="hidden" id="timeRepeatFlag" value="timeIsRepeat"/>
		    <input type="hidden" id="selectedUserIds" name="selectedUserIds" value=""/>	
		    <input type="hidden" id="exceptMeetingId" name="exceptMeetingId" value="${exceptMeetingId}"/>
		    <input type="hidden" value="${app.requester.userId}" name="userId"/> 	
		    <input type="hidden" value="${app.id}" name="id"/>
		    <input type="hidden" value="${expectedTime}" name="expectedTime"/>
		    <input type="hidden" value="${app.noticeSendTimes}" name="noticeSendTimes"/>
		    <input type="hidden" id="selectedstr" name="selectedstr" value="${selectstr }"/>
		    <input type="hidden" id="meetingTypestr" name="meetingTypestr" value="${meeting.meetingType.id }"/>
		    
		    <c:if test="${app.startTime<=currDate}">
		    	<input type="hidden" id="timeRepeatCheckFlag" value="Y"/>
		    </c:if>
		    <c:if test="${app.startTime>currDate}">
		    	<input type="hidden" id="timeRepeatCheckFlag" value="N"/>
		    </c:if>
		    
			<table width="100%" border="0" cellspacing="2" cellpadding="1" class="meetingApp">
				<tr>
					<td align="right" width="20%"><comm:message key='common.patientInfo.socialSecurityNO' />:&nbsp;</td>
					<td height="30">
						<input type="text" name="patientInfo.socialSecurityNO" size="25" id="socialSecurityNO" onkeyup="value=value.replace(/[^\d\.]/g,'')" maxlength="20" value="${app.patientInfo.socialSecurityNO}"/>
					</td>
				</tr>
				<tr>
					<td width="20%"  align="right"><comm:message key='unified.and.expert_name' />:<span class="required">*</span></td>
					<td height="30">
						<table width="100%" border="0">
				             <tr>
				                  <td width="20%"><input type="text" name="patientInfo.name" id="patient_name" size="25" maxlength="20" value="${app.patientInfo.name}"/> </td>
				                  <td width="20%" align="right"><comm:message key='unified.and.expert_user_sex' />:<span class="required">*</span></td>
				                  <td width="15%" align="left">
		                               <select name="patientInfo.sex" id="patient_sex" style="width:45px;" value="${app.patientInfo.sex}">
									        <option value="0" <c:if test="${app.patientInfo.sex == 0}">selected="selected"</c:if>><comm:message key='comm.male' /></option>
									        <option value="1" <c:if test="${app.patientInfo.sex == 1}">selected="selected"</c:if>><comm:message key='comm.female' /></option>
							           </select>					                  
				                  </td>
				                  <td width="13%" align="right"><comm:message key='unified.and.expert_patient.age' />:<span class="required">*</span></td>
				                  <td width="27%" align="left"><input type="text" name="patientInfo.age" id="patient_age" value="${app.patientInfo.age}" size="8" onkeyup="value=value.replace(/[^\d\.]/g,'')" maxlength="3"/></td>
				             </tr>		             
					     </table>
					</td>
				</tr>
				<tr>
					<td align="right" width="20%"><comm:message key='unified.address' />:&nbsp;</td>
					<td height="30">
						<input type="text" maxlength="200" name="patientInfo.address" size="80" id="address" value="${app.patientInfo.address}" >&nbsp;
					</td>
				</tr>
				<!-- <tr>
					<td align="right" width="20%">&nbsp;</td>
					<td align="center" width="80%">
					     <table style="width:100%;border:0px solid green;">
					           <tr><td colspan="2">&nbsp;</td></tr>
					           <tr>
					                <td width="32px;">&nbsp;</td>
					           		<td width="200px;" id="hainaAnch"><a onclick="return checkSSN();" style="color: blue;decoration:underline;cursor:pointer">上传患者放射影像</a></td>
					           </tr>
					     </table>
					</td>
				</tr>	 -->							
			</table>		    
	    </div>
	    <div class="subtitle">
				<div class="titleStyle">
					   <strong><comm:message key='unified.medical_record' /></strong>
				</div>	    
	    </div>
	    <div class="subinfor" id="patientinfor2">
			<table width="100%" class="meetingapp" border="0" align="center" cellspacing="0" cellpadding="0">				
                <tr height="3"><td colspan="2">&nbsp;</td></tr>
				<tr>				    
					<td width="20%"  align="right"><comm:message key='unified.and.expert_complaint' />:<span	class="required">*</span>
					</td>
					<td><input type="text" style="height:20px;line-height:20px;" name="chiefComplaint" id="chief_Complaint"  id="purpose" size="80" maxlength="200" value="${app.chiefComplaint}"/> 
				</tr>
                <tr>
					<td width="20%"  align="right"><comm:message key='unified.medical_record' />:<span	class="required">*</span><div id="hint" style="margin-right: 10px;">(<span id="tip"></span>/500)</div>
					</td>
					<td height="105"><textarea name="medicalRecord" id="medical_record" style="border: 1px solid #7F9DB9;" rows="5" class="text-long"  maxlength="500" onpaste="return realTimeCountClip(this,'hint');" onkeyup="javascript:realTimeCount(this,'hint');" title=" <comm:message key="comm.content_maxlength"/>500">${app.medicalRecord}</textarea>
					</td>
				</tr>
				<tr>
					<td width="20%" align="right"><comm:message key='unified.and.expert_purpose' />:<span	class="required">*</span></td>
					<td><input type="text" name="purpose" style="line-height:20px;height:20px;" id="purpose" size="55" maxlength="200" value="${app.purpose}"/>
					<input type="checkbox" name="checkone" style="line-height:20px;height:20px;border:0px;background:#F8F8FF;height:13px;vertical-align:middle;align:absmiddle;" id="checkone" value="1" <c:if test="${app.problem != null && fn:length(app.problem) > 0}">checked='checked'</c:if>/>
					 <comm:message key='unified.issuesToBe_discussed' />
					</td>
				</tr>
				<tr><td colspan="4">&nbsp;</td></tr>
				<tr id="itd" <c:if test="${app.problem == null || fn:length(app.problem) == 0 }">style="display: none;"</c:if>>
					<td width="20%" align="right"><comm:message key='unified.issuesToBe_discussed' />:&nbsp;</td>
					<td><input type="text" name="problem" style="line-height:20px;height:20px;" id="problem" size="55" value="${app.problem }"/>
						</td>
				</tr>
				<tr>
					<td width="20%" align="right"><comm:message key='unified.dept' />:<span class="required">*</span>
					</td>
					<td>
						<table style="border:1px solid #000;">
							<tr>
								<td bgcolor="#D0D0D0" style="height:25px;border-right:1px solid #000;">
									 &nbsp;<strong>速查条件</strong><input id="queryDepartmentName" style="align:right;width:130px;line-height:18px;height:18px;" value="" size="23" />
								</td>
								<td>
								</td>
								<td bgcolor="#D0D0D0"  style="height:25px;border-left:1px solid #000;">
									&nbsp;<strong>已选择<span id="intSelectedDept">0</span>项</strong>
								</td>
							</tr>
							<tr>
								<td style="border-right:1px solid #000;width:200px;">
									<select multiple="multiple" name="departmentSelect" id="departmentSelect"  style="align:center;width:200px;height:143px;border:none;">
										
									</select>	
								</td>
								
								<td align="center">
									<div id="rightdown" onclick="rightmoveDepartment()"></div>
									<div id="leftup" onclick="leftmoveDepartment()"></div>
								</td>
								
								<td style="border-left:1px solid #000;">
									<select align="center" multiple="multiple" name="departmentSelected" id="departmentSelected" style="width:200px;height:143px;border:none;">
										<c:forEach var="c_deptment" items="${selectedDeparts}">
					                		<option value="${c_deptment.id}">&nbsp;${c_deptment.name}</option> 
					                	</c:forEach>   
									</select>
								</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr height="3"><td colspan="2">&nbsp;</td></tr>
				<tr >
					<td width="20%"  align="right"><comm:message key='meeting.m.icd10' />:<span class="required">*</span>
					</td>
					<td>
						<input type="radio" style="border:0px;background:#F8F8FF;height:13px;vertical-align:middle;align:absmiddle;" onclick="icdDisplayCheck();" name="icdDisplayRadio" value="${ICD_RADIO_DEFAULT}" 
							<c:if test="${app.icdDisplayRadio == null || app.icdDisplayRadio==ICD_RADIO_DEFAULT}">
								checked 
							</c:if>
						/>ICD10
						&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="radio" style="border:0px;background:#F8F8FF;height:13px;vertical-align:middle;align:absmiddle;" onclick="icdDisplayCheck();" name="icdDisplayRadio" value="${ICD_RADIO_USERDEFINED}" 
							<c:if test="${app.icdDisplayRadio==ICD_RADIO_USERDEFINED}">
								checked 
							</c:if>
						/><comm:message key="unified.userDefined"/>
					</td>
				
				</tr>
				<tr height="1"><td colspan="2">&nbsp;</td></tr>	
				<tr id="icdUserDefinedDisplay" <c:if test="${app.icdDisplayRadio == null || app.icdDisplayRadio == ICD_RADIO_DEFAULT }">style="display: none;"</c:if>>
					<td width="20%"  align="right"><div id="hint1" style="margin-right: 10px;">(<span id="tip1"></span>/200)</div>
					</td>
					<td ><textarea name="icdUserDefined" id="icdUserDefined" style="border: 1px solid #7F9DB9;margin-top:2px; " rows="3" class="text-long" maxlength="200" onpaste="return realTimeCountClip(this,'hint1');" onkeyup="javascript:realTimeCount(this,'hint1');" title=" <comm:message key="comm.content_maxlength"/>200">${app.icdUserDefined}</textarea>
					</td>
				</tr>
				<tr id="icdDefaultDisplay" <c:if test="${app.icdDisplayRadio == ICD_RADIO_USERDEFINED }">style="display: none;"</c:if>>
					<td width="20%"  align="right">&nbsp;</td>
					<td>
						<table style="border:1px solid #000;">
							<tr>
								<td bgcolor="#D0D0D0" style="height:25px;border-right:1px solid #000;">
									 &nbsp;<strong>速查条件</strong><input id="queryIcd10Name" style="align:right;width:130px;line-height:18px;height:18px;" value="" size="23" />
								</td>
								<td>
								</td>
								<td bgcolor="#D0D0D0"  style="height:25px;border-left:1px solid #000;">
									&nbsp;<strong>已选择<span id="intSelectedIcd10">0</span>项</strong>
								</td>
							</tr>
							<tr>
								<td style="border-right:1px solid #000;width:200px;">
									<select multiple="multiple" name="icd10Select" id="icd10Select"  style="align:center;width:200px;height:143px;border:none;">
										
									</select>	
								</td>
								
								<td align="center">
									<div id="rightdown" onclick="rightmoveIcd10()"></div>
									<div id="leftup" onclick="leftmoveIcd10()"></div>
								</td>
								
								<td style="border-left:1px solid #000;">
									<select align="center" multiple="multiple" name="icd10Selected" id="icd10Selected" style="width:200px;height:143px;border:none;">
										<c:forEach var="icd10Dic" items="${selectedICD10s}">
											<option value="${icd10Dic.id}">${icd10Dic.name}</option>
										</c:forEach>  
									</select>
								</td>
							</tr>
						</table>
					<br/>
					</td>
				</tr>
				<tr height="3"><td colspan="2">&nbsp;</td></tr>
				<tr>
					<td width="20%"  align="right" valign="top"><comm:message key='unified.and.expert_accessories' />:&nbsp;</td>
					<td>
					<div class="main1_sub" style="border:0px solid red;">
					        <table width="100%" border="0">
					        	<tr>
					                <td width="50%">
					                	 <a href="#" onclick='sAlert("<comm:message key='unified.upload.accessories'/>")' style="COLOR:blue;margin-top:10px;">
											   <comm:message key='unified.select.accessories' />
					                     </a>
					                </td>
					               <%--  <td width="50%">
					                     <a href="#" onclick='sAlert2("<comm:message key='选择放射影像'/>")' style="COLOR:blue;margin-top:10px;">选择放射影像</a>
					                </td> --%>
					           </tr>
					           <tr>
					           
					                       <td valign="top" width="50%">
											<ul id="filelist" style="list-style-type:none;">
												<c:forEach var="myfilelist"	items="${app.accessories}">
													<c:if test="${myfilelist.type.value != 2 }">
														<li id = "li${myfilelist.id}">
														<div style="width:200px;">
														    <div style="float:left;width:80px;" title="${myfilelist.type.name}">${fn:substring(myfilelist.type.name, 0, 6)}<span>:</span></div>
														    <!-- <div style="float:left;">:</div> -->
														    <div style="float:left;width:80px;">
														       <a style="COLOR:blue;" href="#" onclick="Exp('${myfilelist.path}','${myfilelist.name}');" title="${myfilelist.name}">${fn:substring(myfilelist.name, 0, 6)}</a>
														    </div>
														    <div style="float:left;width:30px;margin-left:3px;">
														       <a style="COLOR:blue;" href="#" onclick="return delfile('li${myfilelist.id}','input${myfilelist.id}')"><comm:message key='unified.delete' /></a>
														    </div>
														</div>
														</li>
													</c:if>
												</c:forEach>
											</ul>
											<table style="display: none">
												<tr>
													<td id="filevalue">
														<c:forEach var="file" items="${app.accessories}">
															<input type="hidden" id="input${file.id}" name="myfiles" value="${file.name}@${file.path}@${file.type.id}@${file.type.name}"/>
														</c:forEach>
														<c:forEach var="file" items="${accsImage}">
															<input type="hidden" id="input${file.id}" name="hainaValue" value="${file.path}@${file.name}"/>
														</c:forEach>
													</td>
												</tr>
											</table>                      
					                      </td>					           
					                       <td valign="top" width="50%">
											  <ul id="studylist" style="list-style-type:none;">
											  	<c:forEach var="myfilelist"	items="${accsImage}">
													<c:if test="${myfilelist.type.value == 2 }">
														<li id = "li${myfilelist.id}">
														<div style="width:200px;">
														    <div style="float:left;width:40px;" title="${myfilelist.type.name}">
														          <comm:message key='unified.patient'/><span>:</span>
														    </div>
														    <!-- <div style="float:left;">:</div> -->
														    <div style="float:left;width:110px;" title="${myfilelist.name}">
														        ${fn:substring(myfilelist.name, 0, 6)}<comm:message key='common.platformIntegration.radiationImaging'/>
														    </div>
														    <div style="float:left;width:30px;">
														       <a style="COLOR:blue;" href="#" onclick="return delstudy('li${myfilelist.id}','input${myfilelist.id}')"><comm:message key='unified.delete' /></a>
														    </div>
														</div>
														<%-- <b>${myfilelist.type.name}:</b>
											   				${myfilelist.name}&nbsp;&nbsp;
											   				<a style="COLOR:blue;" href="#" onclick="return delfile('li${myfilelist.id}','input${myfilelist.id}')"><comm:message key='unified.delete' /></a> --%>
														</li>
													</c:if>
												</c:forEach>
											  </ul>
											  <table style="display: none">
													<tr>
														<td id="HainaId"></td>
														<td id="filevalue2"></td>
													</tr>
											  </table>
					                       </td>					           
					           </tr>
					      </table>
					</div>
				  </td>
				</tr>
			</table>		    
	    </div>
	    <div class="subtitle">
			<div class="titleStyle">
				<strong><comm:message key='manager.application_pass_information' /></strong>
			</div>	    
	    </div>
	    <div class="subinfor" id="patientinfor2">
			<table width="100%" border="0" cellspacing="2" cellpadding="1" class="meetingApp">
				<tr style="display:none">
					<td width="20%" height="30" align="right" ><comm:message key='unified.meetingtype' />:<span	class="required">*</span>
					</td>
					<td align="right">
					    <table width="100%" class="patientInfors" border="0">
					            <tr>
					                <td width="30%" >
										<select name="meetingType.id" id="meetingtypeid" style="width:180px;">
												<c:forEach var="meetingtype" items="${basecode['meetingtype']}">
													<option value="${meetingtype.id}" <c:if test="${meetingtype.id ==1}">selected</c:if>>${meetingtype.name}</option>
												</c:forEach>
										</select>					                	
					                </td>
					                <td width="16%" align="right"><comm:message key='unified.meetinglevel' />:<span class="required">*</span></td>
					                <td align="left">
										<select name="level.id" id="meetingleveld"  style="width:180px;">
												<c:forEach var="meetinglevel" items="${basecode['meetinglevel']}">
													<option value="${meetinglevel.id}">${meetinglevel.name}</option>
												</c:forEach>
										</select>					                
					                </td>
					            </tr>
					    </table>
					</td>
				</tr>
				<tr height="3"><td colspan="2">&nbsp;</td></tr>
				<tr>
				    <td colspan="2">
						<table background-color="#ddeefa" border="0">
							<tr>
								<td width="21%"  align="right" ><comm:message key='meeting.m.starttime' />:<span class="required">*</span></td>
								<td>
									<div class="main1_sub" style="border:0px solid red;">
									        <table width="100%" border="0">
									        	<tr>
									        		 <c:if test="${app.startTime<=currDate}">
									        			<td width="55%">
									                	 <input type="text" name="startTime1" id="startTime" class="Wdate"  onclick="var endTime=$dp.$('endTime');
															WdatePicker({readOnly:true,onpicked:setEndTime,dateFmt:'yyyy-M-d H:mm',minDate:'%y-%M-%d %H:%m:%s'})" 
															onfocus="this.blur()" style="border:#999 1px solid;height:20px;background:#fff url(<%=request.getContextPath()%>/resources/My97DatePicker/skin/datePicker.gif) 
															no-repeat right;width:126px;" value="<fmt:formatDate value="${app.startTime}" pattern="yyyy-MM-dd HH:mm" />"readonly="readonly"
														 	disabled="disabled"
														 />
										                </td>
										               <td width="55%">
										                     <comm:message key='meeting.m.endtime' />:<span class="required">*</span> 
															 <input type="text" name="endTime" id="endTime" class="Wdate"  onclick="WdatePicker({readOnly:true,dateFmt:'yyyy-M-d H:mm'})" 
																onfocus="this.blur()" style="border:#999 1px solid;height:20px;background:#fff url(<%=request.getContextPath()%>/resources/My97DatePicker/skin/datePicker.gif) no-repeat right;width:126px;" 
																value="<fmt:formatDate value="${app.endTime}" pattern="yyyy-MM-dd HH:mm" />"readonly="readonly" 
															 />	
										                </td>
										                <input type="hidden" name="startTime" class="Wdate"  onclick="var endTime=$dp.$('endTime');
															WdatePicker({readOnly:true,onpicked:setEndTime,dateFmt:'yyyy-M-d H:mm',minDate:'%y-%M-%d %H:%m:%s'})" 
															onfocus="this.blur()" style="border:#999 1px solid;height:20px;background:#fff url(<%=request.getContextPath()%>/resources/My97DatePicker/skin/datePicker.gif) 
															no-repeat right;width:126px;" value="<fmt:formatDate value="${app.startTime}" pattern="yyyy-MM-dd HH:mm" />"readonly="readonly"
														 />
														 <%-- <input type="hidden" name="endTime" class="Wdate"  onclick="WdatePicker({readOnly:true,dateFmt:'yyyy-M-d H:mm'})" 
																onfocus="this.blur()" style="border:#999 1px solid;height:20px;background:#fff url(<%=request.getContextPath()%>/resources/My97DatePicker/skin/datePicker.gif) no-repeat right;width:126px;" 
																value="<fmt:formatDate value="${app.endTime}" pattern="yyyy-MM-dd HH:mm" />"readonly="readonly" 
														/>	 --%> 
									        		   </c:if> 
									                   <c:if test="${app.startTime>currDate}">  
									        			<td width="55%">
									                	 <input type="text" name="startTime" id="startTime" class="Wdate"  onclick="var endTime=$dp.$('endTime');
															WdatePicker({readOnly:true,onpicked:setEndTime,dateFmt:'yyyy-M-d H:mm',minDate:'%y-%M-%d %H:%m:%s'})" 
															onfocus="this.blur()" style="border:#999 1px solid;height:20px;background:#fff url(<%=request.getContextPath()%>/resources/My97DatePicker/skin/datePicker.gif) 
															no-repeat right;width:126px;" value="<fmt:formatDate value="${app.startTime}" pattern="yyyy-MM-dd HH:mm" />"readonly="readonly"
														 />
										                </td>
										               
										                <td width="55%">
										                     <comm:message key='meeting.m.endtime' />:<span class="required">*</span> 
															 <input type="text" name="endTime" id="endTime" class="Wdate"  onclick="WdatePicker({readOnly:true,dateFmt:'yyyy-M-d H:mm'})" 
																onfocus="this.blur()" style="border:#999 1px solid;height:20px;background:#fff url(<%=request.getContextPath()%>/resources/My97DatePicker/skin/datePicker.gif) no-repeat right;width:126px;" 
																value="<fmt:formatDate value="${app.endTime}" pattern="yyyy-MM-dd HH:mm" />"readonly="readonly" 
															 />	
										                </td>
										             </c:if>  
									            </tr>
									          </table>
									 </div>
								</td>
							</tr>
							<tr height="3"><td colspan="2">&nbsp;</td></tr>
							<tr>
								<td colspan="2">
								  <table width="100%">
								   <tr>
								   <td width="20%"  align="right"><comm:message key='admin.meetingroom' />:<span class="required">*</span></td>
								   <td width="35%">
							     	<select name="meetingRoom" id="meetingRoom" style="width:130px;margin-left:0px;">
										<c:forEach var="meetingRoom" items="${basecode['meetingroom']}">
											<option value="${meetingRoom.id}" <c:if test="${meetingRoom.id == meeting.meetingRoomId.id}">selected</c:if>>${meetingRoom.name}</option>
										</c:forEach>
									</select>
								</td>
								<td width="20%"  align="right"><comm:message key='icu.mobile.devices' />:<span class="required">*</span></td>
								<td>

									<input type="text" name="devicesNo" id = "devicesNo" value = "${mm.devicesNo}" onkeyup="value=value.replace(/[^\d\.]/g,'')"/>
								</td>
								</tr>
								</table>
								</td>
							</tr>
							<tr height="3"><td colspan="2">&nbsp;</td></tr>
							<tr>
								<td width="20%" align="right"><comm:message key='meeting.m.attend.departexperts' />:<span class="required">*</span></td>
								<td>
									<table style="border:1px solid #000;">
										<tr>
											<td bgcolor="#D0D0D0" style="height:25px;border-right:1px solid #000;">
												 &nbsp;<strong>速查条件</strong><input id="queryInnaiUserName" style="align:right;width:130px;line-height:18px;height:18px;" value="" size="23" />
											</td>
											<td>
											</td>
											<td bgcolor="#D0D0D0"  style="height:25px;border-left:1px solid #000;">
												&nbsp;<strong>已选择<span id="intSelectedInnaiUser">0</span>项</strong>
											</td>
										</tr>
										<tr>
											<td style="border-right:1px solid #000;width:200px;">
												<select multiple="multiple" name="innaiUserSelect" id="innaiUserSelect"  style="align:center;width:200px;height:143px;border:none;">
													
												</select>	
											</td>
											
											<td align="center">
												<div id="rightdown" onclick="rightmoveInnaiUser()"></div>
												<div id="leftup" onclick="leftmoveInnaiUser()"></div>
											</td>
											
											<td style="border-left:1px solid #000;">
												<select align="center" multiple="multiple" name="innaiUserSelected" id="innaiUserSelected" style="width:200px;height:143px;border:none;">
													<c:forEach var="experts" items="${expertsList}">
														<option value="${experts.id}">${experts.name}</option>
													</c:forEach> 
												</select>
											</td>
										</tr>
									</table>
								</td>
							</tr>
							<tr height="3"><td colspan="2">&nbsp;</td></tr>
							<tr>
								<td width="20%"  align="right"><comm:message key='meeing.meeting.m.community' />:<span class="required">*</span>
								</td>
								<td>
									<table style="border:1px solid #000;">
										<tr>
											<td bgcolor="#D0D0D0" style="height:25px;border-right:1px solid #000;">
												 &nbsp;<strong>速查条件</strong><input id="queryCommonUserName" style="align:right;width:130px;line-height:18px;height:18px;" value="" size="23" />
											</td>
											<td>
											</td>
											<td bgcolor="#D0D0D0"  style="height:25px;border-left:1px solid #000;">
												&nbsp;<strong>已选择<span id="intSelectedCommonUser">0</span>项</strong>
											</td>
										</tr>
										<tr>
											<td style="border-right:1px solid #000;width:200px;">
												<select multiple="multiple" name="commonUserSelect" id="commonUserSelect"  style="align:center;width:200px;height:143px;border:none;">
													
												</select>	
											</td>
											
											<td align="center">
												<div id="rightdown" onclick="rightmoveCommonUser()"></div>
												<div id="leftup" onclick="leftmoveCommonUser()"></div>
											</td>
											
											<td style="border-left:1px solid #000;">
												<select align="center" multiple="multiple" name="commonUserSelected" id="commonUserSelected" style="width:200px;height:143px;border:none;">
													<c:forEach var="community" items="${communityList}">
														<option value="${community.id}">${community.name}</option>
													</c:forEach>  
												</select>
											</td>
										</tr>
									</table>
								<br/>
								</td>
							</tr>
							<tr height="3"><td colspan="2">&nbsp;</td></tr>
							<tr>
								<td width="20%"  align="right"><comm:message key='meeting.type.outter.infor' />:<span class="required">*</span>
								</td>
								<td>
									<table style="border:1px solid #000;">
										<tr>
											<td bgcolor="#D0D0D0" style="height:25px;border-right:1px solid #000;">
												 &nbsp;<strong>速查条件</strong><input id="queryInterUserName" style="align:right;width:130px;line-height:18px;height:18px;" value="" size="23" />
											</td>
											<td>
											</td>
											<td bgcolor="#D0D0D0"  style="height:25px;border-left:1px solid #000;">
												&nbsp;<strong>已选择<span id="intSelectedInterUser">0</span>项</strong>
											</td>
										</tr>
										<tr>
											<td style="border-right:1px solid #000;width:200px;">
												<select multiple="multiple" name="interUserSelect" id="interUserSelect"  style="align:center;width:200px;height:143px;border:none;">
													
												</select>	
											</td>
											
											<td align="center">
												<div id="rightdown" onclick="rightmoveInterUser()"></div>
												<div id="leftup" onclick="leftmoveInterUser()"></div>
											</td>
											
											<td style="border-left:1px solid #000;">
												<select align="center" multiple="multiple" name="interUserSelected" id="interUserSelected" style="width:200px;height:143px;border:none;">
													<c:forEach var="inters" items="${intersList}">
														<option value="${inters.id}">${inters.name}</option>
													</c:forEach>
												</select>
											</td>
										</tr>
									</table>
								<br/>
								</td>
							</tr>			
						</table>
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
				<tr style="display:none">
					<td width="20%" height="30" align="right"><comm:message key='unified.meetingtype' />:<span	class="required">*</span>
					</td>
					<td align="right">
					    <table width="100%" class="patientInfors" border="0">
					            <tr>
					                <td width="30%">
										<select name="meetingType.id" id="meetingtypeid" style="width:180px;">
												<c:forEach var="meetingtype" items="${basecode['meetingtype']}">
													<option value="${meetingtype.id}">${meetingtype.name}</option>
												</c:forEach>
										</select>					                
					                </td>
					                <td width="16%" align="right"><comm:message key='unified.meetinglevel' />:<span class="required">*</span></td>
					                <td align="left">
										<select name="level.id" id="meetingleveld"  style="width:180px;">
												<c:forEach var="meetinglevel" items="${basecode['meetinglevel']}">
													<option value="${meetinglevel.id}">${meetinglevel.name}</option>
												</c:forEach>
										</select>					                
					                </td>
					            </tr>
					    </table>
					</td>
				</tr>
				<tr height="3"><td colspan="2">&nbsp;</td></tr>
				<tr>
				    <td colspan="2">
						<table background-color="#ddeefa">
						<tr>
							<td width="20%"  align="right">
						            <c:if test="${app.requester.userType.value == 4}">
							            <comm:message key='unified.and.expert.meeting.requester' />:&nbsp;
						            </c:if>
						            <c:if test="${app.requester.userType.value == 5}">
							            <comm:message key='unified.and.expert_requester'/>:&nbsp;
						            </c:if>
								</td>
								<td align="center" height="80">
							     <table width="470" align="center" style="margin-left:3px;border-bottom:0px;border-right:0px;" class="_table" border="1">
								            <tr>
								                <c:if test="${fn:substring(user.deptId.parentDeptCode, 0, 6) == '001001'||user.deptId.deptcode == '001001'}">
									                <td align="right" style="height:25px;"><comm:message key='group.name'/>:&nbsp;&nbsp;</td>
									                <td align="left"><comm:message key='meeting.type.inner'/></td>
									                <td align="right"><comm:message key='unified.and.expert_username'/>:&nbsp;&nbsp;</td>
									                <c:if test="${fn:length(user.name) <= 10}">
									                  <td align="left">${app.requester.name}&nbsp;</td>
									                </c:if>
									                <c:if test="${fn:length(user.name) > 10}">
									                  <td align="left" title="${app.requester.name}">${fn:substring(user.name, 0, 10)}...&nbsp;</td>
									                </c:if>
									            </c:if>
									            <c:if test="${fn:substring(user.deptId.parentDeptCode, 0, 6) == '001002'||user.deptId.deptcode == '001002'}"> 
									            	<td align="right" style="height:25px;"><comm:message key='group.name'/>:&nbsp;&nbsp;</td>
									                <td align="left"><comm:message key='meeting.type.outter'/></td>
									                <td align="right"><comm:message key='unified.and.expert_username'/>:&nbsp;&nbsp;</td>
									                <c:if test="${fn:length(user.name) <= 10}">
									                  <td align="left">${app.requester.name}&nbsp;</td>
									                </c:if>
									                <c:if test="${fn:length(user.name) > 10}">
									                  <td align="left" title="${app.requester.name}">${fn:substring(user.name, 0, 10)}...&nbsp;</td>
									                </c:if>
									            </c:if>
									            <c:if test="${fn:substring(user.deptId.parentDeptCode, 0, 6) == '001003'||user.deptId.deptcode == '001003'}">
									            	<td align="right" style="height:25px;"><comm:message key='group.name'/>:&nbsp;&nbsp;</td>
									                <c:if test="${fn:length(user.name) <= 12}">
									                  <td align="left">${app.requester.name}&nbsp;</td>
									                </c:if>
									                <c:if test="${fn:length(user.name) > 12}">
									                  <td align="left" title="${app.requester.name}">${fn:substring(user.name, 0, 10)}...&nbsp;</td>
									                </c:if>									                
									                <td align="right"><comm:message key='unified.and.expert_username'/>:&nbsp;&nbsp;</td>
											        <c:if test="${fn:length(user.username) <= 10}">
									                  <td align="left">${app.requester.username}&nbsp;</td>
									                </c:if>
									                <c:if test="${fn:length(user.username) > 10}">
									                  <td align="left" title="${app.requester.username}">${fn:substring(user.username, 0, 10)}...&nbsp;</td>
									                </c:if>									                
									            </c:if>
								            </tr>
								            <tr>
								                <td align="right" style="height:25px;"> <comm:message key='unified.and.expert_mobile'/>:&nbsp;&nbsp;</td>
								                <td align="left">${app.requester.mobile}</td>
								                <td align="right"><comm:message key='admin.user_tel'/>:&nbsp;&nbsp;</td>
								                <td align="left">${app.requester.telephone}</td>
								            </tr>
								            <tr>
								                <td width="78" align="right" style="height:25px;"><comm:message key='group.department'/>:&nbsp;&nbsp;</td>
								                <td width="150" align="left">${app.requester.deptId.name}</td>
								                <td width="78" align="right"><comm:message key='unified.and.expert_email'/>:&nbsp;&nbsp;</td>
										        <c:if test="${fn:length(user.mail) <= 21}">
								                  <td>${app.requester.mail}&nbsp;<input type="hidden" name="applicantid" id="applicantid"/></td>
								                </c:if>
								                <c:if test="${fn:length(user.mail) > 21}">
								                  <td title="${app.requester.mail}">${fn:substring(user.mail, 0, 21)}...&nbsp;<input type="hidden" name="applicantid" id="applicantid"/></td>
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
							    <td align="center" height="80">
							     <table width="470" align="center" style="margin-left:3px;border-bottom:0px;border-right:0px;" class="_table" border="1">
								                <tr>
								                    
								                    <c:if test="${fn:substring(user.deptId.parentDeptCode, 0, 6) == '001001'||user.deptId.deptcode == '001001'}">
										                <td align="right"><comm:message key='group.name'/>:&nbsp;&nbsp;</td>
									                    <td align="left"><input style="width:153px;" type="text" name="authorInfo.groupname" id="author_groupname" size="15" value="<comm:message key='meeting.type.inner'/>" onfocus="this.blur()" /></td>
									                    <td align="right"><comm:message key='unified.and.expert_username'/>:&nbsp;&nbsp;</td>
									                    <td align="left"><input style="width:153px;" type="text" name="authorInfo.name" id="author_groupname" size="15" value="${app.authorInfo.name}"  /></td>
										            </c:if>
										            <c:if test="${fn:substring(user.deptId.parentDeptCode, 0, 6) == '001002'||user.deptId.deptcode == '001002'}"> 
										            	<td align="right"><comm:message key='group.name'/>:&nbsp;&nbsp;</td>
									                    <td align="left"><input style="width:153px;" type="text" name="authorInfo.groupname" id="author_groupname" size="15" value="<comm:message key='meeting.type.outter'/>" onfocus="this.blur()"/></td>
									                    <td align="right"><comm:message key='unified.and.expert_username'/>:&nbsp;&nbsp;</td>
									                    <td align="left"><input style="width:153px;" type="text" name="authorInfo.name" id="author_groupname" size="15" value="${app.authorInfo.name}"  /></td>
										            </c:if>
										            <c:if test="${fn:substring(user.deptId.parentDeptCode, 0, 6) == '001003'||user.deptId.deptcode == '001003'}">
										            	<td align="right"><comm:message key='group.name'/>:&nbsp;&nbsp;</td>
									                    <td align="left"><input style="width:153px;" type="text" name="authorInfo.groupname" id="author_groupname" size="15" value="${app.requester.name}" onfocus="this.blur()" /></td>
									                    <td align="right"><comm:message key='unified.and.expert_username'/>:&nbsp;&nbsp;</td>
									                    <td align="left"><input style="width:153px;" type="text" name="authorInfo.name" id="author_groupname" size="15" value="${app.authorInfo.name}"  /></td>
										            </c:if>
								                    
								                </tr>
								                <tr>
								                    <td align="right"><comm:message key='unified.and.expert_mobile'/>:&nbsp;&nbsp;</td>
								                    <td align="left"><input style="width:153px;" type="text" name="authorInfo.mobile" id="author_mobile" size="15"  maxlength="15" value="${app.authorInfo.mobile }"/></td>
								                    <td align="right"><comm:message key='admin.user_tel'/>:&nbsp;&nbsp;</td>
								                    <td align="left"><input style="width:153px;" type="text" name="authorInfo.telephone" id="author_telephone" size="15"  maxlength="25" value="${app.authorInfo.telephone}"/></td>
								                </tr>
								                <tr>
								                    <td width="78" align="right"><comm:message key='group.department'/>:&nbsp;&nbsp;</td>
								                    <td width="150" align="left"><input style="width:153px;" type="text" name="authorInfo.department" id="author_department" size="15" maxlength="50" value="${app.authorInfo.department }"/></td>
								                    <td width="78" align="right"><comm:message key='unified.and.expert_email'/>:&nbsp;&nbsp;</td>
								                    <td width="150" align="left"><input style="width:153px;" type="text" name="authorInfo.email" id="author_email" size="15" maxlength="50" value="${app.authorInfo.email}"/></td>
								                </tr>
								         </table>
								 </td>
					        </tr>				
					        <tr height="60">
							    <td colspan="2" align="center">
									 <input class="button" type="submit" name="button" id="button" value="<comm:message key='comm.save'/>" />&nbsp;&nbsp;&nbsp;
									 <input	class="button" type="button" value="<comm:message key='comm.cancel'/>" onclick="cancelFun();"/>
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