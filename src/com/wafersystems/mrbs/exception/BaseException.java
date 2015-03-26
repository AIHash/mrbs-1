package com.wafersystems.mrbs.exception;

public class BaseException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6011998777993024191L;
	
	public static String MESSAGE = "MRBS EXCEPTION";
	
	public BaseException(){
		super(MESSAGE);
	}
	
	public <T> BaseException(Class<T> clazz, String message){
		super(MESSAGE + " > " + clazz.getName() + " > " + message);
	}

}
