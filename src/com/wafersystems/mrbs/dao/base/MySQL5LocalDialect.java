package com.wafersystems.mrbs.dao.base;

import org.hibernate.Hibernate; 
import org.hibernate.dialect.MySQL5Dialect; 
import org.hibernate.dialect.function.SQLFunctionTemplate ; 


public class MySQL5LocalDialect extends MySQL5Dialect { 

	public MySQL5LocalDialect(){
		super(); 
		registerFunction("convert_gbk", new SQLFunctionTemplate(Hibernate.STRING, "convert(?1 using gbk)"));  
	}
}

