package com.wafersystems.mrbs.exception;

public class CreateFailedException extends BaseException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8280852855015965989L;

	public CreateFailedException(){
		super();
	}
	
	public CreateFailedException(String message){
		super(CreateFailedException.class, message);
	}
	
}
