//==============================================
// Copyright (c) 2002,威发（西安）软件有限公司
// All rights reserved.
//
// 文件名称：JSFunc.js
// 使用说明：见参考文件
// 摘    要：5类19个通用JavaScript函数
//
//
// 当前版本: V0.3 草稿
// 作    者: 张维先
// 完成日期: 2002-6-26
//================================================
//
//-----------------------------------------------------------------------------------
// 				版本历史
//-----------------------------------------------------------------------------------
//  版本	作者	完成日期	备注
//-----------------------------------------------------------------------------------
// V0.1草稿	张维先	2002-06-25	收集整理4类12个通用JavaScript函数
//-----------------------------------------------------------------------------------
// V0.2草稿	张维先	2002-06-25	新增 :
//					isNull 		判断null
//					isMailAddress	判断合法EMAIL地址格式
//					isDblFormatWell	Double格式判断
//					compareDate	比较日期
//					compareTime	比较时间
//					compareDateTime 比较日期时间
//					isVldIP		判断IP格式
//					修改 :
//					更改部分函数名
//					isLetter	增加输入校验
//					isDigit		增加输入校验	
//					isDigitChar	使用基本类型函数简写
//					isMoney		去掉幻数
//					isTelNmbr	使用基本类型函数简写
//					isVldDate	改变算法
//					StrToInt	增加输入校验
//					StrToDbl	增加输入校验
//-----------------------------------------------------------------------------------
// V0.3草稿	张维先	2002-06-26	整理了分类,共5类19个通用JavaScript函数
//-----------------------------------------------------------------------------------						
//				
//

var strSeparatorTime = ":"; //Time分隔符
var strSeparatorDate = "-"; //Date分隔符

strOweDunDaysIsNum      = "Days Get Arrear Back must be number";
strStopDaysIsNum        = "Days Stop Service must be number";
strDoubtDaysIsNum       = "Days Be Doubtful Account must be number";
strBadDaysIsNum         = "Days Be Bad Account must be number";
strInvalidCredit        = "Please input a valid number between 0 to 99 in Credit Degree Upper Limit and Credit Degree Lower Limit";
strCreditLimit          = "Credit Degree Upper Limit must be larger than Credit Degree Lower Limit";
strErrStartDateFmtFrom  = "Call Start Date (From) is not a valid date or in the correct format.";
strErrStartDateFmtTo    = "Call Start Date (To) is not a valid date or in the correct format.";
strNeedCheck            = "You should check one checkbox at least";
strNeedType             = "Please select one type";
strNeedIniCost          = "Please input a folat Initial Cost value";
strNeedIniDuration      = "Please input a Integer Initial Duration value";
strNeedCost             = "Please input a folat Cost value";
strNeedModifyDays       = "Please input a integer Add/Minus Days value";
strNeedDate             = "Please input a date";
strErrNewExpireDate     = "Date of New Expire Date is not a valid date or in the correct format.";
strErrExpireDate        = "Expiration Date (From) or Expiration Date (To) is not a valid date or in the correct format.";
strOverDraftNotDouble   = "Over Draft Limit is not a double";
strCardIsOntime         = "Card Product must is Realtime Balance Deduction";
strRateIsFloat          = "Please input a float discount rate";
strNeedBeginEndDate     = "Begin Date and End Date are needed,Correct Date format is MM/DD/YYYY";
strNeedBeginEndTime     = "Begin Time and End Time are needed. Correct Time format is hh:mm:ss";
strNeedBeginDuration    = "Begin Duration is required and should be an integer";
strErrDurationRange     = "End Duration must be larger than or equal to Begin Duration";
strErrBeginDateFmt      = "Begin Date is not in the correct format.";
strErrEndDateFmt        = "End Date is not in the correct format.";
strErrDateRange         = "End Date must be later than or equal to Begin Date";
strErrTimeRange         = "End Time must be later than or equal to Begin Time";
strServiceInfoIsIP      = "serviceinfo must be a IP address!";
strTimeNeedDate         = "Date is necessary when you input Time";
strErrFromToDateFmt     = "From and To is not a valid date or in the correct format.";
strNeedRecharge         = "Please input a folat Recharge Value";
strNotRechargable       = "The card is not rechargable";
strErrStatFromDateFmt   = "Date of Statistic Time (From) is not a valid date or in the correct format.";
strErrStatToDateFmt     = "Date of Statistic Time (To) is not a valid date or in the correct format.";
strErrStatFromTimeFmt   = "Time of Statistic Time (From) is not a valid date or in the correct format.";
strErrStatToTimeFmt     = "Time of Statistic Time (To) is not a valid date or in the correct format.";
strErrStCllingFrmDate   = "Date of Start Calling Time (From) is not a valid date or in the correct format.";
strErrStCllinToDate     = "Date of Start Calling Time (To) is not a valid date or in the correct format.";
strErrStCllingFrmTime   = "Time of Start Calling Time (From) is not a valid date or in the correct format.";
strErrStCllinToTime     = "Time of Start Calling Time (To) is not a valid date or in the correct format.";
strStCllngFrmTimeLater  = "Start Calling Time (To) must be later than or equal to Start Calling Time (From)";
strNeedDuration         = "Duration should be a integer";
strDurationIsZero       = "Unit Duration must be a integer large than zero";
strFeeIsZero            = "Unit Duration Fee must be a float large than zero";
strDefDurationIsZero    = "Default Unit Duration must be a integer large than zero";
strDefFeeIsZero         = "Default Unit Duration Fee must be a float large than zero";
strNeedCustName         = "Customer Name is required";
strPassNotConsistent    = "Password is not consistent with Confirm Password";
strNeedNumber           = "Please input a valid number";
strErrOperDateFrmFmt    = "Operation Date (From) is not a valid date or in the correct format.";
strErrOperDateToFmt     = "Operation Date (To) is not a valid date or in the correct format.";
strNeedResellRole       = "Please add a reseller role in the system first!";
strNeedName             = "Please input a name";
strNeedCode             = "Please input a code";
strNeedCountryCode      = "Called Country Code is required.";
strNeedProdName         = "Product Name is required.";
strProdNameInvalid      = "Product Name is invalid.";
strNeedAcctType         = "Account Type is required.";
strAcctTypeInvalid      = "Account Type is invalid.";
strNeedChare            = "Initial Charge is required.";
strChargeFeeIsNotFloat  = "Chargeing Fee must be an float.";
strChargeIsNotFloat     = "Initial Charge must be an float.";
strDuraIsNotInt         = "Free Duration must be an integer.";
strExpireIsNotDate      = "Expire Time is not a date.";
strQuanlityIsNotInt     = "Usage Quanlity must be an integer.";
strNeedCalledCountry    = "Called Country is required.";
strNeedCallerArea       = "Caller Area is required.";
strNeedCalledArea       = "Called Area is required.";
strNeedNumberRangeUp    = "Number Range Up is required.";
strNeedNumberRangeLow   = "Number Range Low is required.";
strNeedPortName         = "Port Name is required.";
strNeedAccessNumber     = "Access Number is required.";
strNeedIngressGW        = "Ingress Gateway is required.";
strNeedEgressGW         = "Egress Gateway is required.";
strNeedMinDuration      = "Min Duration is required.";
strNeedUnitDuraion      = "Unit Duration is required.";
strNeedMinFee           = "Min Duration Fee is required.";
strNeedUnitFee          = "Unit Duration Fee is required.";
strNeedConnectFee       = "Connect Fee is required.";
strNeedSetupFee         = "Setup Fee is required.";
strNeedAdditonalFee     = "Additional Fee is required.";
strNumberUpIsNotInt     = "Number Range Up must be an integer.";
strNumberLowIsNotInt    = "Number Range Low must be an integer.";
strDurationIsNotInt     = "Min Duration must be an integer.";
strUnitDuraIsNotInt     = "Unit Duration must be an integer.";
strCalledCountryInvaild = "Called Country is invalid.";
strCallerAreaInvaild    = "Caller Area is invalid.";
strCalledAreaInvaild    = "Called Area is invalid.";
strPortNameInvalid      = "Port Name is invalid.";
strAccessNumbInvaild    = "Access Number is invalid.";
strIngressGWInvalid     = "Ingress Gateway is invalid.";
strEgressGWInvaild      = "Egress Gateway is invalid.";
strMinDuraIsNotFloat    = "Min Duration Fee must be an float.";
strUnitFeeIsNotFloat    = "Unit Duration Fee must be an float.";
strConnectFeeIsNotFloat = "Connect Fee must be an float.";
strSetupFeeIsNotFloat   = "Setup Fee must be an float.";
strAddFeeIsNotFloat     = "Additional Fee must be an float.";


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

	//判斷兩個時間的大小
	//輸入的date1格式為：YYYY/MM/DD,或者YYYY/M/D  如：2010/01/05,2010/1/5
	//date1>date2 return 1;
	//date1<date2 return -1;
	//date1=date2 return 0;
	//輸入的date1或者date2有一個為空則Return false;
	function compareCheckDate(date1, date2) {
		if(date1 == null || date1 == ""){
			return false;
		}
		if(date2 == null || date2 == ""){
			return false;
		}
		var startTime=date1.replace(/-/g, "/");
		var endTime=date2.replace(/-/g, "/");

		var startTimetemp = new Date(startTime);
		var endTimetemp = new Date(endTime);
		if(startTimetemp > endTimetemp) return 1;
		else if(startTimetemp < endTimetemp) return -1;
		else return 0;
	}

	//取得日期字符串,返回YYYY/MM/DD YYYY/MM/DD HH:MM:SS YYYY-MM-DD YYYY-MM-DD HH:MM:SS
	function getDate(date,fomatCode){
	     var thisYear = date.getFullYear();
	     var thisMonth = date.getMonth() + 1;
	     //如果月份长度是一位则前面补0
	     if(thisMonth<10) thisMonth = "0" + thisMonth;
	     var thisDay = date.getDate();
	     //如果天的长度是一位则前面补0
	     if(thisDay<10) thisDay = "0" + thisDay;

	     var thisHour = date.getHours();
	     //如果天的长度是一位则前面补0
	     if(thisHour<10) thisHour = "0" + thisHour;

	     var thisMinutes = date.getMinutes();
	     //如果天的长度是一位则前面补0
	     if(thisMinutes<10) thisMinutes = "0" + thisMinutes;

	     var thisSec = date.getSeconds();
	     //如果天的长度是一位则前面补0   
	     if(thisSec<10) thisSec = "0" + thisSec;
	     if(fomatCode=='YYYY/MM/DD'){
	    	 return thisYear + "/" + thisMonth + "/" + thisDay;   
	     }
	     else if(fomatCode=='YYYY/MM/DD HH:MM:SS'){
	    	 return thisYear + "/" + thisMonth + "/" + thisDay+" "+thisHour+":"+thisMinutes+":"+thisSec;  
	     }else if(fomatCode=='YYYY-MM-DD'){
	    	 return thisYear + "-" + thisMonth + "-" + thisDay+" "+thisHour+":"+thisMinutes+":"+thisSec;  
	     }else if(fomatCode=='YYYY-MM-DD HH:MM:SS'){
	    	 return thisYear + "-" + thisMonth + "-" + thisDay+" "+thisHour+":"+thisMinutes+":"+thisSec;  
	     }else if(fomatCode=='YYYY-MM-DD HH:MM'){
	    	 return thisYear + "-" + thisMonth + "-" + thisDay+" "+thisHour+":"+thisMinutes;  
	     } else if(fomatCode=='YYYY/MM/DD HH:MM'){
	    	 return thisYear + "/" + thisMonth + "/" + thisDay+" "+thisHour+":"+thisMinutes;  
	     }
	}
	
	//下拉选框排序
	function sortOptions(options,optionSelect){
		var optionArray = new Array();
		var length = options.length;
		var optionValueSplit = "";
		if(length > 0){
			for(var i = 0; i < length; i++){
				optionArray[i] = options[i].text + "#" + options[i].value;
			}
			optionArray.sort(function(a,b){return a.localeCompare(b)});

			optionSelect.empty();
			var splitArray = "";
			for(var i = 0; i < length; i++){
				splitArray = optionArray[i].split('#');
				$("<option value=" + splitArray[1] + ">" + splitArray[0] +"</option>").appendTo(optionSelect);
			}
		}
	}
	
