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
	<table id="shenqing" class="submit_table" align="center" width="80%" border="1">
		<caption>视频讲座参会情况统计报表</caption>
		<col width="5%;"/>
		<col width="10%;"/>
		<col width="20%;"/>
		<col width="10%;"/>
		<col width="10%;"/>
		<col width="25%;"/>
		<col width="10%;"/>
		<col width="10%;"/>
		<thead>
	        <tr>
				<td colspan="8">
	         		填报日期：<fmt:formatDate value="${today}" pattern="yyyy-MM-dd"/><br />
					填报人：${sessionScope['USER_LOGIN_SESSION'].name }<br />
					报表统计时间：<fmt:formatDate value="${startDate}" pattern="yyyy-MM-dd"/>至<fmt:formatDate value="${endDate}" pattern="yyyy-MM-dd"/>
	         	</td>
	         </tr>
	         <tr align="center">
	         	<th><font>编号</font></th><th><font>视频日期</font></th><th><font>视频内容</font></th><th><font>专家科室</font></th>
	         	<th><font>专家姓名</font></th><th><font>社区</font></th><th><font>听课人数</font></th><th><font>总人数</font></th>
	         </tr>
         </thead>
         <tbody>
	         <c:forEach items="${meetings}" var="meeting">
	         	<c:set var="professionalName"  />
	         	<c:set var="professionalDepart"  />
	         	<c:set var="unifiedNum"  value="0"/>
	         	<c:set var="allUnifiedNum"  value="0"/>
	         	<c:set var="firstUnified"/>
				<c:forEach items="${meeting.members}" var="member" varStatus="memberStatus">
					<c:choose>
						<c:when test="${member.member.userType.value == USER_TYPE_PROFESSIONAL }">
							<c:set var="professionalName"  value="${professionalName}${member.member.name}<br/>" />
							<c:set var="professionalDepart"  value="${professionalDepart}${member.member.deptId.name}<br/>" />
						</c:when>
						<c:otherwise>
							<c:if test="${unifiedNum == 0 }">
								<c:set var="firstUnified" value="${memberStatus.index}"/>
 							</c:if>
							<c:set var="unifiedNum"  value="${unifiedNum + 1 }"/>
							<c:set var="allUnifiedNum"  value="${allUnifiedNum + member.attendNo}"/>
						</c:otherwise>
					</c:choose>
				</c:forEach>
				<tr>
		         	<td rowspan="${unifiedNum}">${meeting.id}</td>
					<td rowspan="${unifiedNum}"><fmt:formatDate value="${meeting.startTime}" pattern="yyyy-MM-dd"/></td>
					<td rowspan="${unifiedNum}">${meeting.title}</td>
					<td rowspan="${unifiedNum}">
						${professionalDepart}
					</td>
					<td rowspan="${unifiedNum}">
						${professionalName}
					</td>
	         	<c:forEach items="${meeting.members}" var="member" varStatus="status">
	         		<c:if test="${member.member.userType.value != USER_TYPE_PROFESSIONAL }">
						<td>${member.member.name}</td>
						<td align="center">${member.attendNo}</td>
						<c:if test="${status.index == firstUnified}">
							<td rowspan="${unifiedNum}" align="center" valign="middle">${allUnifiedNum}</td>
						</c:if>
						</tr>
					</c:if>
	         	</c:forEach>
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