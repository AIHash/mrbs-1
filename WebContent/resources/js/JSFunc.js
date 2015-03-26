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






//++++++++++++  基本数据类型验证函数  ++++++++++++++



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

function isPstvIntgr(paramString)
{
	var MaxIntegerLenth=9;	//整数不大于9位
	var CurrentChar;
	var intLength = 0;
	
	if (strTrim(paramString)=="")	//如果为空字符串
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
     if (strInput.charAt(i) != '' && strInput.charAt(i) != ' ')
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
   	 return 
false;
   
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
    if(isNull(str))
       return false;
    else
    {
       str=strTrim(str);
       pos=str.indexOf("@");
       if(pos<=0)	//是否包含"@"
          return false;
       else
       {
          strTemp=str.substring(pos+1,str.length -1);
          strTemp=strTrim(strTemp);
          if(strTemp=="")	//"@"右端是否为空
            return false;
          else if(strTemp.indexOf(".")<=0)	//右端是否包含"." 且不是最右
             return false;
          else
             return true;
       }
    }
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
	var strSeparator = "."; //IP分隔符
	var strArray;
	var i;
	
	strArray=paramString.split(strSeparator);
	
	if(strArray.length==4) //如果输入被"."分为四部分
	{
		for(i=0;i<4;i++)
		{
			if(strTrim(strArray[i])==""||
				strTrim(strArray[i])!=strArray[i]||
				strArray[i].length>3||
				(strArray[i].substr(0,1)=="0"&&strArray[i]!="0")||
				(!isPstvIntgr(strArray[i]))||
				StrToInt(strArray[i])>254||
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
					else
					{
					
					}
				}
				else
				{
				
				}
			}
		}//for
		
		return true;
	}
	else
	{
		return false;	
	}
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
			alert();
			
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
			
			if (sDate<eDate)	//比较两个时间的大小
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

function compareCNDate(beginDate, endDate) {
                
	var strSeparatorDate = "-"; //Date分隔符
	var beginArray;
	var endArray;
	var i;

	var startyear;
	var startmonth;
	var startday;
	var endyear;
	var endmonth;
	var endday;
	
	beginArray	= beginDate.split(strSeparatorDate);
	startYear 	= StrToInt(beginArray[0]);
	startMonth 	= StrToInt(beginArray[1]);
	startDay 	= StrToInt(beginArray[2]);
	
	endArray 	= endDate.split(strSeparatorDate);
	endYear 	= StrToInt(endArray[0]);
	endMonth 	= StrToInt(endArray[1]);
	endDay 		= StrToInt(endArray[2]);
	
	if(startYear > endYear) {
	   	return false;
	} else if(startYear == endYear) {
		if(startMonth > endMonth) {
	    	return false;
		} else if(startMonth == endMonth) {
	    	if(startDay > endDay) {
	        	return false;
	     	}
	  	}
	}
	return true;
}	

 function isVldCNDate(paramString) {	
 	
	var strSeparator = "-"; //Time分隔符
	var strArray;
	var i;
	
	if(paramString == "") {
		return true;
	}
	strArray=paramString.split(strSeparator);
	
	var arryear;
	var arrmonth;
	var arrday;
	var intYear;
    var intMonth;
    var intDay;
    var boolLeapYear;
    
	if(strArray.length == 3) {       
		arryear = strArray[0];
		arrmonth = strArray[1];
		arrday = strArray[2];
	    
	    if(arryear.length != 4)  return false;
	        
	    intYear = StrToInt(arryear);
        intMonth = StrToInt(arrmonth);
        intDay = StrToInt(arrday);
        
        if(isNaN(intYear)||isNaN(intMonth)||isNaN(intDay)) return false;
          
        if(intMonth>12||intMonth<1) return false;
	        
	    if ((intMonth == 1 || intMonth == 3 || intMonth == 5 || 
	    		intMonth == 7 || intMonth == 8 || intMonth == 10 || intMonth == 12) && ( intDay > 31 || intDay < 1)){
        	return false;
        }
                  
        if((intMonth==4||intMonth==6||intMonth==9||intMonth==11)&&(intDay>30||intDay<1)) {
        	return false;
        }
                
        if(intMonth==2){
	    	if(intDay<1) return false;
  			boolLeapYear = false;
	   	   	if((intYear%100)==0) {
	        	if((intYear%400)==0) boolLeapYear = true;
	      	} else {
	        	if((intYear%4)==0) boolLeapYear = true;
	     	}
  
	     	if(boolLeapYear) {
	        	if(intDay>29) return false;
	      	} else {
	        	if(intDay>28) return false;
	      	}
		}
	} else {      
		return false;	
	}
	return true;
}




