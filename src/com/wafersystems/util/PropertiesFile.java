package com.wafersystems.util;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.Enumeration;
import java.util.Properties;

import org.apache.log4j.Logger;

public class PropertiesFile {
	
	private static Logger logger = Logger.getLogger(PropertiesFile.class);
	private String fileName = null;
	private Properties property = new Properties();

	public PropertiesFile(String fileName) throws Exception {
		this.fileName = fileName;
		InputStream is = null;
		try {
//			 is = PropertiesFile.class.getResourceAsStream(fileName);
//			is = new FileInputStream(fileName); // find file in classpath.
			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		    if (null == classLoader){
		    	classLoader = PropertiesFile.class.getClassLoader();
		    }
			is = classLoader.getResourceAsStream(fileName);
			property.load(is);

			Enumeration<Object> enur = property.keys();
			String keyName = null;
			String unicodeValue = null;
			while (enur.hasMoreElements()) {
				keyName = (String) enur.nextElement();
				unicodeValue = new String(property.getProperty(keyName).getBytes("UTF-8"));
				property.setProperty(keyName, unicodeValue);
				// logger.debug(keyName + "=" + property.getProperty(keyName));
			}
		} finally {
			try {
				if(is!=null)is.close();
			} catch (Exception e) {
				logger.error("InputStream close faile", e);
			}
		}
	}

	public String get(String key) {
		return property.getProperty(key);
	}

	public void set(String key, String value) {
		property.setProperty(key, value);
	}

	public void save(String desc) throws Exception {
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(fileName);

			Timestamp ts = new Timestamp(System.currentTimeMillis());
			property.store(fos, desc + " " + ts.toString());
		} finally {
			try {
				fos.close();
			} catch (Exception e) {
				logger.error("FileOutputStream close faile", e);
			}
		}
	}

}