//++++++++++++  基本数据类型验证函数  ++++++++++++++

//**********************************
//函数名称：isPstvIntgr
//功能：	判断输入是否为正整数(PositiveInteger)和0
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
	
	for (i=0;i<intLength;i++)	//从左到右检查字符是否为数字
	{
		CurrentChar = paramString.charAt(i);			
		if ((CurrentChar < '0')||(CurrentChar > '9'))	//如果不是数字
		{
			return false;
		}
	}
	return true;
}

//**********************************
//函数名称：isNumber
//功能：	判断输入是否数字
//参数：	value
//返回：	bool 	
//	true	是
//	false	不是
//备注：依赖字符串去空函数strTrim()
//**********************************

function isNumber(value)
{
	if (strTrim(value) == "")	//如果为空字符串
		return false;
	else
	{
		for (i=0; i< value.length; i++)	//从左到右检查字符是否为数字
		{
			if ((value.charAt(i) < '0') || (value.charAt(i) > '9'))	//如果不是数字
				return false;
		}
	}

	return true;
}

//***************************************
//函数名称：isPstvDbl
//功能：	判断输入是否为正Double数(PositiveDouble)
//参数：	paramString
//返回：	bool
//	true	是正Double数
//	false	不是正Double数
//备注：依赖字符串去空函数strTrim()
//	正整型也返回True
//***************************************

function isPstvDbl(paramString)
{
	var length = 0;
	var decimalFlag = 0;
	var CurrentChar;
	var i = 0;

	if (strTrim(paramString) == "" ) //如果是空字符串则返回false
	{
		return false;
	}	
	else
	{
		length = paramString.length;
		decimalFlag = 0;	//小数点存在标志
		for (i=0;i<=length-1;i++)	//从左到右依次检查字符是不是数字
		{
			CurrentChar = paramString.charAt(i);
			if (decimalFlag == 0)	//小数点没有出现
			{
				if ((CurrentChar < '0')||(CurrentChar > '9'))	//如果不是数字字符
				{
					if (CurrentChar == '.')	//判断是否是小数点
					{
						decimalFlag++;
					}
					else	//不是小数点
					{
						return false;
					}
				}
			}
			else	//小数点已经出现
			{
				if (decimalFlag = 1)	//小数点出现了一次
				{
					if ((CurrentChar < '0')||(CurrentChar > '9'))	//如果不是数字字符
					{
						return false;
					}
				}
				else	//小数点多次出现
				{
					return false;
				}
				
			}
		} 
	}	
	return true;
}


//***************************************
//函数名称：isLetter
//功能：	判断字符是否是英文字母	
//参数：	cCheck	单个字符
//返回：	bool
//	true	是英文字母
//	false	不是英文字母
//备注：
//***************************************

