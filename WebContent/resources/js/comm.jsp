<%@page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix='comm' uri='/WEB-INF/common.tld' %>
//------------------------------------------------------------------------------------------------------------------------------------------------
//															全局变量
//------------------------------------------------------------------------------------------------------------------------------------------------


//下拉框箭头按钮
var dropDownBtn = null;
var calendarBox = null;
var panel = null;

//日期编辑器
var dateEditor = null;
//显示、隐藏标志
var isExist = false;
//top坐标值
var top = 0;
//left坐标值
var left = 0;


//------------------------------------------------------------------------------------------------------------------------------------------------
//															通用函数
//------------------------------------------------------------------------------------------------------------------------------------------------
function $()
{
	var elements = [];

	for (var i = 0; i < arguments.length; i++)
	{
		var element = arguments[i];
		if (typeof(element) == 'string')
		{
			var elemId=element;
			element = document.getElementById(elemId);
			
			if (element==null)
			{
				element = document.getElementsByName(elemId);
			
				if (element.length>0)
				{	
					element=element[0];	
				}
				else
				{
					element=null;
				}
			}
		}

	    if (arguments.length == 1) 
			return element;
    
		elements.push(element);
	}

	return elements;
}
//获取当前元素的绝对坐标
function getAbsolutePosition(element)
{
	top = element.offsetTop;
	left = element.offsetLeft;
	var e = element;
	while (e = e.offsetParent)
	{
		top+= e.offsetTop;
		left+= e.offsetLeft;
	}
}

//选中所有的多选下拉列表
function selectAllOptionList(list)
{
	for (i = 0; i < list.options.length; i++)
		list.options[i].selected = true;
}

//选中所有的复选框
function selectAll(fieldName)
{
	boxs = document.getElementsByName(fieldName);
	for ( var i = 0; i < boxs.length; i++ )
		boxs[i].checked = true;
}

//清除所有的复选框选中标志
function disSelectAll(fieldName)
{
	boxs = document.getElementsByName(fieldName);
	for ( var i = 0; i < boxs.length; i++ )
		boxs[i].checked = false;
}

//判断给定的复选框有没有选择1个(推荐使用)
function isSelectOne(fieldName)
{
	var flag = false;
	field = document.getElementsByName(fieldName);
	for ( var i = 0; i < field.length; i++ )
	{
		if (field[i].checked)
		{
			flag = true;
			break;
		}
	}

	return flag;
}

//框架自动适应页面高度
function FrameAutoResize(ele)
{
    try
	{
        document.all[ele].style.height=eval(ele).document.body.scrollHeight;
    }
	catch(e){}
}

//进入修改页面时光标默认位置
function focus_edit(myForm,fieldName)
{
   var field = new Object(eval(myForm + '.' + fieldName));
   if(field.createTextRange)  { 
       var  r  =  field.createTextRange();  
       r.moveStart('character',  field.value.length);  
       r.collapse();  
       r.select();  
   } 
}

//进入页面时光标默认位置
function focus(myForm,fieldName)
{
	var field = new Object(eval(myForm + '.' + fieldName));
	field.focus();
}

//进入页面时光标默认位置为选择框的第一条记录
function focus_sel(myForm,selName)
{
   var field = new Object(eval(myForm + '.' + selName));
   if(field.options.length > 0)
	{
	  field.options[0].selected = true;
	}
}
//+++++++++  字符串处理函数  +++++++++++

//************************************
//函数名称：strTrim
//功能：去掉字符串左右两端的空格
//参数：str	字符串
//返回：string	操作后的字符串
//备注：
//
//*************************************

function strTrim(str)
{
    for(nindex=0;nindex<str.length;nindex++)	//定位左边第一个不是空格的字符
    {
    	cCheck= str.charAt(nindex);
    	if(cCheck!=' ') break;
    }
    
    str = str.substr(nindex,str.length-nindex);	//舍去该字符左边的字符串
    
    for(oindex=str.length-1;oindex>=0;oindex--)	//定位右边第一个不是空格的自负
    {
    	cCheck = str.charAt(oindex);
    	if(cCheck!=' ') break;
    }
    
    str = str.substr(0,oindex+1);	//舍去该字符右边的字符串
    
    return str;
}

