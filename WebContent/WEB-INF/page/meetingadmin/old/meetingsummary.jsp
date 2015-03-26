<%@ page pageEncoding="UTF-8" isELIgnored="false"%>
<%@include file="/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title><comm:message key="meeting.m.detailedpage"/></title>
	<comm:pageHeader hasEcList="false"/>
	<script type="text/javascript" language="javascript" src="<%=request.getContextPath()%>/resources/js/jquery.js"></script>
	<script src="<%=request.getContextPath()%>/resources/jqueryalerts/jquery.ui.draggable.js" type="text/javascript"></script>
	<script src="<%=request.getContextPath()%>/resources/jqueryalerts/jquery.alerts.js" type="text/javascript"></script>
	<link href="<%=request.getContextPath()%>/resources/style/main.css" rel="StyleSheet" type="text/css"/>
	<link href="<%=request.getContextPath()%>/resources/jqueryalerts/jquery.alerts.css" rel="stylesheet" type="text/css" media="screen" />
<script type="text/javascript">
	function check(){
		var summarycontent=document.getElementById("summarycontent").value;
		if(summarycontent==""||trim(summarycontent)=="")
		 {
			 jAlert("<comm:message key='meeting.m.summarycontentalert'/>","<comm:message key='meeting.m.infotishi'/>");
			 return false;
		 } 
		 return true;

	}
	function trim(str){ //删除左右两端的空格
	    return str.replace(/(^\s*)|(\s*$)/g, "");
	}

	function sAlert(strTitle){ 
	    var msgw,msgh,bordercolor; 
	    msgw=400;//提示窗口的宽度 
	    msgh=200;//提示窗口的高度 
	    titleheight=25;//提示窗口标题高度 
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

	    var msgObj=document.createElement("div") 
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
	    msgObj.style.height =msgh + "px"; 
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
	    title.style.height="18px"; 
	    title.style.width="100%";
	    title.style.font="12px Verdana, Geneva, Arial, Helvetica, sans-serif"; 
	    title.style.color="white"; 
	    title.style.cursor="pointer"; 
	    title.title = "点击关闭"; 
	    title.innerHTML="<table border='0′ width='100%' ><tr style='width:100%' ><td align='left' width='93%'>"+strTitle+"</td><td width='7%' >关闭</td></tr></table>";
	    title.onclick=function(){ 
	    document.body.removeChild(bgObj); 
	    document.getElementById("msgDiv").removeChild(title); 
	    document.body.removeChild(msgObj); 
	    } 
	    document.body.appendChild(msgObj); 
	    document.getElementById("msgDiv").appendChild(title); 
	    var txt=document.createElement("p"); 
	    txt.style.margin="1em 0" 
	    txt.setAttribute("id","msgTxt"); 
	    txt.innerHTML="<iframe width=100% height='100%' src='<%=request.getContextPath()%>/resources/fileupload.jsp'></iframe>"; 
	    document.getElementById("msgDiv").appendChild(txt); 
	} 
	$( function($) {
		$("#summarysubmit").click( function() {
			summarysubmit();
		});
	});

	function summarysubmit(){
		var tempfile=document.getElementsByName("myfiles");
		var myfiles="";
		if(tempfile!=null&&tempfile.length>0){
			for(var i=0;i<tempfile.length;i++){
				myfiles=myfiles+tempfile[i].value+"|";
			}
		}
		var requestsummaryid=$('#requestsummaryid').val();
		var summarycontent=$('#summarycontent').val();
		if(summarycontent==""||trim(summarycontent)=="")
		 {
			jAlert("<comm:message key='meeting.m.summarycontentalert'/>","<comm:message key='meeting.m.infotishi'/>");
			 return false;
		 } 
		$.post('${pageContext.request.contextPath}/meeadmdbd/summary', {
			'requestsummaryid' : requestsummaryid,'summarycontent':summarycontent,'myfiles':myfiles
		}, function(json) {
			if(json.flag){
				jAlert("<comm:message key='meeting.m.submitsucc'/>", "<comm:message key='meeting.m.infotishi'/>", function(r) {
					redirctlist();
				});
				var temedis="<comm:message key='system.meeting.alert.dis'/>";
				setTimeout("redirctlist()",temedis);
				//alert("<comm:message key='meeting.m.submitsucc'/>");
				//window.parent.location.reload();
				//window.top.window.$.fancybox.close();
			}else{
				jAlert("<comm:message key='meeting.m.submitfalse'/>", "<comm:message key='meeting.m.infotishi'/>");
			}

		}, 'json');
		
	}
	function redirctlist(){
		parent.location.href="<%=request.getContextPath()%>/meeadmdbd/meetingsearchlist";
	}
</script>	
</head>

<body>
	<div>
		<img src="${pageContext.request.contextPath}/resources/images/unified/logo.jpg"/>
		<div style="height: 10px;"/>
	</div>
   <form method="post" id="summaryform"  action="<%=request.getContextPath()%>/meeadmdbd/summary" onsubmit="return check()">
	<input type="hidden" id="requestsummaryid" name="requestsummaryid" value="${requestsummaryid}"/>
	<table id="summary_table" class="submit_table" align="center" width="100%" >
		<tr align="center">
			<td colspan="2" >
				<font size="4px" color="red"><strong><comm:message key='meeting.m.summarytext'/></strong></font>
			</td>
		</tr>
		<tr>
			<td width="15%" align="right"><strong><comm:message key='meeting.m.summarycontent'/>:</strong></td>
			<td width="85%">
				<textarea id="summarycontent" name="summarycontent" class="text-long" rows="8" cols="85%"></textarea>
				<font color="red" size="3px">*</font>
			</td>
		</tr>
		 <tr>
            <td width="15%" align="right"><strong><comm:message key='meeting.m.summaryacc'/>:</strong></td>
            <td width="85%"><div class="main1_sub"><p><a style="COLOR:blue;" href="#" onclick='sAlert("<comm:message key='meeting.m.summaryupal'/>")'><comm:message key='meeting.m.summaryup'/></a></p>
            <ul id="filelist">
            </ul>
            <table style="display:none">
            <tr>
            <td id="filevalue"></td>
            </tr>
            </table>
            </div></td>
          </tr>
		<tr align="center">
			<td colspan="2" >
				<input class="button" type="button"  name="summarysubmit" id="summarysubmit" value="<comm:message key='meeting.m.commit'/>"  />
        	</td>
		</tr>
	</table>
	</form>
</body>
</html>