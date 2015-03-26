package com.wafersystems.mrbs.exception;

public class LoginFailedException extends BaseException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1745063479439505623L;
	
	public LoginFailedException(){
		super();
	}
	
	public LoginFailedException(String message){
		super(LoginFailedException.class, message);
	}

}