//**********************************
//函数名称：isPstvIntgr
//功能：	判断输入是否为正整数(PositiveInteger)+0
//参数：	paramString
//返回：	bool 	
//	true	是正整数+0
//	false	不是正整数
//备注：依赖字符串去空函数strTrim()
//	整数不大于9位
//**********************************

function isPstvIntgr(paramString, isNeed)
{
	var MaxIntegerLenth=9;	//整数不大于9位
	var CurrentChar;
	var intLength = 0;
	
	if (strTrim(paramString) == "")	//如果为空字符串
	{
		return false;
	}
	
	intLength = paramString.length;			
	
	if (intLength>MaxIntegerLenth)	//字符串长度大于MaxIntegerLenth
	{
		return false;
	}
	
	for (i=0;i < intLength;i++)	//从左到右检查字符是否为数字
	{
		CurrentChar = paramString.charAt(i);			
		if ((CurrentChar < '0')||(CurrentChar > '9'))	//如果不是数字
		{
			return false;
		}
	}
	return true;
}

//------------------------------------------------------------------------------------------------------------------------------------------------
//													隐藏、显示输入表单
//------------------------------------------------------------------------------------------------------------------------------------------------
function hideOrDisplayInputForm(inputForm, img1)
{
	if (inputForm.style.display == "none")
	{
		inputForm.style.display = "block";
		img1.src="${root}/resources/skin/style/Button_top2.gif";
		img1.alt=" <comm:message key='comm.hidden_input_parameter'/>  ";
	}
	else
	{
		//隐藏日期组件
		/*
		if (null != dropDownBtn)
		{
			dropDownBtn.style.visibility = "hidden";
			calendarBox.style.visibility = "hidden";
			panel.style.visibility = "hidden";
		}
		*/

		inputForm.style.display = "none";
		img1.src="${root}/resources/skin/style/Button_top2-2.gif";
		img1.alt=" <comm:message key='comm.hidden_input_parameter'/>  ";
	}
}

//------------------------------------------------------------------------------------------------------------------------------------------------
//															下拉列表项添加、删除
//------------------------------------------------------------------------------------------------------------------------------------------------

//从一个多选框中可以选择多个记录到第二个多选框中，
//从第二个多选框中删除多条记录
//form－－－－－－本页面form
//firstCombo－－ 列表框
//secondCombo－－选择框
function fncmdAddOnClick(form,firstCombo,secondCombo)
{
	fnAddRemove('AddforTree', form.firstCombo.selectedIndex, 
		form, form.firstCombo, form.secondCombo, '');
}

function fncmdRemoveOnClick()
{
	fnAddRemove('Remove', form.secondCombo.selectedIndex, 
		form, form.firstCombo, form.secondCombo, '');
}

