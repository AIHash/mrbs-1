package com.wafersystems.mrbs.exception;

public class InfoExistException extends BaseException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8280852855015965989L;

	public InfoExistException(){
		super();
	}
	
	public InfoExistException(String message){
		super(InfoExistException.class, message);
	}
	
}
