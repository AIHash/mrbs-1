<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page pageEncoding="utf-8"%>
<%@include file="/common.jsp"%>
<%@ page language="java" import="com.wafersystems.mrbs.*,com.wafersystems.mrbs.vo.user.UserInfo" %>
<%
 String returnmessage=(String)request.getSession().getAttribute("returnmessage");
 request.getSession().removeAttribute("returnmessage"); 
 UserInfo user = (UserInfo)request.getSession().getAttribute(GlobalConstent.USER_LOGIN_SESSION);

%>

<html>
  <head>
    <title>mediaupload</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<link href="<%=request.getContextPath()%>/resources/style/main.css" rel="StyleSheet" type="text/css"/>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery.js"></script>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/jqueryalerts/jquery.alerts.css"  media="screen" />	
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/jqueryalerts/jquery.alerts.js"></script>
  <script type="text/javascript">
	
	$(document).ready(function(){
		
	});
	
	function myAlert(){
		var  traget = document.getElementById("BgDiv");  
		var message='<%=returnmessage%>';
		if(message!=""&&message!="null" && message !="error") {
			traget.style.display="none";  
			alert(message, "<comm:message key='meeting.m.infotishi'/>");
			window.parent.location.href="<%=request.getContextPath()%>/media/mediaQuery";
		}else if(message!=""&&message!="null" ){
			alert(message, "<comm:message key='meeting.m.infotishi'/>");
		}
		var userType='<%=user.getRole().getId()%>';
 		var obj =  document.getElementsByName("roleid");
 		for(var i=0; i<obj.length; i++){ 
 			if(obj[i].value == userType ) {
 				obj[i].checked = true;
 			
 			
 			}
 		} 


	}
	function closeFrame(){
		//var bgObj = window.parent.document.getElementById("bgDiv");
		var msgTitle = window.parent.document.getElementById("msgTitle");
		var msgObj = window.parent.document.getElementById("msgDiv");
		//window.parent.document.body.removeChild(bgObj);
		window.parent.document.getElementById("msgDiv").removeChild(msgTitle);
		window.parent.document.body.removeChild(msgObj);
	}

	function showUpType(upTyp){
		var mediaTypeId = $("#mediaTypeId").val();
		if(upTyp=='1'){
			if(mediaTypeId == 1){
				 
			}else if(mediaTypeId == 3){
				 
			}else if(mediaTypeId == 4){
			 
			}else if(mediaTypeId == 5){
				 
			}

          	$("#upfile").remove(); //移除原来的
         	 var upfile=$("<input/>").attr("name","upfile").attr("id","upfile");
         	 upfile.attr("type","file");
          	upfile.appendTo("#fileposi"); 
			$("#urlpath").val(null);
			$("#upfileTr").css("display", "");
			$("#urlpathTr").css("display", "none");
		//	$("#localplaypathTr").css("display", "none");
			//$("#lengthsTr").css("display", "none");
		}else if(upTyp=='2'){
          	$("#upfile").remove(); //移除原来的
        	 var upfile=$("<input/>").attr("name","upfile").attr("id","upfile");
        	 upfile.attr("type","file");
         	upfile.appendTo("#fileposi"); 	
         	$("#urlpath").val(null);
			$("#upfileTr").css("display", "none");
			$("#urlpathTr").css("display", "");
			//$("#localplaypathTr").css("display", "none");
			 
		}
		setfileInfo();
	}

	function check(){
		//验证名称
        var mediaName = $("#medianame").val();
		 if(mediaName=="") {
			 alert( "<comm:message key='user.department.user.name.not.null'/>","<comm:message key='meeting.m.infotishi'/>");
			 return false;
		 }
		//验证类型
		var mediaTypeId = $("#mediaTypeId").val();
		if(mediaTypeId == ""){
			alert( "<comm:message key='media.medialib.select.mediatype'/>","<comm:message key='meeting.m.infotishi'/>");
 			return false;			
 		}
 		//验证来源
		var upType = $("#upType").val();
 		if(upType == ""){
			alert("<comm:message key='media.medialib.select.mediauptype'/>","<comm:message key='meeting.m.infotishi'/>");
 			return false;
 			
 		}
 		//验证文件操作权限
 		var s = false;
 		var obj =  document.getElementsByName("roleid");
 		for(var i=0; i<obj.length; i++){ 
 			if(obj[i].checked) {
				s = true;
 				break;
 			}
 		} 
		if(s == false){
			alert("<comm:message key='media.medialib.select.power'/>","<comm:message key='meeting.m.infotishi'/>");
 			return false;
		}
      
 		if(upType=='1'){//本地
		  var upfile = $("#upfile").val();
		  if(upfile == ""){
			alert("<comm:message key='media.medialib.select.upfile'/>","<comm:message key='meeting.m.infotishi'/>");
			return false; 			
		  }else{
	 			var acceptXNamePosition = upfile.lastIndexOf(".");
	 			var acceptXName = upfile.substring(acceptXNamePosition+1, upfile.length);//后缀	
	 			var acceptXNameWeb = upfile.indexOf(".") ;
	 			var upFileflag = false; 
	 			
				if(mediaTypeId == 3){	 				
 	 				var accept_ext = new Array("txt"); 
 		 			if(acceptXName != ''){ 
 		 			   for(var i=0; i<accept_ext.length; i++){ 
 		 				   if(acceptXName.toLowerCase() == accept_ext[i]){		 						
 		 					 upFileflag = true; 
 		 						break;
 		 				    } 
 		 				} 			
 		 			}	 
				}
 		 		else if (mediaTypeId == 1) {//网页类型是*.*
 		 			var accept_ext = new Array("html","htm","shtml","stm","shtm","asp","php","jsp","cgi");
					 	if (acceptXName != '') {					 		
					 		 for(var i=0; i<accept_ext.length; i++){ 
		 		 					
		 		 				   if(acceptXName.toLowerCase() == accept_ext[i]){
		 		 						
		 		 					 upFileflag = true; 
		 		 					 break;
		 		 				    } 
		 		 				} 
						}
		        }else if(mediaTypeId == 4){//图片
 	 				var accept_ext = new Array("jpg","bmp","png","emf","gif","xps"); 
 		 			if(acceptXName != ''){ 
 		 			   for(var i=0; i<accept_ext.length; i++){ 
 		 					
 		 				   if(acceptXName.toLowerCase() == accept_ext[i]){
 		 						
 		 					 upFileflag = true; 
 		 					 break;
 		 				    } 
 		 				} 			
 		 			}		 			
		        }else if(mediaTypeId == 2){//视屏
	 	 				var accept_ext = new Array("avi","mpg","mpeg","wmv","3gp","mov","mp4","asf","asx","flv","mkv","vob","wmv9","rm","rmvb"); 
	 		 			if(acceptXName != ''){ 
	 		 			   for(var i=0; i<accept_ext.length; i++){ 
	 		 					
	 		 				   if(acceptXName.toLowerCase() == accept_ext[i]){
	 		 						
	 		 					 upFileflag = true; 
	 		 						break;
	 		 				    } 
	 		 				} 			
	 		 			}			        	
			    }
				debugger;
		 		if(!upFileflag){
		 				alert( "<comm:message key='media.medialib.select.file.righttype'/>","<comm:message key='meeting.m.infotishi'/>");

		 	 			return false;
		 		}else{
		 			showdiv();
		 		}
		  }
		  
	    } else{
	    	
	    	var  urlpath = $("#urlpath").val();
	    	if(urlpath == "" || urlpath == null){
	    		alert( "<comm:message key='media.please.input.internet.address'/>","<comm:message key='meeting.m.infotishi'/>");
 	 			return false;
	    	}
	    }		
 		if(mediaTypeId!=2||(mediaTypeId==2&&upType!='1')){//视屏需要验证时间

 	 		var hours=$("#hours").val()==''?0:$("#hours").val();
 	 		var minutes=$("#minutes").val()==''?0:$("#minutes").val();
 	 		var seconds=$("#seconds").val()==''?0:$("#seconds").val(); 	

			var lengths = parseInt(hours*3600)+parseInt(minutes*60)+parseInt(seconds);
			if(lengths<=0){
 				alert("<comm:message key='media.medialib.length.notnull'/>","<comm:message key='meeting.m.infotishi'/>");

				return false; 		
			}else{
				$("#length").val(lengths);
			}
		}else{
			$("#length").val(0);
		}	   
		return true;
	}
	
	function changeDisplayAlert(id1, id2, Str){
		
		$("#"+id1).css("display", "block");
		$("#"+id2).text(Str);
	}	


	
	function  showdiv(){ 
		$("#BgDiv").css("display", "block");
	}
	
	function  setfileInfo(){
		var mediaTypeId = $("#mediaTypeId").val();
		var upType = $("#upType").val();
		if(upType == "1"){
			if(mediaTypeId == "1"){
				$("#fileinfo1").css("display", "");
				$("#fileinfo2").css("display", "none");
				$("#fileinfo3").css("display", "none");
				$("#fileinfo4").css("display", "none");
			}else if(mediaTypeId == "2"){
				$("#fileinfo1").css("display", "none");
				$("#fileinfo2").css("display", "");
				$("#fileinfo3").css("display", "none");
				$("#fileinfo4").css("display", "none");
			}else if(mediaTypeId == "3"){
				$("#fileinfo1").css("display", "none");
				$("#fileinfo2").css("display", "none");
				$("#fileinfo3").css("display", "");
				$("#fileinfo4").css("display", "none");
			}else if(mediaTypeId == "4"){
				$("#fileinfo1").css("display", "none");
				$("#fileinfo2").css("display", "none");
				$("#fileinfo3").css("display", "none");
				$("#fileinfo4").css("display", "");
			}
		}else{
			$("#fileinfo1").css("display", "none");
			$("#fileinfo2").css("display", "none");
			$("#fileinfo3").css("display", "none");
			$("#fileinfo4").css("display", "none");
		}
		
	}
	
  </script>
  <style type="text/css">
	#fixedLayer {
	position:fixed;
	width:200px;
	height:100px;
	margin-top:60px;
	margin-left:200px;
	border:1px solid #84C1FF;
	z-index:99; 
	background: #ECF5FF;
	}
	#BgDiv{
	     display:none;
		 position:absolute; 
		 left:0; 
		 top:0; 
		 width:100%; 
		 height:1000px;
		 background: #D0D0D0;
		 filter: alpha(opacity=60); opacity: 0.6;
		 }