function fnAddRemove(strAction, intSelIndex, frmName, cboFirstListBoxName,
					cboSecondListBoxName, strEnteredText)
{
	var frmName = new Object(frmName);
	var cboFirstBox = new Object(cboFirstListBoxName);
	var cboSecondBox = new Object(cboSecondListBoxName);
	var strText = new Object(strEnteredText);
	var intNumInRight = eval ('cboSecondBox.options.length');
	var intNumInLeft = eval ('cboFirstBox.options.length');
	var strlistName;
	var blnFound;
	var strNameUpper;
	var strValue;
	var strFirstText;
	var strSecondText;
	var strSelectedItem;
	var optlstSel1;
	var intCountInner;
	var intCount;
	var strFirstValue;
	var strSelectedValue;
	
	//“添加”按钮
	if((strAction.toUpperCase()).indexOf('ADD') != -1)
	{
		if (intSelIndex == -1)
		{
			if ((strText.value == null) || (strText.value == ''))
				{return;}
			strNameUpper=(strText.value).toUpperCase();
			for (intCount=0; intCount < intNumInLeft ; intCount++)
			{
				strValue = (cboFirstBox.options[intCount].text).toUpperCase();
				if (strValue == strNameUpper)
				{
					strlistname=cboFirstBox.options[intCount].text;
					blnFound= true;
					break;
				}
			}
			if (!blnFound)
			{
				alert ("Incorrect Name");
			}		
			else
			{
				optlstSel1=new Option(strlistname);
				cboSecondBox.options[intNumInRight] = optlstSel1;
			}
			strText.value = '';
			return;
		}

		else
		{
			var arrDevices = new Array();
			var iCount = 0;
			
			//把选中选项放到缓存数组中
			for (intCount=0; intCount < intNumInLeft; intCount++)
			{	
				if (cboFirstBox.options[intCount].selected)
				{
					strFirstText = cboFirstBox.options[intCount].text;
					strFirstValue = cboFirstBox.options[intCount].value;
					blnFound = false;
					for (intCountInner=0; intCountInner < intNumInRight;
									intCountInner++)
					{	
						strSecondText = cboSecondBox.options[intCountInner].value;
						if ((strFirstValue) == (strSecondText))
						{
							blnFound = true;
							break;
						}
					}
					
					if (!blnFound)
					{
						arrDevices[iCount] = new Array();
						arrDevices[iCount][0] = strFirstText;
						arrDevices[iCount][1] = strFirstValue;
						iCount++;
						//cboFirstBox.options[intCount] = null;
					}
				}
			}
			
			/*if((strAction.toUpperCase()).indexOf('TREE') != -1)
			{
				
				if( (intNumInRight + arrDevices.length) > parseInt("15",10) )
				{
					alert(sstrMessage);	
					return;
				}
				else
				
				{
					for (intCount=0; intCount < arrDevices.length; intCount++)
					{
						optlstSel1= new Option(arrDevices[intCount][0]);
						cboSecondBox.options[intNumInRight] = optlstSel1;
						cboSecondBox.options[intNumInRight].value = arrDevices[intCount][1];
						intNumInRight++;
					}

				}
			}
			else
			{*/

			//从缓存中取得选项,放到目的列表框中
			for (intCount=0; intCount < arrDevices.length; intCount++)
			{
				optlstSel1= new Option(arrDevices[intCount][0]);
				cboSecondBox.options[intNumInRight] = optlstSel1;
				cboSecondBox.options[intNumInRight].value = arrDevices[intCount][1];
				intNumInRight++;
			}

            
			//删除源列表框中所选信息
			var intSelected = 0;
			if (intSelIndex == -1) 
				{return;}
			for (intCount=intNumInLeft-1; intCount>=0; intCount--)
			{	
				if (cboFirstBox.options[intCount].selected)
				{
                   cboFirstBox.options[intCount] = null;
				   intSelected = intCount	
				}
			}
			if(intSelected == 0)
			{
				cboFirstBox.selectedIndex = intSelected;
			}
			else
			{
				cboFirstBox.selectedIndex = intSelected-1;
			}
            strText.value = '';
			//}
		}
        
		// 添加完成
		return;
	}
	/*var intSelected = 0;
	if (strAction.toUpperCase() == 'REMOVE')
	{	
		if (intSelIndex == -1) 
			{return;}
		for (intCount=intNumInRight-1; intCount>=0; intCount--)
		{	
			if (cboSecondBox.options[intCount].selected)
			{
				cboSecondBox.options[intCount] = null;
				intSelected = intCount								
			}						
		}
		
		if(intSelected == 0)
		{
			cboSecondBox.selectedIndex = intSelected;
		}
		else
		{
			cboSecondBox.selectedIndex = intSelected-1;
		}
		
		strText.value = '';
		return;
	}*/
	
	if (strAction.toUpperCase()=='ADDALL')
	{
		alert("ADDALL");
		cboSecondBox.options.length = 0;
		intNumInRight = 0;
		for (intCount=0; intCount < intNumInLeft; intCount++)
		{	
			strSelectedItem = cboFirstBox.options[intCount].text;
			strSelectedValue = cboFirstBox.options[intCount].value;
			optlstSel1= new Option(strSelectedItem);
			cboSecondBox.options[intNumInRight] = optlstSel1;
			cboSecondBox.options[intNumInRight].value = strSelectedValue;
			intNumInRight++;
		}
		cboFirstBox.options.length = 0;
		return;
	}

	// 全部删除
	if (strAction.toUpperCase()=='REMOVEALL')
	{	if 	(cboSecondBox.options.length != 0)
		{
			if (confirm ("<comm:message key='js.confirm_all_records'/>"))
				{cboSecondBox.options.length = 0;}
		}
		return;
	}
}


