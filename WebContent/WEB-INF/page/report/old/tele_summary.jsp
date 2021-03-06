<%@page pageEncoding="UTF-8"%>
<%@include file="/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title><comm:message key="meeting.m.detailedpage"/></title>
<link href="<%=request.getContextPath()%>/resources/style/main.css" rel="StyleSheet" type="text/css"/>
<style type="text/css">
.submit_table{border-collapse:collapse; border:solid #D2D2D2; border-width:1px 0 0 1px; width:90%; margin:auto;}
.submit_table caption {font-size:20px; font-weight:bold;background-color:rgb(255,255,153); height:40px;}
.submit_table td{line-height:20px;}
.submit_table th,.submit_table td {border:solid #D2D2D2; border-width:0 1px 1px 0; padding:2px;}
th {background-color: rgb(0,0,128); line-height: 20px;}
th font{color: white;font-weight: bold;}
tbody tr td{background-color: rgb(255,255, 204);}
</style>
</head>
<body>
	<table id="shenqing" class="submit_table" align="center" width="100%" border="1">
		<caption>远程会诊参会情况统计报表</caption>
		<col width="8%"/>
		<col width="10%"/>
		<col width="40%"/>
		<col width="15%"/>
		<col width="15%"/>
		<col width="6%"/>
		<col width="6%"/>
		<thead>
	        <tr>
				<td colspan="8">
	         		<comm:message key="metting.report.input.date"/>：<fmt:formatDate value="${today}" pattern="yyyy-MM-dd"/><br />
					<comm:message key="metting.report.input.perosn"/>：${sessionScope['USER_LOGIN_SESSION'].name}<br />
					<comm:message key="metting.report.time"/>：<fmt:formatDate value="${startDate}" pattern="yyyy-MM-dd"/>至<fmt:formatDate value="${endDate}" pattern="yyyy-MM-dd"/>
	         	</td>
	         </tr>
	         <tr align="center">
	         	<th><font><comm:message key="metting.report.id"/></font></th>
	         	<th><font><comm:message key="metting.consultationStatistics.date"/></font></th>
	         	<th><font><comm:message key="metting.consultationStatistics.purpose"/></font></th>
	         	<th><font><comm:message key="metting.report.department"/></font></th>
	         	<th><font><comm:message key="metting.report.expert"/></font></th>
	         	<th><font><comm:message key="metting.report.community.number"/></font></th>
	         	<th><font><comm:message key="metting.report.number"/></font></th>
	         </tr>
         </thead>
         <tbody>
         	<c:forEach items="${summary}" var="videoVO">
	         	<tr>
		         	<td align="center">${videoVO.number}</td>
		         	<td align="center">${videoVO.videoDate}</td>
		         	<td align="center">${videoVO.videoContent}</td>
		         	<td align="center">${videoVO.clinicalDepartmentName}</td>
		         	<td align="center">${videoVO.expertsName}</td>
		         	<td align="center">${videoVO.community}</td>
		         	<td align="center">${videoVO.lecturesPeopleCount}</td>
	         	</tr>
	         </c:forEach>
         </tbody>
        </table>
     <div style="margin:auto; width:100%; text-align:center; padding:10px 0;"></div>
     <script type="text/javascript"> 
//table隔行换色
function tr_color(o,a,b,c,d){
 var t = document.getElementById(o).getElementsByTagName("tr");
 for(var i=0;i<t.length;i++){
  t[i].style.backgroundColor=(t[i].sectionRowIndex%2==0)?a:b;
  t[i].onmouseover=function(){
   if(this.x!="1")this.style.backgroundColor=c;
  };
  t[i].onmouseout=function(){
   if(this.x!="1")this.style.backgroundColor=(this.sectionRowIndex%2==0)?a:b;
  };
 }
}
//tr_color("表格名称","奇数行背景","偶数行背景","鼠标经过背景","点击后背景");
tr_color("shenqing","#fafaff","#ffffff","#ecfbd4","#e7e7e7");

parent.document.getElementById('dataFrame').style.height = document.body.scrollHeight;
</script>
</body>
</html>