function isLetter(cCheck)
{
	if (cCheck.length!=1)
	{
		return false;
	}
	return ((('a'<=cCheck) && (cCheck<='z')) || (('A'<=cCheck) && (cCheck<='Z')));
}


//***************************************
//函数名称：isDigit
//功能：	判断字符是否为阿拉伯数字
//参数：	cCheck	单个字符
//返回：	bool
//	true	是阿拉伯数字
//	false	不是阿拉伯数字
//备注：
//***************************************

function isDigit(cCheck)
{
	if (cCheck.length!=1)
	{
		return false;
	}
	return (('0'<=cCheck) && (cCheck<='9'));
}

//***************************************
//函数名称：isNull
//功能：	判断输入是否为Null
//参数：	strInput	输入字符串
//返回：	bool
//	true	是Null
//	false	不是Null
//备注：
//***************************************

function isNull(strInput){
  var i;
  for(i = 0; i < strInput.length; i++)
     if (strInput.charAt(i) != '' && strInput.charAt(i) != '丂')
        return false;
  return true;
}


//+++++++++++++  扩展数据类型验证函数  +++++++++++

//***************************************
//函数名称：isDigitChar
//功能：	判断字符串是否只包含阿拉伯数字和英文字母
//参数：	paramString
//返回：	bool
//	true	只包含数字和英文字母
//	false 	包含其他字符
//备注：依赖字符串去空函数strTrim();基本数据类型验证函数
//	isLetter(),isDigit()
//***************************************

function isDigitChar(paramString)
{
	var CurrentChar;
	var length = 0;

	if (strTrim(paramString)=="")
	{
		return false;
	}
	length = paramString.length;
	for (i=0;i<=length-1;i++)	//从左到右依次判断字符是否合法
	{
		CurrentChar = paramString.charAt(i);
		if (isLetter(CurrentChar))
		{
			continue;
		}
		if (isDigit(CurrentChar))
		{
			continue;
		}
		return false;
	}
	return true;
}


//***************************************
//函数名称：isMoney
//功能：	判断输入是否为货币类型
//参数：	paramString
//返回：	bool
//	true	是货币
//	false	不是货币
//备注：	依赖类型验证函数isPstvDbl()
//	所以货币植不能为负数且不能大于999999.99。
//
//***************************************

function isMoney(paramString)
{
	var MaxInteger=6;	//最大整数位数
	if (!isPstvDbl(paramString))	//如果不是正double数
	{
        	return false;
     	}
     	
     	intLength = paramString.length;	     
	split=paramString.split(".");	     //按小数点分割到数组
     	if(split.length==2)	//如果数组有两个元素
     	{	     		
      		if(split[1]>99||split[1].length>2)	//小数部分超过2位
       		{
          		return false;
       		}	     	   	     
       		if(split[0].length>MaxInteger)	//整数部分超过MaxInteger位
       		{
     	  		return false;
       		}	
     	}	     
     	return true;
}


//***************************************
//函数名称：isTelNmbr
//功能：	判断输入是否为电话号码格式（xxx-xxx-xxx）
//参数：	paramString
//返回：	bool
//	true	是电话号码格式
//	false	否
//备注：	依赖字符串去空函数strTrim(),
//		基本数据类型函数isDigit()
//	
//***************************************

function isTelNmbr(paramString)
{
	var CurrentChar;
	var length = 0;

	if (strTrim(paramString)=="")
	{
		return false;
	}
	length = paramString.length;
	for (i=0;i<=length-1;i++)
	{
		CurrentChar = paramString.charAt(i);
		if (isDigit(CurrentChar))
		{
			continue;
		}
		if (CurrentChar == '-')
		{
			continue;
		}
		return false;
	}
	return true;
}

//***************************************
//函数名称：	isTime
//
//功能：	判断输入是否为合法时间(带是否为空值检查)
//
//参数：	field	表单时间域
//
//返回：bool(true-合法时间, false-非法时间)	
//
//备注：	依赖isVldTime()函数
//***************************************
function isTime(field)
{
		if (field.value.length < 1)
		{
			//alert("1-时间格式无效！");
			return false;
		}
		else
		{
			times = field.value.split(strSeparatorTime);
			if (times.length != 3)
			{
				//alert("2-时间格式无效！");
				return false;
			}
			else
			{
				//alert(times[0] + times[1] + times[2]);
				if (!isVldTime(times[0], times[1], times[2]))
				{
					//alert("3-时间格式无效！");
					return false;
				}

				/*
				if (times[0] == "00" && times[1] == "00" && times[2] == "00")
					return false;
				*/
			}
		}

		return true;
}

//***************************************
//函数名称：	isVldTime
//功能：	判断输入是否为合法时间
//参数：	paramHour	时
//		paramMinute	分
//		paramSecond	秒
//返回：bool
//	true	是合法时间
//	false	不是
//备注：	依赖类型转换函数StrToInt();isPstvIntgr()
//***************************************

function isVldTime(paramHour,paramMinute,paramSecond)
{
	var hour = 0;
	var minute = 0;
	var second = 0;
	
	if (isPstvIntgr(paramHour)&&isPstvIntgr(paramMinute)&&isPstvIntgr(paramSecond))
	{
		hour = StrToInt(paramHour);
		minute = StrToInt(paramMinute);
		second = StrToInt(paramSecond);
		
		if (hour>=24||hour<0)
		{
			return false;
		}
		if (minute>=60||minute<0)
		{
			return false;
		}
		if (second>=60||second<0)
		{
			return false;
		}
		return true;
	}
	else
	{
		return false;
	}
	
}


//***************************************
//函数名称：isVldDate
//功能：	判断输入是否为合法日期
//参数：	strYear	年
//		strMonth月
//		strDay	日
//返回：	bool
//	true	是合法日期
//	false	不是合法日期
//备注：依赖类型转换函数StrToInt（）
//	
//***************************************

function isVldDate(strYear,strMonth,strDay)
{
	var intYear;
   	var intMonth;
   	var intDay;
   	var boolLeapYear;
   	
   	intYear = StrToInt(strYear);
   	intMonth = StrToInt(strMonth);
   	intDay = StrToInt(strDay);
   
   	if(isNaN(intYear)||isNaN(intMonth)||isNaN(intDay)) return false;
   
   	if(intMonth>12||intMonth<1) return false;
   
   
	if ((intMonth==1||intMonth==3||intMonth==5||intMonth==7||intMonth==8||intMonth==10||intMonth==12)&&(intDay>31||intDay<1))
	{
        return false;
	}
	   
   	if((intMonth==4||intMonth==6||intMonth==9||intMonth==11)&&(intDay>30||intDay<1))
        return false;
   
   	if(intMonth==2){
      	if(intDay<1) return false;
      
      	boolLeapYear = false;
      	if((intYear%100)==0){
        	if((intYear%400)==0) boolLeapYear = true;
      	}
      	else{
        	 if((intYear%4)==0) boolLeapYear = true;
      	}
      
      	if(boolLeapYear){
        	 if(intDay>29) return false;
      	}
      	else{
        	 if(intDay>28) return false;
      		}
   	}
   
   return true;
}

//***************************************
//函数名称：isMailAddress
//功能：	判断输入是否为合法Email地址
//参数：	str	输入

//返回：	bool
//	true	是合法地址
//	false	不是合法地址
//备注：依赖isNull();strTrim()
//	
//***************************************