function addMulCbo(argobjForm,firstCombo,secondCombo)
{  
    var objFirstCbo= new Object(eval(argobjForm + '.' + firstCombo));
	var objSecondCbo= new Object(eval(argobjForm + '.' + secondCombo));
	fnAddRemove('AddforTree', objFirstCbo.selectedIndex, 
		argobjForm, objFirstCbo, objSecondCbo, '');
}

function deleteMulCbo(argobjForm,firstCombo,secondCombo)
{
    var objFirstCbo= new Object(eval(argobjForm + '.' + firstCombo));
	var objSecondCbo= new Object(eval(argobjForm + '.' + secondCombo));
	fnAddRemove('Remove', objSecondCbo.selectedIndex, 
		argobjForm, objFirstCbo, objSecondCbo, '');
}


//------------------------------------------------------------------------------------------------------------------------------------------------
//											从文本框中添加数据到选择框中
//------------------------------------------------------------------------------------------------------------------------------------------------

//可以一次添加多个数据，期间用";"格开
//当blnNumericCheck为true时添加，为false时不添加
//argcboName.options[intCount-1].value-----取得选择框的ID
//argcboName.options[intCount-1].text------取得选择框的值

function addComboFromText(argobjForm , argcboName , argtxtName , blnNumericCheck)
{
	var objCombo = new Object(eval(argobjForm + '.' + argcboName));
	var objText = new Object(eval(argobjForm + '.' + argtxtName));

	var strText=new String();
	strText=objText.value;
	
	//验证数据是否为正整数
	if(blnNumericCheck)
	{
		var strExt;
		var arrExt=new Array();
		arrExt = fnsplitString(strText,";");

		for(var intCount=0; intCount < arrExt.length;intCount++)
		{
			strExt = arrExt[intCount];
			strExt = fnRemoveLeadingSpace(strExt);
			strExt = fnRemoveTrailingSpace(strExt);
			var strErrorMessage = "<comm:message key='js.input_valid_phonenumber'/>";				
			
			if(!isPstvIntgr(strExt))
			{
				alert(strErrorMessage);
	            objText.value="";
	            objText.focus();
				return ;
			}

		}

	}

    //向列表框中添加数据
	strText=fnRemoveDuplicateValues(argobjForm,argcboName,strText);
	objText.value="";
	objText.focus();
}

//向列表框中添加数据（不能重复添加）
 function fnRemoveDuplicateValues(argobjForm , argcomboName , argtxtName)
 {
	var objCombo = new Object(eval(argobjForm + '.' + argcomboName));
	var strValue;
	var strComboValue;
	var arrValue = new Array();
	var blnFound;
			
	arrValue = fnsplitString(argtxtName,";");

	for(var intOuterLoop=0; intOuterLoop < arrValue.length;intOuterLoop++)
	{
		  blnFound = false;
		  strValue = arrValue[intOuterLoop];

		  strValue = fnRemoveLeadingSpace(strValue);
		  strValue = fnRemoveTrailingSpace(strValue);

		   if(strValue == "")
		   {
			   continue;
		   }

		   for(var intInnerLoop=0; intInnerLoop < objCombo.length;intInnerLoop++)
		   {
			   strComboValue = objCombo.options[intInnerLoop].text;
			   if(strValue.toUpperCase() == strComboValue.toUpperCase())     //如果有重复数值
			   {
						blnFound = true;
						break;
			   }	
			}
			if(!blnFound)
			{
			var optTemp = new Option(strValue,strValue);                     //添加数据到列表框
			objCombo.add(optTemp);
			}
	}	
 }

