<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page pageEncoding="UTF-8"%>
<%@include file="/common.jsp"%>
<html>
<head>
    <comm:pageHeader hasEcList="true"/>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/resources/skin/style/main.css"/>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/jqueryalerts/jquery.alerts.css" media="screen" />
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/jqueryalerts/jquery.ui.draggable.js" ></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/jqueryalerts/jquery.alerts.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/My97DatePicker/WdatePicker.js"></script>
    <script type="text/javascript"> 
		function deleteCheck(deptId){
			jConfirm("<comm:message key='js.confirm_delete' args='admin.department'/>\n\n<comm:message key='admin.click_ok'/>", "<comm:message key='meeting.m.infotishi'/>",function(resultConfirm){
				if(resultConfirm){
			    	$.post('${pageContext.request.contextPath}/dept/deleteDept?deptId=' + deptId,  function(text) {
						if(text=='success'){
							parent.location.href="<%=request.getContextPath()%>/dept/mainForDelete";
						} else if(text=='failed'){
							jAlert("该部门已被使用，不能删除！", "<comm:message key='meeting.m.infotishi'/>");
							return false;
						}else if(text=='notExist'){
							jAlert("该部门不存在，请确认后再删除！", "<comm:message key='meeting.m.infotishi'/>");
							return false;
						}else if(text.indexOf('<HTML>') != -1){
							window.location.href="<%=request.getContextPath()%>/index.jsp?message=system.session_expire";
						}
					}, 'text');
			    }else{
			    	return false;
			    }				
			});
		}
	</script>
</head>

<body onload="init()">
<table width="480" align="center" border="0" cellspacing="0" cellpadding="0">
    <tr>
	    <c:if test="${parentNodeNo != '001'}">
	    <td  colspan="3" height="30" valign="bottom"><input type="button" name="new" value="<comm:message key='comm.add'/>" class="button" onclick="javascript:location.href='${root}/dept/add'" />&nbsp;</td>
        </c:if>	
	</tr>
    <tr>
		<td colspan="3" height="5" ></td>
	</tr>
	<tr>
	   <td colspan="3" valign="top">
       <ec:table tableId="ec"
        var="dept"
        items="report_data"
        action="${root}/dept/list"
        title=""
        width="480" style="visibility :visible;" 
        retrieveRowsCallback=""
        filterRowsCallback=""
        sortRowsCallback="">
        <ec:row>
        	<ec:column property="name" title="admin.department_name" width="50%"/>
 	    	<ec:column property="id" title="comm.modify" sortable="false" viewsAllowed="html" width="30%" resizeable="false" style="text-align:center;">
         		<a href="${root}/dept/edit/${dept.id}"><img alt="<comm:message key="comm.modify"/>" src="${root}/resources/skin/style/update.gif" /></a>
          	</ec:column>
         	<ec:column property="null" title="comm.delete" sortable="false" viewsAllowed="html" width="30%" resizeable="false"  style="text-align:center;">
            	<a onclick="return deleteCheck(${dept.id});" ><img alt="<comm:message key="comm.delete"/>" src="${root}/resources/skin/style/delete.gif" /></a>
          	</ec:column>       
         </ec:row>
      </ec:table>
      </td>
	</tr>
</table>
</body>
</html>
<%
	String freshTree = (String) request.getAttribute("freshTree");
	if (null != freshTree)
	{
%>
<%
	}	
%>