function isMailAddress(str)
{
	//alert(str);

	if(isNull(str))
		return false;
    else
    {
       str = strTrim(str);
       if (-1 == str.indexOf("@"))
          return false;
       else
       {
			pos = str.indexOf("@");
			//alert("@:" + pos);
			//"@"左端为空
			if (0 == pos)
				return false;
			else
			{
				strTemp = str.substring(pos+1, str.length - 1);
				strTemp = strTrim(strTemp);
		  
				//"@"右端是否为空
				if (strTemp == "")	
					return false;
				else
					//右端是否包含"." 且不是最右
					if (-1 == strTemp.indexOf("."))	
						return false;
					else
					{
						//"."左端为空
						if (0 == strTemp.indexOf("."))
							return false;
						
						str = str.replace("@", "");
						str = str.replace(".", "");

						//alert(str);
						//从左到右依次判断是否包含中文字符
						for (i = 0; i < str.length; i++)	
						{
							//alert(str.charAt(i));
							if (0 > str.charCodeAt(i) || str.charCodeAt(i) > 255)
								return false;
						}
					}
			}
		}
    }

	return true;
}

//***************************************
//函数名称：isVldIP
//功能：判断输入的IP格式是否合法
//参数：paramString
//返回：bool
//	true	合法
//	false	不合法
//备注：
//***************************************

function isVldIP(paramString)
{	
    var strSeparatorIP = "."; //IP分隔符
	var strArray;
	var i;
	
	strArray=paramString.split(strSeparatorIP);
	
	if(strArray.length == 4) //如果输入被"."分为四部分
	{
		for(i=0;i<4;i++)
		{
			if(strTrim(strArray[i])==""||
				strTrim(strArray[i])!=strArray[i]||
				strArray[i].length>3||
				(strArray[i].substr(0,1)=="0"&&strArray[i]!="0")||
				(!isPstvIntgr(strArray[i]))||
				StrToInt(strArray[i])>255||
				StrToInt(strArray[i])<0)
			{
				return false;
			}
			else
			{
				if(i==0||i==3)
				{
					if(StrToInt(strArray[i])==0)
					{
						return false;
					}
				}
			}
		}//for
	}
	else
		if (strArray.length != 3) //如果输入被"."分为三部分
			return false;
		else
		{
			//三个分段中有空值
			if ("" == strTrim(strArray[0]) || "" == strTrim(strArray[1]) || "" == strTrim(strArray[2]))
				return false;
			else
			{
				var str = paramString.replace(".", "");
				//alert(str);
				//从左到右依次判断是否包含中文字符
				for (i = 0; i < str.length; i++)	
				{
					//alert(str.charAt(i));
					if (0 > str.charCodeAt(i) || str.charCodeAt(i) > 255)
						return false;
				}
			}
		}

	return true;
}


//+++++++++++++  数据类型转换函数  ++++++++++++++

//***************************************
//函数名称：StrToInt
//功能：	将字符串转换到正整形
//参数：	paramString
//返回：	integer	转换后的结果
//	
//备注：依赖基本类型函数isPstvIntgr()校验输入,
//	输入不是标准整形数字的字符串则返回null。
//***************************************

function StrToInt(paramString)
{
	var RetVal = 0;
	var CurrentChar;
	if (isPstvIntgr(paramString))
	{
		for(i=0;i<=paramString.length-1;i++)	//从右向左依次转换字符到数字
		{
			CurrentChar = paramString.charAt(i);
			
			RetVal = RetVal*10+(CurrentChar-'0');
		}
		return RetVal;
	}
	else
	{
		return null;
	}
}


//***************************************
//函数名称：StrToDbl
//功能：	将字符串转换到double类型
//参数：	paramString
//返回：	double	转换后的结果
//	
//备注：依赖基本类型函数isPstvDbl(),
//	输入不是标准double数字的字符串返回null。
//***************************************

function StrToDbl(paramString)
{
	var RetVal = 0.00;	
	var CurrentChar;
	var Flag = 0;
	var index = 0.1;
	
	if (isPstvDbl(paramString))
	{
		for(i=0;i<=paramString.length-1;i++)	//从左向右依次转换字符到数字
		{
			CurrentChar = paramString.charAt(i);
			
			if (CurrentChar == '.')	//判断字符是否为小数点
			{
				Flag = 1;	//是则记标记
			}
			else
			{
				if (Flag == 0)	//如果小数点未出现，则进行整数部分转换
				{
					RetVal = RetVal*10+(CurrentChar-'0');
				}
				else	//如果小数点已经出现，则进行小数部分转换
				{
					RetVal = RetVal+(CurrentChar-'0')*index;
					index = index * 0.1;
				}
			}
		}
		
		//以下为消除语言固有误差（如：123.1111-->123.11110000000001）
		//RetVal=RetVal.toString().substr(0,paramString.length);
		
		return RetVal;
	}
	else
	{
		return null;
	}
}



//+++++++++++++  业务应用函数  ++++++++++++++


//***************************************
//函数名称：isDblFormatWell
//功能：	判断输入double格式是否与指定格式相符
//参数：	paramString		输入数据
//		integerMaxLength	整数位数
//		decimalMaxLength	小数位数
//返回：	bool
//		true	相符
//		false	不符
//	
//备注：依赖基本类型函数isPstvDbl(),isPstvIntgr()
//***************************************