//删除字符串末尾的空格
function fnRemoveTrailingSpace(strText)
{
	var intLength=strText.length-1;
	var strTrimText= ""; 

	while ((strText.charAt(intLength)== ' '))
	{
		intLength = intLength - 1;
	}
	
	strTrimText = strText.substring(0,intLength+1);
    return strTrimText;	
}

//删除字符串开头的空格
function fnRemoveLeadingSpace(strTextValue)
{
	var strText=new String(strTextValue);
	 
	while( (strText.indexOf(' '))== 0)
	{
		strText=strText.substr(1);
	}
	
	return strText;		
}

//字符串按分割符分割，放到数组中
function fnsplitString(argstr,argstrDelimiter)
{
var arrAfterSplit = new Array();
arrAfterSplit=argstr.split(argstrDelimiter);
return arrAfterSplit;
}

//删除选择框中一条记录
function deleteCboValue(argobjForm,argcboName)
{
	var cboList = new Object(eval(argobjForm + '.' + argcboName));
	var intLength=cboList.length;
	var intCount;
	for (intCount=intLength;intCount>0;intCount--)
	{	
		//Chscks if the item is selected
		if (cboList.options[intCount-1].selected)
		{
			//Sets the object to null
			cboList.options[intCount-1] = null;
		}
	}
}

//删除选择框中全部记录
function deleteAllCboValues(argobjForm,argcboName)
{
	var cboList = new Object(eval(argobjForm + '.' + argcboName));

	//var cboList = MyForm.userCombo;
	if(cboList.options.length != 0)
	{
		if (confirm ("<comm:message key='js.confirm_all_records'/>?"))
		{
			cboList.options.length = 0;
		}
	}
	return;
}

//------------------------------------------------------------------------------------------------------------------------------------------------
//											弹出式日历选择组件
//------------------------------------------------------------------------------------------------------------------------------------------------

var monthName = new Array("January", "February", "March", "April", "May","June", "July", "August", "September", "October", "November", "December");
var calendarControl;
var isCalendarBoxCreated = false;

//鼠标经过事件
function mouseOverDateInput()
{
	dateEditor = event.srcElement;
		
	if (null == dropDownBtn)
	{
		dropDownBtn = document.createElement("<input id='dropDownBtn' type='button' class='dropdown_button' tabindex=-1 value=6 hidefocus=true bgcolor=#f6f6f6 style='position:absolute; visibility:visible; z-index:100' onclick='javascript:showCalendar()'>");
		document.body.appendChild(dropDownBtn);

		calendarBox = document.createElement("<div id='calendarBox' style='position:absolute; display:none; z-index:100'></div>");
		document.body.appendChild(calendarBox);

		panel =  document.createElement("<iframe id=\"panel\" style=\"position:absolute;z-index:9;width:expression(calendarBox.offsetWidth);height:expression(calendarBox.offsetHeight);top:expression(calendarBox.offsetTop);left:expression(calendarBox.offsetLeft);\" frameborder=\"0\"></iframe>");
		document.body.appendChild(panel);
	}
	
	with (dropDownBtn)
	{
		style.height = dateEditor.offsetHeight;
		style.width = 14;
		
		getAbsolutePosition(dateEditor);
		style.posLeft = left + dateEditor.offsetWidth - 14;
		style.posTop= top;
	}

	dropDownBtn.style.visibility = "visible";

	//debug.innerHTML = "<font color='blue'>name:" + dateEditor.name + "<br>left:" +dateEditor.style.pixelLeft + "<br>top:" + dateEditor.style.pixelTop + "</font>";

	if (event.srcElement.getAttribute("id") == "dropDownBtn")
	{
		dropDownBtn.style.visibility = "visible";
		dropDownBtn.style.backgroundColor="#FFBFFF";
	}
}

