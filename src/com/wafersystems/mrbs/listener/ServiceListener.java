/*
 * Title: IPB System
 * Copyright: Copyright (c) 2004
 * Company: Wafer Systems Ltd
 * All rights reserved.
 * 
 * Created on 2005-5-23 by AnDong
 * JDK version used : 1.4.1_02
 */

package com.wafersystems.mrbs.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import com.wafersystems.mrbs.GlobalParam;
import com.wafersystems.mrbs.service.LicenseService;
import com.wafersystems.mrbs.service.impl.LicenseServiceImpl;

/**
 * 
 * @author moon
 * 
 */
public class ServiceListener implements ServletContextListener
{
	
	private final static Logger log = LoggerFactory.getLogger(ServiceListener.class);
	
//	private BaseDataService baseDataService;
//	private static Map<String,List> baseMap;
	private static ApplicationContext springContext; 

	@Override
	public void contextInitialized(ServletContextEvent arg0)
	{
		try
		{
			LicenseService licenseService = new LicenseServiceImpl();
			if (!licenseService.checkLicense())
			{
				System.exit(0);
			}
// 		   springContext = WebApplicationContextUtils.getWebApplicationContext(arg0.getServletContext()); 
//
//			baseMap=new HashMap <String,List>();
//			//加载数据库基础信息
//			baseDataService=(BaseDataServiceImpl)springContext.getBean("baseDataService");
//			System.out.println("在哪呢？");
//			baseMap.put("deptment", baseDataService.getdeptment());		
//			List templist=baseMap.get("deptment");
//			if(templist!=null)
//			{
//				for(int i=0;i<templist.size();i++)
//				{
//					Department dept=(Department)templist.get(i);
//					System.out.println("部门："+dept.getName());
//				}
//			}
			
			
			GlobalParam.LICENSE_STATE = true;
		}
		catch (Exception e)
		{
			log.error("系统初始化错误：", e);
			System.exit(0);
		}
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce)
	{
	}
	
//    public static ApplicationContext getApplicationContext() 
//    { 
//	        return springContext; 	    
//    } 
//
//	
//	@Resource(type = BaseDataService.class)
//	public void setBaseDataService(BaseDataService basedataservice) {
//		this.baseDataService = basedataservice;
//	}

}