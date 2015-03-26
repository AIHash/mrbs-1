<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@include file="/common.jsp"%>
<%@ page pageEncoding="utf-8" isELIgnored="false"%>
<%
 String returnmessage=(String)request.getSession().getAttribute("returnmessage");
 request.getSession().removeAttribute("returnmessage"); 
 String path = request.getContextPath();
 String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path;
%>

<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title><comm:message key="common.teleconsultation.arrangedConsultation" /></title>
	<comm:pageHeader hasEcList="false"/>
	<link href="${pageContext.request.contextPath}/resources/css/style.css"	rel="stylesheet" type="text/css" />
	<link href="${pageContext.request.contextPath}/resources/jqueryalerts/jquery.alerts.css" rel="stylesheet" type="text/css" media="screen" />
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/My97DatePicker/WdatePicker.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/jqueryalerts/jquery.ui.draggable.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/jqueryalerts/jquery.alerts.js"></script>
	 
	<style type="text/css">
		.table_98per tr td input{margin-left:3px;}
		.table_98per tr td select{margin-left:3px;}
	</style>
	<script type="text/javascript">
	function  delMedia(mediaId){
		jConfirm("<comm:message key='js.confirm_delete' args='common.mediaFile'/>\n\n<comm:message key='admin.click_ok'/>", "<comm:message key='meeting.m.infotishi'/>",function(resultConfirm){
			if(resultConfirm){
		    	$.post('${pageContext.request.contextPath}/media/delMedia/' + mediaId, function(text) {
					if(text == 'succ'){
						window.location.href = "<%=request.getContextPath()%>/media/mediaQuery";
					} else if(text=='error'){
						jAlert("删除失败！", "<comm:message key='meeting.m.infotishi'/>");
						return false;
					}else if(text.toLowerCase().indexOf('<html>') != -1){
						parent.window.location.href = "<%=request.getContextPath()%>/index.jsp?message=system.session_expire";
					}
				}, 'text');
		    }
		});
	}
		function check(){
			 var startTime=document.getElementById("meetingStartTime").value;
			 var endTime=document.getElementById("meetingEndTime").value;
			 if (startTime.length> 1&&endTime.length > 1&&!compareDateString(startTime, endTime)){
				jAlert("<comm:message key='meeting.m.timeRage.notRight'/>", "<comm:message key='meeting.m.infotishi'/>");				
				return false;
			  }	
			return true;
		}
		function sAlert(strTitle){ 
		    var msgw,msgh,bordercolor; 
		    msgw=640;//提示窗口的宽度 
		    msgh=340;//提示窗口的高度 
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
		    bgObj.style.background="#fff"; 
		    bgObj.style.filter="progid:DXImageTransform.Microsoft.Alpha(opacity=100,style=3,finishOpacity=75"; 
		    bgObj.style.opacity="0.6"; 
		    bgObj.style.left="0"; 
		    bgObj.style.width=sWidth + "px"; 
		    bgObj.style.height=sHeight + "px"; 
		    bgObj.style.zIndex = "10000"; 
		    //document.body.appendChild(bgObj);  

		    var msgObj=document.createElement("div");
		    msgObj.setAttribute("id","msgDiv"); 
		    msgObj.setAttribute("align","center"); 
		    msgObj.style.background="white"; 
		    msgObj.style.border="1px solid " + bordercolor; 
		    msgObj.style.position = "absolute"; 
		    msgObj.style.left = "37%"; 
		    msgObj.style.top = "20%"; 
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
		    title.style.border="0px solid " + bordercolor; 
		    title.style.height="28px"; 
		    title.style.width="100%";
		    title.style.font="12px Verdana, Geneva, Arial, Helvetica, sans-serif"; 
		    title.style.color="white"; 
		    title.style.cursor="pointer"; 
		    title.title ="<comm:message key='unified.close'/>"; 
		    title.close="<comm:message key='unified.window.close'/>"; 
		    title.innerHTML="<table border='0′ align='center' width='100%' ><tr><td align='left' width='81%'>"+strTitle+"</td><td width='9%' >"+title.close+"</td></tr></table>";
		    title.onclick = function(){
			    //document.body.removeChild(bgObj);
			    document.getElementById("msgDiv").removeChild(title);
			    document.body.removeChild(msgObj);
		    };
		    document.body.appendChild(msgObj); 
		    document.getElementById("msgDiv").appendChild(title); 
		    var txt=document.createElement("p"); 
		    txt.style.margin="0em 0";
		    //txt.style.border="1px solid red";
		    txt.setAttribute("id","msgTxt"); 
		    txt.innerHTML="<iframe style='width:100%;height:310px;border:1px'  src='<%=request.getContextPath()%>/media/mediauploadView' scrolling='no'></iframe>"; 
		    document.getElementById("msgDiv").appendChild(txt);
		}
		
		
		
		function chechpreviewmedia(mediaType,filepath,pathType){
			var strTitle="资源预览";
			var msgw,msgh,bordercolor; 
		    msgw=640;//提示窗口的宽度 
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
		    bgObj.style.top="0"; 
		    bgObj.style.background="#fff"; 
		    bgObj.style.filter="progid:DXImageTransform.Microsoft.Alpha(style=3,opacity=25,finishOpacity=75"; 
		    bgObj.style.opacity="0.6"; 
		    bgObj.style.left="0"; 
		    bgObj.style.width=sWidth + "px"; 
		    bgObj.style.height=sHeight + "px"; 
		    bgObj.style.zIndex = "10000"; 
		    //document.body.appendChild(bgObj);

		    var msgObj=document.createElement("div");
		    msgObj.setAttribute("id","msgDiv"); 
		    msgObj.setAttribute("align","center"); 
		    msgObj.style.background="white"; 
		    msgObj.style.border="1px solid " + bordercolor; 
		    msgObj.style.position = "absolute"; 
		    msgObj.style.left = "37%"; 
		    msgObj.style.top = "20%"; 
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
		    title.style.border="0px solid " + bordercolor; 
		    title.style.height="28px"; 
		    title.style.width="100%";
		    title.style.font="12px Verdana, Geneva, Arial, Helvetica, sans-serif"; 
		    title.style.color="white"; 
		    title.style.cursor="pointer"; 
		    title.title ="<comm:message key='unified.close'/>"; 
		    title.close="<comm:message key='unified.window.close'/>"; 
		    title.innerHTML="<table border='0′ align='center' width='100%' ><tr><td align='left' width='81%'>"+strTitle+"</td><td width='9%' >"+title.close+"</td></tr></table>";
		    title.onclick = function(){
			    //document.body.removeChild(bgObj);
			    document.getElementById("msgDiv").removeChild(title);
			    document.body.removeChild(msgObj);
		    };
		    document.body.appendChild(msgObj); 
		    document.getElementById("msgDiv").appendChild(title); 
		    var txt=document.createElement("p"); 
		    txt.style.margin="0em 0";
		    //txt.style.border="1px solid red";
		    txt.setAttribute("id","msgTxt");
		    var preview = "<%=basePath%>/" + filepath;
		    if(pathType == 1){
		    	if(mediaType == 1 ){
			    	if(pathType == 1){
			    		txt.innerHTML="<iframe style='width:100%;height:370px;border:1px'  src='"+preview+"' scrolling='no'></iframe>"; 
					    document.getElementById("msgDiv").appendChild(txt);
			    	}
			    }else if(mediaType == 3){
			    	$.post('${pageContext.request.contextPath}/media/readtxt?filepath=' + filepath , function(text) {
						 if(text=='error'){
							jAlert("<comm:message key='common.media.preview.error'/>", "<comm:message key='meeting.m.infotishi'/>");
							//document.body.removeChild(bgObj);
						    document.getElementById("msgDiv").removeChild(title);
						    document.body.removeChild(msgObj);
							return false;
						}else{ 
							txt.innerHTML="<textarea cols='95' rows='25' style='border-style: solid; border-color: #336699;'>"+text+"</textarea>"; 
						    document.getElementById("msgDiv").appendChild(txt);
						}
					}, 'text');
			    }else if(mediaType == 4){ 
			    	  imageSize = getImageSize(preview);
		    		   txt.innerHTML="<img style='width:595px;height:370px;' src='"+preview+"'/>"; 
					  document.getElementById("msgDiv").appendChild(txt);
			    }else if(mediaType == 2){
			    	urlTemp=preview;
					if($.browser.msie) { 
						 txt.innerHTML="<iframe style='width:100%;height:370px;border:1px'  src='<%=request.getContextPath()%>/media/mediaView' scrolling='no'></iframe>"; 
						 document.getElementById("msgDiv").appendChild(txt);
					}  else { 
						txt.innerHTML="<iframe style='width:100%;height:370px;border:1px'  src='"+preview+"' scrolling='no'></iframe>"; 
					    document.getElementById("msgDiv").appendChild(txt);
					}
			    } 	
			 }else{
	    		//document.body.removeChild(bgObj);
			    document.getElementById("msgDiv").removeChild(title);
			    document.body.removeChild(msgObj);
			    window.open(filepath);
			   return false;
			 }
		}
		
		function getImageSize(src){
            var i = new Image(); //新建一个图片对象
             i.src = src;//将图片的src属性赋值给新建的图片对象的src
             return new Array(i.width,i.height);
       }  
	</script>