function showCalendar()
{
	if (!isCalendarBoxCreated)
		createCalendar();


		//alert("calendarBox.style.display:" + calendarBox.style.display);
		if (calendarBox.style.display == "block")
		{
			//alert("block:" + calendarBox.style.display);
			dropDownBtn.style.visibility = "hidden";
			panel.style.visibility = "hidden";
			calendarBox.style.display = "none";
		}
		else
		{
			//alert("none:" + calendarBox.style.display);
			panel.style.visibility = "visible";
			calendarBox.style.display = "block";
		}


	getAbsolutePosition(dateEditor);
	calendarBox.style.posLeft = left - (300 - dateEditor.offsetWidth);
	calendarBox.style.posTop =  top + dateEditor.offsetHeight;

	//alert("after:" + calendarBox.style.display);
}

function createCalendar()
{
	function calendar()
	{
	 	var today = new Date();
	 	this.todayDay = today.getDate();
		this.todayMonth = today.getMonth();
		this.todayYear = today.getFullYear();
	 	this.activeCellIndex = 0;
	}

	calendarControl = new calendar();

	var tmpHTML = "";
	tmpHTML+="<TABLE id='calendarTable' class='calendar' width=300px cellspacing=0 cellpadding=0>";
	tmpHTML+="<TR class='title' valign=top><TD bgcolor='#FFFFFF'>";
	tmpHTML+="<TABLE WIDTH=100% CELLSPACING=0 CELLPADDING=0>";
	tmpHTML+="<TR><TD align=middle colspan=7 height='30' bgcolor='#e4e4e4'>";

	//alert(getYearDropdownList());
	tmpHTML+= getYearDropdownList();
	tmpHTML+="<comm:message key='js.click_select_year'/>&nbsp;&nbsp;";
	tmpHTML+= getMonthDropdownList();
	//调试信息content.value = getMonthDropdownList();
	tmpHTML+="<comm:message key='js.click_select_month'/>&nbsp;&nbsp;&nbsp;&nbsp;<a target='_self' href='javascript:selectDate(" + calendarControl.todayYear + "," + (calendarControl.todayMonth + 1)+ "," + calendarControl.todayDay + ")'><comm:message key='js.today'/> " + calendarControl.todayYear + "-" +
			  (calendarControl.todayMonth + 1) + "-" + calendarControl.todayDay + "</a></TD></TR>";	tmpHTML+="</TABLE></TD></TR>";
	tmpHTML+="<TR><TD>";

	tmpHTML+="<TABLE border=0 id='calendarData' HEIGHT=100% WIDTH=100% CELLSPACING=0 CELLPADDING=0>";
	tmpHTML+="<TR height=25px>";
	tmpHTML+= "<TD align=middle><font color='red'><comm:message key='phone.service.week.SUN'/></font><div style='POSITION:absolute; left:0px; TOP:30px; width:300px; height=150px; background-Color=#FFFFFF; Z-INDEX:-1'><font id='dayBG' style=\"COLOR:#f0f0f0; FONT-FAMILY:'Arial Black'; FONT-SIZE:30pt\"></font></div></TD>";
	tmpHTML+= "<TD align=center><comm:message key='phone.service.week.MON'/></TD>";
	tmpHTML+= "<TD align=center><comm:message key='phone.service.week.TUE'/></TD>";
	tmpHTML+= "<TD align=center><comm:message key='phone.service.week.WED'/></TD>";
	tmpHTML+= "<TD align=center><comm:message key='phone.service.week.THU'/></TD>";
	tmpHTML+= "<TD align=center><comm:message key='phone.service.week.FRI'/></TD>";
	tmpHTML+= "<TD align=center><font color='red'><comm:message key='phone.service.week.SAT'/></font></TD>";
	tmpHTML+= "</TR>";

	for (var i=0; i<=5; i++)
	{
		tmpHTML+= "<TR height=20px>";
		for (var j = 0; j <= 6; j++)
			tmpHTML+= "<TD align=center></TD>";

		tmpHTML+= "</TR>";
	}

	tmpHTML+= "</TABLE></TD></TR>";

	tmpHTML+= "<TR><TD height='30' bgcolor='#e4e4e4'>";
	tmpHTML+= "<input type='button' class='button' id='button_clear' value=' <comm:message key='js.clear'/> ' onclick='clearDateEditor()'>&nbsp;<input type=button id='button_close' class='button' value=' <comm:message key='js.close'/> ' onclick='showCalendar()' />";
	tmpHTML+= "</TD></TR></TABLE>";
	
	calendarBox.innerHTML = tmpHTML;
	changeCalendarDate(calendarControl.todayYear, calendarControl.todayMonth, calendarControl.todayDay);
	isCalendarBoxCreated = true;
}

