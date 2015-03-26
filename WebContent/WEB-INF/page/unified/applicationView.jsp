<%@ page pageEncoding="UTF-8"%>
<%@include file="/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title><comm:message key="meeting.m.detailedpage"/></title>
<link href="<%=request.getContextPath()%>/resources/style/main.css" rel="StyleSheet" type="text/css"/>
</head>

<body > 
        <table id="shenqing" class="submit_table" align="center" width="100%" border="1">
        <tr>
            <td width="15%" align="right"><strong><comm:message key="unified.patient"/><comm:message key="admin.logon_user_name"/></strong>:&nbsp;&nbsp;</td>
            <td width="35%">${application.patientInfo.name}</td>
            <td width="15%" align="right"><strong><comm:message key="unified.patient"/><comm:message key="admin.user_sex"/></strong>:&nbsp;&nbsp;</td>
            <td width="35%">
            	<c:choose>
            		<c:when test="${0 == application.patientInfo.sex}">
            		  <comm:message key="comm.male"/>
            		</c:when>
            		<c:otherwise>
            			<comm:message key="comm.female"/>
            		</c:otherwise>
            	</c:choose>
             </td>     
          </tr>
          <tr>
           <td width="15%" align="right"><strong><comm:message key="unified.patient"/><comm:message key="patient.age"/></strong>:&nbsp;&nbsp;</td>
            <td width="35%">${application.patientInfo.age}</td>
            <td width="15%" align="right"><strong><comm:message key="unified.patient"/><comm:message key="unified.address"/></strong>:&nbsp;&nbsp;</td>
            <td width="35%">${application.patientInfo.address}</td>
          </tr>
           <tr>
            <td width="15%" align="right"><strong><comm:message key="unified.medical_record"/></strong>:&nbsp;&nbsp;</td>
            <td width="35%">${application.medicalRecord}</td>
            <td width="15%" align="right"><strong><comm:message key='meeting.m.icd10'/></strong>:&nbsp;&nbsp;</td>    
                <td width="35%"><select name="icd10Dic" id="icd10Dic"  multiple="multiple" class="multiselect">
                <c:forEach var="icd10Dic" items="${application.applicationICD10s}">
                <option value="${icd10Dic.icd.id}">${icd10Dic.icd.diagnosis}</option>  
                </c:forEach>    
                </select>
              </td>  
          </tr>
           <tr>
               <td width="15%" align="right"><strong><comm:message key="meeting.m.requestlever"/></strong>:&nbsp;&nbsp;</td>
            <td width="35%">${application.level.name}</td>
      <td width="15%" align="right"><strong><comm:message key='unified.dept'/></strong>:&nbsp;&nbsp;</td>
            <td width="35%">
            <select name="deptmentid" id="deptmentid" multiple="multiple" class="multiselect">
            	<option value="${application.mainDept.id}">${application.mainDept.name}</option>
                <c:forEach var="applicationDepartment" items="${application.depts}">
                	<option value="${applicationDepartment.department.id}">${applicationDepartment.department.name}</option>
                </c:forEach>    
              </select>
           </td>
          </tr>
          <tr>
            <td width="15%" align="right"><strong><comm:message key="meeting.m.porpose"/></strong>:&nbsp;&nbsp;</td>
            <td width="35%">${application.purpose}</td>
                        <td width="15%" align="right"><strong><comm:message key="unified.issuesToBe_discussed"/></strong>:&nbsp;&nbsp;</td>
            <td width="35%">${application.problem}</td>
          </tr>
            <c:if test="${2 == application.state}">
	           <tr>
		            <td width="15%" align="right"><strong><comm:message key="meeting.m.starttime"/></strong>:&nbsp;&nbsp;</td>
		            <td width="35%">
		                 <c:if test="${ meeting.startTime != null}">
		                     <fmt:formatDate value="${meeting.startTime}" pattern="yyyy-MM-dd HH:mm" />
		                 </c:if>
		            </td>
		            <td width="15%" align="right"><strong><comm:message key="meeting.m.endtime"/></strong>:&nbsp;&nbsp;</td>
		            <td width="35%" >
			            <c:if test="${ meeting.endTime != null}">
			            	<fmt:formatDate value="${meeting.endTime}" pattern="yyyy-MM-dd HH:mm" />
			            </c:if>
		            </td>
	           </tr> 
            </c:if>
            <c:if test="${1 == application.state}">
	            <tr>
		            <td width="15%" align="right"><strong><comm:message key='meeitng.suggestTime' /></strong>:&nbsp;&nbsp;</td>
		            <td width="35%"><fmt:formatDate value="${application.expectedTime}" pattern="yyyy-MM-dd HH:mm" /></td>   
		            <td width="15%" align="right"><strong><comm:message key="meeting.m.refuseReson"/></strong>:&nbsp;&nbsp;</td>
		            <td width="35%" >${application.refuseReason}</td>
	            </tr>
            </c:if>
            <c:if test="${3 == application.state}">
	            <tr>
		            <td width="15%" align="right"><strong><comm:message key='meeitng.suggestTime' /></strong>:&nbsp;&nbsp;</td>
		            <td width="35%"><fmt:formatDate value="${application.expectedTime}" pattern="yyyy-MM-dd HH:mm" /></td>
		            <td width="15%" align="right"><strong><comm:message key="meeting.m.refuseReson"/></strong>:&nbsp;&nbsp;</td>
		            <td width="35%" >${application.refuseReason}</td>
	            </tr>
            </c:if>
           <tr>
            <td width="15%" align="right"><strong><comm:message key="meeting.m.state"/></strong>:&nbsp;&nbsp;</td>
            <td width="35%">
            <c:if test="${1==application.state}">
               <comm:message key="meeting.m.statewait"/>
            </c:if>
            <c:if test="${2==application.state}">
               <comm:message key="meeting.m.statepass"/>
            </c:if>
            <c:if test="${3==application.state}">
               <comm:message key="meeting.m.staterefuse"/>
            </c:if>
            </td>
            <td width="15%" align="right"><strong><comm:message key="meeting.m.applicant"/></strong>:&nbsp;&nbsp;</td>
            <td width="35%" >${application.authorInfo.name}</td>
          </tr>
          <tr>
            <td width="15%" align="right"><strong><comm:message key="meeting.m.app.type"/></strong>:&nbsp;&nbsp;</td>
            <td width="35%" >${application.meetingType.name}</td>
            <td width="15%" align="right"><strong><comm:message key="meeting.m.app.request"/></strong>:&nbsp;&nbsp;</td>
            <td width="35%">${application.requester.name}</td>
          </tr>
          <tr>
            <td width="15%" align="right"><strong><comm:message key="meeting.m.summaryacc"/></strong>:&nbsp;&nbsp;</td>
            <td colspan="3">
            <ul id="filelist">
           <c:forEach var="myfilelist" items="${application.accessories}">
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
<script type="text/javascript"> 
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