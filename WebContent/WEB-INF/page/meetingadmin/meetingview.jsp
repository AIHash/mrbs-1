<%@ page pageEncoding="UTF-8" isELIgnored="false"%>
<%@include file="/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title><comm:message key="meeting.m.detailedpage"/></title>
<comm:pageHeader hasEcList="false"/>
<link href="<%=request.getContextPath()%>/resources/style/main.css" rel="StyleSheet" type="text/css"/>
</head>
<style type="text/css">
#titleStyle{color:#3c3645;font-size:18px;font-weight: bold;height:30px;margin-top:15px;margin-bottom:0px;margin-left:40px;margin-right:40px;border-bottom:0px dashed #3c3645;}
#viewappinfor{margin-left:25px;margin-top:0px;margin-bottom:5px;margin-right:25px;position:relative;background-color:#dee; overflow-y:auto;}
.subinfor{border:0px solid #7F9DB9;background-color:#F8F8FF;margin-left:25px;margin-right:25px;}
</style>
<script type="text/javascript">
function Exp(filepath,title){  
	title = encodeURI(encodeURI(title));
	window.location.href="<%=request.getContextPath()%>/download?filepath="+filepath+"&filename="+title;
}

</script>
<body >
    <div id="viewappinfor">
	  <div id="titleStyle">
			<spanstyle=""background-color:#F8F8FF; display:-moz-inline-box; display:inline-block;">&nbsp;</span>
	  </div>		
	  <div class="subinfor" id="patientinfor1">
        <table id="shenqing" class="submit_table" align="center" width="100%" border="0">
          <tr>
            <td width="15%" align="right"><strong><comm:message key="meeting.m.title"/></strong>:&nbsp;&nbsp;</td>
            <td width="35%">${viewmeetingdetail.title}</td>
            <td width="15%" align="right"><strong><comm:message key="meeting.m.content"/></strong>:&nbsp;&nbsp;</td>
            <td width="35%" >${viewmeetingdetail.content}</td>
          </tr>
           <tr>
            <td width="15%" align="right"><strong><comm:message key="meeting.m.beforeMin"/></strong>:&nbsp;&nbsp;</td>
            <td width="35%">${viewmeetingdetail.beforeMin}</td>
            <td width="15%" align="right"><strong><comm:message key="unified.datetime"/></strong>:&nbsp;&nbsp;</td>
            <td width="35%" ><fmt:formatDate value="${viewmeetingdetail.startTime}" pattern="yyyy-MM-dd HH:mm" /></td>
          </tr>
           <tr>
            <td width="15%" align="right"><strong><comm:message key="meeting.m.starttime"/></strong>:&nbsp;&nbsp;</td>
            <td width="35%"><fmt:formatDate value="${viewmeetingdetail.startTime}" pattern="yyyy-MM-dd HH:mm" /></td>
            <td width="15%" align="right"><strong><comm:message key="meeting.m.endtime"/></strong>:&nbsp;&nbsp;</td>
            <td width="35%" ><fmt:formatDate value="${viewmeetingdetail.endTime}" pattern="yyyy-MM-dd HH:mm" /></td>
          </tr>
           <tr>
            <td width="15%" align="right"><strong><comm:message key="meeting.m.state"/></strong>:&nbsp;&nbsp;</td>
            <td width="35%">
            <c:if test="${0==viewmeetingdetail.state}">
                <comm:message key='meeting.m.stateinvalid'/>
            </c:if>
            <c:if test="${1==viewmeetingdetail.state}">
                <comm:message key='meeting.m.statenotstart'/>
            </c:if>
            <c:if test="${2==viewmeetingdetail.state}">
                <comm:message key='meeting.m.statehavastart'/>
            </c:if>
            <c:if test="${3==viewmeetingdetail.state}">
                <comm:message key='meeting.m.stateend'/>
            </c:if>
            <c:if test="${4==viewmeetingdetail.state}">
                <comm:message key='meeting.m.stateabsence'/>
            </c:if>       
            </td>
            <td width="15%" align="right"><strong><comm:message key="meeting.m.app.type"/></strong>:&nbsp;&nbsp;</td>
            <td width="35%" >${viewmeetingdetail.meetingType.name}</td>
          </tr>   
          
          <tr>
            <td width="15%" align="right"><strong><comm:message key="meeitng.m.create"/></strong>:&nbsp;&nbsp;</td>
            <td width="35%">${viewmeetingdetail.creator.name}</td>
            <td width="15%" align="right"><strong><comm:message key="meeting.m.holder"/></strong>:&nbsp;&nbsp;</td>
            <td width="35%" >${viewmeetingdetail.holder.name}</td>
          </tr>
          
          
          <tr>
            <td width="15%" align="right"><strong><comm:message key="meeting.m.meetinglevel"/></strong>:&nbsp;&nbsp;</td>
            <td width="35%">${viewmeetingdetail.level.name}</td>
            <td width="15%" align="right"><strong><comm:message key="meeting.m.room"/></strong>:&nbsp;&nbsp;</td>
            <td width="35%" >${viewmeetingdetail.meetingRoomId.name}</td>
          </tr>
           <tr>
            <td width="15%" align="right"><strong><comm:message key="meeting.m.remark"/></strong>:&nbsp;&nbsp;</td>
            <td colspan="3">${viewmeetingdetail.remark}</td>
          </tr>  
           <c:if test="${viewmeetingdetail.meetingKind==1}">
           <tr>
            <td width="15%" align="right"><strong><comm:message key="meeting.m.summaryacc"/></strong>:&nbsp;&nbsp;</td>
            <td colspan="3">
            <ul id="filelist">
             <c:forEach var="myfilelist" items="${viewmeetingdetail.applicationId.accessories}">
	            	<li id="lyc${myfilelist.id}">
	            	<span style="color:red">${myfilelist.type.name}:&nbsp;&nbsp;</span>
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
           </c:if>       
        </table>
      </div>
	  <div id="titleStyle2" style="height:5%;color:#3c3645;font-size:18px;font-weight: bold;margin-top:15px;margin-bottom:0px;margin-left:40px;margin-right:40px;">
			<span style="background-color:#dee; display:-moz-inline-box; display:inline-block; width:25px;">&nbsp;</span>&nbsp;</span>
	  </div>			  
			  </div>      
    </div>

     <div style="margin:auto; width:100%; text-align:center; padding:10px 0;"></div>
	<script language="javascript"> 
	//table隔行换色
	function tr_color(o,a,b,c,d){
	 var t=document.getElementById(o).getElementsByTagName("tr");
	 for(var i=0;i<t.length;i++){
	  t[i].style.backgroundColor=(t[i].sectionRowIndex%2==0)?a:b;
	  t[i].onmouseover=function(){
	   if(this.x!="1")this.style.backgroundColor=c;
	  }
	  t[i].onmouseout=function(){
	   if(this.x!="1")this.style.backgroundColor=(this.sectionRowIndex%2==0)?a:b;
	  }
	 }
	}
	
	//tr_color("表格名称","奇数行背景","偶数行背景","鼠标经过背景","点击后背景");
	tr_color("shenqing","#fafaff","#ffffff","#ecfbd4","#e7e7e7");
	</script>
</body>
</html>