function getYearDropdownList()
{
	tmpHTML = "<Select id='calender_year' class='text' onChange='changeDate()'>";

	//alert("startDate:" + dateEditor.getAttribute("startDate"));
	//alert("endDate:" + dateEditor.getAttribute("endDate"));
	var startDate = 30;
	if (null != dateEditor.getAttribute("startYears"))
		startDate = dateEditor.getAttribute("startYears");
	startDate = calendarControl.todayYear - startDate;
	//alert(startDate);
	startDate = startDate < 1900 ? 1900 : startDate;

	var endDate = 1;
	if (null != dateEditor.getAttribute("endYears"))
		endDate = dateEditor.getAttribute("endYears");
	endDate = calendarControl.todayYear + parseInt(endDate);
	//alert(endDate);
	endDate = endDate > 2049 ? 2049 : endDate;

	for (var i = startDate; i <= endDate; i++)
		tmpHTML+= "<option value='" + i + "'>" + i + "</option>";

	tmpHTML+= "</Select>";
	
	return tmpHTML;
}

function getMonthDropdownList()
{
	var html = "<Select id='calender_month' class='text' onChange='changeDate()'>";
	
	for (var i=1; i<=12; i++)
	{
		if (i < 10)
			html+= "<option value='0" + i + "'>" + i + "</option>";
		else
			html+= "<option value='" + i + "'>" + i + "</option>";

	}

	html+= "</Select>";
	
	return html;
}

function changeDate()
{
	changeCalendarDate(calender_year.value, calender_month.value - 1, 1);
}

