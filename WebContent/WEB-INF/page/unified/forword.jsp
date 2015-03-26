<%@ page pageEncoding="utf-8" isELIgnored="false"%>
<%@include file="/common.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link href="<%=request.getContextPath()%>/resources/style/main.css" rel="StyleSheet" type="text/css"/>
    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery.js"></script>
<script type="text/javascript" language="javascript">
function doSucc()
{
	var filename="${fileinfo['filename']}";
	var filepath="${fileinfo['filepath']}";
	var explain="${fileinfo['explainContents']}";
	var shoudong="${fileinfo['shoudong']}";
	var explainch = "";
	if(explain == 1){
		explainch = "病历讨论课件";
	}else if(explain == 2){
		explainch = "放射影像";
	}else if(explain == 3){
		explainch = "检查结果";
	}else if(explain == 4){
		explainch = "化验结果";
	}else if(explain == 5){
		explainch = "病理";
	}else if(explain == 6){
		explainch = "超声";
	}else if(explain == 7){
		explainch = "心电图";
	}else if(explain == 8){
		explainch = shoudong;
	}
	
	var d = new Date();
	var time = d.getTime();
	
	var filelist=window.parent.document.getElementById("filelist");
	var liobj=window.parent.document.createElement("li");
	liobj.setAttribute("id","li" + time); 
	var explainchSub = explainch.substring(0,6);
	var filenameSub = filename.substring(0,6);
	liobj.style.listStyleType='none';

	liobj.innerHTML = "<div id='totalStyle' style='width:200px;border:0px solid #000;'>"
				+ "   <div title='"+explainch+"' id='explainchStyle' "
	                   +" style='border:0px solid green;width:80px;float:left;'>"
				+ explainchSub
				+ "<span>:</span>"
				+ "</div>"
				+ "<div title='"+filename+"' id='filenameStyle' style='border:0px solid red;float:left;width:80px;'>"
				+ filenameSub
				+ "</div>"
				+ "<div id='deleteStyle' style='float:left;margin-left:3px;'><a style='color:blue' href='#' onclick=\"return delfile('li"
				+ time
				+ "', 'input"
				+ time
				+ "')\"><comm:message key='unified.delete' /></a></div>"
				+ "</div>";
		filelist.appendChild(liobj);

		var filevalue = window.parent.document.getElementById("filevalue");
		var inputobj = window.parent.document.createElement("input");
		inputobj.setAttribute("type", "hidden");
		inputobj.setAttribute("name", "myfiles");
		inputobj.setAttribute("id", "input" + time);

		if (explain != 8) {
			inputobj.setAttribute("value", filename + "@" + filepath + "@"
					+ explain + "@" + explainch);
		} else {
			inputobj.setAttribute("value", filename + "@" + filepath + "@"
					+ explain + "@" + explainch + "@" + shoudong);
		}

		filevalue.appendChild(inputobj);

		var bgObj = window.parent.document.getElementById("bgDiv");
		var msgTitle = window.parent.document.getElementById("msgTitle");
		var msgObj = window.parent.document.getElementById("msgDiv");
		window.parent.document.body.removeChild(bgObj);
		window.parent.document.getElementById("msgDiv").removeChild(msgTitle);
		window.parent.document.body.removeChild(msgObj);
	}
	function doFail() {
		var bgObj = window.parent.document.getElementById("bgDiv");
		var msgTitle = window.parent.document.getElementById("msgTitle");
		var msgObj = window.parent.document.getElementById("msgDiv");
		window.parent.document.body.removeChild(bgObj);
		window.parent.document.getElementById("msgDiv").removeChild(msgTitle);
		window.parent.document.body.removeChild(msgObj);
	}
</script>
  </head>
  
  <body>
    <table align="center">
    <tr><td>&nbsp;</td></tr>
    <tr><td>&nbsp;</td></tr>
    <tr><td>&nbsp;</td></tr>
    <tr>
    <td align="center">
      <font color="red"><b>${message}</b> </font>
    </td>   
    </tr>
    <tr><td>&nbsp;</td></tr>
    <c:if test="${succ}">
    <tr>
    <td align="center">
       <input type="button" value="<comm:message key='unified.sure'/>" onclick="doSucc()">
    </td>   
    </tr>
    </c:if>
    <tr><td>&nbsp;</td></tr>
     <c:if test="${!succ}">
    <tr>
    <td align="center">
       <input type="button" value="<comm:message key='unified.sure'/>" onclick="doFail()">
    </td>   
    </tr>
    </c:if>
    </table>
  </body>
</html>