</head>
<body onload="heigthReset(64);" onresize="heigthReset(64);" style="padding:0; margin:0;background:url('../resources/images/theme/bg_right-2.gif');">
	<div id="center">
		<comm:navigator position="common.media>>common.media" />
		<div id="main">
		<form name="MyForm" action="${root}/media/mediaList" method="post"  target="Report">
		<table border="0" cellspacing="0" cellpadding="0" align="center" width="697">
			<tr id="inputForm"  style="display:block;">
				<td width="1" class='queryBackground'> &nbsp;</td>
				<td class='queryBackground' align="center">
					<table width="695" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="20%"  class="text_left_red2" align="right">
						  		<comm:message key="common.mediaName"/>:&nbsp;&nbsp;
						  	</td>						  		
						 	<td  width="30%" align="left">
								<input type="text" name="medianame" id="medianame" maxlength="50" size="30" style="width:183px;height: 20px;line-height:20px;" title="<comm:message key="meeting.m.keyWord.MeetingTip"/>"/>						 	
						 	</td>
						 	<td width="20%"  class="text_left_red2" align="right">
						  		<comm:message key="common.mediaType"/>:&nbsp;&nbsp;
						  	</td>						  		
						 	<td width="30%" align="left">
								<select name="mediaType" id="mediaType" style="width:187px;height:20px;">
									<option value="0" selected="selected"><comm:message key='unified.status.all'/></option>
								    <option value="1"><comm:message key='common.mediaType.html'/></option>
								    <option value="2"><comm:message key='common.mediaType.media'/></option>
								    <option value="3"><comm:message key='common.mediaType.text'/></option>
								    <option value="4"><comm:message key='common.mediaType.img'/></option>
								</select>
			             	</td>
						</tr>
					</table>
				</td>
				<td class='queryBackground'></td>
			</tr>
		</table>
		<table align="center" width="697">
			<tr>
			  <td height="30" align="center" valign="bottom" class='queryBackground' colspan="3">
				<table class='buttonToolBar' style="background: #eaf9ff;border-color:#AFD7DF;">
				  <tr>
					<td align="center">
					<input type="submit" name="new" value=" <comm:message key="comm.view"/>" class="button" >&nbsp;&nbsp;&nbsp;
					<input type="button" name="add" onclick='sAlert("<comm:message key='media.medialib.add.title'/>")' value=" <comm:message key="comm.add"/>" class="button" >&nbsp;&nbsp;&nbsp;<img id="img1" src="${root}/resources/skin/style/Button_top2.gif" style="cursor:hand;" border="0" onclick="javascript:hideOrDisplayInputForm(inputForm, img1)" alt="<comm:message key='comm.hidden_input_parameter'/>">
					
					</td>
				  </tr>
				</table>
			  </td>
			</tr>
			<tr>
				<td colspan="3" class="table_middle_bg"><iframe id="dataFrame" align="top" frameborder="0" name="Report" width="700" scrolling="no" src="${root}/media/mediaList"></iframe></td>
			</tr>
		</table>
	</form>
	</div>
	</div>
	<div id="center_right">
		<img src="${pageContext.request.contextPath}/resources/images/theme/bg_right.gif" width="25"/>
	</div>
	<div id="imgDiv"  style="width:750px;height:466px;display:none;">
       <img id="pv_image_area" width="750px" height="466px" src=""/>            
	</div>	    
	<div id="txtDiv" style="display:none;background-color:#FFF;">
	    <table width="600px" height="400px" cellspacing="0" cellpadding="0">
          <tr>         
             <td>
                <iframe  frameborder="0" id="pv_text_area" width="750px" height="466px" scrolling="auto" src=""></iframe>            
             </td>
          </tr>
         </table>
     </div> 
    <div id="webDiv" style="display:none;">
       <table width="600px" height="400px" cellspacing="0" cellpadding="0">
          <tr>         
             <td>
                <iframe  frameborder="0" id="pv_web_area" width="600px" height="400px" scrolling="auto" src=""></iframe>            
             </td>
          </tr>
       </table>
    </div>
</body>
</html>
