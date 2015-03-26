<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page pageEncoding="utf-8"%>
<%@include file="/common.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!-- <!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"> -->
<html>
  <head>
    <base href="<%=basePath%>">
    <title></title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link href="<%=request.getContextPath()%>/resources/style/main.css" rel="StyleSheet" type="text/css"/>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/My97DatePicker/WdatePicker.js"></script>
    <link href="${pageContext.request.contextPath}/resources/jqueryalerts/jquery.alerts.css" rel="stylesheet" type="text/css" media="screen" />
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/jqueryalerts/jquery.ui.draggable.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/jqueryalerts/jquery.alerts.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/validate.js"></script>
    <script type="text/javascript" language="javascript">
	$(function(){
		$('#query').click(function(){
			var startDate = $('#starttime').val();
			var endDate = $('#endtime').val();
			parent.queryFileRadiationImag(startDate,endDate)
		});

		var studylist = window.parent.document.getElementById("studylist");
		var filevalue=window.parent.document.getElementById("filevalue");
		$('#select').click(function(){
			var boxes = document.getElementsByName("haianBox");
			var arrSub = "";
		    for (var i = 0; i < boxes.length; i++) {
		    	if (boxes[i].checked) {
			    	var arr = boxes[i].value.split(':');
			    	var liObj = window.parent.document.createElement("li");
			    	liObj.setAttribute("id", "li" + i);
			    	var inputobj = window.parent.document.createElement("input");
					inputobj.setAttribute("type","hidden"); 
					inputobj.setAttribute("name","hainaValue");

					inputobj.setAttribute("id", "input" + i);
					var hiddenValue = arr[0] + "@" + arr[1] ;
					inputobj.setAttribute("value",hiddenValue);
					filevalue.appendChild(inputobj);
					arrSub = arr[1].substring(0, 8);
					liObj.style.listStyleType='none';
					liObj.innerHTML = "<div style='width:180px;'>" + "<div style='float:left;width:40px;'>患者:</div>" + "<div title='"+arr[1]+"' style='float:left;width:110px;'>" + arrSub + "<span>放射影像</span>" + "</div>" + "<div stle='float:left;width:30px;'>" + "<a style='color:blue' href='#' onclick=\"return delstudy('li" + i + "', 'input" + i +"')\"><comm:message key='unified.delete' /></a>" + "</div>" + "</div>" ;

					studylist.appendChild(liObj);
		    	}
		    }

			var bgObj=window.parent.document.getElementById("bgDiv");
			var msgTitle=window.parent.document.getElementById("msgTitle");
			var msgObj=window.parent.document.getElementById("msgDiv");
			window.parent.document.body.removeChild(bgObj); 
		    window.parent.document.getElementById("msgDiv").removeChild(msgTitle); 
		    window.parent.document.body.removeChild(msgObj);
		});

	});
  </script>
  <style type="text/css">
  .inputstyle{background:transparent;border:0px solid gray;"}
  </style>
  </head>
  <body  style="overflow-y:auto;">
    <table width="99%" align="center" border="0">
     <tr>
    <td colspan="4">&nbsp;</td>
    </tr>
     <tr>
		<td align="right" width="20%">上传影像起至日期:</td>
		<td align="left" width="35%">
			<input type="text" name="starttime" id="starttime"	onclick="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'%y-%M-%d'})" onfocus="this.blur()"  style="border:#999 1px solid;height:20px;width:175px;background:#fff url(<%=request.getContextPath()%>/resources/My97DatePicker/skin/datePicker.gif) no-repeat right;"	readonly="readonly" size="25" />		    
		</td>
		<td align="center" width="15%">—</td>
		<td align="left">
			<input type="text" name="endtime" id="endtime"	onclick="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'%y-%M-%d'})" onfocus="this.blur()"  style="border:#999 1px solid;height:20px;width:175px;background:#fff url(<%=request.getContextPath()%>/resources/My97DatePicker/skin/datePicker.gif) no-repeat right;"	readonly="readonly" size="25" />
		</td>
     </tr>
     <tr>
    	<td height="3" colspan="4">&nbsp;</td>
    </tr> 
    <tr >
    <td colspan='4' align="center"><input type="button" id="query" value="<comm:message key='comm.view'/>"></td>
    </tr>
     <tr>
    	<td height="15" colspan="4">&nbsp;</td>
    </tr>
    <tr id="hiddenTr" style="display: none;">
    	<td height="15" colspan="4" align="center"><p style="color: red;" id="tip">正在连接海纳服务器查询数据，请等待</p></td>
    </tr> 
    <tr>
    <td align="left" width="100%" colspan="4">
		<table width="100%" border="0" id="data">
			<tr>
				<th width='10%' >&nbsp;</th>
				<th align='center' width='15%'><comm:message key='名字'/></th>
				<th align='center' width="15%"><comm:message key='性别'/></th>
				<th align='center' width='30%'><comm:message key='检查日期'/></th>
				<th align='center' width='30%'><comm:message key='物理疗法'/></th>
			</tr>

			<tr>
			    <td colspan='5' align='center'><input type='button' id='select' value='选择'/></td>
			</tr>
         </table>
    </td>    
    </tr>
    </table>
  </body>
</html>