function changeCalendarDate(year, month, day)
{
	//同步年、月下拉框的选中状态
	calender_year.value = year;
	if (month < 9)
		calender_month.value = "0" + (month + 1);
	else
		calender_month.value = month + 1;

	if (calendarControl.year == year && calendarControl.month == month && (!day || calendarControl.day == day))
		return;

	if (calendarControl.year != year || calendarControl.month != month)
	{
		calendarControl.year = year;
		calendarControl.month = month;
	    
		if (month == 0)
		{
			calendarControl.preMonth = 11
			calendarControl.preYear = calendarControl.year - 1
		}
		else
		{
			calendarControl.preMonth = calendarControl.month - 1
			calendarControl.preYear = calendarControl.year
		}

		if (month == 11)
		{
			calendarControl.nextMonth = 0
			calendarControl.nextYear = calendarControl.year + 1
		}
		else
		{
			calendarControl.nextMonth = calendarControl.month + 1
			calendarControl.nextYear = calendarControl.year
		}

		calendarControl.startday = (new Date(year, month, 1)).getDay()
		if (calendarControl.startday == 0)
			calendarControl.startday = 7

		var curNumdays = getNumberOfDays(calendarControl.month, calendarControl.year)
		var preNumdays = getNumberOfDays(calendarControl.preMonth, calendarControl.preYear)
		var nextNumdays = getNumberOfDays(calendarControl.nextMonth, calendarControl.nextYear)
		var startDate = preNumdays - calendarControl.startday + 1
		var endDate = 42 - curNumdays - calendarControl.startday

		calendarControl.value = (calendarControl.month + 1);
		calendarControl.innerText = calendarControl.year

		//重新初始化单元格的显示内容
		for (var i = 1; i<= 42; i++)
		{
			var cell = calendarData.cells[i + 6];
			cell.style.backgroundColor = '';
			cell.innerHTML = "";
		}

		var datenum = 0;
		for (var i=startDate; i<=preNumdays; i++)
			datenum++;

		for (var i=1; i<=curNumdays; i++)
		{
			var cell = calendarData.cells[datenum + 7];
			//周末字体高亮显示
			//if ((datenum + 7) % 7 == 0 || (datenum + 7 + 1) % 7 == 0)
			//	cell.style.color = 'red';

			//当前日期背景高亮显示:年、月、日三个数字都必须相等
			if (calendarControl.todayYear == calender_year.value && calendarControl.todayMonth == parseInt(calender_month.value) - 1 && calendarControl.todayDay == i)
				cell.style.backgroundColor = '#FFBFFF';

			cell.innerHTML = "<a target='_self' href='javascript:selectDate(null, null, " + i + ")'><b>" + i + "</b></a>";
			datenum++;
		}

		for (var i=1; i<=endDate; i++)
			datenum++;
		
		//alert("月份:" + parseInt(calender_month.value) + ",english:" + monthName[parseInt(month) - 1]);
		dayBG.innerHTML = calender_year.value + "<br>" + monthName[parseInt(month)];
	}
}

function clearDateEditor()
{
	dateEditor.value = "";
	dropDownBtn.style.visibility = "hidden";
	panel.style.visibility = "hidden";
	calendarBox.style.display = "none";
}

function selectDate(year, month, day)
{
	var dateInfo = "";
	if (null == year)
		dateInfo+= calender_year.value;
	else
		dateInfo+= year;
	
	dateInfo+= "-";
	
	if (null == month)
		dateInfo+= calender_month.value;
	else
		if (month < 10)
			dateInfo+= "0" + month;
		else
			dateInfo+= month;

	dateInfo+= "-";
	
	if (day < 10)
		dateInfo+= "0" + day;
	else
		dateInfo+= day;

	dateEditor.value = dateInfo;
	dropDownBtn.style.visibility = "hidden";
	panel.style.visibility = "hidden";
	calendarBox.style.display = "none";
}

function getNumberOfDays(month, year)
{
	var numDays = new Array(31,28,31,30,31,30,31,31,30,31,30,31);
	n = numDays[month];
	if (month==1 && (year%4 == 0 && year%100 !=0 || year%400 == 0))
		n++;

	return n;
}

/**检查非法字符
 * str 要检查的字符
 * badwords 非法字符 (如#,&等,用法@#)
 * 如果不含非法字符，则返回true，否则返回false
 **/

function checkbadwords(str, badwords)
{
	if (typeof (str) != "string" || typeof (badwords) != "string") 
	{
	   return (false);
	}

	for (i=0; i < badwords.length; i++) 
	{
		bad = badwords.charAt(i);
		for (j=0; j < str.length; j++) 
		{
			if (bad == str.charAt(j)) 
			{
				return false;
				break;
			}
		}
	}

	return true;
}

function getRadioValue(radioName)
{
    var obj = document.getElementsByName(radioName);
    if (null != obj)
	{
        for (i=0; i < obj.length; i++)
		{
            if (obj[i].checked)
                return obj[i].value;
        }
    }

    return null;
}