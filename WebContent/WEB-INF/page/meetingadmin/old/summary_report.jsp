<%@ page pageEncoding="UTF-8" isELIgnored="false"%>
<%@include file="/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title><comm:message key="meeting.m.detailedpage"/></title>
<link href="<%=request.getContextPath()%>/resources/style/main.css" rel="StyleSheet" type="text/css"/>
<style type="text/css">
.submit_table{border-collapse:collapse; border:solid #D2D2D2; border-width:1px 0 0 1px; width:90%; margin:auto; }
.submit_table caption {font-size:20px; font-weight:bold;background-color:rgb(255,255,153); height:40px;}
.submit_table td{line-height:20px;}
.submit_table th,.submit_table td {border:solid #D2D2D2; border-width:0 1px 1px 0; padding:2px;}

th {background-color: rgb(0,0,128); line-height: 20px;}
th font{color: white;font-weight: bold;}
tbody tr td{background-color: rgb(255,255, 204);}
</style>
</head>

<body>
	<div>
		<img src="${pageContext.request.contextPath}/resources/images/unified/logo.jpg"/>
		<div style="height: 10px;"/>
	</div>
	<table id="shenqing" class="submit_table" align="center" width="70%" border="1">
		<caption>${title }</caption>
		<col width="20%;"/>
		<col width="50%;"/>
		<col width="30%;"/>
		<thead>
	        <tr>
				<td colspan="8">
	         		填报日期：<fmt:formatDate value="${today}" pattern="yyyy-MM-dd"/><br />
					填报人：${sessionScope['USER_LOGIN_SESSION'].name }<br />
					报表统计时间：<fmt:formatDate value="${startDate}" pattern="yyyy-MM-dd"/>至<fmt:formatDate value="${endDate}" pattern="yyyy-MM-dd"/>
	         	</td>
	         </tr>
	         <tr align="center">
	         	<th><font>编号</font></th><th><font>${column}</font></th><th><font>会议次数</font></th>
	         </tr>
         </thead>
         <tbody>
	         <c:forEach items="${data}" var="entry">
	         	<c:set var="index"  value="${ index + 1}"/>
	         	<tr>
	         		<td>${index}</td>
	         		<td>${entry.key }</td>
	         		<td align="center">${entry.value}</td>
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
</script>
</body>
</html>