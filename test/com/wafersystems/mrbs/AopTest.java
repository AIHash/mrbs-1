package com.wafersystems.mrbs;

import junit.framework.TestCase;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.wafersystems.mrbs.service.user.UserService;

public class AopTest extends TestCase {
	
	private UserService userService;
	
	@Override
	protected void setUp() throws Exception {
		ApplicationContext context = new FileSystemXmlApplicationContext("./WebContent/WEB-INF/spring/root-context.xml");
		userService = context.getBean(UserService.class);
	}
	
	@Test
	public void testAop(){
		try{
			userService.checkUserLogin("admin", "admin");
			userService.checkUserExit("admin");
		}catch(Exception e){}
	}

}