</style>
  </head>
  <body onload="myAlert();">
     <div id="BgDiv">
        <div id="fixedLayer" align="center"  ><table  height="100px"><tr><td  align="center" valign="middle">正在上传...</td></tr></table></div>
     </div>
    <form id="mediaLibForm" action="<%=request.getContextPath()%>/media/saveMediaLib" method="post"  enctype="multipart/form-data" onsubmit="return check();">
	 <div id="addAndUpdateMedia" >
       <div > 
       <table width="100%" border="0" cellspacing="0" cellpadding="0">
       		  
       		  <tr ><td colspan="4">&nbsp;</td></tr>
              <tr>
               	<td width="10%" align="right" ><comm:message key='media.medialib.name' />&nbsp;<font style="color: #b94a48">*</font>:</td>
				<td width="40%">&nbsp;<input type="text" id="medianame" name="medianame" style=" width:198px;" value=""  /></td>
				<td width="10%" align="right" ><comm:message key='media.medialib.type' />&nbsp;<font style="color: #b94a48">*</font>:</td>
				<td width="40%">&nbsp;<select id="mediaTypeId" name="mediaTypeId" onchange="setfileInfo();"  style=" width:198px;">
                                                            <option value="1" selected><comm:message key='common.mediaType.html' /></option>
                                                            <option value="2"><comm:message key='common.mediaType.media' /></option>
                                                            <option value="3" ><comm:message key='common.mediaType.text' /></option>
                                                            <option value="4" ><comm:message key='common.mediaType.img' /></option>
                    </select></td>
              </tr> 
			  <tr ><td colspan="4">&nbsp;</td></tr>
              <tr id="upTypeTr">
              	<td width="10%" align="right" ><comm:message key='media.medialib.uptype' />&nbsp;<font style="color: #b94a48">*</font>:</td>
				<td width="40%">&nbsp;<select id="upType" name="upType" onchange="showUpType(this.value);"   style=" width:198px;">
                                                            <option value="1"><comm:message key='media.medialib.local' /></option>
                                                            <option value="2"><comm:message key='media.medialib.url' /></option>
                                                       </select></td>
				<td width="10%" align="right" ><comm:message key='media.power.title' />&nbsp;<font style="color: #b94a48">*</font>:</td>
				<td width="40%">
					<label><input  id="roleid" name="roleid" type="checkbox" value="3" /><comm:message key='media.power.profie' /> </label>
					<label><input  id="roleid" name="roleid" type="checkbox" value="2" /><comm:message key='meeting.type.unified' /> </label>
					<label><input  id="roleid" name="roleid" type="checkbox" value="4" /><comm:message key='media.power.manage' /> </label>
				</td>
              </tr>
               <tr ><td colspan="4">&nbsp;</td></tr>
              <tr id="upfileTr" style="height: 40px;">
              	<td width="10%" align="right" ><comm:message key='media.medialib.upfile.path' />&nbsp;<font style="color: #b94a48">*</font>:</td>
				<td width="40%">&nbsp;<span id = "fileposi" name="fileposi"><input type="file"  style=" width:198px;" id="upfile" name="upfile" value=""  /></span></td>
				<td  align="left" colspan="2"></td>
              </tr> 
              <tr>
                 <td></td>
                <td colspan="3" align="left">
                  <div id="fileinfo1" ><font color="red"> 上传提示：上传文件格式支持（html,htm,shtml,stm,shtm,asp,php,jsp,cgi）</font></div>
                  <div id="fileinfo2" style="display: none;"><font color="red"> 上传提示：上传文件格式支持（avi,mpg,mpeg,wmv,3gp,mov,mp4,asf,asx,flv,mkv,vob,wmv9,rm,rmvb）</font></div>
                  <div id="fileinfo3" style="display: none;"><font color="red"> 上传提示：上传文件格式支持（txt且编码为utf-8）</font></div>
                  <div id="fileinfo4" style="display: none;"><font color="red"> 上传提示：上传文件格式支持（jpg,bmp,png,emf,gif,xps）</font></div>
                </td>
              </tr>
              <tr id="urlpathTr" style="display: none;">
              	<td width="10%" align="right" ><comm:message key='media.medialib.url' />&nbsp;<font style="color: #b94a48">*</font>:</td>
				<td width="40%">&nbsp;<input type="text" id="urlpath" name="urlpath" value=""  /></td>
				<td width="10%" align="right" ></td>
				<td width="40%"></td>
              </tr> 
			 <tr ><td colspan="4">&nbsp;</td></tr>
              <tr id="lengthsTr" style="height: 40px;display: none;">
	              	<td width="7%" align="right" ><comm:message key='media.medialib.play.length' />&nbsp;<font style="color: #b94a48">*</font>:</td>
					<td width="43%">&nbsp;<input type="text" id="hours" name="hours" value="" maxlength="2" style="width:45px;"onkeyup="value=value.replace(/[^\d\.]/g,'')"  />&nbsp;<comm:message key='media.medialib.hour' />
									&nbsp;<input type="text" id="minutes" name="minutes" value="" maxlength="2" style="width:45px;" onkeyup="value=value.replace(/[^\d\.]/g,'')" />&nbsp;<comm:message key='media.medialib.minite' />
									&nbsp;<input type="text" id="seconds" name="seconds" value="1" maxlength="2" style="width:45px;"  onkeyup="value=value.replace(/[^\d\.]/g,'')"/><comm:message key='media.medialib.second' /><input type="hidden" id="length" name="length"/></td>
					<td width="10%"></td>
					<td width="40%"></td>
              </tr>
              <tr ><td colspan="4">&nbsp;</td></tr>
              </table>
              <table width="100%" border="0" cellspacing="0" cellpadding="0">
              	<tr>
	              	<td width="10%" align="right" ><comm:message key='media.medialib.desc' />&nbsp;:</td>
					<td width="90%">&nbsp;<textarea id="description" name="description" style="width:530px;" ></textarea></td>
              	</tr>
              	<tr ><td colspan="4">&nbsp;</td></tr>
              	<tr>
              		 <td width="10%"></td>
              		<td width="90%"  align="center">
              		         <button type="submit" id="submitBtn" name="submitBtn" >
			        			<comm:message key='media.medialib.save' />
		     				</button>
		     				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		     				<button type="Button" id="cancle" name="cancle" onclick="closeFrame();">
			        			<comm:message key='media.medialib.cancel' />
		     				</button>
              		</td>

              		

              	</tr>
              	
              </table>

   </div>
 </div>
</form>
  </body>
</html>
