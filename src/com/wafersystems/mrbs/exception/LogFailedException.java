package com.wafersystems.mrbs.exception;

public class LogFailedException extends BaseException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8280852855015965989L;

	public LogFailedException(){
		super();
	}
	
	public LogFailedException(String message){
		super(LogFailedException.class, message);
	}
	
}
