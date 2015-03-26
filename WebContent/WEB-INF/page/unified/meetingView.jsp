<%@ page pageEncoding="UTF-8" isELIgnored="false"%>
<%@include file="/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title><comm:message key="unified.view" /></title>
<link href="<%=request.getContextPath()%>/resources/style/main.css" rel="StyleSheet" type="text/css"/>
<script type="text/javascript">
function Exp(filepath,title){  
	title = encodeURI(encodeURI(title));
	window.location.href="<%=request.getContextPath()%>/download?filepath="+filepath+"&filename="+title;
}
</script>
</head>

<body>
        <table id="shenqing" class="submit_table" align="center" width="100%" border="1">
          <tr>
            <td colspan="4" align="center"><strong><comm:message key='unified.detail.info'/></strong></td>
          </tr>
          <tr>
            <td width="15%" align="right"><strong><comm:message key='unified.title'/></strong>:&nbsp;&nbsp;</td>
            <td width="35%">${meetingview.title}</td>
            <td width="15%" align="right"><strong><comm:message key='unified.content'/></strong>:&nbsp;&nbsp;</td>

            <td width="35%" >${meetingview.content}</td>
          </tr>
           <tr>
            <td width="15%" align="right"><strong><comm:message key='unified.meetingtype'/></strong>:&nbsp;&nbsp;</td>
            <td width="35%">${meetingview.meetingType.name}</td>
            <td width="15%" align="right"><strong><comm:message key='unified.datetime'/></strong>:&nbsp;&nbsp;</td>
            <td width="35%" ><fmt:formatDate value="${meetingview.applicationId.expectedTime}" pattern="yyyy-MM-dd HH:mm" /></td>
          </tr>
           <tr>
            <td width="15%" align="right"><strong><comm:message key='unified.status'/></strong>:&nbsp;&nbsp;</td>
            <td width="35%">
            <c:if test="${0==meetingview.state}">
                <comm:message key='unified.status.none'/>
            </c:if>
            <c:if test="${1==meetingview.state}">
                <comm:message key='unified.status.nostart'/>
            </c:if>
            <c:if test="${2==meetingview.state}">
                <comm:message key='unified.status.start'/>
            </c:if>
            <c:if test="${3==meetingview.state}">
                <comm:message key='unified.status.end'/>
            </c:if>
            <c:if test="${4==meetingview.state}">
                 <comm:message key='unified.status.absence'/>
            </c:if>
             </td>
            <td width="15%" align="right"><strong><comm:message key='unified.application.title'/></strong>:&nbsp;&nbsp;</td>
            <td width="35%" >${meetingview.applicationId.purpose}</td>
          </tr>   
          <tr>
            <td width="15%" align="right"><strong><comm:message key='unified.meeting.requester'/></strong>:&nbsp;&nbsp;</td>
            <td width="35%">${meetingview.creator.name}</td>
            <td width="15%" align="right"><strong><comm:message key='meeting.m.holder'/></strong>:&nbsp;&nbsp;</td>
            <td width="35%" >${meetingview.holder.name}</td>
          </tr>
          <tr>
            <td width="15%" align="right"><strong><comm:message key='unified.meetinglevel'/></strong>:&nbsp;&nbsp;</td>
            <td width="35%">${meetingview.level.name}</td>
            <td width="15%" align="right"><strong><comm:message key='unified.meetingroom'/></strong>:&nbsp;&nbsp;</td>
            <td width="35%" >${meetingview.meetingRoomId.name}</td>
          </tr>
          <tr>
            <td width="15%" align="right"><strong><comm:message key='meeting.m.refuseReson'/></strong>:&nbsp;&nbsp;</td>
            <td colspan="3">${meetingview.applicationId.refuseReason}</td>
          </tr>
          <tr>
            <td width="15%" align="right"><strong><comm:message key='unified.remark'/></strong>:&nbsp;&nbsp;</td>
            <td colspan="3">${meetingview.remark}</td>
          </tr>
          <tr>
            <td width="15%" align="right"><strong><comm:message key='unified.accessories'/></strong>:&nbsp;&nbsp;</td>
            <td colspan="3">
            <ul id="filelist">
            <c:forEach var="myfilelist" items="${meetingview.applicationId.accessories}">
	            <li id="lyc${myfilelist.id}">
	            	<span style="color:red">${myfilelist.type.name}</span>:&nbsp;&nbsp;
	            	<c:choose>
	            		<c:when test="${myfilelist.type.id == 2 }">
	            			<a style="color:blue" href="${HAINA_URL}/HinacomWeb/DisplayImage.aspx?OEM=162&UserID=${USER_LOGIN_SESSION.userId }&UserName=${sessionScope.encodeName}&ConferenceID=${application.id}" target="_blank">患者${myfilelist.name}影像</a>
	            		</c:when>
	            		<c:otherwise>
	            			<a style="color:blue" href="#" onclick="Exp('${myfilelist.path}','${myfilelist.name}');"  >${myfilelist.name}</a>
	            		</c:otherwise>
	            	</c:choose>
	            	&nbsp;&nbsp;</li>
              </c:forEach>
            </ul>
            </td>
          </tr>
        </table>
<div style="margin:auto; width:100%; text-align:center; padding:10px 0;"></div>
<script language="javascript"> 
//table隔行换色
function tr_color(o,a,b,c,d){
 var t=document.getElementById(o).getElementsByTagName("tr");
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