function isDblFormatWell(paramString,integerMaxLength,decimalMaxLength)
{
	var intIndexDot;
	if(isPstvDbl(paramString)&&isPstvIntgr(integerMaxLength)&&isPstvIntgr(decimalMaxLength))
	{	
		intIndexDot=paramString.indexOf(".");
		if(intIndexDot>=0)	//如果有小数部分,分开整数小数比较
		{
			if (paramString.substr(intIndexDot+1).length==decimalMaxLength&&
				paramString.substr(0,intIndexDot).length==integerMaxLength)
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		else
		{
			if (paramString.length==integerMaxLength&&decimalMaxLength==0)
			{
				return true;
			}
			else
			{
				return false;
			}
		}
	}
	else
	{
		return false;
	}
	
}


//***************************************
//函数名称：compareDate
//功能：比较两个日期的大小
//参数：startYear	开始年
//	startMonth	开始月
//	startDay	开始日
//	endYear		结束年
//	endMonth	结束月
//	endDay		结束日
//		
//返回：bool
//	true	结束日期晚于开始日期
//	false	结束日期等于或早于开始日期或者输入的日期非法
//	
//备注：依赖类型验证函数isVldDate()
//***************************************

function compareDate(startYear,startMonth,startDay,endYear,endMonth,endDay)
{
    var sDate;	//存放开始日期对象
    var eDate;	//存放结束日期对象
    if(isVldDate(startYear,startMonth,startDay)
    	&&isVldDate(endYear,endMonth,endDay))
    {	
    	//生成日期对象
    	sDate= new Date(startYear,startMonth-1,startDay);
    	eDate= new Date(endYear,endMonth-1,endDay);
    	
    	if (sDate<=eDate)	//比较大小
    	{
    		return true;
    	}
    	else
    	{
    		return false;
    	}
    }
    else
    {
    	return false;
    }
}

//****************************************
//功能：比较两个时间的大小
//
//返回：bool(true-结束时间大于开始时间，false-结束时间等于或早于开始时间
//****************************************
/*
function compareTime(startField, endField)
{
	startTimes = startField.split(strSeparatorTime);
	endTimes = endField.split(strSeparatorTime);
	
	if (!compareTime(startTimes[0], startTimes[1], startTimes[2], endTimes[0], endTimes[1], endTimes[2]))
		return false;
	else
		return true;
}
*/

//***************************************
//函数名称：compareTime
//功能：比较两个时间的大小
//参数：startHour	开始时
//	startMinute	开始分
//	startSecond	开始秒
//	endHour		结束时
//	endMintue	结束分
//	endSecond	结束秒
//		
//返回：bool
//	true	结束时间晚于开始时间
//	false	结束时间等于或早于开始时间或者输入的时间非法
//	
//备注：依赖类型验证函数isVldTime()
//***************************************

function compareTime(startHour,startMinute,startSecond,endHour,endMintue,endSecond)
{
		var sDate;	//存放开始时间对象
		var eDate;	//存放结束时间对象
		if(isVldTime(startHour,startMinute,startSecond)
			&&isVldTime(endHour,endMintue,endSecond))
		{	
			//生成时间对象
			sDate= new Date("2002","05","25",startHour,startMinute,startSecond);
			eDate= new Date("2002","05","25",endHour,endMintue,endSecond);
			
			if (sDate<=eDate)	//比较两个时间的大小
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		else
		{
			return false;
		}
}

//***************************************
//函数名称：compareDateTime
//功能：比较两个日期时间的大小
//参数：startYear	开始年
//	startMonth	开始月
//	startDay	开始日
//	startHour	开始时
//	startMinute	开始分
//	startSecond	开始秒
//	endYear		结束年
//	endMonth	结束月
//	endDay		结束日
//	endHour		结束时
//	endMintue	结束分
//	endSecond	结束秒
//		
//返回：bool
//	true	结束日期晚于开始日期时间
//	false	结束日期等于或早于开始日期时间或者输入的日期时间非法
//	
//备注：依赖类型验证函数isVldDate(),isVldTime()
//***************************************

function compareDateTime(startYear,startMonth,startDay,startHour,startMinute,startSecond,
			endYear,endMonth,endDay,endHour,endMintue,endSecond)
{
		var sDate;	//存放开始日期对象
		var eDate;	//存放结束日期对象
		if(isVldDate(startYear,startMonth,startDay)
			&&isVldDate(endYear,endMonth,endDay)
			&&isVldTime(startHour,startMinute,startSecond)
			&&isVldTime(endHour,endMintue,endSecond))
		{	
			//生成日期对象
			sDate= new Date(startYear,startMonth-1,startDay,startHour,startMinute,startSecond);
			eDate= new Date(endYear,endMonth-1,endDay,endHour,endMintue,endSecond);
			
			if (sDate<eDate)	//比较大小
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		else
		{
			return false;
		}
}

function isNum(mystr)
{
	if(mystr=="") return false;
    for (var i=0;i<mystr.length;i++)
    {
        if ((mystr.charAt(i) < "0") || (mystr.charAt(i) > "9"))
        return false;
    }
    return true;
}

function checkAcctParaSet(form) { 
	var oweDunDays = form.oweDunDays.value;
	var stopDays = form.stopDays.value;
	var	doubtDays = form.doubtDays.value;
	var	badDays = form.badDays.value;

	

	if(!isNum(oweDunDays))
	{
		alert(strOweDunDaysIsNum);
        form.oweDunDays.focus();
		return false;
	}

	if(!isNum(stopDays))
	{
		alert(strStopDaysIsNum);
        form.stopDays.focus();
		return false;
	}

	if(!isNum(doubtDays))
	{
		alert(strDoubtDaysIsNum);
        form.doubtDays.focus();
		return false;
	}

	if(!isNum(badDays))
	{
		alert(strBadDaysIsNum);
        form.badDays.focus();
		return false;
	}
	
	return true;
}


function degree(paramString) {
	var MaxIntegerLenth=2;	//length limit is 2
	var CurrentChar;
	var intLength = 0;
	
	if (paramString == "")	//can't be null
	{
		alert(strInvalidCredit);
		return false;
	}
	
	intLength = paramString.length;			
	
	if (intLength>MaxIntegerLenth)	//if length is larger than MaxIntegerLenth
	{
		alert(strInvalidCredit);
		return false;
	}
	
	for (i=0;i<intLength;i++)	//determine if it is a number
	{
		CurrentChar = paramString.charAt(i);			
		if ((CurrentChar < '0')||(CurrentChar > '9'))	//if it is not a number
		{
			alert(strInvalidCredit);
			return false;
		}
	}
	return true;
}

function checkCreditRule(form) {
    var highvalue = form.highDegree.value;
    var lowvalue = form.lowDegree.value;
	
    if(degree(highvalue) == true && degree(lowvalue) == true) {
        if(highvalue.length == 1) {
            highvalue = "0" + highvalue;
        }
        if(lowvalue.length == 1) {
            lowvalue = "0" + lowvalue;
        }
        if(highvalue <= lowvalue) {
		alert(strCreditLimit);
		return false;
        } 
    }
    else {
        return false;
    }
    
    return validateCCreditRuleForm(form);
}

function checkCDRSearchDate(form) {
	if(!(isVldDateString(form.startTimeFrom.value))) {
        alert(strErrStartDateFmtFrom);
		return false;
    }
	if(!(isVldDateString(form.startTimeTo.value))) {
		alert(strErrStartDateFmtTo);
		return false;
	}
	
	return true;
}

function isVldDateString(paramString)
{	
	var strArray;
	var i;
	
	if(paramString == "") {
		return true;
	}
	strArray=paramString.split(strSeparatorDate);
	
	var arryear;
	var arrmonth;
	var arrday;
	var intYear;
    var intMonth;
    var intDay;
    var boolLeapYear;
        
    if(strArray.length==3)
	{       
        arrmonth = strArray[0];
        arrday = strArray[1];
        arryear = strArray[2];
        
        if(arryear.length != 4)  return false;
        
        intYear = StrToInt(arryear);
        intMonth = StrToInt(arrmonth);
        intDay = StrToInt(arrday);
        
        if(isNaN(intYear)||isNaN(intMonth)||isNaN(intDay)) return false;
        
        if(intMonth>12||intMonth<1) return false;
        
        if ((intMonth==1||intMonth==3||intMonth==5||intMonth==7||intMonth==8||intMonth==10||intMonth==12)&&(intDay>31||intDay<1))
        {
            return false;
        }
              
        if((intMonth==4||intMonth==6||intMonth==9||intMonth==11)&&(intDay>30||intDay<1))
        {
            return false;
        }
            
        if(intMonth==2){
     	    if(intDay<1) return false;
    
            boolLeapYear = false;
            if((intYear%100)==0) {
                if((intYear%400)==0) boolLeapYear = true;
      	    }
      	    else {
        	    if((intYear%4)==0) boolLeapYear = true;
     	    }
    
     	    if(boolLeapYear) {
        	    if(intDay>29) return false;
      	    }
      	    else {
        	    if(intDay>28) return false;
      		}
        }
    
         return true;
    }
	else {      
        return false;	
    }
}

function CheckAll(form) {
    chkallresult.checked = false;
    for (var i=0;i<form.elements.length;i++)
    {
        var e = form.elements[i];
        e.checked = chkall.checked;
    }
}

function CheckAllResult(form){
    form.isselectall.value=chkallresult.checked;
    chkall.checked = false;
    for (var i=0;i<form.elements.length;i++)
    {
        var e = form.elements[i];
        e.checked = chkallresult.checked;
    }
}

function checkCardInit(form) {
	if(form.name.value == "") {
		alert(strNeedName);
		form.name.focus();
		return false;
		
	}

	if(form.type[0].checked == false && form.type[1].checked == false) {
		alert(strNeedType);
		return false;
	}
	
	if(form.type[0].checked == true && !isPstvDbl(form.credit.value)) {
		alert(strNeedIniCost);
		form.credit.focus();
		return false;
	}
	
	if(form.type[1].checked == true && !isPstvIntgr(form.duration.value)) {
		alert(strNeedIniDuration);
		form.duration.focus();
		return false;
	}
	return true;
}


function checkSearchCardExpireDate(form) {
	if(!(isVldDateString(form.expireFrom.value) && isVldDateString(form.expireTo.value))) {
		alert(strErrExpireDate);
		return false;
	}
	
	return validateCardSearchForm(form);
}

function checkDiscount(form) {  
        
 	if(!isPstvDbl(form.discRate.value)) {
		alert(strRateIsFloat);
		form.discRate.focus();
		return false;
	}
	if(form.discType.value == "1") {
	    if(form.beginDate.value == "" || !isVldDateString(form.beginDate.value))
	    {
	 		alert(strNeedBeginEndDate);
	 		form.beginDate.focus();
	 		return false;
	    }	
	    else if(form.endDate.value == "" || !isVldDateString(form.endDate.value))
	    {
	 		alert(strNeedBeginEndDate);
	 		form.endDate.focus();
	 		return false;
	    }
	 	else if(!compareDateString(form.beginDate.value, form.endDate.value)) {
 	        form.beginDate.focus();
 	        return false;
        }
	}
	else if(form.discType.value == "2"){
		
 	}
 	else if(form.discType.value == "3") {
        if(!isVldDateString(form.beginDate.value) || !isVldDateString(form.endDate.value)) {
            alert(strNeedBeginEndDate);
            form.beginDate.focus();
            return false;
        }
        else if(!isVldTimeString(form.beginTime.value) || !isVldTimeString(form.endTime.value)) {
            alert(strNeedBeginEndTime);
            form.beginTime.focus();
            return false;
        }
        else if(!compareDateString(form.beginDate.value,form.endDate.value)) { 
            form.beginDate.focus();
            return false;
        }
        else if(!compareTimeString(form.beginTime.value,form.endTime.value)) {
            form.beginTime.focus();
            return false;
        }
 	}
 	else {
 		if(!isPstvIntgr(form.beginDuration.value)) {
 			alert(strNeedBeginDuration);
 			form.beginDuration.focus();
 			return false;
 		}
 		if(!isPstvIntgr(form.endDuration.value)) {
 			alert(strNeedBeginDuration);
 			form.endDuration.focus();
 			return false;
 		}
 		
 		var begindur = form.beginDuration.value;
 		var enddur = form.endDuration.value;
 		
 		if(StrToInt(begindur) > StrToInt(enddur)) {
 			alert(strErrDurationRange);
 			form.beginDuration.focus();
 			return false;	
 		}
 	}
 	return true;
}                     
 
function compareDateString(startdate, enddate) 
{
	var strArrayTime;
	var strArrayDate;
	var strArray;
	var i;

    var startyear;
	var startmonth;
	var startday;
	var endyear;
	var endmonth;
	var endday;   

	if (startdate != "")
	{
		strArray=startdate.split(strSeparatorDate);
		if (strArray.length==3)
		{
			startyear = strArray[0];
			startmonth = strArray[1];
			startday = strArray[2];

		}
		else
		{
	 		alert("日期格式不正确，请检查您的输入数据。");
	 		return false;		
		}
	}
					
	if (enddate != "")
	{
		strArray=enddate.split(strSeparatorDate);
		if (strArray.length==3)
		{
			endyear = strArray[0];
			endmonth = strArray[1];
			endday = strArray[2];
		}
		else
		{
	 		alert("日期格式不正确，请检查您的输入数据。");
	 		return false;
		}
	}

	if (startdate != "" && enddate != "")
	{
		if(!compareDate(startyear,startmonth,startday,endyear,endmonth,endday))
			return false;
		else
		  return true;	
	}
}
   
function compareTimeString(starttime,endtime) 
{
	var strArrayTime;
	var strArrayDate;
	var strArray;
	var i;  
	
	var starthour;
	var startminute;
	var startsecond;
	var endhour;
	var endminute;
	var endsecond;
	
	if(starttime != "") {
		strArray=starttime.split(strSeparatorTime);
		if(strArray.length==3) {
			starthour = strArray[0];
			startminute = strArray[1];
			startsecond = strArray[2];

		} else {
	 		//alert(strErrBeginDateFmt);
	 		return false;			
		}
	}
					
	if(endtime != "") {
		strArray=endtime.split(strSeparatorTime);
		if(strArray.length==3) {
			endhour = strArray[0];
			endminute = strArray[1];
			endsecond = strArray[2];

		} else {
			//alert(strErrEndDateFmt);
			return false;			
		}
	}
	if(starttime != "" && endtime != "") {
		if(starthour>endhour) {
			//alert(strErrTimeRange);
			return false;
        }
		else if(starthour == endhour && startminute > endminute) {
            //alert(strErrTimeRange);
			return false;
        }
        else if(starthour == endhour && startminute == endminute && startsecond > endsecond) {
            //alert(strErrTimeRange);
            return false;
        }  
        else {
            return true;
        }	
	}
	return true;
}
 

function checkCallRecordQuery(form) { 
	
	var starttime = form.starttime.value;
	var endtime = form.endtime.value;
	var startdate = form.startdate.value;
	var enddate = form.enddate.value;
	
	if((form.startdate.value == "" && form.starttime.value != "") || (form.enddate.value == "" && form.endtime.value != "")) {
		alert(strTimeNeedDate);
		return false;
	}
		
	if(!isVldDateString(form.startdate.value)) {
	    alert(strErrStCllingFrmDate);
		return false;
	}
	
	if(!isVldDateString(form.enddate.value)) {
		alert(strErrStCllinToDate);
		return false;
	}
            
	if(!isVldTimeString(form.starttime.value)) {
	    alert(strErrStCllingFrmTime);
		return false;
	}
	
	if(!isVldTimeString(form.endtime.value)) {
		alert(strErrStCllinToTime);
		return false;
	}
            
	var strArrayTime;
	var strArrayDate;
	var i;

	var starthour;
	var startminute;
	var startsecond;
	var endhour;
	var endminute;
	var endsecond;

	var startyear;
	var startmonth;
	var startday;
	var endyear;
	var endmonth;
	var endday;

	if(startdate != "") {
		
		strArray=startdate.split(strSeparatorDate);
		if(strArray.length==3) {
			startyear = strArray[2];
			startmonth = strArray[0];
			startday = strArray[1];

		} else {
	 		alert(strErrStCllingFrmDate);
	 		return false;			
		}
	}
					
	if(enddate != "") {
		
		strArray=enddate.split(strSeparatorDate);
		if(strArray.length==3) {
			endyear = strArray[2];
			endmonth = strArray[0];
			endday = strArray[1];

		} else {
			alert(strErrStCllinToDate);
			return false;			
		}
	}
	if(startdate != "" && enddate != "") {
		if(!compareDate(startyear,startmonth,startday,endyear,endmonth,endday)) {
		        
			alert(strStCllngFrmTimeLater);
			return false;
		}	
	}
	if(!isPstvIntgr(form.firstDuration.value, "no") || !isPstvIntgr(form.lastDuration.value,"no")) {
		alert(strNeedDuration);
		return false;
	}
	if(StrToInt(form.firstDuration.value) > StrToInt(form.lastDuration.value)) {
		alert(strErrDurationRange);
		return false;
	}
	
	return true;
}

function isVldTimeString(paramString)
{	
	var strArray;
	var i;
	
	if(paramString == "") {
		return true;
	}
	
	strArray=paramString.split(strSeparatorTime);
	
	if(strArray.length==3)
	{       
		if( strTrim(strArray[0])==""||
		    strTrim(strArray[1])==""||
		    strTrim(strArray[2])==""||
			strArray[0].length!=2||
			strArray[1].length!=2||
			strArray[2].length!=2||
			StrToInt(strArray[0])>=24||
			StrToInt(strArray[0])<0||
			StrToInt(strArray[1])>=60||
			StrToInt(strArray[1])<0||	
			StrToInt(strArray[2])>=60||
			StrToInt(strArray[2])<0     )	
			{
				return false;
			}
			
		return true;
	}
	else
	{
		return false;	
	}
}

function checkSingleCardDate(form) {
	if(!(isVldDateString(form.from.value) && isVldDateString(form.to.value))) {
		alert(strErrFromToDateFmt);
		return false;
	}
	
	return true;
}

function checkSingleCardFloat(form) {
	if(!isPstvDbl(form.rechargeValue.value)) {
		alert(strNeedRecharge);
		form.rechargeValue.focus();
		return false;
	}
	if(form.cardType.value == "1") {
        alert(strNotRechargable);
        return false;
	}
	return true;
}

function checkModifyExpireInput(form) {
        
    if (form.expireModifyMethod[0].checked) {
        var str = form.expireNewDate.value;
        if(strTrim(str) == "") {
            alert(strNeedDate);
            form.expireNewDate.focus();
            return false;
        }
        
        if (!isVldDateString(str))
        {
            alert(strErrNewExpireDate);
            return false;
        }
    }
    else  {
        str = form.expireAddMinusDays.value;
        if(strTrim(str) == "") {
            alert(strNeedModifyDays);
            form.expireAddMinusDays.focus();
            return false;
        }
        else  if (!isPstvIntgr(strTrim(str))) {
            alert(strNeedModifyDays);
            form.expireAddMinusDays.focus();
            return false;
        }
    }

    return true;
}

function checkRateItemsEdit(form) {
	
	if(!isPstvIntgr(form.unitDuration.value) || !(form.unitDuration.value != "0")) {
		alert(strDurationIsZero);
		form.unitDuration.focus();
		return false;
	}
	
	if(!isPstvDbl(form.unitDurationFee.value) || StrToDbl(form.unitDurationFee.value) == 0.0) {
		alert(strFeeIsZero);
		form.unitDurationFee.focus();
		return false;
	}
	
	return validateRateItemForm(form);
}

function checkCustRegist(form) {
	if(document.CCustomerForm.cusName.value == "" && document.CCustomerForm.cusType[0].checked == false) {
		alert(strNeedCustName);
		return false;
	}
	
	if(document.CCustomerForm.passWordConfirm.value != document.CCustomerForm.passWord.value) {
		alert(strPassNotConsistent);
		return false;
	}
	return validateCCustomerForm(form);
}

function operatorCardList(form, type) {
    form.operatetype.value=type;
    
    var atLeastCheck;
    for (var i=0;i<form.elements.length;i++) {
    	if(form.elements[i].checked) {
    		atLeastCheck = form.elements[i].checked;
    	}
    }
    if(!atLeastCheck && form.operatetype.value != 'export') {
    	alert(strNeedCheck);
    } else {
    	form.submit();
    }
}

function checkModifyCreditInput(form) {
	if((!isPstvDbl(form.creditNewValue.value) && form.creditModifyMethod[0].checked)
		|| (!isPstvDbl(form.creditAddMinusValue.value) && !form.creditModifyMethod[0].checked)) {
		alert(strNeedCost);
		return false;
	}
	return true;
}

function operatorCustomer(form, type, value)
{
	var atLeastCheck;
	form.operatetype.value=type;
	form.operatevalue.value=value;
  	for (var i=0;i<form.elements.length;i++) {
    	if(form.elements[i].checked) {
    		atLeastCheck = form.elements[i].checked;
    	}
   	}
   	if(atLeastCheck) {
   		if(type == "degree") {
	   		if(degree(value)) {
				form.submit();
			}
		} else {
			if(isPstvDbl(value)) {
				form.submit();
			} else {
				alert(strOverDraftNotDouble);
			}
		}
	} else {
		alert(strNeedCheck);
	}
}

function realCard(form) {
    if(form.isCard[0].checked == true) {
    	alert(strCardIsOntime);
    	form.isRealTime[0].checked=true;
    }
}

function checkCusProdWholeSale(form, servicetype)
{
	if(servicetype == 13)
	{
		var serviceinfo = form.serviceinfo.value;
		if(isVldIP(serviceinfo)==false)
		{
			alert(strServiceInfoIsIP);
			return false;
		}	
	}

	form.submit();
}


function adjust(form) {
	if(form.type[0].checked) {
		return degree(form.operatevalue.value);
	}
	form.operatetype.value = "limitowe";
	if(!isPstvDbl(form.limitvalue.value)) {
		alert(strOverDraftNotDouble);
		return false;
	}
	return true;
}

function onlyInt(paramString) {
    if (!isPstvDbl(paramString)) {
        alert(strNeedNumber);
        return false;
    }
    return ture;
}

function checkCustomer(form) {
	//if(form.cusName.value == "" && form.cusType[0].checked == false) {
	if(form.cusName.value == "") {	
		alert(strNeedCustName);
		return false;
	}
	
	if(form.passWordConfirm.value != form.passWord.value) {
		alert(strPassNotConsistent);
		return false;
	}
	return validateCCustomerServiceForm(form);
}

function checkUserAcctAdd(form) {		
	if(form.passwordconfirm.value != form.password.value) {
		alert(strPassNotConsistent);
		return false;
	}
	return validateCUserAcctAddForm(form);
}

function checkUserAcctModify(form) {		
	if(form.passwordconfirm.value != form.password.value) {
		alert(strPassNotConsistent);
		return false;
	}
	return validateCUserAcctModifyForm(form);
}

function checkLogQuery(form) { 
	
	if(!isVldDateString(form.startime.value)) {
	    alert(strErrOperDateFrmFmt);
	    form.startime.focus();
		return false;
	}
	if(!isVldDateString(form.endtime.value)) {
		alert(strErrOperDateToFmt);
		form.endtime.focus();
		return false;
	}

	if (form.startime.value !="" && form.endtime.value != "" && !compareDateString(form.startime.value,form.endtime.value)) {
	    form.endtime.focus();
		return false;
	}
	
	return true;
}

function checkOperatorAdd(form) {
    if(form.operatorclass[0].checked) {
        if(form.resallerroleid.value == "") {
            alert(strNeedResellRole);
            return false;
        }
    }
    if(form.passwordconfirm.value != form.password.value) {
    	alert(strPassNotConsistent);
    	return false;
    }
    return validateCOperatorForm(form);
}

function checkOperatorInput(form) {
	
	if(form.passwordconfirm.value != form.password.value) {
		alert(strPassNotConsistent);
		return false;
	}
	return validateCOperatorForm(form);
}

function checkRegionSearch(form){
	if(form.nameOrCode[0].checked && form.name.value=="") {
		alert(strNeedName);
		form.name.focus();
		return false;
	}
	if(form.nameOrCode[1].checked && (form.countryCode.value=="" || !isNum(form.countryCode.value))) {
		alert(strNeedCode);
		form.countryCode.focus();
		return false;
	}
	return true;
}

function checkRateItemsInput(form) {
	
	if(!isPstvIntgr(form.unitDuration.value) || !(form.unitDuration.value != "0")) {
		alert(strDurationIsZero);
		form.unitDuration.focus();
		return false;
	}
	
	if(!isPstvDbl(form.unitDurationFee.value) || StrToDbl(form.unitDurationFee.value) == 0.0) {
		alert(strFeeIsZero);
		form.unitDurationFee.focus();
		return false;
	}
	
	return validateRateItemForm(form);
}

function checkRateTableInput(form) {
	if(form.byCallerArea.checked == false && 
	   form.byCalledArea.checked == false &&
	   form.byCalledNation.checked == false &&
	   form.byPortName.checked == false &&
	   form.byNumberRange.checked == false &&
	   form.byAccessNumber.checked == false &&
	   form.byIngressGW.checked == false &&
	   form.byEgressGW.checked == false) {
            alert(strNeedCheck);
		    return false;
	}
	
	if(!isPstvIntgr(form.defUnitDuration.value) || (form.defUnitDuration.value == "0")) {
		alert(strDefDurationIsZero);
		form.defUnitDuration.focus();
		return false;
	}
	
	if(!isPstvDbl(form.defUnitFee.value) || StrToDbl(form.defUnitFee.value) == 0.0) {
		alert(strDefFeeIsZero);
		form.defUnitFee.focus();
		return false;
	}
	
	return validateRateTableForm(form);
}

function checkRateTableEdit(form) {
	
	if(!isPstvIntgr(form.defUnitDuration.value) || (form.defUnitDuration.value == "0")) {
		alert(strDefDurationIsZero);
		form.defUnitDuration.focus();
		return false;
	}
	
	if(!isPstvDbl(form.defUnitFee.value) || StrToDbl(form.defUnitFee.value) == 0.0) {
		alert(strDefFeeIsZero);
		form.defUnitFee.focus();
		return false;
	}
	
	return validateRateTableForm(form);
}

function checkStatDate(form) {
	if(!isVldDateString(form.startdate.value)) {
	    alert(strErrStatFromDateFmt);
		form.startdate.focus();
		return false;
	}
	
	if(!isVldDateString(form.enddate.value)) {
		alert(strErrStatToDateFmt);
		form.enddate.focus();
		return false;
	}
            
	if(!isVldTimeString(form.starttime.value)) {
	    alert(strErrStatFromTimeFmt);
		form.starttime.focus();
		return false;
	}
	
	if(form.startdate.value == "" && form.starttime.value != "") {
		alert(strTimeNeedDate);
		form.startdate.focus();
		return false;
	}
		
	if(!isVldTimeString(form.endtime.value)) {
		alert(strErrStatToTimeFmt);
		form.endtime.focus();
		return false;
	}
            
	if(form.enddate.value == "" && form.endtime.value != "") {
		alert(strTimeNeedDate);
		form.enddate.focus();
		return false;
	}
		
	if(form.startdate.value != "" && form.enddate.value != "") {
		if(!compareDateString(form.startdate.value,form.enddate.value)) {
		    form.enddate.focus();
			return false;
		}
		else if ((form.startdate.value == form.enddate.value) && !compareTimeString(form.starttime.value,form.endtime.value)) {
		    form.endtime.focus();
			return false;
		}
	}
	
    return true;
}

function checkAccessNumStat(form) {
	
	if(!checkStatDate(form)) {
		return false;
	}
	
	return validateCAccessNumberStatForm(form);
}

function checkCalledAreaStat(form) { 
	
    if(form.countrycode.value == "") {
        alert(strNeedCountryCode);
        form.countrycode.focus();
        return false;
    }
	
	if(!checkStatDate(form)) {
		return false;
	}
	
	return validateCCalledAreaStatForm(form);
}

function checkPlanTypeStat(form) {
	return checkStatDate(form);
}

function checkCalledCountryStat(form) { 
	if(!checkStatDate(form)) {
		return false;
	}
	
	return validateCCalledCountryStatForm(form);
}

function checkEgressGWStat(form) { 
	if(!checkStatDate(form)) {
		return false;
	}
	
	return validateCEgressGWStatForm(form);
}

function checkIngressGWStat(form) { 
	if(!checkStatDate(form)) {
		return false;
	}
	
	return validateCIngressGWStatForm(form);
}

function checkIntervalStat(form) { 
	return checkStatDate(form);
}

function checkExpiredCardStat(form) { 
	return checkStatDate(form);
}

function checkCDRStat(form) { 
	
	if(form.stattype.value == 7) {
	   if(form.countrycodearea.value == "") {
            alert(strNeedCountryCode);
            return false;
        }
    }
	
	if(!checkStatDate(form)) {
		return false;
	}
	
	return validateCCDRStatForm(form);
}

//***************************************
//函数名称：validDir
//功能：判断输入的内容是否为合法的Windows系统目录
//参数：startYear	开始年
//返回：bool
//	true	输入内容格式符合windows系统要求，不包含非法的字符
//	false	输入内容格式不符合windows系统要求，或者包含非法的字符
//	
//备注：依赖类型验证函数isValidChar()
//***************************************
 function validDir(textObj){
	var dir=textObj.value;
	var regExp=/[a-zA-Z]:(\\)([^\/:]?)(:\d*)?([^# ]*)/;
	var result=dir.match(regExp);

	if(result==null){
			return false;
		}
	if(!isValidChar(textObj)){
			return false;
		}
		return true;	
 }

 function isValidChar(textObj){
	 var newValue=textObj.value;
	 var newLength=newValue.length;
	 var extraChars="!*|<?>";

	 for(var i=0;i!=newLength;i++){
			aChar=newValue.substring(i,i+1);
			aChar=aChar.toUpperCase();
			search=extraChars.indexOf(aChar);	
			if(search!=-1){
				return false;
				}
		 }
		 return true;	 
	 }

function getUnicodeLength(value)
{
	return value.replace(/[^\x00-\xff]/g,"*").length;
}  

function realTimeCount(content,hints)
{ 
    var  max = content.getAttribute('maxlength');
    var  len = getUnicodeLength(content.value);
    var  n = max-len;
    if(n < 0){
    	alert("输入文本长度超过最大长度");
    	content.value = content.value.substr(0, max);
    	n = max-getUnicodeLength(content.value);
    }
    document.getElementById(hints).innerHTML = "("+ (n>0? n:"<font color=red> "+n+"</font>")+"/"+max+") ";        
}

function realTimeCountClip(content,hints)
{ 
    var  max = content.getAttribute('maxlength');
    var  len1 = getUnicodeLength(content.value);
    var  clip = window.clipboardData.getData('text');
    var  len2 = getUnicodeLength(clip);
    var  n = max - len1 - len2;
    if(n < 0){
    	content.value = content.value.substr(0, max);
    	document.getElementById(hints).innerHTML = "("+ ("<font color=red> " + (max - len1) + "</font>")+"/" + max + ") ";
    	alert("粘贴文本长度超过最大长度");
    	return false;
    }
	document.getElementById(hints).innerHTML = "("+ (n > 0 ? n : "<font color=red> " + n + "</font>")+"/" + max + ") ";
}

//输入屏蔽sql关键字 '\'与'%'
function checkManyNameValue(value){
	var regExp=/^(?!.*?[%\\]).*$/;
	return regExp.test(value);
}

//输入检查登录ID限制为a-zA-Z1-9   ^(?:[\u4e00-\u9fa5]*\w*\s*)+$
function checkLoginId(value){
	var regExp=/^(?:[\u4e00-\u9fa5]*\w*\s*)+$/;
	return regExp.test(value);
}

//用户名校验
function checkUserName(value){
	var regExp=/^(?:[\u4e00-\u9fa5]*\w*\s*\(*\)*\.*\,*\（*\）*\，*\·*)+$/;
	return regExp.test(value);
}

function heigthReset(heigth){
	   document.getElementById('main').style.height = (document.body.clientHeight - heigth) + 'px';
}